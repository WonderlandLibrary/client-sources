/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.chunk.storage;

import java.util.BitSet;

public class RegionBitmap {
    private final BitSet field_227118_a_ = new BitSet();

    public void func_227120_a_(int n, int n2) {
        this.field_227118_a_.set(n, n + n2);
    }

    public void func_227121_b_(int n, int n2) {
        this.field_227118_a_.clear(n, n + n2);
    }

    public int func_227119_a_(int n) {
        int n2 = 0;
        while (true) {
            int n3;
            int n4;
            if ((n4 = this.field_227118_a_.nextSetBit(n3 = this.field_227118_a_.nextClearBit(n2))) == -1 || n4 - n3 >= n) {
                this.func_227120_a_(n3, n);
                return n3;
            }
            n2 = n4;
        }
    }
}

