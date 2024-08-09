/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.zip;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.compress.archivers.zip.BinaryTree;
import org.apache.commons.compress.archivers.zip.BitStream;
import org.apache.commons.compress.archivers.zip.CircularBuffer;

class ExplodingInputStream
extends InputStream {
    private final InputStream in;
    private BitStream bits;
    private final int dictionarySize;
    private final int numberOfTrees;
    private final int minimumMatchLength;
    private BinaryTree literalTree;
    private BinaryTree lengthTree;
    private BinaryTree distanceTree;
    private final CircularBuffer buffer = new CircularBuffer(32768);

    public ExplodingInputStream(int n, int n2, InputStream inputStream) {
        if (n != 4096 && n != 8192) {
            throw new IllegalArgumentException("The dictionary size must be 4096 or 8192");
        }
        if (n2 != 2 && n2 != 3) {
            throw new IllegalArgumentException("The number of trees must be 2 or 3");
        }
        this.dictionarySize = n;
        this.numberOfTrees = n2;
        this.minimumMatchLength = n2;
        this.in = inputStream;
    }

    private void init() throws IOException {
        if (this.bits == null) {
            if (this.numberOfTrees == 3) {
                this.literalTree = BinaryTree.decode(this.in, 256);
            }
            this.lengthTree = BinaryTree.decode(this.in, 64);
            this.distanceTree = BinaryTree.decode(this.in, 64);
            this.bits = new BitStream(this.in);
        }
    }

    public int read() throws IOException {
        if (!this.buffer.available()) {
            this.fillBuffer();
        }
        return this.buffer.get();
    }

    private void fillBuffer() throws IOException {
        this.init();
        int n = this.bits.nextBit();
        if (n == 1) {
            int n2 = this.literalTree != null ? this.literalTree.read(this.bits) : this.bits.nextBits(8);
            if (n2 == -1) {
                return;
            }
            this.buffer.put(n2);
        } else if (n == 0) {
            int n3 = this.dictionarySize == 4096 ? 6 : 7;
            int n4 = this.bits.nextBits(n3);
            int n5 = this.distanceTree.read(this.bits);
            if (n5 == -1 && n4 <= 0) {
                return;
            }
            int n6 = n5 << n3 | n4;
            int n7 = this.lengthTree.read(this.bits);
            if (n7 == 63) {
                n7 += this.bits.nextBits(8);
            }
            this.buffer.copy(n6 + 1, n7 += this.minimumMatchLength);
        }
    }
}

