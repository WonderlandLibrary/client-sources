/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.TuplesKt;
import kotlin.UByteArray;
import kotlin.UIntArray;
import kotlin.ULongArray;
import kotlin.UShortArray;
import kotlin.collections.ArraysKt;
import kotlin.collections.ArraysKt__ArraysJVMKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.unsigned.UArraysKt;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 9, 0}, k=5, xi=49, d1={"\u0000H\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a5\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\f\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0018\u00010\u00032\u0010\u0010\u0004\u001a\f\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0018\u00010\u0003H\u0001\u00a2\u0006\u0004\b\u0005\u0010\u0006\u001a#\u0010\u0007\u001a\u00020\b\"\u0004\b\u0000\u0010\u0002*\f\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0018\u00010\u0003H\u0001\u00a2\u0006\u0004\b\t\u0010\n\u001a?\u0010\u000b\u001a\u00020\f\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\n\u0010\r\u001a\u00060\u000ej\u0002`\u000f2\u0010\u0010\u0010\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u0011H\u0002\u00a2\u0006\u0004\b\u0012\u0010\u0013\u001a+\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0015\"\u0004\b\u0000\u0010\u0002*\u0012\u0012\u000e\b\u0001\u0012\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00030\u0003\u00a2\u0006\u0002\u0010\u0016\u001a;\u0010\u0017\u001a\u0002H\u0018\"\u0010\b\u0000\u0010\u0019*\u0006\u0012\u0002\b\u00030\u0003*\u0002H\u0018\"\u0004\b\u0001\u0010\u0018*\u0002H\u00192\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u00180\u001bH\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001c\u001a)\u0010\u001d\u001a\u00020\u0001*\b\u0012\u0002\b\u0003\u0018\u00010\u0003H\u0087\b\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0001\u001a\u0004\b\u0003\u0010\u0000\u00a2\u0006\u0002\u0010\u001e\u001aG\u0010\u001f\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0015\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00180\u00150 \"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0018*\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00180 0\u0003\u00a2\u0006\u0002\u0010!\u0082\u0002\u0007\n\u0005\b\u009920\u0001\u00a8\u0006\""}, d2={"contentDeepEqualsImpl", "", "T", "", "other", "contentDeepEquals", "([Ljava/lang/Object;[Ljava/lang/Object;)Z", "contentDeepToStringImpl", "", "contentDeepToString", "([Ljava/lang/Object;)Ljava/lang/String;", "contentDeepToStringInternal", "", "result", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "processed", "", "contentDeepToStringInternal$ArraysKt__ArraysKt", "([Ljava/lang/Object;Ljava/lang/StringBuilder;Ljava/util/List;)V", "flatten", "", "([[Ljava/lang/Object;)Ljava/util/List;", "ifEmpty", "R", "C", "defaultValue", "Lkotlin/Function0;", "([Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "isNullOrEmpty", "([Ljava/lang/Object;)Z", "unzip", "Lkotlin/Pair;", "([Lkotlin/Pair;)Lkotlin/Pair;", "kotlin-stdlib"}, xs="kotlin/collections/ArraysKt")
@SourceDebugExtension(value={"SMAP\nArrays.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Arrays.kt\nkotlin/collections/ArraysKt__ArraysKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,161:1\n1#2:162\n*E\n"})
class ArraysKt__ArraysKt
extends ArraysKt__ArraysJVMKt {
    @NotNull
    public static final <T> List<T> flatten(@NotNull T[][] TArray) {
        Intrinsics.checkNotNullParameter(TArray, "<this>");
        Object[] objectArray = (Object[])TArray;
        int n = 0;
        for (Object object : objectArray) {
            Object[] objectArray2 = (Object[])object;
            int n2 = n;
            boolean bl = false;
            int n3 = objectArray2.length;
            n = n2 + n3;
        }
        int n4 = n;
        ArrayList arrayList = new ArrayList(n4);
        n = ((Object[])TArray).length;
        for (int i = 0; i < n; ++i) {
            T[] TArray2 = TArray[i];
            CollectionsKt.addAll((Collection)arrayList, TArray2);
        }
        return arrayList;
    }

    @NotNull
    public static final <T, R> Pair<List<T>, List<R>> unzip(@NotNull Pair<? extends T, ? extends R>[] pairArray) {
        Intrinsics.checkNotNullParameter(pairArray, "<this>");
        ArrayList<T> arrayList = new ArrayList<T>(pairArray.length);
        ArrayList<R> arrayList2 = new ArrayList<R>(pairArray.length);
        for (Pair<T, R> pair : pairArray) {
            arrayList.add(pair.getFirst());
            arrayList2.add(pair.getSecond());
        }
        return TuplesKt.to(arrayList, arrayList2);
    }

    @SinceKotlin(version="1.3")
    @InlineOnly
    private static final boolean isNullOrEmpty(Object[] objectArray) {
        return objectArray == null || objectArray.length == 0;
    }

    @SinceKotlin(version="1.3")
    @InlineOnly
    private static final <C extends Object[], R> R ifEmpty(C c, Function0<? extends R> function0) {
        Intrinsics.checkNotNullParameter(function0, "defaultValue");
        return (R)(((C)c).length == 0 ? function0.invoke() : c);
    }

    @SinceKotlin(version="1.3")
    @PublishedApi
    @JvmName(name="contentDeepEquals")
    public static final <T> boolean contentDeepEquals(@Nullable T[] TArray, @Nullable T[] TArray2) {
        if (TArray == TArray2) {
            return false;
        }
        if (TArray == null || TArray2 == null || TArray.length != TArray2.length) {
            return true;
        }
        int n = TArray.length;
        for (int i = 0; i < n; ++i) {
            Object[] objectArray;
            Object[] objectArray2;
            T t = TArray[i];
            T t2 = TArray2[i];
            if (t == t2) continue;
            if (t == null || t2 == null) {
                return true;
            }
            if (!(t instanceof Object[] && t2 instanceof Object[] ? !ArraysKt.contentDeepEquals(objectArray2 = (Object[])t, objectArray = (Object[])t2) : (t instanceof byte[] && t2 instanceof byte[] ? !Arrays.equals((byte[])t, (byte[])t2) : (t instanceof short[] && t2 instanceof short[] ? !Arrays.equals((short[])t, (short[])t2) : (t instanceof int[] && t2 instanceof int[] ? !Arrays.equals((int[])t, (int[])t2) : (t instanceof long[] && t2 instanceof long[] ? !Arrays.equals((long[])t, (long[])t2) : (t instanceof float[] && t2 instanceof float[] ? !Arrays.equals((float[])t, (float[])t2) : (t instanceof double[] && t2 instanceof double[] ? !Arrays.equals((double[])t, (double[])t2) : (t instanceof char[] && t2 instanceof char[] ? !Arrays.equals((char[])t, (char[])t2) : (t instanceof boolean[] && t2 instanceof boolean[] ? !Arrays.equals((boolean[])t, (boolean[])t2) : (t instanceof UByteArray && t2 instanceof UByteArray ? !UArraysKt.contentEquals-kV0jMPg(((UByteArray)t).unbox-impl(), ((UByteArray)t2).unbox-impl()) : (t instanceof UShortArray && t2 instanceof UShortArray ? !UArraysKt.contentEquals-FGO6Aew(((UShortArray)t).unbox-impl(), ((UShortArray)t2).unbox-impl()) : (t instanceof UIntArray && t2 instanceof UIntArray ? !UArraysKt.contentEquals-KJPZfPQ(((UIntArray)t).unbox-impl(), ((UIntArray)t2).unbox-impl()) : (t instanceof ULongArray && t2 instanceof ULongArray ? !UArraysKt.contentEquals-lec5QzE(((ULongArray)t).unbox-impl(), ((ULongArray)t2).unbox-impl()) : !Intrinsics.areEqual(t, t2))))))))))))))) continue;
            return true;
        }
        return false;
    }

    @SinceKotlin(version="1.3")
    @PublishedApi
    @JvmName(name="contentDeepToString")
    @NotNull
    public static final <T> String contentDeepToString(@Nullable T[] TArray) {
        StringBuilder stringBuilder;
        if (TArray == null) {
            return "null";
        }
        int n = RangesKt.coerceAtMost(TArray.length, 0x19999999) * 5 + 2;
        StringBuilder stringBuilder2 = stringBuilder = new StringBuilder(n);
        boolean bl = false;
        ArraysKt__ArraysKt.contentDeepToStringInternal$ArraysKt__ArraysKt(TArray, stringBuilder2, new ArrayList());
        String string = stringBuilder.toString();
        Intrinsics.checkNotNullExpressionValue(string, "StringBuilder(capacity).\u2026builderAction).toString()");
        return string;
    }

    private static final <T> void contentDeepToStringInternal$ArraysKt__ArraysKt(T[] TArray, StringBuilder stringBuilder, List<Object[]> list) {
        if (list.contains(TArray)) {
            stringBuilder.append("[...]");
            return;
        }
        list.add(TArray);
        stringBuilder.append('[');
        int n = TArray.length;
        for (int i = 0; i < n; ++i) {
            T t;
            T t2;
            if (i != 0) {
                stringBuilder.append(", ");
            }
            if ((t2 = (t = TArray[i])) == null) {
                stringBuilder.append("null");
                continue;
            }
            if (t2 instanceof Object[]) {
                ArraysKt__ArraysKt.contentDeepToStringInternal$ArraysKt__ArraysKt((Object[])t, stringBuilder, list);
                continue;
            }
            if (t2 instanceof byte[]) {
                String string = Arrays.toString((byte[])t);
                Intrinsics.checkNotNullExpressionValue(string, "toString(this)");
                stringBuilder.append(string);
                continue;
            }
            if (t2 instanceof short[]) {
                String string = Arrays.toString((short[])t);
                Intrinsics.checkNotNullExpressionValue(string, "toString(this)");
                stringBuilder.append(string);
                continue;
            }
            if (t2 instanceof int[]) {
                String string = Arrays.toString((int[])t);
                Intrinsics.checkNotNullExpressionValue(string, "toString(this)");
                stringBuilder.append(string);
                continue;
            }
            if (t2 instanceof long[]) {
                String string = Arrays.toString((long[])t);
                Intrinsics.checkNotNullExpressionValue(string, "toString(this)");
                stringBuilder.append(string);
                continue;
            }
            if (t2 instanceof float[]) {
                String string = Arrays.toString((float[])t);
                Intrinsics.checkNotNullExpressionValue(string, "toString(this)");
                stringBuilder.append(string);
                continue;
            }
            if (t2 instanceof double[]) {
                String string = Arrays.toString((double[])t);
                Intrinsics.checkNotNullExpressionValue(string, "toString(this)");
                stringBuilder.append(string);
                continue;
            }
            if (t2 instanceof char[]) {
                String string = Arrays.toString((char[])t);
                Intrinsics.checkNotNullExpressionValue(string, "toString(this)");
                stringBuilder.append(string);
                continue;
            }
            if (t2 instanceof boolean[]) {
                String string = Arrays.toString((boolean[])t);
                Intrinsics.checkNotNullExpressionValue(string, "toString(this)");
                stringBuilder.append(string);
                continue;
            }
            if (t2 instanceof UByteArray) {
                UByteArray uByteArray = (UByteArray)t;
                stringBuilder.append(UArraysKt.contentToString-2csIQuQ((byte[])(uByteArray != null ? uByteArray.unbox-impl() : null)));
                continue;
            }
            if (t2 instanceof UShortArray) {
                UShortArray uShortArray = (UShortArray)t;
                stringBuilder.append(UArraysKt.contentToString-d-6D3K8((short[])(uShortArray != null ? uShortArray.unbox-impl() : null)));
                continue;
            }
            if (t2 instanceof UIntArray) {
                UIntArray uIntArray = (UIntArray)t;
                stringBuilder.append(UArraysKt.contentToString-XUkPCBk((int[])(uIntArray != null ? uIntArray.unbox-impl() : null)));
                continue;
            }
            if (t2 instanceof ULongArray) {
                ULongArray uLongArray = (ULongArray)t;
                stringBuilder.append(UArraysKt.contentToString-uLth9ew((long[])(uLongArray != null ? uLongArray.unbox-impl() : null)));
                continue;
            }
            stringBuilder.append(t.toString());
        }
        stringBuilder.append(']');
        list.remove(CollectionsKt.getLastIndex(list));
    }
}

