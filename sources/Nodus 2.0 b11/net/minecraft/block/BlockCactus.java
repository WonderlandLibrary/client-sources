/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.material.Material;
/*   5:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   6:    */ import net.minecraft.creativetab.CreativeTabs;
/*   7:    */ import net.minecraft.entity.Entity;
/*   8:    */ import net.minecraft.init.Blocks;
/*   9:    */ import net.minecraft.util.AABBPool;
/*  10:    */ import net.minecraft.util.AxisAlignedBB;
/*  11:    */ import net.minecraft.util.DamageSource;
/*  12:    */ import net.minecraft.util.IIcon;
/*  13:    */ import net.minecraft.world.World;
/*  14:    */ 
/*  15:    */ public class BlockCactus
/*  16:    */   extends Block
/*  17:    */ {
/*  18:    */   private IIcon field_150041_a;
/*  19:    */   private IIcon field_150040_b;
/*  20:    */   private static final String __OBFID = "CL_00000210";
/*  21:    */   
/*  22:    */   protected BlockCactus()
/*  23:    */   {
/*  24: 22 */     super(Material.field_151570_A);
/*  25: 23 */     setTickRandomly(true);
/*  26: 24 */     setCreativeTab(CreativeTabs.tabDecorations);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
/*  30:    */   {
/*  31: 32 */     if (p_149674_1_.isAirBlock(p_149674_2_, p_149674_3_ + 1, p_149674_4_))
/*  32:    */     {
/*  33: 36 */       for (int var6 = 1; p_149674_1_.getBlock(p_149674_2_, p_149674_3_ - var6, p_149674_4_) == this; var6++) {}
/*  34: 41 */       if (var6 < 3)
/*  35:    */       {
/*  36: 43 */         int var7 = p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_);
/*  37: 45 */         if (var7 == 15)
/*  38:    */         {
/*  39: 47 */           p_149674_1_.setBlock(p_149674_2_, p_149674_3_ + 1, p_149674_4_, this);
/*  40: 48 */           p_149674_1_.setBlockMetadataWithNotify(p_149674_2_, p_149674_3_, p_149674_4_, 0, 4);
/*  41: 49 */           onNeighborBlockChange(p_149674_1_, p_149674_2_, p_149674_3_ + 1, p_149674_4_, this);
/*  42:    */         }
/*  43:    */         else
/*  44:    */         {
/*  45: 53 */           p_149674_1_.setBlockMetadataWithNotify(p_149674_2_, p_149674_3_, p_149674_4_, var7 + 1, 4);
/*  46:    */         }
/*  47:    */       }
/*  48:    */     }
/*  49:    */   }
/*  50:    */   
/*  51:    */   public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
/*  52:    */   {
/*  53: 65 */     float var5 = 0.0625F;
/*  54: 66 */     return AxisAlignedBB.getAABBPool().getAABB(p_149668_2_ + var5, p_149668_3_, p_149668_4_ + var5, p_149668_2_ + 1 - var5, p_149668_3_ + 1 - var5, p_149668_4_ + 1 - var5);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public AxisAlignedBB getSelectedBoundingBoxFromPool(World p_149633_1_, int p_149633_2_, int p_149633_3_, int p_149633_4_)
/*  58:    */   {
/*  59: 74 */     float var5 = 0.0625F;
/*  60: 75 */     return AxisAlignedBB.getAABBPool().getAABB(p_149633_2_ + var5, p_149633_3_, p_149633_4_ + var5, p_149633_2_ + 1 - var5, p_149633_3_ + 1, p_149633_4_ + 1 - var5);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/*  64:    */   {
/*  65: 83 */     return p_149691_1_ == 0 ? this.field_150040_b : p_149691_1_ == 1 ? this.field_150041_a : this.blockIcon;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public boolean renderAsNormalBlock()
/*  69:    */   {
/*  70: 88 */     return false;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public boolean isOpaqueCube()
/*  74:    */   {
/*  75: 93 */     return false;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public int getRenderType()
/*  79:    */   {
/*  80:101 */     return 13;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_)
/*  84:    */   {
/*  85:106 */     return !super.canPlaceBlockAt(p_149742_1_, p_149742_2_, p_149742_3_, p_149742_4_) ? false : canBlockStay(p_149742_1_, p_149742_2_, p_149742_3_, p_149742_4_);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
/*  89:    */   {
/*  90:111 */     if (!canBlockStay(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_)) {
/*  91:113 */       p_149695_1_.func_147480_a(p_149695_2_, p_149695_3_, p_149695_4_, true);
/*  92:    */     }
/*  93:    */   }
/*  94:    */   
/*  95:    */   public boolean canBlockStay(World p_149718_1_, int p_149718_2_, int p_149718_3_, int p_149718_4_)
/*  96:    */   {
/*  97:122 */     if (p_149718_1_.getBlock(p_149718_2_ - 1, p_149718_3_, p_149718_4_).getMaterial().isSolid()) {
/*  98:124 */       return false;
/*  99:    */     }
/* 100:126 */     if (p_149718_1_.getBlock(p_149718_2_ + 1, p_149718_3_, p_149718_4_).getMaterial().isSolid()) {
/* 101:128 */       return false;
/* 102:    */     }
/* 103:130 */     if (p_149718_1_.getBlock(p_149718_2_, p_149718_3_, p_149718_4_ - 1).getMaterial().isSolid()) {
/* 104:132 */       return false;
/* 105:    */     }
/* 106:134 */     if (p_149718_1_.getBlock(p_149718_2_, p_149718_3_, p_149718_4_ + 1).getMaterial().isSolid()) {
/* 107:136 */       return false;
/* 108:    */     }
/* 109:140 */     Block var5 = p_149718_1_.getBlock(p_149718_2_, p_149718_3_ - 1, p_149718_4_);
/* 110:141 */     return (var5 == Blocks.cactus) || (var5 == Blocks.sand);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity p_149670_5_)
/* 114:    */   {
/* 115:147 */     p_149670_5_.attackEntityFrom(DamageSource.cactus, 1.0F);
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void registerBlockIcons(IIconRegister p_149651_1_)
/* 119:    */   {
/* 120:152 */     this.blockIcon = p_149651_1_.registerIcon(getTextureName() + "_side");
/* 121:153 */     this.field_150041_a = p_149651_1_.registerIcon(getTextureName() + "_top");
/* 122:154 */     this.field_150040_b = p_149651_1_.registerIcon(getTextureName() + "_bottom");
/* 123:    */   }
/* 124:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockCactus
 * JD-Core Version:    0.7.0.1
 */