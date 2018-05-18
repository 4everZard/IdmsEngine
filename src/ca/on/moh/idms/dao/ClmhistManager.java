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

import ca.on.moh.idms.persister.ClmhistPersister;
import ca.on.moh.idms.persister.ClmhistPersisterImpl;
import ca.on.moh.idms.vo.Clmhist;


public class ClmhistManager {

	static final long serialVersionUID = 6825275;
	private ClmhistPersister persister = new ClmhistPersisterImpl();

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
		Clmhist obj = (Clmhist)vo;
		return  persister.findByPK(obj.getClaimId());
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
				Clmhist obj = (Clmhist)vo;
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
		Clmhist obj = (Clmhist)vo;
		String where = "where";
		if(obj.getMedCond() != Integer.MIN_VALUE && obj.getMedCond() != 0){ 
			where = where + " MED_COND=" + obj.getMedCond() + " AND"; 
		}
		if(obj.getMohPrescriberId() != null && !obj.getMohPrescriberId().trim().equals("")){ 
			where = where + " UPPER(MOH_PRESCRIBER_ID) like UPPER('%" + obj.getMohPrescriberId() + "%') AND"; 
		}
		if(obj.getOdbEligNo() != null && !obj.getOdbEligNo().trim().equals("")){ 
			where = where + " UPPER(ODB_ELIG_NO) like UPPER('%" + obj.getOdbEligNo() + "%') AND"; 
		}
		if(obj.getTheraClass() != null && !obj.getTheraClass().trim().equals("")){ 
			where = where + " UPPER(THERA_CLASS) like UPPER('%" + obj.getTheraClass() + "%') AND"; 
		}
		if(obj.getIntervention10() != null && !obj.getIntervention10().trim().equals("")){ 
			where = where + " UPPER(INTERVENTION_10) like UPPER('%" + obj.getIntervention10() + "%') AND"; 
		}
		if(obj.getIntervention9() != null && !obj.getIntervention9().trim().equals("")){ 
			where = where + " UPPER(INTERVENTION_9) like UPPER('%" + obj.getIntervention9() + "%') AND"; 
		}
		if(obj.getIntervention8() != null && !obj.getIntervention8().trim().equals("")){ 
			where = where + " UPPER(INTERVENTION_8) like UPPER('%" + obj.getIntervention8() + "%') AND"; 
		}
		if(obj.getIntervention7() != null && !obj.getIntervention7().trim().equals("")){ 
			where = where + " UPPER(INTERVENTION_7) like UPPER('%" + obj.getIntervention7() + "%') AND"; 
		}
		if(obj.getIntervention6() != null && !obj.getIntervention6().trim().equals("")){ 
			where = where + " UPPER(INTERVENTION_6) like UPPER('%" + obj.getIntervention6() + "%') AND"; 
		}
		if(obj.getIntervention5() != null && !obj.getIntervention5().trim().equals("")){ 
			where = where + " UPPER(INTERVENTION_5) like UPPER('%" + obj.getIntervention5() + "%') AND"; 
		}
		if(obj.getIntervention4() != null && !obj.getIntervention4().trim().equals("")){ 
			where = where + " UPPER(INTERVENTION_4) like UPPER('%" + obj.getIntervention4() + "%') AND"; 
		}
		if(obj.getIntervention3() != null && !obj.getIntervention3().trim().equals("")){ 
			where = where + " UPPER(INTERVENTION_3) like UPPER('%" + obj.getIntervention3() + "%') AND"; 
		}
		if(obj.getIntervention2() != null && !obj.getIntervention2().trim().equals("")){ 
			where = where + " UPPER(INTERVENTION_2) like UPPER('%" + obj.getIntervention2() + "%') AND"; 
		}
		if(obj.getIntervention1() != null && !obj.getIntervention1().trim().equals("")){ 
			where = where + " UPPER(INTERVENTION_1) like UPPER('%" + obj.getIntervention1() + "%') AND"; 
		}
		if(obj.getProgId() != null && !obj.getProgId().trim().equals("")){ 
			where = where + " UPPER(PROG_ID) like UPPER('%" + obj.getProgId() + "%') AND"; 
		}
		if(obj.getCurrStat() != null && !obj.getCurrStat().trim().equals("")){ 
			where = where + " UPPER(CURR_STAT) like UPPER('%" + obj.getCurrStat() + "%') AND"; 
		}
		if(obj.getProdSel() != Integer.MIN_VALUE && obj.getProdSel() != 0){ 
			where = where + " PROD_SEL=" + obj.getProdSel() + " AND"; 
		}
		if(obj.getCurrRxNo() != null && !obj.getCurrRxNo().trim().equals("")){ 
			where = where + " UPPER(CURR_RX_NO) like UPPER('%" + obj.getCurrRxNo() + "%') AND"; 
		}
		if(obj.getTotAmtPd() != Integer.MIN_VALUE && obj.getTotAmtPd() != 0){ 
			where = where + " TOT_AMT_PD=" + obj.getTotAmtPd() + " AND"; 
		}
		if(obj.getDeductToCollect() != Integer.MIN_VALUE && obj.getDeductToCollect() != 0){ 
			where = where + " DEDUCT_TO_COLLECT=" + obj.getDeductToCollect() + " AND"; 
		}
		if(obj.getCompFeeAlld() != Integer.MIN_VALUE && obj.getCompFeeAlld() != 0){ 
			where = where + " COMP_FEE_ALLD=" + obj.getCompFeeAlld() + " AND"; 
		}
		if(obj.getProfFeeAlld() != Integer.MIN_VALUE && obj.getProfFeeAlld() != 0){ 
			where = where + " PROF_FEE_ALLD=" + obj.getProfFeeAlld() + " AND"; 
		}
		if(obj.getCstUpchrgAlld() != Integer.MIN_VALUE && obj.getCstUpchrgAlld() != 0){ 
			where = where + " CST_UPCHRG_ALLD=" + obj.getCstUpchrgAlld() + " AND"; 
		}
		if(obj.getDrgCstAlld() != Integer.MIN_VALUE && obj.getDrgCstAlld() != 0){ 
			where = where + " DRG_CST_ALLD=" + obj.getDrgCstAlld() + " AND"; 
		}
		if(obj.getQty() != Integer.MIN_VALUE && obj.getQty() != 0){ 
			where = where + " QTY=" + obj.getQty() + " AND"; 
		}
		if(obj.getAdjudicationDt() != null){ 
			SimpleDateFormat format = new SimpleDateFormat(ValueObject.SIMPLE_DATE_FORMAT);
			String aString = format.format( new java.sql.Timestamp(obj.getAdjudicationDt().getTime()));
			where = where + " ADJUDICATION_DT = to_date('" + aString + "','" + ValueObject.SQL_DATE_FORMAT + "') AND"; 
		}
		if(obj.getDtOfServ() != null){ 
			SimpleDateFormat format = new SimpleDateFormat(ValueObject.SIMPLE_DATE_FORMAT);
			String aString = format.format( new java.sql.Timestamp(obj.getDtOfServ().getTime()));
			where = where + " DT_OF_SERV = to_date('" + aString + "','" + ValueObject.SQL_DATE_FORMAT + "') AND"; 
		}
		if(obj.getLtcFlag() != Integer.MIN_VALUE && obj.getLtcFlag() != 0){ 
			where = where + " LTC_FLAG=" + obj.getLtcFlag() + " AND"; 
		}
		if(obj.getDinPin() != null && !obj.getDinPin().trim().equals("")){ 
			where = where + " UPPER(DIN_PIN) like UPPER('%" + obj.getDinPin() + "%') AND"; 
		}
		if(obj.getAgencyId() != Integer.MIN_VALUE && obj.getAgencyId() != 0){ 
			where = where + " AGENCY_ID=" + obj.getAgencyId() + " AND"; 
		}
		if(obj.getClaimId() != Integer.MIN_VALUE && obj.getClaimId() != 0){ 
			where = where + " CLAIM_ID=" + obj.getClaimId() + " AND"; 
		}
		if(obj.getPostalCd() != null && !obj.getPostalCd().trim().equals("")){ 
			where = where + " UPPER(POSTAL_CD) like UPPER('%" + obj.getPostalCd() + "%') AND"; 
		}
		if(obj.getAgTypeCd() != null && !obj.getAgTypeCd().trim().equals("")){ 
			where = where + " UPPER(AG_TYPE_CD) like UPPER('%" + obj.getAgTypeCd() + "%') AND"; 
		}
		if(obj.getCountyCd() != null && !obj.getCountyCd().trim().equals("")){ 
			where = where + " UPPER(COUNTY_CD) like UPPER('%" + obj.getCountyCd() + "%') AND"; 
		}
		if(obj.getPlanCd() != null && !obj.getPlanCd().trim().equals("")){ 
			where = where + " UPPER(PLAN_CD) like UPPER('%" + obj.getPlanCd() + "%') AND"; 
		}
		if(obj.getDysSupply() != Integer.MIN_VALUE && obj.getDysSupply() != 0){ 
			where = where + " DYS_SUPPLY=" + obj.getDysSupply() + " AND"; 
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
		Clmhist obj = (Clmhist)vo;
		int neededColumns [] =  obj.getNeededColumns();
		String where = " where";
		if(obj.getMedCond() != Integer.MIN_VALUE && obj.getMedCond() != 0){ 
			where = where + " MED_COND=" + obj.getMedCond() + " AND"; 
		}
		if(obj.getMohPrescriberId() != null && !obj.getMohPrescriberId().trim().equals("")){ 
			where = where + " UPPER(MOH_PRESCRIBER_ID) like UPPER('%" + obj.getMohPrescriberId() + "%') AND"; 
		}
		if(obj.getOdbEligNo() != null && !obj.getOdbEligNo().trim().equals("")){ 
			where = where + " UPPER(ODB_ELIG_NO) like UPPER('%" + obj.getOdbEligNo() + "%') AND"; 
		}
		if(obj.getTheraClass() != null && !obj.getTheraClass().trim().equals("")){ 
			where = where + " UPPER(THERA_CLASS) like UPPER('%" + obj.getTheraClass() + "%') AND"; 
		}
		if(obj.getIntervention10() != null && !obj.getIntervention10().trim().equals("")){ 
			where = where + " UPPER(INTERVENTION_10) like UPPER('%" + obj.getIntervention10() + "%') AND"; 
		}
		if(obj.getIntervention9() != null && !obj.getIntervention9().trim().equals("")){ 
			where = where + " UPPER(INTERVENTION_9) like UPPER('%" + obj.getIntervention9() + "%') AND"; 
		}
		if(obj.getIntervention8() != null && !obj.getIntervention8().trim().equals("")){ 
			where = where + " UPPER(INTERVENTION_8) like UPPER('%" + obj.getIntervention8() + "%') AND"; 
		}
		if(obj.getIntervention7() != null && !obj.getIntervention7().trim().equals("")){ 
			where = where + " UPPER(INTERVENTION_7) like UPPER('%" + obj.getIntervention7() + "%') AND"; 
		}
		if(obj.getIntervention6() != null && !obj.getIntervention6().trim().equals("")){ 
			where = where + " UPPER(INTERVENTION_6) like UPPER('%" + obj.getIntervention6() + "%') AND"; 
		}
		if(obj.getIntervention5() != null && !obj.getIntervention5().trim().equals("")){ 
			where = where + " UPPER(INTERVENTION_5) like UPPER('%" + obj.getIntervention5() + "%') AND"; 
		}
		if(obj.getIntervention4() != null && !obj.getIntervention4().trim().equals("")){ 
			where = where + " UPPER(INTERVENTION_4) like UPPER('%" + obj.getIntervention4() + "%') AND"; 
		}
		if(obj.getIntervention3() != null && !obj.getIntervention3().trim().equals("")){ 
			where = where + " UPPER(INTERVENTION_3) like UPPER('%" + obj.getIntervention3() + "%') AND"; 
		}
		if(obj.getIntervention2() != null && !obj.getIntervention2().trim().equals("")){ 
			where = where + " UPPER(INTERVENTION_2) like UPPER('%" + obj.getIntervention2() + "%') AND"; 
		}
		if(obj.getIntervention1() != null && !obj.getIntervention1().trim().equals("")){ 
			where = where + " UPPER(INTERVENTION_1) like UPPER('%" + obj.getIntervention1() + "%') AND"; 
		}
		if(obj.getProgId() != null && !obj.getProgId().trim().equals("")){ 
			where = where + " UPPER(PROG_ID) like UPPER('%" + obj.getProgId() + "%') AND"; 
		}
		if(obj.getCurrStat() != null && !obj.getCurrStat().trim().equals("")){ 
			where = where + " UPPER(CURR_STAT) like UPPER('%" + obj.getCurrStat() + "%') AND"; 
		}
		if(obj.getProdSel() != Integer.MIN_VALUE && obj.getProdSel() != 0){ 
			where = where + " PROD_SEL=" + obj.getProdSel() + " AND"; 
		}
		if(obj.getCurrRxNo() != null && !obj.getCurrRxNo().trim().equals("")){ 
			where = where + " UPPER(CURR_RX_NO) like UPPER('%" + obj.getCurrRxNo() + "%') AND"; 
		}
		if(obj.getTotAmtPd() != Integer.MIN_VALUE && obj.getTotAmtPd() != 0){ 
			where = where + " TOT_AMT_PD=" + obj.getTotAmtPd() + " AND"; 
		}
		if(obj.getDeductToCollect() != Integer.MIN_VALUE && obj.getDeductToCollect() != 0){ 
			where = where + " DEDUCT_TO_COLLECT=" + obj.getDeductToCollect() + " AND"; 
		}
		if(obj.getCompFeeAlld() != Integer.MIN_VALUE && obj.getCompFeeAlld() != 0){ 
			where = where + " COMP_FEE_ALLD=" + obj.getCompFeeAlld() + " AND"; 
		}
		if(obj.getProfFeeAlld() != Integer.MIN_VALUE && obj.getProfFeeAlld() != 0){ 
			where = where + " PROF_FEE_ALLD=" + obj.getProfFeeAlld() + " AND"; 
		}
		if(obj.getCstUpchrgAlld() != Integer.MIN_VALUE && obj.getCstUpchrgAlld() != 0){ 
			where = where + " CST_UPCHRG_ALLD=" + obj.getCstUpchrgAlld() + " AND"; 
		}
		if(obj.getDrgCstAlld() != Integer.MIN_VALUE && obj.getDrgCstAlld() != 0){ 
			where = where + " DRG_CST_ALLD=" + obj.getDrgCstAlld() + " AND"; 
		}
		if(obj.getQty() != Integer.MIN_VALUE && obj.getQty() != 0){ 
			where = where + " QTY=" + obj.getQty() + " AND"; 
		}
		if(obj.getAdjudicationDt() != null){ 
			SimpleDateFormat format = new SimpleDateFormat(ValueObject.SIMPLE_DATE_FORMAT);
			String aString = format.format( new java.sql.Timestamp(obj.getAdjudicationDt().getTime()));
			where = where + " ADJUDICATION_DT = to_date('" + aString + "','" + ValueObject.SQL_DATE_FORMAT + "') AND"; 
		}
		if(obj.getDtOfServ() != null){ 
			SimpleDateFormat format = new SimpleDateFormat(ValueObject.SIMPLE_DATE_FORMAT);
			String aString = format.format( new java.sql.Timestamp(obj.getDtOfServ().getTime()));
			where = where + " DT_OF_SERV = to_date('" + aString + "','" + ValueObject.SQL_DATE_FORMAT + "') AND"; 
		}
		if(obj.getLtcFlag() != Integer.MIN_VALUE && obj.getLtcFlag() != 0){ 
			where = where + " LTC_FLAG=" + obj.getLtcFlag() + " AND"; 
		}
		if(obj.getDinPin() != null && !obj.getDinPin().trim().equals("")){ 
			where = where + " UPPER(DIN_PIN) like UPPER('%" + obj.getDinPin() + "%') AND"; 
		}
		if(obj.getAgencyId() != Integer.MIN_VALUE && obj.getAgencyId() != 0){ 
			where = where + " AGENCY_ID=" + obj.getAgencyId() + " AND"; 
		}
		if(obj.getClaimId() != Integer.MIN_VALUE && obj.getClaimId() != 0){ 
			where = where + " CLAIM_ID=" + obj.getClaimId() + " AND"; 
		}
		if(obj.getPostalCd() != null && !obj.getPostalCd().trim().equals("")){ 
			where = where + " UPPER(POSTAL_CD) like UPPER('%" + obj.getPostalCd() + "%') AND"; 
		}
		if(obj.getAgTypeCd() != null && !obj.getAgTypeCd().trim().equals("")){ 
			where = where + " UPPER(AG_TYPE_CD) like UPPER('%" + obj.getAgTypeCd() + "%') AND"; 
		}
		if(obj.getCountyCd() != null && !obj.getCountyCd().trim().equals("")){ 
			where = where + " UPPER(COUNTY_CD) like UPPER('%" + obj.getCountyCd() + "%') AND"; 
		}
		if(obj.getPlanCd() != null && !obj.getPlanCd().trim().equals("")){ 
			where = where + " UPPER(PLAN_CD) like UPPER('%" + obj.getPlanCd() + "%') AND"; 
		}
		if(obj.getDysSupply() != Integer.MIN_VALUE && obj.getDysSupply() != 0){ 
			where = where + " DYS_SUPPLY=" + obj.getDysSupply() + " AND"; 
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
		Clmhist obj = (Clmhist)vo;
		persister.deleteByPK(obj.getClaimId());
	}

	/**
	 * delete one record from the entity table the conditions defined in the value object.
	 *
	 * @param vo ValueObject, value object defining delete conditions	 *
	 * @return rows int.
	 */
	public int deleteByObject(ValueObject vo)throws Exception {
		Clmhist obj = (Clmhist)vo;
		int rows = 0;
		String where = " where";
		if(obj.getMedCond() != Integer.MIN_VALUE && obj.getMedCond() != 0){ 
			where = where + " MED_COND=" + obj.getMedCond() + " AND"; 
		}
		if(obj.getMohPrescriberId() != null && !obj.getMohPrescriberId().trim().equals("")){ 
			where = where + " UPPER(MOH_PRESCRIBER_ID) like UPPER('%" + obj.getMohPrescriberId() + "%') AND"; 
		}
		if(obj.getOdbEligNo() != null && !obj.getOdbEligNo().trim().equals("")){ 
			where = where + " UPPER(ODB_ELIG_NO) like UPPER('%" + obj.getOdbEligNo() + "%') AND"; 
		}
		if(obj.getTheraClass() != null && !obj.getTheraClass().trim().equals("")){ 
			where = where + " UPPER(THERA_CLASS) like UPPER('%" + obj.getTheraClass() + "%') AND"; 
		}
		if(obj.getIntervention10() != null && !obj.getIntervention10().trim().equals("")){ 
			where = where + " UPPER(INTERVENTION_10) like UPPER('%" + obj.getIntervention10() + "%') AND"; 
		}
		if(obj.getIntervention9() != null && !obj.getIntervention9().trim().equals("")){ 
			where = where + " UPPER(INTERVENTION_9) like UPPER('%" + obj.getIntervention9() + "%') AND"; 
		}
		if(obj.getIntervention8() != null && !obj.getIntervention8().trim().equals("")){ 
			where = where + " UPPER(INTERVENTION_8) like UPPER('%" + obj.getIntervention8() + "%') AND"; 
		}
		if(obj.getIntervention7() != null && !obj.getIntervention7().trim().equals("")){ 
			where = where + " UPPER(INTERVENTION_7) like UPPER('%" + obj.getIntervention7() + "%') AND"; 
		}
		if(obj.getIntervention6() != null && !obj.getIntervention6().trim().equals("")){ 
			where = where + " UPPER(INTERVENTION_6) like UPPER('%" + obj.getIntervention6() + "%') AND"; 
		}
		if(obj.getIntervention5() != null && !obj.getIntervention5().trim().equals("")){ 
			where = where + " UPPER(INTERVENTION_5) like UPPER('%" + obj.getIntervention5() + "%') AND"; 
		}
		if(obj.getIntervention4() != null && !obj.getIntervention4().trim().equals("")){ 
			where = where + " UPPER(INTERVENTION_4) like UPPER('%" + obj.getIntervention4() + "%') AND"; 
		}
		if(obj.getIntervention3() != null && !obj.getIntervention3().trim().equals("")){ 
			where = where + " UPPER(INTERVENTION_3) like UPPER('%" + obj.getIntervention3() + "%') AND"; 
		}
		if(obj.getIntervention2() != null && !obj.getIntervention2().trim().equals("")){ 
			where = where + " UPPER(INTERVENTION_2) like UPPER('%" + obj.getIntervention2() + "%') AND"; 
		}
		if(obj.getIntervention1() != null && !obj.getIntervention1().trim().equals("")){ 
			where = where + " UPPER(INTERVENTION_1) like UPPER('%" + obj.getIntervention1() + "%') AND"; 
		}
		if(obj.getProgId() != null && !obj.getProgId().trim().equals("")){ 
			where = where + " UPPER(PROG_ID) like UPPER('%" + obj.getProgId() + "%') AND"; 
		}
		if(obj.getCurrStat() != null && !obj.getCurrStat().trim().equals("")){ 
			where = where + " UPPER(CURR_STAT) like UPPER('%" + obj.getCurrStat() + "%') AND"; 
		}
		if(obj.getProdSel() != Integer.MIN_VALUE && obj.getProdSel() != 0){ 
			where = where + " PROD_SEL=" + obj.getProdSel() + " AND"; 
		}
		if(obj.getCurrRxNo() != null && !obj.getCurrRxNo().trim().equals("")){ 
			where = where + " UPPER(CURR_RX_NO) like UPPER('%" + obj.getCurrRxNo() + "%') AND"; 
		}
		if(obj.getTotAmtPd() != Integer.MIN_VALUE && obj.getTotAmtPd() != 0){ 
			where = where + " TOT_AMT_PD=" + obj.getTotAmtPd() + " AND"; 
		}
		if(obj.getDeductToCollect() != Integer.MIN_VALUE && obj.getDeductToCollect() != 0){ 
			where = where + " DEDUCT_TO_COLLECT=" + obj.getDeductToCollect() + " AND"; 
		}
		if(obj.getCompFeeAlld() != Integer.MIN_VALUE && obj.getCompFeeAlld() != 0){ 
			where = where + " COMP_FEE_ALLD=" + obj.getCompFeeAlld() + " AND"; 
		}
		if(obj.getProfFeeAlld() != Integer.MIN_VALUE && obj.getProfFeeAlld() != 0){ 
			where = where + " PROF_FEE_ALLD=" + obj.getProfFeeAlld() + " AND"; 
		}
		if(obj.getCstUpchrgAlld() != Integer.MIN_VALUE && obj.getCstUpchrgAlld() != 0){ 
			where = where + " CST_UPCHRG_ALLD=" + obj.getCstUpchrgAlld() + " AND"; 
		}
		if(obj.getDrgCstAlld() != Integer.MIN_VALUE && obj.getDrgCstAlld() != 0){ 
			where = where + " DRG_CST_ALLD=" + obj.getDrgCstAlld() + " AND"; 
		}
		if(obj.getQty() != Integer.MIN_VALUE && obj.getQty() != 0){ 
			where = where + " QTY=" + obj.getQty() + " AND"; 
		}
		if(obj.getAdjudicationDt() != null){ 
			SimpleDateFormat format = new SimpleDateFormat(ValueObject.SIMPLE_DATE_FORMAT);
			String aString = format.format( new java.sql.Timestamp(obj.getAdjudicationDt().getTime()));
			where = where + " ADJUDICATION_DT = to_date('" + aString + "','" + ValueObject.SQL_DATE_FORMAT + "') AND"; 
		}
		if(obj.getDtOfServ() != null){ 
			SimpleDateFormat format = new SimpleDateFormat(ValueObject.SIMPLE_DATE_FORMAT);
			String aString = format.format( new java.sql.Timestamp(obj.getDtOfServ().getTime()));
			where = where + " DT_OF_SERV = to_date('" + aString + "','" + ValueObject.SQL_DATE_FORMAT + "') AND"; 
		}
		if(obj.getLtcFlag() != Integer.MIN_VALUE && obj.getLtcFlag() != 0){ 
			where = where + " LTC_FLAG=" + obj.getLtcFlag() + " AND"; 
		}
		if(obj.getDinPin() != null && !obj.getDinPin().trim().equals("")){ 
			where = where + " UPPER(DIN_PIN) like UPPER('%" + obj.getDinPin() + "%') AND"; 
		}
		if(obj.getAgencyId() != Integer.MIN_VALUE && obj.getAgencyId() != 0){ 
			where = where + " AGENCY_ID=" + obj.getAgencyId() + " AND"; 
		}
		if(obj.getClaimId() != Integer.MIN_VALUE && obj.getClaimId() != 0){ 
			where = where + " CLAIM_ID=" + obj.getClaimId() + " AND"; 
		}
		if(obj.getPostalCd() != null && !obj.getPostalCd().trim().equals("")){ 
			where = where + " UPPER(POSTAL_CD) like UPPER('%" + obj.getPostalCd() + "%') AND"; 
		}
		if(obj.getAgTypeCd() != null && !obj.getAgTypeCd().trim().equals("")){ 
			where = where + " UPPER(AG_TYPE_CD) like UPPER('%" + obj.getAgTypeCd() + "%') AND"; 
		}
		if(obj.getCountyCd() != null && !obj.getCountyCd().trim().equals("")){ 
			where = where + " UPPER(COUNTY_CD) like UPPER('%" + obj.getCountyCd() + "%') AND"; 
		}
		if(obj.getPlanCd() != null && !obj.getPlanCd().trim().equals("")){ 
			where = where + " UPPER(PLAN_CD) like UPPER('%" + obj.getPlanCd() + "%') AND"; 
		}
		if(obj.getDysSupply() != Integer.MIN_VALUE && obj.getDysSupply() != 0){ 
			where = where + " DYS_SUPPLY=" + obj.getDysSupply() + " AND"; 
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

