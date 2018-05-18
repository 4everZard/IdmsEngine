package ca.on.moh.idms.persister;

/**
 * The class implementation to manage ClmhistPersisterImpl persistent. 
 * <br> 
 * Automatically generated persister calss. 
 * If any questions, contact Davy Hu (hu_davy@yahoo.com)
 */

import gov.moh.app.db.DBConnectionManager;
import gov.moh.config.vo.ValueObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ca.on.moh.idms.vo.Clmhist;

public class ClmhistPersisterImpl extends DBConnectionManager implements ClmhistPersister{

	static final long serialVersionUID = 6825275;

	static final String[] CLMHIST_COLUMN_NAMES= {
			"CLMHIST.MED_COND",
			"CLMHIST.MOH_PRESCRIBER_ID",
			"CLMHIST.ODB_ELIG_NO",
			"CLMHIST.THERA_CLASS",
			"CLMHIST.INTERVENTION_10",
			"CLMHIST.INTERVENTION_9",
			"CLMHIST.INTERVENTION_8",
			"CLMHIST.INTERVENTION_7",
			"CLMHIST.INTERVENTION_6",
			"CLMHIST.INTERVENTION_5",
			"CLMHIST.INTERVENTION_4",
			"CLMHIST.INTERVENTION_3",
			"CLMHIST.INTERVENTION_2",
			"CLMHIST.INTERVENTION_1",
			"CLMHIST.PROG_ID",
			"CLMHIST.CURR_STAT",
			"CLMHIST.PROD_SEL",
			"CLMHIST.CURR_RX_NO",
			"CLMHIST.TOT_AMT_PD",
			"CLMHIST.DEDUCT_TO_COLLECT",
			"CLMHIST.COMP_FEE_ALLD",
			"CLMHIST.PROF_FEE_ALLD",
			"CLMHIST.CST_UPCHRG_ALLD",
			"CLMHIST.DRG_CST_ALLD",
			"CLMHIST.QTY",
			"CLMHIST.ADJUDICATION_DT",
			"CLMHIST.DT_OF_SERV",
			"CLMHIST.LTC_FLAG",
			"CLMHIST.DIN_PIN",
			"CLMHIST.AGENCY_ID",
			"CLMHIST.CLAIM_ID",
			"CLMHIST.POSTAL_CD",
			"CLMHIST.AG_TYPE_CD",
			"CLMHIST.COUNTY_CD",
			"CLMHIST.PLAN_CD",
			"CLMHIST.DYS_SUPPLY"
			};

	protected static final String ALL_COLUMNS= 
			"CLMHIST.MED_COND, " + 
			"CLMHIST.MOH_PRESCRIBER_ID, " + 
			"CLMHIST.ODB_ELIG_NO, " + 
			"CLMHIST.THERA_CLASS, " + 
			"CLMHIST.INTERVENTION_10, " + 
			"CLMHIST.INTERVENTION_9, " + 
			"CLMHIST.INTERVENTION_8, " + 
			"CLMHIST.INTERVENTION_7, " + 
			"CLMHIST.INTERVENTION_6, " + 
			"CLMHIST.INTERVENTION_5, " + 
			"CLMHIST.INTERVENTION_4, " + 
			"CLMHIST.INTERVENTION_3, " + 
			"CLMHIST.INTERVENTION_2, " + 
			"CLMHIST.INTERVENTION_1, " + 
			"CLMHIST.PROG_ID, " + 
			"CLMHIST.CURR_STAT, " + 
			"CLMHIST.PROD_SEL, " + 
			"CLMHIST.CURR_RX_NO, " + 
			"CLMHIST.TOT_AMT_PD, " + 
			"CLMHIST.DEDUCT_TO_COLLECT, " + 
			"CLMHIST.COMP_FEE_ALLD, " + 
			"CLMHIST.PROF_FEE_ALLD, " + 
			"CLMHIST.CST_UPCHRG_ALLD, " + 
			"CLMHIST.DRG_CST_ALLD, " + 
			"CLMHIST.QTY, " + 
			"CLMHIST.ADJUDICATION_DT, " + 
			"CLMHIST.DT_OF_SERV, " + 
			"CLMHIST.LTC_FLAG, " + 
			"CLMHIST.DIN_PIN, " + 
			"CLMHIST.AGENCY_ID, " + 
			"CLMHIST.CLAIM_ID, " + 
			"CLMHIST.POSTAL_CD, " + 
			"CLMHIST.AG_TYPE_CD, " + 
			"CLMHIST.COUNTY_CD, " + 
			"CLMHIST.PLAN_CD, " + 
			"CLMHIST.DYS_SUPPLY " ; 

