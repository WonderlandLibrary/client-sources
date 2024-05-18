/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockDoor;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ 
/*     */ public class EntityAIBreakDoor extends EntityAIDoorInteract {
/*     */   private int breakingTime;
/*  11 */   private int previousBreakProgress = -1;
/*     */ 
/*     */   
/*     */   public EntityAIBreakDoor(EntityLiving entityIn) {
/*  15 */     super(entityIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldExecute() {
/*  23 */     if (!super.shouldExecute())
/*     */     {
/*  25 */       return false;
/*     */     }
/*  27 */     if (!this.theEntity.worldObj.getGameRules().getBoolean("mobGriefing"))
/*     */     {
/*  29 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  33 */     BlockDoor blockdoor = this.doorBlock;
/*  34 */     return !BlockDoor.isOpen((IBlockAccess)this.theEntity.worldObj, this.doorPosition);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startExecuting() {
/*  43 */     super.startExecuting();
/*  44 */     this.breakingTime = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean continueExecuting() {
/*  52 */     double d0 = this.theEntity.getDistanceSq(this.doorPosition);
/*     */ 
/*     */     
/*  55 */     if (this.breakingTime <= 240) {
/*     */       
/*  57 */       BlockDoor blockdoor = this.doorBlock;
/*     */       
/*  59 */       if (!BlockDoor.isOpen((IBlockAccess)this.theEntity.worldObj, this.doorPosition) && d0 < 4.0D) {
/*     */         
/*  61 */         boolean bool = true;
/*  62 */         return bool;
/*     */       } 
/*     */     } 
/*     */     
/*  66 */     boolean flag = false;
/*  67 */     return flag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetTask() {
/*  75 */     super.resetTask();
/*  76 */     this.theEntity.worldObj.sendBlockBreakProgress(this.theEntity.getEntityId(), this.doorPosition, -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTask() {
/*  84 */     super.updateTask();
/*     */     
/*  86 */     if (this.theEntity.getRNG().nextInt(20) == 0)
/*     */     {
/*  88 */       this.theEntity.worldObj.playAuxSFX(1010, this.doorPosition, 0);
/*     */     }
/*     */     
/*  91 */     this.breakingTime++;
/*  92 */     int i = (int)(this.breakingTime / 240.0F * 10.0F);
/*     */     
/*  94 */     if (i != this.previousBreakProgress) {
/*     */       
/*  96 */       this.theEntity.worldObj.sendBlockBreakProgress(this.theEntity.getEntityId(), this.doorPosition, i);
/*  97 */       this.previousBreakProgress = i;
/*     */     } 
/*     */     
/* 100 */     if (this.breakingTime == 240 && this.theEntity.worldObj.getDifficulty() == EnumDifficulty.HARD) {
/*     */       
/* 102 */       this.theEntity.worldObj.setBlockToAir(this.doorPosition);
/* 103 */       this.theEntity.worldObj.playAuxSFX(1012, this.doorPosition, 0);
/* 104 */       this.theEntity.worldObj.playAuxSFX(2001, this.doorPosition, Block.getIdFromBlock((Block)this.doorBlock));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\entity\ai\EntityAIBreakDoor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */