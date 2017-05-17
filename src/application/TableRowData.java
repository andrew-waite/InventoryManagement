package application;

import javafx.beans.property.SimpleStringProperty;

public class TableRowData
{
	private final SimpleStringProperty description;
	
	public TableRowData(String description)
	{
		this.description = new SimpleStringProperty(description);
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
