package com.vehicletracking.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Class: User - holds the user information.
 * 
 * @Author Kirankumar Bpatech
 * @Version 1.0
 */

@Entity
@Table(name = "APP_USER_MASTER")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "APP_USER_MASTER_ID")
	private int app_user_master_id;

	@Column(name = "PHONE_NUMBER")
	private String phone_number;

	@Column(name = "NAME")
	private String Name;

	@Column(name = "COMPANY_NAME")
	private String company_name;

	@Column(name = "IS_ACTIVE")
	private char is_active;

	@Column(name = "APP_DOWNLOAD_STATUS")
	private char app_download_status;

	@Column(name = "LOCATION")
	private String location;

	@Column(name = "LATITUDE")
	private String latitude;

	@Column(name = "LONGITUDE")
	private String longitude;

	@Column(name = "LAST_SYNC_DATE_TIME")
	private Date last_sync_date_time;
	
	@Column(name = "FULL_ADDRESS")
	private String fullAddress;
	
	/**
	 * @Method app_user_master_id - used to set/populate the app_user_master_id.
	 * @param app_user_master_id
	 * @return void
	 */
	public void setApp_user_master_id(int app_user_master_id) {
		this.app_user_master_id = app_user_master_id;
	}

	public int getApp_user_master_id() {
		return app_user_master_id;
	}

	public String getPhone_number() {
		return phone_number;
	}

	/**
	 * @method Phone_number- used to set the Phone_number
	 * @param phone_number
	 */

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getName() {
		return Name;
	}

	/**
	 * @method Name- used to set the Name
	 * @param name
	 */

	public void setName(String name) {
		this.Name = name;
	}

	public String getCompany_name() {
		return company_name;
	}

	/**
	 * @method Company_Name- used to set the Company_Name
	 * @param company_name
	 */
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public char getIs_active() {
		return is_active;
	}

	/**
	 * @method Is_Active- used to set the Is_Active
	 * @param is_active
	 */

	public void setIs_active(char is_active) {
		this.is_active = is_active;
	}

	public char getApp_download_status() {
		return app_download_status;
	}

	/**
	 * @method App_download_status- used to set the App_download_status
	 * @param app_download_status
	 */
	public void setApp_download_status(char app_download_status) {
		this.app_download_status = app_download_status;
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
	
	public String getFullAddress() {
		return fullAddress;
	}

	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}

	public char getisActiveChar(String isActive) {
		char isActiveChar = 'N' ;

		if (isActive.equalsIgnoreCase("Y")) {
			isActiveChar = 'Y';
		} else if (isActive.equalsIgnoreCase("N")) {
			isActiveChar = 'N';
		}

		return isActiveChar;
	}

	public char setisActiveChar(String isActive) {
		char isActiveChar = 'N';

		if (isActive.equalsIgnoreCase("Y")) {
			isActiveChar = 'Y';
		} else if (isActive.equalsIgnoreCase("N")) {
			isActiveChar = 'N';
		}

		return isActiveChar;
	}
	
	
	public char getappDownloadStatusChar(String appDownloadStatusStr) {
		char appDownloadStatusChar = 'N' ;

		if (appDownloadStatusStr.equalsIgnoreCase("Y")) {
			appDownloadStatusChar = 'Y';
		} else if (appDownloadStatusStr.equalsIgnoreCase("N")) {
			appDownloadStatusChar = 'N';
		}

		return appDownloadStatusChar;
	}

	public char setappDownloadStatusChar(String appDownloadStatusStr) {
		char appDownloadStatusChar = 'N';

		if (appDownloadStatusStr.equalsIgnoreCase("Y")) {
			appDownloadStatusChar = 'Y';
		} else if (appDownloadStatusStr.equalsIgnoreCase("N")) {
			appDownloadStatusChar = 'N';
		}

		return appDownloadStatusChar;
	}

}
