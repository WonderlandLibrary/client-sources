package cc.novoline.utils.minecraft;

import java.util.List;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;

public class FakeWorld$FakeChunkProvider implements IChunkProvider {
   public boolean chunkExists(int var1, int var2) {
      return false;
   }

   public Chunk provideChunk(int var1, int var2) {
      return null;
   }

   public Chunk provideChunk(BlockPos var1) {
      return null;
   }

   public void populate(IChunkProvider var1, int var2, int var3) {
   }

   public boolean func_177460_a(IChunkProvider var1, Chunk var2, int var3, int var4) {
      return false;
   }

   public boolean saveChunks(boolean var1, IProgressUpdate var2) {
      return false;
   }

   public boolean unloadQueuedChunks() {
      return false;
   }

   public boolean canSave() {
      return false;
   }

   public String makeString() {
      return null;
   }

   public List getPossibleCreatures(EnumCreatureType var1, BlockPos var2) {
      return null;
   }

   public BlockPos getStrongholdGen(World var1, String var2, BlockPos var3) {
      return null;
   }

   public int getLoadedChunkCount() {
      return 0;
   }

   public void recreateStructures(Chunk var1, int var2, int var3) {
   }

   public void saveExtraData() {
   }
}
