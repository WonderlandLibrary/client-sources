/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.Trie2;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public final class Trie2_16
extends Trie2 {
    Trie2_16() {
    }

    public static Trie2_16 createFromSerialized(ByteBuffer byteBuffer) throws IOException {
        return (Trie2_16)Trie2.createFromSerialized(byteBuffer);
    }

    @Override
    public final int get(int n) {
        if (n >= 0) {
            if (n < 55296 || n > 56319 && n <= 65535) {
                int n2 = this.index[n >> 5];
                n2 = (n2 << 2) + (n & 0x1F);
                char c = this.index[n2];
                return c;
            }
            if (n <= 65535) {
                int n3 = this.index[2048 + (n - 55296 >> 5)];
                n3 = (n3 << 2) + (n & 0x1F);
                char c = this.index[n3];
                return c;
            }
            if (n < this.highStart) {
                int n4 = 2080 + (n >> 11);
                n4 = this.index[n4];
                n4 += n >> 5 & 0x3F;
                n4 = this.index[n4];
                n4 = (n4 << 2) + (n & 0x1F);
                char c = this.index[n4];
                return c;
            }
            if (n <= 0x10FFFF) {
                char c = this.index[this.highValueIndex];
                return c;
            }
        }
        return this.errorValue;
    }

    @Override
    public int getFromU16SingleLead(char c) {
        int n = this.index[c >> 5];
        n = (n << 2) + (c & 0x1F);
        char c2 = this.index[n];
        return c2;
    }

    public int serialize(OutputStream outputStream) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        int n = 0;
        n += this.serializeHeader(dataOutputStream);
        for (int i = 0; i < this.dataLength; ++i) {
            dataOutputStream.writeChar(this.index[this.data16 + i]);
        }
        return n += this.dataLength * 2;
    }

    public int getSerializedLength() {
        return 16 + (this.header.indexLength + this.dataLength) * 2;
    }

    @Override
    int rangeEnd(int n, int n2, int n3) {
        int n4 = n;
        int n5 = 0;
        int n6 = 0;
        block0: while (n4 < n2) {
            int n7;
            if (n4 < 55296 || n4 > 56319 && n4 <= 65535) {
                n6 = 0;
                n5 = this.index[n4 >> 5] << 2;
            } else if (n4 < 65535) {
                n6 = 2048;
                n5 = this.index[n6 + (n4 - 55296 >> 5)] << 2;
            } else if (n4 < this.highStart) {
                n7 = 2080 + (n4 >> 11);
                n6 = this.index[n7];
                n5 = this.index[n6 + (n4 >> 5 & 0x3F)] << 2;
            } else {
                if (n3 != this.index[this.highValueIndex]) break;
                n4 = n2;
                break;
            }
            if (n6 == this.index2NullOffset) {
                if (n3 != this.initialValue) break;
                n4 += 2048;
                continue;
            }
            if (n5 == this.dataNullOffset) {
                if (n3 != this.initialValue) break;
                n4 += 32;
                continue;
            }
            n7 = n5 + (n4 & 0x1F);
            int n8 = n5 + 32;
            for (int i = n7; i < n8; ++i) {
                if (this.index[i] == n3) continue;
                n4 += i - n7;
                break block0;
            }
            n4 += n8 - n7;
        }
        if (n4 > n2) {
            n4 = n2;
        }
        return n4 - 1;
    }
}

