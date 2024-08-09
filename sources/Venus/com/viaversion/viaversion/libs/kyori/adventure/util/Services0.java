/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.util;

import java.util.ServiceLoader;

final class Services0 {
    private Services0() {
    }

    static <S> ServiceLoader<S> loader(Class<S> clazz) {
        return ServiceLoader.load(clazz, clazz.getClassLoader());
    }
}

