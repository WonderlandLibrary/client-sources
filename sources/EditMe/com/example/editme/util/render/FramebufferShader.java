package com.example.editme.util.render;

import java.awt.Color;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.Shader;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public abstract class FramebufferShader extends Shader {
   protected float radius = 2.0F;
   private Minecraft mc = Minecraft.func_71410_x();
   protected float green;
   protected float alpha = 1.0F;
   private static Framebuffer framebuffer;
   protected float blue;
   private boolean entityShadows;
   protected float red;
   protected float quality = 1.0F;

   public void drawFramebuffer(Framebuffer var1) {
      ScaledResolution var2 = new ScaledResolution(this.mc);
      GL11.glBindTexture(3553, var1.field_147617_g);
      GL11.glBegin(7);
      GL11.glTexCoord2d(0.0D, 1.0D);
      GL11.glVertex2d(0.0D, 0.0D);
      GL11.glTexCoord2d(0.0D, 0.0D);
      GL11.glVertex2d(0.0D, (double)var2.func_78328_b());
      GL11.glTexCoord2d(1.0D, 0.0D);
      GL11.glVertex2d((double)var2.func_78326_a(), (double)var2.func_78328_b());
      GL11.glTexCoord2d(1.0D, 1.0D);
      GL11.glVertex2d((double)var2.func_78326_a(), 0.0D);
      GL11.glEnd();
      GL20.glUseProgram(0);
   }

   public void stopDraw(Color var1, float var2, float var3) {
      this.mc.field_71474_y.field_181151_V = this.entityShadows;
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      this.mc.func_147110_a().func_147610_a(true);
      this.red = (float)var1.getRed() / 255.0F;
      this.green = (float)var1.getGreen() / 255.0F;
      this.blue = (float)var1.getBlue() / 255.0F;
      this.alpha = (float)var1.getAlpha() / 255.0F;
      this.radius = var2;
      this.quality = var3;
      this.mc.field_71460_t.func_175072_h();
      RenderHelper.func_74518_a();
      this.func_148042_a(Minecraft.func_71410_x().func_184121_ak());
      this.mc.field_71460_t.func_78478_c();
      this.drawFramebuffer(framebuffer);
      this.mc.field_71460_t.func_175072_h();
      GlStateManager.func_179121_F();
      GlStateManager.func_179099_b();
   }

   public Framebuffer setupFrameBuffer(Framebuffer var1) {
      if (var1 != null) {
         var1.func_147608_a();
      }

      var1 = new Framebuffer(this.mc.field_71443_c, this.mc.field_71440_d, true);
      return var1;
   }

   public void startDraw(float var1) {
      GlStateManager.func_179141_d();
      GlStateManager.func_179094_E();
      GlStateManager.func_179123_a();
      framebuffer = this.setupFrameBuffer(framebuffer);
      framebuffer.func_147614_f();
      framebuffer.func_147610_a(true);
      this.entityShadows = this.mc.field_71474_y.field_181151_V;
      this.mc.field_71474_y.field_181151_V = false;
      this.mc.field_71460_t.func_78479_a(var1, 0);
   }

   public FramebufferShader(String var1) throws IOException {
      super(Minecraft.func_71410_x().func_110442_L(), var1, framebuffer, framebuffer);
   }
}
