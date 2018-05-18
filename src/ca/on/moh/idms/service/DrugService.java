package ca.on.moh.idms.service;

/**
 * Automatically generated Service calss to implement business logic. 
 * If any questions, contact Davy Hu (hu_davy@yahoo.com)
 */

import gov.moh.config.vo.ValueObject;

import java.util.ArrayList;
import java.util.List;

import ca.on.moh.idms.dao.DrugManager;
import ca.on.moh.idms.vo.Drug;
//import com.wade.framework.db.DBConnectionManager;


public class DrugService{

	static final long serialVersionUID = 42242;
	/**
	 * list all the Drug from the entity table.
	 * @return java.util.List of drug.
	 */
	public List<Drug>  listAllDrug() throws Exception{
		List<Drug> all = null;
		try{
			DrugManager drugManager = new DrugManager();
			List<ValueObject> list = drugManager.findAll();
			if(list != null && list.size() > 0){
				all = new ArrayList<Drug>();
				for(ValueObject vo:list){
					all.add((Drug)vo);
				}
			}
		}catch(Exception e){
			throw e;
		}
		return all;
	}

	/**
	 * list all the Drug from the entity table.
	 * @return java.util.List of drug.
	 */
	public List<Drug>  searchDrug(Drug drug) throws Exception{
		List<Drug> all = null;
		try{
			DrugManager drugManager = new DrugManager();
			List<ValueObject> list = drugManager.findByObject(drug);
			if(list != null && list.size() > 0){
				all = new ArrayList<Drug>();
				for(ValueObject vo:list){
					all.add((Drug)vo);
				}
			}
		}catch(Exception e){
			throw e;
		}
		return all;
	}

	/**
	 * add Drug to the system.
	 * @return Drug created.
	 */
	public Drug addDrug(Drug drug) throws Exception{
		try{
			//long drugId = DBConnectionManager.getManager().getNextSeq("SEQ_DRUG_ID");
			//drug.setDrugId(drugId);
			DrugManager drugManager = new DrugManager();
			drugManager.executeCreate(drug);
		}catch(Exception e){
			throw e;
		}
		return drug;
	}

	/**
	 * update Drug in the system.
	 * @return Drugupdated.
	 */
	public Drug updateDrug(Drug drug) throws Exception{
		try{
			DrugManager drugManager = new DrugManager();
			drugManager.update(drug);
		}catch(Exception e){
			throw e;
		}
		return drug;
	}

	/**
	 * delete Drug in the system.
	 * @return Boolean if deleting successes.
	 */
	public Boolean deleteDrug(Drug drug) throws Exception{
		try{
			DrugManager drugManager = new DrugManager();
			drugManager.executeDelete(drug);
			return new Boolean(true);
		}catch(Exception e){
			throw e;
		}
	}

}

