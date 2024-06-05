package optifine;

import net.minecraft.util.*;
import java.util.*;
import com.google.common.collect.*;

static final class BlockPosM$1 implements Iterable {
    final /* synthetic */ BlockPos val$posFrom;
    final /* synthetic */ BlockPos val$posTo;
    
    @Override
    public Iterator iterator() {
        return (Iterator)new AbstractIterator() {
            private BlockPosM theBlockPosM = null;
            
            protected BlockPosM computeNext0() {
                if (this.theBlockPosM == null) {
                    return this.theBlockPosM = new BlockPosM(Iterable.this.val$posFrom.getX(), Iterable.this.val$posFrom.getY(), Iterable.this.val$posFrom.getZ(), 3);
                }
                if (this.theBlockPosM.equals(Iterable.this.val$posTo)) {
                    return (BlockPosM)this.endOfData();
                }
                int bx = this.theBlockPosM.getX();
                int by = this.theBlockPosM.getY();
                int bz = this.theBlockPosM.getZ();
                if (bx < Iterable.this.val$posTo.getX()) {
                    ++bx;
                }
                else if (by < Iterable.this.val$posTo.getY()) {
                    bx = Iterable.this.val$posFrom.getX();
                    ++by;
                }
                else if (bz < Iterable.this.val$posTo.getZ()) {
                    bx = Iterable.this.val$posFrom.getX();
                    by = Iterable.this.val$posFrom.getY();
                    ++bz;
                }
                this.theBlockPosM.setXyz(bx, by, bz);
                return this.theBlockPosM;
            }
            
            protected Object computeNext() {
                return this.computeNext0();
            }
        };
    }
}