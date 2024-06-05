package net.minecraft.entity.ai;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityAIBeg extends EntityAIBase {
   private EntityWolf theWolf;
   private EntityPlayer thePlayer;
   private World worldObject;
   private float minPlayerDistance;
   private int timeoutCounter;

   public EntityAIBeg(EntityWolf var1, float var2) {
      this.theWolf = var1;
      this.worldObject = var1.worldObj;
      this.minPlayerDistance = var2;
      this.setMutexBits(2);
   }

   public boolean shouldExecute() {
      this.thePlayer = this.worldObject.getClosestPlayerToEntity(this.theWolf, (double)this.minPlayerDistance);
      return this.thePlayer != null && this.hasPlayerGotBoneInHand(this.thePlayer);
   }

   public boolean continueExecuting() {
      return this.thePlayer.isEntityAlive() && this.theWolf.getDistanceSqToEntity(this.thePlayer) <= (double)(this.minPlayerDistance * this.minPlayerDistance) && this.timeoutCounter > 0 && this.hasPlayerGotBoneInHand(this.thePlayer);
   }

   public void startExecuting() {
      this.theWolf.setBegging(true);
      this.timeoutCounter = 40 + this.theWolf.getRNG().nextInt(40);
   }

   public void resetTask() {
      this.theWolf.setBegging(false);
      this.thePlayer = null;
   }

   public void updateTask() {
      this.theWolf.getLookHelper().setLookPosition(this.thePlayer.posX, this.thePlayer.posY + (double)this.thePlayer.getEyeHeight(), this.thePlayer.posZ, 10.0F, (float)this.theWolf.getVerticalFaceSpeed());
      --this.timeoutCounter;
   }

   private boolean hasPlayerGotBoneInHand(EntityPlayer var1) {
      ItemStack var2 = var1.inventory.getCurrentItem();
      return !this.theWolf.isTamed() && var2.getItem() == Items.bone || this.theWolf.isBreedingItem(var2);
   }
}
