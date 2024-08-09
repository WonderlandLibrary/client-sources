/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleSortedSet;
import it.unimi.dsi.fastutil.doubles.DoubleArrays;
import it.unimi.dsi.fastutil.doubles.DoubleBidirectionalIterator;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleComparator;
import it.unimi.dsi.fastutil.doubles.DoubleComparators;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleIterators;
import it.unimi.dsi.fastutil.doubles.DoubleListIterator;
import it.unimi.dsi.fastutil.doubles.DoubleSortedSet;
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
public class DoubleRBTreeSet
extends AbstractDoubleSortedSet
implements Serializable,
Cloneable,
DoubleSortedSet {
    protected transient Entry tree;
    protected int count;
    protected transient Entry firstEntry;
    protected transient Entry lastEntry;
    protected Comparator<? super Double> storedComparator;
    protected transient DoubleComparator actualComparator;
    private static final long serialVersionUID = -7046029254386353130L;
    private transient boolean[] dirPath;
    private transient Entry[] nodePath;

    public DoubleRBTreeSet() {
        this.allocatePaths();
        this.tree = null;
        this.count = 0;
    }

    private void setActualComparator() {
        this.actualComparator = DoubleComparators.asDoubleComparator(this.storedComparator);
    }

    public DoubleRBTreeSet(Comparator<? super Double> comparator) {
        this();
        this.storedComparator = comparator;
        this.setActualComparator();
    }

    public DoubleRBTreeSet(Collection<? extends Double> collection) {
        this();
        this.addAll(collection);
    }

    public DoubleRBTreeSet(SortedSet<Double> sortedSet) {
        this(sortedSet.comparator());
        this.addAll(sortedSet);
    }

    public DoubleRBTreeSet(DoubleCollection doubleCollection) {
        this();
        this.addAll(doubleCollection);
    }

    public DoubleRBTreeSet(DoubleSortedSet doubleSortedSet) {
        this(doubleSortedSet.comparator());
        this.addAll(doubleSortedSet);
    }

    public DoubleRBTreeSet(DoubleIterator doubleIterator) {
        this.allocatePaths();
        while (doubleIterator.hasNext()) {
            this.add(doubleIterator.nextDouble());
        }
    }

    public DoubleRBTreeSet(Iterator<?> iterator2) {
        this(DoubleIterators.asDoubleIterator(iterator2));
    }

    public DoubleRBTreeSet(double[] dArray, int n, int n2, Comparator<? super Double> comparator) {
        this(comparator);
        DoubleArrays.ensureOffsetLength(dArray, n, n2);
        for (int i = 0; i < n2; ++i) {
            this.add(dArray[n + i]);
        }
    }

    public DoubleRBTreeSet(double[] dArray, int n, int n2) {
        this(dArray, n, n2, null);
    }

    public DoubleRBTreeSet(double[] dArray) {
        this();
        int n = dArray.length;
        while (n-- != 0) {
            this.add(dArray[n]);
        }
    }

    public DoubleRBTreeSet(double[] dArray, Comparator<? super Double> comparator) {
        this(comparator);
        int n = dArray.length;
        while (n-- != 0) {
            this.add(dArray[n]);
        }
    }

    final int compare(double d, double d2) {
        return this.actualComparator == null ? Double.compare(d, d2) : this.actualComparator.compare(d, d2);
    }

    private Entry findKey(double d) {
        int n;
        Entry entry = this.tree;
        while (entry != null && (n = this.compare(d, entry.key)) != 0) {
            entry = n < 0 ? entry.left() : entry.right();
        }
        return entry;
    }

    final Entry locateKey(double d) {
        Entry entry = this.tree;
        Entry entry2 = this.tree;
        int n = 0;
        while (entry != null && (n = this.compare(d, entry.key)) != 0) {
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
    public boolean add(double d) {
        int n = 0;
        if (this.tree == null) {
            ++this.count;
            this.lastEntry = this.firstEntry = new Entry(d);
            this.tree = this.firstEntry;
        } else {
            Entry entry = this.tree;
            int n2 = 0;
            while (true) {
                Entry entry2;
                int n3;
                if ((n3 = this.compare(d, entry.key)) == 0) {
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
                        entry2 = new Entry(d);
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
                    entry2 = new Entry(d);
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
    public boolean remove(double d) {
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
                                        if (this.tree == null) {
                                            return true;
                                        }
                                        entry3 = this.tree;
                                        n2 = 0;
                                        double d2 = d;
                                        while (true) {
                                            int n4;
                                            if ((n4 = this.compare(d2, entry3.key)) == 0) {
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
        --this.count;
        while (n-- != 0) {
            this.nodePath[n] = null;
        }
        return false;
    }

    @Override
    public boolean contains(double d) {
        return this.findKey(d) != null;
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
    public double firstDouble() {
        if (this.tree == null) {
            throw new NoSuchElementException();
        }
        return this.firstEntry.key;
    }

    @Override
    public double lastDouble() {
        if (this.tree == null) {
            throw new NoSuchElementException();
        }
        return this.lastEntry.key;
    }

    @Override
    public DoubleBidirectionalIterator iterator() {
        return new SetIterator(this);
    }

    @Override
    public DoubleBidirectionalIterator iterator(double d) {
        return new SetIterator(this, d);
    }

    @Override
    public DoubleComparator comparator() {
        return this.actualComparator;
    }

    @Override
    public DoubleSortedSet headSet(double d) {
        return new Subset(this, 0.0, true, d, false);
    }

    @Override
    public DoubleSortedSet tailSet(double d) {
        return new Subset(this, d, false, 0.0, true);
    }

    @Override
    public DoubleSortedSet subSet(double d, double d2) {
        return new Subset(this, d, false, d2, false);
    }

    public Object clone() {
        DoubleRBTreeSet doubleRBTreeSet;
        try {
            doubleRBTreeSet = (DoubleRBTreeSet)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        doubleRBTreeSet.allocatePaths();
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
                            doubleRBTreeSet.firstEntry = doubleRBTreeSet.tree = entry2.left;
                            while (doubleRBTreeSet.firstEntry.left != null) {
                                doubleRBTreeSet.firstEntry = doubleRBTreeSet.firstEntry.left;
                            }
                            doubleRBTreeSet.lastEntry = doubleRBTreeSet.tree;
                            while (doubleRBTreeSet.lastEntry.right != null) {
                                doubleRBTreeSet.lastEntry = doubleRBTreeSet.lastEntry.right;
                            }
                            return doubleRBTreeSet;
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
        return doubleRBTreeSet;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        int n = this.count;
        SetIterator setIterator = new SetIterator(this);
        objectOutputStream.defaultWriteObject();
        while (n-- != 0) {
            objectOutputStream.writeDouble(setIterator.nextDouble());
        }
    }

    private Entry readTree(ObjectInputStream objectInputStream, int n, Entry entry, Entry entry2) throws IOException, ClassNotFoundException {
        if (n == 1) {
            Entry entry3 = new Entry(objectInputStream.readDouble());
            entry3.pred(entry);
            entry3.succ(entry2);
            entry3.black(false);
            return entry3;
        }
        if (n == 2) {
            Entry entry4 = new Entry(objectInputStream.readDouble());
            entry4.black(false);
            entry4.right(new Entry(objectInputStream.readDouble()));
            entry4.right.pred(entry4);
            entry4.pred(entry);
            entry4.right.succ(entry2);
            return entry4;
        }
        int n2 = n / 2;
        int n3 = n - n2 - 1;
        Entry entry5 = new Entry();
        entry5.left(this.readTree(objectInputStream, n3, entry, entry5));
        entry5.key = objectInputStream.readDouble();
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
    public DoubleIterator iterator() {
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
    extends AbstractDoubleSortedSet
    implements Serializable,
    DoubleSortedSet {
        private static final long serialVersionUID = -7046029254386353129L;
        double from;
        double to;
        boolean bottom;
        boolean top;
        final DoubleRBTreeSet this$0;

        public Subset(DoubleRBTreeSet doubleRBTreeSet, double d, boolean bl, double d2, boolean bl2) {
            this.this$0 = doubleRBTreeSet;
            if (!bl && !bl2 && doubleRBTreeSet.compare(d, d2) > 0) {
                throw new IllegalArgumentException("Start element (" + d + ") is larger than end element (" + d2 + ")");
            }
            this.from = d;
            this.bottom = bl;
            this.to = d2;
            this.top = bl2;
        }

        @Override
        public void clear() {
            SubsetIterator subsetIterator = new SubsetIterator(this);
            while (subsetIterator.hasNext()) {
                subsetIterator.nextDouble();
                subsetIterator.remove();
            }
        }

        final boolean in(double d) {
            return !(!this.bottom && this.this$0.compare(d, this.from) < 0 || !this.top && this.this$0.compare(d, this.to) >= 0);
        }

        @Override
        public boolean contains(double d) {
            return this.in(d) && this.this$0.contains(d);
        }

        @Override
        public boolean add(double d) {
            if (!this.in(d)) {
                throw new IllegalArgumentException("Element (" + d + ") out of range [" + (this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")");
            }
            return this.this$0.add(d);
        }

        @Override
        public boolean remove(double d) {
            if (!this.in(d)) {
                return true;
            }
            return this.this$0.remove(d);
        }

        @Override
        public int size() {
            SubsetIterator subsetIterator = new SubsetIterator(this);
            int n = 0;
            while (subsetIterator.hasNext()) {
                ++n;
                subsetIterator.nextDouble();
            }
            return n;
        }

        @Override
        public boolean isEmpty() {
            return !new SubsetIterator(this).hasNext();
        }

        @Override
        public DoubleComparator comparator() {
            return this.this$0.actualComparator;
        }

        @Override
        public DoubleBidirectionalIterator iterator() {
            return new SubsetIterator(this);
        }

        @Override
        public DoubleBidirectionalIterator iterator(double d) {
            return new SubsetIterator(this, d);
        }

        @Override
        public DoubleSortedSet headSet(double d) {
            if (this.top) {
                return new Subset(this.this$0, this.from, this.bottom, d, false);
            }
            return this.this$0.compare(d, this.to) < 0 ? new Subset(this.this$0, this.from, this.bottom, d, false) : this;
        }

        @Override
        public DoubleSortedSet tailSet(double d) {
            if (this.bottom) {
                return new Subset(this.this$0, d, false, this.to, this.top);
            }
            return this.this$0.compare(d, this.from) > 0 ? new Subset(this.this$0, d, false, this.to, this.top) : this;
        }

        @Override
        public DoubleSortedSet subSet(double d, double d2) {
            if (this.top && this.bottom) {
                return new Subset(this.this$0, d, false, d2, false);
            }
            if (!this.top) {
                double d3 = d2 = this.this$0.compare(d2, this.to) < 0 ? d2 : this.to;
            }
            if (!this.bottom) {
                double d4 = d = this.this$0.compare(d, this.from) > 0 ? d : this.from;
            }
            if (!this.top && !this.bottom && d == this.from && d2 == this.to) {
                return this;
            }
            return new Subset(this.this$0, d, false, d2, false);
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
        public double firstDouble() {
            Entry entry = this.firstEntry();
            if (entry == null) {
                throw new NoSuchElementException();
            }
            return entry.key;
        }

        @Override
        public double lastDouble() {
            Entry entry = this.lastEntry();
            if (entry == null) {
                throw new NoSuchElementException();
            }
            return entry.key;
        }

        @Override
        public DoubleIterator iterator() {
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
            SubsetIterator(Subset subset, double d) {
                this(subset);
                if (this.next == null) return;
                if (!subset.bottom && subset.this$0.compare(d, this.next.key) < 0) {
                    this.prev = null;
                    return;
                }
                if (!subset.top) {
                    this.prev = subset.lastEntry();
                    if (subset.this$0.compare(d, this.prev.key) >= 0) {
                        this.next = null;
                        return;
                    }
                }
                this.next = subset.this$0.locateKey(d);
                if (subset.this$0.compare(this.next.key, d) <= 0) {
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
    implements DoubleListIterator {
        Entry prev;
        Entry next;
        Entry curr;
        int index;
        final DoubleRBTreeSet this$0;

        SetIterator(DoubleRBTreeSet doubleRBTreeSet) {
            this.this$0 = doubleRBTreeSet;
            this.index = 0;
            this.next = doubleRBTreeSet.firstEntry;
        }

        SetIterator(DoubleRBTreeSet doubleRBTreeSet, double d) {
            this.this$0 = doubleRBTreeSet;
            this.index = 0;
            this.next = doubleRBTreeSet.locateKey(d);
            if (this.next != null) {
                if (doubleRBTreeSet.compare(this.next.key, d) <= 0) {
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
        public double nextDouble() {
            return this.nextEntry().key;
        }

        @Override
        public double previousDouble() {
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
        double key;
        Entry left;
        Entry right;
        int info;

        Entry() {
        }

        Entry(double d) {
            this.key = d;
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
            return Double.doubleToLongBits(this.key) == Double.doubleToLongBits(entry.key);
        }

        public int hashCode() {
            return HashCommon.double2int(this.key);
        }

        public String toString() {
            return String.valueOf(this.key);
        }

        public Object clone() throws CloneNotSupportedException {
            return this.clone();
        }
    }
}

