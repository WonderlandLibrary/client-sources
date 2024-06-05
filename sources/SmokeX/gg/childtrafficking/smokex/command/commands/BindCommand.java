// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.command.commands;

import gg.childtrafficking.smokex.module.Module;
import gg.childtrafficking.smokex.utils.player.ChatUtils;
import org.lwjgl.input.Keyboard;
import gg.childtrafficking.smokex.SmokeXClient;
import gg.childtrafficking.smokex.command.CommandInfo;
import gg.childtrafficking.smokex.command.Command;

@CommandInfo(name = "Bind", description = "Binds a module to a specific key.", usage = ".bind <module> <key> | .bind reset <module>", aliases = { "b" })
public final class BindCommand extends Command
{
    @Override
    public void execute(final String[] args) {
        if (args.length < 2) {
            this.printUsage();
        }
        else {
            Module module = SmokeXClient.getInstance().getModuleManager().getModule(args[0]);
            if (module != null) {
                final int key = Keyboard.getKeyIndex(args[1].toUpperCase());
                module.setKey(key);
                ChatUtils.addChatMessage("§6" + module.getName() + " §r§7has been bound to §r§a" + Keyboard.getKeyName(key));
                return;
            }
            if (args[0].equalsIgnoreCase("reset")) {
                module = SmokeXClient.getInstance().getModuleManager().getModule(args[1]);
                if (module != null) {
                    module.setKey(0);
                    ChatUtils.addChatMessage("§7The bind of §6" + module.getName() + " §r§7has been reset");
                    return;
                }
            }
            ChatUtils.addChatMessage("§cThat module does not exist!");
        }
    }
}
