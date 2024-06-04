package net.minecraft.entity.ai;

import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityAIBeg extends EntityAIBase
{
  private EntityWolf theWolf;
  private EntityPlayer thePlayer;
  private World worldObject;
  private float minPlayerDistance;
  private int field_75384_e;
  private static final String __OBFID = "CL_00001576";
  
  public EntityAIBeg(EntityWolf p_i1617_1_, float p_i1617_2_)
  {
    theWolf = p_i1617_1_;
    worldObject = worldObj;
    minPlayerDistance = p_i1617_2_;
    setMutexBits(2);
  }
  



  public boolean shouldExecute()
  {
    thePlayer = worldObject.getClosestPlayerToEntity(theWolf, minPlayerDistance);
    return thePlayer == null ? false : hasPlayerGotBoneInHand(thePlayer);
  }
  



  public boolean continueExecuting()
  {
    return thePlayer.isEntityAlive();
  }
  



  public void startExecuting()
  {
    theWolf.func_70918_i(true);
    field_75384_e = (40 + theWolf.getRNG().nextInt(40));
  }
  



  public void resetTask()
  {
    theWolf.func_70918_i(false);
    thePlayer = null;
  }
  



  public void updateTask()
  {
    theWolf.getLookHelper().setLookPosition(thePlayer.posX, thePlayer.posY + thePlayer.getEyeHeight(), thePlayer.posZ, 10.0F, theWolf.getVerticalFaceSpeed());
    field_75384_e -= 1;
  }
  



  private boolean hasPlayerGotBoneInHand(EntityPlayer p_75382_1_)
  {
    ItemStack var2 = inventory.getCurrentItem();
    return (!theWolf.isTamed()) && (var2.getItem() == Items.bone) ? true : var2 == null ? false : theWolf.isBreedingItem(var2);
  }
}
