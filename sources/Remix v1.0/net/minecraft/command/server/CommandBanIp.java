package net.minecraft.command.server;

import net.minecraft.server.*;
import java.util.regex.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.server.management.*;
import net.minecraft.command.*;
import java.util.*;

public class CommandBanIp extends CommandBase
{
    public static final Pattern field_147211_a;
    
    @Override
    public String getCommandName() {
        return "ban-ip";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final ICommandSender sender) {
        return MinecraftServer.getServer().getConfigurationManager().getBannedIPs().isLanServer() && super.canCommandSenderUseCommand(sender);
    }
    
    @Override
    public String getCommandUsage(final ICommandSender sender) {
        return "commands.banip.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length >= 1 && args[0].length() > 1) {
            final IChatComponent var3 = (args.length >= 2) ? CommandBase.getChatComponentFromNthArg(sender, args, 1) : null;
            final Matcher var4 = CommandBanIp.field_147211_a.matcher(args[0]);
            if (var4.matches()) {
                this.func_147210_a(sender, args[0], (var3 == null) ? null : var3.getUnformattedText());
            }
            else {
                final EntityPlayerMP var5 = MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(args[0]);
                if (var5 == null) {
                    throw new PlayerNotFoundException("commands.banip.invalid", new Object[0]);
                }
                this.func_147210_a(sender, var5.getPlayerIP(), (var3 == null) ? null : var3.getUnformattedText());
            }
            return;
        }
        throw new WrongUsageException("commands.banip.usage", new Object[0]);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return (args.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : null;
    }
    
    protected void func_147210_a(final ICommandSender p_147210_1_, final String p_147210_2_, final String p_147210_3_) {
        final IPBanEntry var4 = new IPBanEntry(p_147210_2_, null, p_147210_1_.getName(), null, p_147210_3_);
        MinecraftServer.getServer().getConfigurationManager().getBannedIPs().addEntry(var4);
        final List var5 = MinecraftServer.getServer().getConfigurationManager().getPlayersMatchingAddress(p_147210_2_);
        final String[] var6 = new String[var5.size()];
        int var7 = 0;
        for (final EntityPlayerMP var9 : var5) {
            var9.playerNetServerHandler.kickPlayerFromServer("You have been IP banned.");
            var6[var7++] = var9.getName();
        }
        if (var5.isEmpty()) {
            CommandBase.notifyOperators(p_147210_1_, this, "commands.banip.success", p_147210_2_);
        }
        else {
            CommandBase.notifyOperators(p_147210_1_, this, "commands.banip.success.players", p_147210_2_, CommandBase.joinNiceString(var6));
        }
    }
    
    static {
        field_147211_a = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
    }
}
