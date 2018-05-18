package ca.on.moh.idms.vo;

public class RebateVO extends Clmhist {

	private int drugId = Integer.MIN_VALUE;
	private String dinDesc;
	private String genName;
	private java.sql.Date recInactiveTimestamp;
	private double dbpLimJl115 = Integer.MIN_VALUE;
	private String strength;
	private String dosageForm;
	private String manufacturerCd;
	private java.sql.Date recEffDt;
	private java.sql.Date recCreateTimestamp;
	private double individualPrice = Integer.MIN_VALUE;
	
	private java.sql.Date plaStartDate;
	private java.sql.Date plaEndDate;
	
	private double firstPrice;
	private double secondPrice;
	private double yyyyPrice;
	private double volumeDiscount;
	
	public int getDrugId() {
		return drugId;
	}
	public void setDrugId(int drugId) {
		this.drugId = drugId;
	}
	public String getDinDesc() {
		return dinDesc;
	}
	public void setDinDesc(String dinDesc) {
		this.dinDesc = dinDesc;
	}
	public String getGenName() {
		return genName;
	}
	public void setGenName(String genName) {
		this.genName = genName;
	}
	public java.sql.Date getRecInactiveTimestamp() {
		return recInactiveTimestamp;
	}
	public void setRecInactiveTimestamp(java.sql.Date recInactiveTimestamp) {
		this.recInactiveTimestamp = recInactiveTimestamp;
	}
	public double getDbpLimJl115() {
		return dbpLimJl115;
	}
	public void setDbpLimJl115(double dbpLimJl115) {
		this.dbpLimJl115 = dbpLimJl115;
	}
	public String getStrength() {
		return strength;
	}
	public void setStrength(String strength) {
		this.strength = strength;
	}
	public String getDosageForm() {
		return dosageForm;
	}
	public void setDosageForm(String dosageForm) {
		this.dosageForm = dosageForm;
	}
	public String getManufacturerCd() {
		return manufacturerCd;
	}
	public void setManufacturerCd(String manufacturerCd) {
		this.manufacturerCd = manufacturerCd;
	}
	public java.sql.Date getRecEffDt() {
		return recEffDt;
	}
	public void setRecEffDt(java.sql.Date recEffDt) {
		this.recEffDt = recEffDt;
	}
	public java.sql.Date getRecCreateTimestamp() {
		return recCreateTimestamp;
	}
	public void setRecCreateTimestamp(java.sql.Date recCreateTimestamp) {
		this.recCreateTimestamp = recCreateTimestamp;
	}
	public double getIndividualPrice() {
		return individualPrice;
	}
	public void setIndividualPrice(double individualPrice) {
		this.individualPrice = individualPrice;
	}
	public double getFirstPrice() {
		return firstPrice;
	}
	public void setFirstPrice(double firstPrice) {
		this.firstPrice = firstPrice;
	}
	public double getSecondPrice() {
		return secondPrice;
	}
	public void setSecondPrice(double secondPrice) {
		this.secondPrice = secondPrice;
	}
	public double getYyyyPrice() {
		return yyyyPrice;
	}
	public void setYyyyPrice(double yyyyPrice) {
		this.yyyyPrice = yyyyPrice;
	}
	public java.sql.Date getPlaStartDate() {
		return plaStartDate;
	}
	public void setPlaStartDate(java.sql.Date plaStartDate) {
		this.plaStartDate = plaStartDate;
	}
	public java.sql.Date getPlaEndDate() {
		return plaEndDate;
	}
	public void setPlaEndDate(java.sql.Date plaEndDate) {
		this.plaEndDate = plaEndDate;
	}
	public double getVolumeDiscount() {
		return volumeDiscount;
	}
	public void setVolumeDiscount(double volumeDiscount) {
		this.volumeDiscount = volumeDiscount;
	}

	
}
