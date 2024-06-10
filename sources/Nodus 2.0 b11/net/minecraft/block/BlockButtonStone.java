/*  1:   */ package net.minecraft.block;
/*  2:   */ 
/*  3:   */ import net.minecraft.init.Blocks;
/*  4:   */ import net.minecraft.util.IIcon;
/*  5:   */ 
/*  6:   */ public class BlockButtonStone
/*  7:   */   extends BlockButton
/*  8:   */ {
/*  9:   */   private static final String __OBFID = "CL_00000319";
/* 10:   */   
/* 11:   */   protected BlockButtonStone()
/* 12:   */   {
/* 13:12 */     super(false);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/* 17:   */   {
/* 18:20 */     return Blocks.stone.getBlockTextureFromSide(1);
/* 19:   */   }
/* 20:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockButtonStone
 * JD-Core Version:    0.7.0.1
 */