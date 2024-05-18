package me.gishreload.yukon.commands;

import me.gishreload.yukon.Edictum;
import me.gishreload.yukon.command.Command;
import me.gishreload.yukon.module.Module;

public class Toggle extends Command{

	@Override
	public String getAlias() {
		// TODO Auto-generated method stub
		return "toggle";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Toggles a mod though the console";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "\u00a74.toggle <×èò>";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		boolean found = false;
		for(Module m: Edictum.getModules()){
			if(args[0].equalsIgnoreCase(m.getName())){
				m.toggle();
				found = true;
				Edictum.addChatMessage("\u00a76"+m.getName() + " \u00a7awas toggled!");
			}
		}
		if(found == false){
			Edictum.addChatMessage("\u00a76Targeted Module was not found!");
		}
	}

}
