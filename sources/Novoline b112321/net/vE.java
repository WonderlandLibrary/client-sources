package net;

import cc.novoline.modules.visual.HUD;
import cc.novoline.modules.visual.JumpCircle;
import cc.novoline.utils.RenderUtils;
import java.awt.Color;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

class vE {
   private float a;
   private float d;
   private Vec3 c;
   final JumpCircle b;

   public vE(JumpCircle var1) {
      this.b = var1;
      this.c = new Vec3(JumpCircle.k(var1).player.posX, JumpCircle.g(var1).player.posY, JumpCircle.h(var1).player.posZ);
      this.d = 0.0F;
      this.a = 255.0F;
   }

   public vE(JumpCircle var1, double var2, double var4, double var6) {
      this.b = var1;
      this.c = new Vec3(var2, var4, var6);
      this.d = 0.0F;
      this.a = 255.0F;
   }

   private void a() {
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      HUD.h();
      GL11.glBlendFunc(770, 771);
      GL11.glShadeModel(7425);
      GL11.glDisable(3553);
      GlStateManager.disableDepth();
      GL11.glEnable(2848);
      GL11.glDisable(2896);
      GL11.glDepthMask(false);
      GL11.glHint(3154, 4354);
      GL11.glLineWidth(5.0F);
      GL11.glBegin(3);
      Color var2 = JumpCircle.c(this.b).getAwtColor();
      RenderUtils.setColor(new Color(var2.getRed(), var2.getGreen(), var2.getBlue(), (int)this.a));
      int var3 = 0;
      if(var3 < 361) {
         double var4 = this.c.getX() - JumpCircle.a(this.b).getRenderManager().renderPosX;
         double var6 = this.c.getY() - JumpCircle.b(this.b).getRenderManager().renderPosY;
         double var8 = this.c.getZ() - JumpCircle.e(this.b).getRenderManager().renderPosZ;
         GL11.glVertex3d(var4 + -Math.sin(Math.toRadians((double)var3)) * (double)this.d, var6, var8 + Math.cos(Math.toRadians((double)var3)) * (double)this.d);
         ++var3;
      }

      GL11.glEnd();
      GL11.glBegin(5);
      double var14 = 0.0D;
      if(var14 < 361.0D) {
         double var5 = this.c.getX() - JumpCircle.f(this.b).getRenderManager().renderPosX;
         double var7 = this.c.getY() - JumpCircle.i(this.b).getRenderManager().renderPosY;
         double var9 = this.c.getZ() - JumpCircle.j(this.b).getRenderManager().renderPosZ;
         double var11 = 0.0D;
         if(var11 < 3.0D) {
            GL11.glVertex3d(var5 + -Math.sin(Math.toRadians(var14)) * (double)this.d, var7, var9 + Math.cos(Math.toRadians(var14)) * (double)this.d);
            GL11.glVertex3d(var5 + -Math.sin(Math.toRadians(var14)) * ((double)this.d - var11 / 10.0D), var7, var9 + Math.cos(Math.toRadians(var14)) * ((double)this.d - var11 / 10.0D));
            ++var11;
         }

         var14 = var14 + 0.5D;
      }

      GL11.glEnd();
      GL11.glDepthMask(true);
      GlStateManager.enableDepth();
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
      var14 = (double)(250 / JumpCircle.d(this.b).getDebugFPS());
      this.a = MathHelper.clamp_float(this.a = (float)((double)this.a - 1.0D * var14), 0.0F, 255.0F);
      this.d = MathHelper.clamp_float(this.d = (float)((double)this.d + 0.005D * var14), 0.0F, 1.5F);
   }

   static void a(vE var0) {
      var0.a();
   }

   static float b(vE var0) {
      return var0.a;
   }
}
