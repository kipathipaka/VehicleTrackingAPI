package com.vehicletracking.model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Class VehicleDAO - is working as a Data Access Object, used to communicate
 *        with Database
 * 
 * @Author Kirankumar Bpatech
 * @Version 1.0
 */
@Repository
@Transactional
public class VehicleDAO {
	protected final Log logger = LogFactory.getLog(VehicleDAO.class);
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * @method createVehicle-user to persit the vehicle objects
	 * @param vehicle
	 * @return vehicle
	 */
	public Vehicle createVehicle(Vehicle vehicle) {
		entityManager.persist(vehicle);
		entityManager.flush();
		return vehicle;
	}

	public List<Vehicle> getAllVehicles() {
		logger.info("@@@ getAll Vehicles method in VehicleDAO.java");
		return entityManager.createQuery("from Vehicle").getResultList();
	}

	/**
	 * @method getOneVehicle - is used to find/get the vehicleId
	 * @param vehicleId
	 * @return vehicle
	 */
	public Vehicle getOneVehicle(int vehicleId) {
		logger.info("@@@ inside getOneVehicle method......");
		Vehicle vehicle = null;
		// List<User> userList = new
		// ArrayList<User>(entityManager.createQuery("from User where APP_USER_MASTER_ID = :userId").setParameter("userId",
		// userId).getResultList());
		vehicle = (Vehicle) entityManager.find(Vehicle.class, vehicleId);
		return vehicle;
	}

	/**
	 * @method deleteVehicle - is used to delete/remove the vehicle details
	 * @param vehicle
	 * @return vehicle
	 */
	public Vehicle deleteVehicle(Vehicle vehicle) {
		logger.info("@@@ inside deleteVehicle method......");
		entityManager.remove(vehicle);
		entityManager.flush();

		return vehicle;
	}

	/**
	 * @method UpdatedVehicle - is used to merge the vehicle details
	 * @param vehicle
	 * @return updatedVehicle
	 */
	public Vehicle updateVehicle(Vehicle vehicle) {
		logger.info("@@@ inside updateVehicle method......");
		// entityManager.getTransaction().begin();
		Vehicle updatedVehicle = entityManager.merge(vehicle);
		entityManager.flush();
		// entityManager.getTransaction().commit();
		return updatedVehicle;
	}

	/**
	 * @method getFilteredVehiclesByOwner - is used to filter vehicles based on
	 *         owner Id
	 * @param vehicle
	 * @return FilteredVehiclesByOwner
	 */
	@SuppressWarnings("unchecked")
	public List<Vehicle> getFilteredVehiclesByOwner(String ownerId) {
		logger.info("inside getFilteredVehiclesByOwner method......");
		return entityManager
				.createQuery("from Vehicle where APP_USER_MASTER_ID = :ownerId")
				.setParameter("ownerId", ownerId).getResultList();
	}

}
