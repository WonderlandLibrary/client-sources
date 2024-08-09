/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.text.Bidi;
import com.ibm.icu.text.BidiRun;
import java.util.Arrays;

final class BidiLine {
    BidiLine() {
    }

    static void setTrailingWSStart(Bidi bidi) {
        int n;
        byte[] byArray = bidi.dirProps;
        byte[] byArray2 = bidi.levels;
        byte by = bidi.paraLevel;
        if (byArray[n - 1] == 7) {
            bidi.trailingWSStart = n;
            return;
        }
        for (n = bidi.length; n > 0 && (Bidi.DirPropFlag(byArray[n - 1]) & Bidi.MASK_WS) != 0; --n) {
        }
        while (n > 0 && byArray2[n - 1] == by) {
            --n;
        }
        bidi.trailingWSStart = n;
    }

    static Bidi setLine(Bidi bidi, int n, int n2) {
        Bidi bidi2 = new Bidi();
        bidi2.originalLength = bidi2.resultLength = n2 - n;
        bidi2.length = bidi2.resultLength;
        int n3 = bidi2.resultLength;
        bidi2.text = new char[n3];
        System.arraycopy(bidi.text, n, bidi2.text, 0, n3);
        bidi2.paraLevel = bidi.GetParaLevelAt(n);
        bidi2.paraCount = bidi.paraCount;
        bidi2.runs = new BidiRun[0];
        bidi2.reorderingMode = bidi.reorderingMode;
        bidi2.reorderingOptions = bidi.reorderingOptions;
        if (bidi.controlCount > 0) {
            for (int i = n; i < n2; ++i) {
                if (!Bidi.IsBidiControlChar(bidi.text[i])) continue;
                ++bidi2.controlCount;
            }
            bidi2.resultLength -= bidi2.controlCount;
        }
        bidi2.getDirPropsMemory(n3);
        bidi2.dirProps = bidi2.dirPropsMemory;
        System.arraycopy(bidi.dirProps, n, bidi2.dirProps, 0, n3);
        bidi2.getLevelsMemory(n3);
        bidi2.levels = bidi2.levelsMemory;
        System.arraycopy(bidi.levels, n, bidi2.levels, 0, n3);
        bidi2.runCount = -1;
        if (bidi.direction != 2) {
            bidi2.direction = bidi.direction;
            bidi2.trailingWSStart = bidi.trailingWSStart <= n ? 0 : (bidi.trailingWSStart < n2 ? bidi.trailingWSStart - n : n3);
        } else {
            byte[] byArray = bidi2.levels;
            BidiLine.setTrailingWSStart(bidi2);
            int n4 = bidi2.trailingWSStart;
            if (n4 == 0) {
                bidi2.direction = (byte)(bidi2.paraLevel & 1);
            } else {
                byte by = (byte)(byArray[0] & 1);
                if (n4 < n3 && (bidi2.paraLevel & 1) != by) {
                    bidi2.direction = (byte)2;
                } else {
                    int n5 = 1;
                    while (true) {
                        if (n5 == n4) {
                            bidi2.direction = by;
                            break;
                        }
                        if ((byArray[n5] & 1) != by) {
                            bidi2.direction = (byte)2;
                            break;
                        }
                        ++n5;
                    }
                }
            }
            switch (bidi2.direction) {
                case 0: {
                    bidi2.paraLevel = (byte)(bidi2.paraLevel + 1 & 0xFFFFFFFE);
                    bidi2.trailingWSStart = 0;
                    break;
                }
                case 1: {
                    bidi2.paraLevel = (byte)(bidi2.paraLevel | 1);
                    bidi2.trailingWSStart = 0;
                    break;
                }
            }
        }
        bidi2.paraBidi = bidi;
        return bidi2;
    }

    static byte getLevelAt(Bidi bidi, int n) {
        if (bidi.direction != 2 || n >= bidi.trailingWSStart) {
            return bidi.GetParaLevelAt(n);
        }
        return bidi.levels[n];
    }

    static byte[] getLevels(Bidi bidi) {
        int n = bidi.trailingWSStart;
        int n2 = bidi.length;
        if (n != n2) {
            Arrays.fill(bidi.levels, n, n2, bidi.paraLevel);
            bidi.trailingWSStart = n2;
        }
        if (n2 < bidi.levels.length) {
            byte[] byArray = new byte[n2];
            System.arraycopy(bidi.levels, 0, byArray, 0, n2);
            return byArray;
        }
        return bidi.levels;
    }

