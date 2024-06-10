/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.material.Material;
/*   5:    */ import net.minecraft.creativetab.CreativeTabs;
/*   6:    */ import net.minecraft.init.Blocks;
/*   7:    */ import net.minecraft.util.AxisAlignedBB;
/*   8:    */ import net.minecraft.util.MovingObjectPosition;
/*   9:    */ import net.minecraft.util.Vec3;
/*  10:    */ import net.minecraft.world.World;
/*  11:    */ 
/*  12:    */ public class BlockTorch
/*  13:    */   extends Block
/*  14:    */ {
/*  15:    */   private static final String __OBFID = "CL_00000325";
/*  16:    */   
/*  17:    */   protected BlockTorch()
/*  18:    */   {
/*  19: 18 */     super(Material.circuits);
/*  20: 19 */     setTickRandomly(true);
/*  21: 20 */     setCreativeTab(CreativeTabs.tabDecorations);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
/*  25:    */   {
/*  26: 29 */     return null;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public boolean isOpaqueCube()
/*  30:    */   {
/*  31: 34 */     return false;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public boolean renderAsNormalBlock()
/*  35:    */   {
/*  36: 39 */     return false;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public int getRenderType()
/*  40:    */   {
/*  41: 47 */     return 2;
/*  42:    */   }
/*  43:    */   
/*  44:    */   private boolean func_150107_m(World p_150107_1_, int p_150107_2_, int p_150107_3_, int p_150107_4_)
/*  45:    */   {
/*  46: 52 */     if (World.doesBlockHaveSolidTopSurface(p_150107_1_, p_150107_2_, p_150107_3_, p_150107_4_)) {
/*  47: 54 */       return true;
/*  48:    */     }
/*  49: 58 */     Block var5 = p_150107_1_.getBlock(p_150107_2_, p_150107_3_, p_150107_4_);
/*  50: 59 */     return (var5 == Blocks.fence) || (var5 == Blocks.nether_brick_fence) || (var5 == Blocks.glass) || (var5 == Blocks.cobblestone_wall);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_)
/*  54:    */   {
/*  55: 65 */     return p_149742_1_.isBlockNormalCubeDefault(p_149742_2_, p_149742_3_, p_149742_4_ + 1, true) ? true : p_149742_1_.isBlockNormalCubeDefault(p_149742_2_, p_149742_3_, p_149742_4_ - 1, true) ? true : p_149742_1_.isBlockNormalCubeDefault(p_149742_2_ + 1, p_149742_3_, p_149742_4_, true) ? true : p_149742_1_.isBlockNormalCubeDefault(p_149742_2_ - 1, p_149742_3_, p_149742_4_, true) ? true : func_150107_m(p_149742_1_, p_149742_2_, p_149742_3_ - 1, p_149742_4_);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public int onBlockPlaced(World p_149660_1_, int p_149660_2_, int p_149660_3_, int p_149660_4_, int p_149660_5_, float p_149660_6_, float p_149660_7_, float p_149660_8_, int p_149660_9_)
/*  59:    */   {
/*  60: 70 */     int var10 = p_149660_9_;
/*  61: 72 */     if ((p_149660_5_ == 1) && (func_150107_m(p_149660_1_, p_149660_2_, p_149660_3_ - 1, p_149660_4_))) {
/*  62: 74 */       var10 = 5;
/*  63:    */     }
/*  64: 77 */     if ((p_149660_5_ == 2) && (p_149660_1_.isBlockNormalCubeDefault(p_149660_2_, p_149660_3_, p_149660_4_ + 1, true))) {
/*  65: 79 */       var10 = 4;
/*  66:    */     }
/*  67: 82 */     if ((p_149660_5_ == 3) && (p_149660_1_.isBlockNormalCubeDefault(p_149660_2_, p_149660_3_, p_149660_4_ - 1, true))) {
/*  68: 84 */       var10 = 3;
/*  69:    */     }
/*  70: 87 */     if ((p_149660_5_ == 4) && (p_149660_1_.isBlockNormalCubeDefault(p_149660_2_ + 1, p_149660_3_, p_149660_4_, true))) {
/*  71: 89 */       var10 = 2;
/*  72:    */     }
/*  73: 92 */     if ((p_149660_5_ == 5) && (p_149660_1_.isBlockNormalCubeDefault(p_149660_2_ - 1, p_149660_3_, p_149660_4_, true))) {
/*  74: 94 */       var10 = 1;
/*  75:    */     }
/*  76: 97 */     return var10;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
/*  80:    */   {
/*  81:105 */     super.updateTick(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, p_149674_5_);
/*  82:107 */     if (p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_) == 0) {
/*  83:109 */       onBlockAdded(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_);
/*  84:    */     }
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_)
/*  88:    */   {
/*  89:115 */     if (p_149726_1_.getBlockMetadata(p_149726_2_, p_149726_3_, p_149726_4_) == 0) {
/*  90:117 */       if (p_149726_1_.isBlockNormalCubeDefault(p_149726_2_ - 1, p_149726_3_, p_149726_4_, true)) {
/*  91:119 */         p_149726_1_.setBlockMetadataWithNotify(p_149726_2_, p_149726_3_, p_149726_4_, 1, 2);
/*  92:121 */       } else if (p_149726_1_.isBlockNormalCubeDefault(p_149726_2_ + 1, p_149726_3_, p_149726_4_, true)) {
/*  93:123 */         p_149726_1_.setBlockMetadataWithNotify(p_149726_2_, p_149726_3_, p_149726_4_, 2, 2);
/*  94:125 */       } else if (p_149726_1_.isBlockNormalCubeDefault(p_149726_2_, p_149726_3_, p_149726_4_ - 1, true)) {
/*  95:127 */         p_149726_1_.setBlockMetadataWithNotify(p_149726_2_, p_149726_3_, p_149726_4_, 3, 2);
/*  96:129 */       } else if (p_149726_1_.isBlockNormalCubeDefault(p_149726_2_, p_149726_3_, p_149726_4_ + 1, true)) {
/*  97:131 */         p_149726_1_.setBlockMetadataWithNotify(p_149726_2_, p_149726_3_, p_149726_4_, 4, 2);
/*  98:133 */       } else if (func_150107_m(p_149726_1_, p_149726_2_, p_149726_3_ - 1, p_149726_4_)) {
/*  99:135 */         p_149726_1_.setBlockMetadataWithNotify(p_149726_2_, p_149726_3_, p_149726_4_, 5, 2);
/* 100:    */       }
/* 101:    */     }
/* 102:139 */     func_150109_e(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
/* 106:    */   {
/* 107:144 */     func_150108_b(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_5_);
/* 108:    */   }
/* 109:    */   
/* 110:    */   protected boolean func_150108_b(World p_150108_1_, int p_150108_2_, int p_150108_3_, int p_150108_4_, Block p_150108_5_)
/* 111:    */   {
/* 112:149 */     if (func_150109_e(p_150108_1_, p_150108_2_, p_150108_3_, p_150108_4_))
/* 113:    */     {
/* 114:151 */       int var6 = p_150108_1_.getBlockMetadata(p_150108_2_, p_150108_3_, p_150108_4_);
/* 115:152 */       boolean var7 = false;
/* 116:154 */       if ((!p_150108_1_.isBlockNormalCubeDefault(p_150108_2_ - 1, p_150108_3_, p_150108_4_, true)) && (var6 == 1)) {
/* 117:156 */         var7 = true;
/* 118:    */       }
/* 119:159 */       if ((!p_150108_1_.isBlockNormalCubeDefault(p_150108_2_ + 1, p_150108_3_, p_150108_4_, true)) && (var6 == 2)) {
/* 120:161 */         var7 = true;
/* 121:    */       }
/* 122:164 */       if ((!p_150108_1_.isBlockNormalCubeDefault(p_150108_2_, p_150108_3_, p_150108_4_ - 1, true)) && (var6 == 3)) {
/* 123:166 */         var7 = true;
/* 124:    */       }
/* 125:169 */       if ((!p_150108_1_.isBlockNormalCubeDefault(p_150108_2_, p_150108_3_, p_150108_4_ + 1, true)) && (var6 == 4)) {
/* 126:171 */         var7 = true;
/* 127:    */       }
/* 128:174 */       if ((!func_150107_m(p_150108_1_, p_150108_2_, p_150108_3_ - 1, p_150108_4_)) && (var6 == 5)) {
/* 129:176 */         var7 = true;
/* 130:    */       }
/* 131:179 */       if (var7)
/* 132:    */       {
/* 133:181 */         dropBlockAsItem(p_150108_1_, p_150108_2_, p_150108_3_, p_150108_4_, p_150108_1_.getBlockMetadata(p_150108_2_, p_150108_3_, p_150108_4_), 0);
/* 134:182 */         p_150108_1_.setBlockToAir(p_150108_2_, p_150108_3_, p_150108_4_);
/* 135:183 */         return true;
/* 136:    */       }
/* 137:187 */       return false;
/* 138:    */     }
/* 139:192 */     return true;
/* 140:    */   }
/* 141:    */   
/* 142:    */   protected boolean func_150109_e(World p_150109_1_, int p_150109_2_, int p_150109_3_, int p_150109_4_)
/* 143:    */   {
/* 144:198 */     if (!canPlaceBlockAt(p_150109_1_, p_150109_2_, p_150109_3_, p_150109_4_))
/* 145:    */     {
/* 146:200 */       if (p_150109_1_.getBlock(p_150109_2_, p_150109_3_, p_150109_4_) == this)
/* 147:    */       {
/* 148:202 */         dropBlockAsItem(p_150109_1_, p_150109_2_, p_150109_3_, p_150109_4_, p_150109_1_.getBlockMetadata(p_150109_2_, p_150109_3_, p_150109_4_), 0);
/* 149:203 */         p_150109_1_.setBlockToAir(p_150109_2_, p_150109_3_, p_150109_4_);
/* 150:    */       }
/* 151:206 */       return false;
/* 152:    */     }
/* 153:210 */     return true;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public MovingObjectPosition collisionRayTrace(World p_149731_1_, int p_149731_2_, int p_149731_3_, int p_149731_4_, Vec3 p_149731_5_, Vec3 p_149731_6_)
/* 157:    */   {
/* 158:216 */     int var7 = p_149731_1_.getBlockMetadata(p_149731_2_, p_149731_3_, p_149731_4_) & 0x7;
/* 159:217 */     float var8 = 0.15F;
/* 160:219 */     if (var7 == 1)
/* 161:    */     {
/* 162:221 */       setBlockBounds(0.0F, 0.2F, 0.5F - var8, var8 * 2.0F, 0.8F, 0.5F + var8);
/* 163:    */     }
/* 164:223 */     else if (var7 == 2)
/* 165:    */     {
/* 166:225 */       setBlockBounds(1.0F - var8 * 2.0F, 0.2F, 0.5F - var8, 1.0F, 0.8F, 0.5F + var8);
/* 167:    */     }
/* 168:227 */     else if (var7 == 3)
/* 169:    */     {
/* 170:229 */       setBlockBounds(0.5F - var8, 0.2F, 0.0F, 0.5F + var8, 0.8F, var8 * 2.0F);
/* 171:    */     }
/* 172:231 */     else if (var7 == 4)
/* 173:    */     {
/* 174:233 */       setBlockBounds(0.5F - var8, 0.2F, 1.0F - var8 * 2.0F, 0.5F + var8, 0.8F, 1.0F);
/* 175:    */     }
/* 176:    */     else
/* 177:    */     {
/* 178:237 */       var8 = 0.1F;
/* 179:238 */       setBlockBounds(0.5F - var8, 0.0F, 0.5F - var8, 0.5F + var8, 0.6F, 0.5F + var8);
/* 180:    */     }
/* 181:241 */     return super.collisionRayTrace(p_149731_1_, p_149731_2_, p_149731_3_, p_149731_4_, p_149731_5_, p_149731_6_);
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_)
/* 185:    */   {
/* 186:249 */     int var6 = p_149734_1_.getBlockMetadata(p_149734_2_, p_149734_3_, p_149734_4_);
/* 187:250 */     double var7 = p_149734_2_ + 0.5F;
/* 188:251 */     double var9 = p_149734_3_ + 0.7F;
/* 189:252 */     double var11 = p_149734_4_ + 0.5F;
/* 190:253 */     double var13 = 0.219999998807907D;
/* 191:254 */     double var15 = 0.2700000107288361D;
/* 192:256 */     if (var6 == 1)
/* 193:    */     {
/* 194:258 */       p_149734_1_.spawnParticle("smoke", var7 - var15, var9 + var13, var11, 0.0D, 0.0D, 0.0D);
/* 195:259 */       p_149734_1_.spawnParticle("flame", var7 - var15, var9 + var13, var11, 0.0D, 0.0D, 0.0D);
/* 196:    */     }
/* 197:261 */     else if (var6 == 2)
/* 198:    */     {
/* 199:263 */       p_149734_1_.spawnParticle("smoke", var7 + var15, var9 + var13, var11, 0.0D, 0.0D, 0.0D);
/* 200:264 */       p_149734_1_.spawnParticle("flame", var7 + var15, var9 + var13, var11, 0.0D, 0.0D, 0.0D);
/* 201:    */     }
/* 202:266 */     else if (var6 == 3)
/* 203:    */     {
/* 204:268 */       p_149734_1_.spawnParticle("smoke", var7, var9 + var13, var11 - var15, 0.0D, 0.0D, 0.0D);
/* 205:269 */       p_149734_1_.spawnParticle("flame", var7, var9 + var13, var11 - var15, 0.0D, 0.0D, 0.0D);
/* 206:    */     }
/* 207:271 */     else if (var6 == 4)
/* 208:    */     {
/* 209:273 */       p_149734_1_.spawnParticle("smoke", var7, var9 + var13, var11 + var15, 0.0D, 0.0D, 0.0D);
/* 210:274 */       p_149734_1_.spawnParticle("flame", var7, var9 + var13, var11 + var15, 0.0D, 0.0D, 0.0D);
/* 211:    */     }
/* 212:    */     else
/* 213:    */     {
/* 214:278 */       p_149734_1_.spawnParticle("smoke", var7, var9, var11, 0.0D, 0.0D, 0.0D);
/* 215:279 */       p_149734_1_.spawnParticle("flame", var7, var9, var11, 0.0D, 0.0D, 0.0D);
/* 216:    */     }
/* 217:    */   }
/* 218:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockTorch
 * JD-Core Version:    0.7.0.1
 */