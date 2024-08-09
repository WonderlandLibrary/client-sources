/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.compression;

final class Bzip2DivSufSort {
    private static final int STACK_SIZE = 64;
    private static final int BUCKET_A_SIZE = 256;
    private static final int BUCKET_B_SIZE = 65536;
    private static final int SS_BLOCKSIZE = 1024;
    private static final int INSERTIONSORT_THRESHOLD = 8;
    private static final int[] LOG_2_TABLE = new int[]{-1, 0, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7};
    private final int[] SA;
    private final byte[] T;
    private final int n;

    Bzip2DivSufSort(byte[] byArray, int[] nArray, int n) {
        this.T = byArray;
        this.SA = nArray;
        this.n = n;
    }

    private static void swapElements(int[] nArray, int n, int[] nArray2, int n2) {
        int n3 = nArray[n];
        nArray[n] = nArray2[n2];
        nArray2[n2] = n3;
    }

    private int ssCompare(int n, int n2, int n3) {
        int n4;
        int[] nArray = this.SA;
        byte[] byArray = this.T;
        int n5 = nArray[n + 1] + 2;
        int n6 = nArray[n2 + 1] + 2;
        int n7 = n3 + nArray[n];
        for (n4 = n3 + nArray[n2]; n7 < n5 && n4 < n6 && byArray[n7] == byArray[n4]; ++n7, ++n4) {
        }
        return n7 < n5 ? (n4 < n6 ? (byArray[n7] & 0xFF) - (byArray[n4] & 0xFF) : 1) : (n4 < n6 ? -1 : 0);
    }

    private int ssCompareLast(int n, int n2, int n3, int n4, int n5) {
        int n6;
        int[] nArray = this.SA;
        byte[] byArray = this.T;
        int n7 = n4 + nArray[n2];
        int n8 = n5;
        int n9 = nArray[n3 + 1] + 2;
        for (n6 = n4 + nArray[n3]; n7 < n8 && n6 < n9 && byArray[n7] == byArray[n6]; ++n7, ++n6) {
        }
        if (n7 < n8) {
            return n6 < n9 ? (byArray[n7] & 0xFF) - (byArray[n6] & 0xFF) : 1;
        }
        if (n6 == n9) {
            return 0;
        }
        n7 %= n5;
        n8 = nArray[n] + 2;
        while (n7 < n8 && n6 < n9 && byArray[n7] == byArray[n6]) {
            ++n7;
            ++n6;
        }
        return n7 < n8 ? (n6 < n9 ? (byArray[n7] & 0xFF) - (byArray[n6] & 0xFF) : 1) : (n6 < n9 ? -1 : 0);
    }

    private void ssInsertionSort(int n, int n2, int n3, int n4) {
        int[] nArray = this.SA;
        for (int i = n3 - 2; n2 <= i; --i) {
            int n5;
            int n6 = nArray[i];
            int n7 = i + 1;
            while (0 < (n5 = this.ssCompare(n + n6, n + nArray[n7], n4))) {
                do {
                    nArray[n7 - 1] = nArray[n7];
                } while (++n7 < n3 && nArray[n7] < 0);
                if (n3 > n7) continue;
            }
            if (n5 == 0) {
                nArray[n7] = ~nArray[n7];
            }
            nArray[n7 - 1] = n6;
        }
    }

    private void ssFixdown(int n, int n2, int n3, int n4, int n5) {
        int n6;
        int[] nArray = this.SA;
        byte[] byArray = this.T;
        int n7 = nArray[n3 + n4];
        int n8 = byArray[n + nArray[n2 + n7]] & 0xFF;
        while ((n6 = 2 * n4 + 1) < n5) {
            int n9;
            int n10;
            int n11;
            if ((n11 = byArray[n + nArray[n2 + nArray[n3 + (n10 = n6++)]]] & 0xFF) < (n9 = byArray[n + nArray[n2 + nArray[n3 + n6]]] & 0xFF)) {
                n10 = n6;
                n11 = n9;
            }
            if (n11 <= n8) break;
            nArray[n3 + n4] = nArray[n3 + n10];
            n4 = n10;
        }
        nArray[n3 + n4] = n7;
    }

    private void ssHeapSort(int n, int n2, int n3, int n4) {
        int n5;
        int[] nArray = this.SA;
        byte[] byArray = this.T;
        int n6 = n4;
        if (n4 % 2 == 0 && (byArray[n + nArray[n2 + nArray[n3 + --n6 / 2]]] & 0xFF) < (byArray[n + nArray[n2 + nArray[n3 + n6]]] & 0xFF)) {
            Bzip2DivSufSort.swapElements(nArray, n3 + n6, nArray, n3 + n6 / 2);
        }
        for (n5 = n6 / 2 - 1; 0 <= n5; --n5) {
            this.ssFixdown(n, n2, n3, n5, n6);
        }
        if (n4 % 2 == 0) {
            Bzip2DivSufSort.swapElements(nArray, n3, nArray, n3 + n6);
            this.ssFixdown(n, n2, n3, 0, n6);
        }
        for (n5 = n6 - 1; 0 < n5; --n5) {
            int n7 = nArray[n3];
            nArray[n3] = nArray[n3 + n5];
            this.ssFixdown(n, n2, n3, 0, n5);
            nArray[n3 + n5] = n7;
        }
    }

    private int ssMedian3(int n, int n2, int n3, int n4, int n5) {
        int[] nArray = this.SA;
        byte[] byArray = this.T;
        int n6 = byArray[n + nArray[n2 + nArray[n3]]] & 0xFF;
        int n7 = byArray[n + nArray[n2 + nArray[n4]]] & 0xFF;
        int n8 = byArray[n + nArray[n2 + nArray[n5]]] & 0xFF;
        if (n6 > n7) {
            int n9 = n3;
            n3 = n4;
            n4 = n9;
            int n10 = n6;
            n6 = n7;
            n7 = n10;
        }
        if (n7 > n8) {
            if (n6 > n8) {
                return n3;
            }
            return n5;
        }
        return n4;
    }

    private int ssMedian5(int n, int n2, int n3, int n4, int n5, int n6, int n7) {
        int n8;
        int n9;
        int[] nArray = this.SA;
        byte[] byArray = this.T;
        int n10 = byArray[n + nArray[n2 + nArray[n3]]] & 0xFF;
        int n11 = byArray[n + nArray[n2 + nArray[n4]]] & 0xFF;
        int n12 = byArray[n + nArray[n2 + nArray[n5]]] & 0xFF;
        int n13 = byArray[n + nArray[n2 + nArray[n6]]] & 0xFF;
        int n14 = byArray[n + nArray[n2 + nArray[n7]]] & 0xFF;
        if (n11 > n12) {
            n9 = n4;
            n4 = n5;
            n5 = n9;
            n8 = n11;
            n11 = n12;
            n12 = n8;
        }
        if (n13 > n14) {
            n9 = n6;
            n6 = n7;
            n7 = n9;
            n8 = n13;
            n13 = n14;
            n14 = n8;
        }
        if (n11 > n13) {
            n6 = n9 = n4;
            n13 = n8 = n11;
            n9 = n5;
            n5 = n7;
            n7 = n9;
            n8 = n12;
            n12 = n14;
            n14 = n8;
        }
        if (n10 > n12) {
            n9 = n3;
            n3 = n5;
            n5 = n9;
            n8 = n10;
            n10 = n12;
            n12 = n8;
        }
        if (n10 > n13) {
            n6 = n9 = n3;
            n13 = n8 = n10;
            n5 = n7;
            n12 = n14;
        }
        if (n12 > n13) {
            return n6;
        }
        return n5;
    }

    private int ssPivot(int n, int n2, int n3, int n4) {
        int n5 = n4 - n3;
        int n6 = n3 + n5 / 2;
        if (n5 <= 512) {
            if (n5 <= 32) {
                return this.ssMedian3(n, n2, n3, n6, n4 - 1);
            }
            return this.ssMedian5(n, n2, n3, n3 + (n5 >>= 2), n6, n4 - 1 - n5, n4 - 1);
        }
        return this.ssMedian3(n, n2, this.ssMedian3(n, n2, n3, n3 + (n5 >>= 3), n3 + (n5 << 1)), this.ssMedian3(n, n2, n6 - n5, n6, n6 + n5), this.ssMedian3(n, n2, n4 - 1 - (n5 << 1), n4 - 1 - n5, n4 - 1));
    }

    private static int ssLog(int n) {
        return (n & 0xFF00) != 0 ? 8 + LOG_2_TABLE[n >> 8 & 0xFF] : LOG_2_TABLE[n & 0xFF];
    }

    private int ssSubstringPartition(int n, int n2, int n3, int n4) {
        int[] nArray = this.SA;
        int n5 = n2 - 1;
        int n6 = n3;
        while (true) {
            if (++n5 < n6 && nArray[n + nArray[n5]] + n4 >= nArray[n + nArray[n5] + 1] + 1) {
                nArray[n5] = ~nArray[n5];
                continue;
            }
            --n6;
            while (n5 < n6 && nArray[n + nArray[n6]] + n4 < nArray[n + nArray[n6] + 1] + 1) {
                --n6;
            }
            if (n6 <= n5) break;
            int n7 = ~nArray[n6];
            nArray[n6] = nArray[n5];
            nArray[n5] = n7;
        }
        if (n2 < n5) {
            nArray[n2] = ~nArray[n2];
        }
        return n5;
    }

