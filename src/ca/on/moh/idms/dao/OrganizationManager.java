package ca.on.moh.idms.dao;

/**
 * Automatically generated DAO calss. 
 * If any questions, contact Davy Hu (hu_davy@yahoo.com)
 */

import gov.moh.config.vo.ValueObject;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import ca.on.moh.idms.persister.OrganizationPersister;
import ca.on.moh.idms.persister.OrganizationPersisterImpl;
import ca.on.moh.idms.vo.Organization;


public class OrganizationManager {

	static final long serialVersionUID = 831019;
	private OrganizationPersister persister = new OrganizationPersisterImpl();

	/**
	 * Find the record from the entity table by primary key.
	 * convenient method only for the table which has a long PK.
	 * return null for entity with PK which is not int and long.
	 *
	 * @param id long, primary key.
	 *
	 * @return vo ValueObject.
	 */
	public ValueObject findByPK(long pk) throws Exception {
		return persister.findByPK((int)pk);
	}

	/**
	 * Find the business object from the entity table by primary key defined in the ValueObject.
	 *
	 * @param vo ValueObject defining the values of primary key.
	 *
	 * @return a ValueObject.
	 */
	public ValueObject findByPK(ValueObject vo)throws Exception {
		Organization obj = (Organization)vo;
		return  persister.findByPK(obj.getOrganizationId());
	}

	/**
	 * Find all records in the entity table.
	 *
	 * @return List of ValueObject.
	 */
	public List <ValueObject> findAll() throws Exception{
		return persister.findAll();
	}

	/**
	 * Find all records stored in the entity table sorted based on the condition defined in the ValueObject.
	 *
	 * @param vo ValueObject defining the sorting order.
	 * @return List of sorted ValueObject.
	 */
	public List <ValueObject> findAll(ValueObject vo) throws Exception{
		if(vo != null){
			try{
				Organization obj = (Organization)vo;
				String orderbyStr = null;
				Map <Integer, String> orderByMap = obj.getOrderByMap();
				if (orderByMap != null){
					for (int i=0; i<orderByMap.size(); i++){
					    String column = (String)orderByMap.get(new Integer(i));
					    if(orderbyStr == null){
					        orderbyStr = " order by " + column;
					    }else{
					        orderbyStr = orderbyStr + ", " + column;
					    }
					}
				}
				if(orderbyStr != null){
					orderbyStr = orderbyStr.substring(0,orderbyStr.length());
				}
				return persister.findAll(orderbyStr);
			}catch (SQLException e){
				throw e;
			}
		}else{
			return persister.findAll();
		}
	}

	/**
	 * Find the record from the entity by teh condition defiend in the value object.
	 * In this method, string uses "like" in the where condition. All the rest uses "equal" in 
	 * in the where condition. 
	 *
	 * @param vo ValueObject	 *
	 * @return List of ValueObject.
	 */
	public List <ValueObject> findByObject(ValueObject vo)throws Exception {
		List <ValueObject> volist = null;
		Organization obj = (Organization)vo;
		String where = "where";
		if(obj.getOrganizationName() != null && !obj.getOrganizationName().trim().equals("")){ 
			where = where + " UPPER(ORGANIZATION_NAME) like UPPER('%" + obj.getOrganizationName() + "%') AND"; 
		}
		if(obj.getOrganizationType() != Integer.MIN_VALUE && obj.getOrganizationType() != 0){ 
			where = where + " ORGANIZATION_TYPE=" + obj.getOrganizationType() + " AND"; 
		}
		if(obj.getOrganizationCode() != null && !obj.getOrganizationCode().trim().equals("")){ 
			where = where + " UPPER(ORGANIZATION_CODE) like UPPER('%" + obj.getOrganizationCode() + "%') AND"; 
		}
		if(obj.getCreatedDate() != null){ 
			SimpleDateFormat format = new SimpleDateFormat(ValueObject.SIMPLE_DATE_FORMAT);
			String aString = format.format( new java.sql.Timestamp(obj.getCreatedDate().getTime()));
			where = where + " CREATED_DATE = to_date('" + aString + "','" + ValueObject.SQL_DATE_FORMAT + "') AND"; 
		}
		if(obj.getOrganizationId() != Integer.MIN_VALUE && obj.getOrganizationId() != 0){ 
			where = where + " ORGANIZATION_ID=" + obj.getOrganizationId() + " AND"; 
		}
		if(obj.getClosedDate() != null){ 
			SimpleDateFormat format = new SimpleDateFormat(ValueObject.SIMPLE_DATE_FORMAT);
			String aString = format.format( new java.sql.Timestamp(obj.getClosedDate().getTime()));
			where = where + " CLOSED_DATE = to_date('" + aString + "','" + ValueObject.SQL_DATE_FORMAT + "') AND"; 
		}
		int andIndex = where.lastIndexOf("AND");
		if (andIndex != -1) {
			where = where.substring(0, andIndex);
		}else{
			int whereIndex = where.indexOf("where");
			if (whereIndex != -1) {
				where = where.substring(0, whereIndex);
			}
		}
		String orderbyStr = null;
		Map <Integer, String>  orderByMap = obj.getOrderByMap();
		if (orderByMap != null){
			for (int i=0; i<orderByMap.size(); i++){
			    String column = (String)orderByMap.get(new Integer(i));
			    if(orderbyStr == null){
			        orderbyStr = " order by " + column;
			    }else{
			        orderbyStr = orderbyStr + ", " + column;
			    }
			}
		}
		if(orderbyStr != null){
			orderbyStr = orderbyStr.substring(0,orderbyStr.length());
			where = where + orderbyStr;
		}
		volist = persister.findByWhere(where);
		if(volist == null || volist.size() == 0){
			return null;
		}else{
			return volist;
		}
	}

