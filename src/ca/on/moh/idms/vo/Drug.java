package ca.on.moh.idms.vo;

/**
 * Automatically generated value object calss. 
 * If any questions, contact Davy Hu (hu_davy@yahoo.com)
 */

import gov.moh.config.vo.ValueObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Drug implements ValueObject {

	static final long serialVersionUID = 5731816;

	public static final String DRUG_ID = "DRUG_ID";
	public static final String DIN_PIN = "DIN_PIN";
	public static final String DIN_DESC = "DIN_DESC";
	public static final String GEN_NAME = "GEN_NAME";
	public static final String AGENCY_ID = "AGENCY_ID";
	public static final String REC_INACTIVE_TIMESTAMP = "REC_INACTIVE_TIMESTAMP";
	public static final String DBP_LIM_JL115 = "DBP_LIM_JL115";
	public static final String STRENGTH = "STRENGTH";
	public static final String DOSAGE_FORM = "DOSAGE_FORM";
	public static final String MANUFACTURER_CD = "MANUFACTURER_CD";
	public static final String REC_EFF_DT = "REC_EFF_DT";
	public static final String INDIVIDUAL_PRICE = "INDIVIDUAL_PRICE";

	private int drugId = Integer.MIN_VALUE;
	private String dinPin;
	private String dinDesc;
	private String genName;
	private int agencyId = Integer.MIN_VALUE;
	private java.sql.Date recInactiveTimestamp;
	private double dbpLimJl115 = Integer.MIN_VALUE;
	private String strength;
	private String dosageForm;
	private String manufacturerCd;
	private java.sql.Date recEffDt;
	private double individualPrice = Integer.MIN_VALUE;
	private int indexInList;
	private java.sql.Date recCreateTimestamp;
	/*
	 * for XML formulary
	 */
	private String id;
	private String sec3;
	private String sec12;
	private String manufacturerId;
	private Date listingDate;
	private double amountMOHLTCPays;
	private String notABenefit;
	
	private Map <Integer,String> orderByMap = new HashMap  <Integer,String>();
 	private List <Integer>columnList = new ArrayList <Integer>();
 	private int [] neededColumns;
 
	public int getDrugId(){
		return drugId;
	}
	public String getDinPin(){
		return dinPin;
	}
	public String getDinDesc(){
		return dinDesc;
	}
	public String getGenName(){
		return genName;
	}
	public int getAgencyId(){
		return agencyId;
	}
	public java.sql.Date getRecInactiveTimestamp(){
		return recInactiveTimestamp;
	}
	public double getDbpLimJl115(){
		return dbpLimJl115;
	}
	public String getStrength(){
		return strength;
	}
	public String getDosageForm(){
		return dosageForm;
	}
	public String getManufacturerCd(){
		return manufacturerCd;
	}
	public java.sql.Date getRecEffDt(){
		return recEffDt;
	}
	public double getIndividualPrice(){
		return individualPrice;
	}

	public void setDrugId (int newValue){
		drugId = newValue;
	}
	public void setDinPin (String newValue){
		dinPin = newValue;
	}
	public void setDinDesc (String newValue){
		dinDesc = newValue;
	}
	public void setGenName (String newValue){
		genName = newValue;
	}
	public void setAgencyId (int newValue){
		agencyId = newValue;
	}
	public void setRecInactiveTimestamp (java.sql.Date newValue){
		recInactiveTimestamp = newValue;
	}
	public void setDbpLimJl115 (double newValue){
		dbpLimJl115 = newValue;
	}
	public void setStrength (String newValue){
		strength = newValue;
	}
	public void setDosageForm (String newValue){
		dosageForm = newValue;
	}
	public void setManufacturerCd (String newValue){
		manufacturerCd = newValue;
	}
	public void setRecEffDt (java.sql.Date newValue){
		recEffDt = newValue;
	}
	public void setIndividualPrice (double newValue){
		individualPrice = newValue;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSec3() {
		return sec3;
	}
	public void setSec3(String sec3) {
		this.sec3 = sec3;
	}
	public String getSec12() {
		return sec12;
	}
	public void setSec12(String sec12) {
		this.sec12 = sec12;
	}
	public String getManufacturerId() {
		return manufacturerId;
	}
	public void setManufacturerId(String manufacturerId) {
		this.manufacturerId = manufacturerId;
	}
	public Date getListingDate() {
		return listingDate;
	}
	public void setListingDate(Date listingDate) {
		this.listingDate = listingDate;
	}
	public double getAmountMOHLTCPays() {
		return amountMOHLTCPays;
	}
	public void setAmountMOHLTCPays(double amountMOHLTCPays) {
		this.amountMOHLTCPays = amountMOHLTCPays;
	}
	public String getNotABenefit() {
		return notABenefit;
	}
	public void setNotABenefit(String notABenefit) {
		this.notABenefit = notABenefit;
	}
	public java.sql.Date getRecCreateTimestamp() {
		return recCreateTimestamp;
	}
	public void setRecCreateTimestamp(java.sql.Date recCreateTimestamp) {
		recCreateTimestamp = recCreateTimestamp;
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

