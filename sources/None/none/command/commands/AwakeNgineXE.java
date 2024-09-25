package none.command.commands;

import none.Client;
import none.command.Command;
import none.module.modules.combat.AutoAwakeNgineXE;

public class AwakeNgineXE extends Command{

	@Override
	public String getAlias() {
		return "Awakening";
	}

	@Override
	public String getDescription() {
		return "Awake Ngine XE";
	}

	@Override
	public String getSyntax() {
		return "Awake Ngine XE";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		evc("Toggled Auto Awake Ngine XE Bypass.");
		evc("Relogin to Update Cape.");
		Client.ISAwakeNgineXE = true;
		Client.instance.moduleManager.autoAwakeNgineXE.toggle();
	}

}
