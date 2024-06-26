package net.minecraft.command;

import java.util.List;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;

public class CommandServerKick
  extends CommandBase
{
  private static final String __OBFID = "CL_00000550";
  
  public CommandServerKick() {}
  
  public String getCommandName()
  {
    return "kick";
  }
  
  public int getRequiredPermissionLevel()
  {
    return 3;
  }
  
  public String getCommandUsage(ICommandSender sender)
  {
    return "orders.kick.usage";
  }
  
  public void processCommand(ICommandSender sender, String[] args)
    throws CommandException
  {
    if ((args.length > 0) && (args[0].length() > 1))
    {
      EntityPlayerMP var3 = MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(args[0]);
      String var4 = "Kicked by an operator.";
      boolean var5 = false;
      if (var3 == null) {
        throw new PlayerNotFoundException();
      }
      if (args.length >= 2)
      {
        var4 = getChatComponentFromNthArg(sender, args, 1).getUnformattedText();
        var5 = true;
      }
      var3.playerNetServerHandler.kickPlayerFromServer(var4);
      if (var5) {
        notifyOperators(sender, this, "orders.kick.success.reason", new Object[] { var3.getName(), var4 });
      } else {
        notifyOperators(sender, this, "orders.kick.success", new Object[] { var3.getName() });
      }
    }
    else
    {
      throw new WrongUsageException("orders.kick.usage", new Object[0]);
    }
  }
  
  public List addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos)
  {
    return args.length >= 1 ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : null;
  }
}
