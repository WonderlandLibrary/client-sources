package eze.util;

import net.minecraft.client.*;
import net.minecraft.block.*;
import org.lwjgl.input.*;
import net.minecraft.network.*;
import net.minecraft.inventory.*;
import net.minecraft.tileentity.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.client.entity.*;
import java.util.*;
import net.minecraft.entity.*;

public class RotationUtils2
{
    public static Minecraft mc;
    public float blockDamage;
    public float hitDelay;
    
    public boolean shouldHitBlock(final int x, final int y, final int z, final double dist) {
        final Block block = this.getBlock(x, y, z);
        final boolean isNotLiquid = !(block instanceof BlockLiquid);
        final boolean canSeeBlock = this.canBlockBeSeen(x, y, z);
        return isNotLiquid && canSeeBlock;
    }
    
    public void movePlayerToBlock(final double posX, final double posY, final double posZ, final float dist) {
        faceBlock(posX + 0.5, posY + 0.5, posZ + 0.5);
        this.moveForward();
        if (RotationUtils2.mc.thePlayer.onGround && RotationUtils2.mc.thePlayer.isCollidedHorizontally && !Keyboard.isKeyDown(RotationUtils2.mc.gameSettings.keyBindJump.getKeyCode()) && !RotationUtils2.mc.thePlayer.isInWater()) {
            RotationUtils2.mc.thePlayer.jump();
        }
        if (this.canReach(posX, posY, posZ, dist)) {
            this.stopMoving();
        }
    }
    
