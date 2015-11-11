package com.vehicletracking.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Loader;
import org.hibernate.annotations.NamedNativeQuery;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * @Class: VehicleTripDetails - holds the VehicleTripDetails information.
 * 
 * @Author Kirankumar Bpatech
 * @version 1.0
 */

@Entity
@Table(name = "VEHICLE_TRIP_HEADER_DETAIL")
public class VehicleTripDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "VEHICLE_TRIP_HEADER_DETAIL_ID")
	private int vehicle_trip_header_detail_id;

	@ManyToOne
	@JoinColumn(name = "VEHICLE_TRIP_HEADER_ID", referencedColumnName = "VEHICLE_TRIP_HEADER_ID")
	@JsonBackReference
	private VehicleTrip vehicleTrip;

	@Column(name = "LOCATION")
	private String location;

	@Column(name = "LATITUDE")
	private String latitude;

	@Column(name = "LONGITUDE")
	private String longitude;

	@Column(name = "LAST_SYNC_DATE_TIME")
	private Date last_sync_date_time;

	public VehicleTrip getVehicleTrip() {
		return vehicleTrip;
	}

	/**
	 * @method VehicleTrip -is used set/populated to the vehicletrip
	 * @param vehicleTrip
	 * @return void
	 */
	public void setVehicleTrip(VehicleTrip vehicleTrip) {
		this.vehicleTrip = vehicleTrip;
	}

	public int getVehicle_trip_header_detail_id() {
		return vehicle_trip_header_detail_id;
	}

	/**
	 * @method vehicle_trip_header-detail-id -is used to populate the
	 *         vehicle_trip_header-detail-id
	 * @param vehicle_trip_header_detail_id
	 */
	public void setVehicle_trip_header_detail_id(
			int vehicle_trip_header_detail_id) {
		this.vehicle_trip_header_detail_id = vehicle_trip_header_detail_id;
	}

	public String getLocation() {
		return location;
	}

	/**
	 * @method Location -is used to set the Location
	 * @param location
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	public String getLatitude() {
		return latitude;
	}

	/**
	 * @method Latitude -is used to set the Latitude
	 * @param latitude
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	/**
	 * @method longitude-is used to set the Longitude
	 * @param longitude
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public Date getLast_sync_date_time() {
		return last_sync_date_time;
	}

	/**
	 * @method Last_sync_date_time is used set/papulated to the
	 *         ast_sync_date_tim
	 * @param last_sync_date_time
	 */
	public void setLast_sync_date_time(Date last_sync_date_time) {
		this.last_sync_date_time = last_sync_date_time;
	}

}