	/**
	 * Find all records in the entity table with a constraints defined in the where clause.
	 *
	 * @param where String	 * @return List of ValueObject.
	 */
	public List <ValueObject> findByWhereClause(String where) throws Exception{
		return persister.findByWhere(where);
	}

	/**
	 * Find the records with limited columns defined by client for performance purpose.
	 *
	 * @param vo ValueObject defining needed columns and sort conditions.
	 *
	 * @return List of ValueObject.
	 */
	public List <ValueObject> findNeedColumnObject(ValueObject vo)throws Exception {
		List <ValueObject> volist = null;
		Organization obj = (Organization)vo;
		int neededColumns [] =  obj.getNeededColumns();
		String where = " where";
		if(obj.getOrganizationName() != null && !obj.getOrganizationName().trim().equals("")){ 
			where = where + " UPPER(ORGANIZATION_NAME) like UPPER('%" + obj.getOrganizationName() + "%') AND"; 
		}
		if(obj.getOrganizationType() != Integer.MIN_VALUE && obj.getOrganizationType() != 0){ 
			where = where + " ORGANIZATION_TYPE=" + obj.getOrganizationType() + " AND"; 
		}
		if(obj.getOrganizationCode() != null && !obj.getOrganizationCode().trim().equals("")){ 
			where = where + " UPPER(ORGANIZATION_CODE) like UPPER('%" + obj.getOrganizationCode() + "%') AND"; 
		}
		if(obj.getCreatedDate() != null){ 
			SimpleDateFormat format = new SimpleDateFormat(ValueObject.SIMPLE_DATE_FORMAT);
			String aString = format.format( new java.sql.Timestamp(obj.getCreatedDate().getTime()));
			where = where + " CREATED_DATE = to_date('" + aString + "','" + ValueObject.SQL_DATE_FORMAT + "') AND"; 
		}
		if(obj.getOrganizationId() != Integer.MIN_VALUE && obj.getOrganizationId() != 0){ 
			where = where + " ORGANIZATION_ID=" + obj.getOrganizationId() + " AND"; 
		}
		if(obj.getClosedDate() != null){ 
			SimpleDateFormat format = new SimpleDateFormat(ValueObject.SIMPLE_DATE_FORMAT);
			String aString = format.format( new java.sql.Timestamp(obj.getClosedDate().getTime()));
			where = where + " CLOSED_DATE = to_date('" + aString + "','" + ValueObject.SQL_DATE_FORMAT + "') AND"; 
		}
		int andIndex = where.lastIndexOf("AND");
		if (andIndex != -1) {
			where = where.substring(0, andIndex);
		}else{
			int whereIndex = where.indexOf("where");
			if (whereIndex != -1) {
				where = where.substring(0, whereIndex);
			}
		}
		String orderbyStr = null;
		Map <Integer, String> orderByMap = obj.getOrderByMap();
		if (orderByMap != null){
			for (int i=0; i<orderByMap.size(); i++){
			    String column = (String)orderByMap.get(new Integer(i));
			    if(orderbyStr == null){
			        orderbyStr = " order by " + column;
			    }else{
			        orderbyStr = orderbyStr + ", " + column;
			    }
			}
		}
		if(orderbyStr != null){
			orderbyStr = orderbyStr.substring(0,orderbyStr.length());
			where = where + orderbyStr;
		}
			volist = persister.findByWhere(where, neededColumns);
		if(volist == null || volist.size() == 0){
			return null;
		}else{
			return volist;
		}
	}

	/**
	 * Create an new record of the entity table in database.
	 *
	 * @param vo ValueObject with the value to be persisted.
	 *
	 * @return ValueObject.
	 */
	public ValueObject executeCreate(ValueObject vo) throws Exception {
		persister.save(vo);
		return vo;
	}

