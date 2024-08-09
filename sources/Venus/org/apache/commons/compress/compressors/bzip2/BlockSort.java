/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.compressors.bzip2;

import java.util.BitSet;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;

class BlockSort {
    private static final int QSORT_STACK_SIZE = 1000;
    private static final int FALLBACK_QSORT_STACK_SIZE = 100;
    private static final int STACK_SIZE = 1000;
    private int workDone;
    private int workLimit;
    private boolean firstAttempt;
    private final int[] stack_ll = new int[1000];
    private final int[] stack_hh = new int[1000];
    private final int[] stack_dd = new int[1000];
    private final int[] mainSort_runningOrder = new int[256];
    private final int[] mainSort_copy = new int[256];
    private final boolean[] mainSort_bigDone = new boolean[256];
    private final int[] ftab = new int[65537];
    private final char[] quadrant;
    private static final int FALLBACK_QSORT_SMALL_THRESH = 10;
    private int[] eclass;
    private static final int[] INCS = new int[]{1, 4, 13, 40, 121, 364, 1093, 3280, 9841, 29524, 88573, 265720, 797161, 2391484};
    private static final int SMALL_THRESH = 20;
    private static final int DEPTH_THRESH = 10;
    private static final int WORK_FACTOR = 30;
    private static final int SETMASK = 0x200000;
    private static final int CLEARMASK = -2097153;

    BlockSort(BZip2CompressorOutputStream.Data data) {
        this.quadrant = data.sfmap;
    }

    void blockSort(BZip2CompressorOutputStream.Data data, int n) {
        this.workLimit = 30 * n;
        this.workDone = 0;
        this.firstAttempt = true;
        if (n + 1 < 10000) {
            this.fallbackSort(data, n);
        } else {
            this.mainSort(data, n);
            if (this.firstAttempt && this.workDone > this.workLimit) {
                this.fallbackSort(data, n);
            }
        }
        int[] nArray = data.fmap;
        data.origPtr = -1;
        for (int i = 0; i <= n; ++i) {
            if (nArray[i] != 0) continue;
            data.origPtr = i;
            break;
        }
    }

    final void fallbackSort(BZip2CompressorOutputStream.Data data, int n) {
        data.block[0] = data.block[n + 1];
        this.fallbackSort(data.fmap, data.block, n + 1);
        int n2 = 0;
        while (n2 < n + 1) {
            int n3 = n2++;
            data.fmap[n3] = data.fmap[n3] - 1;
        }
        for (n2 = 0; n2 < n + 1; ++n2) {
            if (data.fmap[n2] != -1) continue;
            data.fmap[n2] = n;
            break;
        }
    }

    private void fallbackSimpleSort(int[] nArray, int[] nArray2, int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6;
        if (n == n2) {
            return;
        }
        if (n2 - n > 3) {
            for (n6 = n2 - 4; n6 >= n; --n6) {
                n5 = nArray[n6];
                n4 = nArray2[n5];
                for (n3 = n6 + 4; n3 <= n2 && n4 > nArray2[nArray[n3]]; n3 += 4) {
                    nArray[n3 - 4] = nArray[n3];
                }
                nArray[n3 - 4] = n5;
            }
        }
        for (n6 = n2 - 1; n6 >= n; --n6) {
            n5 = nArray[n6];
            n4 = nArray2[n5];
            for (n3 = n6 + 1; n3 <= n2 && n4 > nArray2[nArray[n3]]; ++n3) {
                nArray[n3 - 1] = nArray[n3];
            }
            nArray[n3 - 1] = n5;
        }
    }

    private void fswap(int[] nArray, int n, int n2) {
        int n3 = nArray[n];
        nArray[n] = nArray[n2];
        nArray[n2] = n3;
    }

    private void fvswap(int[] nArray, int n, int n2, int n3) {
        while (n3 > 0) {
            this.fswap(nArray, n, n2);
            ++n;
            ++n2;
            --n3;
        }
    }

