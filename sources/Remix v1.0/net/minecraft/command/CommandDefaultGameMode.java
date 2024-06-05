package net.minecraft.command;

import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.server.*;
import net.minecraft.entity.player.*;
import java.util.*;

public class CommandDefaultGameMode extends CommandGameMode
{
    @Override
    public String getCommandName() {
        return "defaultgamemode";
    }
    
    @Override
    public String getCommandUsage(final ICommandSender sender) {
        return "commands.defaultgamemode.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length <= 0) {
            throw new WrongUsageException("commands.defaultgamemode.usage", new Object[0]);
        }
        final WorldSettings.GameType var3 = this.getGameModeFromCommand(sender, args[0]);
        this.setGameType(var3);
        CommandBase.notifyOperators(sender, this, "commands.defaultgamemode.success", new ChatComponentTranslation("gameMode." + var3.getName(), new Object[0]));
    }
    
    protected void setGameType(final WorldSettings.GameType p_71541_1_) {
        final MinecraftServer var2 = MinecraftServer.getServer();
        var2.setGameType(p_71541_1_);
        if (var2.getForceGamemode()) {
            for (final EntityPlayerMP var4 : MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
                var4.setGameType(p_71541_1_);
                var4.fallDistance = 0.0f;
            }
        }
    }
}