	/**
	 * Update a business object and save this object in the entity table.
	 *
	 * @param vo ValueObject.
	 */
	public ValueObject update(ValueObject vo)throws SQLException, Exception {
		persister.update(vo);
		return vo;
	}

	/**
	 * Update a record to the values defined in the ValueObject with where constraints.
	 *
	 * @param vo ValueObject.
	 * @param where String.
	 */
	public ValueObject updateByWhereClause(ValueObject vo, String where) throws Exception {
		persister.update(vo,where);
		return vo;
	}

	/**
	 * Delete the record in the entity table by PK defined by the value object.
	 *
	 * @param vo ValueObject, value object containing the PK of the record to be deleted.
	 */
	public void executeDelete(ValueObject vo) throws Exception {
		Organization obj = (Organization)vo;
		persister.deleteByPK(obj.getOrganizationId());
	}

	/**
	 * delete one record from the entity table the conditions defined in the value object.
	 *
	 * @param vo ValueObject, value object defining delete conditions	 *
	 * @return rows int.
	 */
	public int deleteByObject(ValueObject vo)throws Exception {
		Organization obj = (Organization)vo;
		int rows = 0;
		String where = " where";
		if(obj.getOrganizationName() != null && !obj.getOrganizationName().trim().equals("")){ 
			where = where + " UPPER(ORGANIZATION_NAME) like UPPER('%" + obj.getOrganizationName() + "%') AND"; 
		}
		if(obj.getOrganizationType() != Integer.MIN_VALUE && obj.getOrganizationType() != 0){ 
			where = where + " ORGANIZATION_TYPE=" + obj.getOrganizationType() + " AND"; 
		}
		if(obj.getOrganizationCode() != null && !obj.getOrganizationCode().trim().equals("")){ 
			where = where + " UPPER(ORGANIZATION_CODE) like UPPER('%" + obj.getOrganizationCode() + "%') AND"; 
		}
		if(obj.getCreatedDate() != null){ 
			SimpleDateFormat format = new SimpleDateFormat(ValueObject.SIMPLE_DATE_FORMAT);
			String aString = format.format( new java.sql.Timestamp(obj.getCreatedDate().getTime()));
			where = where + " CREATED_DATE = to_date('" + aString + "','" + ValueObject.SQL_DATE_FORMAT + "') AND"; 
		}
		if(obj.getOrganizationId() != Integer.MIN_VALUE && obj.getOrganizationId() != 0){ 
			where = where + " ORGANIZATION_ID=" + obj.getOrganizationId() + " AND"; 
		}
		if(obj.getClosedDate() != null){ 
			SimpleDateFormat format = new SimpleDateFormat(ValueObject.SIMPLE_DATE_FORMAT);
			String aString = format.format( new java.sql.Timestamp(obj.getClosedDate().getTime()));
			where = where + " CLOSED_DATE = to_date('" + aString + "','" + ValueObject.SQL_DATE_FORMAT + "') AND"; 
		}
		int andIndex = where.lastIndexOf("AND");
		if (andIndex != -1) {
			where = where.substring(0, andIndex);
		}else{
			int whereIndex = where.indexOf("where");
			if (whereIndex != -1) {
				where = where.substring(0, whereIndex);
			}
		}
		if(where != null && where.substring(6) != null && !where.substring(6).trim().equals("")){
			rows = persister.deleteByWhere(where);
		}else{
			throw new Exception("You can not delete all records of the table!");
		}
		return rows;
	}

	/**
	 * Delete a record by where constraints.
	 *
	 * @param where String.
	 */
	public void deleteByWhereClause(String where) throws Exception {
		persister.deleteByWhere(where);
	}

	/**
	 * Performance method to create more than one record in one transaction.
	 *
	 * @param volist, List of value objects.
	 *
	 * @return number of records created.
	 * @throws Exception.
	 */
	public int[] batchCreate(List <ValueObject> voList) throws Exception {
		return persister.batchCreate(voList);
	}

	/**
	 * Performance method to update more than one record in one transaction.
	 *
	 * @param voList, List of value objects.
	 *
	 * @return number of records updated.
	 * @throws Exception.
	 */
	public int[] batchUpdate(List <ValueObject> voList) throws Exception {
		return persister.batchUpdate(voList);
	}

	/**
	 * Performance method to delete more than one record in one transaction.
	 *
	 * @param voList, List of value objects.
	 *
	 * @return number of records deleted.
	 * @throws Exception.
	 */
	public int[] batchDelete(List <ValueObject> voList) throws Exception {
		return persister.batchDelete(voList);
	}

}

