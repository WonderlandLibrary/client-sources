/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.lang.Builder;
import io.jsonwebtoken.security.KeyOperation;

public interface KeyOperationBuilder
extends Builder<KeyOperation> {
    public KeyOperationBuilder id(String var1);

    public KeyOperationBuilder description(String var1);

    public KeyOperationBuilder related(String var1);
}

