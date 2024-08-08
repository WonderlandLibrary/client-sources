package me.xatzdevelopments.command.commands;

import java.io.File;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.Xatz;
import me.xatzdevelopments.command.Command;
import me.xatzdevelopments.modules.Module;

public class Config extends Command {

	public Config() {
		super("Config", "Save or load config", "config load/save <name> | list", "c");
	}

	@Override
	public void onCommand(String[] args, String command) {
		if(args.length == 2) {
			String function = args[0];
			String fileName = args[1];
			
			
			
			if(function.equalsIgnoreCase("load")) {
				File fileCheck = new File(Xatz.getFileMananger().configdir, fileName + ".txt");
				if(fileCheck.exists()) {
					Xatz.getFileMananger().loadSettingsfromFile(fileName);
					Xatz.addChatMessage("Loaded settings from " + fileName);
				}else if(!fileCheck.exists()) {
					Xatz.addChatMessage("Couldn't find " + fileName);
				}
				
			}
			
			if(function.equalsIgnoreCase("save")) {
				Xatz.getFileMananger().saveSettingstoFile(fileName);
				Xatz.addChatMessage("Saved settings to " + fileName);
			}
		}
		
		if(args.length == 1) {
			String function = args[0];
			
			if(function.equalsIgnoreCase("list")) {
				File[] fileList = Xatz.getFileMananger().configdir.listFiles();
				for (int i = 0; i < fileList.length; i++) {
					  if (fileList[i].isFile()) {
						  Xatz.addChatMessage(fileList[i].getName().replace(".txt", ""));
			   }
			}
		}
	   }
		
	}
	
}
