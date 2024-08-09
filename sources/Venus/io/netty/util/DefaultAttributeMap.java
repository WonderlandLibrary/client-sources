/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util;

import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.AttributeMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class DefaultAttributeMap
implements AttributeMap {
    private static final AtomicReferenceFieldUpdater<DefaultAttributeMap, AtomicReferenceArray> updater = AtomicReferenceFieldUpdater.newUpdater(DefaultAttributeMap.class, AtomicReferenceArray.class, "attributes");
    private static final int BUCKET_SIZE = 4;
    private static final int MASK = 3;
    private volatile AtomicReferenceArray<DefaultAttribute<?>> attributes;

    @Override
    public <T> Attribute<T> attr(AttributeKey<T> attributeKey) {
        DefaultAttribute<Object> defaultAttribute;
        int n;
        DefaultAttribute<Object> defaultAttribute2;
        if (attributeKey == null) {
            throw new NullPointerException("key");
        }
        AtomicReferenceArray<DefaultAttribute<Object>> atomicReferenceArray = this.attributes;
        if (atomicReferenceArray == null && !updater.compareAndSet(this, null, atomicReferenceArray = new AtomicReferenceArray(4))) {
            atomicReferenceArray = this.attributes;
        }
        if ((defaultAttribute2 = atomicReferenceArray.get(n = DefaultAttributeMap.index(attributeKey))) == null) {
            defaultAttribute2 = new DefaultAttribute();
            defaultAttribute = new DefaultAttribute<T>(defaultAttribute2, attributeKey);
            DefaultAttribute.access$002(defaultAttribute2, defaultAttribute);
            DefaultAttribute.access$102(defaultAttribute, defaultAttribute2);
            if (atomicReferenceArray.compareAndSet(n, null, defaultAttribute2)) {
                return defaultAttribute;
            }
            defaultAttribute2 = atomicReferenceArray.get(n);
        }
        defaultAttribute = defaultAttribute2;
        synchronized (defaultAttribute) {
            DefaultAttribute defaultAttribute3 = defaultAttribute2;
            while (true) {
                DefaultAttribute defaultAttribute4;
                if ((defaultAttribute4 = DefaultAttribute.access$000(defaultAttribute3)) == null) {
                    DefaultAttribute<T> defaultAttribute5 = new DefaultAttribute<T>(defaultAttribute2, attributeKey);
                    DefaultAttribute.access$002(defaultAttribute3, defaultAttribute5);
                    DefaultAttribute.access$102(defaultAttribute5, defaultAttribute3);
                    return defaultAttribute5;
                }
                if (DefaultAttribute.access$200(defaultAttribute4) == attributeKey && !DefaultAttribute.access$300(defaultAttribute4)) {
                    return defaultAttribute4;
                }
                defaultAttribute3 = defaultAttribute4;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public <T> boolean hasAttr(AttributeKey<T> attributeKey) {
        if (attributeKey == null) {
            throw new NullPointerException("key");
        }
        AtomicReferenceArray<DefaultAttribute<?>> atomicReferenceArray = this.attributes;
        if (atomicReferenceArray == null) {
            return true;
        }
        int n = DefaultAttributeMap.index(attributeKey);
        DefaultAttribute<?> defaultAttribute = atomicReferenceArray.get(n);
        if (defaultAttribute == null) {
            return true;
        }
        DefaultAttribute<?> defaultAttribute2 = defaultAttribute;
        synchronized (defaultAttribute2) {
            DefaultAttribute defaultAttribute3 = DefaultAttribute.access$000(defaultAttribute);
            while (defaultAttribute3 != null) {
                if (DefaultAttribute.access$200(defaultAttribute3) == attributeKey && !DefaultAttribute.access$300(defaultAttribute3)) {
                    return true;
                }
                defaultAttribute3 = DefaultAttribute.access$000(defaultAttribute3);
            }
            return false;
        }
    }

    private static int index(AttributeKey<?> attributeKey) {
        return attributeKey.id() & 3;
    }

    private static final class DefaultAttribute<T>
    extends AtomicReference<T>
    implements Attribute<T> {
        private static final long serialVersionUID = -2661411462200283011L;
        private final DefaultAttribute<?> head;
        private final AttributeKey<T> key;
        private DefaultAttribute<?> prev;
        private DefaultAttribute<?> next;
        private volatile boolean removed;

        DefaultAttribute(DefaultAttribute<?> defaultAttribute, AttributeKey<T> attributeKey) {
            this.head = defaultAttribute;
            this.key = attributeKey;
        }

        DefaultAttribute() {
            this.head = this;
            this.key = null;
        }

        @Override
        public AttributeKey<T> key() {
            return this.key;
        }

        @Override
        public T setIfAbsent(T t) {
            while (!this.compareAndSet(null, t)) {
                Object v = this.get();
                if (v == null) continue;
                return (T)v;
            }
            return null;
        }

        @Override
        public T getAndRemove() {
            this.removed = true;
            T t = this.getAndSet(null);
            this.remove0();
            return t;
        }

        @Override
        public void remove() {
            this.removed = true;
            this.set(null);
            this.remove0();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void remove0() {
            DefaultAttribute<?> defaultAttribute = this.head;
            synchronized (defaultAttribute) {
                if (this.prev == null) {
                    return;
                }
                this.prev.next = this.next;
                if (this.next != null) {
                    this.next.prev = this.prev;
                }
                this.prev = null;
                this.next = null;
            }
        }

        static DefaultAttribute access$002(DefaultAttribute defaultAttribute, DefaultAttribute defaultAttribute2) {
            defaultAttribute.next = defaultAttribute2;
            return defaultAttribute.next;
        }

        static DefaultAttribute access$102(DefaultAttribute defaultAttribute, DefaultAttribute defaultAttribute2) {
            defaultAttribute.prev = defaultAttribute2;
            return defaultAttribute.prev;
        }

        static DefaultAttribute access$000(DefaultAttribute defaultAttribute) {
            return defaultAttribute.next;
        }

        static AttributeKey access$200(DefaultAttribute defaultAttribute) {
            return defaultAttribute.key;
        }

        static boolean access$300(DefaultAttribute defaultAttribute) {
            return defaultAttribute.removed;
        }
    }
}

