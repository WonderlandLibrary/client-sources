package net.minecraft.command.server;

import java.util.regex.Matcher;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.BanList;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.BlockPos;

public class CommandPardonIp extends CommandBase
{
  private static final String __OBFID = "CL_00000720";
  
  public CommandPardonIp() {}
  
  public String getCommandName()
  {
    return "pardon-ip";
  }
  



  public int getRequiredPermissionLevel()
  {
    return 3;
  }
  



  public boolean canCommandSenderUseCommand(ICommandSender sender)
  {
    return (MinecraftServer.getServer().getConfigurationManager().getBannedIPs().isLanServer()) && (super.canCommandSenderUseCommand(sender));
  }
  
  public String getCommandUsage(ICommandSender sender)
  {
    return "commands.unbanip.usage";
  }
  
  public void processCommand(ICommandSender sender, String[] args) throws net.minecraft.command.CommandException
  {
    if ((args.length == 1) && (args[0].length() > 1))
    {
      Matcher var3 = CommandBanIp.field_147211_a.matcher(args[0]);
      
      if (var3.matches())
      {
        MinecraftServer.getServer().getConfigurationManager().getBannedIPs().removeEntry(args[0]);
        notifyOperators(sender, this, "commands.unbanip.success", new Object[] { args[0] });
      }
      else
      {
        throw new net.minecraft.command.SyntaxErrorException("commands.unbanip.invalid", new Object[0]);
      }
    }
    else
    {
      throw new net.minecraft.command.WrongUsageException("commands.unbanip.usage", new Object[0]);
    }
  }
  
  public java.util.List addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos)
  {
    return args.length == 1 ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getConfigurationManager().getBannedIPs().getKeys()) : null;
  }
}
