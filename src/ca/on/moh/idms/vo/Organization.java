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

public class Organization implements ValueObject {

	static final long serialVersionUID = 831019;

	public static final String ORGANIZATION_NAME = "ORGANIZATION_NAME";
	public static final String ORGANIZATION_TYPE = "ORGANIZATION_TYPE";
	public static final String ORGANIZATION_CODE = "ORGANIZATION_CODE";
	public static final String CREATED_DATE = "CREATED_DATE";
	public static final String ORGANIZATION_ID = "ORGANIZATION_ID";
	public static final String CLOSED_DATE = "CLOSED_DATE";

	private String organizationName;
	private int organizationType = Integer.MIN_VALUE;
	private String organizationCode;
	private java.sql.Timestamp createdDate;
	private int organizationId = Integer.MIN_VALUE;
	private java.sql.Timestamp closedDate;
	private int indexInList;

	private Map <Integer,String> orderByMap = new HashMap  <Integer,String>();
 	private List <Integer>columnList = new ArrayList <Integer>();
 	private int [] neededColumns;
 
 	
	private String id;
	private String name;
	
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
	 	
 	
	public String getOrganizationName(){
		return organizationName;
	}
	public int getOrganizationType(){
		return organizationType;
	}
	public String getOrganizationCode(){
		return organizationCode;
	}
	public java.sql.Timestamp getCreatedDate(){
		return createdDate;
	}
	public int getOrganizationId(){
		return organizationId;
	}
	public java.sql.Timestamp getClosedDate(){
		return closedDate;
	}

	public void setOrganizationName (String newValue){
		organizationName = newValue;
	}
	public void setOrganizationType (int newValue){
		organizationType = newValue;
	}
	public void setOrganizationCode (String newValue){
		organizationCode = newValue;
	}
	public void setCreatedDate (java.sql.Timestamp newValue){
		createdDate = newValue;
	}
	public void setOrganizationId (int newValue){
		organizationId = newValue;
	}
	public void setClosedDate (java.sql.Timestamp newValue){
		closedDate = newValue;
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