    private int fmin(int n, int n2) {
        return n < n2 ? n : n2;
    }

    private void fpush(int n, int n2, int n3) {
        this.stack_ll[n] = n2;
        this.stack_hh[n] = n3;
    }

    private int[] fpop(int n) {
        return new int[]{this.stack_ll[n], this.stack_hh[n]};
    }

    private void fallbackQSort3(int[] nArray, int[] nArray2, int n, int n2) {
        long l = 0L;
        int n3 = 0;
        this.fpush(n3++, n, n2);
        while (n3 > 0) {
            int n4;
            int n5;
            int n6;
            int n7;
            int[] nArray3;
            int n8;
            if ((n8 = (nArray3 = this.fpop(--n3))[1]) - (n7 = nArray3[0]) < 10) {
                this.fallbackSimpleSort(nArray, nArray2, n7, n8);
                continue;
            }
            long l2 = (l = (l * 7621L + 1L) % 32768L) % 3L;
            long l3 = l2 == 0L ? (long)nArray2[nArray[n7]] : (l2 == 1L ? (long)nArray2[nArray[n7 + n8 >>> 1]] : (long)nArray2[nArray[n8]]);
            int n9 = n6 = n7;
            int n10 = n5 = n8;
            while (true) {
                if (n9 <= n10) {
                    n4 = nArray2[nArray[n9]] - (int)l3;
                    if (n4 == 0) {
                        this.fswap(nArray, n9, n6);
                        ++n6;
                        ++n9;
                        continue;
                    }
                    if (n4 <= 0) {
                        ++n9;
                        continue;
                    }
                }
                while (n9 <= n10) {
                    n4 = nArray2[nArray[n10]] - (int)l3;
                    if (n4 == 0) {
                        this.fswap(nArray, n10, n5);
                        --n5;
                        --n10;
                        continue;
                    }
                    if (n4 < 0) break;
                    --n10;
                }
                if (n9 > n10) break;
                this.fswap(nArray, n9, n10);
                ++n9;
                --n10;
            }
            if (n5 < n6) continue;
            n4 = this.fmin(n6 - n7, n9 - n6);
            this.fvswap(nArray, n7, n9 - n4, n4);
            int n11 = this.fmin(n8 - n5, n5 - n10);
            this.fvswap(nArray, n10 + 1, n8 - n11 + 1, n11);
            n4 = n7 + n9 - n6 - 1;
            n11 = n8 - (n5 - n10) + 1;
            if (n4 - n7 > n8 - n11) {
                this.fpush(n3++, n7, n4);
                this.fpush(n3++, n11, n8);
                continue;
            }
            this.fpush(n3++, n11, n8);
            this.fpush(n3++, n7, n4);
        }
    }

    private int[] getEclass() {
        return this.eclass == null ? (this.eclass = new int[this.quadrant.length / 2]) : this.eclass;
    }

