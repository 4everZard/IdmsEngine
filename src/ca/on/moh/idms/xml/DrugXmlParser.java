package ca.on.moh.idms.xml;

import gov.moh.app.db.DBConnectionManager;
import gov.moh.config.PropertyConfig;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import ca.on.moh.idms.vo.Drug;
import ca.on.moh.idms.vo.Extract;
import ca.on.moh.idms.vo.Formulary;
import ca.on.moh.idms.vo.GenericName;
import ca.on.moh.idms.vo.Organization;
import ca.on.moh.idms.vo.Pcg2;
import ca.on.moh.idms.vo.Pcg6;
import ca.on.moh.idms.vo.Pcg9;
import ca.on.moh.idms.vo.PcgGroup;

public class DrugXmlParser extends SaxXmlParser {

	private final static String TAG_EXTRACT = "extract";
	private final static String TAG_MANUFTURERLIST = "manufacturerList";
	private final static String TAG_FORMULARY = "formulary";
	private final static String TAG_MANUFTURER = "manufacturer";
	private final static String TAG_PCG2 = "pcg2";
	private final static String TAG_PCG6 = "pcg6";
	private final static String TAG_PCG9 = "pcg9";
	private final static String TAG_NAME = "name";
	private final static String TAG_PCGGROUP = "pcgGroup";
	private final static String TAG_GENERICNAME = "genericName";
	private final static String TAG_DRUG = "drug";
	private final static String TAG_ITEMNUMBER = "itemNumber";
	private final static String TAG_STRENGTH = "strength";
	private final static String TAG_DOSAGEFORM = "dosageForm";
	private final static String TAG_MANUFACTURERID = "manufacturerId";
	private final static String TAG_LISTINGDATE = "listingDate";
	private final static String TAG_INDIVIDUALPRICE = "individualPrice";
	private final static String TAG_AMOUNTMOHLTCPAYS = "amountMOHLTCPays";
	
	private final static String ATTR_CREATEDATE = "createDate";
	private final static String ATTR_ID = "id";
	private final static String ATTR_EDITION = "edition";
	private final static String ATTR_UPDATEVER = "updateVer";
	private final static String ATTR_FORMULARYDATE = "formularyDate";
	private final static String ATTR_LCCID = "lccId";
	private final static String ATTR_NOTABENEFIT = "notABenefit";
	private final static String ATTR_SEC3 = "sec3";
	private final static String ATTR_SEC12 = "sec12";

	private String previousTag;
	private boolean hasManufacturer = false;
	private boolean hasPcg2Name = false;
	private boolean hasPcg6Name = false;
	boolean hasGenericName = false;
	boolean hasPcgGroupname = false;
	boolean hasDrugName = false;
	private boolean hasItemNumber =  false;
	private boolean hasStrength = false;
	private boolean hasDosageForm = false;
	private boolean hasManufacturerId = false;
	private boolean hasIndividualPrice = false;
	private boolean hasListingDate = false;
	private boolean hasamountMOHLTCPays = false;
	
	/** The buffer to store the characters found between tags. */
	private StringBuffer contentBuffer;


	private List<Organization> manufacturerList;
	//private List<Formulary> formularyList;
	private List<Pcg2> pcg2List;
	private List<Pcg6> pcg6List;
	private List<GenericName> genericNameList;
	private List<PcgGroup> pcgGroupList;
	private List<Pcg9> pcg9List;
	private List<Drug> drugList;
	
	private Extract extract;
	private Formulary formulary;
	private Organization manufacturer;
	private Pcg2 pcg2;
	private Pcg6 pcg6;
	private GenericName genericName;
	private PcgGroup pcgGroup;
	private Pcg9 pcg9;
	private Drug drug;

	DateFormat format = new SimpleDateFormat("YYYY-MM-DD");

	/**
	 * Primary interface method to parse SQL XML file. Takes the file location
	 * and CacheMap objectd as arguments. Method readAndCacheMetadataXml.
	 * 
	 * @param xmlLocation
	 *            The location of the XML file to be parsed
	 * @param validate
	 *            Indicates if the XML should be validated before parsing
	 * @param CacheMap
	 *            Stores the information from the XML file
	 */
	public DrugXmlParser() {
	}

	/**
	 * @see org.xml.sax.ContentHandler#startDocument()
	 */
	public void startDocument() {
	}

