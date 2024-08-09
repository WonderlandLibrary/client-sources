/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.ICUDebug;
import com.ibm.icu.impl.ICUNotifier;
import com.ibm.icu.impl.ICURWLock;
import com.ibm.icu.util.ULocale;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

public class ICUService
extends ICUNotifier {
    protected final String name;
    private static final boolean DEBUG = ICUDebug.enabled("service");
    private final ICURWLock factoryLock = new ICURWLock();
    private final List<Factory> factories = new ArrayList<Factory>();
    private int defaultSize = 0;
    private Map<String, CacheEntry> cache;
    private Map<String, Factory> idcache;
    private LocaleRef dnref;

    public ICUService() {
        this.name = "";
    }

    public ICUService(String string) {
        this.name = string;
    }

    public Object get(String string) {
        return this.getKey(this.createKey(string), null);
    }

    public Object get(String string, String[] stringArray) {
        if (string == null) {
            throw new NullPointerException("descriptor must not be null");
        }
        return this.getKey(this.createKey(string), stringArray);
    }

    public Object getKey(Key key) {
        return this.getKey(key, null);
    }

    public Object getKey(Key key, String[] stringArray) {
        return this.getKey(key, stringArray, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Object getKey(Key key, String[] stringArray, Factory factory) {
        if (this.factories.size() == 0) {
            return this.handleDefault(key, stringArray);
        }
        if (DEBUG) {
            System.out.println("Service: " + this.name + " key: " + key.canonicalID());
        }
        CacheEntry cacheEntry = null;
        if (key != null) {
            try {
                int n;
                this.factoryLock.acquireRead();
                Map<String, CacheEntry> map = this.cache;
                if (map == null) {
                    if (DEBUG) {
                        System.out.println("Service " + this.name + " cache was empty");
                    }
                    map = new ConcurrentHashMap<String, CacheEntry>();
                }
                String string = null;
                ArrayList<String> arrayList = null;
                boolean bl = false;
                int n2 = 0;
                int n3 = 0;
                int n4 = this.factories.size();
                boolean bl2 = true;
                if (factory != null) {
                    for (n = 0; n < n4; ++n) {
                        if (factory != this.factories.get(n)) continue;
                        n3 = n + 1;
                        break;
                    }
                    if (n3 == 0) {
                        throw new IllegalStateException("Factory " + factory + "not registered with service: " + this);
                    }
                    bl2 = false;
                }
                block4: do {
                    string = key.currentDescriptor();
                    if (DEBUG) {
                        System.out.println(this.name + "[" + n2++ + "] looking for: " + string);
                    }
                    if ((cacheEntry = map.get(string)) != null) {
                        if (!DEBUG) break;
                        System.out.println(this.name + " found with descriptor: " + string);
                        break;
                    }
                    if (DEBUG) {
                        System.out.println("did not find: " + string + " in cache");
                    }
                    bl = bl2;
                    n = n3;
                    while (n < n4) {
                        Object object;
                        Factory object2 = this.factories.get(n++);
                        if (DEBUG) {
                            System.out.println("trying factory[" + (n - 1) + "] " + object2.toString());
                        }
                        if ((object = object2.create(key, this)) != null) {
                            cacheEntry = new CacheEntry(string, object);
                            if (!DEBUG) break block4;
                            System.out.println(this.name + " factory supported: " + string + ", caching");
                            break block4;
                        }
                        if (!DEBUG) continue;
                        System.out.println("factory did not support: " + string);
                    }
                    if (arrayList == null) {
                        arrayList = new ArrayList<String>(5);
                    }
                    arrayList.add(string);
                } while (key.fallback());
                if (cacheEntry != null) {
                    if (bl) {
                        if (DEBUG) {
                            System.out.println("caching '" + cacheEntry.actualDescriptor + "'");
                        }
                        map.put(cacheEntry.actualDescriptor, cacheEntry);
                        if (arrayList != null) {
                            for (String string2 : arrayList) {
                                if (DEBUG) {
                                    System.out.println(this.name + " adding descriptor: '" + string2 + "' for actual: '" + cacheEntry.actualDescriptor + "'");
                                }
                                map.put(string2, cacheEntry);
                            }
                        }
                        this.cache = map;
                    }
                    if (stringArray != null) {
                        stringArray[0] = cacheEntry.actualDescriptor.indexOf("/") == 0 ? cacheEntry.actualDescriptor.substring(1) : cacheEntry.actualDescriptor;
                    }
                    if (DEBUG) {
                        System.out.println("found in service: " + this.name);
                    }
                    Object object = cacheEntry.service;
                    return object;
                }
            } finally {
                this.factoryLock.releaseRead();
            }
        }
        if (DEBUG) {
            System.out.println("not found in service: " + this.name);
        }
        return this.handleDefault(key, stringArray);
    }

    protected Object handleDefault(Key key, String[] stringArray) {
        return null;
    }

    public Set<String> getVisibleIDs() {
        return this.getVisibleIDs(null);
    }

    public Set<String> getVisibleIDs(String string) {
        Set<String> set = this.getVisibleIDMap().keySet();
        Key key = this.createKey(string);
        if (key != null) {
            HashSet<String> hashSet = new HashSet<String>(set.size());
            for (String string2 : set) {
                if (!key.isFallbackOf(string2)) continue;
                hashSet.add(string2);
            }
            set = hashSet;
        }
        return set;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Map<String, Factory> getVisibleIDMap() {
        ICUService iCUService = this;
        synchronized (iCUService) {
            if (this.idcache == null) {
                try {
                    this.factoryLock.acquireRead();
                    HashMap<String, Factory> hashMap = new HashMap<String, Factory>();
                    ListIterator<Factory> listIterator2 = this.factories.listIterator(this.factories.size());
                    while (listIterator2.hasPrevious()) {
                        Factory factory = listIterator2.previous();
                        factory.updateVisibleIDs(hashMap);
                    }
                    this.idcache = Collections.unmodifiableMap(hashMap);
                } finally {
                    this.factoryLock.releaseRead();
                }
            }
        }
        return this.idcache;
    }

    public String getDisplayName(String string) {
        return this.getDisplayName(string, ULocale.getDefault(ULocale.Category.DISPLAY));
    }

    public String getDisplayName(String string, ULocale uLocale) {
        Map<String, Factory> map = this.getVisibleIDMap();
        Factory factory = map.get(string);
        if (factory != null) {
            return factory.getDisplayName(string, uLocale);
        }
        Key key = this.createKey(string);
        while (key.fallback()) {
            factory = map.get(key.currentID());
            if (factory == null) continue;
            return factory.getDisplayName(string, uLocale);
        }
        return null;
    }

    public SortedMap<String, String> getDisplayNames() {
        ULocale uLocale = ULocale.getDefault(ULocale.Category.DISPLAY);
        return this.getDisplayNames(uLocale, null, null);
    }

    public SortedMap<String, String> getDisplayNames(ULocale uLocale) {
        return this.getDisplayNames(uLocale, null, null);
    }

    public SortedMap<String, String> getDisplayNames(ULocale uLocale, Comparator<Object> comparator) {
        return this.getDisplayNames(uLocale, comparator, null);
    }

    public SortedMap<String, String> getDisplayNames(ULocale uLocale, String string) {
        return this.getDisplayNames(uLocale, null, string);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public SortedMap<String, String> getDisplayNames(ULocale uLocale, Comparator<Object> comparator, String string) {
        Map<String, Object> map;
        Object object;
        SortedMap<String, String> sortedMap = null;
        LocaleRef localeRef = this.dnref;
        if (localeRef != null) {
            sortedMap = localeRef.get(uLocale, comparator);
        }
        while (sortedMap == null) {
            object = this;
            synchronized (object) {
                if (localeRef == this.dnref || this.dnref == null) {
                    sortedMap = new TreeMap<Object, String>(comparator);
                    map = this.getVisibleIDMap();
                    for (Map.Entry<Object, Object> entry : map.entrySet()) {
                        String string2 = (String)entry.getKey();
                        Factory factory = (Factory)entry.getValue();
                        sortedMap.put(factory.getDisplayName(string2, uLocale), string2);
                    }
                    sortedMap = Collections.unmodifiableSortedMap(sortedMap);
                    this.dnref = new LocaleRef(sortedMap, uLocale, comparator);
                } else {
                    localeRef = this.dnref;
                    sortedMap = localeRef.get(uLocale, comparator);
                }
            }
        }
        object = this.createKey(string);
        if (object == null) {
            return sortedMap;
        }
        map = new TreeMap<String, String>(sortedMap);
        Iterator<Map.Entry<Object, Object>> iterator2 = map.entrySet().iterator();
        while (iterator2.hasNext()) {
            Map.Entry entry = iterator2.next();
            if (((Key)object).isFallbackOf((String)entry.getValue())) continue;
            iterator2.remove();
        }
        return map;
    }

    public final List<Factory> factories() {
        try {
            this.factoryLock.acquireRead();
            ArrayList<Factory> arrayList = new ArrayList<Factory>(this.factories);
            return arrayList;
        } finally {
            this.factoryLock.releaseRead();
        }
    }

    public Factory registerObject(Object object, String string) {
        return this.registerObject(object, string, false);
    }

    public Factory registerObject(Object object, String string, boolean bl) {
        String string2 = this.createKey(string).canonicalID();
        return this.registerFactory(new SimpleFactory(object, string2, bl));
    }

    public final Factory registerFactory(Factory factory) {
        if (factory == null) {
            throw new NullPointerException();
        }
        try {
            this.factoryLock.acquireWrite();
            this.factories.add(0, factory);
            this.clearCaches();
        } finally {
            this.factoryLock.releaseWrite();
        }
        this.notifyChanged();
        return factory;
    }

    public final boolean unregisterFactory(Factory factory) {
        if (factory == null) {
            throw new NullPointerException();
        }
        boolean bl = false;
        try {
            this.factoryLock.acquireWrite();
            if (this.factories.remove(factory)) {
                bl = true;
                this.clearCaches();
            }
        } finally {
            this.factoryLock.releaseWrite();
        }
        if (bl) {
            this.notifyChanged();
        }
        return bl;
    }

    public final void reset() {
        try {
            this.factoryLock.acquireWrite();
            this.reInitializeFactories();
            this.clearCaches();
        } finally {
            this.factoryLock.releaseWrite();
        }
        this.notifyChanged();
    }

    protected void reInitializeFactories() {
        this.factories.clear();
    }

    public boolean isDefault() {
        return this.factories.size() == this.defaultSize;
    }

    protected void markDefault() {
        this.defaultSize = this.factories.size();
    }

    public Key createKey(String string) {
        return string == null ? null : new Key(string);
    }

    protected void clearCaches() {
        this.cache = null;
        this.idcache = null;
        this.dnref = null;
    }

    protected void clearServiceCache() {
        this.cache = null;
    }

    @Override
    protected boolean acceptsListener(EventListener eventListener) {
        return eventListener instanceof ServiceListener;
    }

    @Override
    protected void notifyListener(EventListener eventListener) {
        ((ServiceListener)eventListener).serviceChanged(this);
    }

    public String stats() {
        ICURWLock.Stats stats = this.factoryLock.resetStats();
        if (stats != null) {
            return stats.toString();
        }
        return "no stats";
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return super.toString() + "{" + this.name + "}";
    }

    public static interface ServiceListener
    extends EventListener {
        public void serviceChanged(ICUService var1);
    }

    private static class LocaleRef {
        private final ULocale locale;
        private SortedMap<String, String> dnCache;
        private Comparator<Object> com;

        LocaleRef(SortedMap<String, String> sortedMap, ULocale uLocale, Comparator<Object> comparator) {
            this.locale = uLocale;
            this.com = comparator;
            this.dnCache = sortedMap;
        }

        SortedMap<String, String> get(ULocale uLocale, Comparator<Object> comparator) {
            SortedMap<String, String> sortedMap = this.dnCache;
            if (sortedMap != null && this.locale.equals(uLocale) && (this.com == comparator || this.com != null && this.com.equals(comparator))) {
                return sortedMap;
            }
            return null;
        }
    }

    private static final class CacheEntry {
        final String actualDescriptor;
        final Object service;

        CacheEntry(String string, Object object) {
            this.actualDescriptor = string;
            this.service = object;
        }
    }

    public static class SimpleFactory
    implements Factory {
        protected Object instance;
        protected String id;
        protected boolean visible;

        public SimpleFactory(Object object, String string) {
            this(object, string, true);
        }

        public SimpleFactory(Object object, String string, boolean bl) {
            if (object == null || string == null) {
                throw new IllegalArgumentException("Instance or id is null");
            }
            this.instance = object;
            this.id = string;
            this.visible = bl;
        }

        @Override
        public Object create(Key key, ICUService iCUService) {
            if (this.id.equals(key.currentID())) {
                return this.instance;
            }
            return null;
        }

        @Override
        public void updateVisibleIDs(Map<String, Factory> map) {
            if (this.visible) {
                map.put(this.id, this);
            } else {
                map.remove(this.id);
            }
        }

        @Override
        public String getDisplayName(String string, ULocale uLocale) {
            return this.visible && this.id.equals(string) ? string : null;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(super.toString());
            stringBuilder.append(", id: ");
            stringBuilder.append(this.id);
            stringBuilder.append(", visible: ");
            stringBuilder.append(this.visible);
            return stringBuilder.toString();
        }
    }

    public static interface Factory {
        public Object create(Key var1, ICUService var2);

        public void updateVisibleIDs(Map<String, Factory> var1);

        public String getDisplayName(String var1, ULocale var2);
    }

    public static class Key {
        private final String id;

        public Key(String string) {
            this.id = string;
        }

        public final String id() {
            return this.id;
        }

        public String canonicalID() {
            return this.id;
        }

        public String currentID() {
            return this.canonicalID();
        }

        public String currentDescriptor() {
            return "/" + this.currentID();
        }

        public boolean fallback() {
            return true;
        }

        public boolean isFallbackOf(String string) {
            return this.canonicalID().equals(string);
        }
    }
}

