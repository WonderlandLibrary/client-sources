/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.stream.Collectors;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import ru.govno.client.module.modules.PointTrace;
import ru.govno.client.newfont.CFontRenderer;
import ru.govno.client.newfont.Fonts;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Render.ColorUtils;

public class PointRender {
    private static final ResourceLocation TEXTURE = new ResourceLocation("vegaline/system/points/pointsmark.png");
    private static final ResourceLocation TEXTURE2 = new ResourceLocation("vegaline/system/points/pointsmark2.png");
    private final IntBuffer viewport = GLAllocation.createDirectIntBuffer(16);
    private final FloatBuffer modelview = GLAllocation.createDirectFloatBuffer(16);
    private final FloatBuffer projection = GLAllocation.createDirectFloatBuffer(16);
    private final FloatBuffer vector = GLAllocation.createDirectFloatBuffer(4);

    public void render2D() {
        this.renderVoid();
    }

    public void renderPoints() {
        GL11.glPushMatrix();
        float partialTicks = Minecraft.getMinecraft().getRenderPartialTicks();
        int scaleFactor = ScaledResolution.getScaleFactor();
        double scaling = (double)scaleFactor / Math.pow(scaleFactor, 2.0);
        GL11.glScaled(scaling, scaling, scaling);
        float scale = 1.0f;
        float upscale = 1.0f / scale;
        RenderManager renderMng = Minecraft.getMinecraft().getRenderManager();
        EntityRenderer entityRenderer = Minecraft.getMinecraft().entityRenderer;
        for (PointTrace points : PointTrace.getPointList().stream().filter(point -> point.getServerName().equalsIgnoreCase(Minecraft.getMinecraft().isSingleplayer() || Minecraft.getMinecraft().getCurrentServerData() == null ? "SinglePlayer" : Minecraft.getMinecraft().getCurrentServerData().serverIP)).collect(Collectors.toList())) {
            boolean isDeathPoint = points.getName().startsWith("Death");
            double px = PointTrace.getX(points);
            double py = PointTrace.getY(points);
            double pz = PointTrace.getZ(points);
            if (Minecraft.player.dimension == -1 && PointTrace.getDemension(points) != -1) {
                px = PointTrace.getX(points) / 8.0;
                py = PointTrace.getY(points);
                pz = PointTrace.getZ(points) / 8.0;
            } else if (Minecraft.player.dimension != -1 && PointTrace.getDemension(points) == -1) {
                px = PointTrace.getX(points) * 8.0;
                py = PointTrace.getY(points);
                pz = PointTrace.getZ(points) * 8.0;
            }
            float pTicks = Minecraft.getMinecraft().getRenderPartialTicks();
            double xposme = Minecraft.player.lastTickPosX + (Minecraft.player.posX - Minecraft.player.lastTickPosX) * (double)pTicks;
            double yposme = Minecraft.player.lastTickPosY + (Minecraft.player.posY - Minecraft.player.lastTickPosY) * (double)pTicks;
            double zposme = Minecraft.player.lastTickPosZ + (Minecraft.player.posZ - Minecraft.player.lastTickPosZ) * (double)pTicks;
            double x = MathUtils.clamp(px, xposme - 100.0, xposme + 100.0);
            double y = MathUtils.clamp(py, yposme - 400.0, yposme + 400.0);
            double z = MathUtils.clamp(pz, zposme - 100.0, zposme + 100.0);
            AxisAlignedBB aabb = new AxisAlignedBB(x, y, z, x, y + 0.3, z);
            x = px;
            y = py;
            z = pz;
            Vector3d[] vectors = new Vector3d[]{new Vector3d(aabb.minX, aabb.minY, aabb.minZ), new Vector3d(aabb.minX, aabb.maxY, aabb.minZ), new Vector3d(aabb.maxX, aabb.minY, aabb.minZ), new Vector3d(aabb.maxX, aabb.maxY, aabb.minZ), new Vector3d(aabb.minX, aabb.minY, aabb.maxZ), new Vector3d(aabb.minX, aabb.maxY, aabb.maxZ), new Vector3d(aabb.maxX, aabb.minY, aabb.maxZ), new Vector3d(aabb.maxX, aabb.maxY, aabb.maxZ)};
            entityRenderer.setupCameraTransform(partialTicks, 0);
            Vector4d position = null;
            Vector3d[] var32 = vectors;
            int var33 = vectors.length;
            for (int var34 = 0; var34 < var33; ++var34) {
                Vector3d vector = var32[var34];
                vector = this.project2D(scaleFactor, vector.x - RenderManager.viewerPosX, vector.y - RenderManager.viewerPosY, vector.z - RenderManager.viewerPosZ);
                if (vector == null || !(vector.z >= 0.0) || !(vector.z < 1.0)) continue;
                if (position == null) {
                    position = new Vector4d(vector.x, vector.y, vector.z, 0.0);
                }
                position.x = Math.min(vector.x, position.x);
                position.y = Math.min(vector.y, position.y);
                position.z = Math.max(vector.x, position.z);
                position.w = Math.max(vector.y, position.w);
            }
            if (position == null) continue;
            entityRenderer.setupOverlayRendering();
            double posX = position.x;
            double posY = position.y;
            double endPosX = position.z;
            double endPosY = position.w;
            CFontRenderer font = Fonts.mntsb_10;
            int timeInterval = isDeathPoint ? 350 : 700;
            float pcTime = (float)((System.currentTimeMillis() + (long)points.getIndex() * 350L) % (long)timeInterval) / (float)timeInterval;
            pcTime = ((double)pcTime > 0.5 ? 1.0f - pcTime : pcTime) * 2.0f;
            pcTime *= pcTime;
            float yExtend = -(isDeathPoint ? 2.0f : 5.0f) * pcTime;
            float xp = (float)(posX + (endPosX - posX) / 2.0);
            float yp = (float)(posY - 5.0) + yExtend;
            GlStateManager.enableBlend();
            Minecraft.getMinecraft().getTextureManager().bindTexture(isDeathPoint ? TEXTURE2 : TEXTURE);
            int markCol = ColorUtils.swapAlpha(PointTrace.getDemension(points) == -1 ? ColorUtils.getColor(255, 40, 95) : (PointTrace.getDemension(points) == 1 ? ColorUtils.getColor(255, 255, 95) : ColorUtils.getColor(40, 255, 95)), 155.0f);
            double texW = isDeathPoint ? 24.0 : 16.0;
            double texH = 24.0;
            double texX = (double)xp - texW / 2.0;
            double texY = (double)yp - texH;
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            bufferbuilder.begin(9, DefaultVertexFormats.POSITION_TEX_COLOR);
            bufferbuilder.pos(texX, texY + texH).tex(0.0, 1.0).color(markCol).endVertex();
            bufferbuilder.pos(texX + texW, texY + texH).tex(1.0, 1.0).color(markCol).endVertex();
            bufferbuilder.pos(texX + texW, texY).tex(1.0, 0.0).color(markCol).endVertex();
            bufferbuilder.pos(texX, texY).tex(0.0, 0.0).color(markCol).endVertex();
            GL11.glShadeModel(7425);
            GL11.glEnable(3553);
            tessellator.draw();
            String coords = (int)x + " " + (int)y + " " + (int)z;
            String dst = String.format("%.1f", Float.valueOf(Minecraft.player.getSmoothDistanceToCoord((float)x, (float)Minecraft.player.posY, (float)z))) + "m";
            if (isDeathPoint) {
                float name$dstWidth = (float)font.getStringWidth(points.getName() + " " + dst) + 2.0f;
                font.drawStringWithOutline(points.getName() + " " + dst, texX + texW / 2.0 - (double)(name$dstWidth / 2.0f), texY + texH - (double)yExtend + 1.0, ColorUtils.getOverallColorFrom(markCol, -1));
                font.drawStringWithOutline(coords, texX + texW / 2.0 - (double)((float)font.getStringWidth(coords) / 2.0f), texY - (double)yExtend + texH + 6.0, ColorUtils.getOverallColorFrom(markCol, -1));
                continue;
            }
            font.drawStringWithOutline(points.getName(), texX + texW - 4.0 + (double)(yExtend / 3.0f), texY + texH - 4.0 - (double)(yExtend / 2.0f), ColorUtils.getOverallColorFrom(markCol, -1));
            font.drawStringWithOutline(dst, texX + 4.0 - (double)font.getStringWidth(dst) - (double)(yExtend / 3.0f), texY + texH - 4.0 - (double)(yExtend / 2.0f), ColorUtils.getOverallColorFrom(markCol, -1));
            font.drawStringWithOutline(coords, texX - (double)((float)font.getStringWidth(coords) / 2.0f) + texW / 2.0, texY + texH + 2.0 - (double)(yExtend / 2.0f), ColorUtils.getOverallColorFrom(markCol, -1));
        }
        GL11.glEnable(2929);
        GlStateManager.enableBlend();
        entityRenderer.setupOverlayRendering();
        GL11.glPopMatrix();
    }

    void renderVoid() {
        this.renderPoints();
    }

    private Vector3d project2D(int scaleFactor, double x, double y, double z) {
        GL11.glGetFloat(2982, this.modelview);
        GL11.glGetFloat(2983, this.projection);
        GL11.glGetInteger(2978, this.viewport);
        return GLU.gluProject((float)x, (float)y, (float)z, this.modelview, this.projection, this.viewport, this.vector) ? new Vector3d(this.vector.get(0) / (float)scaleFactor, ((float)Display.getHeight() - this.vector.get(1)) / (float)scaleFactor, this.vector.get(2)) : null;
    }
}

