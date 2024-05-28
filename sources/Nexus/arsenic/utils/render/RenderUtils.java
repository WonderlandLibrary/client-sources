package arsenic.utils.render;

import arsenic.injection.accessor.IMixinMinecraft;
import arsenic.main.Nexus;
import arsenic.utils.java.UtilityClass;
import arsenic.utils.minecraft.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static net.minecraft.client.renderer.GlStateManager.color;
import static org.lwjgl.opengl.GL11.*;

public class RenderUtils extends UtilityClass {

    public static void setColor(final int color) {
        final float a = ((color >> 24) & 0xFF) / 255.0f;
        final float r = ((color >> 16) & 0xFF) / 255.0f;
        final float g = ((color >> 8) & 0xFF) / 255.0f;
        final float b = (color & 0xFF) / 255.0f;
        glColor4f(r, g, b, a);
    }

    public static void glVertex3D2(Vec3 vector3d) {
        GL11.glVertex3d(vector3d.xCoord, vector3d.yCoord, vector3d.zCoord);
    }

    public static Vec3 getRenderPos(double x, double y, double z) {

        x -= mc.getRenderManager().renderPosX;
        y -= mc.getRenderManager().renderPosY;
        z -= mc.getRenderManager().renderPosZ;

        return new Vec3(x, y, z);
    }
    //note skidded tb removed for testing purposes
    public static void BlockESP(BlockPos bp, int color, boolean shade) {
        if (bp != null) {
            double x = (double) bp.getX() - mc.getRenderManager().viewerPosX;
            double y = (double) bp.getY() - mc.getRenderManager().viewerPosY;
            double z = (double) bp.getZ() - mc.getRenderManager().viewerPosZ;
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3042);
            GL11.glLineWidth(2.0F);
            GL11.glDisable(3553);
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            float a = (float) ((color >> 24) & 255) / 255.0F;
            float r = (float) ((color >> 16) & 255) / 255.0F;
            float g = (float) ((color >> 8) & 255) / 255.0F;
            float b = (float) (color & 255) / 255.0F;
            GL11.glColor4d(r, g, b, a);
            RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D));
            if (shade)
                dbb(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D), r, g, b);

            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GL11.glDepthMask(true);
            GL11.glDisable(3042);
        }
    }

    public static void dbb(AxisAlignedBB abb, float r, float g, float b) {
        float a = 0.25F;
        Tessellator ts = Tessellator.getInstance();
        WorldRenderer vb = ts.getWorldRenderer();
        vb.begin(7, DefaultVertexFormats.POSITION_COLOR);
        vb.pos(abb.minX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
        vb.pos(abb.minX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
        vb.pos(abb.maxX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
        vb.pos(abb.maxX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
        vb.pos(abb.maxX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
        vb.pos(abb.maxX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
        vb.pos(abb.minX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
        vb.pos(abb.minX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_COLOR);
        vb.pos(abb.maxX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
        vb.pos(abb.maxX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
        vb.pos(abb.minX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
        vb.pos(abb.minX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
        vb.pos(abb.minX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
        vb.pos(abb.minX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
        vb.pos(abb.maxX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
        vb.pos(abb.maxX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_COLOR);
        vb.pos(abb.minX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
        vb.pos(abb.maxX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
        vb.pos(abb.maxX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
        vb.pos(abb.minX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
        vb.pos(abb.minX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
        vb.pos(abb.minX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
        vb.pos(abb.maxX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
        vb.pos(abb.maxX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_COLOR);
        vb.pos(abb.minX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
        vb.pos(abb.maxX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
        vb.pos(abb.maxX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
        vb.pos(abb.minX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
        vb.pos(abb.minX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
        vb.pos(abb.minX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
        vb.pos(abb.maxX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
        vb.pos(abb.maxX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_COLOR);
        vb.pos(abb.minX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
        vb.pos(abb.minX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
        vb.pos(abb.minX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
        vb.pos(abb.minX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
        vb.pos(abb.maxX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
        vb.pos(abb.maxX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
        vb.pos(abb.maxX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
        vb.pos(abb.maxX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_COLOR);
        vb.pos(abb.minX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
        vb.pos(abb.minX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
        vb.pos(abb.minX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
        vb.pos(abb.minX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
        vb.pos(abb.maxX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
        vb.pos(abb.maxX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
        vb.pos(abb.maxX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
        vb.pos(abb.maxX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
        ts.draw();
    }
    private void drawShadedBoundingBox(AxisAlignedBB abb, int r, int g, int b) {
        int a = 63;
        Tessellator ts = Tessellator.getInstance();
        WorldRenderer vb = ts.getWorldRenderer();
        vb.begin(7, DefaultVertexFormats.POSITION_COLOR);
        vb.pos(abb.minX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
        vb.pos(abb.minX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
        vb.pos(abb.maxX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
        vb.pos(abb.maxX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
        vb.pos(abb.maxX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
        vb.pos(abb.maxX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
        vb.pos(abb.minX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
        vb.pos(abb.minX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_COLOR);
        vb.pos(abb.maxX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
        vb.pos(abb.maxX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
        vb.pos(abb.minX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
        vb.pos(abb.minX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
        vb.pos(abb.minX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
        vb.pos(abb.minX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
        vb.pos(abb.maxX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
        vb.pos(abb.maxX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_COLOR);
        vb.pos(abb.minX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
        vb.pos(abb.maxX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
        vb.pos(abb.maxX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
        vb.pos(abb.minX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
        vb.pos(abb.minX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
        vb.pos(abb.minX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
        vb.pos(abb.maxX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
        vb.pos(abb.maxX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_COLOR);
        vb.pos(abb.minX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
        vb.pos(abb.maxX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
        vb.pos(abb.maxX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
        vb.pos(abb.minX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
        vb.pos(abb.minX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
        vb.pos(abb.minX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
        vb.pos(abb.maxX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
        vb.pos(abb.maxX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_COLOR);
        vb.pos(abb.minX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
        vb.pos(abb.minX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
        vb.pos(abb.minX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
        vb.pos(abb.minX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
        vb.pos(abb.maxX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
        vb.pos(abb.maxX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
        vb.pos(abb.maxX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
        vb.pos(abb.maxX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_COLOR);
        vb.pos(abb.minX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
        vb.pos(abb.minX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
        vb.pos(abb.minX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
        vb.pos(abb.minX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
        vb.pos(abb.maxX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
        vb.pos(abb.maxX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
        vb.pos(abb.maxX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
        vb.pos(abb.maxX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
        ts.draw();
    }
    public static void drawBoundingBox(final AxisAlignedBB aa) {

        glBegin(GL_QUADS);
        glVertex3D2(getRenderPos(aa.minX, aa.minY, aa.minZ));
        glVertex3D2(getRenderPos(aa.minX, aa.maxY, aa.minZ));
        glVertex3D2(getRenderPos(aa.maxX, aa.minY, aa.minZ));
        glVertex3D2(getRenderPos(aa.maxX, aa.maxY, aa.minZ));
        glVertex3D2(getRenderPos(aa.maxX, aa.minY, aa.maxZ));
        glVertex3D2(getRenderPos(aa.maxX, aa.maxY, aa.maxZ));
        glVertex3D2(getRenderPos(aa.minX, aa.minY, aa.maxZ));
        glVertex3D2(getRenderPos(aa.minX, aa.maxY, aa.maxZ));
        GL11.glEnd();

        glBegin(GL_QUADS);
        glVertex3D2(getRenderPos(aa.maxX, aa.maxY, aa.minZ));
        glVertex3D2(getRenderPos(aa.maxX, aa.minY, aa.minZ));
        glVertex3D2(getRenderPos(aa.minX, aa.maxY, aa.minZ));
        glVertex3D2(getRenderPos(aa.minX, aa.minY, aa.minZ));
        glVertex3D2(getRenderPos(aa.minX, aa.maxY, aa.maxZ));
        glVertex3D2(getRenderPos(aa.minX, aa.minY, aa.maxZ));
        glVertex3D2(getRenderPos(aa.maxX, aa.maxY, aa.maxZ));
        glVertex3D2(getRenderPos(aa.maxX, aa.minY, aa.maxZ));
        GL11.glEnd();

        glBegin(GL_QUADS);
        glVertex3D2(getRenderPos(aa.minX, aa.maxY, aa.minZ));
        glVertex3D2(getRenderPos(aa.maxX, aa.maxY, aa.minZ));
        glVertex3D2(getRenderPos(aa.maxX, aa.maxY, aa.maxZ));
        glVertex3D2(getRenderPos(aa.minX, aa.maxY, aa.maxZ));
        glVertex3D2(getRenderPos(aa.minX, aa.maxY, aa.minZ));
        glVertex3D2(getRenderPos(aa.minX, aa.maxY, aa.maxZ));
        glVertex3D2(getRenderPos(aa.maxX, aa.maxY, aa.maxZ));
        glVertex3D2(getRenderPos(aa.maxX, aa.maxY, aa.minZ));
        GL11.glEnd();

        glBegin(GL_QUADS);
        glVertex3D2(getRenderPos(aa.minX, aa.minY, aa.minZ));
        glVertex3D2(getRenderPos(aa.maxX, aa.minY, aa.minZ));
        glVertex3D2(getRenderPos(aa.maxX, aa.minY, aa.maxZ));
        glVertex3D2(getRenderPos(aa.minX, aa.minY, aa.maxZ));
        glVertex3D2(getRenderPos(aa.minX, aa.minY, aa.minZ));
        glVertex3D2(getRenderPos(aa.minX, aa.minY, aa.maxZ));
        glVertex3D2(getRenderPos(aa.maxX, aa.minY, aa.maxZ));
        glVertex3D2(getRenderPos(aa.maxX, aa.minY, aa.minZ));
        GL11.glEnd();

        glBegin(GL_QUADS);
        glVertex3D2(getRenderPos(aa.minX, aa.minY, aa.minZ));
        glVertex3D2(getRenderPos(aa.minX, aa.maxY, aa.minZ));
        glVertex3D2(getRenderPos(aa.minX, aa.minY, aa.maxZ));
        glVertex3D2(getRenderPos(aa.minX, aa.maxY, aa.maxZ));
        glVertex3D2(getRenderPos(aa.maxX, aa.minY, aa.maxZ));
        glVertex3D2(getRenderPos(aa.maxX, aa.maxY, aa.maxZ));
        glVertex3D2(getRenderPos(aa.maxX, aa.minY, aa.minZ));
        glVertex3D2(getRenderPos(aa.maxX, aa.maxY, aa.minZ));
        GL11.glEnd();

        glBegin(GL_QUADS);
        glVertex3D2(getRenderPos(aa.minX, aa.maxY, aa.maxZ));
        glVertex3D2(getRenderPos(aa.minX, aa.minY, aa.maxZ));
        glVertex3D2(getRenderPos(aa.minX, aa.maxY, aa.minZ));
        glVertex3D2(getRenderPos(aa.minX, aa.minY, aa.minZ));
        glVertex3D2(getRenderPos(aa.maxX, aa.maxY, aa.minZ));
        glVertex3D2(getRenderPos(aa.maxX, aa.minY, aa.minZ));
        glVertex3D2(getRenderPos(aa.maxX, aa.maxY, aa.maxZ));
        glVertex3D2(getRenderPos(aa.maxX, aa.minY, aa.maxZ));
        GL11.glEnd();
    }

    public static void resetColorText() {
        color(1f, 1f, 1f, 1f);
    }

    public static void resetColor() {
        glColor4f(1f, 1f, 1f, 1f);
    }

    public static void color2(int color, float alpha) {
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;
        GlStateManager.color(r, g, b, alpha);
    }

    // Colors the next texture without a specified alpha value
    public static void color2(int color3) {
        color2(color3, (float) (color3 >> 24 & 255) / 255.0F);
    }

    public static double ticks = 0;
    public static long lastFrame = 0;

    public static void drawCircle(Entity entity, float partialTicks, double rad, int colored, float alpha) {
        /*Got this from the people i made the Gui for*/
        ticks += .004 * (System.currentTimeMillis() - lastFrame);

        lastFrame = System.currentTimeMillis();

        glPushMatrix();
        glDisable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        GlStateManager.color(1, 1, 1, 1);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glDisable(GL_DEPTH_TEST);
        glDepthMask(false);
        glShadeModel(GL_SMOOTH);
        GlStateManager.disableCull();
        final double x = MathUtils.interpolate(entity.lastTickPosX, entity.posX, ((IMixinMinecraft) mc).getTimer().renderPartialTicks) - mc.getRenderManager().viewerPosX;
        final double y = MathUtils.interpolate(entity.lastTickPosY, entity.posY, ((IMixinMinecraft) mc).getTimer().renderPartialTicks) - mc.getRenderManager().viewerPosY + Math.sin(ticks) + 1;
        final double z = MathUtils.interpolate(entity.lastTickPosZ, entity.posZ, ((IMixinMinecraft) mc).getTimer().renderPartialTicks) - mc.getRenderManager().viewerPosZ;

        glBegin(GL_TRIANGLE_STRIP);

        for (float i = 0; i < (Math.PI * 2); i += (Math.PI * 2) / 64.F) {

            final double vecX = x + rad * Math.cos(i);
            final double vecZ = z + rad * Math.sin(i);

            color2(colored, 0);

            glVertex3d(vecX, y - Math.sin(ticks + 1) / 2.7f, vecZ);

            color2(colored, .52f * alpha);


            glVertex3d(vecX, y, vecZ);
        }

        glEnd();


        glEnable(GL_LINE_SMOOTH);
        glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
        glLineWidth(1.5f);
        glBegin(GL_LINE_STRIP);
        GlStateManager.color(1, 1, 1, 1);
        color2(colored, .5f * alpha);
        for (int i = 0; i <= 180; i++) {
            glVertex3d(x - Math.sin(i * MathUtils.PI2 / 90) * rad, y, z + Math.cos(i * MathUtils.PI2 / 90) * rad);
        }
        glEnd();

        glShadeModel(GL_FLAT);
        glDepthMask(true);
        glEnable(GL_DEPTH_TEST);
        GlStateManager.enableCull();
        glDisable(GL_LINE_SMOOTH);
        glEnable(GL_TEXTURE_2D);
        glPopMatrix();
        glColor4f(1f, 1f, 1f, 1f);
    }

    public static ResourceLocation getResourcePath(String s) {
        InputStream inputStream = Nexus.class.getResourceAsStream(s);
        BufferedImage bf;
        try {
            assert inputStream != null;
            bf = ImageIO.read(inputStream);
            return Minecraft.getMinecraft().renderEngine.getDynamicTextureLocation("Arsenic", new DynamicTexture(bf));
        } catch (IOException | IllegalArgumentException | NullPointerException e) {
            e.printStackTrace();
            return new ResourceLocation("null");
        }
    }

    public static Color interpolateColoursColor(Color a, Color b, float f) {
        float rf = 1 - f;
        int red = (int) (a.getRed() * rf + b.getRed() * f);
        int green = (int) (a.getGreen() * rf + b.getGreen() * f);
        int blue = (int) (a.getBlue() * rf + b.getBlue() * f);
        int alpha = (int) (a.getAlpha() * rf + b.getAlpha() * f);
        return new Color(red, green, blue, alpha);
    }

    public static int interpolateColours(Color a, Color b, float f) {
        return interpolateColoursColor(a, b, f).getRGB();
    }

    public static int interpolateColoursInt(int a, int b, float f) {
        return interpolateColoursColor(new Color(a), new Color(b), f).getRGB();
    }
    public static void drawBoxAroundEntity(Entity e, int type, double expand, double shift, int color) {
        if (e instanceof EntityLivingBase) {
            double x = (e.lastTickPosX + ((e.posX - e.lastTickPosX) * (double) mc.timer.renderPartialTicks))
                    - mc.getRenderManager().viewerPosX;
            double y = (e.lastTickPosY + ((e.posY - e.lastTickPosY) * (double) mc.timer.renderPartialTicks))
                    - mc.getRenderManager().viewerPosY;
            double z = (e.lastTickPosZ + ((e.posZ - e.lastTickPosZ) * (double) mc.timer.renderPartialTicks))
                    - mc.getRenderManager().viewerPosZ;
            float d = (float) expand / 40.0F;

            GlStateManager.pushMatrix();
            if (type == 4) {
                EntityLivingBase en = (EntityLivingBase) e;
                double r = en.getHealth() / en.getMaxHealth();
                int b = (int) (74.0D * r);
                int hc = r < 0.3D ? Color.red.getRGB()
                        : (r < 0.5D ? Color.orange.getRGB()
                        : (r < 0.7D ? Color.yellow.getRGB() : Color.green.getRGB()));
                GL11.glTranslated(x, y - 0.2D, z);
                GL11.glRotated(-mc.getRenderManager().playerViewY, 0.0D, 1.0D, 0.0D);
                GlStateManager.disableDepth();
                GL11.glScalef(0.03F + d, 0.03F + d, 0.03F + d);
                int i = (int) (21.0D + (shift * 2.0D));
                net.minecraft.client.gui.Gui.drawRect(i, -1, i + 5, 75, Color.black.getRGB());
                net.minecraft.client.gui.Gui.drawRect(i + 1, b, i + 4, 74, Color.darkGray.getRGB());
                net.minecraft.client.gui.Gui.drawRect(i + 1, 0, i + 4, b, hc);
                GlStateManager.enableDepth();
            } else if (type == 5) {
                GL11.glTranslated(x, y - 0.2D, z);
                GL11.glRotated(-mc.getRenderManager().playerViewY, 0.0D, 1.0D, 0.0D);
                GlStateManager.disableDepth();
                GL11.glScalef(0.03F + d, 0.03F, 0.03F + d);
                int base = 1;
                d2p(0.0D, 95.0D, 10, 3, Color.black.getRGB());

                for (int i = 0; i < 6; ++i)
                    d2p(0.0D, 95 + (10 - i), 3, 4, Color.black.getRGB());

                for (int i = 0; i < 7; ++i)
                    d2p(0.0D, 95 + (10 - i), 2, 4, color);

                d2p(0.0D, 95.0D, 8, 3, color);
                GlStateManager.enableDepth();
            }

            GlStateManager.popMatrix();
        }
    }
    public static void d2p(double x, double y, int radius, int sides, int color) {
        float a = (float) ((color >> 24) & 255) / 255.0F;
        float r = (float) ((color >> 16) & 255) / 255.0F;
        float g = (float) ((color >> 8) & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(r, g, b, a);
        worldrenderer.begin(6, DefaultVertexFormats.POSITION);

        for (int i = 0; i < sides; ++i) {
            double angle = ((6.283185307179586D * (double) i) / (double) sides) + Math.toRadians(180.0D);
            worldrenderer.pos(x + (Math.sin(angle) * (double) radius), y + (Math.cos(angle) * (double) radius), 0.0D)
                    .endVertex();
        }

        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
}
