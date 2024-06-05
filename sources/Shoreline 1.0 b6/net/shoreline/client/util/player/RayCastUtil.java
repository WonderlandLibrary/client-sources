package net.shoreline.client.util.player;

import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.shoreline.client.util.Globals;

/**
 * @author xgraza
 * @since 04/05/24
 */
public final class RayCastUtil implements Globals
{
    public static HitResult rayCast(final double reach, final float[] angles)
    {
        // learn to give me real rotations
        if (Float.isNaN(angles[0]) || Float.isNaN(angles[1]))
        {
            return null;
        }

        final Vec3d rotationVector = RotationUtil.getRotationVector(angles[1], angles[0]);
        final double eyeHeight = mc.player.getStandingEyeHeight();
        final Vec3d eyes = new Vec3d(mc.player.getX(), mc.player.getY() + eyeHeight, mc.player.getZ());

        return mc.world.raycast(new RaycastContext(
                eyes,
                eyes.add(rotationVector.x * reach, rotationVector.y * reach, rotationVector.z * reach),
                RaycastContext.ShapeType.COLLIDER,
                RaycastContext.FluidHandling.NONE,
                mc.player));
    }
}
