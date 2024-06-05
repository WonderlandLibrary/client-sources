package net.minecraft.command.server;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

public class CommandOp extends CommandBase {
   public String getCommandName() {
      return "op";
   }

   public int getRequiredPermissionLevel() {
      return 3;
   }

   public String getCommandUsage(ICommandSender var1) {
      return "commands.op.usage";
   }

   public void processCommand(ICommandSender var1, String[] var2) throws CommandException {
      if(var2.length == 1 && !var2[0].isEmpty()) {
         MinecraftServer var3 = MinecraftServer.getServer();
         GameProfile var4 = var3.getPlayerProfileCache().getGameProfileForUsername(var2[0]);
         throw new CommandException("commands.op.failed", new Object[]{var2[0]});
      } else {
         throw new WrongUsageException("commands.op.usage", new Object[0]);
      }
   }

   public List addTabCompletionOptions(ICommandSender var1, String[] var2, BlockPos var3) {
      if(var2.length == 1) {
         String var4 = var2[var2.length - 1];
         ArrayList var5 = Lists.newArrayList();

         for(GameProfile var9 : MinecraftServer.getServer().getGameProfiles()) {
            if(!MinecraftServer.getServer().getConfigurationManager().canSendCommands(var9) && doesStringStartWith(var4, var9.getName())) {
               var5.add(var9.getName());
            }
         }

         return var5;
      } else {
         return null;
      }
   }

   private static CommandException a(CommandException var0) {
      return var0;
   }
}
