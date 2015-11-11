package com.vehicletracking.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Loader;
import org.hibernate.annotations.NamedNativeQuery;

/**
 * @Class: VehicleTrip - holds the VehicleTrip information.
 * 
 * @Author Kirankumar Bpatech
 * @Version 1.0
 */
@Entity
@Table(name = "VEHICLE_TRIP_HEADER")
// @NamedNativeQuery(name="vehicleTripDetail",
// query="SELECT * FROM vehicle_trip_header_detail WHERE VEHICLE_TRIP_HEADER_DETAIL_ID = (SELECT MAX(VEHICLE_TRIP_HEADER_DETAIL_ID) FROM vehicle_trip_header_detail where VEHICLE_TRIP_HEADER_ID = ?) LIMIT 1 ;",
// resultClass = VehicleTripDetail.class)
public class VehicleTrip {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "VEHICLE_TRIP_HEADER_ID")
	private int vehicle_trip_header_id;

	@OneToOne
	@JoinColumn(name = "VEHICLE_MASTER_ID", referencedColumnName = "VEHICLE_MASTER_ID")
	private Vehicle vehicle;

	@ManyToOne
	@JoinColumn(name = "VEHICLE_OWNER_ID", referencedColumnName = "APP_USER_MASTER_ID")
	private User vehicleOwner;

	@ManyToOne
	@JoinColumn(name = "DRIVER_ID", referencedColumnName = "APP_USER_MASTER_ID")
	private User driver;

	@ManyToOne
	@JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "APP_USER_MASTER_ID")
	private User customer;

	@Column(name = "SOURCE_STATION")
	private String source_station;

	@Column(name = "DESTINATION_STATION")
	private String destination_station;

	@Column(name = "TRAVEL_START_DATE_TIME")
	private Date travel_start_date_time;

	@Column(name = "TRAVEL_END_DATE_TIME")
	private Date travel_end_date_time;

	@Column(name = "TRAVEL_STATUS")
	private String travel_status;

	@Column(name = "CREATED_BY")
	private int created_by;

	@Column(name = "CREATED_ON")
	private Date created_on;

	@Column(name = "UPDATED_BY")
	private int updated_by;

	@Column(name = "UPDATED_ON")
	private Date updated_on;

	@Column(name = "LOCATION")
	private String location;

	@Column(name = "LATITUDE")
	private String latitude;

	@Column(name = "LONGITUDE")
	private String longitude;

	@Column(name = "LAST_SYNC_DATE_TIME")
	private Date last_sync_date_time;

	// @OneToMany (mappedBy="vehicleTrip", fetch = FetchType.EAGER)
	// @Loader(namedQuery = "vehicleTripDetail")
	// private List<VehicleTripDetail> vehicleTripDetails;

	/*
	 * public List<VehicleTripDetail> getVehicleTripDetails() { return
	 * vehicleTripDetails; } public void
	 * setVehicleTripDetails(List<VehicleTripDetail> vehicleTripDetails) {
	 * this.vehicleTripDetails = vehicleTripDetails; }
	 */
	/**
	 * @method VehicleTripDetail-is used to set the vehicletripDetails
	 * @param vehicleTripDetail
	 * @return void
	 */

	public Vehicle getVehicle() {
		return vehicle;
	}

	/**
	 * @method Vehicle-is used to papulate the Vehicle
	 * @param vehicle
	 */
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public int getVehicle_trip_header_id() {
		return vehicle_trip_header_id;
	}

	/**
	 * @method Vehicle_trip_header_id is used to set the Vehicle_trip_header_id
	 * @param vehicle_trip_header_id
	 */
	public void setVehicle_trip_header_id(int vehicle_trip_header_id) {
		this.vehicle_trip_header_id = vehicle_trip_header_id;
	}

	public String getSource_station() {
		return source_station;
	}

	/**
	 * @method Source_station is used to papulate the Source_station
	 * @param source_station
	 */
	public void setSource_station(String source_station) {
		this.source_station = source_station;
	}

	public String getDestination_station() {
		return destination_station;
	}

	/**
	 * @method Destination_station is used to set the Destination_station
	 * @param destination_station
	 */
	public void setDestination_station(String destination_station) {
		this.destination_station = destination_station;
	}

	public Date getTravel_start_date_time() {
		return travel_start_date_time;
	}

	/**
	 * @method Travel_start_date_time is used to set the Travel_start_date_time
	 * @param travel_start_date_time
	 */
	public void setTravel_start_date_time(Date travel_start_date_time) {
		this.travel_start_date_time = travel_start_date_time;
	}

	public Date getTravel_end_date_time() {
		return travel_end_date_time;
	}

	/**
	 * @method Travel_end_date_time is used to set the Travel_end_date_time
	 * @param travel_end_date_time
	 */
	public void setTravel_end_date_time(Date travel_end_date_time) {
		this.travel_end_date_time = travel_end_date_time;
	}

	public String getTravel_status() {
		return travel_status;
	}

	/**
	 * @method Travel_status is used to set/papulated to the Travel_status
	 * @param travel_staus
	 */
	public void setTravel_status(String travel_status) {
		this.travel_status = travel_status;
	}

	public int getCreated_by() {
		return created_by;
	}

	/**
	 * @method Created_by-is used to set the created_by
	 * @param created_by
	 */
	public void setCreated_by(int created_by) {
		this.created_by = created_by;
	}

	public Date getCreated_on() {
		return created_on;
	}

	/**
	 * @method Created_on is used to set the date Created_on
	 * @param created_on
	 */
	public void setCreated_on(Date created_on) {
		this.created_on = created_on;
	}

	public int getUpdated_by() {
		return updated_by;
	}

	/**
	 * @method Updated_by is used to set the Updated_by
	 * @param updated_by
	 */
	public void setUpdated_by(int updated_by) {
		this.updated_by = updated_by;
	}

	public Date getUpdated_on() {
		return updated_on;
	}

	/**
	 * @method Updated_on is used to set the updated_on
	 * @param updated_on
	 */
	public void setUpdated_on(Date updated_on) {
		this.updated_on = updated_on;
	}

	public User getDriver() {
		return driver;
	}

	/**
	 * @method Driver is used to set/papulated by the driver
	 * @param driver
	 */
	public void setDriver(User driver) {
		this.driver = driver;
	}

	public User getCustomer() {
		return customer;
	}

	/**
	 * @method Customer is used to set the custmer
	 * @param customer
	 */
	public void setCustomer(User customer) {
		this.customer = customer;
	}

	/**
	 * @method VehicleOwner is used to set the VehicleOwner
	 * @param vehicleOwner
	 */
	public User getVehicleOwner() {
		return vehicleOwner;
	}

	public void setVehicleOwner(User vehicleOwner) {
		this.vehicleOwner = vehicleOwner;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public Date getLast_sync_date_time() {
		return last_sync_date_time;
	}

	public void setLast_sync_date_time(Date last_sync_date_time) {
		this.last_sync_date_time = last_sync_date_time;
	}

}
