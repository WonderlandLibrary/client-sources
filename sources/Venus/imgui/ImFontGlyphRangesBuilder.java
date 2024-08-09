/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui;

import java.util.ArrayList;
import java.util.Arrays;

public final class ImFontGlyphRangesBuilder {
    private static final int UNICODE_CODEPOINT_MAX = 65535;
    private final long[] usedChars = new long[8192];

    public ImFontGlyphRangesBuilder() {
        this.clear();
    }

    public void addChar(char c) {
        this.setBit(c);
    }

    public void addText(String string) {
        for (int i = 0; i < string.length(); ++i) {
            this.addChar(string.charAt(i));
        }
    }

    public void addRanges(short[] sArray) {
        for (int i = 0; i < sArray.length && sArray[i] != 0; i += 2) {
            for (int j = sArray[i]; j <= sArray[i + 1]; ++j) {
                this.addChar((char)j);
            }
        }
    }

    public short[] buildRanges() {
        ArrayList<Short> arrayList = new ArrayList<Short>();
        for (int i = 0; i <= 65535; ++i) {
            if (!this.getBit(i)) continue;
            arrayList.add((short)i);
            while (i < 65535 && this.getBit(i + 1)) {
                ++i;
            }
            arrayList.add((short)i);
        }
        short[] sArray = new short[arrayList.size() + 1];
        for (int i = 0; i < arrayList.size(); ++i) {
            sArray[i] = (Short)arrayList.get(i);
        }
        sArray[sArray.length - 1] = 0;
        return sArray;
    }

    public void clear() {
        Arrays.fill(this.usedChars, 0L);
    }

    public void setBit(int n) {
        int n2 = n >> 5;
        long l = 1L << (int)((long)n & 0x1FL);
        int n3 = n2;
        this.usedChars[n3] = this.usedChars[n3] | l;
    }

    public boolean getBit(int n) {
        int n2 = n >> 5;
        long l = 1L << (int)((long)n & 0x1FL);
        return (this.usedChars[n2] & l) > 0L;
    }
}

