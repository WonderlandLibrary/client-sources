/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.CollectSpliterators;
import com.google.common.collect.ForwardingMap;
import com.google.common.collect.ForwardingMapEntry;
import com.google.common.collect.ForwardingSet;
import com.google.common.collect.TransformedIterator;
import com.google.common.primitives.Primitives;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;

@GwtIncompatible
public final class MutableClassToInstanceMap<B>
extends ForwardingMap<Class<? extends B>, B>
implements ClassToInstanceMap<B>,
Serializable {
    private final Map<Class<? extends B>, B> delegate;

    public static <B> MutableClassToInstanceMap<B> create() {
        return new MutableClassToInstanceMap(new HashMap());
    }

    public static <B> MutableClassToInstanceMap<B> create(Map<Class<? extends B>, B> map) {
        return new MutableClassToInstanceMap<B>(map);
    }

    private MutableClassToInstanceMap(Map<Class<? extends B>, B> map) {
        this.delegate = Preconditions.checkNotNull(map);
    }

    @Override
    protected Map<Class<? extends B>, B> delegate() {
        return this.delegate;
    }

    private static <B> Map.Entry<Class<? extends B>, B> checkedEntry(Map.Entry<Class<? extends B>, B> entry) {
        return new ForwardingMapEntry<Class<? extends B>, B>(entry){
            final Map.Entry val$entry;
            {
                this.val$entry = entry;
            }

            @Override
            protected Map.Entry<Class<? extends B>, B> delegate() {
                return this.val$entry;
            }

            @Override
            public B setValue(B b) {
                return super.setValue(MutableClassToInstanceMap.access$000((Class)this.getKey(), b));
            }

            @Override
            protected Object delegate() {
                return this.delegate();
            }
        };
    }

    @Override
    public Set<Map.Entry<Class<? extends B>, B>> entrySet() {
        return new ForwardingSet<Map.Entry<Class<? extends B>, B>>(this){
            final MutableClassToInstanceMap this$0;
            {
                this.this$0 = mutableClassToInstanceMap;
            }

            @Override
            protected Set<Map.Entry<Class<? extends B>, B>> delegate() {
                return this.this$0.delegate().entrySet();
            }

            @Override
            public Spliterator<Map.Entry<Class<? extends B>, B>> spliterator() {
                return CollectSpliterators.map(this.delegate().spliterator(), 2::lambda$spliterator$0);
            }

            @Override
            public Iterator<Map.Entry<Class<? extends B>, B>> iterator() {
                return new TransformedIterator<Map.Entry<Class<? extends B>, B>, Map.Entry<Class<? extends B>, B>>(this, this.delegate().iterator()){
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        super(iterator2);
                    }

                    @Override
                    Map.Entry<Class<? extends B>, B> transform(Map.Entry<Class<? extends B>, B> entry) {
                        return MutableClassToInstanceMap.access$100(entry);
                    }

                    @Override
                    Object transform(Object object) {
                        return this.transform((Map.Entry)object);
                    }
                };
            }

            @Override
            public Object[] toArray() {
                return this.standardToArray();
            }

            @Override
            public <T> T[] toArray(T[] TArray) {
                return this.standardToArray(TArray);
            }

            @Override
            protected Collection delegate() {
                return this.delegate();
            }

            @Override
            protected Object delegate() {
                return this.delegate();
            }

            private static Map.Entry lambda$spliterator$0(Map.Entry entry) {
                return MutableClassToInstanceMap.access$100(entry);
            }
        };
    }

    @Override
    @CanIgnoreReturnValue
    public B put(Class<? extends B> clazz, B b) {
        return super.put(clazz, MutableClassToInstanceMap.cast(clazz, b));
    }

    @Override
    public void putAll(Map<? extends Class<? extends B>, ? extends B> map) {
        LinkedHashMap<Class<B>, B> linkedHashMap = new LinkedHashMap<Class<B>, B>(map);
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            MutableClassToInstanceMap.cast((Class)entry.getKey(), entry.getValue());
        }
        super.putAll(linkedHashMap);
    }

    @Override
    @CanIgnoreReturnValue
    public <T extends B> T putInstance(Class<T> clazz, T t) {
        return MutableClassToInstanceMap.cast(clazz, this.put(clazz, t));
    }

    @Override
    public <T extends B> T getInstance(Class<T> clazz) {
        return MutableClassToInstanceMap.cast(clazz, this.get(clazz));
    }

    @CanIgnoreReturnValue
    private static <B, T extends B> T cast(Class<T> clazz, B b) {
        return Primitives.wrap(clazz).cast(b);
    }

    private Object writeReplace() {
        return new SerializedForm(this.delegate());
    }

    @Override
    @CanIgnoreReturnValue
    public Object put(Object object, Object object2) {
        return this.put((Class)object, object2);
    }

    @Override
    protected Object delegate() {
        return this.delegate();
    }

    static Object access$000(Class clazz, Object object) {
        return MutableClassToInstanceMap.cast(clazz, object);
    }

    static Map.Entry access$100(Map.Entry entry) {
        return MutableClassToInstanceMap.checkedEntry(entry);
    }

    private static final class SerializedForm<B>
    implements Serializable {
        private final Map<Class<? extends B>, B> backingMap;
        private static final long serialVersionUID = 0L;

        SerializedForm(Map<Class<? extends B>, B> map) {
            this.backingMap = map;
        }

        Object readResolve() {
            return MutableClassToInstanceMap.create(this.backingMap);
        }
    }
}

