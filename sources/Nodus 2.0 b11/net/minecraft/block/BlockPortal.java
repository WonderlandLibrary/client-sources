/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.material.Material;
/*   5:    */ import net.minecraft.entity.Entity;
/*   6:    */ import net.minecraft.init.Blocks;
/*   7:    */ import net.minecraft.item.Item;
/*   8:    */ import net.minecraft.item.ItemMonsterPlacer;
/*   9:    */ import net.minecraft.util.AxisAlignedBB;
/*  10:    */ import net.minecraft.util.ChunkCoordinates;
/*  11:    */ import net.minecraft.world.EnumDifficulty;
/*  12:    */ import net.minecraft.world.GameRules;
/*  13:    */ import net.minecraft.world.IBlockAccess;
/*  14:    */ import net.minecraft.world.World;
/*  15:    */ import net.minecraft.world.WorldProvider;
/*  16:    */ 
/*  17:    */ public class BlockPortal
/*  18:    */   extends BlockBreakable
/*  19:    */ {
/*  20: 17 */   public static final int[][] field_150001_a = { new int[0], { 3, 1 }, { 2 } };
/*  21:    */   private static final String __OBFID = "CL_00000284";
/*  22:    */   
/*  23:    */   public BlockPortal()
/*  24:    */   {
/*  25: 22 */     super("portal", Material.Portal, false);
/*  26: 23 */     setTickRandomly(true);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
/*  30:    */   {
/*  31: 31 */     super.updateTick(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, p_149674_5_);
/*  32: 33 */     if ((p_149674_1_.provider.isSurfaceWorld()) && (p_149674_1_.getGameRules().getGameRuleBooleanValue("doMobSpawning")) && (p_149674_5_.nextInt(2000) < p_149674_1_.difficultySetting.getDifficultyId()))
/*  33:    */     {
/*  34: 37 */       for (int var6 = p_149674_3_; (!World.doesBlockHaveSolidTopSurface(p_149674_1_, p_149674_2_, var6, p_149674_4_)) && (var6 > 0); var6--) {}
/*  35: 42 */       if ((var6 > 0) && (!p_149674_1_.getBlock(p_149674_2_, var6 + 1, p_149674_4_).isNormalCube()))
/*  36:    */       {
/*  37: 44 */         Entity var7 = ItemMonsterPlacer.spawnCreature(p_149674_1_, 57, p_149674_2_ + 0.5D, var6 + 1.1D, p_149674_4_ + 0.5D);
/*  38: 46 */         if (var7 != null) {
/*  39: 48 */           var7.timeUntilPortal = var7.getPortalCooldown();
/*  40:    */         }
/*  41:    */       }
/*  42:    */     }
/*  43:    */   }
/*  44:    */   
/*  45:    */   public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
/*  46:    */   {
/*  47: 60 */     return null;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
/*  51:    */   {
/*  52: 65 */     int var5 = func_149999_b(p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_));
/*  53: 67 */     if (var5 == 0)
/*  54:    */     {
/*  55: 69 */       if ((p_149719_1_.getBlock(p_149719_2_ - 1, p_149719_3_, p_149719_4_) != this) && (p_149719_1_.getBlock(p_149719_2_ + 1, p_149719_3_, p_149719_4_) != this)) {
/*  56: 71 */         var5 = 2;
/*  57:    */       } else {
/*  58: 75 */         var5 = 1;
/*  59:    */       }
/*  60: 78 */       if (((p_149719_1_ instanceof World)) && (!((World)p_149719_1_).isClient)) {
/*  61: 80 */         ((World)p_149719_1_).setBlockMetadataWithNotify(p_149719_2_, p_149719_3_, p_149719_4_, var5, 2);
/*  62:    */       }
/*  63:    */     }
/*  64: 84 */     float var6 = 0.125F;
/*  65: 85 */     float var7 = 0.125F;
/*  66: 87 */     if (var5 == 1) {
/*  67: 89 */       var6 = 0.5F;
/*  68:    */     }
/*  69: 92 */     if (var5 == 2) {
/*  70: 94 */       var7 = 0.5F;
/*  71:    */     }
/*  72: 97 */     setBlockBounds(0.5F - var6, 0.0F, 0.5F - var7, 0.5F + var6, 1.0F, 0.5F + var7);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public boolean renderAsNormalBlock()
/*  76:    */   {
/*  77:102 */     return false;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public boolean func_150000_e(World p_150000_1_, int p_150000_2_, int p_150000_3_, int p_150000_4_)
/*  81:    */   {
/*  82:107 */     Size var5 = new Size(p_150000_1_, p_150000_2_, p_150000_3_, p_150000_4_, 1);
/*  83:108 */     Size var6 = new Size(p_150000_1_, p_150000_2_, p_150000_3_, p_150000_4_, 2);
/*  84:110 */     if ((var5.func_150860_b()) && (var5.field_150864_e == 0))
/*  85:    */     {
/*  86:112 */       var5.func_150859_c();
/*  87:113 */       return true;
/*  88:    */     }
/*  89:115 */     if ((var6.func_150860_b()) && (var6.field_150864_e == 0))
/*  90:    */     {
/*  91:117 */       var6.func_150859_c();
/*  92:118 */       return true;
/*  93:    */     }
/*  94:122 */     return false;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
/*  98:    */   {
/*  99:128 */     int var6 = func_149999_b(p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_));
/* 100:129 */     Size var7 = new Size(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, 1);
/* 101:130 */     Size var8 = new Size(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, 2);
/* 102:132 */     if ((var6 == 1) && ((!var7.func_150860_b()) || (var7.field_150864_e < var7.field_150868_h * var7.field_150862_g))) {
/* 103:134 */       p_149695_1_.setBlock(p_149695_2_, p_149695_3_, p_149695_4_, Blocks.air);
/* 104:136 */     } else if ((var6 == 2) && ((!var8.func_150860_b()) || (var8.field_150864_e < var8.field_150868_h * var8.field_150862_g))) {
/* 105:138 */       p_149695_1_.setBlock(p_149695_2_, p_149695_3_, p_149695_4_, Blocks.air);
/* 106:140 */     } else if ((var6 == 0) && (!var7.func_150860_b()) && (!var8.func_150860_b())) {
/* 107:142 */       p_149695_1_.setBlock(p_149695_2_, p_149695_3_, p_149695_4_, Blocks.air);
/* 108:    */     }
/* 109:    */   }
/* 110:    */   
/* 111:    */   public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_)
/* 112:    */   {
/* 113:148 */     int var6 = 0;
/* 114:150 */     if (p_149646_1_.getBlock(p_149646_2_, p_149646_3_, p_149646_4_) == this)
/* 115:    */     {
/* 116:152 */       var6 = func_149999_b(p_149646_1_.getBlockMetadata(p_149646_2_, p_149646_3_, p_149646_4_));
/* 117:154 */       if (var6 == 0) {
/* 118:156 */         return false;
/* 119:    */       }
/* 120:159 */       if ((var6 == 2) && (p_149646_5_ != 5) && (p_149646_5_ != 4)) {
/* 121:161 */         return false;
/* 122:    */       }
/* 123:164 */       if ((var6 == 1) && (p_149646_5_ != 3) && (p_149646_5_ != 2)) {
/* 124:166 */         return false;
/* 125:    */       }
/* 126:    */     }
/* 127:170 */     boolean var7 = (p_149646_1_.getBlock(p_149646_2_ - 1, p_149646_3_, p_149646_4_) == this) && (p_149646_1_.getBlock(p_149646_2_ - 2, p_149646_3_, p_149646_4_) != this);
/* 128:171 */     boolean var8 = (p_149646_1_.getBlock(p_149646_2_ + 1, p_149646_3_, p_149646_4_) == this) && (p_149646_1_.getBlock(p_149646_2_ + 2, p_149646_3_, p_149646_4_) != this);
/* 129:172 */     boolean var9 = (p_149646_1_.getBlock(p_149646_2_, p_149646_3_, p_149646_4_ - 1) == this) && (p_149646_1_.getBlock(p_149646_2_, p_149646_3_, p_149646_4_ - 2) != this);
/* 130:173 */     boolean var10 = (p_149646_1_.getBlock(p_149646_2_, p_149646_3_, p_149646_4_ + 1) == this) && (p_149646_1_.getBlock(p_149646_2_, p_149646_3_, p_149646_4_ + 2) != this);
/* 131:174 */     boolean var11 = (var7) || (var8) || (var6 == 1);
/* 132:175 */     boolean var12 = (var9) || (var10) || (var6 == 2);
/* 133:176 */     return (var11) && (p_149646_5_ == 4);
/* 134:    */   }
/* 135:    */   
/* 136:    */   public int quantityDropped(Random p_149745_1_)
/* 137:    */   {
/* 138:184 */     return 0;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public int getRenderBlockPass()
/* 142:    */   {
/* 143:192 */     return 1;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity p_149670_5_)
/* 147:    */   {
/* 148:197 */     if ((p_149670_5_.ridingEntity == null) && (p_149670_5_.riddenByEntity == null)) {
/* 149:199 */       p_149670_5_.setInPortal();
/* 150:    */     }
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_)
/* 154:    */   {
/* 155:208 */     if (p_149734_5_.nextInt(100) == 0) {
/* 156:210 */       p_149734_1_.playSound(p_149734_2_ + 0.5D, p_149734_3_ + 0.5D, p_149734_4_ + 0.5D, "portal.portal", 0.5F, p_149734_5_.nextFloat() * 0.4F + 0.8F, false);
/* 157:    */     }
/* 158:213 */     for (int var6 = 0; var6 < 4; var6++)
/* 159:    */     {
/* 160:215 */       double var7 = p_149734_2_ + p_149734_5_.nextFloat();
/* 161:216 */       double var9 = p_149734_3_ + p_149734_5_.nextFloat();
/* 162:217 */       double var11 = p_149734_4_ + p_149734_5_.nextFloat();
/* 163:218 */       double var13 = 0.0D;
/* 164:219 */       double var15 = 0.0D;
/* 165:220 */       double var17 = 0.0D;
/* 166:221 */       int var19 = p_149734_5_.nextInt(2) * 2 - 1;
/* 167:222 */       var13 = (p_149734_5_.nextFloat() - 0.5D) * 0.5D;
/* 168:223 */       var15 = (p_149734_5_.nextFloat() - 0.5D) * 0.5D;
/* 169:224 */       var17 = (p_149734_5_.nextFloat() - 0.5D) * 0.5D;
/* 170:226 */       if ((p_149734_1_.getBlock(p_149734_2_ - 1, p_149734_3_, p_149734_4_) != this) && (p_149734_1_.getBlock(p_149734_2_ + 1, p_149734_3_, p_149734_4_) != this))
/* 171:    */       {
/* 172:228 */         var7 = p_149734_2_ + 0.5D + 0.25D * var19;
/* 173:229 */         var13 = p_149734_5_.nextFloat() * 2.0F * var19;
/* 174:    */       }
/* 175:    */       else
/* 176:    */       {
/* 177:233 */         var11 = p_149734_4_ + 0.5D + 0.25D * var19;
/* 178:234 */         var17 = p_149734_5_.nextFloat() * 2.0F * var19;
/* 179:    */       }
/* 180:237 */       p_149734_1_.spawnParticle("portal", var7, var9, var11, var13, var15, var17);
/* 181:    */     }
/* 182:    */   }
/* 183:    */   
/* 184:    */   public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
/* 185:    */   {
/* 186:246 */     return Item.getItemById(0);
/* 187:    */   }
/* 188:    */   
/* 189:    */   public static int func_149999_b(int p_149999_0_)
/* 190:    */   {
/* 191:251 */     return p_149999_0_ & 0x3;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public static class Size
/* 195:    */   {
/* 196:    */     private final World field_150867_a;
/* 197:    */     private final int field_150865_b;
/* 198:    */     private final int field_150866_c;
/* 199:    */     private final int field_150863_d;
/* 200:260 */     private int field_150864_e = 0;
/* 201:    */     private ChunkCoordinates field_150861_f;
/* 202:    */     private int field_150862_g;
/* 203:    */     private int field_150868_h;
/* 204:    */     private static final String __OBFID = "CL_00000285";
/* 205:    */     
/* 206:    */     public Size(World p_i45415_1_, int p_i45415_2_, int p_i45415_3_, int p_i45415_4_, int p_i45415_5_)
/* 207:    */     {
/* 208:268 */       this.field_150867_a = p_i45415_1_;
/* 209:269 */       this.field_150865_b = p_i45415_5_;
/* 210:270 */       this.field_150863_d = BlockPortal.field_150001_a[p_i45415_5_][0];
/* 211:271 */       this.field_150866_c = BlockPortal.field_150001_a[p_i45415_5_][1];
/* 212:273 */       for (int var6 = p_i45415_3_; (p_i45415_3_ > var6 - 21) && (p_i45415_3_ > 0) && (func_150857_a(p_i45415_1_.getBlock(p_i45415_2_, p_i45415_3_ - 1, p_i45415_4_))); p_i45415_3_--) {}
/* 213:278 */       int var7 = func_150853_a(p_i45415_2_, p_i45415_3_, p_i45415_4_, this.field_150863_d) - 1;
/* 214:280 */       if (var7 >= 0)
/* 215:    */       {
/* 216:282 */         this.field_150861_f = new ChunkCoordinates(p_i45415_2_ + var7 * net.minecraft.util.Direction.offsetX[this.field_150863_d], p_i45415_3_, p_i45415_4_ + var7 * net.minecraft.util.Direction.offsetZ[this.field_150863_d]);
/* 217:283 */         this.field_150868_h = func_150853_a(this.field_150861_f.posX, this.field_150861_f.posY, this.field_150861_f.posZ, this.field_150866_c);
/* 218:285 */         if ((this.field_150868_h < 2) || (this.field_150868_h > 21))
/* 219:    */         {
/* 220:287 */           this.field_150861_f = null;
/* 221:288 */           this.field_150868_h = 0;
/* 222:    */         }
/* 223:    */       }
/* 224:292 */       if (this.field_150861_f != null) {
/* 225:294 */         this.field_150862_g = func_150858_a();
/* 226:    */       }
/* 227:    */     }
/* 228:    */     
/* 229:    */     protected int func_150853_a(int p_150853_1_, int p_150853_2_, int p_150853_3_, int p_150853_4_)
/* 230:    */     {
/* 231:300 */       int var6 = net.minecraft.util.Direction.offsetX[p_150853_4_];
/* 232:301 */       int var7 = net.minecraft.util.Direction.offsetZ[p_150853_4_];
/* 233:305 */       for (int var5 = 0; var5 < 22; var5++)
/* 234:    */       {
/* 235:307 */         Block var8 = this.field_150867_a.getBlock(p_150853_1_ + var6 * var5, p_150853_2_, p_150853_3_ + var7 * var5);
/* 236:309 */         if (!func_150857_a(var8)) {
/* 237:    */           break;
/* 238:    */         }
/* 239:314 */         Block var9 = this.field_150867_a.getBlock(p_150853_1_ + var6 * var5, p_150853_2_ - 1, p_150853_3_ + var7 * var5);
/* 240:316 */         if (var9 != Blocks.obsidian) {
/* 241:    */           break;
/* 242:    */         }
/* 243:    */       }
/* 244:322 */       Block var8 = this.field_150867_a.getBlock(p_150853_1_ + var6 * var5, p_150853_2_, p_150853_3_ + var7 * var5);
/* 245:323 */       return var8 == Blocks.obsidian ? var5 : 0;
/* 246:    */     }
/* 247:    */     
/* 248:    */     protected int func_150858_a()
/* 249:    */     {
/* 250:334 */       for (this.field_150862_g = 0; this.field_150862_g < 21; this.field_150862_g += 1)
/* 251:    */       {
/* 252:336 */         int var1 = this.field_150861_f.posY + this.field_150862_g;
/* 253:338 */         for (int var2 = 0; var2 < this.field_150868_h; var2++)
/* 254:    */         {
/* 255:340 */           int var3 = this.field_150861_f.posX + var2 * net.minecraft.util.Direction.offsetX[BlockPortal.field_150001_a[this.field_150865_b][1]];
/* 256:341 */           int var4 = this.field_150861_f.posZ + var2 * net.minecraft.util.Direction.offsetZ[BlockPortal.field_150001_a[this.field_150865_b][1]];
/* 257:342 */           Block var5 = this.field_150867_a.getBlock(var3, var1, var4);
/* 258:344 */           if (!func_150857_a(var5)) {
/* 259:    */             break;
/* 260:    */           }
/* 261:349 */           if (var5 == Blocks.portal) {
/* 262:351 */             this.field_150864_e += 1;
/* 263:    */           }
/* 264:354 */           if (var2 == 0)
/* 265:    */           {
/* 266:356 */             var5 = this.field_150867_a.getBlock(var3 + net.minecraft.util.Direction.offsetX[BlockPortal.field_150001_a[this.field_150865_b][0]], var1, var4 + net.minecraft.util.Direction.offsetZ[BlockPortal.field_150001_a[this.field_150865_b][0]]);
/* 267:358 */             if (var5 != Blocks.obsidian) {
/* 268:    */               break;
/* 269:    */             }
/* 270:    */           }
/* 271:363 */           else if (var2 == this.field_150868_h - 1)
/* 272:    */           {
/* 273:365 */             var5 = this.field_150867_a.getBlock(var3 + net.minecraft.util.Direction.offsetX[BlockPortal.field_150001_a[this.field_150865_b][1]], var1, var4 + net.minecraft.util.Direction.offsetZ[BlockPortal.field_150001_a[this.field_150865_b][1]]);
/* 274:367 */             if (var5 != Blocks.obsidian) {
/* 275:    */               break;
/* 276:    */             }
/* 277:    */           }
/* 278:    */         }
/* 279:    */       }
/* 280:375 */       for (int var1 = 0; var1 < this.field_150868_h; var1++)
/* 281:    */       {
/* 282:377 */         int var2 = this.field_150861_f.posX + var1 * net.minecraft.util.Direction.offsetX[BlockPortal.field_150001_a[this.field_150865_b][1]];
/* 283:378 */         int var3 = this.field_150861_f.posY + this.field_150862_g;
/* 284:379 */         int var4 = this.field_150861_f.posZ + var1 * net.minecraft.util.Direction.offsetZ[BlockPortal.field_150001_a[this.field_150865_b][1]];
/* 285:381 */         if (this.field_150867_a.getBlock(var2, var3, var4) != Blocks.obsidian)
/* 286:    */         {
/* 287:383 */           this.field_150862_g = 0;
/* 288:384 */           break;
/* 289:    */         }
/* 290:    */       }
/* 291:388 */       if ((this.field_150862_g <= 21) && (this.field_150862_g >= 3)) {
/* 292:390 */         return this.field_150862_g;
/* 293:    */       }
/* 294:394 */       this.field_150861_f = null;
/* 295:395 */       this.field_150868_h = 0;
/* 296:396 */       this.field_150862_g = 0;
/* 297:397 */       return 0;
/* 298:    */     }
/* 299:    */     
/* 300:    */     protected boolean func_150857_a(Block p_150857_1_)
/* 301:    */     {
/* 302:403 */       return (p_150857_1_.blockMaterial == Material.air) || (p_150857_1_ == Blocks.fire) || (p_150857_1_ == Blocks.portal);
/* 303:    */     }
/* 304:    */     
/* 305:    */     public boolean func_150860_b()
/* 306:    */     {
/* 307:408 */       return (this.field_150861_f != null) && (this.field_150868_h >= 2) && (this.field_150868_h <= 21) && (this.field_150862_g >= 3) && (this.field_150862_g <= 21);
/* 308:    */     }
/* 309:    */     
/* 310:    */     public void func_150859_c()
/* 311:    */     {
/* 312:413 */       for (int var1 = 0; var1 < this.field_150868_h; var1++)
/* 313:    */       {
/* 314:415 */         int var2 = this.field_150861_f.posX + net.minecraft.util.Direction.offsetX[this.field_150866_c] * var1;
/* 315:416 */         int var3 = this.field_150861_f.posZ + net.minecraft.util.Direction.offsetZ[this.field_150866_c] * var1;
/* 316:418 */         for (int var4 = 0; var4 < this.field_150862_g; var4++)
/* 317:    */         {
/* 318:420 */           int var5 = this.field_150861_f.posY + var4;
/* 319:421 */           this.field_150867_a.setBlock(var2, var5, var3, Blocks.portal, this.field_150865_b, 2);
/* 320:    */         }
/* 321:    */       }
/* 322:    */     }
/* 323:    */   }
/* 324:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockPortal
 * JD-Core Version:    0.7.0.1
 */