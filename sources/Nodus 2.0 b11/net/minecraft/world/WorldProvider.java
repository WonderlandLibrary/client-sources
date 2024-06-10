/*   1:    */ package net.minecraft.world;
/*   2:    */ 
/*   3:    */ import net.minecraft.init.Blocks;
/*   4:    */ import net.minecraft.util.ChunkCoordinates;
/*   5:    */ import net.minecraft.util.MathHelper;
/*   6:    */ import net.minecraft.util.Vec3;
/*   7:    */ import net.minecraft.util.Vec3Pool;
/*   8:    */ import net.minecraft.world.biome.BiomeGenBase;
/*   9:    */ import net.minecraft.world.biome.WorldChunkManager;
/*  10:    */ import net.minecraft.world.biome.WorldChunkManagerHell;
/*  11:    */ import net.minecraft.world.chunk.IChunkProvider;
/*  12:    */ import net.minecraft.world.gen.ChunkProviderFlat;
/*  13:    */ import net.minecraft.world.gen.ChunkProviderGenerate;
/*  14:    */ import net.minecraft.world.gen.FlatGeneratorInfo;
/*  15:    */ import net.minecraft.world.storage.WorldInfo;
/*  16:    */ 
/*  17:    */ public abstract class WorldProvider
/*  18:    */ {
/*  19: 17 */   public static final float[] moonPhaseFactors = { 1.0F, 0.75F, 0.5F, 0.25F, 0.0F, 0.25F, 0.5F, 0.75F };
/*  20:    */   public World worldObj;
/*  21:    */   public WorldType terrainType;
/*  22:    */   public String field_82913_c;
/*  23:    */   public WorldChunkManager worldChunkMgr;
/*  24:    */   public boolean isHellWorld;
/*  25:    */   public boolean hasNoSky;
/*  26: 38 */   public float[] lightBrightnessTable = new float[16];
/*  27:    */   public int dimensionId;
/*  28: 44 */   private float[] colorsSunriseSunset = new float[4];
/*  29:    */   private static final String __OBFID = "CL_00000386";
/*  30:    */   
/*  31:    */   public final void registerWorld(World par1World)
/*  32:    */   {
/*  33: 52 */     this.worldObj = par1World;
/*  34: 53 */     this.terrainType = par1World.getWorldInfo().getTerrainType();
/*  35: 54 */     this.field_82913_c = par1World.getWorldInfo().getGeneratorOptions();
/*  36: 55 */     registerWorldChunkManager();
/*  37: 56 */     generateLightBrightnessTable();
/*  38:    */   }
/*  39:    */   
/*  40:    */   protected void generateLightBrightnessTable()
/*  41:    */   {
/*  42: 64 */     float var1 = 0.0F;
/*  43: 66 */     for (int var2 = 0; var2 <= 15; var2++)
/*  44:    */     {
/*  45: 68 */       float var3 = 1.0F - var2 / 15.0F;
/*  46: 69 */       this.lightBrightnessTable[var2] = ((1.0F - var3) / (var3 * 3.0F + 1.0F) * (1.0F - var1) + var1);
/*  47:    */     }
/*  48:    */   }
/*  49:    */   
/*  50:    */   protected void registerWorldChunkManager()
/*  51:    */   {
/*  52: 78 */     if (this.worldObj.getWorldInfo().getTerrainType() == WorldType.FLAT)
/*  53:    */     {
/*  54: 80 */       FlatGeneratorInfo var1 = FlatGeneratorInfo.createFlatGeneratorFromString(this.worldObj.getWorldInfo().getGeneratorOptions());
/*  55: 81 */       this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.func_150568_d(var1.getBiome()), 0.5F);
/*  56:    */     }
/*  57:    */     else
/*  58:    */     {
/*  59: 85 */       this.worldChunkMgr = new WorldChunkManager(this.worldObj);
/*  60:    */     }
/*  61:    */   }
/*  62:    */   
/*  63:    */   public IChunkProvider createChunkGenerator()
/*  64:    */   {
/*  65: 94 */     return this.terrainType == WorldType.FLAT ? new ChunkProviderFlat(this.worldObj, this.worldObj.getSeed(), this.worldObj.getWorldInfo().isMapFeaturesEnabled(), this.field_82913_c) : new ChunkProviderGenerate(this.worldObj, this.worldObj.getSeed(), this.worldObj.getWorldInfo().isMapFeaturesEnabled());
/*  66:    */   }
/*  67:    */   
/*  68:    */   public boolean canCoordinateBeSpawn(int par1, int par2)
/*  69:    */   {
/*  70:102 */     return this.worldObj.getTopBlock(par1, par2) == Blocks.grass;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public float calculateCelestialAngle(long par1, float par3)
/*  74:    */   {
/*  75:110 */     int var4 = (int)(par1 % 24000L);
/*  76:111 */     float var5 = (var4 + par3) / 24000.0F - 0.25F;
/*  77:113 */     if (var5 < 0.0F) {
/*  78:115 */       var5 += 1.0F;
/*  79:    */     }
/*  80:118 */     if (var5 > 1.0F) {
/*  81:120 */       var5 -= 1.0F;
/*  82:    */     }
/*  83:123 */     float var6 = var5;
/*  84:124 */     var5 = 1.0F - (float)((Math.cos(var5 * 3.141592653589793D) + 1.0D) / 2.0D);
/*  85:125 */     var5 = var6 + (var5 - var6) / 3.0F;
/*  86:126 */     return var5;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public int getMoonPhase(long par1)
/*  90:    */   {
/*  91:131 */     return (int)(par1 / 24000L % 8L + 8L) % 8;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public boolean isSurfaceWorld()
/*  95:    */   {
/*  96:139 */     return true;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public float[] calcSunriseSunsetColors(float par1, float par2)
/* 100:    */   {
/* 101:147 */     float var3 = 0.4F;
/* 102:148 */     float var4 = MathHelper.cos(par1 * 3.141593F * 2.0F) - 0.0F;
/* 103:149 */     float var5 = -0.0F;
/* 104:151 */     if ((var4 >= var5 - var3) && (var4 <= var5 + var3))
/* 105:    */     {
/* 106:153 */       float var6 = (var4 - var5) / var3 * 0.5F + 0.5F;
/* 107:154 */       float var7 = 1.0F - (1.0F - MathHelper.sin(var6 * 3.141593F)) * 0.99F;
/* 108:155 */       var7 *= var7;
/* 109:156 */       this.colorsSunriseSunset[0] = (var6 * 0.3F + 0.7F);
/* 110:157 */       this.colorsSunriseSunset[1] = (var6 * var6 * 0.7F + 0.2F);
/* 111:158 */       this.colorsSunriseSunset[2] = (var6 * var6 * 0.0F + 0.2F);
/* 112:159 */       this.colorsSunriseSunset[3] = var7;
/* 113:160 */       return this.colorsSunriseSunset;
/* 114:    */     }
/* 115:164 */     return null;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public Vec3 getFogColor(float par1, float par2)
/* 119:    */   {
/* 120:173 */     float var3 = MathHelper.cos(par1 * 3.141593F * 2.0F) * 2.0F + 0.5F;
/* 121:175 */     if (var3 < 0.0F) {
/* 122:177 */       var3 = 0.0F;
/* 123:    */     }
/* 124:180 */     if (var3 > 1.0F) {
/* 125:182 */       var3 = 1.0F;
/* 126:    */     }
/* 127:185 */     float var4 = 0.7529412F;
/* 128:186 */     float var5 = 0.8470588F;
/* 129:187 */     float var6 = 1.0F;
/* 130:188 */     var4 *= (var3 * 0.94F + 0.06F);
/* 131:189 */     var5 *= (var3 * 0.94F + 0.06F);
/* 132:190 */     var6 *= (var3 * 0.91F + 0.09F);
/* 133:191 */     return this.worldObj.getWorldVec3Pool().getVecFromPool(var4, var5, var6);
/* 134:    */   }
/* 135:    */   
/* 136:    */   public boolean canRespawnHere()
/* 137:    */   {
/* 138:199 */     return true;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public static WorldProvider getProviderForDimension(int par0)
/* 142:    */   {
/* 143:204 */     return par0 == 1 ? new WorldProviderEnd() : par0 == 0 ? new WorldProviderSurface() : par0 == -1 ? new WorldProviderHell() : null;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public float getCloudHeight()
/* 147:    */   {
/* 148:212 */     return 128.0F;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public boolean isSkyColored()
/* 152:    */   {
/* 153:217 */     return true;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public ChunkCoordinates getEntrancePortalLocation()
/* 157:    */   {
/* 158:225 */     return null;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public int getAverageGroundLevel()
/* 162:    */   {
/* 163:230 */     return this.terrainType == WorldType.FLAT ? 4 : 64;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public boolean getWorldHasVoidParticles()
/* 167:    */   {
/* 168:239 */     return (this.terrainType != WorldType.FLAT) && (!this.hasNoSky);
/* 169:    */   }
/* 170:    */   
/* 171:    */   public double getVoidFogYFactor()
/* 172:    */   {
/* 173:249 */     return this.terrainType == WorldType.FLAT ? 1.0D : 0.03125D;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public boolean doesXZShowFog(int par1, int par2)
/* 177:    */   {
/* 178:257 */     return false;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public abstract String getDimensionName();
/* 182:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.WorldProvider
 * JD-Core Version:    0.7.0.1
 */