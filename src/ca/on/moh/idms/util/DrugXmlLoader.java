package ca.on.moh.idms.util;

import gov.moh.app.db.DBConnectionManager;
import gov.moh.config.PropertyConfig;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

import ca.on.moh.idms.vo.Drug;
import ca.on.moh.idms.vo.Extract;
import ca.on.moh.idms.vo.Formulary;
import ca.on.moh.idms.vo.GenericName;
import ca.on.moh.idms.vo.Organization;
import ca.on.moh.idms.vo.Pcg2;
import ca.on.moh.idms.vo.Pcg6;
import ca.on.moh.idms.vo.Pcg9;
import ca.on.moh.idms.vo.PcgGroup;
import ca.on.moh.idms.xml.DrugXmlParser;

public class DrugXmlLoader {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
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
