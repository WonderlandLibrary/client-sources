package net;

import java.util.List;
import net.minecraft.block.state.BlockPistonStructureHelper;

public class bk {
   public static boolean a(BlockPistonStructureHelper var0) {
      return var0.canMove();
   }

   public static List c(BlockPistonStructureHelper var0) {
      return var0.getBlocksToMove();
   }

   public static List b(BlockPistonStructureHelper var0) {
      return var0.getBlocksToDestroy();
   }
}
