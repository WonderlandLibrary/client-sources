package net;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.util.function.Supplier;
import net.BT;
import net.UW;
import net.VN;
import net.WB;
import net.a2V;
import net.a6d;
import net.aEs;
import net.adZ;
import net.ae9;
import net.agu;
import net.ap9;
import net.as0;
import net.as_;
import net.axu;
import net.bgM;
import net.lS;
import net.uO;
import org.jetbrains.annotations.NotNull;

public class as5 extends as0 {
   @VN("mode")
   private aEs y;
   @VN("wd-mode")
   private aEs x;

   public as5(@NotNull UW var1) {
      super(var1, a2V.EXPLOITS, "Disabler", "Disabler");
      int var10000 = as_.a();
      this.y = axu.a("Watchdog").a(new String[]{"Watchdog", "Verus", "Simple"});
      int var2 = var10000;
      this.x = axu.a("Advanced").a(new String[]{"Advanced", "Normal"});
      ae9.a(new adZ("DISABLER_MODE", "Mode", a6d.COMBOBOX, this, this.y));
      ae9.a(new adZ("DISABLER_WD_MODE", "Watchdog Mode", a6d.COMBOBOX, this, this.x, this::lambda$new$0));
      if(PacketRemapper.b() == null) {
         ++var2;
         as_.b(var2);
      }

   }

   @agu
   public void b(ap9 var1) {
      int var2 = as_.b();
      if(this.y.a("Watchdog")) {
         bgM.d().e(var1);
         if(!this.x.a("Advanced")) {
            return;
         }

         bgM.d().d(var1);
      }

      if(this.y.a("Verus")) {
         if(this.w.theWorld.getScoreboard().getObjectiveInDisplaySlot(1).getDisplayName().equals("§a§lBlocks§e§lMC")) {
            return;
         }

         bgM.d().b(var1);
      }

      if(lS.a()) {
         bgM.d().c(var1);
      }

      if(lS.g()) {
         bgM.d().a(var1);
      }

   }

   @agu
   public void a(uO var1) {
      bgM.d().a(var1);
   }

   @agu
   public void a(BT var1) {
      if(this.y.a("Verus")) {
         bgM.d().a(var1);
      }

   }

   @agu
   public void a(WB var1) {
      this.c((String)this.y.a());
   }

   public void n() {
      this.c((String)this.y.a());
   }

   public aEs b() {
      return this.y;
   }

   public aEs a() {
      return this.x;
   }

   private Boolean lambda$new$0() {
      return Boolean.valueOf(this.y.a("Watchdog"));
   }
}
