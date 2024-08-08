package lol.point.returnclient.command.impl;

import lol.point.Return;
import lol.point.returnclient.command.Command;
import lol.point.returnclient.command.CommandInfo;
import lol.point.returnclient.module.Module;
import lol.point.returnclient.util.minecraft.ChatUtil;
import org.lwjgl.input.Keyboard;

@CommandInfo(
        name = "Bind",
        usage = ".bind <Module> <Key>",
        description = "helps you bind modules",
        aliases = {"bind", "binds"}
)
public class Bind extends Command {

    @Override
    public void execute(String... args) {
        if (args.length < 2) {
            ChatUtil.addChatMessage("§9Usage: §c\"§b" + getUsage() + "§c\"");
            return;
        }

        String moduleName = args[0];
        String moduleBind = args[1];

        Module module = Return.INSTANCE.moduleManager.getModuleByName(moduleName);
        if (module == null) {
            ChatUtil.addChatMessage("§cModule §e" + moduleName + "§c was not found!");
            return;
        }

        try {
            if (moduleBind.equalsIgnoreCase("none") || Keyboard.getKeyName(Keyboard.getKeyIndex(moduleBind.toUpperCase())).equalsIgnoreCase("none")) {
                module.key = 0;
                ChatUtil.addChatMessage("§e" + moduleName + "§a was successfully unbound!");
            } else {
                module.key = Keyboard.getKeyIndex(moduleBind.toUpperCase());
                ChatUtil.addChatMessage("§e" + moduleName + "§a was successfully bound to §e" + Keyboard.getKeyName(Keyboard.getKeyIndex(moduleBind.toUpperCase())) + "§a!");
            }
        } catch (Exception e) {
            ChatUtil.addChatMessage("§cThe key you inputted was malformed!");
        }
    }
}