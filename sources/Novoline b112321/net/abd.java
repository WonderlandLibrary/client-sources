package net;

import java.util.Iterator;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BlockPos$2$1;

final class abd implements Iterable {
   final BlockPos a;
   final BlockPos b;

   abd(BlockPos var1, BlockPos var2) {
      this.a = var1;
      this.b = var2;
   }

   public Iterator iterator() {
      return new BlockPos$2$1(this);
   }
}
