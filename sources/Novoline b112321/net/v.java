package net;

import java.util.Iterator;
import java.util.SortedMap;
import java.util.Map.Entry;
import net.akH;
import net.s;

class v extends s {
   private final Object[] d;
   private final SortedMap a;

   v(Object[] var1, SortedMap var2) {
      this.d = var1;
      this.a = var2;
   }

   public void a(akH var1) {
      akH var3 = var1.a(this.d);
      int var4 = var3.a(-1);
      s.b();
      Iterator var5 = this.a.entrySet().iterator();
      if(var5.hasNext()) {
         Entry var6 = (Entry)var5.next();
         if(((Integer)var6.getKey()).intValue() <= var4) {
            ;
         }

         ((s)var6.getValue()).a(var1);
         var4 = ((Integer)var6.getKey()).intValue();
      }

      var3.a((Object)Integer.valueOf(var4));
   }
}
