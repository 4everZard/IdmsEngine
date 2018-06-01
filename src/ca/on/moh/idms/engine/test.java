package ca.on.moh.idms.engine;

import gov.moh.config.ConfigFromDB;
import gov.moh.config.PropertyConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.functions;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import static org.apache.spark.sql.functions.date_sub;
import static org.apache.spark.sql.functions.date_trunc;
import static org.apache.spark.sql.functions.date_format;
import static org.apache.spark.sql.functions.to_date;
import static org.apache.spark.sql.functions.trunc;
import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.max;
import ca.on.moh.idms.util.RebateConstant;

public class test {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		Logger.getLogger("org").setLevel(Level.ERROR);
		String appRoot = System.getProperty("user.dir");
		PropertyConfig.setPropertyPath(appRoot + "\\conf\\system.properties");
		/*String historyStartDate = ConfigFromDB.getConfigPropertyFromDB(RebateConstant.HISTORY_START_DATE);	//JUN 29 2015
		String historyEndDate = ConfigFromDB.getConfigPropertyFromDB(RebateConstant.HISTORY_END_DATE);	//JUN 30 2015
		String firstPriceDate = ConfigFromDB.getConfigPropertyFromDB(RebateConstant.FIRST_PRICE_DATE);	//OCT-23-2006*/
		String historyStartDate = "2015-06-29";
		String historyEndDate = "2015-06-30";
		String firstPriceDate = "2006-10-23";
		
		/*SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date historyStartDate1 = dateFormat.parse(historyStartDate);
		java.sql.Date sqlHistoryStartDate = new java.sql.Date(historyStartDate1.getTime());
		System.out.println(sqlHistoryStartDate);
		
		
		Timestamp sparkHistoryStartDate = new java.sql.Timestamp(sqlHistoryStartDate.getTime());
		
		java.util.Date historyEndDate1 = dateFormat.parse(historyEndDate);
		java.sql.Date sqlHistoryEndDate = new java.sql.Date(historyEndDate1.getTime());
		Timestamp sparkHistoryEndDate = new java.sql.Timestamp(sqlHistoryEndDate.getTime());*/
		
		/*System.out.println(sparkHistoryStartDate);
		System.out.println(sparkHistoryEndDate);*/
		String manufacturerCode = "LEO";
		
		String ORACLEDB_URL = "jdbc:oracle:thin:@hscgikdcapmdw11.cihs.gov.on.ca:1521/ohfsdev.cihs.gov.on.ca";
		String ORACLEDB_USERNAME = "HUDAVY1";
		String ORACLEDB_PASSWORD = "hudavy";
		
		Properties connectionProperties = new Properties();
		connectionProperties.put("user",ORACLEDB_USERNAME);
		connectionProperties.put("password",ORACLEDB_PASSWORD);
		SparkSession spark = SparkSession
			      .builder()
			      .appName("Java Spark SQL data sources example")
			      .config("spark.some.config.option", "some-value")
			      .master("local[1]")
			      .getOrCreate();
		Dataset<Row> temp01DS = spark.read()
				  .jdbc(ORACLEDB_URL,"CLMHIST", connectionProperties);
		temp01DS.createOrReplaceTempView("CLMHIST");      // Register the DataFrame as a SQL temporary view
		
		Dataset<Row> scheduleADS = spark.read()
				  .jdbc(ORACLEDB_URL,"SCHEDULE_A", connectionProperties);
		scheduleADS.createOrReplaceTempView("SCHEDULE_A");
		
		Dataset<Row> DRUGDS = spark.read()
				  .jdbc(ORACLEDB_URL,"DRUG", connectionProperties);
		DRUGDS.createOrReplaceTempView("DRUG");	
		
		
		
