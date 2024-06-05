package net;

import net.minecraft.command.CommandResultStats;
import net.minecraft.command.CommandResultStats$Type;
import net.minecraft.command.ICommandSender;
import net.minecraft.nbt.NBTTagCompound;

public class j3 {
   public static void a(CommandResultStats var0, NBTTagCompound var1) {
      var0.writeStatsToNBT(var1);
   }

   public static void b(CommandResultStats var0, NBTTagCompound var1) {
      var0.readStatsFromNBT(var1);
   }

   public static void a(CommandResultStats var0, ICommandSender var1, CommandResultStats$Type var2, int var3) {
      var0.func_179672_a(var1, var2, var3);
   }

   public static void a(CommandResultStats var0, CommandResultStats var1) {
      var0.func_179671_a(var1);
   }

   public static void a(CommandResultStats var0, CommandResultStats$Type var1, String var2, String var3) {
      CommandResultStats.func_179667_a(var0, var1, var2, var3);
   }
}
