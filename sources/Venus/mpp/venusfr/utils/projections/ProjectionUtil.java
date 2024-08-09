/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.projections;

import mpp.venusfr.utils.client.IMinecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

public class ProjectionUtil
implements IMinecraft {
    public static Vector2f project(double d, double d2, double d3) {
        Entity entity2;
        Vector3d vector3d = ProjectionUtil.mc.getRenderManager().info.getProjectedView();
        Quaternion quaternion = mc.getRenderManager().getCameraOrientation().copy();
        quaternion.conjugate();
        Vector3f vector3f = new Vector3f((float)(vector3d.x - d), (float)(vector3d.y - d2), (float)(vector3d.z - d3));
        vector3f.transform(quaternion);
        if (ProjectionUtil.mc.gameSettings.viewBobbing && (entity2 = mc.getRenderViewEntity()) instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity)entity2;
            ProjectionUtil.calculateViewBobbing(playerEntity, vector3f);
        }
        double d4 = ProjectionUtil.mc.gameRenderer.getFOVModifier(ProjectionUtil.mc.getRenderManager().info, mc.getRenderPartialTicks(), false);
        return ProjectionUtil.calculateScreenPosition(vector3f, d4);
    }

    private static void calculateViewBobbing(PlayerEntity playerEntity, Vector3f vector3f) {
        float f = playerEntity.distanceWalkedModified;
        float f2 = f - playerEntity.prevDistanceWalkedModified;
        float f3 = -(f + f2 * mc.getRenderPartialTicks());
        float f4 = MathHelper.lerp(mc.getRenderPartialTicks(), playerEntity.prevCameraYaw, playerEntity.cameraYaw);
        Quaternion quaternion = new Quaternion(Vector3f.XP, Math.abs(MathHelper.cos(f3 * (float)Math.PI - 0.2f) * f4) * 5.0f, true);
        quaternion.conjugate();
        vector3f.transform(quaternion);
        Quaternion quaternion2 = new Quaternion(Vector3f.ZP, MathHelper.sin(f3 * (float)Math.PI) * f4 * 3.0f, true);
        quaternion2.conjugate();
        vector3f.transform(quaternion2);
        Vector3f vector3f2 = new Vector3f(MathHelper.sin(f3 * (float)Math.PI) * f4 * 0.5f, -Math.abs(MathHelper.cos(f3 * (float)Math.PI) * f4), 0.0f);
        vector3f2.setY(-vector3f2.getY());
        vector3f.add(vector3f2);
    }

    private static Vector2f calculateScreenPosition(Vector3f vector3f, double d) {
        float f = (float)mc.getMainWindow().getScaledHeight() / 2.0f;
        float f2 = f / (vector3f.getZ() * (float)Math.tan(Math.toRadians(d / 2.0)));
        if (vector3f.getZ() < 0.0f) {
            return new Vector2f(-vector3f.getX() * f2 + (float)mc.getMainWindow().getScaledWidth() / 2.0f, (float)mc.getMainWindow().getScaledHeight() / 2.0f - vector3f.getY() * f2);
        }
        return new Vector2f(Float.MAX_VALUE, Float.MAX_VALUE);
    }
}

