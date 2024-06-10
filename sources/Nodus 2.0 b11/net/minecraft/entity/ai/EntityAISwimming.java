/*  1:   */ package net.minecraft.entity.ai;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.entity.EntityLiving;
/*  5:   */ import net.minecraft.pathfinding.PathNavigate;
/*  6:   */ 
/*  7:   */ public class EntityAISwimming
/*  8:   */   extends EntityAIBase
/*  9:   */ {
/* 10:   */   private EntityLiving theEntity;
/* 11:   */   private static final String __OBFID = "CL_00001584";
/* 12:   */   
/* 13:   */   public EntityAISwimming(EntityLiving par1EntityLiving)
/* 14:   */   {
/* 15:12 */     this.theEntity = par1EntityLiving;
/* 16:13 */     setMutexBits(4);
/* 17:14 */     par1EntityLiving.getNavigator().setCanSwim(true);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public boolean shouldExecute()
/* 21:   */   {
/* 22:22 */     return (this.theEntity.isInWater()) || (this.theEntity.handleLavaMovement());
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void updateTask()
/* 26:   */   {
/* 27:30 */     if (this.theEntity.getRNG().nextFloat() < 0.8F) {
/* 28:32 */       this.theEntity.getJumpHelper().setJumping();
/* 29:   */     }
/* 30:   */   }
/* 31:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.EntityAISwimming
 * JD-Core Version:    0.7.0.1
 */