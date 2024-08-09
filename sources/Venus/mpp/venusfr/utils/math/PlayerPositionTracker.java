/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.math;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import mpp.venusfr.utils.client.IMinecraft;
import mpp.venusfr.utils.glu.GLU;
import mpp.venusfr.utils.math.MathUtil;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector4f;
import org.lwjgl.opengl.GL11;

public class PlayerPositionTracker
implements IMinecraft {
    private static final IntBuffer viewport;
    private static final FloatBuffer modelview;
    private static final FloatBuffer projection;
    private static final FloatBuffer vector;
    static final boolean $assertionsDisabled;

    public static boolean isInView(Entity entity2) {
        if (!$assertionsDisabled && mc.getRenderViewEntity() == null) {
            throw new AssertionError();
        }
        WorldRenderer.frustum.setCameraPosition(PlayerPositionTracker.mc.getRenderManager().info.getProjectedView().x, PlayerPositionTracker.mc.getRenderManager().info.getProjectedView().y, PlayerPositionTracker.mc.getRenderManager().info.getProjectedView().z);
        return WorldRenderer.frustum.isBoundingBoxInFrustum(entity2.getBoundingBox()) || entity2.ignoreFrustumCheck;
    }

    public static boolean isInView(Vector3d vector3d) {
        if (!$assertionsDisabled && mc.getRenderViewEntity() == null) {
            throw new AssertionError();
        }
        WorldRenderer.frustum.setCameraPosition(PlayerPositionTracker.mc.getRenderManager().info.getProjectedView().x, PlayerPositionTracker.mc.getRenderManager().info.getProjectedView().y, PlayerPositionTracker.mc.getRenderManager().info.getProjectedView().z);
        return WorldRenderer.frustum.isBoundingBoxInFrustum(new AxisAlignedBB(vector3d.add(-0.5, -0.5, -0.5), vector3d.add(0.5, 0.5, 0.5)));
    }

    public static Vector4f updatePlayerPositions(Entity entity2, float f) {
        Vector3d vector3d = PlayerPositionTracker.mc.getRenderManager().info.getProjectedView();
        double d = MathUtil.interpolate(entity2.getPosX(), entity2.lastTickPosX, (double)f);
        double d2 = MathUtil.interpolate(entity2.getPosY(), entity2.lastTickPosY, (double)f);
        double d3 = MathUtil.interpolate(entity2.getPosZ(), entity2.lastTickPosZ, (double)f);
        Vector3d vector3d2 = new Vector3d(entity2.getBoundingBox().maxX - entity2.getBoundingBox().minX, entity2.getBoundingBox().maxY - entity2.getBoundingBox().minY, entity2.getBoundingBox().maxZ - entity2.getBoundingBox().minZ);
        AxisAlignedBB axisAlignedBB = new AxisAlignedBB(d - vector3d2.x / 2.0, d2, d3 - vector3d2.z / 2.0, d + vector3d2.x / 2.0, d2 + vector3d2.y, d3 + vector3d2.z / 2.0);
        Vector4f vector4f = null;
        for (int i = 0; i < 8; ++i) {
            Vector3d vector3d3 = new Vector3d(i % 2 == 0 ? axisAlignedBB.minX : axisAlignedBB.maxX, i / 2 % 2 == 0 ? axisAlignedBB.minY : axisAlignedBB.maxY, i / 4 % 2 == 0 ? axisAlignedBB.minZ : axisAlignedBB.maxZ);
            vector3d3 = PlayerPositionTracker.project2D(vector3d3.x - vector3d.x, vector3d3.y - vector3d.y, vector3d3.z - vector3d.z);
            if (vector3d3 == null || !(vector3d3.z >= 0.0) || !(vector3d3.z < 1.0)) continue;
            if (vector4f == null) {
                vector4f = new Vector4f((float)vector3d3.x, (float)vector3d3.y, (float)vector3d3.z, 1.0f);
                continue;
            }
            vector4f.x = (float)Math.min(vector3d3.x, (double)vector4f.x);
            vector4f.y = (float)Math.min(vector3d3.y, (double)vector4f.y);
            vector4f.z = (float)Math.max(vector3d3.x, (double)vector4f.z);
            vector4f.w = (float)Math.max(vector3d3.y, (double)vector4f.w);
        }
        return vector4f;
    }

    private static Vector3d project2D(double d, double d2, double d3) {
        GL11.glGetFloatv(2982, modelview);
        GL11.glGetFloatv(2983, projection);
        GL11.glGetIntegerv(2978, viewport);
        if (GLU.gluProject((float)d, (float)d2, (float)d3, modelview, projection, viewport, vector)) {
            return new Vector3d(vector.get(0) / 2.0f, ((float)mc.getMainWindow().getHeight() - vector.get(1)) / 2.0f, vector.get(2));
        }
        return null;
    }

    static {
        $assertionsDisabled = !PlayerPositionTracker.class.desiredAssertionStatus();
        viewport = GLAllocation.createDirectIntBuffer(16);
        modelview = GLAllocation.createDirectFloatBuffer(16);
        projection = GLAllocation.createDirectFloatBuffer(16);
        vector = GLAllocation.createDirectFloatBuffer(4);
    }
}

