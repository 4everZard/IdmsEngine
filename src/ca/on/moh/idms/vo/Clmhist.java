package ca.on.moh.idms.vo;

/**
 * Automatically generated value object calss. 
 * If any questions, contact Davy Hu (hu_davy@yahoo.com)
 */

import gov.moh.config.vo.ValueObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Clmhist implements ValueObject {

	static final long serialVersionUID = 6825275;

	public static final String MED_COND = "MED_COND";
	public static final String MOH_PRESCRIBER_ID = "MOH_PRESCRIBER_ID";
	public static final String ODB_ELIG_NO = "ODB_ELIG_NO";
	public static final String THERA_CLASS = "THERA_CLASS";
	public static final String INTERVENTION_10 = "INTERVENTION_10";
	public static final String INTERVENTION_9 = "INTERVENTION_9";
	public static final String INTERVENTION_8 = "INTERVENTION_8";
	public static final String INTERVENTION_7 = "INTERVENTION_7";
	public static final String INTERVENTION_6 = "INTERVENTION_6";
	public static final String INTERVENTION_5 = "INTERVENTION_5";
	public static final String INTERVENTION_4 = "INTERVENTION_4";
	public static final String INTERVENTION_3 = "INTERVENTION_3";
	public static final String INTERVENTION_2 = "INTERVENTION_2";
	public static final String INTERVENTION_1 = "INTERVENTION_1";
	public static final String PROG_ID = "PROG_ID";
	public static final String CURR_STAT = "CURR_STAT";
	public static final String PROD_SEL = "PROD_SEL";
	public static final String CURR_RX_NO = "CURR_RX_NO";
	public static final String TOT_AMT_PD = "TOT_AMT_PD";
	public static final String DEDUCT_TO_COLLECT = "DEDUCT_TO_COLLECT";
	public static final String COMP_FEE_ALLD = "COMP_FEE_ALLD";
	public static final String PROF_FEE_ALLD = "PROF_FEE_ALLD";
	public static final String CST_UPCHRG_ALLD = "CST_UPCHRG_ALLD";
	public static final String DRG_CST_ALLD = "DRG_CST_ALLD";
	public static final String QTY = "QTY";
	public static final String ADJUDICATION_DT = "ADJUDICATION_DT";
	public static final String DT_OF_SERV = "DT_OF_SERV";
	public static final String LTC_FLAG = "LTC_FLAG";
	public static final String DIN_PIN = "DIN_PIN";
	public static final String AGENCY_ID = "AGENCY_ID";
	public static final String CLAIM_ID = "CLAIM_ID";
	public static final String POSTAL_CD = "POSTAL_CD";
	public static final String AG_TYPE_CD = "AG_TYPE_CD";
	public static final String COUNTY_CD = "COUNTY_CD";
	public static final String PLAN_CD = "PLAN_CD";
	public static final String DYS_SUPPLY = "DYS_SUPPLY";

	private int medCond = Integer.MIN_VALUE;
	private String mohPrescriberId;
	private String odbEligNo;
	private String theraClass;
	private String intervention10;
	private String intervention9;
	private String intervention8;
	private String intervention7;
	private String intervention6;
	private String intervention5;
	private String intervention4;
	private String intervention3;
	private String intervention2;
	private String intervention1;
	private String progId;
	private String currStat;
	private double prodSel = Integer.MIN_VALUE;
	private String currRxNo;
	private double totAmtPd = Integer.MIN_VALUE;
	private double deductToCollect = Integer.MIN_VALUE;
	private int compFeeAlld = Integer.MIN_VALUE;
	private int profFeeAlld = Integer.MIN_VALUE;
	private double cstUpchrgAlld = Integer.MIN_VALUE;
	private double drgCstAlld = Integer.MIN_VALUE;
	private double qty = Integer.MIN_VALUE;
	private java.sql.Date adjudicationDt;
	private java.sql.Date dtOfServ;
	private int ltcFlag = Integer.MIN_VALUE;
	private String dinPin;
	private int agencyId = Integer.MIN_VALUE;
	private long claimId = Integer.MIN_VALUE;
	private String postalCd;
	private String agTypeCd;
	private String countyCd;
	private String planCd;
	private int dysSupply = Integer.MIN_VALUE;
	private int indexInList;

	private Map <Integer,String> orderByMap = new HashMap  <Integer,String>();
 	private List <Integer>columnList = new ArrayList <Integer>();
 	private int [] neededColumns;
 
	public int getMedCond(){
		return medCond;
	}
	public String getMohPrescriberId(){
		return mohPrescriberId;
	}
	public String getOdbEligNo(){
		return odbEligNo;
	}
	public String getTheraClass(){
		return theraClass;
	}
	public String getIntervention10(){
		return intervention10;
	}
	public String getIntervention9(){
		return intervention9;
	}
	public String getIntervention8(){
		return intervention8;
	}
	public String getIntervention7(){
		return intervention7;
	}
	public String getIntervention6(){
		return intervention6;
	}
	public String getIntervention5(){
		return intervention5;
	}
	public String getIntervention4(){
		return intervention4;
	}
	public String getIntervention3(){
		return intervention3;
	}
	public String getIntervention2(){
		return intervention2;
	}
	public String getIntervention1(){
		return intervention1;
	}
	public String getProgId(){
		return progId;
	}
	public String getCurrStat(){
		return currStat;
	}
	public double getProdSel(){
		return prodSel;
	}
	public String getCurrRxNo(){
		return currRxNo;
	}
	public double getTotAmtPd(){
		return totAmtPd;
	}
	public double getDeductToCollect(){
		return deductToCollect;
	}
	public int getCompFeeAlld(){
		return compFeeAlld;
	}
	public int getProfFeeAlld(){
		return profFeeAlld;
	}
	public double getCstUpchrgAlld(){
		return cstUpchrgAlld;
	}
	public double getDrgCstAlld(){
		return drgCstAlld;
	}
	public double getQty(){
		return qty;
	}
	public java.sql.Date getAdjudicationDt(){
		return adjudicationDt;
	}
	public java.sql.Date getDtOfServ(){
		return dtOfServ;
	}
	public int getLtcFlag(){
		return ltcFlag;
	}
	public String getDinPin(){
		return dinPin;
	}
	public int getAgencyId(){
		return agencyId;
	}
	public long getClaimId(){
		return claimId;
	}
	public String getPostalCd(){
		return postalCd;
	}
	public String getAgTypeCd(){
		return agTypeCd;
	}
	public String getCountyCd(){
		return countyCd;
	}
	public String getPlanCd(){
		return planCd;
	}
	public int getDysSupply(){
		return dysSupply;
	}

	public void setMedCond (int newValue){
		medCond = newValue;
	}
	public void setMohPrescriberId (String newValue){
		mohPrescriberId = newValue;
	}
	public void setOdbEligNo (String newValue){
		odbEligNo = newValue;
	}
	public void setTheraClass (String newValue){
		theraClass = newValue;
	}
	public void setIntervention10 (String newValue){
		intervention10 = newValue;
	}
	public void setIntervention9 (String newValue){
		intervention9 = newValue;
	}
	public void setIntervention8 (String newValue){
		intervention8 = newValue;
	}
	public void setIntervention7 (String newValue){
		intervention7 = newValue;
	}
	public void setIntervention6 (String newValue){
		intervention6 = newValue;
	}
	public void setIntervention5 (String newValue){
		intervention5 = newValue;
	}
	public void setIntervention4 (String newValue){
		intervention4 = newValue;
	}
	public void setIntervention3 (String newValue){
		intervention3 = newValue;
	}
	public void setIntervention2 (String newValue){
		intervention2 = newValue;
	}
	public void setIntervention1 (String newValue){
		intervention1 = newValue;
	}
	public void setProgId (String newValue){
		progId = newValue;
	}
	public void setCurrStat (String newValue){
		currStat = newValue;
	}
	public void setProdSel (double newValue){
		prodSel = newValue;
	}
	public void setCurrRxNo (String newValue){
		currRxNo = newValue;
	}
	public void setTotAmtPd (double newValue){
		totAmtPd = newValue;
	}
	public void setDeductToCollect (double newValue){
		deductToCollect = newValue;
	}
	public void setCompFeeAlld (int newValue){
		compFeeAlld = newValue;
	}
	public void setProfFeeAlld (int newValue){
		profFeeAlld = newValue;
	}
	public void setCstUpchrgAlld (double newValue){
		cstUpchrgAlld = newValue;
	}
	public void setDrgCstAlld (double newValue){
		drgCstAlld = newValue;
	}
	public void setQty (double newValue){
		qty = newValue;
	}
	public void setAdjudicationDt (java.sql.Date newValue){
		adjudicationDt = newValue;
	}
	public void setDtOfServ (java.sql.Date newValue){
		dtOfServ = newValue;
	}
	public void setLtcFlag (int newValue){
		ltcFlag = newValue;
	}
	public void setDinPin (String newValue){
		dinPin = newValue;
	}
	public void setAgencyId (int newValue){
		agencyId = newValue;
	}
	public void setClaimId (long newValue){
		claimId = newValue;
	}
	public void setPostalCd (String newValue){
		postalCd = newValue;
	}
	public void setAgTypeCd (String newValue){
		agTypeCd = newValue;
	}
	public void setCountyCd (String newValue){
		countyCd = newValue;
	}
	public void setPlanCd (String newValue){
		planCd = newValue;
	}
	public void setDysSupply (int newValue){
		dysSupply = newValue;
	}

	public int getIndexInList(){
		return indexInList;
	}

	public void setIndexInList(int indexInList){
		this.indexInList = indexInList;
	}


	public void setOrderByMap(String column){
		int position = orderByMap.size();
		orderByMap.put(new Integer(position),column);
	}

	public Map <Integer, String> getOrderByMap(){
		if(orderByMap.size() == 0){
			return null;
		}else{
			return orderByMap;
		}
	}

	/**
	 * get an array of the needed columns represented by int
	 * @return int [].
	 */
	public int [] getNeededColumns(){
		neededColumns = new int[columnList.size()];
		for(int i=0; i<columnList.size(); i++){
			neededColumns [i] = ((Integer)columnList.get(i)).intValue();
		}
		return neededColumns;
	}

	/**
	 * set the needed column list.
	 * @param column int reppresenting the column.
	 */
	public void setNeededColumn(int column){
		columnList.add(new Integer(column));
	}
}

