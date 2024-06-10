/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.material.Material;
/*   5:    */ import net.minecraft.creativetab.CreativeTabs;
/*   6:    */ import net.minecraft.init.Blocks;
/*   7:    */ import net.minecraft.util.AxisAlignedBB;
/*   8:    */ import net.minecraft.world.IBlockAccess;
/*   9:    */ import net.minecraft.world.World;
/*  10:    */ 
/*  11:    */ public class BlockTripWireHook
/*  12:    */   extends Block
/*  13:    */ {
/*  14:    */   private static final String __OBFID = "CL_00000329";
/*  15:    */   
/*  16:    */   public BlockTripWireHook()
/*  17:    */   {
/*  18: 18 */     super(Material.circuits);
/*  19: 19 */     setCreativeTab(CreativeTabs.tabRedstone);
/*  20: 20 */     setTickRandomly(true);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
/*  24:    */   {
/*  25: 29 */     return null;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public boolean isOpaqueCube()
/*  29:    */   {
/*  30: 34 */     return false;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public boolean renderAsNormalBlock()
/*  34:    */   {
/*  35: 39 */     return false;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public int getRenderType()
/*  39:    */   {
/*  40: 47 */     return 29;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int func_149738_a(World p_149738_1_)
/*  44:    */   {
/*  45: 52 */     return 10;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public boolean canPlaceBlockOnSide(World p_149707_1_, int p_149707_2_, int p_149707_3_, int p_149707_4_, int p_149707_5_)
/*  49:    */   {
/*  50: 60 */     return (p_149707_5_ == 2) && (p_149707_1_.getBlock(p_149707_2_, p_149707_3_, p_149707_4_ + 1).isNormalCube());
/*  51:    */   }
/*  52:    */   
/*  53:    */   public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_)
/*  54:    */   {
/*  55: 65 */     return p_149742_1_.getBlock(p_149742_2_, p_149742_3_, p_149742_4_ - 1).isNormalCube() ? true : p_149742_1_.getBlock(p_149742_2_ + 1, p_149742_3_, p_149742_4_).isNormalCube() ? true : p_149742_1_.getBlock(p_149742_2_ - 1, p_149742_3_, p_149742_4_).isNormalCube() ? true : p_149742_1_.getBlock(p_149742_2_, p_149742_3_, p_149742_4_ + 1).isNormalCube();
/*  56:    */   }
/*  57:    */   
/*  58:    */   public int onBlockPlaced(World p_149660_1_, int p_149660_2_, int p_149660_3_, int p_149660_4_, int p_149660_5_, float p_149660_6_, float p_149660_7_, float p_149660_8_, int p_149660_9_)
/*  59:    */   {
/*  60: 70 */     byte var10 = 0;
/*  61: 72 */     if ((p_149660_5_ == 2) && (p_149660_1_.isBlockNormalCubeDefault(p_149660_2_, p_149660_3_, p_149660_4_ + 1, true))) {
/*  62: 74 */       var10 = 2;
/*  63:    */     }
/*  64: 77 */     if ((p_149660_5_ == 3) && (p_149660_1_.isBlockNormalCubeDefault(p_149660_2_, p_149660_3_, p_149660_4_ - 1, true))) {
/*  65: 79 */       var10 = 0;
/*  66:    */     }
/*  67: 82 */     if ((p_149660_5_ == 4) && (p_149660_1_.isBlockNormalCubeDefault(p_149660_2_ + 1, p_149660_3_, p_149660_4_, true))) {
/*  68: 84 */       var10 = 1;
/*  69:    */     }
/*  70: 87 */     if ((p_149660_5_ == 5) && (p_149660_1_.isBlockNormalCubeDefault(p_149660_2_ - 1, p_149660_3_, p_149660_4_, true))) {
/*  71: 89 */       var10 = 3;
/*  72:    */     }
/*  73: 92 */     return var10;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void onPostBlockPlaced(World p_149714_1_, int p_149714_2_, int p_149714_3_, int p_149714_4_, int p_149714_5_)
/*  77:    */   {
/*  78:100 */     func_150136_a(p_149714_1_, p_149714_2_, p_149714_3_, p_149714_4_, false, p_149714_5_, false, -1, 0);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
/*  82:    */   {
/*  83:105 */     if (p_149695_5_ != this) {
/*  84:107 */       if (func_150137_e(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_))
/*  85:    */       {
/*  86:109 */         int var6 = p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_);
/*  87:110 */         int var7 = var6 & 0x3;
/*  88:111 */         boolean var8 = false;
/*  89:113 */         if ((!p_149695_1_.getBlock(p_149695_2_ - 1, p_149695_3_, p_149695_4_).isNormalCube()) && (var7 == 3)) {
/*  90:115 */           var8 = true;
/*  91:    */         }
/*  92:118 */         if ((!p_149695_1_.getBlock(p_149695_2_ + 1, p_149695_3_, p_149695_4_).isNormalCube()) && (var7 == 1)) {
/*  93:120 */           var8 = true;
/*  94:    */         }
/*  95:123 */         if ((!p_149695_1_.getBlock(p_149695_2_, p_149695_3_, p_149695_4_ - 1).isNormalCube()) && (var7 == 0)) {
/*  96:125 */           var8 = true;
/*  97:    */         }
/*  98:128 */         if ((!p_149695_1_.getBlock(p_149695_2_, p_149695_3_, p_149695_4_ + 1).isNormalCube()) && (var7 == 2)) {
/*  99:130 */           var8 = true;
/* 100:    */         }
/* 101:133 */         if (var8)
/* 102:    */         {
/* 103:135 */           dropBlockAsItem(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, var6, 0);
/* 104:136 */           p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
/* 105:    */         }
/* 106:    */       }
/* 107:    */     }
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void func_150136_a(World p_150136_1_, int p_150136_2_, int p_150136_3_, int p_150136_4_, boolean p_150136_5_, int p_150136_6_, boolean p_150136_7_, int p_150136_8_, int p_150136_9_)
/* 111:    */   {
/* 112:144 */     int var10 = p_150136_6_ & 0x3;
/* 113:145 */     boolean var11 = (p_150136_6_ & 0x4) == 4;
/* 114:146 */     boolean var12 = (p_150136_6_ & 0x8) == 8;
/* 115:147 */     boolean var13 = !p_150136_5_;
/* 116:148 */     boolean var14 = false;
/* 117:149 */     boolean var15 = !World.doesBlockHaveSolidTopSurface(p_150136_1_, p_150136_2_, p_150136_3_ - 1, p_150136_4_);
/* 118:150 */     int var16 = net.minecraft.util.Direction.offsetX[var10];
/* 119:151 */     int var17 = net.minecraft.util.Direction.offsetZ[var10];
/* 120:152 */     int var18 = 0;
/* 121:153 */     int[] var19 = new int[42];
/* 122:159 */     for (int var20 = 1; var20 < 42; var20++)
/* 123:    */     {
/* 124:161 */       int var21 = p_150136_2_ + var16 * var20;
/* 125:162 */       int var22 = p_150136_4_ + var17 * var20;
/* 126:163 */       Block var23 = p_150136_1_.getBlock(var21, p_150136_3_, var22);
/* 127:165 */       if (var23 == Blocks.tripwire_hook)
/* 128:    */       {
/* 129:167 */         int var24 = p_150136_1_.getBlockMetadata(var21, p_150136_3_, var22);
/* 130:169 */         if ((var24 & 0x3) != net.minecraft.util.Direction.rotateOpposite[var10]) {
/* 131:    */           break;
/* 132:    */         }
/* 133:171 */         var18 = var20;
/* 134:    */         
/* 135:    */ 
/* 136:174 */         break;
/* 137:    */       }
/* 138:177 */       if ((var23 != Blocks.tripwire) && (var20 != p_150136_8_))
/* 139:    */       {
/* 140:179 */         var19[var20] = -1;
/* 141:180 */         var13 = false;
/* 142:    */       }
/* 143:    */       else
/* 144:    */       {
/* 145:184 */         int var24 = var20 == p_150136_8_ ? p_150136_9_ : p_150136_1_.getBlockMetadata(var21, p_150136_3_, var22);
/* 146:185 */         boolean var25 = (var24 & 0x8) != 8;
/* 147:186 */         boolean var26 = (var24 & 0x1) == 1;
/* 148:187 */         boolean var27 = (var24 & 0x2) == 2;
/* 149:188 */         var13 &= var27 == var15;
/* 150:189 */         var14 |= ((var25) && (var26));
/* 151:190 */         var19[var20] = var24;
/* 152:192 */         if (var20 == p_150136_8_)
/* 153:    */         {
/* 154:194 */           p_150136_1_.scheduleBlockUpdate(p_150136_2_, p_150136_3_, p_150136_4_, this, func_149738_a(p_150136_1_));
/* 155:195 */           var13 &= var25;
/* 156:    */         }
/* 157:    */       }
/* 158:    */     }
/* 159:200 */     var13 &= var18 > 1;
/* 160:201 */     var14 &= var13;
/* 161:202 */     var20 = (var13 ? 4 : 0) | (var14 ? 8 : 0);
/* 162:203 */     p_150136_6_ = var10 | var20;
/* 163:206 */     if (var18 > 0)
/* 164:    */     {
/* 165:208 */       int var21 = p_150136_2_ + var16 * var18;
/* 166:209 */       int var22 = p_150136_4_ + var17 * var18;
/* 167:210 */       int var28 = net.minecraft.util.Direction.rotateOpposite[var10];
/* 168:211 */       p_150136_1_.setBlockMetadataWithNotify(var21, p_150136_3_, var22, var28 | var20, 3);
/* 169:212 */       func_150134_a(p_150136_1_, var21, p_150136_3_, var22, var28);
/* 170:213 */       func_150135_a(p_150136_1_, var21, p_150136_3_, var22, var13, var14, var11, var12);
/* 171:    */     }
/* 172:216 */     func_150135_a(p_150136_1_, p_150136_2_, p_150136_3_, p_150136_4_, var13, var14, var11, var12);
/* 173:218 */     if (!p_150136_5_)
/* 174:    */     {
/* 175:220 */       p_150136_1_.setBlockMetadataWithNotify(p_150136_2_, p_150136_3_, p_150136_4_, p_150136_6_, 3);
/* 176:222 */       if (p_150136_7_) {
/* 177:224 */         func_150134_a(p_150136_1_, p_150136_2_, p_150136_3_, p_150136_4_, var10);
/* 178:    */       }
/* 179:    */     }
/* 180:228 */     if (var11 != var13) {
/* 181:230 */       for (int var21 = 1; var21 < var18; var21++)
/* 182:    */       {
/* 183:232 */         int var22 = p_150136_2_ + var16 * var21;
/* 184:233 */         int var28 = p_150136_4_ + var17 * var21;
/* 185:234 */         int var24 = var19[var21];
/* 186:236 */         if (var24 >= 0)
/* 187:    */         {
/* 188:238 */           if (var13) {
/* 189:240 */             var24 |= 0x4;
/* 190:    */           } else {
/* 191:244 */             var24 &= 0xFFFFFFFB;
/* 192:    */           }
/* 193:247 */           p_150136_1_.setBlockMetadataWithNotify(var22, p_150136_3_, var28, var24, 3);
/* 194:    */         }
/* 195:    */       }
/* 196:    */     }
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
/* 200:    */   {
/* 201:258 */     func_150136_a(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, false, p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_), true, -1, 0);
/* 202:    */   }
/* 203:    */   
/* 204:    */   private void func_150135_a(World p_150135_1_, int p_150135_2_, int p_150135_3_, int p_150135_4_, boolean p_150135_5_, boolean p_150135_6_, boolean p_150135_7_, boolean p_150135_8_)
/* 205:    */   {
/* 206:263 */     if ((p_150135_6_) && (!p_150135_8_)) {
/* 207:265 */       p_150135_1_.playSoundEffect(p_150135_2_ + 0.5D, p_150135_3_ + 0.1D, p_150135_4_ + 0.5D, "random.click", 0.4F, 0.6F);
/* 208:267 */     } else if ((!p_150135_6_) && (p_150135_8_)) {
/* 209:269 */       p_150135_1_.playSoundEffect(p_150135_2_ + 0.5D, p_150135_3_ + 0.1D, p_150135_4_ + 0.5D, "random.click", 0.4F, 0.5F);
/* 210:271 */     } else if ((p_150135_5_) && (!p_150135_7_)) {
/* 211:273 */       p_150135_1_.playSoundEffect(p_150135_2_ + 0.5D, p_150135_3_ + 0.1D, p_150135_4_ + 0.5D, "random.click", 0.4F, 0.7F);
/* 212:275 */     } else if ((!p_150135_5_) && (p_150135_7_)) {
/* 213:277 */       p_150135_1_.playSoundEffect(p_150135_2_ + 0.5D, p_150135_3_ + 0.1D, p_150135_4_ + 0.5D, "random.bowhit", 0.4F, 1.2F / (p_150135_1_.rand.nextFloat() * 0.2F + 0.9F));
/* 214:    */     }
/* 215:    */   }
/* 216:    */   
/* 217:    */   private void func_150134_a(World p_150134_1_, int p_150134_2_, int p_150134_3_, int p_150134_4_, int p_150134_5_)
/* 218:    */   {
/* 219:283 */     p_150134_1_.notifyBlocksOfNeighborChange(p_150134_2_, p_150134_3_, p_150134_4_, this);
/* 220:285 */     if (p_150134_5_ == 3) {
/* 221:287 */       p_150134_1_.notifyBlocksOfNeighborChange(p_150134_2_ - 1, p_150134_3_, p_150134_4_, this);
/* 222:289 */     } else if (p_150134_5_ == 1) {
/* 223:291 */       p_150134_1_.notifyBlocksOfNeighborChange(p_150134_2_ + 1, p_150134_3_, p_150134_4_, this);
/* 224:293 */     } else if (p_150134_5_ == 0) {
/* 225:295 */       p_150134_1_.notifyBlocksOfNeighborChange(p_150134_2_, p_150134_3_, p_150134_4_ - 1, this);
/* 226:297 */     } else if (p_150134_5_ == 2) {
/* 227:299 */       p_150134_1_.notifyBlocksOfNeighborChange(p_150134_2_, p_150134_3_, p_150134_4_ + 1, this);
/* 228:    */     }
/* 229:    */   }
/* 230:    */   
/* 231:    */   private boolean func_150137_e(World p_150137_1_, int p_150137_2_, int p_150137_3_, int p_150137_4_)
/* 232:    */   {
/* 233:305 */     if (!canPlaceBlockAt(p_150137_1_, p_150137_2_, p_150137_3_, p_150137_4_))
/* 234:    */     {
/* 235:307 */       dropBlockAsItem(p_150137_1_, p_150137_2_, p_150137_3_, p_150137_4_, p_150137_1_.getBlockMetadata(p_150137_2_, p_150137_3_, p_150137_4_), 0);
/* 236:308 */       p_150137_1_.setBlockToAir(p_150137_2_, p_150137_3_, p_150137_4_);
/* 237:309 */       return false;
/* 238:    */     }
/* 239:313 */     return true;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
/* 243:    */   {
/* 244:319 */     int var5 = p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_) & 0x3;
/* 245:320 */     float var6 = 0.1875F;
/* 246:322 */     if (var5 == 3) {
/* 247:324 */       setBlockBounds(0.0F, 0.2F, 0.5F - var6, var6 * 2.0F, 0.8F, 0.5F + var6);
/* 248:326 */     } else if (var5 == 1) {
/* 249:328 */       setBlockBounds(1.0F - var6 * 2.0F, 0.2F, 0.5F - var6, 1.0F, 0.8F, 0.5F + var6);
/* 250:330 */     } else if (var5 == 0) {
/* 251:332 */       setBlockBounds(0.5F - var6, 0.2F, 0.0F, 0.5F + var6, 0.8F, var6 * 2.0F);
/* 252:334 */     } else if (var5 == 2) {
/* 253:336 */       setBlockBounds(0.5F - var6, 0.2F, 1.0F - var6 * 2.0F, 0.5F + var6, 0.8F, 1.0F);
/* 254:    */     }
/* 255:    */   }
/* 256:    */   
/* 257:    */   public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
/* 258:    */   {
/* 259:342 */     boolean var7 = (p_149749_6_ & 0x4) == 4;
/* 260:343 */     boolean var8 = (p_149749_6_ & 0x8) == 8;
/* 261:345 */     if ((var7) || (var8)) {
/* 262:347 */       func_150136_a(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, true, p_149749_6_, false, -1, 0);
/* 263:    */     }
/* 264:350 */     if (var8)
/* 265:    */     {
/* 266:352 */       p_149749_1_.notifyBlocksOfNeighborChange(p_149749_2_, p_149749_3_, p_149749_4_, this);
/* 267:353 */       int var9 = p_149749_6_ & 0x3;
/* 268:355 */       if (var9 == 3) {
/* 269:357 */         p_149749_1_.notifyBlocksOfNeighborChange(p_149749_2_ - 1, p_149749_3_, p_149749_4_, this);
/* 270:359 */       } else if (var9 == 1) {
/* 271:361 */         p_149749_1_.notifyBlocksOfNeighborChange(p_149749_2_ + 1, p_149749_3_, p_149749_4_, this);
/* 272:363 */       } else if (var9 == 0) {
/* 273:365 */         p_149749_1_.notifyBlocksOfNeighborChange(p_149749_2_, p_149749_3_, p_149749_4_ - 1, this);
/* 274:367 */       } else if (var9 == 2) {
/* 275:369 */         p_149749_1_.notifyBlocksOfNeighborChange(p_149749_2_, p_149749_3_, p_149749_4_ + 1, this);
/* 276:    */       }
/* 277:    */     }
/* 278:373 */     super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
/* 279:    */   }
/* 280:    */   
/* 281:    */   public int isProvidingWeakPower(IBlockAccess p_149709_1_, int p_149709_2_, int p_149709_3_, int p_149709_4_, int p_149709_5_)
/* 282:    */   {
/* 283:378 */     return (p_149709_1_.getBlockMetadata(p_149709_2_, p_149709_3_, p_149709_4_) & 0x8) == 8 ? 15 : 0;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public int isProvidingStrongPower(IBlockAccess p_149748_1_, int p_149748_2_, int p_149748_3_, int p_149748_4_, int p_149748_5_)
/* 287:    */   {
/* 288:383 */     int var6 = p_149748_1_.getBlockMetadata(p_149748_2_, p_149748_3_, p_149748_4_);
/* 289:385 */     if ((var6 & 0x8) != 8) {
/* 290:387 */       return 0;
/* 291:    */     }
/* 292:391 */     int var7 = var6 & 0x3;
/* 293:392 */     return (var7 == 3) && (p_149748_5_ == 5) ? 15 : (var7 == 1) && (p_149748_5_ == 4) ? 15 : (var7 == 0) && (p_149748_5_ == 3) ? 15 : (var7 == 2) && (p_149748_5_ == 2) ? 15 : 0;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public boolean canProvidePower()
/* 297:    */   {
/* 298:401 */     return true;
/* 299:    */   }
/* 300:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockTripWireHook
 * JD-Core Version:    0.7.0.1
 */