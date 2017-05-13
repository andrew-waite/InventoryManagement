package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class RootLayoutController extends BorderPane
{
	@FXML
	private Button asdf;
	
	public RootLayoutController()
	{
		
	}
	
	public void initialize()
	{
		
	}

	@FXML
	private void buttonClicked()
	{
		asdf.setText("Hello");
		System.out.println("SOMETHING SOMETHING SOMETHING");
	}
}
