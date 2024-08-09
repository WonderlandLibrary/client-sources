/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.collections.AbstractCollection;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\b\n\u0002\u0010(\n\u0002\b\u0002\n\u0002\u0010*\n\u0002\b\b\b'\u0018\u0000 \u001c*\u0006\b\u0000\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003:\u0004\u001c\u001d\u001e\u001fB\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0004J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0096\u0002J\u0016\u0010\r\u001a\u00028\u00002\u0006\u0010\u000e\u001a\u00020\u0006H\u00a6\u0002\u00a2\u0006\u0002\u0010\u000fJ\b\u0010\u0010\u001a\u00020\u0006H\u0016J\u0015\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00028\u0000H\u0016\u00a2\u0006\u0002\u0010\u0013J\u000f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00028\u00000\u0015H\u0096\u0002J\u0015\u0010\u0016\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00028\u0000H\u0016\u00a2\u0006\u0002\u0010\u0013J\u000e\u0010\u0017\u001a\b\u0012\u0004\u0012\u00028\u00000\u0018H\u0016J\u0016\u0010\u0017\u001a\b\u0012\u0004\u0012\u00028\u00000\u00182\u0006\u0010\u000e\u001a\u00020\u0006H\u0016J\u001e\u0010\u0019\u001a\b\u0012\u0004\u0012\u00028\u00000\u00032\u0006\u0010\u001a\u001a\u00020\u00062\u0006\u0010\u001b\u001a\u00020\u0006H\u0016R\u0012\u0010\u0005\u001a\u00020\u0006X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\b\u00a8\u0006 "}, d2={"Lkotlin/collections/AbstractList;", "E", "Lkotlin/collections/AbstractCollection;", "", "()V", "size", "", "getSize", "()I", "equals", "", "other", "", "get", "index", "(I)Ljava/lang/Object;", "hashCode", "indexOf", "element", "(Ljava/lang/Object;)I", "iterator", "", "lastIndexOf", "listIterator", "", "subList", "fromIndex", "toIndex", "Companion", "IteratorImpl", "ListIteratorImpl", "SubList", "kotlin-stdlib"})
@SinceKotlin(version="1.1")
@SourceDebugExtension(value={"SMAP\nAbstractList.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AbstractList.kt\nkotlin/collections/AbstractList\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,157:1\n350#2,7:158\n378#2,7:165\n*S KotlinDebug\n*F\n+ 1 AbstractList.kt\nkotlin/collections/AbstractList\n*L\n27#1:158,7\n29#1:165,7\n*E\n"})
public abstract class AbstractList<E>
extends AbstractCollection<E>
implements List<E>,
KMappedMarker {
    @NotNull
    public static final Companion Companion = new Companion(null);

    protected AbstractList() {
    }

    @Override
    public abstract int getSize();

    @Override
    public abstract E get(int var1);

    @Override
    @NotNull
    public Iterator<E> iterator() {
        return new IteratorImpl(this);
    }

    @Override
    public int indexOf(E e) {
        int n;
        block2: {
            List list = this;
            boolean bl = false;
            int n2 = 0;
            Iterator iterator2 = list.iterator();
            while (iterator2.hasNext()) {
                Object e2;
                Object e3 = e2 = iterator2.next();
                boolean bl2 = false;
                if (Intrinsics.areEqual(e3, e)) {
                    n = n2;
                    break block2;
                }
                ++n2;
            }
            n = -1;
        }
        return n;
    }

    @Override
    public int lastIndexOf(E e) {
        int n;
        block1: {
            List list = this;
            boolean bl = false;
            ListIterator listIterator2 = list.listIterator(list.size());
            while (listIterator2.hasPrevious()) {
                Object e2 = listIterator2.previous();
                boolean bl2 = false;
                if (!Intrinsics.areEqual(e2, e)) continue;
                n = listIterator2.nextIndex();
                break block1;
            }
            n = -1;
        }
        return n;
    }

    @Override
    @NotNull
    public ListIterator<E> listIterator() {
        return new ListIteratorImpl(this, 0);
    }

    @Override
    @NotNull
    public ListIterator<E> listIterator(int n) {
        return new ListIteratorImpl(this, n);
    }

    @Override
    @NotNull
    public List<E> subList(int n, int n2) {
        return new SubList(this, n, n2);
    }

    @Override
    public boolean equals(@Nullable Object object) {
        if (object == this) {
            return false;
        }
        if (!(object instanceof List)) {
            return true;
        }
        return Companion.orderedEquals$kotlin_stdlib(this, (Collection)object);
    }

    @Override
    public int hashCode() {
        return Companion.orderedHashCode$kotlin_stdlib(this);
    }

    @Override
    public void add(int n, E e) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override
    public boolean addAll(int n, Collection<? extends E> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override
    public E remove(int n) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override
    public E set(int n, E e) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\r\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0005\b\u0080\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J%\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006H\u0000\u00a2\u0006\u0002\b\tJ\u001d\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006H\u0000\u00a2\u0006\u0002\b\fJ\u001d\u0010\r\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006H\u0000\u00a2\u0006\u0002\b\u000eJ%\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006H\u0000\u00a2\u0006\u0002\b\u0012J%\u0010\u0013\u001a\u00020\u00142\n\u0010\u0015\u001a\u0006\u0012\u0002\b\u00030\u00162\n\u0010\u0017\u001a\u0006\u0012\u0002\b\u00030\u0016H\u0000\u00a2\u0006\u0002\b\u0018J\u0019\u0010\u0019\u001a\u00020\u00062\n\u0010\u0015\u001a\u0006\u0012\u0002\b\u00030\u0016H\u0000\u00a2\u0006\u0002\b\u001a\u00a8\u0006\u001b"}, d2={"Lkotlin/collections/AbstractList$Companion;", "", "()V", "checkBoundsIndexes", "", "startIndex", "", "endIndex", "size", "checkBoundsIndexes$kotlin_stdlib", "checkElementIndex", "index", "checkElementIndex$kotlin_stdlib", "checkPositionIndex", "checkPositionIndex$kotlin_stdlib", "checkRangeIndexes", "fromIndex", "toIndex", "checkRangeIndexes$kotlin_stdlib", "orderedEquals", "", "c", "", "other", "orderedEquals$kotlin_stdlib", "orderedHashCode", "orderedHashCode$kotlin_stdlib", "kotlin-stdlib"})
    public static final class Companion {
        private Companion() {
        }

        public final void checkElementIndex$kotlin_stdlib(int n, int n2) {
            if (n < 0 || n >= n2) {
                throw new IndexOutOfBoundsException("index: " + n + ", size: " + n2);
            }
        }

        public final void checkPositionIndex$kotlin_stdlib(int n, int n2) {
            if (n < 0 || n > n2) {
                throw new IndexOutOfBoundsException("index: " + n + ", size: " + n2);
            }
        }

        public final void checkRangeIndexes$kotlin_stdlib(int n, int n2, int n3) {
            if (n < 0 || n2 > n3) {
                throw new IndexOutOfBoundsException("fromIndex: " + n + ", toIndex: " + n2 + ", size: " + n3);
            }
            if (n > n2) {
                throw new IllegalArgumentException("fromIndex: " + n + " > toIndex: " + n2);
            }
        }

        public final void checkBoundsIndexes$kotlin_stdlib(int n, int n2, int n3) {
            if (n < 0 || n2 > n3) {
                throw new IndexOutOfBoundsException("startIndex: " + n + ", endIndex: " + n2 + ", size: " + n3);
            }
            if (n > n2) {
                throw new IllegalArgumentException("startIndex: " + n + " > endIndex: " + n2);
            }
        }

        public final int orderedHashCode$kotlin_stdlib(@NotNull Collection<?> collection) {
            Intrinsics.checkNotNullParameter(collection, "c");
            int n = 1;
            Iterator<?> iterator2 = collection.iterator();
            while (iterator2.hasNext()) {
                Object obj;
                Object obj2 = obj = iterator2.next();
                n = 31 * n + (obj2 != null ? obj2.hashCode() : 0);
            }
            return n;
        }

        public final boolean orderedEquals$kotlin_stdlib(@NotNull Collection<?> collection, @NotNull Collection<?> collection2) {
            Intrinsics.checkNotNullParameter(collection, "c");
            Intrinsics.checkNotNullParameter(collection2, "other");
            if (collection.size() != collection2.size()) {
                return true;
            }
            Iterator<?> iterator2 = collection2.iterator();
            for (Object obj : collection) {
                Object obj2;
                if (Intrinsics.areEqual(obj, obj2 = iterator2.next())) continue;
                return true;
            }
            return false;
        }

        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    @Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010(\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0092\u0004\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\t\u0010\t\u001a\u00020\nH\u0096\u0002J\u000e\u0010\u000b\u001a\u00028\u0000H\u0096\u0002\u00a2\u0006\u0002\u0010\fR\u001a\u0010\u0003\u001a\u00020\u0004X\u0084\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\u00a8\u0006\r"}, d2={"Lkotlin/collections/AbstractList$IteratorImpl;", "", "(Lkotlin/collections/AbstractList;)V", "index", "", "getIndex", "()I", "setIndex", "(I)V", "hasNext", "", "next", "()Ljava/lang/Object;", "kotlin-stdlib"})
    private class IteratorImpl
    implements Iterator<E>,
    KMappedMarker {
        private int index;
        final AbstractList<E> this$0;

        public IteratorImpl(AbstractList abstractList) {
            this.this$0 = abstractList;
        }

        protected final int getIndex() {
            return this.index;
        }

        protected final void setIndex(int n) {
            this.index = n;
        }

        @Override
        public boolean hasNext() {
            return this.index < this.this$0.size();
        }

        @Override
        public E next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            int n = this.index;
            this.index = n + 1;
            return this.this$0.get(n);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }
    }

    @Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010*\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0092\u0004\u0018\u00002\f0\u0001R\b\u0012\u0004\u0012\u00028\u00000\u00022\b\u0012\u0004\u0012\u00028\u00000\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\u0005H\u0016J\r\u0010\n\u001a\u00028\u0000H\u0016\u00a2\u0006\u0002\u0010\u000bJ\b\u0010\f\u001a\u00020\u0005H\u0016\u00a8\u0006\r"}, d2={"Lkotlin/collections/AbstractList$ListIteratorImpl;", "Lkotlin/collections/AbstractList$IteratorImpl;", "Lkotlin/collections/AbstractList;", "", "index", "", "(Lkotlin/collections/AbstractList;I)V", "hasPrevious", "", "nextIndex", "previous", "()Ljava/lang/Object;", "previousIndex", "kotlin-stdlib"})
    private class ListIteratorImpl
    extends IteratorImpl
    implements ListIterator<E>,
    KMappedMarker {
        final AbstractList<E> this$0;

        public ListIteratorImpl(AbstractList abstractList, int n) {
            this.this$0 = abstractList;
            super(abstractList);
            Companion.checkPositionIndex$kotlin_stdlib(n, this.this$0.size());
            this.setIndex(n);
        }

        @Override
        public boolean hasPrevious() {
            return this.getIndex() > 0;
        }

        @Override
        public int nextIndex() {
            return this.getIndex();
        }

        @Override
        public E previous() {
            if (!this.hasPrevious()) {
                throw new NoSuchElementException();
            }
            this.setIndex(this.getIndex() + -1);
            return this.this$0.get(this.getIndex());
        }

        @Override
        public int previousIndex() {
            return this.getIndex() - 1;
        }

        @Override
        public void add(E e) {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        @Override
        public void set(E e) {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }
    }

    @Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\n\b\u0002\u0018\u0000*\u0006\b\u0001\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\u00060\u0003j\u0002`\u0004B#\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00010\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\tJ\u0016\u0010\u000e\u001a\u00028\u00012\u0006\u0010\u000f\u001a\u00020\u0007H\u0096\u0002\u00a2\u0006\u0002\u0010\u0010R\u000e\u0010\n\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00010\u0002X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\u00020\u00078VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\r\u00a8\u0006\u0011"}, d2={"Lkotlin/collections/AbstractList$SubList;", "E", "Lkotlin/collections/AbstractList;", "Ljava/util/RandomAccess;", "Lkotlin/collections/RandomAccess;", "list", "fromIndex", "", "toIndex", "(Lkotlin/collections/AbstractList;II)V", "_size", "size", "getSize", "()I", "get", "index", "(I)Ljava/lang/Object;", "kotlin-stdlib"})
    private static final class SubList<E>
    extends AbstractList<E>
    implements RandomAccess {
        @NotNull
        private final AbstractList<E> list;
        private final int fromIndex;
        private int _size;

        public SubList(@NotNull AbstractList<? extends E> abstractList, int n, int n2) {
            Intrinsics.checkNotNullParameter(abstractList, "list");
            this.list = abstractList;
            this.fromIndex = n;
            Companion.checkRangeIndexes$kotlin_stdlib(this.fromIndex, n2, this.list.size());
            this._size = n2 - this.fromIndex;
        }

        @Override
        public E get(int n) {
            Companion.checkElementIndex$kotlin_stdlib(n, this._size);
            return this.list.get(this.fromIndex + n);
        }

        @Override
        public int getSize() {
            return this._size;
        }
    }
}

