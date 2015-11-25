package com.vehicletracking.model;

import java.util.ArrayList;
import java.util.List;







import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vehicletracking.api.AddVehicleTripDetail;
/**
 * The CommonApiDAO program implements an application that
 * simply displays "CommonApiDAO Details!"
 * 
 * @Author kirankumar Bpatech
 * @Version 1.0
 */
@Repository
@Transactional
public class CommonApiDAO {
	protected final Log logger = LogFactory.getLog(CommonApiDAO.class);
	
	@PersistenceContext
	private EntityManager entityManager;
	
	List<UserAssosiation> userAssosiations = null;
	UserAssosiation userAssosiation = null;
	/**
	 * @method getUserAssosiationListByType -is used to get the user assosiations from database.
	 * @param type
	 * @return userAssosiations
	 */
	@SuppressWarnings("unchecked")
	public List<UserAssosiation> getUserAssosiationListByType(char type){
		userAssosiations = new ArrayList<UserAssosiation>();
		userAssosiations = entityManager.createQuery("from UserAssosiation where type = :type").setParameter("type", type).getResultList();
		return userAssosiations;
	}
	/**
	 * @method saveUserAssosiation -is used to persist the userAssosiation into database
	 * @param userAssosiation
	 * @return userAssosiation
	 */
	public UserAssosiation saveUserAssosiation(UserAssosiation userAssosiation){
		if(userAssosiation != null){
			entityManager.persist(userAssosiation);
			entityManager.flush();
		}
		return userAssosiation;
	}
	
}
