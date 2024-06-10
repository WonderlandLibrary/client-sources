/*   1:    */ package net.minecraft.world.chunk.storage;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.File;
/*   6:    */ import java.io.IOException;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.Collection;
/*   9:    */ import java.util.HashSet;
/*  10:    */ import java.util.Iterator;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import java.util.Set;
/*  14:    */ import net.minecraft.block.Block;
/*  15:    */ import net.minecraft.entity.Entity;
/*  16:    */ import net.minecraft.entity.EntityList;
/*  17:    */ import net.minecraft.nbt.CompressedStreamTools;
/*  18:    */ import net.minecraft.nbt.NBTTagCompound;
/*  19:    */ import net.minecraft.nbt.NBTTagList;
/*  20:    */ import net.minecraft.tileentity.TileEntity;
/*  21:    */ import net.minecraft.world.ChunkCoordIntPair;
/*  22:    */ import net.minecraft.world.MinecraftException;
/*  23:    */ import net.minecraft.world.NextTickListEntry;
/*  24:    */ import net.minecraft.world.World;
/*  25:    */ import net.minecraft.world.WorldProvider;
/*  26:    */ import net.minecraft.world.chunk.Chunk;
/*  27:    */ import net.minecraft.world.chunk.NibbleArray;
/*  28:    */ import net.minecraft.world.storage.IThreadedFileIO;
/*  29:    */ import net.minecraft.world.storage.ThreadedFileIOBase;
/*  30:    */ import org.apache.logging.log4j.LogManager;
/*  31:    */ import org.apache.logging.log4j.Logger;
/*  32:    */ 
/*  33:    */ public class AnvilChunkLoader
/*  34:    */   implements IChunkLoader, IThreadedFileIO
/*  35:    */ {
/*  36: 32 */   private static final Logger logger = ;
/*  37: 33 */   private List chunksToRemove = new ArrayList();
/*  38: 34 */   private Set pendingAnvilChunksCoordinates = new HashSet();
/*  39: 35 */   private Object syncLockObject = new Object();
/*  40:    */   private final File chunkSaveLocation;
/*  41:    */   private static final String __OBFID = "CL_00000384";
/*  42:    */   
/*  43:    */   public AnvilChunkLoader(File par1File)
/*  44:    */   {
/*  45: 43 */     this.chunkSaveLocation = par1File;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public Chunk loadChunk(World par1World, int par2, int par3)
/*  49:    */     throws IOException
/*  50:    */   {
/*  51: 51 */     NBTTagCompound var4 = null;
/*  52: 52 */     ChunkCoordIntPair var5 = new ChunkCoordIntPair(par2, par3);
/*  53: 53 */     Object var6 = this.syncLockObject;
/*  54: 55 */     synchronized (this.syncLockObject)
/*  55:    */     {
/*  56: 57 */       if (this.pendingAnvilChunksCoordinates.contains(var5)) {
/*  57: 59 */         for (int var7 = 0; var7 < this.chunksToRemove.size(); var7++) {
/*  58: 61 */           if (((PendingChunk)this.chunksToRemove.get(var7)).chunkCoordinate.equals(var5))
/*  59:    */           {
/*  60: 63 */             var4 = ((PendingChunk)this.chunksToRemove.get(var7)).nbtTags;
/*  61: 64 */             break;
/*  62:    */           }
/*  63:    */         }
/*  64:    */       }
/*  65:    */     }
/*  66: 70 */     if (var4 == null)
/*  67:    */     {
/*  68: 72 */       DataInputStream var10 = RegionFileCache.getChunkInputStream(this.chunkSaveLocation, par2, par3);
/*  69: 74 */       if (var10 == null) {
/*  70: 76 */         return null;
/*  71:    */       }
/*  72: 79 */       var4 = CompressedStreamTools.read(var10);
/*  73:    */     }
/*  74: 82 */     return checkedReadChunkFromNBT(par1World, par2, par3, var4);
/*  75:    */   }
/*  76:    */   
/*  77:    */   protected Chunk checkedReadChunkFromNBT(World par1World, int par2, int par3, NBTTagCompound par4NBTTagCompound)
/*  78:    */   {
/*  79: 90 */     if (!par4NBTTagCompound.func_150297_b("Level", 10))
/*  80:    */     {
/*  81: 92 */       logger.error("Chunk file at " + par2 + "," + par3 + " is missing level data, skipping");
/*  82: 93 */       return null;
/*  83:    */     }
/*  84: 95 */     if (!par4NBTTagCompound.getCompoundTag("Level").func_150297_b("Sections", 9))
/*  85:    */     {
/*  86: 97 */       logger.error("Chunk file at " + par2 + "," + par3 + " is missing block data, skipping");
/*  87: 98 */       return null;
/*  88:    */     }
/*  89:102 */     Chunk var5 = readChunkFromNBT(par1World, par4NBTTagCompound.getCompoundTag("Level"));
/*  90:104 */     if (!var5.isAtLocation(par2, par3))
/*  91:    */     {
/*  92:106 */       logger.error("Chunk file at " + par2 + "," + par3 + " is in the wrong location; relocating. (Expected " + par2 + ", " + par3 + ", got " + var5.xPosition + ", " + var5.zPosition + ")");
/*  93:107 */       par4NBTTagCompound.setInteger("xPos", par2);
/*  94:108 */       par4NBTTagCompound.setInteger("zPos", par3);
/*  95:109 */       var5 = readChunkFromNBT(par1World, par4NBTTagCompound.getCompoundTag("Level"));
/*  96:    */     }
/*  97:112 */     return var5;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void saveChunk(World par1World, Chunk par2Chunk)
/* 101:    */     throws MinecraftException, IOException
/* 102:    */   {
/* 103:118 */     par1World.checkSessionLock();
/* 104:    */     try
/* 105:    */     {
/* 106:122 */       NBTTagCompound var3 = new NBTTagCompound();
/* 107:123 */       NBTTagCompound var4 = new NBTTagCompound();
/* 108:124 */       var3.setTag("Level", var4);
/* 109:125 */       writeChunkToNBT(par2Chunk, par1World, var4);
/* 110:126 */       addChunkToPending(par2Chunk.getChunkCoordIntPair(), var3);
/* 111:    */     }
/* 112:    */     catch (Exception var5)
/* 113:    */     {
/* 114:130 */       var5.printStackTrace();
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   protected void addChunkToPending(ChunkCoordIntPair par1ChunkCoordIntPair, NBTTagCompound par2NBTTagCompound)
/* 119:    */   {
/* 120:136 */     Object var3 = this.syncLockObject;
/* 121:138 */     synchronized (this.syncLockObject)
/* 122:    */     {
/* 123:140 */       if (this.pendingAnvilChunksCoordinates.contains(par1ChunkCoordIntPair)) {
/* 124:142 */         for (int var4 = 0; var4 < this.chunksToRemove.size(); var4++) {
/* 125:144 */           if (((PendingChunk)this.chunksToRemove.get(var4)).chunkCoordinate.equals(par1ChunkCoordIntPair))
/* 126:    */           {
/* 127:146 */             this.chunksToRemove.set(var4, new PendingChunk(par1ChunkCoordIntPair, par2NBTTagCompound));
/* 128:147 */             return;
/* 129:    */           }
/* 130:    */         }
/* 131:    */       }
/* 132:152 */       this.chunksToRemove.add(new PendingChunk(par1ChunkCoordIntPair, par2NBTTagCompound));
/* 133:153 */       this.pendingAnvilChunksCoordinates.add(par1ChunkCoordIntPair);
/* 134:154 */       ThreadedFileIOBase.threadedIOInstance.queueIO(this);
/* 135:    */     }
/* 136:    */   }
/* 137:    */   
/* 138:    */   public boolean writeNextIO()
/* 139:    */   {
/* 140:163 */     PendingChunk var1 = null;
/* 141:164 */     Object var2 = this.syncLockObject;
/* 142:166 */     synchronized (this.syncLockObject)
/* 143:    */     {
/* 144:168 */       if (this.chunksToRemove.isEmpty()) {
/* 145:170 */         return false;
/* 146:    */       }
/* 147:173 */       var1 = (PendingChunk)this.chunksToRemove.remove(0);
/* 148:174 */       this.pendingAnvilChunksCoordinates.remove(var1.chunkCoordinate);
/* 149:    */     }
/* 150:177 */     if (var1 != null) {
/* 151:    */       try
/* 152:    */       {
/* 153:181 */         writeChunkNBTTags(var1);
/* 154:    */       }
/* 155:    */       catch (Exception var4)
/* 156:    */       {
/* 157:185 */         var4.printStackTrace();
/* 158:    */       }
/* 159:    */     }
/* 160:189 */     return true;
/* 161:    */   }
/* 162:    */   
/* 163:    */   private void writeChunkNBTTags(PendingChunk par1AnvilChunkLoaderPending)
/* 164:    */     throws IOException
/* 165:    */   {
/* 166:194 */     DataOutputStream var2 = RegionFileCache.getChunkOutputStream(this.chunkSaveLocation, par1AnvilChunkLoaderPending.chunkCoordinate.chunkXPos, par1AnvilChunkLoaderPending.chunkCoordinate.chunkZPos);
/* 167:195 */     CompressedStreamTools.write(par1AnvilChunkLoaderPending.nbtTags, var2);
/* 168:196 */     var2.close();
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void saveExtraChunkData(World par1World, Chunk par2Chunk) {}
/* 172:    */   
/* 173:    */   public void chunkTick() {}
/* 174:    */   
/* 175:    */   public void saveExtraData()
/* 176:    */   {
/* 177:216 */     while (writeNextIO()) {}
/* 178:    */   }
/* 179:    */   
/* 180:    */   private void writeChunkToNBT(Chunk par1Chunk, World par2World, NBTTagCompound par3NBTTagCompound)
/* 181:    */   {
/* 182:228 */     par3NBTTagCompound.setByte("V", (byte)1);
/* 183:229 */     par3NBTTagCompound.setInteger("xPos", par1Chunk.xPosition);
/* 184:230 */     par3NBTTagCompound.setInteger("zPos", par1Chunk.zPosition);
/* 185:231 */     par3NBTTagCompound.setLong("LastUpdate", par2World.getTotalWorldTime());
/* 186:232 */     par3NBTTagCompound.setIntArray("HeightMap", par1Chunk.heightMap);
/* 187:233 */     par3NBTTagCompound.setBoolean("TerrainPopulated", par1Chunk.isTerrainPopulated);
/* 188:234 */     par3NBTTagCompound.setBoolean("LightPopulated", par1Chunk.isLightPopulated);
/* 189:235 */     par3NBTTagCompound.setLong("InhabitedTime", par1Chunk.inhabitedTime);
/* 190:236 */     ExtendedBlockStorage[] var4 = par1Chunk.getBlockStorageArray();
/* 191:237 */     NBTTagList var5 = new NBTTagList();
/* 192:238 */     boolean var6 = !par2World.provider.hasNoSky;
/* 193:239 */     ExtendedBlockStorage[] var7 = var4;
/* 194:240 */     int var8 = var4.length;
/* 195:243 */     for (int var9 = 0; var9 < var8; var9++)
/* 196:    */     {
/* 197:245 */       ExtendedBlockStorage var10 = var7[var9];
/* 198:247 */       if (var10 != null)
/* 199:    */       {
/* 200:249 */         NBTTagCompound var11 = new NBTTagCompound();
/* 201:250 */         var11.setByte("Y", (byte)(var10.getYLocation() >> 4 & 0xFF));
/* 202:251 */         var11.setByteArray("Blocks", var10.getBlockLSBArray());
/* 203:253 */         if (var10.getBlockMSBArray() != null) {
/* 204:255 */           var11.setByteArray("Add", var10.getBlockMSBArray().data);
/* 205:    */         }
/* 206:258 */         var11.setByteArray("Data", var10.getMetadataArray().data);
/* 207:259 */         var11.setByteArray("BlockLight", var10.getBlocklightArray().data);
/* 208:261 */         if (var6) {
/* 209:263 */           var11.setByteArray("SkyLight", var10.getSkylightArray().data);
/* 210:    */         } else {
/* 211:267 */           var11.setByteArray("SkyLight", new byte[var10.getBlocklightArray().data.length]);
/* 212:    */         }
/* 213:270 */         var5.appendTag(var11);
/* 214:    */       }
/* 215:    */     }
/* 216:274 */     par3NBTTagCompound.setTag("Sections", var5);
/* 217:275 */     par3NBTTagCompound.setByteArray("Biomes", par1Chunk.getBiomeArray());
/* 218:276 */     par1Chunk.hasEntities = false;
/* 219:277 */     NBTTagList var16 = new NBTTagList();
/* 220:280 */     for (var8 = 0; var8 < par1Chunk.entityLists.length; var8++)
/* 221:    */     {
/* 222:282 */       Iterator var18 = par1Chunk.entityLists[var8].iterator();
/* 223:284 */       while (var18.hasNext())
/* 224:    */       {
/* 225:286 */         Entity var21 = (Entity)var18.next();
/* 226:287 */         NBTTagCompound var11 = new NBTTagCompound();
/* 227:289 */         if (var21.writeToNBTOptional(var11))
/* 228:    */         {
/* 229:291 */           par1Chunk.hasEntities = true;
/* 230:292 */           var16.appendTag(var11);
/* 231:    */         }
/* 232:    */       }
/* 233:    */     }
/* 234:297 */     par3NBTTagCompound.setTag("Entities", var16);
/* 235:298 */     NBTTagList var17 = new NBTTagList();
/* 236:299 */     Iterator var18 = par1Chunk.chunkTileEntityMap.values().iterator();
/* 237:301 */     while (var18.hasNext())
/* 238:    */     {
/* 239:303 */       TileEntity var22 = (TileEntity)var18.next();
/* 240:304 */       NBTTagCompound var11 = new NBTTagCompound();
/* 241:305 */       var22.writeToNBT(var11);
/* 242:306 */       var17.appendTag(var11);
/* 243:    */     }
/* 244:309 */     par3NBTTagCompound.setTag("TileEntities", var17);
/* 245:310 */     List var20 = par2World.getPendingBlockUpdates(par1Chunk, false);
/* 246:312 */     if (var20 != null)
/* 247:    */     {
/* 248:314 */       long var19 = par2World.getTotalWorldTime();
/* 249:315 */       NBTTagList var12 = new NBTTagList();
/* 250:316 */       Iterator var13 = var20.iterator();
/* 251:318 */       while (var13.hasNext())
/* 252:    */       {
/* 253:320 */         NextTickListEntry var14 = (NextTickListEntry)var13.next();
/* 254:321 */         NBTTagCompound var15 = new NBTTagCompound();
/* 255:322 */         var15.setInteger("i", Block.getIdFromBlock(var14.func_151351_a()));
/* 256:323 */         var15.setInteger("x", var14.xCoord);
/* 257:324 */         var15.setInteger("y", var14.yCoord);
/* 258:325 */         var15.setInteger("z", var14.zCoord);
/* 259:326 */         var15.setInteger("t", (int)(var14.scheduledTime - var19));
/* 260:327 */         var15.setInteger("p", var14.priority);
/* 261:328 */         var12.appendTag(var15);
/* 262:    */       }
/* 263:331 */       par3NBTTagCompound.setTag("TileTicks", var12);
/* 264:    */     }
/* 265:    */   }
/* 266:    */   
/* 267:    */   private Chunk readChunkFromNBT(World par1World, NBTTagCompound par2NBTTagCompound)
/* 268:    */   {
/* 269:341 */     int var3 = par2NBTTagCompound.getInteger("xPos");
/* 270:342 */     int var4 = par2NBTTagCompound.getInteger("zPos");
/* 271:343 */     Chunk var5 = new Chunk(par1World, var3, var4);
/* 272:344 */     var5.heightMap = par2NBTTagCompound.getIntArray("HeightMap");
/* 273:345 */     var5.isTerrainPopulated = par2NBTTagCompound.getBoolean("TerrainPopulated");
/* 274:346 */     var5.isLightPopulated = par2NBTTagCompound.getBoolean("LightPopulated");
/* 275:347 */     var5.inhabitedTime = par2NBTTagCompound.getLong("InhabitedTime");
/* 276:348 */     NBTTagList var6 = par2NBTTagCompound.getTagList("Sections", 10);
/* 277:349 */     byte var7 = 16;
/* 278:350 */     ExtendedBlockStorage[] var8 = new ExtendedBlockStorage[var7];
/* 279:351 */     boolean var9 = !par1World.provider.hasNoSky;
/* 280:353 */     for (int var10 = 0; var10 < var6.tagCount(); var10++)
/* 281:    */     {
/* 282:355 */       NBTTagCompound var11 = var6.getCompoundTagAt(var10);
/* 283:356 */       byte var12 = var11.getByte("Y");
/* 284:357 */       ExtendedBlockStorage var13 = new ExtendedBlockStorage(var12 << 4, var9);
/* 285:358 */       var13.setBlockLSBArray(var11.getByteArray("Blocks"));
/* 286:360 */       if (var11.func_150297_b("Add", 7)) {
/* 287:362 */         var13.setBlockMSBArray(new NibbleArray(var11.getByteArray("Add"), 4));
/* 288:    */       }
/* 289:365 */       var13.setBlockMetadataArray(new NibbleArray(var11.getByteArray("Data"), 4));
/* 290:366 */       var13.setBlocklightArray(new NibbleArray(var11.getByteArray("BlockLight"), 4));
/* 291:368 */       if (var9) {
/* 292:370 */         var13.setSkylightArray(new NibbleArray(var11.getByteArray("SkyLight"), 4));
/* 293:    */       }
/* 294:373 */       var13.removeInvalidBlocks();
/* 295:374 */       var8[var12] = var13;
/* 296:    */     }
/* 297:377 */     var5.setStorageArrays(var8);
/* 298:379 */     if (par2NBTTagCompound.func_150297_b("Biomes", 7)) {
/* 299:381 */       var5.setBiomeArray(par2NBTTagCompound.getByteArray("Biomes"));
/* 300:    */     }
/* 301:384 */     NBTTagList var18 = par2NBTTagCompound.getTagList("Entities", 10);
/* 302:386 */     if (var18 != null) {
/* 303:388 */       for (int var17 = 0; var17 < var18.tagCount(); var17++)
/* 304:    */       {
/* 305:390 */         NBTTagCompound var19 = var18.getCompoundTagAt(var17);
/* 306:391 */         Entity var25 = EntityList.createEntityFromNBT(var19, par1World);
/* 307:392 */         var5.hasEntities = true;
/* 308:394 */         if (var25 != null)
/* 309:    */         {
/* 310:396 */           var5.addEntity(var25);
/* 311:397 */           Entity var14 = var25;
/* 312:399 */           for (NBTTagCompound var15 = var19; var15.func_150297_b("Riding", 10); var15 = var15.getCompoundTag("Riding"))
/* 313:    */           {
/* 314:401 */             Entity var16 = EntityList.createEntityFromNBT(var15.getCompoundTag("Riding"), par1World);
/* 315:403 */             if (var16 != null)
/* 316:    */             {
/* 317:405 */               var5.addEntity(var16);
/* 318:406 */               var14.mountEntity(var16);
/* 319:    */             }
/* 320:409 */             var14 = var16;
/* 321:    */           }
/* 322:    */         }
/* 323:    */       }
/* 324:    */     }
/* 325:415 */     NBTTagList var21 = par2NBTTagCompound.getTagList("TileEntities", 10);
/* 326:417 */     if (var21 != null) {
/* 327:419 */       for (int var20 = 0; var20 < var21.tagCount(); var20++)
/* 328:    */       {
/* 329:421 */         NBTTagCompound var22 = var21.getCompoundTagAt(var20);
/* 330:422 */         TileEntity var27 = TileEntity.createAndLoadEntity(var22);
/* 331:424 */         if (var27 != null) {
/* 332:426 */           var5.addTileEntity(var27);
/* 333:    */         }
/* 334:    */       }
/* 335:    */     }
/* 336:431 */     if (par2NBTTagCompound.func_150297_b("TileTicks", 9))
/* 337:    */     {
/* 338:433 */       NBTTagList var24 = par2NBTTagCompound.getTagList("TileTicks", 10);
/* 339:435 */       if (var24 != null) {
/* 340:437 */         for (int var23 = 0; var23 < var24.tagCount(); var23++)
/* 341:    */         {
/* 342:439 */           NBTTagCompound var26 = var24.getCompoundTagAt(var23);
/* 343:440 */           par1World.func_147446_b(var26.getInteger("x"), var26.getInteger("y"), var26.getInteger("z"), Block.getBlockById(var26.getInteger("i")), var26.getInteger("t"), var26.getInteger("p"));
/* 344:    */         }
/* 345:    */       }
/* 346:    */     }
/* 347:445 */     return var5;
/* 348:    */   }
/* 349:    */   
/* 350:    */   static class PendingChunk
/* 351:    */   {
/* 352:    */     public final ChunkCoordIntPair chunkCoordinate;
/* 353:    */     public final NBTTagCompound nbtTags;
/* 354:    */     private static final String __OBFID = "CL_00000385";
/* 355:    */     
/* 356:    */     public PendingChunk(ChunkCoordIntPair par1ChunkCoordIntPair, NBTTagCompound par2NBTTagCompound)
/* 357:    */     {
/* 358:456 */       this.chunkCoordinate = par1ChunkCoordIntPair;
/* 359:457 */       this.nbtTags = par2NBTTagCompound;
/* 360:    */     }
/* 361:    */   }
/* 362:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.chunk.storage.AnvilChunkLoader
 * JD-Core Version:    0.7.0.1
 */