/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.compression;

final class Bzip2HuffmanAllocator {
    private static int first(int[] nArray, int n, int n2) {
        int n3 = nArray.length;
        int n4 = n;
        int n5 = nArray.length - 2;
        while (n >= n2 && nArray[n] % n3 > n4) {
            n5 = n;
            n -= n4 - n + 1;
        }
        n = Math.max(n2 - 1, n);
        while (n5 > n + 1) {
            int n6 = n + n5 >>> 1;
            if (nArray[n6] % n3 > n4) {
                n5 = n6;
                continue;
            }
            n = n6;
        }
        return n5;
    }

    private static void setExtendedParentPointers(int[] nArray) {
        int n = nArray.length;
        nArray[0] = nArray[0] + nArray[1];
        int n2 = 0;
        int n3 = 2;
        for (int i = 1; i < n - 1; ++i) {
            int n4;
            if (n3 >= n || nArray[n2] < nArray[n3]) {
                n4 = nArray[n2];
                nArray[n2++] = i;
            } else {
                n4 = nArray[n3++];
            }
            if (n3 >= n || n2 < i && nArray[n2] < nArray[n3]) {
                n4 += nArray[n2];
                nArray[n2++] = i + n;
            } else {
                n4 += nArray[n3++];
            }
            nArray[i] = n4;
        }
    }

    private static int findNodesToRelocate(int[] nArray, int n) {
        int n2 = nArray.length - 2;
        for (int i = 1; i < n - 1 && n2 > 1; ++i) {
            n2 = Bzip2HuffmanAllocator.first(nArray, n2 - 1, 0);
        }
        return n2;
    }

    private static void allocateNodeLengths(int[] nArray) {
        int n = nArray.length - 2;
        int n2 = nArray.length - 1;
        int n3 = 1;
        int n4 = 2;
        while (n4 > 0) {
            int n5 = n;
            n = Bzip2HuffmanAllocator.first(nArray, n5 - 1, 0);
            for (int i = n4 - (n5 - n); i > 0; --i) {
                nArray[n2--] = n3;
            }
            n4 = n5 - n << 1;
            ++n3;
        }
    }

    private static void allocateNodeLengthsWithRelocation(int[] nArray, int n, int n2) {
        int n3 = nArray.length - 2;
        int n4 = nArray.length - 1;
        int n5 = n2 == 1 ? 2 : 1;
        int n6 = n2 == 1 ? n - 2 : n;
        int n7 = n5 << 1;
        while (n7 > 0) {
            int n8 = n3;
            n3 = n3 <= n ? n3 : Bzip2HuffmanAllocator.first(nArray, n8 - 1, n);
            int n9 = 0;
            if (n5 >= n2) {
                n9 = Math.min(n6, 1 << n5 - n2);
            } else if (n5 == n2 - 1) {
                n9 = 1;
                if (nArray[n3] == n8) {
                    ++n3;
                }
            }
            for (int i = n7 - (n8 - n3 + n9); i > 0; --i) {
                nArray[n4--] = n5;
            }
            n6 -= n9;
            n7 = n8 - n3 + n9 << 1;
            ++n5;
        }
    }

    static void allocateHuffmanCodeLengths(int[] nArray, int n) {
        switch (nArray.length) {
            case 2: {
                nArray[1] = 1;
            }
            case 1: {
                nArray[0] = 1;
                return;
            }
        }
        Bzip2HuffmanAllocator.setExtendedParentPointers(nArray);
        int n2 = Bzip2HuffmanAllocator.findNodesToRelocate(nArray, n);
        if (nArray[0] % nArray.length >= n2) {
            Bzip2HuffmanAllocator.allocateNodeLengths(nArray);
        } else {
            int n3 = n - (32 - Integer.numberOfLeadingZeros(n2 - 1));
            Bzip2HuffmanAllocator.allocateNodeLengthsWithRelocation(nArray, n2, n3);
        }
    }

    private Bzip2HuffmanAllocator() {
    }
}

