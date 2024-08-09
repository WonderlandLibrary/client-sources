/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.lang.Builder;
import java.security.Provider;
import java.security.SecureRandom;

public interface SecurityBuilder<T, B extends SecurityBuilder<T, B>>
extends Builder<T> {
    public B provider(Provider var1);

    public B random(SecureRandom var1);
}

