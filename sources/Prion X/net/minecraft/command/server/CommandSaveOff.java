package net.minecraft.command.server;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class CommandSaveOff extends net.minecraft.command.CommandBase
{
  private static final String __OBFID = "CL_00000847";
  
  public CommandSaveOff() {}
  
  public String getCommandName()
  {
    return "save-off";
  }
  
  public String getCommandUsage(ICommandSender sender)
  {
    return "commands.save-off.usage";
  }
  
  public void processCommand(ICommandSender sender, String[] args) throws CommandException
  {
    MinecraftServer var3 = MinecraftServer.getServer();
    boolean var4 = false;
    
    for (int var5 = 0; var5 < worldServers.length; var5++)
    {
      if (worldServers[var5] != null)
      {
        net.minecraft.world.WorldServer var6 = worldServers[var5];
        
        if (!disableLevelSaving)
        {
          disableLevelSaving = true;
          var4 = true;
        }
      }
    }
    
    if (var4)
    {
      notifyOperators(sender, this, "commands.save.disabled", new Object[0]);
    }
    else
    {
      throw new CommandException("commands.save-off.alreadyOff", new Object[0]);
    }
  }
}
