/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.floats.FloatListIterator;
import it.unimi.dsi.fastutil.objects.AbstractObject2FloatMap;
import it.unimi.dsi.fastutil.objects.AbstractObject2FloatSortedMap;
import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatSortedMap;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
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
public class Object2FloatRBTreeMap<K>
extends AbstractObject2FloatSortedMap<K>
implements Serializable,
Cloneable {
    protected transient Entry<K> tree;
    protected int count;
    protected transient Entry<K> firstEntry;
    protected transient Entry<K> lastEntry;
    protected transient ObjectSortedSet<Object2FloatMap.Entry<K>> entries;
    protected transient ObjectSortedSet<K> keys;
    protected transient FloatCollection values;
    protected transient boolean modified;
    protected Comparator<? super K> storedComparator;
    protected transient Comparator<? super K> actualComparator;
    private static final long serialVersionUID = -7046029254386353129L;
    private transient boolean[] dirPath;
    private transient Entry<K>[] nodePath;

    public Object2FloatRBTreeMap() {
        this.allocatePaths();
        this.tree = null;
        this.count = 0;
    }

    private void setActualComparator() {
        this.actualComparator = this.storedComparator;
    }

    public Object2FloatRBTreeMap(Comparator<? super K> comparator) {
        this();
        this.storedComparator = comparator;
    }

    public Object2FloatRBTreeMap(Map<? extends K, ? extends Float> map) {
        this();
        this.putAll((Map<? extends K, Float>)map);
    }

    public Object2FloatRBTreeMap(SortedMap<K, Float> sortedMap) {
        this(sortedMap.comparator());
        this.putAll((Map<K, Float>)sortedMap);
    }

    public Object2FloatRBTreeMap(Object2FloatMap<? extends K> object2FloatMap) {
        this();
        this.putAll(object2FloatMap);
    }

    public Object2FloatRBTreeMap(Object2FloatSortedMap<K> object2FloatSortedMap) {
        this(object2FloatSortedMap.comparator());
        this.putAll(object2FloatSortedMap);
    }

    public Object2FloatRBTreeMap(K[] KArray, float[] fArray, Comparator<? super K> comparator) {
        this(comparator);
        if (KArray.length != fArray.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + KArray.length + " and " + fArray.length + ")");
        }
        for (int i = 0; i < KArray.length; ++i) {
            this.put(KArray[i], fArray[i]);
        }
    }

    public Object2FloatRBTreeMap(K[] KArray, float[] fArray) {
        this(KArray, fArray, null);
    }

    final int compare(K k, K k2) {
        return this.actualComparator == null ? ((Comparable)k).compareTo(k2) : this.actualComparator.compare(k, k2);
    }

    final Entry<K> findKey(K k) {
        int n;
        Entry<K> entry = this.tree;
        while (entry != null && (n = this.compare(k, entry.key)) != 0) {
            entry = n < 0 ? entry.left() : entry.right();
        }
        return entry;
    }

    final Entry<K> locateKey(K k) {
        Entry<K> entry = this.tree;
        Entry<K> entry2 = this.tree;
        int n = 0;
        while (entry != null && (n = this.compare(k, entry.key)) != 0) {
            entry2 = entry;
            entry = n < 0 ? entry.left() : entry.right();
        }
        return n == 0 ? entry : entry2;
    }

    private void allocatePaths() {
        this.dirPath = new boolean[64];
        this.nodePath = new Entry[64];
    }

    public float addTo(K k, float f) {
        Entry<K> entry = this.add(k);
        float f2 = entry.value;
        entry.value += f;
        return f2;
    }

    @Override
    public float put(K k, float f) {
        Entry<K> entry = this.add(k);
        float f2 = entry.value;
        entry.value = f;
        return f2;
    }

    private Entry<K> add(K k) {
        Entry<K> entry;
        this.modified = false;
        int n = 0;
        if (this.tree == null) {
            ++this.count;
            this.firstEntry = new Entry<K>(k, this.defRetValue);
            this.lastEntry = this.firstEntry;
            this.tree = this.firstEntry;
            entry = this.firstEntry;
        } else {
            Entry<K> entry2 = this.tree;
            int n2 = 0;
            while (true) {
                int n3;
                if ((n3 = this.compare(k, entry2.key)) == 0) {
                    while (n2-- != 0) {
                        this.nodePath[n2] = null;
                    }
                    return entry2;
                }
                this.nodePath[n2] = entry2;
                this.dirPath[n2++] = n3 > 0;
                if (this.dirPath[n2++]) {
                    if (entry2.succ()) {
                        ++this.count;
                        entry = new Entry<K>(k, this.defRetValue);
                        if (entry2.right == null) {
                            this.lastEntry = entry;
                        }
                        entry.left = entry2;
                        entry.right = entry2.right;
                        entry2.right(entry);
                        break;
                    }
                    entry2 = entry2.right;
                    continue;
                }
                if (entry2.pred()) {
                    ++this.count;
                    entry = new Entry<K>(k, this.defRetValue);
                    if (entry2.left == null) {
                        this.firstEntry = entry;
                    }
                    entry.right = entry2;
                    entry.left = entry2.left;
                    entry2.left(entry);
                    break;
                }
                entry2 = entry2.left;
            }
            this.modified = true;
            n = n2--;
            while (n2 > 0 && !this.nodePath[n2].black()) {
                Entry entry3;
                Entry entry4;
                if (!this.dirPath[n2 - 1]) {
                    entry4 = this.nodePath[n2 - 1].right;
                    if (!this.nodePath[n2 - 1].succ() && !entry4.black()) {
                        this.nodePath[n2].black(false);
                        entry4.black(false);
                        this.nodePath[n2 - 1].black(true);
                        n2 -= 2;
                        continue;
                    }
                    if (!this.dirPath[n2]) {
                        entry4 = this.nodePath[n2];
                    } else {
                        entry3 = this.nodePath[n2];
                        entry4 = entry3.right;
                        entry3.right = entry4.left;
                        entry4.left = entry3;
                        this.nodePath[n2 - 1].left = entry4;
                        if (entry4.pred()) {
                            entry4.pred(true);
                            entry3.succ(entry4);
                        }
                    }
                    entry3 = this.nodePath[n2 - 1];
                    entry3.black(true);
                    entry4.black(false);
                    entry3.left = entry4.right;
                    entry4.right = entry3;
                    if (n2 < 2) {
                        this.tree = entry4;
                    } else if (this.dirPath[n2 - 2]) {
                        this.nodePath[n2 - 2].right = entry4;
                    } else {
                        this.nodePath[n2 - 2].left = entry4;
                    }
                    if (!entry4.succ()) break;
                    entry4.succ(true);
                    entry3.pred(entry4);
                    break;
                }
                entry4 = this.nodePath[n2 - 1].left;
                if (!this.nodePath[n2 - 1].pred() && !entry4.black()) {
                    this.nodePath[n2].black(false);
                    entry4.black(false);
                    this.nodePath[n2 - 1].black(true);
                    n2 -= 2;
                    continue;
                }
                if (this.dirPath[n2]) {
                    entry4 = this.nodePath[n2];
                } else {
                    entry3 = this.nodePath[n2];
                    entry4 = entry3.left;
                    entry3.left = entry4.right;
                    entry4.right = entry3;
                    this.nodePath[n2 - 1].right = entry4;
                    if (entry4.succ()) {
                        entry4.succ(true);
                        entry3.pred(entry4);
                    }
                }
                entry3 = this.nodePath[n2 - 1];
                entry3.black(true);
                entry4.black(false);
                entry3.right = entry4.left;
                entry4.left = entry3;
                if (n2 < 2) {
                    this.tree = entry4;
                } else if (this.dirPath[n2 - 2]) {
                    this.nodePath[n2 - 2].right = entry4;
                } else {
                    this.nodePath[n2 - 2].left = entry4;
                }
                if (!entry4.pred()) break;
                entry4.pred(true);
                entry3.succ(entry4);
                break;
            }
        }
        this.tree.black(false);
        while (n-- != 0) {
            this.nodePath[n] = null;
        }
        return entry;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public float removeFloat(Object object) {
        Entry entry;
        int n;
        Entry entry2;
        int n2;
        Entry<K> entry3;
        block68: {
            int n3;
            block74: {
                block69: {
                    block73: {
                        block71: {
                            block72: {
                                block66: {
                                    block67: {
                                        this.modified = false;
                                        if (this.tree == null) {
                                            return this.defRetValue;
                                        }
                                        entry3 = this.tree;
                                        n2 = 0;
                                        Object object2 = object;
                                        while (true) {
                                            int n4;
                                            if ((n4 = this.compare(object2, entry3.key)) == 0) {
                                                if (entry3.left != null) break block66;
                                                break block67;
                                            }
                                            this.dirPath[n2] = n4 > 0;
                                            this.nodePath[n2] = entry3;
                                            if (this.dirPath[n2++]) {
                                                if ((entry3 = entry3.right()) != null) continue;
                                                while (true) {
                                                    if (n2-- == 0) {
                                                        return this.defRetValue;
                                                    }
                                                    this.nodePath[n2] = null;
                                                }
                                            }
                                            if ((entry3 = entry3.left()) == null) break;
                                        }
                                        while (true) {
                                            if (n2-- == 0) {
                                                return this.defRetValue;
                                            }
                                            this.nodePath[n2] = null;
                                        }
                                    }
                                    this.firstEntry = entry3.next();
                                }
                                if (entry3.right == null) {
                                    this.lastEntry = entry3.prev();
                                }
                                if (!entry3.succ()) break block71;
                                if (!entry3.pred()) break block72;
                                if (n2 == 0) {
                                    this.tree = entry3.left;
                                    break block68;
                                } else if (this.dirPath[n2 - 1]) {
                                    this.nodePath[n2 - 1].succ(entry3.right);
                                    break block68;
                                } else {
                                    this.nodePath[n2 - 1].pred(entry3.left);
                                }
                                break block68;
                            }
                            entry3.prev().right = entry3.right;
                            if (n2 == 0) {
                                this.tree = entry3.left;
                                break block68;
                            } else if (this.dirPath[n2 - 1]) {
                                this.nodePath[n2 - 1].right = entry3.left;
                                break block68;
                            } else {
                                this.nodePath[n2 - 1].left = entry3.left;
                            }
                            break block68;
                        }
                        entry2 = entry3.right;
                        if (!entry2.pred()) break block73;
                        entry2.left = entry3.left;
                        entry2.pred(entry3.pred());
                        if (!entry2.pred()) {
                            entry2.prev().right = entry2;
                        }
                        if (n2 == 0) {
                            this.tree = entry2;
                        } else if (this.dirPath[n2 - 1]) {
                            this.nodePath[n2 - 1].right = entry2;
                        } else {
                            this.nodePath[n2 - 1].left = entry2;
                        }
                        n = entry2.black() ? 1 : 0;
                        entry2.black(entry3.black());
                        entry3.black(n != 0);
                        this.dirPath[n2] = true;
                        this.nodePath[n2++] = entry2;
                        break block68;
                    }
                    n3 = n2++;
                    while (true) {
                        this.dirPath[n2] = false;
                        this.nodePath[n2++] = entry2;
                        entry = entry2.left;
                        if (entry.pred()) {
                            this.dirPath[n3] = true;
                            this.nodePath[n3] = entry;
                            if (entry.succ()) {
                                break;
                            }
                            break block69;
                        }
                        entry2 = entry;
                    }
                    entry2.pred(entry);
                    break block74;
                }
                entry2.left = entry.right;
            }
            entry.left = entry3.left;
            if (!entry3.pred()) {
                entry3.prev().right = entry;
                entry.pred(true);
            }
            entry.right(entry3.right);
            n = entry.black() ? 1 : 0;
            entry.black(entry3.black());
            entry3.black(n != 0);
            if (n3 == 0) {
                this.tree = entry;
            } else if (this.dirPath[n3 - 1]) {
                this.nodePath[n3 - 1].right = entry;
            } else {
                this.nodePath[n3 - 1].left = entry;
            }
        }
        n = n2;
        if (entry3.black()) {
            while (n2 > 0) {
                block70: {
                    if (this.dirPath[n2 - 1] && !this.nodePath[n2 - 1].succ() || !this.dirPath[n2 - 1] && !this.nodePath[n2 - 1].pred()) {
                        Entry entry4 = entry2 = this.dirPath[n2 - 1] ? this.nodePath[n2 - 1].right : this.nodePath[n2 - 1].left;
                        if (!entry2.black()) {
                            entry2.black(false);
                            break;
                        }
                    }
                    if (!this.dirPath[n2 - 1]) {
                        entry2 = this.nodePath[n2 - 1].right;
                        if (!entry2.black()) {
                            entry2.black(false);
                            this.nodePath[n2 - 1].black(true);
                            this.nodePath[n2 - 1].right = entry2.left;
                            entry2.left = this.nodePath[n2 - 1];
                            if (n2 < 2) {
                                this.tree = entry2;
                            } else if (this.dirPath[n2 - 2]) {
                                this.nodePath[n2 - 2].right = entry2;
                            } else {
                                this.nodePath[n2 - 2].left = entry2;
                            }
                            this.nodePath[n2] = this.nodePath[n2 - 1];
                            this.dirPath[n2] = false;
                            this.nodePath[n2 - 1] = entry2;
                            if (n == n2++) {
                                ++n;
                            }
                            entry2 = this.nodePath[n2 - 1].right;
                        }
                        if ((entry2.pred() || entry2.left.black()) && (entry2.succ() || entry2.right.black())) {
                            entry2.black(true);
                            break block70;
                        } else {
                            if (entry2.succ() || entry2.right.black()) {
                                entry = entry2.left;
                                entry.black(false);
                                entry2.black(true);
                                entry2.left = entry.right;
                                entry.right = entry2;
                                this.nodePath[n2 - 1].right = entry;
                                entry2 = this.nodePath[n2 - 1].right;
                                if (entry2.succ()) {
                                    entry2.succ(true);
                                    entry2.right.pred(entry2);
                                }
                            }
                            entry2.black(this.nodePath[n2 - 1].black());
                            this.nodePath[n2 - 1].black(false);
                            entry2.right.black(false);
                            this.nodePath[n2 - 1].right = entry2.left;
                            entry2.left = this.nodePath[n2 - 1];
                            if (n2 < 2) {
                                this.tree = entry2;
                            } else if (this.dirPath[n2 - 2]) {
                                this.nodePath[n2 - 2].right = entry2;
                            } else {
                                this.nodePath[n2 - 2].left = entry2;
                            }
                            if (!entry2.pred()) break;
                            entry2.pred(true);
                            this.nodePath[n2 - 1].succ(entry2);
                            break;
                        }
                    }
                    entry2 = this.nodePath[n2 - 1].left;
                    if (!entry2.black()) {
                        entry2.black(false);
                        this.nodePath[n2 - 1].black(true);
                        this.nodePath[n2 - 1].left = entry2.right;
                        entry2.right = this.nodePath[n2 - 1];
                        if (n2 < 2) {
                            this.tree = entry2;
                        } else if (this.dirPath[n2 - 2]) {
                            this.nodePath[n2 - 2].right = entry2;
                        } else {
                            this.nodePath[n2 - 2].left = entry2;
                        }
                        this.nodePath[n2] = this.nodePath[n2 - 1];
                        this.dirPath[n2] = true;
                        this.nodePath[n2 - 1] = entry2;
                        if (n == n2++) {
                            ++n;
                        }
                        entry2 = this.nodePath[n2 - 1].left;
                    }
                    if ((entry2.pred() || entry2.left.black()) && (entry2.succ() || entry2.right.black())) {
                        entry2.black(true);
                    } else {
                        if (entry2.pred() || entry2.left.black()) {
                            entry = entry2.right;
                            entry.black(false);
                            entry2.black(true);
                            entry2.right = entry.left;
                            entry.left = entry2;
                            this.nodePath[n2 - 1].left = entry;
                            entry2 = this.nodePath[n2 - 1].left;
                            if (entry2.pred()) {
                                entry2.pred(true);
                                entry2.left.succ(entry2);
                            }
                        }
                        entry2.black(this.nodePath[n2 - 1].black());
                        this.nodePath[n2 - 1].black(false);
                        entry2.left.black(false);
                        this.nodePath[n2 - 1].left = entry2.right;
                        entry2.right = this.nodePath[n2 - 1];
                        if (n2 < 2) {
                            this.tree = entry2;
                        } else if (this.dirPath[n2 - 2]) {
                            this.nodePath[n2 - 2].right = entry2;
                        } else {
                            this.nodePath[n2 - 2].left = entry2;
                        }
                        if (!entry2.succ()) break;
                        entry2.succ(true);
                        this.nodePath[n2 - 1].pred(entry2);
                        break;
                    }
                }
                --n2;
            }
            if (this.tree != null) {
                this.tree.black(false);
            }
        }
        this.modified = true;
        --this.count;
        while (n-- != 0) {
            this.nodePath[n] = null;
        }
        return entry3.value;
    }

    @Override
    public boolean containsValue(float f) {
        ValueIterator valueIterator = new ValueIterator(this, null);
        int n = this.count;
        while (n-- != 0) {
            float f2 = valueIterator.nextFloat();
            if (Float.floatToIntBits(f2) != Float.floatToIntBits(f)) continue;
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
    public boolean containsKey(Object object) {
        return this.findKey(object) != null;
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
    public float getFloat(Object object) {
        Entry<Object> entry = this.findKey(object);
        return entry == null ? this.defRetValue : entry.value;
    }

    @Override
    public K firstKey() {
        if (this.tree == null) {
            throw new NoSuchElementException();
        }
        return (K)this.firstEntry.key;
    }

    @Override
    public K lastKey() {
        if (this.tree == null) {
            throw new NoSuchElementException();
        }
        return (K)this.lastEntry.key;
    }

    @Override
    public ObjectSortedSet<Object2FloatMap.Entry<K>> object2FloatEntrySet() {
        if (this.entries == null) {
            this.entries = new AbstractObjectSortedSet<Object2FloatMap.Entry<K>>(this){
                final Comparator<? super Object2FloatMap.Entry<K>> comparator;
                final Object2FloatRBTreeMap this$0;
                {
                    this.this$0 = object2FloatRBTreeMap;
                    this.comparator = this::lambda$$0;
                }

                @Override
                public Comparator<? super Object2FloatMap.Entry<K>> comparator() {
                    return this.comparator;
                }

                @Override
                public ObjectBidirectionalIterator<Object2FloatMap.Entry<K>> iterator() {
                    return new EntryIterator(this.this$0);
                }

                @Override
                public ObjectBidirectionalIterator<Object2FloatMap.Entry<K>> iterator(Object2FloatMap.Entry<K> entry) {
                    return new EntryIterator(this.this$0, entry.getKey());
                }

                @Override
                public boolean contains(Object object) {
                    if (!(object instanceof Map.Entry)) {
                        return true;
                    }
                    Map.Entry entry = (Map.Entry)object;
                    if (entry.getValue() == null || !(entry.getValue() instanceof Float)) {
                        return true;
                    }
                    Entry entry2 = this.this$0.findKey(entry.getKey());
                    return entry.equals(entry2);
                }

                @Override
                public boolean remove(Object object) {
                    if (!(object instanceof Map.Entry)) {
                        return true;
                    }
                    Map.Entry entry = (Map.Entry)object;
                    if (entry.getValue() == null || !(entry.getValue() instanceof Float)) {
                        return true;
                    }
                    Entry entry2 = this.this$0.findKey(entry.getKey());
                    if (entry2 == null || Float.floatToIntBits(entry2.getFloatValue()) != Float.floatToIntBits(((Float)entry.getValue()).floatValue())) {
                        return true;
                    }
                    this.this$0.removeFloat(entry2.key);
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
                public Object2FloatMap.Entry<K> first() {
                    return this.this$0.firstEntry;
                }

                @Override
                public Object2FloatMap.Entry<K> last() {
                    return this.this$0.lastEntry;
                }

                @Override
                public ObjectSortedSet<Object2FloatMap.Entry<K>> subSet(Object2FloatMap.Entry<K> entry, Object2FloatMap.Entry<K> entry2) {
                    return this.this$0.subMap(entry.getKey(), entry2.getKey()).object2FloatEntrySet();
                }

                @Override
                public ObjectSortedSet<Object2FloatMap.Entry<K>> headSet(Object2FloatMap.Entry<K> entry) {
                    return this.this$0.headMap(entry.getKey()).object2FloatEntrySet();
                }

                @Override
                public ObjectSortedSet<Object2FloatMap.Entry<K>> tailSet(Object2FloatMap.Entry<K> entry) {
                    return this.this$0.tailMap(entry.getKey()).object2FloatEntrySet();
                }

                @Override
                public ObjectSortedSet tailSet(Object object) {
                    return this.tailSet((Object2FloatMap.Entry)object);
                }

                @Override
                public ObjectSortedSet headSet(Object object) {
                    return this.headSet((Object2FloatMap.Entry)object);
                }

                @Override
                public ObjectSortedSet subSet(Object object, Object object2) {
                    return this.subSet((Object2FloatMap.Entry)object, (Object2FloatMap.Entry)object2);
                }

                @Override
                public ObjectBidirectionalIterator iterator(Object object) {
                    return this.iterator((Object2FloatMap.Entry)object);
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
                    return this.tailSet((Object2FloatMap.Entry)object);
                }

                @Override
                public SortedSet headSet(Object object) {
                    return this.headSet((Object2FloatMap.Entry)object);
                }

                @Override
                public SortedSet subSet(Object object, Object object2) {
                    return this.subSet((Object2FloatMap.Entry)object, (Object2FloatMap.Entry)object2);
                }

                private int lambda$$0(Object2FloatMap.Entry entry, Object2FloatMap.Entry entry2) {
                    return this.this$0.actualComparator.compare(entry.getKey(), entry2.getKey());
                }
            };
        }
        return this.entries;
    }

    @Override
    public ObjectSortedSet<K> keySet() {
        if (this.keys == null) {
            this.keys = new KeySet(this, null);
        }
        return this.keys;
    }

    @Override
    public FloatCollection values() {
        if (this.values == null) {
            this.values = new AbstractFloatCollection(this){
                final Object2FloatRBTreeMap this$0;
                {
                    this.this$0 = object2FloatRBTreeMap;
                }

                @Override
                public FloatIterator iterator() {
                    return new ValueIterator(this.this$0, null);
                }

                @Override
                public boolean contains(float f) {
                    return this.this$0.containsValue(f);
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
    public Comparator<? super K> comparator() {
        return this.actualComparator;
    }

    @Override
    public Object2FloatSortedMap<K> headMap(K k) {
        return new Submap(this, null, true, k, false);
    }

    @Override
    public Object2FloatSortedMap<K> tailMap(K k) {
        return new Submap(this, k, false, null, true);
    }

    @Override
    public Object2FloatSortedMap<K> subMap(K k, K k2) {
        return new Submap(this, k, false, k2, false);
    }

    public Object2FloatRBTreeMap<K> clone() {
        Object2FloatRBTreeMap object2FloatRBTreeMap;
        try {
            object2FloatRBTreeMap = (Object2FloatRBTreeMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        object2FloatRBTreeMap.keys = null;
        object2FloatRBTreeMap.values = null;
        object2FloatRBTreeMap.entries = null;
        object2FloatRBTreeMap.allocatePaths();
        if (this.count != 0) {
            Entry<K> entry = new Entry<K>();
            Entry entry2 = new Entry();
            Entry<K> entry3 = entry;
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
                            object2FloatRBTreeMap.tree = entry2.left;
                            object2FloatRBTreeMap.firstEntry = object2FloatRBTreeMap.tree;
                            while (object2FloatRBTreeMap.firstEntry.left != null) {
                                object2FloatRBTreeMap.firstEntry = object2FloatRBTreeMap.firstEntry.left;
                            }
                            object2FloatRBTreeMap.lastEntry = object2FloatRBTreeMap.tree;
                            while (object2FloatRBTreeMap.lastEntry.right != null) {
                                object2FloatRBTreeMap.lastEntry = object2FloatRBTreeMap.lastEntry.right;
                            }
                            return object2FloatRBTreeMap;
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
        return object2FloatRBTreeMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        int n = this.count;
        EntryIterator entryIterator = new EntryIterator(this);
        objectOutputStream.defaultWriteObject();
        while (n-- != 0) {
            Entry entry = entryIterator.nextEntry();
            objectOutputStream.writeObject(entry.key);
            objectOutputStream.writeFloat(entry.value);
        }
    }

    private Entry<K> readTree(ObjectInputStream objectInputStream, int n, Entry<K> entry, Entry<K> entry2) throws IOException, ClassNotFoundException {
        if (n == 1) {
            Entry<Object> entry3 = new Entry<Object>(objectInputStream.readObject(), objectInputStream.readFloat());
            entry3.pred(entry);
            entry3.succ(entry2);
            entry3.black(false);
            return entry3;
        }
        if (n == 2) {
            Entry<Object> entry4 = new Entry<Object>(objectInputStream.readObject(), objectInputStream.readFloat());
            entry4.black(false);
            entry4.right(new Entry<Object>(objectInputStream.readObject(), objectInputStream.readFloat()));
            entry4.right.pred(entry4);
            entry4.pred(entry);
            entry4.right.succ(entry2);
            return entry4;
        }
        int n2 = n / 2;
        int n3 = n - n2 - 1;
        Entry entry5 = new Entry();
        entry5.left(this.readTree(objectInputStream, n3, entry, entry5));
        entry5.key = objectInputStream.readObject();
        entry5.value = objectInputStream.readFloat();
        entry5.black(false);
        entry5.right(this.readTree(objectInputStream, n2, entry5, entry2));
        if (n + 2 == (n + 2 & -(n + 2))) {
            entry5.right.black(true);
        }
        return entry5;
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.setActualComparator();
        this.allocatePaths();
        if (this.count != 0) {
            this.tree = this.readTree(objectInputStream, this.count, null, null);
            Entry<K> entry = this.tree;
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
    public ObjectSet keySet() {
        return this.keySet();
    }

    @Override
    public ObjectSet object2FloatEntrySet() {
        return this.object2FloatEntrySet();
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
    public SortedMap tailMap(Object object) {
        return this.tailMap(object);
    }

    @Override
    public SortedMap headMap(Object object) {
        return this.headMap(object);
    }

    @Override
    public SortedMap subMap(Object object, Object object2) {
        return this.subMap(object, object2);
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class Submap
    extends AbstractObject2FloatSortedMap<K>
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        K from;
        K to;
        boolean bottom;
        boolean top;
        protected transient ObjectSortedSet<Object2FloatMap.Entry<K>> entries;
        protected transient ObjectSortedSet<K> keys;
        protected transient FloatCollection values;
        final Object2FloatRBTreeMap this$0;

        public Submap(Object2FloatRBTreeMap object2FloatRBTreeMap, K k, boolean bl, K k2, boolean bl2) {
            this.this$0 = object2FloatRBTreeMap;
            if (!bl && !bl2 && object2FloatRBTreeMap.compare(k, k2) > 0) {
                throw new IllegalArgumentException("Start key (" + k + ") is larger than end key (" + k2 + ")");
            }
            this.from = k;
            this.bottom = bl;
            this.to = k2;
            this.top = bl2;
            this.defRetValue = object2FloatRBTreeMap.defRetValue;
        }

        @Override
        public void clear() {
            SubmapIterator submapIterator = new SubmapIterator(this);
            while (submapIterator.hasNext()) {
                submapIterator.nextEntry();
                submapIterator.remove();
            }
        }

        final boolean in(K k) {
            return !(!this.bottom && this.this$0.compare(k, this.from) < 0 || !this.top && this.this$0.compare(k, this.to) >= 0);
        }

        @Override
        public ObjectSortedSet<Object2FloatMap.Entry<K>> object2FloatEntrySet() {
            if (this.entries == null) {
                this.entries = new AbstractObjectSortedSet<Object2FloatMap.Entry<K>>(this){
                    final Submap this$1;
                    {
                        this.this$1 = submap;
                    }

                    @Override
                    public ObjectBidirectionalIterator<Object2FloatMap.Entry<K>> iterator() {
                        return new SubmapEntryIterator(this.this$1);
                    }

                    @Override
                    public ObjectBidirectionalIterator<Object2FloatMap.Entry<K>> iterator(Object2FloatMap.Entry<K> entry) {
                        return new SubmapEntryIterator(this.this$1, entry.getKey());
                    }

                    @Override
                    public Comparator<? super Object2FloatMap.Entry<K>> comparator() {
                        return this.this$1.this$0.object2FloatEntrySet().comparator();
                    }

                    @Override
                    public boolean contains(Object object) {
                        if (!(object instanceof Map.Entry)) {
                            return true;
                        }
                        Map.Entry entry = (Map.Entry)object;
                        if (entry.getValue() == null || !(entry.getValue() instanceof Float)) {
                            return true;
                        }
                        Entry entry2 = this.this$1.this$0.findKey(entry.getKey());
                        return entry2 != null && this.this$1.in(entry2.key) && entry.equals(entry2);
                    }

                    @Override
                    public boolean remove(Object object) {
                        if (!(object instanceof Map.Entry)) {
                            return true;
                        }
                        Map.Entry entry = (Map.Entry)object;
                        if (entry.getValue() == null || !(entry.getValue() instanceof Float)) {
                            return true;
                        }
                        Entry entry2 = this.this$1.this$0.findKey(entry.getKey());
                        if (entry2 != null && this.this$1.in(entry2.key)) {
                            this.this$1.removeFloat(entry2.key);
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
                    public Object2FloatMap.Entry<K> first() {
                        return this.this$1.firstEntry();
                    }

                    @Override
                    public Object2FloatMap.Entry<K> last() {
                        return this.this$1.lastEntry();
                    }

                    @Override
                    public ObjectSortedSet<Object2FloatMap.Entry<K>> subSet(Object2FloatMap.Entry<K> entry, Object2FloatMap.Entry<K> entry2) {
                        return this.this$1.subMap(entry.getKey(), entry2.getKey()).object2FloatEntrySet();
                    }

                    @Override
                    public ObjectSortedSet<Object2FloatMap.Entry<K>> headSet(Object2FloatMap.Entry<K> entry) {
                        return this.this$1.headMap(entry.getKey()).object2FloatEntrySet();
                    }

                    @Override
                    public ObjectSortedSet<Object2FloatMap.Entry<K>> tailSet(Object2FloatMap.Entry<K> entry) {
                        return this.this$1.tailMap(entry.getKey()).object2FloatEntrySet();
                    }

                    @Override
                    public ObjectSortedSet tailSet(Object object) {
                        return this.tailSet((Object2FloatMap.Entry)object);
                    }

                    @Override
                    public ObjectSortedSet headSet(Object object) {
                        return this.headSet((Object2FloatMap.Entry)object);
                    }

                    @Override
                    public ObjectSortedSet subSet(Object object, Object object2) {
                        return this.subSet((Object2FloatMap.Entry)object, (Object2FloatMap.Entry)object2);
                    }

                    @Override
                    public ObjectBidirectionalIterator iterator(Object object) {
                        return this.iterator((Object2FloatMap.Entry)object);
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
                        return this.tailSet((Object2FloatMap.Entry)object);
                    }

                    @Override
                    public SortedSet headSet(Object object) {
                        return this.headSet((Object2FloatMap.Entry)object);
                    }

                    @Override
                    public SortedSet subSet(Object object, Object object2) {
                        return this.subSet((Object2FloatMap.Entry)object, (Object2FloatMap.Entry)object2);
                    }
                };
            }
            return this.entries;
        }

        @Override
        public ObjectSortedSet<K> keySet() {
            if (this.keys == null) {
                this.keys = new KeySet(this, null);
            }
            return this.keys;
        }

        @Override
        public FloatCollection values() {
            if (this.values == null) {
                this.values = new AbstractFloatCollection(this){
                    final Submap this$1;
                    {
                        this.this$1 = submap;
                    }

                    @Override
                    public FloatIterator iterator() {
                        return new SubmapValueIterator(this.this$1, null);
                    }

                    @Override
                    public boolean contains(float f) {
                        return this.this$1.containsValue(f);
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
        public boolean containsKey(Object object) {
            return this.in(object) && this.this$0.containsKey(object);
        }

        @Override
        public boolean containsValue(float f) {
            SubmapIterator submapIterator = new SubmapIterator(this);
            while (submapIterator.hasNext()) {
                float f2 = submapIterator.nextEntry().value;
                if (Float.floatToIntBits(f2) != Float.floatToIntBits(f)) continue;
                return false;
            }
            return true;
        }

        @Override
        public float getFloat(Object object) {
            Entry<Object> entry;
            Object object2 = object;
            return this.in(object2) && (entry = this.this$0.findKey(object2)) != null ? entry.value : this.defRetValue;
        }

        @Override
        public float put(K k, float f) {
            this.this$0.modified = false;
            if (!this.in(k)) {
                throw new IllegalArgumentException("Key (" + k + ") out of range [" + (this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")");
            }
            float f2 = this.this$0.put(k, f);
            return this.this$0.modified ? this.defRetValue : f2;
        }

        @Override
        public float removeFloat(Object object) {
            this.this$0.modified = false;
            if (!this.in(object)) {
                return this.defRetValue;
            }
            float f = this.this$0.removeFloat(object);
            return this.this$0.modified ? f : this.defRetValue;
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
        public Comparator<? super K> comparator() {
            return this.this$0.actualComparator;
        }

        @Override
        public Object2FloatSortedMap<K> headMap(K k) {
            if (this.top) {
                return new Submap(this.this$0, this.from, this.bottom, k, false);
            }
            return this.this$0.compare(k, this.to) < 0 ? new Submap(this.this$0, this.from, this.bottom, k, false) : this;
        }

        @Override
        public Object2FloatSortedMap<K> tailMap(K k) {
            if (this.bottom) {
                return new Submap(this.this$0, k, false, this.to, this.top);
            }
            return this.this$0.compare(k, this.from) > 0 ? new Submap(this.this$0, k, false, this.to, this.top) : this;
        }

        @Override
        public Object2FloatSortedMap<K> subMap(K k, K k2) {
            if (this.top && this.bottom) {
                return new Submap(this.this$0, k, false, k2, false);
            }
            if (!this.top) {
                Object k3 = k2 = this.this$0.compare(k2, this.to) < 0 ? k2 : this.to;
            }
            if (!this.bottom) {
                Object k4 = k = this.this$0.compare(k, this.from) > 0 ? k : this.from;
            }
            if (!this.top && !this.bottom && k == this.from && k2 == this.to) {
                return this;
            }
            return new Submap(this.this$0, k, false, k2, false);
        }

        public Entry<K> firstEntry() {
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

        public Entry<K> lastEntry() {
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
        public K firstKey() {
            Entry entry = this.firstEntry();
            if (entry == null) {
                throw new NoSuchElementException();
            }
            return entry.key;
        }

        @Override
        public K lastKey() {
            Entry entry = this.lastEntry();
            if (entry == null) {
                throw new NoSuchElementException();
            }
            return entry.key;
        }

        @Override
        public ObjectSet keySet() {
            return this.keySet();
        }

        @Override
        public ObjectSet object2FloatEntrySet() {
            return this.object2FloatEntrySet();
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
        public SortedMap tailMap(Object object) {
            return this.tailMap(object);
        }

        @Override
        public SortedMap headMap(Object object) {
            return this.headMap(object);
        }

        @Override
        public SortedMap subMap(Object object, Object object2) {
            return this.subMap(object, object2);
        }

        /*
         * Signature claims super is it.unimi.dsi.fastutil.objects.Object2FloatRBTreeMap$Submap.SubmapIterator, not it.unimi.dsi.fastutil.objects.Object2FloatRBTreeMap$Submap$SubmapIterator - discarding signature.
         */
        private final class SubmapValueIterator
        extends SubmapIterator
        implements FloatListIterator {
            final Submap this$1;

            private SubmapValueIterator(Submap submap) {
                this.this$1 = submap;
                super(submap);
            }

            @Override
            public float nextFloat() {
                return this.nextEntry().value;
            }

            @Override
            public float previousFloat() {
                return this.previousEntry().value;
            }

            SubmapValueIterator(Submap submap, 1 var2_2) {
                this(submap);
            }
        }

        /*
         * Signature claims super is it.unimi.dsi.fastutil.objects.Object2FloatRBTreeMap$Submap.SubmapIterator, not it.unimi.dsi.fastutil.objects.Object2FloatRBTreeMap$Submap$SubmapIterator - discarding signature.
         */
        private final class SubmapKeyIterator
        extends SubmapIterator
        implements ObjectListIterator {
            final Submap this$1;

            public SubmapKeyIterator(Submap submap) {
                this.this$1 = submap;
                super(submap);
            }

            public SubmapKeyIterator(Submap submap, K k) {
                this.this$1 = submap;
                super(submap, k);
            }

            @Override
            public K next() {
                return this.nextEntry().key;
            }

            @Override
            public K previous() {
                return this.previousEntry().key;
            }
        }

        /*
         * Signature claims super is it.unimi.dsi.fastutil.objects.Object2FloatRBTreeMap$Submap.SubmapIterator, not it.unimi.dsi.fastutil.objects.Object2FloatRBTreeMap$Submap$SubmapIterator - discarding signature.
         */
        private class SubmapEntryIterator
        extends SubmapIterator
        implements ObjectListIterator {
            final Submap this$1;

            SubmapEntryIterator(Submap submap) {
                this.this$1 = submap;
                super(submap);
            }

            SubmapEntryIterator(Submap submap, K k) {
                this.this$1 = submap;
                super(submap, k);
            }

            @Override
            public Object2FloatMap.Entry<K> next() {
                return this.nextEntry();
            }

            @Override
            public Object2FloatMap.Entry<K> previous() {
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
            SubmapIterator(Submap submap, K k) {
                this(submap);
                if (this.next == null) return;
                if (!submap.bottom && submap.this$0.compare(k, this.next.key) < 0) {
                    this.prev = null;
                    return;
                }
                if (!submap.top) {
                    this.prev = submap.lastEntry();
                    if (submap.this$0.compare(k, this.prev.key) >= 0) {
                        this.next = null;
                        return;
                    }
                }
                this.next = submap.this$0.locateKey(k);
                if (submap.this$0.compare(this.next.key, k) <= 0) {
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
        extends AbstractObject2FloatSortedMap.KeySet {
            final Submap this$1;

            private KeySet(Submap submap) {
                this.this$1 = submap;
                super(submap);
            }

            @Override
            public ObjectBidirectionalIterator<K> iterator() {
                return new SubmapKeyIterator(this.this$1);
            }

            @Override
            public ObjectBidirectionalIterator<K> iterator(K k) {
                return new SubmapKeyIterator(this.this$1, k);
            }

            @Override
            public ObjectIterator iterator() {
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
    implements FloatListIterator {
        final Object2FloatRBTreeMap this$0;

        private ValueIterator(Object2FloatRBTreeMap object2FloatRBTreeMap) {
            this.this$0 = object2FloatRBTreeMap;
            super(object2FloatRBTreeMap);
        }

        @Override
        public float nextFloat() {
            return this.nextEntry().value;
        }

        @Override
        public float previousFloat() {
            return this.previousEntry().value;
        }

        ValueIterator(Object2FloatRBTreeMap object2FloatRBTreeMap, 1 var2_2) {
            this(object2FloatRBTreeMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class KeySet
    extends AbstractObject2FloatSortedMap.KeySet {
        final Object2FloatRBTreeMap this$0;

        private KeySet(Object2FloatRBTreeMap object2FloatRBTreeMap) {
            this.this$0 = object2FloatRBTreeMap;
            super(object2FloatRBTreeMap);
        }

        @Override
        public ObjectBidirectionalIterator<K> iterator() {
            return new KeyIterator(this.this$0);
        }

        @Override
        public ObjectBidirectionalIterator<K> iterator(K k) {
            return new KeyIterator(this.this$0, k);
        }

        @Override
        public ObjectIterator iterator() {
            return this.iterator();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        KeySet(Object2FloatRBTreeMap object2FloatRBTreeMap, 1 var2_2) {
            this(object2FloatRBTreeMap);
        }
    }

    private final class KeyIterator
    extends TreeIterator
    implements ObjectListIterator<K> {
        final Object2FloatRBTreeMap this$0;

        public KeyIterator(Object2FloatRBTreeMap object2FloatRBTreeMap) {
            this.this$0 = object2FloatRBTreeMap;
            super(object2FloatRBTreeMap);
        }

        public KeyIterator(Object2FloatRBTreeMap object2FloatRBTreeMap, K k) {
            this.this$0 = object2FloatRBTreeMap;
            super(object2FloatRBTreeMap, k);
        }

        @Override
        public K next() {
            return this.nextEntry().key;
        }

        @Override
        public K previous() {
            return this.previousEntry().key;
        }
    }

    private class EntryIterator
    extends TreeIterator
    implements ObjectListIterator<Object2FloatMap.Entry<K>> {
        final Object2FloatRBTreeMap this$0;

        EntryIterator(Object2FloatRBTreeMap object2FloatRBTreeMap) {
            this.this$0 = object2FloatRBTreeMap;
            super(object2FloatRBTreeMap);
        }

        EntryIterator(Object2FloatRBTreeMap object2FloatRBTreeMap, K k) {
            this.this$0 = object2FloatRBTreeMap;
            super(object2FloatRBTreeMap, k);
        }

        @Override
        public Object2FloatMap.Entry<K> next() {
            return this.nextEntry();
        }

        @Override
        public Object2FloatMap.Entry<K> previous() {
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

    private class TreeIterator {
        Entry<K> prev;
        Entry<K> next;
        Entry<K> curr;
        int index;
        final Object2FloatRBTreeMap this$0;

        TreeIterator(Object2FloatRBTreeMap object2FloatRBTreeMap) {
            this.this$0 = object2FloatRBTreeMap;
            this.index = 0;
            this.next = object2FloatRBTreeMap.firstEntry;
        }

        TreeIterator(Object2FloatRBTreeMap object2FloatRBTreeMap, K k) {
            this.this$0 = object2FloatRBTreeMap;
            this.index = 0;
            this.next = object2FloatRBTreeMap.locateKey(k);
            if (this.next != null) {
                if (object2FloatRBTreeMap.compare(this.next.key, k) <= 0) {
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

        Entry<K> nextEntry() {
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

        Entry<K> previousEntry() {
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
            this.this$0.removeFloat(this.curr.key);
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

    private static final class Entry<K>
    extends AbstractObject2FloatMap.BasicEntry<K>
    implements Cloneable {
        private static final int BLACK_MASK = 1;
        private static final int SUCC_MASK = Integer.MIN_VALUE;
        private static final int PRED_MASK = 0x40000000;
        Entry<K> left;
        Entry<K> right;
        int info;

        Entry() {
            super(null, 0.0f);
        }

        Entry(K k, float f) {
            super(k, f);
            this.info = -1073741824;
        }

        Entry<K> left() {
            return (this.info & 0x40000000) != 0 ? null : this.left;
        }

        Entry<K> right() {
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

        void pred(Entry<K> entry) {
            this.info |= 0x40000000;
            this.left = entry;
        }

        void succ(Entry<K> entry) {
            this.info |= Integer.MIN_VALUE;
            this.right = entry;
        }

        void left(Entry<K> entry) {
            this.info &= 0xBFFFFFFF;
            this.left = entry;
        }

        void right(Entry<K> entry) {
            this.info &= Integer.MAX_VALUE;
            this.right = entry;
        }

        boolean black() {
            return (this.info & 1) != 0;
        }

        void black(boolean bl) {
            this.info = bl ? (this.info |= 1) : (this.info &= 0xFFFFFFFE);
        }

        Entry<K> next() {
            Entry<K> entry = this.right;
            if ((this.info & Integer.MIN_VALUE) == 0) {
                while ((entry.info & 0x40000000) == 0) {
                    entry = entry.left;
                }
            }
            return entry;
        }

        Entry<K> prev() {
            Entry<K> entry = this.left;
            if ((this.info & 0x40000000) == 0) {
                while ((entry.info & Integer.MIN_VALUE) == 0) {
                    entry = entry.right;
                }
            }
            return entry;
        }

        @Override
        public float setValue(float f) {
            float f2 = this.value;
            this.value = f;
            return f2;
        }

        public Entry<K> clone() {
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
            return Objects.equals(this.key, entry.getKey()) && Float.floatToIntBits(this.value) == Float.floatToIntBits(((Float)entry.getValue()).floatValue());
        }

        @Override
        public int hashCode() {
            return this.key.hashCode() ^ HashCommon.float2int(this.value);
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

