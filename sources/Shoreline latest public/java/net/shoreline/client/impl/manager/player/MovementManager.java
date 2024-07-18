package net.shoreline.client.impl.manager.player;

import net.minecraft.util.math.Vec3d;
import net.shoreline.client.util.Globals;

public class MovementManager implements Globals {
    /**
     * @param y
     */
    public void setMotionY(double y) {
        Vec3d motion = mc.player.getVelocity();
        mc.player.setVelocity(motion.getX(), y, motion.getZ());
    }

    /**
     * @param x
     * @param z
     */
    public void setMotionXZ(double x, double z) {
        Vec3d motion = mc.player.getVelocity();
        mc.player.setVelocity(x, motion.y, z);
    }
}
