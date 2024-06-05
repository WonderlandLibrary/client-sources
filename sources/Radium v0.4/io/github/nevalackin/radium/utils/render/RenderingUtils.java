package io.github.nevalackin.radium.utils.render;

import io.github.nevalackin.radium.utils.MathUtils;
import io.github.nevalackin.radium.utils.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.Display;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

public final class RenderingUtils {

    private static final Frustum FRUSTUM = new Frustum();
    private static final double DOUBLE_PI = Math.PI * 2;

    private static int lastScaledWidth;
    private static int lastScaledHeight;
    private static int lastGuiScale;
    private static ScaledResolution scaledResolution;

    private static int lastWidth;
    private static int lastHeight;
    private static LockedResolution lockedResolution;

    private RenderingUtils() {
    }

    public static boolean isBBInFrustum(AxisAlignedBB aabb) {
        EntityPlayerSP player = Wrapper.getPlayer();
        FRUSTUM.setPosition(player.posX, player.posY, player.posZ);
        return FRUSTUM.isBoundingBoxInFrustum(aabb);
    }


    public static LockedResolution getLockedResolution() {
        int width = Display.getWidth();
        int height = Display.getHeight();

        if (width != lastWidth ||
                height != lastHeight) {
            lastWidth = width;
            lastHeight = height;
            return lockedResolution = new LockedResolution(width / 2, height / 2);
        }

        return lockedResolution;
    }

    public static ScaledResolution getScaledResolution() {
        int displayWidth = Display.getWidth();
        int displayHeight = Display.getHeight();
        int guiScale = Wrapper.getGameSettings().guiScale;

        if (displayWidth != lastScaledWidth ||
                displayHeight != lastScaledHeight ||
                guiScale != lastGuiScale) {
            lastScaledWidth = displayWidth;
            lastScaledHeight = displayHeight;
            lastGuiScale = guiScale;
            return scaledResolution = new ScaledResolution(Wrapper.getMinecraft());
        }

        return scaledResolution;
    }

    public static int getColorFromPercentage(float percentage) {
        return Color.HSBtoRGB(percentage / 3, 1.0F, 1.0F);
    }

    public static int getRainbowFromEntity(Entity entity, int speed, boolean invert) {
        float time = ((System.currentTimeMillis() + (entity.ticksExisted * 100L)) % speed) / (float) speed;
        return Color.HSBtoRGB(invert ? 1.0F - time : time, 0.9F, 0.9F);
    }

    public static int getRainbow(int speed, int offset) {
        return Color.HSBtoRGB(((System.currentTimeMillis() + (offset * 100)) % speed) / (float) speed,
                0.55F, 0.9F);
    }

    public static void drawAndRotateArrow(float x, float y, float size, boolean rotate) {
        glPushMatrix();
        glTranslatef(x, y + (rotate ? size / 2 : 0), 1.0F);
        OGLUtils.startBlending();
        glEnable(GL_LINE_SMOOTH);
        glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
        glLineWidth(1.0F);
        if (rotate)
            glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
        glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        glDisable(GL_TEXTURE_2D);
        glBegin(GL_LINE_STRIP);
        glVertex2f(0, 0);
        glVertex2f(size / 2, size / 2);
        glVertex2f(size, 0);
        glEnd();
        glDisable(GL_LINE_SMOOTH);
        glEnable(GL_TEXTURE_2D);
        OGLUtils.endBlending();
        glPopMatrix();
    }

    public static double transition(double now, double desired, double speed) {
        final double dif = Math.abs(now - desired);

        final int fps = Minecraft.getDebugFPS();

        if (dif > 0) {
            double animationSpeed = MathUtils.roundToDecimalPlace(Math.min(
                    10.0D, Math.max(0.0625D, (144.0D / fps) * (dif / 10) * speed)), 0.0625D);

            if (dif != 0 && dif < animationSpeed)
                animationSpeed = dif;

            if (now < desired)
                return now + animationSpeed;
            else if (now > desired)
                return now - animationSpeed;
        }

        return now;
    }

    public static void color(int color) {
        glColor4ub(
                (byte) (color >> 16 & 0xFF),
                (byte) (color >> 8 & 0xFF),
                (byte) (color & 0xFF),
                (byte) (color >> 24 & 0xFF));
    }

    public static void drawLinesAroundPlayer(Entity entity,
                                             double radius,
                                             float partialTicks,
                                             int points,
                                             float width,
                                             int color) {
        glPushMatrix();
        glDisable(GL_TEXTURE_2D);
        glEnable(GL_LINE_SMOOTH);
        glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
        glDisable(GL_DEPTH_TEST);
        glLineWidth(width);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glDisable(GL_DEPTH_TEST);
        glBegin(GL_LINE_STRIP);
        final double x = RenderingUtils.interpolate(entity.prevPosX, entity.posX, partialTicks) - RenderManager.viewerPosX;
        final double y = RenderingUtils.interpolate(entity.prevPosY, entity.posY, partialTicks) - RenderManager.viewerPosY;
        final double z = RenderingUtils.interpolate(entity.prevPosZ, entity.posZ, partialTicks) - RenderManager.viewerPosZ;
        RenderingUtils.color(color);
        for (int i = 0; i <= points; i++)
            glVertex3d(
                    x + radius * Math.cos(i * DOUBLE_PI / points),
                    y,
                    z + radius * Math.sin(i * DOUBLE_PI / points));
        glEnd();
        glDepthMask(true);
        glDisable(GL_BLEND);
        glEnable(GL_DEPTH_TEST);
        glDisable(GL_LINE_SMOOTH);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_TEXTURE_2D);
        glPopMatrix();
    }

    public static double interpolate(double old,
                                     double now,
                                     float partialTicks) {
        return old + (now - old) * partialTicks;
    }

    public static float interpolate(float old,
                                    float now,
                                    float partialTicks) {
        return old + (now - old) * partialTicks;
    }

    public static void drawGuiBackground(int width, int height) {
        Gui.drawRect(0, 0, width, height, 0xFF282C34);
    }
}
