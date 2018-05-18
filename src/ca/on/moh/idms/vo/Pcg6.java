package ca.on.moh.idms.vo;

import java.util.List;

import gov.moh.config.vo.ValueObject;

public class Pcg6 implements ValueObject {
	

	private String id;
	private String name;
	List<GenericName> genericNameList;

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

	public List<GenericName> getGenericNameList() {
		return genericNameList;
	}

	public void setGenericNameList(List<GenericName> genericNameList) {
		this.genericNameList = genericNameList;
	}
	
}