    /*
     * Unable to fully structure code
     */
    final void fallbackSort(int[] var1_1, byte[] var2_2, int var3_3) {
        var4_4 = new int[257];
        var15_5 = this.getEclass();
        for (var6_6 = 0; var6_6 < var3_3; ++var6_6) {
            var15_5[var6_6] = 0;
        }
        for (var6_6 = 0; var6_6 < var3_3; ++var6_6) {
            v0 = var2_2[var6_6] & 255;
            var4_4[v0] = var4_4[v0] + 1;
        }
        for (var6_6 = 1; var6_6 < 257; ++var6_6) {
            v1 = var6_6;
            var4_4[v1] = var4_4[v1] + var4_4[var6_6 - 1];
        }
        var6_6 = 0;
        while (var6_6 < var3_3) {
            var7_7 = var2_2[var6_6] & 255;
            var4_4[var7_7] = var8_8 = var4_4[var7_7] - 1;
            var1_1[var8_8] = var6_6++;
        }
        var14_9 = 64 + var3_3;
        var16_10 = new BitSet(var14_9);
        for (var6_6 = 0; var6_6 < 256; ++var6_6) {
            var16_10.set(var4_4[var6_6]);
        }
        for (var6_6 = 0; var6_6 < 32; ++var6_6) {
            var16_10.set(var3_3 + 2 * var6_6);
            var16_10.clear(var3_3 + 2 * var6_6 + 1);
        }
        var5_11 = 1;
        block6: do {
            var7_7 = 0;
            for (var6_6 = 0; var6_6 < var3_3; ++var6_6) {
                if (var16_10.get(var6_6)) {
                    var7_7 = var6_6;
                }
                if ((var8_8 = var1_1[var6_6] - var5_11) < 0) {
                    var8_8 += var3_3;
                }
                var15_5[var8_8] = var7_7;
            }
            var13_16 = 0;
            var10_13 = -1;
            block8: while (true) {
                var8_8 = var10_13 + 1;
                var9_12 = (var8_8 = var16_10.nextClearBit(var8_8)) - 1;
                if (var9_12 >= var3_3 || (var10_13 = (var8_8 = var16_10.nextSetBit(var8_8 + 1)) - 1) >= var3_3) continue block6;
                if (var10_13 <= var9_12) continue;
                var13_16 += var10_13 - var9_12 + 1;
                this.fallbackQSort3(var1_1, var15_5, var9_12, var10_13);
                var11_14 = -1;
                var6_6 = var9_12;
                while (true) {
                    if (var6_6 <= var10_13) ** break;
                    continue block8;
                    var12_15 = var15_5[var1_1[var6_6]];
                    if (var11_14 != var12_15) {
                        var16_10.set(var6_6);
                        var11_14 = var12_15;
                    }
                    ++var6_6;
                }
                break;
            }
        } while ((var5_11 *= 2) <= var3_3 && var13_16 != 0);
    }

