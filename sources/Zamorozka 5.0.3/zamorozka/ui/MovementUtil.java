package zamorozka.ui;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.BlockPos;
import zamorozka.event.events.EventMove2;

public class MovementUtil implements MCUtil {
    public static boolean status;
    private static int cunt;

    public static int getSpeedEffect() {
        if (mc.player.isPotionActive(Potion.getPotionById(1)))
            return mc.player.getActivePotionEffect(Potion.getPotionById(1)).getAmplifier() + 1;
        else
            return 0;
    }

    public static double getJumpBoostModifier(double baseJumpHeight) {
        if (mc.player.isPotionActive(Potion.getPotionById(8))) {
            int amplifier = mc.player.getActivePotionEffect(Potion.getPotionById(8)).getAmplifier();
            baseJumpHeight += ((amplifier + 1) * 0.1F);
        }
        return baseJumpHeight;
    }

    public static int getJumpEffect() {
        if (mc.player.isPotionActive(Potion.getPotionById(8)))
            return mc.player.getActivePotionEffect(Potion.getPotionById(8)).getAmplifier() + 1;
        else
            return 0;
    }

    public static boolean isOnGround2(final double height) {
        return !mc.world.getEntitiesWithinAABBExcludingEntity(mc.player, mc.player.getEntityBoundingBox().offset(0.0, -height, 0.0)).isEmpty();
    }

    public static boolean isOnGround(double height) {
        return mc.world.getEntitiesWithinAABBExcludingEntity(mc.player, mc.player.getEntityBoundingBox().offset(0.0D, height, 0.0D)).isEmpty();
    }

    public static boolean isOnGround(final double motionX, final double motionZ) {
        return mc.world.getEntitiesWithinAABBExcludingEntity(mc.player, mc.player.getEntityBoundingBox().offset(motionX, 0.001, motionZ)).isEmpty();
    }

    public static double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;
        if (mc.player.isPotionActive(Potion.getPotionById(1))) {
            final int amplifier = mc.player.getActivePotionEffect(Potion.getPotionById(1)).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        return baseSpeed;
    }

    public static void setSpeed(EventMove2 moveEvent, double moveSpeed, float pseudoYaw, double pseudoStrafe, double pseudoForward) {
        double forward = pseudoForward;
        double strafe = pseudoStrafe;
        float yaw = pseudoYaw;
        if (forward != 0.0D) {
            if (strafe > 0.0D) {
                yaw += ((forward > 0.0D) ? -45 : 45);
            } else if (strafe < 0.0D) {
                yaw += ((forward > 0.0D) ? 45 : -45);
            }
            strafe = 0.0D;
            if (forward > 0.0D) {
                forward = 1.0D;
            } else if (forward < 0.0D) {
                forward = -1.0D;
            }
        }
        if (strafe > 0.0D) {
            strafe = 1.0D;
        } else if (strafe < 0.0D) {
            strafe = -1.0D;
        }
        double mx = Math.cos(Math.toRadians((yaw + 90.0F)));
        double mz = Math.sin(Math.toRadians((yaw + 90.0F)));
        moveEvent.setX(forward * moveSpeed * mx + strafe * moveSpeed * mz);
        moveEvent.setZ(forward * moveSpeed * mz - strafe * moveSpeed * mx);
    }

    public static void setMotion(EventMove2 event, double speed) {
        double forward = MovementInput.moveForward;
        double strafe = MovementInput.moveStrafe;
        float yaw = mc.player.rotationYaw;
        if (forward == 0.0D && strafe == 0.0D) {
            event.setX(0.0D);
            event.setZ(0.0D);
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
            event.setX(forward * speed * Math.cos(Math.toRadians(yaw + 90.0F)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0F)));
            event.setZ(forward * speed * Math.sin(Math.toRadians(yaw + 90.0F)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0F)));
        }
    }

    public static void setMotion(double speed) {
        double forward = MovementInput.moveForward;
        double strafe = MovementInput.moveStrafe;
        float yaw = mc.player.rotationYaw;
        if ((forward == 0.0D) && (strafe == 0.0D)) {
            mc.player.motionX = 0;
            mc.player.motionZ = 0;
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
            mc.player.motionX = forward * speed * Math.cos(Math.toRadians(yaw + 90.0F)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0F));
            mc.player.motionZ = forward * speed * Math.sin(Math.toRadians(yaw + 90.0F)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0F));
        }
    }

    public static boolean c() {
        for (int i = (int) Math.ceil(mc.player.posY); i >= 0; --i) {
            if (mc.world.getBlockState(new BlockPos(mc.player.posX, i, mc.player.posZ)).getBlock() != Blocks.AIR) {
                return false;
            }
        }
        return true;
    }

    public static float a(final double n, final double n2) {
        return (float) (Math.atan2(n2 - mc.player.posZ, n - mc.player.posX) * 180.0 / 3.141592653589793) - 90.0f;
    }

    public static Block getBlockUnderPlayer(EntityPlayer inPlayer, double height) {
        return mc.world.getBlockState(new BlockPos(inPlayer.posX, inPlayer.posY - height, inPlayer.posZ)).getBlock();
    }
}
