package ca.on.moh.idms.persister;

/**
 * The class implementation to manage EngineParameterPersisterImpl persistent. 
 * <br> 
 * Automatically generated persister calss. 
 * If any questions, contact Davy Hu (hu_davy@yahoo.com)
 */

import gov.moh.app.db.DBConnectionManager;
import gov.moh.config.vo.ValueObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ca.on.moh.idms.vo.EngineParameter;

public class EngineParameterPersisterImpl extends DBConnectionManager implements EngineParameterPersister{

	static final long serialVersionUID = 162504;

	static final String[] ENGINE_PARAMETER_COLUMN_NAMES= {
			"ENGINE_PARAMETER.PARAMETER_ID",
			"ENGINE_PARAMETER.PARAMETER_VALUE",
			"ENGINE_PARAMETER.EFFECTIVE_DATE",
			"ENGINE_PARAMETER.END_DATE",
			"ENGINE_PARAMETER.PARAMETER_CREATED_BY",
			"ENGINE_PARAMETER.PARAMETER_UPDATED_BY",
			"ENGINE_PARAMETER.PARAMETER_NAME"
			};

	protected static final String ALL_COLUMNS= 
			"ENGINE_PARAMETER.PARAMETER_ID, " + 
			"ENGINE_PARAMETER.PARAMETER_VALUE, " + 
			"ENGINE_PARAMETER.EFFECTIVE_DATE, " + 
			"ENGINE_PARAMETER.END_DATE, " + 
			"ENGINE_PARAMETER.PARAMETER_CREATED_BY, " + 
			"ENGINE_PARAMETER.PARAMETER_UPDATED_BY, " + 
			"ENGINE_PARAMETER.PARAMETER_NAME " ; 

	public EngineParameterPersisterImpl(){
		super();
	}
	/**
	 * Find a record in the table by primary key.
	 *
	 * @param parameterId int.
	 *
	 * @return EngineParameter.
	 */
	public EngineParameter findByPK(int parameterId) throws Exception {
		Connection conn = null;
		try {
			conn = getConnection();
			return findByPK(parameterId, conn);
		}catch(Exception e) {
			getLogging().error("Error in finding object by PK:", e);
			throw e;
		} finally{
				closeConnection(conn);
		}
	}

