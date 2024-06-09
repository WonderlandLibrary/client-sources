package us.loki.freaky.command.commands;

import us.loki.legit.Client;
import us.loki.freaky.command.*;
import us.loki.legit.modules.*;

public class Toggle extends Command{

	@Override
	public String getAlias() {
		return "t";
	}

	@Override
	public String getDescription() {
		return "Toggles a mod";
	}

	@Override
	public String getSyntax() {
		return "@t <Module>";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		boolean found = false;
		for(Module m: ModuleManager.getModules()){
			if(args[0].equalsIgnoreCase(m.getName())){
				m.toggle();
				found = true;
				Client.addChatMessage(m.getName() + " was toggled!");
			}
		}
		if(found == false){
			Client.addChatMessage("Mod not found!");
		}
	}

}