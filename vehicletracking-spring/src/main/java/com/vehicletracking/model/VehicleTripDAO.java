package com.vehicletracking.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Class VehicleTripDAO - is working as a Data Access Object, used to
 *        communicate with Database
 * 
 * @Author Kirankumar Bpatech
 * @version 1.0
 */
@Repository
@Transactional
public class VehicleTripDAO {
	protected final Log logger = LogFactory.getLog(VehicleTripDAO.class);
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * @method createVehicleTrip -persist the vehicletrip into the database
	 * @param vehicleTrip
	 * @return vehicleTrip
	 */
	public VehicleTrip createVehicleTrip(VehicleTrip vehicleTrip) {
		entityManager.persist(vehicleTrip);
		entityManager.flush();
		return vehicleTrip;
	}

	@SuppressWarnings("unchecked")
	public List<VehicleTrip> getVehicleTrips() {
		logger.info("@@@ getVehicleTrips method in VehicleTRIPDAO.java");
		return entityManager.createQuery("from VehicleTrip").getResultList();
	}

	/**
	 * @method getOneVehicleTrip -to find the vehicletripId
	 * @param vehicleTripId
	 * @return vehicleTrip
	 */
	public VehicleTrip getOneVehicleTrip(int vehicleTripId) {
		logger.info("@@@ inside getOneVehicleTrip object method......");
		VehicleTrip vehicleTrip = null;
		vehicleTrip = (VehicleTrip) entityManager.find(VehicleTrip.class,
				vehicleTripId);
		return vehicleTrip;
	}

	/**
	 * @method deleteVehicletrip - remove the vehicletrip into the database
	 * @param vehicleTrip
	 * @return vehicleTrip
	 */
	public VehicleTrip deleteVehicleTrip(VehicleTrip vehicleTrip) {
		logger.info("@@@ inside deleteUser method......");
		entityManager.remove(vehicleTrip);
		entityManager.flush();

		return vehicleTrip;
	}

	/**
	 * @method updateVehicleTrip - here we have to update /merge the vehicleTrip
	 * @param vehicleTrip
	 * @return updatedVehicleTrip
	 */
	public VehicleTrip updateVehicleTrip(VehicleTrip vehicleTrip) {
		logger.info("@@@ inside updatedVehicleTrip method......");
		// entityManager.getTransaction().begin();
		VehicleTrip updatedVehicleTrip = entityManager.merge(vehicleTrip);
		entityManager.flush();
		// entityManager.getTransaction().commit();
		return updatedVehicleTrip;
	}

	/**
	 * @method getFilteredVehicletripsByDriver - here we have to get vehicle
	 *         trip details based on Driver Phone Number
	 * @param vehicleTrip
	 * @return FilteredVehicletripsByDriver
	 */
	@SuppressWarnings("unchecked")
	public List<VehicleTrip> getFilteredVehicletripsByDriver(String driverId) {
		logger.info("inside getFilteredVehicletripsByDriver method......");
		return entityManager
				.createQuery("from VehicleTrip where DRIVER_ID = :driverId")
				.setParameter("driverId", driverId).getResultList();
	}

	/**
	 * @method getFilteredVehicletripsByCystomer - here we have to get vehicle
	 *         trip details based on CUSTOMER Phone Number
	 * @param vehicleTrip
	 * @return FilteredVehicletripsByCystomer
	 */
	@SuppressWarnings("unchecked")
	public List<VehicleTrip> getFilteredVehicletripsByCystomer(String customerId) {
		logger.info("inside getFilteredVehicletripsByCystomer method......");
		return entityManager
				.createQuery("from VehicleTrip where CUSTOMER_ID = :customerId")
				.setParameter("customerId", customerId).getResultList();
	}

	/**
	 * @method getVehicleTripByVehicleObj - here we have to get vehicle trip
	 *         details based on Vehicle.
	 * @param vehicleTrip
	 * @return vehicleTrips
	 */

	@SuppressWarnings("unchecked")
	public List<VehicleTrip> getVehicleTripByVehicleObj(String vehicleId,
			String travelStatus) {
		logger.info("@@@ inside getVehicleTripByVehicleObj object method......");
		List<VehicleTrip> vehicleTrips = new ArrayList<VehicleTrip>();
		VehicleTrip vehicleTrip = null;
		// SELECT * FROM vehicle_tracking.vehicle_trip_header where
		// (vehicle_master_id = '2' or driver_id='2' or customer_id = '2') and
		// travel_status <> 'END';
		if (travelStatus != null) {
			logger.info("Travel Status Not Null so getting not Ended trip based on TRAVEL STATUS");
			vehicleTrips = entityManager
					.createQuery(
							"from VehicleTrip where (VEHICLE_OWNER_ID = :vehicleId OR DRIVER_ID = :vehicleId OR CUSTOMER_ID = :vehicleId ) AND TRAVEL_STATUS <> :travelStatus")
					.setParameter("vehicleId", vehicleId)
					.setParameter("travelStatus", travelStatus).getResultList();
		} else {
			logger.info("Travel Status is Null so getting Object of vehicle trip header");
			vehicleTrips = entityManager
					.createQuery(
							"from VehicleTrip where (VEHICLE_OWNER_ID = :vehicleId OR DRIVER_ID = :vehicleId OR CUSTOMER_ID = :vehicleId )")
					.setParameter("vehicleId", vehicleId).getResultList();
		}
		/*
		 * if(vehicleTrips.size() > 0){ vehicleTrip = new VehicleTrip();
		 * vehicleTrip = vehicleTrips.get(0); }
		 */
		return vehicleTrips;
	}

	@SuppressWarnings("unchecked")
	public VehicleTrip trackTripByDriver(int driverId, String travelStatus) {
		List<VehicleTrip> vehicleTrips = null;
		VehicleTrip trackingVehicleTrip = null;
		logger.info("inside trackTripByDriver method......");
		vehicleTrips = entityManager
				.createQuery(
						"from VehicleTrip where DRIVER_ID = :driverId AND TRAVEL_STATUS = :travelStatus ")
				.setParameter("driverId", driverId)
				.setParameter("travelStatus", travelStatus).getResultList();
		if (vehicleTrips.size() > 0) {
			trackingVehicleTrip = vehicleTrips.get(0); // getting the first row
														// ,if in case we get
														// multiple records.
		}
		return trackingVehicleTrip;
	}

}
