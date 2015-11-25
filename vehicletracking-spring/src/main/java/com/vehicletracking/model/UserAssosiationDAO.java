package com.vehicletracking.model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
/**
 * @Class UserAssosiationDAO - is working as a Data Access Object, used to communicate with Database
 * 
 * @Author kirankumar Bpatech
 * @Version 1.0
 */
@Repository
@Transactional
public class UserAssosiationDAO {
	protected final Log logger = LogFactory.getLog(UserAssosiation.class);
	@PersistenceContext
	private EntityManager entityManager;
	/**
	 * @method getAllUserAssosiations -used to get the allUserAssosiation
	 * @return from UserAssosiation
	 */
	
	@SuppressWarnings("unchecked")
	  public List<UserAssosiation> getAllUserAssosiations() {
		logger.info("inside getAllUserAssosiations method......");
	    return entityManager.createQuery("from UserAssosiation").getResultList();
	  }
	/**
	 * @method getOneUserAssosiation -Here used to get the userAssosiationId
	 * @param userAssosiationId
	 * @return userAssosiation
	 */
	public UserAssosiation getOneUserAssosiation(int userAssosiationId) {
			logger.info("@@@ inside getOneUserAssosiation method......");
			UserAssosiation userAssosiation = null;
			userAssosiation = (UserAssosiation)entityManager.find(UserAssosiation.class, userAssosiationId);
		    return userAssosiation;
		  }
	
	@SuppressWarnings("unchecked")
	  public List<UserAssosiation> getFilteredUserAssosiations(String app_user_master_id) {
		logger.info("inside getFilteredUserAssosiations method......");
	    return entityManager.createQuery("from UserAssosiation where APP_USER_MASTER_ID = :app_user_master").setParameter("app_user_master", app_user_master_id).getResultList();
	  }
}
