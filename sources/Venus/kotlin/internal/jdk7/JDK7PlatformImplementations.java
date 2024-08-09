/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.internal.jdk7;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.internal.PlatformImplementations;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0010\u0018\u00002\u00020\u0001:\u0001\u000eB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0016J\u0016\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00060\t2\u0006\u0010\u0007\u001a\u00020\u0006H\u0016J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002\u00a8\u0006\u000f"}, d2={"Lkotlin/internal/jdk7/JDK7PlatformImplementations;", "Lkotlin/internal/PlatformImplementations;", "()V", "addSuppressed", "", "cause", "", "exception", "getSuppressed", "", "sdkIsNullOrAtLeast", "", "version", "", "ReflectSdkVersion", "kotlin-stdlib-jdk7"})
public class JDK7PlatformImplementations
extends PlatformImplementations {
    private final boolean sdkIsNullOrAtLeast(int n) {
        return ReflectSdkVersion.sdkVersion == null || ReflectSdkVersion.sdkVersion >= n;
    }

    @Override
    public void addSuppressed(@NotNull Throwable throwable, @NotNull Throwable throwable2) {
        Intrinsics.checkNotNullParameter(throwable, "cause");
        Intrinsics.checkNotNullParameter(throwable2, "exception");
        if (this.sdkIsNullOrAtLeast(19)) {
            throwable.addSuppressed(throwable2);
        } else {
            super.addSuppressed(throwable, throwable2);
        }
    }

    @Override
    @NotNull
    public List<Throwable> getSuppressed(@NotNull Throwable throwable) {
        List<Object> list;
        Intrinsics.checkNotNullParameter(throwable, "exception");
        if (this.sdkIsNullOrAtLeast(19)) {
            Throwable[] throwableArray = throwable.getSuppressed();
            Intrinsics.checkNotNullExpressionValue(throwableArray, "exception.suppressed");
            list = ArraysKt.asList((Object[])throwableArray);
        } else {
            list = super.getSuppressed(throwable);
        }
        return list;
    }

    @Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u00c2\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006X\u0087\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0005\u00a8\u0006\u0006"}, d2={"Lkotlin/internal/jdk7/JDK7PlatformImplementations$ReflectSdkVersion;", "", "()V", "sdkVersion", "", "Ljava/lang/Integer;", "kotlin-stdlib-jdk7"})
    @SourceDebugExtension(value={"SMAP\nJDK7PlatformImplementations.kt\nKotlin\n*S Kotlin\n*F\n+ 1 JDK7PlatformImplementations.kt\nkotlin/internal/jdk7/JDK7PlatformImplementations$ReflectSdkVersion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,38:1\n1#2:39\n*E\n"})
    private static final class ReflectSdkVersion {
        @NotNull
        public static final ReflectSdkVersion INSTANCE;
        @JvmField
        @Nullable
        public static final Integer sdkVersion;

        private ReflectSdkVersion() {
        }

        static {
            Object object;
            Object object2;
            INSTANCE = new ReflectSdkVersion();
            try {
                object2 = Class.forName("android.os.Build$VERSION").getField("SDK_INT").get(null);
                object2 = object2 instanceof Integer ? (Integer)object2 : null;
            } catch (Throwable throwable) {
                object2 = null;
            }
            Object object3 = object2;
            if (object3 != null) {
                object2 = object3;
                int n = ((Number)object2).intValue();
                boolean bl = false;
                object = n > 0 ? object2 : null;
            } else {
                object = null;
            }
            sdkVersion = object;
        }
    }
}

