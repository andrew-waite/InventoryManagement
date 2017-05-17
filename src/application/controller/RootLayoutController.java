package application.controller;

import application.TableRowData;
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
	private TableColumn<TableRowData, String> tableColumn;
	
	public RootLayoutController()
	{
		
	}
	
	public void initialize()
	{
		
	}

	@FXML
	private void clickedButton(ActionEvent event)
	{
		System.out.println("Something");
		
		
		ObservableList<TableRowData> data = FXCollections.observableArrayList();
		data.add(new TableRowData("HI"));
		
		tableColumn.setCellValueFactory(new PropertyValueFactory<TableRowData, String>("description"));
		
		tableView.setItems(data);
		
	}
}
