package com.vehicletracking.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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

import com.vehicletracking.model.UserAssosiationDAO;
import com.vehicletracking.model.UserDAO;
import com.vehicletracking.model.Vehicle;
import com.vehicletracking.model.VehicleDAO;
/**
 * @Class AddVehicle - holds the AddVehicle Details.
 * 
 * @Author Kirankumar Bpatech
 * @version 1.0
 */
@Component
@Path("/vehicle")
public class AddVehicle {
	
	protected final Log logger = LogFactory.getLog(AddVehicle.class);
	
	@Autowired
	private VehicleDAO vehicleDAO;
	
	@Autowired
	private UserAssosiationDAO userAssosiationDAO;
	
	@Autowired
	private UserDAO userDAO;
	/**
	 * @method getVehicleList -is used to get the VehicleList and return the response
	 * @return response/vehiclesList
	 */
	@GET
	@Transactional
	@Produces(MediaType.APPLICATION_JSON)
	public Response getVehiclesList(){
		logger.info("@@@ In getVehiclesList Method in AddVehilce.java");
		ArrayList<Vehicle> vehiclesList = (ArrayList<Vehicle>) vehicleDAO.getAllVehicles();
		
		return  Response.status(200).entity(vehiclesList).build();
	}
	/**
	 * @method getOneVehicle -used to get the vehicleid and create the vehicle object.
	 * @param vehicleId
	 * @return Responsible/vehicle
	 */
	
	@GET
	@Transactional
	@Path("{vehicleId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOneVehicle(@PathParam("vehicleId")int vehicleId){
		logger.info("@@@ getOneVehicle Method...");
		Vehicle vehicle = (Vehicle) vehicleDAO.getOneVehicle(vehicleId);		
		return  Response.status(200).entity(vehicle).build();
	}
	/**
	 * @method saveVehicle -used to create/save the vehicle and create the object to the vehicle.
	 * @param vehicle_registration_number
	 * @param userId
	 * @param is_active
	 * @param created_by
	 * @param updated_by
	 * @param created_on
	 * @param updated_on
	 * @return response/savedVehicle
	 */
	@POST
	@Transactional
	@Path("/addVehicle")
	@Produces(MediaType.APPLICATION_JSON)
	public  Response saveVehicle(@FormParam("vehicle_registration_number") String vehicle_registration_number,
			@FormParam("user") int userId,
			@FormParam("is_active") String is_active,
			@FormParam("created_by") int  created_by,
			@FormParam("updated_by") int updated_by){
		
		Vehicle vehicle = new Vehicle();
		SimpleDateFormat dbDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); //Ex: 2015-09-28 10:30:30
		if (vehicle_registration_number != null) {
			vehicle.setVehicle_registration_number(vehicle_registration_number);
		}
		
		if(is_active !=null){
			vehicle.setIs_active(is_active.charAt(0));
		}
		vehicle.setCreated_by(created_by);
		vehicle.setUpdated_by(updated_by);
		vehicle.setUser(userDAO.getOneUser(userId));
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss"); // 2015-10-14 00:00:00
		Date currentDateTime = null;
		try {
			currentDateTime = (Date) formatter.parse(formatter
					.format(currentDate.getTime()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (currentDateTime != null) {
			vehicle.setCreated_on(new Date());
		}
		if (currentDateTime != null) {
			//Date updated_Date = dbDateFormat.parse(updated_on);
			vehicle.setUpdated_on(new Date());
		}
		
		Vehicle savedVehicle = vehicleDAO.createVehicle(vehicle);
		return Response.status(200).entity(savedVehicle).build();
		
	}
	/**
	 * @method deleteVehicle - used to delete the vehicleId
	 * @param vehicleId
	 * @return response/deleteVehicle
	 */
	
	@DELETE
	@Transactional
	@Path("{vehicleId}")
	@Produces(MediaType.APPLICATION_JSON)
	public  Response deleteVehicle(@PathParam("vehicleId") int vehicleId){
		Vehicle vehicle = (Vehicle) vehicleDAO.getOneVehicle(vehicleId);
		Vehicle deletedVehicle = vehicleDAO.deleteVehicle(vehicle);
		return Response.status(200).entity(deletedVehicle).build();
	}
	/**
	 * @method UpdateUser -used to update the user details and create the vehicle object
	 * @param vehicle_master_id
	 * @param vehicle_registration_number
	 * @param userId
	 * @param is_active
	 * @param created_by
	 * @param updated_by
	 * @param created_on
	 * @param updated_on
	 * @return response/updatedVehicle
	 */
	@PUT
	@Transactional
	@Path("/updateVehicle")
	@Produces(MediaType.APPLICATION_JSON)
	public  Response UpdateUser(@FormParam("vehicle_master_id") int vehicle_master_id, 
			@FormParam("vehicle_registration_number") String vehicle_registration_number,
			@FormParam("user") int userId,
			@FormParam("is_active") String is_active,
			@FormParam("created_by") int  created_by,
			@FormParam("updated_by") int updated_by){
		
		Vehicle vehicle = (Vehicle) vehicleDAO.getOneVehicle(vehicle_master_id);
		SimpleDateFormat dbDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); //Ex: 2015-09-28 10:30:30
		if (vehicle_registration_number != null) {
			vehicle.setVehicle_registration_number(vehicle_registration_number);
		}
		
		if(is_active !=null){
			vehicle.setIs_active(is_active.charAt(0));
		}
		vehicle.setCreated_by(created_by);
		vehicle.setUpdated_by(updated_by);
		vehicle.setUser(userDAO.getOneUser(userId));
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss"); // 2015-10-14 00:00:00
		Date currentDateTime = null;
		try {
			currentDateTime = (Date) formatter.parse(formatter
					.format(currentDate.getTime()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (currentDateTime != null) {
			vehicle.setCreated_on(currentDateTime);
		}
		if (currentDateTime != null) {
			//Date updated_Date = dbDateFormat.parse(updated_on);
			vehicle.setUpdated_on(currentDateTime);
		}
		
		Vehicle updatedVehicle = vehicleDAO.updateVehicle(vehicle);
		return Response.status(200).entity(updatedVehicle).build();
	}
	
}
