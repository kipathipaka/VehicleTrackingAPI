package com.vehicletracking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TRIP_SHARE")
public class TripShare {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="TRIP_SHARE_ID")
	private int trip_share_id;
	
	@OneToOne
	@JoinColumn(name = "VEHICLE_TRIP_HEADER_ID", referencedColumnName = "VEHICLE_TRIP_HEADER_ID")
	private VehicleTrip vehicleTripHeader;
	
	@OneToOne
	@JoinColumn(name = "APP_USER_MASTER_ID", referencedColumnName = "APP_USER_MASTER_ID")
	private User appUserMaster;

	
	public int getTrip_share_id() {
		return trip_share_id;
	}

	public void setTrip_share_id(int trip_share_id) {
		this.trip_share_id = trip_share_id;
	}

	public VehicleTrip getVehicleTripHeader() {
		return vehicleTripHeader;
	}

	public void setVehicleTripHeader(VehicleTrip vehicleTripHeader) {
		this.vehicleTripHeader = vehicleTripHeader;
	}

	public User getAppUserMaster() {
		return appUserMaster;
	}

	public void setAppUserMaster(User appUserMaster) {
		this.appUserMaster = appUserMaster;
	}
	
}
