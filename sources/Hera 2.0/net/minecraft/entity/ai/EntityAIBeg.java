/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.passive.EntityWolf;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityAIBeg
/*    */   extends EntityAIBase {
/*    */   private EntityWolf theWolf;
/*    */   private EntityPlayer thePlayer;
/*    */   private World worldObject;
/*    */   private float minPlayerDistance;
/*    */   private int timeoutCounter;
/*    */   
/*    */   public EntityAIBeg(EntityWolf wolf, float minDistance) {
/* 19 */     this.theWolf = wolf;
/* 20 */     this.worldObject = wolf.worldObj;
/* 21 */     this.minPlayerDistance = minDistance;
/* 22 */     setMutexBits(2);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldExecute() {
/* 30 */     this.thePlayer = this.worldObject.getClosestPlayerToEntity((Entity)this.theWolf, this.minPlayerDistance);
/* 31 */     return (this.thePlayer == null) ? false : hasPlayerGotBoneInHand(this.thePlayer);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean continueExecuting() {
/* 39 */     return !this.thePlayer.isEntityAlive() ? false : ((this.theWolf.getDistanceSqToEntity((Entity)this.thePlayer) > (this.minPlayerDistance * this.minPlayerDistance)) ? false : ((this.timeoutCounter > 0 && hasPlayerGotBoneInHand(this.thePlayer))));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {
/* 47 */     this.theWolf.setBegging(true);
/* 48 */     this.timeoutCounter = 40 + this.theWolf.getRNG().nextInt(40);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void resetTask() {
/* 56 */     this.theWolf.setBegging(false);
/* 57 */     this.thePlayer = null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateTask() {
/* 65 */     this.theWolf.getLookHelper().setLookPosition(this.thePlayer.posX, this.thePlayer.posY + this.thePlayer.getEyeHeight(), this.thePlayer.posZ, 10.0F, this.theWolf.getVerticalFaceSpeed());
/* 66 */     this.timeoutCounter--;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private boolean hasPlayerGotBoneInHand(EntityPlayer player) {
/* 74 */     ItemStack itemstack = player.inventory.getCurrentItem();
/* 75 */     return (itemstack == null) ? false : ((!this.theWolf.isTamed() && itemstack.getItem() == Items.bone) ? true : this.theWolf.isBreedingItem(itemstack));
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\entity\ai\EntityAIBeg.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */