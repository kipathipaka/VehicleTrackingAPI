package com.vehicletracking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @Class: UserAssosiation - holds the userassosiation information.
 * 
 * @Author kirankumar Bpatech
 * @version 1.0
 */
@Entity
@Table(name = "APP_USER_ASSOSIATION")
public class UserAssosiation {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "APP_USER_ASSOSIATION_ID")
	private int app_user_assosiation_id;

	@OneToOne
	@JoinColumn(name = "APP_USER_MASTER_ID", referencedColumnName = "APP_USER_MASTER_ID")
	private User app_user_master;

	@OneToOne
	@JoinColumn(name = "PARENT_USER_MASTER_ID", referencedColumnName = "APP_USER_MASTER_ID")
	private User parent_user_master;

	@Column(name = "TYPE")
	private char type;

	public int getApp_user_assosiation_id() {
		return app_user_assosiation_id;
	}

	/**
	 * @method App_user_assosiation -is used to set/papulated to the
	 *         App_user_assosiation_id
	 * @param app_user_assosiation_id
	 * @return void
	 */
	public void setApp_user_assosiation_id(int app_user_assosiation_id) {
		this.app_user_assosiation_id = app_user_assosiation_id;
	}

	public User getParent_user_master() {
		return parent_user_master;
	}

	/**
	 * @method Parent_user_master - is used to set the parent_user_master
	 * @param parent_user_maste
	 */
	public void setParent_user_master(User parent_user_master) {
		this.parent_user_master = parent_user_master;
	}

	public char getType() {
		return type;
	}

	/**
	 * @method Type is used to set the type
	 * @param type
	 */
	public void setType(char type) {
		this.type = type;
	}

	public User getApp_user_master() {
		return app_user_master;
	}

	/**
	 * @method App_user_master is used to set the app_user_master
	 * @param app_user_master
	 */
	public void setApp_user_master(User app_user_master) {
		this.app_user_master = app_user_master;
	}

}
