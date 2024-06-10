/*  1:   */ package net.minecraft.block;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.material.MapColor;
/*  4:   */ import net.minecraft.block.material.Material;
/*  5:   */ import net.minecraft.creativetab.CreativeTabs;
/*  6:   */ 
/*  7:   */ public class BlockCompressed
/*  8:   */   extends Block
/*  9:   */ {
/* 10:   */   private final MapColor field_150202_a;
/* 11:   */   private static final String __OBFID = "CL_00000268";
/* 12:   */   
/* 13:   */   public BlockCompressed(MapColor p_i45414_1_)
/* 14:   */   {
/* 15:14 */     super(Material.iron);
/* 16:15 */     this.field_150202_a = p_i45414_1_;
/* 17:16 */     setCreativeTab(CreativeTabs.tabBlock);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public MapColor getMapColor(int p_149728_1_)
/* 21:   */   {
/* 22:21 */     return this.field_150202_a;
/* 23:   */   }
/* 24:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockCompressed
 * JD-Core Version:    0.7.0.1
 */