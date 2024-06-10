/*  1:   */ package net.minecraft.block.material;
/*  2:   */ 
/*  3:   */ public class MaterialPortal
/*  4:   */   extends Material
/*  5:   */ {
/*  6:   */   private static final String __OBFID = "CL_00000545";
/*  7:   */   
/*  8:   */   public MaterialPortal(MapColor par1MapColor)
/*  9:   */   {
/* 10: 9 */     super(par1MapColor);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public boolean isSolid()
/* 14:   */   {
/* 15:14 */     return false;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public boolean getCanBlockGrass()
/* 19:   */   {
/* 20:22 */     return false;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public boolean blocksMovement()
/* 24:   */   {
/* 25:30 */     return false;
/* 26:   */   }
/* 27:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.material.MaterialPortal
 * JD-Core Version:    0.7.0.1
 */