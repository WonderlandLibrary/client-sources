/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package drunkclient.beta.UTILS.blur;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class BlurUtil {
    private static int lastScale;
    private static int lastScaleWidth;
    private static int lastScaleHeight;
    private static Framebuffer buffer;
    private static ShaderGroup blurShader;
    private static final ResourceLocation shader;

    public static void initFboAndShader() {
        Minecraft mc = Minecraft.getMinecraft();
        try {
            blurShader = new ShaderGroup(mc.getTextureManager(), mc.getResourceManager(), mc.getFramebuffer(), shader);
            blurShader.createBindFramebuffers(mc.displayWidth, mc.displayHeight);
            buffer = BlurUtil.blurShader.mainFramebuffer;
            return;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void crop(float x, float y, float x2, float y2) {
        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        int factor = scaledResolution.getScaleFactor();
        GL11.glScissor((int)((int)(x * (float)factor)), (int)((int)(((float)scaledResolution.getScaledHeight() - y2) * (float)factor)), (int)((int)((x2 - x) * (float)factor)), (int)((int)((y2 - y) * (float)factor)));
    }

    public static void blur(float x, float y, float x2, float y2, ScaledResolution sr) {
        Minecraft mc = Minecraft.getMinecraft();
        int factor = sr.getScaleFactor();
        int factor2 = sr.getScaledWidth();
        int factor3 = sr.getScaledHeight();
        if (lastScale != factor || lastScaleWidth != factor2 || lastScaleHeight != factor3 || buffer == null || blurShader == null) {
            BlurUtil.initFboAndShader();
        }
        lastScale = factor;
        lastScaleWidth = factor2;
        lastScaleHeight = factor3;
        GL11.glEnable((int)3089);
        BlurUtil.crop(x, y, x2, y2);
        BlurUtil.buffer.framebufferHeight = mc.displayHeight;
        BlurUtil.buffer.framebufferWidth = mc.displayWidth;
        GlStateManager.resetColor();
        blurShader.loadShaderGroup(mc.timer.renderPartialTicks);
        buffer.bindFramebuffer(true);
        mc.getFramebuffer().bindFramebuffer(true);
        GL11.glDisable((int)3089);
    }

    public static void blur(float x, float y, float x2, float y2) {
        Minecraft mc = Minecraft.getMinecraft();
        GlStateManager.disableAlpha();
        BlurUtil.blur(x, y, x2, y2, new ScaledResolution(mc));
        GlStateManager.enableAlpha();
    }

    public static void blur2(float x, float y, float x2, float y2, float h, float w) {
        Minecraft mc = Minecraft.getMinecraft();
        GlStateManager.disableAlpha();
        BlurUtil.blur(x, y, x2 + w, y2 + h, new ScaledResolution(mc));
        GlStateManager.enableAlpha();
    }

    static {
        shader = new ResourceLocation("drunkclient/blur/blur.json");
    }
}

