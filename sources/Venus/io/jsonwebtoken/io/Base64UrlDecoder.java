/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.io;

import io.jsonwebtoken.io.Base64;
import io.jsonwebtoken.io.Base64Decoder;

class Base64UrlDecoder
extends Base64Decoder {
    Base64UrlDecoder() {
        super(Base64.URL_SAFE);
    }
}

