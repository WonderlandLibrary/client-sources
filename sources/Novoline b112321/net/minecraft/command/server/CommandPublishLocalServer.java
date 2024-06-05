package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldSettings$GameType;

public class CommandPublishLocalServer extends CommandBase {
   public String getCommandName() {
      return "publish";
   }

   public String getCommandUsage(ICommandSender var1) {
      return "commands.publish.usage";
   }

   public void processCommand(ICommandSender var1, String[] var2) throws CommandException {
      String var3 = MinecraftServer.getServer().shareToLAN(WorldSettings$GameType.SURVIVAL, false);
      notifyOperators(var1, this, "commands.publish.started", new Object[]{var3});
   }

   private static CommandException a(CommandException var0) {
      return var0;
   }
}
