/*  1:   */ package net.minecraft.block.material;
/*  2:   */ 
/*  3:   */ public class MaterialLiquid
/*  4:   */   extends Material
/*  5:   */ {
/*  6:   */   private static final String __OBFID = "CL_00000541";
/*  7:   */   
/*  8:   */   public MaterialLiquid(MapColor par1MapColor)
/*  9:   */   {
/* 10: 9 */     super(par1MapColor);
/* 11:10 */     setReplaceable();
/* 12:11 */     setNoPushMobility();
/* 13:   */   }
/* 14:   */   
/* 15:   */   public boolean isLiquid()
/* 16:   */   {
/* 17:19 */     return true;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public boolean blocksMovement()
/* 21:   */   {
/* 22:27 */     return false;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public boolean isSolid()
/* 26:   */   {
/* 27:32 */     return false;
/* 28:   */   }
/* 29:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.material.MaterialLiquid
 * JD-Core Version:    0.7.0.1
 */