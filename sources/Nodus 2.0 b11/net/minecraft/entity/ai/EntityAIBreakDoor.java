/*  1:   */ package net.minecraft.entity.ai;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.block.Block;
/*  5:   */ import net.minecraft.block.BlockDoor;
/*  6:   */ import net.minecraft.entity.EntityLiving;
/*  7:   */ import net.minecraft.world.EnumDifficulty;
/*  8:   */ import net.minecraft.world.World;
/*  9:   */ 
/* 10:   */ public class EntityAIBreakDoor
/* 11:   */   extends EntityAIDoorInteract
/* 12:   */ {
/* 13:   */   private int breakingTime;
/* 14:10 */   private int field_75358_j = -1;
/* 15:   */   private static final String __OBFID = "CL_00001577";
/* 16:   */   
/* 17:   */   public EntityAIBreakDoor(EntityLiving par1EntityLiving)
/* 18:   */   {
/* 19:15 */     super(par1EntityLiving);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public boolean shouldExecute()
/* 23:   */   {
/* 24:23 */     return super.shouldExecute();
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void startExecuting()
/* 28:   */   {
/* 29:31 */     super.startExecuting();
/* 30:32 */     this.breakingTime = 0;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public boolean continueExecuting()
/* 34:   */   {
/* 35:40 */     double var1 = this.theEntity.getDistanceSq(this.entityPosX, this.entityPosY, this.entityPosZ);
/* 36:41 */     return (this.breakingTime <= 240) && (!this.field_151504_e.func_150015_f(this.theEntity.worldObj, this.entityPosX, this.entityPosY, this.entityPosZ)) && (var1 < 4.0D);
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void resetTask()
/* 40:   */   {
/* 41:49 */     super.resetTask();
/* 42:50 */     this.theEntity.worldObj.destroyBlockInWorldPartially(this.theEntity.getEntityId(), this.entityPosX, this.entityPosY, this.entityPosZ, -1);
/* 43:   */   }
/* 44:   */   
/* 45:   */   public void updateTask()
/* 46:   */   {
/* 47:58 */     super.updateTask();
/* 48:60 */     if (this.theEntity.getRNG().nextInt(20) == 0) {
/* 49:62 */       this.theEntity.worldObj.playAuxSFX(1010, this.entityPosX, this.entityPosY, this.entityPosZ, 0);
/* 50:   */     }
/* 51:65 */     this.breakingTime += 1;
/* 52:66 */     int var1 = (int)(this.breakingTime / 240.0F * 10.0F);
/* 53:68 */     if (var1 != this.field_75358_j)
/* 54:   */     {
/* 55:70 */       this.theEntity.worldObj.destroyBlockInWorldPartially(this.theEntity.getEntityId(), this.entityPosX, this.entityPosY, this.entityPosZ, var1);
/* 56:71 */       this.field_75358_j = var1;
/* 57:   */     }
/* 58:74 */     if ((this.breakingTime == 240) && (this.theEntity.worldObj.difficultySetting == EnumDifficulty.HARD))
/* 59:   */     {
/* 60:76 */       this.theEntity.worldObj.setBlockToAir(this.entityPosX, this.entityPosY, this.entityPosZ);
/* 61:77 */       this.theEntity.worldObj.playAuxSFX(1012, this.entityPosX, this.entityPosY, this.entityPosZ, 0);
/* 62:78 */       this.theEntity.worldObj.playAuxSFX(2001, this.entityPosX, this.entityPosY, this.entityPosZ, Block.getIdFromBlock(this.field_151504_e));
/* 63:   */     }
/* 64:   */   }
/* 65:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.EntityAIBreakDoor
 * JD-Core Version:    0.7.0.1
 */