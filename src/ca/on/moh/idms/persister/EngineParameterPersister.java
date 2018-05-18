package ca.on.moh.idms.persister;

/**
 * The class to manager EngineParameterPersister persistent. 
 * <br> 
 * Automatically generated persister interface. 
 * If any questions, contact Davy Hu (hu_davy@yahoo.com)
 */

import java.sql.Connection;

import ca.on.moh.idms.vo.EngineParameter;

public interface EngineParameterPersister extends Persister{

	int PARAMETER_ID = 0;
	int PARAMETER_VALUE = 1;
	int EFFECTIVE_DATE = 2;
	int END_DATE = 3;
	int PARAMETER_CREATED_BY = 4;
	int PARAMETER_UPDATED_BY = 5;
	int PARAMETER_NAME = 6;

	/**
	 * Find a record in the table by primary key.
	 *
	 * @param parameterId int.
	 *
	 * @return EngineParameter.
	 */
	public EngineParameter findByPK(int parameterId) throws Exception;

	/**
	 * Find a record in the table by primary key in a given data source.
	 *
	 * @param parameterId int.
	 * @param dataSourceName String.
	 *
	 * @return EngineParameter.
	 */
	public EngineParameter findByPK(int parameterId, String dataSourceName) throws Exception;

	/**
	 * Find a record in the table by primary key in a given data source.
	 *
	 * @param parameterId int.
	 * @param conn Connection.
	 *
	 * @return EngineParameter.
	 */
	public EngineParameter findByPK(int parameterId, Connection conn) throws Exception;

	/**
	 * Delete a record in the table by primary key.
	 *
	 * @param parameterId int.
	 *
	 * @return int record number deleted.
	 */
	public int deleteByPK(int parameterId) throws Exception;

	/**
	 * Find a record in the table by primary key in a given data source.
	 *
	 * @param parameterId int.
	 * @param dataSourceName String.
	 *
	 * @return int record number deleted.
	 */
	public int deleteByPK(int parameterId, String dataSourceName) throws Exception;

	/**
	 * Find a record in the table by primary key in a given data source.
	 *
	 * @param parameterId int.
	 * @param conn Connection.
	 *
	 * @return int record number deleted.
	 */
	public int deleteByPK(int parameterId, Connection conn) throws Exception;

}
