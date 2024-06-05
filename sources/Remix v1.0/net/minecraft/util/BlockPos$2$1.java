package net.minecraft.util;

import com.google.common.collect.*;

class BlockPos$2$1 extends AbstractIterator {
    private MutableBlockPos theBlockPos = null;
    
    protected MutableBlockPos computeNext0() {
        if (this.theBlockPos == null) {
            return this.theBlockPos = new MutableBlockPos(Iterable.this.val$var2.getX(), Iterable.this.val$var2.getY(), Iterable.this.val$var2.getZ(), null);
        }
        if (this.theBlockPos.equals(Iterable.this.val$var3)) {
            return (MutableBlockPos)this.endOfData();
        }
        int var1 = this.theBlockPos.getX();
        int var2xx = this.theBlockPos.getY();
        int var3x = this.theBlockPos.getZ();
        if (var1 < Iterable.this.val$var3.getX()) {
            ++var1;
        }
        else if (var2xx < Iterable.this.val$var3.getY()) {
            var1 = Iterable.this.val$var2.getX();
            ++var2xx;
        }
        else if (var3x < Iterable.this.val$var3.getZ()) {
            var1 = Iterable.this.val$var2.getX();
            var2xx = Iterable.this.val$var2.getY();
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
}