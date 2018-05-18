package ca.on.moh.idms.vo;

import java.util.List;

import gov.moh.config.vo.ValueObject;

public class Pcg9 implements ValueObject {

	private String id;
	private String itemNumber;
	private String strength;
	private String dosageForm;
	private List<Drug> drugList;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getItemNumber() {
		return itemNumber;
	}
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
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
	public List<Drug> getDrugList() {
		return drugList;
	}
	public void setDrugList(List<Drug> drugList) {
		this.drugList = drugList;
	}
	
	
}
