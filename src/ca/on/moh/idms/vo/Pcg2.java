package ca.on.moh.idms.vo;

import java.util.List;

import gov.moh.config.vo.ValueObject;

public class Pcg2 implements ValueObject {

	private String id;
	private String name;
	private List<Pcg6> pcg6List;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Pcg6> getPcg6List() {
		return pcg6List;
	}
	public void setPcg6List(List<Pcg6> pcg6List) {
		this.pcg6List = pcg6List;
	}

	
}
