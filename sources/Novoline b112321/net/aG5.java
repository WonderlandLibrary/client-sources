package net;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.github.steveice10.opennbt.tag.builtin.Tag;
import java.io.DataInput;
import java.io.DataOutput;
import java.util.Iterator;
import java.util.Map;

public class aG5 {
   private static int[] b;

   public static boolean a(CompoundTag var0, String var1) {
      return var0.contains(var1);
   }

   public static Tag a(CompoundTag var0, Tag var1) {
      return var0.put(var1);
   }

   public static Tag c(CompoundTag var0, String var1) {
      return var0.get(var1);
   }

   public static Tag b(CompoundTag var0, String var1) {
      return var0.remove(var1);
   }

   public static boolean a(CompoundTag var0) {
      return var0.isEmpty();
   }

   public static Iterator c(CompoundTag var0) {
      return var0.iterator();
   }

   public static int e(CompoundTag var0) {
      return var0.size();
   }

   public static CompoundTag b(CompoundTag var0) {
      return var0.clone();
   }

   public static Map d(CompoundTag var0) {
      return var0.getValue();
   }

   public static void a(CompoundTag var0, DataInput var1) {
      var0.read(var1);
   }

   public static void a(CompoundTag var0, DataOutput var1) {
      var0.write(var1);
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new int[4]);
      }

   }
}
