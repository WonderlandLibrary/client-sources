/*  1:   */ package net.minecraft.entity.ai;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.entity.monster.EntityIronGolem;
/*  5:   */ import net.minecraft.entity.passive.EntityVillager;
/*  6:   */ import net.minecraft.util.AxisAlignedBB;
/*  7:   */ import net.minecraft.world.World;
/*  8:   */ 
/*  9:   */ public class EntityAILookAtVillager
/* 10:   */   extends EntityAIBase
/* 11:   */ {
/* 12:   */   private EntityIronGolem theGolem;
/* 13:   */   private EntityVillager theVillager;
/* 14:   */   private int lookTime;
/* 15:   */   private static final String __OBFID = "CL_00001602";
/* 16:   */   
/* 17:   */   public EntityAILookAtVillager(EntityIronGolem par1EntityIronGolem)
/* 18:   */   {
/* 19:15 */     this.theGolem = par1EntityIronGolem;
/* 20:16 */     setMutexBits(3);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public boolean shouldExecute()
/* 24:   */   {
/* 25:24 */     if (!this.theGolem.worldObj.isDaytime()) {
/* 26:26 */       return false;
/* 27:   */     }
/* 28:28 */     if (this.theGolem.getRNG().nextInt(8000) != 0) {
/* 29:30 */       return false;
/* 30:   */     }
/* 31:34 */     this.theVillager = ((EntityVillager)this.theGolem.worldObj.findNearestEntityWithinAABB(EntityVillager.class, this.theGolem.boundingBox.expand(6.0D, 2.0D, 6.0D), this.theGolem));
/* 32:35 */     return this.theVillager != null;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public boolean continueExecuting()
/* 36:   */   {
/* 37:44 */     return this.lookTime > 0;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public void startExecuting()
/* 41:   */   {
/* 42:52 */     this.lookTime = 400;
/* 43:53 */     this.theGolem.setHoldingRose(true);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void resetTask()
/* 47:   */   {
/* 48:61 */     this.theGolem.setHoldingRose(false);
/* 49:62 */     this.theVillager = null;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public void updateTask()
/* 53:   */   {
/* 54:70 */     this.theGolem.getLookHelper().setLookPositionWithEntity(this.theVillager, 30.0F, 30.0F);
/* 55:71 */     this.lookTime -= 1;
/* 56:   */   }
/* 57:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.EntityAILookAtVillager
 * JD-Core Version:    0.7.0.1
 */