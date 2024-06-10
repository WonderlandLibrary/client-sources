/*  1:   */ package net.minecraft.block;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.block.material.Material;
/*  5:   */ import net.minecraft.creativetab.CreativeTabs;
/*  6:   */ 
/*  7:   */ public class BlockGlass
/*  8:   */   extends BlockBreakable
/*  9:   */ {
/* 10:   */   private static final String __OBFID = "CL_00000249";
/* 11:   */   
/* 12:   */   public BlockGlass(Material p_i45408_1_, boolean p_i45408_2_)
/* 13:   */   {
/* 14:13 */     super("glass", p_i45408_1_, p_i45408_2_);
/* 15:14 */     setCreativeTab(CreativeTabs.tabBlock);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public int quantityDropped(Random p_149745_1_)
/* 19:   */   {
/* 20:22 */     return 0;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public int getRenderBlockPass()
/* 24:   */   {
/* 25:30 */     return 0;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public boolean renderAsNormalBlock()
/* 29:   */   {
/* 30:35 */     return false;
/* 31:   */   }
/* 32:   */   
/* 33:   */   protected boolean canSilkHarvest()
/* 34:   */   {
/* 35:40 */     return true;
/* 36:   */   }
/* 37:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockGlass
 * JD-Core Version:    0.7.0.1
 */