/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.Random;
/*   5:    */ import net.minecraft.block.material.Material;
/*   6:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   7:    */ import net.minecraft.creativetab.CreativeTabs;
/*   8:    */ import net.minecraft.entity.Entity;
/*   9:    */ import net.minecraft.entity.player.EntityPlayer;
/*  10:    */ import net.minecraft.entity.projectile.EntityArrow;
/*  11:    */ import net.minecraft.util.AABBPool;
/*  12:    */ import net.minecraft.util.AxisAlignedBB;
/*  13:    */ import net.minecraft.world.IBlockAccess;
/*  14:    */ import net.minecraft.world.World;
/*  15:    */ 
/*  16:    */ public abstract class BlockButton
/*  17:    */   extends Block
/*  18:    */ {
/*  19:    */   private final boolean field_150047_a;
/*  20:    */   private static final String __OBFID = "CL_00000209";
/*  21:    */   
/*  22:    */   protected BlockButton(boolean p_i45396_1_)
/*  23:    */   {
/*  24: 22 */     super(Material.circuits);
/*  25: 23 */     setTickRandomly(true);
/*  26: 24 */     setCreativeTab(CreativeTabs.tabRedstone);
/*  27: 25 */     this.field_150047_a = p_i45396_1_;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
/*  31:    */   {
/*  32: 34 */     return null;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public int func_149738_a(World p_149738_1_)
/*  36:    */   {
/*  37: 39 */     return this.field_150047_a ? 30 : 20;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public boolean isOpaqueCube()
/*  41:    */   {
/*  42: 44 */     return false;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public boolean renderAsNormalBlock()
/*  46:    */   {
/*  47: 49 */     return false;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public boolean canPlaceBlockOnSide(World p_149707_1_, int p_149707_2_, int p_149707_3_, int p_149707_4_, int p_149707_5_)
/*  51:    */   {
/*  52: 57 */     return (p_149707_5_ == 2) && (p_149707_1_.getBlock(p_149707_2_, p_149707_3_, p_149707_4_ + 1).isNormalCube());
/*  53:    */   }
/*  54:    */   
/*  55:    */   public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_)
/*  56:    */   {
/*  57: 62 */     return p_149742_1_.getBlock(p_149742_2_, p_149742_3_, p_149742_4_ - 1).isNormalCube() ? true : p_149742_1_.getBlock(p_149742_2_ + 1, p_149742_3_, p_149742_4_).isNormalCube() ? true : p_149742_1_.getBlock(p_149742_2_ - 1, p_149742_3_, p_149742_4_).isNormalCube() ? true : p_149742_1_.getBlock(p_149742_2_, p_149742_3_, p_149742_4_ + 1).isNormalCube();
/*  58:    */   }
/*  59:    */   
/*  60:    */   public int onBlockPlaced(World p_149660_1_, int p_149660_2_, int p_149660_3_, int p_149660_4_, int p_149660_5_, float p_149660_6_, float p_149660_7_, float p_149660_8_, int p_149660_9_)
/*  61:    */   {
/*  62: 67 */     int var10 = p_149660_1_.getBlockMetadata(p_149660_2_, p_149660_3_, p_149660_4_);
/*  63: 68 */     int var11 = var10 & 0x8;
/*  64: 69 */     var10 &= 0x7;
/*  65: 71 */     if ((p_149660_5_ == 2) && (p_149660_1_.getBlock(p_149660_2_, p_149660_3_, p_149660_4_ + 1).isNormalCube())) {
/*  66: 73 */       var10 = 4;
/*  67: 75 */     } else if ((p_149660_5_ == 3) && (p_149660_1_.getBlock(p_149660_2_, p_149660_3_, p_149660_4_ - 1).isNormalCube())) {
/*  68: 77 */       var10 = 3;
/*  69: 79 */     } else if ((p_149660_5_ == 4) && (p_149660_1_.getBlock(p_149660_2_ + 1, p_149660_3_, p_149660_4_).isNormalCube())) {
/*  70: 81 */       var10 = 2;
/*  71: 83 */     } else if ((p_149660_5_ == 5) && (p_149660_1_.getBlock(p_149660_2_ - 1, p_149660_3_, p_149660_4_).isNormalCube())) {
/*  72: 85 */       var10 = 1;
/*  73:    */     } else {
/*  74: 89 */       var10 = func_150045_e(p_149660_1_, p_149660_2_, p_149660_3_, p_149660_4_);
/*  75:    */     }
/*  76: 92 */     return var10 + var11;
/*  77:    */   }
/*  78:    */   
/*  79:    */   private int func_150045_e(World p_150045_1_, int p_150045_2_, int p_150045_3_, int p_150045_4_)
/*  80:    */   {
/*  81: 97 */     return p_150045_1_.getBlock(p_150045_2_, p_150045_3_, p_150045_4_ + 1).isNormalCube() ? 4 : p_150045_1_.getBlock(p_150045_2_, p_150045_3_, p_150045_4_ - 1).isNormalCube() ? 3 : p_150045_1_.getBlock(p_150045_2_ + 1, p_150045_3_, p_150045_4_).isNormalCube() ? 2 : p_150045_1_.getBlock(p_150045_2_ - 1, p_150045_3_, p_150045_4_).isNormalCube() ? 1 : 1;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
/*  85:    */   {
/*  86:102 */     if (func_150044_m(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_))
/*  87:    */     {
/*  88:104 */       int var6 = p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_) & 0x7;
/*  89:105 */       boolean var7 = false;
/*  90:107 */       if ((!p_149695_1_.getBlock(p_149695_2_ - 1, p_149695_3_, p_149695_4_).isNormalCube()) && (var6 == 1)) {
/*  91:109 */         var7 = true;
/*  92:    */       }
/*  93:112 */       if ((!p_149695_1_.getBlock(p_149695_2_ + 1, p_149695_3_, p_149695_4_).isNormalCube()) && (var6 == 2)) {
/*  94:114 */         var7 = true;
/*  95:    */       }
/*  96:117 */       if ((!p_149695_1_.getBlock(p_149695_2_, p_149695_3_, p_149695_4_ - 1).isNormalCube()) && (var6 == 3)) {
/*  97:119 */         var7 = true;
/*  98:    */       }
/*  99:122 */       if ((!p_149695_1_.getBlock(p_149695_2_, p_149695_3_, p_149695_4_ + 1).isNormalCube()) && (var6 == 4)) {
/* 100:124 */         var7 = true;
/* 101:    */       }
/* 102:127 */       if (var7)
/* 103:    */       {
/* 104:129 */         dropBlockAsItem(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_), 0);
/* 105:130 */         p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
/* 106:    */       }
/* 107:    */     }
/* 108:    */   }
/* 109:    */   
/* 110:    */   private boolean func_150044_m(World p_150044_1_, int p_150044_2_, int p_150044_3_, int p_150044_4_)
/* 111:    */   {
/* 112:137 */     if (!canPlaceBlockAt(p_150044_1_, p_150044_2_, p_150044_3_, p_150044_4_))
/* 113:    */     {
/* 114:139 */       dropBlockAsItem(p_150044_1_, p_150044_2_, p_150044_3_, p_150044_4_, p_150044_1_.getBlockMetadata(p_150044_2_, p_150044_3_, p_150044_4_), 0);
/* 115:140 */       p_150044_1_.setBlockToAir(p_150044_2_, p_150044_3_, p_150044_4_);
/* 116:141 */       return false;
/* 117:    */     }
/* 118:145 */     return true;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
/* 122:    */   {
/* 123:151 */     int var5 = p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_);
/* 124:152 */     func_150043_b(var5);
/* 125:    */   }
/* 126:    */   
/* 127:    */   private void func_150043_b(int p_150043_1_)
/* 128:    */   {
/* 129:157 */     int var2 = p_150043_1_ & 0x7;
/* 130:158 */     boolean var3 = (p_150043_1_ & 0x8) > 0;
/* 131:159 */     float var4 = 0.375F;
/* 132:160 */     float var5 = 0.625F;
/* 133:161 */     float var6 = 0.1875F;
/* 134:162 */     float var7 = 0.125F;
/* 135:164 */     if (var3) {
/* 136:166 */       var7 = 0.0625F;
/* 137:    */     }
/* 138:169 */     if (var2 == 1) {
/* 139:171 */       setBlockBounds(0.0F, var4, 0.5F - var6, var7, var5, 0.5F + var6);
/* 140:173 */     } else if (var2 == 2) {
/* 141:175 */       setBlockBounds(1.0F - var7, var4, 0.5F - var6, 1.0F, var5, 0.5F + var6);
/* 142:177 */     } else if (var2 == 3) {
/* 143:179 */       setBlockBounds(0.5F - var6, var4, 0.0F, 0.5F + var6, var5, var7);
/* 144:181 */     } else if (var2 == 4) {
/* 145:183 */       setBlockBounds(0.5F - var6, var4, 1.0F - var7, 0.5F + var6, var5, 1.0F);
/* 146:    */     }
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void onBlockClicked(World p_149699_1_, int p_149699_2_, int p_149699_3_, int p_149699_4_, EntityPlayer p_149699_5_) {}
/* 150:    */   
/* 151:    */   public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
/* 152:    */   {
/* 153:197 */     int var10 = p_149727_1_.getBlockMetadata(p_149727_2_, p_149727_3_, p_149727_4_);
/* 154:198 */     int var11 = var10 & 0x7;
/* 155:199 */     int var12 = 8 - (var10 & 0x8);
/* 156:201 */     if (var12 == 0) {
/* 157:203 */       return true;
/* 158:    */     }
/* 159:207 */     p_149727_1_.setBlockMetadataWithNotify(p_149727_2_, p_149727_3_, p_149727_4_, var11 + var12, 3);
/* 160:208 */     p_149727_1_.markBlockRangeForRenderUpdate(p_149727_2_, p_149727_3_, p_149727_4_, p_149727_2_, p_149727_3_, p_149727_4_);
/* 161:209 */     p_149727_1_.playSoundEffect(p_149727_2_ + 0.5D, p_149727_3_ + 0.5D, p_149727_4_ + 0.5D, "random.click", 0.3F, 0.6F);
/* 162:210 */     func_150042_a(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_, var11);
/* 163:211 */     p_149727_1_.scheduleBlockUpdate(p_149727_2_, p_149727_3_, p_149727_4_, this, func_149738_a(p_149727_1_));
/* 164:212 */     return true;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
/* 168:    */   {
/* 169:218 */     if ((p_149749_6_ & 0x8) > 0)
/* 170:    */     {
/* 171:220 */       int var7 = p_149749_6_ & 0x7;
/* 172:221 */       func_150042_a(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, var7);
/* 173:    */     }
/* 174:224 */     super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
/* 175:    */   }
/* 176:    */   
/* 177:    */   public int isProvidingWeakPower(IBlockAccess p_149709_1_, int p_149709_2_, int p_149709_3_, int p_149709_4_, int p_149709_5_)
/* 178:    */   {
/* 179:229 */     return (p_149709_1_.getBlockMetadata(p_149709_2_, p_149709_3_, p_149709_4_) & 0x8) > 0 ? 15 : 0;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public int isProvidingStrongPower(IBlockAccess p_149748_1_, int p_149748_2_, int p_149748_3_, int p_149748_4_, int p_149748_5_)
/* 183:    */   {
/* 184:234 */     int var6 = p_149748_1_.getBlockMetadata(p_149748_2_, p_149748_3_, p_149748_4_);
/* 185:236 */     if ((var6 & 0x8) == 0) {
/* 186:238 */       return 0;
/* 187:    */     }
/* 188:242 */     int var7 = var6 & 0x7;
/* 189:243 */     return (var7 == 1) && (p_149748_5_ == 5) ? 15 : (var7 == 2) && (p_149748_5_ == 4) ? 15 : (var7 == 3) && (p_149748_5_ == 3) ? 15 : (var7 == 4) && (p_149748_5_ == 2) ? 15 : (var7 == 5) && (p_149748_5_ == 1) ? 15 : 0;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public boolean canProvidePower()
/* 193:    */   {
/* 194:252 */     return true;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
/* 198:    */   {
/* 199:260 */     if (!p_149674_1_.isClient)
/* 200:    */     {
/* 201:262 */       int var6 = p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_);
/* 202:264 */       if ((var6 & 0x8) != 0) {
/* 203:266 */         if (this.field_150047_a)
/* 204:    */         {
/* 205:268 */           func_150046_n(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_);
/* 206:    */         }
/* 207:    */         else
/* 208:    */         {
/* 209:272 */           p_149674_1_.setBlockMetadataWithNotify(p_149674_2_, p_149674_3_, p_149674_4_, var6 & 0x7, 3);
/* 210:273 */           int var7 = var6 & 0x7;
/* 211:274 */           func_150042_a(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, var7);
/* 212:275 */           p_149674_1_.playSoundEffect(p_149674_2_ + 0.5D, p_149674_3_ + 0.5D, p_149674_4_ + 0.5D, "random.click", 0.3F, 0.5F);
/* 213:276 */           p_149674_1_.markBlockRangeForRenderUpdate(p_149674_2_, p_149674_3_, p_149674_4_, p_149674_2_, p_149674_3_, p_149674_4_);
/* 214:    */         }
/* 215:    */       }
/* 216:    */     }
/* 217:    */   }
/* 218:    */   
/* 219:    */   public void setBlockBoundsForItemRender()
/* 220:    */   {
/* 221:287 */     float var1 = 0.1875F;
/* 222:288 */     float var2 = 0.125F;
/* 223:289 */     float var3 = 0.125F;
/* 224:290 */     setBlockBounds(0.5F - var1, 0.5F - var2, 0.5F - var3, 0.5F + var1, 0.5F + var2, 0.5F + var3);
/* 225:    */   }
/* 226:    */   
/* 227:    */   public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity p_149670_5_)
/* 228:    */   {
/* 229:295 */     if (!p_149670_1_.isClient) {
/* 230:297 */       if (this.field_150047_a) {
/* 231:299 */         if ((p_149670_1_.getBlockMetadata(p_149670_2_, p_149670_3_, p_149670_4_) & 0x8) == 0) {
/* 232:301 */           func_150046_n(p_149670_1_, p_149670_2_, p_149670_3_, p_149670_4_);
/* 233:    */         }
/* 234:    */       }
/* 235:    */     }
/* 236:    */   }
/* 237:    */   
/* 238:    */   private void func_150046_n(World p_150046_1_, int p_150046_2_, int p_150046_3_, int p_150046_4_)
/* 239:    */   {
/* 240:309 */     int var5 = p_150046_1_.getBlockMetadata(p_150046_2_, p_150046_3_, p_150046_4_);
/* 241:310 */     int var6 = var5 & 0x7;
/* 242:311 */     boolean var7 = (var5 & 0x8) != 0;
/* 243:312 */     func_150043_b(var5);
/* 244:313 */     List var9 = p_150046_1_.getEntitiesWithinAABB(EntityArrow.class, AxisAlignedBB.getAABBPool().getAABB(p_150046_2_ + this.field_149759_B, p_150046_3_ + this.field_149760_C, p_150046_4_ + this.field_149754_D, p_150046_2_ + this.field_149755_E, p_150046_3_ + this.field_149756_F, p_150046_4_ + this.field_149757_G));
/* 245:314 */     boolean var8 = !var9.isEmpty();
/* 246:316 */     if ((var8) && (!var7))
/* 247:    */     {
/* 248:318 */       p_150046_1_.setBlockMetadataWithNotify(p_150046_2_, p_150046_3_, p_150046_4_, var6 | 0x8, 3);
/* 249:319 */       func_150042_a(p_150046_1_, p_150046_2_, p_150046_3_, p_150046_4_, var6);
/* 250:320 */       p_150046_1_.markBlockRangeForRenderUpdate(p_150046_2_, p_150046_3_, p_150046_4_, p_150046_2_, p_150046_3_, p_150046_4_);
/* 251:321 */       p_150046_1_.playSoundEffect(p_150046_2_ + 0.5D, p_150046_3_ + 0.5D, p_150046_4_ + 0.5D, "random.click", 0.3F, 0.6F);
/* 252:    */     }
/* 253:324 */     if ((!var8) && (var7))
/* 254:    */     {
/* 255:326 */       p_150046_1_.setBlockMetadataWithNotify(p_150046_2_, p_150046_3_, p_150046_4_, var6, 3);
/* 256:327 */       func_150042_a(p_150046_1_, p_150046_2_, p_150046_3_, p_150046_4_, var6);
/* 257:328 */       p_150046_1_.markBlockRangeForRenderUpdate(p_150046_2_, p_150046_3_, p_150046_4_, p_150046_2_, p_150046_3_, p_150046_4_);
/* 258:329 */       p_150046_1_.playSoundEffect(p_150046_2_ + 0.5D, p_150046_3_ + 0.5D, p_150046_4_ + 0.5D, "random.click", 0.3F, 0.5F);
/* 259:    */     }
/* 260:332 */     if (var8) {
/* 261:334 */       p_150046_1_.scheduleBlockUpdate(p_150046_2_, p_150046_3_, p_150046_4_, this, func_149738_a(p_150046_1_));
/* 262:    */     }
/* 263:    */   }
/* 264:    */   
/* 265:    */   private void func_150042_a(World p_150042_1_, int p_150042_2_, int p_150042_3_, int p_150042_4_, int p_150042_5_)
/* 266:    */   {
/* 267:340 */     p_150042_1_.notifyBlocksOfNeighborChange(p_150042_2_, p_150042_3_, p_150042_4_, this);
/* 268:342 */     if (p_150042_5_ == 1) {
/* 269:344 */       p_150042_1_.notifyBlocksOfNeighborChange(p_150042_2_ - 1, p_150042_3_, p_150042_4_, this);
/* 270:346 */     } else if (p_150042_5_ == 2) {
/* 271:348 */       p_150042_1_.notifyBlocksOfNeighborChange(p_150042_2_ + 1, p_150042_3_, p_150042_4_, this);
/* 272:350 */     } else if (p_150042_5_ == 3) {
/* 273:352 */       p_150042_1_.notifyBlocksOfNeighborChange(p_150042_2_, p_150042_3_, p_150042_4_ - 1, this);
/* 274:354 */     } else if (p_150042_5_ == 4) {
/* 275:356 */       p_150042_1_.notifyBlocksOfNeighborChange(p_150042_2_, p_150042_3_, p_150042_4_ + 1, this);
/* 276:    */     } else {
/* 277:360 */       p_150042_1_.notifyBlocksOfNeighborChange(p_150042_2_, p_150042_3_ - 1, p_150042_4_, this);
/* 278:    */     }
/* 279:    */   }
/* 280:    */   
/* 281:    */   public void registerBlockIcons(IIconRegister p_149651_1_) {}
/* 282:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockButton
 * JD-Core Version:    0.7.0.1
 */