    private void ssMultiKeyIntroSort(int n, int n2, int n3, int n4) {
        int[] nArray = this.SA;
        byte[] byArray = this.T;
        StackEntry[] stackEntryArray = new StackEntry[64];
        int n5 = 0;
        int n6 = 0;
        int n7 = Bzip2DivSufSort.ssLog(n3 - n2);
        while (true) {
            int n8;
            int n9;
            int n10;
            int n11;
            if (n3 - n2 <= 8) {
                if (1 < n3 - n2) {
                    this.ssInsertionSort(n, n2, n3, n4);
                }
                if (n6 == 0) {
                    return;
                }
                StackEntry stackEntry = stackEntryArray[--n6];
                n2 = stackEntry.a;
                n3 = stackEntry.b;
                n4 = stackEntry.c;
                n7 = stackEntry.d;
                continue;
            }
            int n12 = n4;
            if (n7-- == 0) {
                this.ssHeapSort(n12, n, n2, n3 - n2);
            }
            if (n7 < 0) {
                n11 = byArray[n12 + nArray[n + nArray[n2]]] & 0xFF;
                for (n10 = n2 + 1; n10 < n3; ++n10) {
                    n5 = byArray[n12 + nArray[n + nArray[n10]]] & 0xFF;
                    if (n5 == n11) continue;
                    if (1 < n10 - n2) break;
                    n11 = n5;
                    n2 = n10;
                }
                if ((byArray[n12 + nArray[n + nArray[n2]] - 1] & 0xFF) < n11) {
                    n2 = this.ssSubstringPartition(n, n2, n10, n4);
                }
                if (n10 - n2 <= n3 - n10) {
                    if (1 < n10 - n2) {
                        stackEntryArray[n6++] = new StackEntry(n10, n3, n4, -1);
                        n3 = n10;
                        ++n4;
                        n7 = Bzip2DivSufSort.ssLog(n10 - n2);
                        continue;
                    }
                    n2 = n10;
                    n7 = -1;
                    continue;
                }
                if (1 < n3 - n10) {
                    stackEntryArray[n6++] = new StackEntry(n2, n10, n4 + 1, Bzip2DivSufSort.ssLog(n10 - n2));
                    n2 = n10;
                    n7 = -1;
                    continue;
                }
                n3 = n10;
                ++n4;
                n7 = Bzip2DivSufSort.ssLog(n10 - n2);
                continue;
            }
            n10 = this.ssPivot(n12, n, n2, n3);
            n11 = byArray[n12 + nArray[n + nArray[n10]]] & 0xFF;
            Bzip2DivSufSort.swapElements(nArray, n2, nArray, n10);
            for (n9 = n2 + 1; n9 < n3 && (n5 = byArray[n12 + nArray[n + nArray[n9]]] & 0xFF) == n11; ++n9) {
            }
            n10 = n9;
            if (n10 < n3 && n5 < n11) {
                while (++n9 < n3 && (n5 = byArray[n12 + nArray[n + nArray[n9]]] & 0xFF) <= n11) {
                    if (n5 != n11) continue;
                    Bzip2DivSufSort.swapElements(nArray, n9, nArray, n10);
                    ++n10;
                }
            }
            for (n8 = n3 - 1; n9 < n8 && (n5 = byArray[n12 + nArray[n + nArray[n8]]] & 0xFF) == n11; --n8) {
            }
            int n13 = n8;
            if (n9 < n13 && n5 > n11) {
                while (n9 < --n8 && (n5 = byArray[n12 + nArray[n + nArray[n8]]] & 0xFF) >= n11) {
                    if (n5 != n11) continue;
                    Bzip2DivSufSort.swapElements(nArray, n8, nArray, n13);
                    --n13;
                }
            }
            while (n9 < n8) {
                Bzip2DivSufSort.swapElements(nArray, n9, nArray, n8);
                while (++n9 < n8 && (n5 = byArray[n12 + nArray[n + nArray[n9]]] & 0xFF) <= n11) {
                    if (n5 != n11) continue;
                    Bzip2DivSufSort.swapElements(nArray, n9, nArray, n10);
                    ++n10;
                }
                while (n9 < --n8 && (n5 = byArray[n12 + nArray[n + nArray[n8]]] & 0xFF) >= n11) {
                    if (n5 != n11) continue;
                    Bzip2DivSufSort.swapElements(nArray, n8, nArray, n13);
                    --n13;
                }
            }
            if (n10 <= n13) {
                n8 = n9 - 1;
                int n14 = n10 - n2;
                int n15 = n9 - n10;
                if (n14 > n15) {
                    n14 = n15;
                }
                int n16 = n2;
                int n17 = n9 - n14;
                while (0 < n14) {
                    Bzip2DivSufSort.swapElements(nArray, n16, nArray, n17);
                    --n14;
                    ++n16;
                    ++n17;
                }
                n14 = n13 - n8;
                n15 = n3 - n13 - 1;
                if (n14 > n15) {
                    n14 = n15;
                }
                n16 = n9;
                n17 = n3 - n14;
                while (0 < n14) {
                    Bzip2DivSufSort.swapElements(nArray, n16, nArray, n17);
                    --n14;
                    ++n16;
                    ++n17;
                }
                n10 = n2 + (n9 - n10);
                n8 = n3 - (n13 - n8);
                int n18 = n9 = n11 <= (byArray[n12 + nArray[n + nArray[n10]] - 1] & 0xFF) ? n10 : this.ssSubstringPartition(n, n10, n8, n4);
                if (n10 - n2 <= n3 - n8) {
                    if (n3 - n8 <= n8 - n9) {
                        stackEntryArray[n6++] = new StackEntry(n9, n8, n4 + 1, Bzip2DivSufSort.ssLog(n8 - n9));
                        stackEntryArray[n6++] = new StackEntry(n8, n3, n4, n7);
                        n3 = n10;
                        continue;
                    }
                    if (n10 - n2 <= n8 - n9) {
                        stackEntryArray[n6++] = new StackEntry(n8, n3, n4, n7);
                        stackEntryArray[n6++] = new StackEntry(n9, n8, n4 + 1, Bzip2DivSufSort.ssLog(n8 - n9));
                        n3 = n10;
                        continue;
                    }
                    stackEntryArray[n6++] = new StackEntry(n8, n3, n4, n7);
                    stackEntryArray[n6++] = new StackEntry(n2, n10, n4, n7);
                    n2 = n9;
                    n3 = n8;
                    ++n4;
                    n7 = Bzip2DivSufSort.ssLog(n8 - n9);
                    continue;
                }
                if (n10 - n2 <= n8 - n9) {
                    stackEntryArray[n6++] = new StackEntry(n9, n8, n4 + 1, Bzip2DivSufSort.ssLog(n8 - n9));
                    stackEntryArray[n6++] = new StackEntry(n2, n10, n4, n7);
                    n2 = n8;
                    continue;
                }
                if (n3 - n8 <= n8 - n9) {
                    stackEntryArray[n6++] = new StackEntry(n2, n10, n4, n7);
                    stackEntryArray[n6++] = new StackEntry(n9, n8, n4 + 1, Bzip2DivSufSort.ssLog(n8 - n9));
                    n2 = n8;
                    continue;
                }
                stackEntryArray[n6++] = new StackEntry(n2, n10, n4, n7);
                stackEntryArray[n6++] = new StackEntry(n8, n3, n4, n7);
                n2 = n9;
                n3 = n8;
                ++n4;
                n7 = Bzip2DivSufSort.ssLog(n8 - n9);
                continue;
            }
            ++n7;
            if ((byArray[n12 + nArray[n + nArray[n2]] - 1] & 0xFF) < n11) {
                n2 = this.ssSubstringPartition(n, n2, n3, n4);
                n7 = Bzip2DivSufSort.ssLog(n3 - n2);
            }
            ++n4;
        }
    }

    private static void ssBlockSwap(int[] nArray, int n, int[] nArray2, int n2, int n3) {
        int n4 = n3;
        int n5 = n;
        int n6 = n2;
        while (0 < n4) {
            Bzip2DivSufSort.swapElements(nArray, n5, nArray2, n6);
            --n4;
            ++n5;
            ++n6;
        }
    }

