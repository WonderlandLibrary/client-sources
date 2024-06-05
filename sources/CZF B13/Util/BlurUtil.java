package gq.vapu.czfclient.Util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public class BlurUtil {

    private static final Minecraft mc = Minecraft.getMinecraft();
    private static final ResourceLocation shader = new ResourceLocation("shaders/post/blur.json");
    private static ShaderGroup blurShader;
    private static List<Shader> listShaders;
    private static Framebuffer buffer;
    private static int lastScale;
    private static int lastScaleWidth;
    private static int lastScaleHeight;

    static {
        if (blurShader == null) {
            try {
                blurShader = new ShaderGroup(mc.getTextureManager(), mc.getResourceManager(), mc.getFramebuffer(),
                        shader);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            Field field = blurShader.getClass().getDeclaredField("listShaders");
            field.setAccessible(true);
            try {
                listShaders = (List<Shader>) field.get(blurShader);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

    }

    public static void initFboAndShader() {
        try {
            if (blurShader == null)
                blurShader = new ShaderGroup(mc.getTextureManager(), mc.getResourceManager(), mc.getFramebuffer(),
                        shader);
            blurShader.createBindFramebuffers(mc.displayWidth, mc.displayHeight);
            Field field;
            (field = blurShader.getClass().getDeclaredField("mainFramebuffer")).setAccessible(true);
            buffer = (Framebuffer) field.get(blurShader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setShaderConfigs(float intensity, float blurWidth, float blurHeight, float opacity) {
        listShaders.get(0).getShaderManager().getShaderUniform("Radius").set(intensity);
        listShaders.get(1).getShaderManager().getShaderUniform("Radius").set(intensity);
//		listShaders.get(0).getShaderManager().getShaderUniform("Opacity").set(opacity);
//		listShaders.get(1).getShaderManager().getShaderUniform("Opacity").set(opacity);
        listShaders.get(0).getShaderManager().getShaderUniform("BlurDir").set(blurWidth, blurHeight);
        listShaders.get(1).getShaderManager().getShaderUniform("BlurDir").set(blurHeight, blurWidth);
    }

    public static void blurArea(int x, int y, int width, int height, float intensity, float blurWidth,
                                float blurHeight) {
        ScaledResolution scale = new ScaledResolution(mc);
        int factor = scale.getScaleFactor();
        int factor2 = scale.getScaledWidth();
        int factor3 = scale.getScaledHeight();
        if (lastScale != factor || lastScaleWidth != factor2 || lastScaleHeight != factor3 || buffer == null
                || blurShader == null) {
            initFboAndShader();
        }
        lastScale = factor;
        lastScaleWidth = factor2;
        lastScaleHeight = factor3;
        if (OpenGlHelper.isFramebufferEnabled()) {
            buffer.framebufferClear();
            GL11.glScissor((x * factor), (mc.displayHeight - y * factor - height * factor), (width * factor),
                    (height * factor));
            GL11.glEnable(3089);
            setShaderConfigs(intensity, blurWidth, blurHeight, 1.0f);
            buffer.bindFramebuffer(true);
            blurShader.loadShaderGroup(mc.timer.renderPartialTicks);
            mc.getFramebuffer().bindFramebuffer(true);
            GL11.glDisable(3089);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
            GlStateManager.disableBlend();
            GL11.glScalef((float) factor, (float) factor, 0.0f);
        }
    }

    public static void blurArea(int x, int y, int width, int height, float intensity) {
        ScaledResolution scale = new ScaledResolution(mc);
        int factor = scale.getScaleFactor();
        int factor2 = scale.getScaledWidth();
        int factor3 = scale.getScaledHeight();
        if (lastScale != factor || lastScaleWidth != factor2 || lastScaleHeight != factor3 || buffer == null
                || blurShader == null) {
            initFboAndShader();
        }
        lastScale = factor;
        lastScaleWidth = factor2;
        lastScaleHeight = factor3;
        buffer.framebufferClear();
        GL11.glScissor((x * factor), (mc.displayHeight - y * factor - height * factor), (width * factor),
                (height * factor));
        GL11.glEnable(3089);
        setShaderConfigs(intensity, 1.0f, 0.0f, 1.0f);
        buffer.bindFramebuffer(true);
        blurShader.loadShaderGroup(mc.timer.renderPartialTicks);
        mc.getFramebuffer().bindFramebuffer(true);
        GL11.glDisable(3089);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.disableBlend();
        GL11.glScalef((float) factor, (float) factor, 0.0f);
        RenderHelper.enableGUIStandardItemLighting();
    }

    public static void blurAreaBoarder(float x, float f, float width, float height, float intensity, float blurWidth,
                                       float blurHeight) {
        ScaledResolution scale = new ScaledResolution(mc);
        int factor = scale.getScaleFactor();
        int factor2 = scale.getScaledWidth();
        int factor3 = scale.getScaledHeight();
        if (lastScale != factor || lastScaleWidth != factor2 || lastScaleHeight != factor3 || buffer == null
                || blurShader == null) {
            initFboAndShader();
        }
        lastScale = factor;
        lastScaleWidth = factor2;
        lastScaleHeight = factor3;
        GL11.glScissor((int) ((x * (float) factor)),
                (int) (((float) mc.displayHeight - f * (float) factor - height * (float) factor) + 1),
                (int) ((width * (float) factor)), (int) ((height * (float) factor)));
        GL11.glEnable(3089);
        setShaderConfigs(intensity, blurWidth, blurHeight, 1.0f);
        buffer.bindFramebuffer(true);
        blurShader.loadShaderGroup(mc.timer.renderPartialTicks);
        mc.getFramebuffer().bindFramebuffer(true);
        GL11.glDisable(3089);
    }

    public static void blurAreaBoarder(float x, float f, float width, float height, float intensity, float opacity,
                                       float blurWidth, float blurHeight) {
        ScaledResolution scale = new ScaledResolution(mc);
        int factor = scale.getScaleFactor();
        int factor2 = scale.getScaledWidth();
        int factor3 = scale.getScaledHeight();
        if (lastScale != factor || lastScaleWidth != factor2 || lastScaleHeight != factor3 || buffer == null
                || blurShader == null) {
            initFboAndShader();
        }
        lastScale = factor;
        lastScaleWidth = factor2;
        lastScaleHeight = factor3;
        GL11.glScissor((int) ((x * (float) factor)),
                (int) (((float) mc.displayHeight - f * (float) factor - height * (float) factor) + 1),
                (int) ((width * (float) factor)), (int) ((height * (float) factor)));
        GL11.glEnable(3089);
        setShaderConfigs(intensity, blurWidth, blurHeight, opacity);
        buffer.bindFramebuffer(true);
        blurShader.loadShaderGroup(mc.timer.renderPartialTicks);
        mc.getFramebuffer().bindFramebuffer(true);
        GL11.glDisable(3089);
    }

    public static void blurShape(float g, float f, float h, float height, float intensity, float blurWidth,
                                 float blurHeight) {
        ScaledResolution scale = new ScaledResolution(mc);
        int factor = scale.getScaleFactor();
        int factor2 = scale.getScaledWidth();
        int factor3 = scale.getScaledHeight();
        if (lastScale != factor || lastScaleWidth != factor2 || lastScaleHeight != factor3 || buffer == null
                || blurShader == null) {
            initFboAndShader();
        }
        lastScale = factor;
        lastScaleWidth = factor2;
        lastScaleHeight = factor3;
        GL11.glScissor((int) ((g * (float) factor)),
                (int) (((float) mc.displayHeight - f * (float) factor - height * (float) factor) + 1),
                (int) ((h * (float) factor)), (int) ((height * (float) factor)));
        GL11.glEnable(3089);
        setShaderConfigs(intensity, blurWidth, blurHeight, 1.0F);
        buffer.bindFramebuffer(true);
        blurShader.loadShaderGroup(mc.timer.renderPartialTicks);
        mc.getFramebuffer().bindFramebuffer(true);
        GL11.glDisable(3089);
    }

    public static void blurAreaBoarder(int x, int y, int width, int height, float intensity) {
        ScaledResolution scale = new ScaledResolution(mc);
        int factor = scale.getScaleFactor();
        int factor2 = scale.getScaledWidth();
        int factor3 = scale.getScaledHeight();
        if (lastScale != factor || lastScaleWidth != factor2 || lastScaleHeight != factor3 || buffer == null
                || blurShader == null) {
            initFboAndShader();
        }
        lastScale = factor;
        lastScaleWidth = factor2;
        lastScaleHeight = factor3;
        GL11.glPushMatrix();
        GL11.glScissor((x * factor), (mc.displayHeight - y * factor - height * factor), (width * factor) - 1, (height * factor));
        //GL11.glScissor(x, y, width, height);
        GL11.glEnable(3089);
        setShaderConfigs(intensity, 1.0F, 0.0F, 1.0F);
        buffer.bindFramebuffer(true);
        blurShader.loadShaderGroup(mc.timer.renderPartialTicks);
        mc.getFramebuffer().bindFramebuffer(true);
        GL11.glDisable(3089);
        GL11.glPopMatrix();
    }

    public static void blurAreaBoarder1(int x, int y, int width, int height, float intensity) {
        ScaledResolution scale = new ScaledResolution(mc);
        int factor = scale.getScaleFactor();
        int factor2 = scale.getScaledWidth();
        int factor3 = scale.getScaledHeight();
        if (lastScale != factor || lastScaleWidth != factor2 || lastScaleHeight != factor3 || buffer == null
                || blurShader == null) {
            initFboAndShader();
        }
        lastScale = factor;
        lastScaleWidth = factor2;
        lastScaleHeight = factor3;
        GL11.glPushMatrix();
        //GL11.glScissor((x * factor), (mc.displayHeight - y * factor - height * factor), (width * factor) - 1, (height * factor));
        GL11.glScissor(x, y, width, height);
        GL11.glEnable(3089);
        setShaderConfigs(intensity, 1.0F, 0.0F, 1.0F);
        buffer.bindFramebuffer(true);
        blurShader.loadShaderGroup(mc.timer.renderPartialTicks);
        mc.getFramebuffer().bindFramebuffer(true);
        GL11.glDisable(3089);
        GL11.glPopMatrix();
    }

    public static void blurAll(float intensity) {
        ScaledResolution scale = new ScaledResolution(mc);
        int factor = scale.getScaleFactor();
        int factor2 = scale.getScaledWidth();
        int factor3 = scale.getScaledHeight();
        if (lastScale != factor || lastScaleWidth != factor2 || lastScaleHeight != factor3 || buffer == null
                || blurShader == null) {
            initFboAndShader();
        }
        lastScale = factor;
        lastScaleWidth = factor2;
        lastScaleHeight = factor3;
        setShaderConfigs(intensity, 0.5f, 0.5f, 1.0f);
        buffer.bindFramebuffer(true);
        blurShader.loadShaderGroup(mc.timer.renderPartialTicks);
        mc.getFramebuffer().bindFramebuffer(true);
    }

    public static void blurAll(float intensity, float opacity) {
        ScaledResolution scale = new ScaledResolution(mc);
        int factor = scale.getScaleFactor();
        int factor2 = scale.getScaledWidth();
        int factor3 = scale.getScaledHeight();
        if (lastScale != factor || lastScaleWidth != factor2 || lastScaleHeight != factor3 || buffer == null
                || blurShader == null) {
            initFboAndShader();
        }
        lastScale = factor;
        lastScaleWidth = factor2;
        lastScaleHeight = factor3;
        setShaderConfigs(intensity, 0.0f, 1.0f, opacity);
        buffer.bindFramebuffer(true);
        blurShader.loadShaderGroup(mc.timer.renderPartialTicks);
        mc.getFramebuffer().bindFramebuffer(true);
    }

}
