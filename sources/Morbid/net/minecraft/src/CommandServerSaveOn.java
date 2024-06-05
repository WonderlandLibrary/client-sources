package net.minecraft.src;

import net.minecraft.server.*;

public class CommandServerSaveOn extends CommandBase
{
    @Override
    public String getCommandName() {
        return "save-on";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 4;
    }
    
    @Override
    public void processCommand(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        final MinecraftServer var3 = MinecraftServer.getServer();
        for (int var4 = 0; var4 < var3.worldServers.length; ++var4) {
            if (var3.worldServers[var4] != null) {
                final WorldServer var5 = var3.worldServers[var4];
                var5.canNotSave = false;
            }
        }
        CommandBase.notifyAdmins(par1ICommandSender, "commands.save.enabled", new Object[0]);
    }
}
