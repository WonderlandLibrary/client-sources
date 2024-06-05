package Ascii4UwUWareClient.Util.World;


import Ascii4UwUWareClient.API.Events.World.EventMove;
import Ascii4UwUWareClient.Client;
import Ascii4UwUWareClient.Module.Modules.Move.NoSlow;
import Ascii4UwUWareClient.Util.Proxy.InventoryUtils;
import Ascii4UwUWareClient.Util.Wrapper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockIce;
import net.minecraft.block.BlockPackedIce;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class MovementUtils {

    public static final double BUNNY_SLOPE = 0.66;
    public static final double WATCHDOG_BUNNY_SLOPE = BUNNY_SLOPE * 0.96;
    public static final double SPRINTING_MOD = 1.3;
    public static final double SNEAK_MOD = 0.3;
    public static final double ICE_MOD = 2.5;
    public static final double VANILLA_JUMP_HEIGHT = 0.42F;
    public static final double WALK_SPEED = 0.221;
    private static final List<Double> frictionValues = new ArrayList<>();
    private static final double MIN_DIF = 1.0E-2;
    public static final double MAX_DIST = 2.15 - MIN_DIF;
    public static final double BUNNY_DIV_FRICTION = 160.0D - MIN_DIF;
    private static final double SWIM_MOD = 0.115D / WALK_SPEED;
    private static final double[] DEPTH_STRIDER_VALUES = {
            1.0,
            0.1645 / SWIM_MOD / WALK_SPEED,
            0.1995 / SWIM_MOD / WALK_SPEED,
            1.0 / SWIM_MOD,
    };
    private static final double AIR_FRICTION = 0.98;
    private static final double WATER_FRICTION = 0.89;
    private static final double LAVA_FRICTION = 0.535;

    private MovementUtils() {
    }

    public static double getBunnySlope(boolean watchdog) {
        double base = watchdog ? WATCHDOG_BUNNY_SLOPE : BUNNY_SLOPE;
        return base + (0.02D * getJumpBoostModifier());
    }

    public static int getJumpBoostModifier() {
        PotionEffect effect = Wrapper.getPlayer().getActivePotionEffect(Potion.jump);
        if (effect != null)
            return effect.getAmplifier() + 1;
        return 0;
    }

    private static boolean isMovingEnoughForSprint() {
        MovementInput movementInput = Wrapper.getPlayer().movementInput;
        return movementInput.moveForward > 0.8F || movementInput.moveForward < -0.8F ||
                movementInput.moveStrafe > 0.8F || movementInput.moveStrafe < -0.8F;
    }

    public static float getMovementDirection() {
        float forward = Wrapper.getPlayer().movementInput.moveForward;
        float strafe = Wrapper.getPlayer().movementInput.moveStrafe;

        float direction = 0.0f;
        if (forward < 0) {
            direction += 180;
            if (strafe > 0) {
                direction += 45;
            } else if (strafe < 0) {
                direction -= 45;
            }
        } else if (forward > 0) {
            if (strafe > 0) {
                direction -= 45;
            } else if (strafe < 0) {
                direction += 45;
            }
        } else {
            if (strafe > 0) {
                direction -= 90;
            } else if (strafe < 0) {
                direction += 90;
            }
        }

        direction += Wrapper.getPlayer().rotationYaw;

        return MathHelper.wrapAngleTo180_float(direction);
    }

    public static boolean isBlockAbove() {
        for (double height = 0.0D; height <= 1.0D; height += 0.5D) {
            List<AxisAlignedBB> collidingList = Wrapper.getWorld().getCollidingBoundingBoxes(
                    Wrapper.getPlayer(),
                    Wrapper.getPlayer().getEntityBoundingBox().offset(0, height, 0));
            if (!collidingList.isEmpty())
                return true;
        }

        return false;
    }

    public static boolean fallDistDamage() {
        if (!isOnGround() || isBlockAbove()) return false;

        final EntityPlayerSP player = Wrapper.getPlayer();
        final double randomOffset = Math.random() * 0.0003F;
        final double jumpHeight = 0.0625D - 1.0E-2D - randomOffset;
        final int packets = (int) ((getMinFallDist() / (jumpHeight - randomOffset)) + 1);

        for (int i = 0; i < packets; i++) {
            Wrapper.sendPacketDirect(new C03PacketPlayer.C04PacketPlayerPosition(
                    player.posX,
                    player.posY + jumpHeight,
                    player.posZ,
                    false));
            Wrapper.sendPacketDirect(new C03PacketPlayer.C04PacketPlayerPosition(
                    player.posX,
                    player.posY + randomOffset,
                    player.posZ,
                    false));
        }

        Wrapper.sendPacketDirect(new C03PacketPlayer(true));
        return true;
    }

    public static boolean isInLiquid() {
        return Wrapper.getPlayer().isInWater() || Wrapper.getPlayer().isInLava();
    }

    public static boolean isOverVoid() {
        for (double posY = Wrapper.getPlayer().posY; posY > 0.0; posY--) {
            if (!(Wrapper.getWorld().getBlockState(
                    new BlockPos(Wrapper.getPlayer().posX, posY, Wrapper.getPlayer().posZ)).getBlock() instanceof BlockAir))
                return false;
        }

        return true;
    }

    public static double getJumpHeight(double baseJumpHeight) {
        if (isInLiquid()) {
            return WALK_SPEED * SWIM_MOD + 0.02F;
        } else if (Wrapper.getPlayer().isPotionActive(Potion.jump)) {
            return baseJumpHeight + (Wrapper.getPlayer().getActivePotionEffect(Potion.jump).getAmplifier() + 1.0F) * 0.1F;
        }
        return baseJumpHeight;
    }

    public static float getMinFallDist() {
        float minDist = 3.0F;
        if (Wrapper.getPlayer().isPotionActive(Potion.jump))
            minDist += Wrapper.getPlayer().getActivePotionEffect(Potion.jump).getAmplifier() + 1.0F;
        return minDist;
    }

    public static double calculateFriction(double moveSpeed, double lastDist, double baseMoveSpeedRef) {
        frictionValues.clear();
        frictionValues.add(lastDist - (lastDist / BUNNY_DIV_FRICTION));
        frictionValues.add(lastDist - ((moveSpeed - lastDist) / 33.3));
        double materialFriction =
                Wrapper.getPlayer().isInWater() ?
                        WATER_FRICTION :
                        Wrapper.getPlayer().isInLava() ?
                                LAVA_FRICTION :
                                AIR_FRICTION;
        frictionValues.add(lastDist - (baseMoveSpeedRef * (1.0 - materialFriction)));
        return Collections.min(frictionValues);
    }

    public static boolean isOnIce() {
        Block blockUnder = getBlockUnder();
        return blockUnder instanceof BlockIce || blockUnder instanceof BlockPackedIce;
    }

    public static Block getBlockUnder() {
        EntityPlayerSP player = Wrapper.getPlayer();
        return Wrapper.getWorld().getBlockState(
                new BlockPos(
                        player.posX,
                        Math.floor(player.getEntityBoundingBox().minY) - 1,
                        player.posZ)).getBlock();
    }

    public static double getBlockHeight() {
        return Wrapper.getPlayer().posY - (int) Wrapper.getPlayer().posY;
    }

    public static boolean canSprint(boolean omni) {
        return (omni ? isMovingEnoughForSprint() : Wrapper.getPlayer().movementInput.moveForward >= 0.8F) && !Wrapper.getPlayer().isCollidedHorizontally &&
                Wrapper.getPlayer().getFoodStats().getFoodLevel() > 6 && !Wrapper.getPlayer().isSneaking() &&
                (!Wrapper.getPlayer().isUsingItem() || Client.instance.getModuleManager().getModuleByClass(NoSlow.class).isEnabled());
    }


    public static double getBaseMoveSpeed() {
        final EntityPlayerSP player = Wrapper.getPlayer();
        double base = player.isSneaking() ? WALK_SPEED * MovementUtils.SNEAK_MOD : canSprint(true) ? WALK_SPEED * SPRINTING_MOD : WALK_SPEED;

        PotionEffect moveSpeed = player.getActivePotionEffect(Potion.moveSpeed);
        PotionEffect moveSlowness = player.getActivePotionEffect(Potion.moveSlowdown);

        if (moveSpeed != null)
            base *= 1.0 + 0.2 * (moveSpeed.getAmplifier() + 1);

        if (moveSlowness != null)
            base *= 1.0 + 0.2 * (moveSlowness.getAmplifier() + 1);


        if (player.isInWater()) {
            base *= SWIM_MOD;
            final int depthStriderLevel = InventoryUtils.getDepthStriderLevel();
            if (depthStriderLevel > 0) {
                base *= DEPTH_STRIDER_VALUES[depthStriderLevel];
            }
        } else if (player.isInLava()) {
            base *= SWIM_MOD;
        }
        return base;
    }


    public static void setSpeed(EventMove e, double speed, float forward, float strafing, float yaw) {
        boolean reversed = forward < 0.0f;
        float strafingYaw = 90.0f *
                (forward > 0.0f ? 0.5f : reversed ? -0.5f : 1.0f);

        if (reversed)
            yaw += 180.0f;
        if (strafing > 0.0f)
            yaw -= strafingYaw;
        else if (strafing < 0.0f)
            yaw += strafingYaw;

        double x = Math.cos(Math.toRadians(yaw + 90.0f));
        double z = Math.cos(Math.toRadians(yaw));

        e.setX(x * speed);
        e.setZ(z * speed);
    }

    public static boolean isOnGround() {
//        List<AxisAlignedBB> collidingList = Wrapper.getWorld().getCollidingBoundingBoxes(Wrapper.getPlayer(), Wrapper.getPlayer().getEntityBoundingBox().offset(0.0, -(0.01 - MIN_DIF), 0.0));
//        return collidingList.size() > 0;
        return Wrapper.getPlayer().onGround && Wrapper.getPlayer().isCollidedVertically;
    }

    public static boolean isMoving() {
        return Wrapper.getPlayer().movementInput.moveForward != 0.0F || Wrapper.getPlayer().movementInput.moveStrafe != 0.0F;
    }
}