    static BidiRun getLogicalRun(Bidi bidi, int n) {
        BidiRun bidiRun = new BidiRun();
        BidiLine.getRuns(bidi);
        int n2 = bidi.runCount;
        int n3 = 0;
        int n4 = 0;
        BidiRun bidiRun2 = bidi.runs[0];
        for (int i = 0; i < n2; ++i) {
            bidiRun2 = bidi.runs[i];
            n4 = bidiRun2.start + bidiRun2.limit - n3;
            if (n >= bidiRun2.start && n < n4) break;
            n3 = bidiRun2.limit;
        }
        bidiRun.start = bidiRun2.start;
        bidiRun.limit = n4;
        bidiRun.level = bidiRun2.level;
        return bidiRun;
    }

    static BidiRun getVisualRun(Bidi bidi, int n) {
        int n2 = bidi.runs[n].start;
        byte by = bidi.runs[n].level;
        int n3 = n > 0 ? n2 + bidi.runs[n].limit - bidi.runs[n - 1].limit : n2 + bidi.runs[0].limit;
        return new BidiRun(n2, n3, by);
    }

    static void getSingleRun(Bidi bidi, byte by) {
        bidi.runs = bidi.simpleRuns;
        bidi.runCount = 1;
        bidi.runs[0] = new BidiRun(0, bidi.length, by);
    }

    private static void reorderLine(Bidi bidi, byte by, byte by2) {
        BidiRun bidiRun;
        int n;
        if (by2 <= (by | 1)) {
            return;
        }
        by = (byte)(by + 1);
        BidiRun[] bidiRunArray = bidi.runs;
        byte[] byArray = bidi.levels;
        int n2 = bidi.runCount;
        if (bidi.trailingWSStart < bidi.length) {
            --n2;
        }
        block0: while ((by2 = (byte)(by2 - 1)) >= by) {
            n = 0;
            while (true) {
                if (n < n2 && byArray[bidiRunArray[n].start] < by2) {
                    ++n;
                    continue;
                }
                if (n >= n2) continue block0;
                int n3 = n;
                while (++n3 < n2 && byArray[bidiRunArray[n3].start] >= by2) {
                }
                for (int i = n3 - 1; n < i; ++n, --i) {
                    bidiRun = bidiRunArray[n];
                    bidiRunArray[n] = bidiRunArray[i];
                    bidiRunArray[i] = bidiRun;
                }
                if (n3 == n2) continue block0;
                n = n3 + 1;
            }
        }
        if ((by & 1) == 0) {
            n = 0;
            if (bidi.trailingWSStart == bidi.length) {
                --n2;
            }
            while (n < n2) {
                bidiRun = bidiRunArray[n];
                bidiRunArray[n] = bidiRunArray[n2];
                bidiRunArray[n2] = bidiRun;
                ++n;
                --n2;
            }
        }
    }

    static int getRunFromLogicalIndex(Bidi bidi, int n) {
        BidiRun[] bidiRunArray = bidi.runs;
        int n2 = bidi.runCount;
        int n3 = 0;
        for (int i = 0; i < n2; ++i) {
            int n4 = bidiRunArray[i].limit - n3;
            int n5 = bidiRunArray[i].start;
            if (n >= n5 && n < n5 + n4) {
                return i;
            }
            n3 += n4;
        }
        throw new IllegalStateException("Internal ICU error in getRunFromLogicalIndex");
    }

