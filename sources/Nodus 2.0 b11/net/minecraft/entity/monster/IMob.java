/*  1:   */ package net.minecraft.entity.monster;
/*  2:   */ 
/*  3:   */ import net.minecraft.command.IEntitySelector;
/*  4:   */ import net.minecraft.entity.Entity;
/*  5:   */ import net.minecraft.entity.passive.IAnimals;
/*  6:   */ 
/*  7:   */ public abstract interface IMob
/*  8:   */   extends IAnimals
/*  9:   */ {
/* 10:10 */   public static final IEntitySelector mobSelector = new IEntitySelector()
/* 11:   */   {
/* 12:   */     private static final String __OBFID = "CL_00001688";
/* 13:   */     
/* 14:   */     public boolean isEntityApplicable(Entity par1Entity)
/* 15:   */     {
/* 16:15 */       return par1Entity instanceof IMob;
/* 17:   */     }
/* 18:   */   };
/* 19:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.monster.IMob
 * JD-Core Version:    0.7.0.1
 */