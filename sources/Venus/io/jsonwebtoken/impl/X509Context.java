/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl;

import io.jsonwebtoken.security.X509Accessor;
import io.jsonwebtoken.security.X509Mutator;

public interface X509Context<T extends X509Mutator<T>>
extends X509Accessor,
X509Mutator<T> {
}

