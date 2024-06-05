package net;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameRules$ValueType;

public class a_s {
   private static boolean b;

   public static boolean a(GameRules var0, String var1) {
      return var0.getBoolean(var1);
   }

   public static void a(GameRules var0, String var1, String var2) {
      var0.setOrCreateGameRule(var1, var2);
   }

   public static int d(GameRules var0, String var1) {
      return var0.getInt(var1);
   }

   public static void a(GameRules var0, NBTTagCompound var1) {
      var0.readFromNBT(var1);
   }

   public static NBTTagCompound b(GameRules var0) {
      return var0.writeToNBT();
   }

   public static String[] a(GameRules var0) {
      return var0.getRules();
   }

   public static boolean b(GameRules var0, String var1) {
      return var0.hasRule(var1);
   }

   public static String c(GameRules var0, String var1) {
      return var0.a(var1);
   }

   public static boolean a(GameRules var0, String var1, GameRules$ValueType var2) {
      return var0.areSameType(var1, var2);
   }

   public static void b(boolean var0) {
      b = var0;
   }

   public static boolean b() {
      return b;
   }

   public static boolean a() {
      boolean var0 = b();
      return true;
   }

   static {
      if(a()) {
         b(true);
      }

   }
}
