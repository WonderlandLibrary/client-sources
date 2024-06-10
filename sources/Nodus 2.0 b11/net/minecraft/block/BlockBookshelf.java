/*  1:   */ package net.minecraft.block;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.block.material.Material;
/*  5:   */ import net.minecraft.creativetab.CreativeTabs;
/*  6:   */ import net.minecraft.init.Blocks;
/*  7:   */ import net.minecraft.init.Items;
/*  8:   */ import net.minecraft.item.Item;
/*  9:   */ import net.minecraft.util.IIcon;
/* 10:   */ 
/* 11:   */ public class BlockBookshelf
/* 12:   */   extends Block
/* 13:   */ {
/* 14:   */   private static final String __OBFID = "CL_00000206";
/* 15:   */   
/* 16:   */   public BlockBookshelf()
/* 17:   */   {
/* 18:17 */     super(Material.wood);
/* 19:18 */     setCreativeTab(CreativeTabs.tabBlock);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/* 23:   */   {
/* 24:26 */     return (p_149691_1_ != 1) && (p_149691_1_ != 0) ? super.getIcon(p_149691_1_, p_149691_2_) : Blocks.planks.getBlockTextureFromSide(p_149691_1_);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public int quantityDropped(Random p_149745_1_)
/* 28:   */   {
/* 29:34 */     return 3;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
/* 33:   */   {
/* 34:39 */     return Items.book;
/* 35:   */   }
/* 36:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockBookshelf
 * JD-Core Version:    0.7.0.1
 */