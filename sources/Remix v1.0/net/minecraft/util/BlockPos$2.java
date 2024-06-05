package net.minecraft.util;

import java.util.*;
import com.google.common.collect.*;

static final class BlockPos$2 implements Iterable {
    @Override
    public Iterator iterator() {
        return (Iterator)new AbstractIterator() {
            private MutableBlockPos theBlockPos = null;
            
            protected MutableBlockPos computeNext0() {
                if (this.theBlockPos == null) {
                    return this.theBlockPos = new MutableBlockPos(BlockPos.this.getX(), BlockPos.this.getY(), BlockPos.this.getZ(), null);
                }
                if (this.theBlockPos.equals(BlockPos.this)) {
                    return (MutableBlockPos)this.endOfData();
                }
                int var1 = this.theBlockPos.getX();
                int var2xx = this.theBlockPos.getY();
                int var3x = this.theBlockPos.getZ();
                if (var1 < BlockPos.this.getX()) {
                    ++var1;
                }
                else if (var2xx < BlockPos.this.getY()) {
                    var1 = BlockPos.this.getX();
                    ++var2xx;
                }
                else if (var3x < BlockPos.this.getZ()) {
                    var1 = BlockPos.this.getX();
                    var2xx = BlockPos.this.getY();
                    ++var3x;
                }
                this.theBlockPos.x = var1;
                this.theBlockPos.y = var2xx;
                this.theBlockPos.z = var3x;
                return this.theBlockPos;
            }
            
            protected Object computeNext() {
                return this.computeNext0();
            }
        };
    }
}