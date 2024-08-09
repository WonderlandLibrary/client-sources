/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.io;

import io.jsonwebtoken.io.Base64;
import io.jsonwebtoken.io.Base64Encoder;

class Base64UrlEncoder
extends Base64Encoder {
    Base64UrlEncoder() {
        super(Base64.URL_SAFE);
    }
}

