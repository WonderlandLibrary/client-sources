package net.minecraft.entity.passive;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityMooshroom extends EntityCow
{
  private static final String __OBFID = "CL_00001645";
  
  public EntityMooshroom(World worldIn)
  {
    super(worldIn);
    setSize(0.9F, 1.3F);
    field_175506_bl = Blocks.mycelium;
  }
  



  public boolean interact(EntityPlayer p_70085_1_)
  {
    ItemStack var2 = inventory.getCurrentItem();
    
    if ((var2 != null) && (var2.getItem() == Items.bowl) && (getGrowingAge() >= 0))
    {
      if (stackSize == 1)
      {
        inventory.setInventorySlotContents(inventory.currentItem, new ItemStack(Items.mushroom_stew));
        return true;
      }
      
      if ((inventory.addItemStackToInventory(new ItemStack(Items.mushroom_stew))) && (!capabilities.isCreativeMode))
      {
        inventory.decrStackSize(inventory.currentItem, 1);
        return true;
      }
    }
    
    if ((var2 != null) && (var2.getItem() == Items.shears) && (getGrowingAge() >= 0))
    {
      setDead();
      worldObj.spawnParticle(net.minecraft.util.EnumParticleTypes.EXPLOSION_LARGE, posX, posY + height / 2.0F, posZ, 0.0D, 0.0D, 0.0D, new int[0]);
      
      if (!worldObj.isRemote)
      {
        EntityCow var3 = new EntityCow(worldObj);
        var3.setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
        var3.setHealth(getHealth());
        renderYawOffset = renderYawOffset;
        
        if (hasCustomName())
        {
          var3.setCustomNameTag(getCustomNameTag());
        }
        
        worldObj.spawnEntityInWorld(var3);
        
        for (int var4 = 0; var4 < 5; var4++)
        {
          worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX, posY + height, posZ, new ItemStack(Blocks.red_mushroom)));
        }
        
        var2.damageItem(1, p_70085_1_);
        playSound("mob.sheep.shear", 1.0F, 1.0F);
      }
      
      return true;
    }
    

    return super.interact(p_70085_1_);
  }
  

  public EntityMooshroom createChild(EntityAgeable p_90011_1_)
  {
    return new EntityMooshroom(worldObj);
  }
}
