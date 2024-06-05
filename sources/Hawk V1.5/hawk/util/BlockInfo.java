package hawk.util;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class BlockInfo {
   public BlockPos position;
   public EnumFacing face;

   public BlockInfo(BlockPos var1, EnumFacing var2) {
      this.position = var1;
      this.face = var2;
   }
}
