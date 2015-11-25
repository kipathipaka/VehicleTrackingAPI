package com.vehicletracking.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
/**
 *@Class: Vehicle - holds the Vehicle information.
 * 
 * @Author Kirankumar Bpatech
 * @version 1.0
 */

@Entity
@Table(name = "VEHICLE_MASTER")
public class Vehicle {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="VEHICLE_MASTER_ID")
	private int vehicle_master_id;
	
	@Column(name = "VEHICLE_REGISTRATION_NUMBER")
	private String vehicle_registration_number;
	
	@ManyToOne
	@JoinColumn(name = "APP_USER_MASTER_ID", referencedColumnName = "APP_USER_MASTER_ID")
	private User user;
	
	@Column(name = "IS_ACTIVE")
	private char is_active;
	
	@Column(name = "CREATED_BY")
	private int created_by;
	
	@Column(name = "CREATED_ON")
	private Date created_on;
	
	@Column(name = "UPDATED_BY")
	private int updated_by;
	
	@Column(name = "UPDATED_ON")
	private Date updated_on;

	 /**
	   * @method Vehicle_master_id used to set the vehicle_master_id
	   * @param   vehicle_master_id
	   * @return void
	   */
		public void setVehicle_master_id(int vehicle_master_id) {
			this.vehicle_master_id = vehicle_master_id;
		}

		public int getVehicle_master_id() {
		return vehicle_master_id;
	}

		public String getVehicle_registration_number() {
			return vehicle_registration_number;
		}
		/**
		 *  @method Vehicle_registration_number is used set the Vehicle_regestration_number
		 * @param vehicle_registration_number
		 */

		public void setVehicle_registration_number(String vehicle_registration_number) {
			this.vehicle_registration_number = vehicle_registration_number;
		}

		public char getIs_active() {
			return is_active;
		}
	/**
	 * @method Is_active is used to papulate the Is_active
	 * @param  is_active
	 */
		public void setIs_active(char is_active) {
			this.is_active = is_active;
		}

		public int getCreated_by() {
			return created_by;
		}
	/**
	 *  @method Created_by -is used to set the Created_by
	 * @param  created_by
	 */
		public void setCreated_by(int created_by) {
			this.created_by = created_by;
		}

		public Date getCreated_on() {
			return created_on;
		}
	/**
	 * @method Created_on is used to set the Created_on
	 * @param describes created_on
	 */
		public void setCreated_on(Date created_on) {
			this.created_on = created_on;
		}

		public int getUpdated_by() {
			return updated_by;
		}
	/**
	 *  @method Updated_by-is used to set the Updated_by
	 * @param updated_by
	 */
		public void setUpdated_by(int updated_by) {
			this.updated_by = updated_by;
		}

		public Date getUpdated_on() {
			return updated_on;
		}
	/**
	 * @method Updated_on -is used to set the Updated_on
	 * @param  updated_on
	 */
		public void setUpdated_on(Date updated_on) {
			this.updated_on = updated_on;
		}

		public User getUser() {
			return user;
		}
	/**
	 * creates an user using one parameter
	 * @param describes user
	 */
		public void setUser(User user) {
			this.user = user;
		}
	
	
}
