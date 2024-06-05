package net.minecraft.util;

import com.google.common.collect.AbstractIterator;
import net.aYA;
import net.minecraft.util.BlockPos;

class BlockPos$1$1 extends AbstractIterator {
   private BlockPos lastReturned;
   final aYA a;

   BlockPos$1$1(aYA var1) {
      this.a = var1;
      this.lastReturned = null;
   }

   protected BlockPos computeNext() {
      if(this.lastReturned == null) {
         this.lastReturned = this.a.a;
         return this.lastReturned;
      } else if(this.lastReturned.equals(this.a.b)) {
         return (BlockPos)this.endOfData();
      } else {
         int var1 = this.lastReturned.getX();
         int var2 = this.lastReturned.getY();
         int var3 = this.lastReturned.getZ();
         if(var1 < this.a.b.getX()) {
            ++var1;
         } else if(var2 < this.a.b.getY()) {
            var1 = this.a.a.getX();
            ++var2;
         } else if(var3 < this.a.b.getZ()) {
            var1 = this.a.a.getX();
            var2 = this.a.a.getY();
            ++var3;
         }

         this.lastReturned = new BlockPos(var1, var2, var3);
         return this.lastReturned;
      }
   }
}
