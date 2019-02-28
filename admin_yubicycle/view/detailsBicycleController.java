package jj17.yubicycle.view;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
//import com.lynden.gmapsfx.MainApp2;
import com.lynden.gmapsfx.MainApp2;
import jj17.yubicycle.MainApp;
import jj17.yubicycle.model.Bicycle;
import jj17.yubicycle.query.updateQuery;
import jj17.yubicycle.util.ConnectDatabase;
import jj17.yubicycle.view.mainstageController;

/**
 * 로그인 후 자전거 버튼을 눌렸을 때에 대한 컨트롤러.
 *
 * @see detailsBicycleDialog.fxml
 * @author l4in
 *
 */
public class detailsBicycleController {

   Connection conn;




   private static boolean isRental = false;
   //private MainApp2 MainApp2;
   private static final int OKRENTALINDEX   = 1;
   private static final int INRENTALINDEX   = 2;
   private static final int INREPAIRINDEX   = 3;
   private static final int OVERDUEINDEX   = 4;

   static int sid;
   static String sname;
   static String sdept;
   static int phone;
   static public double latitude;
   static public double longitude;
   static byte[] tmpbyte;
   static BufferedImage img;
   static public int bicycleStatus;
   private mainstageController mct;
   static private int bicycleNo;
   static private int btnOK;
   private Stage dialogStage;
   private MainApp2 MainApp2;
   
   @FXML   private mapviewController mapviewController;
   @FXML   private Label selectedNumberLabel;
   @FXML   private Label selectedStatus;
   @FXML   private Label selectedSid;
   @FXML   private Label selectedSname;
   @FXML   private Label selectedSdept;
   @FXML   private Label selectedPhone;

   @FXML   private Button locationButton;
   @FXML   private Button exitButton;
   @FXML   private Button repairButton;
   @FXML   private ImageView imageST;
   Image imageStudent;
   
   private Bicycle tbc;

   public detailsBicycleController() {
      tbc = new Bicycle();
   }

   // 그전 화면으로 돌아가는 함수.
   public void setDialogStage(Stage mainApp) {
      this.dialogStage = mainApp;
   }

   /**
    * 읽어온 UID값을 통해 쿼리 수행 후 바인딩
    * @throws SQLException
    */
   @FXML
   private void initialize() throws SQLException {
	   btnOK=0;
	  bicycleNo=mainstageController.getCellText();
      findSTandBC(bicycleNo);
      selectedNumberLabel.setText(String.valueOf(bicycleNo)); // setText바인
      if (bicycleStatus == 1) {
         System.out.println("대여가능");
         selectedStatus.setText("대여 가능. ");
         locationButton.setDisable(true);
         selectedSid.setText("");
         selectedSname.setText("");
         selectedSdept.setText("");
         selectedPhone.setText(String.valueOf(""));
      } else if (bicycleStatus == 2) {
         System.out.println("대여중");
         selectedStatus.setText("대여중.");
         repairButton.setDisable(true);
         selectedSid.setText(String.valueOf(sid));
         selectedSname.setText(sname);
         selectedSdept.setText(sdept);
         selectedPhone.setText(String.valueOf(phone));
         imageST.setImage(imageStudent);
      } else if (bicycleStatus == 3) {
         System.out.println("연체중");
         selectedStatus.setText("연체중.");
         repairButton.setDisable(true);
         selectedSid.setText(String.valueOf(sid));
         selectedSname.setText(sname);
         selectedSdept.setText(sdept);
         selectedPhone.setText(String.valueOf(phone));
         imageST.setImage(imageStudent);
      } else if(bicycleStatus == 4){
         System.out.println("수리중");
         selectedStatus.setText("수리중.");
         repairButton.setText("수리 완료");
         locationButton.setDisable(true);
         selectedSid.setText("");
         selectedSname.setText("");
         selectedSdept.setText("");
         selectedPhone.setText(String.valueOf(""));
      }  else
      {
         selectedSid.setText("");
         selectedSname.setText("");
         selectedSdept.setText("");
         selectedPhone.setText(String.valueOf(""));
      }

   }




