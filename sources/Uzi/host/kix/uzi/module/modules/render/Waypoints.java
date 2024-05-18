package host.kix.uzi.module.modules.render;

import com.darkmagician6.eventapi.SubscribeEvent;
import host.kix.uzi.events.RenderWorldEvent;
import host.kix.uzi.module.Module;
import host.kix.uzi.utilities.minecraft.RenderingMethods;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.RenderManager;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kix on 6/6/2017.
 * Made for the eclipse project.
 */
public class Waypoints extends Module {

    public static List<Point> points = new ArrayList();

    public Waypoints() {
        super("Waypoints", 0, Category.RENDER);
    }

    @SubscribeEvent()
    public void renderWorld(RenderWorldEvent event) {
        GlStateManager.pushMatrix();
        RenderingMethods.enableGL3D();
        for (Waypoints.Point point : points) {
            double x = point.getX() - mc.getRenderManager().renderPosX;
            double y = point.getY() - mc.getRenderManager().renderPosY;
            double z = point.getZ() - mc.getRenderManager().renderPosZ;

            GlStateManager.color(0.7F, 0.1F, 0.2F, 0.7F);

            boolean bobbing = mc.gameSettings.viewBobbing;
            GL11.glLineWidth(1.0F);
            GL11.glLoadIdentity();
            mc.gameSettings.viewBobbing = false;
            mc.entityRenderer.orientCamera(event.getPartialTicks());
            GL11.glBegin(1);
            GL11.glVertex3d(0.0D, mc.thePlayer.getEyeHeight(), 0.0D);
            GL11.glVertex3d(x, y, z);
            GL11.glVertex3d(x, y, z);
            GL11.glVertex3d(x, y + 2.0D, z);
            GL11.glEnd();
            mc.gameSettings.viewBobbing = bobbing;
        }
        for (Waypoints.Point point : points) {
            double x = point.getX() - mc.getRenderManager().renderPosX;
            double y = point.getY() - mc.getRenderManager().renderPosY;
            double z = point.getZ() - mc.getRenderManager().renderPosZ;

            GlStateManager.pushMatrix();
            Waypoints.this.renderPointNameTag(point, x, y, z);
            GlStateManager.popMatrix();
        }
        RenderingMethods.disableGL3D();
        GlStateManager.popMatrix();
    }

    private boolean isValidPoint(Point point) {
        for (Point p : this.points) {
            if ((p.getX() == point.getX()) && (p.getY() == point.getY()) && (p.getZ() == point.getZ())) {
                return true;
            }
        }
        return false;
    }

    public static Point getPoint(String name) {
        for (Point point : points) {
            if (point.getLabel().equalsIgnoreCase(name)) {
                return point;
            }
        }
        return null;
    }

    private void renderPointNameTag(Point point, double x, double y, double z) {
        double tempY = y;
        tempY += 0.7D;

        double distance = mc.getRenderViewEntity().getDistance(x + mc.getRenderManager().viewerPosX, y + mc.getRenderManager().viewerPosY, z +
                mc.getRenderManager().viewerPosZ);
        int width = mc.fontRendererObj.getStringWidth(point.getLabel()) / 2 + 1;
        double scale = 0.0018D + (0.003F * distance);
        if (distance <= 8.0D) {
            scale = 0.0245D;
        }
        GlStateManager.pushMatrix();
        GlStateManager.enablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0F, -1500000.0F);
        GlStateManager.disableLighting();
        GlStateManager.translate((float) x, (float) tempY + 1.4F, (float) z);
        GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(mc.getRenderManager().playerViewX, mc.gameSettings.thirdPersonView == 2 ? -1.0F : 1.0F, 0.0F, 0.0F);

        GlStateManager.scale(-scale, -scale, scale);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();

        GlStateManager.disableAlpha();
        RenderingMethods.drawBorderedRectReliant(-width - 2, -(mc.fontRendererObj.FONT_HEIGHT + 1), width, 1.5F, 1.6F, 1996488704, -1435496416);
        GlStateManager.enableAlpha();

        mc.fontRendererObj.drawStringWithShadow(point.getLabel(), -width, -(mc.fontRendererObj.FONT_HEIGHT - 1), -5592406);

        GlStateManager.enableDepth();
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.disablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0F, 1500000.0F);
        GlStateManager.popMatrix();
    }

    public static class Point {
        private final String label;
        private final int x;
        private final int y;
        private final int z;

        public Point(String label, int x, int y, int z) {
            this.label = label;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public String getLabel() {
            return this.label;
        }

        public int getZ() {
            return this.z;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }
    }
}
