/*  1:   */ package net.minecraft.block.material;
/*  2:   */ 
/*  3:   */ public class MaterialTransparent
/*  4:   */   extends Material
/*  5:   */ {
/*  6:   */   private static final String __OBFID = "CL_00000540";
/*  7:   */   
/*  8:   */   public MaterialTransparent(MapColor par1MapColor)
/*  9:   */   {
/* 10: 9 */     super(par1MapColor);
/* 11:10 */     setReplaceable();
/* 12:   */   }
/* 13:   */   
/* 14:   */   public boolean isSolid()
/* 15:   */   {
/* 16:15 */     return false;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public boolean getCanBlockGrass()
/* 20:   */   {
/* 21:23 */     return false;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public boolean blocksMovement()
/* 25:   */   {
/* 26:31 */     return false;
/* 27:   */   }
/* 28:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.material.MaterialTransparent
 * JD-Core Version:    0.7.0.1
 */