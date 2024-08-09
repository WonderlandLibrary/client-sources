/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.SortedSet;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ObjectAVLTreeSet<K>
extends AbstractObjectSortedSet<K>
implements Serializable,
Cloneable,
ObjectSortedSet<K> {
    protected transient Entry<K> tree;
    protected int count;
    protected transient Entry<K> firstEntry;
    protected transient Entry<K> lastEntry;
    protected Comparator<? super K> storedComparator;
    protected transient Comparator<? super K> actualComparator;
    private static final long serialVersionUID = -7046029254386353130L;
    private transient boolean[] dirPath;
    static final boolean $assertionsDisabled = !ObjectAVLTreeSet.class.desiredAssertionStatus();

    public ObjectAVLTreeSet() {
        this.allocatePaths();
        this.tree = null;
        this.count = 0;
    }

    private void setActualComparator() {
        this.actualComparator = this.storedComparator;
    }

    public ObjectAVLTreeSet(Comparator<? super K> comparator) {
        this();
        this.storedComparator = comparator;
    }

    public ObjectAVLTreeSet(Collection<? extends K> collection) {
        this();
        this.addAll(collection);
    }

    public ObjectAVLTreeSet(SortedSet<K> sortedSet) {
        this(sortedSet.comparator());
        this.addAll(sortedSet);
    }

    public ObjectAVLTreeSet(ObjectCollection<? extends K> objectCollection) {
        this();
        this.addAll(objectCollection);
    }

    public ObjectAVLTreeSet(ObjectSortedSet<K> objectSortedSet) {
        this(objectSortedSet.comparator());
        this.addAll(objectSortedSet);
    }

    public ObjectAVLTreeSet(Iterator<? extends K> iterator2) {
        this.allocatePaths();
        while (iterator2.hasNext()) {
            this.add(iterator2.next());
        }
    }

    public ObjectAVLTreeSet(K[] KArray, int n, int n2, Comparator<? super K> comparator) {
        this(comparator);
        ObjectArrays.ensureOffsetLength(KArray, n, n2);
        for (int i = 0; i < n2; ++i) {
            this.add(KArray[n + i]);
        }
    }

    public ObjectAVLTreeSet(K[] KArray, int n, int n2) {
        this(KArray, n, n2, null);
    }

    public ObjectAVLTreeSet(K[] KArray) {
        this();
        int n = KArray.length;
        while (n-- != 0) {
            this.add(KArray[n]);
        }
    }

    public ObjectAVLTreeSet(K[] KArray, Comparator<? super K> comparator) {
        this(comparator);
        int n = KArray.length;
        while (n-- != 0) {
            this.add(KArray[n]);
        }
    }

    final int compare(K k, K k2) {
        return this.actualComparator == null ? ((Comparable)k).compareTo(k2) : this.actualComparator.compare(k, k2);
    }

    private Entry<K> findKey(K k) {
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
        this.dirPath = new boolean[48];
    }

    @Override
    public boolean add(K k) {
        if (this.tree == null) {
            ++this.count;
            this.firstEntry = new Entry<K>(k);
            this.lastEntry = this.firstEntry;
            this.tree = this.firstEntry;
        } else {
            Entry<K> entry = this.tree;
            Entry<K> entry2 = null;
            Entry entry3 = this.tree;
            Entry<K> entry4 = null;
            Entry<K> entry5 = null;
            Entry entry6 = null;
            int n = 0;
            while (true) {
                int n2;
                if ((n2 = this.compare(k, entry.key)) == 0) {
                    return true;
                }
                if (entry.balance() != 0) {
                    n = 0;
                    entry4 = entry2;
                    entry3 = entry;
                }
                if (this.dirPath[n++] = n2 > 0) {
                    if (entry.succ()) {
                        ++this.count;
                        entry5 = new Entry<K>(k);
                        if (entry.right == null) {
                            this.lastEntry = entry5;
                        }
                        entry5.left = entry;
                        entry5.right = entry.right;
                        entry.right(entry5);
                        break;
                    }
                    entry2 = entry;
                    entry = entry.right;
                    continue;
                }
                if (entry.pred()) {
                    ++this.count;
                    entry5 = new Entry<K>(k);
                    if (entry.left == null) {
                        this.firstEntry = entry5;
                    }
                    entry5.right = entry;
                    entry5.left = entry.left;
                    entry.left(entry5);
                    break;
                }
                entry2 = entry;
                entry = entry.left;
            }
            entry = entry3;
            n = 0;
            while (entry != entry5) {
                if (this.dirPath[n]) {
                    entry.incBalance();
                } else {
                    entry.decBalance();
                }
                entry = this.dirPath[n++] ? entry.right : entry.left;
            }
            if (entry3.balance() == -2) {
                Entry entry7 = entry3.left;
                if (entry7.balance() == -1) {
                    entry6 = entry7;
                    if (entry7.succ()) {
                        entry7.succ(true);
                        entry3.pred(entry7);
                    } else {
                        entry3.left = entry7.right;
                    }
                    entry7.right = entry3;
                    entry7.balance(0);
                    entry3.balance(0);
                } else {
                    if (!$assertionsDisabled && entry7.balance() != 1) {
                        throw new AssertionError();
                    }
                    entry6 = entry7.right;
                    entry7.right = entry6.left;
                    entry6.left = entry7;
                    entry3.left = entry6.right;
                    entry6.right = entry3;
                    if (entry6.balance() == -1) {
                        entry7.balance(0);
                        entry3.balance(1);
                    } else if (entry6.balance() == 0) {
                        entry7.balance(0);
                        entry3.balance(0);
                    } else {
                        entry7.balance(-1);
                        entry3.balance(0);
                    }
                    entry6.balance(0);
                    if (entry6.pred()) {
                        entry7.succ(entry6);
                        entry6.pred(true);
                    }
                    if (entry6.succ()) {
                        entry3.pred(entry6);
                        entry6.succ(true);
                    }
                }
            } else if (entry3.balance() == 2) {
                Entry entry8 = entry3.right;
                if (entry8.balance() == 1) {
                    entry6 = entry8;
                    if (entry8.pred()) {
                        entry8.pred(true);
                        entry3.succ(entry8);
                    } else {
                        entry3.right = entry8.left;
                    }
                    entry8.left = entry3;
                    entry8.balance(0);
                    entry3.balance(0);
                } else {
                    if (!$assertionsDisabled && entry8.balance() != -1) {
                        throw new AssertionError();
                    }
                    entry6 = entry8.left;
                    entry8.left = entry6.right;
                    entry6.right = entry8;
                    entry3.right = entry6.left;
                    entry6.left = entry3;
                    if (entry6.balance() == 1) {
                        entry8.balance(0);
                        entry3.balance(-1);
                    } else if (entry6.balance() == 0) {
                        entry8.balance(0);
                        entry3.balance(0);
                    } else {
                        entry8.balance(1);
                        entry3.balance(0);
                    }
                    entry6.balance(0);
                    if (entry6.pred()) {
                        entry3.succ(entry6);
                        entry6.pred(true);
                    }
                    if (entry6.succ()) {
                        entry8.pred(entry6);
                        entry6.succ(true);
                    }
                }
            } else {
                return false;
            }
            if (entry4 == null) {
                this.tree = entry6;
            } else if (entry4.left == entry3) {
                entry4.left = entry6;
            } else {
                entry4.right = entry6;
            }
        }
        return false;
    }

    private Entry<K> parent(Entry<K> entry) {
        Entry<K> entry2;
        if (entry == this.tree) {
            return null;
        }
        Entry<K> entry3 = entry2 = entry;
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
    public boolean remove(Object object) {
        Entry entry;
        Entry entry2;
        int n;
        if (this.tree == null) {
            return true;
        }
        Entry entry3 = this.tree;
        Entry entry4 = null;
        boolean bl = false;
        Object object2 = object;
        while ((n = this.compare(object2, entry3.key)) != 0) {
            bl = n > 0;
            if (bl) {
                entry4 = entry3;
                if ((entry3 = entry3.right()) != null) continue;
                return true;
            }
            entry4 = entry3;
            if ((entry3 = entry3.left()) != null) continue;
            return true;
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
        --this.count;
        return false;
    }

    @Override
    public boolean contains(Object object) {
        return this.findKey(object) != null;
    }

    public K get(Object object) {
        Entry<Object> entry = this.findKey(object);
        return entry == null ? null : (K)entry.key;
    }

    @Override
    public void clear() {
        this.count = 0;
        this.tree = null;
        this.lastEntry = null;
        this.firstEntry = null;
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
    public K first() {
        if (this.tree == null) {
            throw new NoSuchElementException();
        }
        return this.firstEntry.key;
    }

    @Override
    public K last() {
        if (this.tree == null) {
            throw new NoSuchElementException();
        }
        return this.lastEntry.key;
    }

    @Override
    public ObjectBidirectionalIterator<K> iterator() {
        return new SetIterator(this);
    }

    @Override
    public ObjectBidirectionalIterator<K> iterator(K k) {
        return new SetIterator(this, k);
    }

    @Override
    public Comparator<? super K> comparator() {
        return this.actualComparator;
    }

    @Override
    public ObjectSortedSet<K> headSet(K k) {
        return new Subset(this, null, true, k, false);
    }

    @Override
    public ObjectSortedSet<K> tailSet(K k) {
        return new Subset(this, k, false, null, true);
    }

    @Override
    public ObjectSortedSet<K> subSet(K k, K k2) {
        return new Subset(this, k, false, k2, false);
    }

    public Object clone() {
        ObjectAVLTreeSet objectAVLTreeSet;
        try {
            objectAVLTreeSet = (ObjectAVLTreeSet)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        objectAVLTreeSet.allocatePaths();
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
                            objectAVLTreeSet.tree = entry2.left;
                            objectAVLTreeSet.firstEntry = objectAVLTreeSet.tree;
                            while (objectAVLTreeSet.firstEntry.left != null) {
                                objectAVLTreeSet.firstEntry = objectAVLTreeSet.firstEntry.left;
                            }
                            objectAVLTreeSet.lastEntry = objectAVLTreeSet.tree;
                            while (objectAVLTreeSet.lastEntry.right != null) {
                                objectAVLTreeSet.lastEntry = objectAVLTreeSet.lastEntry.right;
                            }
                            return objectAVLTreeSet;
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
        return objectAVLTreeSet;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        int n = this.count;
        SetIterator setIterator = new SetIterator(this);
        objectOutputStream.defaultWriteObject();
        while (n-- != 0) {
            objectOutputStream.writeObject(setIterator.next());
        }
    }

    private Entry<K> readTree(ObjectInputStream objectInputStream, int n, Entry<K> entry, Entry<K> entry2) throws IOException, ClassNotFoundException {
        if (n == 1) {
            Entry<Object> entry3 = new Entry<Object>(objectInputStream.readObject());
            entry3.pred(entry);
            entry3.succ(entry2);
            return entry3;
        }
        if (n == 2) {
            Entry<Object> entry4 = new Entry<Object>(objectInputStream.readObject());
            entry4.right(new Entry<Object>(objectInputStream.readObject()));
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
        entry5.key = objectInputStream.readObject();
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
    public ObjectIterator iterator() {
        return this.iterator();
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }

    @Override
    public SortedSet tailSet(Object object) {
        return this.tailSet(object);
    }

    @Override
    public SortedSet headSet(Object object) {
        return this.headSet(object);
    }

    @Override
    public SortedSet subSet(Object object, Object object2) {
        return this.subSet(object, object2);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class Subset
    extends AbstractObjectSortedSet<K>
    implements Serializable,
    ObjectSortedSet<K> {
        private static final long serialVersionUID = -7046029254386353129L;
        K from;
        K to;
        boolean bottom;
        boolean top;
        final ObjectAVLTreeSet this$0;

        public Subset(ObjectAVLTreeSet objectAVLTreeSet, K k, boolean bl, K k2, boolean bl2) {
            this.this$0 = objectAVLTreeSet;
            if (!bl && !bl2 && objectAVLTreeSet.compare(k, k2) > 0) {
                throw new IllegalArgumentException("Start element (" + k + ") is larger than end element (" + k2 + ")");
            }
            this.from = k;
            this.bottom = bl;
            this.to = k2;
            this.top = bl2;
        }

        @Override
        public void clear() {
            SubsetIterator subsetIterator = new SubsetIterator(this);
            while (subsetIterator.hasNext()) {
                subsetIterator.next();
                subsetIterator.remove();
            }
        }

        final boolean in(K k) {
            return !(!this.bottom && this.this$0.compare(k, this.from) < 0 || !this.top && this.this$0.compare(k, this.to) >= 0);
        }

        @Override
        public boolean contains(Object object) {
            return this.in(object) && this.this$0.contains(object);
        }

        @Override
        public boolean add(K k) {
            if (!this.in(k)) {
                throw new IllegalArgumentException("Element (" + k + ") out of range [" + (this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")");
            }
            return this.this$0.add(k);
        }

        @Override
        public boolean remove(Object object) {
            if (!this.in(object)) {
                return true;
            }
            return this.this$0.remove(object);
        }

        @Override
        public int size() {
            SubsetIterator subsetIterator = new SubsetIterator(this);
            int n = 0;
            while (subsetIterator.hasNext()) {
                ++n;
                subsetIterator.next();
            }
            return n;
        }

        @Override
        public boolean isEmpty() {
            return !new SubsetIterator(this).hasNext();
        }

        @Override
        public Comparator<? super K> comparator() {
            return this.this$0.actualComparator;
        }

        @Override
        public ObjectBidirectionalIterator<K> iterator() {
            return new SubsetIterator(this);
        }

        @Override
        public ObjectBidirectionalIterator<K> iterator(K k) {
            return new SubsetIterator(this, k);
        }

        @Override
        public ObjectSortedSet<K> headSet(K k) {
            if (this.top) {
                return new Subset(this.this$0, this.from, this.bottom, k, false);
            }
            return this.this$0.compare(k, this.to) < 0 ? new Subset(this.this$0, this.from, this.bottom, k, false) : this;
        }

        @Override
        public ObjectSortedSet<K> tailSet(K k) {
            if (this.bottom) {
                return new Subset(this.this$0, k, false, this.to, this.top);
            }
            return this.this$0.compare(k, this.from) > 0 ? new Subset(this.this$0, k, false, this.to, this.top) : this;
        }

        @Override
        public ObjectSortedSet<K> subSet(K k, K k2) {
            if (this.top && this.bottom) {
                return new Subset(this.this$0, k, false, k2, false);
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
            return new Subset(this.this$0, k, false, k2, false);
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
        public K first() {
            Entry entry = this.firstEntry();
            if (entry == null) {
                throw new NoSuchElementException();
            }
            return entry.key;
        }

        @Override
        public K last() {
            Entry entry = this.lastEntry();
            if (entry == null) {
                throw new NoSuchElementException();
            }
            return entry.key;
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
        public SortedSet tailSet(Object object) {
            return this.tailSet(object);
        }

        @Override
        public SortedSet headSet(Object object) {
            return this.headSet(object);
        }

        @Override
        public SortedSet subSet(Object object, Object object2) {
            return this.subSet(object, object2);
        }

        private final class SubsetIterator
        extends SetIterator {
            final Subset this$1;

            SubsetIterator(Subset subset) {
                this.this$1 = subset;
                super(subset.this$0);
                this.next = subset.firstEntry();
            }

            /*
             * Enabled aggressive block sorting
             */
            SubsetIterator(Subset subset, K k) {
                this(subset);
                if (this.next == null) return;
                if (!subset.bottom && subset.this$0.compare(k, this.next.key) < 0) {
                    this.prev = null;
                    return;
                }
                if (!subset.top) {
                    this.prev = subset.lastEntry();
                    if (subset.this$0.compare(k, this.prev.key) >= 0) {
                        this.next = null;
                        return;
                    }
                }
                this.next = subset.this$0.locateKey(k);
                if (subset.this$0.compare(this.next.key, k) <= 0) {
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
    }

    private class SetIterator
    implements ObjectListIterator<K> {
        Entry<K> prev;
        Entry<K> next;
        Entry<K> curr;
        int index;
        final ObjectAVLTreeSet this$0;

        SetIterator(ObjectAVLTreeSet objectAVLTreeSet) {
            this.this$0 = objectAVLTreeSet;
            this.index = 0;
            this.next = objectAVLTreeSet.firstEntry;
        }

        SetIterator(ObjectAVLTreeSet objectAVLTreeSet, K k) {
            this.this$0 = objectAVLTreeSet;
            this.index = 0;
            this.next = objectAVLTreeSet.locateKey(k);
            if (this.next != null) {
                if (objectAVLTreeSet.compare(this.next.key, k) <= 0) {
                    this.prev = this.next;
                    this.next = this.next.next();
                } else {
                    this.prev = this.next.prev();
                }
            }
        }

        @Override
        public boolean hasNext() {
            return this.next != null;
        }

        @Override
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

        @Override
        public K next() {
            return this.nextEntry().key;
        }

        @Override
        public K previous() {
            return this.previousEntry().key;
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

        @Override
        public int nextIndex() {
            return this.index;
        }

        @Override
        public int previousIndex() {
            return this.index - 1;
        }

        @Override
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
    }

    private static final class Entry<K>
    implements Cloneable {
        private static final int SUCC_MASK = Integer.MIN_VALUE;
        private static final int PRED_MASK = 0x40000000;
        private static final int BALANCE_MASK = 255;
        K key;
        Entry<K> left;
        Entry<K> right;
        int info;

        Entry() {
        }

        Entry(K k) {
            this.key = k;
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

        public Entry<K> clone() {
            Entry entry;
            try {
                entry = (Entry)super.clone();
            } catch (CloneNotSupportedException cloneNotSupportedException) {
                throw new InternalError();
            }
            entry.key = this.key;
            entry.info = this.info;
            return entry;
        }

        public boolean equals(Object object) {
            if (!(object instanceof Entry)) {
                return true;
            }
            Entry entry = (Entry)object;
            return Objects.equals(this.key, entry.key);
        }

        public int hashCode() {
            return this.key.hashCode();
        }

        public String toString() {
            return String.valueOf(this.key);
        }

        public Object clone() throws CloneNotSupportedException {
            return this.clone();
        }
    }
}

