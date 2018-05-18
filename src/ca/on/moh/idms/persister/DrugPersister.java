package ca.on.moh.idms.persister;

/**
 * The class to manager DrugPersister persistent. 
 * <br> 
 * Automatically generated persister interface. 
 * If any questions, contact Davy Hu (hu_davy@yahoo.com)
 */

import java.sql.Connection;

import ca.on.moh.idms.vo.Drug;

public interface DrugPersister extends Persister{

	int REC_EFF_DT = 0;
	int REC_CREATE_TIMESTAMP = 1;
	int REC_INACTIVE_TIMESTAMP = 2;
	int DBP_LIM_JL115 = 3;
	int DRUG_ID = 4;
	int INDIVIDUAL_PRICE = 5;
	int AGENCY_ID = 6;
	int DOSAGE_FORM = 7;
	int MANUFACTURER_CD = 8;
	int DIN_PIN = 9;
	int DIN_DESC = 10;
	int GEN_NAME = 11;
	int STRENGTH = 12;

	/**
	 * Find a record in the table by primary key.
	 *
	 * @param drugId int.
	 *
	 * @return Drug.
	 */
	public Drug findByPK(int drugId) throws Exception;

	/**
	 * Find a record in the table by primary key in a given data source.
	 *
	 * @param drugId int.
	 * @param dataSourceName String.
	 *
	 * @return Drug.
	 */
	public Drug findByPK(int drugId, String dataSourceName) throws Exception;

	/**
	 * Find a record in the table by primary key in a given data source.
	 *
	 * @param drugId int.
	 * @param conn Connection.
	 *
	 * @return Drug.
	 */
	public Drug findByPK(int drugId, Connection conn) throws Exception;

	/**
	 * Delete a record in the table by primary key.
	 *
	 * @param drugId int.
	 *
	 * @return int record number deleted.
	 */
	public int deleteByPK(int drugId) throws Exception;

	/**
	 * Find a record in the table by primary key in a given data source.
	 *
	 * @param drugId int.
	 * @param dataSourceName String.
	 *
	 * @return int record number deleted.
	 */
	public int deleteByPK(int drugId, String dataSourceName) throws Exception;

	/**
	 * Find a record in the table by primary key in a given data source.
	 *
	 * @param drugId int.
	 * @param conn Connection.
	 *
	 * @return int record number deleted.
	 */
	public int deleteByPK(int drugId, Connection conn) throws Exception;

}
