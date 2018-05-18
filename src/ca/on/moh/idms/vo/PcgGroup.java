package ca.on.moh.idms.vo;

import gov.moh.config.vo.ValueObject;

import java.util.List;

public class PcgGroup implements ValueObject {

	private String lccId;
	private String name;
	List<Pcg9> pcg9List;
	
	public String getLccId() {
		return lccId;
	}
	public void setLccId(String lccId) {
		this.lccId = lccId;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String pcgGroupname) {
		this.name = pcgGroupname;
	}
	public List<Pcg9> getPcg9List() {
		return pcg9List;
	}
	public void setPcg9List(List<Pcg9> pcg9List) {
		this.pcg9List = pcg9List;
	}

}
