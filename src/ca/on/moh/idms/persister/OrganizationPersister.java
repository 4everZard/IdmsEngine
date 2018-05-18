package ca.on.moh.idms.persister;

/**
 * The class to manager OrganizationPersister persistent. 
 * <br> 
 * Automatically generated persister interface. 
 * If any questions, contact Davy Hu (hu_davy@yahoo.com)
 */

import java.sql.Connection;

import ca.on.moh.idms.vo.Organization;

public interface OrganizationPersister extends Persister{

	int ORGANIZATION_NAME = 0;
	int ORGANIZATION_TYPE = 1;
	int ORGANIZATION_CODE = 2;
	int CREATED_DATE = 3;
	int ORGANIZATION_ID = 4;
	int CLOSED_DATE = 5;

	/**
	 * Find a record in the table by primary key.
	 *
	 * @param organizationId int.
	 *
	 * @return Organization.
	 */
	public Organization findByPK(int organizationId) throws Exception;

	/**
	 * Find a record in the table by primary key in a given data source.
	 *
	 * @param organizationId int.
	 * @param dataSourceName String.
	 *
	 * @return Organization.
	 */
	public Organization findByPK(int organizationId, String dataSourceName) throws Exception;

	/**
	 * Find a record in the table by primary key in a given data source.
	 *
	 * @param organizationId int.
	 * @param conn Connection.
	 *
	 * @return Organization.
	 */
	public Organization findByPK(int organizationId, Connection conn) throws Exception;

	/**
	 * Delete a record in the table by primary key.
	 *
	 * @param organizationId int.
	 *
	 * @return int record number deleted.
	 */
	public int deleteByPK(int organizationId) throws Exception;

	/**
	 * Find a record in the table by primary key in a given data source.
	 *
	 * @param organizationId int.
	 * @param dataSourceName String.
	 *
	 * @return int record number deleted.
	 */
	public int deleteByPK(int organizationId, String dataSourceName) throws Exception;

	/**
	 * Find a record in the table by primary key in a given data source.
	 *
	 * @param organizationId int.
	 * @param conn Connection.
	 *
	 * @return int record number deleted.
	 */
	public int deleteByPK(int organizationId, Connection conn) throws Exception;

}
