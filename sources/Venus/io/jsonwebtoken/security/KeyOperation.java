/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.Identifiable;

public interface KeyOperation
extends Identifiable {
    public String getDescription();

    public boolean isRelated(KeyOperation var1);
}

