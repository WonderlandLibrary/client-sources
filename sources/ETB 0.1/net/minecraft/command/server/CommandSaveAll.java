package net.minecraft.command.server;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.WorldServer;

public class CommandSaveAll extends net.minecraft.command.CommandBase
{
  private static final String __OBFID = "CL_00000826";
  
  public CommandSaveAll() {}
  
  public String getCommandName()
  {
    return "save-all";
  }
  
  public String getCommandUsage(ICommandSender sender)
  {
    return "commands.save.usage";
  }
  
  public void processCommand(ICommandSender sender, String[] args) throws CommandException
  {
    MinecraftServer var3 = MinecraftServer.getServer();
    sender.addChatMessage(new ChatComponentTranslation("commands.save.start", new Object[0]));
    
    if (var3.getConfigurationManager() != null)
    {
      var3.getConfigurationManager().saveAllPlayerData();
    }
    




    try
    {
      for (int var4 = 0; var4 < worldServers.length; var4++)
      {
        if (worldServers[var4] != null)
        {
          WorldServer var5 = worldServers[var4];
          boolean var6 = disableLevelSaving;
          disableLevelSaving = false;
          var5.saveAllChunks(true, null);
          disableLevelSaving = var6;
        }
      }
      
      if ((args.length > 0) && ("flush".equals(args[0])))
      {
        sender.addChatMessage(new ChatComponentTranslation("commands.save.flushStart", new Object[0]));
        
        for (var4 = 0; var4 < worldServers.length; var4++)
        {
          if (worldServers[var4] != null)
          {
            WorldServer var5 = worldServers[var4];
            boolean var6 = disableLevelSaving;
            disableLevelSaving = false;
            var5.saveChunkData();
            disableLevelSaving = var6;
          }
        }
        
        sender.addChatMessage(new ChatComponentTranslation("commands.save.flushEnd", new Object[0]));
      }
    }
    catch (MinecraftException var7)
    {
      notifyOperators(sender, this, "commands.save.failed", new Object[] { var7.getMessage() });
      return;
    }
    
    notifyOperators(sender, this, "commands.save.success", new Object[0]);
  }
}