    private void ssMergeForward(int n, int[] nArray, int n2, int n3, int n4, int n5, int n6) {
        int[] nArray2 = this.SA;
        int n7 = n2 + (n4 - n3) - 1;
        Bzip2DivSufSort.ssBlockSwap(nArray, n2, nArray2, n3, n4 - n3);
        int n8 = nArray2[n3];
        int n9 = n3;
        int n10 = n2;
        int n11 = n4;
        while (true) {
            int n12;
            if ((n12 = this.ssCompare(n + nArray[n10], n + nArray2[n11], n6)) < 0) {
                do {
                    nArray2[n9++] = nArray[n10];
                    if (n7 <= n10) {
                        nArray[n10] = n8;
                        return;
                    }
                    nArray[n10++] = nArray2[n9];
                } while (nArray[n10] < 0);
                continue;
            }
            if (n12 > 0) {
                do {
                    nArray2[n9++] = nArray2[n11];
                    nArray2[n11++] = nArray2[n9];
                    if (n5 > n11) continue;
                    while (n10 < n7) {
                        nArray2[n9++] = nArray[n10];
                        nArray[n10++] = nArray2[n9];
                    }
                    nArray2[n9] = nArray[n10];
                    nArray[n10] = n8;
                    return;
                } while (nArray2[n11] < 0);
                continue;
            }
            nArray2[n11] = ~nArray2[n11];
            do {
                nArray2[n9++] = nArray[n10];
                if (n7 <= n10) {
                    nArray[n10] = n8;
                    return;
                }
                nArray[n10++] = nArray2[n9];
            } while (nArray[n10] < 0);
            do {
                nArray2[n9++] = nArray2[n11];
                nArray2[n11++] = nArray2[n9];
                if (n5 > n11) continue;
                while (n10 < n7) {
                    nArray2[n9++] = nArray[n10];
                    nArray[n10++] = nArray2[n9];
                }
                nArray2[n9] = nArray[n10];
                nArray[n10] = n8;
                return;
            } while (nArray2[n11] < 0);
        }
    }

    private void ssMergeBackward(int n, int[] nArray, int n2, int n3, int n4, int n5, int n6) {
        int n7;
        int n8;
        int[] nArray2 = this.SA;
        int n9 = n2 + (n5 - n4);
        Bzip2DivSufSort.ssBlockSwap(nArray, n2, nArray2, n4, n5 - n4);
        int n10 = 0;
        if (nArray[n9 - 1] < 0) {
            n10 |= 1;
            n8 = n + ~nArray[n9 - 1];
        } else {
            n8 = n + nArray[n9 - 1];
        }
        if (nArray2[n4 - 1] < 0) {
            n10 |= 2;
            n7 = n + ~nArray2[n4 - 1];
        } else {
            n7 = n + nArray2[n4 - 1];
        }
        int n11 = nArray2[n5 - 1];
        int n12 = n5 - 1;
        int n13 = n9 - 1;
        int n14 = n4 - 1;
        while (true) {
            int n15;
            if ((n15 = this.ssCompare(n8, n7, n6)) > 0) {
                if ((n10 & 1) != 0) {
                    do {
                        nArray2[n12--] = nArray[n13];
                        nArray[n13--] = nArray2[n12];
                    } while (nArray[n13] < 0);
                    n10 ^= 1;
                }
                nArray2[n12--] = nArray[n13];
                if (n13 <= n2) {
                    nArray[n13] = n11;
                    return;
                }
                nArray[n13--] = nArray2[n12];
                if (nArray[n13] < 0) {
                    n10 |= 1;
                    n8 = n + ~nArray[n13];
                    continue;
                }
                n8 = n + nArray[n13];
                continue;
            }
            if (n15 < 0) {
                if ((n10 & 2) != 0) {
                    do {
                        nArray2[n12--] = nArray2[n14];
                        nArray2[n14--] = nArray2[n12];
                    } while (nArray2[n14] < 0);
                    n10 ^= 2;
                }
                nArray2[n12--] = nArray2[n14];
                nArray2[n14--] = nArray2[n12];
                if (n14 < n3) {
                    while (n2 < n13) {
                        nArray2[n12--] = nArray[n13];
                        nArray[n13--] = nArray2[n12];
                    }
                    nArray2[n12] = nArray[n13];
                    nArray[n13] = n11;
                    return;
                }
                if (nArray2[n14] < 0) {
                    n10 |= 2;
                    n7 = n + ~nArray2[n14];
                    continue;
                }
                n7 = n + nArray2[n14];
                continue;
            }
            if ((n10 & 1) != 0) {
                do {
                    nArray2[n12--] = nArray[n13];
                    nArray[n13--] = nArray2[n12];
                } while (nArray[n13] < 0);
                n10 ^= 1;
            }
            nArray2[n12--] = ~nArray[n13];
            if (n13 <= n2) {
                nArray[n13] = n11;
                return;
            }
            nArray[n13--] = nArray2[n12];
            if ((n10 & 2) != 0) {
                do {
                    nArray2[n12--] = nArray2[n14];
                    nArray2[n14--] = nArray2[n12];
                } while (nArray2[n14] < 0);
                n10 ^= 2;
            }
            nArray2[n12--] = nArray2[n14];
            nArray2[n14--] = nArray2[n12];
            if (n14 < n3) {
                while (n2 < n13) {
                    nArray2[n12--] = nArray[n13];
                    nArray[n13--] = nArray2[n12];
                }
                nArray2[n12] = nArray[n13];
                nArray[n13] = n11;
                return;
            }
            if (nArray[n13] < 0) {
                n10 |= 1;
                n8 = n + ~nArray[n13];
            } else {
                n8 = n + nArray[n13];
            }
            if (nArray2[n14] < 0) {
                n10 |= 2;
                n7 = n + ~nArray2[n14];
                continue;
            }
            n7 = n + nArray2[n14];
        }
    }

    private static int getIDX(int n) {
        return 0 <= n ? n : ~n;
    }

    private void ssMergeCheckEqual(int n, int n2, int n3) {
        int[] nArray = this.SA;
        if (0 <= nArray[n3] && this.ssCompare(n + Bzip2DivSufSort.getIDX(nArray[n3 - 1]), n + nArray[n3], n2) == 0) {
            nArray[n3] = ~nArray[n3];
        }
    }

    private void ssMerge(int n, int n2, int n3, int n4, int[] nArray, int n5, int n6, int n7) {
        int[] nArray2 = this.SA;
        StackEntry[] stackEntryArray = new StackEntry[64];
        int n8 = 0;
        int n9 = 0;
        while (true) {
            StackEntry stackEntry;
            if (n4 - n3 <= n6) {
                if (n2 < n3 && n3 < n4) {
                    this.ssMergeBackward(n, nArray, n5, n2, n3, n4, n7);
                }
                if (n8 & true) {
                    this.ssMergeCheckEqual(n, n7, n2);
                }
                if ((n8 & 2) != 0) {
                    this.ssMergeCheckEqual(n, n7, n4);
                }
                if (n9 == 0) {
                    return;
                }
                stackEntry = stackEntryArray[--n9];
                n2 = stackEntry.a;
                n3 = stackEntry.b;
                n4 = stackEntry.c;
                n8 = stackEntry.d;
                continue;
            }
            if (n3 - n2 <= n6) {
                if (n2 < n3) {
                    this.ssMergeForward(n, nArray, n5, n2, n3, n4, n7);
                }
                if ((n8 & 1) != 0) {
                    this.ssMergeCheckEqual(n, n7, n2);
                }
                if ((n8 & 2) != 0) {
                    this.ssMergeCheckEqual(n, n7, n4);
                }
                if (n9 == 0) {
                    return;
                }
                stackEntry = stackEntryArray[--n9];
                n2 = stackEntry.a;
                n3 = stackEntry.b;
                n4 = stackEntry.c;
                n8 = stackEntry.d;
                continue;
            }
            int n10 = 0;
            int n11 = Math.min(n3 - n2, n4 - n3);
            int n12 = n11 >> 1;
            while (0 < n11) {
                if (this.ssCompare(n + Bzip2DivSufSort.getIDX(nArray2[n3 + n10 + n12]), n + Bzip2DivSufSort.getIDX(nArray2[n3 - n10 - n12 - 1]), n7) < 0) {
                    n10 += n12 + 1;
                    n12 -= n11 & 1 ^ 1;
                }
                n11 = n12;
                n12 >>= 1;
            }
            if (0 < n10) {
                int n13;
                Bzip2DivSufSort.ssBlockSwap(nArray2, n3 - n10, nArray2, n3, n10);
                int n14 = n13 = n3;
                int n15 = 0;
                if (n3 + n10 < n4) {
                    if (nArray2[n3 + n10] < 0) {
                        while (nArray2[n14 - 1] < 0) {
                            --n14;
                        }
                        nArray2[n3 + n10] = ~nArray2[n3 + n10];
                    }
                    n13 = n3;
                    while (nArray2[n13] < 0) {
                        ++n13;
                    }
                    n15 = 1;
                }
                if (n14 - n2 <= n4 - n13) {
                    stackEntryArray[n9++] = new StackEntry(n13, n3 + n10, n4, n8 & 2 | n15 & 1);
                    n3 -= n10;
                    n4 = n14;
                    n8 &= 1;
                    continue;
                }
                if (n14 == n3 && n3 == n13) {
                    n15 <<= 1;
                }
                stackEntryArray[n9++] = new StackEntry(n2, n3 - n10, n14, n8 & 1 | n15 & 2);
                n2 = n13;
                n3 += n10;
                n8 = n8 & 2 | n15 & 1;
                continue;
            }
            if ((n8 & 1) != 0) {
                this.ssMergeCheckEqual(n, n7, n2);
            }
            this.ssMergeCheckEqual(n, n7, n3);
            if ((n8 & 2) != 0) {
                this.ssMergeCheckEqual(n, n7, n4);
            }
            if (n9 == 0) {
                return;
            }
            stackEntry = stackEntryArray[--n9];
            n2 = stackEntry.a;
            n3 = stackEntry.b;
            n4 = stackEntry.c;
            n8 = stackEntry.d;
        }
    }

