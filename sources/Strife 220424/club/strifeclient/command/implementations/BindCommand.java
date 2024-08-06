package club.strifeclient.command.implementations;

import club.strifeclient.Client;
import club.strifeclient.command.Command;
import club.strifeclient.command.CommandInfo;
import club.strifeclient.module.Module;
import club.strifeclient.util.player.ChatUtil;
import org.lwjglx.input.Keyboard;

@CommandInfo(name = "Bind", description = "Bind's a module to a specified key.", aliases = {"b", "setkey"})
public class BindCommand extends Command {
    @Override
    public void execute(String[] args, String name) {
        if (args.length == 2) {
            Module module = Client.INSTANCE.getModuleManager().getModule(args[0]);
            int key = Keyboard.getKeyIndex(args[1].toUpperCase());
            if (module != null) {
                module.setKeybind(key);
                ChatUtil.sendMessageWithPrefix((key == 0 && args[1].equalsIgnoreCase("none") || args[1].equalsIgnoreCase("reset")) ?
                        "The bind of &c" + module.getName() + "&7 has been reset." : "&c" + module.getName() + " &7has been bound to &c" + Keyboard.getKeyName(key));
            } else {
                ChatUtil.sendMessageWithPrefix("&7Module not found.");
            }
        } else {
            ChatUtil.sendMessageWithPrefix(".bind <module> <key>");
        }
    }
}
