package net;

import com.github.steveice10.opennbt.tag.builtin.ListTag;
import com.github.steveice10.opennbt.tag.builtin.Tag;
import java.util.Iterator;
import java.util.List;

public class aes {
   private static String[] b;

   public static Iterator c(ListTag var0) {
      return var0.iterator();
   }

   public static ListTag b(ListTag var0) {
      return var0.clone();
   }

   public static List e(ListTag var0) {
      return var0.getValue();
   }

   public static boolean b(ListTag var0, Tag var1) {
      return var0.add(var1);
   }

   public static int a(ListTag var0) {
      return var0.size();
   }

   public static void a(ListTag var0, List var1) {
      var0.setValue(var1);
   }

   public static boolean a(ListTag var0, Tag var1) {
      return var0.remove(var1);
   }

   public static Tag a(ListTag var0, int var1) {
      return var0.get(var1);
   }

   public static String d(ListTag var0) {
      return var0.getName();
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new String[1]);
      }

   }
}
