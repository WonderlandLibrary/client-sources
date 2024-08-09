/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.reflect;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KType;
import kotlin.reflect.KVariance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0087\b\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\u0019\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0006J\u000b\u0010\u000b\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J!\u0010\r\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u00c6\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0011\u001a\u00020\u0012H\u00d6\u0001J\b\u0010\u0013\u001a\u00020\u0014H\u0016R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0016"}, d2={"Lkotlin/reflect/KTypeProjection;", "", "variance", "Lkotlin/reflect/KVariance;", "type", "Lkotlin/reflect/KType;", "(Lkotlin/reflect/KVariance;Lkotlin/reflect/KType;)V", "getType", "()Lkotlin/reflect/KType;", "getVariance", "()Lkotlin/reflect/KVariance;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "", "Companion", "kotlin-stdlib"})
@SinceKotlin(version="1.1")
public final class KTypeProjection {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @Nullable
    private final KVariance variance;
    @Nullable
    private final KType type;
    @JvmField
    @NotNull
    public static final KTypeProjection star = new KTypeProjection(null, null);

    public KTypeProjection(@Nullable KVariance kVariance, @Nullable KType kType) {
        boolean bl;
        this.variance = kVariance;
        this.type = kType;
        boolean bl2 = bl = this.variance == null == (this.type == null);
        if (!bl) {
            boolean bl3 = false;
            String string = this.variance == null ? "Star projection must have no type specified." : "The projection variance " + (Object)((Object)this.variance) + " requires type to be specified.";
            throw new IllegalArgumentException(string.toString());
        }
    }

    @Nullable
    public final KVariance getVariance() {
        return this.variance;
    }

    @Nullable
    public final KType getType() {
        return this.type;
    }

    @NotNull
    public String toString() {
        String string;
        KVariance kVariance = this.variance;
        switch (kVariance == null ? -1 : WhenMappings.$EnumSwitchMapping$0[kVariance.ordinal()]) {
            case -1: {
                string = "*";
                break;
            }
            case 1: {
                string = String.valueOf(this.type);
                break;
            }
            case 2: {
                string = "in " + this.type;
                break;
            }
            case 3: {
                string = "out " + this.type;
                break;
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
        return string;
    }

    @Nullable
    public final KVariance component1() {
        return this.variance;
    }

    @Nullable
    public final KType component2() {
        return this.type;
    }

    @NotNull
    public final KTypeProjection copy(@Nullable KVariance kVariance, @Nullable KType kType) {
        return new KTypeProjection(kVariance, kType);
    }

    public static KTypeProjection copy$default(KTypeProjection kTypeProjection, KVariance kVariance, KType kType, int n, Object object) {
        if ((n & 1) != 0) {
            kVariance = kTypeProjection.variance;
        }
        if ((n & 2) != 0) {
            kType = kTypeProjection.type;
        }
        return kTypeProjection.copy(kVariance, kType);
    }

    public int hashCode() {
        int n = this.variance == null ? 0 : this.variance.hashCode();
        n = n * 31 + (this.type == null ? 0 : this.type.hashCode());
        return n;
    }

    public boolean equals(@Nullable Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof KTypeProjection)) {
            return true;
        }
        KTypeProjection kTypeProjection = (KTypeProjection)object;
        if (this.variance != kTypeProjection.variance) {
            return true;
        }
        return !Intrinsics.areEqual(this.type, kTypeProjection.type);
    }

    @JvmStatic
    @NotNull
    public static final KTypeProjection invariant(@NotNull KType kType) {
        return Companion.invariant(kType);
    }

    @JvmStatic
    @NotNull
    public static final KTypeProjection contravariant(@NotNull KType kType) {
        return Companion.contravariant(kType);
    }

    @JvmStatic
    @NotNull
    public static final KTypeProjection covariant(@NotNull KType kType) {
        return Companion.covariant(kType);
    }

    @Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000bH\u0007J\u0010\u0010\f\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000bH\u0007J\u0010\u0010\r\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000bH\u0007R\u0011\u0010\u0003\u001a\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0016\u0010\u0007\u001a\u00020\u00048\u0000X\u0081\u0004\u00a2\u0006\b\n\u0000\u0012\u0004\b\b\u0010\u0002\u00a8\u0006\u000e"}, d2={"Lkotlin/reflect/KTypeProjection$Companion;", "", "()V", "STAR", "Lkotlin/reflect/KTypeProjection;", "getSTAR", "()Lkotlin/reflect/KTypeProjection;", "star", "getStar$annotations", "contravariant", "type", "Lkotlin/reflect/KType;", "covariant", "invariant", "kotlin-stdlib"})
    public static final class Companion {
        private Companion() {
        }

        @PublishedApi
        public static void getStar$annotations() {
        }

        @NotNull
        public final KTypeProjection getSTAR() {
            return star;
        }

        @JvmStatic
        @NotNull
        public final KTypeProjection invariant(@NotNull KType kType) {
            Intrinsics.checkNotNullParameter(kType, "type");
            return new KTypeProjection(KVariance.INVARIANT, kType);
        }

        @JvmStatic
        @NotNull
        public final KTypeProjection contravariant(@NotNull KType kType) {
            Intrinsics.checkNotNullParameter(kType, "type");
            return new KTypeProjection(KVariance.IN, kType);
        }

        @JvmStatic
        @NotNull
        public final KTypeProjection covariant(@NotNull KType kType) {
            Intrinsics.checkNotNullParameter(kType, "type");
            return new KTypeProjection(KVariance.OUT, kType);
        }

        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    @Metadata(mv={1, 9, 0}, k=3, xi=48)
    public final class WhenMappings {
        public static final int[] $EnumSwitchMapping$0;

        static {
            int[] nArray = new int[KVariance.values().length];
            try {
                nArray[KVariance.INVARIANT.ordinal()] = 1;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[KVariance.IN.ordinal()] = 2;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[KVariance.OUT.ordinal()] = 3;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            $EnumSwitchMapping$0 = nArray;
        }
    }
}

