/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.tukaani.xz.DeltaOptions
 *  org.tukaani.xz.FinishableOutputStream
 *  org.tukaani.xz.FinishableWrapperOutputStream
 *  org.tukaani.xz.UnsupportedOptionsException
 */
package org.apache.commons.compress.archivers.sevenz;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.compress.archivers.sevenz.Coder;
import org.apache.commons.compress.archivers.sevenz.CoderBase;
import org.tukaani.xz.DeltaOptions;
import org.tukaani.xz.FinishableOutputStream;
import org.tukaani.xz.FinishableWrapperOutputStream;
import org.tukaani.xz.UnsupportedOptionsException;

class DeltaDecoder
extends CoderBase {
    DeltaDecoder() {
        super(Number.class);
    }

    InputStream decode(InputStream inputStream, Coder coder, byte[] byArray) throws IOException {
        return new DeltaOptions(this.getOptionsFromCoder(coder)).getInputStream(inputStream);
    }

    OutputStream encode(OutputStream outputStream, Object object) throws IOException {
        int n = DeltaDecoder.numberOptionOrDefault(object, 1);
        try {
            return new DeltaOptions(n).getOutputStream((FinishableOutputStream)new FinishableWrapperOutputStream(outputStream));
        } catch (UnsupportedOptionsException unsupportedOptionsException) {
            throw new IOException(unsupportedOptionsException.getMessage());
        }
    }

    byte[] getOptionsAsProperties(Object object) {
        return new byte[]{(byte)(DeltaDecoder.numberOptionOrDefault(object, 1) - 1)};
    }

    Object getOptionsFromCoder(Coder coder, InputStream inputStream) {
        return this.getOptionsFromCoder(coder);
    }

    private int getOptionsFromCoder(Coder coder) {
        if (coder.properties == null || coder.properties.length == 0) {
            return 0;
        }
        return (0xFF & coder.properties[0]) + 1;
    }
}

