/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.texture;

import net.optifine.texture.IBlender;
import net.optifine.texture.IColorBlender;

public class ColorBlenderSeparate
implements IColorBlender {
    private IBlender blenderR;
    private IBlender blenderG;
    private IBlender blenderB;
    private IBlender blenderA;

    public ColorBlenderSeparate(IBlender iBlender, IBlender iBlender2, IBlender iBlender3, IBlender iBlender4) {
        this.blenderR = iBlender;
        this.blenderG = iBlender2;
        this.blenderB = iBlender3;
        this.blenderA = iBlender4;
    }

    @Override
    public int blend(int n, int n2, int n3, int n4) {
        int n5 = n >> 24 & 0xFF;
        int n6 = n >> 16 & 0xFF;
        int n7 = n >> 8 & 0xFF;
        int n8 = n & 0xFF;
        int n9 = n2 >> 24 & 0xFF;
        int n10 = n2 >> 16 & 0xFF;
        int n11 = n2 >> 8 & 0xFF;
        int n12 = n2 & 0xFF;
        int n13 = n3 >> 24 & 0xFF;
        int n14 = n3 >> 16 & 0xFF;
        int n15 = n3 >> 8 & 0xFF;
        int n16 = n3 & 0xFF;
        int n17 = n4 >> 24 & 0xFF;
        int n18 = n4 >> 16 & 0xFF;
        int n19 = n4 >> 8 & 0xFF;
        int n20 = n4 & 0xFF;
        int n21 = this.blenderA.blend(n5, n9, n13, n17);
        int n22 = this.blenderR.blend(n6, n10, n14, n18);
        int n23 = this.blenderG.blend(n7, n11, n15, n19);
        int n24 = this.blenderB.blend(n8, n12, n16, n20);
        return n21 << 24 | n22 << 16 | n23 << 8 | n24;
    }
}

