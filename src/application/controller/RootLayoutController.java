package application.controller;

import application.TableRowData;
import application.database.DatabaseController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;

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
	
	public RootLayoutController()
	{
		
	}
	
	public void initialize()
	{
		
	}

	@FXML
	private void clickedButton(ActionEvent event)
	{
		ObservableList<TableRowData> data = FXCollections.observableArrayList();
		data.add(new TableRowData(1, "HI"));
		
		tableColumnReferenceNumber.setCellValueFactory(new PropertyValueFactory<TableRowData, Integer>("referenceNumber"));
		tableColumnDescription.setCellValueFactory(new PropertyValueFactory<TableRowData, String>("description"));
		
		tableView.setItems(data);
		
		new DatabaseController();
		
	}
}
