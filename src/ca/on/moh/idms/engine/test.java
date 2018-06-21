package ca.on.moh.idms.engine;

import gov.moh.app.db.DBConnectionManager;
import gov.moh.config.ConfigFromDB;
import gov.moh.config.ConfigPropertyName;
import gov.moh.config.PropertyConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.RelationalGroupedDataset;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.sql.DataFrameReader;
import org.apache.spark.sql.functions;

import static org.apache.spark.sql.functions.date_sub;
import static org.apache.spark.sql.functions.date_trunc;
import static org.apache.spark.sql.functions.date_format;
import static org.apache.spark.sql.functions.to_date;
import static org.apache.spark.sql.functions.trunc;
import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.max;
import static org.apache.spark.sql.functions.bround;

import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.expressions.Window;

import ca.on.moh.idms.util.RebateCalculatorCache;
import ca.on.moh.idms.util.RebateConstant;
import ca.on.moh.idms.util.RebateUtil;
import ca.on.moh.idms.util.SparkDatabaseConnection;
import ca.on.moh.idms.vo.RebateVO;
import scala.collection.JavaConversions;
import scala.collection.immutable.Seq;
import scala.collection.JavaConverters;

public class test {
	private SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-DD",new Locale("ca", "CA"));
	private static Map<String, List<RebateVO>> dinDetails;
	private List<String> twoPriceDinList;
	private List<String> threePriceDinList;
	static String appRoot = System.getProperty("user.dir");
	SparkSession spark = SparkSession
		      .builder()
		      .appName("Java Spark SQL data sources example")
		      .config("spark.some.config.option", "some-value")
		      .master("local[*]")	      
		      .getOrCreate();
	
	
	
