package jj17.yubicycle.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Vector;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import javafx.stage.Modality;
import javafx.stage.Stage;

import jj17.yubicycle.MainApp;
import jj17.yubicycle.model.BicycleStatus;
import jj17.yubicycle.model.StudentData;
import jj17.yubicycle.util.ConnectDatabase;
import jj17.yubicycle.util.LoginStatus;


/**
 * 메인화면 컨트롤러. 메인화면에서 무슨동작을 할지 여기서 구현해야함.
 *
 * Task를 extend한 것은 해당 동작이 백그라운드에서 계속돌게끔 한 것이다!
 * 이후 Initialize에서 돌리면 잘 돌아간다...
 *
 * @author Administrator
 *
 * gridpane 요소 몇번을 눌렀는지 자동으로 인식하게 하기! (성공)
 */
public class mainstageController implements Initializable {


	static boolean ok= false;
	private boolean isLogin = false;

	Date DueDate = null;

	static Connection conn;

	// 스레드 돌리면서 시간을 1초마다 한번씩 불러오는 플래그.
	private boolean stop = false;

	// 학생 데이터를 읽어오는 StudentData 객체.
	private StudentData loadedStudentData = new StudentData();


	/* 여기서만...
	 * 대여가능: 0, 대여중: 1, 수리중: 2, 연체중: 3*/
	private static final int OKRENTALINDEX	= 0;
	private static final int INRENTALINDEX	= 1;
	private static final int OVERDUEINDEX	= 2;
	private static final int INREPAIRINDEX	= 3;


	/**
	 * 사용자 정보를 알려주는 Label
	 */
	@FXML private Label nameLabel;
	@FXML private Label idLabel;
	@FXML private Label deptLabel;
	@FXML private Label isRentalLabel;


	@FXML private Label nameSTLabel;
	@FXML private Label idSTLabel;
	@FXML private Label deptSTLabel;
	@FXML public Label isRentalSTLabel;
	@FXML private ImageView imageST;
	@FXML private ImageView yuicon;
	Image imageStudent;
	Image imageIcon;
	private ArrayList<Button> list;


	// 대여가능, 대여중, 수리중, 연체중 Label
	@FXML private Label okRentalLabel;
	@FXML private Label inRentalLabel;
	@FXML private Label inRepairLabel;
	@FXML private Label overdueLabel;


	/* 버튼값 바인딩 */
	@FXML private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10;
	@FXML private Button btn11, btn12, btn13, btn14, btn15, btn16, btn17, btn18, btn19, btn20;
	@FXML private Button btn21, btn22, btn23, btn24, btn25, btn26, btn27, btn28, btn29, btn30;
	@FXML private Button btn31, btn32, btn33, btn34, btn35, btn36, btn37, btn38, btn39, btn40;
	@FXML private Button btn41, btn42, btn43, btn44, btn45, btn46, btn47, btn48, btn49, btn50;
	@FXML private Button btn51, btn52, btn53, btn54, btn55, btn56, btn57, btn58, btn59, btn60;
	@FXML private Button btn61, btn62, btn63, btn64, btn65, btn66, btn67, btn68, btn69, btn70;
	@FXML private Button btn71, btn72, btn73, btn74, btn75, btn76, btn77, btn78, btn79, btn80;

	// 현재시간
	@FXML private Label lblTime;

	// 숫자 표현을 위한 GridPane
	@FXML private GridPane gridPane;

	// 로그인 버튼, 반납 버튼
	@FXML private Button loginButton;
	@FXML private Button returnButton;

	// 마우스를 가져다 댄 번호이다.
	private static String bicycleNo;

	private MainApp mainApp;

	static ObservableList<BicycleStatus> bicycleData = FXCollections.observableArrayList();

	private static Vector<Integer> dbValue;

	private BicycleStatus bicycleStatus = null;

	public mainstageController() {
	}

	/*public StudentData getLoginStudentData()	 {
		return loadedStudentData;
	}*/

