// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.command.commands;

import gg.childtrafficking.smokex.module.ModuleManager;
import gg.childtrafficking.smokex.module.modules.visuals.HUDModule;
import gg.childtrafficking.smokex.command.CommandInfo;
import gg.childtrafficking.smokex.command.Command;

@CommandInfo(name = "ClientName", description = "Sets the client name watermark", usage = ".clientName <String>", aliases = { "watermark" })
public final class ClientNameCommand extends Command
{
    @Override
    public void execute(final String[] args) {
        if (args.length < 1) {
            this.printUsage();
        }
        else {
            final StringBuilder stringBuilder = new StringBuilder();
            for (final String arg : args) {
                stringBuilder.append(arg).append(' ');
            }
            ModuleManager.getInstance(HUDModule.class).watermark = stringBuilder.toString();
        }
    }
}
