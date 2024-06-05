package Ascii4UwUWareClient.Command.Commands;

import Ascii4UwUWareClient.Client;
import Ascii4UwUWareClient.Command.Command;
import Ascii4UwUWareClient.Manager.ModuleManager;
import Ascii4UwUWareClient.Module.Module;
import Ascii4UwUWareClient.Util.Helper;
import net.minecraft.util.EnumChatFormatting;

public class ModList extends Command {
	public ModList() {
		super("modlist", new String[] { "mods" }, "", "sketit");
	}

	@Override
	public String execute(String[] args) {
		if (args.length == 0) {
			Client.instance.getModuleManager();
			StringBuilder list = new StringBuilder(String.valueOf(ModuleManager.getModules().size()) + "Modules - ");
			Client.instance.getModuleManager();
			for (Module cheat : ModuleManager.getModules()) {
				list.append((Object) (cheat.isEnabled() ? EnumChatFormatting.GREEN : EnumChatFormatting.RED))
						.append(cheat.getName()).append(", ");
			}
			Helper.sendMessage(list.toString().substring(0, list.toString().length() - 2));
		} else {
			Helper.sendMessage("Correct usage .modlist");
		}
		return null;
	}
}
