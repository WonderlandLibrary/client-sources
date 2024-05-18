package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.BanList;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.server.management.UserListBans;

public class CommandListBans extends CommandBase
{
  private static final String __OBFID = "CL_00000596";
  
  public CommandListBans() {}
  
  public String getCommandName()
  {
    return "banlist";
  }
  



  public int getRequiredPermissionLevel()
  {
    return 3;
  }
  



  public boolean canCommandSenderUseCommand(ICommandSender sender)
  {
    return ((MinecraftServer.getServer().getConfigurationManager().getBannedIPs().isLanServer()) || (MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().isLanServer())) && (super.canCommandSenderUseCommand(sender));
  }
  
  public String getCommandUsage(ICommandSender sender)
  {
    return "commands.banlist.usage";
  }
  
  public void processCommand(ICommandSender sender, String[] args) throws net.minecraft.command.CommandException
  {
    if ((args.length >= 1) && (args[0].equalsIgnoreCase("ips")))
    {
      sender.addChatMessage(new net.minecraft.util.ChatComponentTranslation("commands.banlist.ips", new Object[] { Integer.valueOf(MinecraftServer.getServer().getConfigurationManager().getBannedIPs().getKeys().length) }));
      sender.addChatMessage(new net.minecraft.util.ChatComponentText(joinNiceString(MinecraftServer.getServer().getConfigurationManager().getBannedIPs().getKeys())));
    }
    else
    {
      sender.addChatMessage(new net.minecraft.util.ChatComponentTranslation("commands.banlist.players", new Object[] { Integer.valueOf(MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().getKeys().length) }));
      sender.addChatMessage(new net.minecraft.util.ChatComponentText(joinNiceString(MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().getKeys())));
    }
  }
  
  public java.util.List addTabCompletionOptions(ICommandSender sender, String[] args, net.minecraft.util.BlockPos pos)
  {
    return args.length == 1 ? getListOfStringsMatchingLastWord(args, new String[] { "players", "ips" }) : null;
  }
}
