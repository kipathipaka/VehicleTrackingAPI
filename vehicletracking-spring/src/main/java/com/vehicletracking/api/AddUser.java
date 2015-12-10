package com.vehicletracking.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.vehicletracking.model.User;
import com.vehicletracking.model.UserDAO;

/**
 *@Class AddUser- it holds the AddUser details...
 * 
 * @Author Kirankumar Bpatech
 * @version 1.0
 */
@Component
@Path("/user")

public class AddUser {
	
	protected final Log logger = LogFactory.getLog(AddUser.class);
	
	@Autowired
	private UserDAO userDao;
	/**
	 * @Method MyMessage - used to get the user List and return as a Response object to restful clients.
	 * 
	 * @return Response/userList
	 * 
	 */
	@GET
	@Transactional
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUsers() {
		logger.info("@@@ Inside getAllUsers method ...");
		ArrayList<User> userList = (ArrayList<User>) userDao.getAll();
		return Response.status(200).entity(userList).build();
	}
	/**
	 * @method getOneUser -Here get the userId details and create the object into the user @pathparam to reduce the code to repetation of calling.
	 * @param  userId
	 * @return response/user
	 */
	@GET
	@Transactional
	@Path("{phone_number}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOneUser(@PathParam("phone_number") String phoneNumber) {
		logger.info("@@@ getOne User Method...");
		User user = (User) userDao.getUserByPhoneWithStatus(phoneNumber);
		return Response.status(200).entity(user).build();
	}
	/**
	 * @method saveUser- insert /create the user details
	 * @param phone_number
	 * @param name
	 * @param company_name
	 * @param is_active
	 * @param app_download_status
	 * @return savedUser
	 */
	@POST
	@Transactional
	@Path("/addUser")
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveUser(@FormParam("phone_number") String phone_number,
			@FormParam("Name") String name,
			@FormParam("company_name") String company_name,
			@FormParam("location") String location,
			@FormParam("latitude") String  latitude,
			@FormParam("longitude") String longitude,
			@FormParam("fullAddress") String fullAddress) {
		User user = new User();
		User checkUser = null; User savedUser = null;

		if (phone_number != null) {
			user.setPhone_number(phone_number);
			}
		if (company_name != null) {
			user.setCompany_name(company_name);
		}
		if (name != null) {
			user.setName(name);
		}
		if (fullAddress != null) {
			user.setFullAddress(fullAddress);
		}
		Calendar currentDate = Calendar.getInstance();
		currentDate.setTimeZone(TimeZone.getTimeZone("IST"));
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss"); // 2015-10-14 00:00:00
		Date currentDateTime = null;
		try {
			currentDateTime = (Date) formatter.parse(formatter
					.format(currentDate.getTime()));
			user.setLast_sync_date_time(currentDateTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(phone_number != null){
			
			checkUser = userDao.getUserByPhone(phone_number);
			if(checkUser != null){
				logger.info("User already avaliable so updating status");
				checkUser.setIs_active('Y');
				checkUser.setApp_download_status('Y');
				checkUser.setLocation(location);
				checkUser.setLatitude(latitude);
				checkUser.setLongitude(longitude);
				checkUser.setLast_sync_date_time(currentDateTime);
				checkUser.setFullAddress(fullAddress);
				savedUser = userDao.updateUser(checkUser);
			}else{
				logger.info("new user . so creating new entry in table");
				user.setIs_active('Y');
				user.setApp_download_status('Y');
				user.setLocation(location);
				user.setLatitude(latitude);
				user.setLongitude(longitude);
				user.setLast_sync_date_time(currentDateTime);
				user.setFullAddress(fullAddress);
			 savedUser = userDao.createUser(user);
			}
		}
		return Response.status(200).entity(savedUser).build();
	}
	/**
	 * @method deleteUser -delete the userId details
	 * @param userId
	 * @return Response/deleteUser
	 */
	@DELETE
	@Transactional
	@Path("{phone_number}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteUser(@PathParam("phone_number") String phoneNumber) {
		User user = (User) userDao.getUserByPhone(phoneNumber);
		User deletedUser = userDao.deleteUser(user);
		return Response.status(200).entity(deletedUser).build();
	}
	/**
	 * @method upadateUser -is used to update the user details and create the user object.
	 * @param userId
	 * @param phone_number
	 * @param name
	 * @param company_name
	 * @param is_active
	 * @param app_download_status
	 * @return updatedUser
	 */
	@PUT
	@Transactional
	@Path("/updateUser")
	@Produces(MediaType.APPLICATION_JSON)
	public Response UpdateUser(@FormParam("phone_number") String phone_number,
			@FormParam("Name") String name,
			@FormParam("company_name") String company_name,
			@FormParam("is_active") String is_active,
			@FormParam("app_download_status") String app_download_status,
			@FormParam("location") String location,
			@FormParam("latitude") String  latitude,
			@FormParam("longitude") String longitude,
			@FormParam("fullAddress") String fullAddress) {
		logger.info("inside user update method...");
		User user = null;
		if (phone_number != null) {
			user = (User) userDao.getUserByPhone(phone_number);
			user.setPhone_number(phone_number);
			logger.info("user phone number..."+phone_number);
			
		}
		if (company_name != null) {
			user.setCompany_name(company_name);
		}
		if (name != null) {
			user.setName(name);
		}
		if(is_active !=null){
			user.setIs_active(user.setisActiveChar(is_active));
		}
		if(app_download_status != null){
			user.setApp_download_status(user.setappDownloadStatusChar(app_download_status));
		}
		if(location != null){
			user.setLocation(location);
		}
		if(latitude != null){
			user.setLatitude(latitude);
		}
		if(longitude != null){
			user.setLongitude(longitude);
		}
		if(fullAddress != null){
			user.setFullAddress(fullAddress);
			logger.info("user address..."+fullAddress);
		}
		Calendar currentDate = Calendar.getInstance();
		currentDate.setTimeZone(TimeZone.getTimeZone("IST"));
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a"); // 2015-10-14 00:00:00
		Date currentDateTime = null;
		try {
			currentDateTime = (Date) formatter.parse(formatter
					.format(currentDate.getTime()));
			user.setLast_sync_date_time(currentDateTime);
			logger.info("user update time..."+currentDateTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		User updatedUser = userDao.updateUser(user);
		return Response.status(200).entity(updatedUser).build();
	}

}
