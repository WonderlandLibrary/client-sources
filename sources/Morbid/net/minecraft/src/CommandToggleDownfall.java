package net.minecraft.src;

import net.minecraft.server.*;

public class CommandToggleDownfall extends CommandBase
{
    @Override
    public String getCommandName() {
        return "toggledownfall";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public void processCommand(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        this.toggleDownfall();
        CommandBase.notifyAdmins(par1ICommandSender, "commands.downfall.success", new Object[0]);
    }
    
    protected void toggleDownfall() {
        MinecraftServer.getServer().worldServers[0].toggleRain();
        MinecraftServer.getServer().worldServers[0].getWorldInfo().setThundering(true);
    }
}
