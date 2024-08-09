package ru.FecuritySQ.utils;

import com.jhlabs.image.GaussianFilter;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.opengl.GL11;
import ru.FecuritySQ.shader.ShaderUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;

public class RenderUtil {
    private static final Map<Integer, Integer> shadowCache = new HashMap<>();

    public static ShaderUtil roundedShader = new ShaderUtil("roundedRect");
    private static ShaderUtil roundedGradientShader = new ShaderUtil("roundedRectGradient");
    public static Minecraft mc = Minecraft.getInstance();
    public static MatrixStack resetRotate(MatrixStack stack) {

        stack.rotate(Vector3f.XP.rotationDegrees(0));
        stack.rotate(Vector3f.YP.rotationDegrees(0));

        return stack;
    }
    public static void drawGradientRoundedRect(MatrixStack matrixStack, int x, int y, int width, int height, int radius, int color, int color2) {
        drawCircle(x + radius, y + radius, radius, color);
        drawCircle(width - radius, y + radius, radius, color2);
        drawCircle(x + radius, height - radius, radius, color);
        drawCircle(width - radius, height - radius, radius, color2);
        IngameGui.fill(matrixStack, x, y + radius, x + radius, height - radius, color);
        IngameGui.fill(matrixStack, width - radius, y + radius, width, height - radius, color2);
        drawGradientRect(x + radius, y, width - radius, height, color, color2);
    }
    public static void drawCircle(float x, float y, float radius, int color) {
        float alpha = (color >> 24 & 0xFF) / 255f;
        float red = (color >> 16 & 0xFF) / 255f;
        float green = (color >> 8 & 0xFF) / 255f;
        float blue = (color & 0xFF) / 255f;
        GL11.glColor4f(red, green, blue, alpha);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        GL11.glLineWidth(1);
        GL11.glBegin(9);
        for (int i = 0; i <= 360; i++) {
            GL11.glVertex2d(x + Math.sin(i * Math.PI / 180) * radius, y + Math.cos(i * Math.PI / 180) * radius);
        }
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glColor4f(1, 1, 1, 1);
    }
    public static void drawRound(float x, float y, float width, float height, float radius, int color) {
        GL11.glColor3f(255, 255, 255);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        roundedShader.init();
        ShaderUtil.setupRoundedRectUniforms(x, y, width, height, radius, roundedShader);
        roundedShader.setUniformi("blur", 0);
        roundedShader.setUniformf("color", ColorUtil.getRed(color) / 255f, ColorUtil.getGreen(color) / 255f, ColorUtil.getBlue(color) / 255f, ColorUtil.getAlpha(color) / 255f);
        ShaderUtil.drawQuads(x - 1, y - 1, width + 2, height + 2);
        roundedShader.unload();
        GlStateManager.disableBlend();
        GL11.glColor3f(255, 255, 255);
    }
    public static void drawGradientRects(double left, double top, double right, double bottom, int color1, int color2) {
        float f = (color1 >> 24 & 255) / 255f;
        float f1 = (color1 >> 16 & 255) / 255f;
        float f2 = (color1 >> 8 & 255) / 255f;
        float f3 = (color1 & 255) / 255f;
        float f4 = (color2 >> 24 & 255) / 255f;
        float f5 = (color2 >> 16 & 255) / 255f;
        float f6 = (color2 >> 8 & 255) / 255f;
        float f7 = (color2 & 255) / 255f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        GL11.glPushMatrix();
        GL11.glBegin(7);
        GL11.glColor4f(f1, f2, f3, f);
        GL11.glVertex2d(left, top);
        GL11.glVertex2d(left, bottom);
        GL11.glColor4f(f5, f6, f7, f4);
        GL11.glVertex2d(right, bottom);
        GL11.glVertex2d(right, top);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }
    public static double interporate(double p_219803_0_, double p_219803_2_, double p_219803_4_) {
        return p_219803_2_ + p_219803_0_ * (p_219803_4_ - p_219803_2_);
    }
    public static double[] worldToScreen(double x, double y, double z) {
        if (Minecraft.getInstance().currentScreen != null && !(Minecraft.getInstance().currentScreen instanceof ChatScreen) && (Minecraft.getInstance().currentScreen == null)) {
            return null;
        } else {
            try {
                Vector3d camera_pos = Minecraft.getInstance().getRenderManager().info.getProjectedView();
                Quaternion camera_rotation_conj = Minecraft.getInstance().getRenderManager().getCameraOrientation().copy();
                camera_rotation_conj.conjugate();
                Vector3f result3f = new Vector3f((float)(camera_pos.x - x), (float)(camera_pos.y - y), (float)(camera_pos.z - z));
                result3f.transform(camera_rotation_conj);
                GameRenderer gameRenderer = Minecraft.getInstance().gameRenderer;
                Method method = null;
                Method[] var11 = GameRenderer.class.getDeclaredMethods();
                int var12 = var11.length;

                for(int var13 = 0; var13 < var12; ++var13) {
                    Method m = var11[var13];
                    if (m.getParameterCount() == 3 && m.getParameterTypes()[2] == Boolean.TYPE && m.getParameterTypes()[1] == Float.TYPE && m.getParameterTypes()[0] == ActiveRenderInfo.class && m.getReturnType() == Double.TYPE) {
                        method = m;
                    }
                }

                method.setAccessible(true);
                float fov = ((Double)method.invoke(gameRenderer, Minecraft.getInstance().getRenderManager().info, 1, true)).floatValue();
                float half_height = (float)Minecraft.getInstance().getMainWindow().getScaledHeight() / 2.0F;
                float scale_factor = half_height / (result3f.getZ() * (float)Math.tan(Math.toRadians((double)(fov / 2.0F))));
                if (result3f.getZ() < 0.0F) {
                    return new double[]{(double)(-result3f.getX() * scale_factor + (float)(Minecraft.getInstance().getMainWindow().getScaledWidth() / 2)), (double)((float)(Minecraft.getInstance().getMainWindow().getScaledHeight() / 2) - result3f.getY() * scale_factor)};
                }
            } catch (Exception var15) {
                var15.printStackTrace();
            }

            return null;
        }
    }
    public static void drawCircle(float x, float y, float start, float end, float radius, float width, boolean filled, int color) {
        float sin;
        float cos;
        float i;
        glColor(-1);
        float endOffset;
        if (start > end) {
            endOffset = end;
            end = start;
            start = endOffset;
        }

        GlStateManager.enableBlend();
        GlStateManager.disableTexture();
        GlStateManager.glBlendFuncSeparate(770, 771, 1, 0);
        glColor(color);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glLineWidth(width);
        GL11.glBegin(GL11.GL_LINE_STRIP);
        for (i = end; i >= start; i -= 4) {
            cos = (float) (Math.cos(i * Math.PI / 180) * radius * 1);
            sin = (float) (Math.sin(i * Math.PI / 180) * radius * 1);
            GL11.glVertex2f(x + cos, y + sin);
        }
        GL11.glEnd();
        GL11.glDisable(GL11.GL_LINE_SMOOTH);

        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glBegin(filled ? GL11.GL_TRIANGLE_FAN : GL11.GL_LINE_STRIP);
        for (i = end; i >= start; i -= 4) {
            cos = (float) Math.cos(i * Math.PI / 180) * radius;
            sin = (float) Math.sin(i * Math.PI / 180) * radius;
            GL11.glVertex2f(x + cos, y + sin);
        }
        GL11.glEnd();
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GlStateManager.enableTexture();
        GlStateManager.disableBlend();
    }

