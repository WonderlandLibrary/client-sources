/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.codec.binary;

import java.io.OutputStream;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.BaseNCodecOutputStream;

public class Base64OutputStream
extends BaseNCodecOutputStream {
    public Base64OutputStream(OutputStream outputStream) {
        this(outputStream, true);
    }

    public Base64OutputStream(OutputStream outputStream, boolean bl) {
        super(outputStream, new Base64(false), bl);
    }

    public Base64OutputStream(OutputStream outputStream, boolean bl, int n, byte[] byArray) {
        super(outputStream, new Base64(n, byArray), bl);
    }
}