	/**
	 * @see org.xml.sax.ContentHandler#startElement(String, String, String,
	 *      Attributes)
	 */
	@Override
	public void startElement(String uri, String name, String qname,
			Attributes attrs) {
		try {
			if (name.equals(TAG_EXTRACT)) {
				String createDate = attrs.getValue(ATTR_CREATEDATE);

				extract = new Extract();
				extract.setExtractDate(format.parse(createDate));
				formulary = new Formulary();
				manufacturerList = new ArrayList<>();
			} else if (name.equals(TAG_MANUFTURERLIST)) {
				previousTag = TAG_MANUFTURERLIST;
				//We have nothing here but manufacturer list, which already initialized in the first tag
			} else if (name.equals(TAG_FORMULARY)) {
				String edition = attrs.getValue(ATTR_EDITION);;
				String updateVer = attrs.getValue(ATTR_UPDATEVER);;
				String formularyDate = attrs.getValue(ATTR_FORMULARYDATE);;
				String createDate = attrs.getValue(ATTR_CREATEDATE);;
				formulary.setCreateDate(format.parse(createDate));
				formulary.setEdition(edition);
				formulary.setFormularyDate(format.parse(formularyDate));
				formulary.setUpdateVer(updateVer);

				pcg2List = new ArrayList<>();
				previousTag = TAG_FORMULARY;
			} else if (name.equals(TAG_MANUFTURER)) {
				String id = attrs.getValue(ATTR_ID);
				hasManufacturer = true;
				
				manufacturer = new Organization();
				manufacturer.setId(id);
				previousTag = TAG_MANUFTURER;
			} else if (name.equals(TAG_PCG2)) {
				String id = attrs.getValue(ATTR_ID);
				pcg2 = new Pcg2();
				pcg2.setId(id);
				pcg6List = new ArrayList<>();
				previousTag = TAG_PCG2;
			}else if (name.equals(TAG_NAME)) {
				if(previousTag.equals(TAG_PCG2)){
					hasPcg2Name = true;
				}else if(previousTag.equals(TAG_PCGGROUP)){
					hasPcgGroupname = true;
				}else if(previousTag.equals(TAG_GENERICNAME)){
					hasGenericName = true;
				}else if(previousTag.equals(TAG_DRUG)){
					hasDrugName = true;
				}else if(previousTag.equals(TAG_PCG6)){
					hasPcg6Name = true;
				}
			}else if (name.equals(TAG_PCG6)) {
				String id = attrs.getValue(ATTR_ID);
				pcg6 = new Pcg6();
				pcg6.setId(id);
				genericNameList = new ArrayList<>();
				previousTag = TAG_PCG6;
			}else if (name.equals(TAG_GENERICNAME)) {
				String id = attrs.getValue(ATTR_ID);
				genericName = new GenericName();
				genericName.setId(id);
				pcgGroupList = new ArrayList<>();
				previousTag = TAG_GENERICNAME;
			}else if (name.equals(TAG_PCGGROUP)) {
				String lccId = attrs.getValue(ATTR_LCCID);
				pcgGroup = new PcgGroup();
				pcgGroup.setLccId(lccId);
				pcg9List = new ArrayList<>();
				previousTag = TAG_PCGGROUP;
			}else if (name.equals(TAG_PCG9)) {
				String id = attrs.getValue(ATTR_ID);
				pcg9 = new Pcg9();
				pcg9.setId(id);
				drugList = new ArrayList<>();
				previousTag = TAG_PCG9;
			}else if (name.equals(TAG_ITEMNUMBER)) {
				hasItemNumber =  true;
			}else if (name.equals(TAG_STRENGTH)) {
				hasStrength = true;
			}else if (name.equals(TAG_DOSAGEFORM)) {
				hasDosageForm = true;
			}else if (name.equals(TAG_DRUG)) {
				String id = attrs.getValue(ATTR_ID);
				String sec3 = attrs.getValue(ATTR_SEC3);
				String sec12 = attrs.getValue(ATTR_SEC12);
				String notABenefit = attrs.getValue(ATTR_NOTABENEFIT);
				drug = new Drug();
				drug.setId(id);
				drug.setSec3(sec3);
				drug.setSec12(sec12);
				drug.setNotABenefit(notABenefit);
				
				previousTag = TAG_DRUG;
			}else if(name.equals(TAG_MANUFACTURERID)){
				hasManufacturerId = true;
			}else if(name.equals(TAG_INDIVIDUALPRICE)){
				hasIndividualPrice = true;
			}else if(name.equals(TAG_LISTINGDATE)){
				hasListingDate = true;
			}else if(name.equals(TAG_AMOUNTMOHLTCPAYS)){
				hasamountMOHLTCPays = true;
			}

		} catch (Exception ex) {
			log.error("Error in parsing TTC route XML: ", ex);
		}
	}

