package wtf.diablo.client.util.mc.checks;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;

public final class CheckUtils {
    private static final Minecraft mc = Minecraft.getMinecraft();

    private CheckUtils() {}

    public static final double GRAVITY =  0.9800000190734863D;

    public static boolean validGround(final double x, final double y, final double z) {
        return !mc.theWorld.isAirBlock(new BlockPos(x, y - 1, z));
    }

    public static double nextPredictedY(final Entity entityIn) {
        return getGravity(getDeltaY(entityIn));
    }

    /**
     *
     * @param entityIn
     * @return an array containing 3 doubles, 0 being xMotion, 1 being yMotion, and 2 being zMotion
     */
    public static double[] getMotion(final Entity entityIn) {
        return new double[] {
                entityIn.posX - entityIn.lastTickPosX,
                entityIn.posY - entityIn.lastTickPosY,
                entityIn.posZ - entityIn.lastTickPosZ
        };
    }

    public static double getDeltaY(final Entity entityIn) {
        return entityIn.posY - entityIn.lastTickPosY;
    }

    /**
     * @return Default Vanilla gravity, in modifier-less scenarios, the next vertical motion will be "motionY * GRAVITY"
     */
    public static double getGravity(final double motion) {
        return (motion - 0.08) * GRAVITY;
    }

}
