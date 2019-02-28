package jj17.yubicycle.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.util.Duration;

import jj17.yubicycle.util.getRFIDvalue;
import jj17.yubicycle.util.LoginStatus;
import jj17.yubicycle.model.StudentData;
import jj17.yubicycle.query.selectQuery;

/**
 * 로그인창 컨트롤러. 로그인 창 내에서 무슨 동작을 할지를 여기서 구현해야함.
 */
public class loginController {

	private Stage dialogStage;
	private String RFIDValue = "test";

	// RFID값 가져오는 로직
	private getRFIDvalue getRFID = new getRFIDvalue();

	public static StudentData loadedStudentData = null;

	private StudentData test123 = null;

	ObservableList<StudentData> loadedStudentData2 = FXCollections.observableArrayList();

	@FXML
	private void initialize() {

	}

	public ObservableList<StudentData> getStudentData() {
		return loadedStudentData2;
	}
	/**
	 * 메인에서 불러올 스테이지.
	 * setDialogStage를 부르기때문에
	 * 시작과 동시에 실행하는 효과를 지닌다!
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;

		//while15seconds();
	}

	/**
	 * RFID 값 가져오기 버튼!
	 * 값을 가져오면 로그인 성공창을, 그렇지 못한다면 로그인 실패창을 띄운다.
	 *
	 * @return true: 값을 가져옴, false: 값을 못가져옴
	 */
	@FXML
	public void onClickedRFID() {
		LoginStatus ls = new LoginStatus();

		rfidAction();
		if(RFIDValue == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("로그인 실패");
			alert.setHeaderText("로그인에 실패하였습니다.");
			alert.setContentText("로그인 버튼을 눌러 다시 시도해 주세요.");

			alert.showAndWait();
			dialogStage.close();
		}
		else {
			// 학생 데이터를 가져오고, SHA256 세션을 생성한다.
			loadedStudentData = selectQuery.doSelectQuery(RFIDValue);
			System.out.println(loadedStudentData.getUid());
			ls.setSHA256Session(loadedStudentData.getUid());
			ls.MakeSHA256Session();

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("로그인 성공");
			alert.setHeaderText("로그인 성공 :)");
			alert.setContentText("로그인에 성공하였습니다.");

			alert.showAndWait();
			dialogStage.close();
		}
	}


	/**
	 * RFID 관련 메소드.
	 * 이걸 눌러야 그때부터 UID 값을 가져온다.
	 *
	 *
	 * @author l4in
	 */
	public String rfidAction() {
		RFIDValue = getRFID.getUid();

		return RFIDValue;
	}


	/**
	 * RFID값 로드하는 로직에서 StudentData를 로드함.
	 * @return
	 */
	public static StudentData getStudentDataFromRFID() {
		return loadedStudentData;
	}

}