package xyz.vergoclient.commands;

public interface OnCommandInterface {
	
	public void onCommand(String... args);
	
	public String getName();
	public String getUsage();
	public String getDescription();
	
}
