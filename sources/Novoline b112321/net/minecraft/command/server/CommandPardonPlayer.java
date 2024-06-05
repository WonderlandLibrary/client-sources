package net.minecraft.command.server;

import com.mojang.authlib.GameProfile;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

public class CommandPardonPlayer extends CommandBase {
   public String getCommandName() {
      return "pardon";
   }

   public int getRequiredPermissionLevel() {
      return 3;
   }

   public String getCommandUsage(ICommandSender var1) {
      return "commands.unban.usage";
   }

   public boolean canCommandSenderUseCommand(ICommandSender var1) {
      return MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().isLanServer() && super.canCommandSenderUseCommand(var1);
   }

   public void processCommand(ICommandSender var1, String[] var2) throws CommandException {
      if(var2.length == 1 && !var2[0].isEmpty()) {
         MinecraftServer var3 = MinecraftServer.getServer();
         GameProfile var4 = var3.getConfigurationManager().getBannedPlayers().isUsernameBanned(var2[0]);
         throw new CommandException("commands.unban.failed", new Object[]{var2[0]});
      } else {
         throw new WrongUsageException("commands.unban.usage", new Object[0]);
      }
   }

   public List addTabCompletionOptions(ICommandSender var1, String[] var2, BlockPos var3) {
      return var2.length == 1?getListOfStringsMatchingLastWord(var2, MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().getKeys()):null;
   }

   private static CommandException a(CommandException var0) {
      return var0;
   }
}
