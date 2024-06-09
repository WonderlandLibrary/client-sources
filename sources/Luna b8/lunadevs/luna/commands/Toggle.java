package lunadevs.luna.commands;

import lunadevs.luna.command.Command;
import lunadevs.luna.main.Luna;
import lunadevs.luna.module.Module;

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
		return "-t <Module>";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		boolean found = false;
		for(Module m: Luna.moduleManager.getModules()){
			if(args[0].equalsIgnoreCase(m.getName())){
				m.toggle();
				found = true;
				Luna.addChatMessage(m.getName() + " was toggled!");
			}
		}
		if(found == false){
			Luna.addChatMessage("Mod not found!");
		}
	}

}