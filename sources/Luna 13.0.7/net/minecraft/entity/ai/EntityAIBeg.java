package net.minecraft.entity.ai;

import java.util.Random;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityAIBeg
  extends EntityAIBase
{
  private EntityWolf theWolf;
  private EntityPlayer thePlayer;
  private World worldObject;
  private float minPlayerDistance;
  private int field_75384_e;
  private static final String __OBFID = "CL_00001576";
  
  public EntityAIBeg(EntityWolf p_i1617_1_, float p_i1617_2_)
  {
    this.theWolf = p_i1617_1_;
    this.worldObject = p_i1617_1_.worldObj;
    this.minPlayerDistance = p_i1617_2_;
    setMutexBits(2);
  }
  
  public boolean shouldExecute()
  {
    this.thePlayer = this.worldObject.getClosestPlayerToEntity(this.theWolf, this.minPlayerDistance);
    return this.thePlayer == null ? false : hasPlayerGotBoneInHand(this.thePlayer);
  }
  
  public boolean continueExecuting()
  {
    return this.thePlayer.isEntityAlive();
  }
  
  public void startExecuting()
  {
    this.theWolf.func_70918_i(true);
    this.field_75384_e = (40 + this.theWolf.getRNG().nextInt(40));
  }
  
  public void resetTask()
  {
    this.theWolf.func_70918_i(false);
    this.thePlayer = null;
  }
  
  public void updateTask()
  {
    this.theWolf.getLookHelper().setLookPosition(this.thePlayer.posX, this.thePlayer.posY + this.thePlayer.getEyeHeight(), this.thePlayer.posZ, 10.0F, this.theWolf.getVerticalFaceSpeed());
    this.field_75384_e -= 1;
  }
  
  private boolean hasPlayerGotBoneInHand(EntityPlayer p_75382_1_)
  {
    ItemStack var2 = p_75382_1_.inventory.getCurrentItem();
    return (!this.theWolf.isTamed()) && (var2.getItem() == Items.bone) ? true : var2 == null ? false : this.theWolf.isBreedingItem(var2);
  }
}
