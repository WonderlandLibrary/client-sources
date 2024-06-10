/*  1:   */ package net.minecraft.entity.ai;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.BlockDoor;
/*  4:   */ import net.minecraft.entity.EntityLiving;
/*  5:   */ 
/*  6:   */ public class EntityAIOpenDoor
/*  7:   */   extends EntityAIDoorInteract
/*  8:   */ {
/*  9:   */   boolean field_75361_i;
/* 10:   */   int field_75360_j;
/* 11:   */   private static final String __OBFID = "CL_00001603";
/* 12:   */   
/* 13:   */   public EntityAIOpenDoor(EntityLiving par1EntityLiving, boolean par2)
/* 14:   */   {
/* 15:13 */     super(par1EntityLiving);
/* 16:14 */     this.theEntity = par1EntityLiving;
/* 17:15 */     this.field_75361_i = par2;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public boolean continueExecuting()
/* 21:   */   {
/* 22:23 */     return (this.field_75361_i) && (this.field_75360_j > 0) && (super.continueExecuting());
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void startExecuting()
/* 26:   */   {
/* 27:31 */     this.field_75360_j = 20;
/* 28:32 */     this.field_151504_e.func_150014_a(this.theEntity.worldObj, this.entityPosX, this.entityPosY, this.entityPosZ, true);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void resetTask()
/* 32:   */   {
/* 33:40 */     if (this.field_75361_i) {
/* 34:42 */       this.field_151504_e.func_150014_a(this.theEntity.worldObj, this.entityPosX, this.entityPosY, this.entityPosZ, false);
/* 35:   */     }
/* 36:   */   }
/* 37:   */   
/* 38:   */   public void updateTask()
/* 39:   */   {
/* 40:51 */     this.field_75360_j -= 1;
/* 41:52 */     super.updateTask();
/* 42:   */   }
/* 43:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.EntityAIOpenDoor
 * JD-Core Version:    0.7.0.1
 */