/*  1:   */ package net.minecraft.entity.ai;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.passive.EntityTameable;
/*  4:   */ 
/*  5:   */ public class EntityAITargetNonTamed
/*  6:   */   extends EntityAINearestAttackableTarget
/*  7:   */ {
/*  8:   */   private EntityTameable theTameable;
/*  9:   */   private static final String __OBFID = "CL_00001623";
/* 10:   */   
/* 11:   */   public EntityAITargetNonTamed(EntityTameable par1EntityTameable, Class par2Class, int par3, boolean par4)
/* 12:   */   {
/* 13:12 */     super(par1EntityTameable, par2Class, par3, par4);
/* 14:13 */     this.theTameable = par1EntityTameable;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public boolean shouldExecute()
/* 18:   */   {
/* 19:21 */     return (!this.theTameable.isTamed()) && (super.shouldExecute());
/* 20:   */   }
/* 21:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.EntityAITargetNonTamed
 * JD-Core Version:    0.7.0.1
 */