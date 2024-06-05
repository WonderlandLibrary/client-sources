package net;

import com.google.common.collect.Sets;
import java.util.Set;
import net.Dl;
import net.acE;
import viaversion.viaversion.api.data.UserConnection;
import viaversion.viaversion.api.storage.EntityTracker;

public class cw extends EntityTracker {
   private final Set h;
   private static int g;

   public cw(UserConnection var1) {
      super(var1, Dl.PLAYER);
      int var10000 = c();
      this.h = Sets.newConcurrentHashSet();
      int var2 = var10000;
      if(acE.b() == null) {
         ++var2;
         f(var2);
      }

   }

   public void removeEntity(int var1) {
      c();
      super.removeEntity(var1);
      if(this.a(var1)) {
         this.c(var1);
      }

   }

   public void e(int var1) {
      this.h.add(Integer.valueOf(var1));
   }

   public boolean a(int var1) {
      return this.h.contains(Integer.valueOf(var1));
   }

   public void c(int var1) {
      this.h.remove(Integer.valueOf(var1));
   }

   public static void f(int var0) {
      g = var0;
   }

   public static int a() {
      return g;
   }

   public static int c() {
      int var0 = a();
      return 57;
   }

   static {
      if(a() != 0) {
         f(54);
      }

   }
}
