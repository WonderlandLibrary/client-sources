package zamorozka.ui;


import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class MoveUtilis {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static double defaultSpeed() {
        double baseSpeed = 0.2873D;
        if (Minecraft.player.isPotionActive(Potion.getPotionById(1))) {
            int amplifier = Minecraft.player.getActivePotionEffect(Potion.getPotionById(1)).getAmplifier();
            //  if(((Options) settings.get(MODE).getValue()).getSelected().equalsIgnoreCase("Hypixel")){
            // 	baseSpeed *= (1.0D + 0.225D * (amplifier + 1));
            // }else
            baseSpeed *= (1.0D + 0.2D * (amplifier + 1));
        }
        return baseSpeed;
    }

    public static void strafe(double speed) {
        float a = Minecraft.player.rotationYaw * 0.017453292F;
        float l = Minecraft.player.rotationYaw * 0.017453292F - (float) Math.PI * 1.5f;
        float r = Minecraft.player.rotationYaw * 0.017453292F + (float) Math.PI * 1.5f;
        float rf = Minecraft.player.rotationYaw * 0.017453292F + (float) Math.PI * 0.19f;
        float lf = Minecraft.player.rotationYaw * 0.017453292F + (float) Math.PI * -0.19f;
        float lb = Minecraft.player.rotationYaw * 0.017453292F - (float) Math.PI * 0.76f;
        float rb = Minecraft.player.rotationYaw * 0.017453292F - (float) Math.PI * -0.76f;
        if (mc.gameSettings.keyBindForward.pressed) {
            if (mc.gameSettings.keyBindLeft.pressed && !mc.gameSettings.keyBindRight.pressed) {
                Minecraft.player.motionX -= MathHelper.sin(lf) * speed;
                Minecraft.player.motionZ += MathHelper.cos(lf) * speed;
            } else if (mc.gameSettings.keyBindRight.pressed && !mc.gameSettings.keyBindLeft.pressed) {
                Minecraft.player.motionX -= MathHelper.sin(rf) * speed;
                Minecraft.player.motionZ += MathHelper.cos(rf) * speed;
            } else {
                Minecraft.player.motionX -= MathHelper.sin(a) * speed;
                Minecraft.player.motionZ += MathHelper.cos(a) * speed;
            }
        } else if (mc.gameSettings.keyBindBack.pressed) {
            if (mc.gameSettings.keyBindLeft.pressed && !mc.gameSettings.keyBindRight.pressed) {
                Minecraft.player.motionX -= MathHelper.sin(lb) * speed;
                Minecraft.player.motionZ += MathHelper.cos(lb) * speed;
            } else if (mc.gameSettings.keyBindRight.pressed && !mc.gameSettings.keyBindLeft.pressed) {
                Minecraft.player.motionX -= MathHelper.sin(rb) * speed;
                Minecraft.player.motionZ += MathHelper.cos(rb) * speed;
            } else {
                Minecraft.player.motionX += MathHelper.sin(a) * speed;
                Minecraft.player.motionZ -= MathHelper.cos(a) * speed;
            }
        } else if (mc.gameSettings.keyBindLeft.pressed && !mc.gameSettings.keyBindRight.pressed && !mc.gameSettings.keyBindForward.pressed && !mc.gameSettings.keyBindBack.pressed) {
            Minecraft.player.motionX += MathHelper.sin(l) * speed;
            Minecraft.player.motionZ -= MathHelper.cos(l) * speed;
        } else if (mc.gameSettings.keyBindRight.pressed && !mc.gameSettings.keyBindLeft.pressed && !mc.gameSettings.keyBindForward.pressed && !mc.gameSettings.keyBindBack.pressed) {
            Minecraft.player.motionX += MathHelper.sin(r) * speed;
            Minecraft.player.motionZ -= MathHelper.cos(r) * speed;
        }

    }

    public static void setMotion(double speed) {
        double forward = MovementInput.moveForward;
        double strafe = MovementInput.moveStrafe;
        float yaw = Minecraft.player.rotationYaw;
        if ((forward == 0.0D) && (strafe == 0.0D)) {
            Minecraft.player.motionX = 0;
            Minecraft.player.motionZ = 0;
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
            Minecraft.player.motionX = forward * speed * Math.cos(Math.toRadians(yaw + 90.0F)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0F));
            Minecraft.player.motionZ = forward * speed * Math.sin(Math.toRadians(yaw + 90.0F)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0F));
        }
    }

    public static boolean checkTeleport(double x, double y, double z, double distBetweenPackets) {
        double distx = Minecraft.player.posX - x;
        double disty = Minecraft.player.posY - y;
        double distz = Minecraft.player.posZ - z;
        double dist = Math.sqrt(Minecraft.player.getDistanceSq(x, y, z));
        double distanceEntreLesPackets = distBetweenPackets;
        double nbPackets = Math.round(dist / distanceEntreLesPackets + 0.49999999999) - 1;

        double xtp = Minecraft.player.posX;
        double ytp = Minecraft.player.posY;
        double ztp = Minecraft.player.posZ;
        for (int i = 1; i < nbPackets; i++) {
            double xdi = (x - Minecraft.player.posX) / (nbPackets);
            xtp += xdi;

            double zdi = (z - Minecraft.player.posZ) / (nbPackets);
            ztp += zdi;

            double ydi = (y - Minecraft.player.posY) / (nbPackets);
            ytp += ydi;
            AxisAlignedBB bb = new AxisAlignedBB(xtp - 0.3, ytp, ztp - 0.3, xtp + 0.3, ytp + 1.8, ztp + 0.3);
            if (!mc.world.getCollisionBoxes(Minecraft.player, bb).isEmpty()) {
                return false;
            }

        }
        return true;
    }


    public static boolean isOnGround(double height) {
        return !mc.world.getCollisionBoxes(Minecraft.player, Minecraft.player.getEntityBoundingBox().offset(0.0D, -height, 0.0D)).isEmpty();
    }

    public static Block getBlockUnderPlayer(EntityPlayer inPlayer, double height) {
        return Minecraft.getMinecraft().world.getBlockState(new BlockPos(inPlayer.posX, inPlayer.posY - height, inPlayer.posZ)).getBlock();
    }

    public static Block getBlockAtPosC(double x, double y, double z) {
        EntityPlayer inPlayer = Minecraft.player;
        return Minecraft.getMinecraft().world.getBlockState(new BlockPos(inPlayer.posX + x, inPlayer.posY + y, inPlayer.posZ + z)).getBlock();
    }

    public static float getDistanceToGround(Entity e) {
        if (Minecraft.player.isCollidedVertically && Minecraft.player.onGround) {
            return 0.0F;
        }
        for (float a = (float) e.posY; a > 0.0F; a -= 1.0F) {
            int[] stairs = {53, 67, 108, 109, 114, 128, 134, 135, 136, 156, 163, 164, 180};
            int[] exemptIds = {
                    6, 27, 28, 30, 31, 32, 37, 38, 39, 40, 50, 51, 55, 59,
                    63, 65, 66, 68, 69, 70, 72, 75, 76, 77, 83, 92, 93, 94,
                    104, 105, 106, 115, 119, 131, 132, 143, 147, 148, 149, 150,
                    157, 171, 175, 176, 177};
            Block block = mc.world.getBlockState(new BlockPos(e.posX, a - 1.0F, e.posZ)).getBlock();
            if (!(block instanceof BlockAir)) {
                if ((Block.getIdFromBlock(block) == 44) || (Block.getIdFromBlock(block) == 126)) {
                    return (float) (e.posY - a - 0.5D) < 0.0F ? 0.0F : (float) (e.posY - a - 0.5D);
                }
                int[] arrayOfInt1;
                int j = (arrayOfInt1 = stairs).length;
                for (int i = 0; i < j; i++) {
                    int id = arrayOfInt1[i];
                    if (Block.getIdFromBlock(block) == id) {
                        return (float) (e.posY - a - 1.0D) < 0.0F ? 0.0F : (float) (e.posY - a - 1.0D);
                    }
                }
                j = (arrayOfInt1 = exemptIds).length;
                for (int i = 0; i < j; i++) {
                    int id = arrayOfInt1[i];
                    if (Block.getIdFromBlock(block) == id) {
                        return (float) (e.posY - a) < 0.0F ? 0.0F : (float) (e.posY - a);
                    }
                }
                return (float) (e.posY - a + block.getBlockBoundsMaxY() - 1.0D);
            }
        }
        return 0.0F;
    }


    public static float[] getRotationsBlock(BlockPos block, EnumFacing face) {
        double x = block.getX() + 0.5 - Minecraft.player.posX + (double) face.getFrontOffsetX() / 2;
        double z = block.getZ() + 0.5 - Minecraft.player.posZ + (double) face.getFrontOffsetZ() / 2;
        double y = (block.getY() + 0.5);
        double d1 = Minecraft.player.posY + Minecraft.player.getEyeHeight() - y;
        double d3 = MathHelper.sqrt_double(x * x + z * z);
        float yaw = (float) (Math.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float) (Math.atan2(d1, d3) * 180.0D / Math.PI);
        if (yaw < 0.0F) {
            yaw += 360f;
        }
        return new float[]{yaw, pitch};
    }

    public static boolean isBlockAboveHead() {
        AxisAlignedBB bb = new AxisAlignedBB(Minecraft.player.posX - 0.3, Minecraft.player.posY + Minecraft.player.getEyeHeight(), Minecraft.player.posZ + 0.3,
                Minecraft.player.posX + 0.3, Minecraft.player.posY + 2.5, Minecraft.player.posZ - 0.3);
        return !mc.world.getCollisionBoxes(Minecraft.player, bb).isEmpty();
    }

    public static boolean isCollidedH(double dist) {
        AxisAlignedBB bb = new AxisAlignedBB(Minecraft.player.posX - 0.3, Minecraft.player.posY + 2, Minecraft.player.posZ + 0.3,
                Minecraft.player.posX + 0.3, Minecraft.player.posY + 3, Minecraft.player.posZ - 0.3);
        if (!mc.world.getCollisionBoxes(Minecraft.player, bb.offset(0.3 + dist, 0, 0)).isEmpty()) {
            return true;
        } else if (!mc.world.getCollisionBoxes(Minecraft.player, bb.offset(-0.3 - dist, 0, 0)).isEmpty()) {
            return true;
        } else if (!mc.world.getCollisionBoxes(Minecraft.player, bb.offset(0, 0, 0.3 + dist)).isEmpty()) {
            return true;
        } else return !mc.world.getCollisionBoxes(Minecraft.player, bb.offset(0, 0, -0.3 - dist)).isEmpty();
    }

    public static boolean isRealCollidedH(double dist) {
        AxisAlignedBB bb = new AxisAlignedBB(Minecraft.player.posX - 0.3, Minecraft.player.posY + 0.5, Minecraft.player.posZ + 0.3,
                Minecraft.player.posX + 0.3, Minecraft.player.posY + 1.9, Minecraft.player.posZ - 0.3);
        if (!mc.world.getCollisionBoxes(Minecraft.player, bb.offset(0.3 + dist, 0, 0)).isEmpty()) {
            return true;
        } else if (!mc.world.getCollisionBoxes(Minecraft.player, bb.offset(-0.3 - dist, 0, 0)).isEmpty()) {
            return true;
        } else if (!mc.world.getCollisionBoxes(Minecraft.player, bb.offset(0, 0, 0.3 + dist)).isEmpty()) {
            return true;
        } else return !mc.world.getCollisionBoxes(Minecraft.player, bb.offset(0, 0, -0.3 - dist)).isEmpty();
    }
}
