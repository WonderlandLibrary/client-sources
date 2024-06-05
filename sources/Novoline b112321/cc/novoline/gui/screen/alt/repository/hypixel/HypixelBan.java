package cc.novoline.gui.screen.alt.repository.hypixel;

import cc.novoline.gui.screen.alt.repository.hypixel.HypixelProfile;
import cc.novoline.utils.java.Checks;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import net.acE;

public class HypixelBan {
   private final String reason;
   private final Date unbanDate;

   private HypixelBan(String var1, Date var2) {
      this.reason = Checks.notBlank(var1, "reason");
      this.unbanDate = var2;
   }

   static HypixelBan of(String var0, long var1) {
      Checks.notNegative(var1, "unban date");
      return new HypixelBan(var0, new Date(var1));
   }

   static HypixelBan of(String var0, String var1) {
      return new HypixelBan(var0, parse(var1));
   }

   private static Date parse(String var0) {
      Checks.notBlank(var0, "date");
      HypixelProfile.i();
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
      String var2 = HypixelProfile.i();
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof HypixelBan)) {
         return false;
      } else {
         HypixelBan var3 = (HypixelBan)var1;
         return this.reason.equals(var3.reason) && Objects.equals(this.unbanDate, var3.unbanDate);
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.reason, this.unbanDate});
   }

   public String toString() {
      String var1 = HypixelProfile.i();
      String var10000 = "HypixelBan{reason=\'" + this.reason + '\'' + ", unbanDate=" + this.unbanDate + '}';
      if(acE.b() == null) {
         HypixelProfile.b("QXYHQb");
      }

      return var10000;
   }

   private static NumberFormatException a(NumberFormatException var0) {
      return var0;
   }
}