    private void subStringSort(int n, int n2, int n3, int[] nArray, int n4, int n5, int n6, boolean bl, int n7) {
        int n8;
        int[] nArray2 = this.SA;
        if (bl) {
            ++n2;
        }
        int n9 = n2;
        int n10 = 0;
        while (n9 + 1024 < n3) {
            this.ssMultiKeyIntroSort(n, n9, n9 + 1024, n6);
            int[] nArray3 = nArray2;
            int n11 = n9 + 1024;
            int n12 = n3 - (n9 + 1024);
            if (n12 <= n5) {
                n12 = n5;
                nArray3 = nArray;
                n11 = n4;
            }
            int n13 = n9;
            n8 = 1024;
            int n14 = n10;
            while ((n14 & 1) != 0) {
                this.ssMerge(n, n13 - n8, n13, n13 + n8, nArray3, n11, n12, n6);
                n13 -= n8;
                n8 <<= 1;
                n14 >>>= 1;
            }
            n9 += 1024;
            ++n10;
        }
        this.ssMultiKeyIntroSort(n, n9, n3, n6);
        n8 = 1024;
        while (n10 != 0) {
            if (n10 & true) {
                this.ssMerge(n, n9 - n8, n9, n3, nArray, n4, n5, n6);
                n9 -= n8;
            }
            n8 <<= 1;
            n10 >>= 1;
        }
        if (bl) {
            n10 = nArray2[n2 - 1];
            int n15 = 1;
            for (n9 = n2; n9 < n3 && (nArray2[n9] < 0 || 0 < (n15 = this.ssCompareLast(n, n + n10, n + nArray2[n9], n6, n7))); ++n9) {
                nArray2[n9 - 1] = nArray2[n9];
            }
            if (n15 == 0) {
                nArray2[n9] = ~nArray2[n9];
            }
            nArray2[n9 - 1] = n10;
        }
    }

    private int trGetC(int n, int n2, int n3, int n4) {
        return n2 + n4 < n3 ? this.SA[n2 + n4] : this.SA[n + (n2 - n + n4) % (n3 - n)];
    }

    private void trFixdown(int n, int n2, int n3, int n4, int n5, int n6) {
        int n7;
        int[] nArray = this.SA;
        int n8 = nArray[n4 + n5];
        int n9 = this.trGetC(n, n2, n3, n8);
        while ((n7 = 2 * n5 + 1) < n6) {
            int n10;
            int n11;
            int n12;
            if ((n12 = this.trGetC(n, n2, n3, nArray[n4 + (n11 = n7++)])) < (n10 = this.trGetC(n, n2, n3, nArray[n4 + n7]))) {
                n11 = n7;
                n12 = n10;
            }
            if (n12 <= n9) break;
            nArray[n4 + n5] = nArray[n4 + n11];
            n5 = n11;
        }
        nArray[n4 + n5] = n8;
    }

    private void trHeapSort(int n, int n2, int n3, int n4, int n5) {
        int n6;
        int[] nArray = this.SA;
        int n7 = n5;
        if (n5 % 2 == 0 && this.trGetC(n, n2, n3, nArray[n4 + --n7 / 2]) < this.trGetC(n, n2, n3, nArray[n4 + n7])) {
            Bzip2DivSufSort.swapElements(nArray, n4 + n7, nArray, n4 + n7 / 2);
        }
        for (n6 = n7 / 2 - 1; 0 <= n6; --n6) {
            this.trFixdown(n, n2, n3, n4, n6, n7);
        }
        if (n5 % 2 == 0) {
            Bzip2DivSufSort.swapElements(nArray, n4, nArray, n4 + n7);
            this.trFixdown(n, n2, n3, n4, 0, n7);
        }
        for (n6 = n7 - 1; 0 < n6; --n6) {
            int n8 = nArray[n4];
            nArray[n4] = nArray[n4 + n6];
            this.trFixdown(n, n2, n3, n4, 0, n6);
            nArray[n4 + n6] = n8;
        }
    }

    private void trInsertionSort(int n, int n2, int n3, int n4, int n5) {
        int[] nArray = this.SA;
        for (int i = n4 + 1; i < n5; ++i) {
            int n6;
            int n7 = nArray[i];
            int n8 = i - 1;
            while (0 > (n6 = this.trGetC(n, n2, n3, n7) - this.trGetC(n, n2, n3, nArray[n8]))) {
                do {
                    nArray[n8 + 1] = nArray[n8];
                } while (n4 <= --n8 && nArray[n8] < 0);
                if (n8 >= n4) continue;
            }
            if (n6 == 0) {
                nArray[n8] = ~nArray[n8];
            }
            nArray[n8 + 1] = n7;
        }
    }

    private static int trLog(int n) {
        return (n & 0xFFFF0000) != 0 ? ((n & 0xFF000000) != 0 ? 24 + LOG_2_TABLE[n >> 24 & 0xFF] : LOG_2_TABLE[n >> 16 & 0x10F]) : ((n & 0xFF00) != 0 ? 8 + LOG_2_TABLE[n >> 8 & 0xFF] : LOG_2_TABLE[n & 0xFF]);
    }

    private int trMedian3(int n, int n2, int n3, int n4, int n5, int n6) {
        int[] nArray = this.SA;
        int n7 = this.trGetC(n, n2, n3, nArray[n4]);
        int n8 = this.trGetC(n, n2, n3, nArray[n5]);
        int n9 = this.trGetC(n, n2, n3, nArray[n6]);
        if (n7 > n8) {
            int n10 = n4;
            n4 = n5;
            n5 = n10;
            int n11 = n7;
            n7 = n8;
            n8 = n11;
        }
        if (n8 > n9) {
            if (n7 > n9) {
                return n4;
            }
            return n6;
        }
        return n5;
    }

    private int trMedian5(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        int n9;
        int n10;
        int[] nArray = this.SA;
        int n11 = this.trGetC(n, n2, n3, nArray[n4]);
        int n12 = this.trGetC(n, n2, n3, nArray[n5]);
        int n13 = this.trGetC(n, n2, n3, nArray[n6]);
        int n14 = this.trGetC(n, n2, n3, nArray[n7]);
        int n15 = this.trGetC(n, n2, n3, nArray[n8]);
        if (n12 > n13) {
            n10 = n5;
            n5 = n6;
            n6 = n10;
            n9 = n12;
            n12 = n13;
            n13 = n9;
        }
        if (n14 > n15) {
            n10 = n7;
            n7 = n8;
            n8 = n10;
            n9 = n14;
            n14 = n15;
            n15 = n9;
        }
        if (n12 > n14) {
            n7 = n10 = n5;
            n14 = n9 = n12;
            n10 = n6;
            n6 = n8;
            n8 = n10;
            n9 = n13;
            n13 = n15;
            n15 = n9;
        }
        if (n11 > n13) {
            n10 = n4;
            n4 = n6;
            n6 = n10;
            n9 = n11;
            n11 = n13;
            n13 = n9;
        }
        if (n11 > n14) {
            n7 = n10 = n4;
            n14 = n9 = n11;
            n6 = n8;
            n13 = n15;
        }
        if (n13 > n14) {
            return n7;
        }
        return n6;
    }

    private int trPivot(int n, int n2, int n3, int n4, int n5) {
        int n6 = n5 - n4;
        int n7 = n4 + n6 / 2;
        if (n6 <= 512) {
            if (n6 <= 32) {
                return this.trMedian3(n, n2, n3, n4, n7, n5 - 1);
            }
            return this.trMedian5(n, n2, n3, n4, n4 + (n6 >>= 2), n7, n5 - 1 - n6, n5 - 1);
        }
        return this.trMedian3(n, n2, n3, this.trMedian3(n, n2, n3, n4, n4 + (n6 >>= 3), n4 + (n6 << 1)), this.trMedian3(n, n2, n3, n7 - n6, n7, n7 + n6), this.trMedian3(n, n2, n3, n5 - 1 - (n6 << 1), n5 - 1 - n6, n5 - 1));
    }

    private void lsUpdateGroup(int n, int n2, int n3) {
        int[] nArray = this.SA;
        for (int i = n2; i < n3; ++i) {
            int n4;
            if (0 <= nArray[i]) {
                n4 = i;
                do {
                    nArray[n + nArray[i]] = i;
                } while (++i < n3 && 0 <= nArray[i]);
                nArray[n4] = n4 - i;
                if (n3 <= i) break;
            }
            n4 = i;
            do {
                nArray[i] = ~nArray[i];
            } while (nArray[++i] < 0);
            int n5 = i;
            do {
                nArray[n + nArray[n4]] = n5;
            } while (++n4 <= i);
        }
    }

