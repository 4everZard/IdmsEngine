package ca.on.moh.idms.util;

import gov.moh.config.ConfigPropertyName;
import gov.moh.config.PropertyConfig;

import org.apache.spark.sql.DataFrameReader;
import org.apache.spark.sql.SparkSession;

import java.util.Properties;

public class SparkDatabaseConnection {
	/*
	private SparkDatabaseConnection instance;
	
	private SparkDatabaseConnection (){
	}
	
	public SparkDatabaseConnection getInstance(){
		if(instance == null){
			instance = new SparkDatabaseConnection();
		}
		return instance;
	}
	*/
	static SparkSession spark = null;
	static DataFrameReader sparkDBConnection; 
	static Properties sparkDBConnectionProperties = new Properties();
	static String url;
	static String username;
	static String pwd;
	
	public static SparkSession getSpearkSession() throws Exception{
		if(spark == null){
		    spark = SparkSession.builder().appName("IdmsEngine")
					  .config("spark.executor.memory","6g").master("local[1]").getOrCreate();//setMaster("local[*]")

		}
		return spark;
	}
	
	public static DataFrameReader getSparkDBConnection() throws Exception{
		if(sparkDBConnection == null){
			String userId = PropertyConfig.getProperty(ConfigPropertyName.USERNAME);
			String pwd = PropertyConfig.getProperty(ConfigPropertyName.PASSWORD);
			String url = PropertyConfig.getProperty(ConfigPropertyName.DB_URL);
			sparkDBConnection = getSparkDBConnection(url,userId,pwd);


		}
		return sparkDBConnection;
	}
	
	
	public static DataFrameReader getSparkDBConnection(String url, String userId, String pwd ) throws Exception{
		spark = getSpearkSession();
		DataFrameReader sparkDBConnection = spark.read().format("jdbc")
		    		.option("url", url)
		    		.option("user", userId)
		    		.option("password", pwd);
		return sparkDBConnection;
	}
	
	
	
	public static void setSparkDBConnectionProperties(String username, String pwd) throws Exception{
		 
		
		sparkDBConnectionProperties.put("user", username);
		sparkDBConnectionProperties.put("password", pwd);
	
	}
	
	public static Properties getSparkDBConnectionProperties() throws Exception{
		
		return sparkDBConnectionProperties;
	}
	
	public static void setUrl(String url){
		SparkDatabaseConnection.url = url;
		
	}
	
	public static String getUrl(){
		return url;
	}
	

	public static void setUsername(String username){
		SparkDatabaseConnection.username = username;
		
	}
	public static String getUsername(){
		return username;
	}
	
	public static void setPassword(String pwd){
		SparkDatabaseConnection.pwd = pwd;
		
	}
	public static String getPassword(){
		return pwd;
	}
	
	
}
