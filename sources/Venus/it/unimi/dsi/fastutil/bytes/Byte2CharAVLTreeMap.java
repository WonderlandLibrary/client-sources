/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByte2CharMap;
import it.unimi.dsi.fastutil.bytes.AbstractByte2CharSortedMap;
import it.unimi.dsi.fastutil.bytes.Byte2CharMap;
import it.unimi.dsi.fastutil.bytes.Byte2CharSortedMap;
import it.unimi.dsi.fastutil.bytes.ByteBidirectionalIterator;
import it.unimi.dsi.fastutil.bytes.ByteComparator;
import it.unimi.dsi.fastutil.bytes.ByteComparators;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.bytes.ByteListIterator;
import it.unimi.dsi.fastutil.bytes.ByteSet;
import it.unimi.dsi.fastutil.bytes.ByteSortedSet;
import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.chars.CharListIterator;
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
public class Byte2CharAVLTreeMap
extends AbstractByte2CharSortedMap
implements Serializable,
Cloneable {
    protected transient Entry tree;
    protected int count;
    protected transient Entry firstEntry;
    protected transient Entry lastEntry;
    protected transient ObjectSortedSet<Byte2CharMap.Entry> entries;
    protected transient ByteSortedSet keys;
    protected transient CharCollection values;
    protected transient boolean modified;
    protected Comparator<? super Byte> storedComparator;
    protected transient ByteComparator actualComparator;
    private static final long serialVersionUID = -7046029254386353129L;
    private transient boolean[] dirPath;
    static final boolean $assertionsDisabled = !Byte2CharAVLTreeMap.class.desiredAssertionStatus();

    public Byte2CharAVLTreeMap() {
        this.allocatePaths();
        this.tree = null;
        this.count = 0;
    }

    private void setActualComparator() {
        this.actualComparator = ByteComparators.asByteComparator(this.storedComparator);
    }

    public Byte2CharAVLTreeMap(Comparator<? super Byte> comparator) {
        this();
        this.storedComparator = comparator;
        this.setActualComparator();
    }

    public Byte2CharAVLTreeMap(Map<? extends Byte, ? extends Character> map) {
        this();
        this.putAll(map);
    }

    public Byte2CharAVLTreeMap(SortedMap<Byte, Character> sortedMap) {
        this(sortedMap.comparator());
        this.putAll((Map<? extends Byte, ? extends Character>)sortedMap);
    }

    public Byte2CharAVLTreeMap(Byte2CharMap byte2CharMap) {
        this();
        this.putAll(byte2CharMap);
    }

    public Byte2CharAVLTreeMap(Byte2CharSortedMap byte2CharSortedMap) {
        this(byte2CharSortedMap.comparator());
        this.putAll(byte2CharSortedMap);
    }

    public Byte2CharAVLTreeMap(byte[] byArray, char[] cArray, Comparator<? super Byte> comparator) {
        this(comparator);
        if (byArray.length != cArray.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + byArray.length + " and " + cArray.length + ")");
        }
        for (int i = 0; i < byArray.length; ++i) {
            this.put(byArray[i], cArray[i]);
        }
    }

    public Byte2CharAVLTreeMap(byte[] byArray, char[] cArray) {
        this(byArray, cArray, null);
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
        this.dirPath = new boolean[48];
    }

    public char addTo(byte by, char c) {
        Entry entry = this.add(by);
        char c2 = entry.value;
        entry.value = (char)(entry.value + c);
        return c2;
    }

    @Override
    public char put(byte by, char c) {
        Entry entry = this.add(by);
        char c2 = entry.value;
        entry.value = c;
        return c2;
    }

    private Entry add(byte by) {
        this.modified = false;
        Entry entry = null;
        if (this.tree == null) {
            ++this.count;
            this.lastEntry = this.firstEntry = new Entry(by, this.defRetValue);
            this.tree = this.firstEntry;
            entry = this.firstEntry;
            this.modified = true;
        } else {
            Entry entry2 = this.tree;
            Entry entry3 = null;
            Entry entry4 = this.tree;
            Entry entry5 = null;
            Entry entry6 = null;
            int n = 0;
            while (true) {
                int n2;
                if ((n2 = this.compare(by, entry2.key)) == 0) {
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
                        entry = new Entry(by, this.defRetValue);
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
                    entry = new Entry(by, this.defRetValue);
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

    private Entry parent(Entry entry) {
        Entry entry2;
        if (entry == this.tree) {
            return null;
        }
        Entry entry3 = entry2 = entry;
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
    public char remove(byte by) {
        Entry entry;
        Entry entry2;
        int n;
        this.modified = false;
        if (this.tree == null) {
            return this.defRetValue;
        }
        Entry entry3 = this.tree;
        Entry entry4 = null;
        boolean bl = false;
        byte by2 = by;
        while ((n = this.compare(by2, entry3.key)) != 0) {
            bl = n > 0;
            if (bl) {
                entry4 = entry3;
                if ((entry3 = entry3.right()) != null) continue;
                return this.defRetValue;
            }
            entry4 = entry3;
            if ((entry3 = entry3.left()) != null) continue;
            return this.defRetValue;
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
        return entry3.value;
    }

    @Override
    public boolean containsValue(char c) {
        ValueIterator valueIterator = new ValueIterator(this, null);
        int n = this.count;
        while (n-- != 0) {
            char c2 = valueIterator.nextChar();
            if (c2 != c) continue;
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
    public char get(byte by) {
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
    public ObjectSortedSet<Byte2CharMap.Entry> byte2CharEntrySet() {
        if (this.entries == null) {
            this.entries = new AbstractObjectSortedSet<Byte2CharMap.Entry>(this){
                final Comparator<? super Byte2CharMap.Entry> comparator;
                final Byte2CharAVLTreeMap this$0;
                {
                    this.this$0 = byte2CharAVLTreeMap;
                    this.comparator = this::lambda$$0;
                }

                @Override
                public Comparator<? super Byte2CharMap.Entry> comparator() {
                    return this.comparator;
                }

                @Override
                public ObjectBidirectionalIterator<Byte2CharMap.Entry> iterator() {
                    return new EntryIterator(this.this$0);
                }

                @Override
                public ObjectBidirectionalIterator<Byte2CharMap.Entry> iterator(Byte2CharMap.Entry entry) {
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
                    if (entry.getValue() == null || !(entry.getValue() instanceof Character)) {
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
                    if (entry.getValue() == null || !(entry.getValue() instanceof Character)) {
                        return true;
                    }
                    Entry entry2 = this.this$0.findKey((Byte)entry.getKey());
                    if (entry2 == null || entry2.getCharValue() != ((Character)entry.getValue()).charValue()) {
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
                public Byte2CharMap.Entry first() {
                    return this.this$0.firstEntry;
                }

                @Override
                public Byte2CharMap.Entry last() {
                    return this.this$0.lastEntry;
                }

                @Override
                public ObjectSortedSet<Byte2CharMap.Entry> subSet(Byte2CharMap.Entry entry, Byte2CharMap.Entry entry2) {
                    return this.this$0.subMap(entry.getByteKey(), entry2.getByteKey()).byte2CharEntrySet();
                }

                @Override
                public ObjectSortedSet<Byte2CharMap.Entry> headSet(Byte2CharMap.Entry entry) {
                    return this.this$0.headMap(entry.getByteKey()).byte2CharEntrySet();
                }

                @Override
                public ObjectSortedSet<Byte2CharMap.Entry> tailSet(Byte2CharMap.Entry entry) {
                    return this.this$0.tailMap(entry.getByteKey()).byte2CharEntrySet();
                }

                @Override
                public ObjectSortedSet tailSet(Object object) {
                    return this.tailSet((Byte2CharMap.Entry)object);
                }

                @Override
                public ObjectSortedSet headSet(Object object) {
                    return this.headSet((Byte2CharMap.Entry)object);
                }

                @Override
                public ObjectSortedSet subSet(Object object, Object object2) {
                    return this.subSet((Byte2CharMap.Entry)object, (Byte2CharMap.Entry)object2);
                }

                @Override
                public ObjectBidirectionalIterator iterator(Object object) {
                    return this.iterator((Byte2CharMap.Entry)object);
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
                    return this.tailSet((Byte2CharMap.Entry)object);
                }

                @Override
                public SortedSet headSet(Object object) {
                    return this.headSet((Byte2CharMap.Entry)object);
                }

                @Override
                public SortedSet subSet(Object object, Object object2) {
                    return this.subSet((Byte2CharMap.Entry)object, (Byte2CharMap.Entry)object2);
                }

                private int lambda$$0(Byte2CharMap.Entry entry, Byte2CharMap.Entry entry2) {
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
    public CharCollection values() {
        if (this.values == null) {
            this.values = new AbstractCharCollection(this){
                final Byte2CharAVLTreeMap this$0;
                {
                    this.this$0 = byte2CharAVLTreeMap;
                }

                @Override
                public CharIterator iterator() {
                    return new ValueIterator(this.this$0, null);
                }

                @Override
                public boolean contains(char c) {
                    return this.this$0.containsValue(c);
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
    public Byte2CharSortedMap headMap(byte by) {
        return new Submap(this, 0, true, by, false);
    }

    @Override
    public Byte2CharSortedMap tailMap(byte by) {
        return new Submap(this, by, false, 0, true);
    }

    @Override
    public Byte2CharSortedMap subMap(byte by, byte by2) {
        return new Submap(this, by, false, by2, false);
    }

    public Byte2CharAVLTreeMap clone() {
        Byte2CharAVLTreeMap byte2CharAVLTreeMap;
        try {
            byte2CharAVLTreeMap = (Byte2CharAVLTreeMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        byte2CharAVLTreeMap.keys = null;
        byte2CharAVLTreeMap.values = null;
        byte2CharAVLTreeMap.entries = null;
        byte2CharAVLTreeMap.allocatePaths();
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
                            byte2CharAVLTreeMap.firstEntry = byte2CharAVLTreeMap.tree = entry2.left;
                            while (byte2CharAVLTreeMap.firstEntry.left != null) {
                                byte2CharAVLTreeMap.firstEntry = byte2CharAVLTreeMap.firstEntry.left;
                            }
                            byte2CharAVLTreeMap.lastEntry = byte2CharAVLTreeMap.tree;
                            while (byte2CharAVLTreeMap.lastEntry.right != null) {
                                byte2CharAVLTreeMap.lastEntry = byte2CharAVLTreeMap.lastEntry.right;
                            }
                            return byte2CharAVLTreeMap;
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
        return byte2CharAVLTreeMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        int n = this.count;
        EntryIterator entryIterator = new EntryIterator(this);
        objectOutputStream.defaultWriteObject();
        while (n-- != 0) {
            Entry entry = entryIterator.nextEntry();
            objectOutputStream.writeByte(entry.key);
            objectOutputStream.writeChar(entry.value);
        }
    }

    private Entry readTree(ObjectInputStream objectInputStream, int n, Entry entry, Entry entry2) throws IOException, ClassNotFoundException {
        if (n == 1) {
            Entry entry3 = new Entry(objectInputStream.readByte(), objectInputStream.readChar());
            entry3.pred(entry);
            entry3.succ(entry2);
            return entry3;
        }
        if (n == 2) {
            Entry entry4 = new Entry(objectInputStream.readByte(), objectInputStream.readChar());
            entry4.right(new Entry(objectInputStream.readByte(), objectInputStream.readChar()));
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
        entry5.key = objectInputStream.readByte();
        entry5.value = objectInputStream.readChar();
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
    public ObjectSet byte2CharEntrySet() {
        return this.byte2CharEntrySet();
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
    extends AbstractByte2CharSortedMap
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        byte from;
        byte to;
        boolean bottom;
        boolean top;
        protected transient ObjectSortedSet<Byte2CharMap.Entry> entries;
        protected transient ByteSortedSet keys;
        protected transient CharCollection values;
        final Byte2CharAVLTreeMap this$0;

        public Submap(Byte2CharAVLTreeMap byte2CharAVLTreeMap, byte by, boolean bl, byte by2, boolean bl2) {
            this.this$0 = byte2CharAVLTreeMap;
            if (!bl && !bl2 && byte2CharAVLTreeMap.compare(by, by2) > 0) {
                throw new IllegalArgumentException("Start key (" + by + ") is larger than end key (" + by2 + ")");
            }
            this.from = by;
            this.bottom = bl;
            this.to = by2;
            this.top = bl2;
            this.defRetValue = byte2CharAVLTreeMap.defRetValue;
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
        public ObjectSortedSet<Byte2CharMap.Entry> byte2CharEntrySet() {
            if (this.entries == null) {
                this.entries = new AbstractObjectSortedSet<Byte2CharMap.Entry>(this){
                    final Submap this$1;
                    {
                        this.this$1 = submap;
                    }

                    @Override
                    public ObjectBidirectionalIterator<Byte2CharMap.Entry> iterator() {
                        return new SubmapEntryIterator(this.this$1);
                    }

                    @Override
                    public ObjectBidirectionalIterator<Byte2CharMap.Entry> iterator(Byte2CharMap.Entry entry) {
                        return new SubmapEntryIterator(this.this$1, entry.getByteKey());
                    }

                    @Override
                    public Comparator<? super Byte2CharMap.Entry> comparator() {
                        return this.this$1.this$0.byte2CharEntrySet().comparator();
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
                        if (entry.getValue() == null || !(entry.getValue() instanceof Character)) {
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
                        if (entry.getValue() == null || !(entry.getValue() instanceof Character)) {
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
                    public Byte2CharMap.Entry first() {
                        return this.this$1.firstEntry();
                    }

                    @Override
                    public Byte2CharMap.Entry last() {
                        return this.this$1.lastEntry();
                    }

                    @Override
                    public ObjectSortedSet<Byte2CharMap.Entry> subSet(Byte2CharMap.Entry entry, Byte2CharMap.Entry entry2) {
                        return this.this$1.subMap(entry.getByteKey(), entry2.getByteKey()).byte2CharEntrySet();
                    }

                    @Override
                    public ObjectSortedSet<Byte2CharMap.Entry> headSet(Byte2CharMap.Entry entry) {
                        return this.this$1.headMap(entry.getByteKey()).byte2CharEntrySet();
                    }

                    @Override
                    public ObjectSortedSet<Byte2CharMap.Entry> tailSet(Byte2CharMap.Entry entry) {
                        return this.this$1.tailMap(entry.getByteKey()).byte2CharEntrySet();
                    }

                    @Override
                    public ObjectSortedSet tailSet(Object object) {
                        return this.tailSet((Byte2CharMap.Entry)object);
                    }

                    @Override
                    public ObjectSortedSet headSet(Object object) {
                        return this.headSet((Byte2CharMap.Entry)object);
                    }

                    @Override
                    public ObjectSortedSet subSet(Object object, Object object2) {
                        return this.subSet((Byte2CharMap.Entry)object, (Byte2CharMap.Entry)object2);
                    }

                    @Override
                    public ObjectBidirectionalIterator iterator(Object object) {
                        return this.iterator((Byte2CharMap.Entry)object);
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
                        return this.tailSet((Byte2CharMap.Entry)object);
                    }

                    @Override
                    public SortedSet headSet(Object object) {
                        return this.headSet((Byte2CharMap.Entry)object);
                    }

                    @Override
                    public SortedSet subSet(Object object, Object object2) {
                        return this.subSet((Byte2CharMap.Entry)object, (Byte2CharMap.Entry)object2);
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
        public CharCollection values() {
            if (this.values == null) {
                this.values = new AbstractCharCollection(this){
                    final Submap this$1;
                    {
                        this.this$1 = submap;
                    }

                    @Override
                    public CharIterator iterator() {
                        return new SubmapValueIterator(this.this$1, null);
                    }

                    @Override
                    public boolean contains(char c) {
                        return this.this$1.containsValue(c);
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
        public boolean containsValue(char c) {
            SubmapIterator submapIterator = new SubmapIterator(this);
            while (submapIterator.hasNext()) {
                char c2 = submapIterator.nextEntry().value;
                if (c2 != c) continue;
                return false;
            }
            return true;
        }

        @Override
        public char get(byte by) {
            Entry entry;
            byte by2 = by;
            return this.in(by2) && (entry = this.this$0.findKey(by2)) != null ? entry.value : this.defRetValue;
        }

        @Override
        public char put(byte by, char c) {
            this.this$0.modified = false;
            if (!this.in(by)) {
                throw new IllegalArgumentException("Key (" + by + ") out of range [" + (this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")");
            }
            char c2 = this.this$0.put(by, c);
            return this.this$0.modified ? this.defRetValue : c2;
        }

        @Override
        public char remove(byte by) {
            this.this$0.modified = false;
            if (!this.in(by)) {
                return this.defRetValue;
            }
            char c = this.this$0.remove(by);
            return this.this$0.modified ? c : this.defRetValue;
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
        public Byte2CharSortedMap headMap(byte by) {
            if (this.top) {
                return new Submap(this.this$0, this.from, this.bottom, by, false);
            }
            return this.this$0.compare(by, this.to) < 0 ? new Submap(this.this$0, this.from, this.bottom, by, false) : this;
        }

        @Override
        public Byte2CharSortedMap tailMap(byte by) {
            if (this.bottom) {
                return new Submap(this.this$0, by, false, this.to, this.top);
            }
            return this.this$0.compare(by, this.from) > 0 ? new Submap(this.this$0, by, false, this.to, this.top) : this;
        }

        @Override
        public Byte2CharSortedMap subMap(byte by, byte by2) {
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
        public ObjectSet byte2CharEntrySet() {
            return this.byte2CharEntrySet();
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
        implements CharListIterator {
            final Submap this$1;

            private SubmapValueIterator(Submap submap) {
                this.this$1 = submap;
                super(submap);
            }

            @Override
            public char nextChar() {
                return this.nextEntry().value;
            }

            @Override
            public char previousChar() {
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
        implements ObjectListIterator<Byte2CharMap.Entry> {
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
            public Byte2CharMap.Entry next() {
                return this.nextEntry();
            }

            @Override
            public Byte2CharMap.Entry previous() {
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
        extends AbstractByte2CharSortedMap.KeySet {
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
    implements CharListIterator {
        final Byte2CharAVLTreeMap this$0;

        private ValueIterator(Byte2CharAVLTreeMap byte2CharAVLTreeMap) {
            this.this$0 = byte2CharAVLTreeMap;
            super(byte2CharAVLTreeMap);
        }

        @Override
        public char nextChar() {
            return this.nextEntry().value;
        }

        @Override
        public char previousChar() {
            return this.previousEntry().value;
        }

        ValueIterator(Byte2CharAVLTreeMap byte2CharAVLTreeMap, 1 var2_2) {
            this(byte2CharAVLTreeMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class KeySet
    extends AbstractByte2CharSortedMap.KeySet {
        final Byte2CharAVLTreeMap this$0;

        private KeySet(Byte2CharAVLTreeMap byte2CharAVLTreeMap) {
            this.this$0 = byte2CharAVLTreeMap;
            super(byte2CharAVLTreeMap);
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

        KeySet(Byte2CharAVLTreeMap byte2CharAVLTreeMap, 1 var2_2) {
            this(byte2CharAVLTreeMap);
        }
    }

    private final class KeyIterator
    extends TreeIterator
    implements ByteListIterator {
        final Byte2CharAVLTreeMap this$0;

        public KeyIterator(Byte2CharAVLTreeMap byte2CharAVLTreeMap) {
            this.this$0 = byte2CharAVLTreeMap;
            super(byte2CharAVLTreeMap);
        }

        public KeyIterator(Byte2CharAVLTreeMap byte2CharAVLTreeMap, byte by) {
            this.this$0 = byte2CharAVLTreeMap;
            super(byte2CharAVLTreeMap, by);
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
    implements ObjectListIterator<Byte2CharMap.Entry> {
        final Byte2CharAVLTreeMap this$0;

        EntryIterator(Byte2CharAVLTreeMap byte2CharAVLTreeMap) {
            this.this$0 = byte2CharAVLTreeMap;
            super(byte2CharAVLTreeMap);
        }

        EntryIterator(Byte2CharAVLTreeMap byte2CharAVLTreeMap, byte by) {
            this.this$0 = byte2CharAVLTreeMap;
            super(byte2CharAVLTreeMap, by);
        }

        @Override
        public Byte2CharMap.Entry next() {
            return this.nextEntry();
        }

        @Override
        public Byte2CharMap.Entry previous() {
            return this.previousEntry();
        }

        @Override
        public void set(Byte2CharMap.Entry entry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(Byte2CharMap.Entry entry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(Object object) {
            this.add((Byte2CharMap.Entry)object);
        }

        @Override
        public void set(Object object) {
            this.set((Byte2CharMap.Entry)object);
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
        final Byte2CharAVLTreeMap this$0;

        TreeIterator(Byte2CharAVLTreeMap byte2CharAVLTreeMap) {
            this.this$0 = byte2CharAVLTreeMap;
            this.index = 0;
            this.next = byte2CharAVLTreeMap.firstEntry;
        }

        TreeIterator(Byte2CharAVLTreeMap byte2CharAVLTreeMap, byte by) {
            this.this$0 = byte2CharAVLTreeMap;
            this.index = 0;
            this.next = byte2CharAVLTreeMap.locateKey(by);
            if (this.next != null) {
                if (byte2CharAVLTreeMap.compare(this.next.key, by) <= 0) {
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
    extends AbstractByte2CharMap.BasicEntry
    implements Cloneable {
        private static final int SUCC_MASK = Integer.MIN_VALUE;
        private static final int PRED_MASK = 0x40000000;
        private static final int BALANCE_MASK = 255;
        Entry left;
        Entry right;
        int info;

        Entry() {
            super((byte)0, '\u0000');
        }

        Entry(byte by, char c) {
            super(by, c);
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
        public char setValue(char c) {
            char c2 = this.value;
            this.value = c;
            return c2;
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
            return this.key == (Byte)entry.getKey() && this.value == ((Character)entry.getValue()).charValue();
        }

        @Override
        public int hashCode() {
            return this.key ^ this.value;
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

