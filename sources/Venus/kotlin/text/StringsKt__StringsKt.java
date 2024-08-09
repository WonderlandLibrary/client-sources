/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Deprecated;
import kotlin.ExperimentalStdlibApi;
import kotlin.Metadata;
import kotlin.OverloadResolutionByLambdaReturnType;
import kotlin.Pair;
import kotlin.ReplaceWith;
import kotlin.SinceKotlin;
import kotlin.TuplesKt;
import kotlin.WasExperimental;
import kotlin.collections.ArraysKt;
import kotlin.collections.CharIterator;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.IntProgression;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.CharsKt;
import kotlin.text.DelimitedRangesSequence;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import kotlin.text.StringsKt__StringsJVMKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 9, 0}, k=5, xi=49, d1={"\u0000\u0084\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\r\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\f\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0019\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\b\n\u0002\u0010\u0011\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b!\u001a\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0006H\u0000\u001a\u001c\u0010\f\u001a\u00020\r*\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u00022\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a\u001c\u0010\u0011\u001a\u00020\r*\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u00022\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a\u001f\u0010\u0012\u001a\u00020\u0010*\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u00142\b\b\u0002\u0010\u000f\u001a\u00020\u0010H\u0086\u0002\u001a\u001f\u0010\u0012\u001a\u00020\u0010*\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u00022\b\b\u0002\u0010\u000f\u001a\u00020\u0010H\u0086\u0002\u001a\u0015\u0010\u0012\u001a\u00020\u0010*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0016H\u0087\n\u001a\u0018\u0010\u0017\u001a\u00020\u0010*\u0004\u0018\u00010\u00022\b\u0010\u000e\u001a\u0004\u0018\u00010\u0002H\u0000\u001a\u0018\u0010\u0018\u001a\u00020\u0010*\u0004\u0018\u00010\u00022\b\u0010\u000e\u001a\u0004\u0018\u00010\u0002H\u0000\u001a\u001c\u0010\u0019\u001a\u00020\u0010*\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u00142\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a\u001c\u0010\u0019\u001a\u00020\u0010*\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u00022\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a:\u0010\u001b\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\r\u0018\u00010\u001c*\u00020\u00022\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\r0\u001e2\b\b\u0002\u0010\u001f\u001a\u00020\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001aE\u0010\u001b\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\r\u0018\u00010\u001c*\u00020\u00022\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\r0\u001e2\u0006\u0010\u001f\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010 \u001a\u00020\u0010H\u0002\u00a2\u0006\u0002\b!\u001a:\u0010\"\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\r\u0018\u00010\u001c*\u00020\u00022\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\r0\u001e2\b\b\u0002\u0010\u001f\u001a\u00020\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a\u0012\u0010#\u001a\u00020\u0010*\u00020\u00022\u0006\u0010$\u001a\u00020\u0006\u001a7\u0010%\u001a\u0002H&\"\f\b\u0000\u0010'*\u00020\u0002*\u0002H&\"\u0004\b\u0001\u0010&*\u0002H'2\f\u0010(\u001a\b\u0012\u0004\u0012\u0002H&0)H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010*\u001a7\u0010+\u001a\u0002H&\"\f\b\u0000\u0010'*\u00020\u0002*\u0002H&\"\u0004\b\u0001\u0010&*\u0002H'2\f\u0010(\u001a\b\u0012\u0004\u0012\u0002H&0)H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010*\u001a&\u0010,\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u00142\b\b\u0002\u0010\u001f\u001a\u00020\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a;\u0010,\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u00022\u0006\u0010\u001f\u001a\u00020\u00062\u0006\u0010-\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010 \u001a\u00020\u0010H\u0002\u00a2\u0006\u0002\b.\u001a&\u0010,\u001a\u00020\u0006*\u00020\u00022\u0006\u0010/\u001a\u00020\r2\b\b\u0002\u0010\u001f\u001a\u00020\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a&\u00100\u001a\u00020\u0006*\u00020\u00022\u0006\u00101\u001a\u0002022\b\b\u0002\u0010\u001f\u001a\u00020\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a,\u00100\u001a\u00020\u0006*\u00020\u00022\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\r0\u001e2\b\b\u0002\u0010\u001f\u001a\u00020\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a\r\u00103\u001a\u00020\u0010*\u00020\u0002H\u0087\b\u001a\r\u00104\u001a\u00020\u0010*\u00020\u0002H\u0087\b\u001a\r\u00105\u001a\u00020\u0010*\u00020\u0002H\u0087\b\u001a \u00106\u001a\u00020\u0010*\u0004\u0018\u00010\u0002H\u0087\b\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0001\u001a\u0004\b\u0003\u0010\u0000\u001a \u00107\u001a\u00020\u0010*\u0004\u0018\u00010\u0002H\u0087\b\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0001\u001a\u0004\b\u0003\u0010\u0000\u001a\r\u00108\u001a\u000209*\u00020\u0002H\u0086\u0002\u001a&\u0010:\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u00142\b\b\u0002\u0010\u001f\u001a\u00020\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a&\u0010:\u001a\u00020\u0006*\u00020\u00022\u0006\u0010/\u001a\u00020\r2\b\b\u0002\u0010\u001f\u001a\u00020\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a&\u0010;\u001a\u00020\u0006*\u00020\u00022\u0006\u00101\u001a\u0002022\b\b\u0002\u0010\u001f\u001a\u00020\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a,\u0010;\u001a\u00020\u0006*\u00020\u00022\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\r0\u001e2\b\b\u0002\u0010\u001f\u001a\u00020\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a\u0010\u0010<\u001a\b\u0012\u0004\u0012\u00020\r0=*\u00020\u0002\u001a\u0010\u0010>\u001a\b\u0012\u0004\u0012\u00020\r0?*\u00020\u0002\u001a\u0015\u0010@\u001a\u00020\u0010*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0016H\u0087\f\u001a\u000f\u0010A\u001a\u00020\r*\u0004\u0018\u00010\rH\u0087\b\u001a\u001c\u0010B\u001a\u00020\u0002*\u00020\u00022\u0006\u0010C\u001a\u00020\u00062\b\b\u0002\u0010D\u001a\u00020\u0014\u001a\u001c\u0010B\u001a\u00020\r*\u00020\r2\u0006\u0010C\u001a\u00020\u00062\b\b\u0002\u0010D\u001a\u00020\u0014\u001a\u001c\u0010E\u001a\u00020\u0002*\u00020\u00022\u0006\u0010C\u001a\u00020\u00062\b\b\u0002\u0010D\u001a\u00020\u0014\u001a\u001c\u0010E\u001a\u00020\r*\u00020\r2\u0006\u0010C\u001a\u00020\u00062\b\b\u0002\u0010D\u001a\u00020\u0014\u001aG\u0010F\u001a\b\u0012\u0004\u0012\u00020\u00010=*\u00020\u00022\u000e\u0010G\u001a\n\u0012\u0006\b\u0001\u0012\u00020\r0H2\b\b\u0002\u0010\u001f\u001a\u00020\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u000b\u001a\u00020\u0006H\u0002\u00a2\u0006\u0004\bI\u0010J\u001a=\u0010F\u001a\b\u0012\u0004\u0012\u00020\u00010=*\u00020\u00022\u0006\u0010G\u001a\u0002022\b\b\u0002\u0010\u001f\u001a\u00020\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u000b\u001a\u00020\u0006H\u0002\u00a2\u0006\u0002\bI\u001a4\u0010K\u001a\u00020\u0010*\u00020\u00022\u0006\u0010L\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u00022\u0006\u0010M\u001a\u00020\u00062\u0006\u0010C\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u0010H\u0000\u001a\u0012\u0010N\u001a\u00020\u0002*\u00020\u00022\u0006\u0010O\u001a\u00020\u0002\u001a\u0012\u0010N\u001a\u00020\r*\u00020\r2\u0006\u0010O\u001a\u00020\u0002\u001a\u001a\u0010P\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u001f\u001a\u00020\u00062\u0006\u0010-\u001a\u00020\u0006\u001a\u0012\u0010P\u001a\u00020\u0002*\u00020\u00022\u0006\u0010Q\u001a\u00020\u0001\u001a\u001d\u0010P\u001a\u00020\r*\u00020\r2\u0006\u0010\u001f\u001a\u00020\u00062\u0006\u0010-\u001a\u00020\u0006H\u0087\b\u001a\u0015\u0010P\u001a\u00020\r*\u00020\r2\u0006\u0010Q\u001a\u00020\u0001H\u0087\b\u001a\u0012\u0010R\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u0002\u001a\u0012\u0010R\u001a\u00020\r*\u00020\r2\u0006\u0010\u001a\u001a\u00020\u0002\u001a\u0012\u0010S\u001a\u00020\u0002*\u00020\u00022\u0006\u0010T\u001a\u00020\u0002\u001a\u001a\u0010S\u001a\u00020\u0002*\u00020\u00022\u0006\u0010O\u001a\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u0002\u001a\u0012\u0010S\u001a\u00020\r*\u00020\r2\u0006\u0010T\u001a\u00020\u0002\u001a\u001a\u0010S\u001a\u00020\r*\u00020\r2\u0006\u0010O\u001a\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u0002\u001a.\u0010U\u001a\u00020\r*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u00162\u0014\b\b\u0010V\u001a\u000e\u0012\u0004\u0012\u00020X\u0012\u0004\u0012\u00020\u00020WH\u0087\b\u00f8\u0001\u0000\u001a\u001d\u0010U\u001a\u00020\r*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010Y\u001a\u00020\rH\u0087\b\u001a$\u0010Z\u001a\u00020\r*\u00020\r2\u0006\u0010T\u001a\u00020\u00142\u0006\u0010Y\u001a\u00020\r2\b\b\u0002\u0010[\u001a\u00020\r\u001a$\u0010Z\u001a\u00020\r*\u00020\r2\u0006\u0010T\u001a\u00020\r2\u0006\u0010Y\u001a\u00020\r2\b\b\u0002\u0010[\u001a\u00020\r\u001a$\u0010\\\u001a\u00020\r*\u00020\r2\u0006\u0010T\u001a\u00020\u00142\u0006\u0010Y\u001a\u00020\r2\b\b\u0002\u0010[\u001a\u00020\r\u001a$\u0010\\\u001a\u00020\r*\u00020\r2\u0006\u0010T\u001a\u00020\r2\u0006\u0010Y\u001a\u00020\r2\b\b\u0002\u0010[\u001a\u00020\r\u001a$\u0010]\u001a\u00020\r*\u00020\r2\u0006\u0010T\u001a\u00020\u00142\u0006\u0010Y\u001a\u00020\r2\b\b\u0002\u0010[\u001a\u00020\r\u001a$\u0010]\u001a\u00020\r*\u00020\r2\u0006\u0010T\u001a\u00020\r2\u0006\u0010Y\u001a\u00020\r2\b\b\u0002\u0010[\u001a\u00020\r\u001a$\u0010^\u001a\u00020\r*\u00020\r2\u0006\u0010T\u001a\u00020\u00142\u0006\u0010Y\u001a\u00020\r2\b\b\u0002\u0010[\u001a\u00020\r\u001a$\u0010^\u001a\u00020\r*\u00020\r2\u0006\u0010T\u001a\u00020\r2\u0006\u0010Y\u001a\u00020\r2\b\b\u0002\u0010[\u001a\u00020\r\u001a\u001d\u0010_\u001a\u00020\r*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010Y\u001a\u00020\rH\u0087\b\u001a)\u0010`\u001a\u00020\r*\u00020\r2\u0012\u0010V\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00140WH\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0002\ba\u001a)\u0010`\u001a\u00020\r*\u00020\r2\u0012\u0010V\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00020WH\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0002\bb\u001a\"\u0010c\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u001f\u001a\u00020\u00062\u0006\u0010-\u001a\u00020\u00062\u0006\u0010Y\u001a\u00020\u0002\u001a\u001a\u0010c\u001a\u00020\u0002*\u00020\u00022\u0006\u0010Q\u001a\u00020\u00012\u0006\u0010Y\u001a\u00020\u0002\u001a%\u0010c\u001a\u00020\r*\u00020\r2\u0006\u0010\u001f\u001a\u00020\u00062\u0006\u0010-\u001a\u00020\u00062\u0006\u0010Y\u001a\u00020\u0002H\u0087\b\u001a\u001d\u0010c\u001a\u00020\r*\u00020\r2\u0006\u0010Q\u001a\u00020\u00012\u0006\u0010Y\u001a\u00020\u0002H\u0087\b\u001a=\u0010d\u001a\b\u0012\u0004\u0012\u00020\r0?*\u00020\u00022\u0012\u0010G\u001a\n\u0012\u0006\b\u0001\u0012\u00020\r0H\"\u00020\r2\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u000b\u001a\u00020\u0006\u00a2\u0006\u0002\u0010e\u001a0\u0010d\u001a\b\u0012\u0004\u0012\u00020\r0?*\u00020\u00022\n\u0010G\u001a\u000202\"\u00020\u00142\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u000b\u001a\u00020\u0006\u001a/\u0010d\u001a\b\u0012\u0004\u0012\u00020\r0?*\u00020\u00022\u0006\u0010T\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u000b\u001a\u00020\u0006H\u0002\u00a2\u0006\u0002\bf\u001a%\u0010d\u001a\b\u0012\u0004\u0012\u00020\r0?*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u00162\b\b\u0002\u0010\u000b\u001a\u00020\u0006H\u0087\b\u001a=\u0010g\u001a\b\u0012\u0004\u0012\u00020\r0=*\u00020\u00022\u0012\u0010G\u001a\n\u0012\u0006\b\u0001\u0012\u00020\r0H\"\u00020\r2\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u000b\u001a\u00020\u0006\u00a2\u0006\u0002\u0010h\u001a0\u0010g\u001a\b\u0012\u0004\u0012\u00020\r0=*\u00020\u00022\n\u0010G\u001a\u000202\"\u00020\u00142\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u000b\u001a\u00020\u0006\u001a%\u0010g\u001a\b\u0012\u0004\u0012\u00020\r0=*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u00162\b\b\u0002\u0010\u000b\u001a\u00020\u0006H\u0087\b\u001a\u001c\u0010i\u001a\u00020\u0010*\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u00142\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a\u001c\u0010i\u001a\u00020\u0010*\u00020\u00022\u0006\u0010O\u001a\u00020\u00022\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a$\u0010i\u001a\u00020\u0010*\u00020\u00022\u0006\u0010O\u001a\u00020\u00022\u0006\u0010\u001f\u001a\u00020\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a\u0012\u0010j\u001a\u00020\u0002*\u00020\u00022\u0006\u0010Q\u001a\u00020\u0001\u001a\u001d\u0010j\u001a\u00020\u0002*\u00020\r2\u0006\u0010k\u001a\u00020\u00062\u0006\u0010l\u001a\u00020\u0006H\u0087\b\u001a\u001f\u0010m\u001a\u00020\r*\u00020\u00022\u0006\u0010\u001f\u001a\u00020\u00062\b\b\u0002\u0010-\u001a\u00020\u0006H\u0087\b\u001a\u0012\u0010m\u001a\u00020\r*\u00020\u00022\u0006\u0010Q\u001a\u00020\u0001\u001a\u0012\u0010m\u001a\u00020\r*\u00020\r2\u0006\u0010Q\u001a\u00020\u0001\u001a\u001c\u0010n\u001a\u00020\r*\u00020\r2\u0006\u0010T\u001a\u00020\u00142\b\b\u0002\u0010[\u001a\u00020\r\u001a\u001c\u0010n\u001a\u00020\r*\u00020\r2\u0006\u0010T\u001a\u00020\r2\b\b\u0002\u0010[\u001a\u00020\r\u001a\u001c\u0010o\u001a\u00020\r*\u00020\r2\u0006\u0010T\u001a\u00020\u00142\b\b\u0002\u0010[\u001a\u00020\r\u001a\u001c\u0010o\u001a\u00020\r*\u00020\r2\u0006\u0010T\u001a\u00020\r2\b\b\u0002\u0010[\u001a\u00020\r\u001a\u001c\u0010p\u001a\u00020\r*\u00020\r2\u0006\u0010T\u001a\u00020\u00142\b\b\u0002\u0010[\u001a\u00020\r\u001a\u001c\u0010p\u001a\u00020\r*\u00020\r2\u0006\u0010T\u001a\u00020\r2\b\b\u0002\u0010[\u001a\u00020\r\u001a\u001c\u0010q\u001a\u00020\r*\u00020\r2\u0006\u0010T\u001a\u00020\u00142\b\b\u0002\u0010[\u001a\u00020\r\u001a\u001c\u0010q\u001a\u00020\r*\u00020\r2\u0006\u0010T\u001a\u00020\r2\b\b\u0002\u0010[\u001a\u00020\r\u001a\f\u0010r\u001a\u00020\u0010*\u00020\rH\u0007\u001a\u0013\u0010s\u001a\u0004\u0018\u00010\u0010*\u00020\rH\u0007\u00a2\u0006\u0002\u0010t\u001a\n\u0010u\u001a\u00020\u0002*\u00020\u0002\u001a$\u0010u\u001a\u00020\u0002*\u00020\u00022\u0012\u0010v\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00100WH\u0086\b\u00f8\u0001\u0000\u001a\u0016\u0010u\u001a\u00020\u0002*\u00020\u00022\n\u00101\u001a\u000202\"\u00020\u0014\u001a\r\u0010u\u001a\u00020\r*\u00020\rH\u0087\b\u001a$\u0010u\u001a\u00020\r*\u00020\r2\u0012\u0010v\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00100WH\u0086\b\u00f8\u0001\u0000\u001a\u0016\u0010u\u001a\u00020\r*\u00020\r2\n\u00101\u001a\u000202\"\u00020\u0014\u001a\n\u0010w\u001a\u00020\u0002*\u00020\u0002\u001a$\u0010w\u001a\u00020\u0002*\u00020\u00022\u0012\u0010v\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00100WH\u0086\b\u00f8\u0001\u0000\u001a\u0016\u0010w\u001a\u00020\u0002*\u00020\u00022\n\u00101\u001a\u000202\"\u00020\u0014\u001a\r\u0010w\u001a\u00020\r*\u00020\rH\u0087\b\u001a$\u0010w\u001a\u00020\r*\u00020\r2\u0012\u0010v\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00100WH\u0086\b\u00f8\u0001\u0000\u001a\u0016\u0010w\u001a\u00020\r*\u00020\r2\n\u00101\u001a\u000202\"\u00020\u0014\u001a\n\u0010x\u001a\u00020\u0002*\u00020\u0002\u001a$\u0010x\u001a\u00020\u0002*\u00020\u00022\u0012\u0010v\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00100WH\u0086\b\u00f8\u0001\u0000\u001a\u0016\u0010x\u001a\u00020\u0002*\u00020\u00022\n\u00101\u001a\u000202\"\u00020\u0014\u001a\r\u0010x\u001a\u00020\r*\u00020\rH\u0087\b\u001a$\u0010x\u001a\u00020\r*\u00020\r2\u0012\u0010v\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00100WH\u0086\b\u00f8\u0001\u0000\u001a\u0016\u0010x\u001a\u00020\r*\u00020\r2\n\u00101\u001a\u000202\"\u00020\u0014\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0005\u001a\u00020\u0006*\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\b\u0082\u0002\u0007\n\u0005\b\u009920\u0001\u00a8\u0006y"}, d2={"indices", "Lkotlin/ranges/IntRange;", "", "getIndices", "(Ljava/lang/CharSequence;)Lkotlin/ranges/IntRange;", "lastIndex", "", "getLastIndex", "(Ljava/lang/CharSequence;)I", "requireNonNegativeLimit", "", "limit", "commonPrefixWith", "", "other", "ignoreCase", "", "commonSuffixWith", "contains", "char", "", "regex", "Lkotlin/text/Regex;", "contentEqualsIgnoreCaseImpl", "contentEqualsImpl", "endsWith", "suffix", "findAnyOf", "Lkotlin/Pair;", "strings", "", "startIndex", "last", "findAnyOf$StringsKt__StringsKt", "findLastAnyOf", "hasSurrogatePairAt", "index", "ifBlank", "R", "C", "defaultValue", "Lkotlin/Function0;", "(Ljava/lang/CharSequence;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "ifEmpty", "indexOf", "endIndex", "indexOf$StringsKt__StringsKt", "string", "indexOfAny", "chars", "", "isEmpty", "isNotBlank", "isNotEmpty", "isNullOrBlank", "isNullOrEmpty", "iterator", "Lkotlin/collections/CharIterator;", "lastIndexOf", "lastIndexOfAny", "lineSequence", "Lkotlin/sequences/Sequence;", "lines", "", "matches", "orEmpty", "padEnd", "length", "padChar", "padStart", "rangesDelimitedBy", "delimiters", "", "rangesDelimitedBy$StringsKt__StringsKt", "(Ljava/lang/CharSequence;[Ljava/lang/String;IZI)Lkotlin/sequences/Sequence;", "regionMatchesImpl", "thisOffset", "otherOffset", "removePrefix", "prefix", "removeRange", "range", "removeSuffix", "removeSurrounding", "delimiter", "replace", "transform", "Lkotlin/Function1;", "Lkotlin/text/MatchResult;", "replacement", "replaceAfter", "missingDelimiterValue", "replaceAfterLast", "replaceBefore", "replaceBeforeLast", "replaceFirst", "replaceFirstChar", "replaceFirstCharWithChar", "replaceFirstCharWithCharSequence", "replaceRange", "split", "(Ljava/lang/CharSequence;[Ljava/lang/String;ZI)Ljava/util/List;", "split$StringsKt__StringsKt", "splitToSequence", "(Ljava/lang/CharSequence;[Ljava/lang/String;ZI)Lkotlin/sequences/Sequence;", "startsWith", "subSequence", "start", "end", "substring", "substringAfter", "substringAfterLast", "substringBefore", "substringBeforeLast", "toBooleanStrict", "toBooleanStrictOrNull", "(Ljava/lang/String;)Ljava/lang/Boolean;", "trim", "predicate", "trimEnd", "trimStart", "kotlin-stdlib"}, xs="kotlin/text/StringsKt")
@SourceDebugExtension(value={"SMAP\nStrings.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Strings.kt\nkotlin/text/StringsKt__StringsKt\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,1486:1\n79#1,22:1487\n113#1,5:1509\n130#1,5:1514\n79#1,22:1519\n107#1:1541\n79#1,22:1542\n113#1,5:1564\n124#1:1569\n113#1,5:1570\n130#1,5:1575\n141#1:1580\n130#1,5:1581\n79#1,22:1586\n113#1,5:1608\n130#1,5:1613\n12554#2,2:1618\n12554#2,2:1620\n288#3,2:1622\n288#3,2:1624\n1549#3:1627\n1620#3,3:1628\n1549#3:1631\n1620#3,3:1632\n1#4:1626\n*S KotlinDebug\n*F\n+ 1 Strings.kt\nkotlin/text/StringsKt__StringsKt\n*L\n107#1:1487,22\n124#1:1509,5\n141#1:1514,5\n146#1:1519,22\n151#1:1541\n151#1:1542,22\n156#1:1564,5\n161#1:1569\n161#1:1570,5\n166#1:1575,5\n171#1:1580\n171#1:1581,5\n176#1:1586,22\n187#1:1608,5\n198#1:1613,5\n940#1:1618,2\n964#1:1620,2\n1003#1:1622,2\n1009#1:1624,2\n1309#1:1627\n1309#1:1628,3\n1334#1:1631\n1334#1:1632,3\n*E\n"})
class StringsKt__StringsKt
extends StringsKt__StringsJVMKt {
    @NotNull
    public static final CharSequence trim(@NotNull CharSequence charSequence, @NotNull Function1<? super Character, Boolean> function1) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(function1, "predicate");
        boolean bl = false;
        int n = 0;
        int n2 = charSequence.length() - 1;
        boolean bl2 = false;
        while (n <= n2) {
            int n3 = !bl2 ? n : n2;
            boolean bl3 = function1.invoke(Character.valueOf(charSequence.charAt(n3)));
            if (!bl2) {
                if (!bl3) {
                    bl2 = true;
                    continue;
                }
                ++n;
                continue;
            }
            if (!bl3) break;
            --n2;
        }
        return charSequence.subSequence(n, n2 + 1);
    }

    @NotNull
    public static final String trim(@NotNull String string, @NotNull Function1<? super Character, Boolean> function1) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(function1, "predicate");
        boolean bl = false;
        CharSequence charSequence = string;
        boolean bl2 = false;
        int n = 0;
        int n2 = charSequence.length() - 1;
        boolean bl3 = false;
        while (n <= n2) {
            int n3 = !bl3 ? n : n2;
            boolean bl4 = function1.invoke(Character.valueOf(charSequence.charAt(n3)));
            if (!bl3) {
                if (!bl4) {
                    bl3 = true;
                    continue;
                }
                ++n;
                continue;
            }
            if (!bl4) break;
            --n2;
        }
        return ((Object)charSequence.subSequence(n, n2 + 1)).toString();
    }

    @NotNull
    public static final CharSequence trimStart(@NotNull CharSequence charSequence, @NotNull Function1<? super Character, Boolean> function1) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(function1, "predicate");
        boolean bl = false;
        int n = charSequence.length();
        for (int i = 0; i < n; ++i) {
            if (function1.invoke(Character.valueOf(charSequence.charAt(i))).booleanValue()) continue;
            return charSequence.subSequence(i, charSequence.length());
        }
        return "";
    }

    @NotNull
    public static final String trimStart(@NotNull String string, @NotNull Function1<? super Character, Boolean> function1) {
        CharSequence charSequence;
        block1: {
            Intrinsics.checkNotNullParameter(string, "<this>");
            Intrinsics.checkNotNullParameter(function1, "predicate");
            boolean bl = false;
            CharSequence charSequence2 = string;
            boolean bl2 = false;
            int n = charSequence2.length();
            for (int i = 0; i < n; ++i) {
                if (function1.invoke(Character.valueOf(charSequence2.charAt(i))).booleanValue()) continue;
                charSequence = charSequence2.subSequence(i, charSequence2.length());
                break block1;
            }
            charSequence = "";
        }
        return ((Object)charSequence).toString();
    }

    @NotNull
    public static final CharSequence trimEnd(@NotNull CharSequence charSequence, @NotNull Function1<? super Character, Boolean> function1) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(function1, "predicate");
        boolean bl = false;
        int n = charSequence.length() + -1;
        if (0 <= n) {
            do {
                int n2;
                if (function1.invoke(Character.valueOf(charSequence.charAt(n2 = n--))).booleanValue()) continue;
                return charSequence.subSequence(0, n2 + 1);
            } while (0 <= n);
        }
        return "";
    }

    @NotNull
    public static final String trimEnd(@NotNull String string, @NotNull Function1<? super Character, Boolean> function1) {
        CharSequence charSequence;
        block2: {
            Intrinsics.checkNotNullParameter(string, "<this>");
            Intrinsics.checkNotNullParameter(function1, "predicate");
            boolean bl = false;
            CharSequence charSequence2 = string;
            boolean bl2 = false;
            int n = charSequence2.length() + -1;
            if (0 <= n) {
                do {
                    int n2;
                    if (function1.invoke(Character.valueOf(charSequence2.charAt(n2 = n--))).booleanValue()) continue;
                    charSequence = charSequence2.subSequence(0, n2 + 1);
                    break block2;
                } while (0 <= n);
            }
            charSequence = "";
        }
        return ((Object)charSequence).toString();
    }

    @NotNull
    public static final CharSequence trim(@NotNull CharSequence charSequence, @NotNull char ... cArray) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(cArray, "chars");
        CharSequence charSequence2 = charSequence;
        boolean bl = false;
        int n = 0;
        int n2 = charSequence2.length() - 1;
        boolean bl2 = false;
        while (n <= n2) {
            int n3 = !bl2 ? n : n2;
            char c = charSequence2.charAt(n3);
            boolean bl3 = false;
            c = (char)(ArraysKt.contains(cArray, c) ? 1 : 0);
            if (!bl2) {
                if (c == '\u0000') {
                    bl2 = true;
                    continue;
                }
                ++n;
                continue;
            }
            if (c == '\u0000') break;
            --n2;
        }
        return charSequence2.subSequence(n, n2 + 1);
    }

    @NotNull
    public static final String trim(@NotNull String string, @NotNull char ... cArray) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(cArray, "chars");
        String string2 = string;
        boolean bl = false;
        CharSequence charSequence = string2;
        boolean bl2 = false;
        int n = 0;
        int n2 = charSequence.length() - 1;
        boolean bl3 = false;
        while (n <= n2) {
            int n3 = !bl3 ? n : n2;
            char c = charSequence.charAt(n3);
            boolean bl4 = false;
            c = (char)(ArraysKt.contains(cArray, c) ? 1 : 0);
            if (!bl3) {
                if (c == '\u0000') {
                    bl3 = true;
                    continue;
                }
                ++n;
                continue;
            }
            if (c == '\u0000') break;
            --n2;
        }
        return ((Object)charSequence.subSequence(n, n2 + 1)).toString();
    }

    @NotNull
    public static final CharSequence trimStart(@NotNull CharSequence charSequence, @NotNull char ... cArray) {
        CharSequence charSequence2;
        block1: {
            Intrinsics.checkNotNullParameter(charSequence, "<this>");
            Intrinsics.checkNotNullParameter(cArray, "chars");
            CharSequence charSequence3 = charSequence;
            boolean bl = false;
            int n = charSequence3.length();
            for (int i = 0; i < n; ++i) {
                char c = charSequence3.charAt(i);
                boolean bl2 = false;
                if (ArraysKt.contains(cArray, c)) continue;
                charSequence2 = charSequence3.subSequence(i, charSequence3.length());
                break block1;
            }
            charSequence2 = "";
        }
        return charSequence2;
    }

    @NotNull
    public static final String trimStart(@NotNull String string, @NotNull char ... cArray) {
        CharSequence charSequence;
        block1: {
            Intrinsics.checkNotNullParameter(string, "<this>");
            Intrinsics.checkNotNullParameter(cArray, "chars");
            String string2 = string;
            boolean bl = false;
            CharSequence charSequence2 = string2;
            boolean bl2 = false;
            int n = charSequence2.length();
            for (int i = 0; i < n; ++i) {
                char c = charSequence2.charAt(i);
                boolean bl3 = false;
                if (ArraysKt.contains(cArray, c)) continue;
                charSequence = charSequence2.subSequence(i, charSequence2.length());
                break block1;
            }
            charSequence = "";
        }
        return ((Object)charSequence).toString();
    }

    @NotNull
    public static final CharSequence trimEnd(@NotNull CharSequence charSequence, @NotNull char ... cArray) {
        CharSequence charSequence2;
        block2: {
            Intrinsics.checkNotNullParameter(charSequence, "<this>");
            Intrinsics.checkNotNullParameter(cArray, "chars");
            CharSequence charSequence3 = charSequence;
            boolean bl = false;
            int n = charSequence3.length() + -1;
            if (0 <= n) {
                do {
                    int n2 = n--;
                    char c = charSequence3.charAt(n2);
                    boolean bl2 = false;
                    if (ArraysKt.contains(cArray, c)) continue;
                    charSequence2 = charSequence3.subSequence(0, n2 + 1);
                    break block2;
                } while (0 <= n);
            }
            charSequence2 = "";
        }
        return charSequence2;
    }

    @NotNull
    public static final String trimEnd(@NotNull String string, @NotNull char ... cArray) {
        CharSequence charSequence;
        block2: {
            Intrinsics.checkNotNullParameter(string, "<this>");
            Intrinsics.checkNotNullParameter(cArray, "chars");
            String string2 = string;
            boolean bl = false;
            CharSequence charSequence2 = string2;
            boolean bl2 = false;
            int n = charSequence2.length() + -1;
            if (0 <= n) {
                do {
                    int n2 = n--;
                    char c = charSequence2.charAt(n2);
                    boolean bl3 = false;
                    if (ArraysKt.contains(cArray, c)) continue;
                    charSequence = charSequence2.subSequence(0, n2 + 1);
                    break block2;
                } while (0 <= n);
            }
            charSequence = "";
        }
        return ((Object)charSequence).toString();
    }

    @NotNull
    public static final CharSequence trim(@NotNull CharSequence charSequence) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        CharSequence charSequence2 = charSequence;
        boolean bl = false;
        int n = 0;
        int n2 = charSequence2.length() - 1;
        boolean bl2 = false;
        while (n <= n2) {
            int n3 = !bl2 ? n : n2;
            char c = charSequence2.charAt(n3);
            boolean bl3 = false;
            c = (char)(CharsKt.isWhitespace(c) ? 1 : 0);
            if (!bl2) {
                if (c == '\u0000') {
                    bl2 = true;
                    continue;
                }
                ++n;
                continue;
            }
            if (c == '\u0000') break;
            --n2;
        }
        return charSequence2.subSequence(n, n2 + 1);
    }

    @InlineOnly
    private static final String trim(String string) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        return ((Object)StringsKt.trim((CharSequence)string)).toString();
    }

    @NotNull
    public static final CharSequence trimStart(@NotNull CharSequence charSequence) {
        CharSequence charSequence2;
        block1: {
            Intrinsics.checkNotNullParameter(charSequence, "<this>");
            CharSequence charSequence3 = charSequence;
            boolean bl = false;
            int n = charSequence3.length();
            for (int i = 0; i < n; ++i) {
                char c = charSequence3.charAt(i);
                boolean bl2 = false;
                if (CharsKt.isWhitespace(c)) continue;
                charSequence2 = charSequence3.subSequence(i, charSequence3.length());
                break block1;
            }
            charSequence2 = "";
        }
        return charSequence2;
    }

    @InlineOnly
    private static final String trimStart(String string) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        return ((Object)StringsKt.trimStart((CharSequence)string)).toString();
    }

    @NotNull
    public static final CharSequence trimEnd(@NotNull CharSequence charSequence) {
        CharSequence charSequence2;
        block2: {
            Intrinsics.checkNotNullParameter(charSequence, "<this>");
            CharSequence charSequence3 = charSequence;
            boolean bl = false;
            int n = charSequence3.length() + -1;
            if (0 <= n) {
                do {
                    int n2 = n--;
                    char c = charSequence3.charAt(n2);
                    boolean bl2 = false;
                    if (CharsKt.isWhitespace(c)) continue;
                    charSequence2 = charSequence3.subSequence(0, n2 + 1);
                    break block2;
                } while (0 <= n);
            }
            charSequence2 = "";
        }
        return charSequence2;
    }

    @InlineOnly
    private static final String trimEnd(String string) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        return ((Object)StringsKt.trimEnd((CharSequence)string)).toString();
    }

    @NotNull
    public static final CharSequence padStart(@NotNull CharSequence charSequence, int n, char c) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        if (n < 0) {
            throw new IllegalArgumentException("Desired length " + n + " is less than zero.");
        }
        if (n <= charSequence.length()) {
            return charSequence.subSequence(0, charSequence.length());
        }
        StringBuilder stringBuilder = new StringBuilder(n);
        IntIterator intIterator = new IntRange(1, n - charSequence.length()).iterator();
        while (intIterator.hasNext()) {
            int n2 = intIterator.nextInt();
            stringBuilder.append(c);
        }
        stringBuilder.append(charSequence);
        return stringBuilder;
    }

    public static CharSequence padStart$default(CharSequence charSequence, int n, char c, int n2, Object object) {
        if ((n2 & 2) != 0) {
            c = (char)32;
        }
        return StringsKt.padStart(charSequence, n, c);
    }

    @NotNull
    public static final String padStart(@NotNull String string, int n, char c) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        return ((Object)StringsKt.padStart((CharSequence)string, n, c)).toString();
    }

    public static String padStart$default(String string, int n, char c, int n2, Object object) {
        if ((n2 & 2) != 0) {
            c = (char)32;
        }
        return StringsKt.padStart(string, n, c);
    }

    @NotNull
    public static final CharSequence padEnd(@NotNull CharSequence charSequence, int n, char c) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        if (n < 0) {
            throw new IllegalArgumentException("Desired length " + n + " is less than zero.");
        }
        if (n <= charSequence.length()) {
            return charSequence.subSequence(0, charSequence.length());
        }
        StringBuilder stringBuilder = new StringBuilder(n);
        stringBuilder.append(charSequence);
        IntIterator intIterator = new IntRange(1, n - charSequence.length()).iterator();
        while (intIterator.hasNext()) {
            int n2 = intIterator.nextInt();
            stringBuilder.append(c);
        }
        return stringBuilder;
    }

    public static CharSequence padEnd$default(CharSequence charSequence, int n, char c, int n2, Object object) {
        if ((n2 & 2) != 0) {
            c = (char)32;
        }
        return StringsKt.padEnd(charSequence, n, c);
    }

    @NotNull
    public static final String padEnd(@NotNull String string, int n, char c) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        return ((Object)StringsKt.padEnd((CharSequence)string, n, c)).toString();
    }

    public static String padEnd$default(String string, int n, char c, int n2, Object object) {
        if ((n2 & 2) != 0) {
            c = (char)32;
        }
        return StringsKt.padEnd(string, n, c);
    }

    @InlineOnly
    private static final boolean isNullOrEmpty(CharSequence charSequence) {
        return charSequence == null || charSequence.length() == 0;
    }

    @InlineOnly
    private static final boolean isEmpty(CharSequence charSequence) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        return charSequence.length() == 0;
    }

    @InlineOnly
    private static final boolean isNotEmpty(CharSequence charSequence) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        return charSequence.length() > 0;
    }

    @InlineOnly
    private static final boolean isNotBlank(CharSequence charSequence) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        return !StringsKt.isBlank(charSequence);
    }

    @InlineOnly
    private static final boolean isNullOrBlank(CharSequence charSequence) {
        return charSequence == null || StringsKt.isBlank(charSequence);
    }

    @NotNull
    public static final CharIterator iterator(@NotNull CharSequence charSequence) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        return new CharIterator(charSequence){
            private int index;
            final CharSequence $this_iterator;
            {
                this.$this_iterator = charSequence;
            }

            public char nextChar() {
                int n = this.index;
                this.index = n + 1;
                return this.$this_iterator.charAt(n);
            }

            public boolean hasNext() {
                return this.index < this.$this_iterator.length();
            }
        };
    }

    @InlineOnly
    private static final String orEmpty(String string) {
        String string2 = string;
        if (string2 == null) {
            string2 = "";
        }
        return string2;
    }

    @SinceKotlin(version="1.3")
    @InlineOnly
    private static final <C extends CharSequence & R, R> R ifEmpty(C c, Function0<? extends R> function0) {
        Intrinsics.checkNotNullParameter(function0, "defaultValue");
        return (R)(c.length() == 0 ? function0.invoke() : c);
    }

    @SinceKotlin(version="1.3")
    @InlineOnly
    private static final <C extends CharSequence & R, R> R ifBlank(C c, Function0<? extends R> function0) {
        Intrinsics.checkNotNullParameter(function0, "defaultValue");
        return (R)(StringsKt.isBlank(c) ? function0.invoke() : c);
    }

    @NotNull
    public static final IntRange getIndices(@NotNull CharSequence charSequence) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        return new IntRange(0, charSequence.length() - 1);
    }

    public static final int getLastIndex(@NotNull CharSequence charSequence) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        return charSequence.length() - 1;
    }

    public static final boolean hasSurrogatePairAt(@NotNull CharSequence charSequence, int n) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        return new IntRange(0, charSequence.length() - 2).contains(n) && Character.isHighSurrogate(charSequence.charAt(n)) && Character.isLowSurrogate(charSequence.charAt(n + 1));
    }

    @NotNull
    public static final String substring(@NotNull String string, @NotNull IntRange intRange) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(intRange, "range");
        String string2 = string;
        int n = intRange.getStart();
        int n2 = intRange.getEndInclusive() + 1;
        String string3 = string2.substring(n, n2);
        Intrinsics.checkNotNullExpressionValue(string3, "this as java.lang.String\u2026ing(startIndex, endIndex)");
        return string3;
    }

    @NotNull
    public static final CharSequence subSequence(@NotNull CharSequence charSequence, @NotNull IntRange intRange) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(intRange, "range");
        return charSequence.subSequence(intRange.getStart(), intRange.getEndInclusive() + 1);
    }

    @Deprecated(message="Use parameters named startIndex and endIndex.", replaceWith=@ReplaceWith(expression="subSequence(startIndex = start, endIndex = end)", imports={}))
    @InlineOnly
    private static final CharSequence subSequence(String string, int n, int n2) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        return string.subSequence(n, n2);
    }

    @InlineOnly
    private static final String substring(CharSequence charSequence, int n, int n2) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        return ((Object)charSequence.subSequence(n, n2)).toString();
    }

    static String substring$default(CharSequence charSequence, int n, int n2, int n3, Object object) {
        if ((n3 & 2) != 0) {
            n2 = charSequence.length();
        }
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        return ((Object)charSequence.subSequence(n, n2)).toString();
    }

    @NotNull
    public static final String substring(@NotNull CharSequence charSequence, @NotNull IntRange intRange) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(intRange, "range");
        return ((Object)charSequence.subSequence(intRange.getStart(), intRange.getEndInclusive() + 1)).toString();
    }

    @NotNull
    public static final String substringBefore(@NotNull String string, char c, @NotNull String string2) {
        String string3;
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(string2, "missingDelimiterValue");
        int n = StringsKt.indexOf$default((CharSequence)string, c, 0, false, 6, null);
        if (n == -1) {
            string3 = string2;
        } else {
            String string4 = string;
            int n2 = 0;
            String string5 = string4.substring(n2, n);
            string3 = string5;
            Intrinsics.checkNotNullExpressionValue(string5, "this as java.lang.String\u2026ing(startIndex, endIndex)");
        }
        return string3;
    }

    public static String substringBefore$default(String string, char c, String string2, int n, Object object) {
        if ((n & 2) != 0) {
            string2 = string;
        }
        return StringsKt.substringBefore(string, c, string2);
    }

    @NotNull
    public static final String substringBefore(@NotNull String string, @NotNull String string2, @NotNull String string3) {
        String string4;
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(string2, "delimiter");
        Intrinsics.checkNotNullParameter(string3, "missingDelimiterValue");
        int n = StringsKt.indexOf$default((CharSequence)string, string2, 0, false, 6, null);
        if (n == -1) {
            string4 = string3;
        } else {
            String string5 = string;
            int n2 = 0;
            String string6 = string5.substring(n2, n);
            string4 = string6;
            Intrinsics.checkNotNullExpressionValue(string6, "this as java.lang.String\u2026ing(startIndex, endIndex)");
        }
        return string4;
    }

    public static String substringBefore$default(String string, String string2, String string3, int n, Object object) {
        if ((n & 2) != 0) {
            string3 = string;
        }
        return StringsKt.substringBefore(string, string2, string3);
    }

    @NotNull
    public static final String substringAfter(@NotNull String string, char c, @NotNull String string2) {
        String string3;
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(string2, "missingDelimiterValue");
        int n = StringsKt.indexOf$default((CharSequence)string, c, 0, false, 6, null);
        if (n == -1) {
            string3 = string2;
        } else {
            String string4 = string;
            int n2 = n + 1;
            int n3 = string.length();
            String string5 = string4.substring(n2, n3);
            string3 = string5;
            Intrinsics.checkNotNullExpressionValue(string5, "this as java.lang.String\u2026ing(startIndex, endIndex)");
        }
        return string3;
    }

    public static String substringAfter$default(String string, char c, String string2, int n, Object object) {
        if ((n & 2) != 0) {
            string2 = string;
        }
        return StringsKt.substringAfter(string, c, string2);
    }

    @NotNull
    public static final String substringAfter(@NotNull String string, @NotNull String string2, @NotNull String string3) {
        String string4;
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(string2, "delimiter");
        Intrinsics.checkNotNullParameter(string3, "missingDelimiterValue");
        int n = StringsKt.indexOf$default((CharSequence)string, string2, 0, false, 6, null);
        if (n == -1) {
            string4 = string3;
        } else {
            String string5 = string;
            int n2 = n + string2.length();
            int n3 = string.length();
            String string6 = string5.substring(n2, n3);
            string4 = string6;
            Intrinsics.checkNotNullExpressionValue(string6, "this as java.lang.String\u2026ing(startIndex, endIndex)");
        }
        return string4;
    }

    public static String substringAfter$default(String string, String string2, String string3, int n, Object object) {
        if ((n & 2) != 0) {
            string3 = string;
        }
        return StringsKt.substringAfter(string, string2, string3);
    }

    @NotNull
    public static final String substringBeforeLast(@NotNull String string, char c, @NotNull String string2) {
        String string3;
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(string2, "missingDelimiterValue");
        int n = StringsKt.lastIndexOf$default((CharSequence)string, c, 0, false, 6, null);
        if (n == -1) {
            string3 = string2;
        } else {
            String string4 = string;
            int n2 = 0;
            String string5 = string4.substring(n2, n);
            string3 = string5;
            Intrinsics.checkNotNullExpressionValue(string5, "this as java.lang.String\u2026ing(startIndex, endIndex)");
        }
        return string3;
    }

    public static String substringBeforeLast$default(String string, char c, String string2, int n, Object object) {
        if ((n & 2) != 0) {
            string2 = string;
        }
        return StringsKt.substringBeforeLast(string, c, string2);
    }

    @NotNull
    public static final String substringBeforeLast(@NotNull String string, @NotNull String string2, @NotNull String string3) {
        String string4;
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(string2, "delimiter");
        Intrinsics.checkNotNullParameter(string3, "missingDelimiterValue");
        int n = StringsKt.lastIndexOf$default((CharSequence)string, string2, 0, false, 6, null);
        if (n == -1) {
            string4 = string3;
        } else {
            String string5 = string;
            int n2 = 0;
            String string6 = string5.substring(n2, n);
            string4 = string6;
            Intrinsics.checkNotNullExpressionValue(string6, "this as java.lang.String\u2026ing(startIndex, endIndex)");
        }
        return string4;
    }

    public static String substringBeforeLast$default(String string, String string2, String string3, int n, Object object) {
        if ((n & 2) != 0) {
            string3 = string;
        }
        return StringsKt.substringBeforeLast(string, string2, string3);
    }

    @NotNull
    public static final String substringAfterLast(@NotNull String string, char c, @NotNull String string2) {
        String string3;
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(string2, "missingDelimiterValue");
        int n = StringsKt.lastIndexOf$default((CharSequence)string, c, 0, false, 6, null);
        if (n == -1) {
            string3 = string2;
        } else {
            String string4 = string;
            int n2 = n + 1;
            int n3 = string.length();
            String string5 = string4.substring(n2, n3);
            string3 = string5;
            Intrinsics.checkNotNullExpressionValue(string5, "this as java.lang.String\u2026ing(startIndex, endIndex)");
        }
        return string3;
    }

    public static String substringAfterLast$default(String string, char c, String string2, int n, Object object) {
        if ((n & 2) != 0) {
            string2 = string;
        }
        return StringsKt.substringAfterLast(string, c, string2);
    }

    @NotNull
    public static final String substringAfterLast(@NotNull String string, @NotNull String string2, @NotNull String string3) {
        String string4;
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(string2, "delimiter");
        Intrinsics.checkNotNullParameter(string3, "missingDelimiterValue");
        int n = StringsKt.lastIndexOf$default((CharSequence)string, string2, 0, false, 6, null);
        if (n == -1) {
            string4 = string3;
        } else {
            String string5 = string;
            int n2 = n + string2.length();
            int n3 = string.length();
            String string6 = string5.substring(n2, n3);
            string4 = string6;
            Intrinsics.checkNotNullExpressionValue(string6, "this as java.lang.String\u2026ing(startIndex, endIndex)");
        }
        return string4;
    }

    public static String substringAfterLast$default(String string, String string2, String string3, int n, Object object) {
        if ((n & 2) != 0) {
            string3 = string;
        }
        return StringsKt.substringAfterLast(string, string2, string3);
    }

    @NotNull
    public static final CharSequence replaceRange(@NotNull CharSequence charSequence, int n, int n2, @NotNull CharSequence charSequence2) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(charSequence2, "replacement");
        if (n2 < n) {
            throw new IndexOutOfBoundsException("End index (" + n2 + ") is less than start index (" + n + ").");
        }
        StringBuilder stringBuilder = new StringBuilder();
        Intrinsics.checkNotNullExpressionValue(stringBuilder.append(charSequence, 0, n), "this.append(value, startIndex, endIndex)");
        stringBuilder.append(charSequence2);
        Intrinsics.checkNotNullExpressionValue(stringBuilder.append(charSequence, n2, charSequence.length()), "this.append(value, startIndex, endIndex)");
        return stringBuilder;
    }

    @InlineOnly
    private static final String replaceRange(String string, int n, int n2, CharSequence charSequence) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(charSequence, "replacement");
        return ((Object)StringsKt.replaceRange((CharSequence)string, n, n2, charSequence)).toString();
    }

    @NotNull
    public static final CharSequence replaceRange(@NotNull CharSequence charSequence, @NotNull IntRange intRange, @NotNull CharSequence charSequence2) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(intRange, "range");
        Intrinsics.checkNotNullParameter(charSequence2, "replacement");
        return StringsKt.replaceRange(charSequence, (int)intRange.getStart(), intRange.getEndInclusive() + 1, charSequence2);
    }

    @InlineOnly
    private static final String replaceRange(String string, IntRange intRange, CharSequence charSequence) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(intRange, "range");
        Intrinsics.checkNotNullParameter(charSequence, "replacement");
        return ((Object)StringsKt.replaceRange((CharSequence)string, intRange, charSequence)).toString();
    }

    @NotNull
    public static final CharSequence removeRange(@NotNull CharSequence charSequence, int n, int n2) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        if (n2 < n) {
            throw new IndexOutOfBoundsException("End index (" + n2 + ") is less than start index (" + n + ").");
        }
        if (n2 == n) {
            return charSequence.subSequence(0, charSequence.length());
        }
        StringBuilder stringBuilder = new StringBuilder(charSequence.length() - (n2 - n));
        Intrinsics.checkNotNullExpressionValue(stringBuilder.append(charSequence, 0, n), "this.append(value, startIndex, endIndex)");
        Intrinsics.checkNotNullExpressionValue(stringBuilder.append(charSequence, n2, charSequence.length()), "this.append(value, startIndex, endIndex)");
        return stringBuilder;
    }

    @InlineOnly
    private static final String removeRange(String string, int n, int n2) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        return ((Object)StringsKt.removeRange((CharSequence)string, n, n2)).toString();
    }

    @NotNull
    public static final CharSequence removeRange(@NotNull CharSequence charSequence, @NotNull IntRange intRange) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(intRange, "range");
        return StringsKt.removeRange(charSequence, (int)intRange.getStart(), intRange.getEndInclusive() + 1);
    }

    @InlineOnly
    private static final String removeRange(String string, IntRange intRange) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(intRange, "range");
        return ((Object)StringsKt.removeRange((CharSequence)string, intRange)).toString();
    }

    @NotNull
    public static final CharSequence removePrefix(@NotNull CharSequence charSequence, @NotNull CharSequence charSequence2) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(charSequence2, "prefix");
        if (StringsKt.startsWith$default(charSequence, charSequence2, false, 2, null)) {
            return charSequence.subSequence(charSequence2.length(), charSequence.length());
        }
        return charSequence.subSequence(0, charSequence.length());
    }

    @NotNull
    public static final String removePrefix(@NotNull String string, @NotNull CharSequence charSequence) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(charSequence, "prefix");
        if (StringsKt.startsWith$default((CharSequence)string, charSequence, false, 2, null)) {
            String string2 = string;
            int n = charSequence.length();
            String string3 = string2.substring(n);
            Intrinsics.checkNotNullExpressionValue(string3, "this as java.lang.String).substring(startIndex)");
            return string3;
        }
        return string;
    }

    @NotNull
    public static final CharSequence removeSuffix(@NotNull CharSequence charSequence, @NotNull CharSequence charSequence2) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(charSequence2, "suffix");
        if (StringsKt.endsWith$default(charSequence, charSequence2, false, 2, null)) {
            return charSequence.subSequence(0, charSequence.length() - charSequence2.length());
        }
        return charSequence.subSequence(0, charSequence.length());
    }

    @NotNull
    public static final String removeSuffix(@NotNull String string, @NotNull CharSequence charSequence) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(charSequence, "suffix");
        if (StringsKt.endsWith$default((CharSequence)string, charSequence, false, 2, null)) {
            String string2 = string;
            int n = 0;
            int n2 = string.length() - charSequence.length();
            String string3 = string2.substring(n, n2);
            Intrinsics.checkNotNullExpressionValue(string3, "this as java.lang.String\u2026ing(startIndex, endIndex)");
            return string3;
        }
        return string;
    }

    @NotNull
    public static final CharSequence removeSurrounding(@NotNull CharSequence charSequence, @NotNull CharSequence charSequence2, @NotNull CharSequence charSequence3) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(charSequence2, "prefix");
        Intrinsics.checkNotNullParameter(charSequence3, "suffix");
        if (charSequence.length() >= charSequence2.length() + charSequence3.length() && StringsKt.startsWith$default(charSequence, charSequence2, false, 2, null) && StringsKt.endsWith$default(charSequence, charSequence3, false, 2, null)) {
            return charSequence.subSequence(charSequence2.length(), charSequence.length() - charSequence3.length());
        }
        return charSequence.subSequence(0, charSequence.length());
    }

    @NotNull
    public static final String removeSurrounding(@NotNull String string, @NotNull CharSequence charSequence, @NotNull CharSequence charSequence2) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(charSequence, "prefix");
        Intrinsics.checkNotNullParameter(charSequence2, "suffix");
        if (string.length() >= charSequence.length() + charSequence2.length() && StringsKt.startsWith$default((CharSequence)string, charSequence, false, 2, null) && StringsKt.endsWith$default((CharSequence)string, charSequence2, false, 2, null)) {
            String string2 = string;
            int n = charSequence.length();
            int n2 = string.length() - charSequence2.length();
            String string3 = string2.substring(n, n2);
            Intrinsics.checkNotNullExpressionValue(string3, "this as java.lang.String\u2026ing(startIndex, endIndex)");
            return string3;
        }
        return string;
    }

    @NotNull
    public static final CharSequence removeSurrounding(@NotNull CharSequence charSequence, @NotNull CharSequence charSequence2) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(charSequence2, "delimiter");
        return StringsKt.removeSurrounding(charSequence, charSequence2, charSequence2);
    }

    @NotNull
    public static final String removeSurrounding(@NotNull String string, @NotNull CharSequence charSequence) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(charSequence, "delimiter");
        return StringsKt.removeSurrounding(string, charSequence, charSequence);
    }

    @NotNull
    public static final String replaceBefore(@NotNull String string, char c, @NotNull String string2, @NotNull String string3) {
        String string4;
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(string2, "replacement");
        Intrinsics.checkNotNullParameter(string3, "missingDelimiterValue");
        int n = StringsKt.indexOf$default((CharSequence)string, c, 0, false, 6, null);
        if (n == -1) {
            string4 = string3;
        } else {
            String string5 = string;
            int n2 = 0;
            string4 = ((Object)StringsKt.replaceRange((CharSequence)string5, n2, n, (CharSequence)string2)).toString();
        }
        return string4;
    }

    public static String replaceBefore$default(String string, char c, String string2, String string3, int n, Object object) {
        if ((n & 4) != 0) {
            string3 = string;
        }
        return StringsKt.replaceBefore(string, c, string2, string3);
    }

    @NotNull
    public static final String replaceBefore(@NotNull String string, @NotNull String string2, @NotNull String string3, @NotNull String string4) {
        String string5;
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(string2, "delimiter");
        Intrinsics.checkNotNullParameter(string3, "replacement");
        Intrinsics.checkNotNullParameter(string4, "missingDelimiterValue");
        int n = StringsKt.indexOf$default((CharSequence)string, string2, 0, false, 6, null);
        if (n == -1) {
            string5 = string4;
        } else {
            String string6 = string;
            int n2 = 0;
            string5 = ((Object)StringsKt.replaceRange((CharSequence)string6, n2, n, (CharSequence)string3)).toString();
        }
        return string5;
    }

    public static String replaceBefore$default(String string, String string2, String string3, String string4, int n, Object object) {
        if ((n & 4) != 0) {
            string4 = string;
        }
        return StringsKt.replaceBefore(string, string2, string3, string4);
    }

    @NotNull
    public static final String replaceAfter(@NotNull String string, char c, @NotNull String string2, @NotNull String string3) {
        String string4;
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(string2, "replacement");
        Intrinsics.checkNotNullParameter(string3, "missingDelimiterValue");
        int n = StringsKt.indexOf$default((CharSequence)string, c, 0, false, 6, null);
        if (n == -1) {
            string4 = string3;
        } else {
            String string5 = string;
            int n2 = n + 1;
            int n3 = string.length();
            string4 = ((Object)StringsKt.replaceRange((CharSequence)string5, n2, n3, (CharSequence)string2)).toString();
        }
        return string4;
    }

    public static String replaceAfter$default(String string, char c, String string2, String string3, int n, Object object) {
        if ((n & 4) != 0) {
            string3 = string;
        }
        return StringsKt.replaceAfter(string, c, string2, string3);
    }

    @NotNull
    public static final String replaceAfter(@NotNull String string, @NotNull String string2, @NotNull String string3, @NotNull String string4) {
        String string5;
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(string2, "delimiter");
        Intrinsics.checkNotNullParameter(string3, "replacement");
        Intrinsics.checkNotNullParameter(string4, "missingDelimiterValue");
        int n = StringsKt.indexOf$default((CharSequence)string, string2, 0, false, 6, null);
        if (n == -1) {
            string5 = string4;
        } else {
            String string6 = string;
            int n2 = n + string2.length();
            int n3 = string.length();
            string5 = ((Object)StringsKt.replaceRange((CharSequence)string6, n2, n3, (CharSequence)string3)).toString();
        }
        return string5;
    }

    public static String replaceAfter$default(String string, String string2, String string3, String string4, int n, Object object) {
        if ((n & 4) != 0) {
            string4 = string;
        }
        return StringsKt.replaceAfter(string, string2, string3, string4);
    }

    @NotNull
    public static final String replaceAfterLast(@NotNull String string, @NotNull String string2, @NotNull String string3, @NotNull String string4) {
        String string5;
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(string2, "delimiter");
        Intrinsics.checkNotNullParameter(string3, "replacement");
        Intrinsics.checkNotNullParameter(string4, "missingDelimiterValue");
        int n = StringsKt.lastIndexOf$default((CharSequence)string, string2, 0, false, 6, null);
        if (n == -1) {
            string5 = string4;
        } else {
            String string6 = string;
            int n2 = n + string2.length();
            int n3 = string.length();
            string5 = ((Object)StringsKt.replaceRange((CharSequence)string6, n2, n3, (CharSequence)string3)).toString();
        }
        return string5;
    }

    public static String replaceAfterLast$default(String string, String string2, String string3, String string4, int n, Object object) {
        if ((n & 4) != 0) {
            string4 = string;
        }
        return StringsKt.replaceAfterLast(string, string2, string3, string4);
    }

    @NotNull
    public static final String replaceAfterLast(@NotNull String string, char c, @NotNull String string2, @NotNull String string3) {
        String string4;
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(string2, "replacement");
        Intrinsics.checkNotNullParameter(string3, "missingDelimiterValue");
        int n = StringsKt.lastIndexOf$default((CharSequence)string, c, 0, false, 6, null);
        if (n == -1) {
            string4 = string3;
        } else {
            String string5 = string;
            int n2 = n + 1;
            int n3 = string.length();
            string4 = ((Object)StringsKt.replaceRange((CharSequence)string5, n2, n3, (CharSequence)string2)).toString();
        }
        return string4;
    }

    public static String replaceAfterLast$default(String string, char c, String string2, String string3, int n, Object object) {
        if ((n & 4) != 0) {
            string3 = string;
        }
        return StringsKt.replaceAfterLast(string, c, string2, string3);
    }

    @NotNull
    public static final String replaceBeforeLast(@NotNull String string, char c, @NotNull String string2, @NotNull String string3) {
        String string4;
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(string2, "replacement");
        Intrinsics.checkNotNullParameter(string3, "missingDelimiterValue");
        int n = StringsKt.lastIndexOf$default((CharSequence)string, c, 0, false, 6, null);
        if (n == -1) {
            string4 = string3;
        } else {
            String string5 = string;
            int n2 = 0;
            string4 = ((Object)StringsKt.replaceRange((CharSequence)string5, n2, n, (CharSequence)string2)).toString();
        }
        return string4;
    }

    public static String replaceBeforeLast$default(String string, char c, String string2, String string3, int n, Object object) {
        if ((n & 4) != 0) {
            string3 = string;
        }
        return StringsKt.replaceBeforeLast(string, c, string2, string3);
    }

    @NotNull
    public static final String replaceBeforeLast(@NotNull String string, @NotNull String string2, @NotNull String string3, @NotNull String string4) {
        String string5;
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(string2, "delimiter");
        Intrinsics.checkNotNullParameter(string3, "replacement");
        Intrinsics.checkNotNullParameter(string4, "missingDelimiterValue");
        int n = StringsKt.lastIndexOf$default((CharSequence)string, string2, 0, false, 6, null);
        if (n == -1) {
            string5 = string4;
        } else {
            String string6 = string;
            int n2 = 0;
            string5 = ((Object)StringsKt.replaceRange((CharSequence)string6, n2, n, (CharSequence)string3)).toString();
        }
        return string5;
    }

    public static String replaceBeforeLast$default(String string, String string2, String string3, String string4, int n, Object object) {
        if ((n & 4) != 0) {
            string4 = string;
        }
        return StringsKt.replaceBeforeLast(string, string2, string3, string4);
    }

    @InlineOnly
    private static final String replace(CharSequence charSequence, Regex regex, String string) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(regex, "regex");
        Intrinsics.checkNotNullParameter(string, "replacement");
        return regex.replace(charSequence, string);
    }

    @InlineOnly
    private static final String replace(CharSequence charSequence, Regex regex, Function1<? super MatchResult, ? extends CharSequence> function1) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(regex, "regex");
        Intrinsics.checkNotNullParameter(function1, "transform");
        return regex.replace(charSequence, function1);
    }

    @InlineOnly
    private static final String replaceFirst(CharSequence charSequence, Regex regex, String string) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(regex, "regex");
        Intrinsics.checkNotNullParameter(string, "replacement");
        return regex.replaceFirst(charSequence, string);
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    @OverloadResolutionByLambdaReturnType
    @JvmName(name="replaceFirstCharWithChar")
    @InlineOnly
    private static final String replaceFirstCharWithChar(String string, Function1<? super Character, Character> function1) {
        String string2;
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(function1, "transform");
        if (((CharSequence)string).length() > 0) {
            char c = function1.invoke(Character.valueOf(string.charAt(0))).charValue();
            String string3 = string;
            int n = 1;
            String string4 = string3.substring(n);
            Intrinsics.checkNotNullExpressionValue(string4, "this as java.lang.String).substring(startIndex)");
            string3 = string4;
            string2 = c + string3;
        } else {
            string2 = string;
        }
        return string2;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    @OverloadResolutionByLambdaReturnType
    @JvmName(name="replaceFirstCharWithCharSequence")
    @InlineOnly
    private static final String replaceFirstCharWithCharSequence(String string, Function1<? super Character, ? extends CharSequence> function1) {
        String string2;
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(function1, "transform");
        if (((CharSequence)string).length() > 0) {
            StringBuilder stringBuilder = new StringBuilder().append((Object)function1.invoke(Character.valueOf(string.charAt(0))));
            String string3 = string;
            int n = 1;
            String string4 = string3.substring(n);
            Intrinsics.checkNotNullExpressionValue(string4, "this as java.lang.String).substring(startIndex)");
            string2 = stringBuilder.append(string4).toString();
        } else {
            string2 = string;
        }
        return string2;
    }

    @InlineOnly
    private static final boolean matches(CharSequence charSequence, Regex regex) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(regex, "regex");
        return regex.matches(charSequence);
    }

    public static final boolean regionMatchesImpl(@NotNull CharSequence charSequence, int n, @NotNull CharSequence charSequence2, int n2, int n3, boolean bl) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(charSequence2, "other");
        if (n2 < 0 || n < 0 || n > charSequence.length() - n3 || n2 > charSequence2.length() - n3) {
            return true;
        }
        for (int i = 0; i < n3; ++i) {
            if (CharsKt.equals(charSequence.charAt(n + i), charSequence2.charAt(n2 + i), bl)) continue;
            return true;
        }
        return false;
    }

    public static final boolean startsWith(@NotNull CharSequence charSequence, char c, boolean bl) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        return charSequence.length() > 0 && CharsKt.equals(charSequence.charAt(0), c, bl);
    }

    public static boolean startsWith$default(CharSequence charSequence, char c, boolean bl, int n, Object object) {
        if ((n & 2) != 0) {
            bl = false;
        }
        return StringsKt.startsWith(charSequence, c, bl);
    }

    public static final boolean endsWith(@NotNull CharSequence charSequence, char c, boolean bl) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        return charSequence.length() > 0 && CharsKt.equals(charSequence.charAt(StringsKt.getLastIndex(charSequence)), c, bl);
    }

    public static boolean endsWith$default(CharSequence charSequence, char c, boolean bl, int n, Object object) {
        if ((n & 2) != 0) {
            bl = false;
        }
        return StringsKt.endsWith(charSequence, c, bl);
    }

    public static final boolean startsWith(@NotNull CharSequence charSequence, @NotNull CharSequence charSequence2, boolean bl) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(charSequence2, "prefix");
        if (!bl && charSequence instanceof String && charSequence2 instanceof String) {
            return StringsKt.startsWith$default((String)charSequence, (String)charSequence2, false, 2, null);
        }
        return StringsKt.regionMatchesImpl(charSequence, 0, charSequence2, 0, charSequence2.length(), bl);
    }

    public static boolean startsWith$default(CharSequence charSequence, CharSequence charSequence2, boolean bl, int n, Object object) {
        if ((n & 2) != 0) {
            bl = false;
        }
        return StringsKt.startsWith(charSequence, charSequence2, bl);
    }

    public static final boolean startsWith(@NotNull CharSequence charSequence, @NotNull CharSequence charSequence2, int n, boolean bl) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(charSequence2, "prefix");
        if (!bl && charSequence instanceof String && charSequence2 instanceof String) {
            return StringsKt.startsWith$default((String)charSequence, (String)charSequence2, n, false, 4, null);
        }
        return StringsKt.regionMatchesImpl(charSequence, n, charSequence2, 0, charSequence2.length(), bl);
    }

    public static boolean startsWith$default(CharSequence charSequence, CharSequence charSequence2, int n, boolean bl, int n2, Object object) {
        if ((n2 & 4) != 0) {
            bl = false;
        }
        return StringsKt.startsWith(charSequence, charSequence2, n, bl);
    }

    public static final boolean endsWith(@NotNull CharSequence charSequence, @NotNull CharSequence charSequence2, boolean bl) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(charSequence2, "suffix");
        if (!bl && charSequence instanceof String && charSequence2 instanceof String) {
            return StringsKt.endsWith$default((String)charSequence, (String)charSequence2, false, 2, null);
        }
        return StringsKt.regionMatchesImpl(charSequence, charSequence.length() - charSequence2.length(), charSequence2, 0, charSequence2.length(), bl);
    }

    public static boolean endsWith$default(CharSequence charSequence, CharSequence charSequence2, boolean bl, int n, Object object) {
        if ((n & 2) != 0) {
            bl = false;
        }
        return StringsKt.endsWith(charSequence, charSequence2, bl);
    }

    @NotNull
    public static final String commonPrefixWith(@NotNull CharSequence charSequence, @NotNull CharSequence charSequence2, boolean bl) {
        int n;
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(charSequence2, "other");
        int n2 = Math.min(charSequence.length(), charSequence2.length());
        for (n = 0; n < n2 && CharsKt.equals(charSequence.charAt(n), charSequence2.charAt(n), bl); ++n) {
        }
        if (StringsKt.hasSurrogatePairAt(charSequence, n - 1) || StringsKt.hasSurrogatePairAt(charSequence2, n - 1)) {
            --n;
        }
        return ((Object)charSequence.subSequence(0, n)).toString();
    }

    public static String commonPrefixWith$default(CharSequence charSequence, CharSequence charSequence2, boolean bl, int n, Object object) {
        if ((n & 2) != 0) {
            bl = false;
        }
        return StringsKt.commonPrefixWith(charSequence, charSequence2, bl);
    }

    @NotNull
    public static final String commonSuffixWith(@NotNull CharSequence charSequence, @NotNull CharSequence charSequence2, boolean bl) {
        int n;
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(charSequence2, "other");
        int n2 = charSequence.length();
        int n3 = charSequence2.length();
        int n4 = Math.min(n2, n3);
        for (n = 0; n < n4 && CharsKt.equals(charSequence.charAt(n2 - n - 1), charSequence2.charAt(n3 - n - 1), bl); ++n) {
        }
        if (StringsKt.hasSurrogatePairAt(charSequence, n2 - n - 1) || StringsKt.hasSurrogatePairAt(charSequence2, n3 - n - 1)) {
            --n;
        }
        return ((Object)charSequence.subSequence(n2 - n, n2)).toString();
    }

    public static String commonSuffixWith$default(CharSequence charSequence, CharSequence charSequence2, boolean bl, int n, Object object) {
        if ((n & 2) != 0) {
            bl = false;
        }
        return StringsKt.commonSuffixWith(charSequence, charSequence2, bl);
    }

    public static final int indexOfAny(@NotNull CharSequence charSequence, @NotNull char[] cArray, int n, boolean bl) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(cArray, "chars");
        if (!bl && cArray.length == 1 && charSequence instanceof String) {
            char c = ArraysKt.single(cArray);
            return ((String)charSequence).indexOf(c, n);
        }
        IntIterator intIterator = new IntRange(RangesKt.coerceAtLeast(n, 0), StringsKt.getLastIndex(charSequence)).iterator();
        while (intIterator.hasNext()) {
            boolean bl2;
            int n2;
            block3: {
                n2 = intIterator.nextInt();
                char c = charSequence.charAt(n2);
                char[] cArray2 = cArray;
                boolean bl3 = false;
                int n3 = cArray2.length;
                for (int i = 0; i < n3; ++i) {
                    char c2;
                    char c3 = c2 = cArray2[i];
                    boolean bl4 = false;
                    if (!CharsKt.equals(c3, c, bl)) continue;
                    bl2 = true;
                    break block3;
                }
                bl2 = false;
            }
            if (!bl2) continue;
            return n2;
        }
        return 1;
    }

    public static int indexOfAny$default(CharSequence charSequence, char[] cArray, int n, boolean bl, int n2, Object object) {
        if ((n2 & 2) != 0) {
            n = 0;
        }
        if ((n2 & 4) != 0) {
            bl = false;
        }
        return StringsKt.indexOfAny(charSequence, cArray, n, bl);
    }

    public static final int lastIndexOfAny(@NotNull CharSequence charSequence, @NotNull char[] cArray, int n, boolean bl) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(cArray, "chars");
        if (!bl && cArray.length == 1 && charSequence instanceof String) {
            char c = ArraysKt.single(cArray);
            return ((String)charSequence).lastIndexOf(c, n);
        }
        for (int i = RangesKt.coerceAtMost(n, StringsKt.getLastIndex(charSequence)); -1 < i; --i) {
            boolean bl2;
            block3: {
                char c = charSequence.charAt(i);
                char[] cArray2 = cArray;
                boolean bl3 = false;
                int n2 = cArray2.length;
                for (int j = 0; j < n2; ++j) {
                    char c2;
                    char c3 = c2 = cArray2[j];
                    boolean bl4 = false;
                    if (!CharsKt.equals(c3, c, bl)) continue;
                    bl2 = true;
                    break block3;
                }
                bl2 = false;
            }
            if (!bl2) continue;
            return i;
        }
        return 1;
    }

    public static int lastIndexOfAny$default(CharSequence charSequence, char[] cArray, int n, boolean bl, int n2, Object object) {
        if ((n2 & 2) != 0) {
            n = StringsKt.getLastIndex(charSequence);
        }
        if ((n2 & 4) != 0) {
            bl = false;
        }
        return StringsKt.lastIndexOfAny(charSequence, cArray, n, bl);
    }

    private static final int indexOf$StringsKt__StringsKt(CharSequence charSequence, CharSequence charSequence2, int n, int n2, boolean bl, boolean bl2) {
        IntProgression intProgression;
        IntProgression intProgression2 = intProgression = !bl2 ? (IntProgression)new IntRange(RangesKt.coerceAtLeast(n, 0), RangesKt.coerceAtMost(n2, charSequence.length())) : RangesKt.downTo(RangesKt.coerceAtMost(n, StringsKt.getLastIndex(charSequence)), RangesKt.coerceAtLeast(n2, 0));
        if (charSequence instanceof String && charSequence2 instanceof String) {
            int n3 = intProgression.getFirst();
            int n4 = intProgression.getLast();
            int n5 = intProgression.getStep();
            if (n5 > 0 && n3 <= n4 || n5 < 0 && n4 <= n3) {
                while (true) {
                    if (StringsKt.regionMatches((String)charSequence2, 0, (String)charSequence, n3, charSequence2.length(), bl)) {
                        return n3;
                    }
                    if (n3 != n4) {
                        n3 += n5;
                        continue;
                    }
                    break;
                }
            }
        } else {
            int n6 = intProgression.getFirst();
            int n7 = intProgression.getLast();
            int n8 = intProgression.getStep();
            if (n8 > 0 && n6 <= n7 || n8 < 0 && n7 <= n6) {
                while (true) {
                    if (StringsKt.regionMatchesImpl(charSequence2, 0, charSequence, n6, charSequence2.length(), bl)) {
                        return n6;
                    }
                    if (n6 == n7) break;
                    n6 += n8;
                }
            }
        }
        return 1;
    }

    static int indexOf$StringsKt__StringsKt$default(CharSequence charSequence, CharSequence charSequence2, int n, int n2, boolean bl, boolean bl2, int n3, Object object) {
        if ((n3 & 0x10) != 0) {
            bl2 = false;
        }
        return StringsKt__StringsKt.indexOf$StringsKt__StringsKt(charSequence, charSequence2, n, n2, bl, bl2);
    }

    private static final Pair<Integer, String> findAnyOf$StringsKt__StringsKt(CharSequence charSequence, Collection<String> collection, int n, boolean bl, boolean bl2) {
        IntProgression intProgression;
        if (!bl && collection.size() == 1) {
            String string = (String)CollectionsKt.single((Iterable)collection);
            int n2 = !bl2 ? StringsKt.indexOf$default(charSequence, string, n, false, 4, null) : StringsKt.lastIndexOf$default(charSequence, string, n, false, 4, null);
            return n2 < 0 ? null : TuplesKt.to(n2, string);
        }
        IntProgression intProgression2 = intProgression = !bl2 ? (IntProgression)new IntRange(RangesKt.coerceAtLeast(n, 0), charSequence.length()) : RangesKt.downTo(RangesKt.coerceAtMost(n, StringsKt.getLastIndex(charSequence)), 0);
        if (charSequence instanceof String) {
            int n3 = intProgression.getFirst();
            int n4 = intProgression.getLast();
            int n5 = intProgression.getStep();
            if (n5 > 0 && n3 <= n4 || n5 < 0 && n4 <= n3) {
                while (true) {
                    Object v1;
                    block12: {
                        Iterable iterable = collection;
                        boolean bl3 = false;
                        for (Object t : iterable) {
                            String string = (String)t;
                            boolean bl4 = false;
                            if (!StringsKt.regionMatches(string, 0, (String)charSequence, n3, string.length(), bl)) continue;
                            v1 = t;
                            break block12;
                        }
                        v1 = null;
                    }
                    String string = v1;
                    if (string != null) {
                        return TuplesKt.to(n3, string);
                    }
                    if (n3 != n4) {
                        n3 += n5;
                        continue;
                    }
                    break;
                }
            }
        } else {
            int n6 = intProgression.getFirst();
            int n7 = intProgression.getLast();
            int n8 = intProgression.getStep();
            if (n8 > 0 && n6 <= n7 || n8 < 0 && n7 <= n6) {
                while (true) {
                    Object v2;
                    block14: {
                        Iterable iterable = collection;
                        boolean bl5 = false;
                        for (Object t : iterable) {
                            String string = (String)t;
                            boolean bl6 = false;
                            if (!StringsKt.regionMatchesImpl(string, 0, charSequence, n6, string.length(), bl)) continue;
                            v2 = t;
                            break block14;
                        }
                        v2 = null;
                    }
                    String string = v2;
                    if (string != null) {
                        return TuplesKt.to(n6, string);
                    }
                    if (n6 == n7) break;
                    n6 += n8;
                }
            }
        }
        return null;
    }

    @Nullable
    public static final Pair<Integer, String> findAnyOf(@NotNull CharSequence charSequence, @NotNull Collection<String> collection, int n, boolean bl) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(collection, "strings");
        return StringsKt__StringsKt.findAnyOf$StringsKt__StringsKt(charSequence, collection, n, bl, false);
    }

    public static Pair findAnyOf$default(CharSequence charSequence, Collection collection, int n, boolean bl, int n2, Object object) {
        if ((n2 & 2) != 0) {
            n = 0;
        }
        if ((n2 & 4) != 0) {
            bl = false;
        }
        return StringsKt.findAnyOf(charSequence, collection, n, bl);
    }

    @Nullable
    public static final Pair<Integer, String> findLastAnyOf(@NotNull CharSequence charSequence, @NotNull Collection<String> collection, int n, boolean bl) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(collection, "strings");
        return StringsKt__StringsKt.findAnyOf$StringsKt__StringsKt(charSequence, collection, n, bl, true);
    }

    public static Pair findLastAnyOf$default(CharSequence charSequence, Collection collection, int n, boolean bl, int n2, Object object) {
        if ((n2 & 2) != 0) {
            n = StringsKt.getLastIndex(charSequence);
        }
        if ((n2 & 4) != 0) {
            bl = false;
        }
        return StringsKt.findLastAnyOf(charSequence, collection, n, bl);
    }

    public static final int indexOfAny(@NotNull CharSequence charSequence, @NotNull Collection<String> collection, int n, boolean bl) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(collection, "strings");
        Pair<Integer, String> pair = StringsKt__StringsKt.findAnyOf$StringsKt__StringsKt(charSequence, collection, n, bl, false);
        return pair != null ? ((Number)pair.getFirst()).intValue() : -1;
    }

    public static int indexOfAny$default(CharSequence charSequence, Collection collection, int n, boolean bl, int n2, Object object) {
        if ((n2 & 2) != 0) {
            n = 0;
        }
        if ((n2 & 4) != 0) {
            bl = false;
        }
        return StringsKt.indexOfAny(charSequence, collection, n, bl);
    }

    public static final int lastIndexOfAny(@NotNull CharSequence charSequence, @NotNull Collection<String> collection, int n, boolean bl) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(collection, "strings");
        Pair<Integer, String> pair = StringsKt__StringsKt.findAnyOf$StringsKt__StringsKt(charSequence, collection, n, bl, true);
        return pair != null ? ((Number)pair.getFirst()).intValue() : -1;
    }

    public static int lastIndexOfAny$default(CharSequence charSequence, Collection collection, int n, boolean bl, int n2, Object object) {
        if ((n2 & 2) != 0) {
            n = StringsKt.getLastIndex(charSequence);
        }
        if ((n2 & 4) != 0) {
            bl = false;
        }
        return StringsKt.lastIndexOfAny(charSequence, collection, n, bl);
    }

    public static final int indexOf(@NotNull CharSequence charSequence, char c, int n, boolean bl) {
        int n2;
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        if (bl || !(charSequence instanceof String)) {
            char[] cArray = new char[]{c};
            n2 = StringsKt.indexOfAny(charSequence, cArray, n, bl);
        } else {
            n2 = ((String)charSequence).indexOf(c, n);
        }
        return n2;
    }

    public static int indexOf$default(CharSequence charSequence, char c, int n, boolean bl, int n2, Object object) {
        if ((n2 & 2) != 0) {
            n = 0;
        }
        if ((n2 & 4) != 0) {
            bl = false;
        }
        return StringsKt.indexOf(charSequence, c, n, bl);
    }

    public static final int indexOf(@NotNull CharSequence charSequence, @NotNull String string, int n, boolean bl) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(string, "string");
        return bl || !(charSequence instanceof String) ? StringsKt__StringsKt.indexOf$StringsKt__StringsKt$default(charSequence, string, n, charSequence.length(), bl, false, 16, null) : ((String)charSequence).indexOf(string, n);
    }

    public static int indexOf$default(CharSequence charSequence, String string, int n, boolean bl, int n2, Object object) {
        if ((n2 & 2) != 0) {
            n = 0;
        }
        if ((n2 & 4) != 0) {
            bl = false;
        }
        return StringsKt.indexOf(charSequence, string, n, bl);
    }

    public static final int lastIndexOf(@NotNull CharSequence charSequence, char c, int n, boolean bl) {
        int n2;
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        if (bl || !(charSequence instanceof String)) {
            char[] cArray = new char[]{c};
            n2 = StringsKt.lastIndexOfAny(charSequence, cArray, n, bl);
        } else {
            n2 = ((String)charSequence).lastIndexOf(c, n);
        }
        return n2;
    }

    public static int lastIndexOf$default(CharSequence charSequence, char c, int n, boolean bl, int n2, Object object) {
        if ((n2 & 2) != 0) {
            n = StringsKt.getLastIndex(charSequence);
        }
        if ((n2 & 4) != 0) {
            bl = false;
        }
        return StringsKt.lastIndexOf(charSequence, c, n, bl);
    }

    public static final int lastIndexOf(@NotNull CharSequence charSequence, @NotNull String string, int n, boolean bl) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(string, "string");
        return bl || !(charSequence instanceof String) ? StringsKt__StringsKt.indexOf$StringsKt__StringsKt(charSequence, string, n, 0, bl, true) : ((String)charSequence).lastIndexOf(string, n);
    }

    public static int lastIndexOf$default(CharSequence charSequence, String string, int n, boolean bl, int n2, Object object) {
        if ((n2 & 2) != 0) {
            n = StringsKt.getLastIndex(charSequence);
        }
        if ((n2 & 4) != 0) {
            bl = false;
        }
        return StringsKt.lastIndexOf(charSequence, string, n, bl);
    }

    public static final boolean contains(@NotNull CharSequence charSequence, @NotNull CharSequence charSequence2, boolean bl) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(charSequence2, "other");
        return charSequence2 instanceof String ? StringsKt.indexOf$default(charSequence, (String)charSequence2, 0, bl, 2, null) >= 0 : StringsKt__StringsKt.indexOf$StringsKt__StringsKt$default(charSequence, charSequence2, 0, charSequence.length(), bl, false, 16, null) >= 0;
    }

    public static boolean contains$default(CharSequence charSequence, CharSequence charSequence2, boolean bl, int n, Object object) {
        if ((n & 2) != 0) {
            bl = false;
        }
        return StringsKt.contains(charSequence, charSequence2, bl);
    }

    public static final boolean contains(@NotNull CharSequence charSequence, char c, boolean bl) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        return StringsKt.indexOf$default(charSequence, c, 0, bl, 2, null) >= 0;
    }

    public static boolean contains$default(CharSequence charSequence, char c, boolean bl, int n, Object object) {
        if ((n & 2) != 0) {
            bl = false;
        }
        return StringsKt.contains(charSequence, c, bl);
    }

    @InlineOnly
    private static final boolean contains(CharSequence charSequence, Regex regex) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(regex, "regex");
        return regex.containsMatchIn(charSequence);
    }

    private static final Sequence<IntRange> rangesDelimitedBy$StringsKt__StringsKt(CharSequence charSequence, char[] cArray, int n, boolean bl, int n2) {
        StringsKt.requireNonNegativeLimit(n2);
        return new DelimitedRangesSequence(charSequence, n, n2, (Function2<? super CharSequence, ? super Integer, Pair<Integer, Integer>>)new Function2<CharSequence, Integer, Pair<? extends Integer, ? extends Integer>>(cArray, bl){
            final char[] $delimiters;
            final boolean $ignoreCase;
            {
                this.$delimiters = cArray;
                this.$ignoreCase = bl;
                super(2);
            }

            @Nullable
            public final Pair<Integer, Integer> invoke(@NotNull CharSequence charSequence, int n) {
                int n2;
                Intrinsics.checkNotNullParameter(charSequence, "$this$$receiver");
                int n3 = n2 = StringsKt.indexOfAny(charSequence, this.$delimiters, n, this.$ignoreCase);
                boolean bl = false;
                return n3 < 0 ? null : TuplesKt.to(n3, 1);
            }

            public Object invoke(Object object, Object object2) {
                return this.invoke((CharSequence)object, ((Number)object2).intValue());
            }
        });
    }

    static Sequence rangesDelimitedBy$StringsKt__StringsKt$default(CharSequence charSequence, char[] cArray, int n, boolean bl, int n2, int n3, Object object) {
        if ((n3 & 2) != 0) {
            n = 0;
        }
        if ((n3 & 4) != 0) {
            bl = false;
        }
        if ((n3 & 8) != 0) {
            n2 = 0;
        }
        return StringsKt__StringsKt.rangesDelimitedBy$StringsKt__StringsKt(charSequence, cArray, n, bl, n2);
    }

    private static final Sequence<IntRange> rangesDelimitedBy$StringsKt__StringsKt(CharSequence charSequence, String[] stringArray, int n, boolean bl, int n2) {
        StringsKt.requireNonNegativeLimit(n2);
        List<String> list = ArraysKt.asList(stringArray);
        return new DelimitedRangesSequence(charSequence, n, n2, (Function2<? super CharSequence, ? super Integer, Pair<Integer, Integer>>)new Function2<CharSequence, Integer, Pair<? extends Integer, ? extends Integer>>(list, bl){
            final List<String> $delimitersList;
            final boolean $ignoreCase;
            {
                this.$delimitersList = list;
                this.$ignoreCase = bl;
                super(2);
            }

            @Nullable
            public final Pair<Integer, Integer> invoke(@NotNull CharSequence charSequence, int n) {
                Pair<A, Integer> pair;
                Intrinsics.checkNotNullParameter(charSequence, "$this$$receiver");
                Pair pair2 = StringsKt__StringsKt.access$findAnyOf(charSequence, this.$delimitersList, n, this.$ignoreCase, false);
                if (pair2 != null) {
                    Pair pair3;
                    Pair pair4 = pair3 = pair2;
                    boolean bl = false;
                    pair = TuplesKt.to(pair4.getFirst(), ((String)pair4.getSecond()).length());
                } else {
                    pair = null;
                }
                return pair;
            }

            public Object invoke(Object object, Object object2) {
                return this.invoke((CharSequence)object, ((Number)object2).intValue());
            }
        });
    }

    static Sequence rangesDelimitedBy$StringsKt__StringsKt$default(CharSequence charSequence, String[] stringArray, int n, boolean bl, int n2, int n3, Object object) {
        if ((n3 & 2) != 0) {
            n = 0;
        }
        if ((n3 & 4) != 0) {
            bl = false;
        }
        if ((n3 & 8) != 0) {
            n2 = 0;
        }
        return StringsKt__StringsKt.rangesDelimitedBy$StringsKt__StringsKt(charSequence, stringArray, n, bl, n2);
    }

    public static final void requireNonNegativeLimit(int n) {
        boolean bl;
        boolean bl2 = bl = n >= 0;
        if (!bl) {
            boolean bl3 = false;
            String string = "Limit must be non-negative, but was " + n;
            throw new IllegalArgumentException(string.toString());
        }
    }

    @NotNull
    public static final Sequence<String> splitToSequence(@NotNull CharSequence charSequence, @NotNull String[] stringArray, boolean bl, int n) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(stringArray, "delimiters");
        return SequencesKt.map(StringsKt__StringsKt.rangesDelimitedBy$StringsKt__StringsKt$default(charSequence, stringArray, 0, bl, n, 2, null), (Function1)new Function1<IntRange, String>(charSequence){
            final CharSequence $this_splitToSequence;
            {
                this.$this_splitToSequence = charSequence;
                super(1);
            }

            @NotNull
            public final String invoke(@NotNull IntRange intRange) {
                Intrinsics.checkNotNullParameter(intRange, "it");
                return StringsKt.substring(this.$this_splitToSequence, intRange);
            }

            public Object invoke(Object object) {
                return this.invoke((IntRange)object);
            }
        });
    }

    public static Sequence splitToSequence$default(CharSequence charSequence, String[] stringArray, boolean bl, int n, int n2, Object object) {
        if ((n2 & 2) != 0) {
            bl = false;
        }
        if ((n2 & 4) != 0) {
            n = 0;
        }
        return StringsKt.splitToSequence(charSequence, stringArray, bl, n);
    }

    @NotNull
    public static final List<String> split(@NotNull CharSequence charSequence, @NotNull String[] stringArray, boolean bl, int n) {
        Object object;
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(stringArray, "delimiters");
        if (stringArray.length == 1 && !(((CharSequence)(object = stringArray[0])).length() == 0)) {
            return StringsKt__StringsKt.split$StringsKt__StringsKt(charSequence, (String)object, bl, n);
        }
        object = SequencesKt.asIterable(StringsKt__StringsKt.rangesDelimitedBy$StringsKt__StringsKt$default(charSequence, stringArray, 0, bl, n, 2, null));
        boolean bl2 = false;
        Object object2 = object;
        Collection collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(object, 10));
        boolean bl3 = false;
        Iterator iterator2 = object2.iterator();
        while (iterator2.hasNext()) {
            Object t = iterator2.next();
            IntRange intRange = (IntRange)t;
            Collection collection2 = collection;
            boolean bl4 = false;
            collection2.add(StringsKt.substring(charSequence, intRange));
        }
        return (List)collection;
    }

    public static List split$default(CharSequence charSequence, String[] stringArray, boolean bl, int n, int n2, Object object) {
        if ((n2 & 2) != 0) {
            bl = false;
        }
        if ((n2 & 4) != 0) {
            n = 0;
        }
        return StringsKt.split(charSequence, stringArray, bl, n);
    }

    @NotNull
    public static final Sequence<String> splitToSequence(@NotNull CharSequence charSequence, @NotNull char[] cArray, boolean bl, int n) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(cArray, "delimiters");
        return SequencesKt.map(StringsKt__StringsKt.rangesDelimitedBy$StringsKt__StringsKt$default(charSequence, cArray, 0, bl, n, 2, null), (Function1)new Function1<IntRange, String>(charSequence){
            final CharSequence $this_splitToSequence;
            {
                this.$this_splitToSequence = charSequence;
                super(1);
            }

            @NotNull
            public final String invoke(@NotNull IntRange intRange) {
                Intrinsics.checkNotNullParameter(intRange, "it");
                return StringsKt.substring(this.$this_splitToSequence, intRange);
            }

            public Object invoke(Object object) {
                return this.invoke((IntRange)object);
            }
        });
    }

    public static Sequence splitToSequence$default(CharSequence charSequence, char[] cArray, boolean bl, int n, int n2, Object object) {
        if ((n2 & 2) != 0) {
            bl = false;
        }
        if ((n2 & 4) != 0) {
            n = 0;
        }
        return StringsKt.splitToSequence(charSequence, cArray, bl, n);
    }

    @NotNull
    public static final List<String> split(@NotNull CharSequence charSequence, @NotNull char[] cArray, boolean bl, int n) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(cArray, "delimiters");
        if (cArray.length == 1) {
            return StringsKt__StringsKt.split$StringsKt__StringsKt(charSequence, String.valueOf(cArray[0]), bl, n);
        }
        Iterable iterable = SequencesKt.asIterable(StringsKt__StringsKt.rangesDelimitedBy$StringsKt__StringsKt$default(charSequence, cArray, 0, bl, n, 2, null));
        boolean bl2 = false;
        Iterable iterable2 = iterable;
        Collection collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable, 10));
        boolean bl3 = false;
        for (Object t : iterable2) {
            IntRange intRange = (IntRange)t;
            Collection collection2 = collection;
            boolean bl4 = false;
            collection2.add(StringsKt.substring(charSequence, intRange));
        }
        return (List)collection;
    }

    public static List split$default(CharSequence charSequence, char[] cArray, boolean bl, int n, int n2, Object object) {
        if ((n2 & 2) != 0) {
            bl = false;
        }
        if ((n2 & 4) != 0) {
            n = 0;
        }
        return StringsKt.split(charSequence, cArray, bl, n);
    }

    private static final List<String> split$StringsKt__StringsKt(CharSequence charSequence, String string, boolean bl, int n) {
        StringsKt.requireNonNegativeLimit(n);
        int n2 = 0;
        int n3 = StringsKt.indexOf(charSequence, string, n2, bl);
        if (n3 == -1 || n == 1) {
            return CollectionsKt.listOf(((Object)charSequence).toString());
        }
        boolean bl2 = n > 0;
        ArrayList<String> arrayList = new ArrayList<String>(bl2 ? RangesKt.coerceAtMost(n, 10) : 10);
        do {
            arrayList.add(((Object)charSequence.subSequence(n2, n3)).toString());
            n2 = n3 + string.length();
        } while ((!bl2 || arrayList.size() != n - 1) && (n3 = StringsKt.indexOf(charSequence, string, n2, bl)) != -1);
        arrayList.add(((Object)charSequence.subSequence(n2, charSequence.length())).toString());
        return arrayList;
    }

    @InlineOnly
    private static final List<String> split(CharSequence charSequence, Regex regex, int n) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(regex, "regex");
        return regex.split(charSequence, n);
    }

    static List split$default(CharSequence charSequence, Regex regex, int n, int n2, Object object) {
        if ((n2 & 2) != 0) {
            n = 0;
        }
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(regex, "regex");
        return regex.split(charSequence, n);
    }

    @SinceKotlin(version="1.6")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    @InlineOnly
    private static final Sequence<String> splitToSequence(CharSequence charSequence, Regex regex, int n) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(regex, "regex");
        return regex.splitToSequence(charSequence, n);
    }

    static Sequence splitToSequence$default(CharSequence charSequence, Regex regex, int n, int n2, Object object) {
        if ((n2 & 2) != 0) {
            n = 0;
        }
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(regex, "regex");
        return regex.splitToSequence(charSequence, n);
    }

    @NotNull
    public static final Sequence<String> lineSequence(@NotNull CharSequence charSequence) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        String[] stringArray = new String[]{"\r\n", "\n", "\r"};
        return StringsKt.splitToSequence$default(charSequence, stringArray, false, 0, 6, null);
    }

    @NotNull
    public static final List<String> lines(@NotNull CharSequence charSequence) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        return SequencesKt.toList(StringsKt.lineSequence(charSequence));
    }

    public static final boolean contentEqualsIgnoreCaseImpl(@Nullable CharSequence charSequence, @Nullable CharSequence charSequence2) {
        if (charSequence instanceof String && charSequence2 instanceof String) {
            return StringsKt.equals((String)charSequence, (String)charSequence2, true);
        }
        if (charSequence == charSequence2) {
            return false;
        }
        if (charSequence == null || charSequence2 == null || charSequence.length() != charSequence2.length()) {
            return true;
        }
        int n = charSequence.length();
        for (int i = 0; i < n; ++i) {
            if (CharsKt.equals(charSequence.charAt(i), charSequence2.charAt(i), true)) continue;
            return true;
        }
        return false;
    }

    public static final boolean contentEqualsImpl(@Nullable CharSequence charSequence, @Nullable CharSequence charSequence2) {
        if (charSequence instanceof String && charSequence2 instanceof String) {
            return Intrinsics.areEqual(charSequence, charSequence2);
        }
        if (charSequence == charSequence2) {
            return false;
        }
        if (charSequence == null || charSequence2 == null || charSequence.length() != charSequence2.length()) {
            return true;
        }
        int n = charSequence.length();
        for (int i = 0; i < n; ++i) {
            if (charSequence.charAt(i) == charSequence2.charAt(i)) continue;
            return true;
        }
        return false;
    }

    @SinceKotlin(version="1.5")
    public static final boolean toBooleanStrict(@NotNull String string) {
        boolean bl;
        Intrinsics.checkNotNullParameter(string, "<this>");
        String string2 = string;
        if (Intrinsics.areEqual(string2, "true")) {
            bl = true;
        } else if (Intrinsics.areEqual(string2, "false")) {
            bl = false;
        } else {
            throw new IllegalArgumentException("The string doesn't represent a boolean value: " + string);
        }
        return bl;
    }

    @SinceKotlin(version="1.5")
    @Nullable
    public static final Boolean toBooleanStrictOrNull(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        String string2 = string;
        return Intrinsics.areEqual(string2, "true") ? Boolean.valueOf(true) : (Intrinsics.areEqual(string2, "false") ? Boolean.valueOf(false) : null);
    }

    public static final Pair access$findAnyOf(CharSequence charSequence, Collection collection, int n, boolean bl, boolean bl2) {
        return StringsKt__StringsKt.findAnyOf$StringsKt__StringsKt(charSequence, collection, n, bl, bl2);
    }
}

