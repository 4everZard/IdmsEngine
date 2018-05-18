package ca.on.moh.idms.service;

/**
 * Automatically generated Service calss to implement business logic. 
 * If any questions, contact Davy Hu (hu_davy@yahoo.com)
 */

import gov.moh.config.vo.ValueObject;

import java.util.ArrayList;
import java.util.List;

import ca.on.moh.idms.dao.ClmhistManager;
import ca.on.moh.idms.vo.Clmhist;
//import com.wade.framework.db.DBConnectionManager;


public class ClmhistService{

	static final long serialVersionUID = 6825275;
	/**
	 * list all the Clmhist from the entity table.
	 * @return java.util.List of clmhist.
	 */
	public List<Clmhist>  listAllClmhist() throws Exception{
		List<Clmhist> all = null;
		try{
			ClmhistManager clmhistManager = new ClmhistManager();
			List<ValueObject> list = clmhistManager.findAll();
			if(list != null && list.size() > 0){
				all = new ArrayList<Clmhist>();
				for(ValueObject vo:list){
					all.add((Clmhist)vo);
				}
			}
		}catch(Exception e){
			throw e;
		}
		return all;
	}

	/**
	 * list all the Clmhist from the entity table.
	 * @return java.util.List of clmhist.
	 */
	public List<Clmhist>  searchClmhist(Clmhist clmhist) throws Exception{
		List<Clmhist> all = null;
		try{
			ClmhistManager clmhistManager = new ClmhistManager();
			List<ValueObject> list = clmhistManager.findByObject(clmhist);
			if(list != null && list.size() > 0){
				all = new ArrayList<Clmhist>();
				for(ValueObject vo:list){
					all.add((Clmhist)vo);
				}
			}
		}catch(Exception e){
			throw e;
		}
		return all;
	}

	/**
	 * add Clmhist to the system.
	 * @return Clmhist created.
	 */
	public Clmhist addClmhist(Clmhist clmhist) throws Exception{
		try{
			//long clmhistId = DBConnectionManager.getManager().getNextSeq("SEQ_CLAIM_ID");
			//clmhist.setClmhistId(clmhistId);
			ClmhistManager clmhistManager = new ClmhistManager();
			clmhistManager.executeCreate(clmhist);
		}catch(Exception e){
			throw e;
		}
		return clmhist;
	}

	/**
	 * update Clmhist in the system.
	 * @return Clmhistupdated.
	 */
	public Clmhist updateClmhist(Clmhist clmhist) throws Exception{
		try{
			ClmhistManager clmhistManager = new ClmhistManager();
			clmhistManager.update(clmhist);
		}catch(Exception e){
			throw e;
		}
		return clmhist;
	}

	/**
	 * delete Clmhist in the system.
	 * @return Boolean if deleting successes.
	 */
	public Boolean deleteClmhist(Clmhist clmhist) throws Exception{
		try{
			ClmhistManager clmhistManager = new ClmhistManager();
			clmhistManager.executeDelete(clmhist);
			return new Boolean(true);
		}catch(Exception e){
			throw e;
		}
	}

}

