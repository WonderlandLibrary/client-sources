/*   1:    */ package net.minecraft.world.gen;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Map;
/*   7:    */ import java.util.Random;
/*   8:    */ import net.minecraft.block.Block;
/*   9:    */ import net.minecraft.entity.EnumCreatureType;
/*  10:    */ import net.minecraft.init.Blocks;
/*  11:    */ import net.minecraft.util.IProgressUpdate;
/*  12:    */ import net.minecraft.world.ChunkPosition;
/*  13:    */ import net.minecraft.world.World;
/*  14:    */ import net.minecraft.world.WorldProvider;
/*  15:    */ import net.minecraft.world.biome.BiomeGenBase;
/*  16:    */ import net.minecraft.world.biome.WorldChunkManager;
/*  17:    */ import net.minecraft.world.chunk.Chunk;
/*  18:    */ import net.minecraft.world.chunk.IChunkProvider;
/*  19:    */ import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
/*  20:    */ import net.minecraft.world.gen.feature.WorldGenDungeons;
/*  21:    */ import net.minecraft.world.gen.feature.WorldGenLakes;
/*  22:    */ import net.minecraft.world.gen.structure.MapGenMineshaft;
/*  23:    */ import net.minecraft.world.gen.structure.MapGenScatteredFeature;
/*  24:    */ import net.minecraft.world.gen.structure.MapGenStronghold;
/*  25:    */ import net.minecraft.world.gen.structure.MapGenStructure;
/*  26:    */ import net.minecraft.world.gen.structure.MapGenVillage;
/*  27:    */ 
/*  28:    */ public class ChunkProviderFlat
/*  29:    */   implements IChunkProvider
/*  30:    */ {
/*  31:    */   private World worldObj;
/*  32:    */   private Random random;
/*  33: 30 */   private final Block[] cachedBlockIDs = new Block[256];
/*  34: 31 */   private final byte[] cachedBlockMetadata = new byte[256];
/*  35:    */   private final FlatGeneratorInfo flatWorldGenInfo;
/*  36: 33 */   private final List structureGenerators = new ArrayList();
/*  37:    */   private final boolean hasDecoration;
/*  38:    */   private final boolean hasDungeons;
/*  39:    */   private WorldGenLakes waterLakeGenerator;
/*  40:    */   private WorldGenLakes lavaLakeGenerator;
/*  41:    */   private static final String __OBFID = "CL_00000391";
/*  42:    */   
/*  43:    */   public ChunkProviderFlat(World par1World, long par2, boolean par4, String par5Str)
/*  44:    */   {
/*  45: 42 */     this.worldObj = par1World;
/*  46: 43 */     this.random = new Random(par2);
/*  47: 44 */     this.flatWorldGenInfo = FlatGeneratorInfo.createFlatGeneratorFromString(par5Str);
/*  48: 46 */     if (par4)
/*  49:    */     {
/*  50: 48 */       Map var6 = this.flatWorldGenInfo.getWorldFeatures();
/*  51: 50 */       if (var6.containsKey("village"))
/*  52:    */       {
/*  53: 52 */         Map var7 = (Map)var6.get("village");
/*  54: 54 */         if (!var7.containsKey("size")) {
/*  55: 56 */           var7.put("size", "1");
/*  56:    */         }
/*  57: 59 */         this.structureGenerators.add(new MapGenVillage(var7));
/*  58:    */       }
/*  59: 62 */       if (var6.containsKey("biome_1")) {
/*  60: 64 */         this.structureGenerators.add(new MapGenScatteredFeature((Map)var6.get("biome_1")));
/*  61:    */       }
/*  62: 67 */       if (var6.containsKey("mineshaft")) {
/*  63: 69 */         this.structureGenerators.add(new MapGenMineshaft((Map)var6.get("mineshaft")));
/*  64:    */       }
/*  65: 72 */       if (var6.containsKey("stronghold")) {
/*  66: 74 */         this.structureGenerators.add(new MapGenStronghold((Map)var6.get("stronghold")));
/*  67:    */       }
/*  68:    */     }
/*  69: 78 */     this.hasDecoration = this.flatWorldGenInfo.getWorldFeatures().containsKey("decoration");
/*  70: 80 */     if (this.flatWorldGenInfo.getWorldFeatures().containsKey("lake")) {
/*  71: 82 */       this.waterLakeGenerator = new WorldGenLakes(Blocks.water);
/*  72:    */     }
/*  73: 85 */     if (this.flatWorldGenInfo.getWorldFeatures().containsKey("lava_lake")) {
/*  74: 87 */       this.lavaLakeGenerator = new WorldGenLakes(Blocks.lava);
/*  75:    */     }
/*  76: 90 */     this.hasDungeons = this.flatWorldGenInfo.getWorldFeatures().containsKey("dungeon");
/*  77: 91 */     Iterator var9 = this.flatWorldGenInfo.getFlatLayers().iterator();
/*  78:    */     FlatLayerInfo var10;
/*  79:    */     int var8;
/*  80: 93 */     for (; var9.hasNext(); var8 < var10.getMinY() + var10.getLayerCount())
/*  81:    */     {
/*  82: 95 */       var10 = (FlatLayerInfo)var9.next();
/*  83:    */       
/*  84: 97 */       var8 = var10.getMinY(); continue;
/*  85:    */       
/*  86: 99 */       this.cachedBlockIDs[var8] = var10.func_151536_b();
/*  87:100 */       this.cachedBlockMetadata[var8] = ((byte)var10.getFillBlockMeta());var8++;
/*  88:    */     }
/*  89:    */   }
/*  90:    */   
/*  91:    */   public Chunk loadChunk(int par1, int par2)
/*  92:    */   {
/*  93:110 */     return provideChunk(par1, par2);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public Chunk provideChunk(int par1, int par2)
/*  97:    */   {
/*  98:119 */     Chunk var3 = new Chunk(this.worldObj, par1, par2);
/*  99:122 */     for (int var4 = 0; var4 < this.cachedBlockIDs.length; var4++)
/* 100:    */     {
/* 101:124 */       Block var5 = this.cachedBlockIDs[var4];
/* 102:126 */       if (var5 != null)
/* 103:    */       {
/* 104:128 */         int var6 = var4 >> 4;
/* 105:129 */         ExtendedBlockStorage var7 = var3.getBlockStorageArray()[var6];
/* 106:131 */         if (var7 == null)
/* 107:    */         {
/* 108:133 */           var7 = new ExtendedBlockStorage(var4, !this.worldObj.provider.hasNoSky);
/* 109:134 */           var3.getBlockStorageArray()[var6] = var7;
/* 110:    */         }
/* 111:137 */         for (int var8 = 0; var8 < 16; var8++) {
/* 112:139 */           for (int var9 = 0; var9 < 16; var9++)
/* 113:    */           {
/* 114:141 */             var7.func_150818_a(var8, var4 & 0xF, var9, var5);
/* 115:142 */             var7.setExtBlockMetadata(var8, var4 & 0xF, var9, this.cachedBlockMetadata[var4]);
/* 116:    */           }
/* 117:    */         }
/* 118:    */       }
/* 119:    */     }
/* 120:148 */     var3.generateSkylightMap();
/* 121:149 */     BiomeGenBase[] var10 = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(null, par1 * 16, par2 * 16, 16, 16);
/* 122:150 */     byte[] var11 = var3.getBiomeArray();
/* 123:152 */     for (int var6 = 0; var6 < var11.length; var6++) {
/* 124:154 */       var11[var6] = ((byte)var10[var6].biomeID);
/* 125:    */     }
/* 126:157 */     Iterator var12 = this.structureGenerators.iterator();
/* 127:159 */     while (var12.hasNext())
/* 128:    */     {
/* 129:161 */       MapGenStructure var13 = (MapGenStructure)var12.next();
/* 130:162 */       var13.func_151539_a(this, this.worldObj, par1, par2, null);
/* 131:    */     }
/* 132:165 */     var3.generateSkylightMap();
/* 133:166 */     return var3;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public boolean chunkExists(int par1, int par2)
/* 137:    */   {
/* 138:174 */     return true;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void populate(IChunkProvider par1IChunkProvider, int par2, int par3)
/* 142:    */   {
/* 143:182 */     int var4 = par2 * 16;
/* 144:183 */     int var5 = par3 * 16;
/* 145:184 */     BiomeGenBase var6 = this.worldObj.getBiomeGenForCoords(var4 + 16, var5 + 16);
/* 146:185 */     boolean var7 = false;
/* 147:186 */     this.random.setSeed(this.worldObj.getSeed());
/* 148:187 */     long var8 = this.random.nextLong() / 2L * 2L + 1L;
/* 149:188 */     long var10 = this.random.nextLong() / 2L * 2L + 1L;
/* 150:189 */     this.random.setSeed(par2 * var8 + par3 * var10 ^ this.worldObj.getSeed());
/* 151:190 */     Iterator var12 = this.structureGenerators.iterator();
/* 152:192 */     while (var12.hasNext())
/* 153:    */     {
/* 154:194 */       MapGenStructure var13 = (MapGenStructure)var12.next();
/* 155:195 */       boolean var14 = var13.generateStructuresInChunk(this.worldObj, this.random, par2, par3);
/* 156:197 */       if ((var13 instanceof MapGenVillage)) {
/* 157:199 */         var7 |= var14;
/* 158:    */       }
/* 159:    */     }
/* 160:207 */     if ((this.waterLakeGenerator != null) && (!var7) && (this.random.nextInt(4) == 0))
/* 161:    */     {
/* 162:209 */       int var16 = var4 + this.random.nextInt(16) + 8;
/* 163:210 */       int var17 = this.random.nextInt(256);
/* 164:211 */       int var18 = var5 + this.random.nextInt(16) + 8;
/* 165:212 */       this.waterLakeGenerator.generate(this.worldObj, this.random, var16, var17, var18);
/* 166:    */     }
/* 167:215 */     if ((this.lavaLakeGenerator != null) && (!var7) && (this.random.nextInt(8) == 0))
/* 168:    */     {
/* 169:217 */       int var16 = var4 + this.random.nextInt(16) + 8;
/* 170:218 */       int var17 = this.random.nextInt(this.random.nextInt(248) + 8);
/* 171:219 */       int var18 = var5 + this.random.nextInt(16) + 8;
/* 172:221 */       if ((var17 < 63) || (this.random.nextInt(10) == 0)) {
/* 173:223 */         this.lavaLakeGenerator.generate(this.worldObj, this.random, var16, var17, var18);
/* 174:    */       }
/* 175:    */     }
/* 176:227 */     if (this.hasDungeons) {
/* 177:229 */       for (int var16 = 0; var16 < 8; var16++)
/* 178:    */       {
/* 179:231 */         int var17 = var4 + this.random.nextInt(16) + 8;
/* 180:232 */         int var18 = this.random.nextInt(256);
/* 181:233 */         int var15 = var5 + this.random.nextInt(16) + 8;
/* 182:234 */         new WorldGenDungeons().generate(this.worldObj, this.random, var17, var18, var15);
/* 183:    */       }
/* 184:    */     }
/* 185:238 */     if (this.hasDecoration) {
/* 186:240 */       var6.decorate(this.worldObj, this.random, var4, var5);
/* 187:    */     }
/* 188:    */   }
/* 189:    */   
/* 190:    */   public boolean saveChunks(boolean par1, IProgressUpdate par2IProgressUpdate)
/* 191:    */   {
/* 192:250 */     return true;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void saveExtraData() {}
/* 196:    */   
/* 197:    */   public boolean unloadQueuedChunks()
/* 198:    */   {
/* 199:264 */     return false;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public boolean canSave()
/* 203:    */   {
/* 204:272 */     return true;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public String makeString()
/* 208:    */   {
/* 209:280 */     return "FlatLevelSource";
/* 210:    */   }
/* 211:    */   
/* 212:    */   public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4)
/* 213:    */   {
/* 214:288 */     BiomeGenBase var5 = this.worldObj.getBiomeGenForCoords(par2, par4);
/* 215:289 */     return var5.getSpawnableList(par1EnumCreatureType);
/* 216:    */   }
/* 217:    */   
/* 218:    */   public ChunkPosition func_147416_a(World p_147416_1_, String p_147416_2_, int p_147416_3_, int p_147416_4_, int p_147416_5_)
/* 219:    */   {
/* 220:294 */     if ("Stronghold".equals(p_147416_2_))
/* 221:    */     {
/* 222:296 */       Iterator var6 = this.structureGenerators.iterator();
/* 223:298 */       while (var6.hasNext())
/* 224:    */       {
/* 225:300 */         MapGenStructure var7 = (MapGenStructure)var6.next();
/* 226:302 */         if ((var7 instanceof MapGenStronghold)) {
/* 227:304 */           return var7.func_151545_a(p_147416_1_, p_147416_3_, p_147416_4_, p_147416_5_);
/* 228:    */         }
/* 229:    */       }
/* 230:    */     }
/* 231:309 */     return null;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public int getLoadedChunkCount()
/* 235:    */   {
/* 236:314 */     return 0;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public void recreateStructures(int par1, int par2)
/* 240:    */   {
/* 241:319 */     Iterator var3 = this.structureGenerators.iterator();
/* 242:321 */     while (var3.hasNext())
/* 243:    */     {
/* 244:323 */       MapGenStructure var4 = (MapGenStructure)var3.next();
/* 245:324 */       var4.func_151539_a(this, this.worldObj, par1, par2, null);
/* 246:    */     }
/* 247:    */   }
/* 248:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.ChunkProviderFlat
 * JD-Core Version:    0.7.0.1
 */