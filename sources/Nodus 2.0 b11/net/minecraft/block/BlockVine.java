/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.material.Material;
/*   5:    */ import net.minecraft.creativetab.CreativeTabs;
/*   6:    */ import net.minecraft.entity.player.EntityPlayer;
/*   7:    */ import net.minecraft.init.Blocks;
/*   8:    */ import net.minecraft.init.Items;
/*   9:    */ import net.minecraft.item.Item;
/*  10:    */ import net.minecraft.item.ItemStack;
/*  11:    */ import net.minecraft.util.AxisAlignedBB;
/*  12:    */ import net.minecraft.world.ColorizerFoliage;
/*  13:    */ import net.minecraft.world.IBlockAccess;
/*  14:    */ import net.minecraft.world.World;
/*  15:    */ import net.minecraft.world.biome.BiomeGenBase;
/*  16:    */ 
/*  17:    */ public class BlockVine
/*  18:    */   extends Block
/*  19:    */ {
/*  20:    */   private static final String __OBFID = "CL_00000330";
/*  21:    */   
/*  22:    */   public BlockVine()
/*  23:    */   {
/*  24: 24 */     super(Material.vine);
/*  25: 25 */     setTickRandomly(true);
/*  26: 26 */     setCreativeTab(CreativeTabs.tabDecorations);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void setBlockBoundsForItemRender()
/*  30:    */   {
/*  31: 34 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public int getRenderType()
/*  35:    */   {
/*  36: 42 */     return 20;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public boolean isOpaqueCube()
/*  40:    */   {
/*  41: 47 */     return false;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public boolean renderAsNormalBlock()
/*  45:    */   {
/*  46: 52 */     return false;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
/*  50:    */   {
/*  51: 57 */     float var5 = 0.0625F;
/*  52: 58 */     int var6 = p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_);
/*  53: 59 */     float var7 = 1.0F;
/*  54: 60 */     float var8 = 1.0F;
/*  55: 61 */     float var9 = 1.0F;
/*  56: 62 */     float var10 = 0.0F;
/*  57: 63 */     float var11 = 0.0F;
/*  58: 64 */     float var12 = 0.0F;
/*  59: 65 */     boolean var13 = var6 > 0;
/*  60: 67 */     if ((var6 & 0x2) != 0)
/*  61:    */     {
/*  62: 69 */       var10 = Math.max(var10, 0.0625F);
/*  63: 70 */       var7 = 0.0F;
/*  64: 71 */       var8 = 0.0F;
/*  65: 72 */       var11 = 1.0F;
/*  66: 73 */       var9 = 0.0F;
/*  67: 74 */       var12 = 1.0F;
/*  68: 75 */       var13 = true;
/*  69:    */     }
/*  70: 78 */     if ((var6 & 0x8) != 0)
/*  71:    */     {
/*  72: 80 */       var7 = Math.min(var7, 0.9375F);
/*  73: 81 */       var10 = 1.0F;
/*  74: 82 */       var8 = 0.0F;
/*  75: 83 */       var11 = 1.0F;
/*  76: 84 */       var9 = 0.0F;
/*  77: 85 */       var12 = 1.0F;
/*  78: 86 */       var13 = true;
/*  79:    */     }
/*  80: 89 */     if ((var6 & 0x4) != 0)
/*  81:    */     {
/*  82: 91 */       var12 = Math.max(var12, 0.0625F);
/*  83: 92 */       var9 = 0.0F;
/*  84: 93 */       var7 = 0.0F;
/*  85: 94 */       var10 = 1.0F;
/*  86: 95 */       var8 = 0.0F;
/*  87: 96 */       var11 = 1.0F;
/*  88: 97 */       var13 = true;
/*  89:    */     }
/*  90:100 */     if ((var6 & 0x1) != 0)
/*  91:    */     {
/*  92:102 */       var9 = Math.min(var9, 0.9375F);
/*  93:103 */       var12 = 1.0F;
/*  94:104 */       var7 = 0.0F;
/*  95:105 */       var10 = 1.0F;
/*  96:106 */       var8 = 0.0F;
/*  97:107 */       var11 = 1.0F;
/*  98:108 */       var13 = true;
/*  99:    */     }
/* 100:111 */     if ((!var13) && (func_150093_a(p_149719_1_.getBlock(p_149719_2_, p_149719_3_ + 1, p_149719_4_))))
/* 101:    */     {
/* 102:113 */       var8 = Math.min(var8, 0.9375F);
/* 103:114 */       var11 = 1.0F;
/* 104:115 */       var7 = 0.0F;
/* 105:116 */       var10 = 1.0F;
/* 106:117 */       var9 = 0.0F;
/* 107:118 */       var12 = 1.0F;
/* 108:    */     }
/* 109:121 */     setBlockBounds(var7, var8, var9, var10, var11, var12);
/* 110:    */   }
/* 111:    */   
/* 112:    */   public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
/* 113:    */   {
/* 114:130 */     return null;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public boolean canPlaceBlockOnSide(World p_149707_1_, int p_149707_2_, int p_149707_3_, int p_149707_4_, int p_149707_5_)
/* 118:    */   {
/* 119:138 */     switch (p_149707_5_)
/* 120:    */     {
/* 121:    */     case 1: 
/* 122:141 */       return func_150093_a(p_149707_1_.getBlock(p_149707_2_, p_149707_3_ + 1, p_149707_4_));
/* 123:    */     case 2: 
/* 124:144 */       return func_150093_a(p_149707_1_.getBlock(p_149707_2_, p_149707_3_, p_149707_4_ + 1));
/* 125:    */     case 3: 
/* 126:147 */       return func_150093_a(p_149707_1_.getBlock(p_149707_2_, p_149707_3_, p_149707_4_ - 1));
/* 127:    */     case 4: 
/* 128:150 */       return func_150093_a(p_149707_1_.getBlock(p_149707_2_ + 1, p_149707_3_, p_149707_4_));
/* 129:    */     case 5: 
/* 130:153 */       return func_150093_a(p_149707_1_.getBlock(p_149707_2_ - 1, p_149707_3_, p_149707_4_));
/* 131:    */     }
/* 132:156 */     return false;
/* 133:    */   }
/* 134:    */   
/* 135:    */   private boolean func_150093_a(Block p_150093_1_)
/* 136:    */   {
/* 137:162 */     return (p_150093_1_.renderAsNormalBlock()) && (p_150093_1_.blockMaterial.blocksMovement());
/* 138:    */   }
/* 139:    */   
/* 140:    */   private boolean func_150094_e(World p_150094_1_, int p_150094_2_, int p_150094_3_, int p_150094_4_)
/* 141:    */   {
/* 142:167 */     int var5 = p_150094_1_.getBlockMetadata(p_150094_2_, p_150094_3_, p_150094_4_);
/* 143:168 */     int var6 = var5;
/* 144:170 */     if (var5 > 0) {
/* 145:172 */       for (int var7 = 0; var7 <= 3; var7++)
/* 146:    */       {
/* 147:174 */         int var8 = 1 << var7;
/* 148:176 */         if (((var5 & var8) != 0) && (!func_150093_a(p_150094_1_.getBlock(p_150094_2_ + net.minecraft.util.Direction.offsetX[var7], p_150094_3_, p_150094_4_ + net.minecraft.util.Direction.offsetZ[var7]))) && ((p_150094_1_.getBlock(p_150094_2_, p_150094_3_ + 1, p_150094_4_) != this) || ((p_150094_1_.getBlockMetadata(p_150094_2_, p_150094_3_ + 1, p_150094_4_) & var8) == 0))) {
/* 149:178 */           var6 &= (var8 ^ 0xFFFFFFFF);
/* 150:    */         }
/* 151:    */       }
/* 152:    */     }
/* 153:183 */     if ((var6 == 0) && (!func_150093_a(p_150094_1_.getBlock(p_150094_2_, p_150094_3_ + 1, p_150094_4_)))) {
/* 154:185 */       return false;
/* 155:    */     }
/* 156:189 */     if (var6 != var5) {
/* 157:191 */       p_150094_1_.setBlockMetadataWithNotify(p_150094_2_, p_150094_3_, p_150094_4_, var6, 2);
/* 158:    */     }
/* 159:194 */     return true;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public int getBlockColor()
/* 163:    */   {
/* 164:200 */     return ColorizerFoliage.getFoliageColorBasic();
/* 165:    */   }
/* 166:    */   
/* 167:    */   public int getRenderColor(int p_149741_1_)
/* 168:    */   {
/* 169:208 */     return ColorizerFoliage.getFoliageColorBasic();
/* 170:    */   }
/* 171:    */   
/* 172:    */   public int colorMultiplier(IBlockAccess p_149720_1_, int p_149720_2_, int p_149720_3_, int p_149720_4_)
/* 173:    */   {
/* 174:217 */     return p_149720_1_.getBiomeGenForCoords(p_149720_2_, p_149720_4_).getBiomeFoliageColor(p_149720_2_, p_149720_3_, p_149720_4_);
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
/* 178:    */   {
/* 179:222 */     if ((!p_149695_1_.isClient) && (!func_150094_e(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_)))
/* 180:    */     {
/* 181:224 */       dropBlockAsItem(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_), 0);
/* 182:225 */       p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
/* 183:    */     }
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
/* 187:    */   {
/* 188:234 */     if ((!p_149674_1_.isClient) && (p_149674_1_.rand.nextInt(4) == 0))
/* 189:    */     {
/* 190:236 */       byte var6 = 4;
/* 191:237 */       int var7 = 5;
/* 192:238 */       boolean var8 = false;
/* 193:244 */       for (int var9 = p_149674_2_ - var6; var9 <= p_149674_2_ + var6; var9++) {
/* 194:246 */         for (int var10 = p_149674_4_ - var6; var10 <= p_149674_4_ + var6; var10++) {
/* 195:248 */           for (int var11 = p_149674_3_ - 1; var11 <= p_149674_3_ + 1; var11++) {
/* 196:250 */             if (p_149674_1_.getBlock(var9, var11, var10) == this)
/* 197:    */             {
/* 198:252 */               var7--;
/* 199:254 */               if (var7 <= 0)
/* 200:    */               {
/* 201:256 */                 var8 = true;
/* 202:257 */                 break;
/* 203:    */               }
/* 204:    */             }
/* 205:    */           }
/* 206:    */         }
/* 207:    */       }
/* 208:264 */       var9 = p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_);
/* 209:265 */       int var10 = p_149674_1_.rand.nextInt(6);
/* 210:266 */       int var11 = net.minecraft.util.Direction.facingToDirection[var10];
/* 211:269 */       if ((var10 == 1) && (p_149674_3_ < 255) && (p_149674_1_.isAirBlock(p_149674_2_, p_149674_3_ + 1, p_149674_4_)))
/* 212:    */       {
/* 213:271 */         if (var8) {
/* 214:273 */           return;
/* 215:    */         }
/* 216:276 */         int var15 = p_149674_1_.rand.nextInt(16) & var9;
/* 217:278 */         if (var15 > 0)
/* 218:    */         {
/* 219:280 */           for (int var13 = 0; var13 <= 3; var13++) {
/* 220:282 */             if (!func_150093_a(p_149674_1_.getBlock(p_149674_2_ + net.minecraft.util.Direction.offsetX[var13], p_149674_3_ + 1, p_149674_4_ + net.minecraft.util.Direction.offsetZ[var13]))) {
/* 221:284 */               var15 &= (1 << var13 ^ 0xFFFFFFFF);
/* 222:    */             }
/* 223:    */           }
/* 224:288 */           if (var15 > 0) {
/* 225:290 */             p_149674_1_.setBlock(p_149674_2_, p_149674_3_ + 1, p_149674_4_, this, var15, 2);
/* 226:    */           }
/* 227:    */         }
/* 228:    */       }
/* 229:299 */       else if ((var10 >= 2) && (var10 <= 5) && ((var9 & 1 << var11) == 0))
/* 230:    */       {
/* 231:301 */         if (var8) {
/* 232:303 */           return;
/* 233:    */         }
/* 234:306 */         Block var12 = p_149674_1_.getBlock(p_149674_2_ + net.minecraft.util.Direction.offsetX[var11], p_149674_3_, p_149674_4_ + net.minecraft.util.Direction.offsetZ[var11]);
/* 235:308 */         if (var12.blockMaterial == Material.air)
/* 236:    */         {
/* 237:310 */           int var13 = var11 + 1 & 0x3;
/* 238:311 */           int var14 = var11 + 3 & 0x3;
/* 239:313 */           if (((var9 & 1 << var13) != 0) && (func_150093_a(p_149674_1_.getBlock(p_149674_2_ + net.minecraft.util.Direction.offsetX[var11] + net.minecraft.util.Direction.offsetX[var13], p_149674_3_, p_149674_4_ + net.minecraft.util.Direction.offsetZ[var11] + net.minecraft.util.Direction.offsetZ[var13])))) {
/* 240:315 */             p_149674_1_.setBlock(p_149674_2_ + net.minecraft.util.Direction.offsetX[var11], p_149674_3_, p_149674_4_ + net.minecraft.util.Direction.offsetZ[var11], this, 1 << var13, 2);
/* 241:317 */           } else if (((var9 & 1 << var14) != 0) && (func_150093_a(p_149674_1_.getBlock(p_149674_2_ + net.minecraft.util.Direction.offsetX[var11] + net.minecraft.util.Direction.offsetX[var14], p_149674_3_, p_149674_4_ + net.minecraft.util.Direction.offsetZ[var11] + net.minecraft.util.Direction.offsetZ[var14])))) {
/* 242:319 */             p_149674_1_.setBlock(p_149674_2_ + net.minecraft.util.Direction.offsetX[var11], p_149674_3_, p_149674_4_ + net.minecraft.util.Direction.offsetZ[var11], this, 1 << var14, 2);
/* 243:321 */           } else if (((var9 & 1 << var13) != 0) && (p_149674_1_.isAirBlock(p_149674_2_ + net.minecraft.util.Direction.offsetX[var11] + net.minecraft.util.Direction.offsetX[var13], p_149674_3_, p_149674_4_ + net.minecraft.util.Direction.offsetZ[var11] + net.minecraft.util.Direction.offsetZ[var13])) && (func_150093_a(p_149674_1_.getBlock(p_149674_2_ + net.minecraft.util.Direction.offsetX[var13], p_149674_3_, p_149674_4_ + net.minecraft.util.Direction.offsetZ[var13])))) {
/* 244:323 */             p_149674_1_.setBlock(p_149674_2_ + net.minecraft.util.Direction.offsetX[var11] + net.minecraft.util.Direction.offsetX[var13], p_149674_3_, p_149674_4_ + net.minecraft.util.Direction.offsetZ[var11] + net.minecraft.util.Direction.offsetZ[var13], this, 1 << (var11 + 2 & 0x3), 2);
/* 245:325 */           } else if (((var9 & 1 << var14) != 0) && (p_149674_1_.isAirBlock(p_149674_2_ + net.minecraft.util.Direction.offsetX[var11] + net.minecraft.util.Direction.offsetX[var14], p_149674_3_, p_149674_4_ + net.minecraft.util.Direction.offsetZ[var11] + net.minecraft.util.Direction.offsetZ[var14])) && (func_150093_a(p_149674_1_.getBlock(p_149674_2_ + net.minecraft.util.Direction.offsetX[var14], p_149674_3_, p_149674_4_ + net.minecraft.util.Direction.offsetZ[var14])))) {
/* 246:327 */             p_149674_1_.setBlock(p_149674_2_ + net.minecraft.util.Direction.offsetX[var11] + net.minecraft.util.Direction.offsetX[var14], p_149674_3_, p_149674_4_ + net.minecraft.util.Direction.offsetZ[var11] + net.minecraft.util.Direction.offsetZ[var14], this, 1 << (var11 + 2 & 0x3), 2);
/* 247:329 */           } else if (func_150093_a(p_149674_1_.getBlock(p_149674_2_ + net.minecraft.util.Direction.offsetX[var11], p_149674_3_ + 1, p_149674_4_ + net.minecraft.util.Direction.offsetZ[var11]))) {
/* 248:331 */             p_149674_1_.setBlock(p_149674_2_ + net.minecraft.util.Direction.offsetX[var11], p_149674_3_, p_149674_4_ + net.minecraft.util.Direction.offsetZ[var11], this, 0, 2);
/* 249:    */           }
/* 250:    */         }
/* 251:334 */         else if ((var12.blockMaterial.isOpaque()) && (var12.renderAsNormalBlock()))
/* 252:    */         {
/* 253:336 */           p_149674_1_.setBlockMetadataWithNotify(p_149674_2_, p_149674_3_, p_149674_4_, var9 | 1 << var11, 2);
/* 254:    */         }
/* 255:    */       }
/* 256:339 */       else if (p_149674_3_ > 1)
/* 257:    */       {
/* 258:341 */         Block var12 = p_149674_1_.getBlock(p_149674_2_, p_149674_3_ - 1, p_149674_4_);
/* 259:343 */         if (var12.blockMaterial == Material.air)
/* 260:    */         {
/* 261:345 */           int var13 = p_149674_1_.rand.nextInt(16) & var9;
/* 262:347 */           if (var13 > 0) {
/* 263:349 */             p_149674_1_.setBlock(p_149674_2_, p_149674_3_ - 1, p_149674_4_, this, var13, 2);
/* 264:    */           }
/* 265:    */         }
/* 266:352 */         else if (var12 == this)
/* 267:    */         {
/* 268:354 */           int var13 = p_149674_1_.rand.nextInt(16) & var9;
/* 269:355 */           int var14 = p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_ - 1, p_149674_4_);
/* 270:357 */           if (var14 != (var14 | var13)) {
/* 271:359 */             p_149674_1_.setBlockMetadataWithNotify(p_149674_2_, p_149674_3_ - 1, p_149674_4_, var14 | var13, 2);
/* 272:    */           }
/* 273:    */         }
/* 274:    */       }
/* 275:    */     }
/* 276:    */   }
/* 277:    */   
/* 278:    */   public int onBlockPlaced(World p_149660_1_, int p_149660_2_, int p_149660_3_, int p_149660_4_, int p_149660_5_, float p_149660_6_, float p_149660_7_, float p_149660_8_, int p_149660_9_)
/* 279:    */   {
/* 280:369 */     byte var10 = 0;
/* 281:371 */     switch (p_149660_5_)
/* 282:    */     {
/* 283:    */     case 2: 
/* 284:374 */       var10 = 1;
/* 285:375 */       break;
/* 286:    */     case 3: 
/* 287:378 */       var10 = 4;
/* 288:379 */       break;
/* 289:    */     case 4: 
/* 290:382 */       var10 = 8;
/* 291:383 */       break;
/* 292:    */     case 5: 
/* 293:386 */       var10 = 2;
/* 294:    */     }
/* 295:389 */     return var10 != 0 ? var10 : p_149660_9_;
/* 296:    */   }
/* 297:    */   
/* 298:    */   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
/* 299:    */   {
/* 300:394 */     return null;
/* 301:    */   }
/* 302:    */   
/* 303:    */   public int quantityDropped(Random p_149745_1_)
/* 304:    */   {
/* 305:402 */     return 0;
/* 306:    */   }
/* 307:    */   
/* 308:    */   public void harvestBlock(World p_149636_1_, EntityPlayer p_149636_2_, int p_149636_3_, int p_149636_4_, int p_149636_5_, int p_149636_6_)
/* 309:    */   {
/* 310:407 */     if ((!p_149636_1_.isClient) && (p_149636_2_.getCurrentEquippedItem() != null) && (p_149636_2_.getCurrentEquippedItem().getItem() == Items.shears))
/* 311:    */     {
/* 312:409 */       p_149636_2_.addStat(net.minecraft.stats.StatList.mineBlockStatArray[Block.getIdFromBlock(this)], 1);
/* 313:410 */       dropBlockAsItem_do(p_149636_1_, p_149636_3_, p_149636_4_, p_149636_5_, new ItemStack(Blocks.vine, 1, 0));
/* 314:    */     }
/* 315:    */     else
/* 316:    */     {
/* 317:414 */       super.harvestBlock(p_149636_1_, p_149636_2_, p_149636_3_, p_149636_4_, p_149636_5_, p_149636_6_);
/* 318:    */     }
/* 319:    */   }
/* 320:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockVine
 * JD-Core Version:    0.7.0.1
 */