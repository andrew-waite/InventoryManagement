package application.model;

import javafx.beans.property.SimpleStringProperty;

public class BarcodeInput 
{
	private final SimpleStringProperty barcodeNumber;
	
	public BarcodeInput(String barcodeNumber)
	{
		this.barcodeNumber = new SimpleStringProperty(barcodeNumber);
	}
	
	public String getBarcode()
	{
		return this.barcodeNumber.get();
	}
}
