package com.vehicletracking.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder.In;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @Class DriverDAO - is working as a Data Access Object, used to communicate
 *        with Database
 * 
 * @Author Kirankumar Bpatech
 * @Version 1.0
 */
@Repository
@Transactional
public class DriverDAO {
	protected final Log logger = LogFactory.getLog(DriverDAO.class);
	@PersistenceContext
	private EntityManager entityManager;

	List<UserAssosiation> userAssosiations = null;
	UserAssosiation userAssosiation = null;

	/**
	 * @Method getUserAssosiationListByparentUserMasterId - used to get user
	 *         associations based on given user oject and type.
	 * 
	 * @param User
	 *            , type - holds the User object.
	 * @return userAssosiations
	 */
	@SuppressWarnings("unchecked")
	public List<UserAssosiation> getUserAssosiationListByparentUserMasterId(
			User parentUserMaster, char userType) {
		userAssosiations = new ArrayList<UserAssosiation>();
		// userAssosiations =
		// entityManager.createQuery("from UserAssosiation where parent_user_master = "+parentUserMasterId+" AND type = "+type+"").getResultList();
		logger.info(parentUserMaster + " : " + userType);
		userAssosiations = entityManager
				.createQuery(
						"from UserAssosiation where parent_user_master =:parentUserMaster AND type = :userType")
				.setParameter("parentUserMaster", parentUserMaster)
				.setParameter("userType", userType).getResultList();
		/*
		 * Query query = entityManager.createQuery(
		 * "from UserAssosiation where parent_user_master = :parentUserMaster AND type = :type"
		 * ); query.setParameter ("parentUserMaster", parentUserMaster);
		 * query.setParameter("type", (char)userType);
		 * logger.info(query.toString()); userAssosiations =
		 * query.getResultList();
		 */

		if (userAssosiations.size() > 0) {
			logger.info("User Associatuion size is :" + userAssosiations.size());
			return userAssosiations;
		}
		return userAssosiations;
	}

	/**
	 * @Method saveUserAssosiation - used to persist the UserAssosiation object
	 *         into Database.
	 * 
	 * @param UserAssosiation
	 *            - holds the User object.
	 * @return userAssosiation
	 */
	public UserAssosiation saveUserAssosiation(UserAssosiation userAssosiation) {
		if (userAssosiation != null) {
			entityManager.persist(userAssosiation);
			entityManager.flush();
		}
		return userAssosiation;
	}

	/**
	 * @Method checkUserAssosiation - used to check UserAssosiation object in db
	 *         which it is already exist or not.
	 * 
	 * @param UserAssosiation
	 *            - holds the User object and parent user Object.
	 * @return userAssosiation
	 */
	@SuppressWarnings("unchecked")
	public List<UserAssosiation> checkUserAssosiation(
			UserAssosiation userAssosiation) {
		if (userAssosiation != null) {
			logger.info("Going to check whether UserAssociation is already avaliable are not for same Owner to Driver.");
			userAssosiations = new ArrayList<UserAssosiation>();
			userAssosiations = entityManager
					.createQuery(
							"from UserAssosiation where parent_user_master =:parentUserMaster AND app_user_master = :appUserMaster")
					.setParameter("parentUserMaster",
							userAssosiation.getParent_user_master())
					.setParameter("appUserMaster",
							userAssosiation.getApp_user_master())
					.getResultList();

			if (userAssosiations.size() > 0) {
				logger.info("User Associatuion size is :"
						+ userAssosiations.size());
				return userAssosiations;
			}
		}
		return userAssosiations;
	}

}
