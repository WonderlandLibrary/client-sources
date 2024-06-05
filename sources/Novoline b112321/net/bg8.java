package net;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import net.Ql;
import net.afq;

public class bg8 {
   private final String b;
   private final Date a;

   private bg8(String var1, Date var2) {
      this.b = Ql.a(var1, "reason");
      this.a = var2;
   }

   static bg8 a(String var0, long var1) {
      Ql.a(var1, "unban date");
      return new bg8(var0, new Date(var1));
   }

   static bg8 a(String var0, String var1) {
      return new bg8(var0, a(var1));
   }

   private static Date a(String var0) {
      Ql.a(var0, "date");
      afq.i();
      String[] var2 = var0.split(" ");
      long var3 = 0L;
      int var5 = 0;
      if(var5 < var2.length) {
         String var6 = var2[var5];
         int var7 = var6.length() - 1;
         char var9 = var6.charAt(var7);
         String var10000 = var6;
         byte var10001 = 0;
         int var10002 = var7;

         int var8;
         try {
            var8 = Integer.parseInt(var10000.substring(var10001, var10002));
         } catch (NumberFormatException var11) {
            throw new RuntimeException("An error occurred while parsing number: " + var6.substring(0, var7), var11);
         }

         switch(var9) {
         case 'd':
            var3 += (long)(var8 * 24 * 60 * 60 * 1000);
         case 'h':
            var3 += (long)(var8 * 60 * 60 * 1000);
         case 'm':
            var3 += (long)(var8 * 60 * 1000);
         case 's':
            var3 += (long)(var8 * 1000);
         default:
            ++var5;
         }
      }

      return Date.from(Instant.now().plusMillis(var3));
   }

   public boolean equals(Object var1) {
      String var2 = afq.i();
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof bg8)) {
         return false;
      } else {
         bg8 var3 = (bg8)var1;
         return this.b.equals(var3.b) && Objects.equals(this.a, var3.a);
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.b, this.a});
   }

   public String toString() {
      String var1 = afq.i();
      String var10000 = "HypixelBan{reason=\'" + this.b + '\'' + ", unbanDate=" + this.a + '}';
      if(PacketRemapper.b() == null) {
         afq.b("QXYHQb");
      }

      return var10000;
   }

   private static NumberFormatException a(NumberFormatException var0) {
      return var0;
   }
}
