package ca.on.moh.idms.engine;

import gov.moh.config.ConfigFromDB;
import gov.moh.config.PropertyConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import ca.on.moh.idms.util.RebateConstant;

public class test {

	public static void main(String[] args) throws Exception {
		
		String appRoot = System.getProperty("user.dir");
		PropertyConfig.setPropertyPath(appRoot + "\\conf\\system.properties");
		String historyStartDate = ConfigFromDB.getConfigPropertyFromDB(RebateConstant.HISTORY_START_DATE);	//JUN 29 2015
		String historyEndDate = ConfigFromDB.getConfigPropertyFromDB(RebateConstant.HISTORY_END_DATE);	//JUN 30 2015
		String firstPriceDate = ConfigFromDB.getConfigPropertyFromDB(RebateConstant.FIRST_PRICE_DATE);	//OCT-23-2006
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
		Dataset<Row> jdbcDF = spark.read()
				  .jdbc(ORACLEDB_URL,"CLMHIST", connectionProperties);
		jdbcDF.createOrReplaceTempView("CLMHIST");      // Register the DataFrame as a SQL temporary view
		//jdbcDF.show();
		Dataset<Row> scheduleADF = spark.read()
				  .jdbc(ORACLEDB_URL,"SCHEDULE_A", connectionProperties);
		scheduleADF.createOrReplaceTempView("SCHEDULE_A");
		//scheduleADF.show();
		
		String sql = "select DIN_PIN,DT_OF_SERV, ADJUDICATION"
				  		+ "_DT, PROF_FEE_ALLD,QTY,DRG_CST_ALLD,PROG_ID,PROD_SEL, INTERVENTION_1, " +
				  		"INTERVENTION_2, INTERVENTION_3, INTERVENTION_4, INTERVENTION_5, INTERVENTION_6, INTERVENTION_7, INTERVENTION_8, INTERVENTION_9, INTERVENTION_10 " +
		  				"from CLMHIST " +
		  				"where CURR_STAT = 'P' and Trunc(to_date(DT_OF_SERV,'YY-MON-DD'),'DD') >= to_date('" + historyStartDate + "','MM-DD-YYYY') AND " +
		  				"Trunc(to_date(DT_OF_SERV,'YY-MON-DD'),'DD') <= to_date('" + historyEndDate + "','MM-DD-YYYY') AND " +
		  				"PROG_ID <> 'TP' and DIN_PIN in (select DIN_PIN from SCHEDULE_A where MANUFACTURER_CD = '" + manufacturerCode + "')";
		 	  
		  Dataset<Row> sqlDF = spark.sql(sql);	 
		  sqlDF.show(20);
		  sqlDF.printSchema();
		
		  
		  

	}

}
