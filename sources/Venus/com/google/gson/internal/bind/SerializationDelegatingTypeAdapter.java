/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.gson.internal.bind;

import com.google.gson.TypeAdapter;

public abstract class SerializationDelegatingTypeAdapter<T>
extends TypeAdapter<T> {
    public abstract TypeAdapter<T> getSerializationDelegate();
}

