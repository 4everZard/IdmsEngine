package ca.on.moh.idms.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.List;

import ca.on.moh.idms.vo.Extract;
import ca.on.moh.idms.vo.Formulary;
import ca.on.moh.idms.vo.Organization;
import ca.on.moh.idms.xml.DrugXmlParser;

public class FormularyRestClient {

	public static void main(String [] a) throws Exception{
		System .out.println("1. e-Formulary Rest Service Client started .........");


		String proxyIP = "204.40.130.129";
		int proxyPort = 3128;
		//String agency = "ttc";
		//String route = "501";
		//sourceUrl = sourceUrl + agency + "&r=" + route;
		
		String sourceUrl = "http://health.gov.on.ca/en/pro/programs/drugs/data_extract.xml";
		//String sourceUrl = "http://webservices.nextbus.com/service/publicXMLFeed?command=predictions&a=ttc&r=39&s=6717";
		
		//BufferedReader br = new RestClient().getXmlStream(sourceUrl,proxyIP,proxyPort);
		String xml = new FormularyRestClient().getRouteXmlFromRest(sourceUrl,proxyIP,proxyPort);

		System .out.println("2. xml retrieved .........");
		System.out.println(xml);
		
/*
		BufferedReader br  = new BufferedReader(new StringReader(xml));;
		
		DrugXmlParser parser = new DrugXmlParser();
		DrugXmlParser.readAndCacheMetadataXml(parser, br, true);
		Extract all = parser.getExtract();
		if (all != null) {
			List<Organization> orglist = all.getManufacturerList();
			Formulary  formulary = all.getFormulary();
			System.out.println(formulary.getEdition() + ", " + formulary.getUpdateVer() + ", " + formulary.getCreateDate() + ", " + formulary.getFormularyDate());
		}
		br.close();
*/
	}

	public String getRouteXmlFromRest(String sourceUrl) throws Exception{
		return getRouteXmlFromRest(sourceUrl,null, -1);
	}

	public String getRouteXmlFromRest(String sourceUrl,String proxyIP, int proxyPort) throws Exception{
		String xml = "";
		try {
			HttpURLConnection conn = null;
			if(proxyIP != null){
				Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyIP, proxyPort));
				URL url = new URL(sourceUrl);
				conn = (HttpURLConnection) url.openConnection(proxy);
			}else{
				URL url = new URL(sourceUrl);
				conn = (HttpURLConnection) url.openConnection();
			}
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/xml");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String line;
			while ((line = br.readLine()) != null) {
				xml += line + "\r\n";
			}
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
		return xml;
	}
	
	public BufferedReader getXmlStream(String sourceUrl) throws Exception{
		return getXmlStream(sourceUrl,null, -1);
	}

	
	public BufferedReader getXmlStream(String sourceUrl,String proxyIP, int proxyPort) throws Exception{
		BufferedReader br = null;
		try {
			HttpURLConnection conn = null;
			if(proxyIP != null){
				Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyIP, proxyPort));
				URL url = new URL(sourceUrl);
				conn = (HttpURLConnection) url.openConnection(proxy);
			}else{
				URL url = new URL(sourceUrl);
				conn = (HttpURLConnection) url.openConnection();
			}
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/xml");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
		return br;
	}


}