    private void lsIntroSort(int n, int n2, int n3, int n4, int n5) {
        int[] nArray = this.SA;
        StackEntry[] stackEntryArray = new StackEntry[64];
        int n6 = 0;
        int n7 = 0;
        int n8 = Bzip2DivSufSort.trLog(n5 - n4);
        while (true) {
            int n9;
            int n10;
            int n11;
            StackEntry stackEntry;
            if (n5 - n4 <= 8) {
                if (1 < n5 - n4) {
                    this.trInsertionSort(n, n2, n3, n4, n5);
                    this.lsUpdateGroup(n, n4, n5);
                } else if (n5 - n4 == 1) {
                    nArray[n4] = -1;
                }
                if (n7 == 0) {
                    return;
                }
                stackEntry = stackEntryArray[--n7];
                n4 = stackEntry.a;
                n5 = stackEntry.b;
                n8 = stackEntry.c;
                continue;
            }
            if (n8-- == 0) {
                this.trHeapSort(n, n2, n3, n4, n5 - n4);
                n11 = n5 - 1;
                while (n4 < n11) {
                    n6 = this.trGetC(n, n2, n3, nArray[n11]);
                    for (n10 = n11 - 1; n4 <= n10 && this.trGetC(n, n2, n3, nArray[n10]) == n6; --n10) {
                        nArray[n10] = ~nArray[n10];
                    }
                    n11 = n10;
                }
                this.lsUpdateGroup(n, n4, n5);
                if (n7 == 0) {
                    return;
                }
                stackEntry = stackEntryArray[--n7];
                n4 = stackEntry.a;
                n5 = stackEntry.b;
                n8 = stackEntry.c;
                continue;
            }
            n11 = this.trPivot(n, n2, n3, n4, n5);
            Bzip2DivSufSort.swapElements(nArray, n4, nArray, n11);
            int n12 = this.trGetC(n, n2, n3, nArray[n4]);
            for (n10 = n4 + 1; n10 < n5 && (n6 = this.trGetC(n, n2, n3, nArray[n10])) == n12; ++n10) {
            }
            n11 = n10;
            if (n11 < n5 && n6 < n12) {
                while (++n10 < n5 && (n6 = this.trGetC(n, n2, n3, nArray[n10])) <= n12) {
                    if (n6 != n12) continue;
                    Bzip2DivSufSort.swapElements(nArray, n10, nArray, n11);
                    ++n11;
                }
            }
            for (n9 = n5 - 1; n10 < n9 && (n6 = this.trGetC(n, n2, n3, nArray[n9])) == n12; --n9) {
            }
            int n13 = n9;
            if (n10 < n13 && n6 > n12) {
                while (n10 < --n9 && (n6 = this.trGetC(n, n2, n3, nArray[n9])) >= n12) {
                    if (n6 != n12) continue;
                    Bzip2DivSufSort.swapElements(nArray, n9, nArray, n13);
                    --n13;
                }
            }
            while (n10 < n9) {
                Bzip2DivSufSort.swapElements(nArray, n10, nArray, n9);
                while (++n10 < n9 && (n6 = this.trGetC(n, n2, n3, nArray[n10])) <= n12) {
                    if (n6 != n12) continue;
                    Bzip2DivSufSort.swapElements(nArray, n10, nArray, n11);
                    ++n11;
                }
                while (n10 < --n9 && (n6 = this.trGetC(n, n2, n3, nArray[n9])) >= n12) {
                    if (n6 != n12) continue;
                    Bzip2DivSufSort.swapElements(nArray, n9, nArray, n13);
                    --n13;
                }
            }
            if (n11 <= n13) {
                n9 = n10 - 1;
                int n14 = n11 - n4;
                int n15 = n10 - n11;
                if (n14 > n15) {
                    n14 = n15;
                }
                int n16 = n4;
                int n17 = n10 - n14;
                while (0 < n14) {
                    Bzip2DivSufSort.swapElements(nArray, n16, nArray, n17);
                    --n14;
                    ++n16;
                    ++n17;
                }
                n14 = n13 - n9;
                n15 = n5 - n13 - 1;
                if (n14 > n15) {
                    n14 = n15;
                }
                n16 = n10;
                n17 = n5 - n14;
                while (0 < n14) {
                    Bzip2DivSufSort.swapElements(nArray, n16, nArray, n17);
                    --n14;
                    ++n16;
                    ++n17;
                }
                n11 = n4 + (n10 - n11);
                n10 = n5 - (n13 - n9);
                n12 = n11 - 1;
                for (n9 = n4; n9 < n11; ++n9) {
                    nArray[n + nArray[n9]] = n12;
                }
                if (n10 < n5) {
                    n12 = n10 - 1;
                    for (n9 = n11; n9 < n10; ++n9) {
                        nArray[n + nArray[n9]] = n12;
                    }
                }
                if (n10 - n11 == 1) {
                    nArray[n11] = -1;
                }
                if (n11 - n4 <= n5 - n10) {
                    if (n4 < n11) {
                        stackEntryArray[n7++] = new StackEntry(n10, n5, n8, 0);
                        n5 = n11;
                        continue;
                    }
                    n4 = n10;
                    continue;
                }
                if (n10 < n5) {
                    stackEntryArray[n7++] = new StackEntry(n4, n11, n8, 0);
                    n4 = n10;
                    continue;
                }
                n5 = n11;
                continue;
            }
            if (n7 == 0) {
                return;
            }
            stackEntry = stackEntryArray[--n7];
            n4 = stackEntry.a;
            n5 = stackEntry.b;
            n8 = stackEntry.c;
        }
    }

    private void lsSort(int n, int n2, int n3) {
        int[] nArray = this.SA;
        int n4 = n + n3;
        while (-n2 < nArray[0]) {
            int n5;
            int n6;
            int n7 = 0;
            int n8 = 0;
            do {
                if ((n6 = nArray[n7]) < 0) {
                    n7 -= n6;
                    n8 += n6;
                    continue;
                }
                if (n8 != 0) {
                    nArray[n7 + n8] = n8;
                    n8 = 0;
                }
                n5 = nArray[n + n6] + 1;
                this.lsIntroSort(n, n4, n + n2, n7, n5);
                n7 = n5;
            } while (n7 < n2);
            if (n8 != 0) {
                nArray[n7 + n8] = n8;
            }
            if (n2 < n4 - n) {
                n7 = 0;
                do {
                    if ((n6 = nArray[n7]) < 0) {
                        n7 -= n6;
                        continue;
                    }
                    n5 = nArray[n + n6] + 1;
                    for (int i = n7; i < n5; ++i) {
                        nArray[n + nArray[i]] = i;
                    }
                    n7 = n5;
                } while (n7 < n2);
                break;
            }
            n4 += n4 - n;
        }
    }

    private PartitionResult trPartition(int n, int n2, int n3, int n4, int n5, int n6) {
        int n7;
        int n8;
        int[] nArray = this.SA;
        int n9 = 0;
        for (n8 = n4; n8 < n5 && (n9 = this.trGetC(n, n2, n3, nArray[n8])) == n6; ++n8) {
        }
        int n10 = n8;
        if (n10 < n5 && n9 < n6) {
            while (++n8 < n5 && (n9 = this.trGetC(n, n2, n3, nArray[n8])) <= n6) {
                if (n9 != n6) continue;
                Bzip2DivSufSort.swapElements(nArray, n8, nArray, n10);
                ++n10;
            }
        }
        for (n7 = n5 - 1; n8 < n7 && (n9 = this.trGetC(n, n2, n3, nArray[n7])) == n6; --n7) {
        }
        int n11 = n7;
        if (n8 < n11 && n9 > n6) {
            while (n8 < --n7 && (n9 = this.trGetC(n, n2, n3, nArray[n7])) >= n6) {
                if (n9 != n6) continue;
                Bzip2DivSufSort.swapElements(nArray, n7, nArray, n11);
                --n11;
            }
        }
        while (n8 < n7) {
            Bzip2DivSufSort.swapElements(nArray, n8, nArray, n7);
            while (++n8 < n7 && (n9 = this.trGetC(n, n2, n3, nArray[n8])) <= n6) {
                if (n9 != n6) continue;
                Bzip2DivSufSort.swapElements(nArray, n8, nArray, n10);
                ++n10;
            }
            while (n8 < --n7 && (n9 = this.trGetC(n, n2, n3, nArray[n7])) >= n6) {
                if (n9 != n6) continue;
                Bzip2DivSufSort.swapElements(nArray, n7, nArray, n11);
                --n11;
            }
        }
        if (n10 <= n11) {
            n7 = n8 - 1;
            int n12 = n10 - n4;
            int n13 = n8 - n10;
            if (n12 > n13) {
                n12 = n13;
            }
            int n14 = n4;
            int n15 = n8 - n12;
            while (0 < n12) {
                Bzip2DivSufSort.swapElements(nArray, n14, nArray, n15);
                --n12;
                ++n14;
                ++n15;
            }
            n12 = n11 - n7;
            n13 = n5 - n11 - 1;
            if (n12 > n13) {
                n12 = n13;
            }
            n14 = n8;
            n15 = n5 - n12;
            while (0 < n12) {
                Bzip2DivSufSort.swapElements(nArray, n14, nArray, n15);
                --n12;
                ++n14;
                ++n15;
            }
            n4 += n8 - n10;
            n5 -= n11 - n7;
        }
        return new PartitionResult(n4, n5);
    }

