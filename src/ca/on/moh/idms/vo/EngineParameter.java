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

public class EngineParameter implements ValueObject {

	static final long serialVersionUID = 162504;

	public static final String PARAMETER_ID = "PARAMETER_ID";
	public static final String PARAMETER_VALUE = "PARAMETER_VALUE";
	public static final String EFFECTIVE_DATE = "EFFECTIVE_DATE";
	public static final String END_DATE = "END_DATE";
	public static final String PARAMETER_CREATED_BY = "PARAMETER_CREATED_BY";
	public static final String PARAMETER_UPDATED_BY = "PARAMETER_UPDATED_BY";
	public static final String PARAMETER_NAME = "PARAMETER_NAME";

	private int parameterId = Integer.MIN_VALUE;
	private String parameterValue;
	private java.sql.Timestamp effectiveDate;
	private String endDate;
	private int parameterCreatedBy = Integer.MIN_VALUE;
	private int parameterUpdatedBy = Integer.MIN_VALUE;
	private String parameterName;
	private int indexInList;

	private Map <Integer,String> orderByMap = new HashMap  <Integer,String>();
 	private List <Integer>columnList = new ArrayList <Integer>();
 	private int [] neededColumns;
 
	public int getParameterId(){
		return parameterId;
	}
	public String getParameterValue(){
		return parameterValue;
	}
	public java.sql.Timestamp getEffectiveDate(){
		return effectiveDate;
	}
	public String getEndDate(){
		return endDate;
	}
	public int getParameterCreatedBy(){
		return parameterCreatedBy;
	}
	public int getParameterUpdatedBy(){
		return parameterUpdatedBy;
	}
	public String getParameterName(){
		return parameterName;
	}

	public void setParameterId (int newValue){
		parameterId = newValue;
	}
	public void setParameterValue (String newValue){
		parameterValue = newValue;
	}
	public void setEffectiveDate (java.sql.Timestamp newValue){
		effectiveDate = newValue;
	}
	public void setEndDate (String newValue){
		endDate = newValue;
	}
	public void setParameterCreatedBy (int newValue){
		parameterCreatedBy = newValue;
	}
	public void setParameterUpdatedBy (int newValue){
		parameterUpdatedBy = newValue;
	}
	public void setParameterName (String newValue){
		parameterName = newValue;
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

