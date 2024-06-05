package net.minecraft.command.server;

import net.minecraft.server.*;
import net.minecraft.server.management.*;
import net.minecraft.command.*;
import com.mojang.authlib.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import java.util.*;

public class CommandBanPlayer extends CommandBase
{
    @Override
    public String getCommandName() {
        return "ban";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender sender) {
        return "commands.ban.usage";
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final ICommandSender sender) {
        return MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().isLanServer() && super.canCommandSenderUseCommand(sender);
    }
    
    @Override
    public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length < 1 || args[0].length() <= 0) {
            throw new WrongUsageException("commands.ban.usage", new Object[0]);
        }
        final MinecraftServer var3 = MinecraftServer.getServer();
        final GameProfile var4 = var3.getPlayerProfileCache().getGameProfileForUsername(args[0]);
        if (var4 == null) {
            throw new CommandException("commands.ban.failed", new Object[] { args[0] });
        }
        String var5 = null;
        if (args.length >= 2) {
            var5 = CommandBase.getChatComponentFromNthArg(sender, args, 1).getUnformattedText();
        }
        final UserListBansEntry var6 = new UserListBansEntry(var4, null, sender.getName(), null, var5);
        var3.getConfigurationManager().getBannedPlayers().addEntry(var6);
        final EntityPlayerMP var7 = var3.getConfigurationManager().getPlayerByUsername(args[0]);
        if (var7 != null) {
            var7.playerNetServerHandler.kickPlayerFromServer("You are banned from this server.");
        }
        CommandBase.notifyOperators(sender, this, "commands.ban.success", args[0]);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return (args.length >= 1) ? CommandBase.getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : null;
    }
}
