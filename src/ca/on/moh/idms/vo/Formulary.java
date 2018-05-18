package ca.on.moh.idms.vo;

import gov.moh.config.vo.ValueObject;

import java.util.Date;
import java.util.List;

public class Formulary implements ValueObject {

	private String edition;
	private String updateVer;
	private Date formularyDate;
	private Date createDate;
	private List<Pcg2> pcg2List;
	
	
	public String getEdition() {
		return edition;
	}
	public void setEdition(String edition) {
		this.edition = edition;
	}
	public String getUpdateVer() {
		return updateVer;
	}
	public void setUpdateVer(String updateVer) {
		this.updateVer = updateVer;
	}
	public Date getFormularyDate() {
		return formularyDate;
	}
	public void setFormularyDate(Date formularyDate) {
		this.formularyDate = formularyDate;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public List<Pcg2> getPcg2List() {
		return pcg2List;
	}
	public void setPcg2List(List<Pcg2> pcg2List) {
		this.pcg2List = pcg2List;
	}

	
}
