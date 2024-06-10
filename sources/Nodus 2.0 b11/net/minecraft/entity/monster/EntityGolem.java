/*  1:   */ package net.minecraft.entity.monster;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.EntityCreature;
/*  4:   */ import net.minecraft.entity.passive.IAnimals;
/*  5:   */ import net.minecraft.world.World;
/*  6:   */ 
/*  7:   */ public abstract class EntityGolem
/*  8:   */   extends EntityCreature
/*  9:   */   implements IAnimals
/* 10:   */ {
/* 11:   */   private static final String __OBFID = "CL_00001644";
/* 12:   */   
/* 13:   */   public EntityGolem(World par1World)
/* 14:   */   {
/* 15:13 */     super(par1World);
/* 16:   */   }
/* 17:   */   
/* 18:   */   protected void fall(float par1) {}
/* 19:   */   
/* 20:   */   protected String getLivingSound()
/* 21:   */   {
/* 22:26 */     return "none";
/* 23:   */   }
/* 24:   */   
/* 25:   */   protected String getHurtSound()
/* 26:   */   {
/* 27:34 */     return "none";
/* 28:   */   }
/* 29:   */   
/* 30:   */   protected String getDeathSound()
/* 31:   */   {
/* 32:42 */     return "none";
/* 33:   */   }
/* 34:   */   
/* 35:   */   public int getTalkInterval()
/* 36:   */   {
/* 37:50 */     return 120;
/* 38:   */   }
/* 39:   */   
/* 40:   */   protected boolean canDespawn()
/* 41:   */   {
/* 42:58 */     return false;
/* 43:   */   }
/* 44:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.monster.EntityGolem
 * JD-Core Version:    0.7.0.1
 */