    public void placeBlock(final int posX, final int posY, final int posZ) {
        RotationUtils2.mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(this.getBlockPos(posX, posY, posZ), this.getEnumFacing((float)posX, (float)posY, (float)posZ).getIndex(), RotationUtils2.mc.thePlayer.getHeldItem(), 0.0f, 0.0f, 0.0f));
    }
    
    public int getNextSlotInContainer(final Container container) {
        for (int i = 0, slotAmount = (container.inventorySlots.size() == 90) ? 54 : 27; i < slotAmount; ++i) {
            if (container.getInventory().get(i) != null) {
                return i;
            }
        }
        return -1;
    }
    
    public boolean isContainerEmpty(final Container container) {
        for (int i = 0, slotAmount = (container.inventorySlots.size() == 90) ? 54 : 27; i < slotAmount; ++i) {
            if (container.getSlot(i).getHasStack()) {
                return false;
            }
        }
        return true;
    }
    
    public boolean canReach(final double posX, final double posY, final double posZ, final float distance) {
        final double blockDistance = this.getDistance(posX, posY, posZ);
        return blockDistance < distance && blockDistance > -distance;
    }
    
    public TileEntityChest getBestEntity(final double range) {
        TileEntityChest tempEntity = null;
        double dist = range;
        for (final Object i : RotationUtils2.mc.theWorld.loadedEntityList) {
            final TileEntityChest entity = (TileEntityChest)i;
            if (this.getDistanceToEntity(entity) <= 6.0f) {
                final double curDist = this.getDistanceToEntity(entity);
                if (curDist > dist) {
                    continue;
                }
                dist = curDist;
                tempEntity = entity;
            }
        }
        return tempEntity;
    }
    
    public float getDistanceToEntity(final TileEntity tileEntity) {
        final float var2 = (float)(RotationUtils2.mc.thePlayer.posX - tileEntity.getPos().getX());
        final float var3 = (float)(RotationUtils2.mc.thePlayer.posY - tileEntity.getPos().getY());
        final float var4 = (float)(RotationUtils2.mc.thePlayer.posZ - tileEntity.getPos().getZ());
        return MathHelper.sqrt_float(var2 * var2 + var3 * var3 + var4 * var4);
    }
    
    private Float[] getRotationsTileEntity(final TileEntity entity) {
        final double posX = entity.getPos().getX() - RotationUtils2.mc.thePlayer.posX;
        final double posZ = entity.getPos().getY() - RotationUtils2.mc.thePlayer.posZ;
        final double posY = entity.getPos().getZ() + 1 - RotationUtils2.mc.thePlayer.posY + RotationUtils2.mc.thePlayer.getEyeHeight();
        final double helper = MathHelper.sqrt_double(posX * posX + posZ * posZ);
        float newYaw = (float)Math.toDegrees(-Math.atan(posX / posZ));
        final float newPitch = (float)(-Math.toDegrees(Math.atan(posY / helper)));
        if (posZ < 0.0 && posX < 0.0) {
            newYaw = (float)(90.0 + Math.toDegrees(Math.atan(posZ / posX)));
        }
        else if (posZ < 0.0 && posX > 0.0) {
            newYaw = (float)(-90.0 + Math.toDegrees(Math.atan(posZ / posX)));
        }
        return new Float[] { newYaw, newPitch };
    }
    
    public void breakBlockLegit(final int posX, final int posY, final int posZ, final int delay) {
        ++this.hitDelay;
        RotationUtils2.mc.thePlayer.swingItem();
        if (this.blockDamage == 0.0f) {
            RotationUtils2.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, this.getBlockPos(posX, posY, posZ), this.getEnumFacing((float)posX, (float)posY, (float)posZ)));
        }
        if (this.hitDelay >= delay) {
            this.blockDamage += this.getBlock(posX, posY, posZ).getPlayerRelativeBlockHardness(RotationUtils2.mc.thePlayer, RotationUtils2.mc.theWorld, new BlockPos(posX, posY, posZ));
            RotationUtils2.mc.theWorld.sendBlockBreakProgress(RotationUtils2.mc.thePlayer.getEntityId(), new BlockPos(posX, posY, posZ), (int)(this.blockDamage * 10.0f) - 1);
            if (this.blockDamage >= (RotationUtils2.mc.playerController.isInCreativeMode() ? 0.0f : 1.0f)) {
                RotationUtils2.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.getBlockPos(posX, posY, posZ), this.getEnumFacing((float)posX, (float)posY, (float)posZ)));
                RotationUtils2.mc.playerController.func_178888_a(this.getBlockPos(posX, posY, posZ), this.getEnumFacing((float)posX, (float)posY, (float)posZ));
                this.blockDamage = 0.0f;
                this.hitDelay = 0.0f;
            }
        }
    }
    
    public boolean canBlockBeSeen(final int posX, final int posY, final int posZ) {
        final Vec3 player = new Vec3(RotationUtils2.mc.thePlayer.posX, RotationUtils2.mc.thePlayer.posY + RotationUtils2.mc.thePlayer.getEyeHeight(), RotationUtils2.mc.thePlayer.posZ);
        final Vec3 block = new Vec3(posX + 0.5f, posY + 0.5f, posZ + 0.5f);
        return (RotationUtils2.mc.theWorld.rayTraceBlocks(player, block) != null) ? Boolean.valueOf(RotationUtils2.mc.theWorld.rayTraceBlocks(player, block).field_178784_b != null) : null;
    }
    
    public void breakBlock(final double posX, final double posY, final double posZ) {
        RotationUtils2.mc.thePlayer.swingItem();
        RotationUtils2.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, this.getBlockPos(posX, posY, posZ), this.getEnumFacing((float)(int)posX, (float)(int)posY, (float)(int)posZ)));
        RotationUtils2.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.getBlockPos(posX, posY, posZ), this.getEnumFacing((float)(int)posX, (float)(int)posY, (float)(int)posZ)));
    }
    
    public EnumFacing getEnumFacing(final float posX, final float posY, final float posZ) {
        return EnumFacing.func_176737_a(posX, posY, posZ);
    }
    
    public BlockPos getBlockPos(final double x, final double y, final double z) {
        final BlockPos pos = new BlockPos(x, y, z);
        return pos;
    }
    
    public void moveForward() {
        RotationUtils2.mc.gameSettings.keyBindForward.pressed = true;
    }
    
    public void moveLeft() {
        RotationUtils2.mc.gameSettings.keyBindLeft.pressed = true;
    }
    
    public void moveRight() {
        RotationUtils2.mc.gameSettings.keyBindRight.pressed = true;
    }
    
    public void moveBack() {
        RotationUtils2.mc.gameSettings.keyBindBack.pressed = true;
    }
    
    public void stopMoving() {
        RotationUtils2.mc.gameSettings.keyBindForward.pressed = false;
        RotationUtils2.mc.gameSettings.keyBindLeft.pressed = false;
        RotationUtils2.mc.gameSettings.keyBindRight.pressed = false;
        RotationUtils2.mc.gameSettings.keyBindBack.pressed = false;
    }
    
    public Block getBlock(double posX, double posY, double posZ) {
        posX = MathHelper.floor_double(posX);
        posY = MathHelper.floor_double(posY);
        posZ = MathHelper.floor_double(posZ);
        return RotationUtils2.mc.theWorld.getChunkFromBlockCoords(new BlockPos(posX, posY, posZ)).getBlock(new BlockPos(posX, posY, posZ));
    }
    
    public float getDistance(final double x, final double y, final double z) {
        final float xDiff = (float)(RotationUtils2.mc.thePlayer.posX - x);
        final float yDiff = (float)(RotationUtils2.mc.thePlayer.posY - y);
        final float zDiff = (float)(RotationUtils2.mc.thePlayer.posZ - z);
        return MathHelper.sqrt_float(xDiff * xDiff + yDiff * yDiff + zDiff * zDiff);
    }
    
    public float getDistanceToVec(final double x, final double y, final double z, final double x2, final double y2, final double z2) {
        final float xDiff = (float)(x - x2);
        final float yDiff = (float)(y - y2);
        final float zDiff = (float)(z - z2);
        return MathHelper.sqrt_float(xDiff * xDiff + yDiff * yDiff + zDiff * zDiff);
    }
    
    public static float[] faceBlock(final double posX, final double posY, final double posZ) {
        final double diffX = posX - RotationUtils2.mc.thePlayer.posX;
        final double diffZ = posZ - RotationUtils2.mc.thePlayer.posZ;
        final double diffY = posY - RotationUtils2.mc.thePlayer.posY + RotationUtils2.mc.thePlayer.getEyeHeight();
        final double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0 / 3.141592653589793));
        float lyaw = RotationUtils2.mc.thePlayer.rotationYaw;
        float lpitch = RotationUtils2.mc.thePlayer.rotationPitch;
        final float[] array = new float[2];
        lyaw = (array[0] = lyaw + MathHelper.wrapAngleTo180_float(yaw - lyaw));
        lpitch = (array[1] = lpitch + MathHelper.wrapAngleTo180_float(pitch - lpitch));
        return array;
    }
    
    public boolean isVisibleFOV(final TileEntity tileEntity, final EntityPlayerSP thePlayer, final int fov) {
        return ((Math.abs(this.getRotationsTileEntity(tileEntity)[0] - thePlayer.rotationYaw) % 360.0f > 180.0f) ? (360.0f - Math.abs(this.getRotationsTileEntity(tileEntity)[0] - thePlayer.rotationYaw) % 360.0f) : (Math.abs(this.getRotationsTileEntity(tileEntity)[0] - thePlayer.rotationYaw) % 360.0f)) <= fov;
    }
    
    public static float[] getRotations(final EntityLivingBase ent) {
        final double x = ent.posX;
        final double z = ent.posZ;
        final double y = ent.posY + ent.getEyeHeight() / 2.0f - 0.5;
        return getRotationFromPosition(x, z, y);
    }
    
    public static float[] getAverageRotations(final List<EntityLivingBase> targetList) {
        double posX = 0.0;
        double posY = 0.0;
        double posZ = 0.0;
        for (final Entity ent : targetList) {
            posX += ent.posX;
            posY += ent.boundingBox.maxY - 2.0;
            posZ += ent.posZ;
        }
        posX /= targetList.size();
        posY /= targetList.size();
        posZ /= targetList.size();
        return new float[] { getRotationFromPosition(posX, posZ, posY)[0], getRotationFromPosition(posX, posZ, posY)[1] };
    }
    
    public static float[] getRotationFromPosition(final double x, final double z, final double y) {
        Minecraft.getMinecraft();
        final double xDiff = x - RotationUtils2.mc.thePlayer.posX;
        Minecraft.getMinecraft();
        final double zDiff = z - RotationUtils2.mc.thePlayer.posZ;
        Minecraft.getMinecraft();
        final double yDiff = y - RotationUtils2.mc.thePlayer.posY - 0.6;
        final double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
        final float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-(Math.atan2(yDiff, dist) * 180.0 / 3.141592653589793));
        return new float[] { yaw, pitch };
    }
    
    public static float getTrajAngleSolutionLow(final float d3, final float d1, final float velocity) {
        final float g = 0.006f;
        final float sqrt = velocity * velocity * velocity * velocity - 0.006f * (0.006f * d3 * d3 + 2.0f * d1 * velocity * velocity);
        return (float)Math.toDegrees(Math.atan((velocity * velocity - Math.sqrt(sqrt)) / (0.006f * d3)));
    }
    
    public static float getNewAngle(float angle) {
        angle %= 360.0f;
        if (angle >= 180.0f) {
            angle -= 360.0f;
        }
        if (angle < -180.0f) {
            angle += 360.0f;
        }
        return angle;
    }
    
    public static float getDistanceBetweenAngles(final float angle1, final float angle2) {
        float angle3 = Math.abs(angle1 - angle2) % 360.0f;
        if (angle3 > 180.0f) {
            angle3 = 360.0f - angle3;
        }
        return angle3;
    }
}
