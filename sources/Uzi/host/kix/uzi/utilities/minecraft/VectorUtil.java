package host.kix.uzi.utilities.minecraft;

import net.minecraft.util.Vec3;

/**
 * Created by myche on 2/21/2017.
 */
public class VectorUtil {

    /**
     * Multiply a vector by another vector multiplier.
     *
     * @param multiplier vector multiplier
     * @return product of vectors
     */
    public static Vec3 mult(Vec3 factor, Vec3 multiplier) {
        return new Vec3(factor.xCoord * multiplier.xCoord, factor.yCoord * multiplier.yCoord, factor.zCoord * multiplier.zCoord);
    }

    /**
     * Multiply a vector by another multiplier.
     *
     * @param multiplier vector multiplier
     * @return product of vectors
     */
    public static Vec3 mult(Vec3 factor, float multiplier) {
        return new Vec3(factor.xCoord * multiplier, factor.yCoord * multiplier, factor.zCoord * multiplier);
    }

    /**
     * Divide a vector by another vector divisor.
     *
     * @param divisor vector divisor
     * @return quotient of vectors
     */
    public static Vec3 div(Vec3 factor, Vec3 divisor) {
        return new Vec3(factor.xCoord / divisor.xCoord, factor.yCoord / divisor.yCoord, factor.zCoord / divisor.zCoord);
    }

    /**
     * Divide a vector by another divisor.
     *
     * @param divisor vector divisor
     * @return quotient of vectors
     */
    public static Vec3 div(Vec3 factor, float divisor) {
        return new Vec3(factor.xCoord / divisor, factor.yCoord / divisor, factor.zCoord / divisor);
    }

}
