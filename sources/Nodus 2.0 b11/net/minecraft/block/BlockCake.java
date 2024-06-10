/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.material.Material;
/*   5:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   6:    */ import net.minecraft.entity.player.EntityPlayer;
/*   7:    */ import net.minecraft.init.Items;
/*   8:    */ import net.minecraft.item.Item;
/*   9:    */ import net.minecraft.util.AABBPool;
/*  10:    */ import net.minecraft.util.AxisAlignedBB;
/*  11:    */ import net.minecraft.util.FoodStats;
/*  12:    */ import net.minecraft.util.IIcon;
/*  13:    */ import net.minecraft.world.IBlockAccess;
/*  14:    */ import net.minecraft.world.World;
/*  15:    */ 
/*  16:    */ public class BlockCake
/*  17:    */   extends Block
/*  18:    */ {
/*  19:    */   private IIcon field_150038_a;
/*  20:    */   private IIcon field_150037_b;
/*  21:    */   private IIcon field_150039_M;
/*  22:    */   private static final String __OBFID = "CL_00000211";
/*  23:    */   
/*  24:    */   protected BlockCake()
/*  25:    */   {
/*  26: 23 */     super(Material.field_151568_F);
/*  27: 24 */     setTickRandomly(true);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
/*  31:    */   {
/*  32: 29 */     int var5 = p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_);
/*  33: 30 */     float var6 = 0.0625F;
/*  34: 31 */     float var7 = (1 + var5 * 2) / 16.0F;
/*  35: 32 */     float var8 = 0.5F;
/*  36: 33 */     setBlockBounds(var7, 0.0F, var6, 1.0F - var6, var8, 1.0F - var6);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void setBlockBoundsForItemRender()
/*  40:    */   {
/*  41: 41 */     float var1 = 0.0625F;
/*  42: 42 */     float var2 = 0.5F;
/*  43: 43 */     setBlockBounds(var1, 0.0F, var1, 1.0F - var1, var2, 1.0F - var1);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
/*  47:    */   {
/*  48: 52 */     int var5 = p_149668_1_.getBlockMetadata(p_149668_2_, p_149668_3_, p_149668_4_);
/*  49: 53 */     float var6 = 0.0625F;
/*  50: 54 */     float var7 = (1 + var5 * 2) / 16.0F;
/*  51: 55 */     float var8 = 0.5F;
/*  52: 56 */     return AxisAlignedBB.getAABBPool().getAABB(p_149668_2_ + var7, p_149668_3_, p_149668_4_ + var6, p_149668_2_ + 1 - var6, p_149668_3_ + var8 - var6, p_149668_4_ + 1 - var6);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public AxisAlignedBB getSelectedBoundingBoxFromPool(World p_149633_1_, int p_149633_2_, int p_149633_3_, int p_149633_4_)
/*  56:    */   {
/*  57: 64 */     int var5 = p_149633_1_.getBlockMetadata(p_149633_2_, p_149633_3_, p_149633_4_);
/*  58: 65 */     float var6 = 0.0625F;
/*  59: 66 */     float var7 = (1 + var5 * 2) / 16.0F;
/*  60: 67 */     float var8 = 0.5F;
/*  61: 68 */     return AxisAlignedBB.getAABBPool().getAABB(p_149633_2_ + var7, p_149633_3_, p_149633_4_ + var6, p_149633_2_ + 1 - var6, p_149633_3_ + var8, p_149633_4_ + 1 - var6);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/*  65:    */   {
/*  66: 76 */     return (p_149691_2_ > 0) && (p_149691_1_ == 4) ? this.field_150039_M : p_149691_1_ == 0 ? this.field_150037_b : p_149691_1_ == 1 ? this.field_150038_a : this.blockIcon;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void registerBlockIcons(IIconRegister p_149651_1_)
/*  70:    */   {
/*  71: 81 */     this.blockIcon = p_149651_1_.registerIcon(getTextureName() + "_side");
/*  72: 82 */     this.field_150039_M = p_149651_1_.registerIcon(getTextureName() + "_inner");
/*  73: 83 */     this.field_150038_a = p_149651_1_.registerIcon(getTextureName() + "_top");
/*  74: 84 */     this.field_150037_b = p_149651_1_.registerIcon(getTextureName() + "_bottom");
/*  75:    */   }
/*  76:    */   
/*  77:    */   public boolean renderAsNormalBlock()
/*  78:    */   {
/*  79: 89 */     return false;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public boolean isOpaqueCube()
/*  83:    */   {
/*  84: 94 */     return false;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
/*  88:    */   {
/*  89:102 */     func_150036_b(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_, p_149727_5_);
/*  90:103 */     return true;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void onBlockClicked(World p_149699_1_, int p_149699_2_, int p_149699_3_, int p_149699_4_, EntityPlayer p_149699_5_)
/*  94:    */   {
/*  95:111 */     func_150036_b(p_149699_1_, p_149699_2_, p_149699_3_, p_149699_4_, p_149699_5_);
/*  96:    */   }
/*  97:    */   
/*  98:    */   private void func_150036_b(World p_150036_1_, int p_150036_2_, int p_150036_3_, int p_150036_4_, EntityPlayer p_150036_5_)
/*  99:    */   {
/* 100:116 */     if (p_150036_5_.canEat(false))
/* 101:    */     {
/* 102:118 */       p_150036_5_.getFoodStats().addStats(2, 0.1F);
/* 103:119 */       int var6 = p_150036_1_.getBlockMetadata(p_150036_2_, p_150036_3_, p_150036_4_) + 1;
/* 104:121 */       if (var6 >= 6) {
/* 105:123 */         p_150036_1_.setBlockToAir(p_150036_2_, p_150036_3_, p_150036_4_);
/* 106:    */       } else {
/* 107:127 */         p_150036_1_.setBlockMetadataWithNotify(p_150036_2_, p_150036_3_, p_150036_4_, var6, 2);
/* 108:    */       }
/* 109:    */     }
/* 110:    */   }
/* 111:    */   
/* 112:    */   public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_)
/* 113:    */   {
/* 114:134 */     return !super.canPlaceBlockAt(p_149742_1_, p_149742_2_, p_149742_3_, p_149742_4_) ? false : canBlockStay(p_149742_1_, p_149742_2_, p_149742_3_, p_149742_4_);
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
/* 118:    */   {
/* 119:139 */     if (!canBlockStay(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_)) {
/* 120:141 */       p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
/* 121:    */     }
/* 122:    */   }
/* 123:    */   
/* 124:    */   public boolean canBlockStay(World p_149718_1_, int p_149718_2_, int p_149718_3_, int p_149718_4_)
/* 125:    */   {
/* 126:150 */     return p_149718_1_.getBlock(p_149718_2_, p_149718_3_ - 1, p_149718_4_).getMaterial().isSolid();
/* 127:    */   }
/* 128:    */   
/* 129:    */   public int quantityDropped(Random p_149745_1_)
/* 130:    */   {
/* 131:158 */     return 0;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
/* 135:    */   {
/* 136:163 */     return null;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
/* 140:    */   {
/* 141:171 */     return Items.cake;
/* 142:    */   }
/* 143:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockCake
 * JD-Core Version:    0.7.0.1
 */