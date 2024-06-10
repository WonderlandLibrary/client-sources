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
/*  11:    */ import net.minecraft.util.IIcon;
/*  12:    */ import net.minecraft.world.ColorizerFoliage;
/*  13:    */ import net.minecraft.world.IBlockAccess;
/*  14:    */ import net.minecraft.world.World;
/*  15:    */ import net.minecraft.world.biome.BiomeGenBase;
/*  16:    */ 
/*  17:    */ public abstract class BlockLeaves
/*  18:    */   extends BlockLeavesBase
/*  19:    */ {
/*  20:    */   int[] field_150128_a;
/*  21:    */   protected int field_150127_b;
/*  22: 21 */   protected IIcon[][] field_150129_M = new IIcon[2][];
/*  23:    */   private static final String __OBFID = "CL_00000263";
/*  24:    */   
/*  25:    */   public BlockLeaves()
/*  26:    */   {
/*  27: 26 */     super(Material.leaves, false);
/*  28: 27 */     setTickRandomly(true);
/*  29: 28 */     setCreativeTab(CreativeTabs.tabDecorations);
/*  30: 29 */     setHardness(0.2F);
/*  31: 30 */     setLightOpacity(1);
/*  32: 31 */     setStepSound(soundTypeGrass);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public int getBlockColor()
/*  36:    */   {
/*  37: 36 */     double var1 = 0.5D;
/*  38: 37 */     double var3 = 1.0D;
/*  39: 38 */     return ColorizerFoliage.getFoliageColor(var1, var3);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public int getRenderColor(int p_149741_1_)
/*  43:    */   {
/*  44: 46 */     return ColorizerFoliage.getFoliageColorBasic();
/*  45:    */   }
/*  46:    */   
/*  47:    */   public int colorMultiplier(IBlockAccess p_149720_1_, int p_149720_2_, int p_149720_3_, int p_149720_4_)
/*  48:    */   {
/*  49: 55 */     int var5 = 0;
/*  50: 56 */     int var6 = 0;
/*  51: 57 */     int var7 = 0;
/*  52: 59 */     for (int var8 = -1; var8 <= 1; var8++) {
/*  53: 61 */       for (int var9 = -1; var9 <= 1; var9++)
/*  54:    */       {
/*  55: 63 */         int var10 = p_149720_1_.getBiomeGenForCoords(p_149720_2_ + var9, p_149720_4_ + var8).getBiomeFoliageColor(p_149720_2_ + var9, p_149720_3_, p_149720_4_ + var8);
/*  56: 64 */         var5 += ((var10 & 0xFF0000) >> 16);
/*  57: 65 */         var6 += ((var10 & 0xFF00) >> 8);
/*  58: 66 */         var7 += (var10 & 0xFF);
/*  59:    */       }
/*  60:    */     }
/*  61: 70 */     return (var5 / 9 & 0xFF) << 16 | (var6 / 9 & 0xFF) << 8 | var7 / 9 & 0xFF;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
/*  65:    */   {
/*  66: 75 */     byte var7 = 1;
/*  67: 76 */     int var8 = var7 + 1;
/*  68: 78 */     if (p_149749_1_.checkChunksExist(p_149749_2_ - var8, p_149749_3_ - var8, p_149749_4_ - var8, p_149749_2_ + var8, p_149749_3_ + var8, p_149749_4_ + var8)) {
/*  69: 80 */       for (int var9 = -var7; var9 <= var7; var9++) {
/*  70: 82 */         for (int var10 = -var7; var10 <= var7; var10++) {
/*  71: 84 */           for (int var11 = -var7; var11 <= var7; var11++) {
/*  72: 86 */             if (p_149749_1_.getBlock(p_149749_2_ + var9, p_149749_3_ + var10, p_149749_4_ + var11).getMaterial() == Material.leaves)
/*  73:    */             {
/*  74: 88 */               int var12 = p_149749_1_.getBlockMetadata(p_149749_2_ + var9, p_149749_3_ + var10, p_149749_4_ + var11);
/*  75: 89 */               p_149749_1_.setBlockMetadataWithNotify(p_149749_2_ + var9, p_149749_3_ + var10, p_149749_4_ + var11, var12 | 0x8, 4);
/*  76:    */             }
/*  77:    */           }
/*  78:    */         }
/*  79:    */       }
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
/*  84:    */   {
/*  85:102 */     if (!p_149674_1_.isClient)
/*  86:    */     {
/*  87:104 */       int var6 = p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_);
/*  88:106 */       if (((var6 & 0x8) != 0) && ((var6 & 0x4) == 0))
/*  89:    */       {
/*  90:108 */         byte var7 = 4;
/*  91:109 */         int var8 = var7 + 1;
/*  92:110 */         byte var9 = 32;
/*  93:111 */         int var10 = var9 * var9;
/*  94:112 */         int var11 = var9 / 2;
/*  95:114 */         if (this.field_150128_a == null) {
/*  96:116 */           this.field_150128_a = new int[var9 * var9 * var9];
/*  97:    */         }
/*  98:121 */         if (p_149674_1_.checkChunksExist(p_149674_2_ - var8, p_149674_3_ - var8, p_149674_4_ - var8, p_149674_2_ + var8, p_149674_3_ + var8, p_149674_4_ + var8))
/*  99:    */         {
/* 100:126 */           for (int var12 = -var7; var12 <= var7; var12++) {
/* 101:128 */             for (int var13 = -var7; var13 <= var7; var13++) {
/* 102:130 */               for (int var14 = -var7; var14 <= var7; var14++)
/* 103:    */               {
/* 104:132 */                 Block var15 = p_149674_1_.getBlock(p_149674_2_ + var12, p_149674_3_ + var13, p_149674_4_ + var14);
/* 105:134 */                 if ((var15 != Blocks.log) && (var15 != Blocks.log2))
/* 106:    */                 {
/* 107:136 */                   if (var15.getMaterial() == Material.leaves) {
/* 108:138 */                     this.field_150128_a[((var12 + var11) * var10 + (var13 + var11) * var9 + var14 + var11)] = -2;
/* 109:    */                   } else {
/* 110:142 */                     this.field_150128_a[((var12 + var11) * var10 + (var13 + var11) * var9 + var14 + var11)] = -1;
/* 111:    */                   }
/* 112:    */                 }
/* 113:    */                 else {
/* 114:147 */                   this.field_150128_a[((var12 + var11) * var10 + (var13 + var11) * var9 + var14 + var11)] = 0;
/* 115:    */                 }
/* 116:    */               }
/* 117:    */             }
/* 118:    */           }
/* 119:153 */           for (var12 = 1; var12 <= 4; var12++) {
/* 120:155 */             for (int var13 = -var7; var13 <= var7; var13++) {
/* 121:157 */               for (int var14 = -var7; var14 <= var7; var14++) {
/* 122:159 */                 for (int var16 = -var7; var16 <= var7; var16++) {
/* 123:161 */                   if (this.field_150128_a[((var13 + var11) * var10 + (var14 + var11) * var9 + var16 + var11)] == var12 - 1)
/* 124:    */                   {
/* 125:163 */                     if (this.field_150128_a[((var13 + var11 - 1) * var10 + (var14 + var11) * var9 + var16 + var11)] == -2) {
/* 126:165 */                       this.field_150128_a[((var13 + var11 - 1) * var10 + (var14 + var11) * var9 + var16 + var11)] = var12;
/* 127:    */                     }
/* 128:168 */                     if (this.field_150128_a[((var13 + var11 + 1) * var10 + (var14 + var11) * var9 + var16 + var11)] == -2) {
/* 129:170 */                       this.field_150128_a[((var13 + var11 + 1) * var10 + (var14 + var11) * var9 + var16 + var11)] = var12;
/* 130:    */                     }
/* 131:173 */                     if (this.field_150128_a[((var13 + var11) * var10 + (var14 + var11 - 1) * var9 + var16 + var11)] == -2) {
/* 132:175 */                       this.field_150128_a[((var13 + var11) * var10 + (var14 + var11 - 1) * var9 + var16 + var11)] = var12;
/* 133:    */                     }
/* 134:178 */                     if (this.field_150128_a[((var13 + var11) * var10 + (var14 + var11 + 1) * var9 + var16 + var11)] == -2) {
/* 135:180 */                       this.field_150128_a[((var13 + var11) * var10 + (var14 + var11 + 1) * var9 + var16 + var11)] = var12;
/* 136:    */                     }
/* 137:183 */                     if (this.field_150128_a[((var13 + var11) * var10 + (var14 + var11) * var9 + (var16 + var11 - 1))] == -2) {
/* 138:185 */                       this.field_150128_a[((var13 + var11) * var10 + (var14 + var11) * var9 + (var16 + var11 - 1))] = var12;
/* 139:    */                     }
/* 140:188 */                     if (this.field_150128_a[((var13 + var11) * var10 + (var14 + var11) * var9 + var16 + var11 + 1)] == -2) {
/* 141:190 */                       this.field_150128_a[((var13 + var11) * var10 + (var14 + var11) * var9 + var16 + var11 + 1)] = var12;
/* 142:    */                     }
/* 143:    */                   }
/* 144:    */                 }
/* 145:    */               }
/* 146:    */             }
/* 147:    */           }
/* 148:    */         }
/* 149:199 */         int var12 = this.field_150128_a[(var11 * var10 + var11 * var9 + var11)];
/* 150:201 */         if (var12 >= 0) {
/* 151:203 */           p_149674_1_.setBlockMetadataWithNotify(p_149674_2_, p_149674_3_, p_149674_4_, var6 & 0xFFFFFFF7, 4);
/* 152:    */         } else {
/* 153:207 */           func_150126_e(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_);
/* 154:    */         }
/* 155:    */       }
/* 156:    */     }
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_)
/* 160:    */   {
/* 161:218 */     if ((p_149734_1_.canLightningStrikeAt(p_149734_2_, p_149734_3_ + 1, p_149734_4_)) && (!World.doesBlockHaveSolidTopSurface(p_149734_1_, p_149734_2_, p_149734_3_ - 1, p_149734_4_)) && (p_149734_5_.nextInt(15) == 1))
/* 162:    */     {
/* 163:220 */       double var6 = p_149734_2_ + p_149734_5_.nextFloat();
/* 164:221 */       double var8 = p_149734_3_ - 0.05D;
/* 165:222 */       double var10 = p_149734_4_ + p_149734_5_.nextFloat();
/* 166:223 */       p_149734_1_.spawnParticle("dripWater", var6, var8, var10, 0.0D, 0.0D, 0.0D);
/* 167:    */     }
/* 168:    */   }
/* 169:    */   
/* 170:    */   private void func_150126_e(World p_150126_1_, int p_150126_2_, int p_150126_3_, int p_150126_4_)
/* 171:    */   {
/* 172:229 */     dropBlockAsItem(p_150126_1_, p_150126_2_, p_150126_3_, p_150126_4_, p_150126_1_.getBlockMetadata(p_150126_2_, p_150126_3_, p_150126_4_), 0);
/* 173:230 */     p_150126_1_.setBlockToAir(p_150126_2_, p_150126_3_, p_150126_4_);
/* 174:    */   }
/* 175:    */   
/* 176:    */   public int quantityDropped(Random p_149745_1_)
/* 177:    */   {
/* 178:238 */     return p_149745_1_.nextInt(20) == 0 ? 1 : 0;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
/* 182:    */   {
/* 183:243 */     return Item.getItemFromBlock(Blocks.sapling);
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void dropBlockAsItemWithChance(World p_149690_1_, int p_149690_2_, int p_149690_3_, int p_149690_4_, int p_149690_5_, float p_149690_6_, int p_149690_7_)
/* 187:    */   {
/* 188:251 */     if (!p_149690_1_.isClient)
/* 189:    */     {
/* 190:253 */       int var8 = func_150123_b(p_149690_5_);
/* 191:255 */       if (p_149690_7_ > 0)
/* 192:    */       {
/* 193:257 */         var8 -= (2 << p_149690_7_);
/* 194:259 */         if (var8 < 10) {
/* 195:261 */           var8 = 10;
/* 196:    */         }
/* 197:    */       }
/* 198:265 */       if (p_149690_1_.rand.nextInt(var8) == 0)
/* 199:    */       {
/* 200:267 */         Item var9 = getItemDropped(p_149690_5_, p_149690_1_.rand, p_149690_7_);
/* 201:268 */         dropBlockAsItem_do(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, new ItemStack(var9, 1, damageDropped(p_149690_5_)));
/* 202:    */       }
/* 203:271 */       var8 = 200;
/* 204:273 */       if (p_149690_7_ > 0)
/* 205:    */       {
/* 206:275 */         var8 -= (10 << p_149690_7_);
/* 207:277 */         if (var8 < 40) {
/* 208:279 */           var8 = 40;
/* 209:    */         }
/* 210:    */       }
/* 211:283 */       func_150124_c(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, p_149690_5_, var8);
/* 212:    */     }
/* 213:    */   }
/* 214:    */   
/* 215:    */   protected void func_150124_c(World p_150124_1_, int p_150124_2_, int p_150124_3_, int p_150124_4_, int p_150124_5_, int p_150124_6_) {}
/* 216:    */   
/* 217:    */   protected int func_150123_b(int p_150123_1_)
/* 218:    */   {
/* 219:291 */     return 20;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public void harvestBlock(World p_149636_1_, EntityPlayer p_149636_2_, int p_149636_3_, int p_149636_4_, int p_149636_5_, int p_149636_6_)
/* 223:    */   {
/* 224:296 */     if ((!p_149636_1_.isClient) && (p_149636_2_.getCurrentEquippedItem() != null) && (p_149636_2_.getCurrentEquippedItem().getItem() == Items.shears))
/* 225:    */     {
/* 226:298 */       p_149636_2_.addStat(net.minecraft.stats.StatList.mineBlockStatArray[Block.getIdFromBlock(this)], 1);
/* 227:299 */       dropBlockAsItem_do(p_149636_1_, p_149636_3_, p_149636_4_, p_149636_5_, new ItemStack(Item.getItemFromBlock(this), 1, p_149636_6_ & 0x3));
/* 228:    */     }
/* 229:    */     else
/* 230:    */     {
/* 231:303 */       super.harvestBlock(p_149636_1_, p_149636_2_, p_149636_3_, p_149636_4_, p_149636_5_, p_149636_6_);
/* 232:    */     }
/* 233:    */   }
/* 234:    */   
/* 235:    */   public int damageDropped(int p_149692_1_)
/* 236:    */   {
/* 237:312 */     return p_149692_1_ & 0x3;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public boolean isOpaqueCube()
/* 241:    */   {
/* 242:317 */     return !this.field_150121_P;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public abstract IIcon getIcon(int paramInt1, int paramInt2);
/* 246:    */   
/* 247:    */   public void func_150122_b(boolean p_150122_1_)
/* 248:    */   {
/* 249:327 */     this.field_150121_P = p_150122_1_;
/* 250:328 */     this.field_150127_b = (p_150122_1_ ? 0 : 1);
/* 251:    */   }
/* 252:    */   
/* 253:    */   protected ItemStack createStackedBlock(int p_149644_1_)
/* 254:    */   {
/* 255:337 */     return new ItemStack(Item.getItemFromBlock(this), 1, p_149644_1_ & 0x3);
/* 256:    */   }
/* 257:    */   
/* 258:    */   public abstract String[] func_150125_e();
/* 259:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockLeaves
 * JD-Core Version:    0.7.0.1
 */