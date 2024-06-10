/*  1:   */ package net.minecraft.block;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.block.material.MapColor;
/*  5:   */ import net.minecraft.block.material.Material;
/*  6:   */ import net.minecraft.creativetab.CreativeTabs;
/*  7:   */ import net.minecraft.init.Items;
/*  8:   */ import net.minecraft.item.Item;
/*  9:   */ import net.minecraft.util.MathHelper;
/* 10:   */ 
/* 11:   */ public class BlockGlowstone
/* 12:   */   extends Block
/* 13:   */ {
/* 14:   */   private static final String __OBFID = "CL_00000250";
/* 15:   */   
/* 16:   */   public BlockGlowstone(Material p_i45409_1_)
/* 17:   */   {
/* 18:17 */     super(p_i45409_1_);
/* 19:18 */     setCreativeTab(CreativeTabs.tabBlock);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public int quantityDroppedWithBonus(int p_149679_1_, Random p_149679_2_)
/* 23:   */   {
/* 24:26 */     return MathHelper.clamp_int(quantityDropped(p_149679_2_) + p_149679_2_.nextInt(p_149679_1_ + 1), 1, 4);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public int quantityDropped(Random p_149745_1_)
/* 28:   */   {
/* 29:34 */     return 2 + p_149745_1_.nextInt(3);
/* 30:   */   }
/* 31:   */   
/* 32:   */   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
/* 33:   */   {
/* 34:39 */     return Items.glowstone_dust;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public MapColor getMapColor(int p_149728_1_)
/* 38:   */   {
/* 39:44 */     return MapColor.field_151658_d;
/* 40:   */   }
/* 41:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockGlowstone
 * JD-Core Version:    0.7.0.1
 */