    private void trCopy(int n, int n2, int n3, int n4, int n5, int n6, int n7) {
        int n8;
        int n9;
        int[] nArray = this.SA;
        int n10 = n5 - 1;
        int n11 = n4 - 1;
        for (n9 = n3; n9 <= n11; ++n9) {
            n8 = nArray[n9] - n7;
            if (n8 < 0) {
                n8 += n2 - n;
            }
            if (nArray[n + n8] != n10) continue;
            nArray[++n11] = n8;
            nArray[n + n8] = n11;
        }
        n9 = n6 - 1;
        int n12 = n11 + 1;
        n11 = n5;
        while (n12 < n11) {
            n8 = nArray[n9] - n7;
            if (n8 < 0) {
                n8 += n2 - n;
            }
            if (nArray[n + n8] == n10) {
                nArray[--n11] = n8;
                nArray[n + n8] = n11;
            }
            --n9;
        }
    }

    private void trIntroSort(int n, int n2, int n3, int n4, int n5, TRBudget tRBudget, int n6) {
        int n7;
        int[] nArray = this.SA;
        StackEntry[] stackEntryArray = new StackEntry[64];
        int n8 = 0;
        int n9 = 0;
        int n10 = Bzip2DivSufSort.trLog(n5 - n4);
        while (true) {
            int n11;
            int n12;
            int n13;
            int n14;
            int n15;
            Object object;
            if (n10 < 0) {
                if (n10 == -1) {
                    StackEntry stackEntry;
                    if (!tRBudget.update(n6, n5 - n4)) break;
                    object = this.trPartition(n, n2 - 1, n3, n4, n5, n5 - 1);
                    n15 = ((PartitionResult)object).first;
                    n14 = ((PartitionResult)object).last;
                    if (n4 < n15 || n14 < n5) {
                        if (n15 < n5) {
                            n13 = n15 - 1;
                            for (n12 = n4; n12 < n15; ++n12) {
                                nArray[n + nArray[n12]] = n13;
                            }
                        }
                        if (n14 < n5) {
                            n13 = n14 - 1;
                            for (n12 = n15; n12 < n14; ++n12) {
                                nArray[n + nArray[n12]] = n13;
                            }
                        }
                        stackEntryArray[n9++] = new StackEntry(0, n15, n14, 0);
                        stackEntryArray[n9++] = new StackEntry(n2 - 1, n4, n5, -2);
                        if (n15 - n4 <= n5 - n14) {
                            if (1 < n15 - n4) {
                                stackEntryArray[n9++] = new StackEntry(n2, n14, n5, Bzip2DivSufSort.trLog(n5 - n14));
                                n5 = n15;
                                n10 = Bzip2DivSufSort.trLog(n15 - n4);
                                continue;
                            }
                            if (1 < n5 - n14) {
                                n4 = n14;
                                n10 = Bzip2DivSufSort.trLog(n5 - n14);
                                continue;
                            }
                            if (n9 == 0) {
                                return;
                            }
                            stackEntry = stackEntryArray[--n9];
                            n2 = stackEntry.a;
                            n4 = stackEntry.b;
                            n5 = stackEntry.c;
                            n10 = stackEntry.d;
                            continue;
                        }
                        if (1 < n5 - n14) {
                            stackEntryArray[n9++] = new StackEntry(n2, n4, n15, Bzip2DivSufSort.trLog(n15 - n4));
                            n4 = n14;
                            n10 = Bzip2DivSufSort.trLog(n5 - n14);
                            continue;
                        }
                        if (1 < n15 - n4) {
                            n5 = n15;
                            n10 = Bzip2DivSufSort.trLog(n15 - n4);
                            continue;
                        }
                        if (n9 == 0) {
                            return;
                        }
                        stackEntry = stackEntryArray[--n9];
                        n2 = stackEntry.a;
                        n4 = stackEntry.b;
                        n5 = stackEntry.c;
                        n10 = stackEntry.d;
                        continue;
                    }
                    for (n12 = n4; n12 < n5; ++n12) {
                        nArray[n + nArray[n12]] = n12;
                    }
                    if (n9 == 0) {
                        return;
                    }
                    stackEntry = stackEntryArray[--n9];
                    n2 = stackEntry.a;
                    n4 = stackEntry.b;
                    n5 = stackEntry.c;
                    n10 = stackEntry.d;
                    continue;
                }
                if (n10 == -2) {
                    n15 = stackEntryArray[--n9].b;
                    n14 = stackEntryArray[n9].c;
                    this.trCopy(n, n3, n4, n15, n14, n5, n2 - n);
                    if (n9 == 0) {
                        return;
                    }
                    object = stackEntryArray[--n9];
                    n2 = ((StackEntry)object).a;
                    n4 = ((StackEntry)object).b;
                    n5 = ((StackEntry)object).c;
                    n10 = ((StackEntry)object).d;
                    continue;
                }
                if (0 <= nArray[n4]) {
                    n15 = n4;
                    do {
                        nArray[n + nArray[n15]] = n15;
                    } while (++n15 < n5 && 0 <= nArray[n15]);
                    n4 = n15;
                }
                if (n4 < n5) {
                    n15 = n4;
                    do {
                        nArray[n15] = ~nArray[n15];
                    } while (nArray[++n15] < 0);
                    int n16 = n11 = nArray[n + nArray[n15]] != nArray[n2 + nArray[n15]] ? Bzip2DivSufSort.trLog(n15 - n4 + 1) : -1;
                    if (++n15 < n5) {
                        n13 = n15 - 1;
                        for (n14 = n4; n14 < n15; ++n14) {
                            nArray[n + nArray[n14]] = n13;
                        }
                    }
                    if (n15 - n4 <= n5 - n15) {
                        stackEntryArray[n9++] = new StackEntry(n2, n15, n5, -3);
                        ++n2;
                        n5 = n15;
                        n10 = n11;
                        continue;
                    }
                    if (1 < n5 - n15) {
                        stackEntryArray[n9++] = new StackEntry(n2 + 1, n4, n15, n11);
                        n4 = n15;
                        n10 = -3;
                        continue;
                    }
                    ++n2;
                    n5 = n15;
                    n10 = n11;
                    continue;
                }
                if (n9 == 0) {
                    return;
                }
                object = stackEntryArray[--n9];
                n2 = ((StackEntry)object).a;
                n4 = ((StackEntry)object).b;
                n5 = ((StackEntry)object).c;
                n10 = ((StackEntry)object).d;
                continue;
            }
            if (n5 - n4 <= 8) {
                if (!tRBudget.update(n6, n5 - n4)) break;
                this.trInsertionSort(n, n2, n3, n4, n5);
                n10 = -3;
                continue;
            }
            if (n10-- == 0) {
                if (!tRBudget.update(n6, n5 - n4)) break;
                this.trHeapSort(n, n2, n3, n4, n5 - n4);
                n15 = n5 - 1;
                while (n4 < n15) {
                    n8 = this.trGetC(n, n2, n3, nArray[n15]);
                    for (n14 = n15 - 1; n4 <= n14 && this.trGetC(n, n2, n3, nArray[n14]) == n8; --n14) {
                        nArray[n14] = ~nArray[n14];
                    }
                    n15 = n14;
                }
                n10 = -3;
                continue;
            }
            n15 = this.trPivot(n, n2, n3, n4, n5);
            Bzip2DivSufSort.swapElements(nArray, n4, nArray, n15);
            n13 = this.trGetC(n, n2, n3, nArray[n4]);
            for (n14 = n4 + 1; n14 < n5 && (n8 = this.trGetC(n, n2, n3, nArray[n14])) == n13; ++n14) {
            }
            n15 = n14;
            if (n15 < n5 && n8 < n13) {
                while (++n14 < n5 && (n8 = this.trGetC(n, n2, n3, nArray[n14])) <= n13) {
                    if (n8 != n13) continue;
                    Bzip2DivSufSort.swapElements(nArray, n14, nArray, n15);
                    ++n15;
                }
            }
            for (n12 = n5 - 1; n14 < n12 && (n8 = this.trGetC(n, n2, n3, nArray[n12])) == n13; --n12) {
            }
            int n17 = n12;
            if (n14 < n17 && n8 > n13) {
                while (n14 < --n12 && (n8 = this.trGetC(n, n2, n3, nArray[n12])) >= n13) {
                    if (n8 != n13) continue;
                    Bzip2DivSufSort.swapElements(nArray, n12, nArray, n17);
                    --n17;
                }
            }
            while (n14 < n12) {
                Bzip2DivSufSort.swapElements(nArray, n14, nArray, n12);
                while (++n14 < n12 && (n8 = this.trGetC(n, n2, n3, nArray[n14])) <= n13) {
                    if (n8 != n13) continue;
                    Bzip2DivSufSort.swapElements(nArray, n14, nArray, n15);
                    ++n15;
                }
                while (n14 < --n12 && (n8 = this.trGetC(n, n2, n3, nArray[n12])) >= n13) {
                    if (n8 != n13) continue;
                    Bzip2DivSufSort.swapElements(nArray, n12, nArray, n17);
                    --n17;
                }
            }
            if (n15 <= n17) {
                n12 = n14 - 1;
                n7 = n15 - n4;
                int n18 = n14 - n15;
                if (n7 > n18) {
                    n7 = n18;
                }
                int n19 = n4;
                int n20 = n14 - n7;
                while (0 < n7) {
                    Bzip2DivSufSort.swapElements(nArray, n19, nArray, n20);
                    --n7;
                    ++n19;
                    ++n20;
                }
                n7 = n17 - n12;
                n18 = n5 - n17 - 1;
                if (n7 > n18) {
                    n7 = n18;
                }
                n19 = n14;
                n20 = n5 - n7;
                while (0 < n7) {
                    Bzip2DivSufSort.swapElements(nArray, n19, nArray, n20);
                    --n7;
                    ++n19;
                    ++n20;
                }
                n15 = n4 + (n14 - n15);
                n14 = n5 - (n17 - n12);
                n11 = nArray[n + nArray[n15]] != n13 ? Bzip2DivSufSort.trLog(n14 - n15) : -1;
                n13 = n15 - 1;
                for (n12 = n4; n12 < n15; ++n12) {
                    nArray[n + nArray[n12]] = n13;
                }
                if (n14 < n5) {
                    n13 = n14 - 1;
                    for (n12 = n15; n12 < n14; ++n12) {
                        nArray[n + nArray[n12]] = n13;
                    }
                }
                if (n15 - n4 <= n5 - n14) {
                    if (n5 - n14 <= n14 - n15) {
                        if (1 < n15 - n4) {
                            stackEntryArray[n9++] = new StackEntry(n2 + 1, n15, n14, n11);
                            stackEntryArray[n9++] = new StackEntry(n2, n14, n5, n10);
                            n5 = n15;
                            continue;
                        }
                        if (1 < n5 - n14) {
                            stackEntryArray[n9++] = new StackEntry(n2 + 1, n15, n14, n11);
                            n4 = n14;
                            continue;
                        }
                        if (1 < n14 - n15) {
                            ++n2;
                            n4 = n15;
                            n5 = n14;
                            n10 = n11;
                            continue;
                        }
                        if (n9 == 0) {
                            return;
                        }
                        object = stackEntryArray[--n9];
                        n2 = ((StackEntry)object).a;
                        n4 = ((StackEntry)object).b;
                        n5 = ((StackEntry)object).c;
                        n10 = ((StackEntry)object).d;
                        continue;
                    }
                    if (n15 - n4 <= n14 - n15) {
                        if (1 < n15 - n4) {
                            stackEntryArray[n9++] = new StackEntry(n2, n14, n5, n10);
                            stackEntryArray[n9++] = new StackEntry(n2 + 1, n15, n14, n11);
                            n5 = n15;
                            continue;
                        }
                        if (1 < n14 - n15) {
                            stackEntryArray[n9++] = new StackEntry(n2, n14, n5, n10);
                            ++n2;
                            n4 = n15;
                            n5 = n14;
                            n10 = n11;
                            continue;
                        }
                        n4 = n14;
                        continue;
                    }
                    if (1 < n14 - n15) {
                        stackEntryArray[n9++] = new StackEntry(n2, n14, n5, n10);
                        stackEntryArray[n9++] = new StackEntry(n2, n4, n15, n10);
                        ++n2;
                        n4 = n15;
                        n5 = n14;
                        n10 = n11;
                        continue;
                    }
                    stackEntryArray[n9++] = new StackEntry(n2, n14, n5, n10);
                    n5 = n15;
                    continue;
                }
                if (n15 - n4 <= n14 - n15) {
                    if (1 < n5 - n14) {
                        stackEntryArray[n9++] = new StackEntry(n2 + 1, n15, n14, n11);
                        stackEntryArray[n9++] = new StackEntry(n2, n4, n15, n10);
                        n4 = n14;
                        continue;
                    }
                    if (1 < n15 - n4) {
                        stackEntryArray[n9++] = new StackEntry(n2 + 1, n15, n14, n11);
                        n5 = n15;
                        continue;
                    }
                    if (1 < n14 - n15) {
                        ++n2;
                        n4 = n15;
                        n5 = n14;
                        n10 = n11;
                        continue;
                    }
                    stackEntryArray[n9++] = new StackEntry(n2, n4, n5, n10);
                    continue;
                }
                if (n5 - n14 <= n14 - n15) {
                    if (1 < n5 - n14) {
                        stackEntryArray[n9++] = new StackEntry(n2, n4, n15, n10);
                        stackEntryArray[n9++] = new StackEntry(n2 + 1, n15, n14, n11);
                        n4 = n14;
                        continue;
                    }
                    if (1 < n14 - n15) {
                        stackEntryArray[n9++] = new StackEntry(n2, n4, n15, n10);
                        ++n2;
                        n4 = n15;
                        n5 = n14;
                        n10 = n11;
                        continue;
                    }
                    n5 = n15;
                    continue;
                }
                if (1 < n14 - n15) {
                    stackEntryArray[n9++] = new StackEntry(n2, n4, n15, n10);
                    stackEntryArray[n9++] = new StackEntry(n2, n14, n5, n10);
                    ++n2;
                    n4 = n15;
                    n5 = n14;
                    n10 = n11;
                    continue;
                }
                stackEntryArray[n9++] = new StackEntry(n2, n4, n15, n10);
                n4 = n14;
                continue;
            }
            if (!tRBudget.update(n6, n5 - n4)) break;
            ++n10;
            ++n2;
        }
        for (n7 = 0; n7 < n9; ++n7) {
            if (stackEntryArray[n7].d != -3) continue;
            this.lsUpdateGroup(n, stackEntryArray[n7].b, stackEntryArray[n7].c);
        }
    }

