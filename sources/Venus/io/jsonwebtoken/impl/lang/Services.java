/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.lang;

import io.jsonwebtoken.impl.lang.UnavailableImplementationException;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class Services {
    private static ConcurrentMap<Class<?>, ServiceLoader<?>> SERVICE_CACHE = new ConcurrentHashMap();
    private static final List<ClassLoaderAccessor> CLASS_LOADER_ACCESSORS = Collections.arrayToList(new ClassLoaderAccessor[]{new ClassLoaderAccessor(){

        @Override
        public ClassLoader getClassLoader() {
            return Thread.currentThread().getContextClassLoader();
        }
    }, new ClassLoaderAccessor(){

        @Override
        public ClassLoader getClassLoader() {
            return Services.class.getClassLoader();
        }
    }, new ClassLoaderAccessor(){

        @Override
        public ClassLoader getClassLoader() {
            return ClassLoader.getSystemClassLoader();
        }
    }});

    private Services() {
    }

    public static <T> List<T> loadAll(Class<T> clazz) {
        Assert.notNull(clazz, "Parameter 'spi' must not be null.");
        ServiceLoader<T> serviceLoader = Services.serviceLoader(clazz);
        if (serviceLoader != null) {
            ArrayList<T> arrayList = new ArrayList<T>();
            for (T t : serviceLoader) {
                arrayList.add(t);
            }
            return arrayList;
        }
        throw new UnavailableImplementationException(clazz);
    }

    public static <T> T loadFirst(Class<T> clazz) {
        Assert.notNull(clazz, "Parameter 'spi' must not be null.");
        ServiceLoader<T> serviceLoader = Services.serviceLoader(clazz);
        if (serviceLoader != null) {
            return serviceLoader.iterator().next();
        }
        throw new UnavailableImplementationException(clazz);
    }

    private static <T> ServiceLoader<T> serviceLoader(Class<T> clazz) {
        ServiceLoader<T> serviceLoader = (ServiceLoader<T>)SERVICE_CACHE.get(clazz);
        if (serviceLoader != null) {
            return serviceLoader;
        }
        for (ClassLoaderAccessor classLoaderAccessor : CLASS_LOADER_ACCESSORS) {
            serviceLoader = ServiceLoader.load(clazz, classLoaderAccessor.getClassLoader());
            if (!serviceLoader.iterator().hasNext()) continue;
            SERVICE_CACHE.putIfAbsent(clazz, serviceLoader);
            return serviceLoader;
        }
        return null;
    }

    public static void reload() {
        SERVICE_CACHE.clear();
    }

    private static interface ClassLoaderAccessor {
        public ClassLoader getClassLoader();
    }
}

