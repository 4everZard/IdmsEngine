package ca.on.moh.idms.engine;

import gov.moh.app.db.DBConnectionManager;
import gov.moh.config.ConfigFromDB;
import gov.moh.config.PropertyConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;

import ca.on.moh.idms.util.RebateCalculatorCache;
import ca.on.moh.idms.util.RebateConstant;
import ca.on.moh.idms.vo.RebateVO;

public class RebateEngineSpark {

	private SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-DD",new Locale("ca", "CA"));
	private static Map<String, List<RebateVO>> dinDetails;
	private List<String> twoPriceDinList;
	private List<String> threePriceDinList;
	
	static String appRoot = System.getProperty("user.dir");
	
	public static void main (String [] args) throws Exception {
		System.out.println("Calculation engine started ...... ");
		
		PropertyConfig.setPropertyPath(appRoot + "\\conf\\system.properties");
		RebateEngineSpark calculator = new RebateEngineSpark();
		String manufacturerCode = "LEO";
		Connection conn1 = null;
		Connection conn2 = null;

		try{
			long startTime = System.currentTimeMillis();
			String username = PropertyConfig.getProperty("app.config.db.username2");
			String pwd = PropertyConfig.getProperty("app.config.db.password2");
			String url = PropertyConfig.getProperty("app.config.db.dbUrl2");
			conn1 = DBConnectionManager.getManager().getConnection();
			conn2 = DBConnectionManager.getManager().getConnection(username,pwd,url);
			
			String filepathAndName = "C:\\TEMP\\IDMS\\data";
			calculator.calculateRebate(manufacturerCode, conn1,conn2,filepathAndName);
			
			long endTime = System.currentTimeMillis();
			long timeSpent = (endTime - startTime)/1000;
			System.out.println("Total Time: " + timeSpent);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBConnectionManager.getManager().closeConnection(conn1, null, null);
			DBConnectionManager.getManager().closeConnection(conn2, null, null);
		}
		System.out.println("Calculation Completed. ");
	}
	
	public void calculateRebate(String manufacturerCode, Connection conn1, Connection conn2, String path) throws Exception{
		PropertyConfig.setPropertyPath(appRoot + "\\conf\\system.properties");
		
		step1CreateTemp01(manufacturerCode, conn2);
		
		step2CreateTemp03(manufacturerCode, conn2);
		step3CreateTemp02(manufacturerCode, conn2);
		step4CreateTemp99(manufacturerCode, conn2);
		step5Join2To4TogetherTemp02(manufacturerCode, conn1);
		step6And7DefineMissingDBTemp02(manufacturerCode, conn1);
		step8CreateTemp04(manufacturerCode, conn1);
		step9CreateTemp05(manufacturerCode, conn1);
		step10CreateTemp06(manufacturerCode, conn1);
		step11To13CreateTemp07(manufacturerCode, conn1);
		step14CreateTemp08(manufacturerCode, conn1);
		step15CalculateVolumeDiscountTemp08(manufacturerCode, conn1);
		step16And17CreateTemp09(manufacturerCode, conn1);
		step18And19CreateSummaryFileTemp11(manufacturerCode, conn1);
		step20And21CreateSummaryFileFromTemp11(manufacturerCode, conn1,path);
		
	}
	
