package jj17.yubicycle.query;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.imageio.ImageIO;

import jj17.yubicycle.util.ConnectDatabase;
import jj17.yubicycle.model.StudentData;

public class selectQuery {

	/**
	 * UID값을 불러오고 학생 데이터를 리턴하는 함수.
	 * 해당 함수가 쓰이는 곳:
	 * line 56: loginController.java
	 *
	 * @param UID값
	 * @return StudentData값.
	 */
	public static StudentData doSelectQuery(String UID) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StudentData tempStudent = new StudentData(); // Student 객체.

		final String findStudentQuery = "SELECT * FROM students WHERE UID=?";

		try {
			conn = ConnectDatabase.connectToDB();

			pstmt = conn.prepareStatement(findStudentQuery);
			pstmt.setString(1, UID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				tempStudent.setSid(rs.getInt("sid"));
				tempStudent.setSname(rs.getString("sname"));
				tempStudent.setsDept(rs.getString("sDept"));
				tempStudent.setsPhone(rs.getInt("sPhone"));
				tempStudent.setsPic(rs.getBytes("sPic"));
				tempStudent.setIsBlacklist(rs.getInt("isBlacklist"));
				tempStudent.setUid(rs.getString("uid"));
				tempStudent.setIsRental(rs.getInt("isRental"));
//				tst.setDueDate(rs.getDate("DueDate"));
				BufferedImage img = ImageIO.read(new ByteArrayInputStream(tempStudent.getsPic()));
				ImageIO.write(img, "jpg", new File("student.jpg"));
				System.out.println(img);
			}
			return tempStudent;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		}

		if (tempStudent.getSid() == 0) {
			System.out.println("해당 학생없음 !");
			return null;
		}
		return null;
	}

}
