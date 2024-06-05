/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.UTILS.world;

import drunkclient.beta.API.events.world.EventMove;
import drunkclient.beta.API.events.world.EventPreUpdate;
import drunkclient.beta.UTILS.world.ACType;
import drunkclient.beta.UTILS.world.PlayerUtil;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;

public class MovementUtil {
    private static final Minecraft mc = Minecraft.getMinecraft();
    private static final List<Double> frictionValues = Arrays.asList(0.0, 0.0, 0.0);

    public static void doStrafe() {
        MovementUtil.doStrafe(MovementUtil.getSpeed());
    }

    public static final double getSpeed() {
        return Math.sqrt(Minecraft.thePlayer.motionX * Minecraft.thePlayer.motionX + Minecraft.thePlayer.motionZ * Minecraft.thePlayer.motionZ);
    }

    public static float getSensitivityMultiplier() {
        float f = Minecraft.getMinecraft().gameSettings.mouseSensitivity * 0.6f + 0.2f;
        return f * f * f * 8.0f * 0.15f;
    }

    public static double getDirection() {
        float rotationYaw = Minecraft.thePlayer.rotationYaw;
        if (Minecraft.thePlayer.moveForward < 0.0f) {
            rotationYaw += 180.0f;
        }
        float forward = 1.0f;
        if (Minecraft.thePlayer.moveForward < 0.0f) {
            forward = -0.5f;
        } else if (Minecraft.thePlayer.moveForward > 0.0f) {
            forward = 0.5f;
        }
        if (Minecraft.thePlayer.moveStrafing > 0.0f) {
            rotationYaw -= 90.0f * forward;
        }
        if (!(Minecraft.thePlayer.moveStrafing < 0.0f)) return Math.toRadians(rotationYaw);
        rotationYaw += 90.0f * forward;
        return Math.toRadians(rotationYaw);
    }

    public static float getMovementDirection() {
        Minecraft.getMinecraft();
        EntityPlayerSP player = Minecraft.thePlayer;
        float forward = player.moveForward;
        float strafe = player.moveStrafing;
        float direction = 0.0f;
        if (forward < 0.0f) {
            direction += 180.0f;
            if (strafe > 0.0f) {
                direction += 45.0f;
                return MathHelper.wrapAngleTo180_float(direction += player.rotationYaw);
            }
            if (!(strafe < 0.0f)) return MathHelper.wrapAngleTo180_float(direction += player.rotationYaw);
            direction -= 45.0f;
            return MathHelper.wrapAngleTo180_float(direction += player.rotationYaw);
        }
        if (forward > 0.0f) {
            if (strafe > 0.0f) {
                direction -= 45.0f;
                return MathHelper.wrapAngleTo180_float(direction += player.rotationYaw);
            }
            if (!(strafe < 0.0f)) return MathHelper.wrapAngleTo180_float(direction += player.rotationYaw);
            direction += 45.0f;
            return MathHelper.wrapAngleTo180_float(direction += player.rotationYaw);
        }
        if (strafe > 0.0f) {
            direction -= 90.0f;
            return MathHelper.wrapAngleTo180_float(direction += player.rotationYaw);
        }
        if (!(strafe < 0.0f)) return MathHelper.wrapAngleTo180_float(direction += player.rotationYaw);
        direction += 90.0f;
        return MathHelper.wrapAngleTo180_float(direction += player.rotationYaw);
    }

    public static double calculateFriction(double moveSpeed, double lastDist, double baseMoveSpeed) {
        frictionValues.set(0, lastDist - (lastDist / 160.0 - 0.001));
        frictionValues.set(1, lastDist - (moveSpeed - lastDist) / 33.3);
        double materialFriction = Minecraft.thePlayer.isInWater() ? (double)0.89f : (Minecraft.thePlayer.isInLava() ? (double)0.535f : (double)0.98f);
        frictionValues.set(2, lastDist - baseMoveSpeed * (1.0 - materialFriction));
        return Collections.min(frictionValues);
    }

    public static int getSpeedAmplifier() {
        if (!Minecraft.thePlayer.isPotionActive(Potion.moveSpeed)) return 0;
        return Minecraft.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1;
    }

    public static double getBaseSpeed(ACType value) {
        double baseSpeed = value == ACType.MINEPLEX ? 0.4225 : (value == ACType.VERUS ? 0.24 : 0.2873);
        if (!Minecraft.thePlayer.isPotionActive(Potion.moveSpeed)) return baseSpeed;
        int amp = Minecraft.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1;
        baseSpeed *= 1.0 + (value == ACType.VERUS ? 0.05 : 0.2) * (double)amp;
        return baseSpeed;
    }

