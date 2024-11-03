package net.augustus.utils.shader;

import net.augustus.utils.interfaces.MC;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class ShaderUtil implements MC {
   private ShaderLoader backGroundShader;
   private ShaderLoader blurShader;
   private long initTime;
   private long initBlurTime;
   private String name;
   private boolean selected;
   private ScaledResolution sr;

   public void createBackgroundShader(String fragmentSource, boolean optimize) {
      if (this.backGroundShader == null || !optimize) {
         this.backGroundShader = new ShaderLoader("shaders/shader.vert", fragmentSource);
         this.initTime = System.currentTimeMillis();
      }
   }

   public void createBlurShader() {
      if (this.blurShader == null) {
         this.blurShader = new ShaderLoader("shaders/shader.vert", "myshaders/blur.frag");
         this.initBlurTime = System.currentTimeMillis();
      }
   }

   public void useBlurSizedShader(int x1, int y1, int x2, int y2, float delay) {
      this.sr = new ScaledResolution(mc);
      this.blurShader
         .startShader(
            this.sr.getScaledWidth(),
            this.sr.getScaledHeight(),
            (float)Mouse.getX() * (float)this.sr.getScaledWidth() / (float)mc.displayWidth,
            (float)((double)((float)this.sr.getScaledHeight()) - (double)(Mouse.getY() * this.sr.getScaledHeight()) / (double)mc.displayHeight - 1.0),
            (float)(System.currentTimeMillis() - this.initTime) / delay
         );
      GL11.glBegin(7);
      GL11.glVertex2f((float)x1, (float)y1);
      GL11.glVertex2f((float)x1, (float)y2);
      GL11.glVertex2f((float)x2, (float)y2);
      GL11.glVertex2f((float)x2, (float)y1);
      GL11.glEnd();
      this.blurShader.endShader();
   }

   public void createBackgroundShader(String fragmentSource) {
      if (this.backGroundShader == null) {
         this.backGroundShader = new ShaderLoader("shaders/shader.vert", fragmentSource);
         this.initTime = System.currentTimeMillis();
      }
   }

   public void createBackgroundShader(String fragmentSource, String name) {
      if (this.backGroundShader == null) {
         this.backGroundShader = new ShaderLoader("shaders/shader.vert", fragmentSource);
         this.initTime = System.currentTimeMillis();
      }

      this.name = name;
   }

   public void useBackGroundShader(float delay) {
      this.sr = new ScaledResolution(mc);
      this.backGroundShader
         .startShader(mc.displayWidth, mc.displayHeight, (float)Mouse.getX(), (float)Mouse.getY(), (float)(System.currentTimeMillis() - this.initTime) / delay);
      GlStateManager.disableAlpha();
      GL11.glBegin(7);
      GL11.glVertex2f(0.0F, 0.0F);
      GL11.glVertex2f(0.0F, (float)mc.displayHeight);
      GL11.glVertex2f((float)mc.displayWidth, (float)mc.displayHeight);
      GL11.glVertex2f((float)mc.displayWidth, 0.0F);
      GL11.glEnd();
      GlStateManager.enableAlpha();
      this.backGroundShader.endShader();
   }

   public void useSizedShader(int x1, int y1, int x2, int y2, float delay) {
      this.sr = new ScaledResolution(mc);
      this.backGroundShader
         .startShader(
            mc.displayWidth,
            mc.displayHeight,
            (float)Mouse.getX() * (float)this.sr.getScaledWidth() / (float)mc.displayWidth,
            (float)((double)((float)this.sr.getScaledHeight()) - (double)(Mouse.getY() * this.sr.getScaledHeight()) / (double)mc.displayHeight - 1.0),
            (float)(System.currentTimeMillis() - this.initTime) / delay
         );
      GlStateManager.disableAlpha();
      GL11.glBegin(7);
      GL11.glVertex2f((float)x1, (float)y1);
      GL11.glVertex2f((float)x1, (float)y2);
      GL11.glVertex2f((float)x2, (float)y2);
      GL11.glVertex2f((float)x2, (float)y1);
      GL11.glEnd();
      GlStateManager.enableAlpha();
      this.backGroundShader.endShader();
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public boolean isSelected() {
      return this.selected;
   }

   public void setSelected(boolean selected) {
      this.selected = selected;
   }
}
