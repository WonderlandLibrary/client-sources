package gq.vapu.czfclient.Util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.*;

public class MoveUtils {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static float getSpeed() {
        return (float) Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ);
    }

    public static double defaultSpeed() {
        double baseSpeed = 0.2873D;
        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            int amplifier = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0D + 0.2D * (double) (amplifier + 1);
        }

        return baseSpeed;
    }

    public static void strafe(double speed) {
        float a = mc.thePlayer.rotationYaw * 0.017453292F;
        float l = mc.thePlayer.rotationYaw * 0.017453292F - 4.712389F;
        float r = mc.thePlayer.rotationYaw * 0.017453292F + 4.712389F;
        float rf = mc.thePlayer.rotationYaw * 0.017453292F + 0.5969026F;
        float lf = mc.thePlayer.rotationYaw * 0.017453292F + -0.5969026F;
        float lb = mc.thePlayer.rotationYaw * 0.017453292F - 2.3876104F;
        float rb = mc.thePlayer.rotationYaw * 0.017453292F - -2.3876104F;
        if (mc.gameSettings.keyBindForward.pressed) {
            if (mc.gameSettings.keyBindLeft.pressed && !mc.gameSettings.keyBindRight.pressed) {
                mc.thePlayer.motionX -= (double) MathHelper.sin(lf) * speed;
                mc.thePlayer.motionZ += (double) MathHelper.cos(lf) * speed;
            } else if (mc.gameSettings.keyBindRight.pressed && !mc.gameSettings.keyBindLeft.pressed) {
                mc.thePlayer.motionX -= (double) MathHelper.sin(rf) * speed;
                mc.thePlayer.motionZ += (double) MathHelper.cos(rf) * speed;
            } else {
                mc.thePlayer.motionX -= (double) MathHelper.sin(a) * speed;
                mc.thePlayer.motionZ += (double) MathHelper.cos(a) * speed;
            }
        } else if (mc.gameSettings.keyBindBack.pressed) {
            if (mc.gameSettings.keyBindLeft.pressed && !mc.gameSettings.keyBindRight.pressed) {
                mc.thePlayer.motionX -= (double) MathHelper.sin(lb) * speed;
                mc.thePlayer.motionZ += (double) MathHelper.cos(lb) * speed;
            } else if (mc.gameSettings.keyBindRight.pressed && !mc.gameSettings.keyBindLeft.pressed) {
                mc.thePlayer.motionX -= (double) MathHelper.sin(rb) * speed;
                mc.thePlayer.motionZ += (double) MathHelper.cos(rb) * speed;
            } else {
                mc.thePlayer.motionX += (double) MathHelper.sin(a) * speed;
                mc.thePlayer.motionZ -= (double) MathHelper.cos(a) * speed;
            }
        } else if (mc.gameSettings.keyBindLeft.pressed && !mc.gameSettings.keyBindRight.pressed && !mc.gameSettings.keyBindForward.pressed && !mc.gameSettings.keyBindBack.pressed) {
            mc.thePlayer.motionX += (double) MathHelper.sin(l) * speed;
            mc.thePlayer.motionZ -= (double) MathHelper.cos(l) * speed;
        } else if (mc.gameSettings.keyBindRight.pressed && !mc.gameSettings.keyBindLeft.pressed && !mc.gameSettings.keyBindForward.pressed && !mc.gameSettings.keyBindBack.pressed) {
            mc.thePlayer.motionX += (double) MathHelper.sin(r) * speed;
            mc.thePlayer.motionZ -= (double) MathHelper.cos(r) * speed;
        }

    }

    public static void setMotion(double speed) {
        MovementInput var10000 = mc.thePlayer.movementInput;
        double forward = MovementInput.moveForward;
        var10000 = mc.thePlayer.movementInput;
        double strafe = MovementInput.moveStrafe;
        float yaw = mc.thePlayer.rotationYaw;
        if (forward == 0.0D && strafe == 0.0D) {
            mc.thePlayer.motionX = 0.0D;
            mc.thePlayer.motionZ = 0.0D;
        } else {
            if (forward != 0.0D) {
                if (strafe > 0.0D) {
                    yaw += (float) (forward > 0.0D ? -45 : 45);
                } else if (strafe < 0.0D) {
                    yaw += (float) (forward > 0.0D ? 45 : -45);
                }

                strafe = 0.0D;
                if (forward > 0.0D) {
                    forward = 1.0D;
                } else if (forward < 0.0D) {
                    forward = -1.0D;
                }
            }

            mc.thePlayer.motionX = forward * speed * Math.cos(Math.toRadians(yaw + 90.0F)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0F));
            mc.thePlayer.motionZ = forward * speed * Math.sin(Math.toRadians(yaw + 90.0F)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0F));
        }

    }

    public static boolean checkTeleport(double x, double y, double z, double distBetweenPackets) {
        double var10000 = mc.thePlayer.posX - x;
        var10000 = mc.thePlayer.posY - y;
        var10000 = mc.thePlayer.posZ - z;
        double dist = Math.sqrt(mc.thePlayer.getDistanceSq(x, y, z));
        double nbPackets = (double) (Math.round(dist / distBetweenPackets + 0.49999999999D) - 1L);
        double xtp = mc.thePlayer.posX;
        double ytp = mc.thePlayer.posY;
        double ztp = mc.thePlayer.posZ;

        for (int i = 1; (double) i < nbPackets; ++i) {
            double xdi = (x - mc.thePlayer.posX) / nbPackets;
            xtp += xdi;
            double zdi = (z - mc.thePlayer.posZ) / nbPackets;
            ztp += zdi;
            double ydi = (y - mc.thePlayer.posY) / nbPackets;
            ytp += ydi;
            AxisAlignedBB bb = new AxisAlignedBB(xtp - 0.3D, ytp, ztp - 0.3D, xtp + 0.3D, ytp + 1.8D, ztp + 0.3D);
            if (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb).isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public static boolean isOnGround(double height) {
        return !mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, -height, 0.0D)).isEmpty();
    }

    public static int getJumpEffect() {
        return mc.thePlayer.isPotionActive(Potion.jump) ? mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1 : 0;
    }

    public static int getSpeedEffect() {
        return mc.thePlayer.isPotionActive(Potion.moveSpeed) ? mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1 : 0;
    }

    public static Block getBlockUnderPlayer(EntityPlayer inPlayer, double height) {
        Minecraft.getMinecraft();
        return mc.theWorld.getBlockState(new BlockPos(inPlayer.posX, inPlayer.posY - height, inPlayer.posZ)).getBlock();
    }

    public static Block getBlockAtPosC(double x, double y, double z) {
        Minecraft.getMinecraft();
        EntityPlayer inPlayer = mc.thePlayer;
        Minecraft.getMinecraft();
        return mc.theWorld.getBlockState(new BlockPos(inPlayer.posX + x, inPlayer.posY + y, inPlayer.posZ + z)).getBlock();
    }

    public static float getDistanceToGround(Entity e) {
        if (mc.thePlayer.isCollidedVertically && mc.thePlayer.onGround) {
            return 0.0F;
        } else {
            for (float a = (float) e.posY; a > 0.0F; --a) {
                int[] stairs = new int[]{53, 67, 108, 109, 114, 128, 134, 135, 136, 156, 163, 164, 180};
                int[] exemptIds = new int[]{6, 27, 28, 30, 31, 32, 37, 38, 39, 40, 50, 51, 55, 59, 63, 65, 66, 68, 69, 70, 72, 75, 76, 77, 83, 92, 93, 94, 104, 105, 106, 115, 119, 131, 132, 143, 147, 148, 149, 150, 157, 171, 175, 176, 177};
                Block block = mc.theWorld.getBlockState(new BlockPos(e.posX, a - 1.0F, e.posZ)).getBlock();
                if (!(block instanceof BlockAir)) {
                    if (Block.getIdFromBlock(block) != 44 && Block.getIdFromBlock(block) != 126) {
                        for (int id : stairs) {
                            if (Block.getIdFromBlock(block) == id) {
                                return (float) (e.posY - (double) a - 1.0D) < 0.0F ? 0.0F : (float) (e.posY - (double) a - 1.0D);
                            }
                        }

                        for (int id : exemptIds) {
                            if (Block.getIdFromBlock(block) == id) {
                                return (float) (e.posY - (double) a) < 0.0F ? 0.0F : (float) (e.posY - (double) a);
                            }
                        }

                        return (float) (e.posY - (double) a + block.getBlockBoundsMaxY() - 1.0D);
                    }

                    return (float) (e.posY - (double) a - 0.5D) < 0.0F ? 0.0F : (float) (e.posY - (double) a - 0.5D);
                }
            }

            return 0.0F;
        }
    }

    public static float[] getRotationsBlock(BlockPos block, EnumFacing face) {
        double x = (double) block.getX() + 0.5D - mc.thePlayer.posX + (double) face.getFrontOffsetX() / 2.0D;
        double z = (double) block.getZ() + 0.5D - mc.thePlayer.posZ + (double) face.getFrontOffsetZ() / 2.0D;
        double y = (double) block.getY() + 0.5D;
        double d1 = mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight() - y;
        double d3 = MathHelper.sqrt_double(x * x + z * z);
        float yaw = (float) (Math.atan2(z, x) * 180.0D / 3.141592653589793D) - 90.0F;
        float pitch = (float) (Math.atan2(d1, d3) * 180.0D / 3.141592653589793D);
        if (yaw < 0.0F) {
            yaw += 360.0F;
        }

        return new float[]{yaw, pitch};
    }

    public static boolean isBlockAboveHead() {
        AxisAlignedBB bb = new AxisAlignedBB(mc.thePlayer.posX - 0.3D, mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ + 0.3D, mc.thePlayer.posX + 0.3D, mc.thePlayer.posY + 2.5D, mc.thePlayer.posZ - 0.3D);
        return !mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb).isEmpty();
    }

    public static boolean isCollidedH(double dist) {
        AxisAlignedBB bb = new AxisAlignedBB(mc.thePlayer.posX - 0.3D, mc.thePlayer.posY + 2.0D, mc.thePlayer.posZ + 0.3D, mc.thePlayer.posX + 0.3D, mc.thePlayer.posY + 3.0D, mc.thePlayer.posZ - 0.3D);
        return !mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb.offset(0.3D + dist, 0.0D, 0.0D)).isEmpty() || (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb.offset(-0.3D - dist, 0.0D, 0.0D)).isEmpty() || (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb.offset(0.0D, 0.0D, 0.3D + dist)).isEmpty() || !mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb.offset(0.0D, 0.0D, -0.3D - dist)).isEmpty()));
    }

    public static boolean isRealCollidedH(double dist) {
        AxisAlignedBB bb = new AxisAlignedBB(mc.thePlayer.posX - 0.3D, mc.thePlayer.posY + 0.5D, mc.thePlayer.posZ + 0.3D, mc.thePlayer.posX + 0.3D, mc.thePlayer.posY + 1.9D, mc.thePlayer.posZ - 0.3D);
        return !mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb.offset(0.3D + dist, 0.0D, 0.0D)).isEmpty() || (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb.offset(-0.3D - dist, 0.0D, 0.0D)).isEmpty() || (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb.offset(0.0D, 0.0D, 0.3D + dist)).isEmpty() || !mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb.offset(0.0D, 0.0D, -0.3D - dist)).isEmpty()));
    }


}