	private void hookupChangeListeners() {

		bicycleStatus.okRentalProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldVal, Number newVal) {
				System.out.println("값 바뀜 감지됨!");
				okRentalLabel.setText(String.valueOf(dbValue.get(OKRENTALINDEX)));
			}
		});

		bicycleStatus.inRentalProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldVal, Number newVal) {
				System.out.println("값 바뀜 감지됨!");
				inRentalLabel.setText(String.valueOf(dbValue.get(INRENTALINDEX)));
			}
		});

		bicycleStatus.inRepairProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldVal, Number newVal) {
				System.out.println("값 바뀜 감지됨!");
				inRepairLabel.setText(String.valueOf(dbValue.get(INREPAIRINDEX)));
			}
		});

		bicycleStatus.overdueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldVal, Number newVal) {
				System.out.println("값 바뀜 감지됨!");
				overdueLabel.setText(String.valueOf(dbValue.get(OVERDUEINDEX)));
			}
		});
	}

	/**
	 * initialize메소드는 컨트롤러 객체가 생성되고 나서 호출됩니다.
	 * 해당 메소드에는 주로 UI 컨트롤의 초기화, 이벤트 핸들러 등록,
	 * 속성 감시 등의 코드가 작성됩니다.
	 *
	 * 다만 IntegerProperty와 DoubleProperty에는 반드시 추가로 asObject()를 이용해야 합니다:
	 *
	 * myIntegerColumn.setCellValueFactory(cellData -> cellData.getValue().myIntegerProperty().asObject());
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			imageIcon = new Image(new FileInputStream("signature_kv.png"));
			yuicon.setImage(imageIcon);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		conn = ConnectDatabase.connectToDB();
		System.out.println("initialising...");
		if(ok==false) {
			setBtnColor();ok=true;
		}
		else ok=false;
	}


	/**
	 * 바인딩.
	 */
	private void ShowLabelValues() {

		dbValue = getBicycleData();

	}


	/**
	 * MainApp에서 불리고난 후에 실행되는 소스코드들.
	 *
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

		ShowLabelValues();
		currentTimer();

		conn = null;

		bicycleStatus = new BicycleStatus(
				dbValue.get(OKRENTALINDEX),
				dbValue.get(INRENTALINDEX),
				dbValue.get(INREPAIRINDEX),
				dbValue.get(OVERDUEINDEX));

		hookupChangeListeners();

		okRentalLabel.setText(String.valueOf(dbValue.get(OKRENTALINDEX)));
		inRentalLabel.setText(String.valueOf(dbValue.get(INRENTALINDEX)));
		inRepairLabel.setText(String.valueOf(dbValue.get(INREPAIRINDEX)));
		overdueLabel.setText(String.valueOf(dbValue.get(OVERDUEINDEX)));




	}


	/**
	 * 메인화면에 현재 시간을 측정해주는 함수. 1초마다 한번씩 새로불러온다.
	 *
	 */
	private void currentTimer() {

		Thread thread = new Thread() {
            @Override
            public void run() {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

                while (!stop) {
                    String strTime = sdf.format(new Date());
                    Platform.runLater(() -> {
                        lblTime.setText(strTime);
                    });
                    try { Thread.sleep(100); } catch (InterruptedException e) {}
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
	}


	/**
	 * 로그인 버튼 클릭하면 작동하는 함수.
	 * @throws FileNotFoundException
	 */
	@FXML
	private void handleLoginButton() throws FileNotFoundException {

		// 로그인했으면 버튼 못 누르게.
		// 로그인 컨트롤러에 값 하나 때려박자..
		// 로그인 되는지 안되는지 점검.
		if(isLogin) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("!");
            alert.initOwner(mainApp.getPrimaryStage());
			alert.setHeaderText("에러 발생: 로그인 처리됨");
			alert.setContentText("이미 로그인 되어있습니다.");

			alert.showAndWait();
			System.out.println("로그인 하면 여기를 거친다.");
		}

		else {
			System.out.println("로그인 안했으면 여기를 거친다.");

			mainApp.showLoginDialog(); // 로그인 창 불러온다!
			loadedStudentData = loginController.getStudentDataFromRFID();

			// 로드된 학생정보가 없다면? 에 대한 예외처리부터...
			if(loadedStudentData != null) {
				isLogin = true;

				imageStudent = new Image(new FileInputStream("student.jpg"));


				// 로드된 학생정보가 있다면 값을 넣어주고,
				nameSTLabel.setText(loadedStudentData.getSname());
				idSTLabel.setText(String.valueOf(loadedStudentData.getSid()));
				deptSTLabel.setText(loadedStudentData.getsDept());
				imageST.setImage(imageStudent);


				// 그 불러온 데이터의 isRental값이 0이면 대여가능.
				if(loadedStudentData.getIsRental()==0) {
					isRentalSTLabel.setText("대여 가능");
				}

				else {
					Connection conn = null;
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					String sql = "select DueDate from MainGateBikeData where currentRentPersonSID = ?";

					try {
						conn = ConnectDatabase.connectToDB();
						pstmt = conn.prepareStatement(sql);
						pstmt.setInt(1, loadedStudentData.getSid());
						rs = pstmt.executeQuery();
						while (rs.next()) {
							DueDate = rs.getDate(1);
						}
					} catch (Exception e) {
						e.getMessage();
					}
					isRentalSTLabel.setText("대여 중 (" + DueDate+")");
				}

			}

			/**
			 * 여기서부터 바인딩 새로함.
			 */
			else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("!");
				alert.setHeaderText("에러 발생: 로그인 실패");
				alert.setContentText("관리자에게 문의해 주세요.");

				alert.showAndWait();
			}
		}


	}

	/**
	 * 반납 버튼.
	 */
	@FXML
	private void handleReturnButton() {
		// 로그인 안했을 때의 예외처리
		if(LoginStatus.getSHA256Session().length() != LoginStatus.SHA256LENGTH) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("로그인 필요");
            alert.initOwner(mainApp.getPrimaryStage());
			alert.setHeaderText("로그인이 필요합니다.");
			alert.setContentText("로그인 후 이용해 주세요.");

			alert.showAndWait();
		}


		else {

			if (loadedStudentData.getIsRental() == 0) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("경고창");
	            alert.initOwner(mainApp.getPrimaryStage());
				alert.setHeaderText("대여 중이지 않습니다.");
				alert.showAndWait();
			}

			else {
				mainApp.showReturnDialog();

			}

			if(returnController.getIsReturn()==true ) {
				isRentalSTLabel.setText("대여 가능");

				/**
				 * 값을 추가제거하는 리스너를 도저히 못찾겠어서 이딴식으로 밖에 못했다.
				 **/
				dbValue.set(OKRENTALINDEX, dbValue.get(OKRENTALINDEX)+1);
				dbValue.set(INRENTALINDEX, dbValue.get(INRENTALINDEX)-1);

				System.out.println(dbValue.get(OKRENTALINDEX).toString());
				System.out.println(dbValue.get(INRENTALINDEX).toString());

//				isRentalSTLabel.setText("대여 중 (" + DueDate+")");

				okRentalLabel.setText(String.valueOf(dbValue.get(OKRENTALINDEX)));
				inRentalLabel.setText(String.valueOf(dbValue.get(INRENTALINDEX)));
				inRepairLabel.setText(String.valueOf(dbValue.get(INREPAIRINDEX)));
				overdueLabel.setText(String.valueOf(dbValue.get(OVERDUEINDEX)));

			}
		}
	}


	/**
	 * 대여 메소드.
	 * 자전거 번호 누르면 그에따라 번호가 출력되는 메소드.
	 */
	@FXML
	public void handleNumberDialog() {

			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(MainApp.class.getResource("view/detailsBicycleDialog.fxml"));
				AnchorPane page = (AnchorPane) loader.load();

				Stage dialogStage = new Stage();
				dialogStage.setTitle("select bicycle");
				dialogStage.initModality(Modality.WINDOW_MODAL);
		        //dialogStage.initOwner(mainApp.getPrimaryStage());
				Scene scene = new Scene(page);
				dialogStage.setScene(scene);

				detailsBicycleController controller = loader.getController();
				controller.setDialogStage(dialogStage);

				dialogStage.showAndWait();

			} catch (Exception ex) {
				ex.printStackTrace();
			}
			/**
			 * 값을 추가제거하는 리스너를 도저히 못찾겠어서 이딴식으로 밖에 못했다.
			 */
			int status = detailsBicycleController.returnStatus();
			int bicycleNo=detailsBicycleController.returnBicycleNo();
			int btnOK=detailsBicycleController.returnbtnOK();
		if (btnOK == 1) {
			if (status == 1) { // 수리중으로 바꾸는것
				changeBtnColor(bicycleNo, status);
				dbValue.set(INREPAIRINDEX, dbValue.get(INREPAIRINDEX) + 1);
				dbValue.set(OKRENTALINDEX, dbValue.get(OKRENTALINDEX) - 1);
				inRepairLabel.setText(String.valueOf(dbValue.get(INREPAIRINDEX)));
				okRentalLabel.setText(String.valueOf(dbValue.get(OKRENTALINDEX)));

			} else if (status == 4) { // 대여가능으로 바꾸는것
				changeBtnColor(bicycleNo, status);
				dbValue.set(INREPAIRINDEX, dbValue.get(INREPAIRINDEX) - 1);
				dbValue.set(OKRENTALINDEX, dbValue.get(OKRENTALINDEX) + 1);
				inRepairLabel.setText(String.valueOf(dbValue.get(INREPAIRINDEX)));
				okRentalLabel.setText(String.valueOf(dbValue.get(OKRENTALINDEX)));

			}
		}
			else {
				return;
			}
			/*if(detailsBicycleController.getIsRental() == true) {


				Date testdate = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String test = formatter.format(testdate);

				System.out.println(testdate);
//
				isRentalSTLabel.setText("대여 중 (" + test + ")");
//
//				System.out.println(dbValue.get(OKRENTALINDEX).toString());
//				System.out.println(dbValue.get(INRENTALINDEX).toString());

//				okRentalLabel.setText(String.valueOf(dbValue.get(OKRENTALINDEX)));
//				inRentalLabel.setText(String.valueOf(dbValue.get(INRENTALINDEX)));

				okRentalLabel.setText(String.valueOf(dbValue.get(OKRENTALINDEX)));
				inRentalLabel.setText(String.valueOf(dbValue.get(INRENTALINDEX)));
				inRepairLabel.setText(String.valueOf(dbValue.get(INREPAIRINDEX)));
				overdueLabel.setText(String.valueOf(dbValue.get(OVERDUEINDEX)));


				// TODO: DB 갱신해주는 소스코드만 넣으면 정상작동한다.
			}*/

		}
	

	/**
	 * 마우스를 가져다대면 해당 Button의 번호를 가져온다.
	 * @param e
	 */
	@FXML
	public void getCell(MouseEvent e) {
		bicycleNo = ((Button)e.getSource()).getText();

		System.out.println(bicycleNo);
	}

	/**
	 * Button의 번호를 가져오는 getter
	 * @return
	 */
	public static int getCellText() {
		return Integer.parseInt(bicycleNo);
	}


	/**
	 * 자전거 현황에 대한 Vector<Integer>를 반환한다.
	 * https://stackoverflow.com/questions/24805951/how-to-use-fxml-controller-to-retrieve-data-from-database-and-populate-a-tablevi
	 * https://stackoverflow.com/questions/10797794/multiple-queries-executed-in-java-in-single-statement
	 * @return
	 */
	public static Vector<Integer> getBicycleData() {

		Connection conn = ConnectDatabase.connectToDB();
		Statement stmt 	= null;
		ResultSet rs	= null;

		// 숫자 담는 벡터 생성
		Vector<Integer> VQueryResult = new Vector<>();

		try {

		// 여러쿼리를 동시에 보낸다
		String findST1 = "SELECT COUNT(bicycleStatus) FROM MainGateBikeData WHERE bicycleStatus = 1; ";
		String findST2 = "SELECT COUNT(bicycleStatus) FROM MainGateBikeData WHERE bicycleStatus = 2; ";
		String findST3 = "SELECT COUNT(bicycleStatus) FROM MainGateBikeData WHERE bicycleStatus = 3; ";
		String findST4 = "SELECT COUNT(bicycleStatus) FROM MainGateBikeData WHERE bicycleStatus = 4";

		String selectSQL = findST1 + findST2 + findST3 + findST4;

		stmt = conn.createStatement();
		boolean hasMoreResultSets = stmt.execute( selectSQL );

		// 여러쿼리에 대한 처리구문
		READING_QUERY_RESULTS:
			while( hasMoreResultSets || stmt.getUpdateCount() != -1 ) {
				if (hasMoreResultSets ) {
					rs = stmt.getResultSet();
				}
				else {
					int queryResult = stmt.getUpdateCount();
					if ( queryResult == -1 ) {
						break READING_QUERY_RESULTS;
					}
				}

				// 값을 벡터에 저장
				while(rs.next()) {
					String bsdata = rs.getString(1);

					VQueryResult.add(new Integer(Integer.parseInt(bsdata)));
				}

				hasMoreResultSets = stmt.getMoreResults();
			}

		// 저장된 벡터 속의 값은 수행한 쿼리순서대로 저장되어있다.
		for(int i=0; i<VQueryResult.size(); i++) {
			System.out.println(VQueryResult.get(i));
		}

		bicycleData.add(
				new BicycleStatus (
				VQueryResult.get(0),
				VQueryResult.get(1),
				VQueryResult.get(2),
				VQueryResult.get(3) )
			);

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {	conn.close();	} catch(Exception e) { e.printStackTrace(); }
			try {	stmt.close();	} catch(Exception e) { e.printStackTrace(); }
			try {	rs.close();		} catch(Exception e) { e.printStackTrace(); }
		}

		return VQueryResult;
	}

	public void changeBtnColor(int changedBicycleNo, int status) {
		changedBicycleNo = changedBicycleNo - 1;
		if (status == 1) {
			(list.get(changedBicycleNo)).getStylesheets().clear();
			(list.get(changedBicycleNo)).getStylesheets().add(getClass().getResource("BtnGrey.css").toExternalForm());
			System.out.println("회색으로 변경!!!");
		} else {
			(list.get(changedBicycleNo)).getStylesheets().clear();
			(list.get(changedBicycleNo)).getStylesheets().add(getClass().getResource("BtnGreen.css").toExternalForm());
			System.out.println("초록색으로 변경!!!");
		}
	}

	/**
	 * 버튼에 색깔넣는 메소드.
	 */
	public void setBtnColor() {

	      list = new ArrayList<Button>();
	      int[] arr = new int[80];         // arr는 자전거의 상태를 저장하는 배열
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      String sql = " select bicycleStatus from MainGateBikeData where bicycleNo= ? ";

	      try {
	         conn = ConnectDatabase.connectToDB();
	         for (int i = 0; i < 80; i++) {
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setInt(1, i + 1); // 1~80까지 넣으면서 쿼리진행
	            rs = pstmt.executeQuery();
	            while(rs.next()) {
	               arr[i] = rs.getInt(1);
//	               System.out.print(arr[i]);
	            }
	         }
	      } catch (SQLException e) {
	         e.printStackTrace();
	      }
	      list.add(btn1);   list.add(btn2);  list.add(btn3);  list.add(btn4);  list.add(btn5);  list.add(btn6);  list.add(btn7);  list.add(btn8);  list.add(btn9);  list.add(btn10);
	      list.add(btn11);  list.add(btn12); list.add(btn13); list.add(btn14); list.add(btn15); list.add(btn16); list.add(btn17); list.add(btn18); list.add(btn19); list.add(btn20);
	      list.add(btn21);  list.add(btn22); list.add(btn23); list.add(btn24); list.add(btn25); list.add(btn26); list.add(btn27); list.add(btn28); list.add(btn29); list.add(btn30);
	      list.add(btn31);  list.add(btn32); list.add(btn33); list.add(btn34); list.add(btn35); list.add(btn36); list.add(btn37); list.add(btn38); list.add(btn39); list.add(btn40);
	      list.add(btn41);  list.add(btn42); list.add(btn43); list.add(btn44); list.add(btn45); list.add(btn46); list.add(btn47); list.add(btn48); list.add(btn49); list.add(btn50);
	      list.add(btn51);  list.add(btn52); list.add(btn53); list.add(btn54); list.add(btn55); list.add(btn56); list.add(btn57); list.add(btn58); list.add(btn59); list.add(btn60);
	      list.add(btn61);  list.add(btn62); list.add(btn63); list.add(btn64); list.add(btn65); list.add(btn66); list.add(btn67); list.add(btn68); list.add(btn69); list.add(btn70);
	      list.add(btn71);  list.add(btn72); list.add(btn73); list.add(btn74); list.add(btn75); list.add(btn76); list.add(btn77); list.add(btn78); list.add(btn79); list.add(btn80);



	      for(int i=0; i<80; i++) {
	         if(arr[i]==1)          (list.get(i)).getStylesheets().add(getClass().getResource("BtnGreen.css").toExternalForm());
	         else if (arr[i]==2)    (list.get(i)).getStylesheets().add(getClass().getResource("BtnRed.css").toExternalForm());
	         else if (arr[i]==3)      (list.get(i)).getStylesheets().add(getClass().getResource("BtnYellow.css").toExternalForm());
	         else                (list.get(i)).getStylesheets().add(getClass().getResource("BtnGrey.css").toExternalForm());
	      }

	   }

}