package vestige.util.world;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class BlockInfo {
   private BlockPos pos;
   private EnumFacing facing;

   public BlockPos getPos() {
      return this.pos;
   }

   public EnumFacing getFacing() {
      return this.facing;
   }

   public BlockInfo(BlockPos pos, EnumFacing facing) {
      this.pos = pos;
      this.facing = facing;
   }
}
