package ca.on.moh.idms.engine;


import gov.moh.app.db.DBConnectionManager;
import gov.moh.config.ConfigFromDB;
import gov.moh.config.ConfigPropertyName;
import gov.moh.config.PropertyConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.spark.sql.Dataset;

import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;

import org.apache.spark.sql.functions;
import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.max;
import static org.apache.spark.sql.functions.bround;
import org.apache.spark.sql.expressions.Window;
import ca.on.moh.idms.util.RebateConstant;


public class test {
	

	static String appRoot = System.getProperty("user.dir");
	SparkSession spark = SparkSession
		      .builder()
		      .appName("RebateEngineSpark")
		      .master("local[*]")	      
		      .getOrCreate();
	
	
	
	public static void main (String [] args) throws Exception {
    	Logger.getLogger("org").setLevel(Level.ERROR);
        System.out.println("Calculation engine started ...... ");
        
        PropertyConfig.setPropertyPath(appRoot + "\\conf\\system.properties");
        System.setProperty("hadoop.home.dir", "C:\\Spark\\spark-2.3.0-bin-hadoop2.7\\spark-2.3.0-bin-hadoop2.7");
        
        test calculator = new test();
        String manufacturerCode = "LEO";
             
      
        try{
               long startTime = System.currentTimeMillis();
               
               String filepathAndName = "C:\\TEMP\\IDMS\\data";
               
               //
               String username = PropertyConfig.getProperty("app.config.db.username2");
       			String pwd = PropertyConfig.getProperty("app.config.db.password2");
       			String url = PropertyConfig.getProperty("app.config.db.dbUrl2");
       			Connection conn1 = DBConnectionManager.getManager().getConnection();
       			Connection conn2 = DBConnectionManager.getManager().getConnection(username,pwd,url);
              //

               System.out.println("step1_1");
               calculator.step1_1(manufacturerCode);
               long endTime = System.currentTimeMillis();
               long timeSpent = (endTime - startTime)/1000;
               System.out.println("Total Time: " + timeSpent);
               
               System.out.println("step1_2");
               startTime = System.currentTimeMillis();                  
               calculator.step1_2(manufacturerCode);
               endTime = System.currentTimeMillis();
               timeSpent = (endTime - startTime)/1000;
               System.out.println("Total Time: " + timeSpent);
                             
               System.out.println("non-spark");
               startTime = System.currentTimeMillis();
               RebateCalculator calculatorJava = new RebateCalculator();
               calculatorJava.calculateRebate(manufacturerCode, conn1, conn2, filepathAndName);              
               endTime = System.currentTimeMillis();
               timeSpent = (endTime - startTime)/1000;
               System.out.println("Total Time: " + timeSpent);
                           
        }catch(Exception e){
               e.printStackTrace();
        }finally{	
              
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

	 public void step1_1(String manufacturerCode) throws Exception{
	        
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
	               Dataset<org.apache.spark.sql.Row> qualifiedClaims = getDataset(sql1).cache();
	               
	               qualifiedClaims = qualifiedClaims.withColumn("CLAIM_ID", functions.row_number().over(Window.orderBy("DIN_PIN")));                
	               //System.out.println("All Qualified Claims for " + manufacturerCode);
	               qualifiedClaims.show();

		

	               
	               
	               // step2 ************************************************************
	               Dataset<org.apache.spark.sql.Row> rawFirstPrice = getDataset(sql2).cache();
	               
	                Dataset<org.apache.spark.sql.Row> firstPrice = rawFirstPrice.groupBy("DIN_PIN","REC_EFF_DATE").agg(max("REC_CREATE_TIMESTAMP"))
	                												.withColumnRenamed("max(REC_CREATE_TIMESTAMP)", "REC_CREATE_TIMESTAMP").cache();
	               
	                firstPrice = rawFirstPrice.join(firstPrice,
	             		   						rawFirstPrice.col("REC_CREATE_TIMESTAMP").equalTo(firstPrice.col("REC_CREATE_TIMESTAMP"))
	             		   															     .and(rawFirstPrice.col("DIN_PIN").equalTo(firstPrice.col("DIN_PIN")))
	             		   															     .and(rawFirstPrice.col("REC_EFF_DATE").equalTo(firstPrice.col("REC_EFF_DATE")))
	             		   															     ,"left_semi").orderBy("DIN_PIN");    				
	                //System.out.println("First Price of Drug ");
	                //firstPrice.show();
   	               
	             // step3 ************************************************************
	                //System.out.println("Second Price of Drug ");
	                Dataset<org.apache.spark.sql.Row> rawSecondPrice = getDataset(sql3).cache();	                
	                Dataset<org.apache.spark.sql.Row> secondPrice = rawSecondPrice.groupBy("DIN_PIN","REC_EFF_DATE").agg(max("REC_CREATE_TIMESTAMP"))
	                		.withColumnRenamed("max(REC_CREATE_TIMESTAMP)", "REC_CREATE_TIMESTAMP").cache();                        	                
	                secondPrice = rawSecondPrice.join(secondPrice,
	             		   						rawSecondPrice.col("REC_CREATE_TIMESTAMP").equalTo(secondPrice.col("REC_CREATE_TIMESTAMP"))
	             		   																  .and(rawSecondPrice.col("DIN_PIN").equalTo(secondPrice.col("DIN_PIN")))
	             		   																  .and(rawSecondPrice.col("REC_EFF_DATE").equalTo(secondPrice.col("REC_EFF_DATE")))
	             		   																  ,"left_semi").orderBy("DIN_PIN");    		   						   					 
	               // secondPrice.show(); 

	               
	             // step4 ************************************************************
	                //System.out.println("YYYY Price of Drug ");
	                Dataset<org.apache.spark.sql.Row> rawYYYYPrice = getDataset(sql4).cache();	                
	                Dataset<org.apache.spark.sql.Row> yyyyPrice = rawYYYYPrice.groupBy("DIN_PIN","REC_EFF_DATE").agg(max("REC_CREATE_TIMESTAMP"))
	                		.withColumnRenamed("max(REC_CREATE_TIMESTAMP)", "REC_CREATE_TIMESTAMP").cache();
	                               
	                yyyyPrice = rawYYYYPrice.join(yyyyPrice,
	               		 rawYYYYPrice.col("REC_CREATE_TIMESTAMP").equalTo(yyyyPrice.col("REC_CREATE_TIMESTAMP"))
	   																  .and(rawYYYYPrice.col("DIN_PIN").equalTo(yyyyPrice.col("DIN_PIN")))
	   																  .and(rawYYYYPrice.col("REC_EFF_DATE").equalTo(yyyyPrice.col("REC_EFF_DATE")))
	   																  ,"left_semi").orderBy("DIN_PIN");  
    		   						   					 
	               // yyyyPrice.show();

	             // step5 ************************************************************
	                //System.out.println("Join the 3 previously created temporary drug files together");	              	
	         		Dataset<org.apache.spark.sql.Row> joinedClaimed = secondPrice.select("DIN_PIN","DIN_DESC","SECOND_PRICE","REC_EFF_DATE","REC_CREATE_TIMESTAMP","MANUFACTURER_CD").cache();
	         		
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
	         								     .join(yyyyPrice,joinedClaimed.col("DIN_PIN").equalTo(yyyyPrice.col("c_DIN_PIN")),"full_outer")
	         								     .select("DIN_PIN","DIN_DESC","FIRST_PRICE","SECOND_PRICE","YYYY_PRICE","MANUFACTURER_CD").orderBy("DIN_PIN");
	         		
	         		//joinedClaimed.show();

	               
	         	// step6 ************************************************************
	         		//System.out.println("Define Missing DB(TEMP 04)");
	                 Dataset<org.apache.spark.sql.Row> missingData = getDataset(sql6)
	                		 										.withColumnRenamed("DRUG_BENEFIT_PRICE", "FIRST_PRICE")
	                		 										.withColumnRenamed("DIN_PIN", "m_DIN_PIN").cache();
	                 	                
	                 Dataset<org.apache.spark.sql.Row> dbpData = getDataset(dbpSql)
	                		 									.select("DIN_PIN","PRICE_LIM")
	                		 									.withColumnRenamed("PRICE_LIM", "DBP_LIM_JL115")
	                		 									.withColumnRenamed("DIN_PIN", "d_DIN_PIN").cache();
	                 
	                 joinedClaimed = joinedClaimed.select("DIN_PIN","DIN_DESC","SECOND_PRICE");
	                 missingData = missingData.join(dbpData,missingData.col("m_DIN_PIN").equalTo(dbpData.col("d_DIN_PIN")))
	                		 				  .select("m_DIN_PIN","FIRST_PRICE","YYYY_PRICE","STRENGTH","DOSAGE_FORM","GEN_NAME","DBP_LIM_JL115");
	                 	               	                 
	                 missingData = joinedClaimed.join(missingData,missingData.col("m_DIN_PIN").equalTo(joinedClaimed.col("DIN_PIN")))
	                		 					.select("DIN_PIN","DIN_DESC","GEN_NAME","FIRST_PRICE","SECOND_PRICE","YYYY_PRICE","DBP_LIM_JL115","STRENGTH","DOSAGE_FORM").orderBy("DIN_PIN");
	                 
	                 //missingData.show();	
	                 
	                 	
	         	// step8 ************************************************************
	         		
	                // System.out.println("creates a complete file of the initial claims extract and adds all the price components contained in the drug file ---TEMP05 ");
	                 qualifiedClaims = qualifiedClaims.select("CLAIM_ID","DIN_PIN","DRG_CST_ALLD","QTY","PROD_SEL","PROF_FEE_ALLD","INTERVENTION_1","INTERVENTION_2","INTERVENTION_3","INTERVENTION_4",
	           			  "INTERVENTION_5","INTERVENTION_6","INTERVENTION_7","INTERVENTION_8","INTERVENTION_9","INTERVENTION_10");
	                 missingData = missingData.select("DIN_PIN","DIN_DESC","GEN_NAME","STRENGTH","DOSAGE_FORM","FIRST_PRICE","SECOND_PRICE","YYYY_PRICE","DBP_LIM_JL115")
	                		 				  .withColumnRenamed("DIN_PIN", "b_DIN_PIN");
	           	  	 Dataset<org.apache.spark.sql.Row> completeClaims = qualifiedClaims.join(missingData,qualifiedClaims.col("DIN_PIN").equalTo(missingData.col("b_DIN_PIN")))
	           	  			 											.select("CLAIM_ID","DIN_PIN","DIN_DESC","GEN_NAME","STRENGTH","DOSAGE_FORM","FIRST_PRICE","SECOND_PRICE","YYYY_PRICE","DBP_LIM_JL115",
	           	  			 														"DRG_CST_ALLD","QTY","PROD_SEL","PROF_FEE_ALLD","INTERVENTION_1","INTERVENTION_2","INTERVENTION_3","INTERVENTION_4","INTERVENTION_5","INTERVENTION_6",
	           	  			 														"INTERVENTION_7","INTERVENTION_8","INTERVENTION_9","INTERVENTION_10").orderBy("DIN_PIN").cache();
        	
	           	  
	           	  //completeClaims.show();
	

	           	// step9 ************************************************************
	         		
	           	//System.out.println("uses selection criteria to include only claims that meet the requirements for agreement reconciliation ---- TEMP06");
	        	 Dataset<org.apache.spark.sql.Row> conditionalClaims = completeClaims.select("*").where(col("DBP_LIM_JL115").equalTo(col("SECOND_PRICE"))
	        			 																	.or(col("DBP_LIM_JL115").lt(col("SECOND_PRICE"))
	        			 																	.and(col("PROD_SEL").equalTo(1).or(col("INTERVENTION_1").equalTo("MI"))
	        			 																								   .or(col("INTERVENTION_2").equalTo("MI"))
	        			 																								   .or(col("INTERVENTION_3").equalTo("MI"))
	        			 																								   .or(col("INTERVENTION_4").equalTo("MI"))
	        			 																								   .or(col("INTERVENTION_5").equalTo("MI"))
	        			 																								   .or(col("INTERVENTION_6").equalTo("MI")))));
	        	 conditionalClaims.cache();
	        	//conditionalClaims.show();	 																									
	
	        	
	        	
	        	// step10 ************************************************************
	        	 //System.out.println(" calculates an adjusted quantity ---- TEMP07");
	     
	         	  Dataset<org.apache.spark.sql.Row> adjustedQuantity = conditionalClaims.select("CLAIM_ID","DIN_PIN","DIN_DESC","GEN_NAME","STRENGTH","DOSAGE_FORM","FIRST_PRICE","SECOND_PRICE",
	         			  "YYYY_PRICE","DBP_LIM_JL115","DRG_CST_ALLD","QTY","PROD_SEL","PROF_FEE_ALLD","INTERVENTION_1","INTERVENTION_2","INTERVENTION_3","INTERVENTION_4",
	         			  "INTERVENTION_5","INTERVENTION_6").cache();

	         	adjustedQuantity = adjustedQuantity.withColumn("ADJ_QTY", bround(col("DRG_CST_ALLD").divide(col("SECOND_PRICE")),1))
	         									   .withColumn("FNL_QTY",col("ADJ_QTY"))
	         									   .withColumn("FNL_QTY",functions.when(col("FNL_QTY").gt(col("QTY")),col("QTY")).otherwise(col("ADJ_QTY")));
	         															   
	         	//adjustedQuantity.show();	

	         		
	         	// step11 ************************************************************
	         		
	         	 //System.out.println(" calculates the volume discount for each individual claim ---- TEMP08");
	         	  Dataset<org.apache.spark.sql.Row> drugRebate = adjustedQuantity.select("*")
	         			 .withColumn("IS_TWO_PRICE", functions.when(col("YYYY_PRICE").isNull(), "Y").otherwise("N"))
	         			 .withColumn("CHQ_BACK",functions.when(col("IS_TWO_PRICE").equalTo("Y").and(col("DRG_CST_ALLD").lt(1000)), (col("SECOND_PRICE").minus(col("FIRST_PRICE"))).multiply(col("FNL_QTY")).multiply(1.08))
									.otherwise(functions.when(col("IS_TWO_PRICE").equalTo("Y").and(col("DRG_CST_ALLD").gt(1000)), (col("SECOND_PRICE").minus(col("FIRST_PRICE"))).multiply(col("FNL_QTY")).multiply(1.06))
											.otherwise(functions.when(col("IS_TWO_PRICE").equalTo("N").and(col("DRG_CST_ALLD").lt(1000)), (col("SECOND_PRICE").minus(col("FIRST_PRICE"))).multiply(col("FNL_QTY")).plus((col("SECOND_PRICE").minus(col("YYYY_PRICE"))).multiply(col("FNL_QTY")).multiply(0.08)))
													.otherwise(functions.when(col("IS_TWO_PRICE").equalTo("N").and(col("DRG_CST_ALLD").gt(1000)), (col("SECOND_PRICE").minus(col("FIRST_PRICE"))).multiply(col("FNL_QTY")).plus((col("SECOND_PRICE").minus(col("YYYY_PRICE"))).multiply(col("FNL_QTY")).multiply(0.06)))))))
	         			 .withColumn("CHQ_BACK",bround(col("CHQ_BACK"),4)).cache();     	  

	         	  //drugRebate.show();
	         	           	  
	         	// step12************************************************************
	               
	         	// System.out.println(" creates a new file with the volume discount amount included for each individual claim ---- TEMP09");
	         	  
	         	  Dataset<org.apache.spark.sql.Row> a = drugRebate.groupBy(col("DIN_PIN")).sum("CHQ_BACK","FNL_QTY","ADJ_QTY","QTY","DRG_CST_ALLD").distinct()
	         			  											.withColumnRenamed("sum(CHQ_BACK)", "CHQ_BACK")
	         			  											.withColumnRenamed("sum(FNL_QTY)", "FNL_QTY")
	         			  											.withColumnRenamed("sum(ADJ_QTY)", "ADJ_QTY")
	         			  											.withColumnRenamed("sum(QTY)", "QTY")
	         			  											.withColumnRenamed("sum(DRG_CST_ALLD)", "DRG_CST_ALLD")
	         			  											.withColumnRenamed("DIN_PIN","a_DIN_PIN").cache();
	         	  	 
	         	  Dataset<org.apache.spark.sql.Row> temp09 = drugRebate.select("DIN_PIN","DIN_DESC","GEN_NAME","STRENGTH","DOSAGE_FORM","FIRST_PRICE","SECOND_PRICE","YYYY_PRICE","DBP_LIM_JL115","IS_TWO_PRICE").distinct().cache();
	         			  		         	 
	         	  temp09 = temp09.join(a,a.col("a_DIN_PIN").equalTo(temp09.col("DIN_PIN")),"full_outer").orderBy("DIN_PIN")
	         	  				 .drop(col("a_DIN_PIN"));		
	         	  //temp09.show();
	               
	               
	         	// step13***********************************************************  
	               
	         	 //System.out.println("  joins the summarized data to the drug file that was created earlier. ---- TEMP11");
	         	  Dataset<org.apache.spark.sql.Row> rebateSummary = temp09.select("*")
	         			    											  .drop(col("CLAIM_ID")).cache();
		  	
	         	  Dataset<org.apache.spark.sql.Row> distinctDin_Pin = rebateSummary.select("DIN_PIN").distinct().cache();	         	  
	         	  rebateSummary = rebateSummary.dropDuplicates("DIN_PIN");
	         	  
	         	  String sql = "(select DIN_PIN, YYYY_PRICE from SCHEDULE_A)";
	         	  Dataset<org.apache.spark.sql.Row> c = getDataset(sql).cache();
	           	 
	         	  c = c.join(distinctDin_Pin,c.col("DIN_PIN").equalTo(distinctDin_Pin.col("DIN_PIN")),"left_anti").orderBy("DIN_PIN")
	         		   .withColumn("IS_TWO_PRICE", functions.when(col("YYYY_PRICE").isNull(), "Y").otherwise("N"))
	         		   .drop(col("YYYY_PRICE"))
	         		   .withColumn("DIN_DESC", functions.lit(null).cast(DataTypes.StringType)).withColumn("GEN_NAME",functions.lit(null).cast(DataTypes.StringType))
	         		   .withColumn("STRENGTH",functions.lit(null).cast(DataTypes.StringType)).withColumn("DOSAGE_FORM",functions.lit(null).cast(DataTypes.StringType))
	         		   .withColumn("FIRST_PRICE",functions.lit(null).cast(DataTypes.StringType)).withColumn("SECOND_PRICE",functions.lit(null).cast(DataTypes.StringType))
	         		   .withColumn("YYYY_PRICE",functions.lit(null).cast(DataTypes.StringType)).withColumn("DBP_LIM_JL115",functions.lit(null).cast(DataTypes.StringType))
	         		   .withColumn("CHQ_BACK",functions.lit(null).cast(DataTypes.StringType)).withColumn("ADJ_QTY",functions.lit(null).cast(DataTypes.StringType))
	         		   .withColumn("FNL_QTY",functions.lit(null).cast(DataTypes.StringType)).withColumn("QTY",functions.lit(null).cast(DataTypes.StringType))
	         		   .withColumn("DRG_CST_ALLD",functions.lit(null).cast(DataTypes.StringType))
	         		   .select("DIN_PIN","DIN_DESC","GEN_NAME","STRENGTH","DOSAGE_FORM","FIRST_PRICE","SECOND_PRICE","YYYY_PRICE","DBP_LIM_JL115","DRG_CST_ALLD"
		         			  ,"QTY","ADJ_QTY","FNL_QTY","IS_TWO_PRICE","CHQ_BACK");
  	  
	         	  rebateSummary = rebateSummary.union(c)
	         			  					   .withColumn("CLAIM_ID", functions.row_number().over(Window.orderBy("DIN_PIN")))
	         			  					   .withColumn("FIRST_PRICE", col("FIRST_PRICE").cast(DataTypes.DoubleType))
	         			  					   .withColumn("SECOND_PRICE", col("SECOND_PRICE").cast(DataTypes.DoubleType))
	         			  					   .withColumn("YYYY_PRICE", col("YYYY_PRICE").cast(DataTypes.DoubleType))
	         			  					   .withColumn("FNL_QTY", col("FNL_QTY").cast(DataTypes.DoubleType))
	         			  					   .withColumn("CHQ_BACK", col("CHQ_BACK").cast(DataTypes.DoubleType))
	         			  					   .withColumn("DRG_CST_ALLD", col("DRG_CST_ALLD").cast(DataTypes.DoubleType))
	         			  					   .select("CLAIM_ID","DIN_PIN","DIN_DESC","GEN_NAME","STRENGTH","DOSAGE_FORM","FIRST_PRICE","SECOND_PRICE","YYYY_PRICE","DBP_LIM_JL115","DRG_CST_ALLD"
	         			 	         			  ,"QTY","ADJ_QTY","FNL_QTY","IS_TWO_PRICE","CHQ_BACK");
	         			  					
	         	  //rebateSummary.show();
	         	  
	         	  
	         	// generate excel form
	         	String path = "C:\\TEMP\\IDMS\\data";
	         	 
	         	File file = new File(path); 
	        	if(!file.exists()){
	        		file.mkdirs();
	        	}
	         	 String filepathAndName = path + "\\Rebate_Summary_" + manufacturerCode + "_" + new Date(System.currentTimeMillis()).toString() + ".xlsx";
	         	 XSSFWorkbook workbook = new XSSFWorkbook();
	         	 FileOutputStream outputStream = null;
	         	 // sheet1
	         	 Dataset<org.apache.spark.sql.Row> rebateExcel1 = rebateSummary.select("DIN_PIN","DIN_DESC","STRENGTH","DOSAGE_FORM","DRG_CST_ALLD","FIRST_PRICE",
	         			 "SECOND_PRICE","YYYY_PRICE","FNL_QTY","CHQ_BACK").where(col("IS_TWO_PRICE")
	         					 .equalTo("Y")).orderBy(col("DIN_PIN"));
	         	rebateExcel1.cache();
	         	
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
	            
	            List<org.apache.spark.sql.Row> dataRows = rebateExcel1.collectAsList();
	            for(org.apache.spark.sql.Row sparkRow1: dataRows) {
	            	Row excelRow = sheet1.createRow(rowNum++);
	            	colNum = 0;
	            	String dinPin = sparkRow1.getString(0);
	            	String brandName = sparkRow1.getString(1);
	            	String strength = sparkRow1.getString(2);
	            	String dosageForm = sparkRow1.getString(3);
	            	double totalDrugCostPaid = sparkRow1.getDouble(4);
	            	double formerDBP = sparkRow1.getDouble(5);
	            	double currentDBP = sparkRow1.getDouble(6);
	            	double quantity = sparkRow1.getDouble(8);
	            	double volumeDiscouny = sparkRow1.getDouble(9);
	            	
		            Cell cell1 = excelRow.createCell(colNum++);
		            cell1.setCellValue(dinPin);
		            Cell cell2 = excelRow.createCell(colNum++);
		            cell2.setCellValue(brandName);
		            Cell cell3 = excelRow.createCell(colNum++);
		            cell3.setCellValue(strength);
		            Cell cell4 = excelRow.createCell(colNum++);
		            cell4.setCellValue(dosageForm);
		            Cell cell5 = excelRow.createCell(colNum++);
		            cell5.setCellValue(manufacturerCode);
		            Cell cell6 = excelRow.createCell(colNum++);
		            cell6.setCellValue(totalDrugCostPaid);
		            Cell cell7 = excelRow.createCell(colNum++);
		            cell7.setCellValue(formerDBP);
		            Cell cell8 = excelRow.createCell(colNum++);
		            cell8.setCellValue(currentDBP);
		            Cell cell9 = excelRow.createCell(colNum++);
		            cell9.setCellValue(quantity);
		            Cell cell10 = excelRow.createCell(colNum++);
		            cell10.setCellValue(volumeDiscouny);
		            
		            twoPriceSubTotal += volumeDiscouny;	
	            }
	            
	            Row lastRow1 = sheet1.createRow(rowNum++);
	            Cell cellSubTotalLabel = lastRow1.createCell(colNum-2);
	            cellSubTotalLabel.setCellValue("Subtotal");
	            Cell cellSubTotalValue = lastRow1.createCell(colNum-1);
	            cellSubTotalValue.setCellValue(twoPriceSubTotal);
	            
	            
	            
	         // sheet2
	            XSSFSheet sheet2 = workbook.createSheet("Three Price DIN");
	            Dataset<org.apache.spark.sql.Row> rebateExcel2 = rebateSummary.select("DIN_PIN","DIN_DESC","STRENGTH","DOSAGE_FORM","DRG_CST_ALLD","FIRST_PRICE",
	         			 "SECOND_PRICE","YYYY_PRICE","FNL_QTY","CHQ_BACK").where(col("IS_TWO_PRICE")
	         					 .equalTo("N")).orderBy(col("DIN_PIN"));
	            rebateExcel2.cache();
	            int rowNum2 = 0;
	            Row row2 = sheet2.createRow(rowNum2++);
	            int colNum2 = 0;
	            Cell cell21 = row2.createCell(colNum2++);
	            cell21.setCellValue("DIN/PIN");
	            Cell cell22 = row2.createCell(colNum2++);
	            cell22.setCellValue("Brand Name");
	            Cell cell23 = row2.createCell(colNum2++);
	            cell23.setCellValue("Strength");
	            Cell cell24 = row2.createCell(colNum2++);
	            cell24.setCellValue("Dosage Form");  
	            Cell cell25 = row2.createCell(colNum2++);  
	            cell25.setCellValue("Manufacturer Code");
	            Cell cell26 = row2.createCell(colNum2++);
	            cell26.setCellValue("Total Drug Cost Paid");
	            Cell cell27 = row2.createCell(colNum2++);
	            cell27.setCellValue("Former DBP (B)");
	            Cell cell28 = row2.createCell(colNum2++);
	            cell28.setCellValue("Current DBP (A)");
	            Cell cell29 = row2.createCell(colNum2++);
	            cell29.setCellValue("Quantity");
	            Cell cell210 = row2.createCell(colNum2++);
	            cell210.setCellValue("Volume Discount");
	            double threePriceSubTotal = 0;
	            
	            List<org.apache.spark.sql.Row> dataRows2 = rebateExcel2.collectAsList();
	            for(org.apache.spark.sql.Row sparkRow2: dataRows2) {
	            	Row excelRow = sheet2.createRow(rowNum2++);
	            	colNum = 0;
	            	String dinPin = sparkRow2.getString(0);
	            	String brandName = sparkRow2.getString(1);
	            	String strength = sparkRow2.getString(2);
	            	String dosageForm = sparkRow2.getString(3);
	            	double totalDrugCostPaid = sparkRow2.getDouble(4);
	            	double formerDBP = sparkRow2.getDouble(5);
	            	double currentDBP = sparkRow2.getDouble(6);
	            	double quantity = sparkRow2.getDouble(8);
	            	double volumeDiscouny = sparkRow2.getDouble(9);
	            	
		            Cell cell1 = excelRow.createCell(colNum++);
		            cell1.setCellValue(dinPin);
		            Cell cell2 = excelRow.createCell(colNum++);
		            cell2.setCellValue(brandName);
		            Cell cell3 = excelRow.createCell(colNum++);
		            cell3.setCellValue(strength);
		            Cell cell4 = excelRow.createCell(colNum++);
		            cell4.setCellValue(dosageForm);
		            Cell cell5 = excelRow.createCell(colNum++);
		            cell5.setCellValue(manufacturerCode);
		            Cell cell6 = excelRow.createCell(colNum++);
		            cell6.setCellValue(totalDrugCostPaid);
		            Cell cell7 = excelRow.createCell(colNum++);
		            cell7.setCellValue(formerDBP);
		            Cell cell8 = excelRow.createCell(colNum++);
		            cell8.setCellValue(currentDBP);
		            Cell cell9 = excelRow.createCell(colNum++);
		            cell9.setCellValue(quantity);
		            Cell cell10 = excelRow.createCell(colNum++);
		            cell10.setCellValue(volumeDiscouny);
		            
		            threePriceSubTotal += volumeDiscouny;	
	            }
	            
	            Row lastRow2 = sheet2.createRow(rowNum2++);
	            Cell cellSubTotalLabel2 = lastRow2.createCell(colNum2-2);
	            cellSubTotalLabel2.setCellValue("Subtotal");
	            Cell cellSubTotalValue2 = lastRow2.createCell(colNum2-1);
	            cellSubTotalValue2.setCellValue(threePriceSubTotal);
	             
	            
	            // outputstream
	            outputStream = new FileOutputStream(filepathAndName);
	            workbook.write(outputStream);
	            outputStream.close();
	         	 
	         	 
	        }catch(Exception e){
	               e.printStackTrace();
	               throw e;
	        }finally{
	               
	        }
	  }	

	 
	public void step1_2(String manufacturerCode) throws Exception{
	        
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
	               Dataset<org.apache.spark.sql.Row> qualifiedClaims = getDataset(sql1).cache();
	               
	               qualifiedClaims = qualifiedClaims.withColumn("CLAIM_ID", functions.row_number().over(Window.orderBy("DIN_PIN")));                
	               //System.out.println("All Qualified Claims for " + manufacturerCode);
	               //qualifiedClaims.show();

	   	        SparkSession spark = SparkSession.builder().appName("IdmsEngine")//.enableHiveSupport()
                        .config("spark.executor.memory","4g").master("local[*]").getOrCreate();

		        String userId = PropertyConfig.getProperty(ConfigPropertyName.USERNAME);
		        String pwd = PropertyConfig.getProperty(ConfigPropertyName.PASSWORD);
		        String url = PropertyConfig.getProperty(ConfigPropertyName.DB_URL);
		
		        Properties connectionProperties = new Properties();
		        connectionProperties.put("user",userId);
		        connectionProperties.put("password",pwd);
		
		        //Dataset<org.apache.spark.sql.Row> dataset = spark.read().format("jdbc").option("url", url).option("user", userId).option("password", pwd).option("dbtable", sql).load();

	               
	               
	               // step2 ************************************************************
	               Dataset<org.apache.spark.sql.Row> rawFirstPrice = spark.read().format("jdbc").option("url", url).option("user", userId).option("password", pwd).option("dbtable",sql2).load().cache();
	               
	                Dataset<org.apache.spark.sql.Row> firstPrice = rawFirstPrice.groupBy("DIN_PIN","REC_EFF_DATE").agg(max("REC_CREATE_TIMESTAMP"))
	                												.withColumnRenamed("max(REC_CREATE_TIMESTAMP)", "REC_CREATE_TIMESTAMP").cache();
	               
	                firstPrice = rawFirstPrice.join(firstPrice,
	             		   						rawFirstPrice.col("REC_CREATE_TIMESTAMP").equalTo(firstPrice.col("REC_CREATE_TIMESTAMP"))
	             		   															     .and(rawFirstPrice.col("DIN_PIN").equalTo(firstPrice.col("DIN_PIN")))
	             		   															     .and(rawFirstPrice.col("REC_EFF_DATE").equalTo(firstPrice.col("REC_EFF_DATE")))
	             		   															     ,"left_semi").orderBy("DIN_PIN");    				
	                //System.out.println("First Price of Drug ");
	                //firstPrice.show();
      	               
	             // step3 ************************************************************
	                //System.out.println("Second Price of Drug ");
	                Dataset<org.apache.spark.sql.Row> rawSecondPrice = spark.read().format("jdbc").option("url", url).option("user", userId).option("password", pwd).option("dbtable",sql3).load().cache();	                
	                Dataset<org.apache.spark.sql.Row> secondPrice = rawSecondPrice.groupBy("DIN_PIN","REC_EFF_DATE").agg(max("REC_CREATE_TIMESTAMP"))
	                		.withColumnRenamed("max(REC_CREATE_TIMESTAMP)", "REC_CREATE_TIMESTAMP").cache();                        	                
	                secondPrice = rawSecondPrice.join(secondPrice,
	             		   						rawSecondPrice.col("REC_CREATE_TIMESTAMP").equalTo(secondPrice.col("REC_CREATE_TIMESTAMP"))
	             		   																  .and(rawSecondPrice.col("DIN_PIN").equalTo(secondPrice.col("DIN_PIN")))
	             		   																  .and(rawSecondPrice.col("REC_EFF_DATE").equalTo(secondPrice.col("REC_EFF_DATE")))
	             		   																  ,"left_semi").orderBy("DIN_PIN");    		   						   					 
	               // secondPrice.show(); 

	               
	             // step4 ************************************************************
	                //System.out.println("YYYY Price of Drug ");
	                Dataset<org.apache.spark.sql.Row> rawYYYYPrice = spark.read().format("jdbc").option("url", url).option("user", userId).option("password", pwd).option("dbtable",sql4).load().cache();	                
	                Dataset<org.apache.spark.sql.Row> yyyyPrice = rawYYYYPrice.groupBy("DIN_PIN","REC_EFF_DATE").agg(max("REC_CREATE_TIMESTAMP"))
	                		.withColumnRenamed("max(REC_CREATE_TIMESTAMP)", "REC_CREATE_TIMESTAMP").cache();
	                               
	                yyyyPrice = rawYYYYPrice.join(yyyyPrice,
	               		 rawYYYYPrice.col("REC_CREATE_TIMESTAMP").equalTo(yyyyPrice.col("REC_CREATE_TIMESTAMP"))
	   																  .and(rawYYYYPrice.col("DIN_PIN").equalTo(yyyyPrice.col("DIN_PIN")))
	   																  .and(rawYYYYPrice.col("REC_EFF_DATE").equalTo(yyyyPrice.col("REC_EFF_DATE")))
	   																  ,"left_semi").orderBy("DIN_PIN");  
       		   						   					 
	               // yyyyPrice.show();
   
	             // step5 ************************************************************
	                //System.out.println("Join the 3 previously created temporary drug files together");	              	
	         		Dataset<org.apache.spark.sql.Row> joinedClaimed = secondPrice.select("DIN_PIN","DIN_DESC","SECOND_PRICE","REC_EFF_DATE","REC_CREATE_TIMESTAMP","MANUFACTURER_CD").cache();
	         		
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
	         								     .join(yyyyPrice,joinedClaimed.col("DIN_PIN").equalTo(yyyyPrice.col("c_DIN_PIN")),"full_outer")
	         								     .select("DIN_PIN","DIN_DESC","FIRST_PRICE","SECOND_PRICE","YYYY_PRICE","MANUFACTURER_CD").orderBy("DIN_PIN");
	         		
	         		//joinedClaimed.show();

	               
	         	// step6 ************************************************************
	         		//System.out.println("Define Missing DB(TEMP 04)");
	                 Dataset<org.apache.spark.sql.Row> missingData = spark.read().format("jdbc").option("url", url).option("user", userId).option("password", pwd).option("dbtable",sql6).load()
	                		 										.withColumnRenamed("DRUG_BENEFIT_PRICE", "FIRST_PRICE")
	                		 										.withColumnRenamed("DIN_PIN", "m_DIN_PIN").cache();
	                 	                
	                 Dataset<org.apache.spark.sql.Row> dbpData = spark.read().format("jdbc").option("url", url).option("user", userId).option("password", pwd).option("dbtable",dbpSql).load()
	                		 									.select("DIN_PIN","PRICE_LIM")
	                		 									.withColumnRenamed("PRICE_LIM", "DBP_LIM_JL115")
	                		 									.withColumnRenamed("DIN_PIN", "d_DIN_PIN").cache();
	                 
	                 joinedClaimed = joinedClaimed.select("DIN_PIN","DIN_DESC","SECOND_PRICE");
	                 missingData = missingData.join(dbpData,missingData.col("m_DIN_PIN").equalTo(dbpData.col("d_DIN_PIN")))
	                		 				  .select("m_DIN_PIN","FIRST_PRICE","YYYY_PRICE","STRENGTH","DOSAGE_FORM","GEN_NAME","DBP_LIM_JL115");
	                 	               	                 
	                 missingData = joinedClaimed.join(missingData,missingData.col("m_DIN_PIN").equalTo(joinedClaimed.col("DIN_PIN")))
	                		 					.select("DIN_PIN","DIN_DESC","GEN_NAME","FIRST_PRICE","SECOND_PRICE","YYYY_PRICE","DBP_LIM_JL115","STRENGTH","DOSAGE_FORM").orderBy("DIN_PIN");
	                 
	                 //missingData.show();	
	                 
	                 	
	         	// step8 ************************************************************
	         		
	                 //System.out.println("creates a complete file of the initial claims extract and adds all the price components contained in the drug file ---TEMP05 ");
	                 qualifiedClaims = qualifiedClaims.select("CLAIM_ID","DIN_PIN","DRG_CST_ALLD","QTY","PROD_SEL","PROF_FEE_ALLD","INTERVENTION_1","INTERVENTION_2","INTERVENTION_3","INTERVENTION_4",
	           			  "INTERVENTION_5","INTERVENTION_6","INTERVENTION_7","INTERVENTION_8","INTERVENTION_9","INTERVENTION_10");
	                 missingData = missingData.select("DIN_PIN","DIN_DESC","GEN_NAME","STRENGTH","DOSAGE_FORM","FIRST_PRICE","SECOND_PRICE","YYYY_PRICE","DBP_LIM_JL115")
	                		 				  .withColumnRenamed("DIN_PIN", "b_DIN_PIN");
	           	  	 Dataset<org.apache.spark.sql.Row> completeClaims = qualifiedClaims.join(missingData,qualifiedClaims.col("DIN_PIN").equalTo(missingData.col("b_DIN_PIN")))
	           	  			 											.select("CLAIM_ID","DIN_PIN","DIN_DESC","GEN_NAME","STRENGTH","DOSAGE_FORM","FIRST_PRICE","SECOND_PRICE","YYYY_PRICE","DBP_LIM_JL115",
	           	  			 														"DRG_CST_ALLD","QTY","PROD_SEL","PROF_FEE_ALLD","INTERVENTION_1","INTERVENTION_2","INTERVENTION_3","INTERVENTION_4","INTERVENTION_5","INTERVENTION_6",
	           	  			 														"INTERVENTION_7","INTERVENTION_8","INTERVENTION_9","INTERVENTION_10").orderBy("DIN_PIN").cache();
           	
	           	  
	           	  //completeClaims.show();
  	

	           	// step9 ************************************************************
	         		
	           	//System.out.println("uses selection criteria to include only claims that meet the requirements for agreement reconciliation ---- TEMP06");
	        	 Dataset<org.apache.spark.sql.Row> conditionalClaims = completeClaims.select("*").where(col("DBP_LIM_JL115").equalTo(col("SECOND_PRICE"))
	        			 																	.or(col("DBP_LIM_JL115").lt(col("SECOND_PRICE"))
	        			 																	.and(col("PROD_SEL").equalTo(1).or(col("INTERVENTION_1").equalTo("MI"))
	        			 																								   .or(col("INTERVENTION_2").equalTo("MI"))
	        			 																								   .or(col("INTERVENTION_3").equalTo("MI"))
	        			 																								   .or(col("INTERVENTION_4").equalTo("MI"))
	        			 																								   .or(col("INTERVENTION_5").equalTo("MI"))
	        			 																								   .or(col("INTERVENTION_6").equalTo("MI")))));
	        	 conditionalClaims.cache();
	        	//conditionalClaims.show();	 																									
	
	        	
	        	
	        	// step10 ************************************************************
	        	 //System.out.println(" calculates an adjusted quantity ---- TEMP07");
	     
	         	  Dataset<org.apache.spark.sql.Row> adjustedQuantity = conditionalClaims.select("CLAIM_ID","DIN_PIN","DIN_DESC","GEN_NAME","STRENGTH","DOSAGE_FORM","FIRST_PRICE","SECOND_PRICE",
	         			  "YYYY_PRICE","DBP_LIM_JL115","DRG_CST_ALLD","QTY","PROD_SEL","PROF_FEE_ALLD","INTERVENTION_1","INTERVENTION_2","INTERVENTION_3","INTERVENTION_4",
	         			  "INTERVENTION_5","INTERVENTION_6").cache();

	         	adjustedQuantity = adjustedQuantity.withColumn("ADJ_QTY", bround(col("DRG_CST_ALLD").divide(col("SECOND_PRICE")),1))
	         									   .withColumn("FNL_QTY",col("ADJ_QTY"))
	         									   .withColumn("FNL_QTY",functions.when(col("FNL_QTY").gt(col("QTY")),col("QTY")).otherwise(col("ADJ_QTY")));
	         															   
	         	//adjustedQuantity.show();	

	         		
	         	// step11 ************************************************************
	         		
	         	 //System.out.println(" calculates the volume discount for each individual claim ---- TEMP08");
	         	  Dataset<org.apache.spark.sql.Row> drugRebate = adjustedQuantity.select("*")
	         			 .withColumn("IS_TWO_PRICE", functions.when(col("YYYY_PRICE").isNull(), "Y").otherwise("N"))
	         			 .withColumn("CHQ_BACK",functions.when(col("IS_TWO_PRICE").equalTo("Y").and(col("DRG_CST_ALLD").lt(1000)), (col("SECOND_PRICE").minus(col("FIRST_PRICE"))).multiply(col("FNL_QTY")).multiply(1.08))
									.otherwise(functions.when(col("IS_TWO_PRICE").equalTo("Y").and(col("DRG_CST_ALLD").gt(1000)), (col("SECOND_PRICE").minus(col("FIRST_PRICE"))).multiply(col("FNL_QTY")).multiply(1.06))
											.otherwise(functions.when(col("IS_TWO_PRICE").equalTo("N").and(col("DRG_CST_ALLD").lt(1000)), (col("SECOND_PRICE").minus(col("FIRST_PRICE"))).multiply(col("FNL_QTY")).plus((col("SECOND_PRICE").minus(col("YYYY_PRICE"))).multiply(col("FNL_QTY")).multiply(0.08)))
													.otherwise(functions.when(col("IS_TWO_PRICE").equalTo("N").and(col("DRG_CST_ALLD").gt(1000)), (col("SECOND_PRICE").minus(col("FIRST_PRICE"))).multiply(col("FNL_QTY")).plus((col("SECOND_PRICE").minus(col("YYYY_PRICE"))).multiply(col("FNL_QTY")).multiply(0.06)))))))
	         			 .withColumn("CHQ_BACK",bround(col("CHQ_BACK"),4)).cache();     	  
 
	         	  //drugRebate.show();
	         	           	  
	         	// step12************************************************************
	               
	         	 //System.out.println(" creates a new file with the volume discount amount included for each individual claim ---- TEMP09");
	         	  
	         	  Dataset<org.apache.spark.sql.Row> a = drugRebate.groupBy(col("DIN_PIN")).sum("CHQ_BACK","FNL_QTY","ADJ_QTY","QTY","DRG_CST_ALLD").distinct()
	         			  											.withColumnRenamed("sum(CHQ_BACK)", "CHQ_BACK")
	         			  											.withColumnRenamed("sum(FNL_QTY)", "FNL_QTY")
	         			  											.withColumnRenamed("sum(ADJ_QTY)", "ADJ_QTY")
	         			  											.withColumnRenamed("sum(QTY)", "QTY")
	         			  											.withColumnRenamed("sum(DRG_CST_ALLD)", "DRG_CST_ALLD")
	         			  											.withColumnRenamed("DIN_PIN","a_DIN_PIN").cache();
	         	  	 
	         	  Dataset<org.apache.spark.sql.Row> temp09 = drugRebate.select("DIN_PIN","DIN_DESC","GEN_NAME","STRENGTH","DOSAGE_FORM","FIRST_PRICE","SECOND_PRICE","YYYY_PRICE","DBP_LIM_JL115","IS_TWO_PRICE").distinct().cache();
	         			  		         	 
	         	  temp09 = temp09.join(a,a.col("a_DIN_PIN").equalTo(temp09.col("DIN_PIN")),"full_outer").orderBy("DIN_PIN")
	         	  				 .drop(col("a_DIN_PIN"));		
	         	  //temp09.show();
	               
	               
	         	// step13***********************************************************  
	               
	         	 //System.out.println("  joins the summarized data to the drug file that was created earlier. ---- TEMP11");
	         	  Dataset<org.apache.spark.sql.Row> rebateSummary = temp09.select("*")
	         			    											  .drop(col("CLAIM_ID")).cache();
		  	
	         	  Dataset<org.apache.spark.sql.Row> distinctDin_Pin = rebateSummary.select("DIN_PIN").distinct().cache();	         	  
	         	  rebateSummary = rebateSummary.dropDuplicates("DIN_PIN");
	         	  
	         	  String sql = "(select DIN_PIN, YYYY_PRICE from SCHEDULE_A)";
	         	  Dataset<org.apache.spark.sql.Row> c = spark.read().format("jdbc").option("url", url).option("user", userId).option("password", pwd).option("dbtable",sql).load().cache();
	           	 
	         	  c = c.join(distinctDin_Pin,c.col("DIN_PIN").equalTo(distinctDin_Pin.col("DIN_PIN")),"left_anti").orderBy("DIN_PIN")
	         		   .withColumn("IS_TWO_PRICE", functions.when(col("YYYY_PRICE").isNull(), "Y").otherwise("N"))
	         		   .drop(col("YYYY_PRICE"))
	         		   .withColumn("DIN_DESC", functions.lit(null).cast(DataTypes.StringType)).withColumn("GEN_NAME",functions.lit(null).cast(DataTypes.StringType))
	         		   .withColumn("STRENGTH",functions.lit(null).cast(DataTypes.StringType)).withColumn("DOSAGE_FORM",functions.lit(null).cast(DataTypes.StringType))
	         		   .withColumn("FIRST_PRICE",functions.lit(null).cast(DataTypes.StringType)).withColumn("SECOND_PRICE",functions.lit(null).cast(DataTypes.StringType))
	         		   .withColumn("YYYY_PRICE",functions.lit(null).cast(DataTypes.StringType)).withColumn("DBP_LIM_JL115",functions.lit(null).cast(DataTypes.StringType))
	         		   .withColumn("CHQ_BACK",functions.lit(null).cast(DataTypes.StringType)).withColumn("ADJ_QTY",functions.lit(null).cast(DataTypes.StringType))
	         		   .withColumn("FNL_QTY",functions.lit(null).cast(DataTypes.StringType)).withColumn("QTY",functions.lit(null).cast(DataTypes.StringType))
	         		   .withColumn("DRG_CST_ALLD",functions.lit(null).cast(DataTypes.StringType))
	         		   .select("DIN_PIN","DIN_DESC","GEN_NAME","STRENGTH","DOSAGE_FORM","FIRST_PRICE","SECOND_PRICE","YYYY_PRICE","DBP_LIM_JL115","DRG_CST_ALLD"
		         			  ,"QTY","ADJ_QTY","FNL_QTY","IS_TWO_PRICE","CHQ_BACK");
     	  
	         	  rebateSummary = rebateSummary.union(c)
	         			  					   .withColumn("CLAIM_ID", functions.row_number().over(Window.orderBy("DIN_PIN")))
	         			  					   .withColumn("FIRST_PRICE", col("FIRST_PRICE").cast(DataTypes.DoubleType))
	         			  					   .withColumn("SECOND_PRICE", col("SECOND_PRICE").cast(DataTypes.DoubleType))
	         			  					   .withColumn("YYYY_PRICE", col("YYYY_PRICE").cast(DataTypes.DoubleType))
	         			  					   .withColumn("FNL_QTY", col("FNL_QTY").cast(DataTypes.DoubleType))
	         			  					   .withColumn("CHQ_BACK", col("CHQ_BACK").cast(DataTypes.DoubleType))
	         			  					   .withColumn("DRG_CST_ALLD", col("DRG_CST_ALLD").cast(DataTypes.DoubleType))
	         			  					   .select("CLAIM_ID","DIN_PIN","DIN_DESC","GEN_NAME","STRENGTH","DOSAGE_FORM","FIRST_PRICE","SECOND_PRICE","YYYY_PRICE","DBP_LIM_JL115","DRG_CST_ALLD"
	         			 	         			  ,"QTY","ADJ_QTY","FNL_QTY","IS_TWO_PRICE","CHQ_BACK");
	         			  					
	         	  //rebateSummary.show();
	         	  
	         	  
	         	// generate excel form
	         	String path = "C:\\TEMP\\IDMS\\data";
	         	 
	         	File file = new File(path); 
	        	if(!file.exists()){
	        		file.mkdirs();
	        	}
	         	 String filepathAndName = path + "\\Rebate_Summary_" + manufacturerCode + "_" + new Date(System.currentTimeMillis()).toString() + ".xlsx";
	         	 XSSFWorkbook workbook = new XSSFWorkbook();
	         	 FileOutputStream outputStream = null;
	         	 // sheet1
	         	 Dataset<org.apache.spark.sql.Row> rebateExcel1 = rebateSummary.select("DIN_PIN","DIN_DESC","STRENGTH","DOSAGE_FORM","DRG_CST_ALLD","FIRST_PRICE",
	         			 "SECOND_PRICE","YYYY_PRICE","FNL_QTY","CHQ_BACK").where(col("IS_TWO_PRICE")
	         					 .equalTo("Y")).orderBy(col("DIN_PIN"));
	         	rebateExcel1.cache();
	         	
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
	            
	            List<org.apache.spark.sql.Row> dataRows = rebateExcel1.collectAsList();
	            for(org.apache.spark.sql.Row sparkRow1: dataRows) {
	            	Row excelRow = sheet1.createRow(rowNum++);
	            	colNum = 0;
	            	String dinPin = sparkRow1.getString(0);
	            	String brandName = sparkRow1.getString(1);
	            	String strength = sparkRow1.getString(2);
	            	String dosageForm = sparkRow1.getString(3);
	            	double totalDrugCostPaid = sparkRow1.getDouble(4);
	            	double formerDBP = sparkRow1.getDouble(5);
	            	double currentDBP = sparkRow1.getDouble(6);
	            	double quantity = sparkRow1.getDouble(8);
	            	double volumeDiscouny = sparkRow1.getDouble(9);
	            	
		            Cell cell1 = excelRow.createCell(colNum++);
		            cell1.setCellValue(dinPin);
		            Cell cell2 = excelRow.createCell(colNum++);
		            cell2.setCellValue(brandName);
		            Cell cell3 = excelRow.createCell(colNum++);
		            cell3.setCellValue(strength);
		            Cell cell4 = excelRow.createCell(colNum++);
		            cell4.setCellValue(dosageForm);
		            Cell cell5 = excelRow.createCell(colNum++);
		            cell5.setCellValue(manufacturerCode);
		            Cell cell6 = excelRow.createCell(colNum++);
		            cell6.setCellValue(totalDrugCostPaid);
		            Cell cell7 = excelRow.createCell(colNum++);
		            cell7.setCellValue(formerDBP);
		            Cell cell8 = excelRow.createCell(colNum++);
		            cell8.setCellValue(currentDBP);
		            Cell cell9 = excelRow.createCell(colNum++);
		            cell9.setCellValue(quantity);
		            Cell cell10 = excelRow.createCell(colNum++);
		            cell10.setCellValue(volumeDiscouny);
		            
		            twoPriceSubTotal += volumeDiscouny;	
	            }
	            
	            Row lastRow1 = sheet1.createRow(rowNum++);
	            Cell cellSubTotalLabel = lastRow1.createCell(colNum-2);
	            cellSubTotalLabel.setCellValue("Subtotal");
	            Cell cellSubTotalValue = lastRow1.createCell(colNum-1);
	            cellSubTotalValue.setCellValue(twoPriceSubTotal);
	            
	            
	            
	         // sheet2
	            XSSFSheet sheet2 = workbook.createSheet("Three Price DIN");
	            Dataset<org.apache.spark.sql.Row> rebateExcel2 = rebateSummary.select("DIN_PIN","DIN_DESC","STRENGTH","DOSAGE_FORM","DRG_CST_ALLD","FIRST_PRICE",
	         			 "SECOND_PRICE","YYYY_PRICE","FNL_QTY","CHQ_BACK").where(col("IS_TWO_PRICE")
	         					 .equalTo("N")).orderBy(col("DIN_PIN"));
	            rebateExcel2.cache();
	            int rowNum2 = 0;
	            Row row2 = sheet2.createRow(rowNum2++);
	            int colNum2 = 0;
	            Cell cell21 = row2.createCell(colNum2++);
	            cell21.setCellValue("DIN/PIN");
	            Cell cell22 = row2.createCell(colNum2++);
	            cell22.setCellValue("Brand Name");
	            Cell cell23 = row2.createCell(colNum2++);
	            cell23.setCellValue("Strength");
	            Cell cell24 = row2.createCell(colNum2++);
	            cell24.setCellValue("Dosage Form");  
	            Cell cell25 = row2.createCell(colNum2++);  
	            cell25.setCellValue("Manufacturer Code");
	            Cell cell26 = row2.createCell(colNum2++);
	            cell26.setCellValue("Total Drug Cost Paid");
	            Cell cell27 = row2.createCell(colNum2++);
	            cell27.setCellValue("Former DBP (B)");
	            Cell cell28 = row2.createCell(colNum2++);
	            cell28.setCellValue("Current DBP (A)");
	            Cell cell29 = row2.createCell(colNum2++);
	            cell29.setCellValue("Quantity");
	            Cell cell210 = row2.createCell(colNum2++);
	            cell210.setCellValue("Volume Discount");
	            double threePriceSubTotal = 0;
	            
	            List<org.apache.spark.sql.Row> dataRows2 = rebateExcel2.collectAsList();
	            for(org.apache.spark.sql.Row sparkRow2: dataRows2) {
	            	Row excelRow = sheet2.createRow(rowNum2++);
	            	colNum = 0;
	            	String dinPin = sparkRow2.getString(0);
	            	String brandName = sparkRow2.getString(1);
	            	String strength = sparkRow2.getString(2);
	            	String dosageForm = sparkRow2.getString(3);
	            	double totalDrugCostPaid = sparkRow2.getDouble(4);
	            	double formerDBP = sparkRow2.getDouble(5);
	            	double currentDBP = sparkRow2.getDouble(6);
	            	double quantity = sparkRow2.getDouble(8);
	            	double volumeDiscouny = sparkRow2.getDouble(9);
	            	
		            Cell cell1 = excelRow.createCell(colNum++);
		            cell1.setCellValue(dinPin);
		            Cell cell2 = excelRow.createCell(colNum++);
		            cell2.setCellValue(brandName);
		            Cell cell3 = excelRow.createCell(colNum++);
		            cell3.setCellValue(strength);
		            Cell cell4 = excelRow.createCell(colNum++);
		            cell4.setCellValue(dosageForm);
		            Cell cell5 = excelRow.createCell(colNum++);
		            cell5.setCellValue(manufacturerCode);
		            Cell cell6 = excelRow.createCell(colNum++);
		            cell6.setCellValue(totalDrugCostPaid);
		            Cell cell7 = excelRow.createCell(colNum++);
		            cell7.setCellValue(formerDBP);
		            Cell cell8 = excelRow.createCell(colNum++);
		            cell8.setCellValue(currentDBP);
		            Cell cell9 = excelRow.createCell(colNum++);
		            cell9.setCellValue(quantity);
		            Cell cell10 = excelRow.createCell(colNum++);
		            cell10.setCellValue(volumeDiscouny);
		            
		            threePriceSubTotal += volumeDiscouny;	
	            }
	            
	            Row lastRow2 = sheet2.createRow(rowNum2++);
	            Cell cellSubTotalLabel2 = lastRow2.createCell(colNum2-2);
	            cellSubTotalLabel2.setCellValue("Subtotal");
	            Cell cellSubTotalValue2 = lastRow2.createCell(colNum2-1);
	            cellSubTotalValue2.setCellValue(threePriceSubTotal);
	             
	            
	            // outputstream
	            outputStream = new FileOutputStream(filepathAndName);
	            workbook.write(outputStream);
	            outputStream.close();
	         	 
	         	 
	        }catch(Exception e){
	               e.printStackTrace();
	               throw e;
	        }finally{
	               
	        }
	  }	
	

}
