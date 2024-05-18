package me.swezedcode.client.manager;

import me.swezedcode.client.manager.managers.CommandManager;
import me.swezedcode.client.manager.managers.FileManager;
import me.swezedcode.client.manager.managers.FriendManager;
import me.swezedcode.client.manager.managers.ModuleManager;
import me.swezedcode.client.manager.managers.ValueManager;

public class Manager {

	private static Manager manager = new Manager();
	private ModuleManager moduleManager;
	private CommandManager commandManager;
	private ValueManager valueManager;
	private FriendManager friendManager;
	private FileManager fileManager;
	
	public static Manager getManager() {
		return manager;
	}
	
	public CommandManager getCommandManager() {
		return commandManager;
	}
	
	public ModuleManager getModuleManager() {
		return moduleManager;
	}
	
	public ValueManager getValueManager() {
		return valueManager;
	}
	
	public FriendManager getFriendManager() {
		return friendManager;
	}
	
	public FileManager getFileManager() {
		return fileManager;
	}
	
}
