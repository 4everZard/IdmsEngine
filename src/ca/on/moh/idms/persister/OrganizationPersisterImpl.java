package ca.on.moh.idms.persister;

/**
 * The class implementation to manage OrganizationPersisterImpl persistent. 
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

import ca.on.moh.idms.vo.Organization;

public class OrganizationPersisterImpl extends DBConnectionManager  implements OrganizationPersister{

	static final long serialVersionUID = 831019;

	static final String[] ORGANIZATION_COLUMN_NAMES= {
			"ORGANIZATION.ORGANIZATION_NAME",
			"ORGANIZATION.ORGANIZATION_TYPE",
			"ORGANIZATION.ORGANIZATION_CODE",
			"ORGANIZATION.CREATED_DATE",
			"ORGANIZATION.ORGANIZATION_ID",
			"ORGANIZATION.CLOSED_DATE"
			};

	protected static final String ALL_COLUMNS= 
			"ORGANIZATION.ORGANIZATION_NAME, " + 
			"ORGANIZATION.ORGANIZATION_TYPE, " + 
			"ORGANIZATION.ORGANIZATION_CODE, " + 
			"ORGANIZATION.CREATED_DATE, " + 
			"ORGANIZATION.ORGANIZATION_ID, " + 
			"ORGANIZATION.CLOSED_DATE " ; 

	public OrganizationPersisterImpl(){
		super();
	}
	/**
	 * Find a record in the table by primary key.
	 *
	 * @param organizationId int.
	 *
	 * @return Organization.
	 */
	public Organization findByPK(int organizationId) throws Exception {
		Connection conn = null;
		try {
			conn = getConnection();
			return findByPK(organizationId, conn);
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
	 * @param organizationId int.
	 * @param conn Connection.
	 *
	 * @return Organization.
	 */
	public Organization findByPK(int organizationId, Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("SELECT " + ALL_COLUMNS + " FROM ORGANIZATION WHERE ORGANIZATION.ORGANIZATION_ID=?");
			pstmt.setInt(1, organizationId);
			List <ValueObject> list = findByPreparedStatement(pstmt);
			if(list.size() < 1){
				return null;
			}else {
				return (Organization) list.get(0);
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
	 * @param organizationId int.
	 * @param conn Connection.
	 *
	 * @return Organization.
	 */
	public Organization findByPK(int organizationId, String dataSourceName) throws Exception {
		Connection conn = null;
		try {
			conn = getConnection(dataSourceName);
			return findByPK(organizationId, conn);
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
	 * @return list a list of Organization.
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
	 * @return list a list of Organization.
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
	 * @return list a list of Organization.
	 */
	public List <ValueObject> findAll(String orderBy,Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		try {
			String sql = "SELECT " + ALL_COLUMNS + " FROM ORGANIZATION";
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
	 * @return list a list of Organization.
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
	 * @return list a list of Organization.
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
	 * @return list a list of Organization.
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
	 * @return list a list of Organization.
	 */
	public List <ValueObject> findByPreparedStatement(PreparedStatement pstmt, int[] columnList, int maxRows) throws Exception {
		ResultSet rs = null;
		try {
			rs = pstmt.executeQuery();
			List <ValueObject> list = new ArrayList<ValueObject>();
			int indexInList = 0;
			while(rs.next() && (maxRows == 0 || list.size() < maxRows)) {
				if(columnList == null){
					Organization vo = constructValueObject(rs);
					vo.setIndexInList(indexInList);
					list.add(vo);
				}else{
					Organization vo = constructValueObject(rs, columnList);
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
	 * @return list a list of Organization.
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
	 * @return list a list of Organization.
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
	 * @return list a list of Organization.
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
	 * @return list a list of Organization.
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
	 * @return list a list of Organization.
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
	 * @return list a list of Organization.
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
	 * @return list a list of Organization.
	 */
	public List <ValueObject> findByWhere(String where, Connection conn, int[] columnList, int maxRows) throws Exception {
		String sql = null;
		where = formatSQLParameter(where);
		if(columnList == null){
			 sql = "select " + ALL_COLUMNS + " from ORGANIZATION " +  where;
		}else {
			StringBuffer buff = new StringBuffer(128);
			buff.append("select ");
			for(int i = 0; i < columnList.length; i++) {
				if(i != 0) buff.append(",");
				buff.append(ORGANIZATION_COLUMN_NAMES[columnList[i]]);
			}
			buff.append(" from ORGANIZATION ");
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
					Organization vo = constructValueObject(rs);
					vo.setIndexInList(indexInList);
					list.add(vo);
				}else{
					Organization vo = constructValueObject(rs, columnList);
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
	 * @param obj Organization, value object with the record to be stored in the database.
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
	 * @param obj Organization, value object with the record to be stored in the database.
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
	 * @param obj Organization, value object with the record to be stored in the database.
	 * @param conn Connection.
	 *
	 * @return saveCount int, the number of records created in the database.
	 */
	public int save(ValueObject object, Connection conn) throws Exception {
		int saveCount = 0;
		PreparedStatement pstmt = null;
		try {
			Organization obj = (Organization) object;
			if(obj != null) {
				String sql = populateCreateSql(obj);
				pstmt = conn.prepareStatement(sql.toString());
				int dirtycount = 0;
				if(obj.getOrganizationName() != null && !obj.getOrganizationName().equals("")) {
					pstmt.setString(++dirtycount, obj.getOrganizationName()); 
				}
				if(obj.getOrganizationType() != Integer.MIN_VALUE) { 
					pstmt.setInt(++dirtycount, obj.getOrganizationType()); 
				}
				if(obj.getOrganizationCode() != null && !obj.getOrganizationCode().equals("")) {
					pstmt.setString(++dirtycount, obj.getOrganizationCode()); 
				}
				if(obj.getCreatedDate() != null) {
 					pstmt.setTimestamp(++dirtycount, obj.getCreatedDate()); 
				}
				if(obj.getOrganizationId() != Integer.MIN_VALUE) { 
					pstmt.setInt(++dirtycount, obj.getOrganizationId()); 
				}
				if(obj.getClosedDate() != null) {
 					pstmt.setTimestamp(++dirtycount, obj.getClosedDate()); 
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
	 * @param obj Organization.
	 *
	 * @return int, the number of records updated in the database.
	 */
	public int update(ValueObject obj) throws Exception {
		Organization o = ( Organization)obj;
		String where = " where ORGANIZATION.ORGANIZATION_ID = " + o.getOrganizationId() ;
		return update(obj, where);
	}

	/**
	 * Update the record into the value specified in the value object with the PK defined in the ValueObject in a given database.
	 *
	 * @param obj Organization.
	 * @param conn java.sql.Connection.
	 *
	 * @return int, the number of records updated in the database.
	 */
	public int update(ValueObject obj, Connection conn) throws Exception {
		Organization o = ( Organization)obj;
		String where = " where ORGANIZATION.ORGANIZATION_ID = " + o.getOrganizationId() ;
		return update(obj, where, conn);
	}

	/**
	 * Update the record into the value specified in the value object with the constraints defined in where clause.
	 *
	 * @param obj Organization.
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
	 * @param object Organization.
	 * @param where String.
	 * @param conn Connection.
	 *
	 * @return int, the number of records updated in the database.
	 */
	public int update(ValueObject object,String where, Connection conn) throws Exception {
		int updateCount = 0;
		PreparedStatement pstmt = null;
		try {
			Organization obj = (Organization)object;
			if(obj != null) {
				StringBuffer sql = new StringBuffer("UPDATE ORGANIZATION SET ");
				if(obj.getOrganizationName() != null && !obj.getOrganizationName().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("ORGANIZATION_NAME=?,"); 
				} 
				else if(obj.getOrganizationName() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("ORGANIZATION_NAME=NULL,"); 
				} 
				if(obj.getOrganizationType() != Integer.MIN_VALUE && obj.getOrganizationType() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("ORGANIZATION_TYPE=?,"); 
				} 
				else if(obj.getOrganizationType() == ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("ORGANIZATION_TYPE=NULL,"); 
				} 
				if(obj.getOrganizationCode() != null && !obj.getOrganizationCode().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("ORGANIZATION_CODE=?,"); 
				} 
				else if(obj.getOrganizationCode() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("ORGANIZATION_CODE=NULL,"); 
				} 
				if(obj.getCreatedDate() != null && !obj.getCreatedDate().equals(ValueObject.DATE_TO_NULL_VALUE)) {
					sql.append("CREATED_DATE=?,"); 
				} 
				else if(obj.getCreatedDate()!= null && obj.getCreatedDate().equals(ValueObject.DATE_TO_NULL_VALUE)) { 
 					sql.append("CREATED_DATE=NULL,"); 
				} 
				if(obj.getOrganizationId() != Integer.MIN_VALUE && obj.getOrganizationId() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("ORGANIZATION_ID=?,"); 
				} 
				else if(obj.getOrganizationId() == ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("ORGANIZATION_ID=NULL,"); 
				} 
				if(obj.getClosedDate() != null && !obj.getClosedDate().equals(ValueObject.DATE_TO_NULL_VALUE)) {
					sql.append("CLOSED_DATE=?,"); 
				} 
				else if(obj.getClosedDate()!= null && obj.getClosedDate().equals(ValueObject.DATE_TO_NULL_VALUE)) { 
 					sql.append("CLOSED_DATE=NULL,"); 
				} 
				sql.setLength(sql.length() - 1);
				where = formatSQLParameter(where);
				sql.append(where);

				pstmt = conn.prepareStatement(sql.toString());

				int dirtycount = 0;
				if(obj.getOrganizationName() != null && !obj.getOrganizationName().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getOrganizationName()); 
				}
				if(obj.getOrganizationType() != Integer.MIN_VALUE && obj.getOrganizationType() != ValueObject.NUMBER_TO_NULL_VALUE) {
					pstmt.setInt(++dirtycount, obj.getOrganizationType());
				}
				if(obj.getOrganizationCode() != null && !obj.getOrganizationCode().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getOrganizationCode()); 
				}
				if(obj.getCreatedDate() != null && !obj.getCreatedDate().equals(ValueObject.DATE_TO_NULL_VALUE)) {
					pstmt.setTimestamp(++dirtycount, obj.getCreatedDate()); 
				}
				if(obj.getOrganizationId() != Integer.MIN_VALUE && obj.getOrganizationId() != ValueObject.NUMBER_TO_NULL_VALUE) {
					pstmt.setInt(++dirtycount, obj.getOrganizationId());
				}
				if(obj.getClosedDate() != null && !obj.getClosedDate().equals(ValueObject.DATE_TO_NULL_VALUE)) {
					pstmt.setTimestamp(++dirtycount, obj.getClosedDate()); 
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
	 * @param obj Organization.
	 * @param dataSourceName String.
	 *
	 * @return int, the number of records updated in the database.
	 */
	public int updateTheGivenDataSource(ValueObject obj, String dataSourcename) throws Exception {
		Organization o = ( Organization)obj;
		String where = " where ORGANIZATION.ORGANIZATION_ID = " + o.getOrganizationId() ;
		return updateTheGivenDataSource(obj,where,dataSourcename);
	}

	/**
	 * Update the record into the value specified in the value object with the constraints defined in where clause.
	 *
	 * @param obj Organization.
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
	 * @param organizationId int.
	 *
	 * @return int, the number of records deleted in the database.
	 */
	public int deleteByPK(int organizationId) throws Exception {
		String where = " where ORGANIZATION.ORGANIZATION_ID = " + organizationId ;
		return deleteByWhere(where);
	}

	/**
	 * Delete the record in the table by primary key.
	 *
	 * @param organizationId int.
	 * @param dataSourceName String.
	 *
	 * @return int, the number of records deleted in the database.
	 */
	public int deleteByPK(int organizationId,String dataSourceName) throws Exception {
		String where = " where ORGANIZATION.ORGANIZATION_ID = " + organizationId ;
		try {
			return deleteByWhere(where, dataSourceName);
		}catch(Exception e) {
			throw e;
		}
	}

	/**
	 * Delete the record in the table by primary key.
	 *
	 * @param organizationId int.
	 * @param conn java.sql.Connection.
	 *
	 * @return int, the number of records deleted in the database.
	 */
	public int deleteByPK(int organizationId,Connection conn) throws Exception {
		String where = " where ORGANIZATION.ORGANIZATION_ID = " + organizationId ;
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
			String sql = "DELETE from ORGANIZATION ";
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
				Organization obj = (Organization)volist.get(i);
				String sql = populateCreateSql(obj);
				pstmt = conn.prepareStatement(sql);
				int dirtycount = 0;
				if(obj.getOrganizationName() != null && !obj.getOrganizationName().equals("")) {
					pstmt.setString(++dirtycount, obj.getOrganizationName()); 
				}
				if(obj.getOrganizationType() != Integer.MIN_VALUE) { 
					pstmt.setInt(++dirtycount, obj.getOrganizationType()); 
				}
				if(obj.getOrganizationCode() != null && !obj.getOrganizationCode().equals("")) {
					pstmt.setString(++dirtycount, obj.getOrganizationCode()); 
				}
				if(obj.getCreatedDate() != null) {
 					pstmt.setTimestamp(++dirtycount, obj.getCreatedDate()); 
				}
				if(obj.getOrganizationId() != Integer.MIN_VALUE) { 
					pstmt.setInt(++dirtycount, obj.getOrganizationId()); 
				}
				if(obj.getClosedDate() != null) {
 					pstmt.setTimestamp(++dirtycount, obj.getClosedDate()); 
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
				Organization obj = (Organization)volist.get(i);
				StringBuffer sql = new StringBuffer("UPDATE ORGANIZATION SET ");
				if(obj.getOrganizationName() != null && !obj.getOrganizationName().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("ORGANIZATION_NAME=?,"); 
				} 
				else if(obj.getOrganizationName() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("ORGANIZATION_NAME=NULL,"); 
				} 
				if(obj.getOrganizationType() != Integer.MIN_VALUE && obj.getOrganizationType() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("ORGANIZATION_TYPE=?,"); 
				} 
				else if(obj.getOrganizationType() == ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("ORGANIZATION_TYPE=NULL,"); 
				} 
				if(obj.getOrganizationCode() != null && !obj.getOrganizationCode().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("ORGANIZATION_CODE=?,"); 
				} 
				else if(obj.getOrganizationCode() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("ORGANIZATION_CODE=NULL,"); 
				} 
				if(obj.getCreatedDate() != null && !obj.getCreatedDate().equals(ValueObject.DATE_TO_NULL_VALUE)) {
					sql.append("CREATED_DATE=?,"); 
				} 
				else if(obj.getCreatedDate()!= null && obj.getCreatedDate().equals(ValueObject.DATE_TO_NULL_VALUE)) { 
 					sql.append("CREATED_DATE=NULL,"); 
				} 
				if(obj.getOrganizationId() != Integer.MIN_VALUE && obj.getOrganizationId() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("ORGANIZATION_ID=?,"); 
				} 
				else if(obj.getOrganizationId() == ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("ORGANIZATION_ID=NULL,"); 
				} 
				if(obj.getClosedDate() != null && !obj.getClosedDate().equals(ValueObject.DATE_TO_NULL_VALUE)) {
					sql.append("CLOSED_DATE=?,"); 
				} 
				else if(obj.getClosedDate()!= null && obj.getClosedDate().equals(ValueObject.DATE_TO_NULL_VALUE)) { 
 					sql.append("CLOSED_DATE=NULL,"); 
				} 
				sql.setLength(sql.length() - 1);
				String where = " where ORGANIZATION.ORGANIZATION_ID = " + obj.getOrganizationId() ;
				sql.append(where);

				pstmt = conn.prepareStatement(sql.toString());

				int dirtycount = 0;
				if(obj.getOrganizationName() != null && !obj.getOrganizationName().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getOrganizationName()); 
				}
				if(obj.getOrganizationType() != Integer.MIN_VALUE && obj.getOrganizationType() != ValueObject.NUMBER_TO_NULL_VALUE) {
					pstmt.setInt(++dirtycount, obj.getOrganizationType());
				}
				if(obj.getOrganizationCode() != null && !obj.getOrganizationCode().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getOrganizationCode()); 
				}
				if(obj.getCreatedDate() != null && !obj.getCreatedDate().equals(ValueObject.DATE_TO_NULL_VALUE)) {
					pstmt.setTimestamp(++dirtycount, obj.getCreatedDate()); 
				}
				if(obj.getOrganizationId() != Integer.MIN_VALUE && obj.getOrganizationId() != ValueObject.NUMBER_TO_NULL_VALUE) {
					pstmt.setInt(++dirtycount, obj.getOrganizationId());
				}
				if(obj.getClosedDate() != null && !obj.getClosedDate().equals(ValueObject.DATE_TO_NULL_VALUE)) {
					pstmt.setTimestamp(++dirtycount, obj.getClosedDate()); 
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
				Organization obj = (Organization)volist.get(i);
				String sql = populateDeleteSql(obj);
				pstmt = conn.prepareStatement(sql);
				int dirtycount = 0;
				if(obj.getOrganizationName() != null && !obj.getOrganizationName().equals("")) {
					pstmt.setString(++dirtycount, obj.getOrganizationName()); 
				}
				if(obj.getOrganizationType() != Integer.MIN_VALUE) { 
					pstmt.setInt(++dirtycount, obj.getOrganizationType()); 
				}
				if(obj.getOrganizationCode() != null && !obj.getOrganizationCode().equals("")) {
					pstmt.setString(++dirtycount, obj.getOrganizationCode()); 
				}
				if(obj.getCreatedDate() != null) {
 					pstmt.setTimestamp(++dirtycount, obj.getCreatedDate()); 
				}
				if(obj.getOrganizationId() != Integer.MIN_VALUE) { 
					pstmt.setInt(++dirtycount, obj.getOrganizationId()); 
				}
				if(obj.getClosedDate() != null) {
 					pstmt.setTimestamp(++dirtycount, obj.getClosedDate()); 
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

	private String populateCreateSql(Organization obj){
		int dirtycount = 0;
		StringBuffer sql = new StringBuffer("INSERT into ORGANIZATION(");
		if(obj.getOrganizationName() != null && !obj.getOrganizationName().equals("")) {
			sql.append("ORGANIZATION_NAME").append(","); dirtycount++; 
		}
		if(obj.getOrganizationType() != Integer.MIN_VALUE) {
			sql.append("ORGANIZATION_TYPE").append(","); dirtycount++; 
		}
		if(obj.getOrganizationCode() != null && !obj.getOrganizationCode().equals("")) {
			sql.append("ORGANIZATION_CODE").append(","); dirtycount++; 
		}
		if(obj.getCreatedDate() != null) {
			sql.append("CREATED_DATE").append(","); dirtycount++; 
		}
		if(obj.getOrganizationId() != Integer.MIN_VALUE) {
			sql.append("ORGANIZATION_ID").append(","); dirtycount++; 
		}
		if(obj.getClosedDate() != null) {
			sql.append("CLOSED_DATE").append(","); dirtycount++; 
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

	private String populateDeleteSql(Organization obj){
		int dirtycount = 0;
		StringBuffer sql = new StringBuffer("DELETE FROM ORGANIZATION where ");
		if(obj.getOrganizationName() != null && !obj.getOrganizationName().equals("")) {
			if(dirtycount == 0){
				sql.append("ORGANIZATION_NAME=?").append(",");
				dirtycount++;
			}else{
				sql.append("ORGANIZATION_NAME=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getOrganizationType() != Integer.MIN_VALUE) {
			if(dirtycount == 0){
				sql.append("ORGANIZATION_TYPE=?").append(",");
				dirtycount++;
			}else{
				sql.append("ORGANIZATION_TYPE=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getOrganizationCode() != null && !obj.getOrganizationCode().equals("")) {
			if(dirtycount == 0){
				sql.append("ORGANIZATION_CODE=?").append(",");
				dirtycount++;
			}else{
				sql.append("ORGANIZATION_CODE=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getCreatedDate() != null && !obj.getCreatedDate().equals("")) {
			if(dirtycount == 0){
				sql.append("CREATED_DATE=?").append(",");
				dirtycount++;
			}else{
				sql.append("CREATED_DATE=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getOrganizationId() != Integer.MIN_VALUE) {
			if(dirtycount == 0){
				sql.append("ORGANIZATION_ID=?").append(",");
				dirtycount++;
			}else{
				sql.append("ORGANIZATION_ID=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getClosedDate() != null && !obj.getClosedDate().equals("")) {
			if(dirtycount == 0){
				sql.append("CLOSED_DATE=?").append(",");
				dirtycount++;
			}else{
				sql.append("CLOSED_DATE=?").append(",");
				dirtycount++;
			}
		}
		sql.setLength(sql.length() - 1);
		return sql.toString();
	}

	private Organization constructValueObject(ResultSet rs) throws Exception {
		Organization vo = new Organization();
		vo.setOrganizationName(rs.getString(1));
		int value1 = rs.getInt(2);
		if(value1 == 0 && rs.wasNull()){
			vo.setOrganizationType(Integer.MIN_VALUE);
		}else{
			vo.setOrganizationType(value1);
		}
		vo.setOrganizationCode(rs.getString(3));
		Timestamp value3= rs.getTimestamp(4);
		if(value3 == null && rs.wasNull()){
			vo.setCreatedDate(new java.sql.Timestamp(0));
		}else{
			vo.setCreatedDate(value3);
		}
		int value4 = rs.getInt(5);
		if(value4 == 0 && rs.wasNull()){
			vo.setOrganizationId(Integer.MIN_VALUE);
		}else{
			vo.setOrganizationId(value4);
		}
		Timestamp value5= rs.getTimestamp(6);
		if(value5 == null && rs.wasNull()){
			vo.setClosedDate(new java.sql.Timestamp(0));
		}else{
			vo.setClosedDate(value5);
		}
		return vo;
	}

	private Organization constructValueObject(ResultSet rs, int[] columnList) throws Exception {
		Organization vo = new Organization();
		int pos = 0;
		for(int i = 0; i < columnList.length; i++) {;
			switch(columnList[i]) {
				case ORGANIZATION_NAME: 
					vo.setOrganizationName(rs.getString(++pos));
					break;
				case ORGANIZATION_TYPE: 
					int value1 = rs.getInt(++pos);
					if(value1  == 0 && rs.wasNull()){
					vo.setOrganizationType(Integer.MIN_VALUE);
					}else{
					vo.setOrganizationType(value1);
					}
					break;
				case ORGANIZATION_CODE: 
					vo.setOrganizationCode(rs.getString(++pos));
					break;
				case CREATED_DATE: 
					Timestamp value3 = rs.getTimestamp(++pos);
					if(value3  == null && rs.wasNull()){
					vo.setCreatedDate(new java.sql.Timestamp(0));
					}else{
					vo.setCreatedDate(value3);
					}
					break;
				case ORGANIZATION_ID: 
					int value4 = rs.getInt(++pos);
					if(value4  == 0 && rs.wasNull()){
					vo.setOrganizationId(Integer.MIN_VALUE);
					}else{
					vo.setOrganizationId(value4);
					}
					break;
				case CLOSED_DATE: 
					Timestamp value5 = rs.getTimestamp(++pos);
					if(value5  == null && rs.wasNull()){
					vo.setClosedDate(new java.sql.Timestamp(0));
					}else{
					vo.setClosedDate(value5);
					}
					break;
			}
		}
		return vo;
	}

	private Logger getLogging(){
		return Logger.getLogger(this.getClass());
	}
}

