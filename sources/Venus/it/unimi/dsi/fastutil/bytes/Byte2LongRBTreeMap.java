/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.bytes.AbstractByte2LongMap;
import it.unimi.dsi.fastutil.bytes.AbstractByte2LongSortedMap;
import it.unimi.dsi.fastutil.bytes.Byte2LongMap;
import it.unimi.dsi.fastutil.bytes.Byte2LongSortedMap;
import it.unimi.dsi.fastutil.bytes.ByteBidirectionalIterator;
import it.unimi.dsi.fastutil.bytes.ByteComparator;
import it.unimi.dsi.fastutil.bytes.ByteComparators;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.bytes.ByteListIterator;
import it.unimi.dsi.fastutil.bytes.ByteSet;
import it.unimi.dsi.fastutil.bytes.ByteSortedSet;
import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongListIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
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
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Byte2LongRBTreeMap
extends AbstractByte2LongSortedMap
implements Serializable,
Cloneable {
    protected transient Entry tree;
    protected int count;
    protected transient Entry firstEntry;
    protected transient Entry lastEntry;
    protected transient ObjectSortedSet<Byte2LongMap.Entry> entries;
    protected transient ByteSortedSet keys;
    protected transient LongCollection values;
    protected transient boolean modified;
    protected Comparator<? super Byte> storedComparator;
    protected transient ByteComparator actualComparator;
    private static final long serialVersionUID = -7046029254386353129L;
    private transient boolean[] dirPath;
    private transient Entry[] nodePath;

    public Byte2LongRBTreeMap() {
        this.allocatePaths();
        this.tree = null;
        this.count = 0;
    }

    private void setActualComparator() {
        this.actualComparator = ByteComparators.asByteComparator(this.storedComparator);
    }

    public Byte2LongRBTreeMap(Comparator<? super Byte> comparator) {
        this();
        this.storedComparator = comparator;
        this.setActualComparator();
    }

    public Byte2LongRBTreeMap(Map<? extends Byte, ? extends Long> map) {
        this();
        this.putAll(map);
    }

    public Byte2LongRBTreeMap(SortedMap<Byte, Long> sortedMap) {
        this(sortedMap.comparator());
        this.putAll((Map<? extends Byte, ? extends Long>)sortedMap);
    }

    public Byte2LongRBTreeMap(Byte2LongMap byte2LongMap) {
        this();
        this.putAll(byte2LongMap);
    }

    public Byte2LongRBTreeMap(Byte2LongSortedMap byte2LongSortedMap) {
        this(byte2LongSortedMap.comparator());
        this.putAll(byte2LongSortedMap);
    }

    public Byte2LongRBTreeMap(byte[] byArray, long[] lArray, Comparator<? super Byte> comparator) {
        this(comparator);
        if (byArray.length != lArray.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + byArray.length + " and " + lArray.length + ")");
        }
        for (int i = 0; i < byArray.length; ++i) {
            this.put(byArray[i], lArray[i]);
        }
    }

    public Byte2LongRBTreeMap(byte[] byArray, long[] lArray) {
        this(byArray, lArray, null);
    }

    final int compare(byte by, byte by2) {
        return this.actualComparator == null ? Byte.compare(by, by2) : this.actualComparator.compare(by, by2);
    }

    final Entry findKey(byte by) {
        int n;
        Entry entry = this.tree;
        while (entry != null && (n = this.compare(by, entry.key)) != 0) {
            entry = n < 0 ? entry.left() : entry.right();
        }
        return entry;
    }

    final Entry locateKey(byte by) {
        Entry entry = this.tree;
        Entry entry2 = this.tree;
        int n = 0;
        while (entry != null && (n = this.compare(by, entry.key)) != 0) {
            entry2 = entry;
            entry = n < 0 ? entry.left() : entry.right();
        }
        return n == 0 ? entry : entry2;
    }

    private void allocatePaths() {
        this.dirPath = new boolean[64];
        this.nodePath = new Entry[64];
    }

    public long addTo(byte by, long l) {
        Entry entry = this.add(by);
        long l2 = entry.value;
        entry.value += l;
        return l2;
    }

    @Override
    public long put(byte by, long l) {
        Entry entry = this.add(by);
        long l2 = entry.value;
        entry.value = l;
        return l2;
    }

    private Entry add(byte by) {
        Entry entry;
        this.modified = false;
        int n = 0;
        if (this.tree == null) {
            ++this.count;
            this.lastEntry = this.firstEntry = new Entry(by, this.defRetValue);
            this.tree = this.firstEntry;
            entry = this.firstEntry;
        } else {
            Entry entry2 = this.tree;
            int n2 = 0;
            while (true) {
                int n3;
                if ((n3 = this.compare(by, entry2.key)) == 0) {
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
                        entry = new Entry(by, this.defRetValue);
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
                    entry = new Entry(by, this.defRetValue);
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
    public long remove(byte by) {
        Entry entry;
        int n;
        Entry entry2;
        int n2;
        Entry entry3;
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
                                        byte by2 = by;
                                        while (true) {
                                            int n4;
                                            if ((n4 = this.compare(by2, entry3.key)) == 0) {
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
                                entry2 = this.nodePath[n2 - 1].right = entry;
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
                            entry2 = this.nodePath[n2 - 1].left = entry;
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
    public boolean containsValue(long l) {
        ValueIterator valueIterator = new ValueIterator(this, null);
        int n = this.count;
        while (n-- != 0) {
            long l2 = valueIterator.nextLong();
            if (l2 != l) continue;
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
    public boolean containsKey(byte by) {
        return this.findKey(by) != null;
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
    public long get(byte by) {
        Entry entry = this.findKey(by);
        return entry == null ? this.defRetValue : entry.value;
    }

    @Override
    public byte firstByteKey() {
        if (this.tree == null) {
            throw new NoSuchElementException();
        }
        return this.firstEntry.key;
    }

    @Override
    public byte lastByteKey() {
        if (this.tree == null) {
            throw new NoSuchElementException();
        }
        return this.lastEntry.key;
    }

    @Override
    public ObjectSortedSet<Byte2LongMap.Entry> byte2LongEntrySet() {
        if (this.entries == null) {
            this.entries = new AbstractObjectSortedSet<Byte2LongMap.Entry>(this){
                final Comparator<? super Byte2LongMap.Entry> comparator;
                final Byte2LongRBTreeMap this$0;
                {
                    this.this$0 = byte2LongRBTreeMap;
                    this.comparator = this::lambda$$0;
                }

                @Override
                public Comparator<? super Byte2LongMap.Entry> comparator() {
                    return this.comparator;
                }

                @Override
                public ObjectBidirectionalIterator<Byte2LongMap.Entry> iterator() {
                    return new EntryIterator(this.this$0);
                }

                @Override
                public ObjectBidirectionalIterator<Byte2LongMap.Entry> iterator(Byte2LongMap.Entry entry) {
                    return new EntryIterator(this.this$0, entry.getByteKey());
                }

                @Override
                public boolean contains(Object object) {
                    if (!(object instanceof Map.Entry)) {
                        return true;
                    }
                    Map.Entry entry = (Map.Entry)object;
                    if (entry.getKey() == null || !(entry.getKey() instanceof Byte)) {
                        return true;
                    }
                    if (entry.getValue() == null || !(entry.getValue() instanceof Long)) {
                        return true;
                    }
                    Entry entry2 = this.this$0.findKey((Byte)entry.getKey());
                    return entry.equals(entry2);
                }

                @Override
                public boolean remove(Object object) {
                    if (!(object instanceof Map.Entry)) {
                        return true;
                    }
                    Map.Entry entry = (Map.Entry)object;
                    if (entry.getKey() == null || !(entry.getKey() instanceof Byte)) {
                        return true;
                    }
                    if (entry.getValue() == null || !(entry.getValue() instanceof Long)) {
                        return true;
                    }
                    Entry entry2 = this.this$0.findKey((Byte)entry.getKey());
                    if (entry2 == null || entry2.getLongValue() != ((Long)entry.getValue()).longValue()) {
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
                public Byte2LongMap.Entry first() {
                    return this.this$0.firstEntry;
                }

                @Override
                public Byte2LongMap.Entry last() {
                    return this.this$0.lastEntry;
                }

                @Override
                public ObjectSortedSet<Byte2LongMap.Entry> subSet(Byte2LongMap.Entry entry, Byte2LongMap.Entry entry2) {
                    return this.this$0.subMap(entry.getByteKey(), entry2.getByteKey()).byte2LongEntrySet();
                }

                @Override
                public ObjectSortedSet<Byte2LongMap.Entry> headSet(Byte2LongMap.Entry entry) {
                    return this.this$0.headMap(entry.getByteKey()).byte2LongEntrySet();
                }

                @Override
                public ObjectSortedSet<Byte2LongMap.Entry> tailSet(Byte2LongMap.Entry entry) {
                    return this.this$0.tailMap(entry.getByteKey()).byte2LongEntrySet();
                }

                @Override
                public ObjectSortedSet tailSet(Object object) {
                    return this.tailSet((Byte2LongMap.Entry)object);
                }

                @Override
                public ObjectSortedSet headSet(Object object) {
                    return this.headSet((Byte2LongMap.Entry)object);
                }

                @Override
                public ObjectSortedSet subSet(Object object, Object object2) {
                    return this.subSet((Byte2LongMap.Entry)object, (Byte2LongMap.Entry)object2);
                }

                @Override
                public ObjectBidirectionalIterator iterator(Object object) {
                    return this.iterator((Byte2LongMap.Entry)object);
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
                    return this.tailSet((Byte2LongMap.Entry)object);
                }

                @Override
                public SortedSet headSet(Object object) {
                    return this.headSet((Byte2LongMap.Entry)object);
                }

                @Override
                public SortedSet subSet(Object object, Object object2) {
                    return this.subSet((Byte2LongMap.Entry)object, (Byte2LongMap.Entry)object2);
                }

                private int lambda$$0(Byte2LongMap.Entry entry, Byte2LongMap.Entry entry2) {
                    return this.this$0.actualComparator.compare(entry.getByteKey(), entry2.getByteKey());
                }
            };
        }
        return this.entries;
    }

    @Override
    public ByteSortedSet keySet() {
        if (this.keys == null) {
            this.keys = new KeySet(this, null);
        }
        return this.keys;
    }

    @Override
    public LongCollection values() {
        if (this.values == null) {
            this.values = new AbstractLongCollection(this){
                final Byte2LongRBTreeMap this$0;
                {
                    this.this$0 = byte2LongRBTreeMap;
                }

                @Override
                public LongIterator iterator() {
                    return new ValueIterator(this.this$0, null);
                }

                @Override
                public boolean contains(long l) {
                    return this.this$0.containsValue(l);
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
    public ByteComparator comparator() {
        return this.actualComparator;
    }

    @Override
    public Byte2LongSortedMap headMap(byte by) {
        return new Submap(this, 0, true, by, false);
    }

    @Override
    public Byte2LongSortedMap tailMap(byte by) {
        return new Submap(this, by, false, 0, true);
    }

    @Override
    public Byte2LongSortedMap subMap(byte by, byte by2) {
        return new Submap(this, by, false, by2, false);
    }

    public Byte2LongRBTreeMap clone() {
        Byte2LongRBTreeMap byte2LongRBTreeMap;
        try {
            byte2LongRBTreeMap = (Byte2LongRBTreeMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        byte2LongRBTreeMap.keys = null;
        byte2LongRBTreeMap.values = null;
        byte2LongRBTreeMap.entries = null;
        byte2LongRBTreeMap.allocatePaths();
        if (this.count != 0) {
            Entry entry = new Entry();
            Entry entry2 = new Entry();
            Entry entry3 = entry;
            entry.left(this.tree);
            Entry entry4 = entry2;
            entry2.pred(null);
            while (true) {
                Entry entry5;
                if (!entry3.pred()) {
                    entry5 = entry3.left.clone();
                    entry5.pred(entry4.left);
                    entry5.succ(entry4);
                    entry4.left(entry5);
                    entry3 = entry3.left;
                    entry4 = entry4.left;
                } else {
                    while (entry3.succ()) {
                        entry3 = entry3.right;
                        if (entry3 == null) {
                            entry4.right = null;
                            byte2LongRBTreeMap.firstEntry = byte2LongRBTreeMap.tree = entry2.left;
                            while (byte2LongRBTreeMap.firstEntry.left != null) {
                                byte2LongRBTreeMap.firstEntry = byte2LongRBTreeMap.firstEntry.left;
                            }
                            byte2LongRBTreeMap.lastEntry = byte2LongRBTreeMap.tree;
                            while (byte2LongRBTreeMap.lastEntry.right != null) {
                                byte2LongRBTreeMap.lastEntry = byte2LongRBTreeMap.lastEntry.right;
                            }
                            return byte2LongRBTreeMap;
                        }
                        entry4 = entry4.right;
                    }
                    entry3 = entry3.right;
                    entry4 = entry4.right;
                }
                if (entry3.succ()) continue;
                entry5 = entry3.right.clone();
                entry5.succ(entry4.right);
                entry5.pred(entry4);
                entry4.right(entry5);
            }
        }
        return byte2LongRBTreeMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        int n = this.count;
        EntryIterator entryIterator = new EntryIterator(this);
        objectOutputStream.defaultWriteObject();
        while (n-- != 0) {
            Entry entry = entryIterator.nextEntry();
            objectOutputStream.writeByte(entry.key);
            objectOutputStream.writeLong(entry.value);
        }
    }

    private Entry readTree(ObjectInputStream objectInputStream, int n, Entry entry, Entry entry2) throws IOException, ClassNotFoundException {
        if (n == 1) {
            Entry entry3 = new Entry(objectInputStream.readByte(), objectInputStream.readLong());
            entry3.pred(entry);
            entry3.succ(entry2);
            entry3.black(false);
            return entry3;
        }
        if (n == 2) {
            Entry entry4 = new Entry(objectInputStream.readByte(), objectInputStream.readLong());
            entry4.black(false);
            entry4.right(new Entry(objectInputStream.readByte(), objectInputStream.readLong()));
            entry4.right.pred(entry4);
            entry4.pred(entry);
            entry4.right.succ(entry2);
            return entry4;
        }
        int n2 = n / 2;
        int n3 = n - n2 - 1;
        Entry entry5 = new Entry();
        entry5.left(this.readTree(objectInputStream, n3, entry, entry5));
        entry5.key = objectInputStream.readByte();
        entry5.value = objectInputStream.readLong();
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
            Entry entry = this.tree = this.readTree(objectInputStream, this.count, null, null);
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
    public ByteSet keySet() {
        return this.keySet();
    }

    @Override
    public ObjectSet byte2LongEntrySet() {
        return this.byte2LongEntrySet();
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
    extends AbstractByte2LongSortedMap
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        byte from;
        byte to;
        boolean bottom;
        boolean top;
        protected transient ObjectSortedSet<Byte2LongMap.Entry> entries;
        protected transient ByteSortedSet keys;
        protected transient LongCollection values;
        final Byte2LongRBTreeMap this$0;

        public Submap(Byte2LongRBTreeMap byte2LongRBTreeMap, byte by, boolean bl, byte by2, boolean bl2) {
            this.this$0 = byte2LongRBTreeMap;
            if (!bl && !bl2 && byte2LongRBTreeMap.compare(by, by2) > 0) {
                throw new IllegalArgumentException("Start key (" + by + ") is larger than end key (" + by2 + ")");
            }
            this.from = by;
            this.bottom = bl;
            this.to = by2;
            this.top = bl2;
            this.defRetValue = byte2LongRBTreeMap.defRetValue;
        }

        @Override
        public void clear() {
            SubmapIterator submapIterator = new SubmapIterator(this);
            while (submapIterator.hasNext()) {
                submapIterator.nextEntry();
                submapIterator.remove();
            }
        }

        final boolean in(byte by) {
            return !(!this.bottom && this.this$0.compare(by, this.from) < 0 || !this.top && this.this$0.compare(by, this.to) >= 0);
        }

        @Override
        public ObjectSortedSet<Byte2LongMap.Entry> byte2LongEntrySet() {
            if (this.entries == null) {
                this.entries = new AbstractObjectSortedSet<Byte2LongMap.Entry>(this){
                    final Submap this$1;
                    {
                        this.this$1 = submap;
                    }

                    @Override
                    public ObjectBidirectionalIterator<Byte2LongMap.Entry> iterator() {
                        return new SubmapEntryIterator(this.this$1);
                    }

                    @Override
                    public ObjectBidirectionalIterator<Byte2LongMap.Entry> iterator(Byte2LongMap.Entry entry) {
                        return new SubmapEntryIterator(this.this$1, entry.getByteKey());
                    }

                    @Override
                    public Comparator<? super Byte2LongMap.Entry> comparator() {
                        return this.this$1.this$0.byte2LongEntrySet().comparator();
                    }

                    @Override
                    public boolean contains(Object object) {
                        if (!(object instanceof Map.Entry)) {
                            return true;
                        }
                        Map.Entry entry = (Map.Entry)object;
                        if (entry.getKey() == null || !(entry.getKey() instanceof Byte)) {
                            return true;
                        }
                        if (entry.getValue() == null || !(entry.getValue() instanceof Long)) {
                            return true;
                        }
                        Entry entry2 = this.this$1.this$0.findKey((Byte)entry.getKey());
                        return entry2 != null && this.this$1.in(entry2.key) && entry.equals(entry2);
                    }

                    @Override
                    public boolean remove(Object object) {
                        if (!(object instanceof Map.Entry)) {
                            return true;
                        }
                        Map.Entry entry = (Map.Entry)object;
                        if (entry.getKey() == null || !(entry.getKey() instanceof Byte)) {
                            return true;
                        }
                        if (entry.getValue() == null || !(entry.getValue() instanceof Long)) {
                            return true;
                        }
                        Entry entry2 = this.this$1.this$0.findKey((Byte)entry.getKey());
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
                    public Byte2LongMap.Entry first() {
                        return this.this$1.firstEntry();
                    }

                    @Override
                    public Byte2LongMap.Entry last() {
                        return this.this$1.lastEntry();
                    }

                    @Override
                    public ObjectSortedSet<Byte2LongMap.Entry> subSet(Byte2LongMap.Entry entry, Byte2LongMap.Entry entry2) {
                        return this.this$1.subMap(entry.getByteKey(), entry2.getByteKey()).byte2LongEntrySet();
                    }

                    @Override
                    public ObjectSortedSet<Byte2LongMap.Entry> headSet(Byte2LongMap.Entry entry) {
                        return this.this$1.headMap(entry.getByteKey()).byte2LongEntrySet();
                    }

                    @Override
                    public ObjectSortedSet<Byte2LongMap.Entry> tailSet(Byte2LongMap.Entry entry) {
                        return this.this$1.tailMap(entry.getByteKey()).byte2LongEntrySet();
                    }

                    @Override
                    public ObjectSortedSet tailSet(Object object) {
                        return this.tailSet((Byte2LongMap.Entry)object);
                    }

                    @Override
                    public ObjectSortedSet headSet(Object object) {
                        return this.headSet((Byte2LongMap.Entry)object);
                    }

                    @Override
                    public ObjectSortedSet subSet(Object object, Object object2) {
                        return this.subSet((Byte2LongMap.Entry)object, (Byte2LongMap.Entry)object2);
                    }

                    @Override
                    public ObjectBidirectionalIterator iterator(Object object) {
                        return this.iterator((Byte2LongMap.Entry)object);
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
                        return this.tailSet((Byte2LongMap.Entry)object);
                    }

                    @Override
                    public SortedSet headSet(Object object) {
                        return this.headSet((Byte2LongMap.Entry)object);
                    }

                    @Override
                    public SortedSet subSet(Object object, Object object2) {
                        return this.subSet((Byte2LongMap.Entry)object, (Byte2LongMap.Entry)object2);
                    }
                };
            }
            return this.entries;
        }

        @Override
        public ByteSortedSet keySet() {
            if (this.keys == null) {
                this.keys = new KeySet(this, null);
            }
            return this.keys;
        }

        @Override
        public LongCollection values() {
            if (this.values == null) {
                this.values = new AbstractLongCollection(this){
                    final Submap this$1;
                    {
                        this.this$1 = submap;
                    }

                    @Override
                    public LongIterator iterator() {
                        return new SubmapValueIterator(this.this$1, null);
                    }

                    @Override
                    public boolean contains(long l) {
                        return this.this$1.containsValue(l);
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
        public boolean containsKey(byte by) {
            return this.in(by) && this.this$0.containsKey(by);
        }

        @Override
        public boolean containsValue(long l) {
            SubmapIterator submapIterator = new SubmapIterator(this);
            while (submapIterator.hasNext()) {
                long l2 = submapIterator.nextEntry().value;
                if (l2 != l) continue;
                return false;
            }
            return true;
        }

        @Override
        public long get(byte by) {
            Entry entry;
            byte by2 = by;
            return this.in(by2) && (entry = this.this$0.findKey(by2)) != null ? entry.value : this.defRetValue;
        }

        @Override
        public long put(byte by, long l) {
            this.this$0.modified = false;
            if (!this.in(by)) {
                throw new IllegalArgumentException("Key (" + by + ") out of range [" + (this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")");
            }
            long l2 = this.this$0.put(by, l);
            return this.this$0.modified ? this.defRetValue : l2;
        }

        @Override
        public long remove(byte by) {
            this.this$0.modified = false;
            if (!this.in(by)) {
                return this.defRetValue;
            }
            long l = this.this$0.remove(by);
            return this.this$0.modified ? l : this.defRetValue;
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
        public ByteComparator comparator() {
            return this.this$0.actualComparator;
        }

        @Override
        public Byte2LongSortedMap headMap(byte by) {
            if (this.top) {
                return new Submap(this.this$0, this.from, this.bottom, by, false);
            }
            return this.this$0.compare(by, this.to) < 0 ? new Submap(this.this$0, this.from, this.bottom, by, false) : this;
        }

        @Override
        public Byte2LongSortedMap tailMap(byte by) {
            if (this.bottom) {
                return new Submap(this.this$0, by, false, this.to, this.top);
            }
            return this.this$0.compare(by, this.from) > 0 ? new Submap(this.this$0, by, false, this.to, this.top) : this;
        }

        @Override
        public Byte2LongSortedMap subMap(byte by, byte by2) {
            if (this.top && this.bottom) {
                return new Submap(this.this$0, by, false, by2, false);
            }
            if (!this.top) {
                byte by3 = by2 = this.this$0.compare(by2, this.to) < 0 ? by2 : this.to;
            }
            if (!this.bottom) {
                byte by4 = by = this.this$0.compare(by, this.from) > 0 ? by : this.from;
            }
            if (!this.top && !this.bottom && by == this.from && by2 == this.to) {
                return this;
            }
            return new Submap(this.this$0, by, false, by2, false);
        }

        public Entry firstEntry() {
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

        public Entry lastEntry() {
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
        public byte firstByteKey() {
            Entry entry = this.firstEntry();
            if (entry == null) {
                throw new NoSuchElementException();
            }
            return entry.key;
        }

        @Override
        public byte lastByteKey() {
            Entry entry = this.lastEntry();
            if (entry == null) {
                throw new NoSuchElementException();
            }
            return entry.key;
        }

        @Override
        public ByteSet keySet() {
            return this.keySet();
        }

        @Override
        public ObjectSet byte2LongEntrySet() {
            return this.byte2LongEntrySet();
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

        private final class SubmapValueIterator
        extends SubmapIterator
        implements LongListIterator {
            final Submap this$1;

            private SubmapValueIterator(Submap submap) {
                this.this$1 = submap;
                super(submap);
            }

            @Override
            public long nextLong() {
                return this.nextEntry().value;
            }

            @Override
            public long previousLong() {
                return this.previousEntry().value;
            }

            SubmapValueIterator(Submap submap, 1 var2_2) {
                this(submap);
            }
        }

        private final class SubmapKeyIterator
        extends SubmapIterator
        implements ByteListIterator {
            final Submap this$1;

            public SubmapKeyIterator(Submap submap) {
                this.this$1 = submap;
                super(submap);
            }

            public SubmapKeyIterator(Submap submap, byte by) {
                this.this$1 = submap;
                super(submap, by);
            }

            @Override
            public byte nextByte() {
                return this.nextEntry().key;
            }

            @Override
            public byte previousByte() {
                return this.previousEntry().key;
            }
        }

        /*
         * Duplicate member names - consider using --renamedupmembers true
         */
        private class SubmapEntryIterator
        extends SubmapIterator
        implements ObjectListIterator<Byte2LongMap.Entry> {
            final Submap this$1;

            SubmapEntryIterator(Submap submap) {
                this.this$1 = submap;
                super(submap);
            }

            SubmapEntryIterator(Submap submap, byte by) {
                this.this$1 = submap;
                super(submap, by);
            }

            @Override
            public Byte2LongMap.Entry next() {
                return this.nextEntry();
            }

            @Override
            public Byte2LongMap.Entry previous() {
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
            SubmapIterator(Submap submap, byte by) {
                this(submap);
                if (this.next == null) return;
                if (!submap.bottom && submap.this$0.compare(by, this.next.key) < 0) {
                    this.prev = null;
                    return;
                }
                if (!submap.top) {
                    this.prev = submap.lastEntry();
                    if (submap.this$0.compare(by, this.prev.key) >= 0) {
                        this.next = null;
                        return;
                    }
                }
                this.next = submap.this$0.locateKey(by);
                if (submap.this$0.compare(this.next.key, by) <= 0) {
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
        extends AbstractByte2LongSortedMap.KeySet {
            final Submap this$1;

            private KeySet(Submap submap) {
                this.this$1 = submap;
                super(submap);
            }

            @Override
            public ByteBidirectionalIterator iterator() {
                return new SubmapKeyIterator(this.this$1);
            }

            @Override
            public ByteBidirectionalIterator iterator(byte by) {
                return new SubmapKeyIterator(this.this$1, by);
            }

            @Override
            public ByteIterator iterator() {
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
    implements LongListIterator {
        final Byte2LongRBTreeMap this$0;

        private ValueIterator(Byte2LongRBTreeMap byte2LongRBTreeMap) {
            this.this$0 = byte2LongRBTreeMap;
            super(byte2LongRBTreeMap);
        }

        @Override
        public long nextLong() {
            return this.nextEntry().value;
        }

        @Override
        public long previousLong() {
            return this.previousEntry().value;
        }

        ValueIterator(Byte2LongRBTreeMap byte2LongRBTreeMap, 1 var2_2) {
            this(byte2LongRBTreeMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class KeySet
    extends AbstractByte2LongSortedMap.KeySet {
        final Byte2LongRBTreeMap this$0;

        private KeySet(Byte2LongRBTreeMap byte2LongRBTreeMap) {
            this.this$0 = byte2LongRBTreeMap;
            super(byte2LongRBTreeMap);
        }

        @Override
        public ByteBidirectionalIterator iterator() {
            return new KeyIterator(this.this$0);
        }

        @Override
        public ByteBidirectionalIterator iterator(byte by) {
            return new KeyIterator(this.this$0, by);
        }

        @Override
        public ByteIterator iterator() {
            return this.iterator();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        KeySet(Byte2LongRBTreeMap byte2LongRBTreeMap, 1 var2_2) {
            this(byte2LongRBTreeMap);
        }
    }

    private final class KeyIterator
    extends TreeIterator
    implements ByteListIterator {
        final Byte2LongRBTreeMap this$0;

        public KeyIterator(Byte2LongRBTreeMap byte2LongRBTreeMap) {
            this.this$0 = byte2LongRBTreeMap;
            super(byte2LongRBTreeMap);
        }

        public KeyIterator(Byte2LongRBTreeMap byte2LongRBTreeMap, byte by) {
            this.this$0 = byte2LongRBTreeMap;
            super(byte2LongRBTreeMap, by);
        }

        @Override
        public byte nextByte() {
            return this.nextEntry().key;
        }

        @Override
        public byte previousByte() {
            return this.previousEntry().key;
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class EntryIterator
    extends TreeIterator
    implements ObjectListIterator<Byte2LongMap.Entry> {
        final Byte2LongRBTreeMap this$0;

        EntryIterator(Byte2LongRBTreeMap byte2LongRBTreeMap) {
            this.this$0 = byte2LongRBTreeMap;
            super(byte2LongRBTreeMap);
        }

        EntryIterator(Byte2LongRBTreeMap byte2LongRBTreeMap, byte by) {
            this.this$0 = byte2LongRBTreeMap;
            super(byte2LongRBTreeMap, by);
        }

        @Override
        public Byte2LongMap.Entry next() {
            return this.nextEntry();
        }

        @Override
        public Byte2LongMap.Entry previous() {
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
        Entry prev;
        Entry next;
        Entry curr;
        int index;
        final Byte2LongRBTreeMap this$0;

        TreeIterator(Byte2LongRBTreeMap byte2LongRBTreeMap) {
            this.this$0 = byte2LongRBTreeMap;
            this.index = 0;
            this.next = byte2LongRBTreeMap.firstEntry;
        }

        TreeIterator(Byte2LongRBTreeMap byte2LongRBTreeMap, byte by) {
            this.this$0 = byte2LongRBTreeMap;
            this.index = 0;
            this.next = byte2LongRBTreeMap.locateKey(by);
            if (this.next != null) {
                if (byte2LongRBTreeMap.compare(this.next.key, by) <= 0) {
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

        Entry nextEntry() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.curr = this.prev = this.next;
            ++this.index;
            this.updateNext();
            return this.curr;
        }

        void updatePrevious() {
            this.prev = this.prev.prev();
        }

        Entry previousEntry() {
            if (!this.hasPrevious()) {
                throw new NoSuchElementException();
            }
            this.curr = this.next = this.prev;
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
            this.next = this.prev = this.curr;
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

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static final class Entry
    extends AbstractByte2LongMap.BasicEntry
    implements Cloneable {
        private static final int BLACK_MASK = 1;
        private static final int SUCC_MASK = Integer.MIN_VALUE;
        private static final int PRED_MASK = 0x40000000;
        Entry left;
        Entry right;
        int info;

        Entry() {
            super((byte)0, 0L);
        }

        Entry(byte by, long l) {
            super(by, l);
            this.info = -1073741824;
        }

        Entry left() {
            return (this.info & 0x40000000) != 0 ? null : this.left;
        }

        Entry right() {
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

        void pred(Entry entry) {
            this.info |= 0x40000000;
            this.left = entry;
        }

        void succ(Entry entry) {
            this.info |= Integer.MIN_VALUE;
            this.right = entry;
        }

        void left(Entry entry) {
            this.info &= 0xBFFFFFFF;
            this.left = entry;
        }

        void right(Entry entry) {
            this.info &= Integer.MAX_VALUE;
            this.right = entry;
        }

        boolean black() {
            return (this.info & 1) != 0;
        }

        void black(boolean bl) {
            this.info = bl ? (this.info |= 1) : (this.info &= 0xFFFFFFFE);
        }

        Entry next() {
            Entry entry = this.right;
            if ((this.info & Integer.MIN_VALUE) == 0) {
                while ((entry.info & 0x40000000) == 0) {
                    entry = entry.left;
                }
            }
            return entry;
        }

        Entry prev() {
            Entry entry = this.left;
            if ((this.info & 0x40000000) == 0) {
                while ((entry.info & Integer.MIN_VALUE) == 0) {
                    entry = entry.right;
                }
            }
            return entry;
        }

        @Override
        public long setValue(long l) {
            long l2 = this.value;
            this.value = l;
            return l2;
        }

        public Entry clone() {
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
            return this.key == (Byte)entry.getKey() && this.value == (Long)entry.getValue();
        }

        @Override
        public int hashCode() {
            return this.key ^ HashCommon.long2int(this.value);
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