    private boolean mainSimpleSort(BZip2CompressorOutputStream.Data data, int n, int n2, int n3, int n4) {
        int n5 = n2 - n + 1;
        if (n5 < 2) {
            return this.firstAttempt && this.workDone > this.workLimit;
        }
        int n6 = 0;
        while (INCS[n6] < n5) {
            ++n6;
        }
        int[] nArray = data.fmap;
        char[] cArray = this.quadrant;
        byte[] byArray = data.block;
        int n7 = n4 + 1;
        boolean bl = this.firstAttempt;
        int n8 = this.workLimit;
        int n9 = this.workDone;
        block1: while (--n6 >= 0) {
            int n10 = INCS[n6];
            int n11 = n + n10 - 1;
            int n12 = n + n10;
            while (n12 <= n2) {
                int n13 = 3;
                while (n12 <= n2 && --n13 >= 0) {
                    int n14 = nArray[n12];
                    int n15 = n14 + n3;
                    int n16 = n12;
                    boolean bl2 = false;
                    int n17 = 0;
                    block4: while (true) {
                        int n18;
                        int n19;
                        if (bl2) {
                            nArray[n16] = n17;
                            if ((n16 -= n10) <= n11) {
                                break;
                            }
                        } else {
                            bl2 = true;
                        }
                        if (byArray[(n19 = (n17 = nArray[n16 - n10]) + n3) + 1] == byArray[(n18 = n15) + 1]) {
                            if (byArray[n19 + 2] == byArray[n18 + 2]) {
                                if (byArray[n19 + 3] == byArray[n18 + 3]) {
                                    if (byArray[n19 + 4] == byArray[n18 + 4]) {
                                        if (byArray[n19 + 5] == byArray[n18 + 5]) {
                                            if (byArray[n19 += 6] == byArray[n18 += 6]) {
                                                int n20 = n4;
                                                while (n20 > 0) {
                                                    n20 -= 4;
                                                    if (byArray[n19 + 1] == byArray[n18 + 1]) {
                                                        if (cArray[n19] == cArray[n18]) {
                                                            if (byArray[n19 + 2] == byArray[n18 + 2]) {
                                                                if (cArray[n19 + 1] == cArray[n18 + 1]) {
                                                                    if (byArray[n19 + 3] == byArray[n18 + 3]) {
                                                                        if (cArray[n19 + 2] == cArray[n18 + 2]) {
                                                                            if (byArray[n19 + 4] == byArray[n18 + 4]) {
                                                                                if (cArray[n19 + 3] == cArray[n18 + 3]) {
                                                                                    if ((n19 += 4) >= n7) {
                                                                                        n19 -= n7;
                                                                                    }
                                                                                    if ((n18 += 4) >= n7) {
                                                                                        n18 -= n7;
                                                                                    }
                                                                                    ++n9;
                                                                                    continue;
                                                                                }
                                                                                if (cArray[n19 + 3] <= cArray[n18 + 3]) break block4;
                                                                                continue block4;
                                                                            }
                                                                            if ((byArray[n19 + 4] & 0xFF) <= (byArray[n18 + 4] & 0xFF)) break block4;
                                                                            continue block4;
                                                                        }
                                                                        if (cArray[n19 + 2] <= cArray[n18 + 2]) break block4;
                                                                        continue block4;
                                                                    }
                                                                    if ((byArray[n19 + 3] & 0xFF) <= (byArray[n18 + 3] & 0xFF)) break block4;
                                                                    continue block4;
                                                                }
                                                                if (cArray[n19 + 1] <= cArray[n18 + 1]) break block4;
                                                                continue block4;
                                                            }
                                                            if ((byArray[n19 + 2] & 0xFF) <= (byArray[n18 + 2] & 0xFF)) break block4;
                                                            continue block4;
                                                        }
                                                        if (cArray[n19] <= cArray[n18]) break block4;
                                                        continue block4;
                                                    }
                                                    if ((byArray[n19 + 1] & 0xFF) <= (byArray[n18 + 1] & 0xFF)) break block4;
                                                    continue block4;
                                                }
                                                break;
                                            }
                                            if ((byArray[n19] & 0xFF) <= (byArray[n18] & 0xFF)) break;
                                            continue;
                                        }
                                        if ((byArray[n19 + 5] & 0xFF) <= (byArray[n18 + 5] & 0xFF)) break;
                                        continue;
                                    }
                                    if ((byArray[n19 + 4] & 0xFF) <= (byArray[n18 + 4] & 0xFF)) break;
                                    continue;
                                }
                                if ((byArray[n19 + 3] & 0xFF) <= (byArray[n18 + 3] & 0xFF)) break;
                                continue;
                            }
                            if ((byArray[n19 + 2] & 0xFF) <= (byArray[n18 + 2] & 0xFF)) break;
                            continue;
                        }
                        if ((byArray[n19 + 1] & 0xFF) <= (byArray[n18 + 1] & 0xFF)) break;
                    }
                    nArray[n16] = n14;
                    ++n12;
                }
                if (!bl || n12 > n2 || n9 <= n8) continue;
                break block1;
            }
        }
        this.workDone = n9;
        return bl && n9 > n8;
    }

    private static void vswap(int[] nArray, int n, int n2, int n3) {
        n3 += n;
        while (n < n3) {
            int n4 = nArray[n];
            nArray[n++] = nArray[n2];
            nArray[n2++] = n4;
        }
    }

    private static byte med3(byte by, byte by2, byte by3) {
        return by < by2 ? (by2 < by3 ? by2 : (by < by3 ? by3 : by)) : (by2 > by3 ? by2 : (by > by3 ? by3 : by));
    }

