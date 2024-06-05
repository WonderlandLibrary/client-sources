package net;

import cc.novoline.events.events.PacketDirection;
import net.UW;
import net.VN;
import net.WB;
import net.a2V;
import net.a6d;
import net.aE8;
import net.aEu;
import net.adZ;
import net.ae9;
import net.agu;
import net.ap9;
import net.as0;
import net.asx;
import net.axu;
import net.uO;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

public final class asa extends as0 {
   int z;
   @VN("horizontal")
   private final aE8 A = (aE8)((aE8)axu.b(Integer.valueOf(0)).d(Integer.valueOf(-100))).c(Integer.valueOf(100));
   @VN("vertical")
   private final aE8 x = (aE8)((aE8)axu.b(Integer.valueOf(0)).d(Integer.valueOf(0))).c(Integer.valueOf(100));
   @VN("chance")
   private final aE8 y = (aE8)((aE8)axu.b(Integer.valueOf(100)).d(Integer.valueOf(0))).c(Integer.valueOf(100));
   @VN("skip")
   private final aEu B = axu.g();

   public asa(UW var1) {
      super(var1, "Velocity", "Velocity", 0, a2V.COMBAT, "Don\'t take knockback");
      ae9.a(new adZ("VEL_HOR", "Horizontal", a6d.SLIDER, this, this.A, 5.0D));
      ae9.a(new adZ("VEL_VER", "Vertical", a6d.SLIDER, this, this.x, 5.0D));
      ae9.a(new adZ("VEL_CHANCE", "Chance", a6d.SLIDER, this, this.y, 5.0D));
      ae9.a(new adZ("VEL_SKIP", "Skip", a6d.CHECKBOX, this, this.B));
   }

   @agu
   private void b(ap9 var1) {
      int[] var2 = asx.Q();
      if(var1.a().equals(PacketDirection.INCOMING) && Math.random() <= (double)(((Integer)this.y.a()).intValue() / 100)) {
         this.a(var1, ((Integer)this.A.a()).intValue(), ((Integer)this.x.a()).intValue());
      }

   }

   public void a(ap9 var1, int var2, int var3) {
      int[] var4 = asx.Q();
      if(var1.d() instanceof S12PacketEntityVelocity) {
         S12PacketEntityVelocity var5 = (S12PacketEntityVelocity)var1.d();
         if(var5.getEntityID() == this.w.thePlayer.getEntityId()) {
            ++this.z;
            if(!((Boolean)this.B.a()).booleanValue() || this.z % 2 == 0) {
               double var6 = (double)(var5.getMotionX() * var2 / 100);
               double var8 = (double)(var5.getMotionY() * var3 / 100);
               double var10 = (double)(var5.getMotionZ() * var2 / 100);
               if(var2 != 0) {
                  this.w.thePlayer.motionX = var6 / 8000.0D;
                  this.w.thePlayer.motionZ = var10 / 8000.0D;
               }

               if(var3 != 0) {
                  this.w.thePlayer.motionY = var8 / 8000.0D;
               }

               var1.setCancelled(true);
            }
         }
      }

      if(var1.d() instanceof S27PacketExplosion) {
         S27PacketExplosion var12 = (S27PacketExplosion)var1.d();
         if(!((Boolean)this.B.a()).booleanValue() || this.z % 2 == 0) {
            double var13 = (double)(var12.func_149149_c() * (float)var2 / 100.0F);
            double var14 = (double)(var12.func_149144_d() * (float)var3 / 100.0F);
            double var15 = (double)(var12.func_149147_e() * (float)var2 / 100.0F);
            if(var2 != 0) {
               this.w.thePlayer.motionX += var13;
               this.w.thePlayer.motionZ += var15;
            }

            if(var3 != 0) {
               this.w.thePlayer.motionY += var14;
            }

            var1.setCancelled(true);
         }
      }

   }

   @agu
   public void a(WB var1) {
      this.c(this.A.a() + ".0% " + this.x.a() + ".0%");
   }

   @agu
   public void a(uO var1) {
      this.z = 0;
   }

   public void n() {
      this.c(this.A.a() + ".0% " + this.x.a() + ".0%");
   }

   public void c() {
      this.z = 0;
   }
}
