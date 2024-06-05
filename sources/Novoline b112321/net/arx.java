package net;

import net.acE;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatFileWriter;

public class arx {
   private static acE[] b;

   public static int a(StatFileWriter var0, StatBase var1) {
      return var0.readStat(var1);
   }

   public static void a(StatFileWriter var0, EntityPlayer var1, StatBase var2, int var3) {
      var0.unlockAchievement(var1, var2, var3);
   }

   public static boolean c(StatFileWriter var0, Achievement var1) {
      return var0.hasAchievementUnlocked(var1);
   }

   public static boolean a(StatFileWriter var0, Achievement var1) {
      return var0.canUnlockAchievement(var1);
   }

   public static int b(StatFileWriter var0, Achievement var1) {
      return var0.func_150874_c(var1);
   }

   public static void b(acE[] var0) {
      b = var0;
   }

   public static acE[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new acE[1]);
      }

   }
}
