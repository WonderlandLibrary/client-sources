package net;

import java.util.Set;
import net.acE;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class dg {
   private static acE[] b;

   public static boolean i(NBTTagCompound var0, String var1) {
      return var0.getBoolean(var1);
   }

   public static void b(NBTTagCompound var0, String var1, int var2) {
      var0.setInteger(var1, var2);
   }

   public static void a(NBTTagCompound var0, String var1, boolean var2) {
      var0.setBoolean(var1, var2);
   }

   public static void a(NBTTagCompound var0, String var1, NBTBase var2) {
      var0.setTag(var1, var2);
   }

   public static int b(NBTTagCompound var0, String var1) {
      return var0.getInteger(var1);
   }

   public static boolean c(NBTTagCompound var0, String var1, int var2) {
      return var0.hasKey(var1, var2);
   }

   public static NBTTagCompound e(NBTTagCompound var0, String var1) {
      return var0.getCompoundTag(var1);
   }

   public static NBTTagList a(NBTTagCompound var0, String var1, int var2) {
      return var0.getTagList(var1, var2);
   }

   public static String o(NBTTagCompound var0, String var1) {
      return var0.getString(var1);
   }

   public static boolean k(NBTTagCompound var0, String var1) {
      return var0.hasKey(var1);
   }

   public static void b(NBTTagCompound var0, String var1, String var2) {
      var0.setString(var1, var2);
   }

   public static void d(NBTTagCompound var0, String var1) {
      var0.removeTag(var1);
   }

   public static byte n(NBTTagCompound var0, String var1) {
      return var0.getByte(var1);
   }

   public static void a(NBTTagCompound var0, String var1, short var2) {
      var0.setShort(var1, var2);
   }

   public static short a(NBTTagCompound var0, String var1) {
      return var0.getShort(var1);
   }

   public static int[] h(NBTTagCompound var0, String var1) {
      return var0.getIntArray(var1);
   }

   public static void a(NBTTagCompound var0, String var1, byte var2) {
      var0.setByte(var1, var2);
   }

   public static void a(NBTTagCompound var0, String var1, double var2) {
      var0.setDouble(var1, var2);
   }

   public static double g(NBTTagCompound var0, String var1) {
      return var0.getDouble(var1);
   }

   public static void a(NBTTagCompound var0, String var1, long var2) {
      var0.setLong(var1, var2);
   }

   public static long f(NBTTagCompound var0, String var1) {
      return var0.getLong(var1);
   }

   public static NBTBase a(NBTTagCompound var0) {
      return var0.copy();
   }

   public static void a(NBTTagCompound var0, NBTTagCompound var1) {
      var0.merge(var1);
   }

   public static void a(NBTTagCompound var0, String var1, float var2) {
      var0.setFloat(var1, var2);
   }

   public static float m(NBTTagCompound var0, String var1) {
      return var0.getFloat(var1);
   }

   public static NBTBase c(NBTTagCompound var0, String var1) {
      return var0.getTag(var1);
   }

   public static Set c(NBTTagCompound var0) {
      return var0.getKeySet();
   }

   public static String a(NBTTagCompound var0, String var1, String var2) {
      return var0.getString(var1, var2);
   }

   public static Integer a(NBTTagCompound var0, String var1, Integer var2) {
      return var0.getInteger(var1, var2);
   }

   public static Long a(NBTTagCompound var0, String var1, Long var2) {
      return var0.getLong(var1, var2);
   }

   public static boolean b(NBTTagCompound var0) {
      return var0.hasNoTags();
   }

   public static byte j(NBTTagCompound var0, String var1) {
      return var0.getTagId(var1);
   }

   public static NBTTagCompound a(NBTTagCompound var0, String var1, NBTTagCompound var2) {
      return var0.getCompoundTag(var1, var2);
   }

   public static void a(NBTTagCompound var0, String var1, int[] var2) {
      var0.setIntArray(var1, var2);
   }

   public static byte[] l(NBTTagCompound var0, String var1) {
      return var0.getByteArray(var1);
   }

   public static void a(NBTTagCompound var0, String var1, byte[] var2) {
      var0.setByteArray(var1, var2);
   }

   public static void b(acE[] var0) {
      b = var0;
   }

   public static acE[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new acE[3]);
      }

   }
}
