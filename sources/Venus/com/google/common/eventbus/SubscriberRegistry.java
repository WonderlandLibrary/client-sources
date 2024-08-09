/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.eventbus;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.common.eventbus.Subscriber;
import com.google.common.reflect.TypeToken;
import com.google.common.util.concurrent.UncheckedExecutionException;
import com.google.j2objc.annotations.Weak;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.annotation.Nullable;

final class SubscriberRegistry {
    private final ConcurrentMap<Class<?>, CopyOnWriteArraySet<Subscriber>> subscribers = Maps.newConcurrentMap();
    @Weak
    private final EventBus bus;
    private static final LoadingCache<Class<?>, ImmutableList<Method>> subscriberMethodsCache = CacheBuilder.newBuilder().weakKeys().build(new CacheLoader<Class<?>, ImmutableList<Method>>(){

        @Override
        public ImmutableList<Method> load(Class<?> clazz) throws Exception {
            return SubscriberRegistry.access$000(clazz);
        }

        @Override
        public Object load(Object object) throws Exception {
            return this.load((Class)object);
        }
    });
    private static final LoadingCache<Class<?>, ImmutableSet<Class<?>>> flattenHierarchyCache = CacheBuilder.newBuilder().weakKeys().build(new CacheLoader<Class<?>, ImmutableSet<Class<?>>>(){

        @Override
        public ImmutableSet<Class<?>> load(Class<?> clazz) {
            return ImmutableSet.copyOf(TypeToken.of(clazz).getTypes().rawTypes());
        }

        @Override
        public Object load(Object object) throws Exception {
            return this.load((Class)object);
        }
    });

    SubscriberRegistry(EventBus eventBus) {
        this.bus = Preconditions.checkNotNull(eventBus);
    }

    void register(Object object) {
        Multimap<Class<?>, Subscriber> multimap = this.findAllSubscribers(object);
        for (Map.Entry<Class<?>, Collection<Subscriber>> entry : multimap.asMap().entrySet()) {
            Class<?> clazz = entry.getKey();
            Collection<Subscriber> collection = entry.getValue();
            CopyOnWriteArraySet<Subscriber> copyOnWriteArraySet = (CopyOnWriteArraySet<Subscriber>)this.subscribers.get(clazz);
            if (copyOnWriteArraySet == null) {
                CopyOnWriteArraySet copyOnWriteArraySet2 = new CopyOnWriteArraySet();
                copyOnWriteArraySet = MoreObjects.firstNonNull(this.subscribers.putIfAbsent(clazz, copyOnWriteArraySet2), copyOnWriteArraySet2);
            }
            copyOnWriteArraySet.addAll(collection);
        }
    }

    void unregister(Object object) {
        Multimap<Class<?>, Subscriber> multimap = this.findAllSubscribers(object);
        for (Map.Entry<Class<?>, Collection<Subscriber>> entry : multimap.asMap().entrySet()) {
            Class<?> clazz = entry.getKey();
            Collection<Subscriber> collection = entry.getValue();
            CopyOnWriteArraySet copyOnWriteArraySet = (CopyOnWriteArraySet)this.subscribers.get(clazz);
            if (copyOnWriteArraySet != null && copyOnWriteArraySet.removeAll(collection)) continue;
            throw new IllegalArgumentException("missing event subscriber for an annotated method. Is " + object + " registered?");
        }
    }

    @VisibleForTesting
    Set<Subscriber> getSubscribersForTesting(Class<?> clazz) {
        return MoreObjects.firstNonNull(this.subscribers.get(clazz), ImmutableSet.of());
    }

    Iterator<Subscriber> getSubscribers(Object object) {
        ImmutableSet<Class<?>> immutableSet = SubscriberRegistry.flattenHierarchy(object.getClass());
        ArrayList arrayList = Lists.newArrayListWithCapacity(immutableSet.size());
        for (Class clazz : immutableSet) {
            CopyOnWriteArraySet copyOnWriteArraySet = (CopyOnWriteArraySet)this.subscribers.get(clazz);
            if (copyOnWriteArraySet == null) continue;
            arrayList.add(copyOnWriteArraySet.iterator());
        }
        return Iterators.concat(arrayList.iterator());
    }

    private Multimap<Class<?>, Subscriber> findAllSubscribers(Object object) {
        HashMultimap<Class<?>, Subscriber> hashMultimap = HashMultimap.create();
        Class<?> clazz = object.getClass();
        for (Method method : SubscriberRegistry.getAnnotatedMethods(clazz)) {
            Class<?>[] classArray = method.getParameterTypes();
            Class<?> clazz2 = classArray[0];
            hashMultimap.put(clazz2, Subscriber.create(this.bus, object, method));
        }
        return hashMultimap;
    }

    private static ImmutableList<Method> getAnnotatedMethods(Class<?> clazz) {
        return subscriberMethodsCache.getUnchecked(clazz);
    }

    private static ImmutableList<Method> getAnnotatedMethodsNotCached(Class<?> clazz) {
        Set set = TypeToken.of(clazz).getTypes().rawTypes();
        HashMap<MethodIdentifier, Method> hashMap = Maps.newHashMap();
        for (Class clazz2 : set) {
            for (Method method : clazz2.getDeclaredMethods()) {
                if (!method.isAnnotationPresent(Subscribe.class) || method.isSynthetic()) continue;
                Class<?>[] classArray = method.getParameterTypes();
                Preconditions.checkArgument(classArray.length == 1, "Method %s has @Subscribe annotation but has %s parameters.Subscriber methods must have exactly 1 parameter.", (Object)method, classArray.length);
                MethodIdentifier methodIdentifier = new MethodIdentifier(method);
                if (hashMap.containsKey(methodIdentifier)) continue;
                hashMap.put(methodIdentifier, method);
            }
        }
        return ImmutableList.copyOf(hashMap.values());
    }

    @VisibleForTesting
    static ImmutableSet<Class<?>> flattenHierarchy(Class<?> clazz) {
        try {
            return flattenHierarchyCache.getUnchecked(clazz);
        } catch (UncheckedExecutionException uncheckedExecutionException) {
            throw Throwables.propagate(uncheckedExecutionException.getCause());
        }
    }

    static ImmutableList access$000(Class clazz) {
        return SubscriberRegistry.getAnnotatedMethodsNotCached(clazz);
    }

    private static final class MethodIdentifier {
        private final String name;
        private final List<Class<?>> parameterTypes;

        MethodIdentifier(Method method) {
            this.name = method.getName();
            this.parameterTypes = Arrays.asList(method.getParameterTypes());
        }

        public int hashCode() {
            return Objects.hashCode(this.name, this.parameterTypes);
        }

        public boolean equals(@Nullable Object object) {
            if (object instanceof MethodIdentifier) {
                MethodIdentifier methodIdentifier = (MethodIdentifier)object;
                return this.name.equals(methodIdentifier.name) && this.parameterTypes.equals(methodIdentifier.parameterTypes);
            }
            return true;
        }
    }
}

