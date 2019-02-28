package jj17.yubicycle.model;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * 자전거 대여 현황 클래스
 *
 * @author l4in
 */
public class BicycleStatus {

	/* 대여가능, 대여중, 수리중, 연체중값을 DB에서 가져옴 */
	private SimpleIntegerProperty okRental;
	private SimpleIntegerProperty inRental;
	private SimpleIntegerProperty inRepair;
	private SimpleIntegerProperty overdue;


	public BicycleStatus() {
		this(0, 0, 0, 0);
	}

	public BicycleStatus(int okRental, int inRental, int inRepair, int overdue) {
		this.okRental	= new SimpleIntegerProperty(okRental);
		this.inRental	= new SimpleIntegerProperty(inRental);
		this.inRepair	= new SimpleIntegerProperty(inRepair);
		this.overdue	= new SimpleIntegerProperty(overdue);
	}

	/*
	 * Getters ,Setters and SimpleIntegerProperty
	 * 순서대로: 대여가능, 대여중, 수리중, 연체중
	 *
	 */

	/* 대여가능에 대한 Getter, Setter, Property */
	public int getOkRental() 						{ return okRental.get(); }
	public void setOkRental(int okRental)			{ this.okRental.set(okRental); }
	// 이런게 추가로 필요하다!
	public SimpleIntegerProperty okRentalProperty() { return okRental; }



	/* 대여중에 대한 Getter, Setter, Property */
	public int getInRental() 						{ return inRental.get(); }
	public void setInRental(int inRental) 			{ this.inRental.set(inRental); }
	public SimpleIntegerProperty inRentalProperty() { return inRental; }



	/* 수리중에 대한 Getter, Setter, Property */
	public int getInRepair()						{ return inRepair.get(); }
	public void setInRepair(int inRepair)			{ this.inRepair.set(inRepair); }
	public SimpleIntegerProperty inRepairProperty()	{ return inRepair; }



	/* 연제중에 대한 Getter, Setter, Property */
	public int getOverdue()							{ return overdue.get(); }
	public void setOverdue(int overdue)				{ this.overdue.set(overdue); }
	public SimpleIntegerProperty overdueProperty()	{ return overdue; }

}