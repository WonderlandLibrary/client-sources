/*   1:    */ package net.minecraft.world.biome;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.Sets;
/*   4:    */ import java.awt.Color;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Random;
/*   8:    */ import java.util.Set;
/*   9:    */ import net.minecraft.block.Block;
/*  10:    */ import net.minecraft.block.material.Material;
/*  11:    */ import net.minecraft.entity.EnumCreatureType;
/*  12:    */ import net.minecraft.entity.monster.EntityCreeper;
/*  13:    */ import net.minecraft.entity.monster.EntityEnderman;
/*  14:    */ import net.minecraft.entity.monster.EntitySkeleton;
/*  15:    */ import net.minecraft.entity.monster.EntitySlime;
/*  16:    */ import net.minecraft.entity.monster.EntitySpider;
/*  17:    */ import net.minecraft.entity.monster.EntityWitch;
/*  18:    */ import net.minecraft.entity.monster.EntityZombie;
/*  19:    */ import net.minecraft.entity.passive.EntityBat;
/*  20:    */ import net.minecraft.entity.passive.EntityChicken;
/*  21:    */ import net.minecraft.entity.passive.EntityCow;
/*  22:    */ import net.minecraft.entity.passive.EntityPig;
/*  23:    */ import net.minecraft.entity.passive.EntitySheep;
/*  24:    */ import net.minecraft.entity.passive.EntitySquid;
/*  25:    */ import net.minecraft.init.Blocks;
/*  26:    */ import net.minecraft.util.MathHelper;
/*  27:    */ import net.minecraft.util.WeightedRandom.Item;
/*  28:    */ import net.minecraft.world.ColorizerFoliage;
/*  29:    */ import net.minecraft.world.ColorizerGrass;
/*  30:    */ import net.minecraft.world.World;
/*  31:    */ import net.minecraft.world.gen.NoiseGeneratorPerlin;
/*  32:    */ import net.minecraft.world.gen.feature.WorldGenAbstractTree;
/*  33:    */ import net.minecraft.world.gen.feature.WorldGenBigTree;
/*  34:    */ import net.minecraft.world.gen.feature.WorldGenDoublePlant;
/*  35:    */ import net.minecraft.world.gen.feature.WorldGenSwamp;
/*  36:    */ import net.minecraft.world.gen.feature.WorldGenTallGrass;
/*  37:    */ import net.minecraft.world.gen.feature.WorldGenTrees;
/*  38:    */ import net.minecraft.world.gen.feature.WorldGenerator;
/*  39:    */ import org.apache.logging.log4j.LogManager;
/*  40:    */ import org.apache.logging.log4j.Logger;
/*  41:    */ 
/*  42:    */ public abstract class BiomeGenBase
/*  43:    */ {
/*  44: 45 */   private static final Logger logger = ;
/*  45: 46 */   protected static final Height field_150596_a = new Height(0.1F, 0.2F);
/*  46: 47 */   protected static final Height field_150594_b = new Height(-0.5F, 0.0F);
/*  47: 48 */   protected static final Height field_150595_c = new Height(-1.0F, 0.1F);
/*  48: 49 */   protected static final Height field_150592_d = new Height(-1.8F, 0.1F);
/*  49: 50 */   protected static final Height field_150593_e = new Height(0.125F, 0.05F);
/*  50: 51 */   protected static final Height field_150590_f = new Height(0.2F, 0.2F);
/*  51: 52 */   protected static final Height field_150591_g = new Height(0.45F, 0.3F);
/*  52: 53 */   protected static final Height field_150602_h = new Height(1.5F, 0.025F);
/*  53: 54 */   protected static final Height field_150603_i = new Height(1.0F, 0.5F);
/*  54: 55 */   protected static final Height field_150600_j = new Height(0.0F, 0.025F);
/*  55: 56 */   protected static final Height field_150601_k = new Height(0.1F, 0.8F);
/*  56: 57 */   protected static final Height field_150598_l = new Height(0.2F, 0.3F);
/*  57: 58 */   protected static final Height field_150599_m = new Height(-0.2F, 0.1F);
/*  58: 61 */   public static final BiomeGenBase[] biomeList = new BiomeGenBase[256];
/*  59: 62 */   public static final Set field_150597_n = Sets.newHashSet();
/*  60: 63 */   public static final BiomeGenBase ocean = new BiomeGenOcean(0).setColor(112).setBiomeName("Ocean").func_150570_a(field_150595_c);
/*  61: 64 */   public static final BiomeGenBase plains = new BiomeGenPlains(1).setColor(9286496).setBiomeName("Plains");
/*  62: 65 */   public static final BiomeGenBase desert = new BiomeGenDesert(2).setColor(16421912).setBiomeName("Desert").setDisableRain().setTemperatureRainfall(2.0F, 0.0F).func_150570_a(field_150593_e);
/*  63: 66 */   public static final BiomeGenBase extremeHills = new BiomeGenHills(3, false).setColor(6316128).setBiomeName("Extreme Hills").func_150570_a(field_150603_i).setTemperatureRainfall(0.2F, 0.3F);
/*  64: 67 */   public static final BiomeGenBase forest = new BiomeGenForest(4, 0).setColor(353825).setBiomeName("Forest");
/*  65: 68 */   public static final BiomeGenBase taiga = new BiomeGenTaiga(5, 0).setColor(747097).setBiomeName("Taiga").func_76733_a(5159473).setTemperatureRainfall(0.25F, 0.8F).func_150570_a(field_150590_f);
/*  66: 69 */   public static final BiomeGenBase swampland = new BiomeGenSwamp(6).setColor(522674).setBiomeName("Swampland").func_76733_a(9154376).func_150570_a(field_150599_m).setTemperatureRainfall(0.8F, 0.9F);
/*  67: 70 */   public static final BiomeGenBase river = new BiomeGenRiver(7).setColor(255).setBiomeName("River").func_150570_a(field_150594_b);
/*  68: 71 */   public static final BiomeGenBase hell = new BiomeGenHell(8).setColor(16711680).setBiomeName("Hell").setDisableRain().setTemperatureRainfall(2.0F, 0.0F);
/*  69: 74 */   public static final BiomeGenBase sky = new BiomeGenEnd(9).setColor(8421631).setBiomeName("Sky").setDisableRain();
/*  70: 75 */   public static final BiomeGenBase frozenOcean = new BiomeGenOcean(10).setColor(9474208).setBiomeName("FrozenOcean").setEnableSnow().func_150570_a(field_150595_c).setTemperatureRainfall(0.0F, 0.5F);
/*  71: 76 */   public static final BiomeGenBase frozenRiver = new BiomeGenRiver(11).setColor(10526975).setBiomeName("FrozenRiver").setEnableSnow().func_150570_a(field_150594_b).setTemperatureRainfall(0.0F, 0.5F);
/*  72: 77 */   public static final BiomeGenBase icePlains = new BiomeGenSnow(12, false).setColor(16777215).setBiomeName("Ice Plains").setEnableSnow().setTemperatureRainfall(0.0F, 0.5F).func_150570_a(field_150593_e);
/*  73: 78 */   public static final BiomeGenBase iceMountains = new BiomeGenSnow(13, false).setColor(10526880).setBiomeName("Ice Mountains").setEnableSnow().func_150570_a(field_150591_g).setTemperatureRainfall(0.0F, 0.5F);
/*  74: 79 */   public static final BiomeGenBase mushroomIsland = new BiomeGenMushroomIsland(14).setColor(16711935).setBiomeName("MushroomIsland").setTemperatureRainfall(0.9F, 1.0F).func_150570_a(field_150598_l);
/*  75: 80 */   public static final BiomeGenBase mushroomIslandShore = new BiomeGenMushroomIsland(15).setColor(10486015).setBiomeName("MushroomIslandShore").setTemperatureRainfall(0.9F, 1.0F).func_150570_a(field_150600_j);
/*  76: 83 */   public static final BiomeGenBase beach = new BiomeGenBeach(16).setColor(16440917).setBiomeName("Beach").setTemperatureRainfall(0.8F, 0.4F).func_150570_a(field_150600_j);
/*  77: 86 */   public static final BiomeGenBase desertHills = new BiomeGenDesert(17).setColor(13786898).setBiomeName("DesertHills").setDisableRain().setTemperatureRainfall(2.0F, 0.0F).func_150570_a(field_150591_g);
/*  78: 89 */   public static final BiomeGenBase forestHills = new BiomeGenForest(18, 0).setColor(2250012).setBiomeName("ForestHills").func_150570_a(field_150591_g);
/*  79: 92 */   public static final BiomeGenBase taigaHills = new BiomeGenTaiga(19, 0).setColor(1456435).setBiomeName("TaigaHills").func_76733_a(5159473).setTemperatureRainfall(0.25F, 0.8F).func_150570_a(field_150591_g);
/*  80: 95 */   public static final BiomeGenBase extremeHillsEdge = new BiomeGenHills(20, true).setColor(7501978).setBiomeName("Extreme Hills Edge").func_150570_a(field_150603_i.func_150775_a()).setTemperatureRainfall(0.2F, 0.3F);
/*  81: 98 */   public static final BiomeGenBase jungle = new BiomeGenJungle(21, false).setColor(5470985).setBiomeName("Jungle").func_76733_a(5470985).setTemperatureRainfall(0.95F, 0.9F);
/*  82: 99 */   public static final BiomeGenBase jungleHills = new BiomeGenJungle(22, false).setColor(2900485).setBiomeName("JungleHills").func_76733_a(5470985).setTemperatureRainfall(0.95F, 0.9F).func_150570_a(field_150591_g);
/*  83:100 */   public static final BiomeGenBase field_150574_L = new BiomeGenJungle(23, true).setColor(6458135).setBiomeName("JungleEdge").func_76733_a(5470985).setTemperatureRainfall(0.95F, 0.8F);
/*  84:101 */   public static final BiomeGenBase field_150575_M = new BiomeGenOcean(24).setColor(48).setBiomeName("Deep Ocean").func_150570_a(field_150592_d);
/*  85:102 */   public static final BiomeGenBase field_150576_N = new BiomeGenStoneBeach(25).setColor(10658436).setBiomeName("Stone Beach").setTemperatureRainfall(0.2F, 0.3F).func_150570_a(field_150601_k);
/*  86:103 */   public static final BiomeGenBase field_150577_O = new BiomeGenBeach(26).setColor(16445632).setBiomeName("Cold Beach").setTemperatureRainfall(0.05F, 0.3F).func_150570_a(field_150600_j).setEnableSnow();
/*  87:104 */   public static final BiomeGenBase field_150583_P = new BiomeGenForest(27, 2).setBiomeName("Birch Forest").setColor(3175492);
/*  88:105 */   public static final BiomeGenBase field_150582_Q = new BiomeGenForest(28, 2).setBiomeName("Birch Forest Hills").setColor(2055986).func_150570_a(field_150591_g);
/*  89:106 */   public static final BiomeGenBase field_150585_R = new BiomeGenForest(29, 3).setColor(4215066).setBiomeName("Roofed Forest");
/*  90:107 */   public static final BiomeGenBase field_150584_S = new BiomeGenTaiga(30, 0).setColor(3233098).setBiomeName("Cold Taiga").func_76733_a(5159473).setEnableSnow().setTemperatureRainfall(-0.5F, 0.4F).func_150570_a(field_150590_f).func_150563_c(16777215);
/*  91:108 */   public static final BiomeGenBase field_150579_T = new BiomeGenTaiga(31, 0).setColor(2375478).setBiomeName("Cold Taiga Hills").func_76733_a(5159473).setEnableSnow().setTemperatureRainfall(-0.5F, 0.4F).func_150570_a(field_150591_g).func_150563_c(16777215);
/*  92:109 */   public static final BiomeGenBase field_150578_U = new BiomeGenTaiga(32, 1).setColor(5858897).setBiomeName("Mega Taiga").func_76733_a(5159473).setTemperatureRainfall(0.3F, 0.8F).func_150570_a(field_150590_f);
/*  93:110 */   public static final BiomeGenBase field_150581_V = new BiomeGenTaiga(33, 1).setColor(4542270).setBiomeName("Mega Taiga Hills").func_76733_a(5159473).setTemperatureRainfall(0.3F, 0.8F).func_150570_a(field_150591_g);
/*  94:111 */   public static final BiomeGenBase field_150580_W = new BiomeGenHills(34, true).setColor(5271632).setBiomeName("Extreme Hills+").func_150570_a(field_150603_i).setTemperatureRainfall(0.2F, 0.3F);
/*  95:112 */   public static final BiomeGenBase field_150588_X = new BiomeGenSavanna(35).setColor(12431967).setBiomeName("Savanna").setTemperatureRainfall(1.2F, 0.0F).setDisableRain().func_150570_a(field_150593_e);
/*  96:113 */   public static final BiomeGenBase field_150587_Y = new BiomeGenSavanna(36).setColor(10984804).setBiomeName("Savanna Plateau").setTemperatureRainfall(1.0F, 0.0F).setDisableRain().func_150570_a(field_150602_h);
/*  97:114 */   public static final BiomeGenBase field_150589_Z = new BiomeGenMesa(37, false, false).setColor(14238997).setBiomeName("Mesa");
/*  98:115 */   public static final BiomeGenBase field_150607_aa = new BiomeGenMesa(38, false, true).setColor(11573093).setBiomeName("Mesa Plateau F").func_150570_a(field_150602_h);
/*  99:116 */   public static final BiomeGenBase field_150608_ab = new BiomeGenMesa(39, false, false).setColor(13274213).setBiomeName("Mesa Plateau").func_150570_a(field_150602_h);
/* 100:    */   
/* 101:    */   protected BiomeGenBase(int par1)
/* 102:    */   {
/* 103:189 */     this.topBlock = Blocks.grass;
/* 104:190 */     this.field_150604_aj = 0;
/* 105:191 */     this.fillerBlock = Blocks.dirt;
/* 106:192 */     this.field_76754_C = 5169201;
/* 107:193 */     this.minHeight = field_150596_a.field_150777_a;
/* 108:194 */     this.maxHeight = field_150596_a.field_150776_b;
/* 109:195 */     this.temperature = 0.5F;
/* 110:196 */     this.rainfall = 0.5F;
/* 111:197 */     this.waterColorMultiplier = 16777215;
/* 112:198 */     this.spawnableMonsterList = new ArrayList();
/* 113:199 */     this.spawnableCreatureList = new ArrayList();
/* 114:200 */     this.spawnableWaterCreatureList = new ArrayList();
/* 115:201 */     this.spawnableCaveCreatureList = new ArrayList();
/* 116:202 */     this.enableRain = true;
/* 117:203 */     this.worldGeneratorTrees = new WorldGenTrees(false);
/* 118:204 */     this.worldGeneratorBigTree = new WorldGenBigTree(false);
/* 119:205 */     this.worldGeneratorSwamp = new WorldGenSwamp();
/* 120:206 */     this.biomeID = par1;
/* 121:207 */     biomeList[par1] = this;
/* 122:208 */     this.theBiomeDecorator = createBiomeDecorator();
/* 123:209 */     this.spawnableCreatureList.add(new SpawnListEntry(EntitySheep.class, 12, 4, 4));
/* 124:210 */     this.spawnableCreatureList.add(new SpawnListEntry(EntityPig.class, 10, 4, 4));
/* 125:211 */     this.spawnableCreatureList.add(new SpawnListEntry(EntityChicken.class, 10, 4, 4));
/* 126:212 */     this.spawnableCreatureList.add(new SpawnListEntry(EntityCow.class, 8, 4, 4));
/* 127:213 */     this.spawnableMonsterList.add(new SpawnListEntry(EntitySpider.class, 100, 4, 4));
/* 128:214 */     this.spawnableMonsterList.add(new SpawnListEntry(EntityZombie.class, 100, 4, 4));
/* 129:215 */     this.spawnableMonsterList.add(new SpawnListEntry(EntitySkeleton.class, 100, 4, 4));
/* 130:216 */     this.spawnableMonsterList.add(new SpawnListEntry(EntityCreeper.class, 100, 4, 4));
/* 131:217 */     this.spawnableMonsterList.add(new SpawnListEntry(EntitySlime.class, 100, 4, 4));
/* 132:218 */     this.spawnableMonsterList.add(new SpawnListEntry(EntityEnderman.class, 10, 1, 4));
/* 133:219 */     this.spawnableMonsterList.add(new SpawnListEntry(EntityWitch.class, 5, 1, 1));
/* 134:220 */     this.spawnableWaterCreatureList.add(new SpawnListEntry(EntitySquid.class, 10, 4, 4));
/* 135:221 */     this.spawnableCaveCreatureList.add(new SpawnListEntry(EntityBat.class, 10, 8, 8));
/* 136:    */   }
/* 137:    */   
/* 138:    */   protected BiomeDecorator createBiomeDecorator()
/* 139:    */   {
/* 140:229 */     return new BiomeDecorator();
/* 141:    */   }
/* 142:    */   
/* 143:    */   protected BiomeGenBase setTemperatureRainfall(float par1, float par2)
/* 144:    */   {
/* 145:237 */     if ((par1 > 0.1F) && (par1 < 0.2F)) {
/* 146:239 */       throw new IllegalArgumentException("Please avoid temperatures in the range 0.1 - 0.2 because of snow");
/* 147:    */     }
/* 148:243 */     this.temperature = par1;
/* 149:244 */     this.rainfall = par2;
/* 150:245 */     return this;
/* 151:    */   }
/* 152:    */   
/* 153:    */   protected final BiomeGenBase func_150570_a(Height p_150570_1_)
/* 154:    */   {
/* 155:251 */     this.minHeight = p_150570_1_.field_150777_a;
/* 156:252 */     this.maxHeight = p_150570_1_.field_150776_b;
/* 157:253 */     return this;
/* 158:    */   }
/* 159:    */   
/* 160:    */   protected BiomeGenBase setDisableRain()
/* 161:    */   {
/* 162:261 */     this.enableRain = false;
/* 163:262 */     return this;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public WorldGenAbstractTree func_150567_a(Random p_150567_1_)
/* 167:    */   {
/* 168:267 */     return p_150567_1_.nextInt(10) == 0 ? this.worldGeneratorBigTree : this.worldGeneratorTrees;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public WorldGenerator getRandomWorldGenForGrass(Random par1Random)
/* 172:    */   {
/* 173:275 */     return new WorldGenTallGrass(Blocks.tallgrass, 1);
/* 174:    */   }
/* 175:    */   
/* 176:    */   public String func_150572_a(Random p_150572_1_, int p_150572_2_, int p_150572_3_, int p_150572_4_)
/* 177:    */   {
/* 178:280 */     return p_150572_1_.nextInt(3) > 0 ? net.minecraft.block.BlockFlower.field_149858_b[0] : net.minecraft.block.BlockFlower.field_149859_a[0];
/* 179:    */   }
/* 180:    */   
/* 181:    */   protected BiomeGenBase setEnableSnow()
/* 182:    */   {
/* 183:288 */     this.enableSnow = true;
/* 184:289 */     return this;
/* 185:    */   }
/* 186:    */   
/* 187:    */   protected BiomeGenBase setBiomeName(String par1Str)
/* 188:    */   {
/* 189:294 */     this.biomeName = par1Str;
/* 190:295 */     return this;
/* 191:    */   }
/* 192:    */   
/* 193:    */   protected BiomeGenBase func_76733_a(int par1)
/* 194:    */   {
/* 195:300 */     this.field_76754_C = par1;
/* 196:301 */     return this;
/* 197:    */   }
/* 198:    */   
/* 199:    */   protected BiomeGenBase setColor(int par1)
/* 200:    */   {
/* 201:306 */     func_150557_a(par1, false);
/* 202:307 */     return this;
/* 203:    */   }
/* 204:    */   
/* 205:    */   protected BiomeGenBase func_150563_c(int p_150563_1_)
/* 206:    */   {
/* 207:312 */     this.field_150609_ah = p_150563_1_;
/* 208:313 */     return this;
/* 209:    */   }
/* 210:    */   
/* 211:    */   protected BiomeGenBase func_150557_a(int p_150557_1_, boolean p_150557_2_)
/* 212:    */   {
/* 213:318 */     this.color = p_150557_1_;
/* 214:320 */     if (p_150557_2_) {
/* 215:322 */       this.field_150609_ah = ((p_150557_1_ & 0xFEFEFE) >> 1);
/* 216:    */     } else {
/* 217:326 */       this.field_150609_ah = p_150557_1_;
/* 218:    */     }
/* 219:329 */     return this;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public int getSkyColorByTemp(float par1)
/* 223:    */   {
/* 224:337 */     par1 /= 3.0F;
/* 225:339 */     if (par1 < -1.0F) {
/* 226:341 */       par1 = -1.0F;
/* 227:    */     }
/* 228:344 */     if (par1 > 1.0F) {
/* 229:346 */       par1 = 1.0F;
/* 230:    */     }
/* 231:349 */     return Color.getHSBColor(0.6222222F - par1 * 0.05F, 0.5F + par1 * 0.1F, 1.0F).getRGB();
/* 232:    */   }
/* 233:    */   
/* 234:    */   public List getSpawnableList(EnumCreatureType par1EnumCreatureType)
/* 235:    */   {
/* 236:357 */     return par1EnumCreatureType == EnumCreatureType.ambient ? this.spawnableCaveCreatureList : par1EnumCreatureType == EnumCreatureType.waterCreature ? this.spawnableWaterCreatureList : par1EnumCreatureType == EnumCreatureType.creature ? this.spawnableCreatureList : par1EnumCreatureType == EnumCreatureType.monster ? this.spawnableMonsterList : null;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public boolean getEnableSnow()
/* 240:    */   {
/* 241:365 */     return func_150559_j();
/* 242:    */   }
/* 243:    */   
/* 244:    */   public boolean canSpawnLightningBolt()
/* 245:    */   {
/* 246:373 */     return func_150559_j() ? false : this.enableRain;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public boolean isHighHumidity()
/* 250:    */   {
/* 251:381 */     return this.rainfall > 0.85F;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public float getSpawningChance()
/* 255:    */   {
/* 256:389 */     return 0.1F;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public final int getIntRainfall()
/* 260:    */   {
/* 261:397 */     return (int)(this.rainfall * 65536.0F);
/* 262:    */   }
/* 263:    */   
/* 264:    */   public final float getFloatRainfall()
/* 265:    */   {
/* 266:405 */     return this.rainfall;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public final float getFloatTemperature(int p_150564_1_, int p_150564_2_, int p_150564_3_)
/* 270:    */   {
/* 271:413 */     if (p_150564_2_ > 64)
/* 272:    */     {
/* 273:415 */       float var4 = (float)field_150605_ac.func_151601_a(p_150564_1_ * 1.0D / 8.0D, p_150564_3_ * 1.0D / 8.0D) * 4.0F;
/* 274:416 */       return this.temperature - (var4 + p_150564_2_ - 64.0F) * 0.05F / 30.0F;
/* 275:    */     }
/* 276:420 */     return this.temperature;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public void decorate(World par1World, Random par2Random, int par3, int par4)
/* 280:    */   {
/* 281:426 */     this.theBiomeDecorator.func_150512_a(par1World, par2Random, this, par3, par4);
/* 282:    */   }
/* 283:    */   
/* 284:    */   public int getBiomeGrassColor(int p_150558_1_, int p_150558_2_, int p_150558_3_)
/* 285:    */   {
/* 286:434 */     double var4 = MathHelper.clamp_float(getFloatTemperature(p_150558_1_, p_150558_2_, p_150558_3_), 0.0F, 1.0F);
/* 287:435 */     double var6 = MathHelper.clamp_float(getFloatRainfall(), 0.0F, 1.0F);
/* 288:436 */     return ColorizerGrass.getGrassColor(var4, var6);
/* 289:    */   }
/* 290:    */   
/* 291:    */   public int getBiomeFoliageColor(int p_150571_1_, int p_150571_2_, int p_150571_3_)
/* 292:    */   {
/* 293:444 */     double var4 = MathHelper.clamp_float(getFloatTemperature(p_150571_1_, p_150571_2_, p_150571_3_), 0.0F, 1.0F);
/* 294:445 */     double var6 = MathHelper.clamp_float(getFloatRainfall(), 0.0F, 1.0F);
/* 295:446 */     return ColorizerFoliage.getFoliageColor(var4, var6);
/* 296:    */   }
/* 297:    */   
/* 298:    */   public boolean func_150559_j()
/* 299:    */   {
/* 300:451 */     return this.enableSnow;
/* 301:    */   }
/* 302:    */   
/* 303:    */   public void func_150573_a(World p_150573_1_, Random p_150573_2_, Block[] p_150573_3_, byte[] p_150573_4_, int p_150573_5_, int p_150573_6_, double p_150573_7_)
/* 304:    */   {
/* 305:456 */     func_150560_b(p_150573_1_, p_150573_2_, p_150573_3_, p_150573_4_, p_150573_5_, p_150573_6_, p_150573_7_);
/* 306:    */   }
/* 307:    */   
/* 308:    */   public final void func_150560_b(World p_150560_1_, Random p_150560_2_, Block[] p_150560_3_, byte[] p_150560_4_, int p_150560_5_, int p_150560_6_, double p_150560_7_)
/* 309:    */   {
/* 310:461 */     boolean var9 = true;
/* 311:462 */     Block var10 = this.topBlock;
/* 312:463 */     byte var11 = (byte)(this.field_150604_aj & 0xFF);
/* 313:464 */     Block var12 = this.fillerBlock;
/* 314:465 */     int var13 = -1;
/* 315:466 */     int var14 = (int)(p_150560_7_ / 3.0D + 3.0D + p_150560_2_.nextDouble() * 0.25D);
/* 316:467 */     int var15 = p_150560_5_ & 0xF;
/* 317:468 */     int var16 = p_150560_6_ & 0xF;
/* 318:469 */     int var17 = p_150560_3_.length / 256;
/* 319:471 */     for (int var18 = 255; var18 >= 0; var18--)
/* 320:    */     {
/* 321:473 */       int var19 = (var16 * 16 + var15) * var17 + var18;
/* 322:475 */       if (var18 <= 0 + p_150560_2_.nextInt(5))
/* 323:    */       {
/* 324:477 */         p_150560_3_[var19] = Blocks.bedrock;
/* 325:    */       }
/* 326:    */       else
/* 327:    */       {
/* 328:481 */         Block var20 = p_150560_3_[var19];
/* 329:483 */         if ((var20 != null) && (var20.getMaterial() != Material.air))
/* 330:    */         {
/* 331:485 */           if (var20 == Blocks.stone) {
/* 332:487 */             if (var13 == -1)
/* 333:    */             {
/* 334:489 */               if (var14 <= 0)
/* 335:    */               {
/* 336:491 */                 var10 = null;
/* 337:492 */                 var11 = 0;
/* 338:493 */                 var12 = Blocks.stone;
/* 339:    */               }
/* 340:495 */               else if ((var18 >= 59) && (var18 <= 64))
/* 341:    */               {
/* 342:497 */                 var10 = this.topBlock;
/* 343:498 */                 var11 = (byte)(this.field_150604_aj & 0xFF);
/* 344:499 */                 var12 = this.fillerBlock;
/* 345:    */               }
/* 346:502 */               if ((var18 < 63) && ((var10 == null) || (var10.getMaterial() == Material.air))) {
/* 347:504 */                 if (getFloatTemperature(p_150560_5_, var18, p_150560_6_) < 0.15F)
/* 348:    */                 {
/* 349:506 */                   var10 = Blocks.ice;
/* 350:507 */                   var11 = 0;
/* 351:    */                 }
/* 352:    */                 else
/* 353:    */                 {
/* 354:511 */                   var10 = Blocks.water;
/* 355:512 */                   var11 = 0;
/* 356:    */                 }
/* 357:    */               }
/* 358:516 */               var13 = var14;
/* 359:518 */               if (var18 >= 62)
/* 360:    */               {
/* 361:520 */                 p_150560_3_[var19] = var10;
/* 362:521 */                 p_150560_4_[var19] = var11;
/* 363:    */               }
/* 364:523 */               else if (var18 < 56 - var14)
/* 365:    */               {
/* 366:525 */                 var10 = null;
/* 367:526 */                 var12 = Blocks.stone;
/* 368:527 */                 p_150560_3_[var19] = Blocks.gravel;
/* 369:    */               }
/* 370:    */               else
/* 371:    */               {
/* 372:531 */                 p_150560_3_[var19] = var12;
/* 373:    */               }
/* 374:    */             }
/* 375:534 */             else if (var13 > 0)
/* 376:    */             {
/* 377:536 */               var13--;
/* 378:537 */               p_150560_3_[var19] = var12;
/* 379:539 */               if ((var13 == 0) && (var12 == Blocks.sand))
/* 380:    */               {
/* 381:541 */                 var13 = p_150560_2_.nextInt(4) + Math.max(0, var18 - 63);
/* 382:542 */                 var12 = Blocks.sandstone;
/* 383:    */               }
/* 384:    */             }
/* 385:    */           }
/* 386:    */         }
/* 387:    */         else {
/* 388:549 */           var13 = -1;
/* 389:    */         }
/* 390:    */       }
/* 391:    */     }
/* 392:    */   }
/* 393:    */   
/* 394:    */   protected BiomeGenBase func_150566_k()
/* 395:    */   {
/* 396:557 */     return new BiomeGenMutated(this.biomeID + 128, this);
/* 397:    */   }
/* 398:    */   
/* 399:    */   public Class func_150562_l()
/* 400:    */   {
/* 401:562 */     return getClass();
/* 402:    */   }
/* 403:    */   
/* 404:    */   public boolean func_150569_a(BiomeGenBase p_150569_1_)
/* 405:    */   {
/* 406:567 */     return p_150569_1_ == this;
/* 407:    */   }
/* 408:    */   
/* 409:    */   public TempCategory func_150561_m()
/* 410:    */   {
/* 411:572 */     return this.temperature < 1.0D ? TempCategory.MEDIUM : this.temperature < 0.2D ? TempCategory.COLD : TempCategory.WARM;
/* 412:    */   }
/* 413:    */   
/* 414:    */   public static BiomeGenBase[] getBiomeGenArray()
/* 415:    */   {
/* 416:577 */     return biomeList;
/* 417:    */   }
/* 418:    */   
/* 419:    */   public static BiomeGenBase func_150568_d(int p_150568_0_)
/* 420:    */   {
/* 421:582 */     if ((p_150568_0_ >= 0) && (p_150568_0_ <= biomeList.length)) {
/* 422:584 */       return biomeList[p_150568_0_];
/* 423:    */     }
/* 424:588 */     logger.warn("Biome ID is out of bounds: " + p_150568_0_ + ", defaulting to 0 (Ocean)");
/* 425:589 */     return ocean;
/* 426:    */   }
/* 427:    */   
/* 428:    */   static
/* 429:    */   {
/* 430:595 */     plains.func_150566_k();
/* 431:596 */     desert.func_150566_k();
/* 432:597 */     forest.func_150566_k();
/* 433:598 */     taiga.func_150566_k();
/* 434:599 */     swampland.func_150566_k();
/* 435:600 */     icePlains.func_150566_k();
/* 436:601 */     jungle.func_150566_k();
/* 437:602 */     field_150574_L.func_150566_k();
/* 438:603 */     field_150584_S.func_150566_k();
/* 439:604 */     field_150588_X.func_150566_k();
/* 440:605 */     field_150587_Y.func_150566_k();
/* 441:606 */     field_150589_Z.func_150566_k();
/* 442:607 */     field_150607_aa.func_150566_k();
/* 443:608 */     field_150608_ab.func_150566_k();
/* 444:609 */     field_150583_P.func_150566_k();
/* 445:610 */     field_150582_Q.func_150566_k();
/* 446:611 */     field_150585_R.func_150566_k();
/* 447:612 */     field_150578_U.func_150566_k();
/* 448:613 */     extremeHills.func_150566_k();
/* 449:614 */     field_150580_W.func_150566_k();
/* 450:615 */     biomeList[(field_150581_V.biomeID + 128)] = biomeList[(field_150578_U.biomeID + 128)];
/* 451:616 */     BiomeGenBase[] var0 = biomeList;
/* 452:617 */     int var1 = var0.length;
/* 453:619 */     for (int var2 = 0; var2 < var1; var2++)
/* 454:    */     {
/* 455:621 */       BiomeGenBase var3 = var0[var2];
/* 456:623 */       if ((var3 != null) && (var3.biomeID < 128)) {
/* 457:625 */         field_150597_n.add(var3);
/* 458:    */       }
/* 459:    */     }
/* 460:629 */     field_150597_n.remove(hell);
/* 461:630 */     field_150597_n.remove(sky);
/* 462:    */   }
/* 463:    */   
/* 464:631 */   protected static final NoiseGeneratorPerlin field_150605_ac = new NoiseGeneratorPerlin(new Random(1234L), 1);
/* 465:632 */   protected static final NoiseGeneratorPerlin field_150606_ad = new NoiseGeneratorPerlin(new Random(2345L), 1);
/* 466:633 */   protected static final WorldGenDoublePlant field_150610_ae = new WorldGenDoublePlant();
/* 467:    */   public String biomeName;
/* 468:    */   public int color;
/* 469:    */   public int field_150609_ah;
/* 470:    */   public Block topBlock;
/* 471:    */   public int field_150604_aj;
/* 472:    */   public Block fillerBlock;
/* 473:    */   public int field_76754_C;
/* 474:    */   public float minHeight;
/* 475:    */   public float maxHeight;
/* 476:    */   public float temperature;
/* 477:    */   public float rainfall;
/* 478:    */   public int waterColorMultiplier;
/* 479:    */   public BiomeDecorator theBiomeDecorator;
/* 480:    */   protected List spawnableMonsterList;
/* 481:    */   protected List spawnableCreatureList;
/* 482:    */   protected List spawnableWaterCreatureList;
/* 483:    */   protected List spawnableCaveCreatureList;
/* 484:    */   protected boolean enableSnow;
/* 485:    */   protected boolean enableRain;
/* 486:    */   public final int biomeID;
/* 487:    */   protected WorldGenTrees worldGeneratorTrees;
/* 488:    */   protected WorldGenBigTree worldGeneratorBigTree;
/* 489:    */   protected WorldGenSwamp worldGeneratorSwamp;
/* 490:    */   private static final String __OBFID = "CL_00000158";
/* 491:    */   
/* 492:    */   public static class SpawnListEntry
/* 493:    */     extends WeightedRandom.Item
/* 494:    */   {
/* 495:    */     public Class entityClass;
/* 496:    */     public int minGroupCount;
/* 497:    */     public int maxGroupCount;
/* 498:    */     private static final String __OBFID = "CL_00000161";
/* 499:    */     
/* 500:    */     public SpawnListEntry(Class par1Class, int par2, int par3, int par4)
/* 501:    */     {
/* 502:645 */       super();
/* 503:646 */       this.entityClass = par1Class;
/* 504:647 */       this.minGroupCount = par3;
/* 505:648 */       this.maxGroupCount = par4;
/* 506:    */     }
/* 507:    */     
/* 508:    */     public String toString()
/* 509:    */     {
/* 510:653 */       return this.entityClass.getSimpleName() + "*(" + this.minGroupCount + "-" + this.maxGroupCount + "):" + this.itemWeight;
/* 511:    */     }
/* 512:    */   }
/* 513:    */   
/* 514:    */   public static class Height
/* 515:    */   {
/* 516:    */     public float field_150777_a;
/* 517:    */     public float field_150776_b;
/* 518:    */     private static final String __OBFID = "CL_00000159";
/* 519:    */     
/* 520:    */     public Height(float p_i45371_1_, float p_i45371_2_)
/* 521:    */     {
/* 522:665 */       this.field_150777_a = p_i45371_1_;
/* 523:666 */       this.field_150776_b = p_i45371_2_;
/* 524:    */     }
/* 525:    */     
/* 526:    */     public Height func_150775_a()
/* 527:    */     {
/* 528:671 */       return new Height(this.field_150777_a * 0.8F, this.field_150776_b * 0.6F);
/* 529:    */     }
/* 530:    */   }
/* 531:    */   
/* 532:    */   public static enum TempCategory
/* 533:    */   {
/* 534:677 */     OCEAN("OCEAN", 0),  COLD("COLD", 1),  MEDIUM("MEDIUM", 2),  WARM("WARM", 3);
/* 535:    */     
/* 536:682 */     private static final TempCategory[] $VALUES = { OCEAN, COLD, MEDIUM, WARM };
/* 537:    */     private static final String __OBFID = "CL_00000160";
/* 538:    */     
/* 539:    */     private TempCategory(String p_i45372_1_, int p_i45372_2_) {}
/* 540:    */   }
/* 541:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.biome.BiomeGenBase
 * JD-Core Version:    0.7.0.1
 */