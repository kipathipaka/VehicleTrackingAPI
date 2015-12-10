package com.vehicletracking.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.vehicletracking.model.User;
import com.vehicletracking.model.UserAssosiation;
import com.vehicletracking.model.UserAssosiationDAO;
import com.vehicletracking.model.UserDAO;
import com.vehicletracking.model.Vehicle;
import com.vehicletracking.model.VehicleDAO;
import com.vehicletracking.model.VehicleTrip;
import com.vehicletracking.model.VehicleTripDAO;
import com.vehicletracking.model.VehicleTripDetail;
import com.vehicletracking.model.VehicleTripDetailDAO;
/**
 * class:AddVehicleTrip...
 * The user program implements an application that
 * simply displays "AddVehicleTrip Details!"
 * 
 * @Author Kirankumar Bpatech
 * @version 1.0
 */
@Component
@Path("/trip")
public class AddVehicleTrip {
	
	protected final Log logger = LogFactory.getLog(AddVehicleTrip.class);
	
	@Autowired
	private VehicleTripDAO vehicleTripDAO;
	
	@Autowired
	private VehicleDAO vehicleDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private UserAssosiationDAO userAssosiationDAO;
	
	@Autowired
	private VehicleTripDetailDAO vehicleTripDetailDAO;
	
	/**
	 * @method getVehicleTripsList -used get the vehicleTrip List and return the response
	 * @return Response/vehicleTripsList
	 */
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getVehicleTripsList(){
		logger.info("@@@ In getVehicleTripsList Method in AddVehilceTrip.java");
		ArrayList<VehicleTrip> vehicleTripsList = (ArrayList<VehicleTrip>) vehicleTripDAO.getVehicleTrips();
		
		return  Response.status(200).entity(vehicleTripsList).build();
	}
	/**
	 * @method getOneVehicleTrip - is used to get the VehicleTripId and vehicleTrip object created
	 * @param vehicleTripId
	 * @return Response/vehicleTrip
	 */ 
	@GET
	@Transactional
	@Path("{vehicleTripId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOneVehicleTrip(@PathParam("vehicleTripId")int vehicleTripId){
		logger.info("@@@ getOneVehicleTrip Method...");
		VehicleTrip vehicleTrip = (VehicleTrip) vehicleTripDAO.getOneVehicleTrip(vehicleTripId);		
		return  Response.status(200).entity(vehicleTrip).build();
	}
	/**
	 * @method saveVehicleTrip - is used to create /save the vehicleTrip details
	 * @param truckNumber
	 * @param source_station
	 * @param destination_station
	 * @param driverPhoneNumber
	 * @param vehicleOwnerPhoneNumber
	 * @param customerComanyName
	 * @param customerName
	 * @param customerPhoneNumber
	 * @return savedVehicleTripDetail
	 */
	@SuppressWarnings("unused")
	@POST
	@Transactional
	@Path("/addTrip")
	@Produces(MediaType.APPLICATION_JSON)
	public  Response saveVehicleTrip(
			@FormParam("vehicle_registration_number") String truckNumber,
			@FormParam("destination_station") String destination_station,
			@FormParam("driver_phone_number") String driverPhoneNumber,
			@FormParam("vehicle_owner_phone_number") String vehicleOwnerPhoneNumber,
			@FormParam("customer_company_name") String customerComanyName,
			@FormParam("customer_name") String customerName,
			@FormParam("customer_phone_number") String customerPhoneNumber
			){
		
		VehicleTrip vehicleTrip = new VehicleTrip();
		VehicleTripDetail vehicleTripDetail = null;
		//SimpleDateFormat dbDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); //Ex: 2015-09-28 10:30:30
		Calendar currentDate = Calendar.getInstance();
		currentDate.setTimeZone(TimeZone.getTimeZone("IST"));
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss"); // 2015-10-14 00:00:00
		Date currentDateTime = null;
		try {
			currentDateTime = (Date) formatter.parse(formatter
					.format(currentDate.getTime()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Vehicle vehicle = null;
		Vehicle savedVehicleObj = null;
		User driverUser = null; User customerUser = new User(); User vehicleOwnerUser = new User();
		if (truckNumber != null && !StringUtils.isEmpty(vehicleOwnerPhoneNumber)) {
			logger.info("Got Truck Number "+truckNumber);
			
			vehicleOwnerUser = userDAO.getUserByPhoneWithStatus(vehicleOwnerPhoneNumber);
			vehicle = vehicleDAO.getVehicleByUserAndTruckNumber(vehicleOwnerUser, truckNumber);
			
			if(vehicle != null){
				logger.info("Vehicle Owner User is :"+vehicleOwnerUser.getName());
				logger.info("Vehicle Object Reg Num is :"+vehicle.getVehicle_registration_number());
				vehicleTrip.setVehicleOwner(vehicle.getUser());
				vehicleTrip.setVehicle(vehicle);
			}else{
				vehicle = new Vehicle();
				vehicle.setVehicle_registration_number(truckNumber);
				vehicle.setIs_active('Y');
				vehicle.setCreated_by(vehicleOwnerUser.getApp_user_master_id());
				vehicle.setCreated_on(currentDateTime);
				vehicle.setUser(vehicleOwnerUser);
				savedVehicleObj = vehicleDAO.createVehicle(vehicle);
				vehicleTrip.setVehicle(savedVehicleObj);
				vehicleTrip.setVehicleOwner(savedVehicleObj.getUser());
			}
			
		}
		
		if(!StringUtils.isEmpty(driverPhoneNumber)){
			driverUser = new User();
			driverUser = userDAO.getUserByPhone(driverPhoneNumber);
			if(driverUser != null){
				logger.info("Got Driver obj "+driverUser.getPhone_number()+" : "+driverUser.getName());
				vehicleTrip.setDriver(driverUser);
			}else{
				User driver = new User();
				driver.setPhone_number(driverPhoneNumber);
				driver.setIs_active('N');
				driver.setApp_download_status('N');;
				User savedDriverUser = userDAO.createUser(driver);
				logger.info("No Driver obj avaliable sio inserting new Driver :"+driver.getPhone_number()+" : "+driver.getName());
				vehicleTrip.setDriver(savedDriverUser);
			}
		}
		
		if(!StringUtils.isEmpty(customerPhoneNumber)){
			customerUser = new User();
			customerUser = userDAO.getUserByPhone(customerPhoneNumber);
			if(customerUser != null){
				if(customerUser.getName() == null && customerUser.getCompany_name() == null) {
				customerUser.setName(customerName);
				customerUser.setCompany_name(customerComanyName);
				customerUser = userDAO.updateUser(customerUser);
				}
				logger.info("Got Customer obj "+customerUser.getPhone_number()+" : "+customerUser.getName());
				vehicleTrip.setCustomer(customerUser);
			}else{
				User customer = new User();
				customer.setPhone_number(customerPhoneNumber);
				customer.setCompany_name(customerComanyName);
				customer.setName(customerName);
				customer.setIs_active('N');
				customer.setApp_download_status('N');;
				User savedCustomerUser = userDAO.createUser(customer);
				logger.info("No Customer obj avaliable sio inserting new Customer :"+customer.getPhone_number()+" : "+customer.getName());
				vehicleTrip.setCustomer(savedCustomerUser);
			}
			
		}
		
		/*if (!StringUtils.isEmpty(source_station)) {
			vehicleTrip.setSource_station(source_station);
		}*/
		if (!StringUtils.isEmpty(destination_station)) {
			vehicleTrip.setDestination_station(destination_station);
		}
		
		vehicleTrip.setCreated_by(vehicleOwnerUser.getApp_user_master_id());
		vehicleTrip.setCreated_on(currentDateTime);
		vehicleTrip.setTravel_status("YST"); // setting initially status as "TET TO START" 
		vehicleTrip.setTravel_start_date_time(currentDateTime);
		logger.info("@@@ Before Insert Vehicle Trip");
		VehicleTrip savedVehicleTrip = vehicleTripDAO.createVehicleTrip(vehicleTrip);
		logger.info("@@@ After Insert Vehicle Trip");
		
		/*if(savedVehicleTrip != null){
			vehicleTripDetail = new VehicleTripDetail();
			//vehicleTripDetail.setLocation(source_station); // Making Comment and later we will set current city location Here
			vehicleTripDetail.setVehicleTrip(savedVehicleTrip);
			vehicleTripDetailDAO.createVehicleTripDetail(vehicleTripDetail);
			logger.info("Vehicle trip detail id is:"+vehicleTripDetail.getVehicle_trip_header_detail_id());
		}*/
		return Response.status(200).entity(savedVehicleTrip).build();
	}
	/**
	 * @method deleteVehicleTrip -used to delete/remove the tripId Details
	 * @param vehicleTripId
	 * @return Response/deletedVehicleTrip
	 */
	@DELETE
	@Transactional
	@Path("{vehicleTripId}")
	@Produces(MediaType.APPLICATION_JSON)
	public  Response deleteVehicleTrip(@PathParam("vehicleTripId") int vehicleTripId){
		VehicleTrip vehicleTrip = (VehicleTrip) vehicleTripDAO.getOneVehicleTrip(vehicleTripId);
		VehicleTrip deletedVehicleTrip = vehicleTripDAO.deleteVehicleTrip(vehicleTrip);
		return Response.status(200).entity(deletedVehicleTrip).build();
	}
	/**
	 * @method UpadateVehicleTrip -Is used to update the trip details and create the vehicleTrip Object
	 * @param vehicleTripId
	 * @param truckNumber
	 * @param source_station
	 * @param destination_station
	 * @param driverPhoneNumber
	 * @param vehicleOwnerPhoneNumber
	 * @param customerComanyName
	 * @param customerName
	 * @param customerPhoneNumber
	 * @return Response/updatedVehicleTrip
	 */
	@PUT
	@Transactional
	@Path("/startTrip")
	@Produces(MediaType.APPLICATION_JSON)
	public  Response UpdateVehicleTripStarted(
			@FormParam("vehicle_trip_header_id") int vehicleTripHeaderId){
		VehicleTrip vehicleTrip = null; VehicleTrip updatedVehicleTrip = null;
		if(StringUtils.isNotEmpty(String.valueOf(vehicleTripHeaderId))){
			logger.info("vehicle Trip Header Id is : "+vehicleTripHeaderId);
			vehicleTrip = (VehicleTrip) vehicleTripDAO.getOneVehicleTrip(vehicleTripHeaderId);
		}
		if(vehicleTrip != null){
			logger.info("In Vehicle trip db : "+vehicleTrip.getVehicle_trip_header_id());
			vehicleTrip.setTravel_status("STR"); // setting initially status as "STARTED"
			try{
				//Date last_sync_dateObj = dbDateFormat.parse(new Date());
					Calendar currentDate = Calendar.getInstance();
					currentDate.setTimeZone(TimeZone.getTimeZone("IST"));
					SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss"); // 2015-10-14 00:00:00
					Date currentDateTime = (Date) formatter.parse(formatter
							.format(currentDate.getTime()));
					vehicleTrip.setTravel_start_date_time(currentDateTime);
				}catch(Exception pe){pe.printStackTrace();}
			
		updatedVehicleTrip = vehicleTripDAO.updateVehicleTrip(vehicleTrip);
		}
		return Response.status(200).entity(updatedVehicleTrip).build();
	}
	/**
	 *@method getMyTrips - is used to get the trip details and create the user object and that user object calling the userDAO object
	 * @param phoneNumber
	 * @return Response
	 */
	@PUT
	@Transactional
	@Path("/endTrip")
	@Produces(MediaType.APPLICATION_JSON)
	public  Response UpdateVehicleTripEnded(
			@FormParam("vehicle_trip_header_id") int vehicleTripHeaderId){
		VehicleTrip vehicleTrip = null; VehicleTrip updatedVehicleTrip = null;
		if(StringUtils.isNotEmpty(String.valueOf(vehicleTripHeaderId))){
			vehicleTrip = (VehicleTrip) vehicleTripDAO.getOneVehicleTrip(vehicleTripHeaderId);
		}
		if(vehicleTrip != null){
			vehicleTrip.setTravel_status("END"); // setting initially status as "ENDED"
			try{
				//Date last_sync_dateObj = dbDateFormat.parse(new Date());
					Calendar currentDate = Calendar.getInstance();
					currentDate.setTimeZone(TimeZone.getTimeZone("IST"));
					SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss"); // 2015-10-14 00:00:00
					Date currentDateTime = (Date) formatter.parse(formatter
							.format(currentDate.getTime()));
					vehicleTrip.setTravel_end_date_time(currentDateTime);
				}catch(Exception pe){pe.printStackTrace();}
		updatedVehicleTrip = vehicleTripDAO.updateVehicleTrip(vehicleTrip);
		}
		return Response.status(200).entity(updatedVehicleTrip).build();
	}
	/**
	 * @method getUserDetailsInfo - used to get the UserAssosiationList and return the response
	 * @param phoneNumber
	 * @return
	 */
	@GET
	@Transactional
	@Path("/mytrips/{vehicle_owner_phone_number}")
	@Produces(MediaType.APPLICATION_JSON)
	public  Response getMyTrips(@PathParam("vehicle_owner_phone_number") String phoneNumber){
		User tripUser = null; List<VehicleTrip> vehicleTripsList = null;
		if(!StringUtils.isEmpty(phoneNumber)){
			tripUser = new User();
			tripUser = userDAO.getUserByPhone(phoneNumber);
		}
		if(tripUser != null){
		String travel_Status = "END"; 
		vehicleTripsList = vehicleTripDAO.getVehicleTripByVehicleObj(String.valueOf(tripUser.getApp_user_master_id()),travel_Status);
		logger.info("VehicleTrip List size is :"+vehicleTripsList.size());
		}
		
		
		/*if(!StringUtils.isEmpty(tripUser)){
			logger.info("User Id is :"+tripUser.getApp_user_master_id()+" Name is :"+tripUser.getName());
			vehicleTripsList = new ArrayList<VehicleTrip>();
			vehicleTripsList = vehicleTripDAO.getFilteredVehicletripsByDriver(String.valueOf(tripUser.getApp_user_master_id()));
			logger.info("Vehicle trips Driver size is :"+vehicleTripsList.size());
			if(vehicleTripsList.size() == 0){
				vehicleTripsList = vehicleTripDAO.getFilteredVehicletripsByCystomer(String.valueOf(tripUser.getApp_user_master_id()));
				logger.info("Vehicle trips Customer size is :"+vehicleTripsList.size());
				if(vehicleTripsList.size() == 0){
					vehiclesList = new ArrayList<Vehicle>();
					vehiclesList = vehicleDAO.getFilteredVehiclesByOwner(String.valueOf(tripUser.getApp_user_master_id()));
					logger.info("VehicleList Owner size is :"+vehiclesList.size());
					for(Vehicle vehicle : vehiclesList){
						logger.info("Vehicle Id is :"+vehicle.getVehicle_master_id());
						String travel_Status = "END"; 
						vehicleTrip = vehicleTripDAO.getVehicleTripByVehicleObj(String.valueOf(vehicle.getVehicle_master_id()),travel_Status);
						logger.info("VehicleTrip  is :"+vehicleTrip);
						if(!StringUtils.isEmpty(vehicleTrip)){
							logger.info("get Vehicle trip header id is :"+vehicleTrip.getVehicle_trip_header_id());
							vehicleTripsList.add(vehicleTrip);
						}
					}
					
				}
			}
		}*/
		return Response.status(200).entity(vehicleTripsList).build();
	}
	
	@GET
	@Transactional
	@Path("/mytrip/{vehicle_trip_header_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public  Response getMyTripDetailById(@PathParam("vehicle_trip_header_id") String vehicleTripHeaderId){
		 List<VehicleTrip> vehicleTripsList = null;
		/*if(!StringUtils.isEmpty(vehicleTripHeaderId)){
			tripUser = new User();
			tripUser = userDAO.getOneUser(Integer.parseInt(vehicleTripHeaderId));
		}*/
		if(!StringUtils.isEmpty(vehicleTripHeaderId)){
		String travel_Status = null; // sending travel status as "null" to get required vehicle trip header object data 
		vehicleTripsList = vehicleTripDAO.getVehicleTripByVehicleObj(vehicleTripHeaderId,travel_Status);
		logger.info("VehicleTrip List size is :"+vehicleTripsList.size());
		}
		return Response.status(200).entity(vehicleTripsList).build();
	}
	
	
	
	/*@POST
	@Transactional
	@Path("/userdetailinfo")
	@Produces(MediaType.APPLICATION_JSON)
	public  Response getUserDetailInfo(@FormParam("vehicle_owner_phone_number") String phoneNumber){
		User tripUser = null; VehicleTrip vehicleTrip = null; List<VehicleTrip> vehicleTripsList = null;
		List<UserAssosiation> userAssosiationsList = null; List list = null;
		List<Vehicle> vehiclesList = null;
		if(!StringUtils.isEmpty(phoneNumber)){
			tripUser = new User();
			tripUser = userDAO.getUserByPhone(phoneNumber);
			userAssosiationsList = userAssosiationDAO.getFilteredUserAssosiations(String.valueOf(tripUser.getApp_user_master_id()));
			for(UserAssosiation userAssosiation : userAssosiationsList){
				logger.info("Parent User Id id :"+userAssosiation.getParent_user_master().getApp_user_master_id());
			
		
		//if(!StringUtils.isEmpty(tripUser)){
			logger.info("User Id is :"+userAssosiation.getParent_user_master().getApp_user_master_id()+" Name is :"+userAssosiation.getParent_user_master().getCompany_name());
			vehicleTripsList = new ArrayList<VehicleTrip>();
			vehicleTripsList = vehicleTripDAO.getFilteredVehicletripsByDriver(String.valueOf(userAssosiation.getParent_user_master().getApp_user_master_id()));
			logger.info("Vehicle trips Driver size is :"+vehicleTripsList.size());
			if(vehicleTripsList.size() == 0){
				vehicleTripsList = vehicleTripDAO.getFilteredVehicletripsByCystomer(String.valueOf(userAssosiation.getParent_user_master().getApp_user_master_id()));
				logger.info("Vehicle trips Customer size is :"+vehicleTripsList.size());
				if(vehicleTripsList.size() == 0){
					vehiclesList = new ArrayList<Vehicle>();
					vehiclesList = vehicleDAO.getFilteredVehiclesByOwner(String.valueOf(userAssosiation.getParent_user_master().getApp_user_master_id()));
					logger.info("VehicleList Owner size is :"+vehiclesList.size());
					for(Vehicle vehicle : vehiclesList){
						logger.info("Vehicle Id is :"+vehicle.getVehicle_master_id());
						String travel_Status = "END"; 
						vehicleTrip = vehicleTripDAO.getVehicleTripByVehicleObj(String.valueOf(vehicle.getVehicle_master_id()),travel_Status);
						logger.info("VehicleTrip  is :"+vehicleTrip);
						if(!StringUtils.isEmpty(vehicleTrip)){
							logger.info("get Vehicle trip header id is :"+vehicleTrip.getVehicle_trip_header_id());
							vehicleTripsList.add(vehicleTrip);
						}
					}
					
				}
			}
			list =  new ArrayList();
			list.add(vehicleTripsList);
			list.add(userAssosiation);
		}
		}
		
		return Response.status(200).entity(list).build();
		return Response.status(200).entity(vehicleTripsList).build();
	}*/
	
	@POST
	@Transactional
	@Path("/trackTrip")
	@Produces(MediaType.APPLICATION_JSON)
	public  Response saveVehicleTripDetail(
			@FormParam("driver_phone_number") String driverPhoneNumber,
			@FormParam("location") String location,
			@FormParam("latitude") String  latitude,
			@FormParam("longitude") String longitude,
			@FormParam("fullAddress") String fulladdress
			){
		
		logger.info("inside driver tracking update method..");
		Calendar currentDate = Calendar.getInstance();
		currentDate.setTimeZone(TimeZone.getTimeZone("IST"));
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss"); // 2015-10-14 00:00:00
		Date currentDateTime = null; User updatedUser = null;
		try {
			currentDateTime = (Date) formatter.parse(formatter
					.format(currentDate.getTime()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		User driverObj = null;
		if(StringUtils.isNotBlank(driverPhoneNumber) || StringUtils.isNotEmpty(driverPhoneNumber)){
			driverObj =  userDAO.getUserByPhone(driverPhoneNumber);
			logger.info("driver name:"+driverObj.getName());
			logger.info("ping coming from."+driverObj.getPhone_number());
			logger.info("ping address."+fulladdress);
			if(driverObj != null){
				List<UserAssosiation> filteredUserAssosiations = userAssosiationDAO.getFilteredUserAssosiations(String.valueOf(driverObj.getApp_user_master_id()));
				if(filteredUserAssosiations.size() > 0){
					logger.info("Driver Obje is avaliable so updating info");
					driverObj.setLocation(location);
					driverObj.setLatitude(latitude);
					driverObj.setLongitude(longitude);
					driverObj.setLast_sync_date_time(currentDateTime);
					driverObj.setFullAddress(fulladdress);
					updatedUser =  userDAO.updateUser(driverObj);
					logger.info("Updated Location info in user is :"+updatedUser.getLocation());	
				} else {
					logger.info("Driver Object  is NOT avaliable so passing old object data");
					return Response.status(200).entity(driverObj).build();
				}
			}
		}
		
		/*if(driverPhoneNumber != null){
			trackingVehicleTrip = vehicleTripDAO.trackTripByDriver(userDAO.getUserByPhone(driverPhoneNumber).getApp_user_master_id(),"STR"); // kept statically as "STR" only trip started vehicle details of driver to track.
		}
		
		if(trackingVehicleTrip != null){
			//vehicleTripDetail = new VehicleTripDetail();
			trackingVehicleTrip.setLocation(location); trackingVehicleTrip.setLatitude(latitude);
			trackingVehicleTrip.setLongitude(longitude); trackingVehicleTrip.setLast_sync_date_time(currentDateTime);
			updatedVehicleTrip = vehicleTripDAO.updateVehicleTrip(trackingVehicleTrip);
			if(updatedVehicleTrip != null) {
			//vehicleTripDetail = vehicleTripDetailDAO.getVehicleTripDetailByVehicleTrip(trackingVehicleTrip);
			//System.out.println("Vehicle rtip detail obj is :"+vehicleTripDetail);
			vehicleTripDetail = new VehicleTripDetail();
			vehicleTripDetail.setVehicleTrip(updatedVehicleTrip);
			//if(vehicleTripDetail != null){
				if (location != null) {
					System.out.println("Setting Location is :"+location);
					vehicleTripDetail.setLocation(location);
				}
				if (latitude != null) {
					System.out.println("Setting latitude is :"+latitude);
					vehicleTripDetail.setLatitude(latitude);
				}
				if (longitude != null) {
					System.out.println("Setting longitude is :"+longitude);
					vehicleTripDetail.setLongitude(longitude);
				}
				
				try{
					vehicleTripDetail.setLast_sync_date_time(currentDateTime);
					}catch(Exception pe){pe.printStackTrace();}
					
				updatedVehicleTripDetail = vehicleTripDetailDAO.createVehicleTripDetail(vehicleTripDetail);
			}
		//	}
		}*/
		
		return Response.status(200).entity(updatedUser).build();
	}
	
}
