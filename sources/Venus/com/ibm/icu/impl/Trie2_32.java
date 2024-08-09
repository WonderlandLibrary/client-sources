/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.Trie2;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class Trie2_32
extends Trie2 {
    Trie2_32() {
    }

    public static Trie2_32 createFromSerialized(ByteBuffer byteBuffer) throws IOException {
        return (Trie2_32)Trie2.createFromSerialized(byteBuffer);
    }

    @Override
    public final int get(int n) {
        if (n >= 0) {
            if (n < 55296 || n > 56319 && n <= 65535) {
                int n2 = this.index[n >> 5];
                n2 = (n2 << 2) + (n & 0x1F);
                int n3 = this.data32[n2];
                return n3;
            }
            if (n <= 65535) {
                int n4 = this.index[2048 + (n - 55296 >> 5)];
                n4 = (n4 << 2) + (n & 0x1F);
                int n5 = this.data32[n4];
                return n5;
            }
            if (n < this.highStart) {
                int n6 = 2080 + (n >> 11);
                n6 = this.index[n6];
                n6 += n >> 5 & 0x3F;
                n6 = this.index[n6];
                n6 = (n6 << 2) + (n & 0x1F);
                int n7 = this.data32[n6];
                return n7;
            }
            if (n <= 0x10FFFF) {
                int n8 = this.data32[this.highValueIndex];
                return n8;
            }
        }
        return this.errorValue;
    }

    @Override
    public int getFromU16SingleLead(char c) {
        int n = this.index[c >> 5];
        n = (n << 2) + (c & 0x1F);
        int n2 = this.data32[n];
        return n2;
    }

    public int serialize(OutputStream outputStream) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        int n = 0;
        n += this.serializeHeader(dataOutputStream);
        for (int i = 0; i < this.dataLength; ++i) {
            dataOutputStream.writeInt(this.data32[i]);
        }
        return n += this.dataLength * 4;
    }

    public int getSerializedLength() {
        return 16 + this.header.indexLength * 2 + this.dataLength * 4;
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
                if (n3 != this.data32[this.highValueIndex]) break;
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
                if (this.data32[i] == n3) continue;
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

