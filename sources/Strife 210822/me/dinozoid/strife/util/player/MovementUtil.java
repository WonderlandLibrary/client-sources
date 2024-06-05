package me.dinozoid.strife.util.player;

import me.dinozoid.strife.StrifeClient;
import me.dinozoid.strife.event.implementations.player.MovePlayerEvent;
import me.dinozoid.strife.event.implementations.player.StrafePlayerEvent;
import me.dinozoid.strife.module.implementations.combat.KillAuraModule;
import me.dinozoid.strife.module.implementations.exploit.BlinkModule;
import me.dinozoid.strife.module.implementations.movement.TargetStrafeModule;
import me.dinozoid.strife.module.implementations.player.NoSlowdownModule;
import me.dinozoid.strife.util.MinecraftUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockHopper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MovementUtil extends MinecraftUtil {

    public static final double BUNNY_SLOPE = 0.72F;
    public static final double SPRINTING_MOD = 1.3F;
    public static final double SNEAK_MOD = 0.3F;
    public static final double ICE_MOD = 2.5F;
    public static final double VANILLA_JUMP_HEIGHT = 0.42F;
    public static final double WALK_SPEED = 0.221F;
    public static final double MAX_DIST = 1.9;
    public static final double BUNNY_DIV_FRICTION = 160.0 - 1.0E-3;
    private static final double SWIM_MOD = 0.115F / WALK_SPEED;
    private static final double[] DEPTH_STRIDER_VALUES = {
            1.0F,
            0.1645F / SWIM_MOD / WALK_SPEED,
            0.1995F / SWIM_MOD / WALK_SPEED,
            1.0F / SWIM_MOD,
    };

    public static final List<Double> frictionValues = new ArrayList<>();

    public static final double AIR_FRICTION = 0.98;
    public static final double WATER_FRICTION = 0.89;
    public static final double LAVA_FRICTION = 0.535;

    public static boolean canSprint() {
        return mc.thePlayer.movementInput.moveForward >= 0.8F &&
                !mc.thePlayer.isCollidedHorizontally &&
                (mc.thePlayer.getFoodStats().getFoodLevel() > 6 ||
                        mc.thePlayer.capabilities.allowFlying) &&
                !mc.thePlayer.isSneaking() &&
                (!mc.thePlayer.isUsingItem() || NoSlowdownModule.instance().toggled()) &&
                !mc.thePlayer.isPotionActive(Potion.moveSlowdown.id);
    }

    public static boolean isMoving() {
        return mc.thePlayer.movementInput.moveForward != 0.0F || mc.thePlayer.movementInput.moveStrafe != 0.0F;
    }

    public static boolean isOnGround() {
        return mc.thePlayer.onGround && mc.thePlayer.isCollidedVertically;
    }

    public static double calculateFriction(double moveSpeed, double lastDist, double baseMoveSpeedRef) {
        frictionValues.clear();
        frictionValues.add(lastDist - (lastDist / BUNNY_DIV_FRICTION));
        frictionValues.add(lastDist - ((moveSpeed - lastDist) / 33.3));
        double materialFriction = mc.thePlayer.isInWater() ? WATER_FRICTION : mc.thePlayer.isInLava() ? LAVA_FRICTION : AIR_FRICTION;
        frictionValues.add(lastDist - (baseMoveSpeedRef * (1.0 - materialFriction)));
        Collections.sort(frictionValues);
        return frictionValues.get(0);
    }

    public static double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;
        if (mc.thePlayer != null && mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            int amplifier = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        return baseSpeed;
    }

    public static double getLastDist() {
        double xDif = mc.thePlayer.posX - mc.thePlayer.lastTickPosX;
        double zDif = mc.thePlayer.posZ - mc.thePlayer.lastTickPosZ;
        return StrictMath.sqrt(xDif * xDif + zDif * zDif);
    }

    public static double getJumpHeight(double height) {
        double baseHeight = 0.42;
        if (mc.thePlayer.isPotionActive(Potion.jump)) {
            return baseHeight + getJumpBoostModifier() * 0.1F;
        }
        return baseHeight;
    }

    public static double[] yawPos(double value) {
        double yaw = Math.toRadians(mc.thePlayer.rotationYaw);
        return new double[] {-Math.sin(yaw) * value, Math.cos(yaw) * value};
    }

    public static int getJumpBoostModifier() {
        PotionEffect effect = mc.thePlayer.getActivePotionEffect(Potion.jump);
        if (effect != null)
            return effect.getAmplifier() + 1;
        return 0;
    }

    public static void setSpeed(MovePlayerEvent event, double speed) {
        final TargetStrafeModule targetStrafe = TargetStrafeModule.instance();
        if (targetStrafe.toggled()) {
            final KillAuraModule killAuraModule = KillAuraModule.instance();
            EntityLivingBase target = killAuraModule.target();
            if (target != null && targetStrafe.canStrafe()) {
                targetStrafe.strafe(event, speed);
                return;
            }
        }
        setSpeed(event, speed, mc.thePlayer.movementInput.moveForward, mc.thePlayer.movementInput.moveStrafe, mc.thePlayer.rotationYaw);
    }

    public static void setSpeed(MovePlayerEvent e, double speed, float forward, float strafing, float yaw) {
        if (forward == 0.0F && strafing == 0.0F) return;
        boolean reversed = forward < 0.0f;
        float strafingYaw = 90.0f * (forward > 0.0f ? 0.5f : reversed ? -0.5f : 1.0f);
        if (reversed) yaw += 180.0f;
        if (strafing > 0.0f) yaw -= strafingYaw;
        else if (strafing < 0.0f) yaw += strafingYaw;
        double x = Math.cos(StrictMath.toRadians(yaw + 90.0f));
        double z = Math.cos(StrictMath.toRadians(yaw));
        e.x(x * speed);
        e.z(z * speed);
    }

    public static boolean isInsideBlock() {
        for (int x = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().minX); x < MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().maxX) + 1; x++) {
            for (int y = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().minY); y < MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().maxY) + 1; y++) {
                for (int z = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().minZ); z < MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().maxZ) + 1; z++) {
                    Block block = Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                    if (block != null && !(block instanceof BlockAir)) {
                        AxisAlignedBB boundingBox = block.getCollisionBoundingBox(Minecraft.getMinecraft().theWorld, new BlockPos(x, y, z), Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(x, y, z)));
                        if (block instanceof BlockHopper)
                            boundingBox = new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1);
                        if (boundingBox != null && Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().intersectsWith(boundingBox))
                            return true;
                    }
                }
            }
        }
        return false;
    }

}
