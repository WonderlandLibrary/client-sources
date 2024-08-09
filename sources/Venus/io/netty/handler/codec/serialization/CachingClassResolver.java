/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.serialization;

import io.netty.handler.codec.serialization.ClassResolver;
import java.util.Map;

class CachingClassResolver
implements ClassResolver {
    private final Map<String, Class<?>> classCache;
    private final ClassResolver delegate;

    CachingClassResolver(ClassResolver classResolver, Map<String, Class<?>> map) {
        this.delegate = classResolver;
        this.classCache = map;
    }

    @Override
    public Class<?> resolve(String string) throws ClassNotFoundException {
        Class<?> clazz = this.classCache.get(string);
        if (clazz != null) {
            return clazz;
        }
        clazz = this.delegate.resolve(string);
        this.classCache.put(string, clazz);
        return clazz;
    }
}

