package Focus.Beta.UTILS.world;

import Focus.Beta.API.events.world.EventMove;
import Focus.Beta.UTILS.Math.RotationUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Keyboard;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MovementUtil {
    private final static Minecraft mc = Minecraft.getMinecraft();

    public static void doStrafe() {
        doStrafe(getSpeed());
    }

    public final static double getSpeed() {
        return Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ);
    }
    
    public static float getSensitivityMultiplier() {
        float f = Minecraft.getMinecraft().gameSettings.mouseSensitivity * 0.6F + 0.2F;
        return (f * f * f * 8.0F) * 0.15F;
    }
    public static double getDirection() {
        float rotationYaw = mc.thePlayer.rotationYaw;
        if (mc.thePlayer.moveForward < 0.0f) {
            rotationYaw += 180.0f;
        }
        float forward = 1.0f;
        if (mc.thePlayer.moveForward < 0.0f) {
            forward = -0.5f;
        }
        else if (mc.thePlayer.moveForward > 0.0f) {
            forward = 0.5f;
        }
        if (mc.thePlayer.moveStrafing > 0.0f) {
            rotationYaw -= 90.0f * forward;
        }
        if (mc.thePlayer.moveStrafing < 0.0f) {
            rotationYaw += 90.0f * forward;
        }
        return Math.toRadians(rotationYaw);
    }
    public static float getMovementDirection() {
        final EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        final float forward = player.moveForward;
        final float strafe = player.moveStrafing;
        float direction = 0.0f;
        if (forward < 0.0f) {
            direction += 180.0f;
            if (strafe > 0.0f) {
                direction += 45.0f;
            }
            else if (strafe < 0.0f) {
                direction -= 45.0f;
            }
        }
        else if (forward > 0.0f) {
            if (strafe > 0.0f) {
                direction -= 45.0f;
            }
            else if (strafe < 0.0f) {
                direction += 45.0f;
            }
        }
        else if (strafe > 0.0f) {
            direction -= 90.0f;
        }
        else if (strafe < 0.0f) {
            direction += 90.0f;
        }
        direction += player.rotationYaw;
        return MathHelper.wrapAngleTo180_float(direction);
    }
    

    private static final List<Double> frictionValues = Arrays.asList(0.0, 0.0, 0.0);
    public static double calculateFriction(double moveSpeed, double lastDist, double baseMoveSpeed) {
        frictionValues.set(0, lastDist - (lastDist / 160.0 - 1.0E-3));
        frictionValues.set(1, lastDist - ((moveSpeed - lastDist) / 33.3D));
        double materialFriction =
                mc.thePlayer.isInWater() ?
                        0.89F :
                        mc.thePlayer.isInLava() ?
                                0.535F :
                                0.98F;
        frictionValues.set(2, lastDist - (baseMoveSpeed * (1.0D - materialFriction)));
        return Collections.min(frictionValues);
    }



    public static int getSpeedAmplifier() {
        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            return mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1;
        }
        return 0;
    }

    public static double getBaseSpeed(ACType value) {
        double baseSpeed = value == ACType.MINEPLEX ? 0.4225D : (value == ACType.VERUS ? 0.24D : 0.2873D);
        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            int amp = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1;
            baseSpeed *= 1.0D + (value == ACType.VERUS ? 0.05D : 0.2D) * (double)amp;
        }

        return baseSpeed;
    }

    public static void setSpeed(final EventMove moveEvent, final double moveSpeed, final float pseudoYaw, final double pseudoStrafe, final double pseudoForward) {
        double forward = pseudoForward;
        double strafe = pseudoStrafe;
        float yaw = pseudoYaw;
        if (pseudoForward != 0.0D) {
            if (pseudoStrafe > 0.0D) {
                yaw = pseudoYaw + (float) (pseudoForward > 0.0D ? -45 : 45);
            } else if (pseudoStrafe < 0.0D) {
                yaw = pseudoYaw + (float) (pseudoForward > 0.0D ? 45 : -45);
            }

            strafe = 0.0D;
            if (pseudoForward > 0.0D) {
                forward = 1.0D;
            } else if (pseudoForward < 0.0D) {
                forward = -1.0D;
            }
        }

        if (strafe > 0.0D) {
            strafe = 1.0D;
        } else if (strafe < 0.0D) {
            strafe = -1.0D;
        }

        double mx = Math.cos(Math.toRadians((double) (yaw + 90.0F)));
        double mz = Math.sin(Math.toRadians((double) (yaw + 90.0F)));
        mc.thePlayer.motionX = forward * moveSpeed * mx + strafe * moveSpeed * mz;
        mc.thePlayer.motionZ = forward * moveSpeed * mz - strafe * moveSpeed * mx;
    }
    public static void setSpeed(final EventMove moveEvent, final double moveSpeed) {
        setSpeed(moveEvent, moveSpeed, mc.thePlayer.rotationYaw, mc.thePlayer.movementInput.moveStrafe, mc.thePlayer.movementInput.moveForward);
    }

    public final static void doStrafe(double speed) {
        if(!isMoving())  return;

        final double yaw = getYaw(true);
        mc.thePlayer.motionX = -Math.sin(yaw) * speed;
        mc.thePlayer.motionZ = Math.cos(yaw) * speed;
    }public static double getSpeed(EntityPlayer player) {
        return Math.sqrt(player.motionX * player.motionX + player.motionZ * player.motionZ);
    }

    public final static double getYaw(boolean strafing) {
        float rotationYaw = strafing ? mc.thePlayer.rotationYawHead : mc.thePlayer.rotationYaw;
        float forward = 1F;

        final double moveForward = mc.thePlayer.movementInput.moveForward;
        final double moveStrafing = mc.thePlayer.movementInput.moveStrafe;
        final float yaw = mc.thePlayer.rotationYaw;

        if (moveForward < 0) {
            rotationYaw += 180F;
        }

        if (moveForward < 0) {
            forward = -0.5F;
        } else if(moveForward > 0) {
            forward = 0.5F;
        }

        if (moveStrafing > 0) {
            rotationYaw -= 90F * forward;
        } else if(moveStrafing < 0) {
            rotationYaw += 90F * forward;
        }

        return Math.toRadians(rotationYaw);
    }






    /**
     * Check if there is a block underneath you
     *
     * @return
     */

    public static boolean isBlockUnder() {
        if (Minecraft.getMinecraft().thePlayer.posY < 0)
            return false;
        for (int off = 0; off < (int) Minecraft.getMinecraft().thePlayer.posY + 2; off += 2) {
            AxisAlignedBB bb = Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().offset(0, -off, 0);
            if (!Minecraft.getMinecraft().theWorld.getCollidingBoundingBoxes(Minecraft.getMinecraft().thePlayer, bb).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get Playerspeed with Speed Potion
     */

    public static double getBaseMoveSpeed() {
        double baseSpeed = 0.2875;

        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            baseSpeed *= 1 + .2 * (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
        }

        return baseSpeed;
    }

    /**
     * Get Motion Y if Jump Boost is active
     */

    public static double getJumpBoostModifier(double baseJumpHeight) {
        if (mc.thePlayer.isPotionActive(Potion.jump)) {
            int amplifier = mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier();
            baseJumpHeight += (double) ((float) (amplifier + 1) * 0.1F);
        }

        return baseJumpHeight;
    }

    /**
     * checks if player is moving, for simple and clean code.
     */

    public static boolean isMoving() {
        return mc.thePlayer != null && (mc.thePlayer.movementInput.moveForward != 0F || mc.thePlayer.movementInput.moveStrafe != 0F);
    }


    /**
     * sets Motion, for simple speeds and other shit
     */
    public static void setMotion(double speed) {
        double forward = mc.thePlayer.movementInput.moveForward;
        double strafe = mc.thePlayer.movementInput.moveStrafe;
        float yaw = mc.thePlayer.rotationYaw;
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
            mc.thePlayer.motionX = forward * speed * Math.cos(Math.toRadians(yaw + 90.0F)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0F));
            mc.thePlayer.motionZ = forward * speed * Math.sin(Math.toRadians(yaw + 90.0F)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0F));
        }
    }

    /**
     * Checks for Move input
     */
    public static boolean MovementInput() {
        if (!(mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown())) {
            return false;
        }
        return true;
    }

    /**
     * Set on ground with bounding box shit
     */

    public static boolean isOnGround(double height) {
    	if (!mc.theWorld.getCollidingBoundingBoxes(Minecraft.getMinecraft().thePlayer, Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().offset(0.0, -height, 0.0)).isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * A bit smarter damage Method
     * Should work a bit longer than before.
     * Hate it when Hypixel tries to fix my values.
     */
    public static void damagePlayer() {
        if (Minecraft.getMinecraft().theWorld.getCollidingBoundingBoxes(Minecraft.getMinecraft().thePlayer, Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().offset(0, 3.0001, 0).expand(0, 0, 0)).isEmpty()) {
        	Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY + 3.0001, Minecraft.getMinecraft().thePlayer.posZ, false));
        	Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ, false));
        	Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ, true));
        }
        Minecraft.getMinecraft().thePlayer.setPosition(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY + 0.42, Minecraft.getMinecraft().thePlayer.posZ);
    }



    /**
     * Checks if there is a block above you
     *
     * @return
     */

    public static boolean isBlockAbove() {
        for (double height = 0.0D; height <= 1.0D; height += 0.5D) {
            List<AxisAlignedBB> collidingList = mc.theWorld.getCollidingBoundingBoxes(
                    mc.thePlayer,
                    mc.thePlayer.getEntityBoundingBox().offset(0, height, 0));
            if (!collidingList.isEmpty())
                return true;
        }

        return false;
    }

    /**
     * gives you damage while simulating fall damage
     *
     * @return
     */

    public static boolean fallDistDamage() {
        if (!isOnGround() || isBlockAbove()) return false;

        final EntityPlayerSP player = mc.thePlayer;
        final double randomOffset = Math.random() * 0.0003F;
        final double jumpHeight = 0.0625D - 1.0E-2D - randomOffset;
        final int packets = (int) ((getMinFallDist() / (jumpHeight - randomOffset)) + 1);

        for (int i = 0; i < packets; i++) {
            mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(
                    player.posX,
                    player.posY + jumpHeight,
                    player.posZ,
                    false));
            mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(
                    player.posX,
                    player.posY + randomOffset,
                    player.posZ,
                    false));
        }

        mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer(true));
        return true;
    }

    public static float getMinFallDist() {
        float minDist = 3.0F;
        if (mc.thePlayer.isPotionActive(Potion.jump))
            minDist += mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1.0F;
        return minDist;
    }

    public static boolean isOnGround() {
        return mc.thePlayer.onGround && mc.thePlayer.isCollidedVertically;
    }

    public static int getJumpEffect() {
        if (mc.thePlayer.isPotionActive(Potion.jump)) {
            return mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1;
        }
        return 0;
    }

    public static int getSpeedEffect() {
        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            return mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1;
        }
        return 0;
    }

    public static double defaultSpeed() {
        double baseSpeed = 0.30;
        if (Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.moveSpeed)) {
            final int amplifier = Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        return baseSpeed;
    }

    /**
     * Collect the jump height of the player
     *
     * @param baseJumpHeight
     * @return
     */
    public static double getJumpHeight(double baseJumpHeight) {
        if (PlayerUtil.isInLiquid()) {
            return 0.221 * 0.115D / 0.221 + 0.02F;
        } else if (Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.jump)) {
            return baseJumpHeight + (Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1.0F) * 0.1F;
        }
        return baseJumpHeight;
    }

    public static double[] yawPos(double value) {
        double yaw = Math.toRadians(mc.thePlayer.rotationYaw);
        return new double[] {-Math.sin(yaw) * value, Math.cos(yaw) * value};
    }

}
