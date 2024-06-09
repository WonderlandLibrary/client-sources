package net.minecraft.command;

import java.util.Iterator;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldSettings.GameType;

public class CommandDefaultGameMode extends CommandGameMode
{
  private static final String __OBFID = "CL_00000296";
  
  public CommandDefaultGameMode() {}
  
  public String getCommandName()
  {
    return "defaultgamemode";
  }
  
  public String getCommandUsage(ICommandSender sender)
  {
    return "commands.defaultgamemode.usage";
  }
  
  public void processCommand(ICommandSender sender, String[] args) throws CommandException
  {
    if (args.length <= 0)
    {
      throw new WrongUsageException("commands.defaultgamemode.usage", new Object[0]);
    }
    

    WorldSettings.GameType var3 = getGameModeFromCommand(sender, args[0]);
    setGameType(var3);
    notifyOperators(sender, this, "commands.defaultgamemode.success", new Object[] { new net.minecraft.util.ChatComponentTranslation("gameMode." + var3.getName(), new Object[0]) });
  }
  

  protected void setGameType(WorldSettings.GameType p_71541_1_)
  {
    MinecraftServer var2 = MinecraftServer.getServer();
    var2.setGameType(p_71541_1_);
    

    if (var2.getForceGamemode()) {
      net.minecraft.entity.player.EntityPlayerMP var4;
      for (Iterator var3 = getServergetConfigurationManagerplayerEntityList.iterator(); var3.hasNext(); fallDistance = 0.0F)
      {
        var4 = (net.minecraft.entity.player.EntityPlayerMP)var3.next();
        var4.setGameType(p_71541_1_);
      }
    }
  }
}
