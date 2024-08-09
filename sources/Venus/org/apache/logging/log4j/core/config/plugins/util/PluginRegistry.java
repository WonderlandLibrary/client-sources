/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config.plugins.util;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAliases;
import org.apache.logging.log4j.core.config.plugins.processor.PluginCache;
import org.apache.logging.log4j.core.config.plugins.processor.PluginEntry;
import org.apache.logging.log4j.core.config.plugins.util.PluginType;
import org.apache.logging.log4j.core.config.plugins.util.ResolverUtil;
import org.apache.logging.log4j.core.util.Loader;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.Strings;

public class PluginRegistry {
    private static final Logger LOGGER = StatusLogger.getLogger();
    private static volatile PluginRegistry INSTANCE;
    private static final Object INSTANCE_LOCK;
    private final AtomicReference<Map<String, List<PluginType<?>>>> pluginsByCategoryRef = new AtomicReference();
    private final ConcurrentMap<Long, Map<String, List<PluginType<?>>>> pluginsByCategoryByBundleId = new ConcurrentHashMap();
    private final ConcurrentMap<String, Map<String, List<PluginType<?>>>> pluginsByCategoryByPackage = new ConcurrentHashMap();

    private PluginRegistry() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static PluginRegistry getInstance() {
        PluginRegistry pluginRegistry = INSTANCE;
        if (pluginRegistry == null) {
            Object object = INSTANCE_LOCK;
            synchronized (object) {
                pluginRegistry = INSTANCE;
                if (pluginRegistry == null) {
                    INSTANCE = pluginRegistry = new PluginRegistry();
                }
            }
        }
        return pluginRegistry;
    }

    public void clear() {
        this.pluginsByCategoryRef.set(null);
        this.pluginsByCategoryByPackage.clear();
        this.pluginsByCategoryByBundleId.clear();
    }

    public Map<Long, Map<String, List<PluginType<?>>>> getPluginsByCategoryByBundleId() {
        return this.pluginsByCategoryByBundleId;
    }

    public Map<String, List<PluginType<?>>> loadFromMainClassLoader() {
        Map<String, List<PluginType<?>>> map = this.pluginsByCategoryRef.get();
        if (map != null) {
            return map;
        }
        Map<String, List<PluginType<?>>> map2 = this.decodeCacheFiles(Loader.getClassLoader());
        if (this.pluginsByCategoryRef.compareAndSet(null, map2)) {
            return map2;
        }
        return this.pluginsByCategoryRef.get();
    }

    public void clearBundlePlugins(long l) {
        this.pluginsByCategoryByBundleId.remove(l);
    }

    public Map<String, List<PluginType<?>>> loadFromBundle(long l, ClassLoader classLoader) {
        Map<String, List<PluginType<?>>> map = (Map<String, List<PluginType<?>>>)this.pluginsByCategoryByBundleId.get(l);
        if (map != null) {
            return map;
        }
        Map<String, List<PluginType<?>>> map2 = this.decodeCacheFiles(classLoader);
        map = this.pluginsByCategoryByBundleId.putIfAbsent(l, map2);
        if (map != null) {
            return map;
        }
        return map2;
    }

    private Map<String, List<PluginType<?>>> decodeCacheFiles(ClassLoader classLoader) {
        Object object;
        Object object2;
        long l = System.nanoTime();
        PluginCache pluginCache = new PluginCache();
        try {
            object2 = classLoader.getResources("META-INF/org/apache/logging/log4j/core/config/plugins/Log4j2Plugins.dat");
            if (object2 == null) {
                LOGGER.info("Plugin preloads not available from class loader {}", (Object)classLoader);
            } else {
                pluginCache.loadCacheFiles((Enumeration<URL>)object2);
            }
        } catch (IOException iOException) {
            LOGGER.warn("Unable to preload plugins", (Throwable)iOException);
        }
        object2 = new HashMap();
        int n = 0;
        for (Map.Entry<String, Map<String, PluginEntry>> entry : pluginCache.getAllCategories().entrySet()) {
            object = entry.getKey();
            ArrayList arrayList = new ArrayList(entry.getValue().size());
            object2.put(object, arrayList);
            for (Map.Entry<String, PluginEntry> entry2 : entry.getValue().entrySet()) {
                PluginEntry pluginEntry = entry2.getValue();
                String string = pluginEntry.getClassName();
                try {
                    Class<?> clazz = classLoader.loadClass(string);
                    PluginType pluginType = new PluginType(pluginEntry, clazz, pluginEntry.getName());
                    arrayList.add(pluginType);
                    ++n;
                } catch (ClassNotFoundException classNotFoundException) {
                    LOGGER.info("Plugin [{}] could not be loaded due to missing classes.", (Object)string, (Object)classNotFoundException);
                } catch (VerifyError verifyError) {
                    LOGGER.info("Plugin [{}] could not be loaded due to verification error.", (Object)string, (Object)verifyError);
                }
            }
        }
        long l2 = System.nanoTime();
        object = new DecimalFormat("#0.000000");
        double d = (double)(l2 - l) * 1.0E-9;
        LOGGER.debug("Took {} seconds to load {} plugins from {}", (Object)((NumberFormat)object).format(d), (Object)n, (Object)classLoader);
        return object2;
    }