	/**
	 * @see org.xml.sax.ContentHandler#endElement(String, String, String)
	 * @param name
	 */
	@Override
	public void endElement(String uri, String name, String qname) {
		if (name.equals(TAG_EXTRACT)) {
			extract.setFormulary(formulary);
			extract.setManufacturerList(manufacturerList);
		} else if (name.equals(TAG_FORMULARY)) {
			formulary.setPcg2List(pcg2List);
		} else if (name.equals(TAG_MANUFTURER)) {
			manufacturerList.add(manufacturer);
		} else if (name.equals(TAG_PCG2)) {
			pcg2.setPcg6List(pcg6List);
			pcg2List.add(pcg2);
		}else if (name.equals(TAG_NAME)) {
			//Do nothing here
		}else if (name.equals(TAG_PCG6)) {
			pcg6.setGenericNameList(genericNameList);
			pcg6List.add(pcg6);
		}else if (name.equals(TAG_GENERICNAME)) {
			genericName.setPcgGroupList(pcgGroupList);
			genericNameList.add(genericName);
		}else if (name.equals(TAG_PCGGROUP)) {
			pcgGroup.setPcg9List(pcg9List);
			pcgGroupList.add(pcgGroup);
		}else if (name.equals(TAG_PCG9)) {
			pcg9List.add(pcg9);
			pcg9.setDrugList(drugList);
		}else if (name.equals(TAG_ITEMNUMBER)) {
			//Do nothing here
		}else if (name.equals(TAG_STRENGTH)) {
			//Do nothing here
		}else if (name.equals(TAG_DOSAGEFORM)) {
			//Do nothing here
		}else if (name.equals(TAG_DRUG)) {
			drugList.add(drug);
		}
		
	}

