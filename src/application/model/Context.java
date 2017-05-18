package application.model;

import application.manager.StageManager;

public class Context 
{
	public static final Context instance = new Context();
	
	private BarcodeInput tempBarcode = null;
	private StageManager stageManager = new StageManager();
	
	private int currentPrimaryKey = 0;
	
	public static Context getInstance()
	{
		return instance;
	}
	
	public void setTempBarcode(String string)
	{
		this.tempBarcode = new BarcodeInput(string);
	}
	
	public BarcodeInput getTempBarcode()
	{
		return this.tempBarcode;
	}
	
	public StageManager getStageManager()
	{
		return this.stageManager;
	}
	
	public void setCurrentPrimaryKey(int number)
	{
		this.currentPrimaryKey = number;
	}
	
	public int getCurrentPrimaryKey()
	{
		return this.currentPrimaryKey;
	}
}
