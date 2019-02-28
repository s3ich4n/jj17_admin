package jj17.yubicycle.model;

import javafx.beans.property.*;

import javafx.beans.property.*;

/**
 * 학생 정보에 대한 DO
 * @author l4in
 *
 */
public class StudentData {

	private IntegerProperty				sid;
	private StringProperty				sname;
	private StringProperty				sDept;
	private IntegerProperty				sPhone;
	private SimpleObjectProperty<byte[]>	sPic;
	private IntegerProperty				isBlacklist;
	private StringProperty				uid;
	private IntegerProperty				isRental;


	public StudentData() {
		this(0, null, null, 0, null, 0, null, 0);
	}

	public StudentData( int sid, String sname, String sDept, int sPhone,
						byte[] sPic, int isBlacklist, String uid, int isRental) {
		this.sid 	= new SimpleIntegerProperty(sid);
		this.sname  = new SimpleStringProperty(sname);
		this.sDept	= new SimpleStringProperty(sDept);
		this.sPhone	= new SimpleIntegerProperty(sPhone);
		this.sPic	= new SimpleObjectProperty<byte[]>(sPic);
		this.isBlacklist = new SimpleIntegerProperty(isBlacklist);
		this.uid	= new SimpleStringProperty(uid);
		this.isRental = new SimpleIntegerProperty(isRental);
	}

	/**
	 * Getters, Setters, and Getters for the property itself
	 * @return
	 */
	public final int getSid()						{ return sid.get(); }
	public final void setSid(int sid) 				{ this.sid.set(sid);}
	public final IntegerProperty SidProperty()		{ return sid; }



	public final String getSname()					{ return sname.get(); }
	public final void setSname(String sname) 		{ this.sname.set(sname);}
	public final StringProperty SnameProperty()		{ return sname; }


	public final String getsDept()					{ return sDept.get(); }
	public final void setsDept(String sDept)		{ this.sDept.set(sDept);}
	public final StringProperty sDeptProperty()		{ return sDept; }

	public final int getsPhone() 					{ return sPhone.get();}
	public final void setsPhone(int sPhone) 		{ this.sPhone.set(sPhone);}
	public final IntegerProperty sPhoneProperty() 	{ return sPhone;}

	public final byte[] getsPic() 					{ return sPic.get();}
	public final void setsPic(byte[] bs)			{ this.sPic.set(bs);}
	public final SimpleObjectProperty<byte[]> sPicProperty() { return sPic;}

	public final int getIsBlacklist() 					{ return isBlacklist.get(); }
	public final void setIsBlacklist(int isBlacklist)	{ this.isBlacklist.set(isBlacklist); }
	public final IntegerProperty IsBlacklistProperty()	{ return isBlacklist; }

	public final String getUid() 					{ return uid.get();}
	public final void setUid(String uid)			{ this.uid.set(uid);}
	public final StringProperty UidProperty()		{ return uid;}

	public final int getIsRental()					{ return isRental.get();}
	public final void setIsRental(int isRental)		{ this.isRental.set(isRental);}
	public final IntegerProperty IsRentalProperty() { return isRental;}
}
