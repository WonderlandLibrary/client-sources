// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.command.commands;

import gg.childtrafficking.smokex.module.Module;
import gg.childtrafficking.smokex.utils.player.ChatUtils;
import gg.childtrafficking.smokex.SmokeXClient;
import gg.childtrafficking.smokex.command.CommandInfo;
import gg.childtrafficking.smokex.command.Command;

@CommandInfo(name = "Toggle", description = "Toggles a module on/off", usage = ".toggle <Module>", aliases = { "t" })
public final class ToggleCommand extends Command
{
    @Override
    public void execute(final String[] args) {
        if (args.length < 1) {
            this.printUsage();
        }
        else {
            final Module modules = SmokeXClient.getInstance().getModuleManager().getModule(args[0]);
            if (modules != null) {
                ChatUtils.addChatMessage((modules.isToggled() ? "§cDisabled" : "§aEnabled") + " §r§7module '§6" + modules.getName() + "§7'");
                modules.toggle();
            }
            else {
                ChatUtils.addChatMessage("§cModule not found.");
            }
        }
    }
}
