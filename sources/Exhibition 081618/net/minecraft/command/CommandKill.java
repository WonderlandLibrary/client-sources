package net.minecraft.command;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;

public class CommandKill extends CommandBase {
   public String getCommandName() {
      return "kill";
   }

   public int getRequiredPermissionLevel() {
      return 2;
   }

   public String getCommandUsage(ICommandSender sender) {
      return "commands.kill.usage";
   }

   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
      if (args.length == 0) {
         EntityPlayerMP var4 = CommandBase.getCommandSenderAsPlayer(sender);
         var4.func_174812_G();
         CommandBase.notifyOperators(sender, this, "commands.kill.successful", var4.getDisplayName());
      } else {
         Entity var3 = CommandBase.func_175768_b(sender, args[0]);
         var3.func_174812_G();
         CommandBase.notifyOperators(sender, this, "commands.kill.successful", var3.getDisplayName());
      }

   }

   public boolean isUsernameIndex(String[] args, int index) {
      return index == 0;
   }
}
