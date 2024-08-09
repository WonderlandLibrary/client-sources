/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.spi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.logging.log4j.message.MessageFactory;
import org.apache.logging.log4j.spi.AbstractLogger;
import org.apache.logging.log4j.spi.ExtendedLogger;

public class LoggerRegistry<T extends ExtendedLogger> {
    private static final String DEFAULT_FACTORY_KEY = AbstractLogger.DEFAULT_MESSAGE_FACTORY_CLASS.getName();
    private final MapFactory<T> factory;
    private final Map<String, Map<String, T>> map;

    public LoggerRegistry() {
        this(new ConcurrentMapFactory());
    }

    public LoggerRegistry(MapFactory<T> mapFactory) {
        this.factory = Objects.requireNonNull(mapFactory, "factory");
        this.map = mapFactory.createOuterMap();
    }

    private static String factoryClassKey(Class<? extends MessageFactory> clazz) {
        return clazz == null ? DEFAULT_FACTORY_KEY : clazz.getName();
    }

    private static String factoryKey(MessageFactory messageFactory) {
        return messageFactory == null ? DEFAULT_FACTORY_KEY : messageFactory.getClass().getName();
    }

    public T getLogger(String string) {
        return (T)((ExtendedLogger)this.getOrCreateInnerMap(DEFAULT_FACTORY_KEY).get(string));
    }

    public T getLogger(String string, MessageFactory messageFactory) {
        return (T)((ExtendedLogger)this.getOrCreateInnerMap(LoggerRegistry.factoryKey(messageFactory)).get(string));
    }

    public Collection<T> getLoggers() {
        return this.getLoggers(new ArrayList());
    }

    public Collection<T> getLoggers(Collection<T> collection) {
        for (Map<String, T> map : this.map.values()) {
            collection.addAll(map.values());
        }
        return collection;
    }

    private Map<String, T> getOrCreateInnerMap(String string) {
        Map<String, T> map = this.map.get(string);
        if (map == null) {
            map = this.factory.createInnerMap();
            this.map.put(string, map);
        }
        return map;
    }

    public boolean hasLogger(String string) {
        return this.getOrCreateInnerMap(DEFAULT_FACTORY_KEY).containsKey(string);
    }

    public boolean hasLogger(String string, MessageFactory messageFactory) {
        return this.getOrCreateInnerMap(LoggerRegistry.factoryKey(messageFactory)).containsKey(string);
    }

    public boolean hasLogger(String string, Class<? extends MessageFactory> clazz) {
        return this.getOrCreateInnerMap(LoggerRegistry.factoryClassKey(clazz)).containsKey(string);
    }

    public void putIfAbsent(String string, MessageFactory messageFactory, T t) {
        this.factory.putIfAbsent(this.getOrCreateInnerMap(LoggerRegistry.factoryKey(messageFactory)), string, t);
    }

    public static class WeakMapFactory<T extends ExtendedLogger>
    implements MapFactory<T> {
        @Override
        public Map<String, T> createInnerMap() {
            return new WeakHashMap();
        }

        @Override
        public Map<String, Map<String, T>> createOuterMap() {
            return new WeakHashMap<String, Map<String, T>>();
        }

        @Override
        public void putIfAbsent(Map<String, T> map, String string, T t) {
            map.put(string, t);
        }
    }

    public static class ConcurrentMapFactory<T extends ExtendedLogger>
    implements MapFactory<T> {
        @Override
        public Map<String, T> createInnerMap() {
            return new ConcurrentHashMap();
        }

        @Override
        public Map<String, Map<String, T>> createOuterMap() {
            return new ConcurrentHashMap<String, Map<String, T>>();
        }

        @Override
        public void putIfAbsent(Map<String, T> map, String string, T t) {
            ((ConcurrentMap)map).putIfAbsent(string, t);
        }
    }

    public static interface MapFactory<T extends ExtendedLogger> {
        public Map<String, T> createInnerMap();

        public Map<String, Map<String, T>> createOuterMap();

        public void putIfAbsent(Map<String, T> var1, String var2, T var3);
    }
}

