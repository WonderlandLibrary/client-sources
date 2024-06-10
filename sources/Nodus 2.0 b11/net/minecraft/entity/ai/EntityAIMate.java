/*   1:    */ package net.minecraft.entity.ai;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.Random;
/*   6:    */ import net.minecraft.entity.EntityAgeable;
/*   7:    */ import net.minecraft.entity.item.EntityXPOrb;
/*   8:    */ import net.minecraft.entity.passive.EntityAnimal;
/*   9:    */ import net.minecraft.entity.passive.EntityCow;
/*  10:    */ import net.minecraft.entity.player.EntityPlayer;
/*  11:    */ import net.minecraft.pathfinding.PathNavigate;
/*  12:    */ import net.minecraft.stats.AchievementList;
/*  13:    */ import net.minecraft.stats.StatList;
/*  14:    */ import net.minecraft.util.AxisAlignedBB;
/*  15:    */ import net.minecraft.world.GameRules;
/*  16:    */ import net.minecraft.world.World;
/*  17:    */ 
/*  18:    */ public class EntityAIMate
/*  19:    */   extends EntityAIBase
/*  20:    */ {
/*  21:    */   private EntityAnimal theAnimal;
/*  22:    */   World theWorld;
/*  23:    */   private EntityAnimal targetMate;
/*  24:    */   int spawnBabyDelay;
/*  25:    */   double moveSpeed;
/*  26:    */   private static final String __OBFID = "CL_00001578";
/*  27:    */   
/*  28:    */   public EntityAIMate(EntityAnimal par1EntityAnimal, double par2)
/*  29:    */   {
/*  30: 32 */     this.theAnimal = par1EntityAnimal;
/*  31: 33 */     this.theWorld = par1EntityAnimal.worldObj;
/*  32: 34 */     this.moveSpeed = par2;
/*  33: 35 */     setMutexBits(3);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public boolean shouldExecute()
/*  37:    */   {
/*  38: 43 */     if (!this.theAnimal.isInLove()) {
/*  39: 45 */       return false;
/*  40:    */     }
/*  41: 49 */     this.targetMate = getNearbyMate();
/*  42: 50 */     return this.targetMate != null;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public boolean continueExecuting()
/*  46:    */   {
/*  47: 59 */     return (this.targetMate.isEntityAlive()) && (this.targetMate.isInLove()) && (this.spawnBabyDelay < 60);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void resetTask()
/*  51:    */   {
/*  52: 67 */     this.targetMate = null;
/*  53: 68 */     this.spawnBabyDelay = 0;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void updateTask()
/*  57:    */   {
/*  58: 76 */     this.theAnimal.getLookHelper().setLookPositionWithEntity(this.targetMate, 10.0F, this.theAnimal.getVerticalFaceSpeed());
/*  59: 77 */     this.theAnimal.getNavigator().tryMoveToEntityLiving(this.targetMate, this.moveSpeed);
/*  60: 78 */     this.spawnBabyDelay += 1;
/*  61: 80 */     if ((this.spawnBabyDelay >= 60) && (this.theAnimal.getDistanceSqToEntity(this.targetMate) < 9.0D)) {
/*  62: 82 */       spawnBaby();
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   private EntityAnimal getNearbyMate()
/*  67:    */   {
/*  68: 92 */     float var1 = 8.0F;
/*  69: 93 */     List var2 = this.theWorld.getEntitiesWithinAABB(this.theAnimal.getClass(), this.theAnimal.boundingBox.expand(var1, var1, var1));
/*  70: 94 */     double var3 = 1.7976931348623157E+308D;
/*  71: 95 */     EntityAnimal var5 = null;
/*  72: 96 */     Iterator var6 = var2.iterator();
/*  73: 98 */     while (var6.hasNext())
/*  74:    */     {
/*  75:100 */       EntityAnimal var7 = (EntityAnimal)var6.next();
/*  76:102 */       if ((this.theAnimal.canMateWith(var7)) && (this.theAnimal.getDistanceSqToEntity(var7) < var3))
/*  77:    */       {
/*  78:104 */         var5 = var7;
/*  79:105 */         var3 = this.theAnimal.getDistanceSqToEntity(var7);
/*  80:    */       }
/*  81:    */     }
/*  82:109 */     return var5;
/*  83:    */   }
/*  84:    */   
/*  85:    */   private void spawnBaby()
/*  86:    */   {
/*  87:117 */     EntityAgeable var1 = this.theAnimal.createChild(this.targetMate);
/*  88:119 */     if (var1 != null)
/*  89:    */     {
/*  90:121 */       EntityPlayer var2 = this.theAnimal.func_146083_cb();
/*  91:123 */       if ((var2 == null) && (this.targetMate.func_146083_cb() != null)) {
/*  92:125 */         var2 = this.targetMate.func_146083_cb();
/*  93:    */       }
/*  94:128 */       if (var2 != null)
/*  95:    */       {
/*  96:130 */         var2.triggerAchievement(StatList.field_151186_x);
/*  97:132 */         if ((this.theAnimal instanceof EntityCow)) {
/*  98:134 */           var2.triggerAchievement(AchievementList.field_150962_H);
/*  99:    */         }
/* 100:    */       }
/* 101:138 */       this.theAnimal.setGrowingAge(6000);
/* 102:139 */       this.targetMate.setGrowingAge(6000);
/* 103:140 */       this.theAnimal.resetInLove();
/* 104:141 */       this.targetMate.resetInLove();
/* 105:142 */       var1.setGrowingAge(-24000);
/* 106:143 */       var1.setLocationAndAngles(this.theAnimal.posX, this.theAnimal.posY, this.theAnimal.posZ, 0.0F, 0.0F);
/* 107:144 */       this.theWorld.spawnEntityInWorld(var1);
/* 108:145 */       Random var3 = this.theAnimal.getRNG();
/* 109:147 */       for (int var4 = 0; var4 < 7; var4++)
/* 110:    */       {
/* 111:149 */         double var5 = var3.nextGaussian() * 0.02D;
/* 112:150 */         double var7 = var3.nextGaussian() * 0.02D;
/* 113:151 */         double var9 = var3.nextGaussian() * 0.02D;
/* 114:152 */         this.theWorld.spawnParticle("heart", this.theAnimal.posX + var3.nextFloat() * this.theAnimal.width * 2.0F - this.theAnimal.width, this.theAnimal.posY + 0.5D + var3.nextFloat() * this.theAnimal.height, this.theAnimal.posZ + var3.nextFloat() * this.theAnimal.width * 2.0F - this.theAnimal.width, var5, var7, var9);
/* 115:    */       }
/* 116:155 */       if (this.theWorld.getGameRules().getGameRuleBooleanValue("doMobLoot")) {
/* 117:157 */         this.theWorld.spawnEntityInWorld(new EntityXPOrb(this.theWorld, this.theAnimal.posX, this.theAnimal.posY, this.theAnimal.posZ, var3.nextInt(7) + 1));
/* 118:    */       }
/* 119:    */     }
/* 120:    */   }
/* 121:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.EntityAIMate
 * JD-Core Version:    0.7.0.1
 */