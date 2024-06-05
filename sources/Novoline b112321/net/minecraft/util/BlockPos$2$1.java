package net.minecraft.util;

import com.google.common.collect.AbstractIterator;
import net.abd;
import net.minecraft.util.BlockPos$MutableBlockPos;

class BlockPos$2$1 extends AbstractIterator {
   private BlockPos$MutableBlockPos theBlockPos;
   final abd b;

   BlockPos$2$1(abd var1) {
      this.b = var1;
      this.theBlockPos = null;
   }

   protected BlockPos$MutableBlockPos computeNext() {
      if(this.theBlockPos == null) {
         this.theBlockPos = new BlockPos$MutableBlockPos(this.b.a.getX(), this.b.a.getY(), this.b.a.getZ());
         return this.theBlockPos;
      } else if(this.theBlockPos.equals(this.b.b)) {
         return (BlockPos$MutableBlockPos)this.endOfData();
      } else {
         int var1 = this.theBlockPos.getX();
         int var2 = this.theBlockPos.getY();
         int var3 = this.theBlockPos.getZ();
         if(var1 < this.b.b.getX()) {
            ++var1;
         } else if(var2 < this.b.b.getY()) {
            var1 = this.b.a.getX();
            ++var2;
         } else if(var3 < this.b.b.getZ()) {
            var1 = this.b.a.getX();
            var2 = this.b.a.getY();
            ++var3;
         }

         BlockPos$MutableBlockPos.access$002(this.theBlockPos, var1);
         BlockPos$MutableBlockPos.access$102(this.theBlockPos, var2);
         BlockPos$MutableBlockPos.access$202(this.theBlockPos, var3);
         return this.theBlockPos;
      }
   }
}
