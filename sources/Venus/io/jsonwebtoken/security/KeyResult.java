/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.security.KeySupplier;
import io.jsonwebtoken.security.Message;
import javax.crypto.SecretKey;

public interface KeyResult
extends Message<byte[]>,
KeySupplier<SecretKey> {
}

