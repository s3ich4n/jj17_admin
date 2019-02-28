package jj17.yubicycle.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jj17.yubicycle.model.Bicycle;
import jj17.yubicycle.model.StudentData;
import jj17.yubicycle.util.ConnectDatabase;
import jj17.yubicycle.view.loginController;
import jj17.yubicycle.view.mainstageController;

/**
 * SQL UPDATE SET Query를 날리는 메소드.
 *
 * @author l4in
 *
 */
public class updateQuery {
	/**
	 * SID값을 읽어온 후 대여 트랜잭션을 수행한다.
	 * 대여가능: 1, 대여중: 2, 수리중: 3, 연체중: 4
	 *
	 * @param SID
	 * @return
	 */

	private static final int OKRENTALINDEX	= 1;
	private static final int INRENTALINDEX	= 2;
	private static final int INREPAIRINDEX	= 3;
	private static final int OVERDUEINDEX	= 4;

	private static final double MainGateLatitude	= 35.834873;
	private static final double MainGateLongitude	= 128.754318;
	/**
	 * 대여 트랜잭션을 수행하는 함수.
	 * @param tbc
	 * @return
	 */
	public static boolean rentalTransaction(Bicycle tbc) {

		StudentData tempStudentData = loginController.getStudentDataFromRFID();

		// 쿼리는 다음과같이 보관한다.
		final String studentDataQuery =
					"UPDATE students "
				+	"SET isRental= ? "
				+	"WHERE SID = ?";

		final String MainGateRentalQuery =
					"UPDATE MainGateBikeData "
				+	"SET bicycleStatus = ?, currentRentPersonSID = ?, DueDate = CURDATE() "
				+	"WHERE bicycleNo = ?";

		Connection conn = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;

		try {
			conn = ConnectDatabase.connectToDB();

			// 학생데이터 쿼리수정.
			// 대여하면 isRental을 트루로 셋.
			pstmt1 = conn.prepareStatement(studentDataQuery);
			pstmt1.setBoolean(1, true);
			pstmt1.setInt(2, tempStudentData.getSid());
			if(pstmt1.executeUpdate() == 0) {
				System.out.println("pstmt1 error occured");
				throw new SQLException();
			}

			// 정문 앞 대여소 쿼리 수정.
			pstmt2 = conn.prepareStatement(MainGateRentalQuery);
			pstmt2.setInt(1, INRENTALINDEX);
			pstmt2.setInt(2, tempStudentData.getSid());
			pstmt2.setInt(3, tbc.getBicycleNo());
			if(pstmt2.executeUpdate() == 0) {
				System.out.println("pstmt2 error occured");
				throw new SQLException();
			}

			return true;

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}


	/**
	 * 반납 트랜잭션을 수행하는 함수.
	 *
	 */
	public static boolean returnTransaction() {

		StudentData tempStudentData = loginController.getStudentDataFromRFID();


		// 쿼리는 다음과같이 보관한다.
		// SID가 ?인 학생의 isRental값을 false로 업데이트.
		final String studentDataQuery =
					"UPDATE students "
				+	"SET isRental= ? "
				+	"WHERE SID = ?";




		final String MainGateRentalQuery =
					"UPDATE MainGateBikeData "
				+	"SET bicycleStatus = ?, currentRentPersonSID = ?, "
				+	"currentBikeLatitude = ?, currentBikeLongitude = ?, "
				+	"DueDate = CURDATE() "
				+	"WHERE currentRentPersonSID = ?";

		Connection conn = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;

		try {
			conn = ConnectDatabase.connectToDB();

			// 학생데이터 쿼리수정.
			// 반납하면 isRental을 폴스로 셋.
			pstmt1 = conn.prepareStatement(studentDataQuery);
			pstmt1.setBoolean(1, false);
			pstmt1.setInt(2, tempStudentData.getSid());
			if(pstmt1.executeUpdate() == 0) {
				System.out.println("pstmt1 error occured");
				throw new SQLException();
			}

			// 정문 앞 대여소 쿼리 수정.
			pstmt2 = conn.prepareStatement(MainGateRentalQuery);
			pstmt2.setInt(1, OKRENTALINDEX);
			pstmt2.setNull(2, java.sql.Types.NULL);
			pstmt2.setDouble(3, MainGateLatitude);
			pstmt2.setDouble(4, MainGateLongitude);
			pstmt2.setInt(5, tempStudentData.getSid());
			if(pstmt2.executeUpdate() == 0) {
				System.out.println("pstmt2 error occured");
				throw new SQLException();
			}

			return true;

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
}
