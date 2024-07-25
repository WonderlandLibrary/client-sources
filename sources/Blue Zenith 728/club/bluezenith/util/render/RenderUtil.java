package club.bluezenith.util.render;

import club.bluezenith.util.MinecraftInstance;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import java.awt.Color;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.function.Function;

import static java.lang.Math.*;
import static net.minecraft.client.gui.Gui.drawRect;
import static net.minecraft.client.renderer.GlStateManager.*;
import static org.lwjgl.opengl.GL11.*;

public class RenderUtil extends MinecraftInstance {
    public static int delta = 0;

    public static int alphaLimit255 = 255;
    public static boolean limitAlpha;

    public static void drawImage(ResourceLocation image, float x, float y, float width, float height, float alpha) {
        GlStateManager.pushMatrix();
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
        GlStateManager.color(1, 1, 1, alpha);
        mc.getTextureManager().bindTexture(image);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, width, height, width, height);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GlStateManager.popMatrix();
    }

    public static void drawShadow(ResourceLocation o, float x, float y, float width, float height, float alpha) {
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.color(1, 1, 1, alpha);
        mc.getTextureManager().bindTexture(o);
        GlStateManager.color(1, 1, 1, alpha);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, width, height, width, height);
        disableBlend();
        enableAlpha();
    }

    public static float animate(float target, float current, float speed) {
        speed = MathHelper.clamp(speed * 0.1f * delta, 0, 1);
        float dif = Math.max(target, current) - Math.min(target, current);
        float factor = dif * speed;
        if (target > current) {
            current += factor;
        } else {
            current -= factor;
        }
        return current;
    }

    private static void drawQuads(float x, float y, float width, float height) {
        GL11.glBegin(GL_QUADS);
        glTexCoord2f(0, 0);
        glVertex2f(x, y);
        glTexCoord2f(0, 1);
        glVertex2f(x, y + height);
        glTexCoord2f(1, 1);
        glVertex2f(x + width, y + height);
        glTexCoord2f(1, 0);
        glVertex2f(x + width, y);
        GL11.glEnd();
    }

    public static void circle(float x, float y, float r, int startColor, int c, int rotation) {
        float f = (c >> 24 & 0xFF) / 255.0f;
        float f2 = (c >> 16 & 0xFF) / 255.0f;
        float f3 = (c >> 8 & 0xFF) / 255.0f;
        float f4 = (c & 0xFF) / 255.0f;
        GL11.glPushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GL11.glEnable(2848);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glBegin(6);
        for (int i = 0; i <= 90; ++i) {
            double x2 = Math.sin(i * Math.PI / 180.0) * (r / 2);
            double y2 = Math.cos(i * Math.PI / 180.0) * (r / 2);
            GL11.glVertex2d(x + r / 2 + x2, y + r / 2 + y2);
        }
        GL11.glEnd();
        GL11.glBegin(GL11.GL_LINE_LOOP);
        for (int i = 0; i <= 360; ++i) {
            double x2 = Math.sin(i * Math.PI / 180.0) * ((r / 2));
            double y2 = Math.cos(i * Math.PI / 180.0) * ((r / 2) );
            GL11.glVertex2d(x + ((r / 2)) + x2, y + ((r / 2)) + y2);
        }
        GL11.glEnd();
        GL11.glDisable(2848);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GL11.glPopMatrix();
    }

    public static void hollowRect(float x, float y, float x2, float y2, float width, int color1) {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);

        glColor(color1);
        final int leWidth = GL11.glGetInteger(GL_LINE_WIDTH);
        GL11.glLineWidth(width);

        GL11.glBegin(GL_LINE_LOOP);

        glVertex2d(x2, y);
        glVertex2d(x, y);
        glVertex2d(x, y2);
        glVertex2d(x2, y2);


        GL11.glEnd();
        GL11.glLineWidth(leWidth);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        resetColor();
    }

    public static void hollowRectWithGradient(float x, float y, float x2, float y2, float width, Function<Integer, Integer> color1) {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);

        glColor(color1.apply(0));
        final int leWidth = GL11.glGetInteger(GL_LINE_WIDTH);
        GL11.glLineWidth(width);

        final int before = GL11.glGetInteger(GL_SHADE_MODEL);
        GL11.glShadeModel(GL_SMOOTH);
        GL11.glBegin(GL_LINE_LOOP);

        glColor(color1.apply(1));
        glVertex2d(x2, y);
        glColor(color1.apply(3));
        glVertex2d(x, y);
        glColor(color1.apply(5));
        glVertex2d(x, y2);
        glColor(color1.apply(7));
        glVertex2d(x2, y2);


        GL11.glEnd();
        GL11.glShadeModel(before);
        GL11.glLineWidth(leWidth);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        resetColor();
    }

    public static void start2D(int beginMode) {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);

        GL11.glBegin(beginMode);
    }

    public static void end2D() {
        GL11.glEnd();

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
    }

    public static void drawGradientRectHorizontal(float x, float y, float x2, float y2, int startColor, int endColor) {
        float alpha = (float) (startColor >> 24 & 255) / 255.0F;
        float red = (float) (startColor >> 16 & 255) / 255.0F;
        float green = (float) (startColor >> 8 & 255) / 255.0F;
        float blue = (float) (startColor & 255) / 255.0F;
        float alpha1 = (float) (endColor >> 24 & 255) / 255.0F;
        float red1 = (float) (endColor >> 16 & 255) / 255.0F;
        float green1 = (float) (endColor >> 8 & 255) / 255.0F;
        float blue1 = (float) (endColor & 255) / 255.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        shadeModel(GL_SMOOTH);
        /*Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();*/
        GL11.glBegin(7);

        GlStateManager.color(red1, green1, blue1, alpha1);
        glVertex2d(x2, y);
        GlStateManager.color(red, green, blue, alpha);
        glVertex2d(x, y);
        GlStateManager.color(red, green, blue, alpha);
        glVertex2d(x, y2);
        GlStateManager.color(red1, green1, blue1, alpha1);
        glVertex2d(x2, y2);

        GL11.glEnd();
        //tessellator.draw();
        shadeModel(7424);
        disableBlend();
        enableAlpha();
        enableTexture2D();
    }

    public static void drawGradientRectVertical(float x, float y, float x2, float y2, int startColor, int endColor) {
        float alpha = (float) (startColor >> 24 & 255) / 255.0F;
        float red = (float) (startColor >> 16 & 255) / 255.0F;
        float green = (float) (startColor >> 8 & 255) / 255.0F;
        float blue = (float) (startColor & 255) / 255.0F;
        float alpha1 = (float) (endColor >> 24 & 255) / 255.0F;
        float red1 = (float) (endColor >> 16 & 255) / 255.0F;
        float green1 = (float) (endColor >> 8 & 255) / 255.0F;
        float blue1 = (float) (endColor & 255) / 255.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        shadeModel(GL_SMOOTH);
        /*Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();*/
        GL11.glBegin(7);

        GlStateManager.color(red, green, blue, alpha);
        glVertex2d(x2, y);
        GlStateManager.color(red, green, blue, alpha);
        glVertex2d(x, y);
        GlStateManager.color(red1, green1, blue1, alpha1);
        glVertex2d(x, y2);
        GlStateManager.color(red1, green1, blue1, alpha1);
        glVertex2d(x2, y2);

        GL11.glEnd();
        //tessellator.draw();
        shadeModel(7424);
        disableBlend();
        enableAlpha();
        enableTexture2D();
    }

    public static void gradientCorner(float x, float y, float size, int startColor, int endColor, int rotation) {
        float alpha = (float) (startColor >> 24 & 255) / 255.0F;
        float red = (float) (startColor >> 16 & 255) / 255.0F;
        float green = (float) (startColor >> 8 & 255) / 255.0F;
        float blue = (float) (startColor & 255) / 255.0F;
        float alpha1 = (float) (endColor >> 24 & 255) / 255.0F;
        float red1 = (float) (endColor >> 16 & 255) / 255.0F;
        float green1 = (float) (endColor >> 8 & 255) / 255.0F;
        float blue1 = (float) (endColor & 255) / 255.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        shadeModel(GL_SMOOTH);
        /*Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();*/

        translate(x, y, 0);
        GL11.glBegin(GL11.GL_TRIANGLES); //todo replace all (i * pi / 180) calls with math.toradians
        for (int i = rotation; i < 90 + rotation; i+=18) { // the less the best

            GlStateManager.color(red, green, blue, alpha);
            GL11.glVertex3d(cos(i * Math.PI / 180) * 0, (sin(i * Math.PI / 180) * 0), 0); //todo LMAO

            GlStateManager.color(red1, green1, blue1, alpha1);
            GL11.glVertex3d(cos((i + 18) * Math.PI / 180) * size, (sin((i + 18) * Math.PI / 180) * size), 0);

            GlStateManager.color(red1, green1, blue1, alpha1);
            GL11.glVertex3d(cos(i * Math.PI / 180) * size, (sin(i * Math.PI / 180) * size), 0);
        }

        /*GlStateManager.color(red, green, blue, alpha);
        GL11.glVertex2d(0, 0);
        GlStateManager.color(red1, green1, blue1, alpha1);
        GL11.glVertex2d(0, size);
        GL11.glVertex2d(size, 0);*/

        GL11.glEnd();
        translate(-x, -y, 0);
        //tessellator.draw();
        shadeModel(7424);
        disableBlend();
        enableAlpha();
        enableTexture2D();
    }

    public static void rect(final float x, final float y, final float x2, final float y2, final int color) {
        drawRect(x,y,x2,y2, color);
        resetColor();
    }

    public static void rect(final float x, final float y, final float x2, final float y2, final Color color) {
        drawRect(x,y,x2,y2,color.getRGB());
        resetColor();
    }

    public static void rect(final double x, final double y, final double x2, final double y2, final Color color) {
        drawRect(x,y,x2,y2,color.getRGB());
        resetColor();
    }

    public static void rect(final double x, final double y, final double x2, final double y2, final int color) {
        drawRect(x,y,x2,y2,color);
        resetColor();
    }

    public static void crop(final float x, final float y, final float x2, final float y2) {
        final ScaledResolution scaledResolution = new ScaledResolution(mc);
        final int factor = scaledResolution.getScaleFactor();
        glScissor((int) (x * factor), (int) ((scaledResolution.getScaledHeight() - y2) * factor), (int) ((x2 - x) * factor), (int) ((y2 - y) * factor));
    }

    public static void crop(final double x, final double y, final double x2, final double y2) {
        final ScaledResolution scaledResolution = new ScaledResolution(mc);
        final int factor = scaledResolution.getScaleFactor();
        glScissor((int) (x * factor), (int) ((scaledResolution.getScaledHeight() - y2) * factor), (int) ((x2 - x) * factor), (int) ((y2 - y) * factor));
    }

    private static int lastScale;
    private static int lastScaleWidth;
    private static int lastScaleHeight;
    private static Framebuffer buffer;
    private static final ResourceLocation shader = new ResourceLocation("club/bluezenith/blur.json"); // club/bluezenith/blur.json
    private static ShaderGroup blurShader;

    public static void initFboAndShader() {
        try {
            blurShader = new ShaderGroup(mc.getTextureManager(), mc.getResourceManager(), mc.getFramebuffer(), shader);
            blurShader.createBindFramebuffers(mc.displayWidth, mc.displayHeight);
            buffer = blurShader.mainFramebuffer;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Vec3 project2D(int scaleFactor, float x, float y, float z, FloatBuffer modelView, FloatBuffer projection, IntBuffer viewport, FloatBuffer winPos) {
        GL11.glGetFloat(GL_MODELVIEW_MATRIX, modelView);
        GL11.glGetFloat(GL_PROJECTION_MATRIX, projection);
        GL11.glGetInteger(GL_VIEWPORT, viewport);

        if (GLU.gluProject(x, y, z, modelView, projection, viewport, winPos)) {

            double projX = winPos.get(0) / scaleFactor;
            double projY = (Display.getHeight() -winPos.get(1)) / scaleFactor;
            double projZ = winPos.get(2);

            return new Vec3(projX, projY, projZ);
        }
        else {
            return null;
        }
    }

    public static double[] projectBoundingBox(Entity entity,
                                        AxisAlignedBB boundingBox,
                                        int scaleFactor,
                                        float partialTicks,
                                        FloatBuffer modelViewBuffer,
                                        FloatBuffer projectionBuffer,
                                        IntBuffer viewportBuffer,
                                        FloatBuffer windowPositionBuffer
    ) {
        boolean arrayFilled = false;

        double[] result = new double[4];

        final float bypassValue = partialTicks - 1;

        boundingBox = boundingBox.offset(
                (entity.posX - entity.prevPosX) * bypassValue,
                (entity.posY - entity.prevPosY) * bypassValue,
                (entity.posZ - entity.prevPosZ) * bypassValue
        );

        final double[][] corners = {
                { boundingBox.minX - .3, boundingBox.minY - .1, boundingBox.minZ - .3 },
                { boundingBox.minX - .3, boundingBox.maxY + (entity.isSneaking() ? -.05 : .3), boundingBox.minZ - .3 },
                { boundingBox.minX - .3, boundingBox.maxY + (entity.isSneaking() ? -.05 : .3), boundingBox.maxZ + .3 },
                { boundingBox.minX - .3, boundingBox.minY - .1, boundingBox.maxZ + .3 },
                { boundingBox.maxX + .3, boundingBox.minY - .1, boundingBox.minZ - .3 },
                { boundingBox.maxX + .3, boundingBox.maxY + (entity.isSneaking() ? -.05 : .3), boundingBox.minZ - .3 },
                { boundingBox.maxX + .3, boundingBox.maxY + (entity.isSneaking() ? -.05 : .3), boundingBox.maxZ + .3 },
                { boundingBox.maxX + .3, boundingBox.minY - .1, boundingBox.maxZ + .3 }
        };

        final RenderManager renderManager = mc.getRenderManager();

        for (double[] corner : corners) {
            final Vec3 projected = project2D(
                    scaleFactor,
                    (float) (corner[0] - renderManager.viewerPosX),
                    (float) (corner[1] - renderManager.viewerPosY),
                    (float) (corner[2] - renderManager.viewerPosZ),
                    modelViewBuffer,
                    projectionBuffer,
                    viewportBuffer,
                    windowPositionBuffer
            );

            if(projected == null) continue;

            result[0] = min(arrayFilled ? result[0] : projected.xCoord, projected.xCoord);
            result[1] = min(arrayFilled ? result[1] : projected.yCoord, projected.yCoord);
            result[2] = max(arrayFilled ? result[2] : projected.xCoord, projected.xCoord);
            result[3] = max(arrayFilled ? result[3] : projected.yCoord, projected.yCoord);

            arrayFilled = true;
        }

        return result;
    }

    public static void blur(float x, float y, float x2, float y2, ScaledResolution sc) {
        int factor = sc.getScaleFactor();
        int factor2 = sc.getScaledWidth();
        int factor3 = sc.getScaledHeight();
        if (lastScale != factor || lastScaleWidth != factor2 || lastScaleHeight != factor3 || buffer == null
                || blurShader == null) {
            initFboAndShader();
        }
        lastScale = factor;
        lastScaleWidth = factor2;
        lastScaleHeight = factor3;
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        crop(x, y, x2, y2);
        buffer.framebufferHeight = mc.displayHeight;
        buffer.framebufferWidth = mc.displayWidth;
        resetColor();
        blurShader.loadShaderGroup(mc.timer.renderPartialTicks);
        buffer.bindFramebuffer(true);
        mc.getFramebuffer().bindFramebuffer(true);
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }

    public static void blur(float x, float y, float x2, float y2){
        GlStateManager.disableAlpha();
        blur(x, y, x2, y2, new ScaledResolution(mc));
        enableAlpha();
    }

    public static float drawScaledFont(FontRenderer f, String text, float x, float y, int color, boolean shadow, float scale){
        GlStateManager.pushMatrix();
        translate(x, y, 0);
        GlStateManager.scale(scale, scale, 1);
        f.drawString(text, 0, 0, color, shadow);
        GlStateManager.popMatrix();
        return f.getStringWidthF(text) * scale;
    }

    public static void glColor(final Color color) {
        final float red = color.getRed() / 255F;
        final float green = color.getGreen() / 255F;
        final float blue = color.getBlue() / 255F;
        final float alpha = color.getAlpha() / 255F;

        GlStateManager.color(red, green, blue, alpha);
    }

    public static void glColor(final int hex) {
        final float alpha = (hex >> 24 & 0xFF) / 255F;
        final float red = (hex >> 16 & 0xFF) / 255F;
        final float green = (hex >> 8 & 0xFF) / 255F;
        final float blue = (hex & 0xFF) / 255F;

        GlStateManager.color(red, green, blue, alpha);
    }

    public static void drawEntityBox(Entity entity, final Color color) {
        drawEntityBox(entity, color.getRGB());
    }

    public static void drawBox(double x, double y, double z, int color) {
        final RenderManager renderManager = mc.getRenderManager();
        final Timer timer = mc.timer;

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        GL11.glDepthMask(false);

        final double x1 = x + (0.0) * timer.renderPartialTicks
                - renderManager.renderPosX;
        final double y1 = y + 0.0 * timer.renderPartialTicks
                - renderManager.renderPosY;
        final double z1 = z + 0.0 * timer.renderPartialTicks
                - renderManager.renderPosZ;

        final AxisAlignedBB axisAlignedBB = new AxisAlignedBB(
                (x - x + x1 - 0.05D),
                (y)  - y + y1,
                (z)  - z + z1 - 0.05D,
                (x) - x + x1 + 0.05D,
                (y) - y + y1 + 0.15D,
                (z) - z + z1 + 0.05D
        );
        float f3 = (float) (color >> 24 & 255) / 255.0F;
        float f = (float) (color >> 16 & 255) / 255.0F;
        float f1 = (float) (color >> 8 & 255) / 255.0F;
        float f2 = (float) (color & 255) / 255.0F;

        GlStateManager.color(f, f1, f2, f3);
        drawAxis(axisAlignedBB);
        resetColor();
        GL11.glDepthMask(true);

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    public static void drawEntityBox(Entity entity, int color) {
        final RenderManager renderManager = mc.getRenderManager();
        final Timer timer = mc.timer;

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        GL11.glDepthMask(false);

        final double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * timer.renderPartialTicks
                - renderManager.renderPosX;
        final double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * timer.renderPartialTicks
                - renderManager.renderPosY;
        final double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * timer.renderPartialTicks
                - renderManager.renderPosZ;

        final AxisAlignedBB entityBox = entity.getEntityBoundingBox();
        final AxisAlignedBB axisAlignedBB = new AxisAlignedBB(
                entityBox.minX - entity.posX + x - 0.05D,
                entityBox.minY - entity.posY + y,
                entityBox.minZ - entity.posZ + z - 0.05D,
                entityBox.maxX - entity.posX + x + 0.05D,
                entityBox.maxY - entity.posY + y + 0.15D,
                entityBox.maxZ - entity.posZ + z + 0.05D
        ).expand(0.1, 0.1, 0.1);
        float red = (color >> 16 & 255) / 255F;
        float green = (color >> 8 & 255) / 255F;
        float blue = (color & 255) / 255F;
        float alpha = (color >> 24 & 255) / 255F;

        GlStateManager.color(red, green, blue, alpha);
        drawAxis(axisAlignedBB);
        resetColor();
        GL11.glDepthMask(true);

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    public static void drawAxis(AxisAlignedBB axisAlignedBB){
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldRenderer = tessellator.getWorldRenderer();

        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();

        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();

        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();

        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();

        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();

        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        tessellator.draw();
    }

    public static Color darken(Color c, int by) {
        return new Color(max(0, c.getRed() - by), max(0, c.getGreen() - by), max(0, c.getBlue() - by));
    }

    public static float getProgressForTarget(EntityPlayer target, boolean accountForAbsorption) {
        float maxAbsorptionHearts = 0;
        if(accountForAbsorption) {
            for (PotionEffect potionEffect : target.getActivePotionEffects()) {
                if (potionEffect.getEffectName().equals("potion.absorption") && target.getAbsorptionAmount() > 0) {
                    maxAbsorptionHearts += (potionEffect.getAmplifier() + 1.0F) * 4.0F;
                }
            }
        }
        return 1 - ((target.getMaxHealth() + maxAbsorptionHearts) - (target.getHealth() + target.getAbsorptionAmount())) / (target.getMaxHealth() + maxAbsorptionHearts);
    }
}