    private void mainQSort3(BZip2CompressorOutputStream.Data data, int n, int n2, int n3, int n4) {
        int[] nArray = this.stack_ll;
        int[] nArray2 = this.stack_hh;
        int[] nArray3 = this.stack_dd;
        int[] nArray4 = data.fmap;
        byte[] byArray = data.block;
        nArray[0] = n;
        nArray2[0] = n2;
        nArray3[0] = n3;
        int n5 = 1;
        while (--n5 >= 0) {
            int n6;
            int n7;
            int n8 = nArray[n5];
            int n9 = nArray2[n5];
            int n10 = nArray3[n5];
            if (n9 - n8 < 20 || n10 > 10) {
                if (!this.mainSimpleSort(data, n8, n9, n10, n4)) continue;
                return;
            }
            int n11 = n10 + 1;
            int n12 = BlockSort.med3(byArray[nArray4[n8] + n11], byArray[nArray4[n9] + n11], byArray[nArray4[n8 + n9 >>> 1] + n11]) & 0xFF;
            int n13 = n8;
            int n14 = n9;
            int n15 = n8;
            int n16 = n9;
            while (true) {
                if (n13 <= n14) {
                    n7 = (byArray[nArray4[n13] + n11] & 0xFF) - n12;
                    if (n7 == 0) {
                        n6 = nArray4[n13];
                        nArray4[n13++] = nArray4[n15];
                        nArray4[n15++] = n6;
                        continue;
                    }
                    if (n7 < 0) {
                        ++n13;
                        continue;
                    }
                }
                while (n13 <= n14) {
                    n7 = (byArray[nArray4[n14] + n11] & 0xFF) - n12;
                    if (n7 == 0) {
                        n6 = nArray4[n14];
                        nArray4[n14--] = nArray4[n16];
                        nArray4[n16--] = n6;
                        continue;
                    }
                    if (n7 <= 0) break;
                    --n14;
                }
                if (n13 > n14) break;
                n7 = nArray4[n13];
                nArray4[n13++] = nArray4[n14];
                nArray4[n14--] = n7;
            }
            if (n16 < n15) {
                nArray[n5] = n8;
                nArray2[n5] = n9;
                nArray3[n5] = n11;
                ++n5;
                continue;
            }
            n7 = n15 - n8 < n13 - n15 ? n15 - n8 : n13 - n15;
            BlockSort.vswap(nArray4, n8, n13 - n7, n7);
            n6 = n9 - n16 < n16 - n14 ? n9 - n16 : n16 - n14;
            BlockSort.vswap(nArray4, n13, n9 - n6 + 1, n6);
            n7 = n8 + n13 - n15 - 1;
            n6 = n9 - (n16 - n14) + 1;
            nArray[n5] = n8;
            nArray2[n5] = n7;
            nArray3[n5] = n10;
            nArray[++n5] = n7 + 1;
            nArray2[n5] = n6 - 1;
            nArray3[n5] = n11;
            nArray[++n5] = n6;
            nArray2[n5] = n9;
            nArray3[n5] = n10;
            ++n5;
        }
    }