    public static void sizeAnimation(double width, double height, double animation) {
        GL11.glTranslated(width / 2, height / 2, 0);
        GL11.glScaled(animation, animation, 1);
        GL11.glTranslated(-width / 2, -height / 2, 0);
    }
    public static void drawGradientRound(float x, float y, float width, float height, float radius, int bottomLeft, int topLeft, int bottomRight, int topRight) {
        GlStateManager.color4f(255, 255, 255, 255);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        roundedGradientShader.init();
        ShaderUtil.setupRoundedRectUniforms(x, y, width, height, radius, roundedGradientShader);
        // Bottom Left
        roundedGradientShader.setUniformf("color1", ColorUtil.getRed(bottomLeft) / 255f, ColorUtil.getGreen(bottomLeft) / 255f, ColorUtil.getBlue(bottomLeft) / 255f, ColorUtil.getAlpha(bottomLeft) / 255f);
        //Top left
        roundedGradientShader.setUniformf("color2", ColorUtil.getRed(topLeft) / 255f, ColorUtil.getGreen(topLeft) / 255f, ColorUtil.getBlue(topLeft) / 255f, ColorUtil.getAlpha(topLeft) / 255f);
        //Bottom Right
        roundedGradientShader.setUniformf("color3", ColorUtil.getRed(bottomRight) / 255f, ColorUtil.getGreen(bottomRight) / 255f, ColorUtil.getBlue(bottomRight) / 255f, ColorUtil.getAlpha(bottomRight) / 255f);
        //Top Right
        roundedGradientShader.setUniformf("color4", ColorUtil.getRed(topRight) / 255f, ColorUtil.getGreen(topRight) / 255f, ColorUtil.getBlue(topRight) / 255f, ColorUtil.getAlpha(topRight) / 255f);
        ShaderUtil.drawQuads(x - 1, y - 1, width + 2, height + 2);
        roundedGradientShader.unload();
        GlStateManager.disableBlend();
    }
    protected static float zLevel;
    public static void drawGradientRect(double d, double e, double e2, double g, int startColor, int endColor) {
        float f = (float) (startColor >> 24 & 255) / 255.0F;
        float f1 = (float) (startColor >> 16 & 255) / 255.0F;
        float f2 = (float) (startColor >> 8 & 255) / 255.0F;
        float f3 = (float) (startColor & 255) / 255.0F;
        float f4 = (float) (endColor >> 24 & 255) / 255.0F;
        float f5 = (float) (endColor >> 16 & 255) / 255.0F;
        float f6 = (float) (endColor >> 8 & 255) / 255.0F;
        float f7 = (float) (endColor & 255) / 255.0F;
        GlStateManager.disableTexture();
        GlStateManager.enableBlend();
        GlStateManager.disableAlphaTest();
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA.param, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA.param, GlStateManager.SourceFactor.ONE.param, GlStateManager.DestFactor.ZERO.param);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(e2, e, zLevel).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos(d, e, zLevel).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos(d, g, zLevel).color(f5, f6, f7, f4).endVertex();
        bufferbuilder.pos(e2, g, zLevel).color(f5, f6, f7, f4).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlphaTest();
        GlStateManager.enableTexture();
    }

    public static void drawBorderedRect(float left, float top, float right, float bottom, float borderWidth, int insideColor, int borderColor, boolean borderIncludedInBounds) {
        drawRect(left - (!borderIncludedInBounds ? borderWidth : 0), top - (!borderIncludedInBounds ? borderWidth : 0), right + (!borderIncludedInBounds ? borderWidth : 0), bottom + (!borderIncludedInBounds ? borderWidth : 0), borderColor);
        drawRect(left + (borderIncludedInBounds ? borderWidth : 0), top + (borderIncludedInBounds ? borderWidth : 0), right - ((borderIncludedInBounds ? borderWidth : 0)), bottom - ((borderIncludedInBounds ? borderWidth : 0)), insideColor);
    }
    public static void drawRect(double left, double top, double right, double bottom, int color) {

        right += left;
        bottom += top;
        float f3 = (float) (color >> 24 & 255) / 255.0F;
        float f = (float) (color >> 16 & 255) / 255.0F;
        float f1 = (float) (color >> 8 & 255) / 255.0F;
        float f2 = (float) (color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture();
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA.param,
                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA.param, GlStateManager.SourceFactor.ONE.param,
                GlStateManager.DestFactor.ZERO.param);
        GlStateManager.color4f(f, f1, f2, f3);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferbuilder.pos(left, bottom, 0.0D).endVertex();
        bufferbuilder.pos(right, bottom, 0.0D).endVertex();
        bufferbuilder.pos(right, top, 0.0D).endVertex();
        bufferbuilder.pos(left, top, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture();
        GlStateManager.disableBlend();
    }
    public static void scissorRect(float x, float y, float width, double height) {
        int factor = (int) Minecraft.getInstance().getMainWindow().getGuiScaleFactor();
        int scaledHeight = Minecraft.getInstance().getMainWindow().getHeight();
        GL11.glScissor((int) (x * (float) factor), (int) (((float) scaledHeight - height * (float) factor)), (int) ((width - x) * (float) factor), (int) ((height - y) * (float) factor));
    }

    public static void drawImage(MatrixStack ms, ResourceLocation rs, float x, float y, float width, float height) {
        RenderSystem.disableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.color4f(0.8f, 0.8f, 0.8f, 1);
        RenderSystem.blendFunc(770, 771);
        Minecraft.getInstance().getTextureManager().bindTexture(rs);
        IngameGui.blit(ms, (int) x, (int) y, (float) 0, (float) 0, (int) width, (int) height, (int) width, (int) height);
        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
        RenderSystem.color3f(255f, 255f, 255f);
    }

    public static void drawRoundCircle(float x, float y, float radius, int color) {
        drawRound(x - (radius / 2), y - (radius / 2), radius, radius, ((radius / 2) - 0.5f), color);
    }

    public static void drawBlurredShadow(double x, double y, double width, double height, int blurRadius, Color color) {
        drawBlurredShadow(color, x, y, width, height, blurRadius);
    }

    public static void drawBlurredShadow(Color color, double x, double y, double width, double height, int blurRadius) {
        width = width + blurRadius * 2;
        height = height + blurRadius * 2;
        x = x - blurRadius;
        y = y - blurRadius;

        float _X = (float) (x - 0.25f);
        float _Y = (float) (y + 0.25f);

        int identifier = String.valueOf(width * height + width + 1000000000 * blurRadius + blurRadius).hashCode();
        GL11.glDisable(GL_DEPTH_TEST);

        GL11.glEnable(GL_TEXTURE_2D);

        GlStateManager.disableCull();
        GlStateManager.enableBlend();

        GlStateManager.alphaFunc(GL_GREATER, 0.0f);


        int texId = -1;
        if (shadowCache.containsKey(identifier)) {
            texId = shadowCache.get(identifier);

            GlStateManager.bindTexture(texId);
        } else {
            BufferedImage original = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_ARGB);

            Graphics g = original.getGraphics();
            g.setColor(Color.white);
            g.fillRect(blurRadius, blurRadius, (int) width - blurRadius * 2, (int) height - blurRadius * 2);
            g.dispose();

            GaussianFilter op = new GaussianFilter(blurRadius);

            BufferedImage blurred = op.filter(original, null);

            try {
                texId = new DynamicTexture(NativeImage.read(DynamicTexture.convertImageData(blurred))).getGlTextureId();
            }catch (Exception ex){ex.printStackTrace();}
            shadowCache.put(identifier, texId);
        }


        glColor(color.getRGB());

        GL11.glBegin(GL11.GL_QUADS);

        GL11.glTexCoord2f(0, 0);
        GL11.glVertex2f(_X, _Y);

        GL11.glTexCoord2f(0, 1);
        GL11.glVertex2f(_X, _Y + (int) height);

        GL11.glTexCoord2f(1, 1);
        GL11.glVertex2f(_X + (int) width, _Y + (int) height);

        GL11.glTexCoord2f(1, 0);
        GL11.glVertex2f(_X + (int) width, _Y);
        GL11.glEnd();


        GL11.glEnable(GL_TEXTURE_2D);
        GlStateManager.enableCull();
        GlStateManager.disableBlend();
        GL11.glEnable(GL_DEPTH_TEST);

    }


    public static double[] project(double x, double y, double z) {
        Vector3d camera_pos = Minecraft.getInstance().getRenderManager().info.getProjectedView();
        Quaternion camera_rotation_conj = Minecraft.getInstance().getRenderManager().getCameraOrientation().copy();
        camera_rotation_conj.conjugate();
        Vector3f result3f = new Vector3f((float)(camera_pos.x - x), (float)(camera_pos.y - y), (float)(camera_pos.z - z));result3f.transform(camera_rotation_conj);

        if (mc.gameSettings.viewBobbing) {
            Entity renderViewEntity = mc.getRenderViewEntity();
            if (renderViewEntity instanceof PlayerEntity) {
                PlayerEntity playerentity = (PlayerEntity) renderViewEntity;
                float distwalked_modified = playerentity.distanceWalkedModified;

                float f = distwalked_modified - playerentity.prevDistanceWalkedModified;
                float f1 = -(distwalked_modified + f * mc.getRenderPartialTicks());
                float f2 = MathHelper.lerp(mc.getRenderPartialTicks(), playerentity.prevCameraYaw, playerentity.cameraYaw);
                Quaternion q2 = new Quaternion(Vector3f.XP, Math.abs(MathHelper.cos(f1 * (float) Math.PI - 0.2F) * f2) * 5.0F, true);
                q2.conjugate();
                result3f.transform(q2);

                Quaternion q1 = new Quaternion(Vector3f.ZP, MathHelper.sin(f1 * (float) Math.PI) * f2 * 3.0F, true);
                q1.conjugate();
                result3f.transform(q1);

                Vector3f bob_translation = new Vector3f((MathHelper.sin(f1 * (float) Math.PI) * f2 * 0.5F), (-Math.abs(MathHelper.cos(f1 * (float) Math.PI) * f2)), 0.0f);
                bob_translation.setY(-bob_translation.getY());  // this is weird but hey, if it works
                result3f.add(bob_translation);
            }
        }

        double fov = (float) mc.gameRenderer.getFOVModifier(Minecraft.getInstance().getRenderManager().info, mc.getRenderPartialTicks(), true);

        float half_height = (float)Minecraft.getInstance().getMainWindow().getScaledHeight() / 2.0F;
        float scale_factor = half_height / (result3f.getZ() * (float)Math.tan(Math.toRadians((double)(fov / 2.0F))));
        if (result3f.getZ() < 0.0F) {
            return new double[]{(double)(-result3f.getX() * scale_factor + (float)(Minecraft.getInstance().getMainWindow().getScaledWidth() / 2)), (double)((float)(Minecraft.getInstance().getMainWindow().getScaledHeight() / 2) - result3f.getY() * scale_factor)};
        }
        return null;
    }


    public static String chatPrefix = TextFormatting.DARK_GRAY + "" + TextFormatting.AQUA + "FecuritySQ Recode"
            + TextFormatting.GOLD + " | " + TextFormatting.GRAY;

    public static void addChatMessage(String message) {
        String chatPrefix =  TextFormatting.AQUA + "(FecuritySQ)" + TextFormatting.WHITE + " 1.16.5"
                + TextFormatting.YELLOW + " > " + TextFormatting.GRAY;
        mc.ingameGUI.getChatGUI().printChatMessage(new StringTextComponent(chatPrefix + message));
    }
    public static void enableSmoothLine(float width) {
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDepthMask(false);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
        GL11.glLineWidth(width);
    }

    public static void disableSmoothLine() {
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
    }
    public static void setColor(int color) {
        GL11.glColor4ub((byte) (color >> 16 & 0xFF), (byte) (color >> 8 & 0xFF), (byte) (color & 0xFF), (byte) (color >> 24 & 0xFF));
    }
    public static void glColor(int hex) {
        float alpha = (float) (hex >> 24 & 0xFF) / 255.0f;
        float red = (float) (hex >> 16 & 0xFF) / 255.0f;
        float green = (float) (hex >> 8 & 0xFF) / 255.0f;
        float blue = (float) (hex & 0xFF) / 255.0f;
        GL11.glColor4f(red, green, blue, alpha);
    }
    public static void drawTriangle(float x, float y, float width, float height, int firstColor, int secondColor) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);

        enableSmoothLine(1F);
        GL11.glRotatef(180 + 90, 0F, 0F, 1.0F);

        // fill.
        GL11.glBegin(9);
        glColor(firstColor);
        GL11.glVertex2f(x, y - 2);
        GL11.glVertex2f(x + width, y + height);
        GL11.glVertex2f(x + width, y);
        GL11.glVertex2f(x, y - 2);
        GL11.glEnd();

        GL11.glBegin(9);
        glColor(secondColor);
        GL11.glVertex2f(x + width, y);
        GL11.glVertex2f(x + width, y + height);
        GL11.glVertex2f(x + width * 2, y - 2);
        GL11.glVertex2f(x + width, y);
        GL11.glEnd();

        // line.
        GL11.glBegin(3);
        glColor(firstColor);
        GL11.glVertex2f(x, y - 2);
        GL11.glVertex2f(x + width, y + height);
        GL11.glVertex2f(x + width, y);
        GL11.glVertex2f(x, y - 2);
        GL11.glEnd();

        GL11.glBegin(3);
        glColor(secondColor);
        GL11.glVertex2f(x + width, y);
        GL11.glVertex2f(x + width, y + height);
        GL11.glVertex2f(x + width * 2, y - 2);
        GL11.glVertex2f(x + width, y);
        GL11.glEnd();

        disableSmoothLine();
        GL11.glRotatef(-180 - 90, 0F, 0F, 1.0F);
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_BLEND);
    }
}
