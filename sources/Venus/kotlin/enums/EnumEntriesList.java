/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.enums;

import java.io.Serializable;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.collections.AbstractList;
import kotlin.collections.ArraysKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesSerializationProxy;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\u0000\n\u0000\b\u0003\u0018\u0000*\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\b\u0012\u0004\u0012\u0002H\u00010\u00042\u00060\u0005j\u0002`\u0006B\u0013\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\b\u00a2\u0006\u0002\u0010\tJ\u0016\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00028\u0000H\u0096\u0002\u00a2\u0006\u0002\u0010\u0012J\u0016\u0010\u0013\u001a\u00028\u00002\u0006\u0010\u0014\u001a\u00020\fH\u0096\u0002\u00a2\u0006\u0002\u0010\u0015J\u0015\u0010\u0016\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00028\u0000H\u0016\u00a2\u0006\u0002\u0010\u0017J\u0015\u0010\u0018\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00028\u0000H\u0016\u00a2\u0006\u0002\u0010\u0017J\b\u0010\u0019\u001a\u00020\u001aH\u0002R\u0016\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bX\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\nR\u0014\u0010\u000b\u001a\u00020\f8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u001b"}, d2={"Lkotlin/enums/EnumEntriesList;", "T", "", "Lkotlin/enums/EnumEntries;", "Lkotlin/collections/AbstractList;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "entries", "", "([Ljava/lang/Enum;)V", "[Ljava/lang/Enum;", "size", "", "getSize", "()I", "contains", "", "element", "(Ljava/lang/Enum;)Z", "get", "index", "(I)Ljava/lang/Enum;", "indexOf", "(Ljava/lang/Enum;)I", "lastIndexOf", "writeReplace", "", "kotlin-stdlib"})
@SinceKotlin(version="1.8")
final class EnumEntriesList<T extends Enum<T>>
extends AbstractList<T>
implements EnumEntries<T>,
Serializable {
    @NotNull
    private final T[] entries;

    public EnumEntriesList(@NotNull T[] TArray) {
        Intrinsics.checkNotNullParameter(TArray, "entries");
        this.entries = TArray;
    }

    @Override
    public int getSize() {
        return this.entries.length;
    }

    @Override
    @NotNull
    public T get(int n) {
        AbstractList.Companion.checkElementIndex$kotlin_stdlib(n, this.entries.length);
        return this.entries[n];
    }

    @Override
    public boolean contains(@NotNull T t) {
        Intrinsics.checkNotNullParameter(t, "element");
        Enum enum_ = (Enum)ArraysKt.getOrNull(this.entries, ((Enum)t).ordinal());
        return enum_ == t;
    }

    @Override
    public int indexOf(@NotNull T t) {
        Intrinsics.checkNotNullParameter(t, "element");
        int n = ((Enum)t).ordinal();
        Enum enum_ = (Enum)ArraysKt.getOrNull(this.entries, n);
        return enum_ == t ? n : -1;
    }

    @Override
    public int lastIndexOf(@NotNull T t) {
        Intrinsics.checkNotNullParameter(t, "element");
        return this.indexOf((Object)t);
    }

    private final Object writeReplace() {
        return new EnumEntriesSerializationProxy(this.entries);
    }

    @Override
    public Object get(int n) {
        return this.get(n);
    }

    @Override
    public final boolean contains(Object object) {
        if (!(object instanceof Enum)) {
            return true;
        }
        return this.contains((T)((Enum)object));
    }

    @Override
    public final int indexOf(Object object) {
        if (!(object instanceof Enum)) {
            return 1;
        }
        return this.indexOf((T)((Enum)object));
    }

    @Override
    public final int lastIndexOf(Object object) {
        if (!(object instanceof Enum)) {
            return 1;
        }
        return this.lastIndexOf((T)((Enum)object));
    }
}

