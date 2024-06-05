package net.minecraft.block.state.pattern;

import com.google.common.cache.CacheLoader;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

class BlockPattern$CacheLoader extends CacheLoader {
   private final World world;
   private final boolean field_181626_b;

   public BlockPattern$CacheLoader(World var1, boolean var2) {
      this.world = var1;
      this.field_181626_b = var2;
   }

   public BlockWorldState load(BlockPos var1) throws Exception {
      return new BlockWorldState(this.world, var1, this.field_181626_b);
   }
}
