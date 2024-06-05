package net;

import cc.novoline.events.events.EventState;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;
import net.UW;
import net.VN;
import net.WB;
import net.a2V;
import net.a6d;
import net.aEs;
import net.aG1;
import net.adZ;
import net.ae9;
import net.agu;
import net.ap9;
import net.as0;
import net.as_;
import net.asx;
import net.avS;
import net.axu;
import net.lS;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C03PacketPlayer$C04PacketPlayerPosition;
import net.minecraft.network.play.client.C0APacketAnimation;

public final class asz extends as0 {
   @VN("mode")
   private aEs x = axu.a("Edit").a(new String[]{"Edit", "Packet"});
   private float z;
   private float y;
   private double A;

   public asz(UW var1) {
      super(var1, "Criticals", a2V.COMBAT, "");
      ae9.a(new adZ("CRITICALS_MODE", "Mode", a6d.COMBOBOX, this, this.x));
   }

   public boolean a(asx var1) {
      int[] var2 = asx.Q();
      return this.y() && var1.P() && var1.c(var1.n()) && !this.w.playerController.f() && this.w.thePlayer.onGround && !this.w.thePlayer.N() && !this.w.thePlayer.q() && !this.w.thePlayer.b() && !this.w.thePlayer.ap().d() && !((as_)this.b(as_.class)).y() && (!this.a((Class)avS.class) || !this.w.thePlayer.p());
   }

   private boolean a(Entity var1) {
      int[] var2 = asx.Q();
      return ((asx)this.b(asx.class)).p() <= 6L || var1.hurtResistantTime >= 18;
   }

   @agu
   public void a(aG1 var1) {
      int[] var2 = asx.Q();
      if(var1.h().equals(EventState.PRE) && this.x.a("Edit") && this.a((Class)asx.class)) {
         asx var3 = (asx)this.b(asx.class);
         if(this.a(var3)) {
            Iterator var4 = var3.z().iterator();
            if(var4.hasNext()) {
               Entity var5 = (Entity)var4.next();
               int var6 = var5.hurtResistantTime;
               if(lS.c()) {
                  switch(var6) {
                  case 19:
                     var1.c(false);
                     var1.c(var1.f() + 0.0145D + ThreadLocalRandom.current().nextDouble(0.001D, 0.0011D));
                  case 18:
                     var1.c(false);
                     var1.c(var1.f() + 0.0105D + ThreadLocalRandom.current().nextDouble(0.001D, 0.0011D));
                  case 17:
                     var1.c(false);
                     var1.c(var1.f() + ThreadLocalRandom.current().nextDouble(0.001D, 0.0011D));
                  }
               }

               switch(var6) {
               case 17:
                  var1.c(false);
                  var1.c(var1.f() + ThreadLocalRandom.current().nextDouble(0.001D, 0.0011D));
               case 18:
                  var1.c(false);
                  var1.c(var1.f() + 0.0722435151D);
               case 19:
                  var1.c(false);
                  var1.c(var1.f() + ThreadLocalRandom.current().nextDouble(0.001D, 0.0011D));
               case 20:
                  var1.c(false);
                  var1.c(var1.f() + 0.0722435151D);
               }
            }
         }
      }

   }

   @agu
   public void b(ap9 var1) {
      int[] var2 = asx.Q();
      if(this.x.a("Packet") && var1.d() instanceof C0APacketAnimation && this.a((Class)asx.class)) {
         asx var3 = (asx)this.b(asx.class);
         if(this.a(var3)) {
            Iterator var4 = var3.z().iterator();
            if(var4.hasNext()) {
               Entity var5 = (Entity)var4.next();
               if(this.a(var5)) {
                  if(lS.c() && !this.w.isSingleplayer()) {
                     boolean var19 = true;
                  } else {
                     boolean var10000 = false;
                  }

                  double var7 = this.w.thePlayer.posX;
                  double var9 = this.w.thePlayer.posY;
                  double var11 = this.w.thePlayer.posZ;
                  double var13 = 0.01125D;
                  double var15 = ThreadLocalRandom.current().nextDouble(0.001D, 0.0011D);
                  int var17 = 0;
                  if(var17 < 1) {
                     this.b(new C03PacketPlayer$C04PacketPlayerPosition(var7, var9 + var13 + var15, var11, false));
                     this.b(new C03PacketPlayer$C04PacketPlayerPosition(var7, var9 + var15, var11, false));
                     ++var17;
                  }
               }
            }
         }
      }

   }

   @agu
   public void a(WB var1) {
      this.c((String)this.x.a());
   }

   public void n() {
      this.c((String)this.x.a());
   }

   public aEs a() {
      return this.x;
   }
}
