package Hydro.command.impl;

import java.util.List;

import Hydro.Client;
import Hydro.command.CommandExecutor;
import Hydro.module.Module;
import Hydro.util.ChatUtils;
import net.minecraft.client.entity.EntityPlayerSP;

public class Toggle implements CommandExecutor {

	@Override
	public void execute(EntityPlayerSP sender, List<String> args) {
		if (args.size() == 1) {
			try {
				System.out.println(args.get(0));
				Module m = Client.instance.moduleManager.getModuleByName(args.get(0));
				if (args.get(0).equalsIgnoreCase(m.getName()))
					m.toggle();
				ChatUtils.sendMessageToPlayer(
						m.getName() + (m.isEnabled() ? " has been \u00A72enabled" : " has been \u00A74disabled"));
			} catch (Exception e) {
				ChatUtils.sendMessageToPlayer("Module not found.");
			}
		}else
			ChatUtils.sendMessageToPlayer(".toggle (module)");
	}

}
