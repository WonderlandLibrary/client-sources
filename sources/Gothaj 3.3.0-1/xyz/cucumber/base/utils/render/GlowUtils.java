package xyz.cucumber.base.utils.render;

import java.awt.Color;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL13;
import xyz.cucumber.base.module.settings.ColorSettings;
import xyz.cucumber.base.utils.render.shaders.Kernel;
import xyz.cucumber.base.utils.render.shaders.Shaders;

public class GlowUtils {
   private Minecraft mc = Minecraft.getMinecraft();
   private int programId = Shaders.bloomESP.getProgramID();
   private Framebuffer framebuffer = new Framebuffer(this.mc.displayWidth, this.mc.displayHeight, true);
   public Framebuffer glowFrameBuffer = new Framebuffer(this.mc.displayWidth, this.mc.displayHeight, true);

   public void pre() {
      this.reset();
      this.framebuffer.bindFramebuffer(true);
   }

   public void after() {
      this.framebuffer.unbindFramebuffer();
      this.mc.getFramebuffer().bindFramebuffer(true);
   }

   public void post(float saturation, float radius, float compression, long time, int mode, ColorSettings color) {
      ScaledResolution sr = new ScaledResolution(this.mc);
      this.glowFrameBuffer.framebufferClear();
      this.glowFrameBuffer.bindFramebuffer(true);
      Shaders.bloomESP.startProgram();
      FloatBuffer kernel = BufferUtils.createFloatBuffer(256);

      for (int i = 0; (float)i <= radius; i++) {
         kernel.put(Kernel.calculateGaussianValue((float)i, radius / 2.0F));
      }

      ((Buffer)kernel).rewind();
      Shader.uniform1f(this.programId, "u_radius", radius);
      Shader.uniform1f(this.programId, "sensitivity", saturation);
      Shader.uniformFB(this.programId, "u_kernel", kernel);
      Shader.uniform1i(this.programId, "u_texture1", 0);
      Shader.uniform1i(this.programId, "colorMode", color.getModes().indexOf(color.getMode()));
      Shader.uniform1i(this.programId, "type", mode);
      Shader.uniform1i(this.programId, "u_texture2", 20);
      Shader.uniform2f(this.programId, "u_texel_size", 1.0F / (float)sr.getScaledWidth(), 1.0F / (float)sr.getScaledHeight());
      Shader.uniform2f(this.programId, "u_direction", 1.0F, 0.0F);
      Shader.uniform1f(this.programId, "u_time", (float)(System.currentTimeMillis() - time) / 1000.0F);
      Color c = new Color(color.getMainColor());
      Color c1 = new Color(color.getSecondaryColor());
      Shader.uniform3f(this.programId, "u_color", (float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F);
      Shader.uniform3f(this.programId, "u_color2", (float)c1.getRed() / 255.0F, (float)c1.getGreen() / 255.0F, (float)c1.getBlue() / 255.0F);
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(1, 770);
      GlStateManager.alphaFunc(516, 0.0F);
      this.framebuffer.bindFramebufferTexture();
      Shaders.bloomESP.renderShader(0.0, 0.0, (double)sr.getScaledWidth(), (double)sr.getScaledHeight());
      this.mc.getFramebuffer().bindFramebuffer(true);
      Shader.uniform2f(this.programId, "u_direction", 0.0F, 1.0F);
      GlStateManager.blendFunc(770, 771);
      this.glowFrameBuffer.bindFramebufferTexture();
      GL13.glActiveTexture(34004);
      this.framebuffer.bindFramebufferTexture();
      GL13.glActiveTexture(33984);
      Shaders.bloomESP.renderShader(0.0, 0.0, (double)sr.getScaledWidth(), (double)sr.getScaledHeight());
      GlStateManager.disableBlend();
      Shaders.bloomESP.stopProgram();
   }

   public void reset() {
      if (this.framebuffer == null
         || this.framebuffer.framebufferWidth != Minecraft.getMinecraft().displayWidth
         || this.framebuffer.framebufferHeight != Minecraft.getMinecraft().displayHeight) {
         if (this.framebuffer != null) {
            this.framebuffer.deleteFramebuffer();
         }

         this.framebuffer = new Framebuffer(this.mc.displayWidth, this.mc.displayHeight, true);
      }

      if (this.glowFrameBuffer == null
         || this.glowFrameBuffer.framebufferWidth != Minecraft.getMinecraft().displayWidth
         || this.glowFrameBuffer.framebufferHeight != Minecraft.getMinecraft().displayHeight) {
         if (this.framebuffer != null) {
            this.glowFrameBuffer.deleteFramebuffer();
         }

         this.glowFrameBuffer = new Framebuffer(this.mc.displayWidth, this.mc.displayHeight, true);
      }

      this.framebuffer.framebufferClear();
      this.glowFrameBuffer.framebufferClear();
   }
}
