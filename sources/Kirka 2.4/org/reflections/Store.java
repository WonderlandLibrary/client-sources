/*
 * Decompiled with CFR 0.143.
 */
package org.reflections;

import com.google.common.base.Supplier;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import org.reflections.Configuration;
import org.reflections.ReflectionsException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class Store {
    private transient boolean concurrent;
    private final Map<String, Multimap<String, String>> storeMap = new HashMap<String, Multimap<String, String>>();

    protected Store() {
        this.concurrent = false;
    }

    public Store(Configuration configuration) {
        this.concurrent = configuration.getExecutorService() != null;
    }

    public Set<String> keySet() {
        return this.storeMap.keySet();
    }

    public Multimap<String, String> getOrCreate(String index) {
        SetMultimap mmap = this.storeMap.get(index);
        if (mmap == null) {
            SetMultimap multimap = Multimaps.newSetMultimap(new HashMap(), (Supplier)new Supplier<Set<String>>(){

                public Set<String> get() {
                    return Sets.newSetFromMap(new ConcurrentHashMap());
                }
            });
            mmap = this.concurrent ? Multimaps.synchronizedSetMultimap((SetMultimap)multimap) : multimap;
            this.storeMap.put(index, (Multimap<String, String>)mmap);
        }
        return mmap;
    }

    public Multimap<String, String> get(String index) {
        Multimap<String, String> mmap = this.storeMap.get(index);
        if (mmap == null) {
            throw new ReflectionsException("Scanner " + index + " was not configured");
        }
        return mmap;
    }

    public Iterable<String> get(String index, String ... keys) {
        return this.get(index, Arrays.asList(keys));
    }

    public Iterable<String> get(String index, Iterable<String> keys) {
        Multimap<String, String> mmap = this.get(index);
        IterableChain<String> result = new IterableChain<String>();
        for (String key : keys) {
            result.addAll(mmap.get((Object)key));
        }
        return result;
    }

    private Iterable<String> getAllIncluding(String index, Iterable<String> keys, IterableChain<String> result) {
        result.addAll(keys);
        for (String key : keys) {
            Iterable<String> values = this.get(index, key);
            if (!values.iterator().hasNext()) continue;
            this.getAllIncluding(index, values, result);
        }
        return result;
    }

    public Iterable<String> getAll(String index, String key) {
        return this.getAllIncluding(index, this.get(index, key), new IterableChain<String>());
    }

    public Iterable<String> getAll(String index, Iterable<String> keys) {
        return this.getAllIncluding(index, this.get(index, keys), new IterableChain<String>());
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class IterableChain<T>
    implements Iterable<T> {
        private final List<Iterable<T>> chain = Lists.newArrayList();

        private IterableChain() {
        }

        private void addAll(Iterable<T> iterable) {
            this.chain.add(iterable);
        }

        @Override
        public Iterator<T> iterator() {
            return Iterables.concat(this.chain).iterator();
        }
    }

}

