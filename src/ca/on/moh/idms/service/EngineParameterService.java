package ca.on.moh.idms.service;

/**
 * Automatically generated Service calss to implement business logic. 
 * If any questions, contact Davy Hu (hu_davy@yahoo.com)
 */

import gov.moh.config.vo.ValueObject;

import java.util.ArrayList;
import java.util.List;

import ca.on.moh.idms.dao.EngineParameterManager;
import ca.on.moh.idms.vo.EngineParameter;
//import com.wade.framework.db.DBConnectionManager;


public class EngineParameterService{

	static final long serialVersionUID = 162504;
	/**
	 * list all the EngineParameter from the entity table.
	 * @return java.util.List of engineParameter.
	 */
	public List<EngineParameter>  listAllEngineParameter() throws Exception{
		List<EngineParameter> all = null;
		try{
			EngineParameterManager engineParameterManager = new EngineParameterManager();
			List<ValueObject> list = engineParameterManager.findAll();
			if(list != null && list.size() > 0){
				all = new ArrayList<EngineParameter>();
				for(ValueObject vo:list){
					all.add((EngineParameter)vo);
				}
			}
		}catch(Exception e){
			throw e;
		}
		return all;
	}

	/**
	 * list all the EngineParameter from the entity table.
	 * @return java.util.List of engineParameter.
	 */
	public List<EngineParameter>  searchEngineParameter(EngineParameter engineParameter) throws Exception{
		List<EngineParameter> all = null;
		try{
			EngineParameterManager engineParameterManager = new EngineParameterManager();
			List<ValueObject> list = engineParameterManager.findByObject(engineParameter);
			if(list != null && list.size() > 0){
				all = new ArrayList<EngineParameter>();
				for(ValueObject vo:list){
					all.add((EngineParameter)vo);
				}
			}
		}catch(Exception e){
			throw e;
		}
		return all;
	}

	/**
	 * add EngineParameter to the system.
	 * @return EngineParameter created.
	 */
	public EngineParameter addEngineParameter(EngineParameter engineParameter) throws Exception{
		try{
			//long engineParameterId = DBConnectionManager.getManager().getNextSeq("SEQ_PARAMETER_ID");
			//engineParameter.setEngineParameterId(engineParameterId);
			EngineParameterManager engineParameterManager = new EngineParameterManager();
			engineParameterManager.executeCreate(engineParameter);
		}catch(Exception e){
			throw e;
		}
		return engineParameter;
	}

	/**
	 * update EngineParameter in the system.
	 * @return EngineParameterupdated.
	 */
	public EngineParameter updateEngineParameter(EngineParameter engineParameter) throws Exception{
		try{
			EngineParameterManager engineParameterManager = new EngineParameterManager();
			engineParameterManager.update(engineParameter);
		}catch(Exception e){
			throw e;
		}
		return engineParameter;
	}

	/**
	 * delete EngineParameter in the system.
	 * @return Boolean if deleting successes.
	 */
	public Boolean deleteEngineParameter(EngineParameter engineParameter) throws Exception{
		try{
			EngineParameterManager engineParameterManager = new EngineParameterManager();
			engineParameterManager.executeDelete(engineParameter);
			return new Boolean(true);
		}catch(Exception e){
			throw e;
		}
	}

}

