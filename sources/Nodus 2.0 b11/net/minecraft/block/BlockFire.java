/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.material.MapColor;
/*   5:    */ import net.minecraft.block.material.Material;
/*   6:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   7:    */ import net.minecraft.init.Blocks;
/*   8:    */ import net.minecraft.util.AxisAlignedBB;
/*   9:    */ import net.minecraft.util.IIcon;
/*  10:    */ import net.minecraft.world.EnumDifficulty;
/*  11:    */ import net.minecraft.world.GameRules;
/*  12:    */ import net.minecraft.world.IBlockAccess;
/*  13:    */ import net.minecraft.world.World;
/*  14:    */ import net.minecraft.world.WorldProvider;
/*  15:    */ import net.minecraft.world.WorldProviderEnd;
/*  16:    */ 
/*  17:    */ public class BlockFire
/*  18:    */   extends Block
/*  19:    */ {
/*  20: 16 */   private int[] field_149849_a = new int[256];
/*  21: 17 */   private int[] field_149848_b = new int[256];
/*  22:    */   private IIcon[] field_149850_M;
/*  23:    */   private static final String __OBFID = "CL_00000245";
/*  24:    */   
/*  25:    */   public BlockFire()
/*  26:    */   {
/*  27: 23 */     super(Material.fire);
/*  28: 24 */     setTickRandomly(true);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public static void func_149843_e()
/*  32:    */   {
/*  33: 29 */     Blocks.fire.func_149842_a(getIdFromBlock(Blocks.planks), 5, 20);
/*  34: 30 */     Blocks.fire.func_149842_a(getIdFromBlock(Blocks.double_wooden_slab), 5, 20);
/*  35: 31 */     Blocks.fire.func_149842_a(getIdFromBlock(Blocks.wooden_slab), 5, 20);
/*  36: 32 */     Blocks.fire.func_149842_a(getIdFromBlock(Blocks.fence), 5, 20);
/*  37: 33 */     Blocks.fire.func_149842_a(getIdFromBlock(Blocks.oak_stairs), 5, 20);
/*  38: 34 */     Blocks.fire.func_149842_a(getIdFromBlock(Blocks.birch_stairs), 5, 20);
/*  39: 35 */     Blocks.fire.func_149842_a(getIdFromBlock(Blocks.spruce_stairs), 5, 20);
/*  40: 36 */     Blocks.fire.func_149842_a(getIdFromBlock(Blocks.jungle_stairs), 5, 20);
/*  41: 37 */     Blocks.fire.func_149842_a(getIdFromBlock(Blocks.log), 5, 5);
/*  42: 38 */     Blocks.fire.func_149842_a(getIdFromBlock(Blocks.log2), 5, 5);
/*  43: 39 */     Blocks.fire.func_149842_a(getIdFromBlock(Blocks.leaves), 30, 60);
/*  44: 40 */     Blocks.fire.func_149842_a(getIdFromBlock(Blocks.leaves2), 30, 60);
/*  45: 41 */     Blocks.fire.func_149842_a(getIdFromBlock(Blocks.bookshelf), 30, 20);
/*  46: 42 */     Blocks.fire.func_149842_a(getIdFromBlock(Blocks.tnt), 15, 100);
/*  47: 43 */     Blocks.fire.func_149842_a(getIdFromBlock(Blocks.tallgrass), 60, 100);
/*  48: 44 */     Blocks.fire.func_149842_a(getIdFromBlock(Blocks.double_plant), 60, 100);
/*  49: 45 */     Blocks.fire.func_149842_a(getIdFromBlock(Blocks.yellow_flower), 60, 100);
/*  50: 46 */     Blocks.fire.func_149842_a(getIdFromBlock(Blocks.red_flower), 60, 100);
/*  51: 47 */     Blocks.fire.func_149842_a(getIdFromBlock(Blocks.wool), 30, 60);
/*  52: 48 */     Blocks.fire.func_149842_a(getIdFromBlock(Blocks.vine), 15, 100);
/*  53: 49 */     Blocks.fire.func_149842_a(getIdFromBlock(Blocks.coal_block), 5, 5);
/*  54: 50 */     Blocks.fire.func_149842_a(getIdFromBlock(Blocks.hay_block), 60, 20);
/*  55: 51 */     Blocks.fire.func_149842_a(getIdFromBlock(Blocks.carpet), 60, 20);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void func_149842_a(int p_149842_1_, int p_149842_2_, int p_149842_3_)
/*  59:    */   {
/*  60: 56 */     this.field_149849_a[p_149842_1_] = p_149842_2_;
/*  61: 57 */     this.field_149848_b[p_149842_1_] = p_149842_3_;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
/*  65:    */   {
/*  66: 66 */     return null;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public boolean isOpaqueCube()
/*  70:    */   {
/*  71: 71 */     return false;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public boolean renderAsNormalBlock()
/*  75:    */   {
/*  76: 76 */     return false;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public int getRenderType()
/*  80:    */   {
/*  81: 84 */     return 3;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public int quantityDropped(Random p_149745_1_)
/*  85:    */   {
/*  86: 92 */     return 0;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public int func_149738_a(World p_149738_1_)
/*  90:    */   {
/*  91: 97 */     return 30;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
/*  95:    */   {
/*  96:105 */     if (p_149674_1_.getGameRules().getGameRuleBooleanValue("doFireTick"))
/*  97:    */     {
/*  98:107 */       boolean var6 = p_149674_1_.getBlock(p_149674_2_, p_149674_3_ - 1, p_149674_4_) == Blocks.netherrack;
/*  99:109 */       if (((p_149674_1_.provider instanceof WorldProviderEnd)) && (p_149674_1_.getBlock(p_149674_2_, p_149674_3_ - 1, p_149674_4_) == Blocks.bedrock)) {
/* 100:111 */         var6 = true;
/* 101:    */       }
/* 102:114 */       if (!canPlaceBlockAt(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_)) {
/* 103:116 */         p_149674_1_.setBlockToAir(p_149674_2_, p_149674_3_, p_149674_4_);
/* 104:    */       }
/* 105:119 */       if ((!var6) && (p_149674_1_.isRaining()) && ((p_149674_1_.canLightningStrikeAt(p_149674_2_, p_149674_3_, p_149674_4_)) || (p_149674_1_.canLightningStrikeAt(p_149674_2_ - 1, p_149674_3_, p_149674_4_)) || (p_149674_1_.canLightningStrikeAt(p_149674_2_ + 1, p_149674_3_, p_149674_4_)) || (p_149674_1_.canLightningStrikeAt(p_149674_2_, p_149674_3_, p_149674_4_ - 1)) || (p_149674_1_.canLightningStrikeAt(p_149674_2_, p_149674_3_, p_149674_4_ + 1))))
/* 106:    */       {
/* 107:121 */         p_149674_1_.setBlockToAir(p_149674_2_, p_149674_3_, p_149674_4_);
/* 108:    */       }
/* 109:    */       else
/* 110:    */       {
/* 111:125 */         int var7 = p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_);
/* 112:127 */         if (var7 < 15) {
/* 113:129 */           p_149674_1_.setBlockMetadataWithNotify(p_149674_2_, p_149674_3_, p_149674_4_, var7 + p_149674_5_.nextInt(3) / 2, 4);
/* 114:    */         }
/* 115:132 */         p_149674_1_.scheduleBlockUpdate(p_149674_2_, p_149674_3_, p_149674_4_, this, func_149738_a(p_149674_1_) + p_149674_5_.nextInt(10));
/* 116:134 */         if ((!var6) && (!func_149847_e(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_)))
/* 117:    */         {
/* 118:136 */           if ((!World.doesBlockHaveSolidTopSurface(p_149674_1_, p_149674_2_, p_149674_3_ - 1, p_149674_4_)) || (var7 > 3)) {
/* 119:138 */             p_149674_1_.setBlockToAir(p_149674_2_, p_149674_3_, p_149674_4_);
/* 120:    */           }
/* 121:    */         }
/* 122:141 */         else if ((!var6) && (!func_149844_e(p_149674_1_, p_149674_2_, p_149674_3_ - 1, p_149674_4_)) && (var7 == 15) && (p_149674_5_.nextInt(4) == 0))
/* 123:    */         {
/* 124:143 */           p_149674_1_.setBlockToAir(p_149674_2_, p_149674_3_, p_149674_4_);
/* 125:    */         }
/* 126:    */         else
/* 127:    */         {
/* 128:147 */           boolean var8 = p_149674_1_.isBlockHighHumidity(p_149674_2_, p_149674_3_, p_149674_4_);
/* 129:148 */           byte var9 = 0;
/* 130:150 */           if (var8) {
/* 131:152 */             var9 = -50;
/* 132:    */           }
/* 133:155 */           func_149841_a(p_149674_1_, p_149674_2_ + 1, p_149674_3_, p_149674_4_, 300 + var9, p_149674_5_, var7);
/* 134:156 */           func_149841_a(p_149674_1_, p_149674_2_ - 1, p_149674_3_, p_149674_4_, 300 + var9, p_149674_5_, var7);
/* 135:157 */           func_149841_a(p_149674_1_, p_149674_2_, p_149674_3_ - 1, p_149674_4_, 250 + var9, p_149674_5_, var7);
/* 136:158 */           func_149841_a(p_149674_1_, p_149674_2_, p_149674_3_ + 1, p_149674_4_, 250 + var9, p_149674_5_, var7);
/* 137:159 */           func_149841_a(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_ - 1, 300 + var9, p_149674_5_, var7);
/* 138:160 */           func_149841_a(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_ + 1, 300 + var9, p_149674_5_, var7);
/* 139:162 */           for (int var10 = p_149674_2_ - 1; var10 <= p_149674_2_ + 1; var10++) {
/* 140:164 */             for (int var11 = p_149674_4_ - 1; var11 <= p_149674_4_ + 1; var11++) {
/* 141:166 */               for (int var12 = p_149674_3_ - 1; var12 <= p_149674_3_ + 4; var12++) {
/* 142:168 */                 if ((var10 != p_149674_2_) || (var12 != p_149674_3_) || (var11 != p_149674_4_))
/* 143:    */                 {
/* 144:170 */                   int var13 = 100;
/* 145:172 */                   if (var12 > p_149674_3_ + 1) {
/* 146:174 */                     var13 += (var12 - (p_149674_3_ + 1)) * 100;
/* 147:    */                   }
/* 148:177 */                   int var14 = func_149845_m(p_149674_1_, var10, var12, var11);
/* 149:179 */                   if (var14 > 0)
/* 150:    */                   {
/* 151:181 */                     int var15 = (var14 + 40 + p_149674_1_.difficultySetting.getDifficultyId() * 7) / (var7 + 30);
/* 152:183 */                     if (var8) {
/* 153:185 */                       var15 /= 2;
/* 154:    */                     }
/* 155:188 */                     if ((var15 > 0) && (p_149674_5_.nextInt(var13) <= var15) && ((!p_149674_1_.isRaining()) || (!p_149674_1_.canLightningStrikeAt(var10, var12, var11))) && (!p_149674_1_.canLightningStrikeAt(var10 - 1, var12, p_149674_4_)) && (!p_149674_1_.canLightningStrikeAt(var10 + 1, var12, var11)) && (!p_149674_1_.canLightningStrikeAt(var10, var12, var11 - 1)) && (!p_149674_1_.canLightningStrikeAt(var10, var12, var11 + 1)))
/* 156:    */                     {
/* 157:190 */                       int var16 = var7 + p_149674_5_.nextInt(5) / 4;
/* 158:192 */                       if (var16 > 15) {
/* 159:194 */                         var16 = 15;
/* 160:    */                       }
/* 161:197 */                       p_149674_1_.setBlock(var10, var12, var11, this, var16, 3);
/* 162:    */                     }
/* 163:    */                   }
/* 164:    */                 }
/* 165:    */               }
/* 166:    */             }
/* 167:    */           }
/* 168:    */         }
/* 169:    */       }
/* 170:    */     }
/* 171:    */   }
/* 172:    */   
/* 173:    */   public boolean func_149698_L()
/* 174:    */   {
/* 175:211 */     return false;
/* 176:    */   }
/* 177:    */   
/* 178:    */   private void func_149841_a(World p_149841_1_, int p_149841_2_, int p_149841_3_, int p_149841_4_, int p_149841_5_, Random p_149841_6_, int p_149841_7_)
/* 179:    */   {
/* 180:216 */     int var8 = this.field_149848_b[Block.getIdFromBlock(p_149841_1_.getBlock(p_149841_2_, p_149841_3_, p_149841_4_))];
/* 181:218 */     if (p_149841_6_.nextInt(p_149841_5_) < var8)
/* 182:    */     {
/* 183:220 */       boolean var9 = p_149841_1_.getBlock(p_149841_2_, p_149841_3_, p_149841_4_) == Blocks.tnt;
/* 184:222 */       if ((p_149841_6_.nextInt(p_149841_7_ + 10) < 5) && (!p_149841_1_.canLightningStrikeAt(p_149841_2_, p_149841_3_, p_149841_4_)))
/* 185:    */       {
/* 186:224 */         int var10 = p_149841_7_ + p_149841_6_.nextInt(5) / 4;
/* 187:226 */         if (var10 > 15) {
/* 188:228 */           var10 = 15;
/* 189:    */         }
/* 190:231 */         p_149841_1_.setBlock(p_149841_2_, p_149841_3_, p_149841_4_, this, var10, 3);
/* 191:    */       }
/* 192:    */       else
/* 193:    */       {
/* 194:235 */         p_149841_1_.setBlockToAir(p_149841_2_, p_149841_3_, p_149841_4_);
/* 195:    */       }
/* 196:238 */       if (var9) {
/* 197:240 */         Blocks.tnt.onBlockDestroyedByPlayer(p_149841_1_, p_149841_2_, p_149841_3_, p_149841_4_, 1);
/* 198:    */       }
/* 199:    */     }
/* 200:    */   }
/* 201:    */   
/* 202:    */   private boolean func_149847_e(World p_149847_1_, int p_149847_2_, int p_149847_3_, int p_149847_4_)
/* 203:    */   {
/* 204:247 */     return func_149844_e(p_149847_1_, p_149847_2_, p_149847_3_, p_149847_4_ - 1) ? true : func_149844_e(p_149847_1_, p_149847_2_, p_149847_3_ + 1, p_149847_4_) ? true : func_149844_e(p_149847_1_, p_149847_2_, p_149847_3_ - 1, p_149847_4_) ? true : func_149844_e(p_149847_1_, p_149847_2_ - 1, p_149847_3_, p_149847_4_) ? true : func_149844_e(p_149847_1_, p_149847_2_ + 1, p_149847_3_, p_149847_4_) ? true : func_149844_e(p_149847_1_, p_149847_2_, p_149847_3_, p_149847_4_ + 1);
/* 205:    */   }
/* 206:    */   
/* 207:    */   private int func_149845_m(World p_149845_1_, int p_149845_2_, int p_149845_3_, int p_149845_4_)
/* 208:    */   {
/* 209:252 */     byte var5 = 0;
/* 210:254 */     if (!p_149845_1_.isAirBlock(p_149845_2_, p_149845_3_, p_149845_4_)) {
/* 211:256 */       return 0;
/* 212:    */     }
/* 213:260 */     int var6 = func_149846_a(p_149845_1_, p_149845_2_ + 1, p_149845_3_, p_149845_4_, var5);
/* 214:261 */     var6 = func_149846_a(p_149845_1_, p_149845_2_ - 1, p_149845_3_, p_149845_4_, var6);
/* 215:262 */     var6 = func_149846_a(p_149845_1_, p_149845_2_, p_149845_3_ - 1, p_149845_4_, var6);
/* 216:263 */     var6 = func_149846_a(p_149845_1_, p_149845_2_, p_149845_3_ + 1, p_149845_4_, var6);
/* 217:264 */     var6 = func_149846_a(p_149845_1_, p_149845_2_, p_149845_3_, p_149845_4_ - 1, var6);
/* 218:265 */     var6 = func_149846_a(p_149845_1_, p_149845_2_, p_149845_3_, p_149845_4_ + 1, var6);
/* 219:266 */     return var6;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public boolean isCollidable()
/* 223:    */   {
/* 224:272 */     return false;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public boolean func_149844_e(IBlockAccess p_149844_1_, int p_149844_2_, int p_149844_3_, int p_149844_4_)
/* 228:    */   {
/* 229:277 */     return this.field_149849_a[Block.getIdFromBlock(p_149844_1_.getBlock(p_149844_2_, p_149844_3_, p_149844_4_))] > 0;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public int func_149846_a(World p_149846_1_, int p_149846_2_, int p_149846_3_, int p_149846_4_, int p_149846_5_)
/* 233:    */   {
/* 234:282 */     int var6 = this.field_149849_a[Block.getIdFromBlock(p_149846_1_.getBlock(p_149846_2_, p_149846_3_, p_149846_4_))];
/* 235:283 */     return var6 > p_149846_5_ ? var6 : p_149846_5_;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_)
/* 239:    */   {
/* 240:288 */     return (World.doesBlockHaveSolidTopSurface(p_149742_1_, p_149742_2_, p_149742_3_ - 1, p_149742_4_)) || (func_149847_e(p_149742_1_, p_149742_2_, p_149742_3_, p_149742_4_));
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
/* 244:    */   {
/* 245:293 */     if ((!World.doesBlockHaveSolidTopSurface(p_149695_1_, p_149695_2_, p_149695_3_ - 1, p_149695_4_)) && (!func_149847_e(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_))) {
/* 246:295 */       p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
/* 247:    */     }
/* 248:    */   }
/* 249:    */   
/* 250:    */   public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_)
/* 251:    */   {
/* 252:301 */     if ((p_149726_1_.provider.dimensionId > 0) || (!Blocks.portal.func_150000_e(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_))) {
/* 253:303 */       if ((!World.doesBlockHaveSolidTopSurface(p_149726_1_, p_149726_2_, p_149726_3_ - 1, p_149726_4_)) && (!func_149847_e(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_))) {
/* 254:305 */         p_149726_1_.setBlockToAir(p_149726_2_, p_149726_3_, p_149726_4_);
/* 255:    */       } else {
/* 256:309 */         p_149726_1_.scheduleBlockUpdate(p_149726_2_, p_149726_3_, p_149726_4_, this, func_149738_a(p_149726_1_) + p_149726_1_.rand.nextInt(10));
/* 257:    */       }
/* 258:    */     }
/* 259:    */   }
/* 260:    */   
/* 261:    */   public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_)
/* 262:    */   {
/* 263:319 */     if (p_149734_5_.nextInt(24) == 0) {
/* 264:321 */       p_149734_1_.playSound(p_149734_2_ + 0.5F, p_149734_3_ + 0.5F, p_149734_4_ + 0.5F, "fire.fire", 1.0F + p_149734_5_.nextFloat(), p_149734_5_.nextFloat() * 0.7F + 0.3F, false);
/* 265:    */     }
/* 266:329 */     if ((!World.doesBlockHaveSolidTopSurface(p_149734_1_, p_149734_2_, p_149734_3_ - 1, p_149734_4_)) && (!Blocks.fire.func_149844_e(p_149734_1_, p_149734_2_, p_149734_3_ - 1, p_149734_4_)))
/* 267:    */     {
/* 268:331 */       if (Blocks.fire.func_149844_e(p_149734_1_, p_149734_2_ - 1, p_149734_3_, p_149734_4_)) {
/* 269:333 */         for (int var6 = 0; var6 < 2; var6++)
/* 270:    */         {
/* 271:335 */           float var7 = p_149734_2_ + p_149734_5_.nextFloat() * 0.1F;
/* 272:336 */           float var8 = p_149734_3_ + p_149734_5_.nextFloat();
/* 273:337 */           float var9 = p_149734_4_ + p_149734_5_.nextFloat();
/* 274:338 */           p_149734_1_.spawnParticle("largesmoke", var7, var8, var9, 0.0D, 0.0D, 0.0D);
/* 275:    */         }
/* 276:    */       }
/* 277:342 */       if (Blocks.fire.func_149844_e(p_149734_1_, p_149734_2_ + 1, p_149734_3_, p_149734_4_)) {
/* 278:344 */         for (int var6 = 0; var6 < 2; var6++)
/* 279:    */         {
/* 280:346 */           float var7 = p_149734_2_ + 1 - p_149734_5_.nextFloat() * 0.1F;
/* 281:347 */           float var8 = p_149734_3_ + p_149734_5_.nextFloat();
/* 282:348 */           float var9 = p_149734_4_ + p_149734_5_.nextFloat();
/* 283:349 */           p_149734_1_.spawnParticle("largesmoke", var7, var8, var9, 0.0D, 0.0D, 0.0D);
/* 284:    */         }
/* 285:    */       }
/* 286:353 */       if (Blocks.fire.func_149844_e(p_149734_1_, p_149734_2_, p_149734_3_, p_149734_4_ - 1)) {
/* 287:355 */         for (int var6 = 0; var6 < 2; var6++)
/* 288:    */         {
/* 289:357 */           float var7 = p_149734_2_ + p_149734_5_.nextFloat();
/* 290:358 */           float var8 = p_149734_3_ + p_149734_5_.nextFloat();
/* 291:359 */           float var9 = p_149734_4_ + p_149734_5_.nextFloat() * 0.1F;
/* 292:360 */           p_149734_1_.spawnParticle("largesmoke", var7, var8, var9, 0.0D, 0.0D, 0.0D);
/* 293:    */         }
/* 294:    */       }
/* 295:364 */       if (Blocks.fire.func_149844_e(p_149734_1_, p_149734_2_, p_149734_3_, p_149734_4_ + 1)) {
/* 296:366 */         for (int var6 = 0; var6 < 2; var6++)
/* 297:    */         {
/* 298:368 */           float var7 = p_149734_2_ + p_149734_5_.nextFloat();
/* 299:369 */           float var8 = p_149734_3_ + p_149734_5_.nextFloat();
/* 300:370 */           float var9 = p_149734_4_ + 1 - p_149734_5_.nextFloat() * 0.1F;
/* 301:371 */           p_149734_1_.spawnParticle("largesmoke", var7, var8, var9, 0.0D, 0.0D, 0.0D);
/* 302:    */         }
/* 303:    */       }
/* 304:375 */       if (Blocks.fire.func_149844_e(p_149734_1_, p_149734_2_, p_149734_3_ + 1, p_149734_4_)) {
/* 305:377 */         for (int var6 = 0; var6 < 2; var6++)
/* 306:    */         {
/* 307:379 */           float var7 = p_149734_2_ + p_149734_5_.nextFloat();
/* 308:380 */           float var8 = p_149734_3_ + 1 - p_149734_5_.nextFloat() * 0.1F;
/* 309:381 */           float var9 = p_149734_4_ + p_149734_5_.nextFloat();
/* 310:382 */           p_149734_1_.spawnParticle("largesmoke", var7, var8, var9, 0.0D, 0.0D, 0.0D);
/* 311:    */         }
/* 312:    */       }
/* 313:    */     }
/* 314:    */     else
/* 315:    */     {
/* 316:388 */       for (int var6 = 0; var6 < 3; var6++)
/* 317:    */       {
/* 318:390 */         float var7 = p_149734_2_ + p_149734_5_.nextFloat();
/* 319:391 */         float var8 = p_149734_3_ + p_149734_5_.nextFloat() * 0.5F + 0.5F;
/* 320:392 */         float var9 = p_149734_4_ + p_149734_5_.nextFloat();
/* 321:393 */         p_149734_1_.spawnParticle("largesmoke", var7, var8, var9, 0.0D, 0.0D, 0.0D);
/* 322:    */       }
/* 323:    */     }
/* 324:    */   }
/* 325:    */   
/* 326:    */   public void registerBlockIcons(IIconRegister p_149651_1_)
/* 327:    */   {
/* 328:400 */     this.field_149850_M = new IIcon[] { p_149651_1_.registerIcon(getTextureName() + "_layer_0"), p_149651_1_.registerIcon(getTextureName() + "_layer_1") };
/* 329:    */   }
/* 330:    */   
/* 331:    */   public IIcon func_149840_c(int p_149840_1_)
/* 332:    */   {
/* 333:405 */     return this.field_149850_M[p_149840_1_];
/* 334:    */   }
/* 335:    */   
/* 336:    */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/* 337:    */   {
/* 338:413 */     return this.field_149850_M[0];
/* 339:    */   }
/* 340:    */   
/* 341:    */   public MapColor getMapColor(int p_149728_1_)
/* 342:    */   {
/* 343:418 */     return MapColor.field_151656_f;
/* 344:    */   }
/* 345:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockFire
 * JD-Core Version:    0.7.0.1
 */