	/**
	 * Find a record in the table by primary key.
	 *
	 * @param parameterId int.
	 * @param conn Connection.
	 *
	 * @return EngineParameter.
	 */
	public EngineParameter findByPK(int parameterId, Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("SELECT " + ALL_COLUMNS + " FROM ENGINE_PARAMETER WHERE ENGINE_PARAMETER.PARAMETER_ID=?");
			pstmt.setInt(1, parameterId);
			List <ValueObject> list = findByPreparedStatement(pstmt);
			if(list.size() < 1){
				return null;
			}else {
				return (EngineParameter) list.get(0);
			}
		}catch(Exception e) {
			getLogging().error("Error in finding object by PK and Connection:", e);
			throw e;
		}finally{
			closeConnection(null,pstmt,null);
		}
	}

	/**
	 * Find a record in the table by primary key.
	 *
	 * @param parameterId int.
	 * @param conn Connection.
	 *
	 * @return EngineParameter.
	 */
	public EngineParameter findByPK(int parameterId, String dataSourceName) throws Exception {
		Connection conn = null;
		try {
			conn = getConnection(dataSourceName);
			return findByPK(parameterId, conn);
		}catch(Exception e) {
			getLogging().error("Error in find object by PK and data source:", e);
			throw e;
		}finally{
				closeConnection(conn);
		}
	}

	/**
	 * Find all the records in the table.
	 *
	 * @return list a list of EngineParameter.
	 */
	public List <ValueObject> findAll() throws Exception {
		Connection conn = null;
		try {
			conn = getConnection();
			return findAll(null, conn);
		}catch(Exception e) {
			getLogging().error("Error in finding all object:", e);
			throw e;
		} finally{
				closeConnection(conn);
		}
	}

	/**
	 * Find all the records in the table in a given order.
	 *
	 * @param orderBy String.
	 *
	 * @return list a list of EngineParameter.
	 */
	public List <ValueObject> findAll(String orderBy) throws Exception {
		Connection conn = null;
		try {
			conn = getConnection();
			return findAll(orderBy, conn);
		}catch(Exception e) {
			getLogging().error("Error in finding all object:", e);
			throw e;
		} finally{
				closeConnection(conn);
		}
	}

	/**
	 * Find all the records in the table in a given order and a given connection.
	 *
	 * @param orderBy String.
	 * @param conn Connection.
	 *
	 * @return list a list of EngineParameter.
	 */
	public List <ValueObject> findAll(String orderBy,Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		try {
			String sql = "SELECT " + ALL_COLUMNS + " FROM ENGINE_PARAMETER";
			if(orderBy != null){
				sql = sql + " " + orderBy;
			}
			pstmt = conn.prepareStatement(sql);
			return findByPreparedStatement(pstmt);
		}catch(Exception e) {
			getLogging().error("Error in finding all objects by given connection:", e);
			throw e;
		}finally{
			closeConnection(null,pstmt,null);
		}
	}

	/**
	 * Find all the records in the table by prepared statement.
	 *
	 * @param pstmt PreparedStatement.
	 *
	 * @return list a list of EngineParameter.
	 */
	public List <ValueObject> findByPreparedStatement(PreparedStatement pstmt) throws Exception {
		return findByPreparedStatement(pstmt, null, 0);
	}

	/**
	 * Find all the records in the table by prepared statement.
	 *
	 * @param pstmt PreparedStatement.
	 * @param columnList int [].
	 *
	 * @return list a list of EngineParameter.
	 */
	public List <ValueObject> findByPreparedStatement(PreparedStatement pstmt, int[] columnList) throws Exception {
		return findByPreparedStatement(pstmt, columnList, 0);
	}

	/**
	 * Find all the records in the table by prepared statement.
	 *
	 * @param pstmt PreparedStatement.
	 * @param maxRows int.
	 *
	 * @return list a list of EngineParameter.
	 */
	public List <ValueObject> findByPreparedStatement(PreparedStatement pstmt, int maxRows) throws Exception {
		return findByPreparedStatement(pstmt, null, maxRows);
	}

	/**
	 * Find all the records in the table by prepared statement.
	 *
	 * @param pstmt PreparedStatement.
	 * @param columnList int [].
	 *
	 * @return list a list of EngineParameter.
	 */
	public List <ValueObject> findByPreparedStatement(PreparedStatement pstmt, int[] columnList, int maxRows) throws Exception {
		ResultSet rs = null;
		try {
			rs = pstmt.executeQuery();
			List <ValueObject> list = new ArrayList<ValueObject>();
			int indexInList = 0;
			while(rs.next() && (maxRows == 0 || list.size() < maxRows)) {
				if(columnList == null){
					EngineParameter vo = constructValueObject(rs);
					vo.setIndexInList(indexInList);
					list.add(vo);
				}else{
					EngineParameter vo = constructValueObject(rs, columnList);
					vo.setIndexInList(indexInList);
					list.add(vo);
				}
				indexInList++;
			}
			if(list.size() == 0){
				list = null;
			}
			return list;
		}catch(Exception e) {
			getLogging().error("Error in finding object:", e);
			throw e;
		}finally{
			closeConnection(null,null,rs);
		}
	}

	/**
	 * Find all the records in the table by where constraints.
	 *
	 * @param where String.
	 *
	 * @return list a list of EngineParameter.
	 */
	public List <ValueObject> findByWhere(String where) throws Exception {
		return findByWhere(where, (int[])null, 0);
	}

	/**
	 * Find all the records in the table by where constraints.
	 *
	 * @param where String.
	 * @param columnList int [].
	 *
	 * @return list a list of EngineParameter.
	 */
	public List <ValueObject> findByWhere(String where, int[] columnList) throws Exception {
		return findByWhere(where, columnList, 0);
	}

	/**
	 * Find all the records in the table by where constraints.
	 *
	 * @param where String.
	 * @param columnList int [].
	 *
	 * @return list a list of EngineParameter.
	 */
	public List <ValueObject> findByWhere(String where, int[] columnList, int maxRows) throws Exception {
		Connection conn = null;
		try {
			conn = getConnection();
			return findByWhere(where, conn, columnList, maxRows);
		}catch(Exception e) {
			getLogging().error("Error in finding object by where clause:", e);
			throw e;
		} finally{
				closeConnection(conn);
		}
	}

	/**
	 * Find all the records in the table by where constraints.
	 *
	 * @param where String.
	 * @param maxRows int.
	 *
	 * @return list a list of EngineParameter.
	 */
	public List <ValueObject> findByWhere(String where, int maxRows) throws Exception {
		return findByWhere(where, (int[])null, maxRows);
	}

	/**
	 * Find all the records in the table by where constraints.
	 *
	 * @param where String.
	 * @param conn Connection.
	 *
	 * @return list a list of EngineParameter.
	 */
	public List <ValueObject> findByWhere(String where, Connection conn) throws Exception {
		return findByWhere(where, conn, null, 0);
	}

	/**
	 * Find all the records in the table by where constraints.
	 *
	 * @param where String.
	 * @param conn Connection.
	 *
	 * @return list a list of EngineParameter.
	 */
	public List <ValueObject> findByWhere(String where, Connection conn, int[] columnList) throws Exception {
		return findByWhere(where, conn, columnList, 0);
	}

	/**
	 * Find all the records in the table by where constraints.
	 *
	 * @param where String.
	 * @param columnList int [].
	 * @param conn Connection.
	 *
	 * @return list a list of EngineParameter.
	 */
	public List <ValueObject> findByWhere(String where, Connection conn, int[] columnList, int maxRows) throws Exception {
		String sql = null;
		where = formatSQLParameter(where);
		if(columnList == null){
			 sql = "select " + ALL_COLUMNS + " from ENGINE_PARAMETER " +  where;
		}else {
			StringBuffer buff = new StringBuffer(128);
			buff.append("select ");
			for(int i = 0; i < columnList.length; i++) {
				if(i != 0) buff.append(",");
				buff.append(ENGINE_PARAMETER_COLUMN_NAMES[columnList[i]]);
			}
			buff.append(" from ENGINE_PARAMETER ");
			buff.append(where);
			sql = buff.toString();
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			List <ValueObject>list = new ArrayList<ValueObject>();
			int indexInList = 0;
			while(rs.next() && (maxRows == 0 || list.size() < maxRows)) {
				if(columnList == null){
					EngineParameter vo = constructValueObject(rs);
					vo.setIndexInList(indexInList);
					list.add(vo);
				}else{
					EngineParameter vo = constructValueObject(rs, columnList);
					vo.setIndexInList(indexInList);
					list.add(vo);
				}
				indexInList++;
			}
			if(list.size() == 0){
				list = null;
			}
			return list;
		}catch(Exception e) {
			getLogging().error("Error in finding object:", e);
			throw e;
		}finally{
			closeConnection(null,ps,rs);
		}
	}

	/**
	 * Save the records into the database.
	 *
	 * @param obj EngineParameter, value object with the record to be stored in the database.
	 *
	 * @return saveCount int, the number of records created in the database.
	 */
	public int save(ValueObject obj) throws Exception {
		int saveCount = 0;
		Connection conn = null;
		if (obj != null){
			try {
				conn = getUserTransctionConnection();
				saveCount = save(obj, conn);
			}catch(Exception e) {
				throw e;
			}finally{
			}
		}
		return saveCount;
	}

	/**
	 * Save the records into the given database.
	 *
	 * @param obj EngineParameter, value object with the record to be stored in the database.
	 * @param dataSourceName String.
	 *
	 * @return saveCount int, the number of records created in the database.
	 */
	public int save(ValueObject obj, String dataSourceName) throws Exception {
		Connection conn = null;
		if (obj != null){
			try {
				conn = getUserTransctionConnection(dataSourceName);
			}catch(Exception e) {
				throw e;
			} finally{
			}
		}
		return save(obj, conn);
	}
	/**
	 * Save the records into the database.
	 *
	 * @param obj EngineParameter, value object with the record to be stored in the database.
	 * @param conn Connection.
	 *
	 * @return saveCount int, the number of records created in the database.
	 */
	public int save(ValueObject object, Connection conn) throws Exception {
		int saveCount = 0;
		PreparedStatement pstmt = null;
		try {
			EngineParameter obj = (EngineParameter) object;
			if(obj != null) {
				String sql = populateCreateSql(obj);
				pstmt = conn.prepareStatement(sql.toString());
				int dirtycount = 0;
				if(obj.getParameterId() != Integer.MIN_VALUE) { 
					pstmt.setInt(++dirtycount, obj.getParameterId()); 
				}
				if(obj.getParameterValue() != null && !obj.getParameterValue().equals("")) {
					pstmt.setString(++dirtycount, obj.getParameterValue()); 
				}
				if(obj.getEffectiveDate() != null) {
 					pstmt.setTimestamp(++dirtycount, obj.getEffectiveDate()); 
				}
				if(obj.getEndDate() != null && !obj.getEndDate().equals("")) {
					pstmt.setString(++dirtycount, obj.getEndDate()); 
				}
				if(obj.getParameterCreatedBy() != Integer.MIN_VALUE) { 
					pstmt.setInt(++dirtycount, obj.getParameterCreatedBy()); 
				}
				if(obj.getParameterUpdatedBy() != Integer.MIN_VALUE) { 
					pstmt.setInt(++dirtycount, obj.getParameterUpdatedBy()); 
				}
				if(obj.getParameterName() != null && !obj.getParameterName().equals("")) {
					pstmt.setString(++dirtycount, obj.getParameterName()); 
				}
				saveCount =pstmt.executeUpdate();
			}
		}catch(Exception e) {
			getLogging().error("Error in saving object:", e);
			throw e;
		} finally {
			closeConnection(null,pstmt,null);
		}
		return saveCount;
	}

	/**
	 * Update the record into the value specified in the value object with the PK defined in the ValueObject.
	 *
	 * @param obj EngineParameter.
	 *
	 * @return int, the number of records updated in the database.
	 */
	public int update(ValueObject obj) throws Exception {
		EngineParameter o = ( EngineParameter)obj;
		String where = " where ENGINE_PARAMETER.PARAMETER_ID = " + o.getParameterId() ;
		return update(obj, where);
	}

	/**
	 * Update the record into the value specified in the value object with the PK defined in the ValueObject in a given database.
	 *
	 * @param obj EngineParameter.
	 * @param conn java.sql.Connection.
	 *
	 * @return int, the number of records updated in the database.
	 */
	public int update(ValueObject obj, Connection conn) throws Exception {
		EngineParameter o = ( EngineParameter)obj;
		String where = " where ENGINE_PARAMETER.PARAMETER_ID = " + o.getParameterId() ;
		return update(obj, where, conn);
	}

	/**
	 * Update the record into the value specified in the value object with the constraints defined in where clause.
	 *
	 * @param obj EngineParameter.
	 * @param where String.
	 *
	 * @return int, the number of records updated in the database.
	 */
	public int update(ValueObject obj, String where) throws Exception {
		Connection conn = null;
		try {
			conn = getUserTransctionConnection();
			return update(obj, where, conn);
		}catch(Exception e) {
			throw e;
		} finally{
		}
	}

	/**
	 * Update the record into the value specified in the value object with the constraints defined in where clause.
	 *
	 * @param object EngineParameter.
	 * @param where String.
	 * @param conn Connection.
	 *
	 * @return int, the number of records updated in the database.
	 */
	public int update(ValueObject object,String where, Connection conn) throws Exception {
		int updateCount = 0;
		PreparedStatement pstmt = null;
		try {
			EngineParameter obj = (EngineParameter)object;
			if(obj != null) {
				StringBuffer sql = new StringBuffer("UPDATE ENGINE_PARAMETER SET ");
				if(obj.getParameterId() != Integer.MIN_VALUE && obj.getParameterId() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("PARAMETER_ID=?,"); 
				} 
				else if(obj.getParameterId() == ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("PARAMETER_ID=NULL,"); 
				} 
				if(obj.getParameterValue() != null && !obj.getParameterValue().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("PARAMETER_VALUE=?,"); 
				} 
				else if(obj.getParameterValue() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("PARAMETER_VALUE=NULL,"); 
				} 
				if(obj.getEffectiveDate() != null && !obj.getEffectiveDate().equals(ValueObject.DATE_TO_NULL_VALUE)) {
					sql.append("EFFECTIVE_DATE=?,"); 
				} 
				else if(obj.getEffectiveDate()!= null && obj.getEffectiveDate().equals(ValueObject.DATE_TO_NULL_VALUE)) { 
 					sql.append("EFFECTIVE_DATE=NULL,"); 
				} 
				if(obj.getEndDate() != null && !obj.getEndDate().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("END_DATE=?,"); 
				} 
				else if(obj.getEndDate() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("END_DATE=NULL,"); 
				} 
				if(obj.getParameterCreatedBy() != Integer.MIN_VALUE && obj.getParameterCreatedBy() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("PARAMETER_CREATED_BY=?,"); 
				} 
				else if(obj.getParameterCreatedBy() == ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("PARAMETER_CREATED_BY=NULL,"); 
				} 
				if(obj.getParameterUpdatedBy() != Integer.MIN_VALUE && obj.getParameterUpdatedBy() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("PARAMETER_UPDATED_BY=?,"); 
				} 
				else if(obj.getParameterUpdatedBy() == ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("PARAMETER_UPDATED_BY=NULL,"); 
				} 
				if(obj.getParameterName() != null && !obj.getParameterName().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("PARAMETER_NAME=?,"); 
				} 
				else if(obj.getParameterName() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("PARAMETER_NAME=NULL,"); 
				} 
				sql.setLength(sql.length() - 1);
				where = formatSQLParameter(where);
				sql.append(where);

				pstmt = conn.prepareStatement(sql.toString());

				int dirtycount = 0;
				if(obj.getParameterId() != Integer.MIN_VALUE && obj.getParameterId() != ValueObject.NUMBER_TO_NULL_VALUE) {
					pstmt.setInt(++dirtycount, obj.getParameterId());
				}
				if(obj.getParameterValue() != null && !obj.getParameterValue().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getParameterValue()); 
				}
				if(obj.getEffectiveDate() != null && !obj.getEffectiveDate().equals(ValueObject.DATE_TO_NULL_VALUE)) {
					pstmt.setTimestamp(++dirtycount, obj.getEffectiveDate()); 
				}
				if(obj.getEndDate() != null && !obj.getEndDate().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getEndDate()); 
				}
				if(obj.getParameterCreatedBy() != Integer.MIN_VALUE && obj.getParameterCreatedBy() != ValueObject.NUMBER_TO_NULL_VALUE) {
					pstmt.setInt(++dirtycount, obj.getParameterCreatedBy());
				}
				if(obj.getParameterUpdatedBy() != Integer.MIN_VALUE && obj.getParameterUpdatedBy() != ValueObject.NUMBER_TO_NULL_VALUE) {
					pstmt.setInt(++dirtycount, obj.getParameterUpdatedBy());
				}
				if(obj.getParameterName() != null && !obj.getParameterName().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getParameterName()); 
				}
				updateCount =pstmt.executeUpdate();
			}
		} catch(Exception e) {
			getLogging().error("Error in updating object:", e);
			throw e;
		} finally {
			closeConnection(null,pstmt,null);
		}
		return updateCount;
	}
	/**
	 * Update the record into the value specified in the value object with the PK defined in the ValueObject in a given database.
	 *
	 * @param obj EngineParameter.
	 * @param dataSourceName String.
	 *
	 * @return int, the number of records updated in the database.
	 */
	public int updateTheGivenDataSource(ValueObject obj, String dataSourcename) throws Exception {
		EngineParameter o = ( EngineParameter)obj;
		String where = " where ENGINE_PARAMETER.PARAMETER_ID = " + o.getParameterId() ;
		return updateTheGivenDataSource(obj,where,dataSourcename);
	}

	/**
	 * Update the record into the value specified in the value object with the constraints defined in where clause.
	 *
	 * @param obj EngineParameter.
	 * @param where String.
	 * @param dataSourceName String.
	 *
	 * @return int, the number of records updated in the database.
	 */
	public int updateTheGivenDataSource(ValueObject obj, String where,String dataSourceName) throws Exception {
		Connection conn = null;
		try {
			conn = getUserTransctionConnection(dataSourceName);
			return update(obj, where, conn);
		}catch(Exception e) {
			throw e;
		} finally{
		}
	}

	/**
	 * Delete the record in the table by primary key.
	 *
	 * @param parameterId int.
	 *
	 * @return int, the number of records deleted in the database.
	 */
	public int deleteByPK(int parameterId) throws Exception {
		String where = " where ENGINE_PARAMETER.PARAMETER_ID = " + parameterId ;
		return deleteByWhere(where);
	}

	/**
	 * Delete the record in the table by primary key.
	 *
	 * @param parameterId int.
	 * @param dataSourceName String.
	 *
	 * @return int, the number of records deleted in the database.
	 */
	public int deleteByPK(int parameterId,String dataSourceName) throws Exception {
		String where = " where ENGINE_PARAMETER.PARAMETER_ID = " + parameterId ;
		try {
			return deleteByWhere(where, dataSourceName);
		}catch(Exception e) {
			throw e;
		}
	}

	/**
	 * Delete the record in the table by primary key.
	 *
	 * @param parameterId int.
	 * @param conn java.sql.Connection.
	 *
	 * @return int, the number of records deleted in the database.
	 */
	public int deleteByPK(int parameterId,Connection conn) throws Exception {
		String where = " where ENGINE_PARAMETER.PARAMETER_ID = " + parameterId ;
		return deleteByWhere(where,conn);
	}

	/**
	 * Delete the record in the table by where.
	 *
	 * @param where String.
	 *
	 * @return int, the number of records deleted in the database.
	 */
	public int deleteByWhere(String where) throws Exception {
		Connection conn = null;
		try {
			conn = getUserTransctionConnection();
			return deleteByWhere(where, conn);
		}catch(Exception e) {
			throw e;
		} finally{
		}
	}

	/**
	 * Delete the record in the table by where.
	 *
	 * @param where String.
	 * @param dataSourceName String.
	 *
	 * @return int, the number of records deleted in the database.
	 */
	public int deleteByWhere(String where, String dataSourceName) throws Exception {
		Connection conn = null;
		try {
			conn = getUserTransctionConnection(dataSourceName);
			return deleteByWhere(where, conn);
		}catch(Exception e) {
			throw e;
		} finally{
		}
	}

	/**
	 * Delete the record in the table by where.
	 *
	 * @param where String.
	 * @param conn java.sql.Connection.
	 *
	 * @return int, the number of records deleted in the database.
	 */
	public int deleteByWhere(String where, Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		try {
			String sql = "DELETE from ENGINE_PARAMETER ";
			where = formatSQLParameter(where);
			sql =  sql + where;
			pstmt = conn.prepareStatement(sql);
			int rows = pstmt.executeUpdate();
			return rows;
		}catch(Exception e) {
			getLogging().error("Error in deleteing object:", e);
			throw e;
		}finally{
			closeConnection(null,pstmt,null);
		}
	}

	/**
	 * Batch create method for performance purpose.
	 *
	 * @param volist, a list of value object to be stored.
	 *
	 * @return int [], the number of records created.
	 */
	public int[] batchCreate(List <ValueObject> volist) throws Exception {
		int[] counts = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getUserTransctionConnection();
			for(int i=0;i<volist.size();i++) {
				EngineParameter obj = (EngineParameter)volist.get(i);
				String sql = populateCreateSql(obj);
				pstmt = conn.prepareStatement(sql);
				int dirtycount = 0;
				if(obj.getParameterId() != Integer.MIN_VALUE) { 
					pstmt.setInt(++dirtycount, obj.getParameterId()); 
				}
				if(obj.getParameterValue() != null && !obj.getParameterValue().equals("")) {
					pstmt.setString(++dirtycount, obj.getParameterValue()); 
				}
				if(obj.getEffectiveDate() != null) {
 					pstmt.setTimestamp(++dirtycount, obj.getEffectiveDate()); 
				}
				if(obj.getEndDate() != null && !obj.getEndDate().equals("")) {
					pstmt.setString(++dirtycount, obj.getEndDate()); 
				}
				if(obj.getParameterCreatedBy() != Integer.MIN_VALUE) { 
					pstmt.setInt(++dirtycount, obj.getParameterCreatedBy()); 
				}
				if(obj.getParameterUpdatedBy() != Integer.MIN_VALUE) { 
					pstmt.setInt(++dirtycount, obj.getParameterUpdatedBy()); 
				}
				if(obj.getParameterName() != null && !obj.getParameterName().equals("")) {
					pstmt.setString(++dirtycount, obj.getParameterName()); 
				}
				pstmt.addBatch();
			}
			counts = pstmt.executeBatch();
		}catch(Exception e) {
			getLogging().error("Error in batch persisting object:", e);
			throw e;
		} finally {
				closeConnection(null,pstmt,null);
		}
		return counts;
	}

	/**
	 * Batch update method for performance purpose.
	 *
	 * @param volist, a list of value object to be updated.
	 *
	 * @return int [], the number of records deleted.
	 */
	public int[] batchUpdate(List <ValueObject> volist) throws Exception {
		int[] counts = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getUserTransctionConnection();
			for(int i=0;i<volist.size();i++) {
				EngineParameter obj = (EngineParameter)volist.get(i);
				StringBuffer sql = new StringBuffer("UPDATE ENGINE_PARAMETER SET ");
				if(obj.getParameterId() != Integer.MIN_VALUE && obj.getParameterId() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("PARAMETER_ID=?,"); 
				} 
				else if(obj.getParameterId() == ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("PARAMETER_ID=NULL,"); 
				} 
				if(obj.getParameterValue() != null && !obj.getParameterValue().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("PARAMETER_VALUE=?,"); 
				} 
				else if(obj.getParameterValue() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("PARAMETER_VALUE=NULL,"); 
				} 
				if(obj.getEffectiveDate() != null && !obj.getEffectiveDate().equals(ValueObject.DATE_TO_NULL_VALUE)) {
					sql.append("EFFECTIVE_DATE=?,"); 
				} 
				else if(obj.getEffectiveDate()!= null && obj.getEffectiveDate().equals(ValueObject.DATE_TO_NULL_VALUE)) { 
 					sql.append("EFFECTIVE_DATE=NULL,"); 
				} 
				if(obj.getEndDate() != null && !obj.getEndDate().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("END_DATE=?,"); 
				} 
				else if(obj.getEndDate() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("END_DATE=NULL,"); 
				} 
				if(obj.getParameterCreatedBy() != Integer.MIN_VALUE && obj.getParameterCreatedBy() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("PARAMETER_CREATED_BY=?,"); 
				} 
				else if(obj.getParameterCreatedBy() == ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("PARAMETER_CREATED_BY=NULL,"); 
				} 
				if(obj.getParameterUpdatedBy() != Integer.MIN_VALUE && obj.getParameterUpdatedBy() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("PARAMETER_UPDATED_BY=?,"); 
				} 
				else if(obj.getParameterUpdatedBy() == ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("PARAMETER_UPDATED_BY=NULL,"); 
				} 
				if(obj.getParameterName() != null && !obj.getParameterName().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("PARAMETER_NAME=?,"); 
				} 
				else if(obj.getParameterName() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("PARAMETER_NAME=NULL,"); 
				} 
				sql.setLength(sql.length() - 1);
				String where = " where ENGINE_PARAMETER.PARAMETER_ID = " + obj.getParameterId() ;
				sql.append(where);

				pstmt = conn.prepareStatement(sql.toString());

				int dirtycount = 0;
				if(obj.getParameterId() != Integer.MIN_VALUE && obj.getParameterId() != ValueObject.NUMBER_TO_NULL_VALUE) {
					pstmt.setInt(++dirtycount, obj.getParameterId());
				}
				if(obj.getParameterValue() != null && !obj.getParameterValue().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getParameterValue()); 
				}
				if(obj.getEffectiveDate() != null && !obj.getEffectiveDate().equals(ValueObject.DATE_TO_NULL_VALUE)) {
					pstmt.setTimestamp(++dirtycount, obj.getEffectiveDate()); 
				}
				if(obj.getEndDate() != null && !obj.getEndDate().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getEndDate()); 
				}
				if(obj.getParameterCreatedBy() != Integer.MIN_VALUE && obj.getParameterCreatedBy() != ValueObject.NUMBER_TO_NULL_VALUE) {
					pstmt.setInt(++dirtycount, obj.getParameterCreatedBy());
				}
				if(obj.getParameterUpdatedBy() != Integer.MIN_VALUE && obj.getParameterUpdatedBy() != ValueObject.NUMBER_TO_NULL_VALUE) {
					pstmt.setInt(++dirtycount, obj.getParameterUpdatedBy());
				}
				if(obj.getParameterName() != null && !obj.getParameterName().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getParameterName()); 
				}
				pstmt.addBatch();
			}
			counts = pstmt.executeBatch();
		}catch(Exception e) {
			getLogging().error("Error in batch persisting object:", e);
			throw e;
		} finally {
				closeConnection(null,pstmt,null);
		}
		return counts;
	}

	/**
	 * Batch delete method for performance purpose.
	 *
	 * @param volist, a list of value object to be deleted.
	 *
	 * @return int [], the number of records deleted.
	 */
	public int[] batchDelete(List <ValueObject> volist) throws Exception {
		int[] counts = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getUserTransctionConnection();
			for(int i=0;i<volist.size();i++) {
				EngineParameter obj = (EngineParameter)volist.get(i);
				String sql = populateDeleteSql(obj);
				pstmt = conn.prepareStatement(sql);
				int dirtycount = 0;
				if(obj.getParameterId() != Integer.MIN_VALUE) { 
					pstmt.setInt(++dirtycount, obj.getParameterId()); 
				}
				if(obj.getParameterValue() != null && !obj.getParameterValue().equals("")) {
					pstmt.setString(++dirtycount, obj.getParameterValue()); 
				}
				if(obj.getEffectiveDate() != null) {
 					pstmt.setTimestamp(++dirtycount, obj.getEffectiveDate()); 
				}
				if(obj.getEndDate() != null && !obj.getEndDate().equals("")) {
					pstmt.setString(++dirtycount, obj.getEndDate()); 
				}
				if(obj.getParameterCreatedBy() != Integer.MIN_VALUE) { 
					pstmt.setInt(++dirtycount, obj.getParameterCreatedBy()); 
				}
				if(obj.getParameterUpdatedBy() != Integer.MIN_VALUE) { 
					pstmt.setInt(++dirtycount, obj.getParameterUpdatedBy()); 
				}
				if(obj.getParameterName() != null && !obj.getParameterName().equals("")) {
					pstmt.setString(++dirtycount, obj.getParameterName()); 
				}
				pstmt.addBatch();
			}
			counts = pstmt.executeBatch();
		}catch(Exception e) {
			getLogging().error("Error in batch persisting object:", e);
			throw e;
		} finally {
				closeConnection(null,pstmt,null);
		}
		return counts;
	}

	private String populateCreateSql(EngineParameter obj){
		int dirtycount = 0;
		StringBuffer sql = new StringBuffer("INSERT into ENGINE_PARAMETER(");
		if(obj.getParameterId() != Integer.MIN_VALUE) {
			sql.append("PARAMETER_ID").append(","); dirtycount++; 
		}
		if(obj.getParameterValue() != null && !obj.getParameterValue().equals("")) {
			sql.append("PARAMETER_VALUE").append(","); dirtycount++; 
		}
		if(obj.getEffectiveDate() != null) {
			sql.append("EFFECTIVE_DATE").append(","); dirtycount++; 
		}
		if(obj.getEndDate() != null && !obj.getEndDate().equals("")) {
			sql.append("END_DATE").append(","); dirtycount++; 
		}
		if(obj.getParameterCreatedBy() != Integer.MIN_VALUE) {
			sql.append("PARAMETER_CREATED_BY").append(","); dirtycount++; 
		}
		if(obj.getParameterUpdatedBy() != Integer.MIN_VALUE) {
			sql.append("PARAMETER_UPDATED_BY").append(","); dirtycount++; 
		}
		if(obj.getParameterName() != null && !obj.getParameterName().equals("")) {
			sql.append("PARAMETER_NAME").append(","); dirtycount++; 
		}
		sql.setLength(sql.length() - 1);
		sql.append(") values (");
		for(int i = 0; i < dirtycount; i++) { 
				sql.append("?,"); 
		} 
		sql.setLength(sql.length() - 1);
		sql.append(")");
		return sql.toString();
	}

	private String populateDeleteSql(EngineParameter obj){
		int dirtycount = 0;
		StringBuffer sql = new StringBuffer("DELETE FROM ENGINE_PARAMETER where ");
		if(obj.getParameterId() != Integer.MIN_VALUE) {
			if(dirtycount == 0){
				sql.append("PARAMETER_ID=?").append(",");
				dirtycount++;
			}else{
				sql.append("PARAMETER_ID=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getParameterValue() != null && !obj.getParameterValue().equals("")) {
			if(dirtycount == 0){
				sql.append("PARAMETER_VALUE=?").append(",");
				dirtycount++;
			}else{
				sql.append("PARAMETER_VALUE=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getEffectiveDate() != null && !obj.getEffectiveDate().equals("")) {
			if(dirtycount == 0){
				sql.append("EFFECTIVE_DATE=?").append(",");
				dirtycount++;
			}else{
				sql.append("EFFECTIVE_DATE=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getEndDate() != null && !obj.getEndDate().equals("")) {
			if(dirtycount == 0){
				sql.append("END_DATE=?").append(",");
				dirtycount++;
			}else{
				sql.append("END_DATE=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getParameterCreatedBy() != Integer.MIN_VALUE) {
			if(dirtycount == 0){
				sql.append("PARAMETER_CREATED_BY=?").append(",");
				dirtycount++;
			}else{
				sql.append("PARAMETER_CREATED_BY=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getParameterUpdatedBy() != Integer.MIN_VALUE) {
			if(dirtycount == 0){
				sql.append("PARAMETER_UPDATED_BY=?").append(",");
				dirtycount++;
			}else{
				sql.append("PARAMETER_UPDATED_BY=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getParameterName() != null && !obj.getParameterName().equals("")) {
			if(dirtycount == 0){
				sql.append("PARAMETER_NAME=?").append(",");
				dirtycount++;
			}else{
				sql.append("PARAMETER_NAME=?").append(",");
				dirtycount++;
			}
		}
		sql.setLength(sql.length() - 1);
		return sql.toString();
	}

	private EngineParameter constructValueObject(ResultSet rs) throws Exception {
		EngineParameter vo = new EngineParameter();
		int value0 = rs.getInt(1);
		if(value0 == 0 && rs.wasNull()){
			vo.setParameterId(Integer.MIN_VALUE);
		}else{
			vo.setParameterId(value0);
		}
		vo.setParameterValue(rs.getString(2));
		Timestamp value2= rs.getTimestamp(3);
		if(value2 == null && rs.wasNull()){
			vo.setEffectiveDate(new java.sql.Timestamp(0));
		}else{
			vo.setEffectiveDate(value2);
		}
		vo.setEndDate(rs.getString(4));
		int value4 = rs.getInt(5);
		if(value4 == 0 && rs.wasNull()){
			vo.setParameterCreatedBy(Integer.MIN_VALUE);
		}else{
			vo.setParameterCreatedBy(value4);
		}
		int value5 = rs.getInt(6);
		if(value5 == 0 && rs.wasNull()){
			vo.setParameterUpdatedBy(Integer.MIN_VALUE);
		}else{
			vo.setParameterUpdatedBy(value5);
		}
		vo.setParameterName(rs.getString(7));
		return vo;
	}

	private EngineParameter constructValueObject(ResultSet rs, int[] columnList) throws Exception {
		EngineParameter vo = new EngineParameter();
		int pos = 0;
		for(int i = 0; i < columnList.length; i++) {;
			switch(columnList[i]) {
				case PARAMETER_ID: 
					int value0 = rs.getInt(++pos);
					if(value0  == 0 && rs.wasNull()){
					vo.setParameterId(Integer.MIN_VALUE);
					}else{
					vo.setParameterId(value0);
					}
					break;
				case PARAMETER_VALUE: 
					vo.setParameterValue(rs.getString(++pos));
					break;
				case EFFECTIVE_DATE: 
					Timestamp value2 = rs.getTimestamp(++pos);
					if(value2  == null && rs.wasNull()){
					vo.setEffectiveDate(new java.sql.Timestamp(0));
					}else{
					vo.setEffectiveDate(value2);
					}
					break;
				case END_DATE: 
					vo.setEndDate(rs.getString(++pos));
					break;
				case PARAMETER_CREATED_BY: 
					int value4 = rs.getInt(++pos);
					if(value4  == 0 && rs.wasNull()){
					vo.setParameterCreatedBy(Integer.MIN_VALUE);
					}else{
					vo.setParameterCreatedBy(value4);
					}
					break;
				case PARAMETER_UPDATED_BY: 
					int value5 = rs.getInt(++pos);
					if(value5  == 0 && rs.wasNull()){
					vo.setParameterUpdatedBy(Integer.MIN_VALUE);
					}else{
					vo.setParameterUpdatedBy(value5);
					}
					break;
				case PARAMETER_NAME: 
					vo.setParameterName(rs.getString(++pos));
					break;
			}
		}
		return vo;
	}

	private Logger getLogging(){
		return Logger.getLogger(this.getClass());
	}
}

