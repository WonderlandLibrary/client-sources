/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.AbstractObjectListIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.SortedSet;

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
    private static final boolean ASSERTS = false;
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

    public ObjectRBTreeSet(Comparator<? super K> c) {
        this();
        this.storedComparator = c;
    }

    public ObjectRBTreeSet(Collection<? extends K> c) {
        this();
        this.addAll(c);
    }

    public ObjectRBTreeSet(SortedSet<K> s) {
        this(s.comparator());
        this.addAll((Collection<K>)s);
    }

    public ObjectRBTreeSet(ObjectCollection<? extends K> c) {
        this();
        this.addAll((Collection<? extends K>)c);
    }

    public ObjectRBTreeSet(ObjectSortedSet<K> s) {
        this(s.comparator());
        this.addAll(s);
    }

    public ObjectRBTreeSet(Iterator<? extends K> i) {
        this.allocatePaths();
        while (i.hasNext()) {
            this.add(i.next());
        }
    }

    public ObjectRBTreeSet(K[] a, int offset, int length, Comparator<? super K> c) {
        this(c);
        ObjectArrays.ensureOffsetLength(a, offset, length);
        for (int i = 0; i < length; ++i) {
            this.add(a[offset + i]);
        }
    }

    public ObjectRBTreeSet(K[] a, int offset, int length) {
        this(a, offset, length, null);
    }

    public ObjectRBTreeSet(K[] a) {
        this();
        int i = a.length;
        while (i-- != 0) {
            this.add(a[i]);
        }
    }

    public ObjectRBTreeSet(K[] a, Comparator<? super K> c) {
        this(c);
        int i = a.length;
        while (i-- != 0) {
            this.add(a[i]);
        }
    }

    final int compare(K k1, K k2) {
        return this.actualComparator == null ? ((Comparable)k1).compareTo(k2) : this.actualComparator.compare(k1, k2);
    }

    private Entry<K> findKey(K k) {
        int cmp;
        Entry<K> e = this.tree;
        while (e != null && (cmp = this.compare(k, e.key)) != 0) {
            e = cmp < 0 ? e.left() : e.right();
        }
        return e;
    }

    final Entry<K> locateKey(K k) {
        Entry<K> e = this.tree;
        Entry<K> last = this.tree;
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
    public boolean add(K k) {
        int maxDepth = 0;
        if (this.tree == null) {
            ++this.count;
            this.firstEntry = new Entry<K>(k);
            this.lastEntry = this.firstEntry;
            this.tree = this.firstEntry;
        } else {
            Entry<K> p = this.tree;
            int i = 0;
            while (true) {
                Entry<K> e;
                int cmp;
                if ((cmp = this.compare(k, p.key)) == 0) {
                    while (i-- != 0) {
                        this.nodePath[i] = null;
                    }
                    return false;
                }
                this.nodePath[i] = p;
                this.dirPath[i++] = cmp > 0;
                if (this.dirPath[i++]) {
                    if (p.succ()) {
                        ++this.count;
                        e = new Entry<K>(k);
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
                    e = new Entry<K>(k);
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
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean rem(Object k) {
        int i;
        Entry<K> p;
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
                                        if (this.tree == null) {
                                            return false;
                                        }
                                        p = this.tree;
                                        i = 0;
                                        Object kk = k;
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
                                                        return false;
                                                    }
                                                    this.nodePath[i] = null;
                                                }
                                            }
                                            if ((p = p.left()) == null) break;
                                        }
                                        while (true) {
                                            if (i-- == 0) {
                                                return false;
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
        --this.count;
        while (maxDepth-- != 0) {
            this.nodePath[maxDepth] = null;
        }
        return true;
    }

    @Override
    public boolean contains(Object k) {
        return this.findKey(k) != null;
    }

    public K get(Object k) {
        Entry<Object> entry = this.findKey(k);
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
        return new SetIterator();
    }

    @Override
    public ObjectBidirectionalIterator<K> iterator(K from) {
        return new SetIterator(from);
    }

    @Override
    public Comparator<? super K> comparator() {
        return this.actualComparator;
    }

    @Override
    public ObjectSortedSet<K> headSet(K to) {
        return new Subset(null, true, to, false);
    }

    @Override
    public ObjectSortedSet<K> tailSet(K from) {
        return new Subset(from, false, null, true);
    }

    @Override
    public ObjectSortedSet<K> subSet(K from, K to) {
        return new Subset(from, false, to, false);
    }

    public Object clone() {
        ObjectRBTreeSet c;
        try {
            c = (ObjectRBTreeSet)super.clone();
        } catch (CloneNotSupportedException cantHappen) {
            throw new InternalError();
        }
        c.allocatePaths();
        if (this.count != 0) {
            Entry<K> rp = new Entry<K>();
            Entry rq = new Entry();
            Entry<K> p = rp;
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
        SetIterator i = new SetIterator();
        s.defaultWriteObject();
        while (n-- != 0) {
            s.writeObject(i.next());
        }
    }

    private Entry<K> readTree(ObjectInputStream s, int n, Entry<K> pred, Entry<K> succ) throws IOException, ClassNotFoundException {
        if (n == 1) {
            Entry<Object> top = new Entry<Object>(s.readObject());
            top.pred(pred);
            top.succ(succ);
            top.black(true);
            return top;
        }
        if (n == 2) {
            Entry<Object> top = new Entry<Object>(s.readObject());
            top.black(true);
            top.right(new Entry<Object>(s.readObject()));
            top.right.pred(top);
            top.pred(pred);
            top.right.succ(succ);
            return top;
        }
        int rightN = n / 2;
        int leftN = n - rightN - 1;
        Entry top = new Entry();
        top.left(this.readTree(s, leftN, pred, top));
        top.key = s.readObject();
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
            Entry<K> e = this.tree;
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

    private int checkTree(Entry<K> e, int d, int D) {
        return 0;
    }

    private final class Subset
    extends AbstractObjectSortedSet<K>
    implements Serializable,
    ObjectSortedSet<K> {
        private static final long serialVersionUID = -7046029254386353129L;
        K from;
        K to;
        boolean bottom;
        boolean top;

        public Subset(K from, boolean bottom, K to, boolean top) {
            if (!bottom && !top && ObjectRBTreeSet.this.compare(from, to) > 0) {
                throw new IllegalArgumentException("Start element (" + from + ") is larger than end element (" + to + ")");
            }
            this.from = from;
            this.bottom = bottom;
            this.to = to;
            this.top = top;
        }

        @Override
        public void clear() {
            SubsetIterator i = new SubsetIterator();
            while (i.hasNext()) {
                i.next();
                i.remove();
            }
        }

        final boolean in(K k) {
            return !(!this.bottom && ObjectRBTreeSet.this.compare(k, this.from) < 0 || !this.top && ObjectRBTreeSet.this.compare(k, this.to) >= 0);
        }

        @Override
        public boolean contains(Object k) {
            return this.in(k) && ObjectRBTreeSet.this.contains(k);
        }

        @Override
        public boolean add(K k) {
            if (!this.in(k)) {
                throw new IllegalArgumentException("Element (" + k + ") out of range [" + (this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")");
            }
            return ObjectRBTreeSet.this.add(k);
        }

        @Override
        public boolean rem(Object k) {
            if (!this.in(k)) {
                return false;
            }
            return ObjectRBTreeSet.this.rem(k);
        }

        @Override
        public int size() {
            SubsetIterator i = new SubsetIterator();
            int n = 0;
            while (i.hasNext()) {
                ++n;
                i.next();
            }
            return n;
        }

        @Override
        public boolean isEmpty() {
            return !new SubsetIterator().hasNext();
        }

        @Override
        public Comparator<? super K> comparator() {
            return ObjectRBTreeSet.this.actualComparator;
        }

        @Override
        public ObjectBidirectionalIterator<K> iterator() {
            return new SubsetIterator();
        }

        @Override
        public ObjectBidirectionalIterator<K> iterator(K from) {
            return new SubsetIterator(from);
        }

        @Override
        public ObjectSortedSet<K> headSet(K to) {
            if (this.top) {
                return new Subset(this.from, this.bottom, to, false);
            }
            return ObjectRBTreeSet.this.compare(to, this.to) < 0 ? new Subset(this.from, this.bottom, to, false) : this;
        }

        @Override
        public ObjectSortedSet<K> tailSet(K from) {
            if (this.bottom) {
                return new Subset(from, false, this.to, this.top);
            }
            return ObjectRBTreeSet.this.compare(from, this.from) > 0 ? new Subset(from, false, this.to, this.top) : this;
        }

        @Override
        public ObjectSortedSet<K> subSet(K from, K to) {
            if (this.top && this.bottom) {
                return new Subset(from, false, to, false);
            }
            if (!this.top) {
                Object k = to = ObjectRBTreeSet.this.compare(to, this.to) < 0 ? to : this.to;
            }
            if (!this.bottom) {
                Object k = from = ObjectRBTreeSet.this.compare(from, this.from) > 0 ? from : this.from;
            }
            if (!this.top && !this.bottom && from == this.from && to == this.to) {
                return this;
            }
            return new Subset(from, false, to, false);
        }

        public Entry<K> firstEntry() {
            Entry e;
            if (ObjectRBTreeSet.this.tree == null) {
                return null;
            }
            if (this.bottom) {
                e = ObjectRBTreeSet.this.firstEntry;
            } else {
                e = ObjectRBTreeSet.this.locateKey(this.from);
                if (ObjectRBTreeSet.this.compare(e.key, this.from) < 0) {
                    e = e.next();
                }
            }
            if (e == null || !this.top && ObjectRBTreeSet.this.compare(e.key, this.to) >= 0) {
                return null;
            }
            return e;
        }

        public Entry<K> lastEntry() {
            Entry e;
            if (ObjectRBTreeSet.this.tree == null) {
                return null;
            }
            if (this.top) {
                e = ObjectRBTreeSet.this.lastEntry;
            } else {
                e = ObjectRBTreeSet.this.locateKey(this.to);
                if (ObjectRBTreeSet.this.compare(e.key, this.to) >= 0) {
                    e = e.prev();
                }
            }
            if (e == null || !this.bottom && ObjectRBTreeSet.this.compare(e.key, this.from) < 0) {
                return null;
            }
            return e;
        }

        @Override
        public K first() {
            Entry e = this.firstEntry();
            if (e == null) {
                throw new NoSuchElementException();
            }
            return e.key;
        }

        @Override
        public K last() {
            Entry e = this.lastEntry();
            if (e == null) {
                throw new NoSuchElementException();
            }
            return e.key;
        }

        private final class SubsetIterator
        extends SetIterator {
            SubsetIterator() {
                this.next = Subset.this.firstEntry();
            }

            /*
             * Enabled aggressive block sorting
             */
            SubsetIterator(K k) {
                this();
                if (this.next == null) return;
                if (!subset.bottom && subset.ObjectRBTreeSet.this.compare(k, this.next.key) < 0) {
                    this.prev = null;
                    return;
                }
                if (!subset.top) {
                    this.prev = subset.lastEntry();
                    if (subset.ObjectRBTreeSet.this.compare(k, this.prev.key) >= 0) {
                        this.next = null;
                        return;
                    }
                }
                this.next = subset.ObjectRBTreeSet.this.locateKey(k);
                if (subset.ObjectRBTreeSet.this.compare(this.next.key, k) <= 0) {
                    this.prev = this.next;
                    this.next = this.next.next();
                    return;
                }
                this.prev = this.next.prev();
            }

            @Override
            void updatePrevious() {
                this.prev = this.prev.prev();
                if (!Subset.this.bottom && this.prev != null && ObjectRBTreeSet.this.compare(this.prev.key, Subset.this.from) < 0) {
                    this.prev = null;
                }
            }

            @Override
            void updateNext() {
                this.next = this.next.next();
                if (!Subset.this.top && this.next != null && ObjectRBTreeSet.this.compare(this.next.key, Subset.this.to) >= 0) {
                    this.next = null;
                }
            }
        }
    }

    private class SetIterator
    extends AbstractObjectListIterator<K> {
        Entry<K> prev;
        Entry<K> next;
        Entry<K> curr;
        int index = 0;

        SetIterator() {
            this.next = ObjectRBTreeSet.this.firstEntry;
        }

        SetIterator(K k) {
            this.next = ObjectRBTreeSet.this.locateKey(k);
            if (this.next != null) {
                if (ObjectRBTreeSet.this.compare(this.next.key, k) <= 0) {
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
            ObjectRBTreeSet.this.rem(this.curr.key);
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

        void pred(boolean pred) {
            this.info = pred ? (this.info |= 0x40000000) : (this.info &= 0xBFFFFFFF);
        }

        void succ(boolean succ) {
            this.info = succ ? (this.info |= Integer.MIN_VALUE) : (this.info &= Integer.MAX_VALUE);
        }

        void pred(Entry<K> pred) {
            this.info |= 0x40000000;
            this.left = pred;
        }

        void succ(Entry<K> succ) {
            this.info |= Integer.MIN_VALUE;
            this.right = succ;
        }

        void left(Entry<K> left) {
            this.info &= 0xBFFFFFFF;
            this.left = left;
        }

        void right(Entry<K> right) {
            this.info &= Integer.MAX_VALUE;
            this.right = right;
        }

        boolean black() {
            return (this.info & 1) != 0;
        }

        void black(boolean black) {
            this.info = black ? (this.info |= 1) : (this.info &= 0xFFFFFFFE);
        }

        Entry<K> next() {
            Entry<K> next = this.right;
            if ((this.info & Integer.MIN_VALUE) == 0) {
                while ((next.info & 0x40000000) == 0) {
                    next = next.left;
                }
            }
            return next;
        }

        Entry<K> prev() {
            Entry<K> prev = this.left;
            if ((this.info & 0x40000000) == 0) {
                while ((prev.info & Integer.MIN_VALUE) == 0) {
                    prev = prev.right;
                }
            }
            return prev;
        }

        public Entry<K> clone() {
            Entry c;
            try {
                c = (Entry)super.clone();
            } catch (CloneNotSupportedException cantHappen) {
                throw new InternalError();
            }
            c.key = this.key;
            c.info = this.info;
            return c;
        }

        public boolean equals(Object o) {
            if (!(o instanceof Entry)) {
                return false;
            }
            Entry e = (Entry)o;
            return this.key == null ? e.key == null : this.key.equals(e.key);
        }

        public int hashCode() {
            return this.key.hashCode();
        }

        public String toString() {
            return String.valueOf(this.key);
        }
    }
}

