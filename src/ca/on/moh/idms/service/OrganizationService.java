package ca.on.moh.idms.service;

/**
 * Automatically generated Service calss to implement business logic. 
 * If any questions, contact Davy Hu (hu_davy@yahoo.com)
 */

import gov.moh.config.vo.ValueObject;

import java.util.ArrayList;
import java.util.List;

import ca.on.moh.idms.dao.OrganizationManager;
import ca.on.moh.idms.vo.Organization;
//import com.wade.framework.db.DBConnectionManager;


public class OrganizationService{

	static final long serialVersionUID = 831019;
	/**
	 * list all the Organization from the entity table.
	 * @return java.util.List of organization.
	 */
	public List<Organization>  listAllOrganization() throws Exception{
		List<Organization> all = null;
		try{
			OrganizationManager organizationManager = new OrganizationManager();
			List<ValueObject> list = organizationManager.findAll();
			if(list != null && list.size() > 0){
				all = new ArrayList<Organization>();
				for(ValueObject vo:list){
					all.add((Organization)vo);
				}
			}
		}catch(Exception e){
			throw e;
		}
		return all;
	}

	/**
	 * list all the Organization from the entity table.
	 * @return java.util.List of organization.
	 */
	public List<Organization>  searchOrganization(Organization organization) throws Exception{
		List<Organization> all = null;
		try{
			OrganizationManager organizationManager = new OrganizationManager();
			List<ValueObject> list = organizationManager.findByObject(organization);
			if(list != null && list.size() > 0){
				all = new ArrayList<Organization>();
				for(ValueObject vo:list){
					all.add((Organization)vo);
				}
			}
		}catch(Exception e){
			throw e;
		}
		return all;
	}

	/**
	 * add Organization to the system.
	 * @return Organization created.
	 */
	public Organization addOrganization(Organization organization) throws Exception{
		try{
			//long organizationId = DBConnectionManager.getManager().getNextSeq("SEQ_ORGANIZATION_ID");
			//organization.setOrganizationId(organizationId);
			OrganizationManager organizationManager = new OrganizationManager();
			organizationManager.executeCreate(organization);
		}catch(Exception e){
			throw e;
		}
		return organization;
	}

	/**
	 * update Organization in the system.
	 * @return Organizationupdated.
	 */
	public Organization updateOrganization(Organization organization) throws Exception{
		try{
			OrganizationManager organizationManager = new OrganizationManager();
			organizationManager.update(organization);
		}catch(Exception e){
			throw e;
		}
		return organization;
	}

	/**
	 * delete Organization in the system.
	 * @return Boolean if deleting successes.
	 */
	public Boolean deleteOrganization(Organization organization) throws Exception{
		try{
			OrganizationManager organizationManager = new OrganizationManager();
			organizationManager.executeDelete(organization);
			return new Boolean(true);
		}catch(Exception e){
			throw e;
		}
	}

}

