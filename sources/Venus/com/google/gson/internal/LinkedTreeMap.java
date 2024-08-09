/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.gson.internal;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

public final class LinkedTreeMap<K, V>
extends AbstractMap<K, V>
implements Serializable {
    private static final Comparator<Comparable> NATURAL_ORDER;
    private final Comparator<? super K> comparator;
    private final boolean allowNullValues;
    Node<K, V> root;
    int size = 0;
    int modCount = 0;
    final Node<K, V> header;
    private EntrySet entrySet;
    private KeySet keySet;
    static final boolean $assertionsDisabled;

    public LinkedTreeMap() {
        this(NATURAL_ORDER, true);
    }

    public LinkedTreeMap(boolean bl) {
        this(NATURAL_ORDER, bl);
    }

    public LinkedTreeMap(Comparator<? super K> comparator, boolean bl) {
        this.comparator = comparator != null ? comparator : NATURAL_ORDER;
        this.allowNullValues = bl;
        this.header = new Node(bl);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public V get(Object object) {
        Node<K, V> node = this.findByObject(object);
        return node != null ? (V)node.value : null;
    }

    @Override
    public boolean containsKey(Object object) {
        return this.findByObject(object) != null;
    }

    @Override
    public V put(K k, V v) {
        if (k == null) {
            throw new NullPointerException("key == null");
        }
        if (v == null && !this.allowNullValues) {
            throw new NullPointerException("value == null");
        }
        Node<K, V> node = this.find(k, false);
        Object v2 = node.value;
        node.value = v;
        return v2;
    }

    @Override
    public void clear() {
        this.root = null;
        this.size = 0;
        ++this.modCount;
        Node<K, V> node = this.header;
        node.prev = node;
        node.next = node.prev;
    }

    @Override
    public V remove(Object object) {
        Node<K, V> node = this.removeInternalByKey(object);
        return node != null ? (V)node.value : null;
    }

    Node<K, V> find(K k, boolean bl) {
        Node<K, V> node;
        Object object;
        Comparator<K> comparator = this.comparator;
        Node<K, V> node2 = this.root;
        int n = 0;
        if (node2 != null) {
            object = comparator == NATURAL_ORDER ? (Comparable)k : null;
            while (true) {
                int n2 = n = object != null ? object.compareTo(node2.key) : comparator.compare(k, node2.key);
                if (n == 0) {
                    return node2;
                }
                Node node3 = node = n < 0 ? node2.left : node2.right;
                if (node == null) break;
                node2 = node;
            }
        }
        if (!bl) {
            return null;
        }
        object = this.header;
        if (node2 == null) {
            if (comparator == NATURAL_ORDER && !(k instanceof Comparable)) {
                throw new ClassCastException(k.getClass().getName() + " is not Comparable");
            }
            node = new Node<K, V>(this.allowNullValues, node2, k, object, ((Node)object).prev);
            this.root = node;
        } else {
            node = new Node<K, V>(this.allowNullValues, node2, k, object, ((Node)object).prev);
            if (n < 0) {
                node2.left = node;
            } else {
                node2.right = node;
            }
            this.rebalance(node2, true);
        }
        ++this.size;
        ++this.modCount;
        return node;
    }

    Node<K, V> findByObject(Object object) {
        try {
            return object != null ? this.find(object, true) : null;
        } catch (ClassCastException classCastException) {
            return null;
        }
    }

    Node<K, V> findByEntry(Map.Entry<?, ?> entry) {
        Node<K, V> node = this.findByObject(entry.getKey());
        boolean bl = node != null && this.equal(node.value, entry.getValue());
        return bl ? node : null;
    }

    private boolean equal(Object object, Object object2) {
        return Objects.equals(object, object2);
    }

    void removeInternal(Node<K, V> node, boolean bl) {
        if (bl) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        Node node2 = node.left;
        Node node3 = node.right;
        Node node4 = node.parent;
        if (node2 != null && node3 != null) {
            Node node5 = node2.height > node3.height ? node2.last() : node3.first();
            this.removeInternal(node5, true);
            int n = 0;
            node2 = node.left;
            if (node2 != null) {
                n = node2.height;
                node5.left = node2;
                node2.parent = node5;
                node.left = null;
            }
            int n2 = 0;
            node3 = node.right;
            if (node3 != null) {
                n2 = node3.height;
                node5.right = node3;
                node3.parent = node5;
                node.right = null;
            }
            node5.height = Math.max(n, n2) + 1;
            this.replaceInParent(node, node5);
            return;
        }
        if (node2 != null) {
            this.replaceInParent(node, node2);
            node.left = null;
        } else if (node3 != null) {
            this.replaceInParent(node, node3);
            node.right = null;
        } else {
            this.replaceInParent(node, null);
        }
        this.rebalance(node4, false);
        --this.size;
        ++this.modCount;
    }

    Node<K, V> removeInternalByKey(Object object) {
        Node<K, V> node = this.findByObject(object);
        if (node != null) {
            this.removeInternal(node, false);
        }
        return node;
    }

    private void replaceInParent(Node<K, V> node, Node<K, V> node2) {
        Node node3 = node.parent;
        node.parent = null;
        if (node2 != null) {
            node2.parent = node3;
        }
        if (node3 != null) {
            if (node3.left == node) {
                node3.left = node2;
            } else {
                if (!$assertionsDisabled && node3.right != node) {
                    throw new AssertionError();
                }
                node3.right = node2;
            }
        } else {
            this.root = node2;
        }
    }

    private void rebalance(Node<K, V> node, boolean bl) {
        Node<K, V> node2 = node;
        while (node2 != null) {
            int n;
            Node node3;
            int n2;
            int n3;
            Node node4;
            Node node5;
            int n4;
            Node node6 = node2.left;
            int n5 = node6 != null ? node6.height : 0;
            int n6 = n5 - (n4 = (node5 = node2.right) != null ? node5.height : 0);
            if (n6 == -2) {
                node4 = node5.left;
                n3 = node4 != null ? node4.height : 0;
                n = n3 - (n2 = (node3 = node5.right) != null ? node3.height : 0);
                if (n == -1 || n == 0 && !bl) {
                    this.rotateLeft(node2);
                } else {
                    if (!$assertionsDisabled && n != 1) {
                        throw new AssertionError();
                    }
                    this.rotateRight(node5);
                    this.rotateLeft(node2);
                }
                if (bl) {
                    break;
                }
            } else if (n6 == 2) {
                node4 = node6.left;
                n3 = node4 != null ? node4.height : 0;
                n = n3 - (n2 = (node3 = node6.right) != null ? node3.height : 0);
                if (n == 1 || n == 0 && !bl) {
                    this.rotateRight(node2);
                } else {
                    if (!$assertionsDisabled && n != -1) {
                        throw new AssertionError();
                    }
                    this.rotateLeft(node6);
                    this.rotateRight(node2);
                }
                if (bl) {
                    break;
                }
            } else if (n6 == 0) {
                node2.height = n5 + 1;
                if (bl) {
                    break;
                }
            } else {
                if (!$assertionsDisabled && n6 != -1 && n6 != 1) {
                    throw new AssertionError();
                }
                node2.height = Math.max(n5, n4) + 1;
                if (!bl) break;
            }
            node2 = node2.parent;
        }
    }

    private void rotateLeft(Node<K, V> node) {
        Node node2 = node.left;
        Node node3 = node.right;
        Node node4 = node3.left;
        Node node5 = node3.right;
        node.right = node4;
        if (node4 != null) {
            node4.parent = node;
        }
        this.replaceInParent(node, node3);
        node3.left = node;
        node.parent = node3;
        node.height = Math.max(node2 != null ? node2.height : 0, node4 != null ? node4.height : 0) + 1;
        node3.height = Math.max(node.height, node5 != null ? node5.height : 0) + 1;
    }

    private void rotateRight(Node<K, V> node) {
        Node node2 = node.left;
        Node node3 = node.right;
        Node node4 = node2.left;
        Node node5 = node2.right;
        node.left = node5;
        if (node5 != null) {
            node5.parent = node;
        }
        this.replaceInParent(node, node2);
        node2.right = node;
        node.parent = node2;
        node.height = Math.max(node3 != null ? node3.height : 0, node5 != null ? node5.height : 0) + 1;
        node2.height = Math.max(node.height, node4 != null ? node4.height : 0) + 1;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        EntrySet entrySet = this.entrySet;
        return entrySet != null ? entrySet : (this.entrySet = new EntrySet(this));
    }

    @Override
    public Set<K> keySet() {
        KeySet keySet = this.keySet;
        return keySet != null ? keySet : (this.keySet = new KeySet(this));
    }

    private Object writeReplace() throws ObjectStreamException {
        return new LinkedHashMap(this);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException {
        throw new InvalidObjectException("Deserialization is unsupported");
    }

    static {
        $assertionsDisabled = !LinkedTreeMap.class.desiredAssertionStatus();
        NATURAL_ORDER = new Comparator<Comparable>(){

            @Override
            public int compare(Comparable comparable, Comparable comparable2) {
                return comparable.compareTo(comparable2);
            }

            @Override
            public int compare(Object object, Object object2) {
                return this.compare((Comparable)object, (Comparable)object2);
            }
        };
    }

    final class KeySet
    extends AbstractSet<K> {
        final LinkedTreeMap this$0;

        KeySet(LinkedTreeMap linkedTreeMap) {
            this.this$0 = linkedTreeMap;
        }

        @Override
        public int size() {
            return this.this$0.size;
        }

        @Override
        public Iterator<K> iterator() {
            return new LinkedTreeMapIterator<K>(this){
                final KeySet this$1;
                {
                    this.this$1 = keySet;
                    super(keySet.this$0);
                }

                @Override
                public K next() {
                    return this.nextNode().key;
                }
            };
        }

        @Override
        public boolean contains(Object object) {
            return this.this$0.containsKey(object);
        }

        @Override
        public boolean remove(Object object) {
            return this.this$0.removeInternalByKey(object) != null;
        }

        @Override
        public void clear() {
            this.this$0.clear();
        }
    }

    class EntrySet
    extends AbstractSet<Map.Entry<K, V>> {
        final LinkedTreeMap this$0;

        EntrySet(LinkedTreeMap linkedTreeMap) {
            this.this$0 = linkedTreeMap;
        }

        @Override
        public int size() {
            return this.this$0.size;
        }

        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return new LinkedTreeMapIterator<Map.Entry<K, V>>(this){
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    super(entrySet.this$0);
                }

                @Override
                public Map.Entry<K, V> next() {
                    return this.nextNode();
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public boolean contains(Object object) {
            return object instanceof Map.Entry && this.this$0.findByEntry((Map.Entry)object) != null;
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Node node = this.this$0.findByEntry((Map.Entry)object);
            if (node == null) {
                return true;
            }
            this.this$0.removeInternal(node, false);
            return false;
        }

        @Override
        public void clear() {
            this.this$0.clear();
        }
    }

    private abstract class LinkedTreeMapIterator<T>
    implements Iterator<T> {
        Node<K, V> next;
        Node<K, V> lastReturned;
        int expectedModCount;
        final LinkedTreeMap this$0;

        LinkedTreeMapIterator(LinkedTreeMap linkedTreeMap) {
            this.this$0 = linkedTreeMap;
            this.next = this.this$0.header.next;
            this.lastReturned = null;
            this.expectedModCount = this.this$0.modCount;
        }

        @Override
        public final boolean hasNext() {
            return this.next != this.this$0.header;
        }

        final Node<K, V> nextNode() {
            Node node = this.next;
            if (node == this.this$0.header) {
                throw new NoSuchElementException();
            }
            if (this.this$0.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
            this.next = node.next;
            this.lastReturned = node;
            return this.lastReturned;
        }

        @Override
        public final void remove() {
            if (this.lastReturned == null) {
                throw new IllegalStateException();
            }
            this.this$0.removeInternal(this.lastReturned, false);
            this.lastReturned = null;
            this.expectedModCount = this.this$0.modCount;
        }
    }

    static final class Node<K, V>
    implements Map.Entry<K, V> {
        Node<K, V> parent;
        Node<K, V> left;
        Node<K, V> right;
        Node<K, V> next;
        Node<K, V> prev;
        final K key;
        final boolean allowNullValue;
        V value;
        int height;

        Node(boolean bl) {
            this.key = null;
            this.allowNullValue = bl;
            this.next = this.prev = this;
        }

        Node(boolean bl, Node<K, V> node, K k, Node<K, V> node2, Node<K, V> node3) {
            this.parent = node;
            this.key = k;
            this.allowNullValue = bl;
            this.height = 1;
            this.next = node2;
            this.prev = node3;
            node3.next = this;
            node2.prev = this;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public V setValue(V v) {
            if (v == null && !this.allowNullValue) {
                throw new NullPointerException("value == null");
            }
            V v2 = this.value;
            this.value = v;
            return v2;
        }

        @Override
        public boolean equals(Object object) {
            if (object instanceof Map.Entry) {
                Map.Entry entry = (Map.Entry)object;
                return (this.key == null ? entry.getKey() == null : this.key.equals(entry.getKey())) && (this.value == null ? entry.getValue() == null : this.value.equals(entry.getValue()));
            }
            return true;
        }

        @Override
        public int hashCode() {
            return (this.key == null ? 0 : this.key.hashCode()) ^ (this.value == null ? 0 : this.value.hashCode());
        }

        public String toString() {
            return this.key + "=" + this.value;
        }

        public Node<K, V> first() {
            Node<K, V> node = this;
            Node<K, V> node2 = node.left;
            while (node2 != null) {
                node = node2;
                node2 = node.left;
            }
            return node;
        }

        public Node<K, V> last() {
            Node<K, V> node = this;
            Node<K, V> node2 = node.right;
            while (node2 != null) {
                node = node2;
                node2 = node.right;
            }
            return node;
        }
    }
}

