package jj17.yubicycle.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDatabase {

	public static Connection connectToDB() {
		Connection c = null;

		try {

			// 이거 어디 파일에 저장하는법 없나?
			// 하다못해 3des로라도 암호화하고 싶은데
			String url	=	"jdbc:mysql://alsdn.iptime.org:6033/yubikeDB";
			String multipleQueryOptions =
							"?allowMultiQueries=true";
			String id	=	"yubike";
			String pw	=	"tjdgh123";

			c = DriverManager.getConnection(
					url+multipleQueryOptions,
					id,
					pw);

			System.out.println("[0] connection successful\n");

		} catch (SQLException se) {
			se.printStackTrace();
			return null;
		}

		return c;
	}

}
