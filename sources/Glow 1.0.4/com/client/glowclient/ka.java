package com.client.glowclient;

import com.google.common.collect.*;
import java.util.*;
import net.minecraft.util.math.*;
import com.client.glowclient.utils.*;
import net.minecraft.init.*;
import net.minecraft.util.*;

public final class Ka extends AbstractIterator<BlockPos>
{
    private ArrayDeque M;
    private HashSet G;
    public final boolean d;
    public final double L;
    public final BlockPos A;
    public final Vec3d B;
    public final O b;
    
    public Object computeNext() {
        return this.computeNext();
    }
    
    public Ka(final BlockPos a, final Vec3d b, final double l, final boolean d, final O b2) {
        this.A = a;
        this.B = b;
        this.L = l;
        this.d = d;
        this.b = b2;
        super();
        this.M = new ArrayDeque((Collection<? extends E>)Arrays.asList(this.A));
        this.G = new HashSet();
    }
    
    public BlockPos computeNext() {
    Label_0000:
        while (true) {
            Ka ka = this;
            while (!ka.M.isEmpty()) {
                final BlockPos blockPos = this.M.pop();
                if (this.B.squareDistanceTo(new Vec3d((Vec3i)blockPos)) > this.L) {
                    ka = this;
                }
                else {
                    final boolean b = Wrapper.mc.world.getBlockState(blockPos) != Blocks.AIR;
                    if (this.d || !b) {
                        final EnumFacing[] values;
                        final int length = (values = EnumFacing.values()).length;
                        int n;
                        int i = n = 0;
                        while (i < length) {
                            final BlockPos offset = blockPos.offset(values[n]);
                            if (!this.G.contains(offset)) {
                                this.M.add(offset);
                                this.G.add(offset);
                            }
                            i = ++n;
                        }
                    }
                    if (!b) {
                        continue Label_0000;
                    }
                    if (this.b.M(blockPos)) {
                        return blockPos;
                    }
                    ka = this;
                }
            }
            return this.endOfData();
        }
    }
}
