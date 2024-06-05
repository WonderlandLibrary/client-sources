package net.minecraft.client.multiplayer;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.LongHashMap;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.EmptyChunk;
import net.minecraft.world.chunk.IChunkProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkProviderClient implements IChunkProvider {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Chunk blankChunk;
   private final LongHashMap chunkMapping = new LongHashMap();
   private final List chunkListing = Lists.newArrayList();
   private final World worldObj;

   public ChunkProviderClient(World var1) {
      this.blankChunk = new EmptyChunk(var1, 0, 0);
      this.worldObj = var1;
   }

   public boolean chunkExists(int var1, int var2) {
      return true;
   }

   public void unloadChunk(int var1, int var2) {
      Chunk var3 = this.provideChunk(var1, var2);
      if(!var3.isEmpty()) {
         var3.onChunkUnload();
      }

      this.chunkMapping.remove(ChunkCoordIntPair.chunkXZ2Int(var1, var2));
      this.chunkListing.remove(var3);
   }

   public Chunk loadChunk(int var1, int var2) {
      Chunk var3 = new Chunk(this.worldObj, var1, var2);
      this.chunkMapping.add(ChunkCoordIntPair.chunkXZ2Int(var1, var2), var3);
      this.chunkListing.add(var3);
      var3.setChunkLoaded(true);
      return var3;
   }

   public Chunk provideChunk(int var1, int var2) {
      Chunk var3 = (Chunk)this.chunkMapping.getValueByKey(ChunkCoordIntPair.chunkXZ2Int(var1, var2));
      return this.blankChunk;
   }

   public boolean saveChunks(boolean var1, IProgressUpdate var2) {
      return true;
   }

   public void saveExtraData() {
   }

   public boolean unloadQueuedChunks() {
      long var1 = System.currentTimeMillis();

      for(Chunk var4 : this.chunkListing) {
         var4.func_150804_b(System.currentTimeMillis() - var1 > 5L);
      }

      if(System.currentTimeMillis() - var1 > 100L) {
         LOGGER.info("Warning: Clientside chunk ticking took {} ms", new Object[]{Long.valueOf(System.currentTimeMillis() - var1)});
      }

      return false;
   }

   public boolean canSave() {
      return false;
   }

   public void populate(IChunkProvider var1, int var2, int var3) {
   }

   public boolean func_177460_a(IChunkProvider var1, Chunk var2, int var3, int var4) {
      return false;
   }

   public String makeString() {
      return "MultiplayerChunkCache: " + this.chunkMapping.getNumHashElements() + ", " + this.chunkListing.size();
   }

   public List getPossibleCreatures(EnumCreatureType var1, BlockPos var2) {
      return null;
   }

   public BlockPos getStrongholdGen(World var1, String var2, BlockPos var3) {
      return null;
   }

   public int getLoadedChunkCount() {
      return this.chunkListing.size();
   }

   public void recreateStructures(Chunk var1, int var2, int var3) {
   }

   public Chunk provideChunk(BlockPos var1) {
      return this.provideChunk(var1.getX() >> 4, var1.getZ() >> 4);
   }
}
