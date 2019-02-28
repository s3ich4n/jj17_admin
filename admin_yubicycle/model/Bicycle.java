package jj17.yubicycle.model;

import java.sql.Date;

/**
 * 불러온 자전거에 대한 DO
 * @author l4in
 *
 */
public class Bicycle {

	int bicycleNo;
	int bicycleStatus;
	int currentRentPersonSID;
	double currentBikeLatitude;
	double currentBikeLongitude;
	Date DueDate;

	public int getBicycleNo() {
		return bicycleNo;
	}

	public void setBicycleNo(int bicycleNo) {
		this.bicycleNo = bicycleNo;
	}

	public int getBicycleStatus() {
		return bicycleStatus;
	}

	public void setBicycleStatus(int bicycleStatus) {
		this.bicycleStatus = bicycleStatus;
	}

	public int getCurrentRentPersonSID() {
		return currentRentPersonSID;
	}

	public void setCurrentRentPersonSID(int currentRentPersonSID) {
		this.currentRentPersonSID = currentRentPersonSID;
	}

	public double getCurrentBikeLatitude() {
		return currentBikeLatitude;
	}

	public void setCurrentBikeLatitude(double currentBikeLatitude) {
		this.currentBikeLatitude = currentBikeLatitude;
	}

	public double getCurrentBikeLongitude() {
		return currentBikeLongitude;
	}

	public void setCurrentBikeLongitude(double currentBikeLongitude) {
		this.currentBikeLongitude = currentBikeLongitude;
	}

	public Date getDueDate() {
		return DueDate;
	}

	public void setDueDate(Date dueDate) {
		DueDate = dueDate;
	}
}