    private void trSort(int n, int n2, int n3) {
        int[] nArray = this.SA;
        int n4 = 0;
        if (-n2 < nArray[0]) {
            TRBudget tRBudget = new TRBudget(n2, Bzip2DivSufSort.trLog(n2) * 2 / 3 + 1);
            do {
                int n5;
                if ((n5 = nArray[n4]) < 0) {
                    n4 -= n5;
                    continue;
                }
                int n6 = nArray[n + n5] + 1;
                if (1 < n6 - n4) {
                    this.trIntroSort(n, n + n3, n + n2, n4, n6, tRBudget, n2);
                    if (tRBudget.chance == 0) {
                        if (0 < n4) {
                            nArray[0] = -n4;
                        }
                        this.lsSort(n, n2, n3);
                        break;
                    }
                }
                n4 = n6;
            } while (n4 < n2);
        }
    }

    private static int BUCKET_B(int n, int n2) {
        return n2 << 8 | n;
    }

    private static int BUCKET_BSTAR(int n, int n2) {
        return n << 8 | n2;
    }

    private int sortTypeBstar(int[] nArray, int[] nArray2) {
        int n;
        int n2;
        int n3;
        int n4;
        int n5;
        byte[] byArray = this.T;
        int[] nArray3 = this.SA;
        int n6 = this.n;
        int[] nArray4 = new int[256];
        boolean bl = true;
        for (n5 = 1; n5 < n6; ++n5) {
            if (byArray[n5 - 1] == byArray[n5]) continue;
            if ((byArray[n5 - 1] & 0xFF) <= (byArray[n5] & 0xFF)) break;
            bl = false;
            break;
        }
        n5 = n6 - 1;
        int n7 = n6;
        int n8 = byArray[n5] & 0xFF;
        int n9 = byArray[0] & 0xFF;
        if (n8 < n9 || byArray[n5] == byArray[0] && bl) {
            if (!bl) {
                int n10 = Bzip2DivSufSort.BUCKET_BSTAR(n8, n9);
                nArray2[n10] = nArray2[n10] + 1;
                nArray3[--n7] = n5;
            } else {
                int n11 = Bzip2DivSufSort.BUCKET_B(n8, n9);
                nArray2[n11] = nArray2[n11] + 1;
            }
            --n5;
            while (0 <= n5 && (n8 = byArray[n5] & 0xFF) <= (n4 = byArray[n5 + 1] & 0xFF)) {
                int n12 = Bzip2DivSufSort.BUCKET_B(n8, n4);
                nArray2[n12] = nArray2[n12] + 1;
                --n5;
            }
        }
        while (0 <= n5) {
            do {
                int n13 = byArray[n5] & 0xFF;
                nArray[n13] = nArray[n13] + 1;
            } while (0 <= --n5 && (byArray[n5] & 0xFF) >= (byArray[n5 + 1] & 0xFF));
            if (0 > n5) continue;
            int n14 = Bzip2DivSufSort.BUCKET_BSTAR(byArray[n5] & 0xFF, byArray[n5 + 1] & 0xFF);
            nArray2[n14] = nArray2[n14] + 1;
            nArray3[--n7] = n5--;
            while (0 <= n5 && (n8 = byArray[n5] & 0xFF) <= (n4 = byArray[n5 + 1] & 0xFF)) {
                int n15 = Bzip2DivSufSort.BUCKET_B(n8, n4);
                nArray2[n15] = nArray2[n15] + 1;
                --n5;
            }
        }
        if ((n7 = n6 - n7) == 0) {
            for (n5 = 0; n5 < n6; ++n5) {
                nArray3[n5] = n5;
            }
            return 1;
        }
        n5 = -1;
        int n16 = 0;
        for (n3 = 0; n3 < 256; ++n3) {
            n2 = n5 + nArray[n3];
            nArray[n3] = n5 + n16;
            n5 = n2 + nArray2[Bzip2DivSufSort.BUCKET_B(n3, n3)];
            for (n = n3 + 1; n < 256; ++n) {
                nArray2[n3 << 8 | n] = n16 += nArray2[Bzip2DivSufSort.BUCKET_BSTAR(n3, n)];
                n5 += nArray2[Bzip2DivSufSort.BUCKET_B(n3, n)];
            }
        }
        int n17 = n6 - n7;
        int n18 = n7;
        n5 = n7 - 2;
        while (0 <= n5) {
            n2 = nArray3[n17 + n5];
            n3 = byArray[n2] & 0xFF;
            n = byArray[n2 + 1] & 0xFF;
            int n19 = Bzip2DivSufSort.BUCKET_BSTAR(n3, n);
            int n20 = nArray2[n19] - 1;
            nArray2[n19] = n20;
            nArray3[n20] = n5--;
        }
        n2 = nArray3[n17 + n7 - 1];
        n3 = byArray[n2] & 0xFF;
        n = byArray[n2 + 1] & 0xFF;
        int n21 = Bzip2DivSufSort.BUCKET_BSTAR(n3, n);
        int n22 = nArray2[n21] - 1;
        nArray2[n21] = n22;
        nArray3[n22] = n7 - 1;
        int[] nArray5 = nArray3;
        int n23 = n7;
        int n24 = n6 - 2 * n7;
        if (n24 <= 256) {
            nArray5 = nArray4;
            n23 = 0;
            n24 = 256;
        }
        n3 = 255;
        n16 = n7;
        while (0 < n16) {
            for (n = 255; n3 < n; --n) {
                n5 = nArray2[Bzip2DivSufSort.BUCKET_BSTAR(n3, n)];
                if (1 < n16 - n5) {
                    this.subStringSort(n17, n5, n16, nArray5, n23, n24, 2, nArray3[n5] == n7 - 1, n6);
                }
                n16 = n5;
            }
            --n3;
        }
        for (n5 = n7 - 1; 0 <= n5; --n5) {
            if (0 <= nArray3[n5]) {
                n16 = n5;
                do {
                    nArray3[n18 + nArray3[n5]] = n5;
                } while (0 <= --n5 && 0 <= nArray3[n5]);
                nArray3[n5 + 1] = n5 - n16;
                if (n5 <= 0) break;
            }
            n16 = n5;
            do {
                nArray3[n5] = ~nArray3[n5];
                nArray3[n18 + nArray3[n5]] = n16;
            } while (nArray3[--n5] < 0);
            nArray3[n18 + nArray3[n5]] = n16;
        }
        this.trSort(n18, n7, 1);
        n5 = n6 - 1;
        n16 = n7;
        if ((byArray[n5] & 0xFF) < (byArray[0] & 0xFF) || byArray[n5] == byArray[0] && bl) {
            if (!bl) {
                nArray3[nArray3[n18 + --n16]] = n5;
            }
            --n5;
            while (0 <= n5 && (byArray[n5] & 0xFF) <= (byArray[n5 + 1] & 0xFF)) {
                --n5;
            }
        }
        while (0 <= n5) {
            --n5;
            while (0 <= n5 && (byArray[n5] & 0xFF) >= (byArray[n5 + 1] & 0xFF)) {
                --n5;
            }
            if (0 > n5) continue;
            nArray3[nArray3[n18 + --n16]] = n5--;
            while (0 <= n5 && (byArray[n5] & 0xFF) <= (byArray[n5 + 1] & 0xFF)) {
                --n5;
            }
        }
        n5 = n6 - 1;
        int n25 = n7 - 1;
        for (n3 = 255; 0 <= n3; --n3) {
            for (n = 255; n3 < n; --n) {
                n2 = n5 - nArray2[Bzip2DivSufSort.BUCKET_B(n3, n)];
                nArray2[Bzip2DivSufSort.BUCKET_B((int)n3, (int)n)] = n5 + 1;
                n5 = n2;
                n16 = nArray2[Bzip2DivSufSort.BUCKET_BSTAR(n3, n)];
                while (n16 <= n25) {
                    nArray3[n5] = nArray3[n25];
                    --n5;
                    --n25;
                }
            }
            n2 = n5 - nArray2[Bzip2DivSufSort.BUCKET_B(n3, n3)];
            nArray2[Bzip2DivSufSort.BUCKET_B((int)n3, (int)n3)] = n5 + 1;
            if (n3 < 255) {
                nArray2[Bzip2DivSufSort.BUCKET_BSTAR((int)n3, (int)(n3 + 1))] = n2 + 1;
            }
            n5 = nArray[n3];
        }
        return n7;
    }

