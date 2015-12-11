package com.vehicletracking.api;


import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vehicletracking.model.CommonApiDAO;
import com.vehicletracking.model.CommonMessagesDAO;
import com.vehicletracking.model.UserAssosiation;
import com.vehicletracking.model.UserDAO;
/**
 * @class CommonApi -it holds the api information
 * 
 * @Author Kirankumar Bpatech
 * @version 1.0
 */

@Component
@Path("/common")
public class CommonApi {
	
	protected final Log logger = LogFactory.getLog(CommonApi.class);
	
	@Autowired
	private CommonApiDAO commonApiDAO;
	
	@Autowired
	private CommonMessagesDAO commonMessagesDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	List<UserAssosiation> userAssosiations = null;
	
	/**
	 * @method getDriverList - used to get the driver details
	 * @return response/userAssosiation
	 */
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/drivers")
	public Response getDriversList(){
		userAssosiations = new ArrayList<UserAssosiation>();
		char type = 'D'; // setting statically because we need to get all Drivers list in user association 
		userAssosiations = commonApiDAO.getUserAssosiationListByType(type);
		return  Response.status(200).entity(userAssosiations).build();
	}
	
	/**
	 * @method getCustomerList -is used to get the customer details
	 * @return Response/userAssosiation
	 */
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/customers")
	public Response getCustomersList(){
		userAssosiations = new ArrayList<UserAssosiation>();
		char type = 'C'; // setting statically because we need to get all Drivers list in user association 
		userAssosiations = commonApiDAO.getUserAssosiationListByType(type);
		return  Response.status(200).entity(userAssosiations).build();
	}
	
	
	/**
	 * @method getCommonMessages to get All Messages which need to send user dynamically.
	 * @return CommonMessages object
	 */
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/messages")
	public Response getCommonMessages(){
		logger.info("@@@ inside getCommonMessages API Call method......");
		return  Response.status(200).entity(commonMessagesDAO.getCommonMessages()).build();
	}
	
	
	/**
	 * @method createAssosiation -Here used to create/add the assosiation details
	 * @param vehicleOwnerPhoneNumber
	 * @param driverPhoneNumber
	 * @param type
	 * @return Response/savedUserAssosiation
	 */
	
	/*@POST
	@Transactional
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/addDriverPhone")
	public Response createAssosiation(
			@FormParam("driver_phone_number") String driverPhoneNumber,
			@FormParam("vehicle_owner_phone_number") String vehicleOwnerPhoneNumber){
		User vehicleOwnerUser = null;
		UserAssosiation userAssosiation = null; UserAssosiation savedUserAssosiation = null;
		User driverUser = null;
		try{
		if(!StringUtils.isEmpty(vehicleOwnerPhoneNumber)){
			vehicleOwnerUser = new User();
			userAssosiation = new UserAssosiation();
			vehicleOwnerUser = userDAO.getUserByPhone(vehicleOwnerPhoneNumber);
			logger.info("Vehicle Owner User is :"+vehicleOwnerUser.getName());
			if(!StringUtils.isEmpty(vehicleOwnerUser)){userAssosiation.setParent_user_master(vehicleOwnerUser);}
			
		}
		if(!StringUtils.isEmpty(driverPhoneNumber)){
			driverUser = new User();
			driverUser = userDAO.getUserByPhone(driverPhoneNumber);
			
			if(!StringUtils.isEmpty(driverUser)){
				logger.info("Got Driver obj "+driverUser.getPhone_number()+" : "+driverUser.getName());
				userAssosiation.setApp_user_master(driverUser);
			}else{
				User driver = new User();
				driver.setPhone_number(driverPhoneNumber);
				driver.setIs_active('N');
				driver.setApp_download_status('N');
				User savedDriverUser = userDAO.createUser(driver);
				logger.info("No Driver obj avaliable sio inserting new Driver :"+driver.getPhone_number()+" : "+driver.getName());
				userAssosiation.setApp_user_master(savedDriverUser);
			}
				userAssosiation.setType('D');
		}
		savedUserAssosiation = commonApiDAO.saveUserAssosiation(userAssosiation);
		}catch(Exception e){e.printStackTrace();}
		return Response.status(200).entity(savedUserAssosiation).build();
	}*/
	
}
