/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.AbstractIntSortedSet;
import it.unimi.dsi.fastutil.ints.IntArrays;
import it.unimi.dsi.fastutil.ints.IntBidirectionalIterator;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntComparator;
import it.unimi.dsi.fastutil.ints.IntComparators;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntIterators;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.SortedSet;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class IntRBTreeSet
extends AbstractIntSortedSet
implements Serializable,
Cloneable,
IntSortedSet {
    protected transient Entry tree;
    protected int count;
    protected transient Entry firstEntry;
    protected transient Entry lastEntry;
    protected Comparator<? super Integer> storedComparator;
    protected transient IntComparator actualComparator;
    private static final long serialVersionUID = -7046029254386353130L;
    private transient boolean[] dirPath;
    private transient Entry[] nodePath;

    public IntRBTreeSet() {
        this.allocatePaths();
        this.tree = null;
        this.count = 0;
    }

    private void setActualComparator() {
        this.actualComparator = IntComparators.asIntComparator(this.storedComparator);
    }

    public IntRBTreeSet(Comparator<? super Integer> comparator) {
        this();
        this.storedComparator = comparator;
        this.setActualComparator();
    }

    public IntRBTreeSet(Collection<? extends Integer> collection) {
        this();
        this.addAll(collection);
    }

    public IntRBTreeSet(SortedSet<Integer> sortedSet) {
        this(sortedSet.comparator());
        this.addAll(sortedSet);
    }

    public IntRBTreeSet(IntCollection intCollection) {
        this();
        this.addAll(intCollection);
    }

    public IntRBTreeSet(IntSortedSet intSortedSet) {
        this(intSortedSet.comparator());
        this.addAll(intSortedSet);
    }

    public IntRBTreeSet(IntIterator intIterator) {
        this.allocatePaths();
        while (intIterator.hasNext()) {
            this.add(intIterator.nextInt());
        }
    }

    public IntRBTreeSet(Iterator<?> iterator2) {
        this(IntIterators.asIntIterator(iterator2));
    }

    public IntRBTreeSet(int[] nArray, int n, int n2, Comparator<? super Integer> comparator) {
        this(comparator);
        IntArrays.ensureOffsetLength(nArray, n, n2);
        for (int i = 0; i < n2; ++i) {
            this.add(nArray[n + i]);
        }
    }

    public IntRBTreeSet(int[] nArray, int n, int n2) {
        this(nArray, n, n2, null);
    }

    public IntRBTreeSet(int[] nArray) {
        this();
        int n = nArray.length;
        while (n-- != 0) {
            this.add(nArray[n]);
        }
    }

    public IntRBTreeSet(int[] nArray, Comparator<? super Integer> comparator) {
        this(comparator);
        int n = nArray.length;
        while (n-- != 0) {
            this.add(nArray[n]);
        }
    }

    final int compare(int n, int n2) {
        return this.actualComparator == null ? Integer.compare(n, n2) : this.actualComparator.compare(n, n2);
    }

    private Entry findKey(int n) {
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

    @Override
    public boolean add(int n) {
        int n2 = 0;
        if (this.tree == null) {
            ++this.count;
            this.lastEntry = this.firstEntry = new Entry(n);
            this.tree = this.firstEntry;
        } else {
            Entry entry = this.tree;
            int n3 = 0;
            while (true) {
                Entry entry2;
                int n4;
                if ((n4 = this.compare(n, entry.key)) == 0) {
                    while (n3-- != 0) {
                        this.nodePath[n3] = null;
                    }
                    return true;
                }
                this.nodePath[n3] = entry;
                this.dirPath[n3++] = n4 > 0;
                if (this.dirPath[n3++]) {
                    if (entry.succ()) {
                        ++this.count;
                        entry2 = new Entry(n);
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
                    entry2 = new Entry(n);
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
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean remove(int n) {
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
                                        if (this.tree == null) {
                                            return true;
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
                                                        return true;
                                                    }
                                                    this.nodePath[n3] = null;
                                                }
                                            }
                                            if ((entry3 = entry3.left()) == null) break;
                                        }
                                        while (true) {
                                            if (n3-- == 0) {
                                                return true;
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
        --this.count;
        while (n2-- != 0) {
            this.nodePath[n2] = null;
        }
        return false;
    }

    @Override
    public boolean contains(int n) {
        return this.findKey(n) != null;
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
    public int firstInt() {
        if (this.tree == null) {
            throw new NoSuchElementException();
        }
        return this.firstEntry.key;
    }

    @Override
    public int lastInt() {
        if (this.tree == null) {
            throw new NoSuchElementException();
        }
        return this.lastEntry.key;
    }

    @Override
    public IntBidirectionalIterator iterator() {
        return new SetIterator(this);
    }

    @Override
    public IntBidirectionalIterator iterator(int n) {
        return new SetIterator(this, n);
    }

    @Override
    public IntComparator comparator() {
        return this.actualComparator;
    }

    @Override
    public IntSortedSet headSet(int n) {
        return new Subset(this, 0, true, n, false);
    }

    @Override
    public IntSortedSet tailSet(int n) {
        return new Subset(this, n, false, 0, true);
    }

    @Override
    public IntSortedSet subSet(int n, int n2) {
        return new Subset(this, n, false, n2, false);
    }

    public Object clone() {
        IntRBTreeSet intRBTreeSet;
        try {
            intRBTreeSet = (IntRBTreeSet)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        intRBTreeSet.allocatePaths();
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
                            intRBTreeSet.firstEntry = intRBTreeSet.tree = entry2.left;
                            while (intRBTreeSet.firstEntry.left != null) {
                                intRBTreeSet.firstEntry = intRBTreeSet.firstEntry.left;
                            }
                            intRBTreeSet.lastEntry = intRBTreeSet.tree;
                            while (intRBTreeSet.lastEntry.right != null) {
                                intRBTreeSet.lastEntry = intRBTreeSet.lastEntry.right;
                            }
                            return intRBTreeSet;
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
        return intRBTreeSet;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        int n = this.count;
        SetIterator setIterator = new SetIterator(this);
        objectOutputStream.defaultWriteObject();
        while (n-- != 0) {
            objectOutputStream.writeInt(setIterator.nextInt());
        }
    }

    private Entry readTree(ObjectInputStream objectInputStream, int n, Entry entry, Entry entry2) throws IOException, ClassNotFoundException {
        if (n == 1) {
            Entry entry3 = new Entry(objectInputStream.readInt());
            entry3.pred(entry);
            entry3.succ(entry2);
            entry3.black(false);
            return entry3;
        }
        if (n == 2) {
            Entry entry4 = new Entry(objectInputStream.readInt());
            entry4.black(false);
            entry4.right(new Entry(objectInputStream.readInt()));
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
    public IntIterator iterator() {
        return this.iterator();
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }

    @Override
    public Comparator comparator() {
        return this.comparator();
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class Subset
    extends AbstractIntSortedSet
    implements Serializable,
    IntSortedSet {
        private static final long serialVersionUID = -7046029254386353129L;
        int from;
        int to;
        boolean bottom;
        boolean top;
        final IntRBTreeSet this$0;

        public Subset(IntRBTreeSet intRBTreeSet, int n, boolean bl, int n2, boolean bl2) {
            this.this$0 = intRBTreeSet;
            if (!bl && !bl2 && intRBTreeSet.compare(n, n2) > 0) {
                throw new IllegalArgumentException("Start element (" + n + ") is larger than end element (" + n2 + ")");
            }
            this.from = n;
            this.bottom = bl;
            this.to = n2;
            this.top = bl2;
        }

        @Override
        public void clear() {
            SubsetIterator subsetIterator = new SubsetIterator(this);
            while (subsetIterator.hasNext()) {
                subsetIterator.nextInt();
                subsetIterator.remove();
            }
        }

        final boolean in(int n) {
            return !(!this.bottom && this.this$0.compare(n, this.from) < 0 || !this.top && this.this$0.compare(n, this.to) >= 0);
        }

        @Override
        public boolean contains(int n) {
            return this.in(n) && this.this$0.contains(n);
        }

        @Override
        public boolean add(int n) {
            if (!this.in(n)) {
                throw new IllegalArgumentException("Element (" + n + ") out of range [" + (this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")");
            }
            return this.this$0.add(n);
        }

        @Override
        public boolean remove(int n) {
            if (!this.in(n)) {
                return true;
            }
            return this.this$0.remove(n);
        }

        @Override
        public int size() {
            SubsetIterator subsetIterator = new SubsetIterator(this);
            int n = 0;
            while (subsetIterator.hasNext()) {
                ++n;
                subsetIterator.nextInt();
            }
            return n;
        }

        @Override
        public boolean isEmpty() {
            return !new SubsetIterator(this).hasNext();
        }

        @Override
        public IntComparator comparator() {
            return this.this$0.actualComparator;
        }

        @Override
        public IntBidirectionalIterator iterator() {
            return new SubsetIterator(this);
        }

        @Override
        public IntBidirectionalIterator iterator(int n) {
            return new SubsetIterator(this, n);
        }

        @Override
        public IntSortedSet headSet(int n) {
            if (this.top) {
                return new Subset(this.this$0, this.from, this.bottom, n, false);
            }
            return this.this$0.compare(n, this.to) < 0 ? new Subset(this.this$0, this.from, this.bottom, n, false) : this;
        }

        @Override
        public IntSortedSet tailSet(int n) {
            if (this.bottom) {
                return new Subset(this.this$0, n, false, this.to, this.top);
            }
            return this.this$0.compare(n, this.from) > 0 ? new Subset(this.this$0, n, false, this.to, this.top) : this;
        }

        @Override
        public IntSortedSet subSet(int n, int n2) {
            if (this.top && this.bottom) {
                return new Subset(this.this$0, n, false, n2, false);
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
            return new Subset(this.this$0, n, false, n2, false);
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
        public int firstInt() {
            Entry entry = this.firstEntry();
            if (entry == null) {
                throw new NoSuchElementException();
            }
            return entry.key;
        }

        @Override
        public int lastInt() {
            Entry entry = this.lastEntry();
            if (entry == null) {
                throw new NoSuchElementException();
            }
            return entry.key;
        }

        @Override
        public IntIterator iterator() {
            return this.iterator();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        @Override
        public Comparator comparator() {
            return this.comparator();
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
            SubsetIterator(Subset subset, int n) {
                this(subset);
                if (this.next == null) return;
                if (!subset.bottom && subset.this$0.compare(n, this.next.key) < 0) {
                    this.prev = null;
                    return;
                }
                if (!subset.top) {
                    this.prev = subset.lastEntry();
                    if (subset.this$0.compare(n, this.prev.key) >= 0) {
                        this.next = null;
                        return;
                    }
                }
                this.next = subset.this$0.locateKey(n);
                if (subset.this$0.compare(this.next.key, n) <= 0) {
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
    implements IntListIterator {
        Entry prev;
        Entry next;
        Entry curr;
        int index;
        final IntRBTreeSet this$0;

        SetIterator(IntRBTreeSet intRBTreeSet) {
            this.this$0 = intRBTreeSet;
            this.index = 0;
            this.next = intRBTreeSet.firstEntry;
        }

        SetIterator(IntRBTreeSet intRBTreeSet, int n) {
            this.this$0 = intRBTreeSet;
            this.index = 0;
            this.next = intRBTreeSet.locateKey(n);
            if (this.next != null) {
                if (intRBTreeSet.compare(this.next.key, n) <= 0) {
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
        public int nextInt() {
            return this.nextEntry().key;
        }

        @Override
        public int previousInt() {
            return this.previousEntry().key;
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

        Entry previousEntry() {
            if (!this.hasPrevious()) {
                throw new NoSuchElementException();
            }
            this.curr = this.next = this.prev;
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
            this.next = this.prev = this.curr;
            this.updatePrevious();
            this.updateNext();
            this.this$0.remove(this.curr.key);
            this.curr = null;
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static final class Entry
    implements Cloneable {
        private static final int BLACK_MASK = 1;
        private static final int SUCC_MASK = Integer.MIN_VALUE;
        private static final int PRED_MASK = 0x40000000;
        int key;
        Entry left;
        Entry right;
        int info;

        Entry() {
        }

        Entry(int n) {
            this.key = n;
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

        public Entry clone() {
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
            return this.key == entry.key;
        }

        public int hashCode() {
            return this.key;
        }

        public String toString() {
            return String.valueOf(this.key);
        }

        public Object clone() throws CloneNotSupportedException {
            return this.clone();
        }
    }
}

