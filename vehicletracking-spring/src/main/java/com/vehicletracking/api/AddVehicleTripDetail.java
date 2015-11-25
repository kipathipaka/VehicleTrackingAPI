package com.vehicletracking.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.validation.Valid;
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
import com.vehicletracking.model.VehicleTripDAO;
import com.vehicletracking.model.VehicleTripDetail;
import com.vehicletracking.model.VehicleTripDetailDAO;

/**
 * @Class: AddVehicleTripDetail - holds the VehicleTripDetail information.
 * 
 * @Author Kirankumar Bpatech
 * @version 1.0
 */

@Component
@Path("/tripdetail")
public class AddVehicleTripDetail {
	
	protected final Log logger = LogFactory.getLog(AddVehicleTripDetail.class);
	
	@Autowired
	private VehicleTripDetailDAO vehicleTripDetailDAO;
	
	@Autowired
	private VehicleTripDAO vehicleTripDAO;
	
	/**
	 * @method getVehicleTripDetailsList -is used to get the VehicleTripDetailsList and return to the response.
	 * @return Response/vehicleTripDetailsList
	 */
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getVehicleTripDetailsList(){
		logger.info("@@@ In getVehicleTripDetailsList Method in AddVehilceTripDetail.java");
		ArrayList<VehicleTripDetail> vehicleTripDetailsList = (ArrayList<VehicleTripDetail>) vehicleTripDetailDAO.getVehicleTripDetails();
		
		return  Response.status(200).entity(vehicleTripDetailsList).build();
	}
	
	/**
	 * @method getoneVehicleTripDetail -used to get the one vehicleTripDetailId and create the object to the vehicleTrip
	 * @param vehicleTripDetailId
	 * @return Response/vehicleTripDetail
	 */
	
	@GET
	@Transactional
	@Path("{tripDetailId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOneVehicleTripDetail(@PathParam("tripDetailId")int tripDetailId){
		logger.info("@@@ getOneVehicleTripDetail Method...");
		VehicleTripDetail vehicleTripDetail = (VehicleTripDetail) vehicleTripDetailDAO.getOneVehicleTripDetail(tripDetailId);		
		return  Response.status(200).entity(vehicleTripDetail).build();
	}
	
	/**
	 * @method saveVehicleTripDetail - is used create/save the vehicleTripDetail
	 * @param vehicleTripId
	 * @param location
	 * @param latitude
	 * @param longitude
	 * @param last_sync_date_time
	 * @return Response/savedVehicleTripDetail
	 */
	
	@POST
	@Transactional
	@Path("/addTripDetail")
	@Produces(MediaType.APPLICATION_JSON)
	public  Response saveVehicleTripDetail(
			@FormParam("vehicleTrip") int vehicleTripId,
			@FormParam("location") String location,
			@FormParam("latitude") String  latitude,
			@FormParam("longitude") String longitude){
		
		VehicleTripDetail vehicleTripDetail = new VehicleTripDetail();
		SimpleDateFormat dbDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); //Ex: 2015-09-28 10:30:30
		VehicleTripDetail savedVehicleTripDetail = null;
		vehicleTripDetail.setVehicleTrip(vehicleTripDAO.getOneVehicleTrip(vehicleTripId));
		
		if (location != null) {
			vehicleTripDetail.setLocation(location);
		}
		if (latitude != null) {
			vehicleTripDetail.setLatitude(latitude);
		}
		if (longitude != null) {
			vehicleTripDetail.setLongitude(longitude);
		}
		Calendar currentDate = Calendar.getInstance();
		currentDate.setTimeZone(TimeZone.getTimeZone("IST"));
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss"); // 2015-10-14 00:00:00
		Date currentDateTime = null;
		try {
			currentDateTime = (Date) formatter.parse(formatter
					.format(currentDate.getTime()));
			vehicleTripDetail.setLast_sync_date_time(currentDateTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(location != null && latitude != null && longitude != null){
			savedVehicleTripDetail = vehicleTripDetailDAO.createVehicleTripDetail(vehicleTripDetail);
		}
		return Response.status(200).entity(savedVehicleTripDetail).build();
	}
	
	/**
	 * @method deleteVehicleTripDetail - in this method to delete the vehicleTripDetali
	 * @param vehicleTripDetailId
	 * @return Response/deletedVehicleTripDetail
	 */
	
	@DELETE
	@Transactional
	@Path("{tripDetailId}")
	@Produces(MediaType.APPLICATION_JSON)
	public  Response deleteVehicleTripDetail(@PathParam("tripDetailId") int tripDetailId){
		VehicleTripDetail vehicleTripDetail = (VehicleTripDetail) vehicleTripDetailDAO.getOneVehicleTripDetail(tripDetailId);
		VehicleTripDetail deletedVehicleTripDetail = vehicleTripDetailDAO.deleteVehicleTripDetail(vehicleTripDetail);
		return Response.status(200).entity(deletedVehicleTripDetail).build();
	}
	
	/**
	 * @method UpdateVehicleTripDetail - used to update tripDetails and create the object to the vehicleTripDetail & dbDateFormat.
	 * @param vehicleTripDetailId
	 * @param vehicleTripId
	 * @param location
	 * @param latitude
	 * @param longitude
	 * @param last_sync_date_time
	 * @return Response/updatedVehicleTripDetail
	 */
	
	@PUT
	@Transactional
	@Path("/updateTripDetail")
	@Produces(MediaType.APPLICATION_JSON)
	public  Response UpdateVehicleTripDetail(@FormParam("vehicle_trip_header_detail_id") int vehicleTripDetailId,
			@FormParam("vehicleTrip") int vehicleTripId,
			@FormParam("location") String location,
			@FormParam("latitude") String  latitude,
			@FormParam("longitude") String longitude){
		
		VehicleTripDetail vehicleTripDetail = (VehicleTripDetail) vehicleTripDetailDAO.getOneVehicleTripDetail(vehicleTripDetailId);
		VehicleTripDetail updatedVehicleTripDetail = null;
		
		vehicleTripDetail.setVehicleTrip(vehicleTripDAO.getOneVehicleTrip(vehicleTripId));
		
		if (location != null) {
			vehicleTripDetail.setLocation(location);
		}
		if (latitude != null) {
			vehicleTripDetail.setLatitude(latitude);
		}
		if (longitude != null) {
			vehicleTripDetail.setLongitude(longitude);
		}
		Calendar currentDate = Calendar.getInstance();
		currentDate.setTimeZone(TimeZone.getTimeZone("IST"));
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss"); // 2015-10-14 00:00:00
		Date currentDateTime = null;
		try {
			currentDateTime = (Date) formatter.parse(formatter
					.format(currentDate.getTime()));
			vehicleTripDetail.setLast_sync_date_time(currentDateTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(location != null && latitude != null && longitude != null){
			updatedVehicleTripDetail = vehicleTripDetailDAO.updateVehicleTripDetail(vehicleTripDetail);
		}
		return Response.status(200).entity(updatedVehicleTripDetail).build();
	}
	
}
