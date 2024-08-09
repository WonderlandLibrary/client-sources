/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.security.KeySupplier;
import io.jsonwebtoken.security.Request;
import java.security.Key;

public interface SecureRequest<T, K extends Key>
extends Request<T>,
KeySupplier<K> {
}

