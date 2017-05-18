package application.manager;

import java.util.LinkedList;

import javafx.stage.Stage;

public class StageManager 
{
	private LinkedList<Stage> stageList = new LinkedList<Stage>();
	
	public StageManager()
	{
		
	}
	
	public LinkedList<Stage> getStages()
	{
		return this.stageList;
	}
	
	public void addStage(Stage stage)
	{
		this.stageList.add(stage);
	}
	
	public Stage getCurrentStage()
	{
		return this.stageList.getLast();
	}
	
	public void returnFocusToPreviousStage()
	{
		Stage stageToClose = this.getCurrentStage();
		stageToClose.hide();
		stageList.remove(this.getCurrentStage());
		
		Stage stageToFocus = this.getCurrentStage();
		stageToFocus.show();
	}
}
