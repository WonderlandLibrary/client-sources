/*  1:   */ package net.minecraft.block;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.block.material.Material;
/*  5:   */ import net.minecraft.creativetab.CreativeTabs;
/*  6:   */ 
/*  7:   */ public class BlockPackedIce
/*  8:   */   extends Block
/*  9:   */ {
/* 10:   */   private static final String __OBFID = "CL_00000283";
/* 11:   */   
/* 12:   */   public BlockPackedIce()
/* 13:   */   {
/* 14:13 */     super(Material.field_151598_x);
/* 15:14 */     this.slipperiness = 0.98F;
/* 16:15 */     setCreativeTab(CreativeTabs.tabBlock);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public int quantityDropped(Random p_149745_1_)
/* 20:   */   {
/* 21:23 */     return 0;
/* 22:   */   }
/* 23:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockPackedIce
 * JD-Core Version:    0.7.0.1
 */