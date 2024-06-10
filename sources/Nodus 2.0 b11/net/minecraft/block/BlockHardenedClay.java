/*  1:   */ package net.minecraft.block;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.material.MapColor;
/*  4:   */ import net.minecraft.block.material.Material;
/*  5:   */ import net.minecraft.creativetab.CreativeTabs;
/*  6:   */ 
/*  7:   */ public class BlockHardenedClay
/*  8:   */   extends Block
/*  9:   */ {
/* 10:   */   private static final String __OBFID = "CL_00000255";
/* 11:   */   
/* 12:   */   public BlockHardenedClay()
/* 13:   */   {
/* 14:13 */     super(Material.rock);
/* 15:14 */     setCreativeTab(CreativeTabs.tabBlock);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public MapColor getMapColor(int p_149728_1_)
/* 19:   */   {
/* 20:19 */     return MapColor.field_151676_q;
/* 21:   */   }
/* 22:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockHardenedClay
 * JD-Core Version:    0.7.0.1
 */