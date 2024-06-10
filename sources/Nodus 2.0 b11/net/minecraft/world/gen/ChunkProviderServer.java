/*   1:    */ package net.minecraft.world.gen;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.HashSet;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Set;
/*   9:    */ import net.minecraft.crash.CrashReport;
/*  10:    */ import net.minecraft.crash.CrashReportCategory;
/*  11:    */ import net.minecraft.entity.EnumCreatureType;
/*  12:    */ import net.minecraft.util.ChunkCoordinates;
/*  13:    */ import net.minecraft.util.IProgressUpdate;
/*  14:    */ import net.minecraft.util.LongHashMap;
/*  15:    */ import net.minecraft.util.ReportedException;
/*  16:    */ import net.minecraft.world.ChunkCoordIntPair;
/*  17:    */ import net.minecraft.world.ChunkPosition;
/*  18:    */ import net.minecraft.world.MinecraftException;
/*  19:    */ import net.minecraft.world.World;
/*  20:    */ import net.minecraft.world.WorldProvider;
/*  21:    */ import net.minecraft.world.WorldServer;
/*  22:    */ import net.minecraft.world.chunk.Chunk;
/*  23:    */ import net.minecraft.world.chunk.EmptyChunk;
/*  24:    */ import net.minecraft.world.chunk.IChunkProvider;
/*  25:    */ import net.minecraft.world.chunk.storage.IChunkLoader;
/*  26:    */ import org.apache.logging.log4j.LogManager;
/*  27:    */ import org.apache.logging.log4j.Logger;
/*  28:    */ 
/*  29:    */ public class ChunkProviderServer
/*  30:    */   implements IChunkProvider
/*  31:    */ {
/*  32: 30 */   private static final Logger logger = ;
/*  33: 36 */   private Set chunksToUnload = new HashSet();
/*  34:    */   private Chunk defaultEmptyChunk;
/*  35:    */   private IChunkProvider currentChunkProvider;
/*  36:    */   private IChunkLoader currentChunkLoader;
/*  37: 44 */   public boolean loadChunkOnProvideRequest = true;
/*  38: 45 */   private LongHashMap loadedChunkHashMap = new LongHashMap();
/*  39: 46 */   private List loadedChunks = new ArrayList();
/*  40:    */   private WorldServer worldObj;
/*  41:    */   private static final String __OBFID = "CL_00001436";
/*  42:    */   
/*  43:    */   public ChunkProviderServer(WorldServer par1WorldServer, IChunkLoader par2IChunkLoader, IChunkProvider par3IChunkProvider)
/*  44:    */   {
/*  45: 52 */     this.defaultEmptyChunk = new EmptyChunk(par1WorldServer, 0, 0);
/*  46: 53 */     this.worldObj = par1WorldServer;
/*  47: 54 */     this.currentChunkLoader = par2IChunkLoader;
/*  48: 55 */     this.currentChunkProvider = par3IChunkProvider;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public boolean chunkExists(int par1, int par2)
/*  52:    */   {
/*  53: 63 */     return this.loadedChunkHashMap.containsItem(ChunkCoordIntPair.chunkXZ2Int(par1, par2));
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void unloadChunksIfNotNearSpawn(int par1, int par2)
/*  57:    */   {
/*  58: 72 */     if (this.worldObj.provider.canRespawnHere())
/*  59:    */     {
/*  60: 74 */       ChunkCoordinates var3 = this.worldObj.getSpawnPoint();
/*  61: 75 */       int var4 = par1 * 16 + 8 - var3.posX;
/*  62: 76 */       int var5 = par2 * 16 + 8 - var3.posZ;
/*  63: 77 */       short var6 = 128;
/*  64: 79 */       if ((var4 < -var6) || (var4 > var6) || (var5 < -var6) || (var5 > var6)) {
/*  65: 81 */         this.chunksToUnload.add(Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(par1, par2)));
/*  66:    */       }
/*  67:    */     }
/*  68:    */     else
/*  69:    */     {
/*  70: 86 */       this.chunksToUnload.add(Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(par1, par2)));
/*  71:    */     }
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void unloadAllChunks()
/*  75:    */   {
/*  76: 95 */     Iterator var1 = this.loadedChunks.iterator();
/*  77: 97 */     while (var1.hasNext())
/*  78:    */     {
/*  79: 99 */       Chunk var2 = (Chunk)var1.next();
/*  80:100 */       unloadChunksIfNotNearSpawn(var2.xPosition, var2.zPosition);
/*  81:    */     }
/*  82:    */   }
/*  83:    */   
/*  84:    */   public Chunk loadChunk(int par1, int par2)
/*  85:    */   {
/*  86:109 */     long var3 = ChunkCoordIntPair.chunkXZ2Int(par1, par2);
/*  87:110 */     this.chunksToUnload.remove(Long.valueOf(var3));
/*  88:111 */     Chunk var5 = (Chunk)this.loadedChunkHashMap.getValueByKey(var3);
/*  89:113 */     if (var5 == null)
/*  90:    */     {
/*  91:115 */       var5 = safeLoadChunk(par1, par2);
/*  92:117 */       if (var5 == null) {
/*  93:119 */         if (this.currentChunkProvider == null) {
/*  94:121 */           var5 = this.defaultEmptyChunk;
/*  95:    */         } else {
/*  96:    */           try
/*  97:    */           {
/*  98:127 */             var5 = this.currentChunkProvider.provideChunk(par1, par2);
/*  99:    */           }
/* 100:    */           catch (Throwable var9)
/* 101:    */           {
/* 102:131 */             CrashReport var7 = CrashReport.makeCrashReport(var9, "Exception generating new chunk");
/* 103:132 */             CrashReportCategory var8 = var7.makeCategory("Chunk to be generated");
/* 104:133 */             var8.addCrashSection("Location", String.format("%d,%d", new Object[] { Integer.valueOf(par1), Integer.valueOf(par2) }));
/* 105:134 */             var8.addCrashSection("Position hash", Long.valueOf(var3));
/* 106:135 */             var8.addCrashSection("Generator", this.currentChunkProvider.makeString());
/* 107:136 */             throw new ReportedException(var7);
/* 108:    */           }
/* 109:    */         }
/* 110:    */       }
/* 111:141 */       this.loadedChunkHashMap.add(var3, var5);
/* 112:142 */       this.loadedChunks.add(var5);
/* 113:143 */       var5.onChunkLoad();
/* 114:144 */       var5.populateChunk(this, this, par1, par2);
/* 115:    */     }
/* 116:147 */     return var5;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public Chunk provideChunk(int par1, int par2)
/* 120:    */   {
/* 121:156 */     Chunk var3 = (Chunk)this.loadedChunkHashMap.getValueByKey(ChunkCoordIntPair.chunkXZ2Int(par1, par2));
/* 122:157 */     return var3 == null ? loadChunk(par1, par2) : (!this.worldObj.findingSpawnPoint) && (!this.loadChunkOnProvideRequest) ? this.defaultEmptyChunk : var3;
/* 123:    */   }
/* 124:    */   
/* 125:    */   private Chunk safeLoadChunk(int par1, int par2)
/* 126:    */   {
/* 127:165 */     if (this.currentChunkLoader == null) {
/* 128:167 */       return null;
/* 129:    */     }
/* 130:    */     try
/* 131:    */     {
/* 132:173 */       Chunk var3 = this.currentChunkLoader.loadChunk(this.worldObj, par1, par2);
/* 133:175 */       if (var3 != null)
/* 134:    */       {
/* 135:177 */         var3.lastSaveTime = this.worldObj.getTotalWorldTime();
/* 136:179 */         if (this.currentChunkProvider != null) {
/* 137:181 */           this.currentChunkProvider.recreateStructures(par1, par2);
/* 138:    */         }
/* 139:    */       }
/* 140:185 */       return var3;
/* 141:    */     }
/* 142:    */     catch (Exception var4)
/* 143:    */     {
/* 144:189 */       logger.error("Couldn't load chunk", var4);
/* 145:    */     }
/* 146:190 */     return null;
/* 147:    */   }
/* 148:    */   
/* 149:    */   private void safeSaveExtraChunkData(Chunk par1Chunk)
/* 150:    */   {
/* 151:200 */     if (this.currentChunkLoader != null) {
/* 152:    */       try
/* 153:    */       {
/* 154:204 */         this.currentChunkLoader.saveExtraChunkData(this.worldObj, par1Chunk);
/* 155:    */       }
/* 156:    */       catch (Exception var3)
/* 157:    */       {
/* 158:208 */         logger.error("Couldn't save entities", var3);
/* 159:    */       }
/* 160:    */     }
/* 161:    */   }
/* 162:    */   
/* 163:    */   private void safeSaveChunk(Chunk par1Chunk)
/* 164:    */   {
/* 165:218 */     if (this.currentChunkLoader != null) {
/* 166:    */       try
/* 167:    */       {
/* 168:222 */         par1Chunk.lastSaveTime = this.worldObj.getTotalWorldTime();
/* 169:223 */         this.currentChunkLoader.saveChunk(this.worldObj, par1Chunk);
/* 170:    */       }
/* 171:    */       catch (IOException var3)
/* 172:    */       {
/* 173:227 */         logger.error("Couldn't save chunk", var3);
/* 174:    */       }
/* 175:    */       catch (MinecraftException var4)
/* 176:    */       {
/* 177:231 */         logger.error("Couldn't save chunk; already in use by another instance of Minecraft?", var4);
/* 178:    */       }
/* 179:    */     }
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void populate(IChunkProvider par1IChunkProvider, int par2, int par3)
/* 183:    */   {
/* 184:241 */     Chunk var4 = provideChunk(par2, par3);
/* 185:243 */     if (!var4.isTerrainPopulated)
/* 186:    */     {
/* 187:245 */       var4.func_150809_p();
/* 188:247 */       if (this.currentChunkProvider != null)
/* 189:    */       {
/* 190:249 */         this.currentChunkProvider.populate(par1IChunkProvider, par2, par3);
/* 191:250 */         var4.setChunkModified();
/* 192:    */       }
/* 193:    */     }
/* 194:    */   }
/* 195:    */   
/* 196:    */   public boolean saveChunks(boolean par1, IProgressUpdate par2IProgressUpdate)
/* 197:    */   {
/* 198:261 */     int var3 = 0;
/* 199:263 */     for (int var4 = 0; var4 < this.loadedChunks.size(); var4++)
/* 200:    */     {
/* 201:265 */       Chunk var5 = (Chunk)this.loadedChunks.get(var4);
/* 202:267 */       if (par1) {
/* 203:269 */         safeSaveExtraChunkData(var5);
/* 204:    */       }
/* 205:272 */       if (var5.needsSaving(par1))
/* 206:    */       {
/* 207:274 */         safeSaveChunk(var5);
/* 208:275 */         var5.isModified = false;
/* 209:276 */         var3++;
/* 210:278 */         if ((var3 == 24) && (!par1)) {
/* 211:280 */           return false;
/* 212:    */         }
/* 213:    */       }
/* 214:    */     }
/* 215:285 */     return true;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void saveExtraData()
/* 219:    */   {
/* 220:294 */     if (this.currentChunkLoader != null) {
/* 221:296 */       this.currentChunkLoader.saveExtraData();
/* 222:    */     }
/* 223:    */   }
/* 224:    */   
/* 225:    */   public boolean unloadQueuedChunks()
/* 226:    */   {
/* 227:305 */     if (!this.worldObj.levelSaving)
/* 228:    */     {
/* 229:307 */       for (int var1 = 0; var1 < 100; var1++) {
/* 230:309 */         if (!this.chunksToUnload.isEmpty())
/* 231:    */         {
/* 232:311 */           Long var2 = (Long)this.chunksToUnload.iterator().next();
/* 233:312 */           Chunk var3 = (Chunk)this.loadedChunkHashMap.getValueByKey(var2.longValue());
/* 234:313 */           var3.onChunkUnload();
/* 235:314 */           safeSaveChunk(var3);
/* 236:315 */           safeSaveExtraChunkData(var3);
/* 237:316 */           this.chunksToUnload.remove(var2);
/* 238:317 */           this.loadedChunkHashMap.remove(var2.longValue());
/* 239:318 */           this.loadedChunks.remove(var3);
/* 240:    */         }
/* 241:    */       }
/* 242:322 */       if (this.currentChunkLoader != null) {
/* 243:324 */         this.currentChunkLoader.chunkTick();
/* 244:    */       }
/* 245:    */     }
/* 246:328 */     return this.currentChunkProvider.unloadQueuedChunks();
/* 247:    */   }
/* 248:    */   
/* 249:    */   public boolean canSave()
/* 250:    */   {
/* 251:336 */     return !this.worldObj.levelSaving;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public String makeString()
/* 255:    */   {
/* 256:344 */     return "ServerChunkCache: " + this.loadedChunkHashMap.getNumHashElements() + " Drop: " + this.chunksToUnload.size();
/* 257:    */   }
/* 258:    */   
/* 259:    */   public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4)
/* 260:    */   {
/* 261:352 */     return this.currentChunkProvider.getPossibleCreatures(par1EnumCreatureType, par2, par3, par4);
/* 262:    */   }
/* 263:    */   
/* 264:    */   public ChunkPosition func_147416_a(World p_147416_1_, String p_147416_2_, int p_147416_3_, int p_147416_4_, int p_147416_5_)
/* 265:    */   {
/* 266:357 */     return this.currentChunkProvider.func_147416_a(p_147416_1_, p_147416_2_, p_147416_3_, p_147416_4_, p_147416_5_);
/* 267:    */   }
/* 268:    */   
/* 269:    */   public int getLoadedChunkCount()
/* 270:    */   {
/* 271:362 */     return this.loadedChunkHashMap.getNumHashElements();
/* 272:    */   }
/* 273:    */   
/* 274:    */   public void recreateStructures(int par1, int par2) {}
/* 275:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.ChunkProviderServer
 * JD-Core Version:    0.7.0.1
 */