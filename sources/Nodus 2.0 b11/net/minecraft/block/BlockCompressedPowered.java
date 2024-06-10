/*  1:   */ package net.minecraft.block;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.material.MapColor;
/*  4:   */ import net.minecraft.creativetab.CreativeTabs;
/*  5:   */ import net.minecraft.world.IBlockAccess;
/*  6:   */ 
/*  7:   */ public class BlockCompressedPowered
/*  8:   */   extends BlockCompressed
/*  9:   */ {
/* 10:   */   private static final String __OBFID = "CL_00000287";
/* 11:   */   
/* 12:   */   public BlockCompressedPowered(MapColor p_i45416_1_)
/* 13:   */   {
/* 14:13 */     super(p_i45416_1_);
/* 15:14 */     setCreativeTab(CreativeTabs.tabRedstone);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public boolean canProvidePower()
/* 19:   */   {
/* 20:22 */     return true;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public int isProvidingWeakPower(IBlockAccess p_149709_1_, int p_149709_2_, int p_149709_3_, int p_149709_4_, int p_149709_5_)
/* 24:   */   {
/* 25:27 */     return 15;
/* 26:   */   }
/* 27:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockCompressedPowered
 * JD-Core Version:    0.7.0.1
 */