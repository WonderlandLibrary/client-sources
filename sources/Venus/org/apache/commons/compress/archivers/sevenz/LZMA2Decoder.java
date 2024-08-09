/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.tukaani.xz.FinishableOutputStream
 *  org.tukaani.xz.FinishableWrapperOutputStream
 *  org.tukaani.xz.LZMA2InputStream
 *  org.tukaani.xz.LZMA2Options
 */
package org.apache.commons.compress.archivers.sevenz;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.compress.archivers.sevenz.Coder;
import org.apache.commons.compress.archivers.sevenz.CoderBase;
import org.tukaani.xz.FinishableOutputStream;
import org.tukaani.xz.FinishableWrapperOutputStream;
import org.tukaani.xz.LZMA2InputStream;
import org.tukaani.xz.LZMA2Options;

class LZMA2Decoder
extends CoderBase {
    LZMA2Decoder() {
        super(LZMA2Options.class, Number.class);
    }

    InputStream decode(InputStream inputStream, Coder coder, byte[] byArray) throws IOException {
        try {
            int n = this.getDictionarySize(coder);
            return new LZMA2InputStream(inputStream, n);
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new IOException(illegalArgumentException.getMessage());
        }
    }

    OutputStream encode(OutputStream outputStream, Object object) throws IOException {
        LZMA2Options lZMA2Options = this.getOptions(object);
        FinishableWrapperOutputStream finishableWrapperOutputStream = new FinishableWrapperOutputStream(outputStream);
        return lZMA2Options.getOutputStream((FinishableOutputStream)finishableWrapperOutputStream);
    }

    byte[] getOptionsAsProperties(Object object) {
        int n = this.getDictSize(object);
        int n2 = Integer.numberOfLeadingZeros(n);
        int n3 = (n >>> 30 - n2) - 2;
        return new byte[]{(byte)((19 - n2) * 2 + n3)};
    }

    Object getOptionsFromCoder(Coder coder, InputStream inputStream) {
        return this.getDictionarySize(coder);
    }

    private int getDictSize(Object object) {
        if (object instanceof LZMA2Options) {
            return ((LZMA2Options)object).getDictSize();
        }
        return this.numberOptionOrDefault(object);
    }

    private int getDictionarySize(Coder coder) throws IllegalArgumentException {
        int n = 0xFF & coder.properties[0];
        if ((n & 0xFFFFFFC0) != 0) {
            throw new IllegalArgumentException("Unsupported LZMA2 property bits");
        }
        if (n > 40) {
            throw new IllegalArgumentException("Dictionary larger than 4GiB maximum size");
        }
        if (n == 40) {
            return 1;
        }
        return (2 | n & 1) << n / 2 + 11;
    }

    private LZMA2Options getOptions(Object object) throws IOException {
        if (object instanceof LZMA2Options) {
            return (LZMA2Options)object;
        }
        LZMA2Options lZMA2Options = new LZMA2Options();
        lZMA2Options.setDictSize(this.numberOptionOrDefault(object));
        return lZMA2Options;
    }

    private int numberOptionOrDefault(Object object) {
        return LZMA2Decoder.numberOptionOrDefault(object, 0x800000);
    }
}

