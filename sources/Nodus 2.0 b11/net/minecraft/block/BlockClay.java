/*  1:   */ package net.minecraft.block;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.block.material.Material;
/*  5:   */ import net.minecraft.creativetab.CreativeTabs;
/*  6:   */ import net.minecraft.init.Items;
/*  7:   */ import net.minecraft.item.Item;
/*  8:   */ 
/*  9:   */ public class BlockClay
/* 10:   */   extends Block
/* 11:   */ {
/* 12:   */   private static final String __OBFID = "CL_00000215";
/* 13:   */   
/* 14:   */   public BlockClay()
/* 15:   */   {
/* 16:15 */     super(Material.field_151571_B);
/* 17:16 */     setCreativeTab(CreativeTabs.tabBlock);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
/* 21:   */   {
/* 22:21 */     return Items.clay_ball;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public int quantityDropped(Random p_149745_1_)
/* 26:   */   {
/* 27:29 */     return 4;
/* 28:   */   }
/* 29:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockClay
 * JD-Core Version:    0.7.0.1
 */