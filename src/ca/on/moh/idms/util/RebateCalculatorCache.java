package ca.on.moh.idms.util;

import gov.moh.app.db.DBConnectionManager;
import gov.moh.config.ConfigFromDB;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.on.moh.idms.vo.RebateVO;

public class RebateCalculatorCache {


	private static Map<String,Map<String, Double>> allManufacturerDBPLimiMap = new HashMap<>();

	private static Map<String,Map<String, RebateVO>> allManufacturerDinCache = new HashMap<>();

	private static Map<String,List<String>> twoPriceDinListMap = new HashMap<>();
	private static Map<String,List<String>> threePriceDinListMap = new HashMap<>();
	
	
	private static Map<String, Double> loadDbpLimitData(Connection conn, String manufacturerCode) throws Exception{
		Map<String, Double> dbpLimiMap = allManufacturerDBPLimiMap.get(manufacturerCode);
		if(dbpLimiMap == null){
			dbpLimiMap = new HashMap<String, Double>();
			String historyStartDate = ConfigFromDB.getConfigPropertyFromDB(RebateConstant.HISTORY_START_DATE);	//JUN 29 2015
			String sql = "select p.DIN_PIN, p.PRICE_LIM, p.REC_EFF_DT from ( " +
					"select DIN_PIN,MAX(REC_EFF_DT) as REC_EFF_DATE from PCGLIMIT where MANUFACTURER_CD = '" + manufacturerCode+ "' AND " +
				    "Trunc(REC_EFF_DT) < to_date('" + historyStartDate + "','MM-DD-YYYY') AND REC_INACTIVE_TIMESTAMP is NULL group by DIN_PIN) p1 " +
				    "inner join PCGLIMIT p on p.DIN_PIN = p1.DIN_PIN and p.REC_EFF_DT = p1.REC_EFF_DATE";

			PreparedStatement ps = null;
			PreparedStatement ps1 = null;
			ResultSet rs = null;
			ResultSet rs1 = null;

			try{
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				if(rs != null){
					while(rs.next()){
						double dbpLimit = rs.getDouble("PRICE_LIM");
						String din = rs.getString("DIN_PIN");
						Date effectiveDate = rs.getDate("REC_EFF_DT");
						
						if(dbpLimiMap.containsKey(din)){
							String sql1 = "select p.DIN_PIN, p.PRICE_LIM from (" +
									"select DIN_PIN, MAX(REC_CREATE_TIMESTAMP) as REC_CREATE_TIME from PCGLIMIT where MANUFACTURER_CD ='" + manufacturerCode+ "' AND " +
									"Trunc(REC_EFF_DT) = to_Date('" + effectiveDate.toString() + "','YYYY-MM-DD') AND DIN_PIN='" + din + "' AND " +
									"REC_INACTIVE_TIMESTAMP is NULL group by DIN_PIN,REC_EFF_DT) p1 " +
									"inner join PCGLIMIT p on p.DIN_PIN = p1.DIN_PIN and  p.REC_CREATE_TIMESTAMP = p1.REC_CREATE_TIME";
							/*
							String sql1 = "select p.DIN_PIN, p.PRICE_LIM, p.REC_EFF_DT from PCGLIMIT where MANUFACTURER_CD ='" + manufacturerCode+ "' AND " +
									"Trunc(REC_EFF_DT) > to_Date('" + startDate.toString() + "','YYYY-MM-DD') AND " +
									"Trunc(REC_EFF_DT) < to_Date('" + endDate.toString() + "','YYYY-MM-DD') AND DIN_PIN='" + din + "' AND " +
									"REC_INACTIVE_TIMESTAMP is NULL ";
							*/
							ps1 = conn.prepareStatement(sql1);
							rs1 = ps1.executeQuery();
							if(rs1 != null){
								while(rs1.next()){
									dbpLimit = rs1.getDouble("PRICE_LIM");
									din = rs1.getString("DIN_PIN");
									dbpLimiMap.put(din, new Double(dbpLimit));
								}
							}
						}else{
							dbpLimiMap.put(din, new Double(dbpLimit));
						}
					}
					//to-do: need to add double PRICE_LIM for the same DIN-PIN when there is a PRICE_LIM change during the claim period.
				}
				allManufacturerDBPLimiMap.put(manufacturerCode, dbpLimiMap);
			}catch(Exception e){
				e.printStackTrace();
				throw e;
			}finally{
				DBConnectionManager.getManager().closeConnection(null, ps, rs);
				DBConnectionManager.getManager().closeConnection(null, ps1, rs1);
			}
		}
		return dbpLimiMap;
	}

