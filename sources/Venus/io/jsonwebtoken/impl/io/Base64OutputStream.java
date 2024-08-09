/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.io;

import io.jsonwebtoken.impl.io.Base64Codec;
import io.jsonwebtoken.impl.io.BaseNCodec;
import io.jsonwebtoken.impl.io.BaseNCodecOutputStream;
import io.jsonwebtoken.impl.io.CodecPolicy;
import java.io.OutputStream;

class Base64OutputStream
extends BaseNCodecOutputStream {
    Base64OutputStream(OutputStream outputStream) {
        this(outputStream, true, true);
    }

    Base64OutputStream(OutputStream outputStream, boolean bl, boolean bl2) {
        super(outputStream, new Base64Codec(0, BaseNCodec.CHUNK_SEPARATOR, bl2), bl);
    }

    Base64OutputStream(OutputStream outputStream, boolean bl, int n, byte[] byArray) {
        super(outputStream, new Base64Codec(n, byArray), bl);
    }

    Base64OutputStream(OutputStream outputStream, boolean bl, int n, byte[] byArray, CodecPolicy codecPolicy) {
        super(outputStream, new Base64Codec(n, byArray, false, codecPolicy), bl);
    }
}

