package net.minecraft.src;

import net.minecraft.server.*;
import java.util.regex.*;
import java.util.*;

public class CommandServerBanIp extends CommandBase
{
    public static final Pattern IPv4Pattern;
    
    static {
        IPv4Pattern = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
    }
    
    @Override
    public String getCommandName() {
        return "ban-ip";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final ICommandSender par1ICommandSender) {
        return MinecraftServer.getServer().getConfigurationManager().getBannedIPs().isListActive() && super.canCommandSenderUseCommand(par1ICommandSender);
    }
    
    @Override
    public String getCommandUsage(final ICommandSender par1ICommandSender) {
        return par1ICommandSender.translateString("commands.banip.usage", new Object[0]);
    }
    
    @Override
    public void processCommand(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length >= 1 && par2ArrayOfStr[0].length() > 1) {
            final Matcher var3 = CommandServerBanIp.IPv4Pattern.matcher(par2ArrayOfStr[0]);
            String var4 = null;
            if (par2ArrayOfStr.length >= 2) {
                var4 = CommandBase.func_82360_a(par1ICommandSender, par2ArrayOfStr, 1);
            }
            if (var3.matches()) {
                this.banIP(par1ICommandSender, par2ArrayOfStr[0], var4);
            }
            else {
                final EntityPlayerMP var5 = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(par2ArrayOfStr[0]);
                if (var5 == null) {
                    throw new PlayerNotFoundException("commands.banip.invalid", new Object[0]);
                }
                this.banIP(par1ICommandSender, var5.getPlayerIP(), var4);
            }
            return;
        }
        throw new WrongUsageException("commands.banip.usage", new Object[0]);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        return (par2ArrayOfStr.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames()) : null;
    }
    
    protected void banIP(final ICommandSender par1ICommandSender, final String par2Str, final String par3Str) {
        final BanEntry var4 = new BanEntry(par2Str);
        var4.setBannedBy(par1ICommandSender.getCommandSenderName());
        if (par3Str != null) {
            var4.setBanReason(par3Str);
        }
        MinecraftServer.getServer().getConfigurationManager().getBannedIPs().put(var4);
        final List var5 = MinecraftServer.getServer().getConfigurationManager().getPlayerList(par2Str);
        final String[] var6 = new String[var5.size()];
        int var7 = 0;
        for (final EntityPlayerMP var9 : var5) {
            var9.playerNetServerHandler.kickPlayerFromServer("You have been IP banned.");
            var6[var7++] = var9.getEntityName();
        }
        if (var5.isEmpty()) {
            CommandBase.notifyAdmins(par1ICommandSender, "commands.banip.success", par2Str);
        }
        else {
            CommandBase.notifyAdmins(par1ICommandSender, "commands.banip.success.players", par2Str, CommandBase.joinNiceString(var6));
        }
    }
}
