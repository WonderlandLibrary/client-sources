/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.chars.CharListIterator;
import it.unimi.dsi.fastutil.ints.AbstractInt2CharMap;
import it.unimi.dsi.fastutil.ints.AbstractInt2CharSortedMap;
import it.unimi.dsi.fastutil.ints.Int2CharMap;
import it.unimi.dsi.fastutil.ints.Int2CharSortedMap;
import it.unimi.dsi.fastutil.ints.IntBidirectionalIterator;
import it.unimi.dsi.fastutil.ints.IntComparator;
import it.unimi.dsi.fastutil.ints.IntComparators;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
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
public class Int2CharRBTreeMap
extends AbstractInt2CharSortedMap
implements Serializable,
Cloneable {
    protected transient Entry tree;
    protected int count;
    protected transient Entry firstEntry;
    protected transient Entry lastEntry;
    protected transient ObjectSortedSet<Int2CharMap.Entry> entries;
    protected transient IntSortedSet keys;
    protected transient CharCollection values;
    protected transient boolean modified;
    protected Comparator<? super Integer> storedComparator;
    protected transient IntComparator actualComparator;
    private static final long serialVersionUID = -7046029254386353129L;
    private transient boolean[] dirPath;
    private transient Entry[] nodePath;

    public Int2CharRBTreeMap() {
        this.allocatePaths();
        this.tree = null;
        this.count = 0;
    }

    private void setActualComparator() {
        this.actualComparator = IntComparators.asIntComparator(this.storedComparator);
    }

    public Int2CharRBTreeMap(Comparator<? super Integer> comparator) {
        this();
        this.storedComparator = comparator;
        this.setActualComparator();
    }

    public Int2CharRBTreeMap(Map<? extends Integer, ? extends Character> map) {
        this();
        this.putAll(map);
    }

    public Int2CharRBTreeMap(SortedMap<Integer, Character> sortedMap) {
        this(sortedMap.comparator());
        this.putAll((Map<? extends Integer, ? extends Character>)sortedMap);
    }

    public Int2CharRBTreeMap(Int2CharMap int2CharMap) {
        this();
        this.putAll(int2CharMap);
    }

    public Int2CharRBTreeMap(Int2CharSortedMap int2CharSortedMap) {
        this(int2CharSortedMap.comparator());
        this.putAll(int2CharSortedMap);
    }

    public Int2CharRBTreeMap(int[] nArray, char[] cArray, Comparator<? super Integer> comparator) {
        this(comparator);
        if (nArray.length != cArray.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + nArray.length + " and " + cArray.length + ")");
        }
        for (int i = 0; i < nArray.length; ++i) {
            this.put(nArray[i], cArray[i]);
        }
    }

    public Int2CharRBTreeMap(int[] nArray, char[] cArray) {
        this(nArray, cArray, null);
    }

    final int compare(int n, int n2) {
        return this.actualComparator == null ? Integer.compare(n, n2) : this.actualComparator.compare(n, n2);
    }

    final Entry findKey(int n) {
        int n2;
        Entry entry = this.tree;
        while (entry != null && (n2 = this.compare(n, entry.key)) != 0) {
            entry = n2 < 0 ? entry.left() : entry.right();
        }
        return entry;
    }

    final Entry locateKey(int n) {
        Entry entry = this.tree;
        Entry entry2 = this.tree;
        int n2 = 0;
        while (entry != null && (n2 = this.compare(n, entry.key)) != 0) {
            entry2 = entry;
            entry = n2 < 0 ? entry.left() : entry.right();
        }
        return n2 == 0 ? entry : entry2;
    }

    private void allocatePaths() {
        this.dirPath = new boolean[64];
        this.nodePath = new Entry[64];
    }

    public char addTo(int n, char c) {
        Entry entry = this.add(n);
        char c2 = entry.value;
        entry.value = (char)(entry.value + c);
        return c2;
    }

    @Override
    public char put(int n, char c) {
        Entry entry = this.add(n);
        char c2 = entry.value;
        entry.value = c;
        return c2;
    }

    private Entry add(int n) {
        Entry entry;
        this.modified = false;
        int n2 = 0;
        if (this.tree == null) {
            ++this.count;
            this.lastEntry = this.firstEntry = new Entry(n, this.defRetValue);
            this.tree = this.firstEntry;
            entry = this.firstEntry;
        } else {
            Entry entry2 = this.tree;
            int n3 = 0;
            while (true) {
                int n4;
                if ((n4 = this.compare(n, entry2.key)) == 0) {
                    while (n3-- != 0) {
                        this.nodePath[n3] = null;
                    }
                    return entry2;
                }
                this.nodePath[n3] = entry2;
                this.dirPath[n3++] = n4 > 0;
                if (this.dirPath[n3++]) {
                    if (entry2.succ()) {
                        ++this.count;
                        entry = new Entry(n, this.defRetValue);
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
                    entry = new Entry(n, this.defRetValue);
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
            n2 = n3--;
            while (n3 > 0 && !this.nodePath[n3].black()) {
                Entry entry3;
                Entry entry4;
                if (!this.dirPath[n3 - 1]) {
                    entry4 = this.nodePath[n3 - 1].right;
                    if (!this.nodePath[n3 - 1].succ() && !entry4.black()) {
                        this.nodePath[n3].black(false);
                        entry4.black(false);
                        this.nodePath[n3 - 1].black(true);
                        n3 -= 2;
                        continue;
                    }
                    if (!this.dirPath[n3]) {
                        entry4 = this.nodePath[n3];
                    } else {
                        entry3 = this.nodePath[n3];
                        entry4 = entry3.right;
                        entry3.right = entry4.left;
                        entry4.left = entry3;
                        this.nodePath[n3 - 1].left = entry4;
                        if (entry4.pred()) {
                            entry4.pred(true);
                            entry3.succ(entry4);
                        }
                    }
                    entry3 = this.nodePath[n3 - 1];
                    entry3.black(true);
                    entry4.black(false);
                    entry3.left = entry4.right;
                    entry4.right = entry3;
                    if (n3 < 2) {
                        this.tree = entry4;
                    } else if (this.dirPath[n3 - 2]) {
                        this.nodePath[n3 - 2].right = entry4;
                    } else {
                        this.nodePath[n3 - 2].left = entry4;
                    }
                    if (!entry4.succ()) break;
                    entry4.succ(true);
                    entry3.pred(entry4);
                    break;
                }
                entry4 = this.nodePath[n3 - 1].left;
                if (!this.nodePath[n3 - 1].pred() && !entry4.black()) {
                    this.nodePath[n3].black(false);
                    entry4.black(false);
                    this.nodePath[n3 - 1].black(true);
                    n3 -= 2;
                    continue;
                }
                if (this.dirPath[n3]) {
                    entry4 = this.nodePath[n3];
                } else {
                    entry3 = this.nodePath[n3];
                    entry4 = entry3.left;
                    entry3.left = entry4.right;
                    entry4.right = entry3;
                    this.nodePath[n3 - 1].right = entry4;
                    if (entry4.succ()) {
                        entry4.succ(true);
                        entry3.pred(entry4);
                    }
                }
                entry3 = this.nodePath[n3 - 1];
                entry3.black(true);
                entry4.black(false);
                entry3.right = entry4.left;
                entry4.left = entry3;
                if (n3 < 2) {
                    this.tree = entry4;
                } else if (this.dirPath[n3 - 2]) {
                    this.nodePath[n3 - 2].right = entry4;
                } else {
                    this.nodePath[n3 - 2].left = entry4;
                }
                if (!entry4.pred()) break;
                entry4.pred(true);
                entry3.succ(entry4);
                break;
            }
        }
        this.tree.black(false);
        while (n2-- != 0) {
            this.nodePath[n2] = null;
        }
        return entry;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public char remove(int n) {
        Entry entry;
        int n2;
        Entry entry2;
        int n3;
        Entry entry3;
        block68: {
            int n4;
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
                                        n3 = 0;
                                        int n5 = n;
                                        while (true) {
                                            int n6;
                                            if ((n6 = this.compare(n5, entry3.key)) == 0) {
                                                if (entry3.left != null) break block66;
                                                break block67;
                                            }
                                            this.dirPath[n3] = n6 > 0;
                                            this.nodePath[n3] = entry3;
                                            if (this.dirPath[n3++]) {
                                                if ((entry3 = entry3.right()) != null) continue;
                                                while (true) {
                                                    if (n3-- == 0) {
                                                        return this.defRetValue;
                                                    }
                                                    this.nodePath[n3] = null;
                                                }
                                            }
                                            if ((entry3 = entry3.left()) == null) break;
                                        }
                                        while (true) {
                                            if (n3-- == 0) {
                                                return this.defRetValue;
                                            }
                                            this.nodePath[n3] = null;
                                        }
                                    }
                                    this.firstEntry = entry3.next();
                                }
                                if (entry3.right == null) {
                                    this.lastEntry = entry3.prev();
                                }
                                if (!entry3.succ()) break block71;
                                if (!entry3.pred()) break block72;
                                if (n3 == 0) {
                                    this.tree = entry3.left;
                                    break block68;
                                } else if (this.dirPath[n3 - 1]) {
                                    this.nodePath[n3 - 1].succ(entry3.right);
                                    break block68;
                                } else {
                                    this.nodePath[n3 - 1].pred(entry3.left);
                                }
                                break block68;
                            }
                            entry3.prev().right = entry3.right;
                            if (n3 == 0) {
                                this.tree = entry3.left;
                                break block68;
                            } else if (this.dirPath[n3 - 1]) {
                                this.nodePath[n3 - 1].right = entry3.left;
                                break block68;
                            } else {
                                this.nodePath[n3 - 1].left = entry3.left;
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
                        if (n3 == 0) {
                            this.tree = entry2;
                        } else if (this.dirPath[n3 - 1]) {
                            this.nodePath[n3 - 1].right = entry2;
                        } else {
                            this.nodePath[n3 - 1].left = entry2;
                        }
                        n2 = entry2.black() ? 1 : 0;
                        entry2.black(entry3.black());
                        entry3.black(n2 != 0);
                        this.dirPath[n3] = true;
                        this.nodePath[n3++] = entry2;
                        break block68;
                    }
                    n4 = n3++;
                    while (true) {
                        this.dirPath[n3] = false;
                        this.nodePath[n3++] = entry2;
                        entry = entry2.left;
                        if (entry.pred()) {
                            this.dirPath[n4] = true;
                            this.nodePath[n4] = entry;
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
            n2 = entry.black() ? 1 : 0;
            entry.black(entry3.black());
            entry3.black(n2 != 0);
            if (n4 == 0) {
                this.tree = entry;
            } else if (this.dirPath[n4 - 1]) {
                this.nodePath[n4 - 1].right = entry;
            } else {
                this.nodePath[n4 - 1].left = entry;
            }
        }
        n2 = n3;
        if (entry3.black()) {
            while (n3 > 0) {
                block70: {
                    if (this.dirPath[n3 - 1] && !this.nodePath[n3 - 1].succ() || !this.dirPath[n3 - 1] && !this.nodePath[n3 - 1].pred()) {
                        Entry entry4 = entry2 = this.dirPath[n3 - 1] ? this.nodePath[n3 - 1].right : this.nodePath[n3 - 1].left;
                        if (!entry2.black()) {
                            entry2.black(false);
                            break;
                        }
                    }
                    if (!this.dirPath[n3 - 1]) {
                        entry2 = this.nodePath[n3 - 1].right;
                        if (!entry2.black()) {
                            entry2.black(false);
                            this.nodePath[n3 - 1].black(true);
                            this.nodePath[n3 - 1].right = entry2.left;
                            entry2.left = this.nodePath[n3 - 1];
                            if (n3 < 2) {
                                this.tree = entry2;
                            } else if (this.dirPath[n3 - 2]) {
                                this.nodePath[n3 - 2].right = entry2;
                            } else {
                                this.nodePath[n3 - 2].left = entry2;
                            }
                            this.nodePath[n3] = this.nodePath[n3 - 1];
                            this.dirPath[n3] = false;
                            this.nodePath[n3 - 1] = entry2;
                            if (n2 == n3++) {
                                ++n2;
                            }
                            entry2 = this.nodePath[n3 - 1].right;
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
                                entry2 = this.nodePath[n3 - 1].right = entry;
                                if (entry2.succ()) {
                                    entry2.succ(true);
                                    entry2.right.pred(entry2);
                                }
                            }
                            entry2.black(this.nodePath[n3 - 1].black());
                            this.nodePath[n3 - 1].black(false);
                            entry2.right.black(false);
                            this.nodePath[n3 - 1].right = entry2.left;
                            entry2.left = this.nodePath[n3 - 1];
                            if (n3 < 2) {
                                this.tree = entry2;
                            } else if (this.dirPath[n3 - 2]) {
                                this.nodePath[n3 - 2].right = entry2;
                            } else {
                                this.nodePath[n3 - 2].left = entry2;
                            }
                            if (!entry2.pred()) break;
                            entry2.pred(true);
                            this.nodePath[n3 - 1].succ(entry2);
                            break;
                        }
                    }
                    entry2 = this.nodePath[n3 - 1].left;
                    if (!entry2.black()) {
                        entry2.black(false);
                        this.nodePath[n3 - 1].black(true);
                        this.nodePath[n3 - 1].left = entry2.right;
                        entry2.right = this.nodePath[n3 - 1];
                        if (n3 < 2) {
                            this.tree = entry2;
                        } else if (this.dirPath[n3 - 2]) {
                            this.nodePath[n3 - 2].right = entry2;
                        } else {
                            this.nodePath[n3 - 2].left = entry2;
                        }
                        this.nodePath[n3] = this.nodePath[n3 - 1];
                        this.dirPath[n3] = true;
                        this.nodePath[n3 - 1] = entry2;
                        if (n2 == n3++) {
                            ++n2;
                        }
                        entry2 = this.nodePath[n3 - 1].left;
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
                            entry2 = this.nodePath[n3 - 1].left = entry;
                            if (entry2.pred()) {
                                entry2.pred(true);
                                entry2.left.succ(entry2);
                            }
                        }
                        entry2.black(this.nodePath[n3 - 1].black());
                        this.nodePath[n3 - 1].black(false);
                        entry2.left.black(false);
                        this.nodePath[n3 - 1].left = entry2.right;
                        entry2.right = this.nodePath[n3 - 1];
                        if (n3 < 2) {
                            this.tree = entry2;
                        } else if (this.dirPath[n3 - 2]) {
                            this.nodePath[n3 - 2].right = entry2;
                        } else {
                            this.nodePath[n3 - 2].left = entry2;
                        }
                        if (!entry2.succ()) break;
                        entry2.succ(true);
                        this.nodePath[n3 - 1].pred(entry2);
                        break;
                    }
                }
                --n3;
            }
            if (this.tree != null) {
                this.tree.black(false);
            }
        }
        this.modified = true;
        --this.count;
        while (n2-- != 0) {
            this.nodePath[n2] = null;
        }
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
    public boolean containsKey(int n) {
        return this.findKey(n) != null;
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
    public char get(int n) {
        Entry entry = this.findKey(n);
        return entry == null ? this.defRetValue : entry.value;
    }

    @Override
    public int firstIntKey() {
        if (this.tree == null) {
            throw new NoSuchElementException();
        }
        return this.firstEntry.key;
    }

    @Override
    public int lastIntKey() {
        if (this.tree == null) {
            throw new NoSuchElementException();
        }
        return this.lastEntry.key;
    }

    @Override
    public ObjectSortedSet<Int2CharMap.Entry> int2CharEntrySet() {
        if (this.entries == null) {
            this.entries = new AbstractObjectSortedSet<Int2CharMap.Entry>(this){
                final Comparator<? super Int2CharMap.Entry> comparator;
                final Int2CharRBTreeMap this$0;
                {
                    this.this$0 = int2CharRBTreeMap;
                    this.comparator = this::lambda$$0;
                }

                @Override
                public Comparator<? super Int2CharMap.Entry> comparator() {
                    return this.comparator;
                }

                @Override
                public ObjectBidirectionalIterator<Int2CharMap.Entry> iterator() {
                    return new EntryIterator(this.this$0);
                }

                @Override
                public ObjectBidirectionalIterator<Int2CharMap.Entry> iterator(Int2CharMap.Entry entry) {
                    return new EntryIterator(this.this$0, entry.getIntKey());
                }

                @Override
                public boolean contains(Object object) {
                    if (!(object instanceof Map.Entry)) {
                        return true;
                    }
                    Map.Entry entry = (Map.Entry)object;
                    if (entry.getKey() == null || !(entry.getKey() instanceof Integer)) {
                        return true;
                    }
                    if (entry.getValue() == null || !(entry.getValue() instanceof Character)) {
                        return true;
                    }
                    Entry entry2 = this.this$0.findKey((Integer)entry.getKey());
                    return entry.equals(entry2);
                }

                @Override
                public boolean remove(Object object) {
                    if (!(object instanceof Map.Entry)) {
                        return true;
                    }
                    Map.Entry entry = (Map.Entry)object;
                    if (entry.getKey() == null || !(entry.getKey() instanceof Integer)) {
                        return true;
                    }
                    if (entry.getValue() == null || !(entry.getValue() instanceof Character)) {
                        return true;
                    }
                    Entry entry2 = this.this$0.findKey((Integer)entry.getKey());
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
                public Int2CharMap.Entry first() {
                    return this.this$0.firstEntry;
                }

                @Override
                public Int2CharMap.Entry last() {
                    return this.this$0.lastEntry;
                }

                @Override
                public ObjectSortedSet<Int2CharMap.Entry> subSet(Int2CharMap.Entry entry, Int2CharMap.Entry entry2) {
                    return this.this$0.subMap(entry.getIntKey(), entry2.getIntKey()).int2CharEntrySet();
                }

                @Override
                public ObjectSortedSet<Int2CharMap.Entry> headSet(Int2CharMap.Entry entry) {
                    return this.this$0.headMap(entry.getIntKey()).int2CharEntrySet();
                }

                @Override
                public ObjectSortedSet<Int2CharMap.Entry> tailSet(Int2CharMap.Entry entry) {
                    return this.this$0.tailMap(entry.getIntKey()).int2CharEntrySet();
                }

                @Override
                public ObjectSortedSet tailSet(Object object) {
                    return this.tailSet((Int2CharMap.Entry)object);
                }

                @Override
                public ObjectSortedSet headSet(Object object) {
                    return this.headSet((Int2CharMap.Entry)object);
                }

                @Override
                public ObjectSortedSet subSet(Object object, Object object2) {
                    return this.subSet((Int2CharMap.Entry)object, (Int2CharMap.Entry)object2);
                }

                @Override
                public ObjectBidirectionalIterator iterator(Object object) {
                    return this.iterator((Int2CharMap.Entry)object);
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
                    return this.tailSet((Int2CharMap.Entry)object);
                }

                @Override
                public SortedSet headSet(Object object) {
                    return this.headSet((Int2CharMap.Entry)object);
                }

                @Override
                public SortedSet subSet(Object object, Object object2) {
                    return this.subSet((Int2CharMap.Entry)object, (Int2CharMap.Entry)object2);
                }

                private int lambda$$0(Int2CharMap.Entry entry, Int2CharMap.Entry entry2) {
                    return this.this$0.actualComparator.compare(entry.getIntKey(), entry2.getIntKey());
                }
            };
        }
        return this.entries;
    }

    @Override
    public IntSortedSet keySet() {
        if (this.keys == null) {
            this.keys = new KeySet(this, null);
        }
        return this.keys;
    }

    @Override
    public CharCollection values() {
        if (this.values == null) {
            this.values = new AbstractCharCollection(this){
                final Int2CharRBTreeMap this$0;
                {
                    this.this$0 = int2CharRBTreeMap;
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
    public IntComparator comparator() {
        return this.actualComparator;
    }

    @Override
    public Int2CharSortedMap headMap(int n) {
        return new Submap(this, 0, true, n, false);
    }

    @Override
    public Int2CharSortedMap tailMap(int n) {
        return new Submap(this, n, false, 0, true);
    }

    @Override
    public Int2CharSortedMap subMap(int n, int n2) {
        return new Submap(this, n, false, n2, false);
    }

    public Int2CharRBTreeMap clone() {
        Int2CharRBTreeMap int2CharRBTreeMap;
        try {
            int2CharRBTreeMap = (Int2CharRBTreeMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        int2CharRBTreeMap.keys = null;
        int2CharRBTreeMap.values = null;
        int2CharRBTreeMap.entries = null;
        int2CharRBTreeMap.allocatePaths();
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
                            int2CharRBTreeMap.firstEntry = int2CharRBTreeMap.tree = entry2.left;
                            while (int2CharRBTreeMap.firstEntry.left != null) {
                                int2CharRBTreeMap.firstEntry = int2CharRBTreeMap.firstEntry.left;
                            }
                            int2CharRBTreeMap.lastEntry = int2CharRBTreeMap.tree;
                            while (int2CharRBTreeMap.lastEntry.right != null) {
                                int2CharRBTreeMap.lastEntry = int2CharRBTreeMap.lastEntry.right;
                            }
                            return int2CharRBTreeMap;
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
        return int2CharRBTreeMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        int n = this.count;
        EntryIterator entryIterator = new EntryIterator(this);
        objectOutputStream.defaultWriteObject();
        while (n-- != 0) {
            Entry entry = entryIterator.nextEntry();
            objectOutputStream.writeInt(entry.key);
            objectOutputStream.writeChar(entry.value);
        }
    }

    private Entry readTree(ObjectInputStream objectInputStream, int n, Entry entry, Entry entry2) throws IOException, ClassNotFoundException {
        if (n == 1) {
            Entry entry3 = new Entry(objectInputStream.readInt(), objectInputStream.readChar());
            entry3.pred(entry);
            entry3.succ(entry2);
            entry3.black(false);
            return entry3;
        }
        if (n == 2) {
            Entry entry4 = new Entry(objectInputStream.readInt(), objectInputStream.readChar());
            entry4.black(false);
            entry4.right(new Entry(objectInputStream.readInt(), objectInputStream.readChar()));
            entry4.right.pred(entry4);
            entry4.pred(entry);
            entry4.right.succ(entry2);
            return entry4;
        }
        int n2 = n / 2;
        int n3 = n - n2 - 1;
        Entry entry5 = new Entry();
        entry5.left(this.readTree(objectInputStream, n3, entry, entry5));
        entry5.key = objectInputStream.readInt();
        entry5.value = objectInputStream.readChar();
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
    public IntSet keySet() {
        return this.keySet();
    }

    @Override
    public ObjectSet int2CharEntrySet() {
        return this.int2CharEntrySet();
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
    extends AbstractInt2CharSortedMap
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        int from;
        int to;
        boolean bottom;
        boolean top;
        protected transient ObjectSortedSet<Int2CharMap.Entry> entries;
        protected transient IntSortedSet keys;
        protected transient CharCollection values;
        final Int2CharRBTreeMap this$0;

        public Submap(Int2CharRBTreeMap int2CharRBTreeMap, int n, boolean bl, int n2, boolean bl2) {
            this.this$0 = int2CharRBTreeMap;
            if (!bl && !bl2 && int2CharRBTreeMap.compare(n, n2) > 0) {
                throw new IllegalArgumentException("Start key (" + n + ") is larger than end key (" + n2 + ")");
            }
            this.from = n;
            this.bottom = bl;
            this.to = n2;
            this.top = bl2;
            this.defRetValue = int2CharRBTreeMap.defRetValue;
        }

        @Override
        public void clear() {
            SubmapIterator submapIterator = new SubmapIterator(this);
            while (submapIterator.hasNext()) {
                submapIterator.nextEntry();
                submapIterator.remove();
            }
        }

        final boolean in(int n) {
            return !(!this.bottom && this.this$0.compare(n, this.from) < 0 || !this.top && this.this$0.compare(n, this.to) >= 0);
        }

        @Override
        public ObjectSortedSet<Int2CharMap.Entry> int2CharEntrySet() {
            if (this.entries == null) {
                this.entries = new AbstractObjectSortedSet<Int2CharMap.Entry>(this){
                    final Submap this$1;
                    {
                        this.this$1 = submap;
                    }

                    @Override
                    public ObjectBidirectionalIterator<Int2CharMap.Entry> iterator() {
                        return new SubmapEntryIterator(this.this$1);
                    }

                    @Override
                    public ObjectBidirectionalIterator<Int2CharMap.Entry> iterator(Int2CharMap.Entry entry) {
                        return new SubmapEntryIterator(this.this$1, entry.getIntKey());
                    }

                    @Override
                    public Comparator<? super Int2CharMap.Entry> comparator() {
                        return this.this$1.this$0.int2CharEntrySet().comparator();
                    }

                    @Override
                    public boolean contains(Object object) {
                        if (!(object instanceof Map.Entry)) {
                            return true;
                        }
                        Map.Entry entry = (Map.Entry)object;
                        if (entry.getKey() == null || !(entry.getKey() instanceof Integer)) {
                            return true;
                        }
                        if (entry.getValue() == null || !(entry.getValue() instanceof Character)) {
                            return true;
                        }
                        Entry entry2 = this.this$1.this$0.findKey((Integer)entry.getKey());
                        return entry2 != null && this.this$1.in(entry2.key) && entry.equals(entry2);
                    }

                    @Override
                    public boolean remove(Object object) {
                        if (!(object instanceof Map.Entry)) {
                            return true;
                        }
                        Map.Entry entry = (Map.Entry)object;
                        if (entry.getKey() == null || !(entry.getKey() instanceof Integer)) {
                            return true;
                        }
                        if (entry.getValue() == null || !(entry.getValue() instanceof Character)) {
                            return true;
                        }
                        Entry entry2 = this.this$1.this$0.findKey((Integer)entry.getKey());
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
                    public Int2CharMap.Entry first() {
                        return this.this$1.firstEntry();
                    }

                    @Override
                    public Int2CharMap.Entry last() {
                        return this.this$1.lastEntry();
                    }

                    @Override
                    public ObjectSortedSet<Int2CharMap.Entry> subSet(Int2CharMap.Entry entry, Int2CharMap.Entry entry2) {
                        return this.this$1.subMap(entry.getIntKey(), entry2.getIntKey()).int2CharEntrySet();
                    }

                    @Override
                    public ObjectSortedSet<Int2CharMap.Entry> headSet(Int2CharMap.Entry entry) {
                        return this.this$1.headMap(entry.getIntKey()).int2CharEntrySet();
                    }

                    @Override
                    public ObjectSortedSet<Int2CharMap.Entry> tailSet(Int2CharMap.Entry entry) {
                        return this.this$1.tailMap(entry.getIntKey()).int2CharEntrySet();
                    }

                    @Override
                    public ObjectSortedSet tailSet(Object object) {
                        return this.tailSet((Int2CharMap.Entry)object);
                    }

                    @Override
                    public ObjectSortedSet headSet(Object object) {
                        return this.headSet((Int2CharMap.Entry)object);
                    }

                    @Override
                    public ObjectSortedSet subSet(Object object, Object object2) {
                        return this.subSet((Int2CharMap.Entry)object, (Int2CharMap.Entry)object2);
                    }

                    @Override
                    public ObjectBidirectionalIterator iterator(Object object) {
                        return this.iterator((Int2CharMap.Entry)object);
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
                        return this.tailSet((Int2CharMap.Entry)object);
                    }

                    @Override
                    public SortedSet headSet(Object object) {
                        return this.headSet((Int2CharMap.Entry)object);
                    }

                    @Override
                    public SortedSet subSet(Object object, Object object2) {
                        return this.subSet((Int2CharMap.Entry)object, (Int2CharMap.Entry)object2);
                    }
                };
            }
            return this.entries;
        }

        @Override
        public IntSortedSet keySet() {
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
        public boolean containsKey(int n) {
            return this.in(n) && this.this$0.containsKey(n);
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
        public char get(int n) {
            Entry entry;
            int n2 = n;
            return this.in(n2) && (entry = this.this$0.findKey(n2)) != null ? entry.value : this.defRetValue;
        }

        @Override
        public char put(int n, char c) {
            this.this$0.modified = false;
            if (!this.in(n)) {
                throw new IllegalArgumentException("Key (" + n + ") out of range [" + (this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")");
            }
            char c2 = this.this$0.put(n, c);
            return this.this$0.modified ? this.defRetValue : c2;
        }

        @Override
        public char remove(int n) {
            this.this$0.modified = false;
            if (!this.in(n)) {
                return this.defRetValue;
            }
            char c = this.this$0.remove(n);
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
        public IntComparator comparator() {
            return this.this$0.actualComparator;
        }

        @Override
        public Int2CharSortedMap headMap(int n) {
            if (this.top) {
                return new Submap(this.this$0, this.from, this.bottom, n, false);
            }
            return this.this$0.compare(n, this.to) < 0 ? new Submap(this.this$0, this.from, this.bottom, n, false) : this;
        }

        @Override
        public Int2CharSortedMap tailMap(int n) {
            if (this.bottom) {
                return new Submap(this.this$0, n, false, this.to, this.top);
            }
            return this.this$0.compare(n, this.from) > 0 ? new Submap(this.this$0, n, false, this.to, this.top) : this;
        }

        @Override
        public Int2CharSortedMap subMap(int n, int n2) {
            if (this.top && this.bottom) {
                return new Submap(this.this$0, n, false, n2, false);
            }
            if (!this.top) {
                int n3 = n2 = this.this$0.compare(n2, this.to) < 0 ? n2 : this.to;
            }
            if (!this.bottom) {
                int n4 = n = this.this$0.compare(n, this.from) > 0 ? n : this.from;
            }
            if (!this.top && !this.bottom && n == this.from && n2 == this.to) {
                return this;
            }
            return new Submap(this.this$0, n, false, n2, false);
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
        public int firstIntKey() {
            Entry entry = this.firstEntry();
            if (entry == null) {
                throw new NoSuchElementException();
            }
            return entry.key;
        }

        @Override
        public int lastIntKey() {
            Entry entry = this.lastEntry();
            if (entry == null) {
                throw new NoSuchElementException();
            }
            return entry.key;
        }

        @Override
        public IntSet keySet() {
            return this.keySet();
        }

        @Override
        public ObjectSet int2CharEntrySet() {
            return this.int2CharEntrySet();
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
        implements IntListIterator {
            final Submap this$1;

            public SubmapKeyIterator(Submap submap) {
                this.this$1 = submap;
                super(submap);
            }

            public SubmapKeyIterator(Submap submap, int n) {
                this.this$1 = submap;
                super(submap, n);
            }

            @Override
            public int nextInt() {
                return this.nextEntry().key;
            }

            @Override
            public int previousInt() {
                return this.previousEntry().key;
            }
        }

        /*
         * Duplicate member names - consider using --renamedupmembers true
         */
        private class SubmapEntryIterator
        extends SubmapIterator
        implements ObjectListIterator<Int2CharMap.Entry> {
            final Submap this$1;

            SubmapEntryIterator(Submap submap) {
                this.this$1 = submap;
                super(submap);
            }

            SubmapEntryIterator(Submap submap, int n) {
                this.this$1 = submap;
                super(submap, n);
            }

            @Override
            public Int2CharMap.Entry next() {
                return this.nextEntry();
            }

            @Override
            public Int2CharMap.Entry previous() {
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
            SubmapIterator(Submap submap, int n) {
                this(submap);
                if (this.next == null) return;
                if (!submap.bottom && submap.this$0.compare(n, this.next.key) < 0) {
                    this.prev = null;
                    return;
                }
                if (!submap.top) {
                    this.prev = submap.lastEntry();
                    if (submap.this$0.compare(n, this.prev.key) >= 0) {
                        this.next = null;
                        return;
                    }
                }
                this.next = submap.this$0.locateKey(n);
                if (submap.this$0.compare(this.next.key, n) <= 0) {
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
        extends AbstractInt2CharSortedMap.KeySet {
            final Submap this$1;

            private KeySet(Submap submap) {
                this.this$1 = submap;
                super(submap);
            }

            @Override
            public IntBidirectionalIterator iterator() {
                return new SubmapKeyIterator(this.this$1);
            }

            @Override
            public IntBidirectionalIterator iterator(int n) {
                return new SubmapKeyIterator(this.this$1, n);
            }

            @Override
            public IntIterator iterator() {
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
        final Int2CharRBTreeMap this$0;

        private ValueIterator(Int2CharRBTreeMap int2CharRBTreeMap) {
            this.this$0 = int2CharRBTreeMap;
            super(int2CharRBTreeMap);
        }

        @Override
        public char nextChar() {
            return this.nextEntry().value;
        }

        @Override
        public char previousChar() {
            return this.previousEntry().value;
        }

        ValueIterator(Int2CharRBTreeMap int2CharRBTreeMap, 1 var2_2) {
            this(int2CharRBTreeMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class KeySet
    extends AbstractInt2CharSortedMap.KeySet {
        final Int2CharRBTreeMap this$0;

        private KeySet(Int2CharRBTreeMap int2CharRBTreeMap) {
            this.this$0 = int2CharRBTreeMap;
            super(int2CharRBTreeMap);
        }

        @Override
        public IntBidirectionalIterator iterator() {
            return new KeyIterator(this.this$0);
        }

        @Override
        public IntBidirectionalIterator iterator(int n) {
            return new KeyIterator(this.this$0, n);
        }

        @Override
        public IntIterator iterator() {
            return this.iterator();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        KeySet(Int2CharRBTreeMap int2CharRBTreeMap, 1 var2_2) {
            this(int2CharRBTreeMap);
        }
    }

    private final class KeyIterator
    extends TreeIterator
    implements IntListIterator {
        final Int2CharRBTreeMap this$0;

        public KeyIterator(Int2CharRBTreeMap int2CharRBTreeMap) {
            this.this$0 = int2CharRBTreeMap;
            super(int2CharRBTreeMap);
        }

        public KeyIterator(Int2CharRBTreeMap int2CharRBTreeMap, int n) {
            this.this$0 = int2CharRBTreeMap;
            super(int2CharRBTreeMap, n);
        }

        @Override
        public int nextInt() {
            return this.nextEntry().key;
        }

        @Override
        public int previousInt() {
            return this.previousEntry().key;
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class EntryIterator
    extends TreeIterator
    implements ObjectListIterator<Int2CharMap.Entry> {
        final Int2CharRBTreeMap this$0;

        EntryIterator(Int2CharRBTreeMap int2CharRBTreeMap) {
            this.this$0 = int2CharRBTreeMap;
            super(int2CharRBTreeMap);
        }

        EntryIterator(Int2CharRBTreeMap int2CharRBTreeMap, int n) {
            this.this$0 = int2CharRBTreeMap;
            super(int2CharRBTreeMap, n);
        }

        @Override
        public Int2CharMap.Entry next() {
            return this.nextEntry();
        }

        @Override
        public Int2CharMap.Entry previous() {
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
        final Int2CharRBTreeMap this$0;

        TreeIterator(Int2CharRBTreeMap int2CharRBTreeMap) {
            this.this$0 = int2CharRBTreeMap;
            this.index = 0;
            this.next = int2CharRBTreeMap.firstEntry;
        }

        TreeIterator(Int2CharRBTreeMap int2CharRBTreeMap, int n) {
            this.this$0 = int2CharRBTreeMap;
            this.index = 0;
            this.next = int2CharRBTreeMap.locateKey(n);
            if (this.next != null) {
                if (int2CharRBTreeMap.compare(this.next.key, n) <= 0) {
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
    extends AbstractInt2CharMap.BasicEntry
    implements Cloneable {
        private static final int BLACK_MASK = 1;
        private static final int SUCC_MASK = Integer.MIN_VALUE;
        private static final int PRED_MASK = 0x40000000;
        Entry left;
        Entry right;
        int info;

        Entry() {
            super(0, '\u0000');
        }

        Entry(int n, char c) {
            super(n, c);
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
            return this.key == (Integer)entry.getKey() && this.value == ((Character)entry.getValue()).charValue();
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

