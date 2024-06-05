package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;

public class CommandListPlayers extends CommandBase {
   private static final String __OBFID = "CL_00000615";

   public String getCommandUsage(ICommandSender var1) {
      return "commands.players.usage";
   }

   public void processCommand(ICommandSender var1, String[] var2) throws CommandException {
      int var3 = MinecraftServer.getServer().getCurrentPlayerCount();
      var1.addChatMessage(new ChatComponentTranslation("commands.players.list", new Object[]{var3, MinecraftServer.getServer().getMaxPlayers()}));
      var1.addChatMessage(new ChatComponentText(MinecraftServer.getServer().getConfigurationManager().func_180602_f()));
      var1.func_174794_a(CommandResultStats.Type.QUERY_RESULT, var3);
   }

   public String getCommandName() {
      return "list";
   }

   public int getRequiredPermissionLevel() {
      return 0;
   }
}
