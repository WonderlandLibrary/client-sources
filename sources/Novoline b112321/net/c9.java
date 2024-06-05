package net;

import com.viaversion.viaversion.api.minecraft.Position;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.bgR;
import net.cA;

public class c9 extends cA {
   private static final IntSet e = new IntOpenHashSet(779);
   private final Map c = new ConcurrentHashMap();
   private static boolean d;

   public c9(bgR var1) {
      super(var1);
   }

   public void a(Position var1, int var2) {
      boolean var3 = e();
      if(!e.contains(var2)) {
         this.c.remove(var1);
      } else {
         this.c.put(var1, Integer.valueOf(var2));
      }
   }

   public boolean a(int var1) {
      return e.contains(var1);
   }

   public Integer a(Position var1) {
      return (Integer)this.c.get(var1);
   }

   public int b(Position var1) {
      return ((Integer)this.c.remove(var1)).intValue();
   }

   public void f() {
      this.c.clear();
   }

   public Map a() {
      return this.c;
   }

   static {
      a(false);

      for(int var0 = 5265; var0 <= 5286; ++var0) {
         e.add(var0);
      }

      for(int var1 = 0; var1 < 256; ++var1) {
         e.add(748 + var1);
      }

      for(int var2 = 6854; var2 <= 7173; ++var2) {
         e.add(var2);
      }

      e.add(1647);

      for(int var3 = 5447; var3 <= 5566; ++var3) {
         e.add(var3);
      }

      for(int var4 = 1028; var4 <= 1039; ++var4) {
         e.add(var4);
      }

      for(int var5 = 1047; var5 <= 1082; ++var5) {
         e.add(var5);
      }

      for(int var6 = 1099; var6 <= 1110; ++var6) {
         e.add(var6);
      }

   }

   public static void a(boolean var0) {
      d = var0;
   }

   public static boolean d() {
      return d;
   }

   public static boolean e() {
      boolean var0 = d();
      return true;
   }
}
