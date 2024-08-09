/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.ICUBinary;
import com.ibm.icu.impl.ICUDebug;
import java.io.IOException;
import java.nio.ByteBuffer;

public final class StringPrepDataReader
implements ICUBinary.Authenticate {
    private static final boolean debug = ICUDebug.enabled("NormalizerDataReader");
    private ByteBuffer byteBuffer;
    private int unicodeVersion;
    private static final int DATA_FORMAT_ID = 1397772880;
    private static final byte[] DATA_FORMAT_VERSION = new byte[]{3, 2, 5, 2};

    public StringPrepDataReader(ByteBuffer byteBuffer) throws IOException {
        if (debug) {
            System.out.println("Bytes in buffer " + byteBuffer.remaining());
        }
        this.byteBuffer = byteBuffer;
        this.unicodeVersion = ICUBinary.readHeader(this.byteBuffer, 1397772880, this);
        if (debug) {
            System.out.println("Bytes left in byteBuffer " + this.byteBuffer.remaining());
        }
    }

    public char[] read(int n) throws IOException {
        return ICUBinary.getChars(this.byteBuffer, n, 0);
    }

    @Override
    public boolean isDataVersionAcceptable(byte[] byArray) {
        return byArray[0] == DATA_FORMAT_VERSION[0] && byArray[2] == DATA_FORMAT_VERSION[2] && byArray[3] == DATA_FORMAT_VERSION[3];
    }

    public int[] readIndexes(int n) throws IOException {
        int[] nArray = new int[n];
        for (int i = 0; i < n; ++i) {
            nArray[i] = this.byteBuffer.getInt();
        }
        return nArray;
    }

    public byte[] getUnicodeVersion() {
        return ICUBinary.getVersionByteArrayFromCompactInt(this.unicodeVersion);
    }
}

