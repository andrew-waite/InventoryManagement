package application.controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.Main;
import application.model.Context;
import application.model.TableRowData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
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
	
	@FXML
	private TextArea searchField;
	
	private int currentDeletedRow = 0;
	
	
	//Called before all FXML members have been injected
	public RootLayoutController()
	{
		
	}
	
	//Called after all FXML members have been injected
	public void initialize()
	{
		this.focusListener();
		this.searchFieldListener();
	}
	
	public void focusListener()
	{
		Context.getInstance().getStageManager().getCurrentStage().focusedProperty().addListener(new ChangeListener<Boolean>()
		{
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2)
			{
				if(arg2)
				{
					refreshTable();
				}
			}
		});
	}
	
	public void refreshTable()
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
				data.add(new TableRowData(resultSet.getInt("id"), resultSet.getString("title")));
			}
			
			tableColumnReferenceNumber.setCellValueFactory(new PropertyValueFactory<TableRowData, Integer>("referenceNumber"));
			tableColumnDescription.setCellValueFactory(new PropertyValueFactory<TableRowData, String>("description"));
			
			tableView.setItems(data);
			
			this.setTableRowListener();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void setTableRowListener()
	{
		tableView.setRowFactory( tv -> {
		    TableRow<TableRowData> row = new TableRow<>();
		    row.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 2 && (! row.isEmpty()) ) 
		        {
		        	TableRowData rowData = row.getItem();
		            launchDetailedView(rowData.getReferenceNumber());
		        }
		        else if (event.getClickCount() == 1 && (! row.isEmpty()) ) 
		        {
		        	TableRowData rowData = row.getItem();
		        	this.currentDeletedRow = rowData.getReferenceNumber();
		        }
		    });
		    return row ;
		});
	}
	
	public void searchFieldListener()
	{
		this.searchField.setOnKeyPressed(keyTyped ->
		{
			if(keyTyped.getCode() == KeyCode.BACK_SPACE)
			{
				//TODO: Find a better solution, this will throw a IndexOutOFBoundsException when there is now text in the TextArea
				searchDatabase(this.searchField.getText().substring(0, this.searchField.getText().length() - 1));
			}
			else
			{
				//System.out.println(this.searchField.getText() + keyTyped.getText());
				
				//Add the current character has the TextArea is not updated with it yet when this event fires
				searchDatabase(this.searchField.getText() + keyTyped.getText());
			}
		});
	}
	
	public void searchDatabase(String string)
	{
		try
		{
			Statement statement = Main.getDatabase().getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM Records WHERE title LIKE '%" + string + "%';");			

			ObservableList<TableRowData> data = FXCollections.observableArrayList();
			
			while(resultSet.next())
			{
				data.add(new TableRowData(resultSet.getInt("id"), resultSet.getString("title")));
			}
			
			this.tableColumnReferenceNumber.setCellValueFactory(new PropertyValueFactory<TableRowData, Integer>("referenceNumber"));
			this.tableColumnDescription.setCellValueFactory(new PropertyValueFactory<TableRowData, String>("description"));
			
			this.tableView.setItems(data);
			
			this.tableView.refresh();
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
		
	public void launchDetailedView(int id)
	{
		Context.getInstance().setCurrentPrimaryKey(id);
		
        try 
        {
        	Stage stage = new Stage();
        	
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/ItemDetailLayout.fxml"));
            AnchorPane itemDetailLayout = (AnchorPane) loader.load();

            Scene scene = new Scene(itemDetailLayout, 1000, 700);
            stage.setScene(scene);
            stage.show();
            
            Context.getInstance().getStageManager().getStages().getFirst().hide();
            Context.getInstance().getStageManager().addStage(stage);
        } 
        catch (IOException e) 
        {
        	e.printStackTrace();
	    }
	}

	@FXML
	private void buttonAddItem(ActionEvent event)
	{
		int id = -1;

		Context.getInstance().setCurrentPrimaryKey(id);
		
        try 
        {
        	Stage stage = new Stage();
        	
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/ItemDetailLayout.fxml"));
            AnchorPane itemDetailLayout = (AnchorPane) loader.load();

            Scene scene = new Scene(itemDetailLayout, 1000, 700);
            stage.setScene(scene);
            stage.show();
            
            Context.getInstance().getStageManager().getStages().getFirst().hide();
            Context.getInstance().getStageManager().addStage(stage);
        } 
        catch (IOException e) 
        {
        	e.printStackTrace();
	    }
	}
	
	@FXML
	private void buttonRemoveItem(ActionEvent event)
	{
		try
		{
			Statement statement = Main.getDatabase().getConnection().createStatement();
			statement.execute("DELETE FROM Records WHERE id=" + this.currentDeletedRow + ";");
			
			Statement statement_barcode = Main.getDatabase().getConnection().createStatement();
			statement_barcode.execute("DELETE FROM BarcodeNumbers WHERE barcodesID=" + this.currentDeletedRow + ";");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		this.refreshTable();
	}
}
