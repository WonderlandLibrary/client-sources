/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.codec.binary;

import java.io.InputStream;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.BaseNCodecInputStream;

public class Base64InputStream
extends BaseNCodecInputStream {
    public Base64InputStream(InputStream inputStream) {
        this(inputStream, false);
    }

    public Base64InputStream(InputStream inputStream, boolean bl) {
        super(inputStream, new Base64(false), bl);
    }

    public Base64InputStream(InputStream inputStream, boolean bl, int n, byte[] byArray) {
        super(inputStream, new Base64(n, byArray), bl);
    }
}