    public static void setSpeed(EventPreUpdate moveEvent, double moveSpeed, float pseudoYaw, double pseudoStrafe, double pseudoForward) {
        double forward = pseudoForward;
        double strafe = pseudoStrafe;
        float yaw = pseudoYaw;
        if (pseudoForward != 0.0) {
            if (pseudoStrafe > 0.0) {
                yaw = pseudoYaw + (float)(pseudoForward > 0.0 ? -45 : 45);
            } else if (pseudoStrafe < 0.0) {
                yaw = pseudoYaw + (float)(pseudoForward > 0.0 ? 45 : -45);
            }
            strafe = 0.0;
            if (pseudoForward > 0.0) {
                forward = 1.0;
            } else if (pseudoForward < 0.0) {
                forward = -1.0;
            }
        }
        if (strafe > 0.0) {
            strafe = 1.0;
        } else if (strafe < 0.0) {
            strafe = -1.0;
        }
        double mx = Math.cos(Math.toRadians(yaw + 90.0f));
        double mz = Math.sin(Math.toRadians(yaw + 90.0f));
        Minecraft.thePlayer.motionX = forward * moveSpeed * mx + strafe * moveSpeed * mz;
        Minecraft.thePlayer.motionZ = forward * moveSpeed * mz - strafe * moveSpeed * mx;
    }

    public static void setSpeed(EventPreUpdate moveEvent, double moveSpeed) {
        MovementInput cfr_ignored_0 = Minecraft.thePlayer.movementInput;
        MovementInput cfr_ignored_1 = Minecraft.thePlayer.movementInput;
        MovementUtil.setSpeed(moveEvent, moveSpeed, Minecraft.thePlayer.rotationYaw, (double)MovementInput.moveStrafe, (double)MovementInput.moveForward);
    }

    public static void setSpeed(EventMove moveEvent, double moveSpeed, float pseudoYaw, double pseudoStrafe, double pseudoForward) {
        double forward = pseudoForward;
        double strafe = pseudoStrafe;
        float yaw = pseudoYaw;
        if (pseudoForward != 0.0) {
            if (pseudoStrafe > 0.0) {
                yaw = pseudoYaw + (float)(pseudoForward > 0.0 ? -45 : 45);
            } else if (pseudoStrafe < 0.0) {
                yaw = pseudoYaw + (float)(pseudoForward > 0.0 ? 45 : -45);
            }
            strafe = 0.0;
            if (pseudoForward > 0.0) {
                forward = 1.0;
            } else if (pseudoForward < 0.0) {
                forward = -1.0;
            }
        }
        if (strafe > 0.0) {
            strafe = 1.0;
        } else if (strafe < 0.0) {
            strafe = -1.0;
        }
        double mx = Math.cos(Math.toRadians(yaw + 90.0f));
        double mz = Math.sin(Math.toRadians(yaw + 90.0f));
        Minecraft.thePlayer.motionX = forward * moveSpeed * mx + strafe * moveSpeed * mz;
        Minecraft.thePlayer.motionZ = forward * moveSpeed * mz - strafe * moveSpeed * mx;
    }

    public static void setSpeed(EventMove moveEvent, double moveSpeed) {
        MovementInput cfr_ignored_0 = Minecraft.thePlayer.movementInput;
        MovementInput cfr_ignored_1 = Minecraft.thePlayer.movementInput;
        MovementUtil.setSpeed(moveEvent, moveSpeed, Minecraft.thePlayer.rotationYaw, (double)MovementInput.moveStrafe, (double)MovementInput.moveForward);
    }

    public static final void doStrafe(double speed) {
        if (!MovementUtil.isMoving()) {
            return;
        }
        double yaw = MovementUtil.getYaw(true);
        Minecraft.thePlayer.motionX = -Math.sin(yaw) * speed;
        Minecraft.thePlayer.motionZ = Math.cos(yaw) * speed;
    }

    public static double getSpeed(EntityPlayer player) {
        return Math.sqrt(player.motionX * player.motionX + player.motionZ * player.motionZ);
    }

    public static final double getYaw(boolean strafing) {
        float rotationYaw = strafing ? Minecraft.thePlayer.rotationYawHead : Minecraft.thePlayer.rotationYaw;
        float forward = 1.0f;
        MovementInput cfr_ignored_0 = Minecraft.thePlayer.movementInput;
        double moveForward = MovementInput.moveForward;
        MovementInput cfr_ignored_1 = Minecraft.thePlayer.movementInput;
        double moveStrafing = MovementInput.moveStrafe;
        float yaw = Minecraft.thePlayer.rotationYaw;
        if (moveForward < 0.0) {
            rotationYaw += 180.0f;
        }
        if (moveForward < 0.0) {
            forward = -0.5f;
        } else if (moveForward > 0.0) {
            forward = 0.5f;
        }
        if (moveStrafing > 0.0) {
            return Math.toRadians(rotationYaw -= 90.0f * forward);
        }
        if (!(moveStrafing < 0.0)) return Math.toRadians(rotationYaw);
        rotationYaw += 90.0f * forward;
        return Math.toRadians(rotationYaw);
    }

