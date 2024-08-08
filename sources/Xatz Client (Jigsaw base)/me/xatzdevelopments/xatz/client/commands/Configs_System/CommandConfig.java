package me.xatzdevelopments.xatz.client.commands.Configs_System;

import me.xatzdevelopments.xatz.client.commands.Command;
import me.xatzdevelopments.xatz.client.main.Xatz;
import me.xatzdevelopments.xatz.gui.Notification;
import me.xatzdevelopments.xatz.utils.Wrapper;

import java.io.File;


public class CommandConfig extends Command {

	@Override
	public void run(String[] args) { // TODO learn how to make a fucking config system
	
		
		  String messageFinal;
	        String[] s;
			String function = args[0];
			String fileName = args[1];
	        
	          if (args[1].equalsIgnoreCase("help")) {
	            Xatz.chatMessage(".config load (Config Name)");
	            
	            Xatz.chatMessage(".config list ");
	           
	            Xatz.chatMessage(".config create (config name)");
	       
	            Xatz.chatMessage(".config save (config name)");
	           
	            Xatz.chatMessage(".config delete (config name)");
	          }
/*	        if (args[1].equalsIgnoreCase("load")) {
				File fileCheck = new File(Xatz.getFileMananger().configdir, fileName + ".txt");
				if(fileCheck.exists()) {
					Xatz.getFileMananger().loadSettingsfromFile(fileName);
					Wrapper.tellPlayer("Loaded settings from " + fileName);
				}else if(!fileCheck.exists()) {
					Wrapper.tellPlayer("Couldn't find " + fileName);
				}
	        }
	        if (args[1].equalsIgnoreCase("save")) {
				Xatz.getFileMananger().saveSettingstoFile(fileName);
				Xatz.addChatMessage("Saved settings to " + fileName);
			}
	        }
	*/}
		
	@Override
	public String getActivator() {
		return ".config";
	}

	@Override
	public String getSyntax() {
		return ".config";
	}

	@Override
	public String getDesc() {
		return "config, This command is in beta";
	}
}