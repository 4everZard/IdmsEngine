package ca.on.moh.idms.persister;

/**
 * The class implementation to manage DrugPersisterImpl persistent. 
 * <br> 
 * Automatically generated persister calss. 
 * If any questions, contact Davy Hu (hu_davy@yahoo.com)
 */

import gov.moh.app.db.DBConnectionManager;
import gov.moh.config.vo.ValueObject;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ca.on.moh.idms.vo.Drug;

public class DrugPersisterImpl extends DBConnectionManager implements DrugPersister{

	static final long serialVersionUID = 42242;

	static final String[] DRUG_COLUMN_NAMES= {
			"DRUG.REC_EFF_DT",
			"DRUG.REC_CREATE_TIMESTAMP",
			"DRUG.REC_INACTIVE_TIMESTAMP",
			"DRUG.DBP_LIM_JL115",
			"DRUG.DRUG_ID",
			"DRUG.INDIVIDUAL_PRICE",
			"DRUG.AGENCY_ID",
			"DRUG.DOSAGE_FORM",
			"DRUG.MANUFACTURER_CD",
			"DRUG.DIN_PIN",
			"DRUG.DIN_DESC",
			"DRUG.GEN_NAME",
			"DRUG.STRENGTH"
			};

	protected static final String ALL_COLUMNS= 
			"DRUG.REC_EFF_DT, " + 
			"DRUG.REC_CREATE_TIMESTAMP, " + 
			"DRUG.REC_INACTIVE_TIMESTAMP, " + 
			"DRUG.DBP_LIM_JL115, " + 
			"DRUG.DRUG_ID, " + 
			"DRUG.INDIVIDUAL_PRICE, " + 
			"DRUG.AGENCY_ID, " + 
			"DRUG.DOSAGE_FORM, " + 
			"DRUG.MANUFACTURER_CD, " + 
			"DRUG.DIN_PIN, " + 
			"DRUG.DIN_DESC, " + 
			"DRUG.GEN_NAME, " + 
			"DRUG.STRENGTH " ; 