   /**
    * DB로부터 값을 가져와 Bicycle Type에 저장한다.
    * @param bicycleNo
    * @return
    * @throws SQLException
    */
   public void findSTandBC(int bicycleNo) throws SQLException {
      Connection conn = ConnectDatabase.connectToDB();
      PreparedStatement pstmt = null;
      ResultSet rset;
      String sql = "";

      try {
        sql = "select bicycleStatus from MainGateBikeData where bicycleNo=?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, bicycleNo);
        rset=pstmt.executeQuery();
        while(rset.next()){
           bicycleStatus=rset.getInt(1);
        }
         sql = "select SID,SName,SDept,SPhone, currentBikeLatitude,currentBikeLongitude,SPic from students s, MainGateBikeData b where s.SID = b.currentRentPersonSID and bicycleNo = ?";
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, bicycleNo);
         rset = pstmt.executeQuery();
         while (rset.next()) {
            sid     =   rset.getInt(1);
            sname     =   rset.getString(2);
            sdept     =   rset.getString(3);
            phone     =   rset.getInt(4);
            latitude =   rset.getDouble(5);
            longitude= rset.getDouble(6);
            tmpbyte  = rset.getBytes(7);
            img     = ImageIO.read(new ByteArrayInputStream(tmpbyte));
            ImageIO.write(img, "jpg", new File("student.jpg"));
            imageStudent = new Image(new FileInputStream("student.jpg"));
            System.out.println(phone);


         }
      } catch (Exception e) {
         e.getMessage();
      }

   }

   /**
    * 대여버튼 클릭시 쿼리를 수행하는 메소드.
    */
   @FXML
   private void locationButtonClicked() {
	 //boolean isSucceeded = updateQuery.rentalTransaction(tbc);

	 		mapviewController= new mapviewController();
	 			mapviewController.showmap();
   }

	@FXML
	private void repairConvertClicked() {
		btnOK=1;
		int exist;
		Connection conn = null;
		PreparedStatement pstmt = null;
		conn = ConnectDatabase.connectToDB();
		String sql = "update MainGateBikeData set bicycleStatus=? where bicycleNo=?";
		Alert alert = new Alert(AlertType.INFORMATION);
		if (bicycleStatus == 1) // 대여가능인 자전거를 수리중으로 바꾸는 것
		{
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, 4);
				pstmt.setInt(2, bicycleNo);
				exist = pstmt.executeUpdate();
				if (exist != 0)					System.out.println("수정완료");
				else					System.out.println("수정할 자전거 없음!");
				alert.setTitle("수리중 설정");
				alert.setHeaderText("자전거를 수리중으로 설정하였습니다.");
				alert.showAndWait();
				dialogStage.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else // 수리중인 자전거를 대여가능으로 바꾸는것
		{
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, 1);
				pstmt.setInt(2, bicycleNo);
				exist = pstmt.executeUpdate();
				if (exist != 0)					System.out.println("수정완료");
				else					System.out.println("수정할 자전거 없음!");
				alert.setTitle("수리 완료");
				alert.setHeaderText("자전거를 대여 가능으로 설정하였습니다.");
				alert.showAndWait();
				dialogStage.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
   

	static public int returnStatus() {
		return bicycleStatus;
	}
	static public int returnBicycleNo() {
		return bicycleNo;
	}
	static public int returnbtnOK() {
		return btnOK;
	}
   

   /**
    *
    * @return
    */
//   public static boolean getIsRental() {
//      return isRental;
//   }
   /**
    * 버튼 누르면 꺼지도록 작업하는 메소드.
    */
   @FXML
   private void closeButtonClicked() {
      dialogStage.close();
   }
   @FXML
   private void ConvertClicked() {
	 
	   dialogStage.close();
	

   }
	public double getLatitude() {
		//System.out.println(latitude);
		return latitude;
	}

	public double getLongitude() {
		//System.out.println(longitude);
		return longitude;
	}
	public static boolean getIsRental() {
		// TODO Auto-generated method stub
		return isRental;
	}

}