		temp01DS = temp01DS.withColumn("DT_OF_SERV",date_format(col("DT_OF_SERV"),"yyyy-MM-dd"));   // convert timestamp to string
		temp01DS = temp01DS.withColumn("DT_OF_SERV",to_date(col("DT_OF_SERV"),"yyyy-MM-dd"));       // convert string to date
		//temp01DS = temp01DS.withColumn("DT_OF_SERV",trunc(col("DT_OF_SERV"),"mm"));
		/*temp01DS.show();
		temp01DS.printSchema();*/

	
		String sql = "select DIN_PIN,DT_OF_SERV, ADJUDICATION_DT, PROF_FEE_ALLD,QTY,DRG_CST_ALLD,PROG_ID,PROD_SEL, INTERVENTION_1, " +
				"INTERVENTION_2, INTERVENTION_3, INTERVENTION_4, INTERVENTION_5, INTERVENTION_6, INTERVENTION_7, INTERVENTION_8, INTERVENTION_9, INTERVENTION_10 " +
				"from CLMHIST " +
				"where CURR_STAT = 'P' and DT_OF_SERV >= to_date('" + historyStartDate + "','MM-DD-YYYY') AND " +
						"DT_OF_SERV <= to_date('" + historyEndDate + "','MM-DD-YYYY') AND " +
						"PROG_ID <> 'TP' and DIN_PIN in (select DIN_PIN from SCHEDULE_A where MANUFACTURER_CD = '" + manufacturerCode + "')";
		
		
		System.out.println("==========================TEMP01===============================");	
		temp01DS.sparkSession().sql(sql);
		temp01DS.printSchema();
		temp01DS.show();
		//temp01DS = temp01DS.withColumn("DT_OF_SERV",col("DT_OF_SERV").cast("date"));
		//temp01DS.printSchema();
		//temp01DS.sparkSession().sql(sql1);    sparkHistoryStartDate
		//temp01DS.show();
		//temp01DS.printSchema();
		/*temp01DS.select(date_trunc("MM-dd-yyyy",col("DT_OF_SERV")));
		temp01DS = temp01DS.select(col("DIN_PIN"),col("DT_OF_SERV"),col("ADJUDICATION_DT"),col("PROF_FEE_ALLD"),col("QTY"),col("DRG_CST_ALLD"),col("PROG_ID"),col("PROD_SEL"),
				  col("INTERVENTION_1"),col("INTERVENTION_2"),col("INTERVENTION_3"),col("INTERVENTION_4"),col("INTERVENTION_5"),col("INTERVENTION_6"),
				  col("INTERVENTION_7"),col("INTERVENTION_8"),col("INTERVENTION_9"),col("INTERVENTION_10"))
				  .filter(col("CURR_STAT").equalTo("P"))
				  .filter(col("PROG_ID").notEqual("TP"))*/
				  //.withcolumn("DT_OF_SERV",date_trunc("MM-dd-yyyy",col("DT_OF_SERV")));
				  //.filter(date_trunc("MM-dd-yyyy",col("DT_OF_SERV")));
				  
				  
				  
				  /*.withColumn("DT_OF_SERV",col("DT_OF_SERV").cast("string"))
				  .filter(col("DT_OF_SERV").between(historyStartDate, historyEndDate))
				  .join(scheduleADS, temp01DS.col("DIN_PIN").equalTo(scheduleADS.col("DIN_PIN")),"left_semi");*/
				  
				  
		//temp01DS.select(col("DT_OF_SERV"));
		//temp01DS = temp01DS.select(date_sub(col("DT_OF_SERV"),0));
		//temp01DS = temp01DS.select(date_trunc("dd",col("DT_OF_SERV")));
		
	/*	System.out.println("==========================TEMP03===============================");
		
		Dataset<Row> temp03DS = spark.read()
				  .jdbc(ORACLEDB_URL,"DRUG", connectionProperties);
		
		String sql2 = "select d.INDIVIDUAL_PRICE AS FIRST_PRICE, d.DIN_DESC, r.REC_EFF_DATE,d.REC_CREATE_TIMESTAMP,d.DIN_PIN, d.MANUFACTURER_CD from (" + 
			     " select DIN_PIN,MAX(REC_EFF_DT) as REC_EFF_DATE from DRUG where MANUFACTURER_CD = '" + manufacturerCode+ "' AND " +
			     " Trunc(REC_EFF_DT) < to_date('" + firstPriceDate + "','MM-DD-YYYY') AND REC_INACTIVE_TIMESTAMP is NULL group by DIN_PIN) r " +
			" inner join DRUG d on d.DIN_PIN = r.DIN_PIN and d.REC_EFF_DT = r.REC_EFF_DATE";
		
		Dataset<Row> effectiveDate = temp03DS.sparkSession().sql(sql2);
		
		
		
		String sql3 = "select d.INDIVIDUAL_PRICE AS FIRST_PRICE, d.DIN_DESC, d.REC_EFF_DT,d.REC_CREATE_TIMESTAMP, r.REC_CREATE_TIME,d.DIN_PIN from ( " +
			     "select DIN_PIN,REC_EFF_DT,MAX(REC_CREATE_TIMESTAMP) as REC_CREATE_TIME from DRUG where MANUFACTURER_CD ='" + manufacturerCode + "' AND " +
			     "date_sub(REC_EFF_DT,0) = to_Date('" + eDate + "','YYYY-MM-DD') AND DIN_PIN='" + din + "' AND REC_INACTIVE_TIMESTAMP is NULL group by DIN_PIN,REC_EFF_DT) r " +
			"inner join DRUG d on d.DIN_PIN = r.DIN_PIN and  d.REC_CREATE_TIMESTAMP = r.REC_CREATE_TIME";
		
		
		DRUGDS.sparkSession().sql(sql2);
		DRUGDS.show();
		DRUGDS.printSchema();
		*/

	}



}
