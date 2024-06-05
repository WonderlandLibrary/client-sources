package gq.vapu.czfclient.Command.Commands;

import gq.vapu.czfclient.Client;
import gq.vapu.czfclient.Command.Command;
import gq.vapu.czfclient.Manager.ModuleManager;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Util.Helper;
import net.minecraft.util.EnumChatFormatting;

public class ModList extends Command {
    public ModList() {
        super("modlist", new String[]{"mods"}, "", "sketit");
    }

    @Override
    public String execute(String[] args) {
        if (args.length == 0) {
            Client.getModuleManager();
            StringBuilder list = new StringBuilder(ModuleManager.getModules().size() + "Modules - ");
            Client.getModuleManager();
            for (Module cheat : ModuleManager.getModules()) {
                list.append(cheat.isEnabled() ? EnumChatFormatting.GREEN : EnumChatFormatting.RED)
                        .append(cheat.getName()).append(", ");
            }
            Helper.sendMessage(list.substring(0, list.toString().length() - 2));
        } else {
            Helper.sendMessage("Correct usage .modlist");
        }
        return null;
    }
}
