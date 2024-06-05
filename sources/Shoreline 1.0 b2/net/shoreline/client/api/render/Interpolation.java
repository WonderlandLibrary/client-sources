package net.shoreline.client.api.render;

import net.shoreline.client.util.Globals;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;


/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class Interpolation implements Globals
{
    /**
     * Gets the interpolated {@link Vec3d} position of an entity (i.e. position
     * based on render ticks)
     *
     * @param entity The entity to get the position for
     * @param tickDelta The render time
     * @return The interpolated vector of an entity
     */
    public static Vec3d getRenderPosition(Entity entity, float tickDelta)
    {
        return new Vec3d(entity.getX() - MathHelper.lerp(tickDelta, entity.lastRenderX, entity.getX()),
            entity.getY() - MathHelper.lerp(tickDelta, entity.lastRenderY, entity.getY()),
            entity.getZ() - MathHelper.lerp(tickDelta, entity.lastRenderZ, entity.getZ()));
    }

    /**
     *
     * @param entity
     * @param tickDelta
     * @return
     */
    public static Vec3d getInterpolatedPosition(Entity entity, float tickDelta)
    {
        return new Vec3d(entity.prevX + ((entity.getX() - entity.prevX) * tickDelta),
                entity.prevY + ((entity.getY() - entity.prevY) * tickDelta),
                entity.prevZ + ((entity.getZ() - entity.prevZ) * tickDelta));
    }
}
