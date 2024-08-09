/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import java.io.InputStream;

public interface AssociatedDataSupplier {
    public InputStream getAssociatedData();
}

