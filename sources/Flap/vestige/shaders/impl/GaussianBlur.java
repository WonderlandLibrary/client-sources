package vestige.shaders.impl;

import java.nio.FloatBuffer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.shader.Framebuffer;
import net.optifine.util.TextureUtils;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import vestige.shaders.ShaderUtil;
import vestige.util.IMinecraft;
import vestige.util.render.ColorUtil2;
import vestige.util.render.RenderUtils2;
import vestige.util.render.StencilUtil;

public class GaussianBlur {
   private static final ShaderUtil gaussianBlur = new ShaderUtil("flap/shader/gaussian.frag");
   private static Framebuffer framebuffer = new Framebuffer(1, 1, false);

   private static void setupUniforms(float dir1, float dir2, float radius) {
      gaussianBlur.setUniformi("textureIn", 0);
      gaussianBlur.setUniformf("texelSize", 1.0F / (float)IMinecraft.mc.displayWidth, 1.0F / (float)IMinecraft.mc.displayHeight);
      gaussianBlur.setUniformf("direction", dir1, dir2);
      gaussianBlur.setUniformf("radius", radius);
      FloatBuffer weightBuffer = BufferUtils.createFloatBuffer(256);

      for(int i = 0; (float)i <= radius; ++i) {
         weightBuffer.put(ColorUtil2.calculateGaussianValue((float)i, radius / 2.0F));
      }

      weightBuffer.rewind();
      GL20.glUniform1(gaussianBlur.getUniform("weights"), weightBuffer);
   }

   public static void startBlur() {
      IMinecraft.mc.getFramebuffer().bindFramebuffer(false);
      StencilUtil.checkSetupFBO(IMinecraft.mc.getFramebuffer());
      GL11.glClear(1024);
      GL11.glEnable(2960);
      GL11.glStencilFunc(519, 1, 1);
      GL11.glStencilOp(7681, 7681, 7681);
      GL11.glColorMask(false, false, false, false);
   }

   public static void endBlur(float radius, float compression) {
      StencilUtil.readStencilBuffer(1);
      framebuffer = RenderUtils2.createFrameBuffer(framebuffer);
      framebuffer.framebufferClear();
      framebuffer.bindFramebuffer(false);
      gaussianBlur.init();
      setupUniforms(compression, 0.0F, radius);
      TextureUtils.bindTexture(IMinecraft.mc.getFramebuffer().framebufferTexture);
      ShaderUtil.drawQuads();
      framebuffer.unbindFramebuffer();
      gaussianBlur.unload();
      IMinecraft.mc.getFramebuffer().bindFramebuffer(false);
      gaussianBlur.init();
      setupUniforms(0.0F, compression, radius);
      TextureUtils.bindTexture(framebuffer.framebufferTexture);
      ShaderUtil.drawQuads();
      gaussianBlur.unload();
      StencilUtil.uninitStencilBuffer();
      ColorUtil2.resetColor();
      GlStateManager.bindTexture(0);
   }
}
