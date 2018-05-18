package ca.on.moh.idms.vo;

import gov.moh.config.vo.ValueObject;

import java.util.Date;
import java.util.List;

public class Extract implements ValueObject {

	private Date extractDate;
	private List<Organization> manufacturerList;
	private Formulary formulary;
	
	public Date getExtractDate() {
		return extractDate;
	}
	public void setExtractDate(Date extractDate) {
		this.extractDate = extractDate;
	}
	public List<Organization> getManufacturerList() {
		return manufacturerList;
	}
	public void setManufacturerList(List<Organization> manufacturerList) {
		this.manufacturerList = manufacturerList;
	}
	public Formulary getFormulary() {
		return formulary;
	}
	public void setFormulary(Formulary formulary) {
		this.formulary = formulary;
	}
	

}