    final void mainSort(BZip2CompressorOutputStream.Data data, int n) {
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        int n8;
        int[] nArray = this.mainSort_runningOrder;
        int[] nArray2 = this.mainSort_copy;
        boolean[] blArray = this.mainSort_bigDone;
        int[] nArray3 = this.ftab;
        byte[] byArray = data.block;
        int[] nArray4 = data.fmap;
        char[] cArray = this.quadrant;
        int n9 = this.workLimit;
        boolean bl = this.firstAttempt;
        int n10 = 65537;
        while (--n10 >= 0) {
            nArray3[n10] = 0;
        }
        for (n10 = 0; n10 < 20; ++n10) {
            byArray[n + n10 + 2] = byArray[n10 % (n + 1) + 1];
        }
        n10 = n + 20 + 1;
        while (--n10 >= 0) {
            cArray[n10] = '\u0000';
        }
        byArray[0] = byArray[n + 1];
        n10 = byArray[0] & 0xFF;
        for (n8 = 0; n8 <= n; ++n8) {
            n7 = byArray[n8 + 1] & 0xFF;
            int n11 = (n10 << 8) + n7;
            nArray3[n11] = nArray3[n11] + 1;
            n10 = n7;
        }
        for (n8 = 1; n8 <= 65536; ++n8) {
            int n12 = n8;
            nArray3[n12] = nArray3[n12] + nArray3[n8 - 1];
        }
        n10 = byArray[1] & 0xFF;
        n8 = 0;
        while (n8 < n) {
            n7 = byArray[n8 + 2] & 0xFF;
            int n13 = (n10 << 8) + n7;
            int n14 = nArray3[n13] - 1;
            nArray3[n13] = n14;
            nArray4[n14] = n8++;
            n10 = n7;
        }
        int n15 = ((byArray[n + 1] & 0xFF) << 8) + (byArray[1] & 0xFF);
        int n16 = nArray3[n15] - 1;
        nArray3[n15] = n16;
        nArray4[n16] = n;
        n8 = 256;
        while (--n8 >= 0) {
            blArray[n8] = false;
            nArray[n8] = n8;
        }
        n8 = 364;
        while (n8 != 1) {
            for (n7 = n8 /= 3; n7 <= 255; ++n7) {
                n6 = nArray[n7];
                n5 = nArray3[n6 + 1 << 8] - nArray3[n6 << 8];
                n4 = n8 - 1;
                n3 = n7;
                n2 = nArray[n3 - n8];
                while (nArray3[n2 + 1 << 8] - nArray3[n2 << 8] > n5) {
                    nArray[n3] = n2;
                    if ((n3 -= n8) <= n4) break;
                    n2 = nArray[n3 - n8];
                }
                nArray[n3] = n6;
            }
        }
        for (n8 = 0; n8 <= 255; ++n8) {
            n7 = nArray[n8];
            for (n6 = 0; n6 <= 255; ++n6) {
                n5 = (n7 << 8) + n6;
                n4 = nArray3[n5];
                if ((n4 & 0x200000) == 0x200000) continue;
                n2 = (nArray3[n5 + 1] & 0xFFDFFFFF) - 1;
                n3 = n4 & 0xFFDFFFFF;
                if (n2 > n3) {
                    this.mainQSort3(data, n3, n2, 2, n);
                    if (bl && this.workDone > n9) {
                        return;
                    }
                }
                nArray3[n5] = n4 | 0x200000;
            }
            for (n6 = 0; n6 <= 255; ++n6) {
                nArray2[n6] = nArray3[(n6 << 8) + n7] & 0xFFDFFFFF;
            }
            n5 = nArray3[n7 + 1 << 8] & 0xFFDFFFFF;
            for (n6 = nArray3[n7 << 8] & 0xFFDFFFFF; n6 < n5; ++n6) {
                n4 = nArray4[n6];
                n10 = byArray[n4] & 0xFF;
                if (blArray[n10]) continue;
                nArray4[nArray2[n10]] = n4 == 0 ? n : n4 - 1;
                int n17 = n10;
                nArray2[n17] = nArray2[n17] + 1;
            }
            n6 = 256;
            while (--n6 >= 0) {
                int n18 = (n6 << 8) + n7;
                nArray3[n18] = nArray3[n18] | 0x200000;
            }
            blArray[n7] = true;
            if (n8 >= 255) continue;
            n6 = nArray3[n7 << 8] & 0xFFDFFFFF;
            n5 = (nArray3[n7 + 1 << 8] & 0xFFDFFFFF) - n6;
            n4 = 0;
            while (n5 >> n4 > 65534) {
                ++n4;
            }
            for (n3 = 0; n3 < n5; ++n3) {
                char c;
                n2 = nArray4[n6 + n3];
                cArray[n2] = c = (char)(n3 >> n4);
                if (n2 >= 20) continue;
                cArray[n2 + n + 1] = c;
            }
        }
    }
}

