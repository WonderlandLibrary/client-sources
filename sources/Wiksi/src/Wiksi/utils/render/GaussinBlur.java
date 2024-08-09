// Decompiled with: CFR 0.152
// Class Version: 17
package src.Wiksi.utils.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import src.Wiksi.utils.client.IMinecraft;
import src.Wiksi.utils.shader.ShaderUtil;
import src.Wiksi.utils.render.StencilUtil;
import java.nio.FloatBuffer;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.BufferUtils;

public class GaussinBlur {
    private static final ShaderUtil gaussianBlur = new ShaderUtil("blur");
    private static Framebuffer framebuffer1 = new Framebuffer(1, 1, false, false);
    private static Framebuffer framebuffer2 = new Framebuffer(1, 1, false, false);
    private static final FloatBuffer weightBuffer = BufferUtils.createFloatBuffer(256);

    private static void setupUniforms(float dir1, float dir2, float radius) {
        gaussianBlur.setUniform("textureIn", 0);
        gaussianBlur.setUniformf("texelSize", 1.0f / (float)IMinecraft.mc.getMainWindow().getWidth(), 1.0f / (float)IMinecraft.mc.getMainWindow().getHeight());
        gaussianBlur.setUniformf("direction", dir1, dir2);
        gaussianBlur.setUniformf("radius", radius);
        RenderSystem.glUniform1(gaussianBlur.getUniform("weights"), weightBuffer);
    }

    public static void startBlur() {
        StencilUtil.initStencilToWrite();
    }

    public static void endBlur(float radius, float compression) {
        StencilUtil.readStencilBuffer(1);
        GaussinBlur.performBlur(radius, compression);
        StencilUtil.uninitStencilBuffer();
    }

    public static void blur(float radius, float compression) {
        GaussinBlur.performBlur(radius, compression);
    }

    private static void performBlur(float radius, float compression) {
        framebuffer1 = ShaderUtil.createFrameBuffer(framebuffer1);
        framebuffer2 = ShaderUtil.createFrameBuffer(framebuffer2);
        framebuffer1.framebufferClear(false);
        framebuffer1.bindFramebuffer(false);
        gaussianBlur.attach();
        GaussinBlur.setupUniforms(compression, 0.0f, radius);
        GlStateManager.bindTexture(IMinecraft.mc.getFramebuffer().framebufferTexture);
        ShaderUtil.drawQuads();
        framebuffer1.unbindFramebuffer();
        framebuffer2.framebufferClear(false);
        framebuffer2.bindFramebuffer(false);
        GaussinBlur.setupUniforms(0.0f, compression, radius);
        GlStateManager.bindTexture(GaussinBlur.framebuffer1.framebufferTexture);
        ShaderUtil.drawQuads();
        framebuffer2.unbindFramebuffer();
        gaussianBlur.detach();
        IMinecraft.mc.getFramebuffer().bindFramebuffer(false);
        GlStateManager.bindTexture(GaussinBlur.framebuffer2.framebufferTexture);
        ShaderUtil.drawQuads();
        GlStateManager.color4f(-1.0f, -1.0f, 1.0f, -1.0f);
        GlStateManager.bindTexture(0);
    }

    public static float calculateGaussianValue(float x, float sigma) {
        double output = 1.0 / Math.sqrt(Math.PI * 2 * (double)(sigma * sigma));
        return (float)(output * Math.exp((double)(-(x * x)) / (2.0 * (double)(sigma * sigma))));
    }

    static {
        for (int i = 0; i <= 128; ++i) {
            weightBuffer.put(GaussinBlur.calculateGaussianValue(i, 64.0f));
        }
        weightBuffer.rewind();
    }
}

