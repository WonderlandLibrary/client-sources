/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.collections;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.collections.Grouping;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.jvm.internal.TypeIntrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=5, xi=49, d1={"\u0000&\n\u0000\n\u0002\u0010$\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010&\n\u0000\u001a0\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00030\u0001\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u0005H\u0007\u001aZ\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\b0\u0007\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\t\"\u0004\b\u0002\u0010\b*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\t0\u00072\u001e\u0010\n\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\t0\f\u0012\u0004\u0012\u0002H\b0\u000bH\u0081\b\u00f8\u0001\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001\u00a8\u0006\r"}, d2={"eachCount", "", "K", "", "T", "Lkotlin/collections/Grouping;", "mapValuesInPlace", "", "R", "V", "f", "Lkotlin/Function1;", "", "kotlin-stdlib"}, xs="kotlin/collections/GroupingKt")
@SourceDebugExtension(value={"SMAP\nGroupingJVM.kt\nKotlin\n*S Kotlin\n*F\n+ 1 GroupingJVM.kt\nkotlin/collections/GroupingKt__GroupingJVMKt\n+ 2 Grouping.kt\nkotlin/collections/GroupingKt__GroupingKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,52:1\n143#2:53\n80#2,4:54\n85#2:59\n1#3:58\n1855#4,2:60\n*S KotlinDebug\n*F\n+ 1 GroupingJVM.kt\nkotlin/collections/GroupingKt__GroupingJVMKt\n*L\n22#1:53\n22#1:54,4\n22#1:59\n48#1:60,2\n*E\n"})
class GroupingKt__GroupingJVMKt {
    @SinceKotlin(version="1.1")
    @NotNull
    public static final <T, K> Map<K, Integer> eachCount(@NotNull Grouping<T, ? extends K> grouping) {
        Map.Entry entry;
        Intrinsics.checkNotNullParameter(grouping, "<this>");
        Object object = grouping;
        Object object2 = new LinkedHashMap();
        boolean bl = false;
        Object object3 = object;
        boolean bl2 = false;
        Iterator<T> iterator2 = object3.sourceIterator();
        while (iterator2.hasNext()) {
            Ref.IntRef intRef;
            Object object4;
            T t = iterator2.next();
            K k = object3.keyOf(t);
            Object v = object2.get(k);
            boolean bl3 = v == null && !object2.containsKey(k);
            T t2 = t;
            Object v2 = v;
            K k2 = k;
            K k3 = k;
            Object object5 = object2;
            boolean bl4 = false;
            Object object6 = k2;
            if (bl3) {
                entry = object6;
                boolean bl5 = false;
                object4 = new Ref.IntRef();
                object6 = entry;
            } else {
                object4 = v2;
            }
            Ref.IntRef intRef2 = (Ref.IntRef)object4;
            boolean bl6 = false;
            Ref.IntRef intRef3 = intRef = intRef2;
            boolean bl7 = false;
            ++intRef3.element;
            Ref.IntRef intRef4 = intRef;
            object5.put(k3, intRef4);
        }
        object = object2;
        object2 = object.entrySet();
        Iterator iterator3 = object2.iterator();
        while (iterator3.hasNext()) {
            object3 = (Map.Entry)iterator3.next();
            Intrinsics.checkNotNull(object3, "null cannot be cast to non-null type kotlin.collections.MutableMap.MutableEntry<K of kotlin.collections.GroupingKt__GroupingJVMKt.mapValuesInPlace$lambda$4, R of kotlin.collections.GroupingKt__GroupingJVMKt.mapValuesInPlace$lambda$4>");
            Object object7 = object3;
            entry = TypeIntrinsics.asMutableMapEntry(object3);
            boolean bl8 = false;
            entry.setValue(((Ref.IntRef)object7.getValue()).element);
        }
        return TypeIntrinsics.asMutableMap(object);
    }

    @PublishedApi
    @InlineOnly
    private static final <K, V, R> Map<K, R> mapValuesInPlace(Map<K, V> map, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> function1) {
        Intrinsics.checkNotNullParameter(map, "<this>");
        Intrinsics.checkNotNullParameter(function1, "f");
        Iterable iterable = map.entrySet();
        boolean bl = false;
        for (Object t : iterable) {
            Map.Entry entry = (Map.Entry)t;
            boolean bl2 = false;
            Intrinsics.checkNotNull(entry, "null cannot be cast to non-null type kotlin.collections.MutableMap.MutableEntry<K of kotlin.collections.GroupingKt__GroupingJVMKt.mapValuesInPlace$lambda$4, R of kotlin.collections.GroupingKt__GroupingJVMKt.mapValuesInPlace$lambda$4>");
            TypeIntrinsics.asMutableMapEntry(entry).setValue(function1.invoke(entry));
        }
        return TypeIntrinsics.asMutableMap(map);
    }
}

