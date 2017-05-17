package application.controller;

import java.io.IOException;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ItemDetailController 
{
	//Called before all FXML members have been injected
	public ItemDetailController()
	{
		
	}
	
	//Called after all FXML members have been injected
	public void initialize()
	{
		
	}
	
	@FXML
	public void launchAddBarcode()
	{
		 try 
	     {
			 Stage stage = new Stage();
	        	
			 // Load root layout from fxml file.
	         FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/AddBarcodeLayout.fxml"));
	         AnchorPane itemDetailLayout = (AnchorPane) loader.load();

	         Scene scene = new Scene(itemDetailLayout, 522, 200);
	         stage.setScene(scene);
	         stage.show();
	     } 
	     catch (IOException e) 
	     {
	    	 e.printStackTrace();
		 }
	}
}
