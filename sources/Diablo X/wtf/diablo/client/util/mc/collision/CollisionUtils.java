package wtf.diablo.client.util.mc.collision;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import wtf.diablo.client.util.math.MathUtil;

public class CollisionUtils {
    private static final Minecraft mc = Minecraft.getMinecraft();


    private CollisionUtils() {
    }

    /**
     * Gravity - 0.9800000190734863D, gravity is the value the players motion is multiplied by after
     * drag is applied.
     */
    public static final double GRAVITY = 0.98F;

    /**
     * Drag is the value the players vertical motion is subtracted by before gravity is applied.
     * This value will not be applied if the player is in an unloaded chunk.
     */
    public static final double DRAG = 0.08D;

    /**
     * Zero with gravity applied, -0.07840000152D,
     * a commonly exempted value that occurs in MC frequently for obvious reasons.
     */
    public static final double ZERO_GRAVITY = applyGravity(0);

    /**
     * When the player is in an unloaded chunk and their Y position is > 0,
     * the players motionY becomes -0.9800000190734863D;
     */
    public static final double UNLOADED_CHUNK_MOTION = -0.1D * GRAVITY;

    /**
     * When the player is both in liquid and horizontally collided, regardless of if jump is down
     * the player will jump with the value of 0.30000001192092896D
     */
    public static final double LIQUID_COLLIDED_JUMP_VALUE = (double) 0.3F;

    public static final double SERVER_GROUND_DIVISOR = 1.0D / 64.0D;

    // Directly ported from Niks base because ALOTTTTT of novice anticheats use this base
    // I've seen anticheats use this base entirely trust client ground when one of these y % edge == 0
    // due to their inaccurate ground check
    // and speaking of the ground check - MoveUtils#niggerizeVerticalPosition(double)
    private static final double[] EDGE_MODULOS = {
            .7D,
            .72D,
            .28D,
            .3D
    };

    // From niks base, can disable alot of movement related checks as some bad anticheats will exempt
    // if it appears you are collided with a wall
    private static final double[] WALL_MODULOS = {
            /*
            Full Blocks
             */
            .699999988079071D,
            .30000001192092896D,
            /*
            Glass Panes
             */
            .13749998807907104D,
            .862500011920929D,
            /*
            Cobblestone Walls
             */
            .050000011920928955D,
            .949999988079071D,
            .012499988079071045D,
            .987500011920929D,
            /*
            Fences
             */
            .07499998807907104D,
            .925000011920929D,
            /*
            Chests
             */
            .23750001192092896D,
            .762499988079071D,
            /*
            Heads
             */
            .19999998807907104D,
            .800000011920929D,
            /*
            Chains
             */
            .10624998807907104D,
            .893750011920929D,
            /*
            Bamboo
             */
            .9895833283662796D,
            .35624998807907104D,
            .7770833522081375D,
            .14375001192092896D,
            /*
            Anvils
             */
            .824999988079071D,
            .17500001192092896D,
            .11250001192092896D,
            .887499988079071D
    };

    /**
     *
     * @param y - Y position of the player
     * @return Returns the closest multiple of 1/64 too the Y position
     */
    public static double niggerizeVerticalPosition(final double y) {
        return MathUtil.getClosestMultipleOfDivisor(y, SERVER_GROUND_DIVISOR);
    }

    /**
     *
     * @param xPosition - X position of the player
     * @param zPosition - Z position of the player
     * @return Returns the closest edge position [0] being X and [1] being Z
     */
    public static double[] applyClosestEdge(final double xPosition, final double zPosition) {
        return new double[] {closestMultipleOfDivisorSetCollision(xPosition, EDGE_MODULOS),
                closestMultipleOfDivisorSetCollision(zPosition, EDGE_MODULOS)};
    }

    /**
     *
     * @param xPosition - X position of the player
     * @param zPosition - Z position of the player
     * @return Returns the closest wall collision position [0] being X and [1] being Z
     */
    public static double[] applyClosestWall(final double xPosition, final double zPosition) {
        return new double[] {closestMultipleOfDivisorSetCollision(xPosition, WALL_MODULOS),
                closestMultipleOfDivisorSetCollision(zPosition, WALL_MODULOS)};
    }

    /**
     * @param a - Value In
     * @return Returns the closest multiple of the divisor set too 'a'
     */
    private static double closestMultipleOfDivisorSetCollision(double a, final double[] set) {
        a = Math.abs(a % 1);
        double difference = Double.MAX_VALUE;
        for(final double edge : set) {
            final double closest = MathUtil.getClosestMultipleOfDivisor(a, edge);
            final double differenceIn = Math.abs(a - closest);
            if(differenceIn < difference) {
                a = closest;
                difference = differenceIn;
            }
        }
        return a;
    }

    /**
     * @param a - Value In
     * @return Returns the the value with the gravity formula applied.
     * <p>
     * (a - DRAG) * GRAVITY
     */
    public static double applyGravity(final double a) {
        return (a - DRAG) * GRAVITY;
    }

    /**
     *
     * @param a - Value In
     * @param ticks - how many times to apply gravity
     * @return returns the gravity formula applied 'ticks' times too 'a'
     *
     */
    public static double applyGravityForTicks(final double a, int ticks) {
        // This scenario should always be checked first incase of user error of inputting < 1 ticks
        if(ticks <= 0)
            return a;
        if(ticks == 1)
            return applyGravity(a);
        return applyGravityForTicks(a, ticks - 1);
    }

    /**
     *
     * @return Returns the motion the player jumps at, accounting for potion effects
     */
    public static double getJumpMotion() {
        double motionY = (double) 0.42F;

        if (mc.thePlayer.isPotionActive(Potion.jump))
        {
            motionY += (double)((float)(mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
        }
        return motionY;
    }

    /**
     *
     * @return Returns the players friction factor
     */
    public static double getFriction() {
        float f4 = 0.91F;

        if (mc.thePlayer.onGround)
        {
            f4 = mc.thePlayer.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(mc.thePlayer.posX),
                    MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().minY) - 1,
                    MathHelper.floor_double(mc.thePlayer.posZ))).getBlock().slipperiness * 0.91F;
        }

        float f = 0.16277136F / (f4 * f4 * f4);
        float friction;

        if (mc.thePlayer.onGround)
        {
            friction = mc.thePlayer.getAIMoveSpeed() * f;
        }
        else
        {
            friction = mc.thePlayer.jumpMovementFactor;
        }
        return friction;
    }
}