	public List<RebateVO> step1CreateTemp01(String manufacturerCode, Connection conn) throws Exception{// here din is "02418401" for the example
		
		
		List<RebateVO> temp01 = new ArrayList<RebateVO>();
		
		String historyStartDate = ConfigFromDB.getConfigPropertyFromDB(RebateConstant.HISTORY_START_DATE);	//JUN 29 2015
		String historyEndDate = ConfigFromDB.getConfigPropertyFromDB(RebateConstant.HISTORY_END_DATE);	//JUN 30 2015
		
		String sql = "select DIN_PIN,DT_OF_SERV, ADJUDICATION"
				+ "_DT, PROF_FEE_ALLD,QTY,DRG_CST_ALLD,PROG_ID,PROD_SEL, INTERVENTION_1, " +
				"INTERVENTION_2, INTERVENTION_3, INTERVENTION_4, INTERVENTION_5, INTERVENTION_6, INTERVENTION_7, INTERVENTION_8, INTERVENTION_9, INTERVENTION_10 " +
				"from CLMHIST " +
				"where CURR_STAT = 'P' and Trunc(DT_OF_SERV,'YEAR') >= to_date('" + historyStartDate + "','MM-DD-YYYY') AND " +
						"Trunc(DT_OF_SERV,'YEAR') <= to_date('" + historyEndDate + "','MM-DD-YYYY') AND " +
						"PROG_ID <> 'TP' and DIN_PIN in (select DIN_PIN from SCHEDULE_A where MANUFACTURER_CD = '" + manufacturerCode + "')";
		

		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		
		
		try{
			String sql1 = "TRUNCATE TABLE TEMP01";
			ps =  conn.prepareStatement(sql1);
			ps.executeUpdate();
			ps = null;
			
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			
			Logger.getLogger("org").setLevel(Level.ERROR);
			SparkConf conf = new SparkConf().setAppName("StackOverFlowSurvey").setMaster("local[1]");
			JavaSparkContext sc = new JavaSparkContext(conf);
			List<String> rebateList = Arrays.asList("DIN_PIN","DT_OF_SERV","ADJUDICATION_DT");
			JavaRDD<String> stringRDD = sc.parallelize(rebateList);
			/*try to print out rebateRDD on console and check its schema, convert JavaRDD<String> to JavaRDD<RebateVO>
			 * by using filter,map
			
			 */
		/*	for(String schema: stringRDD.collect()){
				System.out.println(schema);
			}*/
			JavaRDD<RebateVO> rebateVORDD = stringRDD
										    .map( line -> {
									        String[] splits = line.split(" ");
									        return new RebateVO();
			});
			
		
			SparkSession session = SparkSession.builder()
											   .appName("RebateEngine")
											   .master("local[1]")
											   .getOrCreate();
			
			Encoder<RebateVO> rebateEncoder = Encoders.bean(RebateVO.class);
			Dataset<RebateVO> javaBeanDS = session.createDataset(
					rebateVORDD.rdd(),
					rebateEncoder
					);
			
			javaBeanDS.printSchema(); 
			javaBeanDS.show();
			
			
			if(rs != null){
				
				int key = 1;
				while(rs.next()){
					RebateVO row = new RebateVO();
					String din = rs.getString("DIN_PIN");
					java.sql.Date dateOfServ = rs.getDate("DT_OF_SERV");
					java.sql.Date adjudicationDate = rs.getDate("ADJUDICATION_DT");
					double  drugCostAllowed = rs.getDouble("DRG_CST_ALLD");
					//int cstUpchargeAlld = rs.getInt("CST_UPCHRG_ALLD");
					int profFeeAlld = rs.getInt("PROF_FEE_ALLD");
					//int compFeeAlld = rs.getInt("COMP_FEE_ALLD");
					//int deductToCollect = rs.getInt("DEDUCT_TO_COLLECT");
					//int totalAmountPaid = rs.getInt("TOT_AMT_PD");
					//String currentRxNo = rs.getString("CURR_RX_NO");
					//String manufactuerCode = rs.getString("MANUFACTURER_CD");
					int quantity = rs.getInt("QTY");
					
					double prodSel = rs.getDouble("PROD_SEL");
					String progId =  rs.getString("PROG_ID");
					String intervention1 = rs.getString("INTERVENTION_1");
					String intervention2 = rs.getString("INTERVENTION_2");
					String intervention3 = rs.getString("INTERVENTION_3");
					String intervention4 = rs.getString("INTERVENTION_4");
					String intervention5 = rs.getString("INTERVENTION_5");
					String intervention6 = rs.getString("INTERVENTION_6");
					String intervention7 = rs.getString("INTERVENTION_7");
					String intervention8 = rs.getString("INTERVENTION_8");
					String intervention9 = rs.getString("INTERVENTION_9");
					String intervention10 = rs.getString("INTERVENTION_10");	
					
					row.setManufacturerCd(manufacturerCode);
					row.setManufacturerCd(manufacturerCode);
					row.setDinPin(din);
					row.setDtOfServ(dateOfServ);
					row.setAdjudicationDt(adjudicationDate);
					row.setDrgCstAlld(drugCostAllowed);
					//row.setCstUpchrgAlld(cstUpchargeAlld);
					//row.setProfFeeAlld(profFeeAlld);
					//row.setCompFeeAlld(compFeeAlld);
					//row.setDeductToCollect(deductToCollect);
					//row.setTotAmtPd(totalAmountPaid);
					//row.setCurrRxNo(currentRxNo);
					row.setQty(quantity);
					//row.setProdSel(prodSel);
					row.setProgId(progId);
					row.setIntervention1(intervention1);
					row.setIntervention2(intervention2);
					row.setIntervention3(intervention3);
					row.setIntervention4(intervention4);
					row.setIntervention5(intervention5);
					row.setIntervention6(intervention6);
		
				/*	String sql2 = "INSERT INTO TEMP01 (CLAIM_ID,DIN_PIN,PROF_FEE_ALLD,ADJUDICATION_DT,DT_OF_SERV,QTY,DRG_CST_ALLD,PROD_SEL,INTERVENTION_1," +
							"INTERVENTION_2,INTERVENTION_3,INTERVENTION_4,INTERVENTION_5,INTERVENTION_6,INTERVENTION_7,INTERVENTION_8,INTERVENTION_9,INTERVENTION_10) "+
							"VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
					ps1 = conn.prepareStatement(sql2);
					ps1.setInt(1, key++);
					ps1.setString(2, din);
					ps1.setDouble(3, profFeeAlld);
					ps1.setDate(4, adjudicationDate);
					ps1.setDate(5, dateOfServ);
					ps1.setDouble(6, quantity);
					ps1.setDouble(7, drugCostAllowed);
					ps1.setDouble(8, prodSel);				
					ps1.setString(9,intervention1);
					ps1.setString(10,intervention2);
					ps1.setString(11,intervention3);
					ps1.setString(12,intervention4);
					ps1.setString(13,intervention5);
					ps1.setString(14,intervention6);
					ps1.setString(15,intervention7);
					ps1.setString(16,intervention8);
					ps1.setString(17,intervention9);
					ps1.setString(18,intervention10);
					
					ps1.executeUpdate();
					
					temp01.add(row); */
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			DBConnectionManager.getManager().closeConnection(null, ps, rs);
		}
		return temp01;
	}

	/**
	 * PLEASE NOTE:  step 2, step3, and step 4  select from same table with same conditions, only different parameter of DATE is passed.
	 * @param din
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public List<RebateVO> step2CreateTemp03(String manufacturerCode,  Connection conn) throws Exception{// here din is "02418401" for the example
		
		List<RebateVO> temp03 = new ArrayList<RebateVO>();
		String firstPriceDate = ConfigFromDB.getConfigPropertyFromDB(RebateConstant.FIRST_PRICE_DATE);	//OCT-23-2006
		Map<String, RebateVO> dinMap = null;
		
		String sql = "select d.INDIVIDUAL_PRICE AS FIRST_PRICE, d.DIN_DESC, r.REC_EFF_DATE,d.REC_CREATE_TIMESTAMP,d.DIN_PIN, d.MANUFACTURER_CD from (" + 
			     " select DIN_PIN,MAX(REC_EFF_DT) as REC_EFF_DATE from DRUG where MANUFACTURER_CD = '" + manufacturerCode+ "' AND " +
			     " Trunc(REC_EFF_DT) < to_date('" + firstPriceDate + "','MM-DD-YYYY') AND REC_INACTIVE_TIMESTAMP is NULL group by DIN_PIN) r " +
			" inner join DRUG d on d.DIN_PIN = r.DIN_PIN and d.REC_EFF_DT = r.REC_EFF_DATE";
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;

		try{
			String sql1 = "TRUNCATE TABLE TEMP03";
			ps =  conn.prepareStatement(sql1);
			ps.executeUpdate();
			ps = null;
			
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs != null){
				dinMap = new HashMap<String, RebateVO>();
				while(rs.next()){
					RebateVO row = new RebateVO();
					double firstPrice = rs.getDouble("FIRST_PRICE");
					String din = rs.getString("DIN_PIN");
					String brandName = rs.getString("DIN_DESC");
					Date effectiveDate = rs.getDate("REC_EFF_DATE");
					String manufacturerCd =  rs.getString("MANUFACTURER_CD");
					row.setDinDesc(brandName);
					row.setManufacturerCd(manufacturerCode);
					row.setDinPin(din);
					row.setFirstPrice(firstPrice);
					row.setRecEffDt(effectiveDate);
					row.setRecCreateTimestamp(rs.getDate("REC_CREATE_TIMESTAMP"));
					
					if(dinMap.containsKey(din)){
						//String eDate = format.format(new Date(effectiveDate.getTime()));
						String eDate = effectiveDate.toString();
						sql = "select d.INDIVIDUAL_PRICE AS FIRST_PRICE, d.DIN_DESC, d.REC_EFF_DT,d.REC_CREATE_TIMESTAMP, r.REC_CREATE_TIME,d.DIN_PIN from ( " +
							     "select DIN_PIN,REC_EFF_DT,MAX(REC_CREATE_TIMESTAMP) as REC_CREATE_TIME from DRUG where MANUFACTURER_CD ='" + manufacturerCode + "' AND " +
							     "Trunc(REC_EFF_DT) = to_Date('" + eDate + "','YYYY-MM-DD') AND DIN_PIN='" + din + "' AND REC_INACTIVE_TIMESTAMP is NULL group by DIN_PIN,REC_EFF_DT) r " +
							"inner join DRUG d on d.DIN_PIN = r.DIN_PIN and  d.REC_CREATE_TIMESTAMP = r.REC_CREATE_TIME";
						
						/*
						sql = "select d.INDIVIDUAL_PRICE AS FIRST_PRICE, d.DIN_DESC, d.REC_EFF_DT,d.REC_CREATE_TIMESTAMP, r.REC_CREATE_TIME,d.DIN_PIN from ( " +
							     "select DIN_PIN,REC_EFF_DT,MAX(REC_CREATE_TIMESTAMP) as REC_CREATE_TIME from DRUG where MANUFACTURER_CD ='" + manufacturerCode + "' AND " +
							     "Trunc(REC_EFF_DT) = ? AND DIN_PIN='" + din + "' AND REC_INACTIVE_TIMESTAMP is NULL group by DIN_PIN,REC_EFF_DT) r " +
							"inner join DRUG d on d.DIN_PIN = r.DIN_PIN and  d.REC_CREATE_TIMESTAMP = r.REC_CREATE_TIME";
							*/
						ps = conn.prepareStatement(sql);
						rs1 = ps.executeQuery();
						if(rs1 != null){
							while(rs1.next()){
								row = new RebateVO();
								firstPrice = rs1.getDouble("FIRST_PRICE");
								din = rs1.getString("DIN_PIN");
								brandName = rs1.getString("DIN_DESC");
								effectiveDate = rs1.getDate("REC_EFF_DT");
								
								row.setDinDesc(brandName);
								row.setManufacturerCd(manufacturerCode);
								row.setDinPin(din);
								row.setFirstPrice(firstPrice);
								row.setRecEffDt(effectiveDate);
								row.setRecCreateTimestamp(rs1.getDate("REC_CREATE_TIMESTAMP"));
								row.setManufacturerCd(manufacturerCd);
								dinMap.put(din, row);
							}
						}
					}else{
						dinMap.put(din, row);
					}
				}
				
