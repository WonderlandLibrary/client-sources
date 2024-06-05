package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats$Type;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;

public class CommandListPlayers extends CommandBase {
   public String getCommandName() {
      return "list";
   }

   public int getRequiredPermissionLevel() {
      return 0;
   }

   public String getCommandUsage(ICommandSender var1) {
      return "commands.players.usage";
   }

   public void processCommand(ICommandSender var1, String[] var2) throws CommandException {
      int var3 = MinecraftServer.getServer().getCurrentPlayerCount();
      var1.addChatMessage(new ChatComponentTranslation("commands.players.list", new Object[]{Integer.valueOf(var3), Integer.valueOf(MinecraftServer.getServer().getMaxPlayers())}));
      var1.addChatMessage(new ChatComponentText(MinecraftServer.getServer().getConfigurationManager().func_181058_b(var2.length > 0 && "uuids".equalsIgnoreCase(var2[0]))));
      var1.setCommandStat(CommandResultStats$Type.QUERY_RESULT, var3);
   }

   private static CommandException a(CommandException var0) {
      return var0;
   }
}
