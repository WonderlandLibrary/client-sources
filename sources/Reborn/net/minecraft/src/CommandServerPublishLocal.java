package net.minecraft.src;

import net.minecraft.server.*;

public class CommandServerPublishLocal extends CommandBase
{
    @Override
    public String getCommandName() {
        return "publish";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 4;
    }
    
    @Override
    public void processCommand(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        final String var3 = MinecraftServer.getServer().shareToLAN(EnumGameType.SURVIVAL, false);
        if (var3 != null) {
            CommandBase.notifyAdmins(par1ICommandSender, "commands.publish.started", var3);
        }
        else {
            CommandBase.notifyAdmins(par1ICommandSender, "commands.publish.failed", new Object[0]);
        }
    }
}
