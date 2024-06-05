package net.minecraft.command.server;

import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;

public class CommandListBans extends CommandBase {
   public String getCommandName() {
      return "banlist";
   }

   public int getRequiredPermissionLevel() {
      return 3;
   }

   public boolean canCommandSenderUseCommand(ICommandSender var1) {
      return (MinecraftServer.getServer().getConfigurationManager().getBannedIPs().isLanServer() || MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().isLanServer()) && super.canCommandSenderUseCommand(var1);
   }

   public String getCommandUsage(ICommandSender var1) {
      return "commands.banlist.usage";
   }

   public void processCommand(ICommandSender var1, String[] var2) throws CommandException {
      if(var2.length >= 1 && var2[0].equalsIgnoreCase("ips")) {
         var1.addChatMessage(new ChatComponentTranslation("commands.banlist.ips", new Object[]{Integer.valueOf(MinecraftServer.getServer().getConfigurationManager().getBannedIPs().getKeys().length)}));
         var1.addChatMessage(new ChatComponentText(joinNiceString(MinecraftServer.getServer().getConfigurationManager().getBannedIPs().getKeys())));
      } else {
         var1.addChatMessage(new ChatComponentTranslation("commands.banlist.players", new Object[]{Integer.valueOf(MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().getKeys().length)}));
         var1.addChatMessage(new ChatComponentText(joinNiceString(MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().getKeys())));
      }

   }

   public List addTabCompletionOptions(ICommandSender var1, String[] var2, BlockPos var3) {
      return var2.length == 1?getListOfStringsMatchingLastWord(var2, new String[]{"players", "ips"}):null;
   }

   private static CommandException a(CommandException var0) {
      return var0;
   }
}
