package wtf.diablo.client.util.mc.player.movement;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import wtf.diablo.client.module.impl.player.scaffoldrecode.rotations.RotationsHandler;
import wtf.diablo.client.pathfinding.impl.WalkingPlayerPathFinder;
import wtf.diablo.client.util.mc.player.damage.Damage;

public final class MovementUtil
{
    private static final Minecraft mc = Minecraft.getMinecraft();
    public static final WalkingPlayerPathFinder PATH_FINDER_LEGIT = new WalkingPlayerPathFinder(60, true), PATH_FINDER = new WalkingPlayerPathFinder(20, false);

    private MovementUtil()
    {
    }

    public static boolean isMoving()
    {
        return mc.thePlayer.moveForward != 0 || mc.thePlayer.moveStrafing != 0;
    }

    public static void setMotion(final double motionX, final double motionY, final double motionZ) {
        mc.thePlayer.motionX = motionX;
        mc.thePlayer.motionY = motionY;
        mc.thePlayer.motionZ = motionZ;
    }

    public static void setMotion(final double speed) {
        float yaw = mc.thePlayer.rotationYaw;
        double forward = mc.thePlayer.moveForward;
        double strafe = mc.thePlayer.moveStrafing;
        if ((forward == 0.0D) && (strafe == 0.0D)) {
            mc.thePlayer.motionX = 0;
            mc.thePlayer.motionZ = 0;
        } else {
            if (forward != 0.0D) {
                if (strafe > 0.0D) {
                    yaw += (forward > 0.0D ? -45 : 45);
                } else if (strafe < 0.0D) {
                    yaw += (forward > 0.0D ? 45 : -45);
                }
                strafe = 0.0D;
                if (forward > 0.0D) {
                    forward = 1;
                } else if (forward < 0.0D) {
                    forward = -1;
                }
            }

            final double sin = Math.sin(Math.toRadians(yaw + 90.0F));
            final double cos = Math.cos(Math.toRadians(yaw + 90.0F));
            mc.thePlayer.motionX = forward * speed * cos + strafe * speed * sin;
            mc.thePlayer.motionZ = forward * speed * sin - strafe * speed * cos;
        }
    }

    public static double getPlayerSpeed()
    {
        return Math.hypot(mc.thePlayer.motionX, mc.thePlayer.motionZ);
    }

    public static double getBaseMoveSpeed() {
        double baseSpeed = Minecraft.getMinecraft().thePlayer.capabilities.getWalkSpeed() * 2.925;
        if (Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.moveSlowdown)) {
            baseSpeed /= 1.0 + 0.15 * (Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.moveSlowdown).getAmplifier() + 1);
        }
        if (Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.moveSpeed)) {
            baseSpeed *= 1.0 + 0.125 * (Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
        }
        return baseSpeed;
    }

    public static boolean isBlockUnder() {
        if (!(Minecraft.getMinecraft().thePlayer.posY < 0.0D)) {
            for (int offset = 0; offset < (int) Minecraft.getMinecraft().thePlayer.posY + 2; offset += 2) {
                AxisAlignedBB bb = Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().offset(0.0D, -offset, 0.0D);
                if (!Minecraft.getMinecraft().theWorld.getCollidingBoundingBoxes(Minecraft.getMinecraft().thePlayer, bb).isEmpty()) {
                    return true;
                }
            }

        }
        return false;
    }

    public static double getBlocksPerSecond() {
        return (double)Math.round((mc.thePlayer.getDistance(mc.thePlayer.lastTickPosX, mc.thePlayer.posY, mc.thePlayer.lastTickPosZ)
                * (mc.getTimer().timerSpeed * mc.getTimer().ticksPerSecond)) * 100) / 100;

    }

    public static double getJumpMotion() {
        float result = 0.42F;

        if (mc.thePlayer.isPotionActive(Potion.jump)) {
            result += (double)((float)(mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
        }
        return  result;
    }

    public static void freeze(final boolean motion, final boolean y) {

        mc.thePlayer.setPosition(mc.thePlayer.lastTickPosX, y ? mc.thePlayer.lastTickPosY : mc.thePlayer.posY, mc.thePlayer.lastTickPosZ);

        if(motion) mc.thePlayer.motionX = mc.thePlayer.motionZ = 0;
        if(y) mc.thePlayer.motionY = 0;

    }

    public static void strafe(final double speed, final float yaw) {
        if (!isMoving())
            return;
        mc.thePlayer.motionX = -MathHelper.sin(RotationsHandler.getRotationDegree(yaw)) * speed;
        mc.thePlayer.motionZ = MathHelper.cos(RotationsHandler.getRotationDegree(yaw)) * speed;
    }

    public static void strafe(final double speed) {
        if (!isMoving())
            return;
        final float deg = RotationsHandler.getRotationDegree(mc.thePlayer.rotationYaw);

        mc.thePlayer.motionX = -StrictMath.sin(deg) * speed;
        mc.thePlayer.motionZ = StrictMath.cos(deg) * speed;
    }

    public static void strafe() {
        strafe(getPlayerSpeed());
    }

    public static void damage() {
        damage(Damage.VANILLA);
    }

    public static void damage(final Damage damageType) {
        switch (damageType) {
            default:
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 3.1, mc.thePlayer.posZ, false));
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
                break;
            case VERUS:
                mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ), 1, new ItemStack(Blocks.stone.getItem(mc.theWorld, new BlockPos(0, 0, 0))), 0.5f, 0.5f, 0.5f));
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, Math.round(mc.thePlayer.posY) + 4D, mc.thePlayer.posZ, false));
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
                break;
        }
    }

    public static int potionAmplifier(final Potion potionType) {
        final PotionEffect speed =  mc.thePlayer.getActivePotionEffect(potionType);
        if(speed == null)
            return 1;
        return speed.getAmplifier() + 2;
    }

}