package jj17.yubicycle.view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import jj17.yubicycle.query.updateQuery;
import jj17.yubicycle.util.ConnectDatabase;

/**
 * 반납 컨트롤러.
 * @author Administrator
 *
 */
public class returnController {

	private Stage dialogStage;

	private static boolean isReturn = false;
	private int loginStudentSid;
	@FXML private Label returnBicycleNo;

	@FXML private Button doReturnButton;
	@FXML private Button exitButton;


	@FXML
	private void initialize() {
		loginStudentSid=loginController.getStudentDataFromRFID().getSid();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql="select bicycleNo from MainGateBikeData where currentRentPersonSID = ? ";
		try{
			conn = ConnectDatabase.connectToDB();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, loginStudentSid);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				returnBicycleNo.setText(String.valueOf(rs.getInt(1)));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 메인에서 불러올 스테이지.
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}


	/**
	 * 반납버튼 클릭시 쿼리를 수행하는 메소드.
	 * 만일 DB에 저장된 반납일과 프로그램이 돌고있는 날짜가 다르다면
	 * 연체자 처리를 수행한다.
	 *
	 */
	@FXML
	private void doReturnButtonClicked() {

		boolean isSucceeded = updateQuery.returnTransaction();

		if (isSucceeded) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("반납 성공");
			alert.setHeaderText("반납 성공 :)");
			alert.setContentText("반납에 성공하였습니다. 이용해 주셔서 감사합니다.");
			alert.showAndWait();

			isReturn = true;
			dialogStage.close();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("반납 실패");
			alert.setHeaderText("반납에 실패하였습니다.");
			alert.setContentText("관리자에게 문의해 주세요. :(");

			alert.showAndWait();
			dialogStage.close();
		}
	}

	/**
	 *
	 * @return
	 */
	public static boolean getIsReturn() {
		return isReturn;
	}


	/**
	 * 버튼 누르면 꺼지도록 작업하는 메소드.
	 */
	@FXML
	private void closeButtonClicked(){
	    dialogStage.close();
	}
}