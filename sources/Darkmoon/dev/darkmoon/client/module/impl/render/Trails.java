package dev.darkmoon.client.module.impl.render;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.module.setting.impl.NumberSetting;
import dev.darkmoon.client.event.render.EventRender3D;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.utility.render.AntiAliasingUtility;
import dev.darkmoon.client.utility.render.ColorUtility;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.GL_GREATER;

@ModuleAnnotation(name = "Trails", category = Category.RENDER)
public class Trails extends Module {
    private final NumberSetting removeTicks = new NumberSetting("Delete through", 100, 100, 500, 1);
    public ArrayList<Point> points = new ArrayList<>();

    @EventTarget
    public void onRender(EventRender3D eventRender3D) {
        if (mc.gameSettings.thirdPersonView == 0 || !mc.player.isEntityAlive()) return;

        boolean old = mc.gameSettings.viewBobbing;
        mc.gameSettings.viewBobbing = false;
        mc.entityRenderer.setupCameraTransform(eventRender3D.getPartialTicks(), 2);
        mc.gameSettings.viewBobbing = old;
        if (mc.gameSettings.showDebugInfo) return;

        double ix = -(mc.player.lastTickPosX + (mc.player.posX - mc.player.lastTickPosX) * eventRender3D.getPartialTicks());
        double iy = -(mc.player.lastTickPosY + (mc.player.posY - mc.player.lastTickPosY) * eventRender3D.getPartialTicks());
        double iz = -(mc.player.lastTickPosZ + (mc.player.posZ - mc.player.lastTickPosZ) * eventRender3D.getPartialTicks());

        float x = (float) (float) (mc.player.lastTickPosX + (mc.player.posX - mc.player.lastTickPosX) * eventRender3D.getPartialTicks());
        float y = (float) (mc.player.lastTickPosY + (mc.player.posY - mc.player.lastTickPosY) * eventRender3D.getPartialTicks());
        float z = (float) (float) (mc.player.lastTickPosZ + (mc.player.posZ - mc.player.lastTickPosZ) * eventRender3D.getPartialTicks());

        if (!(mc.player.motionX == 0) && !(mc.player.motionZ == 0))
            points.add(new Point(new Vec3d(x, y, z)));
        points.removeIf(point -> point.time >= removeTicks.get());

        GlStateManager.pushMatrix();

        GL11.glDepthMask(false);

        GlStateManager.translate(ix, iy, iz);
        GlStateManager.enableBlend();
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.disableTexture2D();
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GlStateManager.alphaFunc(GL_GREATER, 0.0F);
        AntiAliasingUtility.hook(true, false, false);

        GL11.glBegin(GL11.GL_QUAD_STRIP);
        for (Point point : points) {
            Color color = Arraylist.getColor(points.indexOf(point));

            if (points.indexOf(point) >= points.size() - 1) continue;
            float alpha = 100 * (points.indexOf(point) / (float) points.size());
            Point temp = points.get(points.indexOf(point) + 1);
            int color2 = ColorUtility.setAlpha(new Color(color.getRGB()).getRGB(), (int) alpha);
            ColorUtility.setColor(color2);
            GL11.glVertex3d(temp.pos.x, temp.pos.y, temp.pos.z);
            GL11.glVertex3d(temp.pos.x, temp.pos.y + mc.player.height - 0.1, temp.pos.z);
            point.time++;
        }
        GL11.glEnd();

        GL11.glLineWidth(2);
        GL11.glBegin(GL11.GL_LINE_STRIP);
        for (Point point : points) {
            Color color = Arraylist.getColor(points.indexOf(point));

            if (points.indexOf(point) >= points.size() - 1) continue;
            float alpha = new Color(color.getRGB()).getAlpha() * (points.indexOf(point) / (float) points.size());
            Point temp = points.get(points.indexOf(point) + 1);
            int color2 = ColorUtility.setAlpha(ColorUtility.fade(5, points.indexOf(point) * 10, new Color(color.getRGB()), 1).brighter().getRGB(), (int) alpha);
            ColorUtility.setColor(color2);
            GL11.glVertex3d(temp.pos.x, temp.pos.y, temp.pos.z);

            point.time++;
        }
        GL11.glEnd();
        GL11.glBegin(GL11.GL_LINE_STRIP);
        for (Point point : points) {
            Color color = Arraylist.getColor(points.indexOf(point));
            if (points.indexOf(point) >= points.size() - 1) continue;
            float alpha = new Color(color.getRGB()).getAlpha() * (points.indexOf(point) / (float) points.size());
            Point temp = points.get(points.indexOf(point) + 1);
            int color2 = ColorUtility.setAlpha(ColorUtility.fade(5, points.indexOf(point) * 10, new Color(color.getRGB()), 1).brighter().getRGB(), (int) alpha);
            ColorUtility.setColor(color2);
            GL11.glVertex3d(temp.pos.x, temp.pos.y + mc.player.height - 0.1, temp.pos.z);
            point.time++;
        }
        GL11.glEnd();
        AntiAliasingUtility.unhook(true, false, false);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GL11.glEnable(GL11.GL_CULL_FACE);
        GlStateManager.resetColor();
        GL11.glDepthMask(true);
        GlStateManager.popMatrix();
    }

    public static class Point {
        public Vec3d pos;
        public long time;

        public Point(Vec3d pos) {
            this.pos = pos;
        }
    }
}
