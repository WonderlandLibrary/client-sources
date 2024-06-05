package net;

import com.google.common.reflect.TypeToken;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.Ea;
import net.Gi;
import net.Ql;
import net.UW;
import net.X9;
import net.YP;
import net.ZV;
import net.a2V;
import net.a2t;
import net.a88;
import net.aSt;
import net.aZM;
import net.agu;
import net.ah7;
import net.as0;
import net.ava;
import net.aye;
import net.d3;

public final class ar9 extends as0 {
   private final List A = new ObjectArrayList();
   private static a2t x = aZM.a;
   private static d3 z = new d3();
   private final List y;

   public ar9(UW var1) {
      super(var1, "Waypoints", (a2V)a2V.VISUALS, (String)null, "waypoints");
      this.y = Collections.unmodifiableList(this.A);
      this.b();
   }

   private void b() {
      try {
         this.A.addAll(this.i.b().a(new Object[]{"waypoints"}).a((TypeToken)(new ah7(this)), (List)(new ArrayList())));
      } catch (X9 var2) {
         throw new RuntimeException("An error occurred while deserialization of the waypoints file", var2);
      }
   }

   protected void a(Ea var1) {
      var1.a((TypeToken)a88.a, (ZV)(new a88()));
   }

   @agu
   public void a(aSt var1) {
      this.A.forEach(Gi::a);
   }

   @agu
   public void a(aye var1) {
      ava.e();
      Iterator var3 = this.A.iterator();
      if(var3.hasNext()) {
         Gi var4 = (Gi)var3.next();
         float var5 = (float)((double)((float)var4.b() + 0.0F * var1.a()) - this.w.getRenderManager().h);
         float var6 = (float)((double)((float)var4.f() + 0.0F * var1.a()) - this.w.getRenderManager().g);
         float var7 = (float)((double)((float)var4.e() + 0.0F * var1.a()) - this.w.getRenderManager().m);
         var4.a(var4.a((double)var5, (double)var6, (double)var7));
      }

   }

   public void a(Gi var1) {
      this.A.add(var1);
      ar9 var10000 = this;

      try {
         var10000.e();
      } catch (X9 var3) {
         this.r.warn("An error occurred while saving friends list", var3);
      }

   }

   public boolean a(String var1) {
      ava.e();
      Ql.a(var1, "name");
      boolean var3 = this.A.removeIf(ar9::lambda$removeWaypoint$0);
      if(var3) {
         ar9 var10000 = this;

         try {
            var10000.e();
         } catch (X9 var5) {
            this.r.warn("An error occurred while saving friends list", var5);
         }
      }

      return var3;
   }

   private void e() throws X9 {
      this.i.b().a(new Object[]{"waypoints"}).a((TypeToken)(new YP(this)), (Object)this.A);
   }

   public Gi b(String var1) {
      return (Gi)this.A.stream().filter(ar9::lambda$getWaypointByName$1).findFirst().orElse((Object)null);
   }

   public List d() {
      return this.y;
   }

   public List c() {
      return this.A;
   }

   private static boolean lambda$getWaypointByName$1(String var0, Gi var1) {
      return var1.g().equalsIgnoreCase(var0);
   }

   private static boolean lambda$removeWaypoint$0(String var0, Gi var1) {
      return var1.g().equalsIgnoreCase(var0);
   }

   static a2t f() {
      return x;
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
