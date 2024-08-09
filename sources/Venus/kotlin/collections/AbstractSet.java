/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.collections.AbstractCollection;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b'\u0018\u0000 \u000b*\u0006\b\u0000\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003:\u0001\u000bB\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0004J\u0013\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0096\u0002J\b\u0010\t\u001a\u00020\nH\u0016\u00a8\u0006\f"}, d2={"Lkotlin/collections/AbstractSet;", "E", "Lkotlin/collections/AbstractCollection;", "", "()V", "equals", "", "other", "", "hashCode", "", "Companion", "kotlin-stdlib"})
@SinceKotlin(version="1.1")
public abstract class AbstractSet<E>
extends AbstractCollection<E>
implements Set<E>,
KMappedMarker {
    @NotNull
    public static final Companion Companion = new Companion(null);

    protected AbstractSet() {
    }

    @Override
    public boolean equals(@Nullable Object object) {
        if (object == this) {
            return false;
        }
        if (!(object instanceof Set)) {
            return true;
        }
        return Companion.setEquals$kotlin_stdlib(this, (Set)object);
    }

    @Override
    public int hashCode() {
        return Companion.unorderedHashCode$kotlin_stdlib(this);
    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\"\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\u0010\u001e\n\u0002\b\u0002\b\u0080\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J%\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\u0006H\u0000\u00a2\u0006\u0002\b\bJ\u0019\u0010\t\u001a\u00020\n2\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u000bH\u0000\u00a2\u0006\u0002\b\f\u00a8\u0006\r"}, d2={"Lkotlin/collections/AbstractSet$Companion;", "", "()V", "setEquals", "", "c", "", "other", "setEquals$kotlin_stdlib", "unorderedHashCode", "", "", "unorderedHashCode$kotlin_stdlib", "kotlin-stdlib"})
    public static final class Companion {
        private Companion() {
        }

        public final int unorderedHashCode$kotlin_stdlib(@NotNull Collection<?> collection) {
            Intrinsics.checkNotNullParameter(collection, "c");
            int n = 0;
            Iterator<?> iterator2 = collection.iterator();
            while (iterator2.hasNext()) {
                Object obj;
                Object obj2 = obj = iterator2.next();
                n += obj2 != null ? obj2.hashCode() : 0;
            }
            return n;
        }

        public final boolean setEquals$kotlin_stdlib(@NotNull Set<?> set, @NotNull Set<?> set2) {
            Intrinsics.checkNotNullParameter(set, "c");
            Intrinsics.checkNotNullParameter(set2, "other");
            if (set.size() != set2.size()) {
                return true;
            }
            return ((Collection)set).containsAll((Collection)set2);
        }

        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }
}

