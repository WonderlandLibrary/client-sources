package net.minecraft.command.server;

import net.minecraft.server.*;
import net.minecraft.command.*;
import net.minecraft.world.*;

public class CommandSaveOff extends CommandBase
{
    @Override
    public String getCommandName() {
        return "save-off";
    }
    
    @Override
    public String getCommandUsage(final ICommandSender sender) {
        return "commands.save-off.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
        final MinecraftServer var3 = MinecraftServer.getServer();
        boolean var4 = false;
        for (int var5 = 0; var5 < var3.worldServers.length; ++var5) {
            if (var3.worldServers[var5] != null) {
                final WorldServer var6 = var3.worldServers[var5];
                if (!var6.disableLevelSaving) {
                    var6.disableLevelSaving = true;
                    var4 = true;
                }
            }
        }
        if (var4) {
            CommandBase.notifyOperators(sender, this, "commands.save.disabled", new Object[0]);
            return;
        }
        throw new CommandException("commands.save-off.alreadyOff", new Object[0]);
    }
}
