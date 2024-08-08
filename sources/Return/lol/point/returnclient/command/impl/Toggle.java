package lol.point.returnclient.command.impl;

import lol.point.Return;
import lol.point.returnclient.command.Command;
import lol.point.returnclient.command.CommandInfo;
import lol.point.returnclient.module.Module;
import lol.point.returnclient.util.minecraft.ChatUtil;

@CommandInfo(
        name = "Toggle",
        usage = ".toggle <Module>",
        description = "helps you toggle modules",
        aliases = {"toggle"}
)
public class Toggle extends Command {

    @Override
    public void execute(String... args) {
        if (args.length < 1) {
            ChatUtil.addChatMessage("§9Usage: §c\"§b" + getUsage() + "§c\"");
            return;
        }

        String moduleName = args[0];

        Module module = Return.INSTANCE.moduleManager.getModuleByName(moduleName);
        if (module == null) {
            ChatUtil.addChatMessage("§cModule §e" + moduleName + "§c was not found!");
            return;
        }

        try {
            module.setEnabled(!module.enabled);
            ChatUtil.addChatMessage("§e" + moduleName + "§a was " + ((module.enabled ? "§aenabled" : "§cdisabled") + "§a!"));
        } catch (Exception e) {
            ChatUtil.addChatMessage("§cAn error occurred: §4" + e.getMessage());
        }
    }
}