/*  1:   */ package net.minecraft.block;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.material.Material;
/*  4:   */ 
/*  5:   */ public abstract class BlockDirectional
/*  6:   */   extends Block
/*  7:   */ {
/*  8:   */   private static final String __OBFID = "CL_00000227";
/*  9:   */   
/* 10:   */   protected BlockDirectional(Material p_i45401_1_)
/* 11:   */   {
/* 12:11 */     super(p_i45401_1_);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public static int func_149895_l(int p_149895_0_)
/* 16:   */   {
/* 17:16 */     return p_149895_0_ & 0x3;
/* 18:   */   }
/* 19:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockDirectional
 * JD-Core Version:    0.7.0.1
 */