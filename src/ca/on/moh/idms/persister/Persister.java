package ca.on.moh.idms.persister;

import gov.moh.config.vo.ValueObject;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public interface Persister extends Serializable {
	/**
	 * Find all the records in the table.
	 *
	 * @return list, the array of Valueobject.
	 */
	public List <ValueObject> findAll() throws Exception ;

	/**
	 * Find all the records in the table Valueobject in a given order
	 *
	 * @param orderBy String.
	 *
	 * @return list, the array of Valueobject.
	 */
	public List <ValueObject> findAll(String orderBy) throws Exception;
	/**
	 * Find all the records in the table Valueobject in a given order
	 *
	 * @param orderBy String.
	 * @param conn java.sql.Connection.
	 *
	 * @return list, the array of Valueobject.
	 */
	public List <ValueObject> findAll(String orderBy,Connection conn) throws Exception ;
	

	/**
	 * Find records by prepared statement.
	 *
	 * @param pstmt java.sql.PreparedStatement.
	 *
	 * @return list the array of Valueobject.
	 */
	public List <ValueObject> findByPreparedStatement(PreparedStatement pstmt) throws Exception;

	public List <ValueObject> findByPreparedStatement(PreparedStatement pstmt, int[] columnList) throws Exception;

	public List <ValueObject> findByPreparedStatement(PreparedStatement pstmt, int maxRows) throws Exception;

	public List <ValueObject> findByPreparedStatement(PreparedStatement pstmt, int[] columnList, int maxRows) throws Exception;

	
	/**
	 * Find records in the table by where clause.
	 *
	 * @param where String, where clause in SQL query.
	 *
	 * @return list the array of Valueobject.
	 */
	public List <ValueObject> findByWhere(String where) throws Exception ;

	public List <ValueObject> findByWhere(String where, int[] columnList) throws Exception;

	public List <ValueObject> findByWhere(String where, int[] columnList, int maxRows) throws Exception ;
	
	public List <ValueObject> findByWhere(String where, int maxRows) throws Exception;
	
	public List <ValueObject> findByWhere(String where, Connection conn) throws Exception;

	public List <ValueObject> findByWhere(String where, Connection conn, int[] columnList) throws Exception;
	
	public List <ValueObject> findByWhere(String where, Connection conn, int[] columnList, int maxRows) throws Exception;

	
	/**
	 * Save the records into the database.
	 *
	 * @param obj  valueObject with the record to be stored in the database.
	 *
	 * @return int, the number of records stored in the database.
	 */
	public int save(ValueObject obj) throws Exception;

	/**
	 * Save the records into the database.
	 *
	 * @param obj  database object with the record to be stored in the database.
	 *
	 * @return int, the number of records stored in the database.
	 */
	public int save(ValueObject obj, String dataSourceName) throws Exception;

	public int save(ValueObject obj, Connection conn) throws Exception;
	
	

	/**
	 * update the record to the given values in the ValueObject with the PK as constraint.
	 *
	 * @param obj  ValueObject.
	 *
	 * @return int, the number of records updated successfully.
	 */
	public int update(ValueObject obj) throws Exception;

	/**
	 * update the record to the given values in the ValueObject with the PK as constraint.
	 *
	 * @param obj  ValueObject.
	 * @param conn java.sql.Connection.
	 *
	 * @return int, the number of records updated successfully.
	 */
	public int update(ValueObject obj, Connection conn) throws Exception;
	/**
	 * update the record to the given values in the ValueObject with the PK as constraint.
	 *
	 * @param obj  ValueObject.
	 * @param where String.
	 *
	 * @return int, the number of records updated successfully.
	 */
	public int update(ValueObject obj, String where) throws Exception;

	/**
	 * update the record to the given values in the ValueObject with the PK as constraint.
	 *
	 * @param obj  ValueObject.
	 * @param where String.
	 * @param conn java.sql.Connection.
	 *
	 * @return int, the number of records updated successfully.
	 */
	public int update(ValueObject obj,String where, Connection conn) throws Exception;
	
	/**
	 * update the record to the given values in the ValueObject with the PK as constraint.
	 *
	 * @param obj  ValueObject.
	 * @param dataSourcename String.
	 *
	 * @return int, the number of records updated successfully.
	 */
	public int updateTheGivenDataSource(ValueObject obj, String dataSourcename) throws Exception;

	/**
	 * update the record to the given values in the ValueObject with the PK as constraint.
	 *
	 * @param obj ValueObject.
	 * @param where String.
	 * @param dataSourcename String.
	 *
	 * @return int, the number of records updated successfully.
	 */
	public int updateTheGivenDataSource(ValueObject obj,String where,String dataSourcename) throws Exception;

	/**
	 * Delete the record in the table by where clause.
	 *
	 * @param where String.
	 *
	 * @return int, the number of records deleted from the database.
	 */
	public int deleteByWhere(String where) throws Exception;

	/**
	 * Delete the record in the table by where clause and given sata source.
	 *
	 * @param where String.
	 * @param where String.
	 *
	 * @return int, the number of records deleted from the database.
	 */
	public int deleteByWhere(String where, String dataSourceName) throws Exception;
	
	/**
	 * Delete the record in the table by where clause and a given Connection.
	 *
	 * @param where String.
	 * @param conn java.sql.Connection.
	 *
	 * @return int, the number of records deleted from the database.
	 */
	public int deleteByWhere(String where, Connection conn) throws Exception;

	/**
	 * Batch create method for performance purpose.
	 *
	 * @param , a list of database objectto be stored.
	 *
	 * @return int [], the number of records created.
	 */
	public int[] batchCreate(List <ValueObject> volist) throws Exception;

	/**
	 * Batch update method for performance purpose.
	 *
	 * @param volist, a list of ValueObject to be updated.
	 *
	 * @return int [], the number of records updated.
	 */
	public int[] batchUpdate(List <ValueObject> volist) throws Exception;

	/**
	 * Batch delete method for performance purpose.
	 *
	 * @param volist, a list of ValueObject to be deleted.
	 *
	 * @return int [], the number of records deleted.
	 */
	public int[] batchDelete(List <ValueObject> volist) throws Exception;

}
