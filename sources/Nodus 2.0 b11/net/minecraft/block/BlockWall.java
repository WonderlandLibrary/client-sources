/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import net.minecraft.block.material.Material;
/*   5:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   6:    */ import net.minecraft.creativetab.CreativeTabs;
/*   7:    */ import net.minecraft.init.Blocks;
/*   8:    */ import net.minecraft.item.Item;
/*   9:    */ import net.minecraft.item.ItemStack;
/*  10:    */ import net.minecraft.util.AxisAlignedBB;
/*  11:    */ import net.minecraft.util.IIcon;
/*  12:    */ import net.minecraft.world.IBlockAccess;
/*  13:    */ import net.minecraft.world.World;
/*  14:    */ 
/*  15:    */ public class BlockWall
/*  16:    */   extends Block
/*  17:    */ {
/*  18: 17 */   public static final String[] field_150092_a = { "normal", "mossy" };
/*  19:    */   private static final String __OBFID = "CL_00000331";
/*  20:    */   
/*  21:    */   public BlockWall(Block p_i45435_1_)
/*  22:    */   {
/*  23: 22 */     super(p_i45435_1_.blockMaterial);
/*  24: 23 */     setHardness(p_i45435_1_.blockHardness);
/*  25: 24 */     setResistance(p_i45435_1_.blockResistance / 3.0F);
/*  26: 25 */     setStepSound(p_i45435_1_.stepSound);
/*  27: 26 */     setCreativeTab(CreativeTabs.tabBlock);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/*  31:    */   {
/*  32: 34 */     return p_149691_2_ == 1 ? Blocks.mossy_cobblestone.getBlockTextureFromSide(p_149691_1_) : Blocks.cobblestone.getBlockTextureFromSide(p_149691_1_);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public int getRenderType()
/*  36:    */   {
/*  37: 42 */     return 32;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public boolean renderAsNormalBlock()
/*  41:    */   {
/*  42: 47 */     return false;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public boolean getBlocksMovement(IBlockAccess p_149655_1_, int p_149655_2_, int p_149655_3_, int p_149655_4_)
/*  46:    */   {
/*  47: 52 */     return false;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public boolean isOpaqueCube()
/*  51:    */   {
/*  52: 57 */     return false;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
/*  56:    */   {
/*  57: 62 */     boolean var5 = func_150091_e(p_149719_1_, p_149719_2_, p_149719_3_, p_149719_4_ - 1);
/*  58: 63 */     boolean var6 = func_150091_e(p_149719_1_, p_149719_2_, p_149719_3_, p_149719_4_ + 1);
/*  59: 64 */     boolean var7 = func_150091_e(p_149719_1_, p_149719_2_ - 1, p_149719_3_, p_149719_4_);
/*  60: 65 */     boolean var8 = func_150091_e(p_149719_1_, p_149719_2_ + 1, p_149719_3_, p_149719_4_);
/*  61: 66 */     float var9 = 0.25F;
/*  62: 67 */     float var10 = 0.75F;
/*  63: 68 */     float var11 = 0.25F;
/*  64: 69 */     float var12 = 0.75F;
/*  65: 70 */     float var13 = 1.0F;
/*  66: 72 */     if (var5) {
/*  67: 74 */       var11 = 0.0F;
/*  68:    */     }
/*  69: 77 */     if (var6) {
/*  70: 79 */       var12 = 1.0F;
/*  71:    */     }
/*  72: 82 */     if (var7) {
/*  73: 84 */       var9 = 0.0F;
/*  74:    */     }
/*  75: 87 */     if (var8) {
/*  76: 89 */       var10 = 1.0F;
/*  77:    */     }
/*  78: 92 */     if ((var5) && (var6) && (!var7) && (!var8))
/*  79:    */     {
/*  80: 94 */       var13 = 0.8125F;
/*  81: 95 */       var9 = 0.3125F;
/*  82: 96 */       var10 = 0.6875F;
/*  83:    */     }
/*  84: 98 */     else if ((!var5) && (!var6) && (var7) && (var8))
/*  85:    */     {
/*  86:100 */       var13 = 0.8125F;
/*  87:101 */       var11 = 0.3125F;
/*  88:102 */       var12 = 0.6875F;
/*  89:    */     }
/*  90:105 */     setBlockBounds(var9, 0.0F, var11, var10, var13, var12);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
/*  94:    */   {
/*  95:114 */     setBlockBoundsBasedOnState(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
/*  96:115 */     this.field_149756_F = 1.5D;
/*  97:116 */     return super.getCollisionBoundingBoxFromPool(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public boolean func_150091_e(IBlockAccess p_150091_1_, int p_150091_2_, int p_150091_3_, int p_150091_4_)
/* 101:    */   {
/* 102:121 */     Block var5 = p_150091_1_.getBlock(p_150091_2_, p_150091_3_, p_150091_4_);
/* 103:122 */     return var5.blockMaterial != Material.field_151572_C;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
/* 107:    */   {
/* 108:127 */     p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));
/* 109:128 */     p_149666_3_.add(new ItemStack(p_149666_1_, 1, 1));
/* 110:    */   }
/* 111:    */   
/* 112:    */   public int damageDropped(int p_149692_1_)
/* 113:    */   {
/* 114:136 */     return p_149692_1_;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_)
/* 118:    */   {
/* 119:141 */     return p_149646_5_ == 0 ? super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_) : true;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void registerBlockIcons(IIconRegister p_149651_1_) {}
/* 123:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockWall
 * JD-Core Version:    0.7.0.1
 */