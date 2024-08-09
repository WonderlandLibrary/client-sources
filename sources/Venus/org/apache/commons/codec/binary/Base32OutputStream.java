/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.codec.binary;

import java.io.OutputStream;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.BaseNCodecOutputStream;

public class Base32OutputStream
extends BaseNCodecOutputStream {
    public Base32OutputStream(OutputStream outputStream) {
        this(outputStream, true);
    }

    public Base32OutputStream(OutputStream outputStream, boolean bl) {
        super(outputStream, new Base32(false), bl);
    }

    public Base32OutputStream(OutputStream outputStream, boolean bl, int n, byte[] byArray) {
        super(outputStream, new Base32(n, byArray), bl);
    }
}

