package net.minecraft.src;

import net.minecraft.server.*;

public class CommandServerStop extends CommandBase
{
    @Override
    public String getCommandName() {
        return "stop";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 4;
    }
    
    @Override
    public void processCommand(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        CommandBase.notifyAdmins(par1ICommandSender, "commands.stop.start", new Object[0]);
        MinecraftServer.getServer().initiateShutdown();
    }
}
