/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.collections;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import kotlin.Metadata;
import kotlin.collections.AbstractList;
import kotlin.collections.CollectionsKt__ReversedViewsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010(\n\u0000\n\u0002\u0010*\n\u0000\b\u0012\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\u00a2\u0006\u0002\u0010\u0005J\u0016\u0010\n\u001a\u00028\u00002\u0006\u0010\u000b\u001a\u00020\u0007H\u0096\u0002\u00a2\u0006\u0002\u0010\fJ\u000f\u0010\r\u001a\b\u0012\u0004\u0012\u00028\u00000\u000eH\u0096\u0002J\u000e\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u0010H\u0016J\u0016\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u00102\u0006\u0010\u000b\u001a\u00020\u0007H\u0016R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\t\u00a8\u0006\u0011"}, d2={"Lkotlin/collections/ReversedListReadOnly;", "T", "Lkotlin/collections/AbstractList;", "delegate", "", "(Ljava/util/List;)V", "size", "", "getSize", "()I", "get", "index", "(I)Ljava/lang/Object;", "iterator", "", "listIterator", "", "kotlin-stdlib"})
class ReversedListReadOnly<T>
extends AbstractList<T> {
    @NotNull
    private final List<T> delegate;

    public ReversedListReadOnly(@NotNull List<? extends T> list) {
        Intrinsics.checkNotNullParameter(list, "delegate");
        this.delegate = list;
    }

    @Override
    public int getSize() {
        return this.delegate.size();
    }

    @Override
    public T get(int n) {
        return this.delegate.get(CollectionsKt__ReversedViewsKt.access$reverseElementIndex(this, n));
    }

    @Override
    @NotNull
    public Iterator<T> iterator() {
        return this.listIterator(0);
    }

    @Override
    @NotNull
    public ListIterator<T> listIterator() {
        return this.listIterator(0);
    }

    @Override
    @NotNull
    public ListIterator<T> listIterator(int n) {
        return new ListIterator<T>(this, n){
            @NotNull
            private final ListIterator<T> delegateIterator;
            final ReversedListReadOnly<T> this$0;
            {
                this.this$0 = reversedListReadOnly;
                this.delegateIterator = ReversedListReadOnly.access$getDelegate$p(reversedListReadOnly).listIterator(CollectionsKt__ReversedViewsKt.access$reversePositionIndex(reversedListReadOnly, n));
            }

            @NotNull
            public final ListIterator<T> getDelegateIterator() {
                return this.delegateIterator;
            }

            public boolean hasNext() {
                return this.delegateIterator.hasPrevious();
            }

            public boolean hasPrevious() {
                return this.delegateIterator.hasNext();
            }

            public T next() {
                return this.delegateIterator.previous();
            }

            public int nextIndex() {
                return CollectionsKt__ReversedViewsKt.access$reverseIteratorIndex(this.this$0, this.delegateIterator.previousIndex());
            }

            public T previous() {
                return this.delegateIterator.next();
            }

            public int previousIndex() {
                return CollectionsKt__ReversedViewsKt.access$reverseIteratorIndex(this.this$0, this.delegateIterator.nextIndex());
            }

            public void add(T t) {
                throw new UnsupportedOperationException("Operation is not supported for read-only collection");
            }

            public void remove() {
                throw new UnsupportedOperationException("Operation is not supported for read-only collection");
            }

            public void set(T t) {
                throw new UnsupportedOperationException("Operation is not supported for read-only collection");
            }
        };
    }

    public static final List access$getDelegate$p(ReversedListReadOnly reversedListReadOnly) {
        return reversedListReadOnly.delegate;
    }
}

