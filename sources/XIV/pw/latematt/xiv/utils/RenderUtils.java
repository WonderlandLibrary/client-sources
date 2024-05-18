package pw.latematt.xiv.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.value.ClampedValue;
import pw.latematt.xiv.value.Value;

/**
 * @author Matthew
 */
public class RenderUtils {
    private static final ClampedValue<Float> LINE_WIDTH = new ClampedValue<>("render_line_width", 1.0F, 0.5F, 4.0F);
    private static final Value<Boolean> ANTI_ALIASING = new Value<>("render_anti_aliasing", false);
    private static final Value<Boolean> WORLD_BOBBING = new Value<>("render_world_bobbing", false);
    private static final ClampedValue<Float> NAMETAG_OPACITY = new ClampedValue<>("render_nametag_opacity", 0.25F, 0.0F, 1.0F);
    private static final ClampedValue<Float> NAMETAG_SIZE = new ClampedValue<>("render_nametag_size", 1.0F, 0.1F, 1.5F);
    private static final Value<Boolean> TRACER_ENTITY = new Value<>("render_tracer_entity", true);
    private static final Value<Boolean> SHOW_TAGS = new Value<>("render_show_tags", true);

    public static ClampedValue<Float> getLineWidth() {
        return LINE_WIDTH;
    }

    public static Value<Boolean> getAntiAliasing() {
        return ANTI_ALIASING;
    }

    public static Value<Boolean> getWorldBobbing() {
        return WORLD_BOBBING;
    }

    public static ClampedValue<Float> getNametagOpacity() {
        return NAMETAG_OPACITY;
    }

    public static ClampedValue<Float> getNametagSize() {
        return NAMETAG_SIZE;
    }

    public static Value<Boolean> getTracerEntity() {
        return TRACER_ENTITY;
    }

    public static Value<Boolean> getShowTags() {
        return SHOW_TAGS;
    }

    public static void beginGl() {
        GlStateManager.pushMatrix();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.func_179090_x();
        if (ANTI_ALIASING.getValue())
            GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glLineWidth(LINE_WIDTH.getValue());
    }

    public static void endGl() {
        GL11.glLineWidth(2.0F);
        if (ANTI_ALIASING.getValue())
            GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GlStateManager.func_179098_w();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.popMatrix();
    }

