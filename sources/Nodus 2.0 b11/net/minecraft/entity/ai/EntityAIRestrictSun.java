/*  1:   */ package net.minecraft.entity.ai;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.EntityCreature;
/*  4:   */ import net.minecraft.pathfinding.PathNavigate;
/*  5:   */ import net.minecraft.world.World;
/*  6:   */ 
/*  7:   */ public class EntityAIRestrictSun
/*  8:   */   extends EntityAIBase
/*  9:   */ {
/* 10:   */   private EntityCreature theEntity;
/* 11:   */   private static final String __OBFID = "CL_00001611";
/* 12:   */   
/* 13:   */   public EntityAIRestrictSun(EntityCreature par1EntityCreature)
/* 14:   */   {
/* 15:12 */     this.theEntity = par1EntityCreature;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public boolean shouldExecute()
/* 19:   */   {
/* 20:20 */     return this.theEntity.worldObj.isDaytime();
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void startExecuting()
/* 24:   */   {
/* 25:28 */     this.theEntity.getNavigator().setAvoidSun(true);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void resetTask()
/* 29:   */   {
/* 30:36 */     this.theEntity.getNavigator().setAvoidSun(false);
/* 31:   */   }
/* 32:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.EntityAIRestrictSun
 * JD-Core Version:    0.7.0.1
 */