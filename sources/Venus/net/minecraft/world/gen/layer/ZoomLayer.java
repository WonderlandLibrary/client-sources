/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.layer;

import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.traits.IAreaTransformer1;

/*
 * Uses 'sealed' constructs - enablewith --sealed true
 */
public enum ZoomLayer implements IAreaTransformer1
{
    NORMAL,
    FUZZY{

        @Override
        protected int pickZoomed(IExtendedNoiseRandom<?> iExtendedNoiseRandom, int n, int n2, int n3, int n4) {
            return iExtendedNoiseRandom.pickRandom(n, n2, n3, n4);
        }
    };


    @Override
    public int getOffsetX(int n) {
        return n >> 1;
    }

    @Override
    public int getOffsetZ(int n) {
        return n >> 1;
    }

    @Override
    public int apply(IExtendedNoiseRandom<?> iExtendedNoiseRandom, IArea iArea, int n, int n2) {
        int n3 = iArea.getValue(this.getOffsetX(n), this.getOffsetZ(n2));
        iExtendedNoiseRandom.setPosition(n >> 1 << 1, n2 >> 1 << 1);
        int n4 = n & 1;
        int n5 = n2 & 1;
        if (n4 == 0 && n5 == 0) {
            return n3;
        }
        int n6 = iArea.getValue(this.getOffsetX(n), this.getOffsetZ(n2 + 1));
        int n7 = iExtendedNoiseRandom.pickRandom(n3, n6);
        if (n4 == 0 && n5 == 1) {
            return n7;
        }
        int n8 = iArea.getValue(this.getOffsetX(n + 1), this.getOffsetZ(n2));
        int n9 = iExtendedNoiseRandom.pickRandom(n3, n8);
        if (n4 == 1 && n5 == 0) {
            return n9;
        }
        int n10 = iArea.getValue(this.getOffsetX(n + 1), this.getOffsetZ(n2 + 1));
        return this.pickZoomed(iExtendedNoiseRandom, n3, n8, n6, n10);
    }

    protected int pickZoomed(IExtendedNoiseRandom<?> iExtendedNoiseRandom, int n, int n2, int n3, int n4) {
        if (n2 == n3 && n3 == n4) {
            return n2;
        }
        if (n == n2 && n == n3) {
            return n;
        }
        if (n == n2 && n == n4) {
            return n;
        }
        if (n == n3 && n == n4) {
            return n;
        }
        if (n == n2 && n3 != n4) {
            return n;
        }
        if (n == n3 && n2 != n4) {
            return n;
        }
        if (n == n4 && n2 != n3) {
            return n;
        }
        if (n2 == n3 && n != n4) {
            return n2;
        }
        if (n2 == n4 && n != n3) {
            return n2;
        }
        return n3 == n4 && n != n2 ? n3 : iExtendedNoiseRandom.pickRandom(n, n2, n3, n4);
    }
}

