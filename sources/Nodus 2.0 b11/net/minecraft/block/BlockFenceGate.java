/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import net.minecraft.block.material.Material;
/*   4:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   5:    */ import net.minecraft.creativetab.CreativeTabs;
/*   6:    */ import net.minecraft.entity.EntityLivingBase;
/*   7:    */ import net.minecraft.entity.player.EntityPlayer;
/*   8:    */ import net.minecraft.init.Blocks;
/*   9:    */ import net.minecraft.item.ItemStack;
/*  10:    */ import net.minecraft.util.AABBPool;
/*  11:    */ import net.minecraft.util.AxisAlignedBB;
/*  12:    */ import net.minecraft.util.IIcon;
/*  13:    */ import net.minecraft.util.MathHelper;
/*  14:    */ import net.minecraft.world.IBlockAccess;
/*  15:    */ import net.minecraft.world.World;
/*  16:    */ 
/*  17:    */ public class BlockFenceGate
/*  18:    */   extends BlockDirectional
/*  19:    */ {
/*  20:    */   private static final String __OBFID = "CL_00000243";
/*  21:    */   
/*  22:    */   public BlockFenceGate()
/*  23:    */   {
/*  24: 22 */     super(Material.wood);
/*  25: 23 */     setCreativeTab(CreativeTabs.tabRedstone);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/*  29:    */   {
/*  30: 31 */     return Blocks.planks.getBlockTextureFromSide(p_149691_1_);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_)
/*  34:    */   {
/*  35: 36 */     return !p_149742_1_.getBlock(p_149742_2_, p_149742_3_ - 1, p_149742_4_).getMaterial().isSolid() ? false : super.canPlaceBlockAt(p_149742_1_, p_149742_2_, p_149742_3_, p_149742_4_);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
/*  39:    */   {
/*  40: 45 */     int var5 = p_149668_1_.getBlockMetadata(p_149668_2_, p_149668_3_, p_149668_4_);
/*  41: 46 */     return (var5 != 2) && (var5 != 0) ? AxisAlignedBB.getAABBPool().getAABB(p_149668_2_ + 0.375F, p_149668_3_, p_149668_4_, p_149668_2_ + 0.625F, p_149668_3_ + 1.5F, p_149668_4_ + 1) : isFenceGateOpen(var5) ? null : AxisAlignedBB.getAABBPool().getAABB(p_149668_2_, p_149668_3_, p_149668_4_ + 0.375F, p_149668_2_ + 1, p_149668_3_ + 1.5F, p_149668_4_ + 0.625F);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
/*  45:    */   {
/*  46: 51 */     int var5 = func_149895_l(p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_));
/*  47: 53 */     if ((var5 != 2) && (var5 != 0)) {
/*  48: 55 */       setBlockBounds(0.375F, 0.0F, 0.0F, 0.625F, 1.0F, 1.0F);
/*  49:    */     } else {
/*  50: 59 */       setBlockBounds(0.0F, 0.0F, 0.375F, 1.0F, 1.0F, 0.625F);
/*  51:    */     }
/*  52:    */   }
/*  53:    */   
/*  54:    */   public boolean isOpaqueCube()
/*  55:    */   {
/*  56: 65 */     return false;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public boolean renderAsNormalBlock()
/*  60:    */   {
/*  61: 70 */     return false;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public boolean getBlocksMovement(IBlockAccess p_149655_1_, int p_149655_2_, int p_149655_3_, int p_149655_4_)
/*  65:    */   {
/*  66: 75 */     return isFenceGateOpen(p_149655_1_.getBlockMetadata(p_149655_2_, p_149655_3_, p_149655_4_));
/*  67:    */   }
/*  68:    */   
/*  69:    */   public int getRenderType()
/*  70:    */   {
/*  71: 83 */     return 21;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_)
/*  75:    */   {
/*  76: 91 */     int var7 = (MathHelper.floor_double(p_149689_5_.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3) % 4;
/*  77: 92 */     p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, var7, 2);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
/*  81:    */   {
/*  82:100 */     int var10 = p_149727_1_.getBlockMetadata(p_149727_2_, p_149727_3_, p_149727_4_);
/*  83:102 */     if (isFenceGateOpen(var10))
/*  84:    */     {
/*  85:104 */       p_149727_1_.setBlockMetadataWithNotify(p_149727_2_, p_149727_3_, p_149727_4_, var10 & 0xFFFFFFFB, 2);
/*  86:    */     }
/*  87:    */     else
/*  88:    */     {
/*  89:108 */       int var11 = (MathHelper.floor_double(p_149727_5_.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3) % 4;
/*  90:109 */       int var12 = func_149895_l(var10);
/*  91:111 */       if (var12 == (var11 + 2) % 4) {
/*  92:113 */         var10 = var11;
/*  93:    */       }
/*  94:116 */       p_149727_1_.setBlockMetadataWithNotify(p_149727_2_, p_149727_3_, p_149727_4_, var10 | 0x4, 2);
/*  95:    */     }
/*  96:119 */     p_149727_1_.playAuxSFXAtEntity(p_149727_5_, 1003, p_149727_2_, p_149727_3_, p_149727_4_, 0);
/*  97:120 */     return true;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
/* 101:    */   {
/* 102:125 */     if (!p_149695_1_.isClient)
/* 103:    */     {
/* 104:127 */       int var6 = p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_);
/* 105:128 */       boolean var7 = p_149695_1_.isBlockIndirectlyGettingPowered(p_149695_2_, p_149695_3_, p_149695_4_);
/* 106:130 */       if ((var7) || (p_149695_5_.canProvidePower())) {
/* 107:132 */         if ((var7) && (!isFenceGateOpen(var6)))
/* 108:    */         {
/* 109:134 */           p_149695_1_.setBlockMetadataWithNotify(p_149695_2_, p_149695_3_, p_149695_4_, var6 | 0x4, 2);
/* 110:135 */           p_149695_1_.playAuxSFXAtEntity(null, 1003, p_149695_2_, p_149695_3_, p_149695_4_, 0);
/* 111:    */         }
/* 112:137 */         else if ((!var7) && (isFenceGateOpen(var6)))
/* 113:    */         {
/* 114:139 */           p_149695_1_.setBlockMetadataWithNotify(p_149695_2_, p_149695_3_, p_149695_4_, var6 & 0xFFFFFFFB, 2);
/* 115:140 */           p_149695_1_.playAuxSFXAtEntity(null, 1003, p_149695_2_, p_149695_3_, p_149695_4_, 0);
/* 116:    */         }
/* 117:    */       }
/* 118:    */     }
/* 119:    */   }
/* 120:    */   
/* 121:    */   public static boolean isFenceGateOpen(int p_149896_0_)
/* 122:    */   {
/* 123:151 */     return (p_149896_0_ & 0x4) != 0;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_)
/* 127:    */   {
/* 128:156 */     return true;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void registerBlockIcons(IIconRegister p_149651_1_) {}
/* 132:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockFenceGate
 * JD-Core Version:    0.7.0.1
 */