	private static Map<String, RebateVO> loadMissingDataFromScheduleA(Connection conn, String manufacturerCode) throws Exception{
		
		Map<String, RebateVO> missingDataFromCheduleAMap = new HashMap<String, RebateVO>();
		List<String> twoPriceDinList = new ArrayList<>();
		List<String> threePriceDinList = new ArrayList<>();
		String historyStartDate = ConfigFromDB.getConfigPropertyFromDB(RebateConstant.HISTORY_START_DATE);	//JUN 29 2015
		String sql = "select DIN_PIN, DRUG_BENEFIT_PRICE, YYYY_PRICE,STRENGTH,DOSAGE_FORM,GEN_NAME,CLAIM_START_DATE,CLAIM_END_DATE from SCHEDULE_A " +
				" where MANUFACTURER_CD = '" + manufacturerCode+ "' AND " +
			    "Trunc(CLAIM_START_DATE) < to_date('" + historyStartDate + "','MM-DD-YYYY') AND " +
			    "Trunc(CLAIM_END_DATE) > to_date('" + historyStartDate + "','MM-DD-YYYY')";

		PreparedStatement ps = null;
		ResultSet rs = null;

		try{
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs != null){
				while(rs.next()){
					RebateVO drug = new RebateVO();
					double yyPrice = rs.getDouble("YYYY_PRICE");
					String din = rs.getString("DIN_PIN");
					drug.setFirstPrice(rs.getDouble("DRUG_BENEFIT_PRICE"));
					drug.setYyyyPrice(yyPrice);
					drug.setDinPin(din);
					drug.setGenName(rs.getString("GEN_NAME"));
					drug.setStrength(rs.getString("STRENGTH"));
					drug.setStrength(rs.getString("STRENGTH"));
					drug.setDosageForm(rs.getString("DOSAGE_FORM"));
					drug.setPlaStartDate(rs.getDate("CLAIM_START_DATE"));
					drug.setPlaEndDate(rs.getDate("CLAIM_END_DATE"));
					missingDataFromCheduleAMap.put(din, drug);
					
					if(yyPrice == 0){//2 price Din
						twoPriceDinList.add(din);
					}else{
						threePriceDinList.add(din);
					}
				}
				twoPriceDinListMap.put(manufacturerCode, twoPriceDinList);
				threePriceDinListMap.put(manufacturerCode,threePriceDinList);
				allManufacturerDinCache.put(manufacturerCode, missingDataFromCheduleAMap);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			DBConnectionManager.getManager().closeConnection(null, ps, rs);
		}

		return missingDataFromCheduleAMap;
	}


	public static List<String> getTwoPriceDinList(Connection conn,String manufacturerCode) throws Exception{
		List<String> twoPriceDinList = twoPriceDinListMap.get(manufacturerCode);
		if(twoPriceDinList == null){
			loadMissingDataFromScheduleA(conn,manufacturerCode);
		}

		return twoPriceDinListMap.get(manufacturerCode);
	}

	public static List<String> getThreePriceDinList(Connection conn,String manufacturerCode) throws Exception{
		List<String> threePriceDinList = threePriceDinListMap.get(manufacturerCode);
		if(threePriceDinList == null){
			loadMissingDataFromScheduleA(conn,manufacturerCode);
		}

		return threePriceDinListMap.get(manufacturerCode);
	}


	public static Map<String, Double> getDbpLimiMap(Connection conn,String manufacturerCode) throws Exception{
		Map<String, Double> dbpLimiMap = allManufacturerDBPLimiMap.get(manufacturerCode);
		if(dbpLimiMap == null){
			return loadDbpLimitData(conn, manufacturerCode);
		}
		return dbpLimiMap;
	}


	public static Map<String, Map<String, RebateVO>> getAllManufacturerDinCache() {
		return allManufacturerDinCache;
	}

	public static Map<String, RebateVO> getMissingDataFromCheduleAMap(Connection conn,String manufacturerCode) throws Exception {
		Map<String, RebateVO> missingDataFromCheduleAMap = allManufacturerDinCache.get(manufacturerCode);
		if(missingDataFromCheduleAMap == null){
			return loadMissingDataFromScheduleA(conn, manufacturerCode);
		}
		return missingDataFromCheduleAMap;
	}

}
