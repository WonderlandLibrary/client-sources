package net.minecraft.util;

import com.google.common.collect.*;

class BlockPos$1$1 extends AbstractIterator<BlockPos> {
    private BlockPos lastReturned = null;
    
    protected BlockPos computeNext() {
        if (this.lastReturned == null) {
            return this.lastReturned = Iterable.this.val$blockpos;
        }
        if (this.lastReturned.equals(Iterable.this.val$blockpos1)) {
            return (BlockPos)this.endOfData();
        }
        int i = this.lastReturned.getX();
        int j = this.lastReturned.getY();
        int k = this.lastReturned.getZ();
        if (i < Iterable.this.val$blockpos1.getX()) {
            ++i;
        }
        else if (j < Iterable.this.val$blockpos1.getY()) {
            i = Iterable.this.val$blockpos.getX();
            ++j;
        }
        else if (k < Iterable.this.val$blockpos1.getZ()) {
            i = Iterable.this.val$blockpos.getX();
            j = Iterable.this.val$blockpos.getY();
            ++k;
        }
        return this.lastReturned = new BlockPos(i, j, k);
    }
}