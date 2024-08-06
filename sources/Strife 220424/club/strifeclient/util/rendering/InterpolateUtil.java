package club.strifeclient.util.rendering;

import club.strifeclient.util.misc.MinecraftUtil;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import org.lwjglx.util.glu.GLU;

public final class InterpolateUtil extends MinecraftUtil {
    public static float interpolateFloat(float a, float b, float t) {
        return a + (b - a) * t;
    }
    public static double interpolate(double a, double b, double t) {
        return a + (b - a) * t;
    }
    public static double interpolateRenderX(final Entity entity) {
        return interpolate(entity.posX, entity.lastTickPosX, mc.timer.renderPartialTicks) - mc.getRenderManager().renderPosX;
    }
    public static double interpolateRenderY(final Entity entity) {
        return interpolate(entity.posY, entity.lastTickPosY, mc.timer.renderPartialTicks) - mc.getRenderManager().renderPosY;
    }
    public static double interpolateRenderZ(final Entity entity) {
        return interpolate(entity.posZ, entity.lastTickPosZ, mc.timer.renderPartialTicks) - mc.getRenderManager().renderPosZ;
    }
    public static AxisAlignedBB interpolateRenderBB(final Entity entity) {
        final RenderManager renderManager = mc.getRenderManager();
        final float timer = mc.timer.renderPartialTicks;
        final double x = entity.posX, y = entity.posY, z = entity.posZ;
        final double prevX = entity.lastTickPosX, prevY = entity.lastTickPosY, prevZ = entity.lastTickPosZ;
        final double renderX = renderManager.renderPosX, renderY = renderManager.renderPosY, renderZ = renderManager.renderPosZ;
        final float width = entity.width, height = entity.height;
        final double
                minX = prevX - width / 2 + (x - prevX) * timer - renderX,
                minY = prevY + (y - prevY) * timer - renderY,
                minZ = prevZ - width / 2 + (z - prevZ) * timer - renderZ,
                maxX = prevX + width / 2 + (x - prevX) * timer - renderX,
                maxY = prevY + height + (y - prevY) * timer - renderY,
                maxZ = prevZ + width / 2 + (z - prevZ) * timer - renderZ;
        return AxisAlignedBB.fromBounds(minX, minY, minZ, maxX, maxY, maxZ);
    }
    public static void convertCoords(double[] coordsOut, AxisAlignedBB interpolatedBB, Entity entity) {
        coordsOut[0] = Double.MAX_VALUE;
        coordsOut[1] = Double.MAX_VALUE;
        coordsOut[2] = Double.MIN_VALUE;
        coordsOut[3] = Double.MIN_VALUE;
        for (double x = interpolatedBB.minX; x <= interpolatedBB.maxX; x += entity.width) {
            for (double z = interpolatedBB.minZ; z <= interpolatedBB.maxZ; z += entity.width) {
                for (double y = interpolatedBB.minY; y <= interpolatedBB.maxY; y += entity.height) {
                    coordsOut[0] = Math.min(coordsOut[0], convertCoord(x, y, z)[0]);
                    coordsOut[1] = Math.min(coordsOut[1], mc.getScaledResolution().getScaledHeight_double() - convertCoord(x, y, z)[1]);
                    coordsOut[2] = Math.max(coordsOut[2], convertCoord(x, y, z)[0]);
                    coordsOut[3] = Math.max(coordsOut[3], mc.getScaledResolution().getScaledHeight_double() - convertCoord(x, y, z)[1]);
                }
            }
        }
    }
    public static double[] convertCoord(double x, double y, double z) {
        final boolean success = GLU.gluProject((float) x, (float) y, (float) z,
                ActiveRenderInfo.MODELVIEW, ActiveRenderInfo.PROJECTION, ActiveRenderInfo.VIEWPORT, ActiveRenderInfo.OBJECTCOORDS);
        if (success)
            return new double[]{ActiveRenderInfo.OBJECTCOORDS.get(0) / mc.getScaledResolution().getScaleFactor(),
                    ActiveRenderInfo.OBJECTCOORDS.get(1) / mc.getScaledResolution().getScaleFactor()};
        return new double[]{0, 0};
    }
}
