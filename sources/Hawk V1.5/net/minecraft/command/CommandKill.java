package net.minecraft.command;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;

public class CommandKill extends CommandBase {
   private static final String __OBFID = "CL_00000570";

   public boolean isUsernameIndex(String[] var1, int var2) {
      return var2 == 0;
   }

   public String getCommandUsage(ICommandSender var1) {
      return "commands.kill.usage";
   }

   public String getCommandName() {
      return "kill";
   }

   public void processCommand(ICommandSender var1, String[] var2) throws CommandException {
      if (var2.length == 0) {
         EntityPlayerMP var3 = getCommandSenderAsPlayer(var1);
         var3.func_174812_G();
         notifyOperators(var1, this, "commands.kill.successful", new Object[]{var3.getDisplayName()});
      } else {
         Entity var4 = func_175768_b(var1, var2[0]);
         var4.func_174812_G();
         notifyOperators(var1, this, "commands.kill.successful", new Object[]{var4.getDisplayName()});
      }

   }

   public int getRequiredPermissionLevel() {
      return 2;
   }
}
