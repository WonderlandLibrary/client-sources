package net.minecraft.entity.ai;

import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityAIBeg extends EntityAIBase {
   private static final String __OBFID = "CL_00001576";
   private EntityWolf theWolf;
   private float minPlayerDistance;
   private World worldObject;
   private EntityPlayer thePlayer;
   private int field_75384_e;

   public boolean shouldExecute() {
      this.thePlayer = this.worldObject.getClosestPlayerToEntity(this.theWolf, (double)this.minPlayerDistance);
      return this.thePlayer == null ? false : this.hasPlayerGotBoneInHand(this.thePlayer);
   }

   public void resetTask() {
      this.theWolf.func_70918_i(false);
      this.thePlayer = null;
   }

   public void updateTask() {
      this.theWolf.getLookHelper().setLookPosition(this.thePlayer.posX, this.thePlayer.posY + (double)this.thePlayer.getEyeHeight(), this.thePlayer.posZ, 10.0F, (float)this.theWolf.getVerticalFaceSpeed());
      --this.field_75384_e;
   }

   public boolean continueExecuting() {
      return !this.thePlayer.isEntityAlive() ? false : (this.theWolf.getDistanceSqToEntity(this.thePlayer) > (double)(this.minPlayerDistance * this.minPlayerDistance) ? false : this.field_75384_e > 0 && this.hasPlayerGotBoneInHand(this.thePlayer));
   }

   private boolean hasPlayerGotBoneInHand(EntityPlayer var1) {
      ItemStack var2 = var1.inventory.getCurrentItem();
      return var2 == null ? false : (!this.theWolf.isTamed() && var2.getItem() == Items.bone ? true : this.theWolf.isBreedingItem(var2));
   }

   public void startExecuting() {
      this.theWolf.func_70918_i(true);
      this.field_75384_e = 40 + this.theWolf.getRNG().nextInt(40);
   }

   public EntityAIBeg(EntityWolf var1, float var2) {
      this.theWolf = var1;
      this.worldObject = var1.worldObj;
      this.minPlayerDistance = var2;
      this.setMutexBits(2);
   }
}
