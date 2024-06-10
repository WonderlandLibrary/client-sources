/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.material.Material;
/*   5:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   6:    */ import net.minecraft.entity.player.EntityPlayer;
/*   7:    */ import net.minecraft.item.Item;
/*   8:    */ import net.minecraft.tileentity.TileEntity;
/*   9:    */ import net.minecraft.tileentity.TileEntityPiston;
/*  10:    */ import net.minecraft.util.AxisAlignedBB;
/*  11:    */ import net.minecraft.world.IBlockAccess;
/*  12:    */ import net.minecraft.world.World;
/*  13:    */ 
/*  14:    */ public class BlockPistonMoving
/*  15:    */   extends BlockContainer
/*  16:    */ {
/*  17:    */   private static final String __OBFID = "CL_00000368";
/*  18:    */   
/*  19:    */   public BlockPistonMoving()
/*  20:    */   {
/*  21: 21 */     super(Material.piston);
/*  22: 22 */     setHardness(-1.0F);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
/*  26:    */   {
/*  27: 30 */     return null;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_) {}
/*  31:    */   
/*  32:    */   public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
/*  33:    */   {
/*  34: 37 */     TileEntity var7 = p_149749_1_.getTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);
/*  35: 39 */     if ((var7 instanceof TileEntityPiston)) {
/*  36: 41 */       ((TileEntityPiston)var7).func_145866_f();
/*  37:    */     } else {
/*  38: 45 */       super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
/*  39:    */     }
/*  40:    */   }
/*  41:    */   
/*  42:    */   public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_)
/*  43:    */   {
/*  44: 51 */     return false;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public boolean canPlaceBlockOnSide(World p_149707_1_, int p_149707_2_, int p_149707_3_, int p_149707_4_, int p_149707_5_)
/*  48:    */   {
/*  49: 59 */     return false;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public int getRenderType()
/*  53:    */   {
/*  54: 67 */     return -1;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public boolean isOpaqueCube()
/*  58:    */   {
/*  59: 72 */     return false;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public boolean renderAsNormalBlock()
/*  63:    */   {
/*  64: 77 */     return false;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
/*  68:    */   {
/*  69: 85 */     if ((!p_149727_1_.isClient) && (p_149727_1_.getTileEntity(p_149727_2_, p_149727_3_, p_149727_4_) == null))
/*  70:    */     {
/*  71: 87 */       p_149727_1_.setBlockToAir(p_149727_2_, p_149727_3_, p_149727_4_);
/*  72: 88 */       return true;
/*  73:    */     }
/*  74: 92 */     return false;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
/*  78:    */   {
/*  79: 98 */     return null;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void dropBlockAsItemWithChance(World p_149690_1_, int p_149690_2_, int p_149690_3_, int p_149690_4_, int p_149690_5_, float p_149690_6_, int p_149690_7_)
/*  83:    */   {
/*  84:106 */     if (!p_149690_1_.isClient)
/*  85:    */     {
/*  86:108 */       TileEntityPiston var8 = func_149963_e(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_);
/*  87:110 */       if (var8 != null) {
/*  88:112 */         var8.func_145861_a().dropBlockAsItem(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, var8.getBlockMetadata(), 0);
/*  89:    */       }
/*  90:    */     }
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
/*  94:    */   {
/*  95:119 */     if (!p_149695_1_.isClient) {
/*  96:121 */       p_149695_1_.getTileEntity(p_149695_2_, p_149695_3_, p_149695_4_);
/*  97:    */     }
/*  98:    */   }
/*  99:    */   
/* 100:    */   public static TileEntity func_149962_a(Block p_149962_0_, int p_149962_1_, int p_149962_2_, boolean p_149962_3_, boolean p_149962_4_)
/* 101:    */   {
/* 102:127 */     return new TileEntityPiston(p_149962_0_, p_149962_1_, p_149962_2_, p_149962_3_, p_149962_4_);
/* 103:    */   }
/* 104:    */   
/* 105:    */   public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
/* 106:    */   {
/* 107:136 */     TileEntityPiston var5 = func_149963_e(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
/* 108:138 */     if (var5 == null) {
/* 109:140 */       return null;
/* 110:    */     }
/* 111:144 */     float var6 = var5.func_145860_a(0.0F);
/* 112:146 */     if (var5.func_145868_b()) {
/* 113:148 */       var6 = 1.0F - var6;
/* 114:    */     }
/* 115:151 */     return func_149964_a(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_, var5.func_145861_a(), var6, var5.func_145864_c());
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
/* 119:    */   {
/* 120:157 */     TileEntityPiston var5 = func_149963_e(p_149719_1_, p_149719_2_, p_149719_3_, p_149719_4_);
/* 121:159 */     if (var5 != null)
/* 122:    */     {
/* 123:161 */       Block var6 = var5.func_145861_a();
/* 124:163 */       if ((var6 == this) || (var6.getMaterial() == Material.air)) {
/* 125:165 */         return;
/* 126:    */       }
/* 127:168 */       var6.setBlockBoundsBasedOnState(p_149719_1_, p_149719_2_, p_149719_3_, p_149719_4_);
/* 128:169 */       float var7 = var5.func_145860_a(0.0F);
/* 129:171 */       if (var5.func_145868_b()) {
/* 130:173 */         var7 = 1.0F - var7;
/* 131:    */       }
/* 132:176 */       int var8 = var5.func_145864_c();
/* 133:177 */       this.field_149759_B = (var6.getBlockBoundsMinX() - net.minecraft.util.Facing.offsetsXForSide[var8] * var7);
/* 134:178 */       this.field_149760_C = (var6.getBlockBoundsMinY() - net.minecraft.util.Facing.offsetsYForSide[var8] * var7);
/* 135:179 */       this.field_149754_D = (var6.getBlockBoundsMinZ() - net.minecraft.util.Facing.offsetsZForSide[var8] * var7);
/* 136:180 */       this.field_149755_E = (var6.getBlockBoundsMaxX() - net.minecraft.util.Facing.offsetsXForSide[var8] * var7);
/* 137:181 */       this.field_149756_F = (var6.getBlockBoundsMaxY() - net.minecraft.util.Facing.offsetsYForSide[var8] * var7);
/* 138:182 */       this.field_149757_G = (var6.getBlockBoundsMaxZ() - net.minecraft.util.Facing.offsetsZForSide[var8] * var7);
/* 139:    */     }
/* 140:    */   }
/* 141:    */   
/* 142:    */   public AxisAlignedBB func_149964_a(World p_149964_1_, int p_149964_2_, int p_149964_3_, int p_149964_4_, Block p_149964_5_, float p_149964_6_, int p_149964_7_)
/* 143:    */   {
/* 144:188 */     if ((p_149964_5_ != this) && (p_149964_5_.getMaterial() != Material.air))
/* 145:    */     {
/* 146:190 */       AxisAlignedBB var8 = p_149964_5_.getCollisionBoundingBoxFromPool(p_149964_1_, p_149964_2_, p_149964_3_, p_149964_4_);
/* 147:192 */       if (var8 == null) {
/* 148:194 */         return null;
/* 149:    */       }
/* 150:198 */       if (net.minecraft.util.Facing.offsetsXForSide[p_149964_7_] < 0) {
/* 151:200 */         var8.minX -= net.minecraft.util.Facing.offsetsXForSide[p_149964_7_] * p_149964_6_;
/* 152:    */       } else {
/* 153:204 */         var8.maxX -= net.minecraft.util.Facing.offsetsXForSide[p_149964_7_] * p_149964_6_;
/* 154:    */       }
/* 155:207 */       if (net.minecraft.util.Facing.offsetsYForSide[p_149964_7_] < 0) {
/* 156:209 */         var8.minY -= net.minecraft.util.Facing.offsetsYForSide[p_149964_7_] * p_149964_6_;
/* 157:    */       } else {
/* 158:213 */         var8.maxY -= net.minecraft.util.Facing.offsetsYForSide[p_149964_7_] * p_149964_6_;
/* 159:    */       }
/* 160:216 */       if (net.minecraft.util.Facing.offsetsZForSide[p_149964_7_] < 0) {
/* 161:218 */         var8.minZ -= net.minecraft.util.Facing.offsetsZForSide[p_149964_7_] * p_149964_6_;
/* 162:    */       } else {
/* 163:222 */         var8.maxZ -= net.minecraft.util.Facing.offsetsZForSide[p_149964_7_] * p_149964_6_;
/* 164:    */       }
/* 165:225 */       return var8;
/* 166:    */     }
/* 167:230 */     return null;
/* 168:    */   }
/* 169:    */   
/* 170:    */   private TileEntityPiston func_149963_e(IBlockAccess p_149963_1_, int p_149963_2_, int p_149963_3_, int p_149963_4_)
/* 171:    */   {
/* 172:236 */     TileEntity var5 = p_149963_1_.getTileEntity(p_149963_2_, p_149963_3_, p_149963_4_);
/* 173:237 */     return (var5 instanceof TileEntityPiston) ? (TileEntityPiston)var5 : null;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
/* 177:    */   {
/* 178:245 */     return Item.getItemById(0);
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void registerBlockIcons(IIconRegister p_149651_1_)
/* 182:    */   {
/* 183:250 */     this.blockIcon = p_149651_1_.registerIcon("piston_top_normal");
/* 184:    */   }
/* 185:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockPistonMoving
 * JD-Core Version:    0.7.0.1
 */