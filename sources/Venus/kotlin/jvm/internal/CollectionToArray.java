/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.jvm.internal;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 9, 0}, k=2, xi=48, d1={"\u00002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a#\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00012\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\bH\u0007\u00a2\u0006\u0004\b\t\u0010\n\u001a5\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00012\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b2\u0010\u0010\u000b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0018\u00010\u0001H\u0007\u00a2\u0006\u0004\b\t\u0010\f\u001a~\u0010\r\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00012\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b2\u0014\u0010\u000e\u001a\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00010\u000f2\u001a\u0010\u0010\u001a\u0016\u0012\u0004\u0012\u00020\u0005\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00010\u00112(\u0010\u0012\u001a$\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001\u0012\u0004\u0012\u00020\u0005\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00010\u0013H\u0082\b\u00a2\u0006\u0002\u0010\u0014\"\u0018\u0010\u0000\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0003\"\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2={"EMPTY", "", "", "[Ljava/lang/Object;", "MAX_SIZE", "", "collectionToArray", "collection", "", "toArray", "(Ljava/util/Collection;)[Ljava/lang/Object;", "a", "(Ljava/util/Collection;[Ljava/lang/Object;)[Ljava/lang/Object;", "toArrayImpl", "empty", "Lkotlin/Function0;", "alloc", "Lkotlin/Function1;", "trim", "Lkotlin/Function2;", "(Ljava/util/Collection;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function2;)[Ljava/lang/Object;", "kotlin-stdlib"})
@JvmName(name="CollectionToArray")
@SourceDebugExtension(value={"SMAP\nCollectionToArray.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CollectionToArray.kt\nkotlin/jvm/internal/CollectionToArray\n+ 2 ArrayIntrinsics.kt\nkotlin/ArrayIntrinsicsKt\n*L\n1#1,82:1\n57#1,22:83\n57#1,22:105\n26#2:127\n*S KotlinDebug\n*F\n+ 1 CollectionToArray.kt\nkotlin/jvm/internal/CollectionToArray\n*L\n19#1:83,22\n31#1:105,22\n14#1:127\n*E\n"})
public final class CollectionToArray {
    @NotNull
    private static final Object[] EMPTY;
    private static final int MAX_SIZE = 0x7FFFFFFD;

    @JvmName(name="toArray")
    @NotNull
    public static final Object[] toArray(@NotNull Collection<?> collection) {
        Object[] objectArray;
        block9: {
            Intrinsics.checkNotNullParameter(collection, "collection");
            boolean bl = false;
            int n = collection.size();
            if (n == 0) {
                boolean bl2 = false;
                objectArray = EMPTY;
            } else {
                Iterator<?> iterator2 = collection.iterator();
                if (!iterator2.hasNext()) {
                    boolean bl3 = false;
                    objectArray = EMPTY;
                } else {
                    int n2 = n;
                    int n3 = 0;
                    Object[] objectArray2 = new Object[n2];
                    n3 = 0;
                    while (true) {
                        objectArray2[n3++] = iterator2.next();
                        if (n3 >= objectArray2.length) {
                            if (!iterator2.hasNext()) {
                                objectArray = objectArray2;
                                break block9;
                            }
                            int n4 = n3 * 3 + 1 >>> 1;
                            if (n4 <= n3) {
                                if (n3 >= 0x7FFFFFFD) {
                                    throw new OutOfMemoryError();
                                }
                                n4 = 0x7FFFFFFD;
                            }
                            Intrinsics.checkNotNullExpressionValue(Arrays.copyOf(objectArray2, n4), "copyOf(result, newSize)");
                            continue;
                        }
                        if (!iterator2.hasNext()) break;
                    }
                    int n5 = n3;
                    Object[] objectArray3 = objectArray2;
                    boolean bl4 = false;
                    Object[] objectArray4 = Arrays.copyOf(objectArray3, n5);
                    objectArray = objectArray4;
                    Intrinsics.checkNotNullExpressionValue(objectArray4, "copyOf(result, size)");
                }
            }
        }
        return objectArray;
    }

    @JvmName(name="toArray")
    @NotNull
    public static final Object[] toArray(@NotNull Collection<?> collection, @Nullable Object[] objectArray) {
        Object[] objectArray2;
        block16: {
            Intrinsics.checkNotNullParameter(collection, "collection");
            if (objectArray == null) {
                throw new NullPointerException();
            }
            boolean bl = false;
            int n = collection.size();
            if (n == 0) {
                boolean bl2 = false;
                if (objectArray.length > 0) {
                    objectArray[0] = null;
                }
                objectArray2 = objectArray;
            } else {
                Iterator<?> iterator2 = collection.iterator();
                if (!iterator2.hasNext()) {
                    boolean bl3 = false;
                    if (objectArray.length > 0) {
                        objectArray[0] = null;
                    }
                    objectArray2 = objectArray;
                } else {
                    Object[] objectArray3;
                    int n2 = n;
                    int n3 = 0;
                    if (n2 <= objectArray.length) {
                        objectArray3 = objectArray;
                    } else {
                        Object object = Array.newInstance(objectArray.getClass().getComponentType(), n2);
                        Intrinsics.checkNotNull(object, "null cannot be cast to non-null type kotlin.Array<kotlin.Any?>");
                        objectArray3 = (Object[])object;
                    }
                    Object[] objectArray4 = objectArray3;
                    n2 = 0;
                    while (true) {
                        objectArray4[n2++] = iterator2.next();
                        if (n2 >= objectArray4.length) {
                            if (!iterator2.hasNext()) {
                                objectArray2 = objectArray4;
                                break block16;
                            }
                            n3 = n2 * 3 + 1 >>> 1;
                            if (n3 <= n2) {
                                if (n2 >= 0x7FFFFFFD) {
                                    throw new OutOfMemoryError();
                                }
                                n3 = 0x7FFFFFFD;
                            }
                            Intrinsics.checkNotNullExpressionValue(Arrays.copyOf(objectArray4, n3), "copyOf(result, newSize)");
                            continue;
                        }
                        if (!iterator2.hasNext()) break;
                    }
                    int n4 = n2;
                    Object[] objectArray5 = objectArray4;
                    boolean bl4 = false;
                    if (objectArray5 == objectArray) {
                        objectArray[n4] = null;
                        objectArray2 = objectArray;
                    } else {
                        Object[] objectArray6 = Arrays.copyOf(objectArray5, n4);
                        objectArray2 = objectArray6;
                        Intrinsics.checkNotNullExpressionValue(objectArray6, "copyOf(result, size)");
                    }
                }
            }
        }
        return objectArray2;
    }

    private static final Object[] toArrayImpl(Collection<?> collection, Function0<Object[]> function0, Function1<? super Integer, Object[]> function1, Function2<? super Object[], ? super Integer, Object[]> function2) {
        boolean bl = false;
        int n = collection.size();
        if (n == 0) {
            return function0.invoke();
        }
        Iterator<?> iterator2 = collection.iterator();
        if (!iterator2.hasNext()) {
            return function0.invoke();
        }
        Object[] objectArray = function1.invoke((Integer)n);
        int n2 = 0;
        while (true) {
            objectArray[n2++] = iterator2.next();
            if (n2 >= objectArray.length) {
                if (!iterator2.hasNext()) {
                    return objectArray;
                }
                int n3 = n2 * 3 + 1 >>> 1;
                if (n3 <= n2) {
                    if (n2 >= 0x7FFFFFFD) {
                        throw new OutOfMemoryError();
                    }
                    n3 = 0x7FFFFFFD;
                }
                Intrinsics.checkNotNullExpressionValue(Arrays.copyOf(objectArray, n3), "copyOf(result, newSize)");
                continue;
            }
            if (!iterator2.hasNext()) break;
        }
        return function2.invoke((Object[])objectArray, (Integer)n2);
    }

    static {
        boolean bl = false;
        EMPTY = new Object[0];
    }
}