    public static void renderOne() {
        GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glLineWidth(RenderUtils.getLineWidth().getValue() * 2);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_STENCIL_TEST);
        GL11.glClear(GL11.GL_STENCIL_BUFFER_BIT);
        GL11.glClearStencil(0xF);
        GL11.glStencilFunc(GL11.GL_NEVER, 1, 0xF);
        GL11.glStencilOp(GL11.GL_REPLACE, GL11.GL_REPLACE, GL11.GL_REPLACE);
        GL11.glPolygonMode(GL11.GL_FRONT, GL11.GL_LINE);
    }

    public static void renderTwo() {
        GL11.glStencilFunc(GL11.GL_NEVER, 0, 0xF);
        GL11.glStencilOp(GL11.GL_REPLACE, GL11.GL_REPLACE, GL11.GL_REPLACE);
        GL11.glPolygonMode(GL11.GL_FRONT, GL11.GL_FILL);
    }

    public static void renderThree() {
        GL11.glStencilFunc(GL11.GL_EQUAL, 1, 0xF);
        GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_KEEP);
        GL11.glPolygonMode(GL11.GL_FRONT, GL11.GL_LINE);
    }

    public static void renderFour(Minecraft mc, Entity renderEntity) {
        float[] color = new float[]{0.0F, 0.9F, 0.0F};

        if(renderEntity instanceof EntityLivingBase) {
            EntityLivingBase entity = (EntityLivingBase) renderEntity;

            final float distance = EntityUtils.getReference().getDistanceToEntity(entity);
            if (entity instanceof EntityPlayer && XIV.getInstance().getFriendManager().isFriend(entity.getName())) {
                color = new float[]{0.3F, 0.7F, 1.0F};
            } else if (entity instanceof EntityPlayer && XIV.getInstance().getAdminManager().isAdmin(entity.getName())) {
                color = new float[]{1.0F, 0.0F, 1.0F};
            } else if (entity.isInvisibleToPlayer(mc.thePlayer)) {
                color = new float[]{1.0F, 0.9F, 0.0F};
            } else if (entity.hurtTime > 0) {
                color = new float[]{1.0F, 0.66F, 0.0F};
            } else if (distance <= 3.9F) {
                color = new float[]{0.9F, 0.0F, 0.0F};
            }
        }else{
            Entity entity = renderEntity;

            final float distance = EntityUtils.getReference().getDistanceToEntity(entity);
            if (entity.isInvisibleToPlayer(mc.thePlayer)) {
                color = new float[]{1.0F, 0.9F, 0.0F};
            } else if (distance <= 3.9F) {
                color = new float[]{0.9F, 0.0F, 0.0F};
            }
        }
        GlStateManager.color(color[0], color[1], color[2], 1F);

        renderFour();
    }

    public static void renderFour() {
        GL11.glDepthMask(false);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_POLYGON_OFFSET_LINE);
        GL11.glPolygonOffset(1.0F, -2000000F);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
    }

    public static void renderFive() {
        GL11.glPolygonOffset(1.0F, 2000000F);
        GL11.glDisable(GL11.GL_POLYGON_OFFSET_LINE);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_STENCIL_TEST);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_DONT_CARE);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glPopAttrib();
    }

    public static void drawLines(AxisAlignedBB bb) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.startDrawing(2);
        worldRenderer.addVertex(bb.minX, bb.minY, bb.minZ);
        worldRenderer.addVertex(bb.minX, bb.maxY, bb.maxZ);
        tessellator.draw();
        worldRenderer.startDrawing(2);
        worldRenderer.addVertex(bb.maxX, bb.minY, bb.minZ);
        worldRenderer.addVertex(bb.minX, bb.maxY, bb.maxZ);
        tessellator.draw();
        worldRenderer.startDrawing(2);
        worldRenderer.addVertex(bb.maxX, bb.minY, bb.maxZ);
        worldRenderer.addVertex(bb.minX, bb.maxY, bb.maxZ);
        tessellator.draw();
        worldRenderer.startDrawing(2);
        worldRenderer.addVertex(bb.maxX, bb.minY, bb.minZ);
        worldRenderer.addVertex(bb.minX, bb.maxY, bb.minZ);
        tessellator.draw();
        worldRenderer.startDrawing(2);
        worldRenderer.addVertex(bb.maxX, bb.minY, bb.minZ);
        worldRenderer.addVertex(bb.minX, bb.minY, bb.maxZ);
        tessellator.draw();
        worldRenderer.startDrawing(2);
        worldRenderer.addVertex(bb.maxX, bb.maxY, bb.minZ);
        worldRenderer.addVertex(bb.minX, bb.maxY, bb.maxZ);
        tessellator.draw();
    }

    public static void drawFilledBox(AxisAlignedBB bb) {
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(bb.minX, bb.minY, bb.minZ);
        worldRenderer.addVertex(bb.minX, bb.maxY, bb.minZ);
        worldRenderer.addVertex(bb.maxX, bb.minY, bb.minZ);
        worldRenderer.addVertex(bb.maxX, bb.maxY, bb.minZ);
        worldRenderer.addVertex(bb.maxX, bb.minY, bb.maxZ);
        worldRenderer.addVertex(bb.maxX, bb.maxY, bb.maxZ);
        worldRenderer.addVertex(bb.minX, bb.minY, bb.maxZ);
        worldRenderer.addVertex(bb.minX, bb.maxY, bb.maxZ);
        tessellator.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(bb.maxX, bb.maxY, bb.minZ);
        worldRenderer.addVertex(bb.maxX, bb.minY, bb.minZ);
        worldRenderer.addVertex(bb.minX, bb.maxY, bb.minZ);
        worldRenderer.addVertex(bb.minX, bb.minY, bb.minZ);
        worldRenderer.addVertex(bb.minX, bb.maxY, bb.maxZ);
        worldRenderer.addVertex(bb.minX, bb.minY, bb.maxZ);
        worldRenderer.addVertex(bb.maxX, bb.maxY, bb.maxZ);
        worldRenderer.addVertex(bb.maxX, bb.minY, bb.maxZ);
        tessellator.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(bb.minX, bb.maxY, bb.minZ);
        worldRenderer.addVertex(bb.maxX, bb.maxY, bb.minZ);
        worldRenderer.addVertex(bb.maxX, bb.maxY, bb.maxZ);
        worldRenderer.addVertex(bb.minX, bb.maxY, bb.maxZ);
        worldRenderer.addVertex(bb.minX, bb.maxY, bb.minZ);
        worldRenderer.addVertex(bb.minX, bb.maxY, bb.maxZ);
        worldRenderer.addVertex(bb.maxX, bb.maxY, bb.maxZ);
        worldRenderer.addVertex(bb.maxX, bb.maxY, bb.minZ);
        tessellator.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(bb.minX, bb.minY, bb.minZ);
        worldRenderer.addVertex(bb.maxX, bb.minY, bb.minZ);
        worldRenderer.addVertex(bb.maxX, bb.minY, bb.maxZ);
        worldRenderer.addVertex(bb.minX, bb.minY, bb.maxZ);
        worldRenderer.addVertex(bb.minX, bb.minY, bb.minZ);
        worldRenderer.addVertex(bb.minX, bb.minY, bb.maxZ);
        worldRenderer.addVertex(bb.maxX, bb.minY, bb.maxZ);
        worldRenderer.addVertex(bb.maxX, bb.minY, bb.minZ);
        tessellator.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(bb.minX, bb.minY, bb.minZ);
        worldRenderer.addVertex(bb.minX, bb.maxY, bb.minZ);
        worldRenderer.addVertex(bb.minX, bb.minY, bb.maxZ);
        worldRenderer.addVertex(bb.minX, bb.maxY, bb.maxZ);
        worldRenderer.addVertex(bb.maxX, bb.minY, bb.maxZ);
        worldRenderer.addVertex(bb.maxX, bb.maxY, bb.maxZ);
        worldRenderer.addVertex(bb.maxX, bb.minY, bb.minZ);
        worldRenderer.addVertex(bb.maxX, bb.maxY, bb.minZ);
        tessellator.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(bb.minX, bb.maxY, bb.maxZ);
        worldRenderer.addVertex(bb.minX, bb.minY, bb.maxZ);
        worldRenderer.addVertex(bb.minX, bb.maxY, bb.minZ);
        worldRenderer.addVertex(bb.minX, bb.minY, bb.minZ);
        worldRenderer.addVertex(bb.maxX, bb.maxY, bb.minZ);
        worldRenderer.addVertex(bb.maxX, bb.minY, bb.minZ);
        worldRenderer.addVertex(bb.maxX, bb.maxY, bb.maxZ);
        worldRenderer.addVertex(bb.maxX, bb.minY, bb.maxZ);
        tessellator.draw();
    }

    public static void drawRect(double left, double top, double right, double bottom, int color) {
        float alpha = (float) (color >> 24 & 255) / 255.0F;
        float red = (float) (color >> 16 & 255) / 255.0F;
        float green = (float) (color >> 8 & 255) / 255.0F;
        float blue = (float) (color & 255) / 255.0F;
        Tessellator var9 = Tessellator.getInstance();
        WorldRenderer var10 = var9.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.func_179090_x();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(red, green, blue, alpha);
        var10.startDrawingQuads();
        var10.addVertex(left, bottom, 0.0D);
        var10.addVertex(right, bottom, 0.0D);
        var10.addVertex(right, top, 0.0D);
        var10.addVertex(left, top, 0.0D);
        var9.draw();
        GlStateManager.func_179098_w();
        GlStateManager.disableBlend();
    }

    public static void drawGradientRect(double left, double top, double right, double bottom, int startColor, int endColor) {
        float var7 = (float) (startColor >> 24 & 255) / 255.0F;
        float var8 = (float) (startColor >> 16 & 255) / 255.0F;
        float var9 = (float) (startColor >> 8 & 255) / 255.0F;
        float var10 = (float) (startColor & 255) / 255.0F;
        float var11 = (float) (endColor >> 24 & 255) / 255.0F;
        float var12 = (float) (endColor >> 16 & 255) / 255.0F;
        float var13 = (float) (endColor >> 8 & 255) / 255.0F;
        float var14 = (float) (endColor & 255) / 255.0F;
        GlStateManager.func_179090_x();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        Tessellator var15 = Tessellator.getInstance();
        WorldRenderer var16 = var15.getWorldRenderer();
        var16.startDrawingQuads();
        var16.func_178960_a(var8, var9, var10, var7);
        var16.addVertex(right, top, 0);
        var16.addVertex(left, top, 0);
        var16.func_178960_a(var12, var13, var14, var11);
        var16.addVertex(left, bottom, 0);
        var16.addVertex(right, bottom, 0);
        var15.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.func_179098_w();
    }

    public static void drawBorderedRect(double left, double top, double right, double bottom, float borderWidth, int borderColor, int color) {
        float alpha = (borderColor >> 24 & 0xFF) / 255.0f;
        float red = (borderColor >> 16 & 0xFF) / 255.0f;
        float green = (borderColor >> 8 & 0xFF) / 255.0f;
        float blue = (borderColor & 0xFF) / 255.0f;
        GlStateManager.pushMatrix();
        drawRect(left, top, right, bottom, color);
        GlStateManager.enableBlend();
        GlStateManager.func_179090_x();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(red, green, blue, alpha);

        if (borderWidth == 1.0F) {
            GL11.glEnable(GL11.GL_LINE_SMOOTH);
        }

        GL11.glLineWidth(borderWidth);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.startDrawing(1);
        worldRenderer.addVertex(left, top, 0.0F);
        worldRenderer.addVertex(left, bottom, 0.0F);
        worldRenderer.addVertex(right, bottom, 0.0F);
        worldRenderer.addVertex(right, top, 0.0F);
        worldRenderer.addVertex(left, top, 0.0F);
        worldRenderer.addVertex(right, top, 0.0F);
        worldRenderer.addVertex(left, bottom, 0.0F);
        worldRenderer.addVertex(right, bottom, 0.0F);
        tessellator.draw();
        GL11.glLineWidth(2.0F);

        if (borderWidth == 1.0F) {
            GL11.glDisable(GL11.GL_LINE_SMOOTH);
        }

        GlStateManager.func_179098_w();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void drawBorderedRect(double left, double top, double right, double bottom, int borderColor, int color) {
        drawBorderedRect(left, top, right, bottom, 1.0F, borderColor, color);
    }

    public static void drawBorderedGradientRect(double left, double top, double right, double bottom, float borderWidth, int borderColor, int startColor, int endColor) {
        float alpha = (borderColor >> 24 & 0xFF) / 255.0f;
        float red = (borderColor >> 16 & 0xFF) / 255.0f;
        float green = (borderColor >> 8 & 0xFF) / 255.0f;
        float blue = (borderColor & 0xFF) / 255.0f;
        GlStateManager.pushMatrix();
        drawGradientRect(left, top, right, bottom, startColor, endColor);
        GlStateManager.enableBlend();
        GlStateManager.func_179090_x();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(red, green, blue, alpha);

        if (borderWidth == 1.0F) {
            GL11.glEnable(GL11.GL_LINE_SMOOTH);
        }

        GL11.glLineWidth(borderWidth);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.startDrawing(1);
        worldRenderer.addVertex(left, top, 0.0F);
        worldRenderer.addVertex(left, bottom, 0.0F);
        worldRenderer.addVertex(right, bottom, 0.0F);
        worldRenderer.addVertex(right, top, 0.0F);
        worldRenderer.addVertex(left, top, 0.0F);
        worldRenderer.addVertex(right, top, 0.0F);
        worldRenderer.addVertex(left, bottom, 0.0F);
        worldRenderer.addVertex(right, bottom, 0.0F);
        tessellator.draw();
        GL11.glLineWidth(2.0F);

        if (borderWidth == 1.0F) {
            GL11.glDisable(GL11.GL_LINE_SMOOTH);
        }

        GlStateManager.func_179098_w();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void drawBorderedGradientRect(double left, double top, double right, double bottom, int borderColor, int startColor, int endColor) {
        drawBorderedGradientRect(left, top, right, bottom, 1.0F, borderColor, startColor, endColor);
    }

    public static ScaledResolution newScaledResolution() {
        return new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
    }
}
