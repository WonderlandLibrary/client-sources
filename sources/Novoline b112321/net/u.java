package net;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;
import net.CZ;
import net.MU;
import net.aIg;
import net.akH;
import net.s;

class u extends s {
   private final CZ d;
   private final Map a;
   private final ThreadLocal e = ThreadLocal.withInitial(aIg::<init>);

   u(Map var1, CZ var2) {
      this.a = var1;
      this.d = var2;
   }

   public void a(akH var1) {
      s.b();
      Iterator var3 = this.a.entrySet().iterator();
      if(var3.hasNext()) {
         Entry var4 = (Entry)var3.next();
         this.a(var1, (Object[])var4.getKey(), 0, var1, (MU)var4.getValue());
      }

   }

   private void a(akH var1, Object[] var2, int var3, akH var4, MU var5) {
      s.b();
      if(var3 < var2.length) {
         if(var2[var3] == c) {
            if(var4.p()) {
               List var11 = var4.b();
               int var9 = 0;
               if(var9 < var11.size()) {
                  var2[var3] = Integer.valueOf(var9);
                  this.a(var1, var2, var3 + 1, (akH)var11.get(var9), var5);
                  ++var9;
               }

               var2[var3] = c;
            }

            if(var4.f()) {
               Iterator var12 = var4.s().entrySet().iterator();
               if(var12.hasNext()) {
                  Entry var14 = (Entry)var12.next();
                  var2[var3] = var14.getKey();
                  this.a(var1, var2, var3 + 1, (akH)var14.getValue(), var5);
               }

               var2[var3] = c;
            }

            return;
         }

         var4 = var4.a(new Object[]{var2[var3]});
         if(var4.e()) {
            return;
         }

         int var7 = var3 + 1;
      }

      aIg var10 = (aIg)this.e.get();
      var10.a = var2;
      Object[] var8 = var5.a(var10, var4);
      if(var8 != null && !Arrays.equals(var2, var8)) {
         this.d.a(var4, var1.a(var8));
         var4.a((Object)null);
      }

   }
}
