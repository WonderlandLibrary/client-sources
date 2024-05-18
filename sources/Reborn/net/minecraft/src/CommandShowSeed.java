package net.minecraft.src;

import net.minecraft.server.*;

public class CommandShowSeed extends CommandBase
{
    @Override
    public boolean canCommandSenderUseCommand(final ICommandSender par1ICommandSender) {
        return MinecraftServer.getServer().isSinglePlayer() || super.canCommandSenderUseCommand(par1ICommandSender);
    }
    
    @Override
    public String getCommandName() {
        return "seed";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public void processCommand(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        final Object var3 = (par1ICommandSender instanceof EntityPlayer) ? ((EntityPlayer)par1ICommandSender).worldObj : MinecraftServer.getServer().worldServerForDimension(0);
        par1ICommandSender.sendChatToPlayer("Seed: " + ((World)var3).getSeed());
    }
}
