/*     */ package net.minecraft.world.chunk.storage;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityList;
/*     */ import net.minecraft.nbt.CompressedStreamTools;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.ChunkCoordIntPair;
/*     */ import net.minecraft.world.MinecraftException;
/*     */ import net.minecraft.world.NextTickListEntry;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.chunk.NibbleArray;
/*     */ import net.minecraft.world.storage.IThreadedFileIO;
/*     */ import net.minecraft.world.storage.ThreadedFileIOBase;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class AnvilChunkLoader implements IChunkLoader, IThreadedFileIO {
/*  34 */   private static final Logger logger = LogManager.getLogger();
/*  35 */   private Map<ChunkCoordIntPair, NBTTagCompound> chunksToRemove = new ConcurrentHashMap<ChunkCoordIntPair, NBTTagCompound>();
/*  36 */   private Set<ChunkCoordIntPair> pendingAnvilChunksCoordinates = Collections.newSetFromMap(new ConcurrentHashMap<ChunkCoordIntPair, Boolean>());
/*     */   
/*     */   private final File chunkSaveLocation;
/*     */   
/*     */   private boolean field_183014_e = false;
/*     */ 
/*     */   
/*     */   public AnvilChunkLoader(File chunkSaveLocationIn) {
/*  44 */     this.chunkSaveLocation = chunkSaveLocationIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Chunk loadChunk(World worldIn, int x, int z) throws IOException {
/*  52 */     ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(x, z);
/*  53 */     NBTTagCompound nbttagcompound = this.chunksToRemove.get(chunkcoordintpair);
/*     */     
/*  55 */     if (nbttagcompound == null) {
/*     */       
/*  57 */       DataInputStream datainputstream = RegionFileCache.getChunkInputStream(this.chunkSaveLocation, x, z);
/*     */       
/*  59 */       if (datainputstream == null)
/*     */       {
/*  61 */         return null;
/*     */       }
/*     */       
/*  64 */       nbttagcompound = CompressedStreamTools.read(datainputstream);
/*     */     } 
/*     */     
/*  67 */     return checkedReadChunkFromNBT(worldIn, x, z, nbttagcompound);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Chunk checkedReadChunkFromNBT(World worldIn, int x, int z, NBTTagCompound p_75822_4_) {
/*  75 */     if (!p_75822_4_.hasKey("Level", 10)) {
/*     */       
/*  77 */       logger.error("Chunk file at " + x + "," + z + " is missing level data, skipping");
/*  78 */       return null;
/*     */     } 
/*     */ 
/*     */     
/*  82 */     NBTTagCompound nbttagcompound = p_75822_4_.getCompoundTag("Level");
/*     */     
/*  84 */     if (!nbttagcompound.hasKey("Sections", 9)) {
/*     */       
/*  86 */       logger.error("Chunk file at " + x + "," + z + " is missing block data, skipping");
/*  87 */       return null;
/*     */     } 
/*     */ 
/*     */     
/*  91 */     Chunk chunk = readChunkFromNBT(worldIn, nbttagcompound);
/*     */     
/*  93 */     if (!chunk.isAtLocation(x, z)) {
/*     */       
/*  95 */       logger.error("Chunk file at " + x + "," + z + " is in the wrong location; relocating. (Expected " + x + ", " + z + ", got " + chunk.xPosition + ", " + chunk.zPosition + ")");
/*  96 */       nbttagcompound.setInteger("xPos", x);
/*  97 */       nbttagcompound.setInteger("zPos", z);
/*  98 */       chunk = readChunkFromNBT(worldIn, nbttagcompound);
/*     */     } 
/*     */     
/* 101 */     return chunk;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveChunk(World worldIn, Chunk chunkIn) throws MinecraftException, IOException {
/* 108 */     worldIn.checkSessionLock();
/*     */ 
/*     */     
/*     */     try {
/* 112 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 113 */       NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 114 */       nbttagcompound.setTag("Level", (NBTBase)nbttagcompound1);
/* 115 */       writeChunkToNBT(chunkIn, worldIn, nbttagcompound1);
/* 116 */       addChunkToPending(chunkIn.getChunkCoordIntPair(), nbttagcompound);
/*     */     }
/* 118 */     catch (Exception exception) {
/*     */       
/* 120 */       logger.error("Failed to save chunk", exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addChunkToPending(ChunkCoordIntPair p_75824_1_, NBTTagCompound p_75824_2_) {
/* 126 */     if (!this.pendingAnvilChunksCoordinates.contains(p_75824_1_))
/*     */     {
/* 128 */       this.chunksToRemove.put(p_75824_1_, p_75824_2_);
/*     */     }
/*     */     
/* 131 */     ThreadedFileIOBase.getThreadedIOInstance().queueIO(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean writeNextIO() {
/*     */     boolean lvt_3_1_;
/* 139 */     if (this.chunksToRemove.isEmpty()) {
/*     */       
/* 141 */       if (this.field_183014_e)
/*     */       {
/* 143 */         logger.info("ThreadedAnvilChunkStorage ({}): All chunks are saved", new Object[] { this.chunkSaveLocation.getName() });
/*     */       }
/*     */       
/* 146 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 150 */     ChunkCoordIntPair chunkcoordintpair = this.chunksToRemove.keySet().iterator().next();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 155 */       this.pendingAnvilChunksCoordinates.add(chunkcoordintpair);
/* 156 */       NBTTagCompound nbttagcompound = this.chunksToRemove.remove(chunkcoordintpair);
/*     */       
/* 158 */       if (nbttagcompound != null) {
/*     */         
/*     */         try {
/*     */           
/* 162 */           func_183013_b(chunkcoordintpair, nbttagcompound);
/*     */         }
/* 164 */         catch (Exception exception) {
/*     */           
/* 166 */           logger.error("Failed to save chunk", exception);
/*     */         } 
/*     */       }
/*     */       
/* 170 */       lvt_3_1_ = true;
/*     */     }
/*     */     finally {
/*     */       
/* 174 */       this.pendingAnvilChunksCoordinates.remove(chunkcoordintpair);
/*     */     } 
/*     */     
/* 177 */     return lvt_3_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void func_183013_b(ChunkCoordIntPair p_183013_1_, NBTTagCompound p_183013_2_) throws IOException {
/* 183 */     DataOutputStream dataoutputstream = RegionFileCache.getChunkOutputStream(this.chunkSaveLocation, p_183013_1_.chunkXPos, p_183013_1_.chunkZPos);
/* 184 */     CompressedStreamTools.write(p_183013_2_, dataoutputstream);
/* 185 */     dataoutputstream.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveExtraChunkData(World worldIn, Chunk chunkIn) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void chunkTick() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveExtraData() {
/*     */     try {
/* 211 */       this.field_183014_e = true;
/*     */ 
/*     */       
/*     */       while (true) {
/* 215 */         if (writeNextIO());
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/*     */     finally {
/*     */       
/* 223 */       this.field_183014_e = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeChunkToNBT(Chunk chunkIn, World worldIn, NBTTagCompound p_75820_3_) {
/* 233 */     p_75820_3_.setByte("V", (byte)1);
/* 234 */     p_75820_3_.setInteger("xPos", chunkIn.xPosition);
/* 235 */     p_75820_3_.setInteger("zPos", chunkIn.zPosition);
/* 236 */     p_75820_3_.setLong("LastUpdate", worldIn.getTotalWorldTime());
/* 237 */     p_75820_3_.setIntArray("HeightMap", chunkIn.getHeightMap());
/* 238 */     p_75820_3_.setBoolean("TerrainPopulated", chunkIn.isTerrainPopulated());
/* 239 */     p_75820_3_.setBoolean("LightPopulated", chunkIn.isLightPopulated());
/* 240 */     p_75820_3_.setLong("InhabitedTime", chunkIn.getInhabitedTime());
/* 241 */     ExtendedBlockStorage[] aextendedblockstorage = chunkIn.getBlockStorageArray();
/* 242 */     NBTTagList nbttaglist = new NBTTagList();
/* 243 */     boolean flag = !worldIn.provider.getHasNoSky(); byte b; int i;
/*     */     ExtendedBlockStorage[] arrayOfExtendedBlockStorage1;
/* 245 */     for (i = (arrayOfExtendedBlockStorage1 = aextendedblockstorage).length, b = 0; b < i; ) { ExtendedBlockStorage extendedblockstorage = arrayOfExtendedBlockStorage1[b];
/*     */       
/* 247 */       if (extendedblockstorage != null) {
/*     */         
/* 249 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 250 */         nbttagcompound.setByte("Y", (byte)(extendedblockstorage.getYLocation() >> 4 & 0xFF));
/* 251 */         byte[] abyte = new byte[(extendedblockstorage.getData()).length];
/* 252 */         NibbleArray nibblearray = new NibbleArray();
/* 253 */         NibbleArray nibblearray1 = null;
/*     */         
/* 255 */         for (int j = 0; j < (extendedblockstorage.getData()).length; j++) {
/*     */           
/* 257 */           char c0 = extendedblockstorage.getData()[j];
/* 258 */           int m = j & 0xF;
/* 259 */           int k = j >> 8 & 0xF;
/* 260 */           int l = j >> 4 & 0xF;
/*     */           
/* 262 */           if (c0 >> 12 != 0) {
/*     */             
/* 264 */             if (nibblearray1 == null)
/*     */             {
/* 266 */               nibblearray1 = new NibbleArray();
/*     */             }
/*     */             
/* 269 */             nibblearray1.set(m, k, l, c0 >> 12);
/*     */           } 
/*     */           
/* 272 */           abyte[j] = (byte)(c0 >> 4 & 0xFF);
/* 273 */           nibblearray.set(m, k, l, c0 & 0xF);
/*     */         } 
/*     */         
/* 276 */         nbttagcompound.setByteArray("Blocks", abyte);
/* 277 */         nbttagcompound.setByteArray("Data", nibblearray.getData());
/*     */         
/* 279 */         if (nibblearray1 != null)
/*     */         {
/* 281 */           nbttagcompound.setByteArray("Add", nibblearray1.getData());
/*     */         }
/*     */         
/* 284 */         nbttagcompound.setByteArray("BlockLight", extendedblockstorage.getBlocklightArray().getData());
/*     */         
/* 286 */         if (flag) {
/*     */           
/* 288 */           nbttagcompound.setByteArray("SkyLight", extendedblockstorage.getSkylightArray().getData());
/*     */         }
/*     */         else {
/*     */           
/* 292 */           nbttagcompound.setByteArray("SkyLight", new byte[(extendedblockstorage.getBlocklightArray().getData()).length]);
/*     */         } 
/*     */         
/* 295 */         nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */       } 
/*     */       b++; }
/*     */     
/* 299 */     p_75820_3_.setTag("Sections", (NBTBase)nbttaglist);
/* 300 */     p_75820_3_.setByteArray("Biomes", chunkIn.getBiomeArray());
/* 301 */     chunkIn.setHasEntities(false);
/* 302 */     NBTTagList nbttaglist1 = new NBTTagList();
/*     */     
/* 304 */     for (int i1 = 0; i1 < (chunkIn.getEntityLists()).length; i1++) {
/*     */       
/* 306 */       for (Entity entity : chunkIn.getEntityLists()[i1]) {
/*     */         
/* 308 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*     */         
/* 310 */         if (entity.writeToNBTOptional(nbttagcompound1)) {
/*     */           
/* 312 */           chunkIn.setHasEntities(true);
/* 313 */           nbttaglist1.appendTag((NBTBase)nbttagcompound1);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 318 */     p_75820_3_.setTag("Entities", (NBTBase)nbttaglist1);
/* 319 */     NBTTagList nbttaglist2 = new NBTTagList();
/*     */     
/* 321 */     for (TileEntity tileentity : chunkIn.getTileEntityMap().values()) {
/*     */       
/* 323 */       NBTTagCompound nbttagcompound2 = new NBTTagCompound();
/* 324 */       tileentity.writeToNBT(nbttagcompound2);
/* 325 */       nbttaglist2.appendTag((NBTBase)nbttagcompound2);
/*     */     } 
/*     */     
/* 328 */     p_75820_3_.setTag("TileEntities", (NBTBase)nbttaglist2);
/* 329 */     List<NextTickListEntry> list = worldIn.getPendingBlockUpdates(chunkIn, false);
/*     */     
/* 331 */     if (list != null) {
/*     */       
/* 333 */       long j1 = worldIn.getTotalWorldTime();
/* 334 */       NBTTagList nbttaglist3 = new NBTTagList();
/*     */       
/* 336 */       for (NextTickListEntry nextticklistentry : list) {
/*     */         
/* 338 */         NBTTagCompound nbttagcompound3 = new NBTTagCompound();
/* 339 */         ResourceLocation resourcelocation = (ResourceLocation)Block.blockRegistry.getNameForObject(nextticklistentry.getBlock());
/* 340 */         nbttagcompound3.setString("i", (resourcelocation == null) ? "" : resourcelocation.toString());
/* 341 */         nbttagcompound3.setInteger("x", nextticklistentry.position.getX());
/* 342 */         nbttagcompound3.setInteger("y", nextticklistentry.position.getY());
/* 343 */         nbttagcompound3.setInteger("z", nextticklistentry.position.getZ());
/* 344 */         nbttagcompound3.setInteger("t", (int)(nextticklistentry.scheduledTime - j1));
/* 345 */         nbttagcompound3.setInteger("p", nextticklistentry.priority);
/* 346 */         nbttaglist3.appendTag((NBTBase)nbttagcompound3);
/*     */       } 
/*     */       
/* 349 */       p_75820_3_.setTag("TileTicks", (NBTBase)nbttaglist3);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Chunk readChunkFromNBT(World worldIn, NBTTagCompound p_75823_2_) {
/* 359 */     int i = p_75823_2_.getInteger("xPos");
/* 360 */     int j = p_75823_2_.getInteger("zPos");
/* 361 */     Chunk chunk = new Chunk(worldIn, i, j);
/* 362 */     chunk.setHeightMap(p_75823_2_.getIntArray("HeightMap"));
/* 363 */     chunk.setTerrainPopulated(p_75823_2_.getBoolean("TerrainPopulated"));
/* 364 */     chunk.setLightPopulated(p_75823_2_.getBoolean("LightPopulated"));
/* 365 */     chunk.setInhabitedTime(p_75823_2_.getLong("InhabitedTime"));
/* 366 */     NBTTagList nbttaglist = p_75823_2_.getTagList("Sections", 10);
/* 367 */     int k = 16;
/* 368 */     ExtendedBlockStorage[] aextendedblockstorage = new ExtendedBlockStorage[k];
/* 369 */     boolean flag = !worldIn.provider.getHasNoSky();
/*     */     
/* 371 */     for (int l = 0; l < nbttaglist.tagCount(); l++) {
/*     */       
/* 373 */       NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(l);
/* 374 */       int i1 = nbttagcompound.getByte("Y");
/* 375 */       ExtendedBlockStorage extendedblockstorage = new ExtendedBlockStorage(i1 << 4, flag);
/* 376 */       byte[] abyte = nbttagcompound.getByteArray("Blocks");
/* 377 */       NibbleArray nibblearray = new NibbleArray(nbttagcompound.getByteArray("Data"));
/* 378 */       NibbleArray nibblearray1 = nbttagcompound.hasKey("Add", 7) ? new NibbleArray(nbttagcompound.getByteArray("Add")) : null;
/* 379 */       char[] achar = new char[abyte.length];
/*     */       
/* 381 */       for (int j1 = 0; j1 < achar.length; j1++) {
/*     */         
/* 383 */         int k1 = j1 & 0xF;
/* 384 */         int l1 = j1 >> 8 & 0xF;
/* 385 */         int i2 = j1 >> 4 & 0xF;
/* 386 */         int j2 = (nibblearray1 != null) ? nibblearray1.get(k1, l1, i2) : 0;
/* 387 */         achar[j1] = (char)(j2 << 12 | (abyte[j1] & 0xFF) << 4 | nibblearray.get(k1, l1, i2));
/*     */       } 
/*     */       
/* 390 */       extendedblockstorage.setData(achar);
/* 391 */       extendedblockstorage.setBlocklightArray(new NibbleArray(nbttagcompound.getByteArray("BlockLight")));
/*     */       
/* 393 */       if (flag)
/*     */       {
/* 395 */         extendedblockstorage.setSkylightArray(new NibbleArray(nbttagcompound.getByteArray("SkyLight")));
/*     */       }
/*     */       
/* 398 */       extendedblockstorage.removeInvalidBlocks();
/* 399 */       aextendedblockstorage[i1] = extendedblockstorage;
/*     */     } 
/*     */     
/* 402 */     chunk.setStorageArrays(aextendedblockstorage);
/*     */     
/* 404 */     if (p_75823_2_.hasKey("Biomes", 7))
/*     */     {
/* 406 */       chunk.setBiomeArray(p_75823_2_.getByteArray("Biomes"));
/*     */     }
/*     */     
/* 409 */     NBTTagList nbttaglist1 = p_75823_2_.getTagList("Entities", 10);
/*     */     
/* 411 */     if (nbttaglist1 != null)
/*     */     {
/* 413 */       for (int k2 = 0; k2 < nbttaglist1.tagCount(); k2++) {
/*     */         
/* 415 */         NBTTagCompound nbttagcompound1 = nbttaglist1.getCompoundTagAt(k2);
/* 416 */         Entity entity = EntityList.createEntityFromNBT(nbttagcompound1, worldIn);
/* 417 */         chunk.setHasEntities(true);
/*     */         
/* 419 */         if (entity != null) {
/*     */           
/* 421 */           chunk.addEntity(entity);
/* 422 */           Entity entity1 = entity;
/*     */           
/* 424 */           for (NBTTagCompound nbttagcompound4 = nbttagcompound1; nbttagcompound4.hasKey("Riding", 10); nbttagcompound4 = nbttagcompound4.getCompoundTag("Riding")) {
/*     */             
/* 426 */             Entity entity2 = EntityList.createEntityFromNBT(nbttagcompound4.getCompoundTag("Riding"), worldIn);
/*     */             
/* 428 */             if (entity2 != null) {
/*     */               
/* 430 */               chunk.addEntity(entity2);
/* 431 */               entity1.mountEntity(entity2);
/*     */             } 
/*     */             
/* 434 */             entity1 = entity2;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 440 */     NBTTagList nbttaglist2 = p_75823_2_.getTagList("TileEntities", 10);
/*     */     
/* 442 */     if (nbttaglist2 != null)
/*     */     {
/* 444 */       for (int l2 = 0; l2 < nbttaglist2.tagCount(); l2++) {
/*     */         
/* 446 */         NBTTagCompound nbttagcompound2 = nbttaglist2.getCompoundTagAt(l2);
/* 447 */         TileEntity tileentity = TileEntity.createAndLoadEntity(nbttagcompound2);
/*     */         
/* 449 */         if (tileentity != null)
/*     */         {
/* 451 */           chunk.addTileEntity(tileentity);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 456 */     if (p_75823_2_.hasKey("TileTicks", 9)) {
/*     */       
/* 458 */       NBTTagList nbttaglist3 = p_75823_2_.getTagList("TileTicks", 10);
/*     */       
/* 460 */       if (nbttaglist3 != null)
/*     */       {
/* 462 */         for (int i3 = 0; i3 < nbttaglist3.tagCount(); i3++) {
/*     */           Block block;
/* 464 */           NBTTagCompound nbttagcompound3 = nbttaglist3.getCompoundTagAt(i3);
/*     */ 
/*     */           
/* 467 */           if (nbttagcompound3.hasKey("i", 8)) {
/*     */             
/* 469 */             block = Block.getBlockFromName(nbttagcompound3.getString("i"));
/*     */           }
/*     */           else {
/*     */             
/* 473 */             block = Block.getBlockById(nbttagcompound3.getInteger("i"));
/*     */           } 
/*     */           
/* 476 */           worldIn.scheduleBlockUpdate(new BlockPos(nbttagcompound3.getInteger("x"), nbttagcompound3.getInteger("y"), nbttagcompound3.getInteger("z")), block, nbttagcompound3.getInteger("t"), nbttagcompound3.getInteger("p"));
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 481 */     return chunk;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\world\chunk\storage\AnvilChunkLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */