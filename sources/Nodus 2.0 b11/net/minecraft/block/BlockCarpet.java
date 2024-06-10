/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import net.minecraft.block.material.Material;
/*   5:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   6:    */ import net.minecraft.creativetab.CreativeTabs;
/*   7:    */ import net.minecraft.init.Blocks;
/*   8:    */ import net.minecraft.item.Item;
/*   9:    */ import net.minecraft.item.ItemStack;
/*  10:    */ import net.minecraft.util.AABBPool;
/*  11:    */ import net.minecraft.util.AxisAlignedBB;
/*  12:    */ import net.minecraft.util.IIcon;
/*  13:    */ import net.minecraft.world.IBlockAccess;
/*  14:    */ import net.minecraft.world.World;
/*  15:    */ 
/*  16:    */ public class BlockCarpet
/*  17:    */   extends Block
/*  18:    */ {
/*  19:    */   private static final String __OBFID = "CL_00000338";
/*  20:    */   
/*  21:    */   protected BlockCarpet()
/*  22:    */   {
/*  23: 21 */     super(Material.carpet);
/*  24: 22 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
/*  25: 23 */     setTickRandomly(true);
/*  26: 24 */     setCreativeTab(CreativeTabs.tabDecorations);
/*  27: 25 */     func_150089_b(0);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/*  31:    */   {
/*  32: 33 */     return Blocks.wool.getIcon(p_149691_1_, p_149691_2_);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
/*  36:    */   {
/*  37: 42 */     byte var5 = 0;
/*  38: 43 */     float var6 = 0.0625F;
/*  39: 44 */     return AxisAlignedBB.getAABBPool().getAABB(p_149668_2_ + this.field_149759_B, p_149668_3_ + this.field_149760_C, p_149668_4_ + this.field_149754_D, p_149668_2_ + this.field_149755_E, p_149668_3_ + var5 * var6, p_149668_4_ + this.field_149757_G);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public boolean isOpaqueCube()
/*  43:    */   {
/*  44: 49 */     return false;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public boolean renderAsNormalBlock()
/*  48:    */   {
/*  49: 54 */     return false;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void setBlockBoundsForItemRender()
/*  53:    */   {
/*  54: 62 */     func_150089_b(0);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
/*  58:    */   {
/*  59: 67 */     func_150089_b(p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_));
/*  60:    */   }
/*  61:    */   
/*  62:    */   protected void func_150089_b(int p_150089_1_)
/*  63:    */   {
/*  64: 72 */     byte var2 = 0;
/*  65: 73 */     float var3 = 1 * (1 + var2) / 16.0F;
/*  66: 74 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, var3, 1.0F);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_)
/*  70:    */   {
/*  71: 79 */     return (super.canPlaceBlockAt(p_149742_1_, p_149742_2_, p_149742_3_, p_149742_4_)) && (canBlockStay(p_149742_1_, p_149742_2_, p_149742_3_, p_149742_4_));
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
/*  75:    */   {
/*  76: 84 */     func_150090_e(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_);
/*  77:    */   }
/*  78:    */   
/*  79:    */   private boolean func_150090_e(World p_150090_1_, int p_150090_2_, int p_150090_3_, int p_150090_4_)
/*  80:    */   {
/*  81: 89 */     if (!canBlockStay(p_150090_1_, p_150090_2_, p_150090_3_, p_150090_4_))
/*  82:    */     {
/*  83: 91 */       dropBlockAsItem(p_150090_1_, p_150090_2_, p_150090_3_, p_150090_4_, p_150090_1_.getBlockMetadata(p_150090_2_, p_150090_3_, p_150090_4_), 0);
/*  84: 92 */       p_150090_1_.setBlockToAir(p_150090_2_, p_150090_3_, p_150090_4_);
/*  85: 93 */       return false;
/*  86:    */     }
/*  87: 97 */     return true;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public boolean canBlockStay(World p_149718_1_, int p_149718_2_, int p_149718_3_, int p_149718_4_)
/*  91:    */   {
/*  92:106 */     return !p_149718_1_.isAirBlock(p_149718_2_, p_149718_3_ - 1, p_149718_4_);
/*  93:    */   }
/*  94:    */   
/*  95:    */   public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_)
/*  96:    */   {
/*  97:111 */     return p_149646_5_ == 1 ? true : super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public int damageDropped(int p_149692_1_)
/* 101:    */   {
/* 102:119 */     return p_149692_1_;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
/* 106:    */   {
/* 107:124 */     for (int var4 = 0; var4 < 16; var4++) {
/* 108:126 */       p_149666_3_.add(new ItemStack(p_149666_1_, 1, var4));
/* 109:    */     }
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void registerBlockIcons(IIconRegister p_149651_1_) {}
/* 113:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockCarpet
 * JD-Core Version:    0.7.0.1
 */