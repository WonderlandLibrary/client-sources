package net.optifine;

import com.google.common.collect.AbstractIterator;
import net.acE;
import net.q3;
import net.optifine.BlockPosM;
import net.optifine.MatchBlock;

class BlockPosM$1$1 extends AbstractIterator {
   private BlockPosM theBlockPosM;
   final q3 b;

   BlockPosM$1$1(q3 var1) {
      this.b = var1;
      this.theBlockPosM = null;
   }

   protected BlockPosM computeNext0() {
      acE[] var1 = MatchBlock.b();
      if(this.theBlockPosM == null) {
         this.theBlockPosM = new BlockPosM(this.b.b.getX(), this.b.b.getY(), this.b.b.getZ(), 3);
         return this.theBlockPosM;
      } else if(this.theBlockPosM.equals(this.b.a)) {
         return (BlockPosM)this.endOfData();
      } else {
         int var2 = this.theBlockPosM.getX();
         int var3 = this.theBlockPosM.getY();
         int var4 = this.theBlockPosM.getZ();
         if(var2 < this.b.a.getX()) {
            ++var2;
         }

         if(var3 < this.b.a.getY()) {
            var2 = this.b.b.getX();
            ++var3;
         }

         if(var4 < this.b.a.getZ()) {
            var2 = this.b.b.getX();
            var3 = this.b.b.getY();
            ++var4;
         }

         this.theBlockPosM.setXyz(var2, var3, var4);
         return this.theBlockPosM;
      }
   }

   protected Object computeNext() {
      return this.computeNext0();
   }
}
