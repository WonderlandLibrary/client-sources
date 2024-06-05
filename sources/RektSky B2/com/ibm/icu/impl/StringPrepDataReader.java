package com.ibm.icu.impl;

import java.nio.*;
import java.io.*;

public final class StringPrepDataReader implements ICUBinary.Authenticate
{
    private static final boolean debug;
    private ByteBuffer byteBuffer;
    private int unicodeVersion;
    private static final int DATA_FORMAT_ID = 1397772880;
    private static final byte[] DATA_FORMAT_VERSION;
    
    public StringPrepDataReader(final ByteBuffer bytes) throws IOException {
        if (StringPrepDataReader.debug) {
            System.out.println("Bytes in buffer " + bytes.remaining());
        }
        this.byteBuffer = bytes;
        this.unicodeVersion = ICUBinary.readHeader(this.byteBuffer, 1397772880, this);
        if (StringPrepDataReader.debug) {
            System.out.println("Bytes left in byteBuffer " + this.byteBuffer.remaining());
        }
    }
    
    public char[] read(final int length) throws IOException {
        return ICUBinary.getChars(this.byteBuffer, length, 0);
    }
    
    @Override
    public boolean isDataVersionAcceptable(final byte[] version) {
        return version[0] == StringPrepDataReader.DATA_FORMAT_VERSION[0] && version[2] == StringPrepDataReader.DATA_FORMAT_VERSION[2] && version[3] == StringPrepDataReader.DATA_FORMAT_VERSION[3];
    }
    
    public int[] readIndexes(final int length) throws IOException {
        final int[] indexes = new int[length];
        for (int i = 0; i < length; ++i) {
            indexes[i] = this.byteBuffer.getInt();
        }
        return indexes;
    }
    
    public byte[] getUnicodeVersion() {
        return ICUBinary.getVersionByteArrayFromCompactInt(this.unicodeVersion);
    }
    
    static {
        debug = ICUDebug.enabled("NormalizerDataReader");
        DATA_FORMAT_VERSION = new byte[] { 3, 2, 5, 2 };
    }
}
