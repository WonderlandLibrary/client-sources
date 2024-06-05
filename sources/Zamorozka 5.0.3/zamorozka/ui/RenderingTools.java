package zamorozka.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

public class RenderingTools {
	
	static Minecraft MC = Minecraft.getMinecraft();
	
	 /**
     * Returns the current color of the cycling rainbow
     *
     * @return color
     */
    public static Color rainbow() {
        float hue = (System.currentTimeMillis() % (320 * 32)) / (320f * 32);
        return new Color(Color.HSBtoRGB(hue, 1, 1));
    }

    /**
     * Renders an item onto the GUI
     *
     * @param itemStack item stack to render
     * @param x         x position to render at
     * @param y         y position to render at
     */
    public static void renderItem(ItemStack itemStack, int x, int y) {
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_ONE, GL_ZERO);
        GlStateManager.enableDepth();
        RenderHelper.enableGUIStandardItemLighting();

        MC.getRenderItem().renderItemAndEffectIntoGUI(itemStack, x, y);
        MC.getRenderItem().renderItemOverlays(MC.fontRendererObj, itemStack, x, y);

        RenderHelper.disableStandardItemLighting();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.disableDepth();
    }

    public static void drawTracer(Vec3d pos, Color color) {
        Vec3d eyeVector = new Vec3d(0.0, 0.0, 70.0)
                .rotatePitch((float) (-Math.toRadians(MC.player.rotationPitch)))
                .rotateYaw((float) (-Math.toRadians(MC.player.rotationYaw)));

        prepare3d();
        GL11.glColor4f(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex3d(eyeVector.xCoord + MC.getRenderManager().viewerPosX, (double) MC.player.getEyeHeight() + eyeVector.yCoord + MC.getRenderManager().viewerPosY, eyeVector.zCoord + MC.getRenderManager().viewerPosZ);
        GL11.glVertex3d(pos.xCoord, pos.yCoord, pos.zCoord);
        GL11.glEnd();
        end3d();
    }

    public static void drawTracer(Entity entity, Color color) {
        Vec3d pos = getRenderPos(entity);

        Vec3d eyeVector = new Vec3d(0.0, 0.0, 70.0)
                .rotatePitch((float) (-Math.toRadians(MC.player.rotationPitch)))
                .rotateYaw((float) (-Math.toRadians(MC.player.rotationYaw)));

        GL11.glBlendFunc(GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glLineWidth(1);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glColor4f(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex3d(eyeVector.xCoord, (double) MC.player.getEyeHeight() + eyeVector.yCoord, eyeVector.zCoord);
        GL11.glVertex3d(pos.xCoord, pos.yCoord, pos.zCoord);
        GL11.glVertex3d(pos.xCoord, pos.yCoord, pos.zCoord);
        GL11.glVertex3d(pos.xCoord, pos.yCoord + entity.height, pos.zCoord);
        GL11.glEnd();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
        GlStateManager.resetColor();
    }

    public static Vec3d getRenderPos(Entity ent) {
        double x = ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * MC.timer.renderPartialTicks - MC.getRenderManager().viewerPosX;
        double y = ent.lastTickPosY + (ent.posY - ent.lastTickPosY) * MC.timer.renderPartialTicks - MC.getRenderManager().viewerPosY;
        double z = ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * MC.timer.renderPartialTicks - MC.getRenderManager().viewerPosZ;

        return new Vec3d(x, y, z);
    }

    public static void prepare3d() {
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_LINE_SMOOTH);
        glLineWidth(2);
        glDisable(GL_TEXTURE_2D);
        glEnable(GL_CULL_FACE);
        glDisable(GL_DEPTH_TEST);

        double renderPosX = MC.getRenderManager().viewerPosX;
        double renderPosY = MC.getRenderManager().viewerPosY;
        double renderPosZ = MC.getRenderManager().viewerPosZ;

        glPushMatrix();
        glTranslated(-renderPosX, -renderPosY, -renderPosZ);
        glColor4f(1, 1, 1, 1);
    }

    public static void end3d() {
        glColor4f(1, 1, 1, 1);
        glPopMatrix();

        glEnable(GL_DEPTH_TEST);
        glEnable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);
        glDisable(GL_LINE_SMOOTH);
    }

    public static AxisAlignedBB getBoundingBox(BlockPos pos) {
        return MC.world.getBlockState(pos).getBoundingBox(MC.world, pos).offset(pos);
    }
    
    public static void enableGL2D() {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glDepthMask(true);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
    }

    public static void disableGL2D() {
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }

    public static void drawCircle(float cx, float cy, float r, int num_segments, int c) {
        GL11.glPushMatrix();
        cx *= 2.0F;
        cy *= 2.0F;
        float f = (c >> 24 & 0xFF) / 255.0F;
        float f1 = (c >> 16 & 0xFF) / 255.0F;
        float f2 = (c >> 8 & 0xFF) / 255.0F;
        float f3 = (c & 0xFF) / 255.0F;
        float theta = (float) (6.2831852D / num_segments);
        float p = (float) Math.cos(theta);
        float s = (float) Math.sin(theta);
        float x = r *= 2.0F;
        float y = 0.0F;
        enableGL2D();
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        GL11.glColor4f(f1, f2, f3, f);
        GL11.glBegin(2);
        int ii = 0;
        while (ii < num_segments) {
            GL11.glVertex2f(x + cx, y + cy);
            float t = x;
            x = p * x - s * y;
            y = s * t + p * y;
            ii++;
        }
        GL11.glEnd();
        GL11.glScalef(2.0F, 2.0F, 2.0F);
        disableGL2D();
        GlStateManager.color(1, 1, 1, 1);
        GL11.glPopMatrix();
    }
}