    static void getRuns(Bidi bidi) {
        int n;
        int n2;
        if (bidi.runCount >= 0) {
            return;
        }
        if (bidi.direction != 2) {
            BidiLine.getSingleRun(bidi, bidi.paraLevel);
        } else {
            int n3;
            n2 = bidi.length;
            byte[] byArray = bidi.levels;
            byte by = -1;
            n = bidi.trailingWSStart;
            int n4 = 0;
            for (n3 = 0; n3 < n; ++n3) {
                if (byArray[n3] == by) continue;
                ++n4;
                by = byArray[n3];
            }
            if (n4 == 1 && n == n2) {
                BidiLine.getSingleRun(bidi, byArray[0]);
            } else {
                byte by2 = 126;
                byte by3 = 0;
                if (n < n2) {
                    ++n4;
                }
                bidi.getRunsMemory(n4);
                BidiRun[] bidiRunArray = bidi.runsMemory;
                int n5 = 0;
                n3 = 0;
                do {
                    int n6 = n3;
                    by = byArray[n3];
                    if (by < by2) {
                        by2 = by;
                    }
                    if (by > by3) {
                        by3 = by;
                    }
                    while (++n3 < n && byArray[n3] == by) {
                    }
                    bidiRunArray[n5] = new BidiRun(n6, n3 - n6, by);
                    ++n5;
                } while (n3 < n);
                if (n < n2) {
                    bidiRunArray[n5] = new BidiRun(n, n2 - n, bidi.paraLevel);
                    if (bidi.paraLevel < by2) {
                        by2 = bidi.paraLevel;
                    }
                }
                bidi.runs = bidiRunArray;
                bidi.runCount = n4;
                BidiLine.reorderLine(bidi, by2, by3);
                n = 0;
                for (n3 = 0; n3 < n4; ++n3) {
                    bidiRunArray[n3].level = byArray[bidiRunArray[n3].start];
                    n = bidiRunArray[n3].limit += n;
                }
                if (n5 < n4) {
                    int n7 = (bidi.paraLevel & 1) != 0 ? 0 : n5;
                    bidiRunArray[n7].level = bidi.paraLevel;
                }
            }
        }
        if (bidi.insertPoints.size > 0) {
            for (int i = 0; i < bidi.insertPoints.size; ++i) {
                Bidi.Point point = bidi.insertPoints.points[i];
                n = BidiLine.getRunFromLogicalIndex(bidi, point.pos);
                bidi.runs[n].insertRemove |= point.flag;
            }
        }
        if (bidi.controlCount > 0) {
            for (n = 0; n < bidi.length; ++n) {
                char c = bidi.text[n];
                if (!Bidi.IsBidiControlChar(c)) continue;
                n2 = BidiLine.getRunFromLogicalIndex(bidi, n);
                --bidi.runs[n2].insertRemove;
            }
        }
    }

    static int[] prepareReorder(byte[] byArray, byte[] byArray2, byte[] byArray3) {
        if (byArray == null || byArray.length <= 0) {
            return null;
        }
        byte by = 126;
        byte by2 = 0;
        int n = byArray.length;
        while (n > 0) {
            byte by3;
            if ((by3 = byArray[--n]) < 0) {
                return null;
            }
            if (by3 > 126) {
                return null;
            }
            if (by3 < by) {
                by = by3;
            }
            if (by3 <= by2) continue;
            by2 = by3;
        }
        byArray2[0] = by;
        byArray3[0] = by2;
        int[] nArray = new int[byArray.length];
        n = byArray.length;
        while (n > 0) {
            nArray[--n] = n;
        }
        return nArray;
    }

    static int[] reorderLogical(byte[] byArray) {
        byte[] byArray2 = new byte[1];
        byte[] byArray3 = new byte[1];
        int[] nArray = BidiLine.prepareReorder(byArray, byArray2, byArray3);
        if (nArray == null) {
            return null;
        }
        byte by = byArray2[0];
        byte by2 = byArray3[0];
        if (by == by2 && (by & 1) == 0) {
            return nArray;
        }
        by = (byte)(by | 1);
        block0: do {
            int n = 0;
            while (true) {
                if (n < byArray.length && byArray[n] < by2) {
                    ++n;
                    continue;
                }
                if (n >= byArray.length) continue block0;
                int n2 = n;
                while (++n2 < byArray.length && byArray[n2] >= by2) {
                }
                int n3 = n + n2 - 1;
                do {
                    nArray[n] = n3 - nArray[n];
                } while (++n < n2);
                if (n2 == byArray.length) continue block0;
                n = n2 + 1;
            }
        } while ((by2 = (byte)(by2 - 1)) >= by);
        return nArray;
    }

    static int[] reorderVisual(byte[] byArray) {
        byte[] byArray2 = new byte[1];
        byte[] byArray3 = new byte[1];
        int[] nArray = BidiLine.prepareReorder(byArray, byArray2, byArray3);
        if (nArray == null) {
            return null;
        }
        byte by = byArray2[0];
        byte by2 = byArray3[0];
        if (by == by2 && (by & 1) == 0) {
            return nArray;
        }
        by = (byte)(by | 1);
        block0: do {
            int n = 0;
            while (true) {
                if (n < byArray.length && byArray[n] < by2) {
                    ++n;
                    continue;
                }
                if (n >= byArray.length) continue block0;
                int n2 = n;
                while (++n2 < byArray.length && byArray[n2] >= by2) {
                }
                for (int i = n2 - 1; n < i; ++n, --i) {
                    int n3 = nArray[n];
                    nArray[n] = nArray[i];
                    nArray[i] = n3;
                }
                if (n2 == byArray.length) continue block0;
                n = n2 + 1;
            }
        } while ((by2 = (byte)(by2 - 1)) >= by);
        return nArray;
    }

