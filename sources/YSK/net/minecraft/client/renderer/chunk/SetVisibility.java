package net.minecraft.client.renderer.chunk;

import net.minecraft.util.*;
import java.util.*;

public class SetVisibility
{
    private static final String[] I;
    private static final int COUNT_FACES;
    private final BitSet bitSet;
    
    public boolean isVisible(final EnumFacing enumFacing, final EnumFacing enumFacing2) {
        return this.bitSet.get(enumFacing.ordinal() + enumFacing2.ordinal() * SetVisibility.COUNT_FACES);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("ZI", "zigst");
    }
    
    public void setManyVisible(final Set<EnumFacing> set) {
        final Iterator<EnumFacing> iterator = set.iterator();
        "".length();
        if (3 <= 1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final EnumFacing enumFacing = iterator.next();
            final Iterator<EnumFacing> iterator2 = set.iterator();
            "".length();
            if (4 != 4) {
                throw null;
            }
            while (iterator2.hasNext()) {
                this.setVisible(enumFacing, iterator2.next(), " ".length() != 0);
            }
        }
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append((char)(0xB3 ^ 0x93));
        final EnumFacing[] values;
        final int length = (values = EnumFacing.values()).length;
        int i = "".length();
        "".length();
        if (3 < 2) {
            throw null;
        }
        while (i < length) {
            sb.append((char)(0x1D ^ 0x3D)).append(values[i].toString().toUpperCase().charAt("".length()));
            ++i;
        }
        sb.append((char)(0x91 ^ 0x9B));
        final EnumFacing[] values2;
        final int length2 = (values2 = EnumFacing.values()).length;
        int j = "".length();
        "".length();
        if (1 < 0) {
            throw null;
        }
        while (j < length2) {
            final EnumFacing enumFacing = values2[j];
            sb.append(enumFacing.toString().toUpperCase().charAt("".length()));
            final EnumFacing[] values3;
            final int length3 = (values3 = EnumFacing.values()).length;
            int k = "".length();
            "".length();
            if (false) {
                throw null;
            }
            while (k < length3) {
                final EnumFacing enumFacing2 = values3[k];
                if (enumFacing == enumFacing2) {
                    sb.append(SetVisibility.I["".length()]);
                    "".length();
                    if (4 < 1) {
                        throw null;
                    }
                }
                else {
                    final boolean visible = this.isVisible(enumFacing, enumFacing2);
                    final StringBuilder append = sb.append((char)(0xB5 ^ 0x95));
                    int n;
                    if (visible) {
                        n = (0x26 ^ 0x7F);
                        "".length();
                        if (3 != 3) {
                            throw null;
                        }
                    }
                    else {
                        n = (0x45 ^ 0x2B);
                    }
                    append.append((char)n);
                }
                ++k;
            }
            sb.append((char)(0xB1 ^ 0xBB));
            ++j;
        }
        return sb.toString();
    }
    
    public SetVisibility() {
        this.bitSet = new BitSet(SetVisibility.COUNT_FACES * SetVisibility.COUNT_FACES);
    }
    
    public void setAllVisible(final boolean b) {
        this.bitSet.set("".length(), this.bitSet.size(), b);
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (true != true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
        COUNT_FACES = EnumFacing.values().length;
    }
    
    public void setVisible(final EnumFacing enumFacing, final EnumFacing enumFacing2, final boolean b) {
        this.bitSet.set(enumFacing.ordinal() + enumFacing2.ordinal() * SetVisibility.COUNT_FACES, b);
        this.bitSet.set(enumFacing2.ordinal() + enumFacing.ordinal() * SetVisibility.COUNT_FACES, b);
    }
}
