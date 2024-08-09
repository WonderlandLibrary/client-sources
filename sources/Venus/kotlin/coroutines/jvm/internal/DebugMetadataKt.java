/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.coroutines.jvm.internal;

import java.lang.reflect.Field;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.coroutines.jvm.internal.BaseContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.ModuleNameRetriever;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 9, 0}, k=2, xi=48, d1={"\u00000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0002\u001a\u000e\u0010\u0006\u001a\u0004\u0018\u00010\u0007*\u00020\bH\u0002\u001a\f\u0010\t\u001a\u00020\u0001*\u00020\bH\u0002\u001a\u0019\u0010\n\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000b*\u00020\bH\u0001\u00a2\u0006\u0002\u0010\r\u001a\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u000f*\u00020\bH\u0001\u00a2\u0006\u0002\b\u0010\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2={"COROUTINES_DEBUG_METADATA_VERSION", "", "checkDebugMetadataVersion", "", "expected", "actual", "getDebugMetadataAnnotation", "Lkotlin/coroutines/jvm/internal/DebugMetadata;", "Lkotlin/coroutines/jvm/internal/BaseContinuationImpl;", "getLabel", "getSpilledVariableFieldMapping", "", "", "(Lkotlin/coroutines/jvm/internal/BaseContinuationImpl;)[Ljava/lang/String;", "getStackTraceElementImpl", "Ljava/lang/StackTraceElement;", "getStackTraceElement", "kotlin-stdlib"})
@SourceDebugExtension(value={"SMAP\nDebugMetadata.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DebugMetadata.kt\nkotlin/coroutines/jvm/internal/DebugMetadataKt\n+ 2 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n*L\n1#1,134:1\n37#2,2:135\n*S KotlinDebug\n*F\n+ 1 DebugMetadata.kt\nkotlin/coroutines/jvm/internal/DebugMetadataKt\n*L\n131#1:135,2\n*E\n"})
public final class DebugMetadataKt {
    private static final int COROUTINES_DEBUG_METADATA_VERSION = 1;

    @SinceKotlin(version="1.3")
    @JvmName(name="getStackTraceElement")
    @Nullable
    public static final StackTraceElement getStackTraceElement(@NotNull BaseContinuationImpl baseContinuationImpl) {
        Intrinsics.checkNotNullParameter(baseContinuationImpl, "<this>");
        DebugMetadata debugMetadata = DebugMetadataKt.getDebugMetadataAnnotation(baseContinuationImpl);
        if (debugMetadata == null) {
            return null;
        }
        DebugMetadata debugMetadata2 = debugMetadata;
        DebugMetadataKt.checkDebugMetadataVersion(1, debugMetadata2.v());
        int n = DebugMetadataKt.getLabel(baseContinuationImpl);
        int n2 = n < 0 ? -1 : debugMetadata2.l()[n];
        String string = ModuleNameRetriever.INSTANCE.getModuleName(baseContinuationImpl);
        String string2 = string == null ? debugMetadata2.c() : string + '/' + debugMetadata2.c();
        return new StackTraceElement(string2, debugMetadata2.m(), debugMetadata2.f(), n2);
    }

    private static final DebugMetadata getDebugMetadataAnnotation(BaseContinuationImpl baseContinuationImpl) {
        return baseContinuationImpl.getClass().getAnnotation(DebugMetadata.class);
    }

    private static final int getLabel(BaseContinuationImpl baseContinuationImpl) {
        int n;
        try {
            Field field = baseContinuationImpl.getClass().getDeclaredField("label");
            field.setAccessible(false);
            Object object = field.get(baseContinuationImpl);
            Integer n2 = object instanceof Integer ? (Integer)object : null;
            n = (n2 != null ? n2 : 0) - 1;
        } catch (Exception exception) {
            n = -1;
        }
        return n;
    }

    private static final void checkDebugMetadataVersion(int n, int n2) {
        if (n2 > n) {
            throw new IllegalStateException(("Debug metadata version mismatch. Expected: " + n + ", got " + n2 + ". Please update the Kotlin standard library.").toString());
        }
    }

    @SinceKotlin(version="1.3")
    @JvmName(name="getSpilledVariableFieldMapping")
    @Nullable
    public static final String[] getSpilledVariableFieldMapping(@NotNull BaseContinuationImpl baseContinuationImpl) {
        int n;
        Intrinsics.checkNotNullParameter(baseContinuationImpl, "<this>");
        DebugMetadata debugMetadata = DebugMetadataKt.getDebugMetadataAnnotation(baseContinuationImpl);
        if (debugMetadata == null) {
            return null;
        }
        DebugMetadata debugMetadata2 = debugMetadata;
        DebugMetadataKt.checkDebugMetadataVersion(1, debugMetadata2.v());
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = DebugMetadataKt.getLabel(baseContinuationImpl);
        Object object = debugMetadata2.i();
        int n3 = ((int[])object).length;
        for (n = 0; n < n3; ++n) {
            int n4 = n;
            int n5 = object[n];
            if (n5 != n2) continue;
            arrayList.add(debugMetadata2.s()[n4]);
            arrayList.add(debugMetadata2.n()[n4]);
        }
        object = arrayList;
        n = 0;
        Object object2 = object;
        return object2.toArray(new String[0]);
    }
}

