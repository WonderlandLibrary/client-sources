package net.minecraft.command;

import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats$Type;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

public class CommandXP extends CommandBase {
   public String getCommandName() {
      return "xp";
   }

   public int getRequiredPermissionLevel() {
      return 2;
   }

   public String getCommandUsage(ICommandSender var1) {
      return "commands.xp.usage";
   }

   public void processCommand(ICommandSender var1, String[] var2) throws CommandException {
      if(var2.length <= 0) {
         throw new WrongUsageException("commands.xp.usage", new Object[0]);
      } else {
         String var3 = var2[0];
         boolean var4 = var3.endsWith("l") || var3.endsWith("L");
         if(var3.length() > 1) {
            var3 = var3.substring(0, var3.length() - 1);
         }

         int var5 = parseInt(var3);
         boolean var6 = true;
         var5 = var5 * -1;
         EntityPlayerMP var7 = var2.length > 1?getPlayer(var1, var2[1]):getCommandSenderAsPlayer(var1);
         var1.setCommandStat(CommandResultStats$Type.QUERY_RESULT, var7.experienceLevel);
         var7.addExperienceLevel(-var5);
         notifyOperators(var1, this, "commands.xp.success.negative.levels", new Object[]{Integer.valueOf(var5), var7.getName()});
      }
   }

   public List addTabCompletionOptions(ICommandSender var1, String[] var2, BlockPos var3) {
      return var2.length == 2?getListOfStringsMatchingLastWord(var2, this.getAllUsernames()):null;
   }

   protected String[] getAllUsernames() {
      return MinecraftServer.getServer().getAllUsernames();
   }

   public boolean isUsernameIndex(String[] var1, int var2) {
      return var2 == 1;
   }

   private static CommandException a(CommandException var0) {
      return var0;
   }
}
