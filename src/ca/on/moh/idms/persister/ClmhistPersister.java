package ca.on.moh.idms.persister;

/**
 * The class to manager ClmhistPersister persistent. 
 * <br> 
 * Automatically generated persister interface. 
 * If any questions, contact Davy Hu (hu_davy@yahoo.com)
 */

import java.sql.Connection;

import ca.on.moh.idms.vo.Clmhist;

public interface ClmhistPersister extends Persister{

	int MED_COND = 0;
	int MOH_PRESCRIBER_ID = 1;
	int ODB_ELIG_NO = 2;
	int THERA_CLASS = 3;
	int INTERVENTION_10 = 4;
	int INTERVENTION_9 = 5;
	int INTERVENTION_8 = 6;
	int INTERVENTION_7 = 7;
	int INTERVENTION_6 = 8;
	int INTERVENTION_5 = 9;
	int INTERVENTION_4 = 10;
	int INTERVENTION_3 = 11;
	int INTERVENTION_2 = 12;
	int INTERVENTION_1 = 13;
	int PROG_ID = 14;
	int CURR_STAT = 15;
	int PROD_SEL = 16;
	int CURR_RX_NO = 17;
	int TOT_AMT_PD = 18;
	int DEDUCT_TO_COLLECT = 19;
	int COMP_FEE_ALLD = 20;
	int PROF_FEE_ALLD = 21;
	int CST_UPCHRG_ALLD = 22;
	int DRG_CST_ALLD = 23;
	int QTY = 24;
	int ADJUDICATION_DT = 25;
	int DT_OF_SERV = 26;
	int LTC_FLAG = 27;
	int DIN_PIN = 28;
	int AGENCY_ID = 29;
	int CLAIM_ID = 30;
	int POSTAL_CD = 31;
	int AG_TYPE_CD = 32;
	int COUNTY_CD = 33;
	int PLAN_CD = 34;
	int DYS_SUPPLY = 35;

	/**
	 * Find a record in the table by primary key.
	 *
	 * @param claimId long.
	 *
	 * @return Clmhist.
	 */
	public Clmhist findByPK(long claimId) throws Exception;

	/**
	 * Find a record in the table by primary key in a given data source.
	 *
	 * @param claimId long.
	 * @param dataSourceName String.
	 *
	 * @return Clmhist.
	 */
	public Clmhist findByPK(long claimId, String dataSourceName) throws Exception;

	/**
	 * Find a record in the table by primary key in a given data source.
	 *
	 * @param claimId long.
	 * @param conn Connection.
	 *
	 * @return Clmhist.
	 */
	public Clmhist findByPK(long claimId, Connection conn) throws Exception;

	/**
	 * Delete a record in the table by primary key.
	 *
	 * @param claimId long.
	 *
	 * @return int record number deleted.
	 */
	public int deleteByPK(long claimId) throws Exception;

	/**
	 * Find a record in the table by primary key in a given data source.
	 *
	 * @param claimId long.
	 * @param dataSourceName String.
	 *
	 * @return int record number deleted.
	 */
	public int deleteByPK(long claimId, String dataSourceName) throws Exception;

	/**
	 * Find a record in the table by primary key in a given data source.
	 *
	 * @param claimId long.
	 * @param conn Connection.
	 *
	 * @return int record number deleted.
	 */
	public int deleteByPK(long claimId, Connection conn) throws Exception;

}
