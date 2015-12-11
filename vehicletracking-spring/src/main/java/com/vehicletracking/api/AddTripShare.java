package com.vehicletracking.api;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.vehicletracking.model.TripShare;
import com.vehicletracking.model.TripShareDAO;
import com.vehicletracking.model.User;
import com.vehicletracking.model.UserDAO;
import com.vehicletracking.model.VehicleDAO;
import com.vehicletracking.model.VehicleTrip;
import com.vehicletracking.model.VehicleTripDAO;

@Component
@Path("/sharedTrip")
public class AddTripShare {

	protected final Log logger = LogFactory.getLog(AddTripShare.class);

	@Autowired
	private TripShareDAO tripShareDAO;

	@Autowired
	private UserDAO userDao;

	@Autowired
	private VehicleTripDAO vehicleTripDAO;

	@POST
	@Transactional
	@Path("/addSharedTrip")
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveSharedTrip(@FormParam("phone_number") String phoneNumber,
			@FormParam("vehicleTripId") String vehicleTripId) {
		TripShare tripShare = new TripShare();
		VehicleTrip vehicleTrip = null;
		TripShare savedTripShare = null; User user = null;
		if (StringUtils.isNotBlank(phoneNumber)) {
			user = userDao.getUserByPhone(phoneNumber);
			if (user != null) {
				tripShare.setAppUserMaster(user);
			}
		}
		if (StringUtils.isNotBlank(vehicleTripId)) {
			vehicleTrip = new VehicleTrip();
			vehicleTrip = vehicleTripDAO.getOneVehicleTrip(Integer
					.parseInt(vehicleTripId));
			tripShare.setVehicleTripHeader(vehicleTrip);
		}
		System.out.println("Vehicle Owner Phone Number : "+vehicleTrip.getVehicleOwner().getPhone_number());
		System.out.println("Driver Phone Number : "+vehicleTrip.getDriver().getPhone_number());
		System.out.println("Customer Phone Number : "+vehicleTrip.getCustomer().getPhone_number());
		System.out.println("User passed num is :"+phoneNumber);
		if(vehicleTrip.getVehicleOwner().getPhone_number().equalsIgnoreCase(phoneNumber) || 
				vehicleTrip.getDriver().getPhone_number().equalsIgnoreCase(phoneNumber) || 
				vehicleTrip.getCustomer().getPhone_number().equalsIgnoreCase(phoneNumber)){
			logger.info("%%% It is not possible to share your trip (as Driver or Customer or owner) to you");
			return Response.status(200).entity(savedTripShare).build();
		}else {
			if(tripShareDAO.checkTripSharesByTripAndUser(user, vehicleTrip)){
				logger.info("%%% Duplicates avaliable in Trip Share table");
				return Response.status(200).entity(savedTripShare).build();
			} else {
				logger.info("%%% Duplicates are NOT avaliable in Trip Share table");
				savedTripShare = new  TripShare();
				savedTripShare = tripShareDAO.createTripShare(tripShare);
				return Response.status(200).entity(savedTripShare).build();
			}
		}
		
	}
}
