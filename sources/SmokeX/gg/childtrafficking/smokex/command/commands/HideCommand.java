// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.command.commands;

import gg.childtrafficking.smokex.module.Module;
import gg.childtrafficking.smokex.utils.player.ChatUtils;
import gg.childtrafficking.smokex.SmokeXClient;
import gg.childtrafficking.smokex.command.CommandInfo;
import gg.childtrafficking.smokex.command.Command;

@CommandInfo(name = "Hide", description = "Hide/show specific modules from the arraylist", usage = ".hide <module>", aliases = {})
public final class HideCommand extends Command
{
    @Override
    public void execute(final String[] args) {
        if (args.length < 1) {
            this.printUsage();
        }
        else {
            final Module modules = SmokeXClient.getInstance().getModuleManager().getModule(args[0]);
            if (modules != null) {
                modules.setHidden(!modules.isHidden());
                modules.toggle();
                modules.toggle();
            }
            else {
                ChatUtils.addChatMessage("§cModule not found.");
            }
        }
    }
}
