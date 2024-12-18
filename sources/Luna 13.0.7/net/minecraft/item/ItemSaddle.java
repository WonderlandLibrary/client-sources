package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ItemSaddle
  extends Item
{
  private static final String __OBFID = "CL_00000059";
  
  public ItemSaddle()
  {
    this.maxStackSize = 1;
    setCreativeTab(CreativeTabs.tabTransport);
  }
  
  public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target)
  {
    if ((target instanceof EntityPig))
    {
      EntityPig var4 = (EntityPig)target;
      if ((!var4.getSaddled()) && (!var4.isChild()))
      {
        var4.setSaddled(true);
        var4.worldObj.playSoundAtEntity(var4, "mob.horse.leather", 0.5F, 1.0F);
        stack.stackSize -= 1;
      }
      return true;
    }
    return false;
  }
  
  public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
  {
    itemInteractionForEntity(stack, (EntityPlayer)null, target);
    return true;
  }
}