    public Map<String, List<PluginType<?>>> loadFromPackage(String string) {
        Object object;
        if (Strings.isBlank(string)) {
            return Collections.emptyMap();
        }
        Map map = (Map)this.pluginsByCategoryByPackage.get(string);
        if (map != null) {
            return map;
        }
        long l = System.nanoTime();
        ResolverUtil resolverUtil = new ResolverUtil();
        ClassLoader classLoader = Loader.getClassLoader();
        if (classLoader != null) {
            resolverUtil.setClassLoader(classLoader);
        }
        resolverUtil.findInPackage(new PluginTest(), string);
        HashMap hashMap = new HashMap();
        for (Class<?> clazz : resolverUtil.getClasses()) {
            object = clazz.getAnnotation(Plugin.class);
            String string2 = object.category().toLowerCase();
            ArrayList arrayList = (ArrayList)hashMap.get(string2);
            if (arrayList == null) {
                arrayList = new ArrayList();
                hashMap.put(string2, arrayList);
            }
            PluginEntry pluginEntry = new PluginEntry();
            String string3 = object.elementType().equals("") ? object.name() : object.elementType();
            pluginEntry.setKey(object.name().toLowerCase());
            pluginEntry.setName(object.name());
            pluginEntry.setCategory(object.category());
            pluginEntry.setClassName(clazz.getName());
            pluginEntry.setPrintable(object.printObject());
            pluginEntry.setDefer(object.deferChildren());
            PluginType pluginType = new PluginType(pluginEntry, clazz, string3);
            arrayList.add(pluginType);
            PluginAliases pluginAliases = clazz.getAnnotation(PluginAliases.class);
            if (pluginAliases == null) continue;
            for (String string4 : pluginAliases.value()) {
                PluginEntry pluginEntry2 = new PluginEntry();
                String string5 = object.elementType().equals("") ? string4.trim() : object.elementType();
                pluginEntry2.setKey(string4.trim().toLowerCase());
                pluginEntry2.setName(object.name());
                pluginEntry2.setCategory(object.category());
                pluginEntry2.setClassName(clazz.getName());
                pluginEntry2.setPrintable(object.printObject());
                pluginEntry2.setDefer(object.deferChildren());
                PluginType pluginType2 = new PluginType(pluginEntry2, clazz, string5);
                arrayList.add(pluginType2);
            }
        }
        long l2 = System.nanoTime();
        object = new DecimalFormat("#0.000000");
        double d = (double)(l2 - l) * 1.0E-9;
        LOGGER.debug("Took {} seconds to load {} plugins from package {}", (Object)((NumberFormat)object).format(d), (Object)resolverUtil.getClasses().size(), (Object)string);
        map = this.pluginsByCategoryByPackage.putIfAbsent(string, hashMap);
        if (map != null) {
            return map;
        }
        return hashMap;
    }

    static {
        INSTANCE_LOCK = new Object();
    }

    public static class PluginTest
    implements ResolverUtil.Test {
        @Override
        public boolean matches(Class<?> clazz) {
            return clazz != null && clazz.isAnnotationPresent(Plugin.class);
        }

        public String toString() {
            return "annotated with @" + Plugin.class.getSimpleName();
        }

        @Override
        public boolean matches(URI uRI) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean doesMatchClass() {
            return false;
        }

        @Override
        public boolean doesMatchResource() {
            return true;
        }
    }
}

