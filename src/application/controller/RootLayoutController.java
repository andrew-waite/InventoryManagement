package application.controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.Main;
import application.TableRowData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class RootLayoutController extends StackPane
{
	@FXML
	private Button asdf;
	
	@FXML 
	private TableView<TableRowData> tableView;
	
	@FXML
	private TableColumn<TableRowData, Integer> tableColumnReferenceNumber;
	
	@FXML
	private TableColumn<TableRowData, String> tableColumnDescription;
	
	//Called before all FXML members have been injected
	public RootLayoutController()
	{
		
	}
	
	//Called after all FXML members have been injected
	public void initialize()
	{
		Statement statement;
		ResultSet resultSet;
		
		try 
		{
			ObservableList<TableRowData> data = FXCollections.observableArrayList();
			
			statement = Main.getDatabase().getConnection().createStatement();
			resultSet = statement.executeQuery("SELECT * FROM Records");
			
			while(resultSet.next())
			{
				data.add(new TableRowData(resultSet.getInt("id"), resultSet.getString("description")));
			}
			
			tableColumnReferenceNumber.setCellValueFactory(new PropertyValueFactory<TableRowData, Integer>("referenceNumber"));
			tableColumnDescription.setCellValueFactory(new PropertyValueFactory<TableRowData, String>("description"));
			
			tableView.setItems(data);
		} 
		catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@FXML
	private void buttonAddItem(ActionEvent event)
	{
        try 
        {
        	Stage stage = new Stage();
        	
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/ItemDetailLayout.fxml"));
            AnchorPane rootLayout = (AnchorPane) loader.load();

            Scene scene = new Scene(rootLayout, 1000, 700);
            stage.setScene(scene);
            stage.show();
        } 
        catch (IOException e) 
        {
        	e.printStackTrace();
	    }
	}
}
