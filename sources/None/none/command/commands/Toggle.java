package none.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;

import none.Client;
import none.command.Command;
import none.module.Module;

public class Toggle extends Command{

	@Override
	public String getAlias() {
		return "t";
	}

	@Override
	public String getDescription() {
		return "Toggle Modules";
	}

	@Override
	public String getSyntax() {
		return ChatFormatting.AQUA + ".t " + ChatFormatting.GRAY + "[" + ChatFormatting.GREEN + "Module" + ChatFormatting.GRAY + "]";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		for (Module m : Client.instance.moduleManager.getModules()) {
			if (args[0].equalsIgnoreCase(m.getName())) {
				if (m.isEnabled()) {
					evc(m.getName() + ChatFormatting.RED + " was Disabled");
				}else {
					evc(m.getName() + ChatFormatting.GREEN + " was Enabled");
				}
				m.toggle();
			}
		}
	}

}
