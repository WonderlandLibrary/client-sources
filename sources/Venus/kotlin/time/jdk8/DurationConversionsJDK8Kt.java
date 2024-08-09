/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.time.jdk8;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.WasExperimental;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlin.time.ExperimentalTime;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 9, 0}, k=2, xi=48, d1={"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\u001a\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0087\b\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0003\u0010\u0004\u001a\u0015\u0010\u0005\u001a\u00020\u0002*\u00020\u0001H\u0087\b\u00f8\u0001\u0001\u00a2\u0006\u0002\u0010\u0006\u0082\u0002\u000b\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b\u0019\u00a8\u0006\u0007"}, d2={"toJavaDuration", "Ljava/time/Duration;", "Lkotlin/time/Duration;", "toJavaDuration-LRDsOJo", "(J)Ljava/time/Duration;", "toKotlinDuration", "(Ljava/time/Duration;)J", "kotlin-stdlib-jdk8"}, pn="kotlin.time")
@JvmName(name="DurationConversionsJDK8Kt")
@SourceDebugExtension(value={"SMAP\nDurationConversions.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DurationConversions.kt\nkotlin/time/jdk8/DurationConversionsJDK8Kt\n+ 2 Duration.kt\nkotlin/time/Duration\n*L\n1#1,33:1\n731#2,2:34\n*S KotlinDebug\n*F\n+ 1 DurationConversions.kt\nkotlin/time/jdk8/DurationConversionsJDK8Kt\n*L\n33#1:34,2\n*E\n"})
public final class DurationConversionsJDK8Kt {
    @SinceKotlin(version="1.6")
    @WasExperimental(markerClass={ExperimentalTime.class})
    @InlineOnly
    private static final long toKotlinDuration(java.time.Duration duration) {
        Intrinsics.checkNotNullParameter(duration, "<this>");
        return Duration.plus-LRDsOJo(DurationKt.toDuration(duration.getSeconds(), DurationUnit.SECONDS), DurationKt.toDuration(duration.getNano(), DurationUnit.NANOSECONDS));
    }

    @SinceKotlin(version="1.6")
    @WasExperimental(markerClass={ExperimentalTime.class})
    @InlineOnly
    private static final java.time.Duration toJavaDuration-LRDsOJo(long l) {
        boolean bl = false;
        int n = Duration.getNanosecondsComponent-impl(l);
        long l2 = Duration.getInWholeSeconds-impl(l);
        boolean bl2 = false;
        java.time.Duration duration = java.time.Duration.ofSeconds(l2, n);
        Intrinsics.checkNotNullExpressionValue(duration, "toJavaDuration-LRDsOJo");
        return duration;
    }
}

