/*   1:    */ package net.minecraft.world.biome;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.Random;
/*   6:    */ import net.minecraft.crash.CrashReport;
/*   7:    */ import net.minecraft.crash.CrashReportCategory;
/*   8:    */ import net.minecraft.util.ReportedException;
/*   9:    */ import net.minecraft.world.ChunkPosition;
/*  10:    */ import net.minecraft.world.World;
/*  11:    */ import net.minecraft.world.WorldType;
/*  12:    */ import net.minecraft.world.gen.layer.GenLayer;
/*  13:    */ import net.minecraft.world.gen.layer.IntCache;
/*  14:    */ import net.minecraft.world.storage.WorldInfo;
/*  15:    */ 
/*  16:    */ public class WorldChunkManager
/*  17:    */ {
/*  18:    */   private GenLayer genBiomes;
/*  19:    */   private GenLayer biomeIndexLayer;
/*  20:    */   private BiomeCache biomeCache;
/*  21:    */   private List biomesToSpawnIn;
/*  22:    */   private static final String __OBFID = "CL_00000166";
/*  23:    */   
/*  24:    */   protected WorldChunkManager()
/*  25:    */   {
/*  26: 31 */     this.biomeCache = new BiomeCache(this);
/*  27: 32 */     this.biomesToSpawnIn = new ArrayList();
/*  28: 33 */     this.biomesToSpawnIn.add(BiomeGenBase.forest);
/*  29: 34 */     this.biomesToSpawnIn.add(BiomeGenBase.plains);
/*  30: 35 */     this.biomesToSpawnIn.add(BiomeGenBase.taiga);
/*  31: 36 */     this.biomesToSpawnIn.add(BiomeGenBase.taigaHills);
/*  32: 37 */     this.biomesToSpawnIn.add(BiomeGenBase.forestHills);
/*  33: 38 */     this.biomesToSpawnIn.add(BiomeGenBase.jungle);
/*  34: 39 */     this.biomesToSpawnIn.add(BiomeGenBase.jungleHills);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public WorldChunkManager(long par1, WorldType par3WorldType)
/*  38:    */   {
/*  39: 44 */     this();
/*  40: 45 */     GenLayer[] var4 = GenLayer.initializeAllBiomeGenerators(par1, par3WorldType);
/*  41: 46 */     this.genBiomes = var4[0];
/*  42: 47 */     this.biomeIndexLayer = var4[1];
/*  43:    */   }
/*  44:    */   
/*  45:    */   public WorldChunkManager(World par1World)
/*  46:    */   {
/*  47: 52 */     this(par1World.getSeed(), par1World.getWorldInfo().getTerrainType());
/*  48:    */   }
/*  49:    */   
/*  50:    */   public List getBiomesToSpawnIn()
/*  51:    */   {
/*  52: 60 */     return this.biomesToSpawnIn;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public BiomeGenBase getBiomeGenAt(int par1, int par2)
/*  56:    */   {
/*  57: 68 */     return this.biomeCache.getBiomeGenAt(par1, par2);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public float[] getRainfall(float[] par1ArrayOfFloat, int par2, int par3, int par4, int par5)
/*  61:    */   {
/*  62:    */     
/*  63: 78 */     if ((par1ArrayOfFloat == null) || (par1ArrayOfFloat.length < par4 * par5)) {
/*  64: 80 */       par1ArrayOfFloat = new float[par4 * par5];
/*  65:    */     }
/*  66: 83 */     int[] var6 = this.biomeIndexLayer.getInts(par2, par3, par4, par5);
/*  67: 85 */     for (int var7 = 0; var7 < par4 * par5; var7++) {
/*  68:    */       try
/*  69:    */       {
/*  70: 89 */         float var8 = BiomeGenBase.func_150568_d(var6[var7]).getIntRainfall() / 65536.0F;
/*  71: 91 */         if (var8 > 1.0F) {
/*  72: 93 */           var8 = 1.0F;
/*  73:    */         }
/*  74: 96 */         par1ArrayOfFloat[var7] = var8;
/*  75:    */       }
/*  76:    */       catch (Throwable var11)
/*  77:    */       {
/*  78:100 */         CrashReport var9 = CrashReport.makeCrashReport(var11, "Invalid Biome id");
/*  79:101 */         CrashReportCategory var10 = var9.makeCategory("DownfallBlock");
/*  80:102 */         var10.addCrashSection("biome id", Integer.valueOf(var7));
/*  81:103 */         var10.addCrashSection("downfalls[] size", Integer.valueOf(par1ArrayOfFloat.length));
/*  82:104 */         var10.addCrashSection("x", Integer.valueOf(par2));
/*  83:105 */         var10.addCrashSection("z", Integer.valueOf(par3));
/*  84:106 */         var10.addCrashSection("w", Integer.valueOf(par4));
/*  85:107 */         var10.addCrashSection("h", Integer.valueOf(par5));
/*  86:108 */         throw new ReportedException(var9);
/*  87:    */       }
/*  88:    */     }
/*  89:112 */     return par1ArrayOfFloat;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public float getTemperatureAtHeight(float par1, int par2)
/*  93:    */   {
/*  94:120 */     return par1;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] par1ArrayOfBiomeGenBase, int par2, int par3, int par4, int par5)
/*  98:    */   {
/*  99:    */     
/* 100:130 */     if ((par1ArrayOfBiomeGenBase == null) || (par1ArrayOfBiomeGenBase.length < par4 * par5)) {
/* 101:132 */       par1ArrayOfBiomeGenBase = new BiomeGenBase[par4 * par5];
/* 102:    */     }
/* 103:135 */     int[] var6 = this.genBiomes.getInts(par2, par3, par4, par5);
/* 104:    */     try
/* 105:    */     {
/* 106:139 */       for (int var7 = 0; var7 < par4 * par5; var7++) {
/* 107:141 */         par1ArrayOfBiomeGenBase[var7] = BiomeGenBase.func_150568_d(var6[var7]);
/* 108:    */       }
/* 109:144 */       return par1ArrayOfBiomeGenBase;
/* 110:    */     }
/* 111:    */     catch (Throwable var10)
/* 112:    */     {
/* 113:148 */       CrashReport var8 = CrashReport.makeCrashReport(var10, "Invalid Biome id");
/* 114:149 */       CrashReportCategory var9 = var8.makeCategory("RawBiomeBlock");
/* 115:150 */       var9.addCrashSection("biomes[] size", Integer.valueOf(par1ArrayOfBiomeGenBase.length));
/* 116:151 */       var9.addCrashSection("x", Integer.valueOf(par2));
/* 117:152 */       var9.addCrashSection("z", Integer.valueOf(par3));
/* 118:153 */       var9.addCrashSection("w", Integer.valueOf(par4));
/* 119:154 */       var9.addCrashSection("h", Integer.valueOf(par5));
/* 120:155 */       throw new ReportedException(var8);
/* 121:    */     }
/* 122:    */   }
/* 123:    */   
/* 124:    */   public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] par1ArrayOfBiomeGenBase, int par2, int par3, int par4, int par5)
/* 125:    */   {
/* 126:165 */     return getBiomeGenAt(par1ArrayOfBiomeGenBase, par2, par3, par4, par5, true);
/* 127:    */   }
/* 128:    */   
/* 129:    */   public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] par1ArrayOfBiomeGenBase, int par2, int par3, int par4, int par5, boolean par6)
/* 130:    */   {
/* 131:    */     
/* 132:176 */     if ((par1ArrayOfBiomeGenBase == null) || (par1ArrayOfBiomeGenBase.length < par4 * par5)) {
/* 133:178 */       par1ArrayOfBiomeGenBase = new BiomeGenBase[par4 * par5];
/* 134:    */     }
/* 135:181 */     if ((par6) && (par4 == 16) && (par5 == 16) && ((par2 & 0xF) == 0) && ((par3 & 0xF) == 0))
/* 136:    */     {
/* 137:183 */       BiomeGenBase[] var9 = this.biomeCache.getCachedBiomes(par2, par3);
/* 138:184 */       System.arraycopy(var9, 0, par1ArrayOfBiomeGenBase, 0, par4 * par5);
/* 139:185 */       return par1ArrayOfBiomeGenBase;
/* 140:    */     }
/* 141:189 */     int[] var7 = this.biomeIndexLayer.getInts(par2, par3, par4, par5);
/* 142:191 */     for (int var8 = 0; var8 < par4 * par5; var8++) {
/* 143:193 */       par1ArrayOfBiomeGenBase[var8] = BiomeGenBase.func_150568_d(var7[var8]);
/* 144:    */     }
/* 145:196 */     return par1ArrayOfBiomeGenBase;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public boolean areBiomesViable(int par1, int par2, int par3, List par4List)
/* 149:    */   {
/* 150:205 */     IntCache.resetIntCache();
/* 151:206 */     int var5 = par1 - par3 >> 2;
/* 152:207 */     int var6 = par2 - par3 >> 2;
/* 153:208 */     int var7 = par1 + par3 >> 2;
/* 154:209 */     int var8 = par2 + par3 >> 2;
/* 155:210 */     int var9 = var7 - var5 + 1;
/* 156:211 */     int var10 = var8 - var6 + 1;
/* 157:212 */     int[] var11 = this.genBiomes.getInts(var5, var6, var9, var10);
/* 158:    */     try
/* 159:    */     {
/* 160:216 */       for (int var12 = 0; var12 < var9 * var10; var12++)
/* 161:    */       {
/* 162:218 */         BiomeGenBase var16 = BiomeGenBase.func_150568_d(var11[var12]);
/* 163:220 */         if (!par4List.contains(var16)) {
/* 164:222 */           return false;
/* 165:    */         }
/* 166:    */       }
/* 167:226 */       return true;
/* 168:    */     }
/* 169:    */     catch (Throwable var15)
/* 170:    */     {
/* 171:230 */       CrashReport var13 = CrashReport.makeCrashReport(var15, "Invalid Biome id");
/* 172:231 */       CrashReportCategory var14 = var13.makeCategory("Layer");
/* 173:232 */       var14.addCrashSection("Layer", this.genBiomes.toString());
/* 174:233 */       var14.addCrashSection("x", Integer.valueOf(par1));
/* 175:234 */       var14.addCrashSection("z", Integer.valueOf(par2));
/* 176:235 */       var14.addCrashSection("radius", Integer.valueOf(par3));
/* 177:236 */       var14.addCrashSection("allowed", par4List);
/* 178:237 */       throw new ReportedException(var13);
/* 179:    */     }
/* 180:    */   }
/* 181:    */   
/* 182:    */   public ChunkPosition func_150795_a(int p_150795_1_, int p_150795_2_, int p_150795_3_, List p_150795_4_, Random p_150795_5_)
/* 183:    */   {
/* 184:243 */     IntCache.resetIntCache();
/* 185:244 */     int var6 = p_150795_1_ - p_150795_3_ >> 2;
/* 186:245 */     int var7 = p_150795_2_ - p_150795_3_ >> 2;
/* 187:246 */     int var8 = p_150795_1_ + p_150795_3_ >> 2;
/* 188:247 */     int var9 = p_150795_2_ + p_150795_3_ >> 2;
/* 189:248 */     int var10 = var8 - var6 + 1;
/* 190:249 */     int var11 = var9 - var7 + 1;
/* 191:250 */     int[] var12 = this.genBiomes.getInts(var6, var7, var10, var11);
/* 192:251 */     ChunkPosition var13 = null;
/* 193:252 */     int var14 = 0;
/* 194:254 */     for (int var15 = 0; var15 < var10 * var11; var15++)
/* 195:    */     {
/* 196:256 */       int var16 = var6 + var15 % var10 << 2;
/* 197:257 */       int var17 = var7 + var15 / var10 << 2;
/* 198:258 */       BiomeGenBase var18 = BiomeGenBase.func_150568_d(var12[var15]);
/* 199:260 */       if ((p_150795_4_.contains(var18)) && ((var13 == null) || (p_150795_5_.nextInt(var14 + 1) == 0)))
/* 200:    */       {
/* 201:262 */         var13 = new ChunkPosition(var16, 0, var17);
/* 202:263 */         var14++;
/* 203:    */       }
/* 204:    */     }
/* 205:267 */     return var13;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void cleanupCache()
/* 209:    */   {
/* 210:275 */     this.biomeCache.cleanupCache();
/* 211:    */   }
/* 212:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.biome.WorldChunkManager
 * JD-Core Version:    0.7.0.1
 */