/*  1:   */ package net.minecraft.entity.ai;
/*  2:   */ 
/*  3:   */ import java.util.Iterator;
/*  4:   */ import java.util.List;
/*  5:   */ import net.minecraft.entity.EntityCreature;
/*  6:   */ import net.minecraft.util.AABBPool;
/*  7:   */ import net.minecraft.util.AxisAlignedBB;
/*  8:   */ import net.minecraft.world.World;
/*  9:   */ 
/* 10:   */ public class EntityAIHurtByTarget
/* 11:   */   extends EntityAITarget
/* 12:   */ {
/* 13:   */   boolean entityCallsForHelp;
/* 14:   */   private int field_142052_b;
/* 15:   */   private static final String __OBFID = "CL_00001619";
/* 16:   */   
/* 17:   */   public EntityAIHurtByTarget(EntityCreature par1EntityCreature, boolean par2)
/* 18:   */   {
/* 19:16 */     super(par1EntityCreature, false);
/* 20:17 */     this.entityCallsForHelp = par2;
/* 21:18 */     setMutexBits(1);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public boolean shouldExecute()
/* 25:   */   {
/* 26:26 */     int var1 = this.taskOwner.func_142015_aE();
/* 27:27 */     return (var1 != this.field_142052_b) && (isSuitableTarget(this.taskOwner.getAITarget(), false));
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void startExecuting()
/* 31:   */   {
/* 32:35 */     this.taskOwner.setAttackTarget(this.taskOwner.getAITarget());
/* 33:36 */     this.field_142052_b = this.taskOwner.func_142015_aE();
/* 34:38 */     if (this.entityCallsForHelp)
/* 35:   */     {
/* 36:40 */       double var1 = getTargetDistance();
/* 37:41 */       List var3 = this.taskOwner.worldObj.getEntitiesWithinAABB(this.taskOwner.getClass(), AxisAlignedBB.getAABBPool().getAABB(this.taskOwner.posX, this.taskOwner.posY, this.taskOwner.posZ, this.taskOwner.posX + 1.0D, this.taskOwner.posY + 1.0D, this.taskOwner.posZ + 1.0D).expand(var1, 10.0D, var1));
/* 38:42 */       Iterator var4 = var3.iterator();
/* 39:44 */       while (var4.hasNext())
/* 40:   */       {
/* 41:46 */         EntityCreature var5 = (EntityCreature)var4.next();
/* 42:48 */         if ((this.taskOwner != var5) && (var5.getAttackTarget() == null) && (!var5.isOnSameTeam(this.taskOwner.getAITarget()))) {
/* 43:50 */           var5.setAttackTarget(this.taskOwner.getAITarget());
/* 44:   */         }
/* 45:   */       }
/* 46:   */     }
/* 47:55 */     super.startExecuting();
/* 48:   */   }
/* 49:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.EntityAIHurtByTarget
 * JD-Core Version:    0.7.0.1
 */