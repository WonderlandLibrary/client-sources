/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.material.Material;
/*   5:    */ import net.minecraft.creativetab.CreativeTabs;
/*   6:    */ import net.minecraft.util.AxisAlignedBB;
/*   7:    */ import net.minecraft.world.IBlockAccess;
/*   8:    */ import net.minecraft.world.World;
/*   9:    */ 
/*  10:    */ public class BlockLadder
/*  11:    */   extends Block
/*  12:    */ {
/*  13:    */   private static final String __OBFID = "CL_00000262";
/*  14:    */   
/*  15:    */   protected BlockLadder()
/*  16:    */   {
/*  17: 16 */     super(Material.circuits);
/*  18: 17 */     setCreativeTab(CreativeTabs.tabDecorations);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
/*  22:    */   {
/*  23: 26 */     setBlockBoundsBasedOnState(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
/*  24: 27 */     return super.getCollisionBoundingBoxFromPool(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public AxisAlignedBB getSelectedBoundingBoxFromPool(World p_149633_1_, int p_149633_2_, int p_149633_3_, int p_149633_4_)
/*  28:    */   {
/*  29: 35 */     setBlockBoundsBasedOnState(p_149633_1_, p_149633_2_, p_149633_3_, p_149633_4_);
/*  30: 36 */     return super.getSelectedBoundingBoxFromPool(p_149633_1_, p_149633_2_, p_149633_3_, p_149633_4_);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
/*  34:    */   {
/*  35: 41 */     func_149797_b(p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_));
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void func_149797_b(int p_149797_1_)
/*  39:    */   {
/*  40: 46 */     float var3 = 0.125F;
/*  41: 48 */     if (p_149797_1_ == 2) {
/*  42: 50 */       setBlockBounds(0.0F, 0.0F, 1.0F - var3, 1.0F, 1.0F, 1.0F);
/*  43:    */     }
/*  44: 53 */     if (p_149797_1_ == 3) {
/*  45: 55 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var3);
/*  46:    */     }
/*  47: 58 */     if (p_149797_1_ == 4) {
/*  48: 60 */       setBlockBounds(1.0F - var3, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  49:    */     }
/*  50: 63 */     if (p_149797_1_ == 5) {
/*  51: 65 */       setBlockBounds(0.0F, 0.0F, 0.0F, var3, 1.0F, 1.0F);
/*  52:    */     }
/*  53:    */   }
/*  54:    */   
/*  55:    */   public boolean isOpaqueCube()
/*  56:    */   {
/*  57: 71 */     return false;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public boolean renderAsNormalBlock()
/*  61:    */   {
/*  62: 76 */     return false;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public int getRenderType()
/*  66:    */   {
/*  67: 84 */     return 8;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_)
/*  71:    */   {
/*  72: 89 */     return p_149742_1_.getBlock(p_149742_2_, p_149742_3_, p_149742_4_ - 1).isNormalCube() ? true : p_149742_1_.getBlock(p_149742_2_ + 1, p_149742_3_, p_149742_4_).isNormalCube() ? true : p_149742_1_.getBlock(p_149742_2_ - 1, p_149742_3_, p_149742_4_).isNormalCube() ? true : p_149742_1_.getBlock(p_149742_2_, p_149742_3_, p_149742_4_ + 1).isNormalCube();
/*  73:    */   }
/*  74:    */   
/*  75:    */   public int onBlockPlaced(World p_149660_1_, int p_149660_2_, int p_149660_3_, int p_149660_4_, int p_149660_5_, float p_149660_6_, float p_149660_7_, float p_149660_8_, int p_149660_9_)
/*  76:    */   {
/*  77: 94 */     int var10 = p_149660_9_;
/*  78: 96 */     if (((p_149660_9_ == 0) || (p_149660_5_ == 2)) && (p_149660_1_.getBlock(p_149660_2_, p_149660_3_, p_149660_4_ + 1).isNormalCube())) {
/*  79: 98 */       var10 = 2;
/*  80:    */     }
/*  81:101 */     if (((var10 == 0) || (p_149660_5_ == 3)) && (p_149660_1_.getBlock(p_149660_2_, p_149660_3_, p_149660_4_ - 1).isNormalCube())) {
/*  82:103 */       var10 = 3;
/*  83:    */     }
/*  84:106 */     if (((var10 == 0) || (p_149660_5_ == 4)) && (p_149660_1_.getBlock(p_149660_2_ + 1, p_149660_3_, p_149660_4_).isNormalCube())) {
/*  85:108 */       var10 = 4;
/*  86:    */     }
/*  87:111 */     if (((var10 == 0) || (p_149660_5_ == 5)) && (p_149660_1_.getBlock(p_149660_2_ - 1, p_149660_3_, p_149660_4_).isNormalCube())) {
/*  88:113 */       var10 = 5;
/*  89:    */     }
/*  90:116 */     return var10;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
/*  94:    */   {
/*  95:121 */     int var6 = p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_);
/*  96:122 */     boolean var7 = false;
/*  97:124 */     if ((var6 == 2) && (p_149695_1_.getBlock(p_149695_2_, p_149695_3_, p_149695_4_ + 1).isNormalCube())) {
/*  98:126 */       var7 = true;
/*  99:    */     }
/* 100:129 */     if ((var6 == 3) && (p_149695_1_.getBlock(p_149695_2_, p_149695_3_, p_149695_4_ - 1).isNormalCube())) {
/* 101:131 */       var7 = true;
/* 102:    */     }
/* 103:134 */     if ((var6 == 4) && (p_149695_1_.getBlock(p_149695_2_ + 1, p_149695_3_, p_149695_4_).isNormalCube())) {
/* 104:136 */       var7 = true;
/* 105:    */     }
/* 106:139 */     if ((var6 == 5) && (p_149695_1_.getBlock(p_149695_2_ - 1, p_149695_3_, p_149695_4_).isNormalCube())) {
/* 107:141 */       var7 = true;
/* 108:    */     }
/* 109:144 */     if (!var7)
/* 110:    */     {
/* 111:146 */       dropBlockAsItem(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, var6, 0);
/* 112:147 */       p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
/* 113:    */     }
/* 114:150 */     super.onNeighborBlockChange(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_5_);
/* 115:    */   }
/* 116:    */   
/* 117:    */   public int quantityDropped(Random p_149745_1_)
/* 118:    */   {
/* 119:158 */     return 1;
/* 120:    */   }
/* 121:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockLadder
 * JD-Core Version:    0.7.0.1
 */