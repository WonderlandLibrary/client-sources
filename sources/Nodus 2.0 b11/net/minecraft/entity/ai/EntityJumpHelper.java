/*  1:   */ package net.minecraft.entity.ai;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.EntityLiving;
/*  4:   */ 
/*  5:   */ public class EntityJumpHelper
/*  6:   */ {
/*  7:   */   private EntityLiving entity;
/*  8:   */   private boolean isJumping;
/*  9:   */   private static final String __OBFID = "CL_00001571";
/* 10:   */   
/* 11:   */   public EntityJumpHelper(EntityLiving par1EntityLiving)
/* 12:   */   {
/* 13:13 */     this.entity = par1EntityLiving;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void setJumping()
/* 17:   */   {
/* 18:18 */     this.isJumping = true;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void doJump()
/* 22:   */   {
/* 23:26 */     this.entity.setJumping(this.isJumping);
/* 24:27 */     this.isJumping = false;
/* 25:   */   }
/* 26:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.EntityJumpHelper
 * JD-Core Version:    0.7.0.1
 */