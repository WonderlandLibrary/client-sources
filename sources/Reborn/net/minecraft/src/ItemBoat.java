package net.minecraft.src;

import java.util.*;

public class ItemBoat extends Item
{
    public ItemBoat(final int par1) {
        super(par1);
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.tabTransport);
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack par1ItemStack, final World par2World, final EntityPlayer par3EntityPlayer) {
        final float var4 = 1.0f;
        final float var5 = par3EntityPlayer.prevRotationPitch + (par3EntityPlayer.rotationPitch - par3EntityPlayer.prevRotationPitch) * var4;
        final float var6 = par3EntityPlayer.prevRotationYaw + (par3EntityPlayer.rotationYaw - par3EntityPlayer.prevRotationYaw) * var4;
        final double var7 = par3EntityPlayer.prevPosX + (par3EntityPlayer.posX - par3EntityPlayer.prevPosX) * var4;
        final double var8 = par3EntityPlayer.prevPosY + (par3EntityPlayer.posY - par3EntityPlayer.prevPosY) * var4 + 1.62 - par3EntityPlayer.yOffset;
        final double var9 = par3EntityPlayer.prevPosZ + (par3EntityPlayer.posZ - par3EntityPlayer.prevPosZ) * var4;
        final Vec3 var10 = par2World.getWorldVec3Pool().getVecFromPool(var7, var8, var9);
        final float var11 = MathHelper.cos(-var6 * 0.017453292f - 3.1415927f);
        final float var12 = MathHelper.sin(-var6 * 0.017453292f - 3.1415927f);
        final float var13 = -MathHelper.cos(-var5 * 0.017453292f);
        final float var14 = MathHelper.sin(-var5 * 0.017453292f);
        final float var15 = var12 * var13;
        final float var16 = var11 * var13;
        final double var17 = 5.0;
        final Vec3 var18 = var10.addVector(var15 * var17, var14 * var17, var16 * var17);
        final MovingObjectPosition var19 = par2World.rayTraceBlocks_do(var10, var18, true);
        if (var19 == null) {
            return par1ItemStack;
        }
        final Vec3 var20 = par3EntityPlayer.getLook(var4);
        boolean var21 = false;
        final float var22 = 1.0f;
        final List var23 = par2World.getEntitiesWithinAABBExcludingEntity(par3EntityPlayer, par3EntityPlayer.boundingBox.addCoord(var20.xCoord * var17, var20.yCoord * var17, var20.zCoord * var17).expand(var22, var22, var22));
        for (int var24 = 0; var24 < var23.size(); ++var24) {
            final Entity var25 = var23.get(var24);
            if (var25.canBeCollidedWith()) {
                final float var26 = var25.getCollisionBorderSize();
                final AxisAlignedBB var27 = var25.boundingBox.expand(var26, var26, var26);
                if (var27.isVecInside(var10)) {
                    var21 = true;
                }
            }
        }
        if (var21) {
            return par1ItemStack;
        }
        if (var19.typeOfHit == EnumMovingObjectType.TILE) {
            final int var24 = var19.blockX;
            int var28 = var19.blockY;
            final int var29 = var19.blockZ;
            if (par2World.getBlockId(var24, var28, var29) == Block.snow.blockID) {
                --var28;
            }
            final EntityBoat var30 = new EntityBoat(par2World, var24 + 0.5f, var28 + 1.0f, var29 + 0.5f);
            var30.rotationYaw = ((MathHelper.floor_double(par3EntityPlayer.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3) - 1) * 90;
            if (!par2World.getCollidingBoundingBoxes(var30, var30.boundingBox.expand(-0.1, -0.1, -0.1)).isEmpty()) {
                return par1ItemStack;
            }
            if (!par2World.isRemote) {
                par2World.spawnEntityInWorld(var30);
            }
            if (!par3EntityPlayer.capabilities.isCreativeMode) {
                --par1ItemStack.stackSize;
            }
        }
        return par1ItemStack;
    }
}
