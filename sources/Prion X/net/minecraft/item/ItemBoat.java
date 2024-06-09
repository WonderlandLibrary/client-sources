package net.minecraft.item;

import java.util.List;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemBoat extends Item
{
  private static final String __OBFID = "CL_00001774";
  
  public ItemBoat()
  {
    maxStackSize = 1;
    setCreativeTab(CreativeTabs.tabTransport);
  }
  



  public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
  {
    float var4 = 1.0F;
    float var5 = prevRotationPitch + (rotationPitch - prevRotationPitch) * var4;
    float var6 = prevRotationYaw + (rotationYaw - prevRotationYaw) * var4;
    double var7 = prevPosX + (posX - prevPosX) * var4;
    double var9 = prevPosY + (posY - prevPosY) * var4 + playerIn.getEyeHeight();
    double var11 = prevPosZ + (posZ - prevPosZ) * var4;
    Vec3 var13 = new Vec3(var7, var9, var11);
    float var14 = MathHelper.cos(-var6 * 0.017453292F - 3.1415927F);
    float var15 = MathHelper.sin(-var6 * 0.017453292F - 3.1415927F);
    float var16 = -MathHelper.cos(-var5 * 0.017453292F);
    float var17 = MathHelper.sin(-var5 * 0.017453292F);
    float var18 = var15 * var16;
    float var20 = var14 * var16;
    double var21 = 5.0D;
    Vec3 var23 = var13.addVector(var18 * var21, var17 * var21, var20 * var21);
    MovingObjectPosition var24 = worldIn.rayTraceBlocks(var13, var23, true);
    
    if (var24 == null)
    {
      return itemStackIn;
    }
    

    Vec3 var25 = playerIn.getLook(var4);
    boolean var26 = false;
    float var27 = 1.0F;
    List var28 = worldIn.getEntitiesWithinAABBExcludingEntity(playerIn, playerIn.getEntityBoundingBox().addCoord(xCoord * var21, yCoord * var21, zCoord * var21).expand(var27, var27, var27));
    
    for (int var29 = 0; var29 < var28.size(); var29++)
    {
      Entity var30 = (Entity)var28.get(var29);
      
      if (var30.canBeCollidedWith())
      {
        float var31 = var30.getCollisionBorderSize();
        AxisAlignedBB var32 = var30.getEntityBoundingBox().expand(var31, var31, var31);
        
        if (var32.isVecInside(var13))
        {
          var26 = true;
        }
      }
    }
    
    if (var26)
    {
      return itemStackIn;
    }
    

    if (typeOfHit == net.minecraft.util.MovingObjectPosition.MovingObjectType.BLOCK)
    {
      BlockPos var33 = var24.func_178782_a();
      
      if (worldIn.getBlockState(var33).getBlock() == net.minecraft.init.Blocks.snow_layer)
      {
        var33 = var33.offsetDown();
      }
      
      EntityBoat var34 = new EntityBoat(worldIn, var33.getX() + 0.5F, var33.getY() + 1.0F, var33.getZ() + 0.5F);
      rotationYaw = (((MathHelper.floor_double(rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3) - 1) * 90);
      
      if (!worldIn.getCollidingBoundingBoxes(var34, var34.getEntityBoundingBox().expand(-0.1D, -0.1D, -0.1D)).isEmpty())
      {
        return itemStackIn;
      }
      
      if (!isRemote)
      {
        worldIn.spawnEntityInWorld(var34);
      }
      
      if (!capabilities.isCreativeMode)
      {
        stackSize -= 1;
      }
      
      playerIn.triggerAchievement(net.minecraft.stats.StatList.objectUseStats[Item.getIdFromItem(this)]);
    }
    
    return itemStackIn;
  }
}
