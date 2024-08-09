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
public class ObjectRBTreeSet<K>
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
    private transient Entry<K>[] nodePath;

    public ObjectRBTreeSet() {
        this.allocatePaths();
        this.tree = null;
        this.count = 0;
    }

    private void setActualComparator() {
        this.actualComparator = this.storedComparator;
    }

    public ObjectRBTreeSet(Comparator<? super K> comparator) {
        this();
        this.storedComparator = comparator;
    }

    public ObjectRBTreeSet(Collection<? extends K> collection) {
        this();
        this.addAll(collection);
    }

    public ObjectRBTreeSet(SortedSet<K> sortedSet) {
        this(sortedSet.comparator());
        this.addAll(sortedSet);
    }

    public ObjectRBTreeSet(ObjectCollection<? extends K> objectCollection) {
        this();
        this.addAll(objectCollection);
    }

    public ObjectRBTreeSet(ObjectSortedSet<K> objectSortedSet) {
        this(objectSortedSet.comparator());
        this.addAll(objectSortedSet);
    }

    public ObjectRBTreeSet(Iterator<? extends K> iterator2) {
        this.allocatePaths();
        while (iterator2.hasNext()) {
            this.add(iterator2.next());
        }
    }

    public ObjectRBTreeSet(K[] KArray, int n, int n2, Comparator<? super K> comparator) {
        this(comparator);
        ObjectArrays.ensureOffsetLength(KArray, n, n2);
        for (int i = 0; i < n2; ++i) {
            this.add(KArray[n + i]);
        }
    }

    public ObjectRBTreeSet(K[] KArray, int n, int n2) {
        this(KArray, n, n2, null);
    }

    public ObjectRBTreeSet(K[] KArray) {
        this();
        int n = KArray.length;
        while (n-- != 0) {
            this.add(KArray[n]);
        }
    }

    public ObjectRBTreeSet(K[] KArray, Comparator<? super K> comparator) {
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
        this.dirPath = new boolean[64];
        this.nodePath = new Entry[64];
    }

    @Override
    public boolean add(K k) {
        int n = 0;
        if (this.tree == null) {
            ++this.count;
            this.firstEntry = new Entry<K>(k);
            this.lastEntry = this.firstEntry;
            this.tree = this.firstEntry;
        } else {
            Entry<K> entry = this.tree;
            int n2 = 0;
            while (true) {
                Entry<K> entry2;
                int n3;
                if ((n3 = this.compare(k, entry.key)) == 0) {
                    while (n2-- != 0) {
                        this.nodePath[n2] = null;
                    }
                    return true;
                }
                this.nodePath[n2] = entry;
                this.dirPath[n2++] = n3 > 0;
                if (this.dirPath[n2++]) {
                    if (entry.succ()) {
                        ++this.count;
                        entry2 = new Entry<K>(k);
                        if (entry.right == null) {
                            this.lastEntry = entry2;
                        }
                        entry2.left = entry;
                        entry2.right = entry.right;
                        entry.right(entry2);
                        break;
                    }
                    entry = entry.right;
                    continue;
                }
                if (entry.pred()) {
                    ++this.count;
                    entry2 = new Entry<K>(k);
                    if (entry.left == null) {
                        this.firstEntry = entry2;
                    }
                    entry2.right = entry;
                    entry2.left = entry.left;
                    entry.left(entry2);
                    break;
                }
                entry = entry.left;
            }
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
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean remove(Object object) {
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
                                        if (this.tree == null) {
                                            return true;
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
                                                        return true;
                                                    }
                                                    this.nodePath[n2] = null;
                                                }
                                            }
                                            if ((entry3 = entry3.left()) == null) break;
                                        }
                                        while (true) {
                                            if (n2-- == 0) {
                                                return true;
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
        --this.count;
        while (n-- != 0) {
            this.nodePath[n] = null;
        }
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
        ObjectRBTreeSet objectRBTreeSet;
        try {
            objectRBTreeSet = (ObjectRBTreeSet)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        objectRBTreeSet.allocatePaths();
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
                            objectRBTreeSet.tree = entry2.left;
                            objectRBTreeSet.firstEntry = objectRBTreeSet.tree;
                            while (objectRBTreeSet.firstEntry.left != null) {
                                objectRBTreeSet.firstEntry = objectRBTreeSet.firstEntry.left;
                            }
                            objectRBTreeSet.lastEntry = objectRBTreeSet.tree;
                            while (objectRBTreeSet.lastEntry.right != null) {
                                objectRBTreeSet.lastEntry = objectRBTreeSet.lastEntry.right;
                            }
                            return objectRBTreeSet;
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
        return objectRBTreeSet;
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
            entry3.black(false);
            return entry3;
        }
        if (n == 2) {
            Entry<Object> entry4 = new Entry<Object>(objectInputStream.readObject());
            entry4.black(false);
            entry4.right(new Entry<Object>(objectInputStream.readObject()));
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
        final ObjectRBTreeSet this$0;

        public Subset(ObjectRBTreeSet objectRBTreeSet, K k, boolean bl, K k2, boolean bl2) {
            this.this$0 = objectRBTreeSet;
            if (!bl && !bl2 && objectRBTreeSet.compare(k, k2) > 0) {
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
        final ObjectRBTreeSet this$0;

        SetIterator(ObjectRBTreeSet objectRBTreeSet) {
            this.this$0 = objectRBTreeSet;
            this.index = 0;
            this.next = objectRBTreeSet.firstEntry;
        }

        SetIterator(ObjectRBTreeSet objectRBTreeSet, K k) {
            this.this$0 = objectRBTreeSet;
            this.index = 0;
            this.next = objectRBTreeSet.locateKey(k);
            if (this.next != null) {
                if (objectRBTreeSet.compare(this.next.key, k) <= 0) {
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

        void updatePrevious() {
            this.prev = this.prev.prev();
        }

        @Override
        public K next() {
            return this.nextEntry().key;
        }

        @Override
        public K previous() {
            return this.previousEntry().key;
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
        private static final int BLACK_MASK = 1;
        private static final int SUCC_MASK = Integer.MIN_VALUE;
        private static final int PRED_MASK = 0x40000000;
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

