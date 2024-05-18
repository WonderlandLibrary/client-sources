package net.minecraft.src;

import net.minecraft.server.*;

public class CommandServerSaveOff extends CommandBase
{
    @Override
    public String getCommandName() {
        return "save-off";
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
                var5.canNotSave = true;
            }
        }
        CommandBase.notifyAdmins(par1ICommandSender, "commands.save.disabled", new Object[0]);
    }
}
