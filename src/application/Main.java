package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application 
{
	private Stage primaryStage;
    private AnchorPane rootLayout;
    
    @FXML
    private Button myButton;
    
    @Override
    public void start(Stage primaryStage) 
    {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Inventory Management");

        initRootLayout();
    }
    
    /**
     * Initializes the root layout.
     */
    public void initRootLayout() 
    {
        try 
        {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/RootLayout.fxml"));
            rootLayout = (AnchorPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout, 1000, 700);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        } 
        catch (IOException e) 
        {
        	e.printStackTrace();
	    }
    }

    /**
	 * Returns the main stage.
	 * @return
	 */
    public Stage getPrimaryStage() 
    {
    	return primaryStage;
	}

	public static void main(String[] args) 
	{
		launch(args);
	}
}
