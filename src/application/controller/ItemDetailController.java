package application.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import application.Main;
import application.model.Context;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;

public class ItemDetailController 
{
	@FXML
	private ListView<String> associatedBarcodes;
	
	@FXML
	private TextArea titleTextArea;
	
	@FXML 
	private TextArea longDescriptionTextArea;
	
	private List<String> newBarcodes = new ArrayList<String>();
	
	private ObservableList<String> barcodes = FXCollections.observableArrayList();
	
	//Called before all FXML members have been injected
	public ItemDetailController()
	{
		
	}
	
	//Called after all FXML members have been injected
	public void initialize()
	{
		
		if(Context.getInstance().getCurrentPrimaryKey() != -1)
		{		
			String title = "", longDescription = "";
			int barcodeID = 0;
			try
			{
				Statement statement = Main.getDatabase().getConnection().createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT title, longDescription, barcodesID FROM Records WHERE id = " + Context.getInstance().getCurrentPrimaryKey());
				while(resultSet.next())
				{
					title = resultSet.getString("title");
					longDescription = resultSet.getString("longDescription");
					barcodeID = resultSet.getInt("barcodesID");
				}
				
				ResultSet barcodeResults = statement.executeQuery("SELECT * FROM BarcodeNumbers WHERE barcodesID = " + barcodeID);
				while(barcodeResults.next())
				{
					this.barcodes.add(barcodeResults.getString("barcode"));
				}
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
			
			this.titleTextArea.setText(title);
			this.longDescriptionTextArea.setText(longDescription);
			
			this.associatedBarcodes.setItems(barcodes);
		}
		else
		{
			this.titleTextArea.setText("");
			this.longDescriptionTextArea.setText("");
			
			this.associatedBarcodes.setItems(barcodes);
		}
	}
	
	@FXML
	public void launchAddBarcode()
	{
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Input barcode EAN\\TUN");
		dialog.setHeaderText("Please scan or enter the barcode");
		dialog.setContentText("Barcode:");

		Optional<String> result = dialog.showAndWait();
		result.ifPresent(barcodeResult -> 
		{
			this.barcodes.add(barcodeResult);
			this.newBarcodes.add(barcodeResult);
		});
		
		this.associatedBarcodes.refresh();
	}
	
	@FXML
	public void updateFields()
	{
		if(Context.getInstance().getCurrentPrimaryKey() == -1)
		{
			try 
			{
				Statement statement = Main.getDatabase().getConnection().createStatement();
				statement.execute("INSERT INTO Records (title, longDescription, barcodesID) VALUES ('a', 'b', 0);");
				
				Statement getLastRowStatement = Main.getDatabase().getConnection().createStatement();
				ResultSet resultSet = getLastRowStatement.executeQuery("SELECT id FROM Records WHERE id = (SELECT MAX(ID) FROM Records);");
				resultSet.next();
				Context.getInstance().setCurrentPrimaryKey(resultSet.getInt("id"));
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}	
		}
		
		int pk = Context.getInstance().getCurrentPrimaryKey();
		
		//Update the title and description fields and the barcode ID to match the current primary key id
		try 
		{
			Statement statement = Main.getDatabase().getConnection().createStatement();
			statement.execute("UPDATE Records SET title = '" + this.titleTextArea.getText() + "', longDescription = '" + this.longDescriptionTextArea.getText() + "', barcodesID = " + pk + " WHERE id = " + pk + ";");
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		if(this.barcodes.size() != 0)
		{
			try 
			{
				//Statement barcodeStatement = Main.getDatabase().getConnection().createStatement();
				for(int i = 0; i < this.newBarcodes.size(); i++)
				{
					Main.getDatabase().getConnection().createStatement().execute("INSERT INTO BarcodeNumbers (barcodesID, barcode) VALUES (" + pk + ", " + this.newBarcodes.get(i) + ");");
				}
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	@FXML
	public void closeWindow()
	{
		Context.getInstance().getStageManager().returnFocusToPreviousStage();
	}
}