    public static boolean isBlockUnder() {
        Minecraft.getMinecraft();
        if (Minecraft.thePlayer.posY < 0.0) {
            return false;
        }
        int off = 0;
        while (true) {
            Minecraft.getMinecraft();
            if (off >= (int)Minecraft.thePlayer.posY + 2) return false;
            Minecraft.getMinecraft();
            AxisAlignedBB bb = Minecraft.thePlayer.getEntityBoundingBox().offset(0.0, -off, 0.0);
            WorldClient worldClient = Minecraft.getMinecraft().theWorld;
            Minecraft.getMinecraft();
            if (!worldClient.getCollidingBoundingBoxes(Minecraft.thePlayer, bb).isEmpty()) {
                return true;
            }
            off += 2;
        }
    }

    public static double getBaseMoveSpeed() {
        double baseSpeed = 0.2875;
        if (!Minecraft.thePlayer.isPotionActive(Potion.moveSpeed)) return baseSpeed;
        baseSpeed *= 1.0 + 0.2 * (double)(Minecraft.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
        return baseSpeed;
    }

    public static double getJumpBoostModifier(double baseJumpHeight) {
        if (!Minecraft.thePlayer.isPotionActive(Potion.jump)) return baseJumpHeight;
        int amplifier = Minecraft.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier();
        baseJumpHeight += (double)((float)(amplifier + 1) * 0.1f);
        return baseJumpHeight;
    }

    public static boolean isMoving() {
        if (Minecraft.thePlayer == null) return false;
        MovementInput cfr_ignored_0 = Minecraft.thePlayer.movementInput;
        if (MovementInput.moveForward != 0.0f) return true;
        MovementInput cfr_ignored_1 = Minecraft.thePlayer.movementInput;
        if (MovementInput.moveStrafe == 0.0f) return false;
        return true;
    }

    public static void setMotion(double speed) {
        MovementInput cfr_ignored_0 = Minecraft.thePlayer.movementInput;
        double forward = MovementInput.moveForward;
        MovementInput cfr_ignored_1 = Minecraft.thePlayer.movementInput;
        double strafe = MovementInput.moveStrafe;
        float yaw = Minecraft.thePlayer.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            Minecraft.thePlayer.motionX = 0.0;
            Minecraft.thePlayer.motionZ = 0.0;
            return;
        }
        if (forward != 0.0) {
            if (strafe > 0.0) {
                yaw += (float)(forward > 0.0 ? -45 : 45);
            } else if (strafe < 0.0) {
                yaw += (float)(forward > 0.0 ? 45 : -45);
            }
            strafe = 0.0;
            if (forward > 0.0) {
                forward = 1.0;
            } else if (forward < 0.0) {
                forward = -1.0;
            }
        }
        Minecraft.thePlayer.motionX = forward * speed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0f));
        Minecraft.thePlayer.motionZ = forward * speed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0f));
    }

    public static boolean MovementInput() {
        if (MovementUtil.mc.gameSettings.keyBindForward.isKeyDown()) return true;
        if (MovementUtil.mc.gameSettings.keyBindLeft.isKeyDown()) return true;
        if (MovementUtil.mc.gameSettings.keyBindRight.isKeyDown()) return true;
        if (MovementUtil.mc.gameSettings.keyBindBack.isKeyDown()) return true;
        return false;
    }

    public static boolean isOnGround(double height) {
        WorldClient worldClient = MovementUtil.mc.theWorld;
        Minecraft.getMinecraft();
        Minecraft.getMinecraft();
        if (worldClient.getCollidingBoundingBoxes(Minecraft.thePlayer, Minecraft.thePlayer.getEntityBoundingBox().offset(0.0, -height, 0.0)).isEmpty()) return false;
        return true;
    }

    public static void damagePlayer() {
        WorldClient worldClient = Minecraft.getMinecraft().theWorld;
        Minecraft.getMinecraft();
        Minecraft.getMinecraft();
        if (worldClient.getCollidingBoundingBoxes(Minecraft.thePlayer, Minecraft.thePlayer.getEntityBoundingBox().offset(0.0, 3.0001, 0.0).expand(0.0, 0.0, 0.0)).isEmpty()) {
            Minecraft.getMinecraft();
            NetHandlerPlayClient netHandlerPlayClient = Minecraft.thePlayer.sendQueue;
            Minecraft.getMinecraft();
            double d = Minecraft.thePlayer.posX;
            Minecraft.getMinecraft();
            double d2 = Minecraft.thePlayer.posY + 3.0001;
            Minecraft.getMinecraft();
            netHandlerPlayClient.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(d, d2, Minecraft.thePlayer.posZ, false));
            Minecraft.getMinecraft();
            NetHandlerPlayClient netHandlerPlayClient2 = Minecraft.thePlayer.sendQueue;
            Minecraft.getMinecraft();
            double d3 = Minecraft.thePlayer.posX;
            Minecraft.getMinecraft();
            double d4 = Minecraft.thePlayer.posY;
            Minecraft.getMinecraft();
            netHandlerPlayClient2.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(d3, d4, Minecraft.thePlayer.posZ, false));
            Minecraft.getMinecraft();
            NetHandlerPlayClient netHandlerPlayClient3 = Minecraft.thePlayer.sendQueue;
            Minecraft.getMinecraft();
            double d5 = Minecraft.thePlayer.posX;
            Minecraft.getMinecraft();
            double d6 = Minecraft.thePlayer.posY;
            Minecraft.getMinecraft();
            netHandlerPlayClient3.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(d5, d6, Minecraft.thePlayer.posZ, true));
        }
        Minecraft.getMinecraft();
        Minecraft.getMinecraft();
        double d = Minecraft.thePlayer.posX;
        Minecraft.getMinecraft();
        double d7 = Minecraft.thePlayer.posY + 0.42;
        Minecraft.getMinecraft();
        Minecraft.thePlayer.setPosition(d, d7, Minecraft.thePlayer.posZ);
    }

    public static boolean isBlockAbove() {
        double height = 0.0;
        while (height <= 1.0) {
            List<AxisAlignedBB> collidingList = MovementUtil.mc.theWorld.getCollidingBoundingBoxes(Minecraft.thePlayer, Minecraft.thePlayer.getEntityBoundingBox().offset(0.0, height, 0.0));
            if (!collidingList.isEmpty()) {
                return true;
            }
            height += 0.5;
        }
        return false;
    }

    public static boolean fallDistDamage() {
        if (!MovementUtil.isOnGround()) return false;
        if (MovementUtil.isBlockAbove()) {
            return false;
        }
        EntityPlayerSP player = Minecraft.thePlayer;
        double randomOffset = Math.random() * (double)3.0E-4f;
        double jumpHeight = 0.0525 - randomOffset;
        int packets = (int)((double)MovementUtil.getMinFallDist() / (jumpHeight - randomOffset) + 1.0);
        int i = 0;
        while (true) {
            if (i >= packets) {
                mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer(true));
                return true;
            }
            mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(player.posX, player.posY + jumpHeight, player.posZ, false));
            mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(player.posX, player.posY + randomOffset, player.posZ, false));
            ++i;
        }
    }

    public static float getMinFallDist() {
        float minDist = 3.0f;
        if (!Minecraft.thePlayer.isPotionActive(Potion.jump)) return minDist;
        minDist += (float)Minecraft.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1.0f;
        return minDist;
    }

    public static boolean isOnGround() {
        if (!Minecraft.thePlayer.onGround) return false;
        if (!Minecraft.thePlayer.isCollidedVertically) return false;
        return true;
    }

    public static int getJumpEffect() {
        if (!Minecraft.thePlayer.isPotionActive(Potion.jump)) return 0;
        return Minecraft.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1;
    }

    public static int getSpeedEffect() {
        if (!Minecraft.thePlayer.isPotionActive(Potion.moveSpeed)) return 0;
        return Minecraft.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1;
    }

    public static double defaultSpeed() {
        double baseSpeed = 0.3;
        Minecraft.getMinecraft();
        if (!Minecraft.thePlayer.isPotionActive(Potion.moveSpeed)) return baseSpeed;
        Minecraft.getMinecraft();
        int amplifier = Minecraft.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
        baseSpeed *= 1.0 + 0.2 * (double)(amplifier + 1);
        return baseSpeed;
    }

    public static double getJumpHeight(double baseJumpHeight) {
        if (PlayerUtil.isInLiquid()) {
            return 0.13499999955296516;
        }
        Minecraft.getMinecraft();
        if (!Minecraft.thePlayer.isPotionActive(Potion.jump)) return baseJumpHeight;
        Minecraft.getMinecraft();
        return baseJumpHeight + (double)(((float)Minecraft.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1.0f) * 0.1f);
    }

    public static double[] yawPos(double value) {
        double yaw = Math.toRadians(Minecraft.thePlayer.rotationYaw);
        return new double[]{-Math.sin(yaw) * value, Math.cos(yaw) * value};
    }

    public static boolean isOverVoid() {
        int i = (int)(Minecraft.thePlayer.posY - 1.0);
        while (i > 0) {
            BlockPos pos = new BlockPos(Minecraft.thePlayer.posX, (double)i, Minecraft.thePlayer.posZ);
            if (!(MovementUtil.mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir)) {
                return false;
            }
            --i;
        }
        return true;
    }
}

