/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.ranges;

import java.util.NoSuchElementException;
import kotlin.Deprecated;
import kotlin.DeprecatedSinceKotlin;
import kotlin.ExperimentalStdlibApi;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.WasExperimental;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.random.Random;
import kotlin.random.RandomKt;
import kotlin.ranges.CharProgression;
import kotlin.ranges.CharRange;
import kotlin.ranges.ClosedFloatingPointRange;
import kotlin.ranges.ClosedRange;
import kotlin.ranges.IntProgression;
import kotlin.ranges.IntRange;
import kotlin.ranges.LongProgression;
import kotlin.ranges.LongRange;
import kotlin.ranges.OpenEndRange;
import kotlin.ranges.RangesKt;
import kotlin.ranges.RangesKt__RangesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 9, 0}, k=5, xi=49, d1={"\u0000t\n\u0002\b\u0002\n\u0002\u0010\u000f\n\u0002\b\u0002\n\u0002\u0010\u0005\n\u0002\u0010\u0006\n\u0002\u0010\u0007\n\u0002\u0010\b\n\u0002\u0010\t\n\u0002\u0010\n\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\f\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u001d\u001a'\u0010\u0000\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002*\u0002H\u00012\u0006\u0010\u0003\u001a\u0002H\u0001\u00a2\u0006\u0002\u0010\u0004\u001a\u0012\u0010\u0000\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u0005\u001a\u0012\u0010\u0000\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u0006\u001a\u0012\u0010\u0000\u001a\u00020\u0007*\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u0007\u001a\u0012\u0010\u0000\u001a\u00020\b*\u00020\b2\u0006\u0010\u0003\u001a\u00020\b\u001a\u0012\u0010\u0000\u001a\u00020\t*\u00020\t2\u0006\u0010\u0003\u001a\u00020\t\u001a\u0012\u0010\u0000\u001a\u00020\n*\u00020\n2\u0006\u0010\u0003\u001a\u00020\n\u001a'\u0010\u000b\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002*\u0002H\u00012\u0006\u0010\f\u001a\u0002H\u0001\u00a2\u0006\u0002\u0010\u0004\u001a\u0012\u0010\u000b\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\f\u001a\u00020\u0005\u001a\u0012\u0010\u000b\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\f\u001a\u00020\u0006\u001a\u0012\u0010\u000b\u001a\u00020\u0007*\u00020\u00072\u0006\u0010\f\u001a\u00020\u0007\u001a\u0012\u0010\u000b\u001a\u00020\b*\u00020\b2\u0006\u0010\f\u001a\u00020\b\u001a\u0012\u0010\u000b\u001a\u00020\t*\u00020\t2\u0006\u0010\f\u001a\u00020\t\u001a\u0012\u0010\u000b\u001a\u00020\n*\u00020\n2\u0006\u0010\f\u001a\u00020\n\u001a3\u0010\r\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002*\u0002H\u00012\b\u0010\u0003\u001a\u0004\u0018\u0001H\u00012\b\u0010\f\u001a\u0004\u0018\u0001H\u0001\u00a2\u0006\u0002\u0010\u000e\u001a/\u0010\r\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002*\u0002H\u00012\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0010H\u0007\u00a2\u0006\u0002\u0010\u0011\u001a-\u0010\r\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002*\u0002H\u00012\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0012\u00a2\u0006\u0002\u0010\u0013\u001a\u001a\u0010\r\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u0005\u001a\u001a\u0010\r\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u0006\u001a\u001a\u0010\r\u001a\u00020\u0007*\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u0007\u001a\u001a\u0010\r\u001a\u00020\b*\u00020\b2\u0006\u0010\u0003\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\b\u001a\u0018\u0010\r\u001a\u00020\b*\u00020\b2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\b0\u0012\u001a\u001a\u0010\r\u001a\u00020\t*\u00020\t2\u0006\u0010\u0003\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\t\u001a\u0018\u0010\r\u001a\u00020\t*\u00020\t2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\t0\u0012\u001a\u001a\u0010\r\u001a\u00020\n*\u00020\n2\u0006\u0010\u0003\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\n\u001a\u001c\u0010\u0014\u001a\u00020\u0015*\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0087\n\u00a2\u0006\u0002\u0010\u0019\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010\u001a\u001a\u00020\u0006H\u0087\u0002\u00a2\u0006\u0002\b\u001b\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010\u001a\u001a\u00020\u0007H\u0087\u0002\u00a2\u0006\u0002\b\u001b\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010\u001a\u001a\u00020\bH\u0087\u0002\u00a2\u0006\u0002\b\u001b\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010\u001a\u001a\u00020\tH\u0087\u0002\u00a2\u0006\u0002\b\u001b\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010\u001a\u001a\u00020\nH\u0087\u0002\u00a2\u0006\u0002\b\u001b\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00060\u00122\u0006\u0010\u001a\u001a\u00020\u0005H\u0087\u0002\u00a2\u0006\u0002\b\u001c\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00060\u00122\u0006\u0010\u001a\u001a\u00020\u0007H\u0087\u0002\u00a2\u0006\u0002\b\u001c\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00060\u00122\u0006\u0010\u001a\u001a\u00020\bH\u0087\u0002\u00a2\u0006\u0002\b\u001c\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00060\u00122\u0006\u0010\u001a\u001a\u00020\tH\u0087\u0002\u00a2\u0006\u0002\b\u001c\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00060\u00122\u0006\u0010\u001a\u001a\u00020\nH\u0087\u0002\u00a2\u0006\u0002\b\u001c\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00070\u00122\u0006\u0010\u001a\u001a\u00020\u0005H\u0087\u0002\u00a2\u0006\u0002\b\u001d\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00070\u00122\u0006\u0010\u001a\u001a\u00020\u0006H\u0087\u0002\u00a2\u0006\u0002\b\u001d\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00070\u00122\u0006\u0010\u001a\u001a\u00020\bH\u0087\u0002\u00a2\u0006\u0002\b\u001d\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00070\u00122\u0006\u0010\u001a\u001a\u00020\tH\u0087\u0002\u00a2\u0006\u0002\b\u001d\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00070\u00122\u0006\u0010\u001a\u001a\u00020\nH\u0087\u0002\u00a2\u0006\u0002\b\u001d\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0\u00122\u0006\u0010\u001a\u001a\u00020\u0005H\u0087\u0002\u00a2\u0006\u0002\b\u001e\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0\u00122\u0006\u0010\u001a\u001a\u00020\u0006H\u0087\u0002\u00a2\u0006\u0002\b\u001e\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0\u00122\u0006\u0010\u001a\u001a\u00020\u0007H\u0087\u0002\u00a2\u0006\u0002\b\u001e\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0\u00122\u0006\u0010\u001a\u001a\u00020\tH\u0087\u0002\u00a2\u0006\u0002\b\u001e\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0\u00122\u0006\u0010\u001a\u001a\u00020\nH\u0087\u0002\u00a2\u0006\u0002\b\u001e\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\t0\u00122\u0006\u0010\u001a\u001a\u00020\u0005H\u0087\u0002\u00a2\u0006\u0002\b\u001f\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\t0\u00122\u0006\u0010\u001a\u001a\u00020\u0006H\u0087\u0002\u00a2\u0006\u0002\b\u001f\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\t0\u00122\u0006\u0010\u001a\u001a\u00020\u0007H\u0087\u0002\u00a2\u0006\u0002\b\u001f\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\t0\u00122\u0006\u0010\u001a\u001a\u00020\bH\u0087\u0002\u00a2\u0006\u0002\b\u001f\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\t0\u00122\u0006\u0010\u001a\u001a\u00020\nH\u0087\u0002\u00a2\u0006\u0002\b\u001f\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\n0\u00122\u0006\u0010\u001a\u001a\u00020\u0005H\u0087\u0002\u00a2\u0006\u0002\b \u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\n0\u00122\u0006\u0010\u001a\u001a\u00020\u0006H\u0087\u0002\u00a2\u0006\u0002\b \u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\n0\u00122\u0006\u0010\u001a\u001a\u00020\u0007H\u0087\u0002\u00a2\u0006\u0002\b \u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\n0\u00122\u0006\u0010\u001a\u001a\u00020\bH\u0087\u0002\u00a2\u0006\u0002\b \u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\n0\u00122\u0006\u0010\u001a\u001a\u00020\tH\u0087\u0002\u00a2\u0006\u0002\b \u001a\u0015\u0010\u0014\u001a\u00020\u0015*\u00020!2\u0006\u0010\u001a\u001a\u00020\u0005H\u0087\n\u001a\u001c\u0010\u0014\u001a\u00020\u0015*\u00020!2\b\u0010\u0017\u001a\u0004\u0018\u00010\bH\u0087\n\u00a2\u0006\u0002\u0010\"\u001a\u0015\u0010\u0014\u001a\u00020\u0015*\u00020!2\u0006\u0010\u001a\u001a\u00020\tH\u0087\n\u001a\u0015\u0010\u0014\u001a\u00020\u0015*\u00020!2\u0006\u0010\u001a\u001a\u00020\nH\u0087\n\u001a\u0015\u0010\u0014\u001a\u00020\u0015*\u00020#2\u0006\u0010\u001a\u001a\u00020\u0005H\u0087\n\u001a\u0015\u0010\u0014\u001a\u00020\u0015*\u00020#2\u0006\u0010\u001a\u001a\u00020\bH\u0087\n\u001a\u001c\u0010\u0014\u001a\u00020\u0015*\u00020#2\b\u0010\u0017\u001a\u0004\u0018\u00010\tH\u0087\n\u00a2\u0006\u0002\u0010$\u001a\u0015\u0010\u0014\u001a\u00020\u0015*\u00020#2\u0006\u0010\u001a\u001a\u00020\nH\u0087\n\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00050%2\u0006\u0010\u001a\u001a\u00020\bH\u0087\u0002\u00a2\u0006\u0002\b\u001b\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00050%2\u0006\u0010\u001a\u001a\u00020\tH\u0087\u0002\u00a2\u0006\u0002\b\u001b\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00050%2\u0006\u0010\u001a\u001a\u00020\nH\u0087\u0002\u00a2\u0006\u0002\b\u001b\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00060%2\u0006\u0010\u001a\u001a\u00020\u0007H\u0087\u0002\u00a2\u0006\u0002\b\u001c\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0%2\u0006\u0010\u001a\u001a\u00020\u0005H\u0087\u0002\u00a2\u0006\u0002\b\u001e\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0%2\u0006\u0010\u001a\u001a\u00020\tH\u0087\u0002\u00a2\u0006\u0002\b\u001e\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0%2\u0006\u0010\u001a\u001a\u00020\nH\u0087\u0002\u00a2\u0006\u0002\b\u001e\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\t0%2\u0006\u0010\u001a\u001a\u00020\u0005H\u0087\u0002\u00a2\u0006\u0002\b\u001f\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\t0%2\u0006\u0010\u001a\u001a\u00020\bH\u0087\u0002\u00a2\u0006\u0002\b\u001f\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\t0%2\u0006\u0010\u001a\u001a\u00020\nH\u0087\u0002\u00a2\u0006\u0002\b\u001f\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\n0%2\u0006\u0010\u001a\u001a\u00020\u0005H\u0087\u0002\u00a2\u0006\u0002\b \u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\n0%2\u0006\u0010\u001a\u001a\u00020\bH\u0087\u0002\u00a2\u0006\u0002\b \u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\n0%2\u0006\u0010\u001a\u001a\u00020\tH\u0087\u0002\u00a2\u0006\u0002\b \u001a\u0015\u0010&\u001a\u00020'*\u00020\u00052\u0006\u0010(\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010&\u001a\u00020'*\u00020\u00052\u0006\u0010(\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010&\u001a\u00020)*\u00020\u00052\u0006\u0010(\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010&\u001a\u00020'*\u00020\u00052\u0006\u0010(\u001a\u00020\nH\u0086\u0004\u001a\u0015\u0010&\u001a\u00020**\u00020\u00182\u0006\u0010(\u001a\u00020\u0018H\u0086\u0004\u001a\u0015\u0010&\u001a\u00020'*\u00020\b2\u0006\u0010(\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010&\u001a\u00020'*\u00020\b2\u0006\u0010(\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010&\u001a\u00020)*\u00020\b2\u0006\u0010(\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010&\u001a\u00020'*\u00020\b2\u0006\u0010(\u001a\u00020\nH\u0086\u0004\u001a\u0015\u0010&\u001a\u00020)*\u00020\t2\u0006\u0010(\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010&\u001a\u00020)*\u00020\t2\u0006\u0010(\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010&\u001a\u00020)*\u00020\t2\u0006\u0010(\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010&\u001a\u00020)*\u00020\t2\u0006\u0010(\u001a\u00020\nH\u0086\u0004\u001a\u0015\u0010&\u001a\u00020'*\u00020\n2\u0006\u0010(\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010&\u001a\u00020'*\u00020\n2\u0006\u0010(\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010&\u001a\u00020)*\u00020\n2\u0006\u0010(\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010&\u001a\u00020'*\u00020\n2\u0006\u0010(\u001a\u00020\nH\u0086\u0004\u001a\f\u0010+\u001a\u00020\u0018*\u00020*H\u0007\u001a\f\u0010+\u001a\u00020\b*\u00020'H\u0007\u001a\f\u0010+\u001a\u00020\t*\u00020)H\u0007\u001a\u0013\u0010,\u001a\u0004\u0018\u00010\u0018*\u00020*H\u0007\u00a2\u0006\u0002\u0010-\u001a\u0013\u0010,\u001a\u0004\u0018\u00010\b*\u00020'H\u0007\u00a2\u0006\u0002\u0010.\u001a\u0013\u0010,\u001a\u0004\u0018\u00010\t*\u00020)H\u0007\u00a2\u0006\u0002\u0010/\u001a\f\u00100\u001a\u00020\u0018*\u00020*H\u0007\u001a\f\u00100\u001a\u00020\b*\u00020'H\u0007\u001a\f\u00100\u001a\u00020\t*\u00020)H\u0007\u001a\u0013\u00101\u001a\u0004\u0018\u00010\u0018*\u00020*H\u0007\u00a2\u0006\u0002\u0010-\u001a\u0013\u00101\u001a\u0004\u0018\u00010\b*\u00020'H\u0007\u00a2\u0006\u0002\u0010.\u001a\u0013\u00101\u001a\u0004\u0018\u00010\t*\u00020)H\u0007\u00a2\u0006\u0002\u0010/\u001a\r\u00102\u001a\u00020\u0018*\u00020\u0016H\u0087\b\u001a\u0014\u00102\u001a\u00020\u0018*\u00020\u00162\u0006\u00102\u001a\u000203H\u0007\u001a\r\u00102\u001a\u00020\b*\u00020!H\u0087\b\u001a\u0014\u00102\u001a\u00020\b*\u00020!2\u0006\u00102\u001a\u000203H\u0007\u001a\r\u00102\u001a\u00020\t*\u00020#H\u0087\b\u001a\u0014\u00102\u001a\u00020\t*\u00020#2\u0006\u00102\u001a\u000203H\u0007\u001a\u0014\u00104\u001a\u0004\u0018\u00010\u0018*\u00020\u0016H\u0087\b\u00a2\u0006\u0002\u00105\u001a\u001b\u00104\u001a\u0004\u0018\u00010\u0018*\u00020\u00162\u0006\u00102\u001a\u000203H\u0007\u00a2\u0006\u0002\u00106\u001a\u0014\u00104\u001a\u0004\u0018\u00010\b*\u00020!H\u0087\b\u00a2\u0006\u0002\u00107\u001a\u001b\u00104\u001a\u0004\u0018\u00010\b*\u00020!2\u0006\u00102\u001a\u000203H\u0007\u00a2\u0006\u0002\u00108\u001a\u0014\u00104\u001a\u0004\u0018\u00010\t*\u00020#H\u0087\b\u00a2\u0006\u0002\u00109\u001a\u001b\u00104\u001a\u0004\u0018\u00010\t*\u00020#2\u0006\u00102\u001a\u000203H\u0007\u00a2\u0006\u0002\u0010:\u001a\n\u0010;\u001a\u00020**\u00020*\u001a\n\u0010;\u001a\u00020'*\u00020'\u001a\n\u0010;\u001a\u00020)*\u00020)\u001a\u0015\u0010<\u001a\u00020**\u00020*2\u0006\u0010<\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010<\u001a\u00020'*\u00020'2\u0006\u0010<\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010<\u001a\u00020)*\u00020)2\u0006\u0010<\u001a\u00020\tH\u0086\u0004\u001a\u0013\u0010=\u001a\u0004\u0018\u00010\u0005*\u00020\u0006H\u0000\u00a2\u0006\u0002\u0010>\u001a\u0013\u0010=\u001a\u0004\u0018\u00010\u0005*\u00020\u0007H\u0000\u00a2\u0006\u0002\u0010?\u001a\u0013\u0010=\u001a\u0004\u0018\u00010\u0005*\u00020\bH\u0000\u00a2\u0006\u0002\u0010@\u001a\u0013\u0010=\u001a\u0004\u0018\u00010\u0005*\u00020\tH\u0000\u00a2\u0006\u0002\u0010A\u001a\u0013\u0010=\u001a\u0004\u0018\u00010\u0005*\u00020\nH\u0000\u00a2\u0006\u0002\u0010B\u001a\u0013\u0010C\u001a\u0004\u0018\u00010\b*\u00020\u0006H\u0000\u00a2\u0006\u0002\u0010D\u001a\u0013\u0010C\u001a\u0004\u0018\u00010\b*\u00020\u0007H\u0000\u00a2\u0006\u0002\u0010E\u001a\u0013\u0010C\u001a\u0004\u0018\u00010\b*\u00020\tH\u0000\u00a2\u0006\u0002\u0010F\u001a\u0013\u0010G\u001a\u0004\u0018\u00010\t*\u00020\u0006H\u0000\u00a2\u0006\u0002\u0010H\u001a\u0013\u0010G\u001a\u0004\u0018\u00010\t*\u00020\u0007H\u0000\u00a2\u0006\u0002\u0010I\u001a\u0013\u0010J\u001a\u0004\u0018\u00010\n*\u00020\u0006H\u0000\u00a2\u0006\u0002\u0010K\u001a\u0013\u0010J\u001a\u0004\u0018\u00010\n*\u00020\u0007H\u0000\u00a2\u0006\u0002\u0010L\u001a\u0013\u0010J\u001a\u0004\u0018\u00010\n*\u00020\bH\u0000\u00a2\u0006\u0002\u0010M\u001a\u0013\u0010J\u001a\u0004\u0018\u00010\n*\u00020\tH\u0000\u00a2\u0006\u0002\u0010N\u001a\u0015\u0010O\u001a\u00020!*\u00020\u00052\u0006\u0010(\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010O\u001a\u00020!*\u00020\u00052\u0006\u0010(\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010O\u001a\u00020#*\u00020\u00052\u0006\u0010(\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010O\u001a\u00020!*\u00020\u00052\u0006\u0010(\u001a\u00020\nH\u0086\u0004\u001a\u0015\u0010O\u001a\u00020\u0016*\u00020\u00182\u0006\u0010(\u001a\u00020\u0018H\u0086\u0004\u001a\u0015\u0010O\u001a\u00020!*\u00020\b2\u0006\u0010(\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010O\u001a\u00020!*\u00020\b2\u0006\u0010(\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010O\u001a\u00020#*\u00020\b2\u0006\u0010(\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010O\u001a\u00020!*\u00020\b2\u0006\u0010(\u001a\u00020\nH\u0086\u0004\u001a\u0015\u0010O\u001a\u00020#*\u00020\t2\u0006\u0010(\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010O\u001a\u00020#*\u00020\t2\u0006\u0010(\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010O\u001a\u00020#*\u00020\t2\u0006\u0010(\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010O\u001a\u00020#*\u00020\t2\u0006\u0010(\u001a\u00020\nH\u0086\u0004\u001a\u0015\u0010O\u001a\u00020!*\u00020\n2\u0006\u0010(\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010O\u001a\u00020!*\u00020\n2\u0006\u0010(\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010O\u001a\u00020#*\u00020\n2\u0006\u0010(\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010O\u001a\u00020!*\u00020\n2\u0006\u0010(\u001a\u00020\nH\u0086\u0004\u00a8\u0006P"}, d2={"coerceAtLeast", "T", "", "minimumValue", "(Ljava/lang/Comparable;Ljava/lang/Comparable;)Ljava/lang/Comparable;", "", "", "", "", "", "", "coerceAtMost", "maximumValue", "coerceIn", "(Ljava/lang/Comparable;Ljava/lang/Comparable;Ljava/lang/Comparable;)Ljava/lang/Comparable;", "range", "Lkotlin/ranges/ClosedFloatingPointRange;", "(Ljava/lang/Comparable;Lkotlin/ranges/ClosedFloatingPointRange;)Ljava/lang/Comparable;", "Lkotlin/ranges/ClosedRange;", "(Ljava/lang/Comparable;Lkotlin/ranges/ClosedRange;)Ljava/lang/Comparable;", "contains", "", "Lkotlin/ranges/CharRange;", "element", "", "(Lkotlin/ranges/CharRange;Ljava/lang/Character;)Z", "value", "byteRangeContains", "doubleRangeContains", "floatRangeContains", "intRangeContains", "longRangeContains", "shortRangeContains", "Lkotlin/ranges/IntRange;", "(Lkotlin/ranges/IntRange;Ljava/lang/Integer;)Z", "Lkotlin/ranges/LongRange;", "(Lkotlin/ranges/LongRange;Ljava/lang/Long;)Z", "Lkotlin/ranges/OpenEndRange;", "downTo", "Lkotlin/ranges/IntProgression;", "to", "Lkotlin/ranges/LongProgression;", "Lkotlin/ranges/CharProgression;", "first", "firstOrNull", "(Lkotlin/ranges/CharProgression;)Ljava/lang/Character;", "(Lkotlin/ranges/IntProgression;)Ljava/lang/Integer;", "(Lkotlin/ranges/LongProgression;)Ljava/lang/Long;", "last", "lastOrNull", "random", "Lkotlin/random/Random;", "randomOrNull", "(Lkotlin/ranges/CharRange;)Ljava/lang/Character;", "(Lkotlin/ranges/CharRange;Lkotlin/random/Random;)Ljava/lang/Character;", "(Lkotlin/ranges/IntRange;)Ljava/lang/Integer;", "(Lkotlin/ranges/IntRange;Lkotlin/random/Random;)Ljava/lang/Integer;", "(Lkotlin/ranges/LongRange;)Ljava/lang/Long;", "(Lkotlin/ranges/LongRange;Lkotlin/random/Random;)Ljava/lang/Long;", "reversed", "step", "toByteExactOrNull", "(D)Ljava/lang/Byte;", "(F)Ljava/lang/Byte;", "(I)Ljava/lang/Byte;", "(J)Ljava/lang/Byte;", "(S)Ljava/lang/Byte;", "toIntExactOrNull", "(D)Ljava/lang/Integer;", "(F)Ljava/lang/Integer;", "(J)Ljava/lang/Integer;", "toLongExactOrNull", "(D)Ljava/lang/Long;", "(F)Ljava/lang/Long;", "toShortExactOrNull", "(D)Ljava/lang/Short;", "(F)Ljava/lang/Short;", "(I)Ljava/lang/Short;", "(J)Ljava/lang/Short;", "until", "kotlin-stdlib"}, xs="kotlin/ranges/RangesKt")
@SourceDebugExtension(value={"SMAP\n_Ranges.kt\nKotlin\n*S Kotlin\n*F\n+ 1 _Ranges.kt\nkotlin/ranges/RangesKt___RangesKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,1537:1\n1#2:1538\n*E\n"})
class RangesKt___RangesKt
extends RangesKt__RangesKt {
    @SinceKotlin(version="1.7")
    public static final int first(@NotNull IntProgression intProgression) {
        Intrinsics.checkNotNullParameter(intProgression, "<this>");
        if (intProgression.isEmpty()) {
            throw new NoSuchElementException("Progression " + intProgression + " is empty.");
        }
        return intProgression.getFirst();
    }

    @SinceKotlin(version="1.7")
    public static final long first(@NotNull LongProgression longProgression) {
        Intrinsics.checkNotNullParameter(longProgression, "<this>");
        if (longProgression.isEmpty()) {
            throw new NoSuchElementException("Progression " + longProgression + " is empty.");
        }
        return longProgression.getFirst();
    }

    @SinceKotlin(version="1.7")
    public static final char first(@NotNull CharProgression charProgression) {
        Intrinsics.checkNotNullParameter(charProgression, "<this>");
        if (charProgression.isEmpty()) {
            throw new NoSuchElementException("Progression " + charProgression + " is empty.");
        }
        return charProgression.getFirst();
    }

    @SinceKotlin(version="1.7")
    @Nullable
    public static final Integer firstOrNull(@NotNull IntProgression intProgression) {
        Intrinsics.checkNotNullParameter(intProgression, "<this>");
        return intProgression.isEmpty() ? null : Integer.valueOf(intProgression.getFirst());
    }

    @SinceKotlin(version="1.7")
    @Nullable
    public static final Long firstOrNull(@NotNull LongProgression longProgression) {
        Intrinsics.checkNotNullParameter(longProgression, "<this>");
        return longProgression.isEmpty() ? null : Long.valueOf(longProgression.getFirst());
    }

    @SinceKotlin(version="1.7")
    @Nullable
    public static final Character firstOrNull(@NotNull CharProgression charProgression) {
        Intrinsics.checkNotNullParameter(charProgression, "<this>");
        return charProgression.isEmpty() ? null : Character.valueOf(charProgression.getFirst());
    }

    @SinceKotlin(version="1.7")
    public static final int last(@NotNull IntProgression intProgression) {
        Intrinsics.checkNotNullParameter(intProgression, "<this>");
        if (intProgression.isEmpty()) {
            throw new NoSuchElementException("Progression " + intProgression + " is empty.");
        }
        return intProgression.getLast();
    }

    @SinceKotlin(version="1.7")
    public static final long last(@NotNull LongProgression longProgression) {
        Intrinsics.checkNotNullParameter(longProgression, "<this>");
        if (longProgression.isEmpty()) {
            throw new NoSuchElementException("Progression " + longProgression + " is empty.");
        }
        return longProgression.getLast();
    }

    @SinceKotlin(version="1.7")
    public static final char last(@NotNull CharProgression charProgression) {
        Intrinsics.checkNotNullParameter(charProgression, "<this>");
        if (charProgression.isEmpty()) {
            throw new NoSuchElementException("Progression " + charProgression + " is empty.");
        }
        return charProgression.getLast();
    }

    @SinceKotlin(version="1.7")
    @Nullable
    public static final Integer lastOrNull(@NotNull IntProgression intProgression) {
        Intrinsics.checkNotNullParameter(intProgression, "<this>");
        return intProgression.isEmpty() ? null : Integer.valueOf(intProgression.getLast());
    }

    @SinceKotlin(version="1.7")
    @Nullable
    public static final Long lastOrNull(@NotNull LongProgression longProgression) {
        Intrinsics.checkNotNullParameter(longProgression, "<this>");
        return longProgression.isEmpty() ? null : Long.valueOf(longProgression.getLast());
    }

    @SinceKotlin(version="1.7")
    @Nullable
    public static final Character lastOrNull(@NotNull CharProgression charProgression) {
        Intrinsics.checkNotNullParameter(charProgression, "<this>");
        return charProgression.isEmpty() ? null : Character.valueOf(charProgression.getLast());
    }

    @SinceKotlin(version="1.3")
    @InlineOnly
    private static final int random(IntRange intRange) {
        Intrinsics.checkNotNullParameter(intRange, "<this>");
        return RangesKt.random(intRange, (Random)Random.Default);
    }

    @SinceKotlin(version="1.3")
    @InlineOnly
    private static final long random(LongRange longRange) {
        Intrinsics.checkNotNullParameter(longRange, "<this>");
        return RangesKt.random(longRange, (Random)Random.Default);
    }

    @SinceKotlin(version="1.3")
    @InlineOnly
    private static final char random(CharRange charRange) {
        Intrinsics.checkNotNullParameter(charRange, "<this>");
        return RangesKt.random(charRange, (Random)Random.Default);
    }

    @SinceKotlin(version="1.3")
    public static final int random(@NotNull IntRange intRange, @NotNull Random random2) {
        Intrinsics.checkNotNullParameter(intRange, "<this>");
        Intrinsics.checkNotNullParameter(random2, "random");
        try {
            return RandomKt.nextInt(random2, intRange);
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new NoSuchElementException(illegalArgumentException.getMessage());
        }
    }

    @SinceKotlin(version="1.3")
    public static final long random(@NotNull LongRange longRange, @NotNull Random random2) {
        Intrinsics.checkNotNullParameter(longRange, "<this>");
        Intrinsics.checkNotNullParameter(random2, "random");
        try {
            return RandomKt.nextLong(random2, longRange);
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new NoSuchElementException(illegalArgumentException.getMessage());
        }
    }

    @SinceKotlin(version="1.3")
    public static final char random(@NotNull CharRange charRange, @NotNull Random random2) {
        Intrinsics.checkNotNullParameter(charRange, "<this>");
        Intrinsics.checkNotNullParameter(random2, "random");
        try {
            return (char)random2.nextInt(charRange.getFirst(), charRange.getLast() + '\u0001');
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new NoSuchElementException(illegalArgumentException.getMessage());
        }
    }

    @SinceKotlin(version="1.4")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    @InlineOnly
    private static final Integer randomOrNull(IntRange intRange) {
        Intrinsics.checkNotNullParameter(intRange, "<this>");
        return RangesKt.randomOrNull(intRange, (Random)Random.Default);
    }

    @SinceKotlin(version="1.4")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    @InlineOnly
    private static final Long randomOrNull(LongRange longRange) {
        Intrinsics.checkNotNullParameter(longRange, "<this>");
        return RangesKt.randomOrNull(longRange, (Random)Random.Default);
    }

    @SinceKotlin(version="1.4")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    @InlineOnly
    private static final Character randomOrNull(CharRange charRange) {
        Intrinsics.checkNotNullParameter(charRange, "<this>");
        return RangesKt.randomOrNull(charRange, (Random)Random.Default);
    }

    @SinceKotlin(version="1.4")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    @Nullable
    public static final Integer randomOrNull(@NotNull IntRange intRange, @NotNull Random random2) {
        Intrinsics.checkNotNullParameter(intRange, "<this>");
        Intrinsics.checkNotNullParameter(random2, "random");
        if (intRange.isEmpty()) {
            return null;
        }
        return RandomKt.nextInt(random2, intRange);
    }

    @SinceKotlin(version="1.4")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    @Nullable
    public static final Long randomOrNull(@NotNull LongRange longRange, @NotNull Random random2) {
        Intrinsics.checkNotNullParameter(longRange, "<this>");
        Intrinsics.checkNotNullParameter(random2, "random");
        if (longRange.isEmpty()) {
            return null;
        }
        return RandomKt.nextLong(random2, longRange);
    }

    @SinceKotlin(version="1.4")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    @Nullable
    public static final Character randomOrNull(@NotNull CharRange charRange, @NotNull Random random2) {
        Intrinsics.checkNotNullParameter(charRange, "<this>");
        Intrinsics.checkNotNullParameter(random2, "random");
        if (charRange.isEmpty()) {
            return null;
        }
        return Character.valueOf((char)random2.nextInt(charRange.getFirst(), charRange.getLast() + '\u0001'));
    }

    @SinceKotlin(version="1.3")
    @InlineOnly
    private static final boolean contains(IntRange intRange, Integer n) {
        Intrinsics.checkNotNullParameter(intRange, "<this>");
        return n != null && intRange.contains(n);
    }

    @SinceKotlin(version="1.3")
    @InlineOnly
    private static final boolean contains(LongRange longRange, Long l) {
        Intrinsics.checkNotNullParameter(longRange, "<this>");
        return l != null && longRange.contains(l);
    }

    @SinceKotlin(version="1.3")
    @InlineOnly
    private static final boolean contains(CharRange charRange, Character c) {
        Intrinsics.checkNotNullParameter(charRange, "<this>");
        return c != null && charRange.contains(c.charValue());
    }

    @JvmName(name="intRangeContains")
    public static final boolean intRangeContains(@NotNull ClosedRange<Integer> closedRange, byte by) {
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        return closedRange.contains((Integer)((Comparable)Integer.valueOf(by)));
    }

    @JvmName(name="longRangeContains")
    public static final boolean longRangeContains(@NotNull ClosedRange<Long> closedRange, byte by) {
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        return closedRange.contains((Long)((Comparable)Long.valueOf(by)));
    }

    @JvmName(name="shortRangeContains")
    public static final boolean shortRangeContains(@NotNull ClosedRange<Short> closedRange, byte by) {
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        return closedRange.contains((Short)((Comparable)Short.valueOf(by)));
    }

    @Deprecated(message="This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @DeprecatedSinceKotlin(warningSince="1.3", errorSince="1.4", hiddenSince="1.5")
    @JvmName(name="doubleRangeContains")
    public static final boolean doubleRangeContains(ClosedRange closedRange, byte by) {
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        return closedRange.contains((Comparable)Double.valueOf(by));
    }

    @Deprecated(message="This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @DeprecatedSinceKotlin(warningSince="1.3", errorSince="1.4", hiddenSince="1.5")
    @JvmName(name="floatRangeContains")
    public static final boolean floatRangeContains(ClosedRange closedRange, byte by) {
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        return closedRange.contains((Comparable)Float.valueOf(by));
    }

    @JvmName(name="intRangeContains")
    @SinceKotlin(version="1.9")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    public static final boolean intRangeContains(@NotNull OpenEndRange<Integer> openEndRange, byte by) {
        Intrinsics.checkNotNullParameter(openEndRange, "<this>");
        return openEndRange.contains((Integer)((Comparable)Integer.valueOf(by)));
    }

    @JvmName(name="longRangeContains")
    @SinceKotlin(version="1.9")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    public static final boolean longRangeContains(@NotNull OpenEndRange<Long> openEndRange, byte by) {
        Intrinsics.checkNotNullParameter(openEndRange, "<this>");
        return openEndRange.contains((Long)((Comparable)Long.valueOf(by)));
    }

    @JvmName(name="shortRangeContains")
    @SinceKotlin(version="1.9")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    public static final boolean shortRangeContains(@NotNull OpenEndRange<Short> openEndRange, byte by) {
        Intrinsics.checkNotNullParameter(openEndRange, "<this>");
        return openEndRange.contains((Short)((Comparable)Short.valueOf(by)));
    }

    @InlineOnly
    private static final boolean contains(IntRange intRange, byte by) {
        Intrinsics.checkNotNullParameter(intRange, "<this>");
        return RangesKt.intRangeContains((ClosedRange<Integer>)intRange, by);
    }

    @InlineOnly
    private static final boolean contains(LongRange longRange, byte by) {
        Intrinsics.checkNotNullParameter(longRange, "<this>");
        return RangesKt.longRangeContains((ClosedRange<Long>)longRange, by);
    }

    @Deprecated(message="This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @DeprecatedSinceKotlin(warningSince="1.3", errorSince="1.4", hiddenSince="1.5")
    @JvmName(name="intRangeContains")
    public static final boolean intRangeContains(ClosedRange closedRange, double d) {
        Integer n;
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        Integer n2 = n = RangesKt.toIntExactOrNull(d);
        boolean bl = false;
        Integer n3 = n2;
        return n3 != null ? closedRange.contains((Comparable)n3) : false;
    }

    @Deprecated(message="This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @DeprecatedSinceKotlin(warningSince="1.3", errorSince="1.4", hiddenSince="1.5")
    @JvmName(name="longRangeContains")
    public static final boolean longRangeContains(ClosedRange closedRange, double d) {
        Long l;
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        Long l2 = l = RangesKt.toLongExactOrNull(d);
        boolean bl = false;
        Long l3 = l2;
        return l3 != null ? closedRange.contains((Comparable)l3) : false;
    }

    @Deprecated(message="This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @DeprecatedSinceKotlin(warningSince="1.3", errorSince="1.4", hiddenSince="1.5")
    @JvmName(name="byteRangeContains")
    public static final boolean byteRangeContains(ClosedRange closedRange, double d) {
        Byte by;
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        Byte by2 = by = RangesKt.toByteExactOrNull(d);
        boolean bl = false;
        Byte by3 = by2;
        return by3 != null ? closedRange.contains((Comparable)by3) : false;
    }

    @Deprecated(message="This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @DeprecatedSinceKotlin(warningSince="1.3", errorSince="1.4", hiddenSince="1.5")
    @JvmName(name="shortRangeContains")
    public static final boolean shortRangeContains(ClosedRange closedRange, double d) {
        Short s;
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        Short s2 = s = RangesKt.toShortExactOrNull(d);
        boolean bl = false;
        Short s3 = s2;
        return s3 != null ? closedRange.contains((Comparable)s3) : false;
    }

    @JvmName(name="floatRangeContains")
    public static final boolean floatRangeContains(@NotNull ClosedRange<Float> closedRange, double d) {
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        return closedRange.contains((Float)((Comparable)Float.valueOf((float)d)));
    }

    @Deprecated(message="This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @DeprecatedSinceKotlin(warningSince="1.3", errorSince="1.4", hiddenSince="1.5")
    @JvmName(name="intRangeContains")
    public static final boolean intRangeContains(ClosedRange closedRange, float f) {
        Integer n;
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        Integer n2 = n = RangesKt.toIntExactOrNull(f);
        boolean bl = false;
        Integer n3 = n2;
        return n3 != null ? closedRange.contains((Comparable)n3) : false;
    }

    @Deprecated(message="This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @DeprecatedSinceKotlin(warningSince="1.3", errorSince="1.4", hiddenSince="1.5")
    @JvmName(name="longRangeContains")
    public static final boolean longRangeContains(ClosedRange closedRange, float f) {
        Long l;
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        Long l2 = l = RangesKt.toLongExactOrNull(f);
        boolean bl = false;
        Long l3 = l2;
        return l3 != null ? closedRange.contains((Comparable)l3) : false;
    }

    @Deprecated(message="This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @DeprecatedSinceKotlin(warningSince="1.3", errorSince="1.4", hiddenSince="1.5")
    @JvmName(name="byteRangeContains")
    public static final boolean byteRangeContains(ClosedRange closedRange, float f) {
        Byte by;
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        Byte by2 = by = RangesKt.toByteExactOrNull(f);
        boolean bl = false;
        Byte by3 = by2;
        return by3 != null ? closedRange.contains((Comparable)by3) : false;
    }

    @Deprecated(message="This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @DeprecatedSinceKotlin(warningSince="1.3", errorSince="1.4", hiddenSince="1.5")
    @JvmName(name="shortRangeContains")
    public static final boolean shortRangeContains(ClosedRange closedRange, float f) {
        Short s;
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        Short s2 = s = RangesKt.toShortExactOrNull(f);
        boolean bl = false;
        Short s3 = s2;
        return s3 != null ? closedRange.contains((Comparable)s3) : false;
    }

    @JvmName(name="doubleRangeContains")
    public static final boolean doubleRangeContains(@NotNull ClosedRange<Double> closedRange, float f) {
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        return closedRange.contains((Double)((Comparable)Double.valueOf(f)));
    }

    @JvmName(name="doubleRangeContains")
    @SinceKotlin(version="1.9")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    public static final boolean doubleRangeContains(@NotNull OpenEndRange<Double> openEndRange, float f) {
        Intrinsics.checkNotNullParameter(openEndRange, "<this>");
        return openEndRange.contains((Double)((Comparable)Double.valueOf(f)));
    }

    @JvmName(name="longRangeContains")
    public static final boolean longRangeContains(@NotNull ClosedRange<Long> closedRange, int n) {
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        return closedRange.contains((Long)((Comparable)Long.valueOf(n)));
    }

    @JvmName(name="byteRangeContains")
    public static final boolean byteRangeContains(@NotNull ClosedRange<Byte> closedRange, int n) {
        Byte by;
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        Byte by2 = by = RangesKt.toByteExactOrNull(n);
        boolean bl = false;
        Byte by3 = by2;
        return by3 != null ? closedRange.contains((Byte)((Comparable)by3)) : false;
    }

    @JvmName(name="shortRangeContains")
    public static final boolean shortRangeContains(@NotNull ClosedRange<Short> closedRange, int n) {
        Short s;
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        Short s2 = s = RangesKt.toShortExactOrNull(n);
        boolean bl = false;
        Short s3 = s2;
        return s3 != null ? closedRange.contains((Short)((Comparable)s3)) : false;
    }

    @Deprecated(message="This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @DeprecatedSinceKotlin(warningSince="1.3", errorSince="1.4", hiddenSince="1.5")
    @JvmName(name="doubleRangeContains")
    public static final boolean doubleRangeContains(ClosedRange closedRange, int n) {
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        return closedRange.contains((Comparable)Double.valueOf(n));
    }

    @Deprecated(message="This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @DeprecatedSinceKotlin(warningSince="1.3", errorSince="1.4", hiddenSince="1.5")
    @JvmName(name="floatRangeContains")
    public static final boolean floatRangeContains(ClosedRange closedRange, int n) {
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        return closedRange.contains((Comparable)Float.valueOf(n));
    }

    @JvmName(name="longRangeContains")
    @SinceKotlin(version="1.9")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    public static final boolean longRangeContains(@NotNull OpenEndRange<Long> openEndRange, int n) {
        Intrinsics.checkNotNullParameter(openEndRange, "<this>");
        return openEndRange.contains((Long)((Comparable)Long.valueOf(n)));
    }

    @JvmName(name="byteRangeContains")
    @SinceKotlin(version="1.9")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    public static final boolean byteRangeContains(@NotNull OpenEndRange<Byte> openEndRange, int n) {
        Byte by;
        Intrinsics.checkNotNullParameter(openEndRange, "<this>");
        Byte by2 = by = RangesKt.toByteExactOrNull(n);
        boolean bl = false;
        Byte by3 = by2;
        return by3 != null ? openEndRange.contains((Byte)((Comparable)by3)) : false;
    }

    @JvmName(name="shortRangeContains")
    @SinceKotlin(version="1.9")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    public static final boolean shortRangeContains(@NotNull OpenEndRange<Short> openEndRange, int n) {
        Short s;
        Intrinsics.checkNotNullParameter(openEndRange, "<this>");
        Short s2 = s = RangesKt.toShortExactOrNull(n);
        boolean bl = false;
        Short s3 = s2;
        return s3 != null ? openEndRange.contains((Short)((Comparable)s3)) : false;
    }

    @InlineOnly
    private static final boolean contains(LongRange longRange, int n) {
        Intrinsics.checkNotNullParameter(longRange, "<this>");
        return RangesKt.longRangeContains((ClosedRange<Long>)longRange, n);
    }

    @JvmName(name="intRangeContains")
    public static final boolean intRangeContains(@NotNull ClosedRange<Integer> closedRange, long l) {
        Integer n;
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        Integer n2 = n = RangesKt.toIntExactOrNull(l);
        boolean bl = false;
        Integer n3 = n2;
        return n3 != null ? closedRange.contains((Integer)((Comparable)n3)) : false;
    }

    @JvmName(name="byteRangeContains")
    public static final boolean byteRangeContains(@NotNull ClosedRange<Byte> closedRange, long l) {
        Byte by;
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        Byte by2 = by = RangesKt.toByteExactOrNull(l);
        boolean bl = false;
        Byte by3 = by2;
        return by3 != null ? closedRange.contains((Byte)((Comparable)by3)) : false;
    }

    @JvmName(name="shortRangeContains")
    public static final boolean shortRangeContains(@NotNull ClosedRange<Short> closedRange, long l) {
        Short s;
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        Short s2 = s = RangesKt.toShortExactOrNull(l);
        boolean bl = false;
        Short s3 = s2;
        return s3 != null ? closedRange.contains((Short)((Comparable)s3)) : false;
    }

    @Deprecated(message="This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @DeprecatedSinceKotlin(warningSince="1.3", errorSince="1.4", hiddenSince="1.5")
    @JvmName(name="doubleRangeContains")
    public static final boolean doubleRangeContains(ClosedRange closedRange, long l) {
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        return closedRange.contains((Comparable)Double.valueOf(l));
    }

    @Deprecated(message="This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @DeprecatedSinceKotlin(warningSince="1.3", errorSince="1.4", hiddenSince="1.5")
    @JvmName(name="floatRangeContains")
    public static final boolean floatRangeContains(ClosedRange closedRange, long l) {
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        return closedRange.contains((Comparable)Float.valueOf(l));
    }

    @JvmName(name="intRangeContains")
    @SinceKotlin(version="1.9")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    public static final boolean intRangeContains(@NotNull OpenEndRange<Integer> openEndRange, long l) {
        Integer n;
        Intrinsics.checkNotNullParameter(openEndRange, "<this>");
        Integer n2 = n = RangesKt.toIntExactOrNull(l);
        boolean bl = false;
        Integer n3 = n2;
        return n3 != null ? openEndRange.contains((Integer)((Comparable)n3)) : false;
    }

    @JvmName(name="byteRangeContains")
    @SinceKotlin(version="1.9")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    public static final boolean byteRangeContains(@NotNull OpenEndRange<Byte> openEndRange, long l) {
        Byte by;
        Intrinsics.checkNotNullParameter(openEndRange, "<this>");
        Byte by2 = by = RangesKt.toByteExactOrNull(l);
        boolean bl = false;
        Byte by3 = by2;
        return by3 != null ? openEndRange.contains((Byte)((Comparable)by3)) : false;
    }

    @JvmName(name="shortRangeContains")
    @SinceKotlin(version="1.9")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    public static final boolean shortRangeContains(@NotNull OpenEndRange<Short> openEndRange, long l) {
        Short s;
        Intrinsics.checkNotNullParameter(openEndRange, "<this>");
        Short s2 = s = RangesKt.toShortExactOrNull(l);
        boolean bl = false;
        Short s3 = s2;
        return s3 != null ? openEndRange.contains((Short)((Comparable)s3)) : false;
    }

    @InlineOnly
    private static final boolean contains(IntRange intRange, long l) {
        Intrinsics.checkNotNullParameter(intRange, "<this>");
        return RangesKt.intRangeContains((ClosedRange<Integer>)intRange, l);
    }

    @JvmName(name="intRangeContains")
    public static final boolean intRangeContains(@NotNull ClosedRange<Integer> closedRange, short s) {
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        return closedRange.contains((Integer)((Comparable)Integer.valueOf(s)));
    }

    @JvmName(name="longRangeContains")
    public static final boolean longRangeContains(@NotNull ClosedRange<Long> closedRange, short s) {
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        return closedRange.contains((Long)((Comparable)Long.valueOf(s)));
    }

    @JvmName(name="byteRangeContains")
    public static final boolean byteRangeContains(@NotNull ClosedRange<Byte> closedRange, short s) {
        Byte by;
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        Byte by2 = by = RangesKt.toByteExactOrNull(s);
        boolean bl = false;
        Byte by3 = by2;
        return by3 != null ? closedRange.contains((Byte)((Comparable)by3)) : false;
    }

    @Deprecated(message="This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @DeprecatedSinceKotlin(warningSince="1.3", errorSince="1.4", hiddenSince="1.5")
    @JvmName(name="doubleRangeContains")
    public static final boolean doubleRangeContains(ClosedRange closedRange, short s) {
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        return closedRange.contains((Comparable)Double.valueOf(s));
    }

    @Deprecated(message="This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @DeprecatedSinceKotlin(warningSince="1.3", errorSince="1.4", hiddenSince="1.5")
    @JvmName(name="floatRangeContains")
    public static final boolean floatRangeContains(ClosedRange closedRange, short s) {
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        return closedRange.contains((Comparable)Float.valueOf(s));
    }

    @JvmName(name="intRangeContains")
    @SinceKotlin(version="1.9")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    public static final boolean intRangeContains(@NotNull OpenEndRange<Integer> openEndRange, short s) {
        Intrinsics.checkNotNullParameter(openEndRange, "<this>");
        return openEndRange.contains((Integer)((Comparable)Integer.valueOf(s)));
    }

    @JvmName(name="longRangeContains")
    @SinceKotlin(version="1.9")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    public static final boolean longRangeContains(@NotNull OpenEndRange<Long> openEndRange, short s) {
        Intrinsics.checkNotNullParameter(openEndRange, "<this>");
        return openEndRange.contains((Long)((Comparable)Long.valueOf(s)));
    }

    @JvmName(name="byteRangeContains")
    @SinceKotlin(version="1.9")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    public static final boolean byteRangeContains(@NotNull OpenEndRange<Byte> openEndRange, short s) {
        Byte by;
        Intrinsics.checkNotNullParameter(openEndRange, "<this>");
        Byte by2 = by = RangesKt.toByteExactOrNull(s);
        boolean bl = false;
        Byte by3 = by2;
        return by3 != null ? openEndRange.contains((Byte)((Comparable)by3)) : false;
    }

    @InlineOnly
    private static final boolean contains(IntRange intRange, short s) {
        Intrinsics.checkNotNullParameter(intRange, "<this>");
        return RangesKt.intRangeContains((ClosedRange<Integer>)intRange, s);
    }

    @InlineOnly
    private static final boolean contains(LongRange longRange, short s) {
        Intrinsics.checkNotNullParameter(longRange, "<this>");
        return RangesKt.longRangeContains((ClosedRange<Long>)longRange, s);
    }

    @NotNull
    public static final IntProgression downTo(int n, byte by) {
        return IntProgression.Companion.fromClosedRange(n, by, -1);
    }

    @NotNull
    public static final LongProgression downTo(long l, byte by) {
        return LongProgression.Companion.fromClosedRange(l, by, -1L);
    }

    @NotNull
    public static final IntProgression downTo(byte by, byte by2) {
        return IntProgression.Companion.fromClosedRange(by, by2, -1);
    }

    @NotNull
    public static final IntProgression downTo(short s, byte by) {
        return IntProgression.Companion.fromClosedRange(s, by, -1);
    }

    @NotNull
    public static final CharProgression downTo(char c, char c2) {
        return CharProgression.Companion.fromClosedRange(c, c2, -1);
    }

    @NotNull
    public static final IntProgression downTo(int n, int n2) {
        return IntProgression.Companion.fromClosedRange(n, n2, -1);
    }

    @NotNull
    public static final LongProgression downTo(long l, int n) {
        return LongProgression.Companion.fromClosedRange(l, n, -1L);
    }

    @NotNull
    public static final IntProgression downTo(byte by, int n) {
        return IntProgression.Companion.fromClosedRange(by, n, -1);
    }

    @NotNull
    public static final IntProgression downTo(short s, int n) {
        return IntProgression.Companion.fromClosedRange(s, n, -1);
    }

    @NotNull
    public static final LongProgression downTo(int n, long l) {
        return LongProgression.Companion.fromClosedRange(n, l, -1L);
    }

    @NotNull
    public static final LongProgression downTo(long l, long l2) {
        return LongProgression.Companion.fromClosedRange(l, l2, -1L);
    }

    @NotNull
    public static final LongProgression downTo(byte by, long l) {
        return LongProgression.Companion.fromClosedRange(by, l, -1L);
    }

    @NotNull
    public static final LongProgression downTo(short s, long l) {
        return LongProgression.Companion.fromClosedRange(s, l, -1L);
    }

    @NotNull
    public static final IntProgression downTo(int n, short s) {
        return IntProgression.Companion.fromClosedRange(n, s, -1);
    }

    @NotNull
    public static final LongProgression downTo(long l, short s) {
        return LongProgression.Companion.fromClosedRange(l, s, -1L);
    }

    @NotNull
    public static final IntProgression downTo(byte by, short s) {
        return IntProgression.Companion.fromClosedRange(by, s, -1);
    }

    @NotNull
    public static final IntProgression downTo(short s, short s2) {
        return IntProgression.Companion.fromClosedRange(s, s2, -1);
    }

    @NotNull
    public static final IntProgression reversed(@NotNull IntProgression intProgression) {
        Intrinsics.checkNotNullParameter(intProgression, "<this>");
        return IntProgression.Companion.fromClosedRange(intProgression.getLast(), intProgression.getFirst(), -intProgression.getStep());
    }

    @NotNull
    public static final LongProgression reversed(@NotNull LongProgression longProgression) {
        Intrinsics.checkNotNullParameter(longProgression, "<this>");
        return LongProgression.Companion.fromClosedRange(longProgression.getLast(), longProgression.getFirst(), -longProgression.getStep());
    }

    @NotNull
    public static final CharProgression reversed(@NotNull CharProgression charProgression) {
        Intrinsics.checkNotNullParameter(charProgression, "<this>");
        return CharProgression.Companion.fromClosedRange(charProgression.getLast(), charProgression.getFirst(), -charProgression.getStep());
    }

    @NotNull
    public static final IntProgression step(@NotNull IntProgression intProgression, int n) {
        Intrinsics.checkNotNullParameter(intProgression, "<this>");
        RangesKt.checkStepIsPositive(n > 0, n);
        return IntProgression.Companion.fromClosedRange(intProgression.getFirst(), intProgression.getLast(), intProgression.getStep() > 0 ? n : -n);
    }

    @NotNull
    public static final LongProgression step(@NotNull LongProgression longProgression, long l) {
        Intrinsics.checkNotNullParameter(longProgression, "<this>");
        RangesKt.checkStepIsPositive(l > 0L, l);
        return LongProgression.Companion.fromClosedRange(longProgression.getFirst(), longProgression.getLast(), longProgression.getStep() > 0L ? l : -l);
    }

    @NotNull
    public static final CharProgression step(@NotNull CharProgression charProgression, int n) {
        Intrinsics.checkNotNullParameter(charProgression, "<this>");
        RangesKt.checkStepIsPositive(n > 0, n);
        return CharProgression.Companion.fromClosedRange(charProgression.getFirst(), charProgression.getLast(), charProgression.getStep() > 0 ? n : -n);
    }

    @Nullable
    public static final Byte toByteExactOrNull(int n) {
        return new IntRange(-128, 127).contains(n) ? Byte.valueOf((byte)n) : null;
    }

    @Nullable
    public static final Byte toByteExactOrNull(long l) {
        return new LongRange(-128L, 127L).contains(l) ? Byte.valueOf((byte)l) : null;
    }

    @Nullable
    public static final Byte toByteExactOrNull(short s) {
        return RangesKt.intRangeContains((ClosedRange<Integer>)new IntRange(-128, 127), s) ? Byte.valueOf((byte)s) : null;
    }

    @Nullable
    public static final Byte toByteExactOrNull(double d) {
        return (-128.0 <= d ? d <= 127.0 : false) ? Byte.valueOf((byte)d) : null;
    }

    @Nullable
    public static final Byte toByteExactOrNull(float f) {
        return (-128.0f <= f ? f <= 127.0f : false) ? Byte.valueOf((byte)f) : null;
    }

    @Nullable
    public static final Integer toIntExactOrNull(long l) {
        return new LongRange(Integer.MIN_VALUE, Integer.MAX_VALUE).contains(l) ? Integer.valueOf((int)l) : null;
    }

    @Nullable
    public static final Integer toIntExactOrNull(double d) {
        return (-2.147483648E9 <= d ? d <= 2.147483647E9 : false) ? Integer.valueOf((int)d) : null;
    }

    @Nullable
    public static final Integer toIntExactOrNull(float f) {
        return (-2.14748365E9f <= f ? f <= 2.14748365E9f : false) ? Integer.valueOf((int)f) : null;
    }

    @Nullable
    public static final Long toLongExactOrNull(double d) {
        return (-9.223372036854776E18 <= d ? d <= 9.223372036854776E18 : false) ? Long.valueOf((long)d) : null;
    }

    @Nullable
    public static final Long toLongExactOrNull(float f) {
        return (-9.223372E18f <= f ? f <= 9.223372E18f : false) ? Long.valueOf((long)f) : null;
    }

    @Nullable
    public static final Short toShortExactOrNull(int n) {
        return new IntRange(Short.MIN_VALUE, Short.MAX_VALUE).contains(n) ? Short.valueOf((short)n) : null;
    }

    @Nullable
    public static final Short toShortExactOrNull(long l) {
        return new LongRange(-32768L, 32767L).contains(l) ? Short.valueOf((short)l) : null;
    }

    @Nullable
    public static final Short toShortExactOrNull(double d) {
        return (-32768.0 <= d ? d <= 32767.0 : false) ? Short.valueOf((short)d) : null;
    }

    @Nullable
    public static final Short toShortExactOrNull(float f) {
        return (-32768.0f <= f ? f <= 32767.0f : false) ? Short.valueOf((short)f) : null;
    }

    @NotNull
    public static final IntRange until(int n, byte by) {
        return new IntRange(n, by - 1);
    }

    @NotNull
    public static final LongRange until(long l, byte by) {
        return new LongRange(l, (long)by - 1L);
    }

    @NotNull
    public static final IntRange until(byte by, byte by2) {
        return new IntRange(by, by2 - 1);
    }

    @NotNull
    public static final IntRange until(short s, byte by) {
        return new IntRange(s, by - 1);
    }

    @NotNull
    public static final CharRange until(char c, char c2) {
        if (Intrinsics.compare(c2, 0) <= 0) {
            return CharRange.Companion.getEMPTY();
        }
        return new CharRange(c, (char)(c2 - '\u0001'));
    }

    @NotNull
    public static final IntRange until(int n, int n2) {
        if (n2 <= Integer.MIN_VALUE) {
            return IntRange.Companion.getEMPTY();
        }
        return new IntRange(n, n2 - 1);
    }

    @NotNull
    public static final LongRange until(long l, int n) {
        return new LongRange(l, (long)n - 1L);
    }

    @NotNull
    public static final IntRange until(byte by, int n) {
        if (n <= Integer.MIN_VALUE) {
            return IntRange.Companion.getEMPTY();
        }
        return new IntRange(by, n - 1);
    }

    @NotNull
    public static final IntRange until(short s, int n) {
        if (n <= Integer.MIN_VALUE) {
            return IntRange.Companion.getEMPTY();
        }
        return new IntRange(s, n - 1);
    }

    @NotNull
    public static final LongRange until(int n, long l) {
        if (l <= Long.MIN_VALUE) {
            return LongRange.Companion.getEMPTY();
        }
        return new LongRange(n, l - 1L);
    }

    @NotNull
    public static final LongRange until(long l, long l2) {
        if (l2 <= Long.MIN_VALUE) {
            return LongRange.Companion.getEMPTY();
        }
        return new LongRange(l, l2 - 1L);
    }

    @NotNull
    public static final LongRange until(byte by, long l) {
        if (l <= Long.MIN_VALUE) {
            return LongRange.Companion.getEMPTY();
        }
        return new LongRange(by, l - 1L);
    }

    @NotNull
    public static final LongRange until(short s, long l) {
        if (l <= Long.MIN_VALUE) {
            return LongRange.Companion.getEMPTY();
        }
        return new LongRange(s, l - 1L);
    }

    @NotNull
    public static final IntRange until(int n, short s) {
        return new IntRange(n, s - 1);
    }

    @NotNull
    public static final LongRange until(long l, short s) {
        return new LongRange(l, (long)s - 1L);
    }

    @NotNull
    public static final IntRange until(byte by, short s) {
        return new IntRange(by, s - 1);
    }

    @NotNull
    public static final IntRange until(short s, short s2) {
        return new IntRange(s, s2 - 1);
    }

    @NotNull
    public static final <T extends Comparable<? super T>> T coerceAtLeast(@NotNull T t, @NotNull T t2) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        Intrinsics.checkNotNullParameter(t2, "minimumValue");
        return t.compareTo(t2) < 0 ? t2 : t;
    }

    public static final byte coerceAtLeast(byte by, byte by2) {
        return by < by2 ? by2 : by;
    }

    public static final short coerceAtLeast(short s, short s2) {
        return s < s2 ? s2 : s;
    }

    public static final int coerceAtLeast(int n, int n2) {
        return n < n2 ? n2 : n;
    }

    public static final long coerceAtLeast(long l, long l2) {
        return l < l2 ? l2 : l;
    }

    public static final float coerceAtLeast(float f, float f2) {
        return f < f2 ? f2 : f;
    }

    public static final double coerceAtLeast(double d, double d2) {
        return d < d2 ? d2 : d;
    }

    @NotNull
    public static final <T extends Comparable<? super T>> T coerceAtMost(@NotNull T t, @NotNull T t2) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        Intrinsics.checkNotNullParameter(t2, "maximumValue");
        return t.compareTo(t2) > 0 ? t2 : t;
    }

    public static final byte coerceAtMost(byte by, byte by2) {
        return by > by2 ? by2 : by;
    }

    public static final short coerceAtMost(short s, short s2) {
        return s > s2 ? s2 : s;
    }

    public static final int coerceAtMost(int n, int n2) {
        return n > n2 ? n2 : n;
    }

    public static final long coerceAtMost(long l, long l2) {
        return l > l2 ? l2 : l;
    }

    public static final float coerceAtMost(float f, float f2) {
        return f > f2 ? f2 : f;
    }

    public static final double coerceAtMost(double d, double d2) {
        return d > d2 ? d2 : d;
    }

    @NotNull
    public static final <T extends Comparable<? super T>> T coerceIn(@NotNull T t, @Nullable T t2, @Nullable T t3) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        if (t2 != null && t3 != null) {
            if (t2.compareTo(t3) > 0) {
                throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + t3 + " is less than minimum " + t2 + '.');
            }
            if (t.compareTo(t2) < 0) {
                return t2;
            }
            if (t.compareTo(t3) > 0) {
                return t3;
            }
        } else {
            if (t2 != null && t.compareTo(t2) < 0) {
                return t2;
            }
            if (t3 != null && t.compareTo(t3) > 0) {
                return t3;
            }
        }
        return t;
    }

    public static final byte coerceIn(byte by, byte by2, byte by3) {
        if (by2 > by3) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + by3 + " is less than minimum " + by2 + '.');
        }
        if (by < by2) {
            return by2;
        }
        if (by > by3) {
            return by3;
        }
        return by;
    }

    public static final short coerceIn(short s, short s2, short s3) {
        if (s2 > s3) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + s3 + " is less than minimum " + s2 + '.');
        }
        if (s < s2) {
            return s2;
        }
        if (s > s3) {
            return s3;
        }
        return s;
    }

    public static final int coerceIn(int n, int n2, int n3) {
        if (n2 > n3) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + n3 + " is less than minimum " + n2 + '.');
        }
        if (n < n2) {
            return n2;
        }
        if (n > n3) {
            return n3;
        }
        return n;
    }

    public static final long coerceIn(long l, long l2, long l3) {
        if (l2 > l3) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + l3 + " is less than minimum " + l2 + '.');
        }
        if (l < l2) {
            return l2;
        }
        if (l > l3) {
            return l3;
        }
        return l;
    }

    public static final float coerceIn(float f, float f2, float f3) {
        if (f2 > f3) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + f3 + " is less than minimum " + f2 + '.');
        }
        if (f < f2) {
            return f2;
        }
        if (f > f3) {
            return f3;
        }
        return f;
    }

    public static final double coerceIn(double d, double d2, double d3) {
        if (d2 > d3) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + d3 + " is less than minimum " + d2 + '.');
        }
        if (d < d2) {
            return d2;
        }
        if (d > d3) {
            return d3;
        }
        return d;
    }

    @SinceKotlin(version="1.1")
    @NotNull
    public static final <T extends Comparable<? super T>> T coerceIn(@NotNull T t, @NotNull ClosedFloatingPointRange<T> closedFloatingPointRange) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        Intrinsics.checkNotNullParameter(closedFloatingPointRange, "range");
        if (closedFloatingPointRange.isEmpty()) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: " + closedFloatingPointRange + '.');
        }
        return closedFloatingPointRange.lessThanOrEquals(t, closedFloatingPointRange.getStart()) && !closedFloatingPointRange.lessThanOrEquals(closedFloatingPointRange.getStart(), t) ? closedFloatingPointRange.getStart() : (closedFloatingPointRange.lessThanOrEquals(closedFloatingPointRange.getEndInclusive(), t) && !closedFloatingPointRange.lessThanOrEquals(t, closedFloatingPointRange.getEndInclusive()) ? closedFloatingPointRange.getEndInclusive() : t);
    }

    @NotNull
    public static final <T extends Comparable<? super T>> T coerceIn(@NotNull T object, @NotNull ClosedRange<T> closedRange) {
        Intrinsics.checkNotNullParameter(object, "<this>");
        Intrinsics.checkNotNullParameter(closedRange, "range");
        if (closedRange instanceof ClosedFloatingPointRange) {
            return RangesKt.coerceIn(object, (ClosedFloatingPointRange)closedRange);
        }
        if (closedRange.isEmpty()) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: " + closedRange + '.');
        }
        return (T)(object.compareTo(closedRange.getStart()) < 0 ? closedRange.getStart() : (object.compareTo(closedRange.getEndInclusive()) > 0 ? closedRange.getEndInclusive() : object));
    }

    public static final int coerceIn(int n, @NotNull ClosedRange<Integer> closedRange) {
        Intrinsics.checkNotNullParameter(closedRange, "range");
        if (closedRange instanceof ClosedFloatingPointRange) {
            return ((Number)((Object)RangesKt.coerceIn((Comparable)Integer.valueOf(n), (ClosedFloatingPointRange)closedRange))).intValue();
        }
        if (closedRange.isEmpty()) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: " + closedRange + '.');
        }
        return n < ((Number)closedRange.getStart()).intValue() ? ((Number)closedRange.getStart()).intValue() : (n > ((Number)closedRange.getEndInclusive()).intValue() ? ((Number)closedRange.getEndInclusive()).intValue() : n);
    }

    public static final long coerceIn(long l, @NotNull ClosedRange<Long> closedRange) {
        Intrinsics.checkNotNullParameter(closedRange, "range");
        if (closedRange instanceof ClosedFloatingPointRange) {
            return ((Number)((Object)RangesKt.coerceIn((Comparable)Long.valueOf(l), (ClosedFloatingPointRange)closedRange))).longValue();
        }
        if (closedRange.isEmpty()) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: " + closedRange + '.');
        }
        return l < ((Number)closedRange.getStart()).longValue() ? ((Number)closedRange.getStart()).longValue() : (l > ((Number)closedRange.getEndInclusive()).longValue() ? ((Number)closedRange.getEndInclusive()).longValue() : l);
    }
}

