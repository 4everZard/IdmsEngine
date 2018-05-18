package ca.on.moh.idms.util;

import gov.moh.app.db.UserTransactionManager;
import gov.moh.config.vo.ValueObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import javax.transaction.UserTransaction;

import org.apache.poi.hssf.record.crypto.Biff8EncryptionKey;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import ca.on.moh.idms.service.ClmhistService;
import ca.on.moh.idms.vo.Clmhist;

public class DataLoader {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		loadClaimHistory();

	}

	private static void loadClaimHistory() throws Exception{
		String vcFile = "C:\\Users\\huda\\Documents\\Project\\Drug Rebates Management and Tracking Project\\data.xls";
		File file = new File(vcFile);
		byte[] fileByte = new byte [(int)file.length()];
		InputStream in = new FileInputStream(file);
		BufferedInputStream bin = new BufferedInputStream(in);
		//bin.read(fileByte);
		
 		try {

 			POIFSFileSystem poif = new POIFSFileSystem(bin);
 			System.out.println("1. PoiFile Created");
 	        //Biff8EncryptionKey.setCurrentUserPassword("idmsmdplar");
			HSSFWorkbook wb = new HSSFWorkbook(poif);
			System.out.println("2. WorkBook Created");
			HSSFSheet sheet = wb.getSheetAt(0);
			System.out.println("3. Sheet Created");
            Iterator <Row> rows = sheet.rowIterator();
            int i=0;
            while( rows.hasNext() ) { 
            	HSSFRow row = (HSSFRow) rows.next();
            	if(i==0){
            		i++;
            		continue;//escape the first line of titles
            	}
            	Iterator <Cell> cells = row.cellIterator();
            	
            	String adjudication_dt = null;
            	String agency_id = null;
            	String ltc_flag	 = null;
            	String dt_of_serv = null;
            	String din_pin = null;
            	String qty = null;
            	String tot_amt_pd = null;
            	String thera_class = null;
            	String odb_elig_no	= null;
            	String moh_prescriber_id = null;
            	String med_cond	 = null;
            	String dys_supply = null;	
            	String drg_cst_alld	 = null;
            	String prod_sel	 = null;
            	String cst_upchrg_alld = null;
            	String deduct_to_collect = null;
            	String comp_fee_alld = null;
            	String plan_cd = null;
            	String county_cd = null;	
            	String ag_type_cd = null;
            	String postal_cd = null;
            	String prof_fee_alld = null;
            	
        		DateFormat format = new SimpleDateFormat();
        		Clmhist clmhist = new Clmhist();
        		
             	while( cells.hasNext() ) {
            		HSSFCell cell = (HSSFCell) cells.next();
            		cell.setCellType(Cell.CELL_TYPE_STRING); 
 
             		//clmhist.setCurrRxNo(curr_)
            		//clmhist.setCurrStat(cur)
            		clmhist.setProgId("1");	//need an ID
            		
            		if(cell.getColumnIndex() == 0){
            			adjudication_dt = cell.getRichStringCellValue().getString();
                		//clmhist.setAdjudicationDt(new Timestamp(format.parse(adjudication_dt).getTime()));
            		}
            		if(cell.getColumnIndex() == 1){
            			agency_id = cell.getRichStringCellValue().getString();
                		clmhist.setAgencyId(Integer.parseInt(agency_id));
            		}
            		if(cell.getColumnIndex() == 2){
            			ltc_flag = cell.getRichStringCellValue().getString();
                		clmhist.setLtcFlag(Integer.parseInt(ltc_flag));
            		}
            		if(cell.getColumnIndex() == 3){
            			dt_of_serv = cell.getRichStringCellValue().getString();
                		//clmhist.setDtOfServ(new Timestamp(format.parse(dt_of_serv).getTime()));
            		}
            		if(cell.getColumnIndex() == 4 ){
            			din_pin = cell.getRichStringCellValue().getString();
                		clmhist.setDinPin(din_pin);
             		}
            		if(cell.getColumnIndex() == 5){
            			qty = cell.getRichStringCellValue().getString();
                		clmhist.setQty(Integer.parseInt(qty));
            		}
            		if(cell.getColumnIndex() == 6){
            			tot_amt_pd = cell.getRichStringCellValue().getString().trim();
                		clmhist.setTotAmtPd(Double.parseDouble(tot_amt_pd));
            		}
            		if(cell.getColumnIndex() == 7){
            			thera_class = cell.getRichStringCellValue().getString().trim();
                		clmhist.setTheraClass(thera_class);
            		}
            		if(cell.getColumnIndex() == 8){
            			odb_elig_no = cell.getRichStringCellValue().getString().trim();
                		clmhist.setOdbEligNo(odb_elig_no);
            		}
            		if(cell.getColumnIndex() == 9){
            			moh_prescriber_id = cell.getRichStringCellValue().getString().trim();
                		clmhist.setMohPrescriberId(moh_prescriber_id);
            		}
            		if(cell.getColumnIndex() == 10){
            			med_cond = cell.getRichStringCellValue().getString().trim();
            			if(med_cond != null && !med_cond.equals("") && !med_cond.equals("NULL")){
            				clmhist.setMedCond(Integer.parseInt(med_cond));
            			}
            		}
            		if(cell.getColumnIndex() == 11){
            			dys_supply = cell.getRichStringCellValue().getString().trim();
                		clmhist.setDysSupply(Integer.parseInt(dys_supply));
            		}
            		if(cell.getColumnIndex() == 12){
            			drg_cst_alld = cell.getRichStringCellValue().getString().trim();
                		clmhist.setDrgCstAlld(Double.parseDouble(drg_cst_alld));
            		}
            		if(cell.getColumnIndex() == 13){
            			prod_sel = cell.getRichStringCellValue().getString().trim();
                		clmhist.setProdSel(Double.parseDouble(prod_sel));
            		}
            		if(cell.getColumnIndex() == 14){
            			cst_upchrg_alld = cell.getRichStringCellValue().getString().trim();
                		clmhist.setCstUpchrgAlld(Double.parseDouble(cst_upchrg_alld));
            		}
            		if(cell.getColumnIndex() == 15){
            			deduct_to_collect = cell.getRichStringCellValue().getString().trim();
                		clmhist.setDeductToCollect(Double.parseDouble(deduct_to_collect));
            		}
            		if(cell.getColumnIndex() == 16){
            			comp_fee_alld = cell.getRichStringCellValue().getString().trim();
                		clmhist.setCompFeeAlld(Integer.parseInt(comp_fee_alld));
            		}
            		if(cell.getColumnIndex() == 17){
            			plan_cd = cell.getRichStringCellValue().getString().trim();
                		clmhist.setPlanCd(plan_cd);
            		}
            		if(cell.getColumnIndex() == 18){
            			county_cd = cell.getRichStringCellValue().getString().trim();
                		clmhist.setCountyCd(county_cd);
            		}
            		if(cell.getColumnIndex() == 19){
            			ag_type_cd = cell.getRichStringCellValue().getString().trim();
                		clmhist.setAgTypeCd(ag_type_cd);
            		}
            		if(cell.getColumnIndex() == 20){
            			postal_cd = cell.getRichStringCellValue().getString().trim();
                		clmhist.setPostalCd(postal_cd);
            		}
            		if(cell.getColumnIndex() == 21){
            			prof_fee_alld = cell.getRichStringCellValue().getString().trim();
                		clmhist.setProfFeeAlld(Integer.parseInt(prod_sel));
            		}
            		


            		/*
            		UserTransactionManager txMgr = UserTransactionManager.getInstance();
            		UserTransaction tx = null;
            		
            		try {
            			tx = txMgr.createTransaction();
            			if (tx != null) {
            				tx.begin();
            			}
            		
            			ClmhistService service = new ClmhistService();
            			service.addClmhist(clmhist);
            		
            			if (tx != null) {
            				tx.commit();
            				tx = null;
            			}
            		} catch (Exception e) {
            			e.printStackTrace();
            			try {
            				if (tx != null) {
            					tx.rollback();
            					tx = null;
            				}
            			} catch (Exception ex) {
            				tx = null;
            			}
            		}
					*/
               }	
    			System.out.println(agency_id + ", " + comp_fee_alld + "," + thera_class);

            }
        
            
 		}catch(Exception e){
 			e.printStackTrace();
 		}

	}

}
