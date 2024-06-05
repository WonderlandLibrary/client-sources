package net;

import java.util.Iterator;
import net.minecraft.util.BlockPos;
import net.optifine.BlockPosM$1$1;

final class q3 implements Iterable {
   final BlockPos b;
   final BlockPos a;

   q3(BlockPos var1, BlockPos var2) {
      this.b = var1;
      this.a = var2;
   }

   public Iterator iterator() {
      return new BlockPosM$1$1(this);
   }
}
