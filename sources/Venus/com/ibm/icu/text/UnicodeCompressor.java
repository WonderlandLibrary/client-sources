/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.text.SCSU;

public final class UnicodeCompressor
implements SCSU {
    private static boolean[] sSingleTagTable = new boolean[]{false, true, true, true, true, true, true, true, true, false, false, true, true, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
    private static boolean[] sUnicodeTagTable = new boolean[]{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false};
    private int fCurrentWindow = 0;
    private int[] fOffsets = new int[8];
    private int fMode = 0;
    private int[] fIndexCount = new int[256];
    private int[] fTimeStamps = new int[8];
    private int fTimeStamp = 0;

    public UnicodeCompressor() {
        this.reset();
    }

    public static byte[] compress(String string) {
        return UnicodeCompressor.compress(string.toCharArray(), 0, string.length());
    }

    public static byte[] compress(char[] cArray, int n, int n2) {
        UnicodeCompressor unicodeCompressor = new UnicodeCompressor();
        int n3 = Math.max(4, 3 * (n2 - n) + 1);
        byte[] byArray = new byte[n3];
        int n4 = unicodeCompressor.compress(cArray, n, n2, null, byArray, 0, n3);
        byte[] byArray2 = new byte[n4];
        System.arraycopy(byArray, 0, byArray2, 0, n4);
        return byArray2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public int compress(char[] cArray, int n, int n2, int[] nArray, byte[] byArray, int n3, int n4) {
        int n5 = n3;
        int n6 = n;
        int n7 = -1;
        int n8 = -1;
        int n9 = -1;
        int n10 = -1;
        int n11 = 0;
        int n12 = 0;
        int n13 = 0;
        if (byArray.length < 4 || n4 - n3 < 4) {
            throw new IllegalArgumentException("byteBuffer.length < 4");
        }
        block4: while (n6 < n2 && n5 < n4) {
            block47: {
                block48: {
                    switch (this.fMode) {
                        case 0: {
                            break;
                        }
                        case 1: {
                            break block48;
                        }
                    }
                    while (n6 < n2 && n5 < n4) {
                        n7 = cArray[n6++];
                        n9 = n6 < n2 ? cArray[n6] : -1;
                        if (n7 < 128) {
                            n13 = n7 & 0xFF;
                            if (sSingleTagTable[n13]) {
                                if (n5 + 1 >= n4) {
                                    --n6;
                                    break block4;
                                }
                                byArray[n5++] = 1;
                            }
                            byArray[n5++] = (byte)n13;
                            continue;
                        }
                        if (this.inDynamicWindow(n7, this.fCurrentWindow)) {
                            byArray[n5++] = (byte)(n7 - this.fOffsets[this.fCurrentWindow] + 128);
                            continue;
                        }
                        if (!UnicodeCompressor.isCompressible(n7)) {
                            if (n9 != -1 && UnicodeCompressor.isCompressible(n9)) {
                                if (n5 + 2 >= n4) {
                                    --n6;
                                    break block4;
                                }
                                byArray[n5++] = 14;
                                byArray[n5++] = (byte)(n7 >>> 8);
                                byArray[n5++] = (byte)(n7 & 0xFF);
                                continue;
                            }
                            if (n5 + 3 >= n4) {
                                --n6;
                                break block4;
                            }
                            byArray[n5++] = 15;
                            n12 = n7 >>> 8;
                            n13 = n7 & 0xFF;
                            if (sUnicodeTagTable[n12]) {
                                byArray[n5++] = -16;
                            }
                            byArray[n5++] = (byte)n12;
                            byArray[n5++] = (byte)n13;
                            this.fMode = 1;
                            break block47;
                        } else {
                            n11 = this.findDynamicWindow(n7);
                            if (n11 != -1) {
                                n10 = n6 + 1 < n2 ? cArray[n6 + 1] : -1;
                                if (this.inDynamicWindow(n9, n11) && this.inDynamicWindow(n10, n11)) {
                                    if (n5 + 1 >= n4) {
                                        --n6;
                                        break block4;
                                    }
                                    byArray[n5++] = (byte)(16 + n11);
                                    byArray[n5++] = (byte)(n7 - this.fOffsets[n11] + 128);
                                    this.fTimeStamps[n11] = ++this.fTimeStamp;
                                    this.fCurrentWindow = n11;
                                    continue;
                                }
                                if (n5 + 1 >= n4) {
                                    --n6;
                                    break block4;
                                }
                                byArray[n5++] = (byte)(1 + n11);
                                byArray[n5++] = (byte)(n7 - this.fOffsets[n11] + 128);
                                continue;
                            }
                            n11 = UnicodeCompressor.findStaticWindow(n7);
                            if (n11 != -1 && !UnicodeCompressor.inStaticWindow(n9, n11)) {
                                if (n5 + 1 >= n4) {
                                    --n6;
                                    break block4;
                                }
                                byArray[n5++] = (byte)(1 + n11);
                                byArray[n5++] = (byte)(n7 - sOffsets[n11]);
                                continue;
                            }
                            int n14 = n8 = UnicodeCompressor.makeIndex(n7);
                            this.fIndexCount[n14] = this.fIndexCount[n14] + 1;
                            n10 = n6 + 1 < n2 ? cArray[n6 + 1] : -1;
                            if (this.fIndexCount[n8] > 1 || n8 == UnicodeCompressor.makeIndex(n9) && n8 == UnicodeCompressor.makeIndex(n10)) {
                                if (n5 + 2 >= n4) {
                                    --n6;
                                    break block4;
                                }
                                n11 = this.getLRDefinedWindow();
                                byArray[n5++] = (byte)(24 + n11);
                                byArray[n5++] = (byte)n8;
                                byArray[n5++] = (byte)(n7 - sOffsetTable[n8] + 128);
                                this.fOffsets[n11] = sOffsetTable[n8];
                                this.fCurrentWindow = n11;
                                this.fTimeStamps[n11] = ++this.fTimeStamp;
                                continue;
                            }
                            if (n5 + 3 >= n4) {
                                --n6;
                                break block4;
                            }
                            byArray[n5++] = 15;
                            n12 = n7 >>> 8;
                            n13 = n7 & 0xFF;
                            if (sUnicodeTagTable[n12]) {
                                byArray[n5++] = -16;
                            }
                            byArray[n5++] = (byte)n12;
                            byArray[n5++] = (byte)n13;
                            this.fMode = 1;
                        }
                        break block47;
                    }
                    break block47;
                }
                while (n6 < n2 && n5 < n4) {
                    n7 = cArray[n6++];
                    n9 = n6 < n2 ? cArray[n6] : -1;
                    if (!UnicodeCompressor.isCompressible(n7) || n9 != -1 && !UnicodeCompressor.isCompressible(n9)) {
                        if (n5 + 2 >= n4) {
                            --n6;
                            break block4;
                        }
                        n12 = n7 >>> 8;
                        n13 = n7 & 0xFF;
                        if (sUnicodeTagTable[n12]) {
                            byArray[n5++] = -16;
                        }
                        byArray[n5++] = (byte)n12;
                        byArray[n5++] = (byte)n13;
                        continue;
                    }
                    if (n7 < 128) {
                        n13 = n7 & 0xFF;
                        if (n9 != -1 && n9 < 128 && !sSingleTagTable[n13]) {
                            if (n5 + 1 >= n4) {
                                --n6;
                                break block4;
                            }
                            n11 = this.fCurrentWindow;
                            byArray[n5++] = (byte)(224 + n11);
                            byArray[n5++] = (byte)n13;
                            this.fTimeStamps[n11] = ++this.fTimeStamp;
                            this.fMode = 0;
                            break;
                        } else {
                            if (n5 + 1 >= n4) {
                                --n6;
                                break block4;
                            }
                            byArray[n5++] = 0;
                            byArray[n5++] = (byte)n13;
                            continue;
                        }
                    }
                    n11 = this.findDynamicWindow(n7);
                    if (n11 != -1) {
                        if (this.inDynamicWindow(n9, n11)) {
                            if (n5 + 1 >= n4) {
                                --n6;
                                break block4;
                            }
                            byArray[n5++] = (byte)(224 + n11);
                            byArray[n5++] = (byte)(n7 - this.fOffsets[n11] + 128);
                            this.fTimeStamps[n11] = ++this.fTimeStamp;
                            this.fCurrentWindow = n11;
                            this.fMode = 0;
                            break;
                        } else {
                            if (n5 + 2 >= n4) {
                                --n6;
                                break block4;
                            }
                            n12 = n7 >>> 8;
                            n13 = n7 & 0xFF;
                            if (sUnicodeTagTable[n12]) {
                                byArray[n5++] = -16;
                            }
                            byArray[n5++] = (byte)n12;
                            byArray[n5++] = (byte)n13;
                            continue;
                        }
                    }
                    int n15 = n8 = UnicodeCompressor.makeIndex(n7);
                    this.fIndexCount[n15] = this.fIndexCount[n15] + 1;
                    n10 = n6 + 1 < n2 ? cArray[n6 + 1] : -1;
                    if (this.fIndexCount[n8] > 1 || n8 == UnicodeCompressor.makeIndex(n9) && n8 == UnicodeCompressor.makeIndex(n10)) {
                        if (n5 + 2 >= n4) {
                            --n6;
                            break block4;
                        }
                        n11 = this.getLRDefinedWindow();
                        byArray[n5++] = (byte)(232 + n11);
                        byArray[n5++] = (byte)n8;
                        byArray[n5++] = (byte)(n7 - sOffsetTable[n8] + 128);
                        this.fOffsets[n11] = sOffsetTable[n8];
                        this.fCurrentWindow = n11;
                        this.fTimeStamps[n11] = ++this.fTimeStamp;
                        this.fMode = 0;
                        break;
                    }
                    if (n5 + 2 >= n4) {
                        --n6;
                        break block4;
                    }
                    n12 = n7 >>> 8;
                    n13 = n7 & 0xFF;
                    if (sUnicodeTagTable[n12]) {
                        byArray[n5++] = -16;
                    }
                    byArray[n5++] = (byte)n12;
                    byArray[n5++] = (byte)n13;
                }
            }
        }
        if (nArray != null) {
            nArray[0] = n6 - n;
        }
        return n5 - n3;
    }

    public void reset() {
        int n;
        this.fOffsets[0] = 128;
        this.fOffsets[1] = 192;
        this.fOffsets[2] = 1024;
        this.fOffsets[3] = 1536;
        this.fOffsets[4] = 2304;
        this.fOffsets[5] = 12352;
        this.fOffsets[6] = 12448;
        this.fOffsets[7] = 65280;
        for (n = 0; n < 8; ++n) {
            this.fTimeStamps[n] = 0;
        }
        for (n = 0; n <= 255; ++n) {
            this.fIndexCount[n] = 0;
        }
        this.fTimeStamp = 0;
        this.fCurrentWindow = 0;
        this.fMode = 0;
    }

    private static int makeIndex(int n) {
        if (n >= 192 && n < 320) {
            return 0;
        }
        if (n >= 592 && n < 720) {
            return 1;
        }
        if (n >= 880 && n < 1008) {
            return 0;
        }
        if (n >= 1328 && n < 1424) {
            return 1;
        }
        if (n >= 12352 && n < 12448) {
            return 0;
        }
        if (n >= 12448 && n < 12576) {
            return 1;
        }
        if (n >= 65376 && n < 65439) {
            return 0;
        }
        if (n >= 128 && n < 13312) {
            return n / 128 & 0xFF;
        }
        if (n >= 57344 && n <= 65535) {
            return (n - 44032) / 128 & 0xFF;
        }
        return 1;
    }

    private boolean inDynamicWindow(int n, int n2) {
        return n >= this.fOffsets[n2] && n < this.fOffsets[n2] + 128;
    }

    private static boolean inStaticWindow(int n, int n2) {
        return n >= sOffsets[n2] && n < sOffsets[n2] + 128;
    }

    private static boolean isCompressible(int n) {
        return n < 13312 || n >= 57344;
    }

    private int findDynamicWindow(int n) {
        for (int i = 7; i >= 0; --i) {
            if (!this.inDynamicWindow(n, i)) continue;
            int n2 = i;
            this.fTimeStamps[n2] = this.fTimeStamps[n2] + 1;
            return i;
        }
        return 1;
    }

    private static int findStaticWindow(int n) {
        for (int i = 7; i >= 0; --i) {
            if (!UnicodeCompressor.inStaticWindow(n, i)) continue;
            return i;
        }
        return 1;
    }

    private int getLRDefinedWindow() {
        int n = Integer.MAX_VALUE;
        int n2 = -1;
        for (int i = 7; i >= 0; --i) {
            if (this.fTimeStamps[i] >= n) continue;
            n = this.fTimeStamps[i];
            n2 = i;
        }
        return n2;
    }
}

