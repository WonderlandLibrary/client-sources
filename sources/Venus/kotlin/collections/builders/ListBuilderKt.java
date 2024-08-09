/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.collections.builders;

import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=2, xi=48, d1={"\u00002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a!\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0000\u00a2\u0006\u0002\u0010\u0005\u001a+\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0001\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\u00012\u0006\u0010\b\u001a\u00020\u0004H\u0000\u00a2\u0006\u0002\u0010\t\u001a%\u0010\n\u001a\u00020\u000b\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\f\u001a\u00020\u0004H\u0000\u00a2\u0006\u0002\u0010\r\u001a-\u0010\u000e\u001a\u00020\u000b\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0004H\u0000\u00a2\u0006\u0002\u0010\u0011\u001a9\u0010\u0012\u001a\u00020\u0013\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\u00012\u0006\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u00042\n\u0010\u0016\u001a\u0006\u0012\u0002\b\u00030\u0017H\u0002\u00a2\u0006\u0002\u0010\u0018\u001a-\u0010\u0019\u001a\u00020\u0004\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\u00012\u0006\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u0004H\u0002\u00a2\u0006\u0002\u0010\u001a\u001a/\u0010\u001b\u001a\u00020\u001c\"\u0004\b\u0000\u0010\u0007*\n\u0012\u0006\b\u0001\u0012\u0002H\u00070\u00012\u0006\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u0004H\u0002\u00a2\u0006\u0002\u0010\u001d\u00a8\u0006\u001e"}, d2={"arrayOfUninitializedElements", "", "E", "size", "", "(I)[Ljava/lang/Object;", "copyOfUninitializedElements", "T", "newSize", "([Ljava/lang/Object;I)[Ljava/lang/Object;", "resetAt", "", "index", "([Ljava/lang/Object;I)V", "resetRange", "fromIndex", "toIndex", "([Ljava/lang/Object;II)V", "subarrayContentEquals", "", "offset", "length", "other", "", "([Ljava/lang/Object;IILjava/util/List;)Z", "subarrayContentHashCode", "([Ljava/lang/Object;II)I", "subarrayContentToString", "", "([Ljava/lang/Object;II)Ljava/lang/String;", "kotlin-stdlib"})
@SourceDebugExtension(value={"SMAP\nListBuilder.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ListBuilder.kt\nkotlin/collections/builders/ListBuilderKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,432:1\n1#2:433\n*E\n"})
public final class ListBuilderKt {
    @NotNull
    public static final <E> E[] arrayOfUninitializedElements(int n) {
        boolean bl;
        boolean bl2 = bl = n >= 0;
        if (!bl) {
            boolean bl3 = false;
            String string = "capacity must be non-negative.";
            throw new IllegalArgumentException(string.toString());
        }
        return new Object[n];
    }

    private static final <T> String subarrayContentToString(T[] TArray, int n, int n2) {
        StringBuilder stringBuilder = new StringBuilder(2 + n2 * 3);
        stringBuilder.append("[");
        for (int i = 0; i < n2; ++i) {
            if (i > 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(TArray[n + i]);
        }
        stringBuilder.append("]");
        String string = stringBuilder.toString();
        Intrinsics.checkNotNullExpressionValue(string, "sb.toString()");
        return string;
    }

    private static final <T> int subarrayContentHashCode(T[] TArray, int n, int n2) {
        int n3 = 1;
        for (int i = 0; i < n2; ++i) {
            T t;
            T t2 = t = TArray[n + i];
            n3 = n3 * 31 + (t2 != null ? t2.hashCode() : 0);
        }
        return n3;
    }

    private static final <T> boolean subarrayContentEquals(T[] TArray, int n, int n2, List<?> list) {
        if (n2 != list.size()) {
            return true;
        }
        for (int i = 0; i < n2; ++i) {
            if (Intrinsics.areEqual(TArray[n + i], list.get(i))) continue;
            return true;
        }
        return false;
    }

    @NotNull
    public static final <T> T[] copyOfUninitializedElements(@NotNull T[] TArray, int n) {
        Intrinsics.checkNotNullParameter(TArray, "<this>");
        T[] TArray2 = Arrays.copyOf(TArray, n);
        Intrinsics.checkNotNullExpressionValue(TArray2, "copyOf(this, newSize)");
        return TArray2;
    }

    public static final <E> void resetAt(@NotNull E[] EArray, int n) {
        Intrinsics.checkNotNullParameter(EArray, "<this>");
        EArray[n] = null;
    }

    public static final <E> void resetRange(@NotNull E[] EArray, int n, int n2) {
        Intrinsics.checkNotNullParameter(EArray, "<this>");
        for (int i = n; i < n2; ++i) {
            ListBuilderKt.resetAt(EArray, i);
        }
    }

    public static final int access$subarrayContentHashCode(Object[] objectArray, int n, int n2) {
        return ListBuilderKt.subarrayContentHashCode(objectArray, n, n2);
    }

    public static final String access$subarrayContentToString(Object[] objectArray, int n, int n2) {
        return ListBuilderKt.subarrayContentToString(objectArray, n, n2);
    }

    public static final boolean access$subarrayContentEquals(Object[] objectArray, int n, int n2, List list) {
        return ListBuilderKt.subarrayContentEquals(objectArray, n, n2, list);
    }
}

