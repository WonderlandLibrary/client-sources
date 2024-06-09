package net.minecraft.command.server;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class CommandStop extends net.minecraft.command.CommandBase
{
  private static final String __OBFID = "CL_00001132";
  
  public CommandStop() {}
  
  public String getCommandName()
  {
    return "stop";
  }
  
  public String getCommandUsage(ICommandSender sender)
  {
    return "commands.stop.usage";
  }
  
  public void processCommand(ICommandSender sender, String[] args) throws net.minecraft.command.CommandException
  {
    if (getServerworldServers != null)
    {
      notifyOperators(sender, this, "commands.stop.start", new Object[0]);
    }
    
    MinecraftServer.getServer().initiateShutdown();
  }
}
