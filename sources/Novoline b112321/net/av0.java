package net;

import net.UW;
import net.VN;
import net.WB;
import net.a2V;
import net.a6d;
import net.aEs;
import net.adZ;
import net.ae9;
import net.agu;
import net.as0;
import net.ava;
import net.axu;
import net.gZ;

public final class av0 extends as0 {
   @VN("mode")
   private final aEs x = axu.a("Gamma").a(new String[]{"Gamma", "Effect"});

   public av0(UW var1) {
      super(var1, "Brightness", a2V.VISUALS, "It\'s too dark in here");
      ae9.a(new adZ("BN_MODE", "Mode", a6d.COMBOBOX, this, this.x));
   }

   @agu
   public void a(WB var1) {
      this.c((String)this.x.a());
   }

   public aEs b() {
      return this.x;
   }

   public static boolean a() {
      int var0 = ava.e();
      if(gZ.g().m()) {
         return false;
      } else {
         av0 var1 = (av0)gZ.g().d().b(av0.class);
         return var1.y() && var1.b().a("Gamma");
      }
   }

   public static boolean c() {
      int var0 = ava.e();
      if(gZ.g().m()) {
         return false;
      } else {
         av0 var1 = (av0)gZ.g().d().b(av0.class);
         return var1.y() && var1.b().a("Effect");
      }
   }
}
