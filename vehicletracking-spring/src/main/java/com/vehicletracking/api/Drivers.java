package com.vehicletracking.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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

import com.vehicletracking.model.DriverDAO;
import com.vehicletracking.model.User;
import com.vehicletracking.model.UserAssosiation;
import com.vehicletracking.model.UserDAO;

@Component
@Path("/drivers")
public class Drivers {
	
	protected final Log logger = LogFactory.getLog(Drivers.class);
	
	@Autowired
	private DriverDAO driverDAO;

	@Autowired
	private UserDAO userDAO;

	List<UserAssosiation> userAssosiations = null;

	@GET
	@Transactional
	@Path("{vehicle_owner_phone_number}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDriversList(
			@PathParam("vehicle_owner_phone_number") String vehicleOwnerPhoneNumber) {
		User vehicleOwnerUser = null;
		List<UserAssosiation> userAssosiations = null;
		try {
			if (StringUtils.isNotEmpty(vehicleOwnerPhoneNumber)) {
				vehicleOwnerUser = new User();
				vehicleOwnerUser = userDAO
						.getUserByPhone(vehicleOwnerPhoneNumber);
				logger.info("vehicleOwnerUser is : "+vehicleOwnerUser);
				
				if (vehicleOwnerUser != null) {
					logger.info("Vehicle Owner User is :"
							+ vehicleOwnerUser.getName());
					char userType = 'D'; // setting statically because we need to get all Drivers list based on Vehicle Owner Object in user association
					userAssosiations = new ArrayList<UserAssosiation>();
					userAssosiations = driverDAO
							.getUserAssosiationListByparentUserMasterId(vehicleOwnerUser,userType);
					if (userAssosiations.size() > 0) {
						return Response.status(200).entity(userAssosiations)
								.build();
					} else {
						userAssosiations = null;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(userAssosiations).build();
	}
	
	
	@POST
	@Transactional
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/addDriverPhone")
	public Response createAssosiation(
			@FormParam("driver_phone_number") String driverPhoneNumber,
			@FormParam("vehicle_owner_phone_number") String vehicleOwnerPhoneNumber,
			@FormParam("location") String location,
			@FormParam("latitude") String  latitude,
			@FormParam("longitude") String longitude){
		User vehicleOwnerUser = null;
		UserAssosiation userAssosiation = null; UserAssosiation savedUserAssosiation = null;
		List<UserAssosiation> userAssosiations = null;
		User driverUser = null;
		try{
		if(!StringUtils.isEmpty(vehicleOwnerPhoneNumber)){
			vehicleOwnerUser = new User();
			userAssosiation = new UserAssosiation();
			vehicleOwnerUser = userDAO.getUserByPhone(vehicleOwnerPhoneNumber);
			if(vehicleOwnerUser != null){
				logger.info("Vehicle Owner User is :"+vehicleOwnerUser.getName());
				userAssosiation.setParent_user_master(vehicleOwnerUser);
				}
			
		}
		if(!StringUtils.isEmpty(driverPhoneNumber)){
			driverUser = new User();
			User savedDriverUser = null;
			driverUser = userDAO.getUserByPhone(driverPhoneNumber);
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
			if( driverUser != null){
				logger.info("Got Driver obj "+driverUser.getPhone_number()+" : "+driverUser.getName());
				/*driverUser.setIs_active('Y');
				driverUser.setApp_download_status('Y');
				if(location != null){
					driverUser.setLocation(location);
					driverUser.setLast_sync_date_time(currentDateTime);
				}
				if(latitude != null){
					driverUser.setLatitude(latitude);
				}
				if(longitude != null){
					driverUser.setLongitude(longitude);
				}
				savedDriverUser = userDAO.updateUser(driverUser);*/
				userAssosiation.setApp_user_master(driverUser);
			}else{
				User driver = new User();
				driver.setPhone_number(driverPhoneNumber);
				driver.setIs_active('N');
				//driver.setLast_sync_date_time(currentDateTime);
				driver.setApp_download_status('N');
				savedDriverUser = userDAO.createUser(driver);
				logger.info("No Driver obj avaliable sio inserting new Driver :"+driver.getPhone_number()+" : "+driver.getName());
				userAssosiation.setApp_user_master(savedDriverUser);
			}
				userAssosiation.setType('D');
		}
		
		userAssosiations = driverDAO.checkUserAssosiation(userAssosiation);
		if(userAssosiations.size() <= 0 || userAssosiations.equals(null)){
			logger.info("No User Association avaliable , so creating new association to Driver-Owner ...");
			savedUserAssosiation = driverDAO.saveUserAssosiation(userAssosiation);
		if( savedUserAssosiation!= null ){
			
			return Response.status(200).entity(savedUserAssosiation).build();
		} else { savedUserAssosiation = null; }
		}
		}catch(Exception e){e.printStackTrace();}
		return Response.status(200).entity(savedUserAssosiation).build();
	}
}
