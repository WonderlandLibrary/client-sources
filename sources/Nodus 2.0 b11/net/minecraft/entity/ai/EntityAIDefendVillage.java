/*  1:   */ package net.minecraft.entity.ai;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.entity.EntityCreature;
/*  5:   */ import net.minecraft.entity.EntityLivingBase;
/*  6:   */ import net.minecraft.entity.monster.EntityIronGolem;
/*  7:   */ import net.minecraft.village.Village;
/*  8:   */ 
/*  9:   */ public class EntityAIDefendVillage
/* 10:   */   extends EntityAITarget
/* 11:   */ {
/* 12:   */   EntityIronGolem irongolem;
/* 13:   */   EntityLivingBase villageAgressorTarget;
/* 14:   */   private static final String __OBFID = "CL_00001618";
/* 15:   */   
/* 16:   */   public EntityAIDefendVillage(EntityIronGolem par1EntityIronGolem)
/* 17:   */   {
/* 18:19 */     super(par1EntityIronGolem, false, true);
/* 19:20 */     this.irongolem = par1EntityIronGolem;
/* 20:21 */     setMutexBits(1);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public boolean shouldExecute()
/* 24:   */   {
/* 25:29 */     Village var1 = this.irongolem.getVillage();
/* 26:31 */     if (var1 == null) {
/* 27:33 */       return false;
/* 28:   */     }
/* 29:37 */     this.villageAgressorTarget = var1.findNearestVillageAggressor(this.irongolem);
/* 30:39 */     if (!isSuitableTarget(this.villageAgressorTarget, false))
/* 31:   */     {
/* 32:41 */       if (this.taskOwner.getRNG().nextInt(20) == 0)
/* 33:   */       {
/* 34:43 */         this.villageAgressorTarget = var1.func_82685_c(this.irongolem);
/* 35:44 */         return isSuitableTarget(this.villageAgressorTarget, false);
/* 36:   */       }
/* 37:48 */       return false;
/* 38:   */     }
/* 39:53 */     return true;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void startExecuting()
/* 43:   */   {
/* 44:63 */     this.irongolem.setAttackTarget(this.villageAgressorTarget);
/* 45:64 */     super.startExecuting();
/* 46:   */   }
/* 47:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.EntityAIDefendVillage
 * JD-Core Version:    0.7.0.1
 */