    private int constructBWT(int[] nArray, int[] nArray2) {
        int n;
        int n2;
        int n3;
        int n4;
        byte[] byArray = this.T;
        int[] nArray3 = this.SA;
        int n5 = this.n;
        int n6 = 0;
        int n7 = 0;
        int n8 = -1;
        for (int i = 254; 0 <= i; --i) {
            n4 = nArray2[Bzip2DivSufSort.BUCKET_BSTAR(i, i + 1)];
            n6 = 0;
            n7 = -1;
            for (int j = nArray[i + 1]; n4 <= j; --j) {
                n2 = n3 = nArray3[j];
                if (0 <= n3) {
                    if (--n3 < 0) {
                        n3 = n5 - 1;
                    }
                    if ((n = byArray[n3] & 0xFF) > i) continue;
                    nArray3[j] = ~n2;
                    if (0 < n3 && (byArray[n3 - 1] & 0xFF) > n) {
                        n3 ^= 0xFFFFFFFF;
                    }
                    if (n7 == n) {
                        nArray3[--n6] = n3;
                        continue;
                    }
                    if (0 <= n7) {
                        nArray2[Bzip2DivSufSort.BUCKET_B((int)n7, (int)i)] = n6;
                    }
                    n7 = n;
                    n6 = nArray2[Bzip2DivSufSort.BUCKET_B(n7, i)] - 1;
                    nArray3[n6] = n3;
                    continue;
                }
                nArray3[j] = ~n3;
            }
        }
        for (n4 = 0; n4 < n5; ++n4) {
            n2 = n3 = nArray3[n4];
            if (0 <= n3) {
                if (--n3 < 0) {
                    n3 = n5 - 1;
                }
                if ((n = byArray[n3] & 0xFF) >= (byArray[n3 + 1] & 0xFF)) {
                    if (0 < n3 && (byArray[n3 - 1] & 0xFF) < n) {
                        n3 ^= 0xFFFFFFFF;
                    }
                    if (n == n7) {
                        nArray3[++n6] = n3;
                    } else {
                        if (n7 != -1) {
                            nArray[n7] = n6;
                        }
                        n7 = n;
                        n6 = nArray[n7] + 1;
                        nArray3[n6] = n3;
                    }
                }
            } else {
                n2 ^= 0xFFFFFFFF;
            }
            if (n2 == 0) {
                nArray3[n4] = byArray[n5 - 1];
                n8 = n4;
                continue;
            }
            nArray3[n4] = byArray[n2 - 1];
        }
        return n8;
    }

    public int bwt() {
        int[] nArray = this.SA;
        byte[] byArray = this.T;
        int n = this.n;
        int[] nArray2 = new int[256];
        int[] nArray3 = new int[65536];
        if (n == 0) {
            return 1;
        }
        if (n == 1) {
            nArray[0] = byArray[0];
            return 1;
        }
        int n2 = this.sortTypeBstar(nArray2, nArray3);
        if (0 < n2) {
            return this.constructBWT(nArray2, nArray3);
        }
        return 1;
    }

    private static class TRBudget {
        int budget;
        int chance;

        TRBudget(int n, int n2) {
            this.budget = n;
            this.chance = n2;
        }

        boolean update(int n, int n2) {
            this.budget -= n2;
            if (this.budget <= 0) {
                if (--this.chance == 0) {
                    return true;
                }
                this.budget += n;
            }
            return false;
        }
    }

    private static class PartitionResult {
        final int first;
        final int last;

        PartitionResult(int n, int n2) {
            this.first = n;
            this.last = n2;
        }
    }

    private static class StackEntry {
        final int a;
        final int b;
        final int c;
        final int d;

        StackEntry(int n, int n2, int n3, int n4) {
            this.a = n;
            this.b = n2;
            this.c = n3;
            this.d = n4;
        }
    }
}

