package net.minecraft.command.server;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class CommandPublishLocalServer extends net.minecraft.command.CommandBase
{
  private static final String __OBFID = "CL_00000799";
  
  public CommandPublishLocalServer() {}
  
  public String getCommandName()
  {
    return "publish";
  }
  
  public String getCommandUsage(ICommandSender sender)
  {
    return "commands.publish.usage";
  }
  
  public void processCommand(ICommandSender sender, String[] args) throws CommandException
  {
    String var3 = MinecraftServer.getServer().shareToLAN(net.minecraft.world.WorldSettings.GameType.SURVIVAL, false);
    
    if (var3 != null)
    {
      notifyOperators(sender, this, "commands.publish.started", new Object[] { var3 });
    }
    else
    {
      notifyOperators(sender, this, "commands.publish.failed", new Object[0]);
    }
  }
}
