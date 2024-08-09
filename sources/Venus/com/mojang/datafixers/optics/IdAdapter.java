/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.optics;

import com.mojang.datafixers.optics.Adapter;

class IdAdapter<S, T>
implements Adapter<S, T, S, T> {
    IdAdapter() {
    }

    @Override
    public S from(S s) {
        return s;
    }

    @Override
    public T to(T t) {
        return t;
    }

    public boolean equals(Object object) {
        return object instanceof IdAdapter;
    }

    public String toString() {
        return "id";
    }
}

