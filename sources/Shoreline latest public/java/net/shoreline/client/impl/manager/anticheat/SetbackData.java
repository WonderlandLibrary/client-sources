package net.shoreline.client.impl.manager.anticheat;

import net.minecraft.util.math.Vec3d;

/**
 * @author xgraza
 * @since 1.0
 */
public record SetbackData(Vec3d position, long timeMS, int teleportID)
{
    public long timeSince()
    {
        return System.currentTimeMillis() - timeMS;
    }
}