	public static void main (String [] args) throws Exception {
    	Logger.getLogger("org").setLevel(Level.ERROR);
        System.out.println("Calculation engine started ...... ");
        
        PropertyConfig.setPropertyPath(appRoot + "\\conf\\system.properties");
        System.setProperty("hadoop.home.dir", "C:\\Spark\\hadoop");
        
        test calculator = new test();
        String manufacturerCode = "LEO";
        
        Connection conn1 = null;
        Connection conn2 = null;
        try{
               long startTime = System.currentTimeMillis();
               
               String filepathAndName = "C:\\TEMP\\IDMS\\data";
               calculator.step1(manufacturerCode);
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
	
	 private static Dataset<org.apache.spark.sql.Row> getDataset(String sql) throws Exception{
	        SparkSession spark = SparkSession.builder().appName("IdmsEngine")//.enableHiveSupport()
	                        .config("spark.executor.memory","4g").master("local[*]").getOrCreate();

	        String userId = PropertyConfig.getProperty(ConfigPropertyName.USERNAME);
	        String pwd = PropertyConfig.getProperty(ConfigPropertyName.PASSWORD);
	        String url = PropertyConfig.getProperty(ConfigPropertyName.DB_URL);

	        Properties connectionProperties = new Properties();
	        connectionProperties.put("user",userId);
	        connectionProperties.put("password",pwd);

	        Dataset<org.apache.spark.sql.Row> dataset = spark.read().format("jdbc")
	               .option("url", url).option("user", userId).option("password", pwd).option("dbtable", sql).load();
	        
	        return dataset;
	  }
	
	 public void step1(String manufacturerCode) throws Exception{
	        
	        String historyStartDate = ConfigFromDB.getConfigPropertyFromDB(RebateConstant.HISTORY_START_DATE); //JUN 29 2015
	        String historyEndDate = ConfigFromDB.getConfigPropertyFromDB(RebateConstant.HISTORY_END_DATE);   //JUN 30 2015
	        String firstPriceDate = ConfigFromDB.getConfigPropertyFromDB(RebateConstant.FIRST_PRICE_DATE);   //OCT-23-2006
	        String secondPriceDate = ConfigFromDB.getConfigPropertyFromDB(RebateConstant.SECOND_PRICE_DATE);  //OCT-23-2015
	        String yyyyPriceDate = ConfigFromDB.getConfigPropertyFromDB(RebateConstant.YYYY_PRICE_DATE);
	        
	        String sql1 = "(select DIN_PIN,DT_OF_SERV, ADJUDICATION_DT, PROF_FEE_ALLD,QTY,DRG_CST_ALLD,PROG_ID,PROD_SEL, INTERVENTION_1, " +
	                      "INTERVENTION_2, INTERVENTION_3, INTERVENTION_4, INTERVENTION_5, INTERVENTION_6, INTERVENTION_7, INTERVENTION_8, INTERVENTION_9, INTERVENTION_10 " +
	                      "from CLMHIST " +
	                      "where CURR_STAT = 'P' and Trunc(DT_OF_SERV) >= to_date('" + historyStartDate + "','MM-DD-YYYY') AND " +
	                                   "Trunc(DT_OF_SERV) <= to_date('" + historyEndDate + "','MM-DD-YYYY') AND " +
	                                   "PROG_ID <> 'TP' and DIN_PIN in (select DIN_PIN from SCHEDULE_A where MANUFACTURER_CD = '" + manufacturerCode + "'))";
	        
	        String sql2 = "(select d.INDIVIDUAL_PRICE AS FIRST_PRICE, d.DIN_DESC, r.REC_EFF_DATE,d.REC_CREATE_TIMESTAMP,d.DIN_PIN, d.MANUFACTURER_CD from (" + 
                    " select DIN_PIN,MAX(REC_EFF_DT) as REC_EFF_DATE from DRUG where MANUFACTURER_CD = '" + manufacturerCode+ "' AND " +
                    " Trunc(REC_EFF_DT) < to_date('" + firstPriceDate + "','MM-DD-YYYY') AND REC_INACTIVE_TIMESTAMP is NULL group by DIN_PIN) r " +
               " inner join DRUG d on d.DIN_PIN = r.DIN_PIN and d.REC_EFF_DT = r.REC_EFF_DATE)";
	        
	        String sql3 = "(select d.INDIVIDUAL_PRICE AS SECOND_PRICE, d.DIN_DESC, r.REC_EFF_DATE,d.REC_CREATE_TIMESTAMP,d.DIN_PIN, d.MANUFACTURER_CD from (" + 
                    "select DIN_PIN,MAX(REC_EFF_DT) as REC_EFF_DATE from DRUG where MANUFACTURER_CD = '" + manufacturerCode+ "' AND " +
                    "REC_EFF_DT < to_date('" + secondPriceDate + "','MM-DD-YYYY')  AND REC_INACTIVE_TIMESTAMP is NULL group by DIN_PIN) r " +
               "inner join DRUG d on d.DIN_PIN = r.DIN_PIN and d.REC_EFF_DT = r.REC_EFF_DATE)";
	        
			
			String sql4 = "(select d.INDIVIDUAL_PRICE AS YYYY_PRICE, d.DIN_DESC, r.REC_EFF_DATE,d.REC_CREATE_TIMESTAMP,d.DIN_PIN, d.MANUFACTURER_CD from (" + 
				     "select DIN_PIN,MAX(REC_EFF_DT) as REC_EFF_DATE from DRUG where MANUFACTURER_CD = '" + manufacturerCode+ "' AND " +
				     "REC_EFF_DT < to_date('" + yyyyPriceDate + "','MM-DD-YYYY') AND REC_INACTIVE_TIMESTAMP is NULL group by DIN_PIN) r " +
				"inner join DRUG d on d.DIN_PIN = r.DIN_PIN and d.REC_EFF_DT = r.REC_EFF_DATE)";

			String sql6 = "(select DIN_PIN, DRUG_BENEFIT_PRICE, YYYY_PRICE,STRENGTH,DOSAGE_FORM,GEN_NAME,CLAIM_START_DATE,CLAIM_END_DATE from SCHEDULE_A " +
	  				" where MANUFACTURER_CD = '" + manufacturerCode+ "' AND " +
	  			    "CLAIM_START_DATE < to_date('" + historyStartDate + "','MM-DD-YYYY') AND " +
	  			    "CLAIM_END_DATE > to_date('" + historyStartDate + "','MM-DD-YYYY'))";
			
			
			String dbpSql = "(select p.DIN_PIN, p.PRICE_LIM, p.REC_EFF_DT from ( " +
					"select DIN_PIN,MAX(REC_EFF_DT) as REC_EFF_DATE from PCGLIMIT where MANUFACTURER_CD = '" + manufacturerCode+ "' AND " +
				    "REC_EFF_DT < to_date('" + historyStartDate + "','MM-DD-YYYY') AND REC_INACTIVE_TIMESTAMP is NULL group by DIN_PIN) p1 " +
				    "inner join PCGLIMIT p on p.DIN_PIN = p1.DIN_PIN and p.REC_EFF_DT = p1.REC_EFF_DATE)";

	        

	        try{
	        		// step1 ************************************************************
	               Dataset<org.apache.spark.sql.Row> qualifiedClaims = getDataset(sql1);
	               qualifiedClaims.cache();
	               qualifiedClaims = qualifiedClaims.withColumn("CLAIM_ID", functions.row_number().over(Window.orderBy("DIN_PIN")));                
	               System.out.println("All Qualified Claims for " + manufacturerCode);
	               qualifiedClaims.show();

	               
	               
	               // step2 ************************************************************
	               Dataset<org.apache.spark.sql.Row> rawFirstPrice = getDataset(sql2);
	               rawFirstPrice.cache();
	                Dataset<org.apache.spark.sql.Row> firstPrice = rawFirstPrice.groupBy("DIN_PIN","REC_EFF_DATE").agg(max("REC_CREATE_TIMESTAMP"));
	                firstPrice.cache();
	                firstPrice = firstPrice.withColumnRenamed("max(REC_CREATE_TIMESTAMP)", "REC_CREATE_TIMESTAMP");
	                firstPrice = rawFirstPrice.join(firstPrice,
	             		   						rawFirstPrice.col("REC_CREATE_TIMESTAMP").equalTo(firstPrice.col("REC_CREATE_TIMESTAMP"))
	             		   															     .and(rawFirstPrice.col("DIN_PIN").equalTo(firstPrice.col("DIN_PIN")))
	             		   															     .and(rawFirstPrice.col("REC_EFF_DATE").equalTo(firstPrice.col("REC_EFF_DATE")))
	             		   															     ,"left_semi").orderBy("DIN_PIN");    				
	                System.out.println("First Price of Drug ");
	                firstPrice.show();
      
	               
	               
	               
	             // step3 ************************************************************
	                System.out.println("Second Price of Drug ");
	                Dataset<org.apache.spark.sql.Row> rawSecondPrice = getDataset(sql3); 
	                rawSecondPrice.cache();
	                Dataset<org.apache.spark.sql.Row> secondPrice = rawSecondPrice.groupBy("DIN_PIN","REC_EFF_DATE").agg(max("REC_CREATE_TIMESTAMP"));   
	                secondPrice.cache();
	                secondPrice = secondPrice.withColumnRenamed("max(REC_CREATE_TIMESTAMP)", "REC_CREATE_TIMESTAMP");
	                
	                secondPrice = rawSecondPrice.join(secondPrice,
	             		   						rawSecondPrice.col("REC_CREATE_TIMESTAMP").equalTo(secondPrice.col("REC_CREATE_TIMESTAMP"))
	             		   																  .and(rawSecondPrice.col("DIN_PIN").equalTo(secondPrice.col("DIN_PIN")))
	             		   																  .and(rawSecondPrice.col("REC_EFF_DATE").equalTo(secondPrice.col("REC_EFF_DATE")))
	             		   																  ,"left_semi").orderBy("DIN_PIN");    		   						   					 
	                secondPrice.show(); 

	               
	             // step4 ************************************************************
	                System.out.println("YYYY Price of Drug ");
	                Dataset<org.apache.spark.sql.Row> rawYYYYPrice = getDataset(sql4);  
	                rawYYYYPrice.cache();
	                
	                Dataset<org.apache.spark.sql.Row> yyyyPrice = rawYYYYPrice.groupBy("DIN_PIN","REC_EFF_DATE").agg(max("REC_CREATE_TIMESTAMP")); 
	                yyyyPrice.cache();
	                yyyyPrice = yyyyPrice.withColumnRenamed("max(REC_CREATE_TIMESTAMP)", "REC_CREATE_TIMESTAMP");
	                
	                yyyyPrice = rawYYYYPrice.join(yyyyPrice,
	               		 rawYYYYPrice.col("REC_CREATE_TIMESTAMP").equalTo(yyyyPrice.col("REC_CREATE_TIMESTAMP"))
	   																  .and(rawYYYYPrice.col("DIN_PIN").equalTo(yyyyPrice.col("DIN_PIN")))
	   																  .and(rawYYYYPrice.col("REC_EFF_DATE").equalTo(yyyyPrice.col("REC_EFF_DATE")))
	   																  ,"left_semi").orderBy("DIN_PIN");  
	             		   																 
	         		   						   					 
	                yyyyPrice.show();
   
	               
	               
	             // step5 ************************************************************
	                System.out.println("Join the 3 previously created temporary drug files together");
	              	
	         		Dataset<org.apache.spark.sql.Row> joinedClaimed = secondPrice.select("DIN_PIN","DIN_DESC","SECOND_PRICE","REC_EFF_DATE","REC_CREATE_TIMESTAMP","MANUFACTURER_CD");
	         		joinedClaimed.cache();
	         		firstPrice = firstPrice.withColumnRenamed("DIN_DESC","b.DIN_DESC")
	         							   .withColumnRenamed("REC_EFF_DATE","b.REC_EFF_DATE")
	         							   .withColumnRenamed("REC_CREATE_TIMESTAMP","b.REC_CREATE_TIMESTAMP")
	         							   .withColumnRenamed("MANUFACTURER_CD", "b.MANUFACTURER_CD")
	         							   .withColumnRenamed("DIN_PIN","b_DIN_PIN");
	         		
	         		yyyyPrice = yyyyPrice.withColumnRenamed("DIN_DESC","c.DIN_DESC")
	    					   .withColumnRenamed("REC_EFF_DATE","c.REC_EFF_DATE")
	    					   .withColumnRenamed("REC_CREATE_TIMESTAMP","c.REC_CREATE_TIMESTAMP")
	    					   .withColumnRenamed("MANUFACTURER_CD", "c.MANUFACTURER_CD")
	    					   .withColumnRenamed("DIN_PIN","c_DIN_PIN");

	         		joinedClaimed = joinedClaimed.join(firstPrice,joinedClaimed.col("DIN_PIN").equalTo(firstPrice.col("b_DIN_PIN")),"full_outer")
	         						
	         								 .join(yyyyPrice,joinedClaimed.col("DIN_PIN").equalTo(yyyyPrice.col("c_DIN_PIN")),"full_outer");
	         		
	         		joinedClaimed = joinedClaimed.select("DIN_PIN","DIN_DESC","FIRST_PRICE","SECOND_PRICE","YYYY_PRICE","MANUFACTURER_CD").orderBy("DIN_PIN");
	         		joinedClaimed.show();

	               
	         	// step6 ************************************************************
	         		System.out.println("Define Missing DB(TEMP 04)");
	                 Dataset<org.apache.spark.sql.Row> missingData = getDataset(sql6);
	                 missingData.cache();
	                 missingData = missingData.withColumnRenamed("DRUG_BENEFIT_PRICE", "FIRST_PRICE")
			 				  .withColumnRenamed("DIN_PIN", "m_DIN_PIN");
	                 Dataset<org.apache.spark.sql.Row> dbpData = getDataset(dbpSql).select("DIN_PIN","PRICE_LIM");
	                 dbpData = dbpData.withColumnRenamed("PRICE_LIM", "DBP_LIM_JL115")
	                 		.withColumnRenamed("DIN_PIN", "d_DIN_PIN");
	              
	                 missingData = missingData.join(dbpData,missingData.col("m_DIN_PIN").equalTo(dbpData.col("d_DIN_PIN")));
	                 missingData = missingData.select("m_DIN_PIN","FIRST_PRICE","YYYY_PRICE","STRENGTH","DOSAGE_FORM","GEN_NAME","DBP_LIM_JL115");
	                 
	                 joinedClaimed = joinedClaimed.select("DIN_PIN","DIN_DESC","SECOND_PRICE");
	                 missingData = joinedClaimed.join(missingData,missingData.col("m_DIN_PIN").equalTo(joinedClaimed.col("DIN_PIN")));
	                 missingData = missingData.select("DIN_PIN","DIN_DESC","GEN_NAME","FIRST_PRICE","SECOND_PRICE","YYYY_PRICE","DBP_LIM_JL115","STRENGTH","DOSAGE_FORM").orderBy("DIN_PIN");
	                 missingData.show();	
	                 
	         		
	         	// step8 ************************************************************
	         		
	                 System.out.println("creates a complete file of the initial claims extract and adds all the price components contained in the drug file ---TEMP05 ");
	                 qualifiedClaims = qualifiedClaims.select("CLAIM_ID","DIN_PIN","DRG_CST_ALLD","QTY","PROD_SEL","PROF_FEE_ALLD","INTERVENTION_1","INTERVENTION_2","INTERVENTION_3","INTERVENTION_4",
	           			  "INTERVENTION_5","INTERVENTION_6","INTERVENTION_7","INTERVENTION_8","INTERVENTION_9","INTERVENTION_10");
	                 missingData = missingData.select("DIN_PIN","DIN_DESC","GEN_NAME","STRENGTH","DOSAGE_FORM","FIRST_PRICE","SECOND_PRICE","YYYY_PRICE","DBP_LIM_JL115");
	                 missingData = missingData.withColumnRenamed("DIN_PIN", "b_DIN_PIN");
	           	  Dataset<org.apache.spark.sql.Row> completeClaims = qualifiedClaims.join(missingData,qualifiedClaims.col("DIN_PIN").equalTo(missingData.col("b_DIN_PIN")));
	           	completeClaims.cache();
	           	  completeClaims = completeClaims.select("CLAIM_ID","DIN_PIN","DIN_DESC","GEN_NAME","STRENGTH","DOSAGE_FORM","FIRST_PRICE","SECOND_PRICE","YYYY_PRICE","DBP_LIM_JL115",
	           			  "DRG_CST_ALLD","QTY","PROD_SEL","PROF_FEE_ALLD","INTERVENTION_1","INTERVENTION_2","INTERVENTION_3","INTERVENTION_4","INTERVENTION_5","INTERVENTION_6",
	           			  "INTERVENTION_7","INTERVENTION_8","INTERVENTION_9","INTERVENTION_10").orderBy("DIN_PIN");
	           	  completeClaims.show();
  	
	         	
	           	
	           	
	           	
	           	// step9 ************************************************************
	         		
	           	System.out.println("uses selection criteria to include only claims that meet the requirements for agreement reconciliation ---- TEMP06");
	        	 Dataset<org.apache.spark.sql.Row> conditionalClaims = completeClaims.select("*").where(col("DBP_LIM_JL115").equalTo(col("SECOND_PRICE"))
	        			 																	.or(col("DBP_LIM_JL115").lt(col("SECOND_PRICE"))
	        			 																	.and(col("PROD_SEL").equalTo(1).or(col("INTERVENTION_1").equalTo("MI"))
	        			 																								   .or(col("INTERVENTION_2").equalTo("MI"))
	        			 																								   .or(col("INTERVENTION_3").equalTo("MI"))
	        			 																								   .or(col("INTERVENTION_4").equalTo("MI"))
	        			 																								   .or(col("INTERVENTION_5").equalTo("MI"))
	        			 																								   .or(col("INTERVENTION_6").equalTo("MI")))));
	        	 conditionalClaims.cache();
	        	conditionalClaims.show();	 																									
	
	        	
	        	
	        	// step10 ************************************************************
	        	 System.out.println(" calculates an adjusted quantity ---- TEMP07");
	     
	         	  Dataset<org.apache.spark.sql.Row> adjustedQuantity = conditionalClaims.select("CLAIM_ID","DIN_PIN","DIN_DESC","GEN_NAME","STRENGTH","DOSAGE_FORM","FIRST_PRICE","SECOND_PRICE",
	         			  "YYYY_PRICE","DBP_LIM_JL115","DRG_CST_ALLD","QTY","PROD_SEL","PROF_FEE_ALLD","INTERVENTION_1","INTERVENTION_2","INTERVENTION_3","INTERVENTION_4",
	         			  "INTERVENTION_5","INTERVENTION_6");
	         	 adjustedQuantity.cache();
	       	  
	         	adjustedQuantity = adjustedQuantity.withColumn("ADJ_QTY", bround(col("DRG_CST_ALLD").divide(col("SECOND_PRICE")),1));
	         	adjustedQuantity = adjustedQuantity.withColumn("FNL_QTY",col("ADJ_QTY"));  	
	         	adjustedQuantity = adjustedQuantity.withColumn("FNL_QTY",functions.when(col("FNL_QTY").gt(col("QTY")),col("QTY")).otherwise(col("ADJ_QTY")));															   
	         	adjustedQuantity.show();	

	         		
	         	// step11 ************************************************************
	         		
	         	 System.out.println(" calculates the volume discount for each individual claim ---- TEMP08");
	         	  Dataset<org.apache.spark.sql.Row> drugRebate = adjustedQuantity.select("*");
	         	 drugRebate.cache();
	         	  drugRebate = drugRebate.withColumn("IS_TWO_PRICE", functions.when(col("YYYY_PRICE").isNull(), "Y").otherwise("N"));
	         	 
	         	  drugRebate = drugRebate.withColumn("CHQ_BACK",functions.when(col("IS_TWO_PRICE").equalTo("Y").and(col("DRG_CST_ALLD").lt(1000)), (col("SECOND_PRICE").minus(col("FIRST_PRICE"))).multiply(col("FNL_QTY")).multiply(1.08))
	         			  													.otherwise(functions.when(col("IS_TWO_PRICE").equalTo("Y").and(col("DRG_CST_ALLD").gt(1000)), (col("SECOND_PRICE").minus(col("FIRST_PRICE"))).multiply(col("FNL_QTY")).multiply(1.06))
	         			  															.otherwise(functions.when(col("IS_TWO_PRICE").equalTo("N").and(col("DRG_CST_ALLD").lt(1000)), (col("SECOND_PRICE").minus(col("FIRST_PRICE"))).multiply(col("FNL_QTY")).plus((col("SECOND_PRICE").minus(col("YYYY_PRICE"))).multiply(col("FNL_QTY")).multiply(0.08)))
	         			  																	.otherwise(functions.when(col("IS_TWO_PRICE").equalTo("N").and(col("DRG_CST_ALLD").gt(1000)), (col("SECOND_PRICE").minus(col("FIRST_PRICE"))).multiply(col("FNL_QTY")).plus((col("SECOND_PRICE").minus(col("YYYY_PRICE"))).multiply(col("FNL_QTY")).multiply(0.06)))))));
	         			  				 
	         	 drugRebate = drugRebate.withColumn("CHQ_BACK",bround(col("CHQ_BACK"),4));
	         	  
	         	  
	         	  drugRebate.show();
	         	  
	         	  
	         	// step12************************************************************
	               
	         	 System.out.println(" creates a new file with the volume discount amount included for each individual claim ---- TEMP09");
	         	  
	         	  Dataset<org.apache.spark.sql.Row> a = drugRebate.groupBy(col("DIN_PIN")).sum("CHQ_BACK","FNL_QTY","ADJ_QTY","QTY","DRG_CST_ALLD").distinct();
	         	  a.cache();
	         	  a = a.withColumnRenamed("sum(CHQ_BACK)", "CHQ_BACK")
	         		   .withColumnRenamed("sum(FNL_QTY)", "FNL_QTY")
	         		   .withColumnRenamed("sum(ADJ_QTY)", "ADJ_QTY")
	         		   .withColumnRenamed("sum(QTY)", "QTY")
	         		   .withColumnRenamed("sum(DRG_CST_ALLD)", "DRG_CST_ALLD")
	         	       .withColumnRenamed("DIN_PIN","a_DIN_PIN");  	 
	         	  Dataset<org.apache.spark.sql.Row> temp09 = drugRebate.select("DIN_PIN","DIN_DESC","GEN_NAME","STRENGTH","DOSAGE_FORM","FIRST_PRICE","SECOND_PRICE","YYYY_PRICE","DBP_LIM_JL115","IS_TWO_PRICE").distinct();
	         	 temp09.cache();
	         	  temp09 = temp09.join(a,a.col("a_DIN_PIN").equalTo(temp09.col("DIN_PIN")),"full_outer").orderBy("DIN_PIN");
	         	  temp09 = temp09.drop(col("a_DIN_PIN"));		
	         	  temp09.show();
	               
	               
	         	// step13***********************************************************  
	               
	         	 System.out.println("  joins the summarized data to the drug file that was created earlier. ---- TEMP11");
	         	  Dataset<org.apache.spark.sql.Row> rebateSummary = drugRebate.select("*");
	         	 rebateSummary.cache();
	         	  rebateSummary = rebateSummary.drop(col("INTERVENTION_1"))
	         			  					   .drop(col("INTERVENTION_2"))
	         			  					   .drop(col("INTERVENTION_3"))
	         			  					   .drop(col("INTERVENTION_4"))
	         			  					   .drop(col("INTERVENTION_5"))
	         			  					   .drop(col("INTERVENTION_6"))
	         			  					   .drop(col("CLAIM_ID"));
	         	  
	         			  	
	         	  Dataset<org.apache.spark.sql.Row> distinctDin_Pin = rebateSummary.select("DIN_PIN").distinct();
	         	 distinctDin_Pin.cache();
	         	  rebateSummary = rebateSummary.dropDuplicates("DIN_PIN");
	         	  
	         	  String sql = "(select DIN_PIN, YYYY_PRICE from SCHEDULE_A)";
	         	  Dataset<org.apache.spark.sql.Row> c = getDataset(sql);
	           	 c.cache();
	         	  c = c.join(distinctDin_Pin,c.col("DIN_PIN").equalTo(distinctDin_Pin.col("DIN_PIN")),"left_anti").orderBy("DIN_PIN");
	         	  
	         	  
	         	  c = c.withColumn("IS_TWO_PRICE", functions.when(col("YYYY_PRICE").isNull(), "Y").otherwise("N"));
	         	  c = c.drop(col("YYYY_PRICE"));
	         	  
	         	  c = c.withColumn("DIN_DESC", functions.lit(null).cast(DataTypes.StringType)).withColumn("GEN_NAME",functions.lit(null).cast(DataTypes.StringType))
	         		   .withColumn("STRENGTH",functions.lit(null).cast(DataTypes.StringType)).withColumn("DOSAGE_FORM",functions.lit(null).cast(DataTypes.StringType))
	         		   .withColumn("FIRST_PRICE",functions.lit(null).cast(DataTypes.StringType)).withColumn("SECOND_PRICE",functions.lit(null).cast(DataTypes.StringType))
	         		   .withColumn("YYYY_PRICE",functions.lit(null).cast(DataTypes.StringType)).withColumn("DBP_LIM_JL115",functions.lit(null).cast(DataTypes.StringType))
	         		   .withColumn("CHQ_BACK",functions.lit(null).cast(DataTypes.StringType)).withColumn("ADJ_QTY",functions.lit(null).cast(DataTypes.StringType))
	         		   .withColumn("FNL_QTY",functions.lit(null).cast(DataTypes.StringType)).withColumn("QTY",functions.lit(null).cast(DataTypes.StringType))
	         		   .withColumn("DRG_CST_ALLD",functions.lit(null).cast(DataTypes.StringType))
	         		   .withColumn("PROD_SEL",functions.lit(null).cast(DataTypes.StringType)).withColumn("PROF_FEE_ALLD",functions.lit(null).cast(DataTypes.StringType));
	         	  c = c.select("DIN_PIN","DIN_DESC","GEN_NAME","STRENGTH","DOSAGE_FORM","FIRST_PRICE","SECOND_PRICE","YYYY_PRICE","DBP_LIM_JL115","DRG_CST_ALLD"
	         			  ,"QTY","PROD_SEL","PROF_FEE_ALLD","ADJ_QTY","FNL_QTY","IS_TWO_PRICE","CHQ_BACK");
	         	  
	         	  rebateSummary = rebateSummary.union(c);		
	         	  rebateSummary = rebateSummary.withColumn("CLAIM_ID", functions.row_number().over(Window.orderBy("DIN_PIN")));	 
	         	  rebateSummary = rebateSummary.select("CLAIM_ID","DIN_PIN","DIN_DESC","GEN_NAME","STRENGTH","DOSAGE_FORM","FIRST_PRICE","SECOND_PRICE","YYYY_PRICE","DBP_LIM_JL115","DRG_CST_ALLD"
	         			  ,"QTY","PROD_SEL","PROF_FEE_ALLD","ADJ_QTY","FNL_QTY","IS_TWO_PRICE","CHQ_BACK");
	         	  rebateSummary.show();
	               
	        }catch(Exception e){
	               e.printStackTrace();
	               throw e;
	        }finally{
	               
	        }
	  }	
	

}
