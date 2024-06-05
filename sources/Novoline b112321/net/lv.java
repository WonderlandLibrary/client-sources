package net;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class lv {
   public static boolean a(Entity var0, String var1) {
      return EntityList.isStringEntityName(var0, var1);
   }

   public static int b(Entity var0) {
      return EntityList.getEntityID(var0);
   }

   public static Class b(int var0) {
      return EntityList.getClassFromID(var0);
   }

   public static boolean b(String var0) {
      return EntityList.isStringValidEntityName(var0);
   }

   public static int a(String var0) {
      return EntityList.getIDFromString(var0);
   }

   public static String a(int var0) {
      return EntityList.getStringFromID(var0);
   }

   public static Entity a(int var0, World var1) {
      return EntityList.createEntityByID(var0, var1);
   }

   public static String a(Entity var0) {
      return EntityList.getEntityString(var0);
   }

   public static Entity a(String var0, World var1) {
      return EntityList.createEntityByName(var0, var1);
   }

   public static Entity a(NBTTagCompound var0, World var1) {
      return EntityList.createEntityFromNBT(var0, var1);
   }

   public static List b() {
      return EntityList.getEntityNameList();
   }

   public static void a() {
      EntityList.func_151514_a();
   }
}