	public ClmhistPersisterImpl(){
		super();
	}
	/**
	 * Find a record in the table by primary key.
	 *
	 * @param claimId long.
	 *
	 * @return Clmhist.
	 */
	public Clmhist findByPK(long claimId) throws Exception {
		Connection conn = null;
		try {
			conn = getConnection();
			return findByPK(claimId, conn);
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
	 * @param claimId long.
	 * @param conn Connection.
	 *
	 * @return Clmhist.
	 */
	public Clmhist findByPK(long claimId, Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("SELECT " + ALL_COLUMNS + " FROM CLMHIST WHERE CLMHIST.CLAIM_ID=?");
			pstmt.setLong(1, claimId);
			List <ValueObject> list = findByPreparedStatement(pstmt);
			if(list.size() < 1){
				return null;
			}else {
				return (Clmhist) list.get(0);
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
	 * @param claimId long.
	 * @param conn Connection.
	 *
	 * @return Clmhist.
	 */
	public Clmhist findByPK(long claimId, String dataSourceName) throws Exception {
		Connection conn = null;
		try {
			conn = getConnection(dataSourceName);
			return findByPK(claimId, conn);
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
	 * @return list a list of Clmhist.
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
	 * @return list a list of Clmhist.
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
	 * @return list a list of Clmhist.
	 */
	public List <ValueObject> findAll(String orderBy,Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		try {
			String sql = "SELECT " + ALL_COLUMNS + " FROM CLMHIST";
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
	 * @return list a list of Clmhist.
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
	 * @return list a list of Clmhist.
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
	 * @return list a list of Clmhist.
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
	 * @return list a list of Clmhist.
	 */
	public List <ValueObject> findByPreparedStatement(PreparedStatement pstmt, int[] columnList, int maxRows) throws Exception {
		ResultSet rs = null;
		try {
			rs = pstmt.executeQuery();
			List <ValueObject> list = new ArrayList<ValueObject>();
			int indexInList = 0;
			while(rs.next() && (maxRows == 0 || list.size() < maxRows)) {
				if(columnList == null){
					Clmhist vo = constructValueObject(rs);
					vo.setIndexInList(indexInList);
					list.add(vo);
				}else{
					Clmhist vo = constructValueObject(rs, columnList);
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
	 * @return list a list of Clmhist.
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
	 * @return list a list of Clmhist.
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
	 * @return list a list of Clmhist.
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
	 * @return list a list of Clmhist.
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
	 * @return list a list of Clmhist.
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
	 * @return list a list of Clmhist.
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
	 * @return list a list of Clmhist.
	 */
	public List <ValueObject> findByWhere(String where, Connection conn, int[] columnList, int maxRows) throws Exception {
		String sql = null;
		where = formatSQLParameter(where);
		if(columnList == null){
			 sql = "select " + ALL_COLUMNS + " from CLMHIST " +  where;
		}else {
			StringBuffer buff = new StringBuffer(128);
			buff.append("select ");
			for(int i = 0; i < columnList.length; i++) {
				if(i != 0) buff.append(",");
				buff.append(CLMHIST_COLUMN_NAMES[columnList[i]]);
			}
			buff.append(" from CLMHIST ");
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
					Clmhist vo = constructValueObject(rs);
					vo.setIndexInList(indexInList);
					list.add(vo);
				}else{
					Clmhist vo = constructValueObject(rs, columnList);
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
	 * @param obj Clmhist, value object with the record to be stored in the database.
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
	 * @param obj Clmhist, value object with the record to be stored in the database.
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
				closeConnection(conn);
			}
		}
		return save(obj, conn);
	}
	/**
	 * Save the records into the database.
	 *
	 * @param obj Clmhist, value object with the record to be stored in the database.
	 * @param conn Connection.
	 *
	 * @return saveCount int, the number of records created in the database.
	 */
	public int save(ValueObject object, Connection conn) throws Exception {
		int saveCount = 0;
		PreparedStatement pstmt = null;
		try {
			Clmhist obj = (Clmhist) object;
			if(obj != null) {
				String sql = populateCreateSql(obj);
				pstmt = conn.prepareStatement(sql.toString());
				int dirtycount = 0;
				if(obj.getMedCond() != Integer.MIN_VALUE) { 
					pstmt.setInt(++dirtycount, obj.getMedCond()); 
				}
				if(obj.getMohPrescriberId() != null && !obj.getMohPrescriberId().equals("")) {
					pstmt.setString(++dirtycount, obj.getMohPrescriberId()); 
				}
				if(obj.getOdbEligNo() != null && !obj.getOdbEligNo().equals("")) {
					pstmt.setString(++dirtycount, obj.getOdbEligNo()); 
				}
				if(obj.getTheraClass() != null && !obj.getTheraClass().equals("")) {
					pstmt.setString(++dirtycount, obj.getTheraClass()); 
				}
				if(obj.getIntervention10() != null && !obj.getIntervention10().equals("")) {
					pstmt.setString(++dirtycount, obj.getIntervention10()); 
				}
				if(obj.getIntervention9() != null && !obj.getIntervention9().equals("")) {
					pstmt.setString(++dirtycount, obj.getIntervention9()); 
				}
				if(obj.getIntervention8() != null && !obj.getIntervention8().equals("")) {
					pstmt.setString(++dirtycount, obj.getIntervention8()); 
				}
				if(obj.getIntervention7() != null && !obj.getIntervention7().equals("")) {
					pstmt.setString(++dirtycount, obj.getIntervention7()); 
				}
				if(obj.getIntervention6() != null && !obj.getIntervention6().equals("")) {
					pstmt.setString(++dirtycount, obj.getIntervention6()); 
				}
				if(obj.getIntervention5() != null && !obj.getIntervention5().equals("")) {
					pstmt.setString(++dirtycount, obj.getIntervention5()); 
				}
				if(obj.getIntervention4() != null && !obj.getIntervention4().equals("")) {
					pstmt.setString(++dirtycount, obj.getIntervention4()); 
				}
				if(obj.getIntervention3() != null && !obj.getIntervention3().equals("")) {
					pstmt.setString(++dirtycount, obj.getIntervention3()); 
				}
				if(obj.getIntervention2() != null && !obj.getIntervention2().equals("")) {
					pstmt.setString(++dirtycount, obj.getIntervention2()); 
				}
				if(obj.getIntervention1() != null && !obj.getIntervention1().equals("")) {
					pstmt.setString(++dirtycount, obj.getIntervention1()); 
				}
				if(obj.getProgId() != null && !obj.getProgId().equals("")) {
					pstmt.setString(++dirtycount, obj.getProgId()); 
				}
				if(obj.getCurrStat() != null && !obj.getCurrStat().equals("")) {
					pstmt.setString(++dirtycount, obj.getCurrStat()); 
				}
				if(obj.getProdSel() != Integer.MIN_VALUE) {
					pstmt.setDouble(++dirtycount, obj.getProdSel()); 
				}
				if(obj.getCurrRxNo() != null && !obj.getCurrRxNo().equals("")) {
					pstmt.setString(++dirtycount, obj.getCurrRxNo()); 
				}
				if(obj.getTotAmtPd() != Integer.MIN_VALUE) {
					pstmt.setDouble(++dirtycount, obj.getTotAmtPd()); 
				}
				if(obj.getDeductToCollect() != Integer.MIN_VALUE) { 
					pstmt.setDouble(++dirtycount, obj.getDeductToCollect()); 
				}
				if(obj.getCompFeeAlld() != Integer.MIN_VALUE) { 
					pstmt.setInt(++dirtycount, obj.getCompFeeAlld()); 
				}
				if(obj.getProfFeeAlld() != Integer.MIN_VALUE) { 
					pstmt.setInt(++dirtycount, obj.getProfFeeAlld()); 
				}
				if(obj.getCstUpchrgAlld() != Integer.MIN_VALUE) {
					pstmt.setDouble(++dirtycount, obj.getCstUpchrgAlld()); 
				}
				if(obj.getDrgCstAlld() != Integer.MIN_VALUE) {
					pstmt.setDouble(++dirtycount, obj.getDrgCstAlld()); 
				}
				if(obj.getQty() != Integer.MIN_VALUE) { 
					pstmt.setDouble(++dirtycount, obj.getQty()); 
				}
				if(obj.getAdjudicationDt() != null) {
 					pstmt.setDate(++dirtycount, obj.getAdjudicationDt()); 
				}
				if(obj.getDtOfServ() != null) {
 					pstmt.setDate(++dirtycount, obj.getDtOfServ()); 
				}
				if(obj.getLtcFlag() != Integer.MIN_VALUE) { 
					pstmt.setInt(++dirtycount, obj.getLtcFlag()); 
				}
				if(obj.getDinPin() != null && !obj.getDinPin().equals("")) {
					pstmt.setString(++dirtycount, obj.getDinPin()); 
				}
				if(obj.getAgencyId() != Integer.MIN_VALUE) { 
					pstmt.setInt(++dirtycount, obj.getAgencyId()); 
				}
				if(obj.getClaimId() != Integer.MIN_VALUE) {
					pstmt.setLong(++dirtycount, obj.getClaimId()); 
				}
				if(obj.getPostalCd() != null && !obj.getPostalCd().equals("")) {
					pstmt.setString(++dirtycount, obj.getPostalCd()); 
				}
				if(obj.getAgTypeCd() != null && !obj.getAgTypeCd().equals("")) {
					pstmt.setString(++dirtycount, obj.getAgTypeCd()); 
				}
				if(obj.getCountyCd() != null && !obj.getCountyCd().equals("")) {
					pstmt.setString(++dirtycount, obj.getCountyCd()); 
				}
				if(obj.getPlanCd() != null && !obj.getPlanCd().equals("")) {
					pstmt.setString(++dirtycount, obj.getPlanCd()); 
				}
				if(obj.getDysSupply() != Integer.MIN_VALUE) { 
					pstmt.setInt(++dirtycount, obj.getDysSupply()); 
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
	 * @param obj Clmhist.
	 *
	 * @return int, the number of records updated in the database.
	 */
	public int update(ValueObject obj) throws Exception {
		Clmhist o = ( Clmhist)obj;
		String where = " where CLMHIST.CLAIM_ID = " + o.getClaimId() ;
		return update(obj, where);
	}

	/**
	 * Update the record into the value specified in the value object with the PK defined in the ValueObject in a given database.
	 *
	 * @param obj Clmhist.
	 * @param conn java.sql.Connection.
	 *
	 * @return int, the number of records updated in the database.
	 */
	public int update(ValueObject obj, Connection conn) throws Exception {
		Clmhist o = ( Clmhist)obj;
		String where = " where CLMHIST.CLAIM_ID = " + o.getClaimId() ;
		return update(obj, where, conn);
	}

	/**
	 * Update the record into the value specified in the value object with the constraints defined in where clause.
	 *
	 * @param obj Clmhist.
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
	 * @param object Clmhist.
	 * @param where String.
	 * @param conn Connection.
	 *
	 * @return int, the number of records updated in the database.
	 */
	public int update(ValueObject object,String where, Connection conn) throws Exception {
		int updateCount = 0;
		PreparedStatement pstmt = null;
		try {
			Clmhist obj = (Clmhist)object;
			if(obj != null) {
				StringBuffer sql = new StringBuffer("UPDATE CLMHIST SET ");
				if(obj.getMedCond() != Integer.MIN_VALUE && obj.getMedCond() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("MED_COND=?,"); 
				} 
				else if(obj.getMedCond() == ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("MED_COND=NULL,"); 
				} 
				if(obj.getMohPrescriberId() != null && !obj.getMohPrescriberId().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("MOH_PRESCRIBER_ID=?,"); 
				} 
				else if(obj.getMohPrescriberId() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("MOH_PRESCRIBER_ID=NULL,"); 
				} 
				if(obj.getOdbEligNo() != null && !obj.getOdbEligNo().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("ODB_ELIG_NO=?,"); 
				} 
				else if(obj.getOdbEligNo() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("ODB_ELIG_NO=NULL,"); 
				} 
				if(obj.getTheraClass() != null && !obj.getTheraClass().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("THERA_CLASS=?,"); 
				} 
				else if(obj.getTheraClass() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("THERA_CLASS=NULL,"); 
				} 
				if(obj.getIntervention10() != null && !obj.getIntervention10().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("INTERVENTION_10=?,"); 
				} 
				else if(obj.getIntervention10() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("INTERVENTION_10=NULL,"); 
				} 
				if(obj.getIntervention9() != null && !obj.getIntervention9().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("INTERVENTION_9=?,"); 
				} 
				else if(obj.getIntervention9() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("INTERVENTION_9=NULL,"); 
				} 
				if(obj.getIntervention8() != null && !obj.getIntervention8().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("INTERVENTION_8=?,"); 
				} 
				else if(obj.getIntervention8() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("INTERVENTION_8=NULL,"); 
				} 
				if(obj.getIntervention7() != null && !obj.getIntervention7().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("INTERVENTION_7=?,"); 
				} 
				else if(obj.getIntervention7() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("INTERVENTION_7=NULL,"); 
				} 
				if(obj.getIntervention6() != null && !obj.getIntervention6().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("INTERVENTION_6=?,"); 
				} 
				else if(obj.getIntervention6() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("INTERVENTION_6=NULL,"); 
				} 
				if(obj.getIntervention5() != null && !obj.getIntervention5().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("INTERVENTION_5=?,"); 
				} 
				else if(obj.getIntervention5() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("INTERVENTION_5=NULL,"); 
				} 
				if(obj.getIntervention4() != null && !obj.getIntervention4().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("INTERVENTION_4=?,"); 
				} 
				else if(obj.getIntervention4() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("INTERVENTION_4=NULL,"); 
				} 
				if(obj.getIntervention3() != null && !obj.getIntervention3().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("INTERVENTION_3=?,"); 
				} 
				else if(obj.getIntervention3() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("INTERVENTION_3=NULL,"); 
				} 
				if(obj.getIntervention2() != null && !obj.getIntervention2().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("INTERVENTION_2=?,"); 
				} 
				else if(obj.getIntervention2() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("INTERVENTION_2=NULL,"); 
				} 
				if(obj.getIntervention1() != null && !obj.getIntervention1().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("INTERVENTION_1=?,"); 
				} 
				else if(obj.getIntervention1() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("INTERVENTION_1=NULL,"); 
				} 
				if(obj.getProgId() != null && !obj.getProgId().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("PROG_ID=?,"); 
				} 
				else if(obj.getProgId() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("PROG_ID=NULL,"); 
				} 
				if(obj.getCurrStat() != null && !obj.getCurrStat().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("CURR_STAT=?,"); 
				} 
				else if(obj.getCurrStat() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("CURR_STAT=NULL,"); 
				} 
				if(obj.getProdSel() != Integer.MIN_VALUE && obj.getProdSel() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("PROD_SEL=?,"); 
				} 
				else if(obj.getProdSel() == ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("PROD_SEL=NULL,"); 
				} 
				if(obj.getCurrRxNo() != null && !obj.getCurrRxNo().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("CURR_RX_NO=?,"); 
				} 
				else if(obj.getCurrRxNo() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("CURR_RX_NO=NULL,"); 
				} 
				if(obj.getTotAmtPd() != Integer.MIN_VALUE && obj.getTotAmtPd() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("TOT_AMT_PD=?,"); 
				} 
				else if(obj.getTotAmtPd() == ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("TOT_AMT_PD=NULL,"); 
				} 
				if(obj.getDeductToCollect() != Integer.MIN_VALUE && obj.getDeductToCollect() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("DEDUCT_TO_COLLECT=?,"); 
				} 
				else if(obj.getDeductToCollect() == ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("DEDUCT_TO_COLLECT=NULL,"); 
				} 
				if(obj.getCompFeeAlld() != Integer.MIN_VALUE && obj.getCompFeeAlld() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("COMP_FEE_ALLD=?,"); 
				} 
				else if(obj.getCompFeeAlld() == ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("COMP_FEE_ALLD=NULL,"); 
				} 
				if(obj.getProfFeeAlld() != Integer.MIN_VALUE && obj.getProfFeeAlld() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("PROF_FEE_ALLD=?,"); 
				} 
				else if(obj.getProfFeeAlld() == ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("PROF_FEE_ALLD=NULL,"); 
				} 
				if(obj.getCstUpchrgAlld() != Integer.MIN_VALUE && obj.getCstUpchrgAlld() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("CST_UPCHRG_ALLD=?,"); 
				} 
				else if(obj.getCstUpchrgAlld() == ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("CST_UPCHRG_ALLD=NULL,"); 
				} 
				if(obj.getDrgCstAlld() != Integer.MIN_VALUE && obj.getDrgCstAlld() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("DRG_CST_ALLD=?,"); 
				} 
				else if(obj.getDrgCstAlld() == ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("DRG_CST_ALLD=NULL,"); 
				} 
				if(obj.getQty() != Integer.MIN_VALUE && obj.getQty() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("QTY=?,"); 
				} 
				else if(obj.getQty() == ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("QTY=NULL,"); 
				} 
				if(obj.getAdjudicationDt() != null && !obj.getAdjudicationDt().equals(ValueObject.DATE_TO_NULL_VALUE)) {
					sql.append("ADJUDICATION_DT=?,"); 
				} 
				else if(obj.getAdjudicationDt()!= null && obj.getAdjudicationDt().equals(ValueObject.DATE_TO_NULL_VALUE)) { 
 					sql.append("ADJUDICATION_DT=NULL,"); 
				} 
				if(obj.getDtOfServ() != null && !obj.getDtOfServ().equals(ValueObject.DATE_TO_NULL_VALUE)) {
					sql.append("DT_OF_SERV=?,"); 
				} 
				else if(obj.getDtOfServ()!= null && obj.getDtOfServ().equals(ValueObject.DATE_TO_NULL_VALUE)) { 
 					sql.append("DT_OF_SERV=NULL,"); 
				} 
				if(obj.getLtcFlag() != Integer.MIN_VALUE && obj.getLtcFlag() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("LTC_FLAG=?,"); 
				} 
				else if(obj.getLtcFlag() == ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("LTC_FLAG=NULL,"); 
				} 
				if(obj.getDinPin() != null && !obj.getDinPin().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("DIN_PIN=?,"); 
				} 
				else if(obj.getDinPin() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("DIN_PIN=NULL,"); 
				} 
				if(obj.getAgencyId() != Integer.MIN_VALUE && obj.getAgencyId() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("AGENCY_ID=?,"); 
				} 
				else if(obj.getAgencyId() == ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("AGENCY_ID=NULL,"); 
				} 
				if(obj.getClaimId() != Integer.MIN_VALUE && obj.getClaimId() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("CLAIM_ID=?,"); 
				} 
				else if(obj.getClaimId() == ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("CLAIM_ID=NULL,"); 
				} 
				if(obj.getPostalCd() != null && !obj.getPostalCd().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("POSTAL_CD=?,"); 
				} 
				else if(obj.getPostalCd() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("POSTAL_CD=NULL,"); 
				} 
				if(obj.getAgTypeCd() != null && !obj.getAgTypeCd().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("AG_TYPE_CD=?,"); 
				} 
				else if(obj.getAgTypeCd() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("AG_TYPE_CD=NULL,"); 
				} 
				if(obj.getCountyCd() != null && !obj.getCountyCd().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("COUNTY_CD=?,"); 
				} 
				else if(obj.getCountyCd() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("COUNTY_CD=NULL,"); 
				} 
				if(obj.getPlanCd() != null && !obj.getPlanCd().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("PLAN_CD=?,"); 
				} 
				else if(obj.getPlanCd() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("PLAN_CD=NULL,"); 
				} 
				if(obj.getDysSupply() != Integer.MIN_VALUE && obj.getDysSupply() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("DYS_SUPPLY=?,"); 
				} 
				else if(obj.getDysSupply() == ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("DYS_SUPPLY=NULL,"); 
				} 
				sql.setLength(sql.length() - 1);
				where = formatSQLParameter(where);
				sql.append(where);

				pstmt = conn.prepareStatement(sql.toString());

				int dirtycount = 0;
				if(obj.getMedCond() != Integer.MIN_VALUE && obj.getMedCond() != ValueObject.NUMBER_TO_NULL_VALUE) {
					pstmt.setInt(++dirtycount, obj.getMedCond());
				}
				if(obj.getMohPrescriberId() != null && !obj.getMohPrescriberId().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getMohPrescriberId()); 
				}
				if(obj.getOdbEligNo() != null && !obj.getOdbEligNo().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getOdbEligNo()); 
				}
				if(obj.getTheraClass() != null && !obj.getTheraClass().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getTheraClass()); 
				}
				if(obj.getIntervention10() != null && !obj.getIntervention10().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getIntervention10()); 
				}
				if(obj.getIntervention9() != null && !obj.getIntervention9().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getIntervention9()); 
				}
				if(obj.getIntervention8() != null && !obj.getIntervention8().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getIntervention8()); 
				}
				if(obj.getIntervention7() != null && !obj.getIntervention7().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getIntervention7()); 
				}
				if(obj.getIntervention6() != null && !obj.getIntervention6().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getIntervention6()); 
				}
				if(obj.getIntervention5() != null && !obj.getIntervention5().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getIntervention5()); 
				}
				if(obj.getIntervention4() != null && !obj.getIntervention4().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getIntervention4()); 
				}
				if(obj.getIntervention3() != null && !obj.getIntervention3().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getIntervention3()); 
				}
				if(obj.getIntervention2() != null && !obj.getIntervention2().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getIntervention2()); 
				}
				if(obj.getIntervention1() != null && !obj.getIntervention1().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getIntervention1()); 
				}
				if(obj.getProgId() != null && !obj.getProgId().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getProgId()); 
				}
				if(obj.getCurrStat() != null && !obj.getCurrStat().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getCurrStat()); 
				}
				if(obj.getProdSel() != Integer.MIN_VALUE && obj.getProdSel() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					pstmt.setDouble(++dirtycount, obj.getProdSel()); 
				}
				if(obj.getCurrRxNo() != null && !obj.getCurrRxNo().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getCurrRxNo()); 
				}
				if(obj.getTotAmtPd() != Integer.MIN_VALUE && obj.getTotAmtPd() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					pstmt.setDouble(++dirtycount, obj.getTotAmtPd()); 
				}
				if(obj.getDeductToCollect() != Integer.MIN_VALUE && obj.getDeductToCollect() != ValueObject.NUMBER_TO_NULL_VALUE) {
					pstmt.setDouble(++dirtycount, obj.getDeductToCollect());
				}
				if(obj.getCompFeeAlld() != Integer.MIN_VALUE && obj.getCompFeeAlld() != ValueObject.NUMBER_TO_NULL_VALUE) {
					pstmt.setInt(++dirtycount, obj.getCompFeeAlld());
				}
				if(obj.getProfFeeAlld() != Integer.MIN_VALUE && obj.getProfFeeAlld() != ValueObject.NUMBER_TO_NULL_VALUE) {
					pstmt.setInt(++dirtycount, obj.getProfFeeAlld());
				}
				if(obj.getCstUpchrgAlld() != Integer.MIN_VALUE && obj.getCstUpchrgAlld() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					pstmt.setDouble(++dirtycount, obj.getCstUpchrgAlld()); 
				}
				if(obj.getDrgCstAlld() != Integer.MIN_VALUE && obj.getDrgCstAlld() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					pstmt.setDouble(++dirtycount, obj.getDrgCstAlld()); 
				}
				if(obj.getQty() != Integer.MIN_VALUE && obj.getQty() != ValueObject.NUMBER_TO_NULL_VALUE) {
					pstmt.setDouble(++dirtycount, obj.getQty());
				}
				if(obj.getAdjudicationDt() != null && !obj.getAdjudicationDt().equals(ValueObject.DATE_TO_NULL_VALUE)) {
					pstmt.setDate(++dirtycount, obj.getAdjudicationDt()); 
				}
				if(obj.getDtOfServ() != null && !obj.getDtOfServ().equals(ValueObject.DATE_TO_NULL_VALUE)) {
					pstmt.setDate(++dirtycount, obj.getDtOfServ()); 
				}
				if(obj.getLtcFlag() != Integer.MIN_VALUE && obj.getLtcFlag() != ValueObject.NUMBER_TO_NULL_VALUE) {
					pstmt.setInt(++dirtycount, obj.getLtcFlag());
				}
				if(obj.getDinPin() != null && !obj.getDinPin().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getDinPin()); 
				}
				if(obj.getAgencyId() != Integer.MIN_VALUE && obj.getAgencyId() != ValueObject.NUMBER_TO_NULL_VALUE) {
					pstmt.setInt(++dirtycount, obj.getAgencyId());
				}
				if(obj.getClaimId() != Integer.MIN_VALUE && obj.getClaimId() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					pstmt.setLong(++dirtycount, obj.getClaimId()); 
				}
				if(obj.getPostalCd() != null && !obj.getPostalCd().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getPostalCd()); 
				}
				if(obj.getAgTypeCd() != null && !obj.getAgTypeCd().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getAgTypeCd()); 
				}
				if(obj.getCountyCd() != null && !obj.getCountyCd().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getCountyCd()); 
				}
				if(obj.getPlanCd() != null && !obj.getPlanCd().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getPlanCd()); 
				}
				if(obj.getDysSupply() != Integer.MIN_VALUE && obj.getDysSupply() != ValueObject.NUMBER_TO_NULL_VALUE) {
					pstmt.setInt(++dirtycount, obj.getDysSupply());
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
	 * @param obj Clmhist.
	 * @param dataSourceName String.
	 *
	 * @return int, the number of records updated in the database.
	 */
	public int updateTheGivenDataSource(ValueObject obj, String dataSourcename) throws Exception {
		Clmhist o = ( Clmhist)obj;
		String where = " where CLMHIST.CLAIM_ID = " + o.getClaimId() ;
		return updateTheGivenDataSource(obj,where,dataSourcename);
	}

	/**
	 * Update the record into the value specified in the value object with the constraints defined in where clause.
	 *
	 * @param obj Clmhist.
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
	 * @param claimId long.
	 *
	 * @return int, the number of records deleted in the database.
	 */
	public int deleteByPK(long claimId) throws Exception {
		String where = " where CLMHIST.CLAIM_ID = " + claimId ;
		return deleteByWhere(where);
	}

	/**
	 * Delete the record in the table by primary key.
	 *
	 * @param claimId long.
	 * @param dataSourceName String.
	 *
	 * @return int, the number of records deleted in the database.
	 */
	public int deleteByPK(long claimId,String dataSourceName) throws Exception {
		String where = " where CLMHIST.CLAIM_ID = " + claimId ;
		try {
			return deleteByWhere(where, dataSourceName);
		}catch(Exception e) {
			throw e;
		}
	}

	/**
	 * Delete the record in the table by primary key.
	 *
	 * @param claimId long.
	 * @param conn java.sql.Connection.
	 *
	 * @return int, the number of records deleted in the database.
	 */
	public int deleteByPK(long claimId,Connection conn) throws Exception {
		String where = " where CLMHIST.CLAIM_ID = " + claimId ;
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
			String sql = "DELETE from CLMHIST ";
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
				Clmhist obj = (Clmhist)volist.get(i);
				String sql = populateCreateSql(obj);
				pstmt = conn.prepareStatement(sql);
				int dirtycount = 0;
				if(obj.getMedCond() != Integer.MIN_VALUE) { 
					pstmt.setInt(++dirtycount, obj.getMedCond()); 
				}
				if(obj.getMohPrescriberId() != null && !obj.getMohPrescriberId().equals("")) {
					pstmt.setString(++dirtycount, obj.getMohPrescriberId()); 
				}
				if(obj.getOdbEligNo() != null && !obj.getOdbEligNo().equals("")) {
					pstmt.setString(++dirtycount, obj.getOdbEligNo()); 
				}
				if(obj.getTheraClass() != null && !obj.getTheraClass().equals("")) {
					pstmt.setString(++dirtycount, obj.getTheraClass()); 
				}
				if(obj.getIntervention10() != null && !obj.getIntervention10().equals("")) {
					pstmt.setString(++dirtycount, obj.getIntervention10()); 
				}
				if(obj.getIntervention9() != null && !obj.getIntervention9().equals("")) {
					pstmt.setString(++dirtycount, obj.getIntervention9()); 
				}
				if(obj.getIntervention8() != null && !obj.getIntervention8().equals("")) {
					pstmt.setString(++dirtycount, obj.getIntervention8()); 
				}
				if(obj.getIntervention7() != null && !obj.getIntervention7().equals("")) {
					pstmt.setString(++dirtycount, obj.getIntervention7()); 
				}
				if(obj.getIntervention6() != null && !obj.getIntervention6().equals("")) {
					pstmt.setString(++dirtycount, obj.getIntervention6()); 
				}
				if(obj.getIntervention5() != null && !obj.getIntervention5().equals("")) {
					pstmt.setString(++dirtycount, obj.getIntervention5()); 
				}
				if(obj.getIntervention4() != null && !obj.getIntervention4().equals("")) {
					pstmt.setString(++dirtycount, obj.getIntervention4()); 
				}
				if(obj.getIntervention3() != null && !obj.getIntervention3().equals("")) {
					pstmt.setString(++dirtycount, obj.getIntervention3()); 
				}
				if(obj.getIntervention2() != null && !obj.getIntervention2().equals("")) {
					pstmt.setString(++dirtycount, obj.getIntervention2()); 
				}
				if(obj.getIntervention1() != null && !obj.getIntervention1().equals("")) {
					pstmt.setString(++dirtycount, obj.getIntervention1()); 
				}
				if(obj.getProgId() != null && !obj.getProgId().equals("")) {
					pstmt.setString(++dirtycount, obj.getProgId()); 
				}
				if(obj.getCurrStat() != null && !obj.getCurrStat().equals("")) {
					pstmt.setString(++dirtycount, obj.getCurrStat()); 
				}
				if(obj.getProdSel() != Integer.MIN_VALUE) {
					pstmt.setDouble(++dirtycount, obj.getProdSel()); 
				}
				if(obj.getCurrRxNo() != null && !obj.getCurrRxNo().equals("")) {
					pstmt.setString(++dirtycount, obj.getCurrRxNo()); 
				}
				if(obj.getTotAmtPd() != Integer.MIN_VALUE) {
					pstmt.setDouble(++dirtycount, obj.getTotAmtPd()); 
				}
				if(obj.getDeductToCollect() != Integer.MIN_VALUE) { 
					pstmt.setDouble(++dirtycount, obj.getDeductToCollect()); 
				}
				if(obj.getCompFeeAlld() != Integer.MIN_VALUE) { 
					pstmt.setInt(++dirtycount, obj.getCompFeeAlld()); 
				}
				if(obj.getProfFeeAlld() != Integer.MIN_VALUE) { 
					pstmt.setInt(++dirtycount, obj.getProfFeeAlld()); 
				}
				if(obj.getCstUpchrgAlld() != Integer.MIN_VALUE) {
					pstmt.setDouble(++dirtycount, obj.getCstUpchrgAlld()); 
				}
				if(obj.getDrgCstAlld() != Integer.MIN_VALUE) {
					pstmt.setDouble(++dirtycount, obj.getDrgCstAlld()); 
				}
				if(obj.getQty() != Integer.MIN_VALUE) { 
					pstmt.setDouble(++dirtycount, obj.getQty()); 
				}
				if(obj.getAdjudicationDt() != null) {
 					pstmt.setDate(++dirtycount, obj.getAdjudicationDt()); 
				}
				if(obj.getDtOfServ() != null) {
 					pstmt.setDate(++dirtycount, obj.getDtOfServ()); 
				}
				if(obj.getLtcFlag() != Integer.MIN_VALUE) { 
					pstmt.setInt(++dirtycount, obj.getLtcFlag()); 
				}
				if(obj.getDinPin() != null && !obj.getDinPin().equals("")) {
					pstmt.setString(++dirtycount, obj.getDinPin()); 
				}
				if(obj.getAgencyId() != Integer.MIN_VALUE) { 
					pstmt.setInt(++dirtycount, obj.getAgencyId()); 
				}
				if(obj.getClaimId() != Integer.MIN_VALUE) {
					pstmt.setLong(++dirtycount, obj.getClaimId()); 
				}
				if(obj.getPostalCd() != null && !obj.getPostalCd().equals("")) {
					pstmt.setString(++dirtycount, obj.getPostalCd()); 
				}
				if(obj.getAgTypeCd() != null && !obj.getAgTypeCd().equals("")) {
					pstmt.setString(++dirtycount, obj.getAgTypeCd()); 
				}
				if(obj.getCountyCd() != null && !obj.getCountyCd().equals("")) {
					pstmt.setString(++dirtycount, obj.getCountyCd()); 
				}
				if(obj.getPlanCd() != null && !obj.getPlanCd().equals("")) {
					pstmt.setString(++dirtycount, obj.getPlanCd()); 
				}
				if(obj.getDysSupply() != Integer.MIN_VALUE) { 
					pstmt.setInt(++dirtycount, obj.getDysSupply()); 
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
				Clmhist obj = (Clmhist)volist.get(i);
				StringBuffer sql = new StringBuffer("UPDATE CLMHIST SET ");
				if(obj.getMedCond() != Integer.MIN_VALUE && obj.getMedCond() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("MED_COND=?,"); 
				} 
				else if(obj.getMedCond() == ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("MED_COND=NULL,"); 
				} 
				if(obj.getMohPrescriberId() != null && !obj.getMohPrescriberId().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("MOH_PRESCRIBER_ID=?,"); 
				} 
				else if(obj.getMohPrescriberId() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("MOH_PRESCRIBER_ID=NULL,"); 
				} 
				if(obj.getOdbEligNo() != null && !obj.getOdbEligNo().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("ODB_ELIG_NO=?,"); 
				} 
				else if(obj.getOdbEligNo() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("ODB_ELIG_NO=NULL,"); 
				} 
				if(obj.getTheraClass() != null && !obj.getTheraClass().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("THERA_CLASS=?,"); 
				} 
				else if(obj.getTheraClass() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("THERA_CLASS=NULL,"); 
				} 
				if(obj.getIntervention10() != null && !obj.getIntervention10().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("INTERVENTION_10=?,"); 
				} 
				else if(obj.getIntervention10() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("INTERVENTION_10=NULL,"); 
				} 
				if(obj.getIntervention9() != null && !obj.getIntervention9().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("INTERVENTION_9=?,"); 
				} 
				else if(obj.getIntervention9() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("INTERVENTION_9=NULL,"); 
				} 
				if(obj.getIntervention8() != null && !obj.getIntervention8().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("INTERVENTION_8=?,"); 
				} 
				else if(obj.getIntervention8() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("INTERVENTION_8=NULL,"); 
				} 
				if(obj.getIntervention7() != null && !obj.getIntervention7().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("INTERVENTION_7=?,"); 
				} 
				else if(obj.getIntervention7() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("INTERVENTION_7=NULL,"); 
				} 
				if(obj.getIntervention6() != null && !obj.getIntervention6().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("INTERVENTION_6=?,"); 
				} 
				else if(obj.getIntervention6() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("INTERVENTION_6=NULL,"); 
				} 
				if(obj.getIntervention5() != null && !obj.getIntervention5().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("INTERVENTION_5=?,"); 
				} 
				else if(obj.getIntervention5() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("INTERVENTION_5=NULL,"); 
				} 
				if(obj.getIntervention4() != null && !obj.getIntervention4().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("INTERVENTION_4=?,"); 
				} 
				else if(obj.getIntervention4() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("INTERVENTION_4=NULL,"); 
				} 
				if(obj.getIntervention3() != null && !obj.getIntervention3().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("INTERVENTION_3=?,"); 
				} 
				else if(obj.getIntervention3() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("INTERVENTION_3=NULL,"); 
				} 
				if(obj.getIntervention2() != null && !obj.getIntervention2().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("INTERVENTION_2=?,"); 
				} 
				else if(obj.getIntervention2() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("INTERVENTION_2=NULL,"); 
				} 
				if(obj.getIntervention1() != null && !obj.getIntervention1().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("INTERVENTION_1=?,"); 
				} 
				else if(obj.getIntervention1() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("INTERVENTION_1=NULL,"); 
				} 
				if(obj.getProgId() != null && !obj.getProgId().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("PROG_ID=?,"); 
				} 
				else if(obj.getProgId() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("PROG_ID=NULL,"); 
				} 
				if(obj.getCurrStat() != null && !obj.getCurrStat().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("CURR_STAT=?,"); 
				} 
				else if(obj.getCurrStat() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("CURR_STAT=NULL,"); 
				} 
				if(obj.getProdSel() != Integer.MIN_VALUE && obj.getProdSel() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("PROD_SEL=?,"); 
				} 
				else if(obj.getProdSel() == ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("PROD_SEL=NULL,"); 
				} 
				if(obj.getCurrRxNo() != null && !obj.getCurrRxNo().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("CURR_RX_NO=?,"); 
				} 
				else if(obj.getCurrRxNo() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("CURR_RX_NO=NULL,"); 
				} 
				if(obj.getTotAmtPd() != Integer.MIN_VALUE && obj.getTotAmtPd() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("TOT_AMT_PD=?,"); 
				} 
				else if(obj.getTotAmtPd() == ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("TOT_AMT_PD=NULL,"); 
				} 
				if(obj.getDeductToCollect() != Integer.MIN_VALUE && obj.getDeductToCollect() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("DEDUCT_TO_COLLECT=?,"); 
				} 
				else if(obj.getDeductToCollect() == ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("DEDUCT_TO_COLLECT=NULL,"); 
				} 
				if(obj.getCompFeeAlld() != Integer.MIN_VALUE && obj.getCompFeeAlld() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("COMP_FEE_ALLD=?,"); 
				} 
				else if(obj.getCompFeeAlld() == ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("COMP_FEE_ALLD=NULL,"); 
				} 
				if(obj.getProfFeeAlld() != Integer.MIN_VALUE && obj.getProfFeeAlld() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("PROF_FEE_ALLD=?,"); 
				} 
				else if(obj.getProfFeeAlld() == ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("PROF_FEE_ALLD=NULL,"); 
				} 
				if(obj.getCstUpchrgAlld() != Integer.MIN_VALUE && obj.getCstUpchrgAlld() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("CST_UPCHRG_ALLD=?,"); 
				} 
				else if(obj.getCstUpchrgAlld() == ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("CST_UPCHRG_ALLD=NULL,"); 
				} 
				if(obj.getDrgCstAlld() != Integer.MIN_VALUE && obj.getDrgCstAlld() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("DRG_CST_ALLD=?,"); 
				} 
				else if(obj.getDrgCstAlld() == ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("DRG_CST_ALLD=NULL,"); 
				} 
				if(obj.getQty() != Integer.MIN_VALUE && obj.getQty() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("QTY=?,"); 
				} 
				else if(obj.getQty() == ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("QTY=NULL,"); 
				} 
				if(obj.getAdjudicationDt() != null && !obj.getAdjudicationDt().equals(ValueObject.DATE_TO_NULL_VALUE)) {
					sql.append("ADJUDICATION_DT=?,"); 
				} 
				else if(obj.getAdjudicationDt()!= null && obj.getAdjudicationDt().equals(ValueObject.DATE_TO_NULL_VALUE)) { 
 					sql.append("ADJUDICATION_DT=NULL,"); 
				} 
				if(obj.getDtOfServ() != null && !obj.getDtOfServ().equals(ValueObject.DATE_TO_NULL_VALUE)) {
					sql.append("DT_OF_SERV=?,"); 
				} 
				else if(obj.getDtOfServ()!= null && obj.getDtOfServ().equals(ValueObject.DATE_TO_NULL_VALUE)) { 
 					sql.append("DT_OF_SERV=NULL,"); 
				} 
				if(obj.getLtcFlag() != Integer.MIN_VALUE && obj.getLtcFlag() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("LTC_FLAG=?,"); 
				} 
				else if(obj.getLtcFlag() == ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("LTC_FLAG=NULL,"); 
				} 
				if(obj.getDinPin() != null && !obj.getDinPin().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("DIN_PIN=?,"); 
				} 
				else if(obj.getDinPin() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("DIN_PIN=NULL,"); 
				} 
				if(obj.getAgencyId() != Integer.MIN_VALUE && obj.getAgencyId() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("AGENCY_ID=?,"); 
				} 
				else if(obj.getAgencyId() == ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("AGENCY_ID=NULL,"); 
				} 
				if(obj.getClaimId() != Integer.MIN_VALUE && obj.getClaimId() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("CLAIM_ID=?,"); 
				} 
				else if(obj.getClaimId() == ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("CLAIM_ID=NULL,"); 
				} 
				if(obj.getPostalCd() != null && !obj.getPostalCd().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("POSTAL_CD=?,"); 
				} 
				else if(obj.getPostalCd() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("POSTAL_CD=NULL,"); 
				} 
				if(obj.getAgTypeCd() != null && !obj.getAgTypeCd().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("AG_TYPE_CD=?,"); 
				} 
				else if(obj.getAgTypeCd() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("AG_TYPE_CD=NULL,"); 
				} 
				if(obj.getCountyCd() != null && !obj.getCountyCd().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("COUNTY_CD=?,"); 
				} 
				else if(obj.getCountyCd() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("COUNTY_CD=NULL,"); 
				} 
				if(obj.getPlanCd() != null && !obj.getPlanCd().equals(ValueObject.STRING_TO_NULL_VALUE)) { 
 					 sql.append("PLAN_CD=?,"); 
				} 
				else if(obj.getPlanCd() == ValueObject.STRING_TO_NULL_VALUE) { 
					sql.append("PLAN_CD=NULL,"); 
				} 
				if(obj.getDysSupply() != Integer.MIN_VALUE && obj.getDysSupply() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("DYS_SUPPLY=?,"); 
				} 
				else if(obj.getDysSupply() == ValueObject.NUMBER_TO_NULL_VALUE) { 
					sql.append("DYS_SUPPLY=NULL,"); 
				} 
				sql.setLength(sql.length() - 1);
				String where = " where CLMHIST.CLAIM_ID = " + obj.getClaimId() ;
				sql.append(where);

				pstmt = conn.prepareStatement(sql.toString());

				int dirtycount = 0;
				if(obj.getMedCond() != Integer.MIN_VALUE && obj.getMedCond() != ValueObject.NUMBER_TO_NULL_VALUE) {
					pstmt.setInt(++dirtycount, obj.getMedCond());
				}
				if(obj.getMohPrescriberId() != null && !obj.getMohPrescriberId().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getMohPrescriberId()); 
				}
				if(obj.getOdbEligNo() != null && !obj.getOdbEligNo().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getOdbEligNo()); 
				}
				if(obj.getTheraClass() != null && !obj.getTheraClass().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getTheraClass()); 
				}
				if(obj.getIntervention10() != null && !obj.getIntervention10().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getIntervention10()); 
				}
				if(obj.getIntervention9() != null && !obj.getIntervention9().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getIntervention9()); 
				}
				if(obj.getIntervention8() != null && !obj.getIntervention8().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getIntervention8()); 
				}
				if(obj.getIntervention7() != null && !obj.getIntervention7().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getIntervention7()); 
				}
				if(obj.getIntervention6() != null && !obj.getIntervention6().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getIntervention6()); 
				}
				if(obj.getIntervention5() != null && !obj.getIntervention5().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getIntervention5()); 
				}
				if(obj.getIntervention4() != null && !obj.getIntervention4().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getIntervention4()); 
				}
				if(obj.getIntervention3() != null && !obj.getIntervention3().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getIntervention3()); 
				}
				if(obj.getIntervention2() != null && !obj.getIntervention2().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getIntervention2()); 
				}
				if(obj.getIntervention1() != null && !obj.getIntervention1().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getIntervention1()); 
				}
				if(obj.getProgId() != null && !obj.getProgId().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getProgId()); 
				}
				if(obj.getCurrStat() != null && !obj.getCurrStat().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getCurrStat()); 
				}
				if(obj.getProdSel() != Integer.MIN_VALUE && obj.getProdSel() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					pstmt.setDouble(++dirtycount, obj.getProdSel()); 
				}
				if(obj.getCurrRxNo() != null && !obj.getCurrRxNo().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getCurrRxNo()); 
				}
				if(obj.getTotAmtPd() != Integer.MIN_VALUE && obj.getTotAmtPd() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					pstmt.setDouble(++dirtycount, obj.getTotAmtPd()); 
				}
				if(obj.getDeductToCollect() != Integer.MIN_VALUE && obj.getDeductToCollect() != ValueObject.NUMBER_TO_NULL_VALUE) {
					pstmt.setDouble(++dirtycount, obj.getDeductToCollect());
				}
				if(obj.getCompFeeAlld() != Integer.MIN_VALUE && obj.getCompFeeAlld() != ValueObject.NUMBER_TO_NULL_VALUE) {
					pstmt.setInt(++dirtycount, obj.getCompFeeAlld());
				}
				if(obj.getProfFeeAlld() != Integer.MIN_VALUE && obj.getProfFeeAlld() != ValueObject.NUMBER_TO_NULL_VALUE) {
					pstmt.setInt(++dirtycount, obj.getProfFeeAlld());
				}
				if(obj.getCstUpchrgAlld() != Integer.MIN_VALUE && obj.getCstUpchrgAlld() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					pstmt.setDouble(++dirtycount, obj.getCstUpchrgAlld()); 
				}
				if(obj.getDrgCstAlld() != Integer.MIN_VALUE && obj.getDrgCstAlld() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					pstmt.setDouble(++dirtycount, obj.getDrgCstAlld()); 
				}
				if(obj.getQty() != Integer.MIN_VALUE && obj.getQty() != ValueObject.NUMBER_TO_NULL_VALUE) {
					pstmt.setDouble(++dirtycount, obj.getQty());
				}
				if(obj.getAdjudicationDt() != null && !obj.getAdjudicationDt().equals(ValueObject.DATE_TO_NULL_VALUE)) {
					pstmt.setDate(++dirtycount, obj.getAdjudicationDt()); 
				}
				if(obj.getDtOfServ() != null && !obj.getDtOfServ().equals(ValueObject.DATE_TO_NULL_VALUE)) {
					pstmt.setDate(++dirtycount, obj.getDtOfServ()); 
				}
				if(obj.getLtcFlag() != Integer.MIN_VALUE && obj.getLtcFlag() != ValueObject.NUMBER_TO_NULL_VALUE) {
					pstmt.setInt(++dirtycount, obj.getLtcFlag());
				}
				if(obj.getDinPin() != null && !obj.getDinPin().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getDinPin()); 
				}
				if(obj.getAgencyId() != Integer.MIN_VALUE && obj.getAgencyId() != ValueObject.NUMBER_TO_NULL_VALUE) {
					pstmt.setInt(++dirtycount, obj.getAgencyId());
				}
				if(obj.getClaimId() != Integer.MIN_VALUE && obj.getClaimId() != ValueObject.NUMBER_TO_NULL_VALUE) { 
					pstmt.setLong(++dirtycount, obj.getClaimId()); 
				}
				if(obj.getPostalCd() != null && !obj.getPostalCd().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getPostalCd()); 
				}
				if(obj.getAgTypeCd() != null && !obj.getAgTypeCd().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getAgTypeCd()); 
				}
				if(obj.getCountyCd() != null && !obj.getCountyCd().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getCountyCd()); 
				}
				if(obj.getPlanCd() != null && !obj.getPlanCd().equals(ValueObject.STRING_TO_NULL_VALUE)) {  
					pstmt.setString(++dirtycount, obj.getPlanCd()); 
				}
				if(obj.getDysSupply() != Integer.MIN_VALUE && obj.getDysSupply() != ValueObject.NUMBER_TO_NULL_VALUE) {
					pstmt.setInt(++dirtycount, obj.getDysSupply());
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
				Clmhist obj = (Clmhist)volist.get(i);
				String sql = populateDeleteSql(obj);
				pstmt = conn.prepareStatement(sql);
				int dirtycount = 0;
				if(obj.getMedCond() != Integer.MIN_VALUE) { 
					pstmt.setInt(++dirtycount, obj.getMedCond()); 
				}
				if(obj.getMohPrescriberId() != null && !obj.getMohPrescriberId().equals("")) {
					pstmt.setString(++dirtycount, obj.getMohPrescriberId()); 
				}
				if(obj.getOdbEligNo() != null && !obj.getOdbEligNo().equals("")) {
					pstmt.setString(++dirtycount, obj.getOdbEligNo()); 
				}
				if(obj.getTheraClass() != null && !obj.getTheraClass().equals("")) {
					pstmt.setString(++dirtycount, obj.getTheraClass()); 
				}
				if(obj.getIntervention10() != null && !obj.getIntervention10().equals("")) {
					pstmt.setString(++dirtycount, obj.getIntervention10()); 
				}
				if(obj.getIntervention9() != null && !obj.getIntervention9().equals("")) {
					pstmt.setString(++dirtycount, obj.getIntervention9()); 
				}
				if(obj.getIntervention8() != null && !obj.getIntervention8().equals("")) {
					pstmt.setString(++dirtycount, obj.getIntervention8()); 
				}
				if(obj.getIntervention7() != null && !obj.getIntervention7().equals("")) {
					pstmt.setString(++dirtycount, obj.getIntervention7()); 
				}
				if(obj.getIntervention6() != null && !obj.getIntervention6().equals("")) {
					pstmt.setString(++dirtycount, obj.getIntervention6()); 
				}
				if(obj.getIntervention5() != null && !obj.getIntervention5().equals("")) {
					pstmt.setString(++dirtycount, obj.getIntervention5()); 
				}
				if(obj.getIntervention4() != null && !obj.getIntervention4().equals("")) {
					pstmt.setString(++dirtycount, obj.getIntervention4()); 
				}
				if(obj.getIntervention3() != null && !obj.getIntervention3().equals("")) {
					pstmt.setString(++dirtycount, obj.getIntervention3()); 
				}
				if(obj.getIntervention2() != null && !obj.getIntervention2().equals("")) {
					pstmt.setString(++dirtycount, obj.getIntervention2()); 
				}
				if(obj.getIntervention1() != null && !obj.getIntervention1().equals("")) {
					pstmt.setString(++dirtycount, obj.getIntervention1()); 
				}
				if(obj.getProgId() != null && !obj.getProgId().equals("")) {
					pstmt.setString(++dirtycount, obj.getProgId()); 
				}
				if(obj.getCurrStat() != null && !obj.getCurrStat().equals("")) {
					pstmt.setString(++dirtycount, obj.getCurrStat()); 
				}
				if(obj.getProdSel() != Integer.MIN_VALUE) {
					pstmt.setDouble(++dirtycount, obj.getProdSel()); 
				}
				if(obj.getCurrRxNo() != null && !obj.getCurrRxNo().equals("")) {
					pstmt.setString(++dirtycount, obj.getCurrRxNo()); 
				}
				if(obj.getTotAmtPd() != Integer.MIN_VALUE) {
					pstmt.setDouble(++dirtycount, obj.getTotAmtPd()); 
				}
				if(obj.getDeductToCollect() != Integer.MIN_VALUE) { 
					pstmt.setDouble(++dirtycount, obj.getDeductToCollect()); 
				}
				if(obj.getCompFeeAlld() != Integer.MIN_VALUE) { 
					pstmt.setInt(++dirtycount, obj.getCompFeeAlld()); 
				}
				if(obj.getProfFeeAlld() != Integer.MIN_VALUE) { 
					pstmt.setInt(++dirtycount, obj.getProfFeeAlld()); 
				}
				if(obj.getCstUpchrgAlld() != Integer.MIN_VALUE) {
					pstmt.setDouble(++dirtycount, obj.getCstUpchrgAlld()); 
				}
				if(obj.getDrgCstAlld() != Integer.MIN_VALUE) {
					pstmt.setDouble(++dirtycount, obj.getDrgCstAlld()); 
				}
				if(obj.getQty() != Integer.MIN_VALUE) { 
					pstmt.setDouble(++dirtycount, obj.getQty()); 
				}
				if(obj.getAdjudicationDt() != null) {
 					pstmt.setDate(++dirtycount, obj.getAdjudicationDt()); 
				}
				if(obj.getDtOfServ() != null) {
 					pstmt.setDate(++dirtycount, obj.getDtOfServ()); 
				}
				if(obj.getLtcFlag() != Integer.MIN_VALUE) { 
					pstmt.setInt(++dirtycount, obj.getLtcFlag()); 
				}
				if(obj.getDinPin() != null && !obj.getDinPin().equals("")) {
					pstmt.setString(++dirtycount, obj.getDinPin()); 
				}
				if(obj.getAgencyId() != Integer.MIN_VALUE) { 
					pstmt.setInt(++dirtycount, obj.getAgencyId()); 
				}
				if(obj.getClaimId() != Integer.MIN_VALUE) {
					pstmt.setLong(++dirtycount, obj.getClaimId()); 
				}
				if(obj.getPostalCd() != null && !obj.getPostalCd().equals("")) {
					pstmt.setString(++dirtycount, obj.getPostalCd()); 
				}
				if(obj.getAgTypeCd() != null && !obj.getAgTypeCd().equals("")) {
					pstmt.setString(++dirtycount, obj.getAgTypeCd()); 
				}
				if(obj.getCountyCd() != null && !obj.getCountyCd().equals("")) {
					pstmt.setString(++dirtycount, obj.getCountyCd()); 
				}
				if(obj.getPlanCd() != null && !obj.getPlanCd().equals("")) {
					pstmt.setString(++dirtycount, obj.getPlanCd()); 
				}
				if(obj.getDysSupply() != Integer.MIN_VALUE) { 
					pstmt.setInt(++dirtycount, obj.getDysSupply()); 
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

	private String populateCreateSql(Clmhist obj){
		int dirtycount = 0;
		StringBuffer sql = new StringBuffer("INSERT into CLMHIST(");
		if(obj.getMedCond() != Integer.MIN_VALUE) {
			sql.append("MED_COND").append(","); dirtycount++; 
		}
		if(obj.getMohPrescriberId() != null && !obj.getMohPrescriberId().equals("")) {
			sql.append("MOH_PRESCRIBER_ID").append(","); dirtycount++; 
		}
		if(obj.getOdbEligNo() != null && !obj.getOdbEligNo().equals("")) {
			sql.append("ODB_ELIG_NO").append(","); dirtycount++; 
		}
		if(obj.getTheraClass() != null && !obj.getTheraClass().equals("")) {
			sql.append("THERA_CLASS").append(","); dirtycount++; 
		}
		if(obj.getIntervention10() != null && !obj.getIntervention10().equals("")) {
			sql.append("INTERVENTION_10").append(","); dirtycount++; 
		}
		if(obj.getIntervention9() != null && !obj.getIntervention9().equals("")) {
			sql.append("INTERVENTION_9").append(","); dirtycount++; 
		}
		if(obj.getIntervention8() != null && !obj.getIntervention8().equals("")) {
			sql.append("INTERVENTION_8").append(","); dirtycount++; 
		}
		if(obj.getIntervention7() != null && !obj.getIntervention7().equals("")) {
			sql.append("INTERVENTION_7").append(","); dirtycount++; 
		}
		if(obj.getIntervention6() != null && !obj.getIntervention6().equals("")) {
			sql.append("INTERVENTION_6").append(","); dirtycount++; 
		}
		if(obj.getIntervention5() != null && !obj.getIntervention5().equals("")) {
			sql.append("INTERVENTION_5").append(","); dirtycount++; 
		}
		if(obj.getIntervention4() != null && !obj.getIntervention4().equals("")) {
			sql.append("INTERVENTION_4").append(","); dirtycount++; 
		}
		if(obj.getIntervention3() != null && !obj.getIntervention3().equals("")) {
			sql.append("INTERVENTION_3").append(","); dirtycount++; 
		}
		if(obj.getIntervention2() != null && !obj.getIntervention2().equals("")) {
			sql.append("INTERVENTION_2").append(","); dirtycount++; 
		}
		if(obj.getIntervention1() != null && !obj.getIntervention1().equals("")) {
			sql.append("INTERVENTION_1").append(","); dirtycount++; 
		}
		if(obj.getProgId() != null && !obj.getProgId().equals("")) {
			sql.append("PROG_ID").append(","); dirtycount++; 
		}
		if(obj.getCurrStat() != null && !obj.getCurrStat().equals("")) {
			sql.append("CURR_STAT").append(","); dirtycount++; 
		}
		if(obj.getProdSel() != Integer.MIN_VALUE) {
			sql.append("PROD_SEL").append(","); dirtycount++; 
		}
		if(obj.getCurrRxNo() != null && !obj.getCurrRxNo().equals("")) {
			sql.append("CURR_RX_NO").append(","); dirtycount++; 
		}
		if(obj.getTotAmtPd() != Integer.MIN_VALUE) {
			sql.append("TOT_AMT_PD").append(","); dirtycount++; 
		}
		if(obj.getDeductToCollect() != Integer.MIN_VALUE) {
			sql.append("DEDUCT_TO_COLLECT").append(","); dirtycount++; 
		}
		if(obj.getCompFeeAlld() != Integer.MIN_VALUE) {
			sql.append("COMP_FEE_ALLD").append(","); dirtycount++; 
		}
		if(obj.getProfFeeAlld() != Integer.MIN_VALUE) {
			sql.append("PROF_FEE_ALLD").append(","); dirtycount++; 
		}
		if(obj.getCstUpchrgAlld() != Integer.MIN_VALUE) {
			sql.append("CST_UPCHRG_ALLD").append(","); dirtycount++; 
		}
		if(obj.getDrgCstAlld() != Integer.MIN_VALUE) {
			sql.append("DRG_CST_ALLD").append(","); dirtycount++; 
		}
		if(obj.getQty() != Integer.MIN_VALUE) {
			sql.append("QTY").append(","); dirtycount++; 
		}
		if(obj.getAdjudicationDt() != null) {
			sql.append("ADJUDICATION_DT").append(","); dirtycount++; 
		}
		if(obj.getDtOfServ() != null) {
			sql.append("DT_OF_SERV").append(","); dirtycount++; 
		}
		if(obj.getLtcFlag() != Integer.MIN_VALUE) {
			sql.append("LTC_FLAG").append(","); dirtycount++; 
		}
		if(obj.getDinPin() != null && !obj.getDinPin().equals("")) {
			sql.append("DIN_PIN").append(","); dirtycount++; 
		}
		if(obj.getAgencyId() != Integer.MIN_VALUE) {
			sql.append("AGENCY_ID").append(","); dirtycount++; 
		}
		if(obj.getClaimId() != Integer.MIN_VALUE) {
			sql.append("CLAIM_ID").append(","); dirtycount++; 
		}
		if(obj.getPostalCd() != null && !obj.getPostalCd().equals("")) {
			sql.append("POSTAL_CD").append(","); dirtycount++; 
		}
		if(obj.getAgTypeCd() != null && !obj.getAgTypeCd().equals("")) {
			sql.append("AG_TYPE_CD").append(","); dirtycount++; 
		}
		if(obj.getCountyCd() != null && !obj.getCountyCd().equals("")) {
			sql.append("COUNTY_CD").append(","); dirtycount++; 
		}
		if(obj.getPlanCd() != null && !obj.getPlanCd().equals("")) {
			sql.append("PLAN_CD").append(","); dirtycount++; 
		}
		if(obj.getDysSupply() != Integer.MIN_VALUE) {
			sql.append("DYS_SUPPLY").append(","); dirtycount++; 
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

	private String populateDeleteSql(Clmhist obj){
		int dirtycount = 0;
		StringBuffer sql = new StringBuffer("DELETE FROM CLMHIST where ");
		if(obj.getMedCond() != Integer.MIN_VALUE) {
			if(dirtycount == 0){
				sql.append("MED_COND=?").append(",");
				dirtycount++;
			}else{
				sql.append("MED_COND=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getMohPrescriberId() != null && !obj.getMohPrescriberId().equals("")) {
			if(dirtycount == 0){
				sql.append("MOH_PRESCRIBER_ID=?").append(",");
				dirtycount++;
			}else{
				sql.append("MOH_PRESCRIBER_ID=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getOdbEligNo() != null && !obj.getOdbEligNo().equals("")) {
			if(dirtycount == 0){
				sql.append("ODB_ELIG_NO=?").append(",");
				dirtycount++;
			}else{
				sql.append("ODB_ELIG_NO=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getTheraClass() != null && !obj.getTheraClass().equals("")) {
			if(dirtycount == 0){
				sql.append("THERA_CLASS=?").append(",");
				dirtycount++;
			}else{
				sql.append("THERA_CLASS=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getIntervention10() != null && !obj.getIntervention10().equals("")) {
			if(dirtycount == 0){
				sql.append("INTERVENTION_10=?").append(",");
				dirtycount++;
			}else{
				sql.append("INTERVENTION_10=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getIntervention9() != null && !obj.getIntervention9().equals("")) {
			if(dirtycount == 0){
				sql.append("INTERVENTION_9=?").append(",");
				dirtycount++;
			}else{
				sql.append("INTERVENTION_9=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getIntervention8() != null && !obj.getIntervention8().equals("")) {
			if(dirtycount == 0){
				sql.append("INTERVENTION_8=?").append(",");
				dirtycount++;
			}else{
				sql.append("INTERVENTION_8=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getIntervention7() != null && !obj.getIntervention7().equals("")) {
			if(dirtycount == 0){
				sql.append("INTERVENTION_7=?").append(",");
				dirtycount++;
			}else{
				sql.append("INTERVENTION_7=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getIntervention6() != null && !obj.getIntervention6().equals("")) {
			if(dirtycount == 0){
				sql.append("INTERVENTION_6=?").append(",");
				dirtycount++;
			}else{
				sql.append("INTERVENTION_6=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getIntervention5() != null && !obj.getIntervention5().equals("")) {
			if(dirtycount == 0){
				sql.append("INTERVENTION_5=?").append(",");
				dirtycount++;
			}else{
				sql.append("INTERVENTION_5=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getIntervention4() != null && !obj.getIntervention4().equals("")) {
			if(dirtycount == 0){
				sql.append("INTERVENTION_4=?").append(",");
				dirtycount++;
			}else{
				sql.append("INTERVENTION_4=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getIntervention3() != null && !obj.getIntervention3().equals("")) {
			if(dirtycount == 0){
				sql.append("INTERVENTION_3=?").append(",");
				dirtycount++;
			}else{
				sql.append("INTERVENTION_3=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getIntervention2() != null && !obj.getIntervention2().equals("")) {
			if(dirtycount == 0){
				sql.append("INTERVENTION_2=?").append(",");
				dirtycount++;
			}else{
				sql.append("INTERVENTION_2=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getIntervention1() != null && !obj.getIntervention1().equals("")) {
			if(dirtycount == 0){
				sql.append("INTERVENTION_1=?").append(",");
				dirtycount++;
			}else{
				sql.append("INTERVENTION_1=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getProgId() != null && !obj.getProgId().equals("")) {
			if(dirtycount == 0){
				sql.append("PROG_ID=?").append(",");
				dirtycount++;
			}else{
				sql.append("PROG_ID=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getCurrStat() != null && !obj.getCurrStat().equals("")) {
			if(dirtycount == 0){
				sql.append("CURR_STAT=?").append(",");
				dirtycount++;
			}else{
				sql.append("CURR_STAT=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getProdSel() != Integer.MIN_VALUE) {
			if(dirtycount == 0){
				sql.append("PROD_SEL=?").append(",");
				dirtycount++;
			}else{
				sql.append("PROD_SEL=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getCurrRxNo() != null && !obj.getCurrRxNo().equals("")) {
			if(dirtycount == 0){
				sql.append("CURR_RX_NO=?").append(",");
				dirtycount++;
			}else{
				sql.append("CURR_RX_NO=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getTotAmtPd() != Integer.MIN_VALUE) {
			if(dirtycount == 0){
				sql.append("TOT_AMT_PD=?").append(",");
				dirtycount++;
			}else{
				sql.append("TOT_AMT_PD=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getDeductToCollect() != Integer.MIN_VALUE) {
			if(dirtycount == 0){
				sql.append("DEDUCT_TO_COLLECT=?").append(",");
				dirtycount++;
			}else{
				sql.append("DEDUCT_TO_COLLECT=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getCompFeeAlld() != Integer.MIN_VALUE) {
			if(dirtycount == 0){
				sql.append("COMP_FEE_ALLD=?").append(",");
				dirtycount++;
			}else{
				sql.append("COMP_FEE_ALLD=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getProfFeeAlld() != Integer.MIN_VALUE) {
			if(dirtycount == 0){
				sql.append("PROF_FEE_ALLD=?").append(",");
				dirtycount++;
			}else{
				sql.append("PROF_FEE_ALLD=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getCstUpchrgAlld() != Integer.MIN_VALUE) {
			if(dirtycount == 0){
				sql.append("CST_UPCHRG_ALLD=?").append(",");
				dirtycount++;
			}else{
				sql.append("CST_UPCHRG_ALLD=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getDrgCstAlld() != Integer.MIN_VALUE) {
			if(dirtycount == 0){
				sql.append("DRG_CST_ALLD=?").append(",");
				dirtycount++;
			}else{
				sql.append("DRG_CST_ALLD=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getQty() != Integer.MIN_VALUE) {
			if(dirtycount == 0){
				sql.append("QTY=?").append(",");
				dirtycount++;
			}else{
				sql.append("QTY=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getAdjudicationDt() != null && !obj.getAdjudicationDt().equals("")) {
			if(dirtycount == 0){
				sql.append("ADJUDICATION_DT=?").append(",");
				dirtycount++;
			}else{
				sql.append("ADJUDICATION_DT=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getDtOfServ() != null && !obj.getDtOfServ().equals("")) {
			if(dirtycount == 0){
				sql.append("DT_OF_SERV=?").append(",");
				dirtycount++;
			}else{
				sql.append("DT_OF_SERV=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getLtcFlag() != Integer.MIN_VALUE) {
			if(dirtycount == 0){
				sql.append("LTC_FLAG=?").append(",");
				dirtycount++;
			}else{
				sql.append("LTC_FLAG=?").append(",");
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
		if(obj.getAgencyId() != Integer.MIN_VALUE) {
			if(dirtycount == 0){
				sql.append("AGENCY_ID=?").append(",");
				dirtycount++;
			}else{
				sql.append("AGENCY_ID=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getClaimId() != Integer.MIN_VALUE) {
			if(dirtycount == 0){
				sql.append("CLAIM_ID=?").append(",");
				dirtycount++;
			}else{
				sql.append("CLAIM_ID=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getPostalCd() != null && !obj.getPostalCd().equals("")) {
			if(dirtycount == 0){
				sql.append("POSTAL_CD=?").append(",");
				dirtycount++;
			}else{
				sql.append("POSTAL_CD=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getAgTypeCd() != null && !obj.getAgTypeCd().equals("")) {
			if(dirtycount == 0){
				sql.append("AG_TYPE_CD=?").append(",");
				dirtycount++;
			}else{
				sql.append("AG_TYPE_CD=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getCountyCd() != null && !obj.getCountyCd().equals("")) {
			if(dirtycount == 0){
				sql.append("COUNTY_CD=?").append(",");
				dirtycount++;
			}else{
				sql.append("COUNTY_CD=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getPlanCd() != null && !obj.getPlanCd().equals("")) {
			if(dirtycount == 0){
				sql.append("PLAN_CD=?").append(",");
				dirtycount++;
			}else{
				sql.append("PLAN_CD=?").append(",");
				dirtycount++;
			}
		}
		if(obj.getDysSupply() != Integer.MIN_VALUE) {
			if(dirtycount == 0){
				sql.append("DYS_SUPPLY=?").append(",");
				dirtycount++;
			}else{
				sql.append("DYS_SUPPLY=?").append(",");
				dirtycount++;
			}
		}
		sql.setLength(sql.length() - 1);
		return sql.toString();
	}

	private Clmhist constructValueObject(ResultSet rs) throws Exception {
		Clmhist vo = new Clmhist();
		int value0 = rs.getInt(1);
		if(value0 == 0 && rs.wasNull()){
			vo.setMedCond(Integer.MIN_VALUE);
		}else{
			vo.setMedCond(value0);
		}
		vo.setMohPrescriberId(rs.getString(2));
		vo.setOdbEligNo(rs.getString(3));
		vo.setTheraClass(rs.getString(4));
		vo.setIntervention10(rs.getString(5));
		vo.setIntervention9(rs.getString(6));
		vo.setIntervention8(rs.getString(7));
		vo.setIntervention7(rs.getString(8));
		vo.setIntervention6(rs.getString(9));
		vo.setIntervention5(rs.getString(10));
		vo.setIntervention4(rs.getString(11));
		vo.setIntervention3(rs.getString(12));
		vo.setIntervention2(rs.getString(13));
		vo.setIntervention1(rs.getString(14));
		vo.setProgId(rs.getString(15));
		vo.setCurrStat(rs.getString(16));
		double value16= rs.getDouble(17);
		if(value16  == 0 && rs.wasNull()){
			vo.setProdSel(Integer.MIN_VALUE);
		}else{
			vo.setProdSel(value16);
		}
		vo.setCurrRxNo(rs.getString(18));
		double value18= rs.getDouble(19);
		if(value18  == 0 && rs.wasNull()){
			vo.setTotAmtPd(Integer.MIN_VALUE);
		}else{
			vo.setTotAmtPd(value18);
		}
		double value19 = rs.getDouble(20);
		if(value19 == 0 && rs.wasNull()){
			vo.setDeductToCollect(Integer.MIN_VALUE);
		}else{
			vo.setDeductToCollect(value19);
		}
		int value20 = rs.getInt(21);
		if(value20 == 0 && rs.wasNull()){
			vo.setCompFeeAlld(Integer.MIN_VALUE);
		}else{
			vo.setCompFeeAlld(value20);
		}
		int value21 = rs.getInt(22);
		if(value21 == 0 && rs.wasNull()){
			vo.setProfFeeAlld(Integer.MIN_VALUE);
		}else{
			vo.setProfFeeAlld(value21);
		}
		double value22= rs.getDouble(23);
		if(value22  == 0 && rs.wasNull()){
			vo.setCstUpchrgAlld(Integer.MIN_VALUE);
		}else{
			vo.setCstUpchrgAlld(value22);
		}
		double value23= rs.getDouble(24);
		if(value23  == 0 && rs.wasNull()){
			vo.setDrgCstAlld(Integer.MIN_VALUE);
		}else{
			vo.setDrgCstAlld(value23);
		}
		Double value24 = rs.getDouble(25);
		if(value24 != 0){
			vo.setQty(value24);
		}
		Date value25= rs.getDate(26);
		if(value25 == null && rs.wasNull()){
			vo.setAdjudicationDt(new java.sql.Date(0));
		}else{
			vo.setAdjudicationDt(value25);
		}
		Date value26= rs.getDate(27);
		if(value26 == null && rs.wasNull()){
			vo.setDtOfServ(new java.sql.Date(0));
		}else{
			vo.setDtOfServ(value26);
		}
		int value27 = rs.getInt(28);
		if(value27 == 0 && rs.wasNull()){
			vo.setLtcFlag(Integer.MIN_VALUE);
		}else{
			vo.setLtcFlag(value27);
		}
		vo.setDinPin(rs.getString(29));
		int value29 = rs.getInt(30);
		if(value29 == 0 && rs.wasNull()){
			vo.setAgencyId(Integer.MIN_VALUE);
		}else{
			vo.setAgencyId(value29);
		}
		long value30= rs.getLong(31);
		if(value30 == 0 && rs.wasNull()){
			vo.setClaimId(Integer.MIN_VALUE);
		}else{
			vo.setClaimId(value30);
		}
		vo.setPostalCd(rs.getString(32));
		vo.setAgTypeCd(rs.getString(33));
		vo.setCountyCd(rs.getString(34));
		vo.setPlanCd(rs.getString(35));
		int value35 = rs.getInt(36);
		if(value35 == 0 && rs.wasNull()){
			vo.setDysSupply(Integer.MIN_VALUE);
		}else{
			vo.setDysSupply(value35);
		}
		return vo;
	}

	private Clmhist constructValueObject(ResultSet rs, int[] columnList) throws Exception {
		Clmhist vo = new Clmhist();
		int pos = 0;
		for(int i = 0; i < columnList.length; i++) {;
			switch(columnList[i]) {
				case MED_COND: 
					int value0 = rs.getInt(++pos);
					if(value0  == 0 && rs.wasNull()){
					vo.setMedCond(Integer.MIN_VALUE);
					}else{
					vo.setMedCond(value0);
					}
					break;
				case MOH_PRESCRIBER_ID: 
					vo.setMohPrescriberId(rs.getString(++pos));
					break;
				case ODB_ELIG_NO: 
					vo.setOdbEligNo(rs.getString(++pos));
					break;
				case THERA_CLASS: 
					vo.setTheraClass(rs.getString(++pos));
					break;
				case INTERVENTION_10: 
					vo.setIntervention10(rs.getString(++pos));
					break;
				case INTERVENTION_9: 
					vo.setIntervention9(rs.getString(++pos));
					break;
				case INTERVENTION_8: 
					vo.setIntervention8(rs.getString(++pos));
					break;
				case INTERVENTION_7: 
					vo.setIntervention7(rs.getString(++pos));
					break;
				case INTERVENTION_6: 
					vo.setIntervention6(rs.getString(++pos));
					break;
				case INTERVENTION_5: 
					vo.setIntervention5(rs.getString(++pos));
					break;
				case INTERVENTION_4: 
					vo.setIntervention4(rs.getString(++pos));
					break;
				case INTERVENTION_3: 
					vo.setIntervention3(rs.getString(++pos));
					break;
				case INTERVENTION_2: 
					vo.setIntervention2(rs.getString(++pos));
					break;
				case INTERVENTION_1: 
					vo.setIntervention1(rs.getString(++pos));
					break;
				case PROG_ID: 
					vo.setProgId(rs.getString(++pos));
					break;
				case CURR_STAT: 
					vo.setCurrStat(rs.getString(++pos));
					break;
				case PROD_SEL: 
					double value16 = rs.getDouble(++pos);
					if(value16  == 0 && rs.wasNull()){
					vo.setProdSel(Integer.MIN_VALUE);
					}else{
					vo.setProdSel(value16);
					}
					break;
				case CURR_RX_NO: 
					vo.setCurrRxNo(rs.getString(++pos));
					break;
				case TOT_AMT_PD: 
					double value18 = rs.getDouble(++pos);
					if(value18  == 0 && rs.wasNull()){
					vo.setTotAmtPd(Integer.MIN_VALUE);
					}else{
					vo.setTotAmtPd(value18);
					}
					break;
				case DEDUCT_TO_COLLECT: 
					double value19 = rs.getDouble(++pos);
					if(value19  == 0 && rs.wasNull()){
					vo.setDeductToCollect(Integer.MIN_VALUE);
					}else{
					vo.setDeductToCollect(value19);
					}
					break;
				case COMP_FEE_ALLD: 
					int value20 = rs.getInt(++pos);
					if(value20  == 0 && rs.wasNull()){
					vo.setCompFeeAlld(Integer.MIN_VALUE);
					}else{
					vo.setCompFeeAlld(value20);
					}
					break;
				case PROF_FEE_ALLD: 
					int value21 = rs.getInt(++pos);
					if(value21  == 0 && rs.wasNull()){
					vo.setProfFeeAlld(Integer.MIN_VALUE);
					}else{
					vo.setProfFeeAlld(value21);
					}
					break;
				case CST_UPCHRG_ALLD: 
					double value22 = rs.getDouble(++pos);
					if(value22  == 0 && rs.wasNull()){
					vo.setCstUpchrgAlld(Integer.MIN_VALUE);
					}else{
					vo.setCstUpchrgAlld(value22);
					}
					break;
				case DRG_CST_ALLD: 
					double value23 = rs.getDouble(++pos);
					if(value23  == 0 && rs.wasNull()){
					vo.setDrgCstAlld(Integer.MIN_VALUE);
					}else{
					vo.setDrgCstAlld(value23);
					}
					break;
				case QTY: 
					double value24 = rs.getDouble(++pos);
					if(value24  > 0 ){
						vo.setQty(value24);
					}
					break;
				case ADJUDICATION_DT: 
					Date value25 = rs.getDate(++pos);
					if(value25  == null && rs.wasNull()){
					vo.setAdjudicationDt(new java.sql.Date(0));
					}else{
					vo.setAdjudicationDt(value25);
					}
					break;
				case DT_OF_SERV: 
					Date value26 = rs.getDate(++pos);
					if(value26  == null && rs.wasNull()){
					vo.setDtOfServ(new java.sql.Date(0));
					}else{
					vo.setDtOfServ(value26);
					}
					break;
				case LTC_FLAG: 
					int value27 = rs.getInt(++pos);
					if(value27  == 0 && rs.wasNull()){
					vo.setLtcFlag(Integer.MIN_VALUE);
					}else{
					vo.setLtcFlag(value27);
					}
					break;
				case DIN_PIN: 
					vo.setDinPin(rs.getString(++pos));
					break;
				case AGENCY_ID: 
					int value29 = rs.getInt(++pos);
					if(value29  == 0 && rs.wasNull()){
					vo.setAgencyId(Integer.MIN_VALUE);
					}else{
					vo.setAgencyId(value29);
					}
					break;
				case CLAIM_ID: 
					long value30 = rs.getLong(++pos);
					if(value30  == 0 && rs.wasNull()){
					vo.setClaimId(Integer.MIN_VALUE);
					}else{
					vo.setClaimId(value30);
					}
					break;
				case POSTAL_CD: 
					vo.setPostalCd(rs.getString(++pos));
					break;
				case AG_TYPE_CD: 
					vo.setAgTypeCd(rs.getString(++pos));
					break;
				case COUNTY_CD: 
					vo.setCountyCd(rs.getString(++pos));
					break;
				case PLAN_CD: 
					vo.setPlanCd(rs.getString(++pos));
					break;
				case DYS_SUPPLY: 
					int value35 = rs.getInt(++pos);
					if(value35  == 0 && rs.wasNull()){
					vo.setDysSupply(Integer.MIN_VALUE);
					}else{
					vo.setDysSupply(value35);
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