	public DrugPersisterImpl(){
		super();
	}
	/**
	 * Find a record in the table by primary key.
	 *
	 * @param drugId int.
	 *
	 * @return Drug.
	 */
	public Drug findByPK(int drugId) throws Exception {
		Connection conn = null;
		try {
			conn = getConnection();
			return findByPK(drugId, conn);
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
	 * @param drugId int.
	 * @param conn Connection.
	 *
	 * @return Drug.
	 */
	public Drug findByPK(int drugId, Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("SELECT " + ALL_COLUMNS + " FROM DRUG WHERE DRUG.DRUG_ID=?");
			pstmt.setInt(1, drugId);
			List <ValueObject> list = findByPreparedStatement(pstmt);
			if(list.size() < 1){
				return null;
			}else {
				return (Drug) list.get(0);
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
	 * @param drugId int.
	 * @param conn Connection.
	 *
	 * @return Drug.
	 */
	public Drug findByPK(int drugId, String dataSourceName) throws Exception {
		Connection conn = null;
		try {
			conn = getConnection(dataSourceName);
			return findByPK(drugId, conn);
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
	 * @return list a list of Drug.
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
	 * @return list a list of Drug.
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
	 * @return list a list of Drug.
	 */
	public List <ValueObject> findAll(String orderBy,Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		try {
			String sql = "SELECT " + ALL_COLUMNS + " FROM DRUG";
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
	 * @return list a list of Drug.
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
	 * @return list a list of Drug.
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
	 * @return list a list of Drug.
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
	 * @return list a list of Drug.
	 */
	public List <ValueObject> findByPreparedStatement(PreparedStatement pstmt, int[] columnList, int maxRows) throws Exception {
		ResultSet rs = null;
		try {
			rs = pstmt.executeQuery();
			List <ValueObject> list = new ArrayList<ValueObject>();
			int indexInList = 0;
			while(rs.next() && (maxRows == 0 || list.size() < maxRows)) {
				if(columnList == null){
					Drug vo = constructValueObject(rs);
					vo.setIndexInList(indexInList);
					list.add(vo);
				}else{
					Drug vo = constructValueObject(rs, columnList);
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
	 * @return list a list of Drug.
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
	 * @return list a list of Drug.
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
	 * @return list a list of Drug.
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
	 * @return list a list of Drug.
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
	 * @return list a list of Drug.
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
	 * @return list a list of Drug.
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
	 * @return list a list of Drug.
	 */
	public List <ValueObject> findByWhere(String where, Connection conn, int[] columnList, int maxRows) throws Exception {
		String sql = null;
		where = formatSQLParameter(where);
		if(columnList == null){
			 sql = "select " + ALL_COLUMNS + " from DRUG " +  where;
		}else {
			StringBuffer buff = new StringBuffer(128);
			buff.append("select ");
			for(int i = 0; i < columnList.length; i++) {
				if(i != 0) buff.append(",");
				buff.append(DRUG_COLUMN_NAMES[columnList[i]]);
			}
			buff.append(" from DRUG ");
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
					Drug vo = constructValueObject(rs);
					vo.setIndexInList(indexInList);
					list.add(vo);
				}else{
					Drug vo = constructValueObject(rs, columnList);
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
	 * @param obj Drug, value object with the record to be stored in the database.
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
	 * @param obj Drug, value object with the record to be stored in the database.
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
	 * @param obj Drug, value object with the record to be stored in the database.
	 * @param conn Connection.
	 *
	 * @return saveCount int, the number of records created in the database.
	 */
	public int save(ValueObject object, Connection conn) throws Exception {
		int saveCount = 0;
		PreparedStatement pstmt = null;
		try {
			Drug obj = (Drug) object;
			if(obj != null) {
				String sql = populateCreateSql(obj);
				pstmt = conn.prepareStatement(sql.toString());
				int dirtycount = 0;
				if(obj.getRecEffDt() != null) {
 					pstmt.setDate(++dirtycount, obj.getRecEffDt()); 
				}
				if(obj.getRecCreateTimestamp() != null) {
 					pstmt.setDate(++dirtycount, obj.getRecCreateTimestamp()); 
				}
				if(obj.getRecInactiveTimestamp() != null) {
 					pstmt.setDate(++dirtycount, obj.getRecInactiveTimestamp()); 
				}
				if(obj.getDbpLimJl115() != Integer.MIN_VALUE) {
					pstmt.setDouble(++dirtycount, obj.getDbpLimJl115()); 
				}
				if(obj.getDrugId() != Integer.MIN_VALUE) { 
					pstmt.setInt(++dirtycount, obj.getDrugId()); 
				}
				if(obj.getIndividualPrice() != Integer.MIN_VALUE) {
					pstmt.setDouble(++dirtycount, obj.getIndividualPrice()); 
				}
				if(obj.getAgencyId() != Integer.MIN_VALUE) { 
					pstmt.setInt(++dirtycount, obj.getAgencyId()); 
				}
				if(obj.getDosageForm() != null && !obj.getDosageForm().equals("")) {
					pstmt.setString(++dirtycount, obj.getDosageForm()); 
				}
				if(obj.getManufacturerCd() != null && !obj.getManufacturerCd().equals("")) {
					pstmt.setString(++dirtycount, obj.getManufacturerCd()); 
				}
				if(obj.getDinPin() != null && !obj.getDinPin().equals("")) {
					pstmt.setString(++dirtycount, obj.getDinPin()); 
				}
				if(obj.getDinDesc() != null && !obj.getDinDesc().equals("")) {
					pstmt.setString(++dirtycount, obj.getDinDesc()); 
				}
				if(obj.getGenName() != null && !obj.getGenName().equals("")) {
					pstmt.setString(++dirtycount, obj.getGenName()); 
				}
				if(obj.getStrength() != null) { 
					pstmt.setString(++dirtycount, obj.getStrength()); 
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
	 * @param obj Drug.
	 *
	 * @return int, the number of records updated in the database.
	 */
	public int update(ValueObject obj) throws Exception {
		Drug o = ( Drug)obj;
		String where = " where DRUG.DRUG_ID = " + o.getDrugId() ;
		return update(obj, where);
	}

	/**
	 * Update the record into the value specified in the value object with the PK defined in the ValueObject in a given database.
	 *
	 * @param obj Drug.
	 * @param conn java.sql.Connection.
	 *
	 * @return int, the number of records updated in the database.
	 */
	public int update(ValueObject obj, Connection conn) throws Exception {
		Drug o = ( Drug)obj;
		String where = " where DRUG.DRUG_ID = " + o.getDrugId() ;
		return update(obj, where, conn);
	}

	/**
	 * Update the record into the value specified in the value object with the constraints defined in where clause.
	 *
	 * @param obj Drug.
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
	 * @param object Drug.
	 * @param where String.
	 * @param conn Connection.
	 *
	 * @return int, the number of records updated in the database.
	 */
	public int update(ValueObject object,String where, Connection conn) throws Exception {
		int updateCount = 0;
		PreparedStatement pstmt = null;
		try {
			Drug obj = (Drug)object;
			if(obj != null) {
				StringBuffer sql = new StringBuffer("UPDATE DRUG SET ");
				if(obj.getRecEffDt() != null && !obj.getRecEffDt().equals(ValueObject.DATE_TO_NULL_VALUE)) {
					sql.append("REC_EFF_DT=?,"); 
				} 
				else if(obj.getRecEffDt()!= null && obj.getRecEffDt().equals(ValueObject.DATE_TO_NULL_VALUE)) { 
 					sql.append("REC_EFF_DT=NULL,"); 
				} 
				if(obj.getRecCreateTimestamp() != null && !obj.getRecCreateTimestamp().equals(ValueObject.DATE_TO_NULL_VALUE)) {
					sql.append("REC_CREATE_TIMESTAMP=?,"); 
				} 
				else if(obj.getRecCreateTimestamp()!= null && obj.getRecCreateTimestamp().equals(ValueObject.DATE_TO_NULL_VALUE)) { 
 					sql.append("REC_CREATE_TIMESTAMP=NULL,"); 
				} 
				if(obj.getRecInactiveTimestamp() != null && !obj.getRecInactiveTimestamp().equals(ValueObject.DATE_TO_NULL_VALUE)) {
					sql.append("REC_INACTIVE_TIMESTAMP=?,"); 
				} 
				else if(obj.getRecInactiveTimestamp()!= null && obj.getRecInactiveTimestamp().equals(ValueObject.DATE_TO_NULL_VALUE)) { 
 					sql.append("REC_INACTIVE_TIMESTAMP=NULL,"); 
				} 
				if(obj.getDbpLimJl115() != Integer.MIN_VALUE && obj.getDbpLimJl115() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("DBP_LIM_JL115=?,"); 
				} 
				else if(obj.getDbpLimJl115() == ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("DBP_LIM_JL115=NULL,"); 
				} 
				if(obj.getDrugId() != Integer.MIN_VALUE && obj.getDrugId() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("DRUG_ID=?,"); 
				} 
				else if(obj.getDrugId() == ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("DRUG_ID=NULL,"); 
				} 
				if(obj.getIndividualPrice() != Integer.MIN_VALUE && obj.getIndividualPrice() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("INDIVIDUAL_PRICE=?,"); 
				} 
				else if(obj.getIndividualPrice() == ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("INDIVIDUAL_PRICE=NULL,"); 
				} 
				if(obj.getAgencyId() != Integer.MIN_VALUE && obj.getAgencyId() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("AGENCY_ID=?,"); 
				} 
				else if(obj.getAgencyId() == ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("AGENCY_ID=NULL,"); 
				} 
				if(obj.getDosageForm() != null && !obj.getDosageForm().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("DOSAGE_FORM=?,"); 
				} 
				else if(obj.getDosageForm() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("DOSAGE_FORM=NULL,"); 
				} 
				if(obj.getManufacturerCd() != null && !obj.getManufacturerCd().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("MANUFACTURER_CD=?,"); 
				} 
				else if(obj.getManufacturerCd() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("MANUFACTURER_CD=NULL,"); 
				} 
				if(obj.getDinPin() != null && !obj.getDinPin().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("DIN_PIN=?,"); 
				} 
				else if(obj.getDinPin() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("DIN_PIN=NULL,"); 
				} 
				if(obj.getDinDesc() != null && !obj.getDinDesc().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("DIN_DESC=?,"); 
				} 
				else if(obj.getDinDesc() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("DIN_DESC=NULL,"); 
				} 
				if(obj.getGenName() != null && !obj.getGenName().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("GEN_NAME=?,"); 
				} 
				else if(obj.getGenName() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("GEN_NAME=NULL,"); 
				} 
				if(obj.getStrength() != null && !obj.getStrength().equals("")) { 
					sql.append("STRENGTH=?,"); 
				} 
				else if(obj.getStrength() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("STRENGTH=NULL,"); 
				} 
				sql.setLength(sql.length() - 1);
				where = formatSQLParameter(where);
				sql.append(where);

				pstmt = conn.prepareStatement(sql.toString());

				int dirtycount = 0;
				if(obj.getRecEffDt() != null && !obj.getRecEffDt().equals(ValueObject.DATE_TO_NULL_VALUE)) {
					pstmt.setDate(++dirtycount, obj.getRecEffDt()); 
				}
				if(obj.getRecCreateTimestamp() != null && !obj.getRecCreateTimestamp().equals(ValueObject.DATE_TO_NULL_VALUE)) {
					pstmt.setDate(++dirtycount, obj.getRecCreateTimestamp()); 
				}
				if(obj.getRecInactiveTimestamp() != null && !obj.getRecInactiveTimestamp().equals(ValueObject.DATE_TO_NULL_VALUE)) {
					pstmt.setDate(++dirtycount, obj.getRecInactiveTimestamp()); 
				}
				if(obj.getDbpLimJl115() != Integer.MIN_VALUE && obj.getDbpLimJl115() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					pstmt.setDouble(++dirtycount, obj.getDbpLimJl115()); 
				}
				if(obj.getDrugId() != Integer.MIN_VALUE && obj.getDrugId() != ValueObject.NUMBER_TO_NULL_VALUE) {
					pstmt.setInt(++dirtycount, obj.getDrugId());
				}
				if(obj.getIndividualPrice() != Integer.MIN_VALUE && obj.getIndividualPrice() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					pstmt.setDouble(++dirtycount, obj.getIndividualPrice()); 
				}
				if(obj.getAgencyId() != Integer.MIN_VALUE && obj.getAgencyId() != ValueObject.NUMBER_TO_NULL_VALUE) {
					pstmt.setInt(++dirtycount, obj.getAgencyId());
				}
				if(obj.getDosageForm() != null && !obj.getDosageForm().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getDosageForm()); 
				}
				if(obj.getManufacturerCd() != null && !obj.getManufacturerCd().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getManufacturerCd()); 
				}
				if(obj.getDinPin() != null && !obj.getDinPin().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getDinPin()); 
				}
				if(obj.getDinDesc() != null && !obj.getDinDesc().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getDinDesc()); 
				}
				if(obj.getGenName() != null && !obj.getGenName().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getGenName()); 
				}
				if(obj.getStrength() != null && !obj.getStrength().equals("")) {
					pstmt.setString(++dirtycount, obj.getStrength());
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
	 * @param obj Drug.
	 * @param dataSourceName String.
	 *
	 * @return int, the number of records updated in the database.
	 */
	public int updateTheGivenDataSource(ValueObject obj, String dataSourcename) throws Exception {
		Drug o = ( Drug)obj;
		String where = " where DRUG.DRUG_ID = " + o.getDrugId() ;
		return updateTheGivenDataSource(obj,where,dataSourcename);
	}

	/**
	 * Update the record into the value specified in the value object with the constraints defined in where clause.
	 *
	 * @param obj Drug.
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
	 * @param drugId int.
	 *
	 * @return int, the number of records deleted in the database.
	 */
	public int deleteByPK(int drugId) throws Exception {
		String where = " where DRUG.DRUG_ID = " + drugId ;
		return deleteByWhere(where);
	}

	/**
	 * Delete the record in the table by primary key.
	 *
	 * @param drugId int.
	 * @param dataSourceName String.
	 *
	 * @return int, the number of records deleted in the database.
	 */
	public int deleteByPK(int drugId,String dataSourceName) throws Exception {
		String where = " where DRUG.DRUG_ID = " + drugId ;
		try {
			return deleteByWhere(where, dataSourceName);
		}catch(Exception e) {
			throw e;
		}
	}

	/**
	 * Delete the record in the table by primary key.
	 *
	 * @param drugId int.
	 * @param conn java.sql.Connection.
	 *
	 * @return int, the number of records deleted in the database.
	 */
	public int deleteByPK(int drugId,Connection conn) throws Exception {
		String where = " where DRUG.DRUG_ID = " + drugId ;
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
			String sql = "DELETE from DRUG ";
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
				Drug obj = (Drug)volist.get(i);
				String sql = populateCreateSql(obj);
				pstmt = conn.prepareStatement(sql);
				int dirtycount = 0;
				if(obj.getRecEffDt() != null) {
 					pstmt.setDate(++dirtycount, obj.getRecEffDt()); 
				}
				if(obj.getRecCreateTimestamp() != null) {
 					pstmt.setDate(++dirtycount, obj.getRecCreateTimestamp()); 
				}
				if(obj.getRecInactiveTimestamp() != null) {
 					pstmt.setDate(++dirtycount, obj.getRecInactiveTimestamp()); 
				}
				if(obj.getDbpLimJl115() != Integer.MIN_VALUE) {
					pstmt.setDouble(++dirtycount, obj.getDbpLimJl115()); 
				}
				if(obj.getDrugId() != Integer.MIN_VALUE) { 
					pstmt.setInt(++dirtycount, obj.getDrugId()); 
				}
				if(obj.getIndividualPrice() != Integer.MIN_VALUE) {
					pstmt.setDouble(++dirtycount, obj.getIndividualPrice()); 
				}
				if(obj.getAgencyId() != Integer.MIN_VALUE) { 
					pstmt.setInt(++dirtycount, obj.getAgencyId()); 
				}
				if(obj.getDosageForm() != null && !obj.getDosageForm().equals("")) {
					pstmt.setString(++dirtycount, obj.getDosageForm()); 
				}
				if(obj.getManufacturerCd() != null && !obj.getManufacturerCd().equals("")) {
					pstmt.setString(++dirtycount, obj.getManufacturerCd()); 
				}
				if(obj.getDinPin() != null && !obj.getDinPin().equals("")) {
					pstmt.setString(++dirtycount, obj.getDinPin()); 
				}
				if(obj.getDinDesc() != null && !obj.getDinDesc().equals("")) {
					pstmt.setString(++dirtycount, obj.getDinDesc()); 
				}
				if(obj.getGenName() != null && !obj.getGenName().equals("")) {
					pstmt.setString(++dirtycount, obj.getGenName()); 
				}
				if(obj.getStrength() != null && !obj.getStrength().equals("")) { 
					pstmt.setString(++dirtycount, obj.getStrength()); 
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
				Drug obj = (Drug)volist.get(i);
				StringBuffer sql = new StringBuffer("UPDATE DRUG SET ");
				if(obj.getRecEffDt() != null && !obj.getRecEffDt().equals(ValueObject.DATE_TO_NULL_VALUE)) {
					sql.append("REC_EFF_DT=?,"); 
				} 
				else if(obj.getRecEffDt()!= null && obj.getRecEffDt().equals(ValueObject.DATE_TO_NULL_VALUE)) { 
 					sql.append("REC_EFF_DT=NULL,"); 
				} 
				if(obj.getRecCreateTimestamp() != null && !obj.getRecCreateTimestamp().equals(ValueObject.DATE_TO_NULL_VALUE)) {
					sql.append("REC_CREATE_TIMESTAMP=?,"); 
				} 
				else if(obj.getRecCreateTimestamp()!= null && obj.getRecCreateTimestamp().equals(ValueObject.DATE_TO_NULL_VALUE)) { 
 					sql.append("REC_CREATE_TIMESTAMP=NULL,"); 
				} 
				if(obj.getRecInactiveTimestamp() != null && !obj.getRecInactiveTimestamp().equals(ValueObject.DATE_TO_NULL_VALUE)) {
					sql.append("REC_INACTIVE_TIMESTAMP=?,"); 
				} 
				else if(obj.getRecInactiveTimestamp()!= null && obj.getRecInactiveTimestamp().equals(ValueObject.DATE_TO_NULL_VALUE)) { 
 					sql.append("REC_INACTIVE_TIMESTAMP=NULL,"); 
				} 
				if(obj.getDbpLimJl115() != Integer.MIN_VALUE && obj.getDbpLimJl115() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("DBP_LIM_JL115=?,"); 
				} 
				else if(obj.getDbpLimJl115() == ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("DBP_LIM_JL115=NULL,"); 
				} 
				if(obj.getDrugId() != Integer.MIN_VALUE && obj.getDrugId() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("DRUG_ID=?,"); 
				} 
				else if(obj.getDrugId() == ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("DRUG_ID=NULL,"); 
				} 
				if(obj.getIndividualPrice() != Integer.MIN_VALUE && obj.getIndividualPrice() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("INDIVIDUAL_PRICE=?,"); 
				} 
				else if(obj.getIndividualPrice() == ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("INDIVIDUAL_PRICE=NULL,"); 
				} 
				if(obj.getAgencyId() != Integer.MIN_VALUE && obj.getAgencyId() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("AGENCY_ID=?,"); 
				} 
				else if(obj.getAgencyId() == ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("AGENCY_ID=NULL,"); 
				} 
				if(obj.getDosageForm() != null && !obj.getDosageForm().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("DOSAGE_FORM=?,"); 
				} 
				else if(obj.getDosageForm() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("DOSAGE_FORM=NULL,"); 
				} 
				if(obj.getManufacturerCd() != null && !obj.getManufacturerCd().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("MANUFACTURER_CD=?,"); 
				} 
				else if(obj.getManufacturerCd() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("MANUFACTURER_CD=NULL,"); 
				} 
				if(obj.getDinPin() != null && !obj.getDinPin().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("DIN_PIN=?,"); 
				} 
				else if(obj.getDinPin() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("DIN_PIN=NULL,"); 
				} 
				if(obj.getDinDesc() != null && !obj.getDinDesc().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("DIN_DESC=?,"); 
				} 
				else if(obj.getDinDesc() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("DIN_DESC=NULL,"); 
				} 
				if(obj.getGenName() != null && !obj.getGenName().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("GEN_NAME=?,"); 
				} 
				else if(obj.getGenName() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("GEN_NAME=NULL,"); 
				} 
				if(obj.getStrength() != null && !obj.getStrength().equals("")) { 
					sql.append("STRENGTH=?,"); 
				} 
				else if(obj.getStrength() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("STRENGTH=NULL,"); 
				} 
				sql.setLength(sql.length() - 1);
				String where = " where DRUG.DRUG_ID = " + obj.getDrugId() ;
				sql.append(where);

				pstmt = conn.prepareStatement(sql.toString());

				int dirtycount = 0;
				if(obj.getRecEffDt() != null && !obj.getRecEffDt().equals(ValueObject.DATE_TO_NULL_VALUE)) {
					pstmt.setDate(++dirtycount, obj.getRecEffDt()); 
				}
				if(obj.getRecCreateTimestamp() != null && !obj.getRecCreateTimestamp().equals(ValueObject.DATE_TO_NULL_VALUE)) {
					pstmt.setDate(++dirtycount, obj.getRecCreateTimestamp()); 
				}
				if(obj.getRecInactiveTimestamp() != null && !obj.getRecInactiveTimestamp().equals(ValueObject.DATE_TO_NULL_VALUE)) {
					pstmt.setDate(++dirtycount, obj.getRecInactiveTimestamp()); 
				}
				if(obj.getDbpLimJl115() != Integer.MIN_VALUE && obj.getDbpLimJl115() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					pstmt.setDouble(++dirtycount, obj.getDbpLimJl115()); 
				}
				if(obj.getDrugId() != Integer.MIN_VALUE && obj.getDrugId() != ValueObject.NUMBER_TO_NULL_VALUE) {
					pstmt.setInt(++dirtycount, obj.getDrugId());
				}
				if(obj.getIndividualPrice() != Integer.MIN_VALUE && obj.getIndividualPrice() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					pstmt.setDouble(++dirtycount, obj.getIndividualPrice()); 
				}
				if(obj.getAgencyId() != Integer.MIN_VALUE && obj.getAgencyId() != ValueObject.NUMBER_TO_NULL_VALUE) {
					pstmt.setInt(++dirtycount, obj.getAgencyId());
				}
				if(obj.getDosageForm() != null && !obj.getDosageForm().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getDosageForm()); 
				}
				if(obj.getManufacturerCd() != null && !obj.getManufacturerCd().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getManufacturerCd()); 
				}
				if(obj.getDinPin() != null && !obj.getDinPin().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getDinPin()); 
				}
				if(obj.getDinDesc() != null && !obj.getDinDesc().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getDinDesc()); 
				}
				if(obj.getGenName() != null && !obj.getGenName().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getGenName()); 
				}
				if(obj.getStrength() != null && !obj.getStrength().equals("")) {
					pstmt.setString(++dirtycount, obj.getStrength());
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
				Drug obj = (Drug)volist.get(i);
				String sql = populateDeleteSql(obj);
				pstmt = conn.prepareStatement(sql);
				int dirtycount = 0;
				if(obj.getRecEffDt() != null) {
 					pstmt.setDate(++dirtycount, obj.getRecEffDt()); 
				}
				if(obj.getRecCreateTimestamp() != null) {
 					pstmt.setDate(++dirtycount, obj.getRecCreateTimestamp()); 
				}
				if(obj.getRecInactiveTimestamp() != null) {
 					pstmt.setDate(++dirtycount, obj.getRecInactiveTimestamp()); 
				}
				if(obj.getDbpLimJl115() != Integer.MIN_VALUE) {
					pstmt.setDouble(++dirtycount, obj.getDbpLimJl115()); 
				}
				if(obj.getDrugId() != Integer.MIN_VALUE) { 
					pstmt.setInt(++dirtycount, obj.getDrugId()); 
				}
				if(obj.getIndividualPrice() != Integer.MIN_VALUE) {
					pstmt.setDouble(++dirtycount, obj.getIndividualPrice()); 
				}
				if(obj.getAgencyId() != Integer.MIN_VALUE) { 
					pstmt.setInt(++dirtycount, obj.getAgencyId()); 
				}
				if(obj.getDosageForm() != null && !obj.getDosageForm().equals("")) {
					pstmt.setString(++dirtycount, obj.getDosageForm()); 
				}
				if(obj.getManufacturerCd() != null && !obj.getManufacturerCd().equals("")) {
					pstmt.setString(++dirtycount, obj.getManufacturerCd()); 
				}
				if(obj.getDinPin() != null && !obj.getDinPin().equals("")) {
					pstmt.setString(++dirtycount, obj.getDinPin()); 
				}
				if(obj.getDinDesc() != null && !obj.getDinDesc().equals("")) {
					pstmt.setString(++dirtycount, obj.getDinDesc()); 
				}
				if(obj.getGenName() != null && !obj.getGenName().equals("")) {
					pstmt.setString(++dirtycount, obj.getGenName()); 
				}
				if(obj.getStrength() != null && !obj.getStrength().equals("")) { 
					pstmt.setString(++dirtycount, obj.getStrength()); 
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

	private String populateCreateSql(Drug obj){
		int dirtycount = 0;
		StringBuffer sql = new StringBuffer("INSERT into DRUG(");
		if(obj.getRecEffDt() != null) {
			sql.append("REC_EFF_DT").append(","); dirtycount++; 
		}
		if(obj.getRecCreateTimestamp() != null) {
			sql.append("REC_CREATE_TIMESTAMP").append(","); dirtycount++; 
		}
		if(obj.getRecInactiveTimestamp() != null) {
			sql.append("REC_INACTIVE_TIMESTAMP").append(","); dirtycount++; 
		}
		if(obj.getDbpLimJl115() != Integer.MIN_VALUE) {
			sql.append("DBP_LIM_JL115").append(","); dirtycount++; 
		}
		if(obj.getDrugId() != Integer.MIN_VALUE) {
			sql.append("DRUG_ID").append(","); dirtycount++; 
		}
		if(obj.getIndividualPrice() != Integer.MIN_VALUE) {
			sql.append("INDIVIDUAL_PRICE").append(","); dirtycount++; 
		}
		if(obj.getAgencyId() != Integer.MIN_VALUE) {
			sql.append("AGENCY_ID").append(","); dirtycount++; 
		}
		if(obj.getDosageForm() != null && !obj.getDosageForm().equals("")) {
			sql.append("DOSAGE_FORM").append(","); dirtycount++; 
		}
		if(obj.getManufacturerCd() != null && !obj.getManufacturerCd().equals("")) {
			sql.append("MANUFACTURER_CD").append(","); dirtycount++; 
		}
		if(obj.getDinPin() != null && !obj.getDinPin().equals("")) {
			sql.append("DIN_PIN").append(","); dirtycount++; 
		}
		if(obj.getDinDesc() != null && !obj.getDinDesc().equals("")) {
			sql.append("DIN_DESC").append(","); dirtycount++; 
		}
		if(obj.getGenName() != null && !obj.getGenName().equals("")) {
			sql.append("GEN_NAME").append(","); dirtycount++; 
		}
		if(obj.getStrength() != null && !obj.getStrength().equals("")) {
			sql.append("STRENGTH").append(","); dirtycount++; 
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

	private String populateDeleteSql(Drug obj){
		int dirtycount = 0;
		StringBuffer sql = new StringBuffer("DELETE FROM DRUG where ");
		if(obj.getRecEffDt() != null && !obj.getRecEffDt().equals("")) {
			if(dirtycount == 0){
				sql.append("REC_EFF_DT=?").append(",");
				dirtycount++;
			}else{
				sql.append("REC_EFF_DT=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getRecCreateTimestamp() != null && !obj.getRecCreateTimestamp().equals("")) {
			if(dirtycount == 0){
				sql.append("REC_CREATE_TIMESTAMP=?").append(",");
				dirtycount++;
			}else{
				sql.append("REC_CREATE_TIMESTAMP=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getRecInactiveTimestamp() != null && !obj.getRecInactiveTimestamp().equals("")) {
			if(dirtycount == 0){
				sql.append("REC_INACTIVE_TIMESTAMP=?").append(",");
				dirtycount++;
			}else{
				sql.append("REC_INACTIVE_TIMESTAMP=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getDbpLimJl115() != Integer.MIN_VALUE) {
			if(dirtycount == 0){
				sql.append("DBP_LIM_JL115=?").append(",");
				dirtycount++;
			}else{
				sql.append("DBP_LIM_JL115=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getDrugId() != Integer.MIN_VALUE) {
			if(dirtycount == 0){
				sql.append("DRUG_ID=?").append(",");
				dirtycount++;
			}else{
				sql.append("DRUG_ID=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getIndividualPrice() != Integer.MIN_VALUE) {
			if(dirtycount == 0){
				sql.append("INDIVIDUAL_PRICE=?").append(",");
				dirtycount++;
			}else{
				sql.append("INDIVIDUAL_PRICE=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getAgencyId() != Integer.MIN_VALUE) {
			if(dirtycount == 0){
				sql.append("AGENCY_ID=?").append(",");
				dirtycount++;
			}else{
				sql.append("AGENCY_ID=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getDosageForm() != null && !obj.getDosageForm().equals("")) {
			if(dirtycount == 0){
				sql.append("DOSAGE_FORM=?").append(",");
				dirtycount++;
			}else{
				sql.append("DOSAGE_FORM=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getManufacturerCd() != null && !obj.getManufacturerCd().equals("")) {
			if(dirtycount == 0){
				sql.append("MANUFACTURER_CD=?").append(",");
				dirtycount++;
			}else{
				sql.append("MANUFACTURER_CD=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getDinPin() != null && !obj.getDinPin().equals("")) {
			if(dirtycount == 0){
				sql.append("DIN_PIN=?").append(",");
				dirtycount++;
			}else{
				sql.append("DIN_PIN=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getDinDesc() != null && !obj.getDinDesc().equals("")) {
			if(dirtycount == 0){
				sql.append("DIN_DESC=?").append(",");
				dirtycount++;
			}else{
				sql.append("DIN_DESC=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getGenName() != null && !obj.getGenName().equals("")) {
			if(dirtycount == 0){
				sql.append("GEN_NAME=?").append(",");
				dirtycount++;
			}else{
				sql.append("GEN_NAME=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getStrength() != null && !obj.getStrength().equals("")) {
			if(dirtycount == 0){
				sql.append("STRENGTH=?").append(",");
				dirtycount++;
			}else{
				sql.append("STRENGTH=?").append(",");
				dirtycount++;
			}
		}
		sql.setLength(sql.length() - 1);
		return sql.toString();
	}

	private Drug constructValueObject(ResultSet rs) throws Exception {
		Drug vo = new Drug();
		Date value0= rs.getDate(1);
		if(value0 == null && rs.wasNull()){
			vo.setRecEffDt(new java.sql.Date(0));
		}else{
			vo.setRecEffDt(value0);
		}
		Date value1= rs.getDate(2);
		if(value1 == null && rs.wasNull()){
			vo.setRecCreateTimestamp(new java.sql.Date(0));
		}else{
			vo.setRecCreateTimestamp(value1);
		}
		Date value2= rs.getDate(3);
		if(value2 == null && rs.wasNull()){
			vo.setRecInactiveTimestamp(new java.sql.Date(0));
		}else{
			vo.setRecInactiveTimestamp(value2);
		}
		double value3= rs.getDouble(4);
		if(value3  == 0 && rs.wasNull()){
			vo.setDbpLimJl115(Integer.MIN_VALUE);
		}else{
			vo.setDbpLimJl115(value3);
		}
		int value4 = rs.getInt(5);
		if(value4 == 0 && rs.wasNull()){
			vo.setDrugId(Integer.MIN_VALUE);
		}else{
			vo.setDrugId(value4);
		}
		double value5= rs.getDouble(6);
		if(value5  == 0 && rs.wasNull()){
			vo.setIndividualPrice(Integer.MIN_VALUE);
		}else{
			vo.setIndividualPrice(value5);
		}
		int value6 = rs.getInt(7);
		if(value6 == 0 && rs.wasNull()){
			vo.setAgencyId(Integer.MIN_VALUE);
		}else{
			vo.setAgencyId(value6);
		}
		vo.setDosageForm(rs.getString(8));
		vo.setManufacturerCd(rs.getString(9));
		vo.setDinPin(rs.getString(10));
		vo.setDinDesc(rs.getString(11));
		vo.setGenName(rs.getString(12));
		String value12 = rs.getString(13);
		if(value12 != null && !value12.equals("")){
			vo.setStrength(value12);
		}
		return vo;
	}

	private Drug constructValueObject(ResultSet rs, int[] columnList) throws Exception {
		Drug vo = new Drug();
		int pos = 0;
		for(int i = 0; i < columnList.length; i++) {;
			switch(columnList[i]) {
				case REC_EFF_DT: 
					Date value0 = rs.getDate(++pos);
					if(value0  == null && rs.wasNull()){
					vo.setRecEffDt(new java.sql.Date(0));
					}else{
					vo.setRecEffDt(value0);
					}
					break;
				case REC_CREATE_TIMESTAMP: 
					Date value1 = rs.getDate(++pos);
					if(value1  == null && rs.wasNull()){
					vo.setRecCreateTimestamp(new java.sql.Date(0));
					}else{
					vo.setRecCreateTimestamp(value1);
					}
					break;
				case REC_INACTIVE_TIMESTAMP: 
					Date value2 = rs.getDate(++pos);
					if(value2  == null && rs.wasNull()){
					vo.setRecInactiveTimestamp(new java.sql.Date(0));
					}else{
					vo.setRecInactiveTimestamp(value2);
					}
					break;
				case DBP_LIM_JL115: 
					double value3 = rs.getDouble(++pos);
					if(value3  == 0 && rs.wasNull()){
					vo.setDbpLimJl115(Integer.MIN_VALUE);
					}else{
					vo.setDbpLimJl115(value3);
					}
					break;
				case DRUG_ID: 
					int value4 = rs.getInt(++pos);
					if(value4  == 0 && rs.wasNull()){
					vo.setDrugId(Integer.MIN_VALUE);
					}else{
					vo.setDrugId(value4);
					}
					break;
				case INDIVIDUAL_PRICE: 
					double value5 = rs.getDouble(++pos);
					if(value5  == 0 && rs.wasNull()){
					vo.setIndividualPrice(Integer.MIN_VALUE);
					}else{
					vo.setIndividualPrice(value5);
					}
					break;
				case AGENCY_ID: 
					int value6 = rs.getInt(++pos);
					if(value6  == 0 && rs.wasNull()){
					vo.setAgencyId(Integer.MIN_VALUE);
					}else{
					vo.setAgencyId(value6);
					}
					break;
				case DOSAGE_FORM: 
					vo.setDosageForm(rs.getString(++pos));
					break;
				case MANUFACTURER_CD: 
					vo.setManufacturerCd(rs.getString(++pos));
					break;
				case DIN_PIN: 
					vo.setDinPin(rs.getString(++pos));
					break;
				case DIN_DESC: 
					vo.setDinDesc(rs.getString(++pos));
					break;
				case GEN_NAME: 
					vo.setGenName(rs.getString(++pos));
					break;
				case STRENGTH: 
					String value12 = rs.getString(++pos);
					if(value12  != null && !value12.equals("")){
						vo.setStrength(value12);
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

