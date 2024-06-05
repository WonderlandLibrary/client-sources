package net;

import java.util.List;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;

public class awE {
   public static int a(ICommandManager var0, ICommandSender var1, String var2) {
      return var0.executeCommand(var1, var2);
   }

   public static List a(ICommandManager var0, ICommandSender var1, String var2, BlockPos var3) {
      return var0.getTabCompletionOptions(var1, var2, var3);
   }

   public static List a(ICommandManager var0, ICommandSender var1) {
      return var0.getPossibleCommands(var1);
   }
}
