/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.codec.binary;

import java.io.InputStream;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.BaseNCodecInputStream;

public class Base32InputStream
extends BaseNCodecInputStream {
    public Base32InputStream(InputStream inputStream) {
        this(inputStream, false);
    }

    public Base32InputStream(InputStream inputStream, boolean bl) {
        super(inputStream, new Base32(false), bl);
    }

    public Base32InputStream(InputStream inputStream, boolean bl, int n, byte[] byArray) {
        super(inputStream, new Base32(n, byArray), bl);
    }
}