    static int getVisualIndex(Bidi bidi, int n) {
        int n2;
        int n3;
        int n4;
        int n5;
        BidiRun[] bidiRunArray;
        int n6 = -1;
        switch (bidi.direction) {
            case 0: {
                n6 = n;
                break;
            }
            case 1: {
                n6 = bidi.length - n - 1;
                break;
            }
            default: {
                BidiLine.getRuns(bidi);
                bidiRunArray = bidi.runs;
                n5 = 0;
                for (n4 = 0; n4 < bidi.runCount; ++n4) {
                    n3 = bidiRunArray[n4].limit - n5;
                    n2 = n - bidiRunArray[n4].start;
                    if (n2 >= 0 && n2 < n3) {
                        if (bidiRunArray[n4].isEvenRun()) {
                            n6 = n5 + n2;
                            break;
                        }
                        n6 = n5 + n3 - n2 - 1;
                        break;
                    }
                    n5 += n3;
                }
                if (n4 < bidi.runCount) break;
                return 1;
            }
        }
        if (bidi.insertPoints.size > 0) {
            bidiRunArray = bidi.runs;
            n3 = 0;
            int n7 = 0;
            n4 = 0;
            while (true) {
                n5 = bidiRunArray[n4].limit - n3;
                n2 = bidiRunArray[n4].insertRemove;
                if ((n2 & 5) > 0) {
                    ++n7;
                }
                if (n6 < bidiRunArray[n4].limit) {
                    return n6 + n7;
                }
                if ((n2 & 0xA) > 0) {
                    ++n7;
                }
                ++n4;
                n3 += n5;
            }
        }
        if (bidi.controlCount > 0) {
            bidiRunArray = bidi.runs;
            int n8 = 0;
            int n9 = 0;
            char c = bidi.text[n];
            if (Bidi.IsBidiControlChar(c)) {
                return 1;
            }
            n4 = 0;
            while (true) {
                int n10 = bidiRunArray[n4].limit - n8;
                int n11 = bidiRunArray[n4].insertRemove;
                if (n6 >= bidiRunArray[n4].limit) {
                    n9 -= n11;
                } else {
                    if (n11 == 0) {
                        return n6 - n9;
                    }
                    if (bidiRunArray[n4].isEvenRun()) {
                        n2 = bidiRunArray[n4].start;
                        n3 = n;
                    } else {
                        n2 = n + 1;
                        n3 = bidiRunArray[n4].start + n10;
                    }
                    for (n5 = n2; n5 < n3; ++n5) {
                        c = bidi.text[n5];
                        if (!Bidi.IsBidiControlChar(c)) continue;
                        ++n9;
                    }
                    return n6 - n9;
                }
                ++n4;
                n8 += n10;
            }
        }
        return n6;
    }

