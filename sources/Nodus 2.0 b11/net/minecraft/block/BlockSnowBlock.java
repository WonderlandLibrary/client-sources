/*  1:   */ package net.minecraft.block;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.block.material.Material;
/*  5:   */ import net.minecraft.creativetab.CreativeTabs;
/*  6:   */ import net.minecraft.init.Items;
/*  7:   */ import net.minecraft.item.Item;
/*  8:   */ import net.minecraft.world.EnumSkyBlock;
/*  9:   */ import net.minecraft.world.World;
/* 10:   */ 
/* 11:   */ public class BlockSnowBlock
/* 12:   */   extends Block
/* 13:   */ {
/* 14:   */   private static final String __OBFID = "CL_00000308";
/* 15:   */   
/* 16:   */   protected BlockSnowBlock()
/* 17:   */   {
/* 18:17 */     super(Material.craftedSnow);
/* 19:18 */     setTickRandomly(true);
/* 20:19 */     setCreativeTab(CreativeTabs.tabBlock);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
/* 24:   */   {
/* 25:24 */     return Items.snowball;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public int quantityDropped(Random p_149745_1_)
/* 29:   */   {
/* 30:32 */     return 4;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
/* 34:   */   {
/* 35:40 */     if (p_149674_1_.getSavedLightValue(EnumSkyBlock.Block, p_149674_2_, p_149674_3_, p_149674_4_) > 11)
/* 36:   */     {
/* 37:42 */       dropBlockAsItem(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_), 0);
/* 38:43 */       p_149674_1_.setBlockToAir(p_149674_2_, p_149674_3_, p_149674_4_);
/* 39:   */     }
/* 40:   */   }
/* 41:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockSnowBlock
 * JD-Core Version:    0.7.0.1
 */