package jj17.yubicycle;

import jj17.yubicycle.model.BicycleStatus;
import jj17.yubicycle.view.loginController;
import jj17.yubicycle.view.mainstageController;
import jj17.yubicycle.view.returnController;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ListResourceBundle;
import java.util.Vector;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class MainApp extends Application {

	Connection conn;

	private Stage primaryStage;
	private BorderPane rootLayout;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("The YU Bicycle rental system");

		initRootLayout();

		showMainPage();
	}
	 private static class ResourceWrapper extends ListResourceBundle {
	        @Override
	        protected Object[][] getContents() {
	            return new Object[0][];
	        }
	    }
	/**
	 * 루트 레이아웃 생성
	 * 여기를 기반으로 메인페이지가 뜬다!
	 * 아마 이거때문에 메인페이지가 안뜨는거일지도 모른다...
	 */
	public void initRootLayout() {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setResources(new ResourceWrapper());
			loader.setLocation(MainApp.class.getResource("view/rootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 메인페이지 뷰어를 여기다가 세팅, 컨트롤러 세팅
	 * 스레드를 돌려서 현재시간 보여주게 하기
	 */
	public void showMainPage() {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setResources(new ResourceWrapper());
			loader.setLocation(MainApp.class.getResource("view/mainstage.fxml"));
			AnchorPane mainpage = (AnchorPane) loader.load();
			rootLayout.setCenter(mainpage);

			mainstageController controller = loader.getController();
			controller.setMainApp(this);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 로그인 다이얼로그를 띄우는 컨트롤러.
	 * 그 다음 스테이지인 로그인창 띄우는 과정을 갖다가 작업해준다.
	 */
	public void showLoginDialog() {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/loginDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("login");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(primaryStage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        loginController controller = loader.getController();
	        controller.setDialogStage(dialogStage);

	        dialogStage.showAndWait();

	        System.out.println("postmortem");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 반납관련 다이얼로그.
	 */
	public void showReturnDialog() {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/returnDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("register");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(primaryStage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        returnController controller = loader.getController();
	        controller.setDialogStage(dialogStage);

	        dialogStage.showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * MainApp으로 돌아오게하는 메소드
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	/**
	 * 자전거 현황에 대한 Vector<Integer>를 반환한다.
	 * https://stackoverflow.com/questions/24805951/how-to-use-fxml-controller-to-retrieve-data-from-database-and-populate-a-tablevi
	 * https://stackoverflow.com/questions/10797794/multiple-queries-executed-in-java-in-single-statement
	 * @return
	 */
	public Vector<Integer> getBicycleData(Connection conn) {

		Statement stmt 	= null;
		ResultSet rs	= null;

		ObservableList<BicycleStatus> bicycleData = FXCollections.observableArrayList();

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

	/**
	 * 메인함수는 여기있다!
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
