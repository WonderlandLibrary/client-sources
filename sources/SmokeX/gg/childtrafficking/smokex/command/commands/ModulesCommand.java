// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.command.commands;

import java.util.Iterator;
import gg.childtrafficking.smokex.module.Module;
import gg.childtrafficking.smokex.SmokeXClient;
import gg.childtrafficking.smokex.utils.player.ChatUtils;
import gg.childtrafficking.smokex.command.CommandInfo;
import gg.childtrafficking.smokex.command.Command;

@CommandInfo(name = "Modules", description = "Shows a list of modules and their descriptions", usage = ".modules", aliases = {})
public final class ModulesCommand extends Command
{
    @Override
    public void execute(final String[] args) {
        ChatUtils.addChatMessage("§7Module List:");
        for (final Module module : SmokeXClient.getInstance().getModuleManager().getModules()) {
            ChatUtils.addChatMessage("§b" + module.getName() + " - " + module.getDescription());
        }
    }
}
