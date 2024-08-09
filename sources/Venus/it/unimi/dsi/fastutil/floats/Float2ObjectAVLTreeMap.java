/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.floats.AbstractFloat2ObjectMap;
import it.unimi.dsi.fastutil.floats.AbstractFloat2ObjectSortedMap;
import it.unimi.dsi.fastutil.floats.Float2ObjectMap;
import it.unimi.dsi.fastutil.floats.Float2ObjectSortedMap;
import it.unimi.dsi.fastutil.floats.FloatBidirectionalIterator;
import it.unimi.dsi.fastutil.floats.FloatComparator;
import it.unimi.dsi.fastutil.floats.FloatComparators;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.floats.FloatListIterator;
import it.unimi.dsi.fastutil.floats.FloatSet;
import it.unimi.dsi.fastutil.floats.FloatSortedSet;
import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Float2ObjectAVLTreeMap<V>
extends AbstractFloat2ObjectSortedMap<V>
implements Serializable,
Cloneable {
    protected transient Entry<V> tree;
    protected int count;
    protected transient Entry<V> firstEntry;
    protected transient Entry<V> lastEntry;
    protected transient ObjectSortedSet<Float2ObjectMap.Entry<V>> entries;
    protected transient FloatSortedSet keys;
    protected transient ObjectCollection<V> values;
    protected transient boolean modified;
    protected Comparator<? super Float> storedComparator;
    protected transient FloatComparator actualComparator;
    private static final long serialVersionUID = -7046029254386353129L;
    private transient boolean[] dirPath;
    static final boolean $assertionsDisabled = !Float2ObjectAVLTreeMap.class.desiredAssertionStatus();

    public Float2ObjectAVLTreeMap() {
        this.allocatePaths();
        this.tree = null;
        this.count = 0;
    }

    private void setActualComparator() {
        this.actualComparator = FloatComparators.asFloatComparator(this.storedComparator);
    }

    public Float2ObjectAVLTreeMap(Comparator<? super Float> comparator) {
        this();
        this.storedComparator = comparator;
    }

    public Float2ObjectAVLTreeMap(Map<? extends Float, ? extends V> map) {
        this();
        this.putAll((Map<Float, ? extends V>)map);
    }

    public Float2ObjectAVLTreeMap(SortedMap<Float, V> sortedMap) {
        this(sortedMap.comparator());
        this.putAll((Map<Float, V>)sortedMap);
    }

    public Float2ObjectAVLTreeMap(Float2ObjectMap<? extends V> float2ObjectMap) {
        this();
        this.putAll(float2ObjectMap);
    }

    public Float2ObjectAVLTreeMap(Float2ObjectSortedMap<V> float2ObjectSortedMap) {
        this(float2ObjectSortedMap.comparator());
        this.putAll(float2ObjectSortedMap);
    }

    public Float2ObjectAVLTreeMap(float[] fArray, V[] VArray, Comparator<? super Float> comparator) {
        this(comparator);
        if (fArray.length != VArray.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + fArray.length + " and " + VArray.length + ")");
        }
        for (int i = 0; i < fArray.length; ++i) {
            this.put(fArray[i], VArray[i]);
        }
    }

    public Float2ObjectAVLTreeMap(float[] fArray, V[] VArray) {
        this(fArray, VArray, null);
    }

    final int compare(float f, float f2) {
        return this.actualComparator == null ? Float.compare(f, f2) : this.actualComparator.compare(f, f2);
    }

    final Entry<V> findKey(float f) {
        int n;
        Entry<V> entry = this.tree;
        while (entry != null && (n = this.compare(f, entry.key)) != 0) {
            entry = n < 0 ? entry.left() : entry.right();
        }
        return entry;
    }

    final Entry<V> locateKey(float f) {
        Entry<V> entry = this.tree;
        Entry<V> entry2 = this.tree;
        int n = 0;
        while (entry != null && (n = this.compare(f, entry.key)) != 0) {
            entry2 = entry;
            entry = n < 0 ? entry.left() : entry.right();
        }
        return n == 0 ? entry : entry2;
    }

    private void allocatePaths() {
        this.dirPath = new boolean[48];
    }

    @Override
    public V put(float f, V v) {
        Entry<V> entry = this.add(f);
        Object object = entry.value;
        entry.value = v;
        return (V)object;
    }

    private Entry<V> add(float f) {
        this.modified = false;
        Entry<Object> entry = null;
        if (this.tree == null) {
            ++this.count;
            this.firstEntry = new Entry<Object>(f, this.defRetValue);
            this.lastEntry = this.firstEntry;
            this.tree = this.firstEntry;
            entry = this.firstEntry;
            this.modified = true;
        } else {
            Entry<Object> entry2 = this.tree;
            Entry<Object> entry3 = null;
            Entry entry4 = this.tree;
            Entry<Object> entry5 = null;
            Entry entry6 = null;
            int n = 0;
            while (true) {
                int n2;
                if ((n2 = this.compare(f, entry2.key)) == 0) {
                    return entry2;
                }
                if (entry2.balance() != 0) {
                    n = 0;
                    entry5 = entry3;
                    entry4 = entry2;
                }
                if (this.dirPath[n++] = n2 > 0) {
                    if (entry2.succ()) {
                        ++this.count;
                        entry = new Entry<Object>(f, this.defRetValue);
                        this.modified = true;
                        if (entry2.right == null) {
                            this.lastEntry = entry;
                        }
                        entry.left = entry2;
                        entry.right = entry2.right;
                        entry2.right(entry);
                        break;
                    }
                    entry3 = entry2;
                    entry2 = entry2.right;
                    continue;
                }
                if (entry2.pred()) {
                    ++this.count;
                    entry = new Entry<Object>(f, this.defRetValue);
                    this.modified = true;
                    if (entry2.left == null) {
                        this.firstEntry = entry;
                    }
                    entry.right = entry2;
                    entry.left = entry2.left;
                    entry2.left(entry);
                    break;
                }
                entry3 = entry2;
                entry2 = entry2.left;
            }
            entry2 = entry4;
            n = 0;
            while (entry2 != entry) {
                if (this.dirPath[n]) {
                    entry2.incBalance();
                } else {
                    entry2.decBalance();
                }
                entry2 = this.dirPath[n++] ? entry2.right : entry2.left;
            }
            if (entry4.balance() == -2) {
                Entry entry7 = entry4.left;
                if (entry7.balance() == -1) {
                    entry6 = entry7;
                    if (entry7.succ()) {
                        entry7.succ(true);
                        entry4.pred(entry7);
                    } else {
                        entry4.left = entry7.right;
                    }
                    entry7.right = entry4;
                    entry7.balance(0);
                    entry4.balance(0);
                } else {
                    if (!$assertionsDisabled && entry7.balance() != 1) {
                        throw new AssertionError();
                    }
                    entry6 = entry7.right;
                    entry7.right = entry6.left;
                    entry6.left = entry7;
                    entry4.left = entry6.right;
                    entry6.right = entry4;
                    if (entry6.balance() == -1) {
                        entry7.balance(0);
                        entry4.balance(1);
                    } else if (entry6.balance() == 0) {
                        entry7.balance(0);
                        entry4.balance(0);
                    } else {
                        entry7.balance(-1);
                        entry4.balance(0);
                    }
                    entry6.balance(0);
                    if (entry6.pred()) {
                        entry7.succ(entry6);
                        entry6.pred(true);
                    }
                    if (entry6.succ()) {
                        entry4.pred(entry6);
                        entry6.succ(true);
                    }
                }
            } else if (entry4.balance() == 2) {
                Entry entry8 = entry4.right;
                if (entry8.balance() == 1) {
                    entry6 = entry8;
                    if (entry8.pred()) {
                        entry8.pred(true);
                        entry4.succ(entry8);
                    } else {
                        entry4.right = entry8.left;
                    }
                    entry8.left = entry4;
                    entry8.balance(0);
                    entry4.balance(0);
                } else {
                    if (!$assertionsDisabled && entry8.balance() != -1) {
                        throw new AssertionError();
                    }
                    entry6 = entry8.left;
                    entry8.left = entry6.right;
                    entry6.right = entry8;
                    entry4.right = entry6.left;
                    entry6.left = entry4;
                    if (entry6.balance() == 1) {
                        entry8.balance(0);
                        entry4.balance(-1);
                    } else if (entry6.balance() == 0) {
                        entry8.balance(0);
                        entry4.balance(0);
                    } else {
                        entry8.balance(1);
                        entry4.balance(0);
                    }
                    entry6.balance(0);
                    if (entry6.pred()) {
                        entry4.succ(entry6);
                        entry6.pred(true);
                    }
                    if (entry6.succ()) {
                        entry8.pred(entry6);
                        entry6.succ(true);
                    }
                }
            } else {
                return entry;
            }
            if (entry5 == null) {
                this.tree = entry6;
            } else if (entry5.left == entry4) {
                entry5.left = entry6;
            } else {
                entry5.right = entry6;
            }
        }
        return entry;
    }

    private Entry<V> parent(Entry<V> entry) {
        Entry<V> entry2;
        if (entry == this.tree) {
            return null;
        }
        Entry<V> entry3 = entry2 = entry;
        while (true) {
            if (entry2.succ()) {
                Entry entry4 = entry2.right;
                if (entry4 == null || entry4.left != entry) {
                    while (!entry3.pred()) {
                        entry3 = entry3.left;
                    }
                    entry4 = entry3.left;
                }
                return entry4;
            }
            if (entry3.pred()) {
                Entry entry5 = entry3.left;
                if (entry5 == null || entry5.right != entry) {
                    while (!entry2.succ()) {
                        entry2 = entry2.right;
                    }
                    entry5 = entry2.right;
                }
                return entry5;
            }
            entry3 = entry3.left;
            entry2 = entry2.right;
        }
    }

    @Override
    public V remove(float f) {
        Entry entry;
        Entry entry2;
        int n;
        this.modified = false;
        if (this.tree == null) {
            return (V)this.defRetValue;
        }
        Entry entry3 = this.tree;
        Entry entry4 = null;
        boolean bl = false;
        float f2 = f;
        while ((n = this.compare(f2, entry3.key)) != 0) {
            bl = n > 0;
            if (bl) {
                entry4 = entry3;
                if ((entry3 = entry3.right()) != null) continue;
                return (V)this.defRetValue;
            }
            entry4 = entry3;
            if ((entry3 = entry3.left()) != null) continue;
            return (V)this.defRetValue;
        }
        if (entry3.left == null) {
            this.firstEntry = entry3.next();
        }
        if (entry3.right == null) {
            this.lastEntry = entry3.prev();
        }
        if (entry3.succ()) {
            if (entry3.pred()) {
                if (entry4 != null) {
                    if (bl) {
                        entry4.succ(entry3.right);
                    } else {
                        entry4.pred(entry3.left);
                    }
                } else {
                    this.tree = bl ? entry3.right : entry3.left;
                }
            } else {
                entry3.prev().right = entry3.right;
                if (entry4 != null) {
                    if (bl) {
                        entry4.right = entry3.left;
                    } else {
                        entry4.left = entry3.left;
                    }
                } else {
                    this.tree = entry3.left;
                }
            }
        } else {
            entry2 = entry3.right;
            if (entry2.pred()) {
                entry2.left = entry3.left;
                entry2.pred(entry3.pred());
                if (!entry2.pred()) {
                    entry2.prev().right = entry2;
                }
                if (entry4 != null) {
                    if (bl) {
                        entry4.right = entry2;
                    } else {
                        entry4.left = entry2;
                    }
                } else {
                    this.tree = entry2;
                }
                entry2.balance(entry3.balance());
                entry4 = entry2;
                bl = true;
            } else {
                while (!(entry = entry2.left).pred()) {
                    entry2 = entry;
                }
                if (entry.succ()) {
                    entry2.pred(entry);
                } else {
                    entry2.left = entry.right;
                }
                entry.left = entry3.left;
                if (!entry3.pred()) {
                    entry3.prev().right = entry;
                    entry.pred(true);
                }
                entry.right = entry3.right;
                entry.succ(true);
                if (entry4 != null) {
                    if (bl) {
                        entry4.right = entry;
                    } else {
                        entry4.left = entry;
                    }
                } else {
                    this.tree = entry;
                }
                entry.balance(entry3.balance());
                entry4 = entry2;
                bl = false;
            }
        }
        while (entry4 != null) {
            Entry entry5;
            entry2 = entry4;
            entry4 = this.parent(entry2);
            if (!bl) {
                bl = entry4 != null && entry4.left != entry2;
                entry2.incBalance();
                if (entry2.balance() == 1) break;
                if (entry2.balance() != 2) continue;
                entry = entry2.right;
                if (!$assertionsDisabled && entry == null) {
                    throw new AssertionError();
                }
                if (entry.balance() == -1) {
                    if (!$assertionsDisabled && entry.balance() != -1) {
                        throw new AssertionError();
                    }
                    entry5 = entry.left;
                    entry.left = entry5.right;
                    entry5.right = entry;
                    entry2.right = entry5.left;
                    entry5.left = entry2;
                    if (entry5.balance() == 1) {
                        entry.balance(0);
                        entry2.balance(-1);
                    } else if (entry5.balance() == 0) {
                        entry.balance(0);
                        entry2.balance(0);
                    } else {
                        if (!$assertionsDisabled && entry5.balance() != -1) {
                            throw new AssertionError();
                        }
                        entry.balance(1);
                        entry2.balance(0);
                    }
                    entry5.balance(0);
                    if (entry5.pred()) {
                        entry2.succ(entry5);
                        entry5.pred(true);
                    }
                    if (entry5.succ()) {
                        entry.pred(entry5);
                        entry5.succ(true);
                    }
                    if (entry4 != null) {
                        if (bl) {
                            entry4.right = entry5;
                            continue;
                        }
                        entry4.left = entry5;
                        continue;
                    }
                    this.tree = entry5;
                    continue;
                }
                if (entry4 != null) {
                    if (bl) {
                        entry4.right = entry;
                    } else {
                        entry4.left = entry;
                    }
                } else {
                    this.tree = entry;
                }
                if (entry.balance() == 0) {
                    entry2.right = entry.left;
                    entry.left = entry2;
                    entry.balance(-1);
                    entry2.balance(1);
                    break;
                }
                if (!$assertionsDisabled && entry.balance() != 1) {
                    throw new AssertionError();
                }
                if (entry.pred()) {
                    entry2.succ(false);
                    entry.pred(true);
                } else {
                    entry2.right = entry.left;
                }
                entry.left = entry2;
                entry2.balance(0);
                entry.balance(0);
                continue;
            }
            bl = entry4 != null && entry4.left != entry2;
            entry2.decBalance();
            if (entry2.balance() == -1) break;
            if (entry2.balance() != -2) continue;
            entry = entry2.left;
            if (!$assertionsDisabled && entry == null) {
                throw new AssertionError();
            }
            if (entry.balance() == 1) {
                if (!$assertionsDisabled && entry.balance() != 1) {
                    throw new AssertionError();
                }
                entry5 = entry.right;
                entry.right = entry5.left;
                entry5.left = entry;
                entry2.left = entry5.right;
                entry5.right = entry2;
                if (entry5.balance() == -1) {
                    entry.balance(0);
                    entry2.balance(1);
                } else if (entry5.balance() == 0) {
                    entry.balance(0);
                    entry2.balance(0);
                } else {
                    if (!$assertionsDisabled && entry5.balance() != 1) {
                        throw new AssertionError();
                    }
                    entry.balance(-1);
                    entry2.balance(0);
                }
                entry5.balance(0);
                if (entry5.pred()) {
                    entry.succ(entry5);
                    entry5.pred(true);
                }
                if (entry5.succ()) {
                    entry2.pred(entry5);
                    entry5.succ(true);
                }
                if (entry4 != null) {
                    if (bl) {
                        entry4.right = entry5;
                        continue;
                    }
                    entry4.left = entry5;
                    continue;
                }
                this.tree = entry5;
                continue;
            }
            if (entry4 != null) {
                if (bl) {
                    entry4.right = entry;
                } else {
                    entry4.left = entry;
                }
            } else {
                this.tree = entry;
            }
            if (entry.balance() == 0) {
                entry2.left = entry.right;
                entry.right = entry2;
                entry.balance(1);
                entry2.balance(-1);
                break;
            }
            if (!$assertionsDisabled && entry.balance() != -1) {
                throw new AssertionError();
            }
            if (entry.succ()) {
                entry2.pred(false);
                entry.succ(true);
            } else {
                entry2.left = entry.right;
            }
            entry.right = entry2;
            entry2.balance(0);
            entry.balance(0);
        }
        this.modified = true;
        --this.count;
        return (V)entry3.value;
    }

    @Override
    public boolean containsValue(Object object) {
        ValueIterator valueIterator = new ValueIterator(this, null);
        int n = this.count;
        while (n-- != 0) {
            Object v = valueIterator.next();
            if (!Objects.equals(v, object)) continue;
            return false;
        }
        return true;
    }

    @Override
    public void clear() {
        this.count = 0;
        this.tree = null;
        this.entries = null;
        this.values = null;
        this.keys = null;
        this.lastEntry = null;
        this.firstEntry = null;
    }

    @Override
    public boolean containsKey(float f) {
        return this.findKey(f) != null;
    }

    @Override
    public int size() {
        return this.count;
    }

    @Override
    public boolean isEmpty() {
        return this.count == 0;
    }

    @Override
    public V get(float f) {
        Entry<V> entry = this.findKey(f);
        return (V)(entry == null ? this.defRetValue : entry.value);
    }

    @Override
    public float firstFloatKey() {
        if (this.tree == null) {
            throw new NoSuchElementException();
        }
        return this.firstEntry.key;
    }

    @Override
    public float lastFloatKey() {
        if (this.tree == null) {
            throw new NoSuchElementException();
        }
        return this.lastEntry.key;
    }

    @Override
    public ObjectSortedSet<Float2ObjectMap.Entry<V>> float2ObjectEntrySet() {
        if (this.entries == null) {
            this.entries = new AbstractObjectSortedSet<Float2ObjectMap.Entry<V>>(this){
                final Comparator<? super Float2ObjectMap.Entry<V>> comparator;
                final Float2ObjectAVLTreeMap this$0;
                {
                    this.this$0 = float2ObjectAVLTreeMap;
                    this.comparator = this::lambda$$0;
                }

                @Override
                public Comparator<? super Float2ObjectMap.Entry<V>> comparator() {
                    return this.comparator;
                }

                @Override
                public ObjectBidirectionalIterator<Float2ObjectMap.Entry<V>> iterator() {
                    return new EntryIterator(this.this$0);
                }

                @Override
                public ObjectBidirectionalIterator<Float2ObjectMap.Entry<V>> iterator(Float2ObjectMap.Entry<V> entry) {
                    return new EntryIterator(this.this$0, entry.getFloatKey());
                }

                @Override
                public boolean contains(Object object) {
                    if (!(object instanceof Map.Entry)) {
                        return true;
                    }
                    Map.Entry entry = (Map.Entry)object;
                    if (entry.getKey() == null || !(entry.getKey() instanceof Float)) {
                        return true;
                    }
                    Entry entry2 = this.this$0.findKey(((Float)entry.getKey()).floatValue());
                    return entry.equals(entry2);
                }

                @Override
                public boolean remove(Object object) {
                    if (!(object instanceof Map.Entry)) {
                        return true;
                    }
                    Map.Entry entry = (Map.Entry)object;
                    if (entry.getKey() == null || !(entry.getKey() instanceof Float)) {
                        return true;
                    }
                    Entry entry2 = this.this$0.findKey(((Float)entry.getKey()).floatValue());
                    if (entry2 == null || !Objects.equals(entry2.getValue(), entry.getValue())) {
                        return true;
                    }
                    this.this$0.remove(entry2.key);
                    return false;
                }

                @Override
                public int size() {
                    return this.this$0.count;
                }

                @Override
                public void clear() {
                    this.this$0.clear();
                }

                @Override
                public Float2ObjectMap.Entry<V> first() {
                    return this.this$0.firstEntry;
                }

                @Override
                public Float2ObjectMap.Entry<V> last() {
                    return this.this$0.lastEntry;
                }

                @Override
                public ObjectSortedSet<Float2ObjectMap.Entry<V>> subSet(Float2ObjectMap.Entry<V> entry, Float2ObjectMap.Entry<V> entry2) {
                    return this.this$0.subMap(entry.getFloatKey(), entry2.getFloatKey()).float2ObjectEntrySet();
                }

                @Override
                public ObjectSortedSet<Float2ObjectMap.Entry<V>> headSet(Float2ObjectMap.Entry<V> entry) {
                    return this.this$0.headMap(entry.getFloatKey()).float2ObjectEntrySet();
                }

                @Override
                public ObjectSortedSet<Float2ObjectMap.Entry<V>> tailSet(Float2ObjectMap.Entry<V> entry) {
                    return this.this$0.tailMap(entry.getFloatKey()).float2ObjectEntrySet();
                }

                @Override
                public ObjectSortedSet tailSet(Object object) {
                    return this.tailSet((Float2ObjectMap.Entry)object);
                }

                @Override
                public ObjectSortedSet headSet(Object object) {
                    return this.headSet((Float2ObjectMap.Entry)object);
                }

                @Override
                public ObjectSortedSet subSet(Object object, Object object2) {
                    return this.subSet((Float2ObjectMap.Entry)object, (Float2ObjectMap.Entry)object2);
                }

                @Override
                public ObjectBidirectionalIterator iterator(Object object) {
                    return this.iterator((Float2ObjectMap.Entry)object);
                }

                @Override
                public ObjectIterator iterator() {
                    return this.iterator();
                }

                @Override
                public Iterator iterator() {
                    return this.iterator();
                }

                @Override
                public Object last() {
                    return this.last();
                }

                @Override
                public Object first() {
                    return this.first();
                }

                @Override
                public SortedSet tailSet(Object object) {
                    return this.tailSet((Float2ObjectMap.Entry)object);
                }

                @Override
                public SortedSet headSet(Object object) {
                    return this.headSet((Float2ObjectMap.Entry)object);
                }

                @Override
                public SortedSet subSet(Object object, Object object2) {
                    return this.subSet((Float2ObjectMap.Entry)object, (Float2ObjectMap.Entry)object2);
                }

                private int lambda$$0(Float2ObjectMap.Entry entry, Float2ObjectMap.Entry entry2) {
                    return this.this$0.actualComparator.compare(entry.getFloatKey(), entry2.getFloatKey());
                }
            };
        }
        return this.entries;
    }

    @Override
    public FloatSortedSet keySet() {
        if (this.keys == null) {
            this.keys = new KeySet(this, null);
        }
        return this.keys;
    }

    @Override
    public ObjectCollection<V> values() {
        if (this.values == null) {
            this.values = new AbstractObjectCollection<V>(this){
                final Float2ObjectAVLTreeMap this$0;
                {
                    this.this$0 = float2ObjectAVLTreeMap;
                }

                @Override
                public ObjectIterator<V> iterator() {
                    return new ValueIterator(this.this$0, null);
                }

                @Override
                public boolean contains(Object object) {
                    return this.this$0.containsValue(object);
                }

                @Override
                public int size() {
                    return this.this$0.count;
                }

                @Override
                public void clear() {
                    this.this$0.clear();
                }

                @Override
                public Iterator iterator() {
                    return this.iterator();
                }
            };
        }
        return this.values;
    }

    @Override
    public FloatComparator comparator() {
        return this.actualComparator;
    }

    @Override
    public Float2ObjectSortedMap<V> headMap(float f) {
        return new Submap(this, 0.0f, true, f, false);
    }

    @Override
    public Float2ObjectSortedMap<V> tailMap(float f) {
        return new Submap(this, f, false, 0.0f, true);
    }

    @Override
    public Float2ObjectSortedMap<V> subMap(float f, float f2) {
        return new Submap(this, f, false, f2, false);
    }

    public Float2ObjectAVLTreeMap<V> clone() {
        Float2ObjectAVLTreeMap float2ObjectAVLTreeMap;
        try {
            float2ObjectAVLTreeMap = (Float2ObjectAVLTreeMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        float2ObjectAVLTreeMap.keys = null;
        float2ObjectAVLTreeMap.values = null;
        float2ObjectAVLTreeMap.entries = null;
        float2ObjectAVLTreeMap.allocatePaths();
        if (this.count != 0) {
            Entry<V> entry = new Entry<V>();
            Entry entry2 = new Entry();
            Entry<V> entry3 = entry;
            entry.left(this.tree);
            Entry entry4 = entry2;
            entry2.pred(null);
            while (true) {
                Object object;
                if (!entry3.pred()) {
                    object = entry3.left.clone();
                    ((Entry)object).pred(entry4.left);
                    ((Entry)object).succ(entry4);
                    entry4.left(object);
                    entry3 = entry3.left;
                    entry4 = entry4.left;
                } else {
                    while (entry3.succ()) {
                        entry3 = entry3.right;
                        if (entry3 == null) {
                            entry4.right = null;
                            float2ObjectAVLTreeMap.tree = entry2.left;
                            float2ObjectAVLTreeMap.firstEntry = float2ObjectAVLTreeMap.tree;
                            while (float2ObjectAVLTreeMap.firstEntry.left != null) {
                                float2ObjectAVLTreeMap.firstEntry = float2ObjectAVLTreeMap.firstEntry.left;
                            }
                            float2ObjectAVLTreeMap.lastEntry = float2ObjectAVLTreeMap.tree;
                            while (float2ObjectAVLTreeMap.lastEntry.right != null) {
                                float2ObjectAVLTreeMap.lastEntry = float2ObjectAVLTreeMap.lastEntry.right;
                            }
                            return float2ObjectAVLTreeMap;
                        }
                        entry4 = entry4.right;
                    }
                    entry3 = entry3.right;
                    entry4 = entry4.right;
                }
                if (entry3.succ()) continue;
                object = entry3.right.clone();
                ((Entry)object).succ(entry4.right);
                ((Entry)object).pred(entry4);
                entry4.right(object);
            }
        }
        return float2ObjectAVLTreeMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        int n = this.count;
        EntryIterator entryIterator = new EntryIterator(this);
        objectOutputStream.defaultWriteObject();
        while (n-- != 0) {
            Entry entry = entryIterator.nextEntry();
            objectOutputStream.writeFloat(entry.key);
            objectOutputStream.writeObject(entry.value);
        }
    }

    private Entry<V> readTree(ObjectInputStream objectInputStream, int n, Entry<V> entry, Entry<V> entry2) throws IOException, ClassNotFoundException {
        if (n == 1) {
            Entry<Object> entry3 = new Entry<Object>(objectInputStream.readFloat(), objectInputStream.readObject());
            entry3.pred(entry);
            entry3.succ(entry2);
            return entry3;
        }
        if (n == 2) {
            Entry<Object> entry4 = new Entry<Object>(objectInputStream.readFloat(), objectInputStream.readObject());
            entry4.right(new Entry<Object>(objectInputStream.readFloat(), objectInputStream.readObject()));
            entry4.right.pred(entry4);
            entry4.balance(1);
            entry4.pred(entry);
            entry4.right.succ(entry2);
            return entry4;
        }
        int n2 = n / 2;
        int n3 = n - n2 - 1;
        Entry entry5 = new Entry();
        entry5.left(this.readTree(objectInputStream, n3, entry, entry5));
        entry5.key = objectInputStream.readFloat();
        entry5.value = objectInputStream.readObject();
        entry5.right(this.readTree(objectInputStream, n2, entry5, entry2));
        if (n == (n & -n)) {
            entry5.balance(1);
        }
        return entry5;
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.setActualComparator();
        this.allocatePaths();
        if (this.count != 0) {
            this.tree = this.readTree(objectInputStream, this.count, null, null);
            Entry<V> entry = this.tree;
            while (entry.left() != null) {
                entry = entry.left();
            }
            this.firstEntry = entry;
            entry = this.tree;
            while (entry.right() != null) {
                entry = entry.right();
            }
            this.lastEntry = entry;
        }
    }

    @Override
    public FloatSet keySet() {
        return this.keySet();
    }

    @Override
    public ObjectSet float2ObjectEntrySet() {
        return this.float2ObjectEntrySet();
    }

    @Override
    public Collection values() {
        return this.values();
    }

    @Override
    public Set keySet() {
        return this.keySet();
    }

    @Override
    public Comparator comparator() {
        return this.comparator();
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class Submap
    extends AbstractFloat2ObjectSortedMap<V>
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        float from;
        float to;
        boolean bottom;
        boolean top;
        protected transient ObjectSortedSet<Float2ObjectMap.Entry<V>> entries;
        protected transient FloatSortedSet keys;
        protected transient ObjectCollection<V> values;
        final Float2ObjectAVLTreeMap this$0;

        public Submap(Float2ObjectAVLTreeMap float2ObjectAVLTreeMap, float f, boolean bl, float f2, boolean bl2) {
            this.this$0 = float2ObjectAVLTreeMap;
            if (!bl && !bl2 && float2ObjectAVLTreeMap.compare(f, f2) > 0) {
                throw new IllegalArgumentException("Start key (" + f + ") is larger than end key (" + f2 + ")");
            }
            this.from = f;
            this.bottom = bl;
            this.to = f2;
            this.top = bl2;
            this.defRetValue = float2ObjectAVLTreeMap.defRetValue;
        }

        @Override
        public void clear() {
            SubmapIterator submapIterator = new SubmapIterator(this);
            while (submapIterator.hasNext()) {
                submapIterator.nextEntry();
                submapIterator.remove();
            }
        }

        final boolean in(float f) {
            return !(!this.bottom && this.this$0.compare(f, this.from) < 0 || !this.top && this.this$0.compare(f, this.to) >= 0);
        }

        @Override
        public ObjectSortedSet<Float2ObjectMap.Entry<V>> float2ObjectEntrySet() {
            if (this.entries == null) {
                this.entries = new AbstractObjectSortedSet<Float2ObjectMap.Entry<V>>(this){
                    final Submap this$1;
                    {
                        this.this$1 = submap;
                    }

                    @Override
                    public ObjectBidirectionalIterator<Float2ObjectMap.Entry<V>> iterator() {
                        return new SubmapEntryIterator(this.this$1);
                    }

                    @Override
                    public ObjectBidirectionalIterator<Float2ObjectMap.Entry<V>> iterator(Float2ObjectMap.Entry<V> entry) {
                        return new SubmapEntryIterator(this.this$1, entry.getFloatKey());
                    }

                    @Override
                    public Comparator<? super Float2ObjectMap.Entry<V>> comparator() {
                        return this.this$1.this$0.float2ObjectEntrySet().comparator();
                    }

                    @Override
                    public boolean contains(Object object) {
                        if (!(object instanceof Map.Entry)) {
                            return true;
                        }
                        Map.Entry entry = (Map.Entry)object;
                        if (entry.getKey() == null || !(entry.getKey() instanceof Float)) {
                            return true;
                        }
                        Entry entry2 = this.this$1.this$0.findKey(((Float)entry.getKey()).floatValue());
                        return entry2 != null && this.this$1.in(entry2.key) && entry.equals(entry2);
                    }

                    @Override
                    public boolean remove(Object object) {
                        if (!(object instanceof Map.Entry)) {
                            return true;
                        }
                        Map.Entry entry = (Map.Entry)object;
                        if (entry.getKey() == null || !(entry.getKey() instanceof Float)) {
                            return true;
                        }
                        Entry entry2 = this.this$1.this$0.findKey(((Float)entry.getKey()).floatValue());
                        if (entry2 != null && this.this$1.in(entry2.key)) {
                            this.this$1.remove(entry2.key);
                        }
                        return entry2 != null;
                    }

                    @Override
                    public int size() {
                        int n = 0;
                        ObjectIterator objectIterator = this.iterator();
                        while (objectIterator.hasNext()) {
                            ++n;
                            objectIterator.next();
                        }
                        return n;
                    }

                    @Override
                    public boolean isEmpty() {
                        return !new SubmapIterator(this.this$1).hasNext();
                    }

                    @Override
                    public void clear() {
                        this.this$1.clear();
                    }

                    @Override
                    public Float2ObjectMap.Entry<V> first() {
                        return this.this$1.firstEntry();
                    }

                    @Override
                    public Float2ObjectMap.Entry<V> last() {
                        return this.this$1.lastEntry();
                    }

                    @Override
                    public ObjectSortedSet<Float2ObjectMap.Entry<V>> subSet(Float2ObjectMap.Entry<V> entry, Float2ObjectMap.Entry<V> entry2) {
                        return this.this$1.subMap(entry.getFloatKey(), entry2.getFloatKey()).float2ObjectEntrySet();
                    }

                    @Override
                    public ObjectSortedSet<Float2ObjectMap.Entry<V>> headSet(Float2ObjectMap.Entry<V> entry) {
                        return this.this$1.headMap(entry.getFloatKey()).float2ObjectEntrySet();
                    }

                    @Override
                    public ObjectSortedSet<Float2ObjectMap.Entry<V>> tailSet(Float2ObjectMap.Entry<V> entry) {
                        return this.this$1.tailMap(entry.getFloatKey()).float2ObjectEntrySet();
                    }

                    @Override
                    public ObjectSortedSet tailSet(Object object) {
                        return this.tailSet((Float2ObjectMap.Entry)object);
                    }

                    @Override
                    public ObjectSortedSet headSet(Object object) {
                        return this.headSet((Float2ObjectMap.Entry)object);
                    }

                    @Override
                    public ObjectSortedSet subSet(Object object, Object object2) {
                        return this.subSet((Float2ObjectMap.Entry)object, (Float2ObjectMap.Entry)object2);
                    }

                    @Override
                    public ObjectBidirectionalIterator iterator(Object object) {
                        return this.iterator((Float2ObjectMap.Entry)object);
                    }

                    @Override
                    public ObjectIterator iterator() {
                        return this.iterator();
                    }

                    @Override
                    public Iterator iterator() {
                        return this.iterator();
                    }

                    @Override
                    public Object last() {
                        return this.last();
                    }

                    @Override
                    public Object first() {
                        return this.first();
                    }

                    @Override
                    public SortedSet tailSet(Object object) {
                        return this.tailSet((Float2ObjectMap.Entry)object);
                    }

                    @Override
                    public SortedSet headSet(Object object) {
                        return this.headSet((Float2ObjectMap.Entry)object);
                    }

                    @Override
                    public SortedSet subSet(Object object, Object object2) {
                        return this.subSet((Float2ObjectMap.Entry)object, (Float2ObjectMap.Entry)object2);
                    }
                };
            }
            return this.entries;
        }

        @Override
        public FloatSortedSet keySet() {
            if (this.keys == null) {
                this.keys = new KeySet(this, null);
            }
            return this.keys;
        }

        @Override
        public ObjectCollection<V> values() {
            if (this.values == null) {
                this.values = new AbstractObjectCollection<V>(this){
                    final Submap this$1;
                    {
                        this.this$1 = submap;
                    }

                    @Override
                    public ObjectIterator<V> iterator() {
                        return new SubmapValueIterator(this.this$1, null);
                    }

                    @Override
                    public boolean contains(Object object) {
                        return this.this$1.containsValue(object);
                    }

                    @Override
                    public int size() {
                        return this.this$1.size();
                    }

                    @Override
                    public void clear() {
                        this.this$1.clear();
                    }

                    @Override
                    public Iterator iterator() {
                        return this.iterator();
                    }
                };
            }
            return this.values;
        }

        @Override
        public boolean containsKey(float f) {
            return this.in(f) && this.this$0.containsKey(f);
        }

        @Override
        public boolean containsValue(Object object) {
            SubmapIterator submapIterator = new SubmapIterator(this);
            while (submapIterator.hasNext()) {
                Object object2 = submapIterator.nextEntry().value;
                if (!Objects.equals(object2, object)) continue;
                return false;
            }
            return true;
        }

        @Override
        public V get(float f) {
            Entry entry;
            float f2 = f;
            return this.in(f2) && (entry = this.this$0.findKey(f2)) != null ? entry.value : this.defRetValue;
        }

        @Override
        public V put(float f, V v) {
            this.this$0.modified = false;
            if (!this.in(f)) {
                throw new IllegalArgumentException("Key (" + f + ") out of range [" + (this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")");
            }
            Object v2 = this.this$0.put(f, v);
            return this.this$0.modified ? this.defRetValue : v2;
        }

        @Override
        public V remove(float f) {
            this.this$0.modified = false;
            if (!this.in(f)) {
                return this.defRetValue;
            }
            Object v = this.this$0.remove(f);
            return this.this$0.modified ? v : this.defRetValue;
        }

        @Override
        public int size() {
            SubmapIterator submapIterator = new SubmapIterator(this);
            int n = 0;
            while (submapIterator.hasNext()) {
                ++n;
                submapIterator.nextEntry();
            }
            return n;
        }

        @Override
        public boolean isEmpty() {
            return !new SubmapIterator(this).hasNext();
        }

        @Override
        public FloatComparator comparator() {
            return this.this$0.actualComparator;
        }

        @Override
        public Float2ObjectSortedMap<V> headMap(float f) {
            if (this.top) {
                return new Submap(this.this$0, this.from, this.bottom, f, false);
            }
            return this.this$0.compare(f, this.to) < 0 ? new Submap(this.this$0, this.from, this.bottom, f, false) : this;
        }

        @Override
        public Float2ObjectSortedMap<V> tailMap(float f) {
            if (this.bottom) {
                return new Submap(this.this$0, f, false, this.to, this.top);
            }
            return this.this$0.compare(f, this.from) > 0 ? new Submap(this.this$0, f, false, this.to, this.top) : this;
        }

        @Override
        public Float2ObjectSortedMap<V> subMap(float f, float f2) {
            if (this.top && this.bottom) {
                return new Submap(this.this$0, f, false, f2, false);
            }
            if (!this.top) {
                float f3 = f2 = this.this$0.compare(f2, this.to) < 0 ? f2 : this.to;
            }
            if (!this.bottom) {
                float f4 = f = this.this$0.compare(f, this.from) > 0 ? f : this.from;
            }
            if (!this.top && !this.bottom && f == this.from && f2 == this.to) {
                return this;
            }
            return new Submap(this.this$0, f, false, f2, false);
        }

        public Entry<V> firstEntry() {
            Entry entry;
            if (this.this$0.tree == null) {
                return null;
            }
            if (this.bottom) {
                entry = this.this$0.firstEntry;
            } else {
                entry = this.this$0.locateKey(this.from);
                if (this.this$0.compare(entry.key, this.from) < 0) {
                    entry = entry.next();
                }
            }
            if (entry == null || !this.top && this.this$0.compare(entry.key, this.to) >= 0) {
                return null;
            }
            return entry;
        }

        public Entry<V> lastEntry() {
            Entry entry;
            if (this.this$0.tree == null) {
                return null;
            }
            if (this.top) {
                entry = this.this$0.lastEntry;
            } else {
                entry = this.this$0.locateKey(this.to);
                if (this.this$0.compare(entry.key, this.to) >= 0) {
                    entry = entry.prev();
                }
            }
            if (entry == null || !this.bottom && this.this$0.compare(entry.key, this.from) < 0) {
                return null;
            }
            return entry;
        }

        @Override
        public float firstFloatKey() {
            Entry entry = this.firstEntry();
            if (entry == null) {
                throw new NoSuchElementException();
            }
            return entry.key;
        }

        @Override
        public float lastFloatKey() {
            Entry entry = this.lastEntry();
            if (entry == null) {
                throw new NoSuchElementException();
            }
            return entry.key;
        }

        @Override
        public FloatSet keySet() {
            return this.keySet();
        }

        @Override
        public ObjectSet float2ObjectEntrySet() {
            return this.float2ObjectEntrySet();
        }

        @Override
        public Collection values() {
            return this.values();
        }

        @Override
        public Set keySet() {
            return this.keySet();
        }

        @Override
        public Comparator comparator() {
            return this.comparator();
        }

        /*
         * Signature claims super is it.unimi.dsi.fastutil.floats.Float2ObjectAVLTreeMap$Submap.SubmapIterator, not it.unimi.dsi.fastutil.floats.Float2ObjectAVLTreeMap$Submap$SubmapIterator - discarding signature.
         */
        private final class SubmapValueIterator
        extends SubmapIterator
        implements ObjectListIterator {
            final Submap this$1;

            private SubmapValueIterator(Submap submap) {
                this.this$1 = submap;
                super(submap);
            }

            @Override
            public V next() {
                return this.nextEntry().value;
            }

            @Override
            public V previous() {
                return this.previousEntry().value;
            }

            SubmapValueIterator(Submap submap, 1 var2_2) {
                this(submap);
            }
        }

        /*
         * Signature claims super is it.unimi.dsi.fastutil.floats.Float2ObjectAVLTreeMap$Submap.SubmapIterator, not it.unimi.dsi.fastutil.floats.Float2ObjectAVLTreeMap$Submap$SubmapIterator - discarding signature.
         */
        private final class SubmapKeyIterator
        extends SubmapIterator
        implements FloatListIterator {
            final Submap this$1;

            public SubmapKeyIterator(Submap submap) {
                this.this$1 = submap;
                super(submap);
            }

            public SubmapKeyIterator(Submap submap, float f) {
                this.this$1 = submap;
                super(submap, f);
            }

            @Override
            public float nextFloat() {
                return this.nextEntry().key;
            }

            @Override
            public float previousFloat() {
                return this.previousEntry().key;
            }
        }

        /*
         * Signature claims super is it.unimi.dsi.fastutil.floats.Float2ObjectAVLTreeMap$Submap.SubmapIterator, not it.unimi.dsi.fastutil.floats.Float2ObjectAVLTreeMap$Submap$SubmapIterator - discarding signature.
         */
        private class SubmapEntryIterator
        extends SubmapIterator
        implements ObjectListIterator {
            final Submap this$1;

            SubmapEntryIterator(Submap submap) {
                this.this$1 = submap;
                super(submap);
            }

            SubmapEntryIterator(Submap submap, float f) {
                this.this$1 = submap;
                super(submap, f);
            }

            @Override
            public Float2ObjectMap.Entry<V> next() {
                return this.nextEntry();
            }

            @Override
            public Float2ObjectMap.Entry<V> previous() {
                return this.previousEntry();
            }

            @Override
            public Object next() {
                return this.next();
            }

            @Override
            public Object previous() {
                return this.previous();
            }
        }

        private class SubmapIterator
        extends TreeIterator {
            final Submap this$1;

            SubmapIterator(Submap submap) {
                this.this$1 = submap;
                super(submap.this$0);
                this.next = submap.firstEntry();
            }

            /*
             * Enabled aggressive block sorting
             */
            SubmapIterator(Submap submap, float f) {
                this(submap);
                if (this.next == null) return;
                if (!submap.bottom && submap.this$0.compare(f, this.next.key) < 0) {
                    this.prev = null;
                    return;
                }
                if (!submap.top) {
                    this.prev = submap.lastEntry();
                    if (submap.this$0.compare(f, this.prev.key) >= 0) {
                        this.next = null;
                        return;
                    }
                }
                this.next = submap.this$0.locateKey(f);
                if (submap.this$0.compare(this.next.key, f) <= 0) {
                    this.prev = this.next;
                    this.next = this.next.next();
                    return;
                }
                this.prev = this.next.prev();
            }

            @Override
            void updatePrevious() {
                this.prev = this.prev.prev();
                if (!this.this$1.bottom && this.prev != null && this.this$1.this$0.compare(this.prev.key, this.this$1.from) < 0) {
                    this.prev = null;
                }
            }

            @Override
            void updateNext() {
                this.next = this.next.next();
                if (!this.this$1.top && this.next != null && this.this$1.this$0.compare(this.next.key, this.this$1.to) >= 0) {
                    this.next = null;
                }
            }
        }

        /*
         * Duplicate member names - consider using --renamedupmembers true
         */
        private class KeySet
        extends AbstractFloat2ObjectSortedMap.KeySet {
            final Submap this$1;

            private KeySet(Submap submap) {
                this.this$1 = submap;
                super(submap);
            }

            @Override
            public FloatBidirectionalIterator iterator() {
                return new SubmapKeyIterator(this.this$1);
            }

            @Override
            public FloatBidirectionalIterator iterator(float f) {
                return new SubmapKeyIterator(this.this$1, f);
            }

            @Override
            public FloatIterator iterator() {
                return this.iterator();
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }

            KeySet(Submap submap, 1 var2_2) {
                this(submap);
            }
        }
    }

    private final class ValueIterator
    extends TreeIterator
    implements ObjectListIterator<V> {
        final Float2ObjectAVLTreeMap this$0;

        private ValueIterator(Float2ObjectAVLTreeMap float2ObjectAVLTreeMap) {
            this.this$0 = float2ObjectAVLTreeMap;
            super(float2ObjectAVLTreeMap);
        }

        @Override
        public V next() {
            return this.nextEntry().value;
        }

        @Override
        public V previous() {
            return this.previousEntry().value;
        }

        ValueIterator(Float2ObjectAVLTreeMap float2ObjectAVLTreeMap, 1 var2_2) {
            this(float2ObjectAVLTreeMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class KeySet
    extends AbstractFloat2ObjectSortedMap.KeySet {
        final Float2ObjectAVLTreeMap this$0;

        private KeySet(Float2ObjectAVLTreeMap float2ObjectAVLTreeMap) {
            this.this$0 = float2ObjectAVLTreeMap;
            super(float2ObjectAVLTreeMap);
        }

        @Override
        public FloatBidirectionalIterator iterator() {
            return new KeyIterator(this.this$0);
        }

        @Override
        public FloatBidirectionalIterator iterator(float f) {
            return new KeyIterator(this.this$0, f);
        }

        @Override
        public FloatIterator iterator() {
            return this.iterator();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        KeySet(Float2ObjectAVLTreeMap float2ObjectAVLTreeMap, 1 var2_2) {
            this(float2ObjectAVLTreeMap);
        }
    }

    private final class KeyIterator
    extends TreeIterator
    implements FloatListIterator {
        final Float2ObjectAVLTreeMap this$0;

        public KeyIterator(Float2ObjectAVLTreeMap float2ObjectAVLTreeMap) {
            this.this$0 = float2ObjectAVLTreeMap;
            super(float2ObjectAVLTreeMap);
        }

        public KeyIterator(Float2ObjectAVLTreeMap float2ObjectAVLTreeMap, float f) {
            this.this$0 = float2ObjectAVLTreeMap;
            super(float2ObjectAVLTreeMap, f);
        }

        @Override
        public float nextFloat() {
            return this.nextEntry().key;
        }

        @Override
        public float previousFloat() {
            return this.previousEntry().key;
        }
    }

    private class EntryIterator
    extends TreeIterator
    implements ObjectListIterator<Float2ObjectMap.Entry<V>> {
        final Float2ObjectAVLTreeMap this$0;

        EntryIterator(Float2ObjectAVLTreeMap float2ObjectAVLTreeMap) {
            this.this$0 = float2ObjectAVLTreeMap;
            super(float2ObjectAVLTreeMap);
        }

        EntryIterator(Float2ObjectAVLTreeMap float2ObjectAVLTreeMap, float f) {
            this.this$0 = float2ObjectAVLTreeMap;
            super(float2ObjectAVLTreeMap, f);
        }

        @Override
        public Float2ObjectMap.Entry<V> next() {
            return this.nextEntry();
        }

        @Override
        public Float2ObjectMap.Entry<V> previous() {
            return this.previousEntry();
        }

        @Override
        public void set(Float2ObjectMap.Entry<V> entry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(Float2ObjectMap.Entry<V> entry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(Object object) {
            this.add((Float2ObjectMap.Entry)object);
        }

        @Override
        public void set(Object object) {
            this.set((Float2ObjectMap.Entry)object);
        }

        @Override
        public Object next() {
            return this.next();
        }

        @Override
        public Object previous() {
            return this.previous();
        }
    }

    private class TreeIterator {
        Entry<V> prev;
        Entry<V> next;
        Entry<V> curr;
        int index;
        final Float2ObjectAVLTreeMap this$0;

        TreeIterator(Float2ObjectAVLTreeMap float2ObjectAVLTreeMap) {
            this.this$0 = float2ObjectAVLTreeMap;
            this.index = 0;
            this.next = float2ObjectAVLTreeMap.firstEntry;
        }

        TreeIterator(Float2ObjectAVLTreeMap float2ObjectAVLTreeMap, float f) {
            this.this$0 = float2ObjectAVLTreeMap;
            this.index = 0;
            this.next = float2ObjectAVLTreeMap.locateKey(f);
            if (this.next != null) {
                if (float2ObjectAVLTreeMap.compare(this.next.key, f) <= 0) {
                    this.prev = this.next;
                    this.next = this.next.next();
                } else {
                    this.prev = this.next.prev();
                }
            }
        }

        public boolean hasNext() {
            return this.next != null;
        }

        public boolean hasPrevious() {
            return this.prev != null;
        }

        void updateNext() {
            this.next = this.next.next();
        }

        Entry<V> nextEntry() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.prev = this.next;
            this.curr = this.prev;
            ++this.index;
            this.updateNext();
            return this.curr;
        }

        void updatePrevious() {
            this.prev = this.prev.prev();
        }

        Entry<V> previousEntry() {
            if (!this.hasPrevious()) {
                throw new NoSuchElementException();
            }
            this.next = this.prev;
            this.curr = this.next;
            --this.index;
            this.updatePrevious();
            return this.curr;
        }

        public int nextIndex() {
            return this.index;
        }

        public int previousIndex() {
            return this.index - 1;
        }

        public void remove() {
            if (this.curr == null) {
                throw new IllegalStateException();
            }
            if (this.curr == this.prev) {
                --this.index;
            }
            this.prev = this.curr;
            this.next = this.prev;
            this.updatePrevious();
            this.updateNext();
            this.this$0.remove(this.curr.key);
            this.curr = null;
        }

        public int skip(int n) {
            int n2 = n;
            while (n2-- != 0 && this.hasNext()) {
                this.nextEntry();
            }
            return n - n2 - 1;
        }

        public int back(int n) {
            int n2 = n;
            while (n2-- != 0 && this.hasPrevious()) {
                this.previousEntry();
            }
            return n - n2 - 1;
        }
    }

    private static final class Entry<V>
    extends AbstractFloat2ObjectMap.BasicEntry<V>
    implements Cloneable {
        private static final int SUCC_MASK = Integer.MIN_VALUE;
        private static final int PRED_MASK = 0x40000000;
        private static final int BALANCE_MASK = 255;
        Entry<V> left;
        Entry<V> right;
        int info;

        Entry() {
            super(0.0f, null);
        }

        Entry(float f, V v) {
            super(f, v);
            this.info = -1073741824;
        }

        Entry<V> left() {
            return (this.info & 0x40000000) != 0 ? null : this.left;
        }

        Entry<V> right() {
            return (this.info & Integer.MIN_VALUE) != 0 ? null : this.right;
        }

        boolean pred() {
            return (this.info & 0x40000000) != 0;
        }

        boolean succ() {
            return (this.info & Integer.MIN_VALUE) != 0;
        }

        void pred(boolean bl) {
            this.info = bl ? (this.info |= 0x40000000) : (this.info &= 0xBFFFFFFF);
        }

        void succ(boolean bl) {
            this.info = bl ? (this.info |= Integer.MIN_VALUE) : (this.info &= Integer.MAX_VALUE);
        }

        void pred(Entry<V> entry) {
            this.info |= 0x40000000;
            this.left = entry;
        }

        void succ(Entry<V> entry) {
            this.info |= Integer.MIN_VALUE;
            this.right = entry;
        }

        void left(Entry<V> entry) {
            this.info &= 0xBFFFFFFF;
            this.left = entry;
        }

        void right(Entry<V> entry) {
            this.info &= Integer.MAX_VALUE;
            this.right = entry;
        }

        int balance() {
            return (byte)this.info;
        }

        void balance(int n) {
            this.info &= 0xFFFFFF00;
            this.info |= n & 0xFF;
        }

        void incBalance() {
            this.info = this.info & 0xFFFFFF00 | (byte)this.info + 1 & 0xFF;
        }

        protected void decBalance() {
            this.info = this.info & 0xFFFFFF00 | (byte)this.info - 1 & 0xFF;
        }

        Entry<V> next() {
            Entry<V> entry = this.right;
            if ((this.info & Integer.MIN_VALUE) == 0) {
                while ((entry.info & 0x40000000) == 0) {
                    entry = entry.left;
                }
            }
            return entry;
        }

        Entry<V> prev() {
            Entry<V> entry = this.left;
            if ((this.info & 0x40000000) == 0) {
                while ((entry.info & Integer.MIN_VALUE) == 0) {
                    entry = entry.right;
                }
            }
            return entry;
        }

        @Override
        public V setValue(V v) {
            Object object = this.value;
            this.value = v;
            return (V)object;
        }

        public Entry<V> clone() {
            Entry entry;
            try {
                entry = (Entry)super.clone();
            } catch (CloneNotSupportedException cloneNotSupportedException) {
                throw new InternalError();
            }
            entry.key = this.key;
            entry.value = this.value;
            entry.info = this.info;
            return entry;
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            return Float.floatToIntBits(this.key) == Float.floatToIntBits(((Float)entry.getKey()).floatValue()) && Objects.equals(this.value, entry.getValue());
        }

        @Override
        public int hashCode() {
            return HashCommon.float2int(this.key) ^ (this.value == null ? 0 : this.value.hashCode());
        }

        @Override
        public String toString() {
            return this.key + "=>" + this.value;
        }

        public Object clone() throws CloneNotSupportedException {
            return this.clone();
        }
    }
}

