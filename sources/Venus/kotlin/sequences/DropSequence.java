/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.sequences;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.sequences.DropTakeSequence;
import kotlin.sequences.Sequence;
import kotlin.sequences.SubSequence;
import kotlin.sequences.TakeSequence;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010(\n\u0002\b\u0002\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u001b\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J\u0016\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\u00022\u0006\u0010\t\u001a\u00020\u0006H\u0016J\u000f\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\u000bH\u0096\u0002J\u0016\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\u00022\u0006\u0010\t\u001a\u00020\u0006H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2={"Lkotlin/sequences/DropSequence;", "T", "Lkotlin/sequences/Sequence;", "Lkotlin/sequences/DropTakeSequence;", "sequence", "count", "", "(Lkotlin/sequences/Sequence;I)V", "drop", "n", "iterator", "", "take", "kotlin-stdlib"})
@SourceDebugExtension(value={"SMAP\nSequences.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Sequences.kt\nkotlin/sequences/DropSequence\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,680:1\n1#2:681\n*E\n"})
public final class DropSequence<T>
implements Sequence<T>,
DropTakeSequence<T> {
    @NotNull
    private final Sequence<T> sequence;
    private final int count;

    public DropSequence(@NotNull Sequence<? extends T> sequence, int n) {
        boolean bl;
        Intrinsics.checkNotNullParameter(sequence, "sequence");
        this.sequence = sequence;
        this.count = n;
        boolean bl2 = bl = this.count >= 0;
        if (!bl) {
            boolean bl3 = false;
            String string = "count must be non-negative, but was " + this.count + '.';
            throw new IllegalArgumentException(string.toString());
        }
    }

    @Override
    @NotNull
    public Sequence<T> drop(int n) {
        int n2;
        int n3 = n2 = this.count + n;
        boolean bl = false;
        return n3 < 0 ? new DropSequence<T>(this, n) : new DropSequence<T>(this.sequence, n3);
    }

    @Override
    @NotNull
    public Sequence<T> take(int n) {
        int n2;
        int n3 = n2 = this.count + n;
        boolean bl = false;
        return n3 < 0 ? (DropTakeSequence)new TakeSequence(this, n) : (DropTakeSequence)new SubSequence<T>(this.sequence, this.count, n3);
    }

    @Override
    @NotNull
    public Iterator<T> iterator() {
        return new Iterator<T>(this){
            @NotNull
            private final Iterator<T> iterator;
            private int left;
            {
                this.iterator = DropSequence.access$getSequence$p(dropSequence).iterator();
                this.left = DropSequence.access$getCount$p(dropSequence);
            }

            @NotNull
            public final Iterator<T> getIterator() {
                return this.iterator;
            }

            public final int getLeft() {
                return this.left;
            }

            public final void setLeft(int n) {
                this.left = n;
            }

            private final void drop() {
                while (this.left > 0 && this.iterator.hasNext()) {
                    this.iterator.next();
                    int n = this.left;
                    this.left = n + -1;
                }
            }

            public T next() {
                this.drop();
                return this.iterator.next();
            }

            public boolean hasNext() {
                this.drop();
                return this.iterator.hasNext();
            }

            public void remove() {
                throw new UnsupportedOperationException("Operation is not supported for read-only collection");
            }
        };
    }

    public static final Sequence access$getSequence$p(DropSequence dropSequence) {
        return dropSequence.sequence;
    }

    public static final int access$getCount$p(DropSequence dropSequence) {
        return dropSequence.count;
    }
}

