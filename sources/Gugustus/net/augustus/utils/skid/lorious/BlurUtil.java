package net.augustus.utils.skid.lorious;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class BlurUtil {
    public static Minecraft mc = Minecraft.getMinecraft();
    private static int lastScale;
    private static int lastScaleWidth;
    private static int lastScaleHeight;
    private static Framebuffer buffer;
    private static final ResourceLocation shader;
    private static ShaderGroup blurShader;

    public static void initFboAndShader() {
        try {
            blurShader = new ShaderGroup(mc.getTextureManager(), mc.getResourceManager(), mc.getFramebuffer(), shader);
            blurShader.createBindFramebuffers(BlurUtil.mc.displayWidth, BlurUtil.mc.displayHeight);
            buffer = BlurUtil.blurShader.mainFramebuffer;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void blur(float x, float y, float x2, float y2, ScaledResolution sc) {
        int factor = sc.getScaleFactor();
        int factor2 = sc.getScaledWidth();
        int factor3 = sc.getScaledHeight();
        if (lastScale != factor || lastScaleWidth != factor2 || lastScaleHeight != factor3 || buffer == null || blurShader == null) {
            BlurUtil.initFboAndShader();
        }
        lastScale = factor;
        lastScaleWidth = factor2;
        lastScaleHeight = factor3;
        GL11.glEnable(3089);
        BlurUtil.crop(x, y, x2, y2);
        BlurUtil.buffer.framebufferHeight = BlurUtil.mc.displayHeight;
        BlurUtil.buffer.framebufferWidth = BlurUtil.mc.displayWidth;
        GlStateManager.resetColor();
        blurShader.loadShaderGroup(BlurUtil.mc.timer.renderPartialTicks);
        buffer.bindFramebuffer(true);
        mc.getFramebuffer().bindFramebuffer(true);
        GL11.glDisable(3089);
    }

    public static void blur(float x, float y, float x2, float y2) {
        GlStateManager.disableAlpha();
        BlurUtil.blur(x, y, x2, y2, new ScaledResolution(mc));
        GlStateManager.enableAlpha();
    }

    public static void crop(float x, float y, float x2, float y2) {
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        int factor = scaledResolution.getScaleFactor();
        GL11.glScissor((int)(x * (float)factor), (int)(((float)scaledResolution.getScaledHeight() - y2) * (float)factor), (int)((x2 - x) * (float)factor), (int)((y2 - y) * (float)factor));
    }

    static {
        shader = new ResourceLocation("client/shaders/blur.json");
    }
}
