package xyz.cucumber.base.utils.render;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import xyz.cucumber.base.utils.render.shaders.Kernel;
import xyz.cucumber.base.utils.render.shaders.Shaders;

public class BlurUtils {
   private static Minecraft mc = Minecraft.getMinecraft();
   public static Framebuffer framebuffer = new Framebuffer(1, 1, false);

   public static void setupUniforms(float dir1, float dir2, float radius) {
      ScaledResolution sr = new ScaledResolution(mc);
      Shader.uniform1i(Shaders.blur.getProgramID(), "textureIn", 0);
      Shader.uniform2f(Shaders.blur.getProgramID(), "texelSize", 1.0F / (float)sr.getScaledWidth_double(), 1.0F / (float)sr.getScaledHeight_double());
      Shader.uniform2f(Shaders.blur.getProgramID(), "direction", dir1, dir2);
      Shader.uniform1f(Shaders.blur.getProgramID(), "radius", radius);
      FloatBuffer weightBuffer = BufferUtils.createFloatBuffer(256);

      for (int i = 0; (float)i <= radius; i++) {
         weightBuffer.put(Kernel.calculateGaussianValue((float)i, radius / 2.0F));
      }

      ((Buffer)weightBuffer).rewind();
      GL20.glUniform1(GL20.glGetUniformLocation(Shaders.blur.getProgramID(), "weights"), weightBuffer);
   }

   public static void renderBlur(float radius) {
      ScaledResolution sr = new ScaledResolution(mc);
      GlStateManager.enableBlend();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      OpenGlHelper.glBlendFunc(770, 771, 1, 0);
      framebuffer = createFrameBuffer(framebuffer);
      framebuffer.framebufferClear();
      framebuffer.bindFramebuffer(true);
      Shaders.blur.startProgram();
      setupUniforms(1.0F, 0.0F, radius);
      bindTexture(mc.getFramebuffer().framebufferTexture);
      Shaders.blur.renderShader(0.0, 0.0, sr.getScaledWidth_double(), sr.getScaledHeight_double());
      framebuffer.unbindFramebuffer();
      Shaders.blur.stopProgram();
      mc.getFramebuffer().bindFramebuffer(true);
      Shaders.blur.startProgram();
      setupUniforms(0.0F, 1.0F, radius);
      bindTexture(framebuffer.framebufferTexture);
      Shaders.blur.renderShader(0.0, 0.0, sr.getScaledWidth_double(), sr.getScaledHeight_double());
      Shaders.blur.stopProgram();
      GlStateManager.resetColor();
      GlStateManager.bindTexture(0);
   }

   public static Framebuffer createFrameBuffer(Framebuffer framebuffer) {
      if (framebuffer != null && framebuffer.framebufferWidth == mc.displayWidth && framebuffer.framebufferHeight == mc.displayHeight) {
         return framebuffer;
      } else {
         if (framebuffer != null) {
            framebuffer.deleteFramebuffer();
         }

         return new Framebuffer(mc.displayWidth, mc.displayHeight, true);
      }
   }

   public static void bindTexture(int texture) {
      GL11.glBindTexture(3553, texture);
   }
}
