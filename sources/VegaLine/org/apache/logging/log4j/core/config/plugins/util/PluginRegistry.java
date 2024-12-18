/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config.plugins.util;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.text.DecimalFormat;
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
        PluginRegistry result = INSTANCE;
        if (result == null) {
            Object object = INSTANCE_LOCK;
            synchronized (object) {
                result = INSTANCE;
                if (result == null) {
                    INSTANCE = result = new PluginRegistry();
                }
            }
        }
        return result;
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
        Map<String, List<PluginType<?>>> existing = this.pluginsByCategoryRef.get();
        if (existing != null) {
            return existing;
        }
        Map<String, List<PluginType<?>>> newPluginsByCategory = this.decodeCacheFiles(Loader.getClassLoader());
        if (this.pluginsByCategoryRef.compareAndSet(null, newPluginsByCategory)) {
            return newPluginsByCategory;
        }
        return this.pluginsByCategoryRef.get();
    }

    public void clearBundlePlugins(long bundleId) {
        this.pluginsByCategoryByBundleId.remove(bundleId);
    }

    public Map<String, List<PluginType<?>>> loadFromBundle(long bundleId, ClassLoader loader) {
        Map<String, List<PluginType<?>>> existing = (Map<String, List<PluginType<?>>>)this.pluginsByCategoryByBundleId.get(bundleId);
        if (existing != null) {
            return existing;
        }
        Map<String, List<PluginType<?>>> newPluginsByCategory = this.decodeCacheFiles(loader);
        existing = this.pluginsByCategoryByBundleId.putIfAbsent(bundleId, newPluginsByCategory);
        if (existing != null) {
            return existing;
        }
        return newPluginsByCategory;
    }

    private Map<String, List<PluginType<?>>> decodeCacheFiles(ClassLoader loader) {
        long startTime = System.nanoTime();
        PluginCache cache = new PluginCache();
        try {
            Enumeration<URL> resources = loader.getResources("META-INF/org/apache/logging/log4j/core/config/plugins/Log4j2Plugins.dat");
            if (resources == null) {
                LOGGER.info("Plugin preloads not available from class loader {}", (Object)loader);
            } else {
                cache.loadCacheFiles(resources);
            }
        } catch (IOException ioe) {
            LOGGER.warn("Unable to preload plugins", (Throwable)ioe);
        }
        HashMap newPluginsByCategory = new HashMap();
        int pluginCount = 0;
        for (Map.Entry<String, Map<String, PluginEntry>> outer : cache.getAllCategories().entrySet()) {
            String categoryLowerCase = outer.getKey();
            ArrayList types = new ArrayList(outer.getValue().size());
            newPluginsByCategory.put(categoryLowerCase, types);
            for (Map.Entry<String, PluginEntry> inner : outer.getValue().entrySet()) {
                PluginEntry entry = inner.getValue();
                String className = entry.getClassName();
                try {
                    Class<?> clazz = loader.loadClass(className);
                    PluginType type2 = new PluginType(entry, clazz, entry.getName());
                    types.add(type2);
                    ++pluginCount;
                } catch (ClassNotFoundException e) {
                    LOGGER.info("Plugin [{}] could not be loaded due to missing classes.", (Object)className, (Object)e);
                } catch (VerifyError e) {
                    LOGGER.info("Plugin [{}] could not be loaded due to verification error.", (Object)className, (Object)e);
                }
            }
        }
        long endTime = System.nanoTime();
        DecimalFormat numFormat = new DecimalFormat("#0.000000");
        double seconds = (double)(endTime - startTime) * 1.0E-9;
        LOGGER.debug("Took {} seconds to load {} plugins from {}", (Object)numFormat.format(seconds), (Object)pluginCount, (Object)loader);
        return newPluginsByCategory;
    }

    public Map<String, List<PluginType<?>>> loadFromPackage(String pkg) {
        if (Strings.isBlank(pkg)) {
            return Collections.emptyMap();
        }
        Map existing = (Map)this.pluginsByCategoryByPackage.get(pkg);
        if (existing != null) {
            return existing;
        }
        long startTime = System.nanoTime();
        ResolverUtil resolver = new ResolverUtil();
        ClassLoader classLoader = Loader.getClassLoader();
        if (classLoader != null) {
            resolver.setClassLoader(classLoader);
        }
        resolver.findInPackage(new PluginTest(), pkg);
        HashMap newPluginsByCategory = new HashMap();
        for (Class<?> clazz : resolver.getClasses()) {
            Plugin plugin = clazz.getAnnotation(Plugin.class);
            String categoryLowerCase = plugin.category().toLowerCase();
            ArrayList list = (ArrayList)newPluginsByCategory.get(categoryLowerCase);
            if (list == null) {
                list = new ArrayList();
                newPluginsByCategory.put(categoryLowerCase, list);
            }
            PluginEntry mainEntry = new PluginEntry();
            String mainElementName = plugin.elementType().equals("") ? plugin.name() : plugin.elementType();
            mainEntry.setKey(plugin.name().toLowerCase());
            mainEntry.setName(plugin.name());
            mainEntry.setCategory(plugin.category());
            mainEntry.setClassName(clazz.getName());
            mainEntry.setPrintable(plugin.printObject());
            mainEntry.setDefer(plugin.deferChildren());
            PluginType mainType = new PluginType(mainEntry, clazz, mainElementName);
            list.add(mainType);
            PluginAliases pluginAliases = clazz.getAnnotation(PluginAliases.class);
            if (pluginAliases == null) continue;
            for (String alias : pluginAliases.value()) {
                PluginEntry aliasEntry = new PluginEntry();
                String aliasElementName = plugin.elementType().equals("") ? alias.trim() : plugin.elementType();
                aliasEntry.setKey(alias.trim().toLowerCase());
                aliasEntry.setName(plugin.name());
                aliasEntry.setCategory(plugin.category());
                aliasEntry.setClassName(clazz.getName());
                aliasEntry.setPrintable(plugin.printObject());
                aliasEntry.setDefer(plugin.deferChildren());
                PluginType aliasType = new PluginType(aliasEntry, clazz, aliasElementName);
                list.add(aliasType);
            }
        }
        long endTime = System.nanoTime();
        DecimalFormat numFormat = new DecimalFormat("#0.000000");
        double seconds = (double)(endTime - startTime) * 1.0E-9;
        LOGGER.debug("Took {} seconds to load {} plugins from package {}", (Object)numFormat.format(seconds), (Object)resolver.getClasses().size(), (Object)pkg);
        existing = this.pluginsByCategoryByPackage.putIfAbsent(pkg, newPluginsByCategory);
        if (existing != null) {
            return existing;
        }
        return newPluginsByCategory;
    }

    static {
        INSTANCE_LOCK = new Object();
    }

    public static class PluginTest
    implements ResolverUtil.Test {
        @Override
        public boolean matches(Class<?> type2) {
            return type2 != null && type2.isAnnotationPresent(Plugin.class);
        }

        public String toString() {
            return "annotated with @" + Plugin.class.getSimpleName();
        }

        @Override
        public boolean matches(URI resource) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean doesMatchClass() {
            return true;
        }

        @Override
        public boolean doesMatchResource() {
            return false;
        }
    }
}

