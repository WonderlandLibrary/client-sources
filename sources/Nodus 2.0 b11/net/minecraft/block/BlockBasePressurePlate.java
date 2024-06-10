/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.material.Material;
/*   5:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   6:    */ import net.minecraft.creativetab.CreativeTabs;
/*   7:    */ import net.minecraft.entity.Entity;
/*   8:    */ import net.minecraft.util.AABBPool;
/*   9:    */ import net.minecraft.util.AxisAlignedBB;
/*  10:    */ import net.minecraft.world.IBlockAccess;
/*  11:    */ import net.minecraft.world.World;
/*  12:    */ 
/*  13:    */ public abstract class BlockBasePressurePlate
/*  14:    */   extends Block
/*  15:    */ {
/*  16:    */   private String field_150067_a;
/*  17:    */   private static final String __OBFID = "CL_00000194";
/*  18:    */   
/*  19:    */   protected BlockBasePressurePlate(String p_i45387_1_, Material p_i45387_2_)
/*  20:    */   {
/*  21: 19 */     super(p_i45387_2_);
/*  22: 20 */     this.field_150067_a = p_i45387_1_;
/*  23: 21 */     setCreativeTab(CreativeTabs.tabRedstone);
/*  24: 22 */     setTickRandomly(true);
/*  25: 23 */     func_150063_b(func_150066_d(15));
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
/*  29:    */   {
/*  30: 28 */     func_150063_b(p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_));
/*  31:    */   }
/*  32:    */   
/*  33:    */   protected void func_150063_b(int p_150063_1_)
/*  34:    */   {
/*  35: 33 */     boolean var2 = func_150060_c(p_150063_1_) > 0;
/*  36: 34 */     float var3 = 0.0625F;
/*  37: 36 */     if (var2) {
/*  38: 38 */       setBlockBounds(var3, 0.0F, var3, 1.0F - var3, 0.03125F, 1.0F - var3);
/*  39:    */     } else {
/*  40: 42 */       setBlockBounds(var3, 0.0F, var3, 1.0F - var3, 0.0625F, 1.0F - var3);
/*  41:    */     }
/*  42:    */   }
/*  43:    */   
/*  44:    */   public int func_149738_a(World p_149738_1_)
/*  45:    */   {
/*  46: 48 */     return 20;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
/*  50:    */   {
/*  51: 57 */     return null;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public boolean isOpaqueCube()
/*  55:    */   {
/*  56: 62 */     return false;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public boolean renderAsNormalBlock()
/*  60:    */   {
/*  61: 67 */     return false;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public boolean getBlocksMovement(IBlockAccess p_149655_1_, int p_149655_2_, int p_149655_3_, int p_149655_4_)
/*  65:    */   {
/*  66: 72 */     return true;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_)
/*  70:    */   {
/*  71: 77 */     return (World.doesBlockHaveSolidTopSurface(p_149742_1_, p_149742_2_, p_149742_3_ - 1, p_149742_4_)) || (BlockFence.func_149825_a(p_149742_1_.getBlock(p_149742_2_, p_149742_3_ - 1, p_149742_4_)));
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
/*  75:    */   {
/*  76: 82 */     boolean var6 = false;
/*  77: 84 */     if ((!World.doesBlockHaveSolidTopSurface(p_149695_1_, p_149695_2_, p_149695_3_ - 1, p_149695_4_)) && (!BlockFence.func_149825_a(p_149695_1_.getBlock(p_149695_2_, p_149695_3_ - 1, p_149695_4_)))) {
/*  78: 86 */       var6 = true;
/*  79:    */     }
/*  80: 89 */     if (var6)
/*  81:    */     {
/*  82: 91 */       dropBlockAsItem(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_), 0);
/*  83: 92 */       p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
/*  84:    */     }
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
/*  88:    */   {
/*  89:101 */     if (!p_149674_1_.isClient)
/*  90:    */     {
/*  91:103 */       int var6 = func_150060_c(p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_));
/*  92:105 */       if (var6 > 0) {
/*  93:107 */         func_150062_a(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, var6);
/*  94:    */       }
/*  95:    */     }
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity p_149670_5_)
/*  99:    */   {
/* 100:114 */     if (!p_149670_1_.isClient)
/* 101:    */     {
/* 102:116 */       int var6 = func_150060_c(p_149670_1_.getBlockMetadata(p_149670_2_, p_149670_3_, p_149670_4_));
/* 103:118 */       if (var6 == 0) {
/* 104:120 */         func_150062_a(p_149670_1_, p_149670_2_, p_149670_3_, p_149670_4_, var6);
/* 105:    */       }
/* 106:    */     }
/* 107:    */   }
/* 108:    */   
/* 109:    */   protected void func_150062_a(World p_150062_1_, int p_150062_2_, int p_150062_3_, int p_150062_4_, int p_150062_5_)
/* 110:    */   {
/* 111:127 */     int var6 = func_150065_e(p_150062_1_, p_150062_2_, p_150062_3_, p_150062_4_);
/* 112:128 */     boolean var7 = p_150062_5_ > 0;
/* 113:129 */     boolean var8 = var6 > 0;
/* 114:131 */     if (p_150062_5_ != var6)
/* 115:    */     {
/* 116:133 */       p_150062_1_.setBlockMetadataWithNotify(p_150062_2_, p_150062_3_, p_150062_4_, func_150066_d(var6), 2);
/* 117:134 */       func_150064_a_(p_150062_1_, p_150062_2_, p_150062_3_, p_150062_4_);
/* 118:135 */       p_150062_1_.markBlockRangeForRenderUpdate(p_150062_2_, p_150062_3_, p_150062_4_, p_150062_2_, p_150062_3_, p_150062_4_);
/* 119:    */     }
/* 120:138 */     if ((!var8) && (var7)) {
/* 121:140 */       p_150062_1_.playSoundEffect(p_150062_2_ + 0.5D, p_150062_3_ + 0.1D, p_150062_4_ + 0.5D, "random.click", 0.3F, 0.5F);
/* 122:142 */     } else if ((var8) && (!var7)) {
/* 123:144 */       p_150062_1_.playSoundEffect(p_150062_2_ + 0.5D, p_150062_3_ + 0.1D, p_150062_4_ + 0.5D, "random.click", 0.3F, 0.6F);
/* 124:    */     }
/* 125:147 */     if (var8) {
/* 126:149 */       p_150062_1_.scheduleBlockUpdate(p_150062_2_, p_150062_3_, p_150062_4_, this, func_149738_a(p_150062_1_));
/* 127:    */     }
/* 128:    */   }
/* 129:    */   
/* 130:    */   protected AxisAlignedBB func_150061_a(int p_150061_1_, int p_150061_2_, int p_150061_3_)
/* 131:    */   {
/* 132:155 */     float var4 = 0.125F;
/* 133:156 */     return AxisAlignedBB.getAABBPool().getAABB(p_150061_1_ + var4, p_150061_2_, p_150061_3_ + var4, p_150061_1_ + 1 - var4, p_150061_2_ + 0.25D, p_150061_3_ + 1 - var4);
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
/* 137:    */   {
/* 138:161 */     if (func_150060_c(p_149749_6_) > 0) {
/* 139:163 */       func_150064_a_(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_);
/* 140:    */     }
/* 141:166 */     super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
/* 142:    */   }
/* 143:    */   
/* 144:    */   protected void func_150064_a_(World p_150064_1_, int p_150064_2_, int p_150064_3_, int p_150064_4_)
/* 145:    */   {
/* 146:171 */     p_150064_1_.notifyBlocksOfNeighborChange(p_150064_2_, p_150064_3_, p_150064_4_, this);
/* 147:172 */     p_150064_1_.notifyBlocksOfNeighborChange(p_150064_2_, p_150064_3_ - 1, p_150064_4_, this);
/* 148:    */   }
/* 149:    */   
/* 150:    */   public int isProvidingWeakPower(IBlockAccess p_149709_1_, int p_149709_2_, int p_149709_3_, int p_149709_4_, int p_149709_5_)
/* 151:    */   {
/* 152:177 */     return func_150060_c(p_149709_1_.getBlockMetadata(p_149709_2_, p_149709_3_, p_149709_4_));
/* 153:    */   }
/* 154:    */   
/* 155:    */   public int isProvidingStrongPower(IBlockAccess p_149748_1_, int p_149748_2_, int p_149748_3_, int p_149748_4_, int p_149748_5_)
/* 156:    */   {
/* 157:182 */     return p_149748_5_ == 1 ? func_150060_c(p_149748_1_.getBlockMetadata(p_149748_2_, p_149748_3_, p_149748_4_)) : 0;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public boolean canProvidePower()
/* 161:    */   {
/* 162:190 */     return true;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setBlockBoundsForItemRender()
/* 166:    */   {
/* 167:198 */     float var1 = 0.5F;
/* 168:199 */     float var2 = 0.125F;
/* 169:200 */     float var3 = 0.5F;
/* 170:201 */     setBlockBounds(0.5F - var1, 0.5F - var2, 0.5F - var3, 0.5F + var1, 0.5F + var2, 0.5F + var3);
/* 171:    */   }
/* 172:    */   
/* 173:    */   public int getMobilityFlag()
/* 174:    */   {
/* 175:206 */     return 1;
/* 176:    */   }
/* 177:    */   
/* 178:    */   protected abstract int func_150065_e(World paramWorld, int paramInt1, int paramInt2, int paramInt3);
/* 179:    */   
/* 180:    */   protected abstract int func_150060_c(int paramInt);
/* 181:    */   
/* 182:    */   protected abstract int func_150066_d(int paramInt);
/* 183:    */   
/* 184:    */   public void registerBlockIcons(IIconRegister p_149651_1_)
/* 185:    */   {
/* 186:217 */     this.blockIcon = p_149651_1_.registerIcon(this.field_150067_a);
/* 187:    */   }
/* 188:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockBasePressurePlate
 * JD-Core Version:    0.7.0.1
 */