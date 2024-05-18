package baritone;

import baritone.api.utils.*;

public final class dp implements ISchematic
{
    private int a;
    private int b;
    private int c;
    private awt[][][] a;
    
    public dp(final fy fy) {
        super();
        final String l;
        if (!(l = fy.l("Materials")).equals("Alpha")) {
            throw new IllegalStateException("bad schematic ".concat(String.valueOf(l)));
        }
        this.a = fy.h("Width");
        this.b = fy.h("Height");
        this.c = fy.h("Length");
        final byte[] m = fy.m("Blocks");
        final byte[] i = fy.m("Data");
        byte[] array = null;
        if (fy.e("AddBlocks")) {
            final byte[] j;
            array = new byte[(j = fy.m("AddBlocks")).length << 1];
            for (int k = 0; k < j.length; ++k) {
                array[k << 1] = (byte)(j[k] >> 4 & 0xF);
                array[(k << 1) + 1] = (byte)(j[k] & 0xF);
            }
        }
        this.a = new awt[this.a][this.c][this.b];
        for (int n = 0; n < this.b; ++n) {
            for (int n2 = 0; n2 < this.c; ++n2) {
                for (int n3 = 0; n3 < this.a; ++n3) {
                    final int n4 = (n * this.c + n2) * this.a + n3;
                    int n5 = m[n4] & 0xFF;
                    if (array != null) {
                        n5 |= array[n4] << 8;
                    }
                    this.a[n3][n2][n] = ((aow)aow.h.a(n5)).a(i[n4] & 0xFF);
                }
            }
        }
    }
    
    @Override
    public final awt desiredState(final int n, final int n2, final int n3) {
        return this.a[n][n3][n2];
    }
    
    @Override
    public final int widthX() {
        return this.a;
    }
    
    @Override
    public final int heightY() {
        return this.b;
    }
    
    @Override
    public final int lengthZ() {
        return this.c;
    }
}
