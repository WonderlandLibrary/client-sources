/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.zip;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import org.apache.commons.compress.archivers.zip.BitStream;

class BinaryTree {
    private static final int UNDEFINED = -1;
    private static final int NODE = -2;
    private final int[] tree;

    public BinaryTree(int n) {
        this.tree = new int[(1 << n + 1) - 1];
        Arrays.fill(this.tree, -1);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void addLeaf(int n, int n2, int n3, int n4) {
        if (n3 == 0) {
            if (this.tree[n] != -1) throw new IllegalArgumentException("Tree value at index " + n + " has already been assigned (" + this.tree[n] + ")");
            this.tree[n] = n4;
            return;
        } else {
            this.tree[n] = -2;
            int n5 = 2 * n + 1 + (n2 & 1);
            this.addLeaf(n5, n2 >>> 1, n3 - 1, n4);
        }
    }

    public int read(BitStream bitStream) throws IOException {
        int n;
        int n2;
        int n3 = 0;
        while (true) {
            if ((n2 = bitStream.nextBit()) == -1) {
                return 1;
            }
            int n4 = 2 * n3 + 1 + n2;
            n = this.tree[n4];
            if (n != -2) break;
            n3 = n4;
        }
        if (n != -1) {
            return n;
        }
        throw new IOException("The child " + n2 + " of node at index " + n3 + " is not defined");
    }

    /*
     * WARNING - void declaration
     */
    static BinaryTree decode(InputStream inputStream, int n) throws IOException {
        void var10_14;
        int n2;
        int n3;
        int n6 = inputStream.read() + 1;
        if (n6 == 0) {
            throw new IOException("Cannot read the size of the encoded tree, unexpected end of stream");
        }
        byte[] byArray = new byte[n6];
        new DataInputStream(inputStream).readFully(byArray);
        int n7 = 0;
        int[] nArray = new int[n];
        int n8 = 0;
        byte[] objectArray = byArray;
        int n4 = objectArray.length;
        for (int nArray2 = 0; nArray2 < n4; ++nArray2) {
            byte by = objectArray[nArray2];
            n3 = ((by & 0xF0) >> 4) + 1;
            n2 = (by & 0xF) + 1;
            for (int i = 0; i < n3; ++i) {
                nArray[n8++] = n2;
            }
            n7 = Math.max(n7, n2);
        }
        int[] nArray2 = new int[nArray.length];
        for (n4 = 0; n4 < nArray2.length; ++n4) {
            nArray2[n4] = n4;
        }
        n4 = 0;
        int[] nArray3 = new int[nArray.length];
        boolean bl = false;
        while (var10_14 < nArray.length) {
            for (n3 = 0; n3 < nArray.length; ++n3) {
                if (nArray[n3] != var10_14) continue;
                nArray3[n4] = var10_14;
                nArray2[n4] = n3;
                ++n4;
            }
            ++var10_14;
        }
        boolean bl2 = false;
        n3 = 0;
        n2 = 0;
        int[] nArray4 = new int[n];
        for (int i = n - 1; i >= 0; --i) {
            void var10_16;
            var10_16 += n3;
            if (nArray3[i] != n2) {
                n2 = nArray3[i];
                n3 = 1 << 16 - n2;
            }
            nArray4[nArray2[i]] = var10_16;
        }
        BinaryTree binaryTree = new BinaryTree(n7);
        for (int i = 0; i < nArray4.length; ++i) {
            int n5 = nArray[i];
            if (n5 <= 0) continue;
            binaryTree.addLeaf(0, Integer.reverse(nArray4[i] << 16), n5, i);
        }
        return binaryTree;
    }
}