    static int getLogicalIndex(Bidi bidi, int n) {
        int n2;
        int n3;
        int n4;
        int n5;
        BidiRun[] bidiRunArray;
        block21: {
            int n6;
            int n7;
            bidiRunArray = bidi.runs;
            n5 = bidi.runCount;
            if (bidi.insertPoints.size > 0) {
                n4 = 0;
                n7 = 0;
                n3 = 0;
                while (true) {
                    n6 = bidiRunArray[n3].limit - n7;
                    n2 = bidiRunArray[n3].insertRemove;
                    if ((n2 & 5) > 0) {
                        if (n <= n7 + n4) {
                            return 1;
                        }
                        ++n4;
                    }
                    if (n < bidiRunArray[n3].limit + n4) {
                        n -= n4;
                        break block21;
                    }
                    if ((n2 & 0xA) > 0) {
                        if (n == n7 + n6 + n4) {
                            return 1;
                        }
                        ++n4;
                    }
                    ++n3;
                    n7 += n6;
                }
            }
            if (bidi.controlCount > 0) {
                n4 = 0;
                int n8 = 0;
                n3 = 0;
                while (true) {
                    n7 = bidiRunArray[n3].limit - n8;
                    n2 = bidiRunArray[n3].insertRemove;
                    if (n >= bidiRunArray[n3].limit - n4 + n2) {
                        n4 -= n2;
                    } else {
                        if (n2 == 0) {
                            n += n4;
                            break;
                        }
                        n6 = bidiRunArray[n3].start;
                        boolean bl = bidiRunArray[n3].isEvenRun();
                        int n9 = n6 + n7 - 1;
                        for (int i = 0; i < n7; ++i) {
                            int n10 = bl ? n6 + i : n9 - i;
                            char c = bidi.text[n10];
                            if (Bidi.IsBidiControlChar(c)) {
                                ++n4;
                            }
                            if (n + n4 == n8 + i) break;
                        }
                        n += n4;
                        break;
                    }
                    ++n3;
                    n8 += n7;
                }
            }
        }
        if (n5 <= 10) {
            n3 = 0;
            while (n >= bidiRunArray[n3].limit) {
                ++n3;
            }
        } else {
            n4 = 0;
            n2 = n5;
            while (true) {
                n3 = n4 + n2 >>> 1;
                if (n >= bidiRunArray[n3].limit) {
                    n4 = n3 + 1;
                    continue;
                }
                if (n3 == 0 || n >= bidiRunArray[n3 - 1].limit) break;
                n2 = n3;
            }
        }
        int n11 = bidiRunArray[n3].start;
        if (bidiRunArray[n3].isEvenRun()) {
            if (n3 > 0) {
                n -= bidiRunArray[n3 - 1].limit;
            }
            return n11 + n;
        }
        return n11 + bidiRunArray[n3].limit - n - 1;
    }

    static int[] getLogicalMap(Bidi bidi) {
        int[] nArray;
        block18: {
            int n;
            int n2;
            int n3;
            BidiRun[] bidiRunArray;
            block17: {
                bidiRunArray = bidi.runs;
                nArray = new int[bidi.length];
                if (bidi.length > bidi.resultLength) {
                    Arrays.fill(nArray, -1);
                }
                n3 = 0;
                for (n2 = 0; n2 < bidi.runCount; ++n2) {
                    n = bidiRunArray[n2].start;
                    int n4 = bidiRunArray[n2].limit;
                    if (bidiRunArray[n2].isEvenRun()) {
                        do {
                            nArray[n++] = n3++;
                        } while (n3 < n4);
                        continue;
                    }
                    n += n4 - n3;
                    do {
                        nArray[--n] = n3++;
                    } while (n3 < n4);
                }
                if (bidi.insertPoints.size <= 0) break block17;
                n2 = 0;
                int n5 = bidi.runCount;
                bidiRunArray = bidi.runs;
                n3 = 0;
                int n6 = 0;
                while (n6 < n5) {
                    int n7 = bidiRunArray[n6].limit - n3;
                    int n8 = bidiRunArray[n6].insertRemove;
                    if ((n8 & 5) > 0) {
                        ++n2;
                    }
                    if (n2 > 0) {
                        n = bidiRunArray[n6].start;
                        int n9 = n + n7;
                        int n10 = n;
                        while (n10 < n9) {
                            int n11 = n10++;
                            nArray[n11] = nArray[n11] + n2;
                        }
                    }
                    if ((n8 & 0xA) > 0) {
                        ++n2;
                    }
                    ++n6;
                    n3 += n7;
                }
                break block18;
            }
            if (bidi.controlCount <= 0) break block18;
            n2 = 0;
            int n12 = bidi.runCount;
            bidiRunArray = bidi.runs;
            n3 = 0;
            int n13 = 0;
            while (n13 < n12) {
                int n14 = bidiRunArray[n13].limit - n3;
                int n15 = bidiRunArray[n13].insertRemove;
                if (n2 - n15 != 0) {
                    int n16;
                    n = bidiRunArray[n13].start;
                    boolean bl = bidiRunArray[n13].isEvenRun();
                    int n17 = n + n14;
                    if (n15 == 0) {
                        n16 = n;
                        while (n16 < n17) {
                            int n18 = n16++;
                            nArray[n18] = nArray[n18] - n2;
                        }
                    } else {
                        for (n16 = 0; n16 < n14; ++n16) {
                            int n19 = bl ? n + n16 : n17 - n16 - 1;
                            char c = bidi.text[n19];
                            if (Bidi.IsBidiControlChar(c)) {
                                ++n2;
                                nArray[n19] = -1;
                                continue;
                            }
                            int n20 = n19;
                            nArray[n20] = nArray[n20] - n2;
                        }
                    }
                }
                ++n13;
                n3 += n14;
            }
        }
        return nArray;
    }

