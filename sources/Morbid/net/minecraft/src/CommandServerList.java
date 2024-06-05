package net.minecraft.src;

import net.minecraft.server.*;

public class CommandServerList extends CommandBase
{
    @Override
    public String getCommandName() {
        return "list";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
    
    @Override
    public void processCommand(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        par1ICommandSender.sendChatToPlayer(par1ICommandSender.translateString("commands.players.list", MinecraftServer.getServer().getCurrentPlayerCount(), MinecraftServer.getServer().getMaxPlayers()));
        par1ICommandSender.sendChatToPlayer(MinecraftServer.getServer().getConfigurationManager().getPlayerListAsString());
    }
}
