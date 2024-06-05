package net;

import net.BT;
import net.UW;
import net.VD;
import net.VN;
import net.WB;
import net.a2V;
import net.a6d;
import net.aEl;
import net.adZ;
import net.ae9;
import net.agu;
import net.as0;
import net.avI;
import net.avS;
import net.avg;
import net.avr;
import net.avu;
import net.axu;
import net.bgM;
import net.d3;
import net.lS;
import net.minecraft.network.play.client.C03PacketPlayer$C04PacketPlayerPosition;

public class av3 extends as0 {
   private boolean y;
   private d3 B = new d3();
   private double[] x = new double[]{0.41999998688698D, 0.7531999805212D};
   private double[] D = new double[]{0.42D, 0.753D, 1.001D, 1.084D, 1.006D};
   private double[] z = new double[]{0.425D, 0.821D, 0.699D, 0.599D, 1.022D, 1.372D, 1.652D, 1.869D};
   private double[] E = new double[]{0.425D, 0.821D, 0.699D, 0.599D, 1.022D, 1.372D, 1.652D, 1.869D, 2.019D, 1.907D};
   private double[] A;
   @VN("height")
   private aEl C;
   @VN("timer-speed")
   private aEl F;

   public aEl a() {
      return this.C;
   }

   public av3(UW var1) {
      super(var1, "Step", a2V.MOVEMENT, "");
      this.A = bgM.d().e;
      this.C = (aEl)((aEl)axu.a(Float.valueOf(1.5F)).d(Float.valueOf(1.0F))).c(Float.valueOf(2.5F));
      this.F = (aEl)((aEl)axu.a(Float.valueOf(0.5F)).d(Float.valueOf(0.4F))).c(Float.valueOf(1.0F));
      ae9.a(new adZ("STEP_HEIGHT", "Height", a6d.SLIDER, this, this.C, 0.5D));
      ae9.a(new adZ("STEP_TIMER_SPEED", "Timer Speed", a6d.SLIDER, this, this.F, 0.10000000149011612D));
   }

   public void n() {
      this.c(((Float)this.C.a()).toString());
   }

   @agu
   public void a(WB var1) {
      this.c(((Float)this.C.a()).toString());
   }

   public void c() {
      this.w.timer.i = 1.0F;
      this.w.thePlayer.stepHeight = 0.625F;
   }

   @agu
   public void a(BT var1) {
      String var2 = avu.r();
      if(this.a((Class)avg.class) || this.a((Class)avr.class) && this.w.thePlayer.N()) {
         this.w.thePlayer.stepHeight = 0.015625F;
      }

      if(this.w.thePlayer.q() || this.w.thePlayer.N() || this.a((Class)avS.class) || !this.w.thePlayer.onGround) {
         this.w.thePlayer.stepHeight = 0.625F;
      }

      this.w.thePlayer.stepHeight = ((Float)this.C.a()).floatValue();
      if(this.y && !this.a((Class)avI.class)) {
         this.w.timer.i = 1.0F;
         this.y = false;
      }

   }

   @agu
   public void a(VD var1) {
      double var3 = this.w.thePlayer.posY;
      double var5 = this.w.thePlayer.posX;
      avu.r();
      double var7 = this.w.thePlayer.posZ;
      double var9 = this.w.thePlayer.getEntityBoundingBox().minY - var3;
      if(var9 > 0.625D && var9 <= 2.5D) {
         this.y = true;
         if(var9 <= 1.0D) {
            this.w.timer.i = ((Float)this.F.a()).floatValue();
            double var11 = 0.41999998688698D;
            double var13 = 0.7531999805212D;
            if(var9 != 1.0D) {
               var11 *= var9;
               var13 *= var9;
               if(var11 > 0.425D) {
                  var11 = 0.425D;
               }

               if(var13 > 0.78D) {
                  var13 = 0.78D;
               }

               if(var13 < 0.49D) {
                  var13 = 0.49D;
               }
            }

            this.b(new C03PacketPlayer$C04PacketPlayerPosition(var5, var3 + var11, var7, false));
            this.b(new C03PacketPlayer$C04PacketPlayerPosition(var5, var3 + var13, var7, false));
         }

         if(var9 <= 1.5D) {
            this.w.timer.i = ((Float)this.F.a()).floatValue() - 0.075F;
            double[] var16 = lS.c()?this.A:this.D;
            int var12 = var16.length;
            int var21 = 0;
            if(var21 < var12) {
               double var14 = var16[var21];
               this.b(new C03PacketPlayer$C04PacketPlayerPosition(var5, var3 + var14, var7, false));
               ++var21;
            }
         }

         if(var9 <= 2.0D) {
            this.w.timer.i = ((Float)this.F.a()).floatValue() - 0.15F;
            double[] var17 = this.z;
            int var19 = var17.length;
            int var23 = 0;
            if(var23 < var19) {
               double var27 = var17[var23];
               this.b(new C03PacketPlayer$C04PacketPlayerPosition(var5, var3 + var27, var7, false));
               ++var23;
            }
         }

         if(var9 > 2.0D) {
            this.w.timer.i = ((Float)this.F.a()).floatValue() - 0.225F;
            double[] var18 = this.E;
            int var20 = var18.length;
            int var25 = 0;
            if(var25 < var20) {
               double var28 = var18[var25];
               this.b(new C03PacketPlayer$C04PacketPlayerPosition(var5, var3 + var28, var7, false));
               ++var25;
            }
         }
      }

   }
}