    static int[] getVisualMap(Bidi bidi) {
        int n;
        int n2;
        int n3;
        BidiRun[] bidiRunArray = bidi.runs;
        int n4 = bidi.length > bidi.resultLength ? bidi.length : bidi.resultLength;
        int[] nArray = new int[n4];
        int n5 = 0;
        int n6 = 0;
        for (n3 = 0; n3 < bidi.runCount; ++n3) {
            n2 = bidiRunArray[n3].start;
            n = bidiRunArray[n3].limit;
            if (bidiRunArray[n3].isEvenRun()) {
                do {
                    nArray[n6++] = n2++;
                } while (++n5 < n);
                continue;
            }
            n2 += n - n5;
            do {
                nArray[n6++] = --n2;
            } while (++n5 < n);
        }
        if (bidi.insertPoints.size > 0) {
            int n7;
            int n8;
            n3 = 0;
            int n9 = bidi.runCount;
            bidiRunArray = bidi.runs;
            for (n8 = 0; n8 < n9; ++n8) {
                n7 = bidiRunArray[n8].insertRemove;
                if ((n7 & 5) > 0) {
                    ++n3;
                }
                if ((n7 & 0xA) <= 0) continue;
                ++n3;
            }
            int n10 = bidi.resultLength;
            for (n8 = n9 - 1; n8 >= 0 && n3 > 0; --n8) {
                n7 = bidiRunArray[n8].insertRemove;
                if ((n7 & 0xA) > 0) {
                    nArray[--n10] = -1;
                    --n3;
                }
                n5 = n8 > 0 ? bidiRunArray[n8 - 1].limit : 0;
                for (int i = bidiRunArray[n8].limit - 1; i >= n5 && n3 > 0; --i) {
                    nArray[--n10] = nArray[i];
                }
                if ((n7 & 5) <= 0) continue;
                nArray[--n10] = -1;
                --n3;
            }
        } else if (bidi.controlCount > 0) {
            n3 = bidi.runCount;
            bidiRunArray = bidi.runs;
            n5 = 0;
            int n11 = 0;
            int n12 = 0;
            while (n12 < n3) {
                int n13;
                int n14 = bidiRunArray[n12].limit - n5;
                int n15 = bidiRunArray[n12].insertRemove;
                if (n15 == 0 && n11 == n5) {
                    n11 += n14;
                } else if (n15 == 0) {
                    n = bidiRunArray[n12].limit;
                    for (n13 = n5; n13 < n; ++n13) {
                        nArray[n11++] = nArray[n13];
                    }
                } else {
                    n2 = bidiRunArray[n12].start;
                    boolean bl = bidiRunArray[n12].isEvenRun();
                    int n16 = n2 + n14 - 1;
                    for (n13 = 0; n13 < n14; ++n13) {
                        int n17 = bl ? n2 + n13 : n16 - n13;
                        char c = bidi.text[n17];
                        if (Bidi.IsBidiControlChar(c)) continue;
                        nArray[n11++] = n17;
                    }
                }
                ++n12;
                n5 += n14;
            }
        }
        if (n4 == bidi.resultLength) {
            return nArray;
        }
        int[] nArray2 = new int[bidi.resultLength];
        System.arraycopy(nArray, 0, nArray2, 0, bidi.resultLength);
        return nArray2;
    }

    static int[] invertMap(int[] nArray) {
        int n;
        int n2;
        int n3 = nArray.length;
        int n4 = -1;
        int n5 = 0;
        for (n2 = 0; n2 < n3; ++n2) {
            n = nArray[n2];
            if (n > n4) {
                n4 = n;
            }
            if (n < 0) continue;
            ++n5;
        }
        int[] nArray2 = new int[++n4];
        if (n5 < n4) {
            Arrays.fill(nArray2, -1);
        }
        for (n2 = 0; n2 < n3; ++n2) {
            n = nArray[n2];
            if (n < 0) continue;
            nArray2[n] = n2;
        }
        return nArray2;
    }
}

