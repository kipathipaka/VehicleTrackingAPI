package com.vehicletracking.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @Class UserDAO - is working as a Data Access Object, used to communicate with
 *        Database
 * 
 * @Author Kirankumar Bpatech
 * @Version 1.0
 */
@Repository
@Transactional
public class UserDAO {
	protected final Log logger = LogFactory.getLog(UserDAO.class);
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * @Method create - used to persist the User object into Database.
	 * 
	 * @param User
	 *            - holds the User object.
	 * @return User
	 */
	public User createUser(User user) {
		logger.info("inside create method......");
		entityManager.persist(user);
		entityManager.flush();
		return user;
	}

	/**
	 * @method getAll to get All users in table.
	 * @param user
	 * @return userList
	 */
	@SuppressWarnings("unchecked")
	public List<User> getAll() {
		logger.info("inside getall method......");
		return entityManager.createQuery("from User").getResultList();
	}

	/**
	 * @method getOneUser-is used find the userid details
	 * 
	 * @param userId
	 * @return user
	 */
	public User getOneUser(int userId) {
		logger.info("@@@ inside getOneUser method......");
		User user = null;
		// List<User> userList = new
		// ArrayList<User>(entityManager.createQuery("from User where APP_USER_MASTER_ID = :userId").setParameter("userId",
		// userId).getResultList());
		user = (User) entityManager.find(User.class, userId);
		return user;
	}

	/**
	 * @method getUserByPhone -create Qurey from the phone_number
	 * 
	 * @param phoneNumber
	 * 
	 * @return user
	 */
	public User getUserByPhone(String phoneNumber) {
		logger.info("@@@ inside getUserByPhone method......");
		User user = null;
		List<User> usersList = null;
		try {
			usersList = entityManager
					.createQuery("from User where phone_number = :phone_number")
					.setParameter("phone_number", phoneNumber).getResultList();
			if (usersList.size() > 0) {
				return usersList.get(0);
			} else {
				logger.info("No User Object avalible in User DAO");
				user = null;
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	/**
	 * @method deleteUser - is used to remove/delete the user data.
	 * 
	 * @param user
	 * @return user
	 */
	public User deleteUser(User user) {
		logger.info("@@@ inside deleteUser method......");
		entityManager.remove(user);
		entityManager.flush();

		return user;
	}

	/**
	 * @method UpdateUser-used to update/merge the user details and craete the
	 *         object to the user
	 * @param user
	 * @return updatedUser
	 */
	public User updateUser(User user) {
		logger.info("@@@ inside updateUser method......");
		// entityManager.getTransaction().begin();
		User updatedUser = entityManager.merge(user);
		entityManager.flush();
		// entityManager.getTransaction().commit();
		return updatedUser;
	}
}
