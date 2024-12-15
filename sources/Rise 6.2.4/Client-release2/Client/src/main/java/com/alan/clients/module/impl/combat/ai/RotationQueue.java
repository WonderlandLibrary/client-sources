package com.alan.clients.module.impl.combat.ai;

import com.alan.clients.util.EvictingList;
import com.alan.clients.util.math.MathConst;
import com.alan.clients.util.vector.Vector2f;
import com.alan.clients.util.vector.Vector3d;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Tuple;

public class RotationQueue {
    public final EvictingList<Tuple<Vector2f, Vector3d>> rotations = new EvictingList<>(10);

    public void add(Vector2f rotation, Vector3d offset, int ahead) {
        if (ahead < rotations.size()) return;

        for (int i = 0; i < ahead - rotations.size(); i++)
            rotations.add(null);
        rotations.add(new Tuple<>(rotation, offset));
    }

    public Vector2f get() {
        Minecraft mc = Minecraft.getMinecraft();

        Tuple<Vector2f, Vector3d> rotation = rotations.getFirst();
        rotations.removeFirst();

        Vector2f offset = rotation.getFirst();

        final Vector3d diff = rotation.getSecond();
        final double distance = Math.hypot(diff.getX(), diff.getZ());
        final float yaw = (float) (MathHelper.atan2(diff.getZ(), diff.getX()) * MathConst.TO_DEGREES) - 90.0F;
        final float pitch = (float) (-(MathHelper.atan2(diff.getY(), distance) * MathConst.TO_DEGREES));
        Vector2f perfect = new Vector2f(yaw, pitch);
        Vector2f rotations = new Vector2f((float) (perfect.getX() + offset.getX()), MathHelper.clamp_float((float) (perfect.getY()), -90, 90));

        return new Vector2f(MathHelper.wrapAngleTo180_float(rotations.getX() - mc.thePlayer.rotationYaw), rotations.getY() - mc.thePlayer.rotationPitch);
    }
}
