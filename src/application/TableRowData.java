package application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TableRowData
{
	private final SimpleIntegerProperty referenceNumber;
	private final SimpleStringProperty description;
	
	public TableRowData(int referenceNumber, String description)
	{
		this.referenceNumber = new SimpleIntegerProperty(referenceNumber);
		this.description = new SimpleStringProperty(description);
	}
	
	public int getReferenceNumber()
	{
		return this.referenceNumber.get();
	}
	
	public void setReferenceNumber(int referenceNumber)
	{
		this.referenceNumber.set(referenceNumber);
	}
	
	public String getDescription()
	{
		return this.description.get();
	}
	
	public void setDescription(String description)
	{
		this.description.set(description);
	}
}
