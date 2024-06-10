/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.material.Material;
/*   5:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   6:    */ import net.minecraft.creativetab.CreativeTabs;
/*   7:    */ import net.minecraft.entity.player.EntityPlayer;
/*   8:    */ import net.minecraft.init.Items;
/*   9:    */ import net.minecraft.item.Item;
/*  10:    */ import net.minecraft.item.ItemStack;
/*  11:    */ import net.minecraft.util.AABBPool;
/*  12:    */ import net.minecraft.util.AxisAlignedBB;
/*  13:    */ import net.minecraft.world.EnumSkyBlock;
/*  14:    */ import net.minecraft.world.IBlockAccess;
/*  15:    */ import net.minecraft.world.World;
/*  16:    */ 
/*  17:    */ public class BlockSnow
/*  18:    */   extends Block
/*  19:    */ {
/*  20:    */   private static final String __OBFID = "CL_00000309";
/*  21:    */   
/*  22:    */   protected BlockSnow()
/*  23:    */   {
/*  24: 24 */     super(Material.field_151597_y);
/*  25: 25 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
/*  26: 26 */     setTickRandomly(true);
/*  27: 27 */     setCreativeTab(CreativeTabs.tabDecorations);
/*  28: 28 */     func_150154_b(0);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void registerBlockIcons(IIconRegister p_149651_1_)
/*  32:    */   {
/*  33: 33 */     this.blockIcon = p_149651_1_.registerIcon("snow");
/*  34:    */   }
/*  35:    */   
/*  36:    */   public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
/*  37:    */   {
/*  38: 42 */     int var5 = p_149668_1_.getBlockMetadata(p_149668_2_, p_149668_3_, p_149668_4_) & 0x7;
/*  39: 43 */     float var6 = 0.125F;
/*  40: 44 */     return AxisAlignedBB.getAABBPool().getAABB(p_149668_2_ + this.field_149759_B, p_149668_3_ + this.field_149760_C, p_149668_4_ + this.field_149754_D, p_149668_2_ + this.field_149755_E, p_149668_3_ + var5 * var6, p_149668_4_ + this.field_149757_G);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public boolean isOpaqueCube()
/*  44:    */   {
/*  45: 49 */     return false;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public boolean renderAsNormalBlock()
/*  49:    */   {
/*  50: 54 */     return false;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void setBlockBoundsForItemRender()
/*  54:    */   {
/*  55: 62 */     func_150154_b(0);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
/*  59:    */   {
/*  60: 67 */     func_150154_b(p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_));
/*  61:    */   }
/*  62:    */   
/*  63:    */   protected void func_150154_b(int p_150154_1_)
/*  64:    */   {
/*  65: 72 */     int var2 = p_150154_1_ & 0x7;
/*  66: 73 */     float var3 = 2 * (1 + var2) / 16.0F;
/*  67: 74 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, var3, 1.0F);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_)
/*  71:    */   {
/*  72: 79 */     Block var5 = p_149742_1_.getBlock(p_149742_2_, p_149742_3_ - 1, p_149742_4_);
/*  73: 80 */     return var5.getMaterial() == Material.leaves;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
/*  77:    */   {
/*  78: 85 */     func_150155_m(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_);
/*  79:    */   }
/*  80:    */   
/*  81:    */   private boolean func_150155_m(World p_150155_1_, int p_150155_2_, int p_150155_3_, int p_150155_4_)
/*  82:    */   {
/*  83: 90 */     if (!canPlaceBlockAt(p_150155_1_, p_150155_2_, p_150155_3_, p_150155_4_))
/*  84:    */     {
/*  85: 92 */       dropBlockAsItem(p_150155_1_, p_150155_2_, p_150155_3_, p_150155_4_, p_150155_1_.getBlockMetadata(p_150155_2_, p_150155_3_, p_150155_4_), 0);
/*  86: 93 */       p_150155_1_.setBlockToAir(p_150155_2_, p_150155_3_, p_150155_4_);
/*  87: 94 */       return false;
/*  88:    */     }
/*  89: 98 */     return true;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void harvestBlock(World p_149636_1_, EntityPlayer p_149636_2_, int p_149636_3_, int p_149636_4_, int p_149636_5_, int p_149636_6_)
/*  93:    */   {
/*  94:104 */     int var7 = p_149636_6_ & 0x7;
/*  95:105 */     dropBlockAsItem_do(p_149636_1_, p_149636_3_, p_149636_4_, p_149636_5_, new ItemStack(Items.snowball, var7 + 1, 0));
/*  96:106 */     p_149636_1_.setBlockToAir(p_149636_3_, p_149636_4_, p_149636_5_);
/*  97:107 */     p_149636_2_.addStat(net.minecraft.stats.StatList.mineBlockStatArray[Block.getIdFromBlock(this)], 1);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
/* 101:    */   {
/* 102:112 */     return Items.snowball;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public int quantityDropped(Random p_149745_1_)
/* 106:    */   {
/* 107:120 */     return 0;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
/* 111:    */   {
/* 112:128 */     if (p_149674_1_.getSavedLightValue(EnumSkyBlock.Block, p_149674_2_, p_149674_3_, p_149674_4_) > 11)
/* 113:    */     {
/* 114:130 */       dropBlockAsItem(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_), 0);
/* 115:131 */       p_149674_1_.setBlockToAir(p_149674_2_, p_149674_3_, p_149674_4_);
/* 116:    */     }
/* 117:    */   }
/* 118:    */   
/* 119:    */   public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_)
/* 120:    */   {
/* 121:137 */     return p_149646_5_ == 1 ? true : super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_);
/* 122:    */   }
/* 123:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockSnow
 * JD-Core Version:    0.7.0.1
 */