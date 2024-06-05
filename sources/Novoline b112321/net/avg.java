package net;

import cc.novoline.events.events.EventState;
import java.util.function.Supplier;
import net.UW;
import net.VN;
import net.a2V;
import net.a6d;
import net.aE8;
import net.aEs;
import net.aG1;
import net.aL_;
import net.adZ;
import net.ae9;
import net.agu;
import net.apo;
import net.as0;
import net.as_;
import net.axu;
import net.minecraft.network.play.client.C03PacketPlayer$C04PacketPlayerPosition;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;

public final class avg extends as0 {
   @VN("mode")
   private aEs y = axu.a("Normal").a(new String[]{"VClip", "Normal"});
   @VN("hieght")
   private aE8 x = (aE8)((aE8)axu.b(Integer.valueOf(-3)).d(Integer.valueOf(-10))).c(Integer.valueOf(10));

   public avg(UW var1) {
      super(var1, "Phase", a2V.EXPLOITS, "Allows you to walks through blocks");
      ae9.a(new adZ("PHASE_MODE", "Mode", a6d.COMBOBOX, this, this.y));
      ae9.a(new adZ("PHASE_CLIP_HEIGHT", "VClip Height", a6d.SLIDER, this, this.x, 1.0D, this::lambda$new$0));
   }

   public void n() {
      int var1 = as_.b();
      if(this.y.a("VClip")) {
         this.w.thePlayer.setPosition(this.w.thePlayer.posX, this.w.thePlayer.posY + (double)((Integer)this.x.a()).intValue(), this.w.thePlayer.posZ);
         this.e();
      }

   }

   public void c() {
      this.w.thePlayer.stepHeight = 0.625F;
   }

   @agu
   public void a(apo var1) {
      int var2 = as_.a();
      if(this.y.a("Normal") && this.w.thePlayer.b()) {
         var1.a((AxisAlignedBB)null);
      }

   }

   @agu
   public void a(aL_ var1) {
      int var2 = as_.b();
      if(this.y.a("Normal") && this.w.thePlayer.b()) {
         if(this.w.gameSettings.keyBindJump.isKeyDown()) {
            var1.a(this.w.thePlayer.motionY += 0.09000000357627869D);
         }

         if(this.w.gameSettings.keyBindSneak.isKeyDown()) {
            var1.a(this.w.thePlayer.motionY -= 0.09000000357627869D);
         }

         var1.a(this.w.thePlayer.motionY = 0.0D);
         var1.c(this.w.thePlayer.as());
      }

   }

   @agu
   public void a(aG1 var1) {
      int var2 = as_.a();
      if(this.y.a("Normal") && var1.h().equals(EventState.PRE)) {
         this.w.thePlayer.stepHeight = 0.015625F;
         float var3 = this.w.thePlayer.ap().e();
         float var4 = this.w.thePlayer.ap().c();
         float var5 = this.w.thePlayer.rotationYaw;
         double var6 = 0.3D;
         double var8 = (double)(-MathHelper.h(Math.toRadians((double)var5)));
         double var10 = (double)MathHelper.d(Math.toRadians((double)var5));
         double var12 = (double)var4 * var6 * var8 + (double)var3 * var6 * var10;
         double var14 = (double)var4 * var6 * var10 - (double)var3 * var6 * var8;
         if(this.w.thePlayer.isCollidedHorizontally && !this.w.thePlayer.isOnLadder() && this.w.thePlayer.onGround) {
            double var16 = this.w.thePlayer.posX;
            double var18 = this.w.thePlayer.posY;
            double var20 = this.w.thePlayer.posZ;
            this.b(new C03PacketPlayer$C04PacketPlayerPosition(var16 + var12, var18, var20 + var14, true));
            this.b(new C03PacketPlayer$C04PacketPlayerPosition(var16, var18 + 3.0D, var20, true));
            this.w.thePlayer.setPosition(var16 + var12, var18, var20 + var14);
         }
      }

   }

   private Boolean lambda$new$0() {
      return Boolean.valueOf(this.y.a("VClip"));
   }
}
