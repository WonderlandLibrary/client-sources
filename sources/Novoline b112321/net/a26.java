package net;

import net.aTX;
import net.acE;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings$GameType;

public class a26 {
   private static acE[] b;

   public static boolean i(aTX var0) {
      return var0.f();
   }

   public static boolean b(aTX var0, EntityPlayerSP var1, WorldClient var2, ItemStack var3, BlockPos var4, EnumFacing var5, Vec3 var6) {
      return var0.onPlayerRightClick(var1, var2, var3, var4, var5, var6);
   }

   public static void a(aTX var0, int var1, int var2, int var3, int var4, EntityPlayer var5) {
      var0.a(var1, var2, var3, var4, var5);
   }

   public static boolean b(aTX var0, BlockPos var1, EnumFacing var2) {
      return var0.a(var1, var2);
   }

   public static void c(aTX var0) {
      var0.d();
   }

   public static boolean m(aTX var0) {
      return var0.j();
   }

   public static void b(aTX var0, EntityPlayer var1, Entity var2) {
      var0.b(var1, var2);
   }

   public static boolean a(aTX var0, BlockPos var1, EnumFacing var2) {
      return var0.b(var1, var2);
   }

   public static boolean f(aTX var0) {
      return var0.m();
   }

   public static boolean a(aTX var0, EntityPlayer var1, Entity var2, MovingObjectPosition var3) {
      return var0.a(var1, var2, var3);
   }

   public static boolean a(aTX var0, EntityPlayer var1, Entity var2) {
      return var0.a(var1, var2);
   }

   public static boolean e(aTX var0) {
      return var0.h();
   }

   public static boolean a(aTX var0, EntityPlayer var1, World var2, ItemStack var3) {
      return var0.sendUseItem(var1, var2, var3);
   }

   public static void l(aTX var0) {
      var0.g();
   }

   public static boolean a(aTX var0) {
      return var0.a();
   }

   public static void a(aTX var0, EntityPlayer var1) {
      var0.a(var1);
   }

   public static void a(aTX var0, ItemStack var1, int var2) {
      var0.a(var1, var2);
   }

   public static EntityPlayerSP a(aTX var0, World var1, StatFileWriter var2) {
      return var0.a(var1, var2);
   }

   public static void c(aTX var0, EntityPlayer var1) {
      var0.b(var1);
   }

   public static void b(aTX var0, EntityPlayer var1) {
      var0.c(var1);
   }

   public static void a(aTX var0, int var1, int var2) {
      var0.a(var1, var2);
   }

   public static WorldSettings$GameType g(aTX var0) {
      return var0.l();
   }

   public static boolean b(aTX var0) {
      return var0.n();
   }

   public static void c(aTX var0, BlockPos var1, EnumFacing var2) {
      var0.c(var1, var2);
   }

   public static float j(aTX var0) {
      return var0.i();
   }

   public static boolean h(aTX var0) {
      return var0.k();
   }

   public static void a(aTX var0, ItemStack var1) {
      var0.a(var1);
   }

   public static boolean a(aTX var0, EntityPlayerSP var1, WorldClient var2, ItemStack var3, BlockPos var4, EnumFacing var5, Vec3 var6) {
      return var0.b(var1, var2, var3, var4, var5, var6);
   }

   public static void a(aTX var0, WorldSettings$GameType var1) {
      var0.a(var1);
   }

   public static boolean k(aTX var0) {
      return var0.o();
   }

   public static void b(aTX var0, int var1, int var2, int var3, int var4, EntityPlayer var5) {
      var0.b(var1, var2, var3, var4, var5);
   }

   public static boolean d(aTX var0) {
      return var0.b();
   }

   public static boolean n(aTX var0) {
      return var0.c();
   }

   public static void b(acE[] var0) {
      b = var0;
   }

   public static acE[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new acE[1]);
      }

   }
}
