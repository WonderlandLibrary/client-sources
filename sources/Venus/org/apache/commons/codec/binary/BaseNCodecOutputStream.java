/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.codec.binary;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.commons.codec.binary.BaseNCodec;

public class BaseNCodecOutputStream
extends FilterOutputStream {
    private final boolean doEncode;
    private final BaseNCodec baseNCodec;
    private final byte[] singleByte = new byte[1];
    private final BaseNCodec.Context context = new BaseNCodec.Context();

    public BaseNCodecOutputStream(OutputStream outputStream, BaseNCodec baseNCodec, boolean bl) {
        super(outputStream);
        this.baseNCodec = baseNCodec;
        this.doEncode = bl;
    }

    @Override
    public void write(int n) throws IOException {
        this.singleByte[0] = (byte)n;
        this.write(this.singleByte, 0, 1);
    }

    @Override
    public void write(byte[] byArray, int n, int n2) throws IOException {
        if (byArray == null) {
            throw new NullPointerException();
        }
        if (n < 0 || n2 < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (n > byArray.length || n + n2 > byArray.length) {
            throw new IndexOutOfBoundsException();
        }
        if (n2 > 0) {
            if (this.doEncode) {
                this.baseNCodec.encode(byArray, n, n2, this.context);
            } else {
                this.baseNCodec.decode(byArray, n, n2, this.context);
            }
            this.flush(false);
        }
    }

    private void flush(boolean bl) throws IOException {
        byte[] byArray;
        int n;
        int n2 = this.baseNCodec.available(this.context);
        if (n2 > 0 && (n = this.baseNCodec.readResults(byArray = new byte[n2], 0, n2, this.context)) > 0) {
            this.out.write(byArray, 0, n);
        }
        if (bl) {
            this.out.flush();
        }
    }

    @Override
    public void flush() throws IOException {
        this.flush(true);
    }

    @Override
    public void close() throws IOException {
        this.eof();
        this.flush();
        this.out.close();
    }

    public void eof() throws IOException {
        if (this.doEncode) {
            this.baseNCodec.encode(this.singleByte, 0, -1, this.context);
        } else {
            this.baseNCodec.decode(this.singleByte, 0, -1, this.context);
        }
    }
}

