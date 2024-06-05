package net;

import com.google.common.reflect.TypeToken;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap.Entry;
import java.util.Iterator;
import net.I6;
import net.Ql;
import net.UW;
import net.X9;
import net.ZV;
import net.akH;
import net.am5;
import net.as0;
import net.ck;
import net.t8;
import net.tm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class kq implements ZV {
   private final Logger b = LogManager.getLogger();
   private final UW a;

   public kq(UW var1) {
      Ql.a((Object)var1, "module manager");
      this.a = var1;
   }

   public void a(TypeToken var1, I6 var2, akH var3) throws X9 {
      int[] var4 = ck.b();
      if(var2 == null) {
         var3.a((Object)null);
      } else {
         ObjectIterator var5 = var2.object2ObjectEntrySet().iterator();
         if(var5.hasNext()) {
            Entry var6 = (Entry)var5.next();
            String var7 = (String)var6.getKey();
            t8 var8 = (t8)var6.getValue();
            if(var8 != null) {
               TypeToken var9 = ((t8)var6.getValue()).c();
               as0 var10 = ((t8)var6.getValue()).b();
               var3.a(new Object[]{var7}).a((TypeToken)var9, (Object)var10);
            }
         }

      }
   }

   public I6 a(TypeToken var1, akH var2) {
      ck.b();
      I6 var4 = this.a.e();
      I6 var5 = new I6();
      Iterator var7 = var2.s().entrySet().iterator();
      if(var7.hasNext()) {
         java.util.Map.Entry var8 = (java.util.Map.Entry)var7.next();
         String var9 = var8.getKey().toString();
         akH var10 = (akH)var8.getValue();
         t8 var6;
         if(var10.a() != am5.NULL && (var6 = (t8)var4.get(var9)) != null) {
            as0 var11 = null;
            X9 var12 = null;

            try {
               var11 = (as0)var10.b(var6.c());
            } catch (X9 var14) {
               var12 = var14;
            }

            if(var11 == null) {
               String var13 = "Cannot initiate " + var9 + " module. Skipping it...";
               this.b.warn(var13, var12);
               this.b.warn(var13);
            }

            var5.put(var9, tm.a(var9, var11));
         }
      }

      if(PacketRemapper.b() == null) {
         ck.b(new int[1]);
      }

      return var5;
   }

   private static X9 a(X9 var0) {
      return var0;
   }
}
