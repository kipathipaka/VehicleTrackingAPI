package com.vehicletracking.model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @Class VehicleTripDetailDAO - is working as a Data Access Object, used to
 *        communicate with Database
 * 
 * @Author kirankumar Bpatech
 * @Version 1.0
 */
@Repository
@Transactional
public class VehicleTripDetailDAO {
	protected final Log logger = LogFactory.getLog(VehicleTripDetailDAO.class);
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * @method createVehicleTripDetail -is used to persist vehicleTripDetail
	 *         into the database
	 * @param vehicleTripDetail
	 * @return vehicletripDetail
	 */
	public VehicleTripDetail createVehicleTripDetail(
			VehicleTripDetail vehicleTripDetail) {
		entityManager.persist(vehicleTripDetail);
		entityManager.flush();
		return vehicleTripDetail;
	}

	/**
	 * @method getVehicleTripDetails -is used to get all vehicle trip header
	 *         details from the database
	 * @param vehicleTripDetail
	 * @return vehicletripDetail
	 */
	@SuppressWarnings("unchecked")
	public List<VehicleTripDetail> getVehicleTripDetails() {
		System.out
				.println("@@@ getVehicleTripDetails method in VehicleTRIPDetailDAO.java");
		return entityManager.createQuery("from VehicleTripDetail")
				.getResultList();
	}

	/**
	 * @method getOneVehicletripDetail - used to find the vehicleTripDeatilId
	 * @param vehicleTripDetailId
	 * @return vehicleTripDetail
	 */
	public VehicleTripDetail getOneVehicleTripDetail(int vehicleTripDetailId) {
		logger.info("@@@ inside getOneVehicleTripDetail method......");
		VehicleTripDetail vehicleTripDetail = null;
		vehicleTripDetail = (VehicleTripDetail) entityManager.find(
				VehicleTripDetail.class, vehicleTripDetailId);
		return vehicleTripDetail;
	}

	/**
	 * @method deleteVehicleTripDetail - is used to delete/remove the
	 *         vehicleTripDetail
	 * @param vehicleTripDetail
	 * @return vehicleTripDetail
	 */
	public VehicleTripDetail deleteVehicleTripDetail(
			VehicleTripDetail vehicleTripDetail) {
		logger.info("@@@ inside deleteVehicleTripDetail method......");
		entityManager.remove(vehicleTripDetail);
		entityManager.flush();

		return vehicleTripDetail;
	}

	/**
	 * @method upadateVehicleTripDetail - is used to update/merge the
	 *         vehicleTripDetail into the database
	 * @param vehicleTripDetail
	 * @return updatedVehicleTripDetail
	 */
	public VehicleTripDetail updateVehicleTripDetail(
			VehicleTripDetail vehicleTripDetail) {
		logger.info("@@@ inside updateVehicleTripDetail method......");
		// entityManager.getTransaction().begin();
		VehicleTripDetail updatedVehicleTripDetail = entityManager
				.merge(vehicleTripDetail);
		entityManager.flush();
		// entityManager.getTransaction().commit();
		return updatedVehicleTripDetail;
	}

	@SuppressWarnings("unchecked")
	public VehicleTripDetail getVehicleTripDetailByVehicleTrip(
			VehicleTrip vehicleTrip) {
		logger.info("@@@ inside getVehicleTripDetailByVehicleTrip method......");
		VehicleTripDetail vehicleTripDetail = null;
		List<VehicleTripDetail> vehicleTripDetails = null;

		vehicleTripDetails = entityManager
				.createQuery(
						"from VehicleTripDetail  where vehicleTrip = :vehicleTrip")
				.setParameter("vehicleTrip", vehicleTrip).getResultList();
		// vehicleTripDetail = (VehicleTripDetail)
		// entityManager.find(VehicleTripDetail.class, vehicleTripDetailId);
		if (vehicleTripDetails.size() > 0) {
			vehicleTripDetail = vehicleTripDetails.get(0);
		}
		return vehicleTripDetail;
	}

}
