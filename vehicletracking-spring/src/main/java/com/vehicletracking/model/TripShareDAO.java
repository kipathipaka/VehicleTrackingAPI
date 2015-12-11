package com.vehicletracking.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class TripShareDAO {

	protected final Log logger = LogFactory.getLog(TripShareDAO.class);
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * @Method create - used to persist the TripShare object into Database.
	 * 
	 * @param TripShare
	 *            - holds the User object and Vehicle Trip Object.
	 * @return TripShare
	 */
	public TripShare createTripShare(TripShare tripShare) {
		logger.info("inside createTripShare method......");
		entityManager.persist(tripShare);
		entityManager.flush();
		return tripShare;
	}
	
	/**
	 * @method getFilteredTripSharesByUser - here we have to get Trip Share details based on User object
	 * @param vehicleTrip
	 * @return FilteredTripSharesByUser - List of vehicle trips
	 */
	
	@SuppressWarnings("unchecked")
	public List<TripShare> getTripSharesByUser(User user) {
			logger.info("@@@ inside getTripSharesByUser object method......");
			List<TripShare> tripShares = new ArrayList<TripShare>();
			tripShares = entityManager.createQuery("from TripShare  where appUserMaster = :appUserMaster").setParameter("appUserMaster", user).getResultList();
			return  tripShares;
	}
	
	@SuppressWarnings("unchecked")
	public boolean checkTripSharesByTripAndUser(User user,VehicleTrip vehicleTrip) {
			logger.info("@@@ inside getTripSharesByTripAndUser method......");
			List<TripShare> tripShares = new ArrayList<TripShare>();
			tripShares = entityManager.createQuery("from TripShare  where appUserMaster = :appUserMaster AND vehicleTripHeader = :vehicleTripHeader").setParameter("appUserMaster", user).setParameter("vehicleTripHeader", vehicleTrip).getResultList();
			return  tripShares.size() > 0 ? true : false;
	}

}
