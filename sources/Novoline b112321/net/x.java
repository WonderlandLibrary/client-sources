package net;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import viaversion.viaversion.api.minecraft.item.Item;

public class x {
   private static int b;

   public static void a(Item var0, int var1) {
      var0.setIdentifier(var1);
   }

   public static void a(Item var0, short var1) {
      var0.setData(var1);
   }

   public static CompoundTag d(Item var0) {
      return var0.getTag();
   }

   public static void a(Item var0, CompoundTag var1) {
      var0.setTag(var1);
   }

   public static int a(Item var0) {
      return var0.getIdentifier();
   }

   public static short c(Item var0) {
      return var0.getData();
   }

   public static void a(Item var0, byte var1) {
      var0.setAmount(var1);
   }

   public static byte b(Item var0) {
      return var0.getAmount();
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int a() {
      int var0 = b();
      return 101;
   }

   static {
      if(b() == 0) {
         b(94);
      }

   }
}
