package net.minecraft.util;

import java.util.*;
import com.google.common.collect.*;

static final class BlockPos$1 implements Iterable<BlockPos> {
    @Override
    public Iterator<BlockPos> iterator() {
        return (Iterator<BlockPos>)new AbstractIterator<BlockPos>() {
            private BlockPos lastReturned = null;
            
            protected BlockPos computeNext() {
                if (this.lastReturned == null) {
                    return this.lastReturned = BlockPos.this;
                }
                if (this.lastReturned.equals(BlockPos.this)) {
                    return (BlockPos)this.endOfData();
                }
                int i = this.lastReturned.getX();
                int j = this.lastReturned.getY();
                int k = this.lastReturned.getZ();
                if (i < BlockPos.this.getX()) {
                    ++i;
                }
                else if (j < BlockPos.this.getY()) {
                    i = BlockPos.this.getX();
                    ++j;
                }
                else if (k < BlockPos.this.getZ()) {
                    i = BlockPos.this.getX();
                    j = BlockPos.this.getY();
                    ++k;
                }
                return this.lastReturned = new BlockPos(i, j, k);
            }
        };
    }
}