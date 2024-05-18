package net.minecraft.item;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.entity.item.*;
import net.minecraft.stats.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.creativetab.*;

public class ItemBoat extends Item
{
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        final float n = 1.0f;
        final float n2 = entityPlayer.prevRotationPitch + (entityPlayer.rotationPitch - entityPlayer.prevRotationPitch) * n;
        final float n3 = entityPlayer.prevRotationYaw + (entityPlayer.rotationYaw - entityPlayer.prevRotationYaw) * n;
        final Vec3 vec3 = new Vec3(entityPlayer.prevPosX + (entityPlayer.posX - entityPlayer.prevPosX) * n, entityPlayer.prevPosY + (entityPlayer.posY - entityPlayer.prevPosY) * n + entityPlayer.getEyeHeight(), entityPlayer.prevPosZ + (entityPlayer.posZ - entityPlayer.prevPosZ) * n);
        final float cos = MathHelper.cos(-n3 * 0.017453292f - 3.1415927f);
        final float sin = MathHelper.sin(-n3 * 0.017453292f - 3.1415927f);
        final float n4 = -MathHelper.cos(-n2 * 0.017453292f);
        final float sin2 = MathHelper.sin(-n2 * 0.017453292f);
        final float n5 = sin * n4;
        final float n6 = cos * n4;
        final double n7 = 5.0;
        final MovingObjectPosition rayTraceBlocks = world.rayTraceBlocks(vec3, vec3.addVector(n5 * n7, sin2 * n7, n6 * n7), " ".length() != 0);
        if (rayTraceBlocks == null) {
            return itemStack;
        }
        final Vec3 look = entityPlayer.getLook(n);
        int n8 = "".length();
        final float n9 = 1.0f;
        final List<Entity> entitiesWithinAABBExcludingEntity = world.getEntitiesWithinAABBExcludingEntity(entityPlayer, entityPlayer.getEntityBoundingBox().addCoord(look.xCoord * n7, look.yCoord * n7, look.zCoord * n7).expand(n9, n9, n9));
        int i = "".length();
        "".length();
        if (4 < -1) {
            throw null;
        }
        while (i < entitiesWithinAABBExcludingEntity.size()) {
            final Entity entity = entitiesWithinAABBExcludingEntity.get(i);
            if (entity.canBeCollidedWith()) {
                final float collisionBorderSize = entity.getCollisionBorderSize();
                if (entity.getEntityBoundingBox().expand(collisionBorderSize, collisionBorderSize, collisionBorderSize).isVecInside(vec3)) {
                    n8 = " ".length();
                }
            }
            ++i;
        }
        if (n8 != 0) {
            return itemStack;
        }
        if (rayTraceBlocks.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            BlockPos blockPos = rayTraceBlocks.getBlockPos();
            if (world.getBlockState(blockPos).getBlock() == Blocks.snow_layer) {
                blockPos = blockPos.down();
            }
            final EntityBoat entityBoat = new EntityBoat(world, blockPos.getX() + 0.5f, blockPos.getY() + 1.0f, blockPos.getZ() + 0.5f);
            entityBoat.rotationYaw = ((MathHelper.floor_double(entityPlayer.rotationYaw * 4.0f / 360.0f + 0.5) & "   ".length()) - " ".length()) * (0xE4 ^ 0xBE);
            if (!world.getCollidingBoundingBoxes(entityBoat, entityBoat.getEntityBoundingBox().expand(-0.1, -0.1, -0.1)).isEmpty()) {
                return itemStack;
            }
            if (!world.isRemote) {
                world.spawnEntityInWorld(entityBoat);
            }
            if (!entityPlayer.capabilities.isCreativeMode) {
                itemStack.stackSize -= " ".length();
            }
            entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        }
        return itemStack;
    }
    
    public ItemBoat() {
        this.maxStackSize = " ".length();
        this.setCreativeTab(CreativeTabs.tabTransport);
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (2 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
}