	/**
	 * @see org.xml.sax.ContentHandler#characters(char[], int, int)
	 */
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (contentBuffer != null) {
			contentBuffer.append(ch, start, length);
		}
		if(hasManufacturer){
			manufacturer.setName(new String(ch, start, length));
			System.out.println("111111111111 manufactuer name: " + manufacturer.getName());
			hasManufacturer = false;
		}
		if(hasPcg2Name){
			pcg2.setName(new String(ch, start, length));
			System.out.println("2222222222222222 pcg2 name: " + pcg2.getName());
			hasPcg2Name = false;
		}
		if(hasGenericName){
			genericName.setName(new String(ch, start, length));
			System.out.println("3333333333333333 genric name: " + genericName.getName());
			hasGenericName = false;
		}
		if (hasPcgGroupname) {
			pcgGroup.setName(new String(ch, start, length));
			hasPcgGroupname = false;
			System.out.println("444444444444444 PCG group name: " + pcgGroup.getName());
		}
		if(hasItemNumber){
			pcg9.setItemNumber(new String(ch, start, length));
			hasItemNumber =  false;
		}
		if(hasStrength){
			pcg9.setStrength(new String(ch, start, length));
			hasStrength = false;
		}
		if(hasDosageForm){
			pcg9.setDosageForm(new String(ch, start, length));
			hasDosageForm = false;
		}
		if(hasDrugName){
			drug.setDinDesc(new String(ch, start, length));
			hasDrugName = false;
			System.out.println("55555555555555555 drug name: " + drug.getGenName());
		}
		if(hasPcg6Name){
			pcg6.setName(new String(ch, start, length));
			hasPcg6Name = false;
			System.out.println("6666666666666666 PCG6 name: " + pcg6.getName());
		}
		if(hasManufacturerId){
			drug.setManufacturerCd(new String(ch, start, length));
			hasManufacturerId = false;
		}
		if(hasIndividualPrice){
			String price = new String(ch, start, length);
			drug.setIndividualPrice(new Double(price));
			if(drug.getIndividualPrice() == -2.147483648E9){
				drug.setIndividualPrice(0);
			}
			hasIndividualPrice = false;
		}
		if(hasListingDate){
			try{
				drug.setListingDate(format.parse(new String(ch, start, length)));
			}catch (Exception e){
				e.printStackTrace();
			}
			hasListingDate = false;
		}
		if(hasamountMOHLTCPays){
			drug.setAmountMOHLTCPays(new Double(new String(ch, start, length)));
			hasamountMOHLTCPays = false;
		}
	}

	public Extract getExtract() {
		return extract;
	}

	/**
	 * @see org.xml.sax.ContentHandler#ignorableWhitespace(char[], int, int)
	 */
	public void ignorableWhitespace(char[] ch, int start, int length) {
	}

	public static void main(String[] a) {
		//String filePath = "http://webservices.nextbus.com/service/publicXMLFeed?command=predictions&a=ttc&r=39&s=6717";
		
		String filePath = "C:\\Users\\huda\\Documents\\Project\\Drug Rebates Management and Tracking Project\\Data\\data_extract.xml";
		
		PropertyConfig.setPropertyPath("C:\\Project\\new_workspace\\IdmsEngine\\conf\\system.properties");
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		DrugXmlParser parser = new DrugXmlParser();
		DrugXmlParser.readAndCacheMetadataXml(parser, filePath, true);
		Extract all = parser.getExtract();
		if (all != null) {
			List<Organization> orglist = all.getManufacturerList();
			try{
				conn = DBConnectionManager.getManager().getConnection();
				
				//String sql = "insert into ORGANIZATION(ORGANIZATION_ID, ORGANIZATION_NAME, ORGANIZATION_TYPE,  ORGANIZATION_CODE, CREATED_DATE) values(SEQ_ORGANIZATION.NEXTVAL,?,1,?,sysdate)";
				String sql = "insert into DRUG(DRUG_ID, DIN_PIN, DIN_DESC, GEN_NAME, AGENCY_ID, INDIVIDUAL_PRICE, STRENGTH, DOSAGE_FORM, MANUFACTURER_CD, REC_EFF_DT) " +
						"values(SEQ_DRUG.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				ps = conn.prepareStatement(sql);
				/*
				for(Organization org: orglist){
					ps.setString(1, org.getName());
					ps.setString(2, org.getId());
					ps.addBatch();
					System.out.println(org.getId() + ", " + org.getName());
				}
				*/
				Formulary  formulary = all.getFormulary();
				System.out.println("Formulary: " + formulary.getEdition() + ", " + formulary.getUpdateVer() + ", " + formulary.getCreateDate() + ", " + formulary.getFormularyDate());
				List<Pcg2> pcg2List = formulary.getPcg2List();
				for(Pcg2 pg2: pcg2List){
					System.out.println("	pcg2: " + pg2.getId() + ", " + pg2.getName());
					List<Pcg6> pcg6List = pg2.getPcg6List();
					for (Pcg6 pg6:pcg6List){
						System.out.println("		pcg6: " + pg6.getId() + ", " + pg6.getName());
						List<GenericName> genericList = pg6.getGenericNameList();
						for(GenericName gn: genericList){
							System.out.println("			generic name: " + gn.getId() + ", " + gn.getName());
							List<PcgGroup> grpList = gn.getPcgGroupList();
							for(PcgGroup grp: grpList){
								System.out.println("				PCG Group: " + grp.getLccId() + ", " + grp.getName());
								List<Pcg9> pcg9List = grp.getPcg9List();
								for(Pcg9 pcg9: pcg9List){
									System.out.println("					PCG9: " + pcg9.getId() + ", " + pcg9.getItemNumber() + "," + pcg9.getStrength() + "," + pcg9.getDosageForm());
									List<Drug> drugList = pcg9.getDrugList();
									for(Drug dg: drugList){
										//1.DIN_PIN, 2.DIN_DESC, 3.GEN_NAME, 4.AGENCY_ID, 5.INDIVIDUAL_PRICE, 6.STRENGTH, 
										//7.DOSAGE_FORM, 8.MANUFACTURER_CD, 9.REC_EFF_DT
										ps.setString(1, dg.getId());
										ps.setString(2, dg.getDinDesc());
										ps.setString(3, gn.getName());
										ps.setString(4, "14157");
										if(dg.getIndividualPrice() > 0){
											ps.setDouble(5,dg.getIndividualPrice());
										}else{
											ps.setNull(5,java.sql.Types.DOUBLE);
										}
										ps.setString(6,pcg9.getStrength());
										ps.setString(7,pcg9.getDosageForm());
										ps.setString(8,dg.getManufacturerCd());
										if(dg.getListingDate() != null){
											ps.setDate(9,new Date(dg.getListingDate().getTime()));
										}else{
											ps.setDate(9,null);
										}
										ps.addBatch();
										
										
										System.out.println("Drug: " + dg.getId() + ", " + dg.getGenName()  + ", " + dg.getSec3() + "," + dg.getSec12() + "," + dg.getManufacturerId() + ", " + dg.getIndividualPrice() + ", " + dg.getListingDate() + ", " + dg.getAmountMOHLTCPays());
									}
								}
							}
						}
					}
				}
				
				try{
					ps.executeBatch();
				}catch(Exception ex){
					ex.printStackTrace();
				}

				
			}catch(Exception e){
				e.printStackTrace();
			}finally {
				DBConnectionManager.getManager().closeConnection(conn, ps, null);
			}
		}
		
	}
}
