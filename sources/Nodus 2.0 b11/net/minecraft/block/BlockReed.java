/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.material.Material;
/*   5:    */ import net.minecraft.init.Blocks;
/*   6:    */ import net.minecraft.init.Items;
/*   7:    */ import net.minecraft.item.Item;
/*   8:    */ import net.minecraft.util.AxisAlignedBB;
/*   9:    */ import net.minecraft.world.IBlockAccess;
/*  10:    */ import net.minecraft.world.World;
/*  11:    */ import net.minecraft.world.biome.BiomeGenBase;
/*  12:    */ 
/*  13:    */ public class BlockReed
/*  14:    */   extends Block
/*  15:    */ {
/*  16:    */   private static final String __OBFID = "CL_00000300";
/*  17:    */   
/*  18:    */   protected BlockReed()
/*  19:    */   {
/*  20: 18 */     super(Material.plants);
/*  21: 19 */     float var1 = 0.375F;
/*  22: 20 */     setBlockBounds(0.5F - var1, 0.0F, 0.5F - var1, 0.5F + var1, 1.0F, 0.5F + var1);
/*  23: 21 */     setTickRandomly(true);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
/*  27:    */   {
/*  28: 29 */     if ((p_149674_1_.getBlock(p_149674_2_, p_149674_3_ - 1, p_149674_4_) == Blocks.reeds) || (func_150170_e(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_))) {
/*  29: 31 */       if (p_149674_1_.isAirBlock(p_149674_2_, p_149674_3_ + 1, p_149674_4_))
/*  30:    */       {
/*  31: 35 */         for (int var6 = 1; p_149674_1_.getBlock(p_149674_2_, p_149674_3_ - var6, p_149674_4_) == this; var6++) {}
/*  32: 40 */         if (var6 < 3)
/*  33:    */         {
/*  34: 42 */           int var7 = p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_);
/*  35: 44 */           if (var7 == 15)
/*  36:    */           {
/*  37: 46 */             p_149674_1_.setBlock(p_149674_2_, p_149674_3_ + 1, p_149674_4_, this);
/*  38: 47 */             p_149674_1_.setBlockMetadataWithNotify(p_149674_2_, p_149674_3_, p_149674_4_, 0, 4);
/*  39:    */           }
/*  40:    */           else
/*  41:    */           {
/*  42: 51 */             p_149674_1_.setBlockMetadataWithNotify(p_149674_2_, p_149674_3_, p_149674_4_, var7 + 1, 4);
/*  43:    */           }
/*  44:    */         }
/*  45:    */       }
/*  46:    */     }
/*  47:    */   }
/*  48:    */   
/*  49:    */   public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_)
/*  50:    */   {
/*  51: 60 */     Block var5 = p_149742_1_.getBlock(p_149742_2_, p_149742_3_ - 1, p_149742_4_);
/*  52: 61 */     return var5 == this;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
/*  56:    */   {
/*  57: 66 */     func_150170_e(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_);
/*  58:    */   }
/*  59:    */   
/*  60:    */   protected final boolean func_150170_e(World p_150170_1_, int p_150170_2_, int p_150170_3_, int p_150170_4_)
/*  61:    */   {
/*  62: 71 */     if (!canBlockStay(p_150170_1_, p_150170_2_, p_150170_3_, p_150170_4_))
/*  63:    */     {
/*  64: 73 */       dropBlockAsItem(p_150170_1_, p_150170_2_, p_150170_3_, p_150170_4_, p_150170_1_.getBlockMetadata(p_150170_2_, p_150170_3_, p_150170_4_), 0);
/*  65: 74 */       p_150170_1_.setBlockToAir(p_150170_2_, p_150170_3_, p_150170_4_);
/*  66: 75 */       return false;
/*  67:    */     }
/*  68: 79 */     return true;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public boolean canBlockStay(World p_149718_1_, int p_149718_2_, int p_149718_3_, int p_149718_4_)
/*  72:    */   {
/*  73: 88 */     return canPlaceBlockAt(p_149718_1_, p_149718_2_, p_149718_3_, p_149718_4_);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
/*  77:    */   {
/*  78: 97 */     return null;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
/*  82:    */   {
/*  83:102 */     return Items.reeds;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public boolean isOpaqueCube()
/*  87:    */   {
/*  88:107 */     return false;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public boolean renderAsNormalBlock()
/*  92:    */   {
/*  93:112 */     return false;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public int getRenderType()
/*  97:    */   {
/*  98:120 */     return 1;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
/* 102:    */   {
/* 103:128 */     return Items.reeds;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public int colorMultiplier(IBlockAccess p_149720_1_, int p_149720_2_, int p_149720_3_, int p_149720_4_)
/* 107:    */   {
/* 108:137 */     return p_149720_1_.getBiomeGenForCoords(p_149720_2_, p_149720_4_).getBiomeGrassColor(p_149720_2_, p_149720_3_, p_149720_4_);
/* 109:    */   }
/* 110:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockReed
 * JD-Core Version:    0.7.0.1
 */