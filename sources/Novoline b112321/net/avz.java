package net;

import net.J3;
import net.Ju;
import net.UW;
import net.VN;
import net.a2V;
import net.a6d;
import net.aEE;
import net.aEu;
import net.aHn;
import net.aSt;
import net.adZ;
import net.ae9;
import net.agu;
import net.as0;
import net.ava;
import net.axu;
import net.dI;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

public final class avz extends as0 {
   @VN("dynamic")
   private final aEu A = axu.b();
   @VN("gap")
   private final aEE z = (aEE)((aEE)axu.a(Double.valueOf(0.95D)).d(Double.valueOf(0.25D))).c(Double.valueOf(15.0D));
   @VN("width")
   private final aEE y = (aEE)((aEE)axu.a(Double.valueOf(0.25D)).d(Double.valueOf(0.0D))).c(Double.valueOf(10.0D));
   @VN("size")
   private final aEE x = (aEE)((aEE)axu.a(Double.valueOf(4.0D)).d(Double.valueOf(0.25D))).c(Double.valueOf(15.0D));

   public avz(UW var1) {
      super(var1, "Crosshair", a2V.VISUALS, "Crosshair like in cs:go!");
      ae9.a(new adZ("Dynamic", "Dynamic", a6d.CHECKBOX, this, this.A));
      ae9.a(new adZ("Gap", "Gap", a6d.SLIDER, this, this.z, 0.05D));
      ae9.a(new adZ("Width", "Width", a6d.SLIDER, this, this.y, 0.01D));
      ae9.a(new adZ("CH_size", "Size", a6d.SLIDER, this, this.x, 0.05D));
   }

   @agu
   public void a(aSt var1) {
      int var2 = ava.h();
      if(!(this.w.currentScreen instanceof aHn)) {
         GL11.glPushMatrix();
         dI.a(this.w);
         double var3 = ((Double)this.z.a()).doubleValue();
         double var5 = ((Double)this.y.a()).doubleValue();
         double var7 = ((Double)this.x.a()).doubleValue();
         boolean var9 = ((Boolean)this.A.a()).booleanValue();
         int var10 = Ju.a(0, 0, 0, 255);
         int var11 = ((ava)this.b(ava.class)).q();
         ScaledResolution var12 = var1.a();
         int var13 = var12.a(this.w);
         int var14 = var12.b(this.w);
         float var15 = (float)var13 / 2.0F;
         float var16 = (float)var14 / 2.0F;
         if(var9) {
            if(this.w.thePlayer.p()) {
               J3.a((double)var15 - var5, (double)var16 - var3 - var7 - 2.0D, (double)(var15 + 1.0F) + var5, (double)var16 - var3 - 2.0D, 0.5F, var10, var11);
               J3.a((double)var15 - var5, (double)var16 + var3 + 1.0D + (double)(this.w.thePlayer.p()?2:0) - 0.15000000596046448D, (double)(var15 + 1.0F) + var5, (double)(var16 + 1.0F) + var3 + var7 + (double)(this.w.thePlayer.p()?2:0) - 0.15000000596046448D, 0.5F, var10, var11);
               J3.a((double)var15 - var3 - var7 - (double)(this.w.thePlayer.p()?2:0) + 0.5D, (double)var16 - var5, (double)var15 - var3 - (double)(this.w.thePlayer.p()?2:0) + 0.5D, (double)(var16 + 1.0F) + var5, 0.5F, var10, var11);
               J3.a((double)(var15 + 0.5F) + var3 + (double)(this.w.thePlayer.p()?2:0), (double)var16 - var5, (double)var15 + var7 + var3 + 0.5D + (double)(this.w.thePlayer.p()?2:0), (double)(var16 + 1.0F) + var5, 0.5F, var10, var11);
            }

            J3.a((double)var15 - var5, (double)var16 - var3 - var7 - 0.0D, (double)(var15 + 1.0F) + var5, (double)var16 - var3 - 0.0D, 0.5F, var10, var11);
            J3.a((double)var15 - var5, (double)var16 + var3 + 1.0D + (double)(this.w.thePlayer.p()?2:0) - 0.15000000596046448D, (double)(var15 + 1.0F) + var5, (double)(var16 + 1.0F) + var3 + var7 + (double)(this.w.thePlayer.p()?2:0) - 0.15000000596046448D, 0.5F, var10, var11);
            J3.a((double)var15 - var3 - var7 - (double)(this.w.thePlayer.p()?2:0) + 0.5D, (double)var16 - var5, (double)var15 - var3 - (double)(this.w.thePlayer.p()?2:0) + 0.5D, (double)(var16 + 1.0F) + var5, 0.5F, var10, var11);
            J3.a((double)(var15 + 0.5F) + var3 + (double)(this.w.thePlayer.p()?2:0), (double)var16 - var5, (double)var15 + var7 + var3 + 0.5D + (double)(this.w.thePlayer.p()?2:0), (double)(var16 + 1.0F) + var5, 0.5F, var10, var11);
         }

         if(this.w.thePlayer.p()) {
            J3.a((double)var15 - var5, (double)var16 - var3 - var7 - 0.0D, (double)(var15 + 1.0F) + var5, (double)var16 - var3 - 0.0D, 0.5F, var10, var11);
            J3.a((double)var15 - var5, (double)var16 + var3 + 1.0D + 0.0D - 0.15000000596046448D, (double)(var15 + 1.0F) + var5, (double)(var16 + 1.0F) + var3 + var7 + 0.0D - 0.15000000596046448D, 0.5F, var10, var11);
            J3.a((double)var15 - var3 - var7 - 0.0D + 0.5D, (double)var16 - var5, (double)var15 - var3 - 0.0D + 0.5D, (double)(var16 + 1.0F) + var5, 0.5F, var10, var11);
            J3.a((double)(var15 + 0.5F) + var3 + 0.0D, (double)var16 - var5, (double)var15 + var7 + var3 + 0.5D + 0.0D, (double)(var16 + 1.0F) + var5, 0.5F, var10, var11);
         }

         J3.a((double)var15 - var5, (double)var16 - var3 - var7 - 0.0D, (double)(var15 + 1.0F) + var5, (double)var16 - var3 - 0.0D, 0.5F, var10, var11);
         J3.a((double)var15 - var5, (double)var16 + var3 + 1.0D + 0.0D - 0.15000000596046448D, (double)(var15 + 1.0F) + var5, (double)(var16 + 1.0F) + var3 + var7 + 0.0D - 0.15000000596046448D, 0.5F, var10, var11);
         J3.a((double)var15 - var3 - var7 - 0.0D + 0.5D, (double)var16 - var5, (double)var15 - var3 - 0.0D + 0.5D, (double)(var16 + 1.0F) + var5, 0.5F, var10, var11);
         J3.a((double)(var15 + 0.5F) + var3 + 0.0D, (double)var16 - var5, (double)var15 + var7 + var3 + 0.5D + 0.0D, (double)(var16 + 1.0F) + var5, 0.5F, var10, var11);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glPopMatrix();
      }
   }
}