				Set<String> allKeys = dinMap.keySet();
				Iterator<String> keys = allKeys.iterator();
				while(keys.hasNext()){
					String din = keys.next();
					RebateVO vo = (RebateVO)dinMap.get(din);
					//sql = "select * from TEMP03 where DIN_PIN = '" + vo.getDinPin() + "' and DIN_DESC = '" + vo.getDinDesc() +
					//		"' and REC_EFF_DT = to_Date('" + vo.getRecEffDt() + "','YYYY-MM-DD')";
					/*
					sql = "select * from TEMP03 where DIN_PIN = '" + vo.getDinPin() + "' and DIN_DESC = '" + vo.getDinDesc() +
							"' and REC_EFF_DT = ?";

					ps = conn.prepareStatement(sql);
					ps.setDate(1,vo.getRecEffDt());
					rs = ps.executeQuery();
					if(rs != null && rs.next()){
						sql = "update TEMP03 set FIRST_PRICE = ? ,REC_CREATE_TIMESTAMP = ? where DIN_PIN = '" + vo.getDinPin() + "' and DIN_DESC = '" + vo.getDinDesc() +
							"' and REC_EFF_DT = to_Date('" +vo.getRecEffDt() + "','YYYY-MM-DD')";
						ps1 = conn.prepareStatement(sql);
						ps1.setDouble(1, vo.getFirstPrice());
						ps1.setDate(2,vo.getRecCreateTimestamp());
					}else{*/
						String sql2 = "insert into TEMP03 (DIN_PIN,DIN_DESC,FIRST_PRICE,REC_EFF_DT,REC_CREATE_TIMESTAMP,MANUFACTURER_CD) values(?,?,?,?,?,?)";
						ps1 = conn.prepareStatement(sql2);
						ps1.setString(1, vo.getDinPin());
						ps1.setString(2, vo.getDinDesc());
						ps1.setDouble(3, vo.getFirstPrice());
						ps1.setDate(4, new java.sql.Date(vo.getRecEffDt().getTime()));
						ps1.setDate(5, new java.sql.Date(vo.getRecCreateTimestamp().getTime()));
						ps1.setString(6, vo.getManufacturerCd());
					//}
					int updateRow = ps1.executeUpdate();
					//ps1.addBatch();
					temp03.add(vo);
				}
				//ps1.executeBatch();
				
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			DBConnectionManager.getManager().closeConnection(null, ps, rs);
			DBConnectionManager.getManager().closeConnection(null, ps1, rs1);
		}
		return temp03;
	}

	/**
	 * PLEASE NOTE: step 2, step3, and step 4 select from same table with same conditions, only different parameter of DATE is passed.
	 * @param din
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public List<RebateVO> step3CreateTemp02(String manufacturerCode,  Connection conn) throws Exception{// here din is "02418401" for the example
		
		List<RebateVO> temp02 = new ArrayList<RebateVO>();
		String secondPriceDate = ConfigFromDB.getConfigPropertyFromDB(RebateConstant.SECOND_PRICE_DATE);	//OCT-23-2015

		Map<String, RebateVO> dinMap = null;
		String sql = "select d.INDIVIDUAL_PRICE AS SECOND_PRICE, d.DIN_DESC, r.REC_EFF_DATE,d.REC_CREATE_TIMESTAMP,d.DIN_PIN from (" + 
			     "select DIN_PIN,MAX(REC_EFF_DT) as REC_EFF_DATE from DRUG where MANUFACTURER_CD = '" + manufacturerCode+ "' AND " +
			     "REC_EFF_DT < to_date('" + secondPriceDate + "','MM-DD-YYYY')  AND REC_INACTIVE_TIMESTAMP is NULL group by DIN_PIN) r " +
			"inner join DRUG d on d.DIN_PIN = r.DIN_PIN and d.REC_EFF_DT = r.REC_EFF_DATE";

		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;

		try{
			String sql1 = "TRUNCATE TABLE TEMP02";
			ps =  conn.prepareStatement(sql1);
			ps.executeUpdate();
			ps = null;
			
			ps = conn.prepareStatement(sql);
			//ps.setDate(1, new java.sql.Date(format.parse(secondPriceDate).getTime()));
			rs = ps.executeQuery();
			if(rs != null){
				dinMap = new HashMap<String, RebateVO>();
				while(rs.next()){
					RebateVO row = new RebateVO();
					double secondPrice = rs.getDouble("SECOND_PRICE");
					String din = rs.getString("DIN_PIN");
					String brandName = rs.getString("DIN_DESC");
					Date effectiveDate = rs.getDate("REC_EFF_DATE");
					
					row.setDinDesc(brandName);
					row.setManufacturerCd(manufacturerCode);
					row.setDinPin(din);
					row.setSecondPrice(secondPrice);
					row.setRecEffDt(effectiveDate);
					row.setRecCreateTimestamp(rs.getDate("REC_CREATE_TIMESTAMP"));
					
					if(dinMap.containsKey(din)){
						String eDate = format.format(effectiveDate);//to_Date('" + eDate + "','YYYY-MM-DD')
						String sql2 = "select d.INDIVIDUAL_PRICE AS SECOND_PRICE, d.DIN_DESC, d.REC_EFF_DT,d.REC_CREATE_TIMESTAMP, r.REC_CREATE_TIME,d.DIN_PIN from ( " +
							     "select DIN_PIN,REC_EFF_DT,MAX(REC_CREATE_TIMESTAMP) as REC_CREATE_TIME from DRUG where MANUFACTURER_CD ='" + manufacturerCode + "' AND " +
							     "Trunc(REC_EFF_DT) = to_Date('" + effectiveDate.toString() + "','YYYY-MM-DD') AND DIN_PIN='" + din + "' AND REC_INACTIVE_TIMESTAMP is NULL group by DIN_PIN,REC_EFF_DT) r " +
							"inner join DRUG d on d.DIN_PIN = r.DIN_PIN and  d.REC_CREATE_TIMESTAMP = r.REC_CREATE_TIME";

						ps1 = conn.prepareStatement(sql2);
						rs1 = ps1.executeQuery();
						if(rs1 != null){
							while(rs1.next()){
								row = new RebateVO();
								secondPrice = rs1.getDouble("SECOND_PRICE");
								din = rs1.getString("DIN_PIN");
								brandName = rs1.getString("DIN_DESC");
								effectiveDate = rs1.getDate("REC_EFF_DT");
								
								row.setDinDesc(brandName);
								row.setManufacturerCd(manufacturerCode);
								row.setDinPin(din);
								row.setSecondPrice(secondPrice);
								row.setRecEffDt(effectiveDate);
								row.setRecCreateTimestamp(rs1.getDate("REC_CREATE_TIMESTAMP"));
								row.setManufacturerCd(manufacturerCode);
								dinMap.put(din, row);
							}
						}
					}else{
						dinMap.put(din, row);
					}
				}
				
				rs = null;
				ps1 = null;
				
				Set<String> allKeys = dinMap.keySet();
				Iterator<String> keys = allKeys.iterator();
				while(keys.hasNext()){
					String din = keys.next();
					RebateVO vo = (RebateVO)dinMap.get(din);
					/*
					sql = "select * from TEMP02 where DIN_PIN = '" + vo.getDinPin() + "' and DIN_DESC = '" + vo.getDinDesc() +
							"' and Trunc(REC_EFF_DT) = to_Date('" + vo.getRecEffDt() + "','YYYY-MM-DD')";
					ps = conn.prepareStatement(sql);
					rs = ps.executeQuery();
					if(rs != null && rs.next()){
						sql = "update TEMP02 set SECOND_PRICE = ? ,REC_CREATE_TIMESTAMP = ? where DIN_PIN = '" + vo.getDinPin() + "' and DIN_DESC = '" + vo.getDinDesc() +
							"' and REC_EFF_DT = to_Date('" + vo.getRecEffDt() + "','YYYY-MM-DD')";
						ps1 = conn.prepareStatement(sql);
						ps1.setDouble(1, vo.getSecondPrice());
						ps1.setDate(2,vo.getRecCreateTimestamp());
					}else{*/
						sql = "insert into TEMP02 (DIN_PIN,DIN_DESC,SECOND_PRICE,REC_EFF_DT,REC_CREATE_TIMESTAMP,MANUFACTURER_CD) values(?,?,?,?,?,?)";
						ps1 = conn.prepareStatement(sql);
						ps1.setString(1, vo.getDinPin());
						ps1.setString(2, vo.getDinDesc());
						ps1.setDouble(3, vo.getSecondPrice());
						ps1.setDate(4, vo.getRecEffDt());
						ps1.setDate(5, vo.getRecCreateTimestamp());
						ps1.setString(6, vo.getManufacturerCd());
					//}
					//ps1.addBatch();
					ps1.executeUpdate();
					temp02.add(vo);
				}
				//ps1.executeBatch();
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			DBConnectionManager.getManager().closeConnection(null, ps, rs);
			DBConnectionManager.getManager().closeConnection(null, ps1, rs1);
		}

		return temp02;
	}

	/**
	 * PLEASE NOTE:  step 2, step3, and step 4  select from same table with same conditions, only different parameter of DATE is passed.
	 * @param din
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public List<RebateVO> step4CreateTemp99(String manufacturerCode,  Connection conn) throws Exception{// here din is "02418401" for the example
		
		List<RebateVO> temp99 = new ArrayList<RebateVO>();
		String yyyyPriceDate = ConfigFromDB.getConfigPropertyFromDB(RebateConstant.YYYY_PRICE_DATE);	//???
		Map<String, RebateVO> dinMap = null;
		String sql = "select d.INDIVIDUAL_PRICE AS YYYY_PRICE, d.DIN_DESC, r.REC_EFF_DATE,d.DIN_PIN from (" + 
			     "select DIN_PIN,MAX(REC_EFF_DT) as REC_EFF_DATE from DRUG where MANUFACTURER_CD = '" + manufacturerCode+ "' AND " +
			     "REC_EFF_DT < to_date('" + yyyyPriceDate + "','MM-DD-YYYY') AND REC_INACTIVE_TIMESTAMP is NULL group by DIN_PIN) r " +
			"inner join DRUG d on d.DIN_PIN = r.DIN_PIN and d.REC_EFF_DT = r.REC_EFF_DATE";

		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;

		try{
			String sql1 = "TRUNCATE TABLE TEMP99";
			ps =  conn.prepareStatement(sql1);
			ps.executeUpdate();
			ps = null;
			
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs != null){
				dinMap = new HashMap<String, RebateVO>();
				while(rs.next()){
					RebateVO row = new RebateVO();
					double yyyyPrice = rs.getDouble("YYYY_PRICE");
					String din = rs.getString("DIN_PIN");
					String brandName = rs.getString("DIN_DESC");
					Date effectiveDate = rs.getDate("REC_EFF_DATE");
					
					row.setDinDesc(brandName);
					row.setManufacturerCd(manufacturerCode);
					row.setDinPin(din);
					row.setYyyyPrice(yyyyPrice);
					row.setRecEffDt(effectiveDate);
					
					if(dinMap.containsKey(din)){
						String eDate = format.format(effectiveDate);
						String sql2 = "select d.INDIVIDUAL_PRICE AS YYYY_PRICE, d.DIN_DESC, d.REC_EFF_DT, r.REC_CREATE_TIME,d.DIN_PIN from ( " +
							     "select DIN_PIN,REC_EFF_DT,MAX(REC_CREATE_TIMESTAMP) as REC_CREATE_TIME from DRUG where MANUFACTURER_CD ='" + manufacturerCode + "' AND " +
							     "Trunc(REC_EFF_DT) = to_Date('" + effectiveDate.toString() + "','YYYY-MM-DD') AND DIN_PIN='" + din + "' AND REC_INACTIVE_TIMESTAMP is NULL group by DIN_PIN,REC_EFF_DT) r " +
							     "inner join DRUG d on d.DIN_PIN = r.DIN_PIN and  d.REC_CREATE_TIMESTAMP = r.REC_CREATE_TIME";
						ps1 = conn.prepareStatement(sql2);
						rs1 = ps1.executeQuery();
						if(rs1 != null){
							while(rs1.next()){
								row = new RebateVO();
								yyyyPrice = rs1.getDouble("YYYY_PRICE");
								din = rs1.getString("DIN_PIN");
								brandName = rs1.getString("DIN_DESC");
								effectiveDate = rs1.getDate("REC_EFF_DT");
								
								row.setDinDesc(brandName);
								row.setManufacturerCd(manufacturerCode);
								row.setDinPin(din);
								row.setYyyyPrice(yyyyPrice);
								row.setRecEffDt(effectiveDate);
								row.setManufacturerCd(manufacturerCode);
								dinMap.put(din, row);
							}
						}
					}else{
						dinMap.put(din, row);
					}
					//temp99.add(row);
				}

				rs = null;
				ps1 = null;
				
				Set<String> allKeys = dinMap.keySet();
				Iterator<String> keys = allKeys.iterator();
				while(keys.hasNext()){
					String din = keys.next();
					RebateVO vo = (RebateVO)dinMap.get(din);
					/*
					sql = "select * from TEMP99 where DIN_PIN = '" + vo.getDinPin() + "' and DIN_DESC = '" + vo.getDinDesc() +
							"' and Trunc(REC_EFF_DT) = to_Date('" + vo.getRecEffDt() + "','YYYY-MM-DD')";
					ps = conn.prepareStatement(sql);
					rs = ps.executeQuery();
					if(rs != null && rs.next()){
						sql = "update TEMP99 set YYYY_PRICE = ? ,REC_CREATE_TIMESTAMP = ? where DIN_PIN = '" + vo.getDinPin() + "' and DIN_DESC = '" + vo.getDinDesc() +
							"' and REC_EFF_DT = to_Date('" + vo.getRecEffDt() + "','YYYY-MM-DD')";
						ps1 = conn.prepareStatement(sql);
						ps1.setDouble(1, vo.getYyyyPrice());
						ps1.setDate(2,vo.getRecCreateTimestamp());
					}else{*/
						String sql2 = "insert into TEMP99 (DIN_PIN,DIN_DESC,YYYY_PRICE,REC_EFF_DT,REC_CREATE_TIMESTAMP,MANUFACTURER_CD) values(?,?,?,?,?,?)";
						ps1 = conn.prepareStatement(sql2);
						ps1.setString(1, vo.getDinPin());
						ps1.setString(2, vo.getDinDesc());
						ps1.setDouble(3, vo.getYyyyPrice());
						ps1.setDate(4, vo.getRecEffDt());
						ps1.setDate(5, vo.getRecCreateTimestamp());
						ps1.setString(6, vo.getManufacturerCd());
					//}
					//ps1.addBatch();
					ps1.executeUpdate();
					temp99.add(vo);
				}
				//ps1.executeBatch();
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			DBConnectionManager.getManager().closeConnection(null, ps, rs);
			DBConnectionManager.getManager().closeConnection(null, ps1, rs1);
		}

		return temp99;
	}

	public List<RebateVO> step5Join2To4TogetherTemp02(String manufacturerCode,  Connection conn) throws Exception{// here din is "02418401" for the example
		
		List<RebateVO> temp02 = new ArrayList<RebateVO>();
		String sql = "select a.DIN_PIN,a.DIN_DESC, b.FIRST_PRICE, a.SECOND_PRICE, c.YYYY_PRICE, a.REC_EFF_DT,a.REC_CREATE_TIMESTAMP,b.MANUFACTURER_CD from TEMP02 a " +
					"join TEMP03 b on a.DIN_PIN = b.DIN_PIN " +
					"join Temp99 c on a.DIN_PIN = c.DIN_PIN " +
					"order by a.DIN_PIN";
		
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;

		try{
			
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs != null){
				while(rs.next()){
					RebateVO row = new RebateVO();
					String din = rs.getString("DIN_PIN");
					String dinDesc = rs.getString("DIN_DESC");
					double firstPrice = rs.getDouble("FIRST_PRICE");
					double secondPrice = rs.getDouble("SECOND_PRICE");
					double yyPrice = rs.getDouble("YYYY_PRICE");
					java.sql.Date recEffDt = rs.getDate("REC_EFF_DT");
					java.sql.Date recCreateTimestamp = rs.getDate("REC_CREATE_TIMESTAMP");
					String manufacturerCd = rs.getString("MANUFACTURER_CD");
	
					String sql1 = "update TEMP02 set FIRST_PRICE = ?, SECOND_PRICE=?, YYYY_PRICE = ? where DIN_PIN = '" +din + "' " +
							"and DIN_DESC = '" + dinDesc + "' and REC_EFF_DT = ? and REC_CREATE_TIMESTAMP = ? and MANUFACTURER_CD = ?";
					
					ps2 = conn.prepareStatement(sql1);
					ps2.setDouble(1, firstPrice);
					ps2.setDouble(2,secondPrice);
					ps2.setDouble(3, yyPrice);
					ps2.setDate(4, recEffDt);
					ps2.setDate(5, recCreateTimestamp);
					ps2.setString(6, manufacturerCd);

					int updateRow = ps2.executeUpdate();
					temp02.add(row);
					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			DBConnectionManager.getManager().closeConnection(null, ps, rs);
			DBConnectionManager.getManager().closeConnection(null, ps1, rs1);
			DBConnectionManager.getManager().closeConnection(null, ps2, null);
		}
		return temp02;
	}
	
	public List<RebateVO> step6And7DefineMissingDBTemp02(String manufacturerCode,  Connection conn) throws Exception{// here din is "02418401" for the example
		
		List<RebateVO> temp02 = new ArrayList<RebateVO>();
		String sql = "select * from TEMP02";
		
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;

		try{
			Map<String, RebateVO> missingDataFromCheduleAMap = RebateCalculatorCache.getMissingDataFromCheduleAMap(conn,manufacturerCode);
			Map<String, Double> dbpLimiMap = RebateCalculatorCache.getDbpLimiMap(conn,manufacturerCode);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs != null){
				while(rs.next()){
					RebateVO row = new RebateVO();
					String din = rs.getString("DIN_PIN");
					double firstPrice =rs.getDouble("FIRST_PRICE");
					double secondPrice = rs.getDouble("SECOND_PRICE");
					double yyPrice = rs.getDouble("YYYY_PRICE");
	
					String sql1 = "update TEMP02 set DBP_LIM_JL115 = " + dbpLimiMap.get(din) +
										",STRENGTH = '" + missingDataFromCheduleAMap.get(din).getStrength() + "'," +
										"DOSAGE_FORM = '" + missingDataFromCheduleAMap.get(din).getDosageForm() + "'," +
										"GEN_NAME = '" + missingDataFromCheduleAMap.get(din).getGenName() + "' " ;
					if(firstPrice == 0){
						sql1 += ", FIRST_PRICE= " + missingDataFromCheduleAMap.get(din).getFirstPrice();
					}
					if(yyPrice == 0){
						if(missingDataFromCheduleAMap.get(din).getYyyyPrice() != 0){
							sql1 += ", YYYY_PRICE= " + missingDataFromCheduleAMap.get(din).getYyyyPrice();
						}
					}
					
					sql1 += " where DIN_PIN = '" + din + "'";
					
					ps1 = conn.prepareStatement(sql1);
					int updateRow = ps1.executeUpdate();

					temp02.add(row);
					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			DBConnectionManager.getManager().closeConnection(null, ps, rs);
			DBConnectionManager.getManager().closeConnection(null, ps1, rs1);
		}

		return temp02;
	}
	

	public List<RebateVO> step8CreateTemp04(String manufacturerCode,  Connection conn) throws Exception{// here din is "02418401" for the example
		
		List<RebateVO> temp04 = new ArrayList<RebateVO>();
		PreparedStatement ps = null;

		try{
			String sql1 = "TRUNCATE TABLE TEMP04";
			ps =  conn.prepareStatement(sql1);
			ps.executeUpdate();
			ps = null;
			
			String sql = "Insert into TEMP04 (DIN_PIN,GEN_NAME,DIN_DESC,FIRST_PRICE,SECOND_PRICE,YYYY_PRICE,DBP_LIM_JL115,STRENGTH,DOSAGE_FORM)  " +	//Do we need GEN_NAME?
					"select DIN_PIN,GEN_NAME,DIN_DESC,FIRST_PRICE,SECOND_PRICE,YYYY_PRICE,DBP_LIM_JL115,STRENGTH,DOSAGE_FORM from TEMP02 ";
					//"where MANUFACTURER_CD = '" + manufacturerCode + "'";
	
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			
			//String sql2 = "select * from TEMP04 ";
			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			DBConnectionManager.getManager().closeConnection(null, ps, null);
		}
		return temp04;
	}

	public List<RebateVO> step9CreateTemp05(String manufacturerCode,  Connection conn) throws Exception{// here din is "02418401" for the example
		
		List<RebateVO> temp05 = new ArrayList<RebateVO>();
		PreparedStatement ps = null;

		try{
			String sql1 = "TRUNCATE TABLE TEMP05";
			ps =  conn.prepareStatement(sql1);
			ps.executeUpdate();
			ps = null;
			
			String sql = "Insert into TEMP05 (DIN_PIN,DIN_DESC,GEN_NAME,STRENGTH, DOSAGE_FORM," +
					"FIRST_PRICE,SECOND_PRICE,YYYY_PRICE,DBP_LIM_JL115," +
					"DRG_CST_ALLD,QTY,PROD_SEL,MANUFACTURER_CD,PROF_FEE_ALLD,TOT_AMT_PD," + 
					"INTERVENTION_1,INTERVENTION_2,INTERVENTION_3,INTERVENTION_4,INTERVENTION_5,INTERVENTION_6," +
					"INTERVENTION_7,INTERVENTION_8,INTERVENTION_9,INTERVENTION_10,CLAIM_ID)" +
					"select b.DIN_PIN,b.DIN_DESC,b.GEN_NAME,b.STRENGTH, b.DOSAGE_FORM," +
					"b.FIRST_PRICE,b.SECOND_PRICE,b.YYYY_PRICE,b.DBP_LIM_JL115," +
					"a.DRG_CST_ALLD,a.QTY,a.PROD_SEL,b.MANUFACTURER_CD,a.PROF_FEE_ALLD,a.TOT_AMT_PD, " +
					"a.INTERVENTION_1,a.INTERVENTION_2,a.INTERVENTION_3,a.INTERVENTION_4,a.INTERVENTION_5,a.INTERVENTION_6," +
					"a.INTERVENTION_7,a.INTERVENTION_8,a.INTERVENTION_9,a.INTERVENTION_10,a.CLAIM_ID from TEMP01 a " +
					"Join TEMP04 b on a.DIN_PIN = b.DIN_PIN";
					//"where MANUFACTURER_CD = '" + manufacturerCode + "'";
	
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			DBConnectionManager.getManager().closeConnection(null, ps, null);
		}
		return temp05;
	}

	public List<RebateVO> step10CreateTemp06(String manufacturerCode,  Connection conn) throws Exception{// here din is "02418401" for the example
		
		List<RebateVO> temp06 = new ArrayList<RebateVO>();
		PreparedStatement ps = null;

		try{
			String sql1 = "TRUNCATE TABLE TEMP06";
			ps =  conn.prepareStatement(sql1);
			ps.executeUpdate();
			ps = null;
			
			String sql = "Insert into TEMP06 (CLAIM_ID,DIN_PIN,DIN_DESC,GEN_NAME,STRENGTH, DOSAGE_FORM," +
					"FIRST_PRICE,SECOND_PRICE,YYYY_PRICE,DBP_LIM_JL115,DRG_CST_ALLD,QTY,PROD_SEL,MANUFACTURER_CD,PROF_FEE_ALLD,TOT_AMT_PD, " +
					"INTERVENTION_1,INTERVENTION_2,INTERVENTION_3,INTERVENTION_4,INTERVENTION_5,INTERVENTION_6," +
					"INTERVENTION_7,INTERVENTION_8,INTERVENTION_9,INTERVENTION_10) " +
					"select CLAIM_ID,DIN_PIN,DIN_DESC,GEN_NAME,STRENGTH, DOSAGE_FORM,FIRST_PRICE,SECOND_PRICE,YYYY_PRICE,DBP_LIM_JL115," +
					"DRG_CST_ALLD,QTY,PROD_SEL,MANUFACTURER_CD,PROF_FEE_ALLD,TOT_AMT_PD, " +
					"INTERVENTION_1,INTERVENTION_2,INTERVENTION_3,INTERVENTION_4,INTERVENTION_5,INTERVENTION_6, " +
					"INTERVENTION_7,INTERVENTION_8,INTERVENTION_9,INTERVENTION_10 from TEMP05 " +
					"where DBP_LIM_JL115  = SECOND_PRICE or  " +
					"(DBP_LIM_JL115  < SECOND_PRICE and (PROD_SEL = '1' or  INTERVENTION_1 = 'MI' or  INTERVENTION_2 = 'MI' " +
					"or  INTERVENTION_3 = 'MI' or  INTERVENTION_4 = 'MI' or  INTERVENTION_5 = 'MI' or  INTERVENTION_6 = 'MI'))";
					//"where MANUFACTURER_CD = '" + manufacturerCode + "'";
		
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			DBConnectionManager.getManager().closeConnection(null, ps, null);
		}
		return temp06;
	}
	
	public List<RebateVO> step11To13CreateTemp07(String manufacturerCode,  Connection conn) throws Exception{// here din is "02418401" for the example
		
		List<RebateVO> temp07 = new ArrayList<RebateVO>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;

		try{
			String sql = "TRUNCATE TABLE TEMP07";
			ps =  conn.prepareStatement(sql);
			ps.executeUpdate();
			ps = null;
			
			sql = "insert into TEMP07 (CLAIM_ID,DIN_PIN,DIN_DESC,GEN_NAME,STRENGTH,DOSAGE_FORM,FIRST_PRICE,SECOND_PRICE,YYYY_PRICE," +
					"DBP_LIM_JL115,DRG_CST_ALLD,QTY,PROD_SEL,MANUFACTURER_CD,PROF_FEE_ALLD,TOT_AMT_PD,INTERVENTION_1," +
					"INTERVENTION_2,INTERVENTION_3,INTERVENTION_4,INTERVENTION_5,INTERVENTION_6,REC_EFF_DT,REC_CREATE_TIMESTAMP) " +
					"select CLAIM_ID,DIN_PIN,DIN_DESC,GEN_NAME,STRENGTH,DOSAGE_FORM,FIRST_PRICE,SECOND_PRICE,YYYY_PRICE," +
					"DBP_LIM_JL115,DRG_CST_ALLD,QTY,PROD_SEL,MANUFACTURER_CD,PROF_FEE_ALLD,TOT_AMT_PD,INTERVENTION_1," +
					"INTERVENTION_2,INTERVENTION_3,INTERVENTION_4,INTERVENTION_5,INTERVENTION_6,REC_EFF_DT,REC_CREATE_TIMESTAMP  from TEMP06";
			// +"where MANUFACTURER_CD = " + manufacturerCode;
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			
			sql= "select *  from TEMP06 order by DIN_PIN";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs != null){
				while(rs.next()){
					RebateVO row = new RebateVO();
					String din = rs.getString("DIN_PIN");
					double secondPrice = rs.getDouble("SECOND_PRICE");
					double drugCostAllowed = rs.getDouble("DRG_CST_ALLD");
					double qty = rs.getDouble("QTY");
					double adustedQty = drugCostAllowed/secondPrice;
					int claimID = rs.getInt("CLAIM_ID");
					
					String sql1 = "update TEMP07 set ADJ_QTY = " + adustedQty;
							
					double finalQty = -1;
					if(qty < adustedQty ){
						finalQty = qty;
					}else{
						finalQty = adustedQty;
					}
					sql1 += ", FNL_QTY = " + finalQty + " where CLAIM_ID = " + claimID;

					ps1 = conn.prepareStatement(sql1);
					ps1.executeUpdate();

					row.setDinPin(din);
					row.setSecondPrice(secondPrice);
					row.setDrgCstAlld(drugCostAllowed);
					row.setQty(finalQty);
					temp07.add(row);
				}
			}			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			DBConnectionManager.getManager().closeConnection(null, ps, rs);
			DBConnectionManager.getManager().closeConnection(null, ps1, null);
		}

		return temp07;
	}
	
	public List<RebateVO> step14CreateTemp08(String manufacturerCode,  Connection conn) throws Exception{// here din is "02418401" for the example
		
		List<RebateVO> temp08 = new ArrayList<RebateVO>();
		//I think it is done in step 11 to 13;
		return temp08;
	}

	/**
	 * Calculate Discount_Volumn (CHQ_BACK)
	 * 2 prices: DIN_PIN in (02408872,02418401,02245126,02245127,02240835,02240836,02240837,02230418,02230420,02444186 ,02217422)
	 * The rest are 3 prices
	 * 
	 * Calculate Volume discount
	 */
	public List<RebateVO> step15CalculateVolumeDiscountTemp08(String manufacturerCode,  Connection conn) throws Exception{// here din is "02418401" for the example
		
		List<RebateVO> temp08 = new ArrayList<RebateVO>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;

		try{
			String sql = "TRUNCATE TABLE DRUG_REBATE";
			ps =  conn.prepareStatement(sql);
			ps.executeUpdate();
			ps = null;
			
			sql = "Insert into DRUG_REBATE (CLAIM_ID,DIN_PIN,DIN_DESC,GEN_NAME,STRENGTH, DOSAGE_FORM," +
					"FIRST_PRICE,SECOND_PRICE,YYYY_PRICE,DBP_LIM_JL115,DRG_CST_ALLD,QTY,ADJ_QTY,FNL_QTY,PROD_SEL,MANUFACTURER_CD,PROF_FEE_ALLD,TOT_AMT_PD, " +
					"INTERVENTION_1,INTERVENTION_2,INTERVENTION_3,INTERVENTION_4,INTERVENTION_5,INTERVENTION_6) " +
					"select CLAIM_ID,DIN_PIN,DIN_DESC,GEN_NAME,STRENGTH, DOSAGE_FORM,FIRST_PRICE,SECOND_PRICE,YYYY_PRICE,DBP_LIM_JL115," +
					"DRG_CST_ALLD,QTY,ADJ_QTY,FNL_QTY,PROD_SEL,MANUFACTURER_CD,PROF_FEE_ALLD,TOT_AMT_PD, " +
					"INTERVENTION_1,INTERVENTION_2,INTERVENTION_3,INTERVENTION_4,INTERVENTION_5,INTERVENTION_6 from TEMP07 ";
			// +"where MANUFACTURER_CD = " + manufacturerCode;
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			
			sql = "select * from TEMP07";
			ps =  conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			
			if(rs != null){
				dinDetails = new HashMap<>();
				List<RebateVO> allRebates = new ArrayList<>();
				String dinNumber = null;
				twoPriceDinList = RebateCalculatorCache.getTwoPriceDinList(conn,manufacturerCode);
				threePriceDinList = RebateCalculatorCache.getThreePriceDinList(conn,manufacturerCode);
				while(rs.next()){
					RebateVO row = new RebateVO();
					String din = rs.getString("DIN_PIN");
					double firstPrice = rs.getDouble("FIRST_PRICE");
					double secondPrice = rs.getDouble("SECOND_PRICE");
					double yyyyPrice = rs.getDouble("YYYY_PRICE");
					int finalQuantity = rs.getInt("FNL_QTY");
					double drugCostAllowed = rs.getDouble("DRG_CST_ALLD");
					double claimID = rs.getInt("CLAIM_ID");
					
					double volumeDiscount = -1;
					boolean isTwoPriceDin = twoPriceDinList.contains(din);
					boolean isThreePriceDin = threePriceDinList.contains(din);
					String isTwoPrices = "Y";
					if(isTwoPriceDin && drugCostAllowed < 1000){
						volumeDiscount = (secondPrice - firstPrice) * finalQuantity * 1.08; 
						isTwoPrices = "Y";
					}else if(isTwoPriceDin && drugCostAllowed > 1000){
						volumeDiscount = (secondPrice - firstPrice) * finalQuantity * 1.06; 
						isTwoPrices = "Y";
					}else if(isThreePriceDin && !isTwoPriceDin && drugCostAllowed < 1000){
						volumeDiscount = (secondPrice - firstPrice) * finalQuantity + (secondPrice - yyyyPrice) * finalQuantity * 0.08; 
						isTwoPrices = "N";
					}else if(isThreePriceDin && !isTwoPriceDin && drugCostAllowed > 1000){
						volumeDiscount = (secondPrice - firstPrice) * finalQuantity + (secondPrice - yyyyPrice) * finalQuantity * 0.06; 
						isTwoPrices = "N";
					}
					
					String sql1 = "update DRUG_REBATE set CHQ_BACK = " + volumeDiscount + ",IS_TWO_PRICE ='" + isTwoPrices + "' where CLAIM_ID = " + claimID;
					ps1 = conn.prepareStatement(sql1);
					ps1.executeUpdate();
					
					String dinDesc = rs.getString("DIN_DESC");
					String strength = rs.getString("STRENGTH");
					String dosageForm = rs.getString("DOSAGE_FORM");
					String manufacturerCd = rs.getString("MANUFACTURER_CD");
					
					if(dinNumber != null && !din.equals(dinNumber)){
						dinDetails.put(dinNumber, allRebates);
						allRebates = new ArrayList<>();
					}
					
					
					RebateVO vo = new RebateVO();
					vo.setDinPin(din);
					vo.setDinDesc(dinDesc);
					vo.setStrength(strength);
					vo.setDosageForm(dosageForm);
					vo.setManufacturerCd(manufacturerCd);
					vo.setDrgCstAlld(drugCostAllowed);
					vo.setFirstPrice(firstPrice);
					vo.setSecondPrice(secondPrice);
					vo.setVolumeDiscount(volumeDiscount);
					vo.setQty(finalQuantity);
					allRebates.add(vo);
					dinNumber = din;
					//DIN/PIN	Brand Name	Strength	Dosage Form	Manufacturer Code	
					//Total Drug Cost Paid	Former DBP (B)	Current DBP (A)	Quantity	Volume Discount

				}
				//Add the final in
				dinDetails.put(dinNumber, allRebates);

			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			DBConnectionManager.getManager().closeConnection(null, ps, rs);
			DBConnectionManager.getManager().closeConnection(null, ps1, null);
		}
		
		return temp08;
	}

	/**
	 *  creates a new file at DIN level (merge the value of QTY, CHQ_BACK,and DRG_CST_ALLD 
	 * 
	 * @param din
	 * @param conn
	 * @return
	 * @throws Exception
	 *
	 */
	public List<RebateVO> step16And17CreateTemp09(String manufacturerCode,  Connection conn) throws Exception{// here din is "02418401" for the example
		
		List<RebateVO> temp09 = new ArrayList<RebateVO>();
		
		PreparedStatement ps = null;
		//ResultSet rs = null;
		//PreparedStatement ps1 = null;

		try{
			String sql = "TRUNCATE TABLE TEMP09";
			ps =  conn.prepareStatement(sql);
			ps.executeUpdate();
			ps = null;
			
			sql = "insert into TEMP09 (DIN_PIN,DIN_DESC,GEN_NAME,STRENGTH, DOSAGE_FORM,FIRST_PRICE,SECOND_PRICE,YYYY_PRICE,DBP_LIM_JL115, " +
					"MANUFACTURER_CD,IS_TWO_PRICE,CHQ_BACK,FNL_QTY,ADJ_QTY,QTY,DRG_CST_ALLD)  " +
					"select distinct b.DIN_PIN,b.DIN_DESC,b.GEN_NAME,b.STRENGTH, b.DOSAGE_FORM,b.FIRST_PRICE,b.SECOND_PRICE,b.YYYY_PRICE,b.DBP_LIM_JL115,b.MANUFACTURER_CD,b.IS_TWO_PRICE, " +
					"a.CHQ_BACK1,a.FNL_QTY1,a.ADJ_QTY1,a.QTY1,a.DRG_CST_ALLD1 from( " +
					"select DIN_PIN,sum(CHQ_BACK) as CHQ_BACK1,sum(FNL_QTY) as FNL_QTY1,sum(ADJ_QTY) as ADJ_QTY1,sum(QTY) as QTY1,sum(DRG_CST_ALLD) as DRG_CST_ALLD1 from DRUG_REBATE group by DIN_PIN) a " +
					"inner join DRUG_REBATE b  on a.DIN_PIN = b.DIN_PIN order by b.DIN_PIN";
								// +"where MANUFACTURER_CD = " + manufacturerCode;
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			DBConnectionManager.getManager().closeConnection(null, ps, null);
			//DBConnectionManager.getManager().closeConnection(null, ps1, null);
		}
		
		return temp09;
	}
	
	/**
	 * creates a Summary File at DIN level 
	 * 
	 * @param din
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public List<RebateVO> step17CreateSummaryFileTemp10(String manufacturerCode,  Connection conn) throws Exception{// here din is "02418401" for the example
		
		List<RebateVO> temp10 = new ArrayList<RebateVO>();
		//completed in step 16 already.
		
		return temp10;
	}
	
	/**
	 * create a summary file that includes all DINs 
	 * 
	 * @param din
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public List<RebateVO> step18And19CreateSummaryFileTemp11(String manufacturerCode,  Connection conn) throws Exception{// here din is "02418401" for the example
		
		List<RebateVO> temp11 = new ArrayList<RebateVO>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;

		try{
			String sql = "TRUNCATE TABLE REBATE_SUMMARY";
			ps =  conn.prepareStatement(sql);
			ps.executeUpdate();
			ps = null;
			
			sql = "insert into REBATE_SUMMARY select * from TEMP09";
								// +"where MANUFACTURER_CD = " + manufacturerCode;
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			
			sql = "select DIN_PIN, YYYY_PRICE from SCHEDULE_A where DIN_PIN not in (select distinct DIN_PIN from REBATE_SUMMARY)";
			ps =  conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			
			if(rs != null){
				while(rs.next()){
					//RebateVO row = new RebateVO();
					String din = rs.getString("DIN_PIN");
					double yyyyPrice = rs.getDouble("YYYY_PRICE");
					
					String isTwoPrices = "Y";
					if(yyyyPrice != 0){
						isTwoPrices = "N";
					}
					String sql1 = "insert into REBATE_SUMMARY (DIN_PIN,IS_TWO_PRICE) values('" + din + "','" + isTwoPrices + "')";
					ps1 = conn.prepareStatement(sql1);
					ps1.executeUpdate();
					
				}
			}
			
			sql = "select * from REBATE_SUMMARY where CHQ_BACK < 0";
			ps =  conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs != null){
				while(rs.next()){
					String din = rs.getString("DIN_PIN");
					
					String sql1 = "update REBATE_SUMMARY set DIN_DESC = null,GEN_NAME = null,STRENGTH = null,DOSAGE_FORM = null, " +
									"FIRST_PRICE = null,SECOND_PRICE = null,YYYY_PRICE = null,DBP_LIM_JL115 = null,CHQ_BACK = null, " +
									"ADJ_QTY = null,FNL_QTY = null,QTY = null,DRG_CST_ALLD = null,MANUFACTURER_CD = null, " +
									"PROF_FEE_ALLD = null,TOT_AMT_PD = null,REC_EFF_DT = null where DIN_PIN = '" + din + "'";
					ps1 = conn.prepareStatement(sql1);
					ps1.executeUpdate();
					
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			DBConnectionManager.getManager().closeConnection(null, ps, rs);
			DBConnectionManager.getManager().closeConnection(null, ps1, null);
		}
		return temp11;
	}
	
	/**
	 *  creates a summary Excel file for the products that include 3 price points, and export to Excel sheet file 
	 * 	DIN_PIN not in (02408872,02418401,02245126,02245127,02240835,02240836,02240837,02230418,02230420,02444186,02217422)
	 * @param din
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public void step20And21CreateSummaryFileFromTemp11(String manufacturerCode,  Connection conn, String path) throws Exception{// here din is "02418401" for the example
		
		File file = new File(path); 
		if(!file.exists()){
			file.mkdirs();
		}
		String filepathAndName = path + "\\Rebate_Summary_" + manufacturerCode + "_" + new Date(System.currentTimeMillis()).toString() + ".xlsx";
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		FileOutputStream outputStream = null;
		try{
			String sql = "select DIN_PIN,DIN_DESC,STRENGTH,DOSAGE_FORM,MANUFACTURER_CD,DRG_CST_ALLD," +
					"FIRST_PRICE,SECOND_PRICE,FNL_QTY,CHQ_BACK from REBATE_SUMMARY where IS_TWO_PRICE = 'Y' order by DIN_PIN";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
	        XSSFWorkbook workbook = new XSSFWorkbook();
			if(rs != null){
		        XSSFSheet sheet1 = workbook.createSheet("Two Price DIN");
		        int rowNum = 0;
	            Row row1 = sheet1.createRow(rowNum++);
	            int colNum = 0;
	            Cell cell11 = row1.createCell(colNum++);
	            cell11.setCellValue("DIN/PIN");
	            Cell cell12 = row1.createCell(colNum++);
	            cell12.setCellValue("Brand Name");
	            Cell cell13 = row1.createCell(colNum++);
	            cell13.setCellValue("Strength");
	            Cell cell14 = row1.createCell(colNum++);
	            cell14.setCellValue("Dosage Form");
	            Cell cell15 = row1.createCell(colNum++);
	            cell15.setCellValue("Manufacturer Code");
	            Cell cell16 = row1.createCell(colNum++);
	            cell16.setCellValue("Total Drug Cost Paid");
	            Cell cell17 = row1.createCell(colNum++);
	            cell17.setCellValue("Former DBP (B)");
	            Cell cell18 = row1.createCell(colNum++);
	            cell18.setCellValue("Current DBP (A)");
	            Cell cell19 = row1.createCell(colNum++);
	            cell19.setCellValue("Quantity");
	            Cell cell110 = row1.createCell(colNum++);
	            cell110.setCellValue("Volume Discount");
	            double twoPriceSubTotal = 0;
				while(rs.next()){
					String dinPin = rs.getString("DIN_PIN");
					String brandName = rs.getString("DIN_DESC");
					String strength = rs.getString("STRENGTH");
					String dosageForm = rs.getString("DOSAGE_FORM");
					manufacturerCode = rs.getString("MANUFACTURER_CD");
					double totalDrugCostPaid =  rs.getDouble("DRG_CST_ALLD");
					double formerDBP  =  rs.getDouble("FIRST_PRICE");
					double currentDBP = rs.getDouble("SECOND_PRICE");
					double quantity = rs.getDouble("FNL_QTY");
					double volumeDiscouny =  rs.getDouble("CHQ_BACK");
	
		            Row row = sheet1.createRow(rowNum++);;
		            colNum = 0;
		            Cell cell1 = row.createCell(colNum++);
		            cell1.setCellValue(dinPin);
		            Cell cell2 = row.createCell(colNum++);
		            cell2.setCellValue(brandName);
		            Cell cell3 = row.createCell(colNum++);
		            cell3.setCellValue(strength);
		            Cell cell4 = row.createCell(colNum++);
		            cell4.setCellValue(dosageForm);
		            Cell cell5 = row.createCell(colNum++);
		            cell5.setCellValue(manufacturerCode);
		            Cell cell6 = row.createCell(colNum++);
		            cell6.setCellValue(totalDrugCostPaid);
		            Cell cell7 = row.createCell(colNum++);
		            cell7.setCellValue(formerDBP);
		            Cell cell8 = row.createCell(colNum++);
		            cell8.setCellValue(currentDBP);
		            Cell cell9 = row.createCell(colNum++);
		            cell9.setCellValue(quantity);
		            Cell cell10 = row.createCell(colNum++);
		            cell10.setCellValue(volumeDiscouny);
		            
		            twoPriceSubTotal += volumeDiscouny;
				}
	            Row row = sheet1.createRow(rowNum++);
	            Cell cellSubTotalLabel = row.createCell(colNum-2);
	            cellSubTotalLabel.setCellValue("Subtotal");
	            Cell cellSubTotalValue = row.createCell(colNum-1);
	            cellSubTotalValue.setCellValue(twoPriceSubTotal);
	            
			}
			
			sql = "select DIN_PIN,DIN_DESC,STRENGTH,DOSAGE_FORM,MANUFACTURER_CD,DRG_CST_ALLD,FIRST_PRICE," +
					"SECOND_PRICE,FNL_QTY,CHQ_BACK from REBATE_SUMMARY where IS_TWO_PRICE = 'N' order by DIN_PIN";
			
			rs = null;
			ps = null;
			
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs != null){
		        XSSFSheet sheet2 = workbook.createSheet("Three Price DIN");
		        int rowNum = 0;
	            Row row1 = sheet2.createRow(rowNum++);
	            int colNum = 0;
	            Cell cell11 = row1.createCell(colNum++);
	            cell11.setCellValue("DIN/PIN");
	            Cell cell12 = row1.createCell(colNum++);
	            cell12.setCellValue("Brand Name");
	            Cell cell13 = row1.createCell(colNum++);
	            cell13.setCellValue("Strength");
	            Cell cell14 = row1.createCell(colNum++);
	            cell14.setCellValue("Dosage Form");
	            Cell cell15 = row1.createCell(colNum++);
	            cell15.setCellValue("Manufacturer Code");
	            Cell cell16 = row1.createCell(colNum++);
	            cell16.setCellValue("Total Drug Cost Paid");
	            Cell cell17 = row1.createCell(colNum++);
	            cell17.setCellValue("Former DBP (B)");
	            Cell cell18 = row1.createCell(colNum++);
	            cell18.setCellValue("Current DBP (A)");
	            Cell cell19 = row1.createCell(colNum++);
	            cell19.setCellValue("Quantity");
	            Cell cell110 = row1.createCell(colNum++);
	            cell110.setCellValue("Volume Discount");
	            double threePriceSubTotal = 0;
				while(rs.next()){
					String dinPin = rs.getString("DIN_PIN");
					String brandName = rs.getString("DIN_DESC");
					String strength = rs.getString("STRENGTH");
					String dosageForm = rs.getString("DOSAGE_FORM");
					manufacturerCode = rs.getString("MANUFACTURER_CD");
					double totalDrugCostPaid =  rs.getDouble("DRG_CST_ALLD");
					double formerDBP  =  rs.getDouble("FIRST_PRICE");
					double currentDBP = rs.getDouble("SECOND_PRICE");
					double quantity = rs.getDouble("FNL_QTY");
					double volumeDiscouny =  rs.getDouble("CHQ_BACK");
	
		            Row row = sheet2.createRow(rowNum++);;
		            colNum = 0;
		            Cell cell1 = row.createCell(colNum++);
		            cell1.setCellValue(dinPin);
		            Cell cell2 = row.createCell(colNum++);
		            cell2.setCellValue(brandName);
		            Cell cell3 = row.createCell(colNum++);
		            cell3.setCellValue(strength);
		            Cell cell4 = row.createCell(colNum++);
		            cell4.setCellValue(dosageForm);
		            Cell cell5 = row.createCell(colNum++);
		            cell5.setCellValue(manufacturerCode);
		            Cell cell6 = row.createCell(colNum++);
		            cell6.setCellValue(totalDrugCostPaid);
		            Cell cell7 = row.createCell(colNum++);
		            cell7.setCellValue(formerDBP);
		            Cell cell8 = row.createCell(colNum++);
		            cell8.setCellValue(currentDBP);
		            Cell cell9 = row.createCell(colNum++);
		            cell9.setCellValue(quantity);
		            Cell cell10 = row.createCell(colNum++);
		            cell10.setCellValue(volumeDiscouny);

		            threePriceSubTotal += volumeDiscouny;
				}
	            Row row = sheet2.createRow(rowNum++);
	            Cell cellSubTotalLabel = row.createCell(colNum-2);
	            cellSubTotalLabel.setCellValue("Subtotal");
	            Cell cellSubTotalValue = row.createCell(colNum-1);
	            cellSubTotalValue.setCellValue(threePriceSubTotal);
			}
			
            outputStream = new FileOutputStream(filepathAndName);
            workbook.write(outputStream);
            outputStream.close();
            //workbook.close();
		
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			DBConnectionManager.getManager().closeConnection(null, ps, rs);
			if(outputStream != null){
				outputStream.close();
			}
		}
		
	}
	
	


	public List<RebateVO> getDinDetail(String din,String path)  throws Exception {
		List<RebateVO> dinebates = dinDetails.get(din);

		
		String filepathAndName = path + "\\DIN" + din + "Rebate_Summary_" + new Date(System.currentTimeMillis()).toString() + ".xlsx";
		
		FileOutputStream outputStream = null;
		try{
	        XSSFWorkbook workbook = new XSSFWorkbook();
	        XSSFSheet sheet1 = workbook.createSheet("DIN" + din);
	        int rowNum = 0;
            Row row1 = sheet1.createRow(rowNum++);
            int colNum = 0;
            Cell cell11 = row1.createCell(colNum++);
            cell11.setCellValue("DIN/PIN");
            Cell cell12 = row1.createCell(colNum++);
            cell12.setCellValue("Brand Name");
            Cell cell13 = row1.createCell(colNum++);
            cell13.setCellValue("Strength");
            Cell cell14 = row1.createCell(colNum++);
            cell14.setCellValue("Dosage Form");
            Cell cell15 = row1.createCell(colNum++);
            cell15.setCellValue("Manufacturer Code");
            Cell cell16 = row1.createCell(colNum++);
            cell16.setCellValue("Total Drug Cost Paid");
            Cell cell17 = row1.createCell(colNum++);
            cell17.setCellValue("Former DBP (B)");
            Cell cell18 = row1.createCell(colNum++);
            cell18.setCellValue("Current DBP (A)");
            Cell cell19 = row1.createCell(colNum++);
            cell19.setCellValue("Quantity");
            Cell cell110 = row1.createCell(colNum++);
            cell110.setCellValue("Volume Discount");

			for(RebateVO vo:dinebates){
				String dinPin = vo.getDinPin();
				String brandName = vo.getDinDesc();
				String strength = vo.getStrength();
				String dosageForm = vo.getDosageForm();
				String manufacturerCode = vo.getManufacturerCd();
				double totalDrugCostPaid =  vo.getDrgCstAlld();
				double formerDBP  =  vo.getFirstPrice();
				double currentDBP = vo.getSecondPrice();
				double quantity = vo.getQty();
				double volumeDiscount =  vo.getVolumeDiscount();

	            Row row = sheet1.createRow(rowNum++);;
	            colNum = 0;
	            Cell cell1 = row.createCell(colNum++);
	            cell1.setCellValue(dinPin);
	            Cell cell2 = row.createCell(colNum++);
	            cell2.setCellValue(brandName);
	            Cell cell3 = row.createCell(colNum++);
	            cell3.setCellValue(strength);
	            Cell cell4 = row.createCell(colNum++);
	            cell4.setCellValue(dosageForm);
	            Cell cell5 = row.createCell(colNum++);
	            cell5.setCellValue(manufacturerCode);
	            Cell cell6 = row.createCell(colNum++);
	            cell6.setCellValue(totalDrugCostPaid);
	            Cell cell7 = row.createCell(colNum++);
	            cell7.setCellValue(formerDBP);
	            Cell cell8 = row.createCell(colNum++);
	            cell8.setCellValue(currentDBP);
	            Cell cell9 = row.createCell(colNum++);
	            cell9.setCellValue(quantity);
	            Cell cell10 = row.createCell(colNum++);
	            cell10.setCellValue(volumeDiscount);
			}

            outputStream = new FileOutputStream(filepathAndName);
            workbook.write(outputStream);
            outputStream.close();
            //workbook.close();
		
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			if(outputStream != null){
				outputStream.close();
			}
		}

		
		
		return dinebates;
	}
	
	public static Map<String, List<RebateVO>> getDinDetails() {
		return dinDetails;
	}

	
}

