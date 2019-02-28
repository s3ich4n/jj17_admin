package jj17.yubicycle.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jj17.yubicycle.MainApp;

import com.lynden.gmapsfx.MainApp2;

public class mapviewController {
	private Stage dialogStage;
	private MainApp2 MainApp2;


	@FXML
	public void showmap()
	{
		try {
			
//			FXMLLoader loader = new FXMLLoader();
//			loader.setLocation(MainApp.class.getResource("view/mapview.fxml"));
//			AnchorPane page = (AnchorPane) loader.load();
//
//			Stage dialogStage = new Stage();
//			dialogStage.setTitle("select bicycle");
//			dialogStage.initModality(Modality.WINDOW_MODAL);
//			//dialogStage.initOwner();
//			Scene scene = new Scene(page);
//			dialogStage.setScene(scene);
//			dialogStage.showAndWait();

			MainApp2=new MainApp2();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/mapview.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			Stage stage = new Stage();

//			System.setProperty("java.net.useSystemProxies","true");
			MainApp2.start(stage);
//	         MainApp2.mapInitialized();
//			stage.showAndWait();
}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

}
