package ca.on.moh.idms.vo;

import java.util.List;

import gov.moh.config.vo.ValueObject;

public class GenericName implements ValueObject {

	private String id;
	private String name;
	private List<PcgGroup> pcgGroupList;
	
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
	public List<PcgGroup> getPcgGroupList() {
		return pcgGroupList;
	}
	public void setPcgGroupList(List<PcgGroup> pcgGroupList) {
		this.pcgGroupList = pcgGroupList;
	}

}
