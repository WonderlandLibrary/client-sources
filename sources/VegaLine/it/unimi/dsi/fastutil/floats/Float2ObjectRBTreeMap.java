/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.floats.AbstractFloat2ObjectSortedMap;
import it.unimi.dsi.fastutil.floats.Float2ObjectMap;
import it.unimi.dsi.fastutil.floats.Float2ObjectSortedMap;
import it.unimi.dsi.fastutil.floats.FloatBidirectionalIterator;
import it.unimi.dsi.fastutil.floats.FloatComparator;
import it.unimi.dsi.fastutil.floats.FloatListIterator;
import it.unimi.dsi.fastutil.floats.FloatSortedSet;
import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.SortedMap;

public class Float2ObjectRBTreeMap<V>
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
    private static final boolean ASSERTS = false;
    private transient boolean[] dirPath;
    private transient Entry<V>[] nodePath;

    public Float2ObjectRBTreeMap() {
        this.allocatePaths();
        this.tree = null;
        this.count = 0;
    }

    private void setActualComparator() {
        this.actualComparator = this.storedComparator == null || this.storedComparator instanceof FloatComparator ? (FloatComparator)this.storedComparator : new FloatComparator(){

            @Override
            public int compare(float k1, float k2) {
                return Float2ObjectRBTreeMap.this.storedComparator.compare(Float.valueOf(k1), Float.valueOf(k2));
            }

            @Override
            public int compare(Float ok1, Float ok2) {
                return Float2ObjectRBTreeMap.this.storedComparator.compare(ok1, ok2);
            }
        };
    }

    public Float2ObjectRBTreeMap(Comparator<? super Float> c) {
        this();
        this.storedComparator = c;
    }

    public Float2ObjectRBTreeMap(Map<? extends Float, ? extends V> m) {
        this();
        this.putAll((Map<Float, ? extends V>)m);
    }

    public Float2ObjectRBTreeMap(SortedMap<Float, V> m) {
        this(m.comparator());
        this.putAll((Map<Float, V>)m);
    }

    public Float2ObjectRBTreeMap(Float2ObjectMap<? extends V> m) {
        this();
        this.putAll(m);
    }

    public Float2ObjectRBTreeMap(Float2ObjectSortedMap<V> m) {
        this(m.comparator());
        this.putAll(m);
    }

    public Float2ObjectRBTreeMap(float[] k, V[] v, Comparator<? super Float> c) {
        this(c);
        if (k.length != v.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
        }
        for (int i = 0; i < k.length; ++i) {
            this.put(k[i], v[i]);
        }
    }

    public Float2ObjectRBTreeMap(float[] k, V[] v) {
        this(k, v, null);
    }

    final int compare(float k1, float k2) {
        return this.actualComparator == null ? Float.compare(k1, k2) : this.actualComparator.compare(k1, k2);
    }

    final Entry<V> findKey(float k) {
        int cmp;
        Entry<V> e = this.tree;
        while (e != null && (cmp = this.compare(k, e.key)) != 0) {
            e = cmp < 0 ? e.left() : e.right();
        }
        return e;
    }

    final Entry<V> locateKey(float k) {
        Entry<V> e = this.tree;
        Entry<V> last = this.tree;
        int cmp = 0;
        while (e != null && (cmp = this.compare(k, e.key)) != 0) {
            last = e;
            e = cmp < 0 ? e.left() : e.right();
        }
        return cmp == 0 ? e : last;
    }

    private void allocatePaths() {
        this.dirPath = new boolean[64];
        this.nodePath = new Entry[64];
    }

    @Override
    public V put(float k, V v) {
        Entry<V> e = this.add(k);
        Object oldValue = e.value;
        e.value = v;
        return oldValue;
    }

    private Entry<V> add(float k) {
        Entry<Object> e;
        this.modified = false;
        int maxDepth = 0;
        if (this.tree == null) {
            ++this.count;
            this.firstEntry = new Entry<Object>(k, this.defRetValue);
            this.lastEntry = this.firstEntry;
            this.tree = this.firstEntry;
            e = this.firstEntry;
        } else {
            Entry<Object> p = this.tree;
            int i = 0;
            while (true) {
                int cmp;
                if ((cmp = this.compare(k, p.key)) == 0) {
                    while (i-- != 0) {
                        this.nodePath[i] = null;
                    }
                    return p;
                }
                this.nodePath[i] = p;
                this.dirPath[i++] = cmp > 0;
                if (this.dirPath[i++]) {
                    if (p.succ()) {
                        ++this.count;
                        e = new Entry<Object>(k, this.defRetValue);
                        if (p.right == null) {
                            this.lastEntry = e;
                        }
                        e.left = p;
                        e.right = p.right;
                        p.right(e);
                        break;
                    }
                    p = p.right;
                    continue;
                }
                if (p.pred()) {
                    ++this.count;
                    e = new Entry<Object>(k, this.defRetValue);
                    if (p.left == null) {
                        this.firstEntry = e;
                    }
                    e.right = p;
                    e.left = p.left;
                    p.left(e);
                    break;
                }
                p = p.left;
            }
            this.modified = true;
            maxDepth = i--;
            while (i > 0 && !this.nodePath[i].black()) {
                Entry x;
                Entry y;
                if (!this.dirPath[i - 1]) {
                    y = this.nodePath[i - 1].right;
                    if (!this.nodePath[i - 1].succ() && !y.black()) {
                        this.nodePath[i].black(true);
                        y.black(true);
                        this.nodePath[i - 1].black(false);
                        i -= 2;
                        continue;
                    }
                    if (!this.dirPath[i]) {
                        y = this.nodePath[i];
                    } else {
                        x = this.nodePath[i];
                        y = x.right;
                        x.right = y.left;
                        y.left = x;
                        this.nodePath[i - 1].left = y;
                        if (y.pred()) {
                            y.pred(false);
                            x.succ(y);
                        }
                    }
                    x = this.nodePath[i - 1];
                    x.black(false);
                    y.black(true);
                    x.left = y.right;
                    y.right = x;
                    if (i < 2) {
                        this.tree = y;
                    } else if (this.dirPath[i - 2]) {
                        this.nodePath[i - 2].right = y;
                    } else {
                        this.nodePath[i - 2].left = y;
                    }
                    if (!y.succ()) break;
                    y.succ(false);
                    x.pred(y);
                    break;
                }
                y = this.nodePath[i - 1].left;
                if (!this.nodePath[i - 1].pred() && !y.black()) {
                    this.nodePath[i].black(true);
                    y.black(true);
                    this.nodePath[i - 1].black(false);
                    i -= 2;
                    continue;
                }
                if (this.dirPath[i]) {
                    y = this.nodePath[i];
                } else {
                    x = this.nodePath[i];
                    y = x.left;
                    x.left = y.right;
                    y.right = x;
                    this.nodePath[i - 1].right = y;
                    if (y.succ()) {
                        y.succ(false);
                        x.pred(y);
                    }
                }
                x = this.nodePath[i - 1];
                x.black(false);
                y.black(true);
                x.right = y.left;
                y.left = x;
                if (i < 2) {
                    this.tree = y;
                } else if (this.dirPath[i - 2]) {
                    this.nodePath[i - 2].right = y;
                } else {
                    this.nodePath[i - 2].left = y;
                }
                if (!y.pred()) break;
                y.pred(false);
                x.succ(y);
                break;
            }
        }
        this.tree.black(true);
        while (maxDepth-- != 0) {
            this.nodePath[maxDepth] = null;
        }
        return e;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public V remove(float k) {
        int i;
        Entry<V> p;
        block68: {
            Entry s;
            int j;
            boolean color;
            block74: {
                block69: {
                    Entry r;
                    block73: {
                        block71: {
                            block72: {
                                block66: {
                                    block67: {
                                        this.modified = false;
                                        if (this.tree == null) {
                                            return (V)this.defRetValue;
                                        }
                                        p = this.tree;
                                        i = 0;
                                        float kk = k;
                                        while (true) {
                                            int cmp;
                                            if ((cmp = this.compare(kk, p.key)) == 0) {
                                                if (p.left != null) break block66;
                                                break block67;
                                            }
                                            this.dirPath[i] = cmp > 0;
                                            this.nodePath[i] = p;
                                            if (this.dirPath[i++]) {
                                                if ((p = p.right()) != null) continue;
                                                while (true) {
                                                    if (i-- == 0) {
                                                        return (V)this.defRetValue;
                                                    }
                                                    this.nodePath[i] = null;
                                                }
                                            }
                                            if ((p = p.left()) == null) break;
                                        }
                                        while (true) {
                                            if (i-- == 0) {
                                                return (V)this.defRetValue;
                                            }
                                            this.nodePath[i] = null;
                                        }
                                    }
                                    this.firstEntry = p.next();
                                }
                                if (p.right == null) {
                                    this.lastEntry = p.prev();
                                }
                                if (!p.succ()) break block71;
                                if (!p.pred()) break block72;
                                if (i == 0) {
                                    this.tree = p.left;
                                    break block68;
                                } else if (this.dirPath[i - 1]) {
                                    this.nodePath[i - 1].succ(p.right);
                                    break block68;
                                } else {
                                    this.nodePath[i - 1].pred(p.left);
                                }
                                break block68;
                            }
                            p.prev().right = p.right;
                            if (i == 0) {
                                this.tree = p.left;
                                break block68;
                            } else if (this.dirPath[i - 1]) {
                                this.nodePath[i - 1].right = p.left;
                                break block68;
                            } else {
                                this.nodePath[i - 1].left = p.left;
                            }
                            break block68;
                        }
                        r = p.right;
                        if (!r.pred()) break block73;
                        r.left = p.left;
                        r.pred(p.pred());
                        if (!r.pred()) {
                            r.prev().right = r;
                        }
                        if (i == 0) {
                            this.tree = r;
                        } else if (this.dirPath[i - 1]) {
                            this.nodePath[i - 1].right = r;
                        } else {
                            this.nodePath[i - 1].left = r;
                        }
                        color = r.black();
                        r.black(p.black());
                        p.black(color);
                        this.dirPath[i] = true;
                        this.nodePath[i++] = r;
                        break block68;
                    }
                    j = i++;
                    while (true) {
                        this.dirPath[i] = false;
                        this.nodePath[i++] = r;
                        s = r.left;
                        if (s.pred()) {
                            this.dirPath[j] = true;
                            this.nodePath[j] = s;
                            if (s.succ()) {
                                break;
                            }
                            break block69;
                        }
                        r = s;
                    }
                    r.pred(s);
                    break block74;
                }
                r.left = s.right;
            }
            s.left = p.left;
            if (!p.pred()) {
                p.prev().right = s;
                s.pred(false);
            }
            s.right(p.right);
            color = s.black();
            s.black(p.black());
            p.black(color);
            if (j == 0) {
                this.tree = s;
            } else if (this.dirPath[j - 1]) {
                this.nodePath[j - 1].right = s;
            } else {
                this.nodePath[j - 1].left = s;
            }
        }
        int maxDepth = i;
        if (p.black()) {
            while (i > 0) {
                block70: {
                    Entry y;
                    Entry w;
                    if (this.dirPath[i - 1] && !this.nodePath[i - 1].succ() || !this.dirPath[i - 1] && !this.nodePath[i - 1].pred()) {
                        Entry x;
                        Entry entry = x = this.dirPath[i - 1] ? this.nodePath[i - 1].right : this.nodePath[i - 1].left;
                        if (!x.black()) {
                            x.black(true);
                            break;
                        }
                    }
                    if (!this.dirPath[i - 1]) {
                        w = this.nodePath[i - 1].right;
                        if (!w.black()) {
                            w.black(true);
                            this.nodePath[i - 1].black(false);
                            this.nodePath[i - 1].right = w.left;
                            w.left = this.nodePath[i - 1];
                            if (i < 2) {
                                this.tree = w;
                            } else if (this.dirPath[i - 2]) {
                                this.nodePath[i - 2].right = w;
                            } else {
                                this.nodePath[i - 2].left = w;
                            }
                            this.nodePath[i] = this.nodePath[i - 1];
                            this.dirPath[i] = false;
                            this.nodePath[i - 1] = w;
                            if (maxDepth == i++) {
                                ++maxDepth;
                            }
                            w = this.nodePath[i - 1].right;
                        }
                        if ((w.pred() || w.left.black()) && (w.succ() || w.right.black())) {
                            w.black(false);
                            break block70;
                        } else {
                            if (w.succ() || w.right.black()) {
                                y = w.left;
                                y.black(true);
                                w.black(false);
                                w.left = y.right;
                                y.right = w;
                                this.nodePath[i - 1].right = y;
                                w = this.nodePath[i - 1].right;
                                if (w.succ()) {
                                    w.succ(false);
                                    w.right.pred(w);
                                }
                            }
                            w.black(this.nodePath[i - 1].black());
                            this.nodePath[i - 1].black(true);
                            w.right.black(true);
                            this.nodePath[i - 1].right = w.left;
                            w.left = this.nodePath[i - 1];
                            if (i < 2) {
                                this.tree = w;
                            } else if (this.dirPath[i - 2]) {
                                this.nodePath[i - 2].right = w;
                            } else {
                                this.nodePath[i - 2].left = w;
                            }
                            if (!w.pred()) break;
                            w.pred(false);
                            this.nodePath[i - 1].succ(w);
                            break;
                        }
                    }
                    w = this.nodePath[i - 1].left;
                    if (!w.black()) {
                        w.black(true);
                        this.nodePath[i - 1].black(false);
                        this.nodePath[i - 1].left = w.right;
                        w.right = this.nodePath[i - 1];
                        if (i < 2) {
                            this.tree = w;
                        } else if (this.dirPath[i - 2]) {
                            this.nodePath[i - 2].right = w;
                        } else {
                            this.nodePath[i - 2].left = w;
                        }
                        this.nodePath[i] = this.nodePath[i - 1];
                        this.dirPath[i] = true;
                        this.nodePath[i - 1] = w;
                        if (maxDepth == i++) {
                            ++maxDepth;
                        }
                        w = this.nodePath[i - 1].left;
                    }
                    if ((w.pred() || w.left.black()) && (w.succ() || w.right.black())) {
                        w.black(false);
                    } else {
                        if (w.pred() || w.left.black()) {
                            y = w.right;
                            y.black(true);
                            w.black(false);
                            w.right = y.left;
                            y.left = w;
                            this.nodePath[i - 1].left = y;
                            w = this.nodePath[i - 1].left;
                            if (w.pred()) {
                                w.pred(false);
                                w.left.succ(w);
                            }
                        }
                        w.black(this.nodePath[i - 1].black());
                        this.nodePath[i - 1].black(true);
                        w.left.black(true);
                        this.nodePath[i - 1].left = w.right;
                        w.right = this.nodePath[i - 1];
                        if (i < 2) {
                            this.tree = w;
                        } else if (this.dirPath[i - 2]) {
                            this.nodePath[i - 2].right = w;
                        } else {
                            this.nodePath[i - 2].left = w;
                        }
                        if (!w.succ()) break;
                        w.succ(false);
                        this.nodePath[i - 1].pred(w);
                        break;
                    }
                }
                --i;
            }
            if (this.tree != null) {
                this.tree.black(true);
            }
        }
        this.modified = true;
        --this.count;
        while (maxDepth-- != 0) {
            this.nodePath[maxDepth] = null;
        }
        return p.value;
    }

    @Override
    @Deprecated
    public V put(Float ok, V ov) {
        V oldValue = this.put(ok.floatValue(), ov);
        return (V)(this.modified ? this.defRetValue : oldValue);
    }

    @Override
    @Deprecated
    public V remove(Object ok) {
        V oldValue = this.remove(((Float)ok).floatValue());
        return (V)(this.modified ? oldValue : this.defRetValue);
    }

    @Override
    public boolean containsValue(Object v) {
        ValueIterator i = new ValueIterator();
        int j = this.count;
        while (j-- != 0) {
            Object ev = i.next();
            if (!(ev == null ? v == null : ev.equals(v))) continue;
            return true;
        }
        return false;
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
    public boolean containsKey(float k) {
        return this.findKey(k) != null;
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
    public V get(float k) {
        Entry<V> e = this.findKey(k);
        return (V)(e == null ? this.defRetValue : e.value);
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
            this.entries = new AbstractObjectSortedSet<Float2ObjectMap.Entry<V>>(){
                final Comparator<? super Float2ObjectMap.Entry<V>> comparator = new Comparator<Float2ObjectMap.Entry<V>>(){

                    @Override
                    public int compare(Float2ObjectMap.Entry<V> x, Float2ObjectMap.Entry<V> y) {
                        return Float2ObjectRBTreeMap.this.actualComparator.compare(x.getFloatKey(), y.getFloatKey());
                    }
                };

                @Override
                public Comparator<? super Float2ObjectMap.Entry<V>> comparator() {
                    return this.comparator;
                }

                @Override
                public ObjectBidirectionalIterator<Float2ObjectMap.Entry<V>> iterator() {
                    return new EntryIterator();
                }

                @Override
                public ObjectBidirectionalIterator<Float2ObjectMap.Entry<V>> iterator(Float2ObjectMap.Entry<V> from) {
                    return new EntryIterator(from.getFloatKey());
                }

                @Override
                public boolean contains(Object o) {
                    if (!(o instanceof Map.Entry)) {
                        return false;
                    }
                    Map.Entry e = (Map.Entry)o;
                    if (e.getKey() == null || !(e.getKey() instanceof Float)) {
                        return false;
                    }
                    Entry f = Float2ObjectRBTreeMap.this.findKey(((Float)e.getKey()).floatValue());
                    return e.equals(f);
                }

                @Override
                public boolean remove(Object o) {
                    if (!(o instanceof Map.Entry)) {
                        return false;
                    }
                    Map.Entry e = (Map.Entry)o;
                    if (e.getKey() == null || !(e.getKey() instanceof Float)) {
                        return false;
                    }
                    Entry f = Float2ObjectRBTreeMap.this.findKey(((Float)e.getKey()).floatValue());
                    if (f != null) {
                        Float2ObjectRBTreeMap.this.remove(f.key);
                    }
                    return f != null;
                }

                @Override
                public int size() {
                    return Float2ObjectRBTreeMap.this.count;
                }

                @Override
                public void clear() {
                    Float2ObjectRBTreeMap.this.clear();
                }

                @Override
                public Float2ObjectMap.Entry<V> first() {
                    return Float2ObjectRBTreeMap.this.firstEntry;
                }

                @Override
                public Float2ObjectMap.Entry<V> last() {
                    return Float2ObjectRBTreeMap.this.lastEntry;
                }

                @Override
                public ObjectSortedSet<Float2ObjectMap.Entry<V>> subSet(Float2ObjectMap.Entry<V> from, Float2ObjectMap.Entry<V> to) {
                    return Float2ObjectRBTreeMap.this.subMap(from.getFloatKey(), to.getFloatKey()).float2ObjectEntrySet();
                }

                @Override
                public ObjectSortedSet<Float2ObjectMap.Entry<V>> headSet(Float2ObjectMap.Entry<V> to) {
                    return Float2ObjectRBTreeMap.this.headMap(to.getFloatKey()).float2ObjectEntrySet();
                }

                @Override
                public ObjectSortedSet<Float2ObjectMap.Entry<V>> tailSet(Float2ObjectMap.Entry<V> from) {
                    return Float2ObjectRBTreeMap.this.tailMap(from.getFloatKey()).float2ObjectEntrySet();
                }
            };
        }
        return this.entries;
    }

    @Override
    public FloatSortedSet keySet() {
        if (this.keys == null) {
            this.keys = new KeySet();
        }
        return this.keys;
    }

    @Override
    public ObjectCollection<V> values() {
        if (this.values == null) {
            this.values = new AbstractObjectCollection<V>(){

                @Override
                public ObjectIterator<V> iterator() {
                    return new ValueIterator();
                }

                @Override
                public boolean contains(Object k) {
                    return Float2ObjectRBTreeMap.this.containsValue(k);
                }

                @Override
                public int size() {
                    return Float2ObjectRBTreeMap.this.count;
                }

                @Override
                public void clear() {
                    Float2ObjectRBTreeMap.this.clear();
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
    public Float2ObjectSortedMap<V> headMap(float to) {
        return new Submap(0.0f, true, to, false);
    }

    @Override
    public Float2ObjectSortedMap<V> tailMap(float from) {
        return new Submap(from, false, 0.0f, true);
    }

    @Override
    public Float2ObjectSortedMap<V> subMap(float from, float to) {
        return new Submap(from, false, to, false);
    }

    public Float2ObjectRBTreeMap<V> clone() {
        Float2ObjectRBTreeMap c;
        try {
            c = (Float2ObjectRBTreeMap)super.clone();
        } catch (CloneNotSupportedException cantHappen) {
            throw new InternalError();
        }
        c.keys = null;
        c.values = null;
        c.entries = null;
        c.allocatePaths();
        if (this.count != 0) {
            Entry<V> rp = new Entry<V>();
            Entry rq = new Entry();
            Entry<V> p = rp;
            rp.left(this.tree);
            Entry q = rq;
            rq.pred(null);
            while (true) {
                Object e;
                if (!p.pred()) {
                    e = p.left.clone();
                    ((Entry)e).pred(q.left);
                    ((Entry)e).succ(q);
                    q.left(e);
                    p = p.left;
                    q = q.left;
                } else {
                    while (p.succ()) {
                        p = p.right;
                        if (p == null) {
                            q.right = null;
                            c.tree = rq.left;
                            c.firstEntry = c.tree;
                            while (c.firstEntry.left != null) {
                                c.firstEntry = c.firstEntry.left;
                            }
                            c.lastEntry = c.tree;
                            while (c.lastEntry.right != null) {
                                c.lastEntry = c.lastEntry.right;
                            }
                            return c;
                        }
                        q = q.right;
                    }
                    p = p.right;
                    q = q.right;
                }
                if (p.succ()) continue;
                e = p.right.clone();
                ((Entry)e).succ(q.right);
                ((Entry)e).pred(q);
                q.right(e);
            }
        }
        return c;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        int n = this.count;
        EntryIterator i = new EntryIterator();
        s.defaultWriteObject();
        while (n-- != 0) {
            Entry e = i.nextEntry();
            s.writeFloat(e.key);
            s.writeObject(e.value);
        }
    }

    private Entry<V> readTree(ObjectInputStream s, int n, Entry<V> pred, Entry<V> succ) throws IOException, ClassNotFoundException {
        if (n == 1) {
            Entry<Object> top = new Entry<Object>(s.readFloat(), s.readObject());
            top.pred(pred);
            top.succ(succ);
            top.black(true);
            return top;
        }
        if (n == 2) {
            Entry<Object> top = new Entry<Object>(s.readFloat(), s.readObject());
            top.black(true);
            top.right(new Entry<Object>(s.readFloat(), s.readObject()));
            top.right.pred(top);
            top.pred(pred);
            top.right.succ(succ);
            return top;
        }
        int rightN = n / 2;
        int leftN = n - rightN - 1;
        Entry top = new Entry();
        top.left(this.readTree(s, leftN, pred, top));
        top.key = s.readFloat();
        top.value = s.readObject();
        top.black(true);
        top.right(this.readTree(s, rightN, top, succ));
        if (n + 2 == (n + 2 & -(n + 2))) {
            top.right.black(false);
        }
        return top;
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.setActualComparator();
        this.allocatePaths();
        if (this.count != 0) {
            this.tree = this.readTree(s, this.count, null, null);
            Entry<V> e = this.tree;
            while (e.left() != null) {
                e = e.left();
            }
            this.firstEntry = e;
            e = this.tree;
            while (e.right() != null) {
                e = e.right();
            }
            this.lastEntry = e;
        }
    }

    private void checkNodePath() {
    }

    private static <V> int checkTree(Entry<V> e, int d, int D) {
        return 0;
    }

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

        public Submap(float from, boolean bottom, float to, boolean top) {
            if (!bottom && !top && Float2ObjectRBTreeMap.this.compare(from, to) > 0) {
                throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")");
            }
            this.from = from;
            this.bottom = bottom;
            this.to = to;
            this.top = top;
            this.defRetValue = Float2ObjectRBTreeMap.this.defRetValue;
        }

        @Override
        public void clear() {
            SubmapIterator i = new SubmapIterator();
            while (i.hasNext()) {
                i.nextEntry();
                i.remove();
            }
        }

        final boolean in(float k) {
            return !(!this.bottom && Float2ObjectRBTreeMap.this.compare(k, this.from) < 0 || !this.top && Float2ObjectRBTreeMap.this.compare(k, this.to) >= 0);
        }

        @Override
        public ObjectSortedSet<Float2ObjectMap.Entry<V>> float2ObjectEntrySet() {
            if (this.entries == null) {
                this.entries = new AbstractObjectSortedSet<Float2ObjectMap.Entry<V>>(){

                    @Override
                    public ObjectBidirectionalIterator<Float2ObjectMap.Entry<V>> iterator() {
                        return new SubmapEntryIterator();
                    }

                    @Override
                    public ObjectBidirectionalIterator<Float2ObjectMap.Entry<V>> iterator(Float2ObjectMap.Entry<V> from) {
                        return new SubmapEntryIterator(from.getFloatKey());
                    }

                    @Override
                    public Comparator<? super Float2ObjectMap.Entry<V>> comparator() {
                        return Float2ObjectRBTreeMap.this.float2ObjectEntrySet().comparator();
                    }

                    @Override
                    public boolean contains(Object o) {
                        if (!(o instanceof Map.Entry)) {
                            return false;
                        }
                        Map.Entry e = (Map.Entry)o;
                        if (e.getKey() == null || !(e.getKey() instanceof Float)) {
                            return false;
                        }
                        Entry f = Float2ObjectRBTreeMap.this.findKey(((Float)e.getKey()).floatValue());
                        return f != null && Submap.this.in(f.key) && e.equals(f);
                    }

                    @Override
                    public boolean remove(Object o) {
                        if (!(o instanceof Map.Entry)) {
                            return false;
                        }
                        Map.Entry e = (Map.Entry)o;
                        if (e.getKey() == null || !(e.getKey() instanceof Float)) {
                            return false;
                        }
                        Entry f = Float2ObjectRBTreeMap.this.findKey(((Float)e.getKey()).floatValue());
                        if (f != null && Submap.this.in(f.key)) {
                            Submap.this.remove(f.key);
                        }
                        return f != null;
                    }

                    @Override
                    public int size() {
                        int c = 0;
                        ObjectIterator i = this.iterator();
                        while (i.hasNext()) {
                            ++c;
                            i.next();
                        }
                        return c;
                    }

                    @Override
                    public boolean isEmpty() {
                        return !new SubmapIterator().hasNext();
                    }

                    @Override
                    public void clear() {
                        Submap.this.clear();
                    }

                    @Override
                    public Float2ObjectMap.Entry<V> first() {
                        return Submap.this.firstEntry();
                    }

                    @Override
                    public Float2ObjectMap.Entry<V> last() {
                        return Submap.this.lastEntry();
                    }

                    @Override
                    public ObjectSortedSet<Float2ObjectMap.Entry<V>> subSet(Float2ObjectMap.Entry<V> from, Float2ObjectMap.Entry<V> to) {
                        return Submap.this.subMap(from.getFloatKey(), to.getFloatKey()).float2ObjectEntrySet();
                    }

                    @Override
                    public ObjectSortedSet<Float2ObjectMap.Entry<V>> headSet(Float2ObjectMap.Entry<V> to) {
                        return Submap.this.headMap(to.getFloatKey()).float2ObjectEntrySet();
                    }

                    @Override
                    public ObjectSortedSet<Float2ObjectMap.Entry<V>> tailSet(Float2ObjectMap.Entry<V> from) {
                        return Submap.this.tailMap(from.getFloatKey()).float2ObjectEntrySet();
                    }
                };
            }
            return this.entries;
        }

        @Override
        public FloatSortedSet keySet() {
            if (this.keys == null) {
                this.keys = new KeySet();
            }
            return this.keys;
        }

        @Override
        public ObjectCollection<V> values() {
            if (this.values == null) {
                this.values = new AbstractObjectCollection<V>(){

                    @Override
                    public ObjectIterator<V> iterator() {
                        return new SubmapValueIterator();
                    }

                    @Override
                    public boolean contains(Object k) {
                        return Submap.this.containsValue(k);
                    }

                    @Override
                    public int size() {
                        return Submap.this.size();
                    }

                    @Override
                    public void clear() {
                        Submap.this.clear();
                    }
                };
            }
            return this.values;
        }

        @Override
        public boolean containsKey(float k) {
            return this.in(k) && Float2ObjectRBTreeMap.this.containsKey(k);
        }

        @Override
        public boolean containsValue(Object v) {
            SubmapIterator i = new SubmapIterator();
            while (i.hasNext()) {
                Object ev = i.nextEntry().value;
                if (!(ev == null ? v == null : ev.equals(v))) continue;
                return true;
            }
            return false;
        }

        @Override
        public V get(float k) {
            Entry e;
            float kk = k;
            return this.in(kk) && (e = Float2ObjectRBTreeMap.this.findKey(kk)) != null ? e.value : this.defRetValue;
        }

        @Override
        public V put(float k, V v) {
            Float2ObjectRBTreeMap.this.modified = false;
            if (!this.in(k)) {
                throw new IllegalArgumentException("Key (" + k + ") out of range [" + (this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")");
            }
            Object oldValue = Float2ObjectRBTreeMap.this.put(k, v);
            return Float2ObjectRBTreeMap.this.modified ? this.defRetValue : oldValue;
        }

        @Override
        @Deprecated
        public V put(Float ok, V ov) {
            Object oldValue = this.put(ok.floatValue(), (V)ov);
            return Float2ObjectRBTreeMap.this.modified ? this.defRetValue : oldValue;
        }

        @Override
        public V remove(float k) {
            Float2ObjectRBTreeMap.this.modified = false;
            if (!this.in(k)) {
                return this.defRetValue;
            }
            Object oldValue = Float2ObjectRBTreeMap.this.remove(k);
            return Float2ObjectRBTreeMap.this.modified ? oldValue : this.defRetValue;
        }

        @Override
        @Deprecated
        public V remove(Object ok) {
            Object oldValue = this.remove(((Float)ok).floatValue());
            return Float2ObjectRBTreeMap.this.modified ? oldValue : this.defRetValue;
        }

        @Override
        public int size() {
            SubmapIterator i = new SubmapIterator();
            int n = 0;
            while (i.hasNext()) {
                ++n;
                i.nextEntry();
            }
            return n;
        }

        @Override
        public boolean isEmpty() {
            return !new SubmapIterator().hasNext();
        }

        @Override
        public FloatComparator comparator() {
            return Float2ObjectRBTreeMap.this.actualComparator;
        }

        @Override
        public Float2ObjectSortedMap<V> headMap(float to) {
            if (this.top) {
                return new Submap(this.from, this.bottom, to, false);
            }
            return Float2ObjectRBTreeMap.this.compare(to, this.to) < 0 ? new Submap(this.from, this.bottom, to, false) : this;
        }

        @Override
        public Float2ObjectSortedMap<V> tailMap(float from) {
            if (this.bottom) {
                return new Submap(from, false, this.to, this.top);
            }
            return Float2ObjectRBTreeMap.this.compare(from, this.from) > 0 ? new Submap(from, false, this.to, this.top) : this;
        }

        @Override
        public Float2ObjectSortedMap<V> subMap(float from, float to) {
            if (this.top && this.bottom) {
                return new Submap(from, false, to, false);
            }
            if (!this.top) {
                float f = to = Float2ObjectRBTreeMap.this.compare(to, this.to) < 0 ? to : this.to;
            }
            if (!this.bottom) {
                float f = from = Float2ObjectRBTreeMap.this.compare(from, this.from) > 0 ? from : this.from;
            }
            if (!this.top && !this.bottom && from == this.from && to == this.to) {
                return this;
            }
            return new Submap(from, false, to, false);
        }

        public Entry<V> firstEntry() {
            Entry e;
            if (Float2ObjectRBTreeMap.this.tree == null) {
                return null;
            }
            if (this.bottom) {
                e = Float2ObjectRBTreeMap.this.firstEntry;
            } else {
                e = Float2ObjectRBTreeMap.this.locateKey(this.from);
                if (Float2ObjectRBTreeMap.this.compare(e.key, this.from) < 0) {
                    e = e.next();
                }
            }
            if (e == null || !this.top && Float2ObjectRBTreeMap.this.compare(e.key, this.to) >= 0) {
                return null;
            }
            return e;
        }

        public Entry<V> lastEntry() {
            Entry e;
            if (Float2ObjectRBTreeMap.this.tree == null) {
                return null;
            }
            if (this.top) {
                e = Float2ObjectRBTreeMap.this.lastEntry;
            } else {
                e = Float2ObjectRBTreeMap.this.locateKey(this.to);
                if (Float2ObjectRBTreeMap.this.compare(e.key, this.to) >= 0) {
                    e = e.prev();
                }
            }
            if (e == null || !this.bottom && Float2ObjectRBTreeMap.this.compare(e.key, this.from) < 0) {
                return null;
            }
            return e;
        }

        @Override
        public float firstFloatKey() {
            Entry e = this.firstEntry();
            if (e == null) {
                throw new NoSuchElementException();
            }
            return e.key;
        }

        @Override
        public float lastFloatKey() {
            Entry e = this.lastEntry();
            if (e == null) {
                throw new NoSuchElementException();
            }
            return e.key;
        }

        @Override
        @Deprecated
        public Float firstKey() {
            Entry e = this.firstEntry();
            if (e == null) {
                throw new NoSuchElementException();
            }
            return e.getKey();
        }

        @Override
        @Deprecated
        public Float lastKey() {
            Entry e = this.lastEntry();
            if (e == null) {
                throw new NoSuchElementException();
            }
            return e.getKey();
        }

        /*
         * Signature claims super is it.unimi.dsi.fastutil.floats.Float2ObjectRBTreeMap$Submap.SubmapIterator, not it.unimi.dsi.fastutil.floats.Float2ObjectRBTreeMap$Submap$SubmapIterator - discarding signature.
         */
        private final class SubmapValueIterator
        extends SubmapIterator
        implements ObjectListIterator {
            private SubmapValueIterator() {
            }

            @Override
            public V next() {
                return this.nextEntry().value;
            }

            @Override
            public V previous() {
                return this.previousEntry().value;
            }

            @Override
            public void set(V v) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void add(V v) {
                throw new UnsupportedOperationException();
            }
        }

        /*
         * Signature claims super is it.unimi.dsi.fastutil.floats.Float2ObjectRBTreeMap$Submap.SubmapIterator, not it.unimi.dsi.fastutil.floats.Float2ObjectRBTreeMap$Submap$SubmapIterator - discarding signature.
         */
        private final class SubmapKeyIterator
        extends SubmapIterator
        implements FloatListIterator {
            public SubmapKeyIterator() {
            }

            public SubmapKeyIterator(float from) {
                super(from);
            }

            @Override
            public float nextFloat() {
                return this.nextEntry().key;
            }

            @Override
            public float previousFloat() {
                return this.previousEntry().key;
            }

            @Override
            public void set(float k) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void add(float k) {
                throw new UnsupportedOperationException();
            }

            @Override
            public Float next() {
                return Float.valueOf(this.nextEntry().key);
            }

            @Override
            public Float previous() {
                return Float.valueOf(this.previousEntry().key);
            }

            @Override
            public void set(Float ok) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void add(Float ok) {
                throw new UnsupportedOperationException();
            }
        }

        /*
         * Signature claims super is it.unimi.dsi.fastutil.floats.Float2ObjectRBTreeMap$Submap.SubmapIterator, not it.unimi.dsi.fastutil.floats.Float2ObjectRBTreeMap$Submap$SubmapIterator - discarding signature.
         */
        private class SubmapEntryIterator
        extends SubmapIterator
        implements ObjectListIterator {
            SubmapEntryIterator() {
            }

            SubmapEntryIterator(float k) {
                super(k);
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
            public void set(Float2ObjectMap.Entry<V> ok) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void add(Float2ObjectMap.Entry<V> ok) {
                throw new UnsupportedOperationException();
            }
        }

        private class SubmapIterator
        extends TreeIterator {
            SubmapIterator() {
                this.next = Submap.this.firstEntry();
            }

            /*
             * Enabled aggressive block sorting
             */
            SubmapIterator(float k) {
                this();
                if (this.next == null) return;
                if (!submap.bottom && submap.Float2ObjectRBTreeMap.this.compare(k, this.next.key) < 0) {
                    this.prev = null;
                    return;
                }
                if (!submap.top) {
                    this.prev = submap.lastEntry();
                    if (submap.Float2ObjectRBTreeMap.this.compare(k, this.prev.key) >= 0) {
                        this.next = null;
                        return;
                    }
                }
                this.next = submap.Float2ObjectRBTreeMap.this.locateKey(k);
                if (submap.Float2ObjectRBTreeMap.this.compare(this.next.key, k) <= 0) {
                    this.prev = this.next;
                    this.next = this.next.next();
                    return;
                }
                this.prev = this.next.prev();
            }

            @Override
            void updatePrevious() {
                this.prev = this.prev.prev();
                if (!Submap.this.bottom && this.prev != null && Float2ObjectRBTreeMap.this.compare(this.prev.key, Submap.this.from) < 0) {
                    this.prev = null;
                }
            }

            @Override
            void updateNext() {
                this.next = this.next.next();
                if (!Submap.this.top && this.next != null && Float2ObjectRBTreeMap.this.compare(this.next.key, Submap.this.to) >= 0) {
                    this.next = null;
                }
            }
        }

        private class KeySet
        extends AbstractFloat2ObjectSortedMap.KeySet {
            private KeySet() {
            }

            @Override
            public FloatBidirectionalIterator iterator() {
                return new SubmapKeyIterator();
            }

            @Override
            public FloatBidirectionalIterator iterator(float from) {
                return new SubmapKeyIterator(from);
            }
        }
    }

    private final class ValueIterator
    extends TreeIterator
    implements ObjectListIterator<V> {
        private ValueIterator() {
        }

        @Override
        public V next() {
            return this.nextEntry().value;
        }

        @Override
        public V previous() {
            return this.previousEntry().value;
        }

        @Override
        public void set(V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(V v) {
            throw new UnsupportedOperationException();
        }
    }

    private class KeySet
    extends AbstractFloat2ObjectSortedMap.KeySet {
        private KeySet() {
        }

        @Override
        public FloatBidirectionalIterator iterator() {
            return new KeyIterator();
        }

        @Override
        public FloatBidirectionalIterator iterator(float from) {
            return new KeyIterator(from);
        }
    }

    private final class KeyIterator
    extends TreeIterator
    implements FloatListIterator {
        public KeyIterator() {
        }

        public KeyIterator(float k) {
            super(k);
        }

        @Override
        public float nextFloat() {
            return this.nextEntry().key;
        }

        @Override
        public float previousFloat() {
            return this.previousEntry().key;
        }

        @Override
        public void set(float k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(float k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Float next() {
            return Float.valueOf(this.nextEntry().key);
        }

        @Override
        public Float previous() {
            return Float.valueOf(this.previousEntry().key);
        }

        @Override
        public void set(Float ok) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(Float ok) {
            throw new UnsupportedOperationException();
        }
    }

    private class EntryIterator
    extends TreeIterator
    implements ObjectListIterator<Float2ObjectMap.Entry<V>> {
        EntryIterator() {
        }

        EntryIterator(float k) {
            super(k);
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
        public void set(Float2ObjectMap.Entry<V> ok) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(Float2ObjectMap.Entry<V> ok) {
            throw new UnsupportedOperationException();
        }
    }

    private class TreeIterator {
        Entry<V> prev;
        Entry<V> next;
        Entry<V> curr;
        int index = 0;

        TreeIterator() {
            this.next = Float2ObjectRBTreeMap.this.firstEntry;
        }

        TreeIterator(float k) {
            this.next = Float2ObjectRBTreeMap.this.locateKey(k);
            if (this.next != null) {
                if (Float2ObjectRBTreeMap.this.compare(this.next.key, k) <= 0) {
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
            Float2ObjectRBTreeMap.this.remove(this.curr.key);
            this.curr = null;
        }

        public int skip(int n) {
            int i = n;
            while (i-- != 0 && this.hasNext()) {
                this.nextEntry();
            }
            return n - i - 1;
        }

        public int back(int n) {
            int i = n;
            while (i-- != 0 && this.hasPrevious()) {
                this.previousEntry();
            }
            return n - i - 1;
        }
    }

    private static final class Entry<V>
    implements Cloneable,
    Float2ObjectMap.Entry<V> {
        private static final int BLACK_MASK = 1;
        private static final int SUCC_MASK = Integer.MIN_VALUE;
        private static final int PRED_MASK = 0x40000000;
        float key;
        V value;
        Entry<V> left;
        Entry<V> right;
        int info;

        Entry() {
        }

        Entry(float k, V v) {
            this.key = k;
            this.value = v;
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

        void pred(boolean pred) {
            this.info = pred ? (this.info |= 0x40000000) : (this.info &= 0xBFFFFFFF);
        }

        void succ(boolean succ) {
            this.info = succ ? (this.info |= Integer.MIN_VALUE) : (this.info &= Integer.MAX_VALUE);
        }

        void pred(Entry<V> pred) {
            this.info |= 0x40000000;
            this.left = pred;
        }

        void succ(Entry<V> succ) {
            this.info |= Integer.MIN_VALUE;
            this.right = succ;
        }

        void left(Entry<V> left) {
            this.info &= 0xBFFFFFFF;
            this.left = left;
        }

        void right(Entry<V> right) {
            this.info &= Integer.MAX_VALUE;
            this.right = right;
        }

        boolean black() {
            return (this.info & 1) != 0;
        }

        void black(boolean black) {
            this.info = black ? (this.info |= 1) : (this.info &= 0xFFFFFFFE);
        }

        Entry<V> next() {
            Entry<V> next = this.right;
            if ((this.info & Integer.MIN_VALUE) == 0) {
                while ((next.info & 0x40000000) == 0) {
                    next = next.left;
                }
            }
            return next;
        }

        Entry<V> prev() {
            Entry<V> prev = this.left;
            if ((this.info & 0x40000000) == 0) {
                while ((prev.info & Integer.MIN_VALUE) == 0) {
                    prev = prev.right;
                }
            }
            return prev;
        }

        @Override
        @Deprecated
        public Float getKey() {
            return Float.valueOf(this.key);
        }

        @Override
        public float getFloatKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        public Entry<V> clone() {
            Entry c;
            try {
                c = (Entry)super.clone();
            } catch (CloneNotSupportedException cantHappen) {
                throw new InternalError();
            }
            c.key = this.key;
            c.value = this.value;
            c.info = this.info;
            return c;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry)o;
            return Float.floatToIntBits(this.key) == Float.floatToIntBits(((Float)e.getKey()).floatValue()) && (this.value == null ? e.getValue() == null : this.value.equals(e.getValue()));
        }

        @Override
        public int hashCode() {
            return HashCommon.float2int(this.key) ^ (this.value == null ? 0 : this.value.hashCode());
        }

        public String toString() {
            return this.key + "=>" + this.value;
        }
    }
}

