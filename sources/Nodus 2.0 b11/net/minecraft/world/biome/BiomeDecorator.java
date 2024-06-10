/*   1:    */ package net.minecraft.world.biome;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.BlockFlower;
/*   5:    */ import net.minecraft.block.material.Material;
/*   6:    */ import net.minecraft.init.Blocks;
/*   7:    */ import net.minecraft.world.World;
/*   8:    */ import net.minecraft.world.gen.feature.WorldGenAbstractTree;
/*   9:    */ import net.minecraft.world.gen.feature.WorldGenBigMushroom;
/*  10:    */ import net.minecraft.world.gen.feature.WorldGenCactus;
/*  11:    */ import net.minecraft.world.gen.feature.WorldGenClay;
/*  12:    */ import net.minecraft.world.gen.feature.WorldGenDeadBush;
/*  13:    */ import net.minecraft.world.gen.feature.WorldGenFlowers;
/*  14:    */ import net.minecraft.world.gen.feature.WorldGenLiquids;
/*  15:    */ import net.minecraft.world.gen.feature.WorldGenMinable;
/*  16:    */ import net.minecraft.world.gen.feature.WorldGenPumpkin;
/*  17:    */ import net.minecraft.world.gen.feature.WorldGenReed;
/*  18:    */ import net.minecraft.world.gen.feature.WorldGenSand;
/*  19:    */ import net.minecraft.world.gen.feature.WorldGenWaterlily;
/*  20:    */ import net.minecraft.world.gen.feature.WorldGenerator;
/*  21:    */ 
/*  22:    */ public class BiomeDecorator
/*  23:    */ {
/*  24:    */   protected World currentWorld;
/*  25:    */   protected Random randomGenerator;
/*  26:    */   protected int chunk_X;
/*  27:    */   protected int chunk_Z;
/*  28: 37 */   protected WorldGenerator clayGen = new WorldGenClay(4);
/*  29:    */   protected WorldGenerator sandGen;
/*  30:    */   protected WorldGenerator gravelAsSandGen;
/*  31:    */   protected WorldGenerator dirtGen;
/*  32:    */   protected WorldGenerator gravelGen;
/*  33:    */   protected WorldGenerator coalGen;
/*  34:    */   protected WorldGenerator ironGen;
/*  35:    */   protected WorldGenerator goldGen;
/*  36:    */   protected WorldGenerator redstoneGen;
/*  37:    */   protected WorldGenerator diamondGen;
/*  38:    */   protected WorldGenerator lapisGen;
/*  39:    */   protected WorldGenFlowers field_150514_p;
/*  40:    */   protected WorldGenerator mushroomBrownGen;
/*  41:    */   protected WorldGenerator mushroomRedGen;
/*  42:    */   protected WorldGenerator bigMushroomGen;
/*  43:    */   protected WorldGenerator reedGen;
/*  44:    */   protected WorldGenerator cactusGen;
/*  45:    */   protected WorldGenerator waterlilyGen;
/*  46:    */   protected int waterlilyPerChunk;
/*  47:    */   protected int treesPerChunk;
/*  48:    */   protected int flowersPerChunk;
/*  49:    */   protected int grassPerChunk;
/*  50:    */   protected int deadBushPerChunk;
/*  51:    */   protected int mushroomsPerChunk;
/*  52:    */   protected int reedsPerChunk;
/*  53:    */   protected int cactiPerChunk;
/*  54:    */   protected int sandPerChunk;
/*  55:    */   protected int sandPerChunk2;
/*  56:    */   protected int clayPerChunk;
/*  57:    */   protected int bigMushroomsPerChunk;
/*  58:    */   public boolean generateLakes;
/*  59:    */   private static final String __OBFID = "CL_00000164";
/*  60:    */   
/*  61:    */   public BiomeDecorator()
/*  62:    */   {
/*  63:145 */     this.sandGen = new WorldGenSand(Blocks.sand, 7);
/*  64:146 */     this.gravelAsSandGen = new WorldGenSand(Blocks.gravel, 6);
/*  65:147 */     this.dirtGen = new WorldGenMinable(Blocks.dirt, 32);
/*  66:148 */     this.gravelGen = new WorldGenMinable(Blocks.gravel, 32);
/*  67:149 */     this.coalGen = new WorldGenMinable(Blocks.coal_ore, 16);
/*  68:150 */     this.ironGen = new WorldGenMinable(Blocks.iron_ore, 8);
/*  69:151 */     this.goldGen = new WorldGenMinable(Blocks.gold_ore, 8);
/*  70:152 */     this.redstoneGen = new WorldGenMinable(Blocks.redstone_ore, 7);
/*  71:153 */     this.diamondGen = new WorldGenMinable(Blocks.diamond_ore, 7);
/*  72:154 */     this.lapisGen = new WorldGenMinable(Blocks.lapis_ore, 6);
/*  73:155 */     this.field_150514_p = new WorldGenFlowers(Blocks.yellow_flower);
/*  74:156 */     this.mushroomBrownGen = new WorldGenFlowers(Blocks.brown_mushroom);
/*  75:157 */     this.mushroomRedGen = new WorldGenFlowers(Blocks.red_mushroom);
/*  76:158 */     this.bigMushroomGen = new WorldGenBigMushroom();
/*  77:159 */     this.reedGen = new WorldGenReed();
/*  78:160 */     this.cactusGen = new WorldGenCactus();
/*  79:161 */     this.waterlilyGen = new WorldGenWaterlily();
/*  80:162 */     this.flowersPerChunk = 2;
/*  81:163 */     this.grassPerChunk = 1;
/*  82:164 */     this.sandPerChunk = 1;
/*  83:165 */     this.sandPerChunk2 = 3;
/*  84:166 */     this.clayPerChunk = 1;
/*  85:167 */     this.generateLakes = true;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void func_150512_a(World p_150512_1_, Random p_150512_2_, BiomeGenBase p_150512_3_, int p_150512_4_, int p_150512_5_)
/*  89:    */   {
/*  90:172 */     if (this.currentWorld != null) {
/*  91:174 */       throw new RuntimeException("Already decorating!!");
/*  92:    */     }
/*  93:178 */     this.currentWorld = p_150512_1_;
/*  94:179 */     this.randomGenerator = p_150512_2_;
/*  95:180 */     this.chunk_X = p_150512_4_;
/*  96:181 */     this.chunk_Z = p_150512_5_;
/*  97:182 */     func_150513_a(p_150512_3_);
/*  98:183 */     this.currentWorld = null;
/*  99:184 */     this.randomGenerator = null;
/* 100:    */   }
/* 101:    */   
/* 102:    */   protected void func_150513_a(BiomeGenBase p_150513_1_)
/* 103:    */   {
/* 104:190 */     generateOres();
/* 105:195 */     for (int var2 = 0; var2 < this.sandPerChunk2; var2++)
/* 106:    */     {
/* 107:197 */       int var3 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
/* 108:198 */       int var4 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
/* 109:199 */       this.sandGen.generate(this.currentWorld, this.randomGenerator, var3, this.currentWorld.getTopSolidOrLiquidBlock(var3, var4), var4);
/* 110:    */     }
/* 111:202 */     for (var2 = 0; var2 < this.clayPerChunk; var2++)
/* 112:    */     {
/* 113:204 */       int var3 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
/* 114:205 */       int var4 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
/* 115:206 */       this.clayGen.generate(this.currentWorld, this.randomGenerator, var3, this.currentWorld.getTopSolidOrLiquidBlock(var3, var4), var4);
/* 116:    */     }
/* 117:209 */     for (var2 = 0; var2 < this.sandPerChunk; var2++)
/* 118:    */     {
/* 119:211 */       int var3 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
/* 120:212 */       int var4 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
/* 121:213 */       this.gravelAsSandGen.generate(this.currentWorld, this.randomGenerator, var3, this.currentWorld.getTopSolidOrLiquidBlock(var3, var4), var4);
/* 122:    */     }
/* 123:216 */     var2 = this.treesPerChunk;
/* 124:218 */     if (this.randomGenerator.nextInt(10) == 0) {
/* 125:220 */       var2++;
/* 126:    */     }
/* 127:226 */     for (int var3 = 0; var3 < var2; var3++)
/* 128:    */     {
/* 129:228 */       int var4 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
/* 130:229 */       int var5 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
/* 131:230 */       int var6 = this.currentWorld.getHeightValue(var4, var5);
/* 132:231 */       WorldGenAbstractTree var7 = p_150513_1_.func_150567_a(this.randomGenerator);
/* 133:232 */       var7.setScale(1.0D, 1.0D, 1.0D);
/* 134:234 */       if (var7.generate(this.currentWorld, this.randomGenerator, var4, var6, var5)) {
/* 135:236 */         var7.func_150524_b(this.currentWorld, this.randomGenerator, var4, var6, var5);
/* 136:    */       }
/* 137:    */     }
/* 138:240 */     for (var3 = 0; var3 < this.bigMushroomsPerChunk; var3++)
/* 139:    */     {
/* 140:242 */       int var4 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
/* 141:243 */       int var5 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
/* 142:244 */       this.bigMushroomGen.generate(this.currentWorld, this.randomGenerator, var4, this.currentWorld.getHeightValue(var4, var5), var5);
/* 143:    */     }
/* 144:247 */     for (var3 = 0; var3 < this.flowersPerChunk; var3++)
/* 145:    */     {
/* 146:249 */       int var4 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
/* 147:250 */       int var5 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
/* 148:251 */       int var6 = this.randomGenerator.nextInt(this.currentWorld.getHeightValue(var4, var5) + 32);
/* 149:252 */       String var9 = p_150513_1_.func_150572_a(this.randomGenerator, var4, var6, var5);
/* 150:253 */       BlockFlower var8 = BlockFlower.func_149857_e(var9);
/* 151:255 */       if (var8.getMaterial() != Material.air)
/* 152:    */       {
/* 153:257 */         this.field_150514_p.func_150550_a(var8, BlockFlower.func_149856_f(var9));
/* 154:258 */         this.field_150514_p.generate(this.currentWorld, this.randomGenerator, var4, var6, var5);
/* 155:    */       }
/* 156:    */     }
/* 157:262 */     for (var3 = 0; var3 < this.grassPerChunk; var3++)
/* 158:    */     {
/* 159:264 */       int var4 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
/* 160:265 */       int var5 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
/* 161:266 */       int var6 = this.randomGenerator.nextInt(this.currentWorld.getHeightValue(var4, var5) * 2);
/* 162:267 */       WorldGenerator var10 = p_150513_1_.getRandomWorldGenForGrass(this.randomGenerator);
/* 163:268 */       var10.generate(this.currentWorld, this.randomGenerator, var4, var6, var5);
/* 164:    */     }
/* 165:271 */     for (var3 = 0; var3 < this.deadBushPerChunk; var3++)
/* 166:    */     {
/* 167:273 */       int var4 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
/* 168:274 */       int var5 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
/* 169:275 */       int var6 = this.randomGenerator.nextInt(this.currentWorld.getHeightValue(var4, var5) * 2);
/* 170:276 */       new WorldGenDeadBush(Blocks.deadbush).generate(this.currentWorld, this.randomGenerator, var4, var6, var5);
/* 171:    */     }
/* 172:279 */     for (var3 = 0; var3 < this.waterlilyPerChunk; var3++)
/* 173:    */     {
/* 174:281 */       int var4 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
/* 175:282 */       int var5 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
/* 176:284 */       for (int var6 = this.randomGenerator.nextInt(this.currentWorld.getHeightValue(var4, var5) * 2); (var6 > 0) && (this.currentWorld.isAirBlock(var4, var6 - 1, var5)); var6--) {}
/* 177:289 */       this.waterlilyGen.generate(this.currentWorld, this.randomGenerator, var4, var6, var5);
/* 178:    */     }
/* 179:292 */     for (var3 = 0; var3 < this.mushroomsPerChunk; var3++)
/* 180:    */     {
/* 181:294 */       if (this.randomGenerator.nextInt(4) == 0)
/* 182:    */       {
/* 183:296 */         int var4 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
/* 184:297 */         int var5 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
/* 185:298 */         int var6 = this.currentWorld.getHeightValue(var4, var5);
/* 186:299 */         this.mushroomBrownGen.generate(this.currentWorld, this.randomGenerator, var4, var6, var5);
/* 187:    */       }
/* 188:302 */       if (this.randomGenerator.nextInt(8) == 0)
/* 189:    */       {
/* 190:304 */         int var4 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
/* 191:305 */         int var5 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
/* 192:306 */         int var6 = this.randomGenerator.nextInt(this.currentWorld.getHeightValue(var4, var5) * 2);
/* 193:307 */         this.mushroomRedGen.generate(this.currentWorld, this.randomGenerator, var4, var6, var5);
/* 194:    */       }
/* 195:    */     }
/* 196:311 */     if (this.randomGenerator.nextInt(4) == 0)
/* 197:    */     {
/* 198:313 */       var3 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
/* 199:314 */       int var4 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
/* 200:315 */       int var5 = this.randomGenerator.nextInt(this.currentWorld.getHeightValue(var3, var4) * 2);
/* 201:316 */       this.mushroomBrownGen.generate(this.currentWorld, this.randomGenerator, var3, var5, var4);
/* 202:    */     }
/* 203:319 */     if (this.randomGenerator.nextInt(8) == 0)
/* 204:    */     {
/* 205:321 */       var3 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
/* 206:322 */       int var4 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
/* 207:323 */       int var5 = this.randomGenerator.nextInt(this.currentWorld.getHeightValue(var3, var4) * 2);
/* 208:324 */       this.mushroomRedGen.generate(this.currentWorld, this.randomGenerator, var3, var5, var4);
/* 209:    */     }
/* 210:327 */     for (var3 = 0; var3 < this.reedsPerChunk; var3++)
/* 211:    */     {
/* 212:329 */       int var4 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
/* 213:330 */       int var5 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
/* 214:331 */       int var6 = this.randomGenerator.nextInt(this.currentWorld.getHeightValue(var4, var5) * 2);
/* 215:332 */       this.reedGen.generate(this.currentWorld, this.randomGenerator, var4, var6, var5);
/* 216:    */     }
/* 217:335 */     for (var3 = 0; var3 < 10; var3++)
/* 218:    */     {
/* 219:337 */       int var4 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
/* 220:338 */       int var5 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
/* 221:339 */       int var6 = this.randomGenerator.nextInt(this.currentWorld.getHeightValue(var4, var5) * 2);
/* 222:340 */       this.reedGen.generate(this.currentWorld, this.randomGenerator, var4, var6, var5);
/* 223:    */     }
/* 224:343 */     if (this.randomGenerator.nextInt(32) == 0)
/* 225:    */     {
/* 226:345 */       var3 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
/* 227:346 */       int var4 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
/* 228:347 */       int var5 = this.randomGenerator.nextInt(this.currentWorld.getHeightValue(var3, var4) * 2);
/* 229:348 */       new WorldGenPumpkin().generate(this.currentWorld, this.randomGenerator, var3, var5, var4);
/* 230:    */     }
/* 231:351 */     for (var3 = 0; var3 < this.cactiPerChunk; var3++)
/* 232:    */     {
/* 233:353 */       int var4 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
/* 234:354 */       int var5 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
/* 235:355 */       int var6 = this.randomGenerator.nextInt(this.currentWorld.getHeightValue(var4, var5) * 2);
/* 236:356 */       this.cactusGen.generate(this.currentWorld, this.randomGenerator, var4, var6, var5);
/* 237:    */     }
/* 238:359 */     if (this.generateLakes)
/* 239:    */     {
/* 240:361 */       for (var3 = 0; var3 < 50; var3++)
/* 241:    */       {
/* 242:363 */         int var4 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
/* 243:364 */         int var5 = this.randomGenerator.nextInt(this.randomGenerator.nextInt(248) + 8);
/* 244:365 */         int var6 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
/* 245:366 */         new WorldGenLiquids(Blocks.flowing_water).generate(this.currentWorld, this.randomGenerator, var4, var5, var6);
/* 246:    */       }
/* 247:369 */       for (var3 = 0; var3 < 20; var3++)
/* 248:    */       {
/* 249:371 */         int var4 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
/* 250:372 */         int var5 = this.randomGenerator.nextInt(this.randomGenerator.nextInt(this.randomGenerator.nextInt(240) + 8) + 8);
/* 251:373 */         int var6 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
/* 252:374 */         new WorldGenLiquids(Blocks.flowing_lava).generate(this.currentWorld, this.randomGenerator, var4, var5, var6);
/* 253:    */       }
/* 254:    */     }
/* 255:    */   }
/* 256:    */   
/* 257:    */   protected void genStandardOre1(int par1, WorldGenerator par2WorldGenerator, int par3, int par4)
/* 258:    */   {
/* 259:384 */     for (int var5 = 0; var5 < par1; var5++)
/* 260:    */     {
/* 261:386 */       int var6 = this.chunk_X + this.randomGenerator.nextInt(16);
/* 262:387 */       int var7 = this.randomGenerator.nextInt(par4 - par3) + par3;
/* 263:388 */       int var8 = this.chunk_Z + this.randomGenerator.nextInt(16);
/* 264:389 */       par2WorldGenerator.generate(this.currentWorld, this.randomGenerator, var6, var7, var8);
/* 265:    */     }
/* 266:    */   }
/* 267:    */   
/* 268:    */   protected void genStandardOre2(int par1, WorldGenerator par2WorldGenerator, int par3, int par4)
/* 269:    */   {
/* 270:398 */     for (int var5 = 0; var5 < par1; var5++)
/* 271:    */     {
/* 272:400 */       int var6 = this.chunk_X + this.randomGenerator.nextInt(16);
/* 273:401 */       int var7 = this.randomGenerator.nextInt(par4) + this.randomGenerator.nextInt(par4) + (par3 - par4);
/* 274:402 */       int var8 = this.chunk_Z + this.randomGenerator.nextInt(16);
/* 275:403 */       par2WorldGenerator.generate(this.currentWorld, this.randomGenerator, var6, var7, var8);
/* 276:    */     }
/* 277:    */   }
/* 278:    */   
/* 279:    */   protected void generateOres()
/* 280:    */   {
/* 281:412 */     genStandardOre1(20, this.dirtGen, 0, 256);
/* 282:413 */     genStandardOre1(10, this.gravelGen, 0, 256);
/* 283:414 */     genStandardOre1(20, this.coalGen, 0, 128);
/* 284:415 */     genStandardOre1(20, this.ironGen, 0, 64);
/* 285:416 */     genStandardOre1(2, this.goldGen, 0, 32);
/* 286:417 */     genStandardOre1(8, this.redstoneGen, 0, 16);
/* 287:418 */     genStandardOre1(1, this.diamondGen, 0, 16);
/* 288:419 */     genStandardOre2(1, this.lapisGen, 16, 16);
/* 289:    */   }
/* 290:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.biome.BiomeDecorator
 * JD-Core Version:    0.7.0.1
 */