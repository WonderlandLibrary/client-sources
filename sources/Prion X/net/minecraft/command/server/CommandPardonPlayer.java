package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.server.management.UserListBans;

public class CommandPardonPlayer extends CommandBase
{
  private static final String __OBFID = "CL_00000747";
  
  public CommandPardonPlayer() {}
  
  public String getCommandName()
  {
    return "pardon";
  }
  



  public int getRequiredPermissionLevel()
  {
    return 3;
  }
  
  public String getCommandUsage(ICommandSender sender)
  {
    return "commands.unban.usage";
  }
  



  public boolean canCommandSenderUseCommand(ICommandSender sender)
  {
    return (MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().isLanServer()) && (super.canCommandSenderUseCommand(sender));
  }
  
  public void processCommand(ICommandSender sender, String[] args) throws CommandException
  {
    if ((args.length == 1) && (args[0].length() > 0))
    {
      MinecraftServer var3 = MinecraftServer.getServer();
      com.mojang.authlib.GameProfile var4 = var3.getConfigurationManager().getBannedPlayers().isUsernameBanned(args[0]);
      
      if (var4 == null)
      {
        throw new CommandException("commands.unban.failed", new Object[] { args[0] });
      }
      

      var3.getConfigurationManager().getBannedPlayers().removeEntry(var4);
      notifyOperators(sender, this, "commands.unban.success", new Object[] { args[0] });

    }
    else
    {
      throw new net.minecraft.command.WrongUsageException("commands.unban.usage", new Object[0]);
    }
  }
  
  public java.util.List addTabCompletionOptions(ICommandSender sender, String[] args, net.minecraft.util.BlockPos pos)
  {
    return args.length == 1 ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().getKeys()) : null;
  }
}
