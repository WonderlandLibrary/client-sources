/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.text;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.text.FlagEnum;
import kotlin.text.MatchResult;
import kotlin.text.MatcherMatchResult;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=2, xi=48, d1={"\u0000>\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\r\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0000\u001a-\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0014\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003*\b\u0012\u0004\u0012\u0002H\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0082\b\u001a\u001e\u0010\u0007\u001a\u0004\u0018\u00010\b*\u00020\t2\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\fH\u0002\u001a\u0016\u0010\r\u001a\u0004\u0018\u00010\b*\u00020\t2\u0006\u0010\u000b\u001a\u00020\fH\u0002\u001a\f\u0010\u000e\u001a\u00020\u000f*\u00020\u0010H\u0002\u001a\u0014\u0010\u000e\u001a\u00020\u000f*\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0006H\u0002\u001a\u0012\u0010\u0012\u001a\u00020\u0006*\b\u0012\u0004\u0012\u00020\u00030\u0013H\u0002\u00a8\u0006\u0014"}, d2={"fromInt", "", "T", "Lkotlin/text/FlagEnum;", "", "value", "", "findNext", "Lkotlin/text/MatchResult;", "Ljava/util/regex/Matcher;", "from", "input", "", "matchEntire", "range", "Lkotlin/ranges/IntRange;", "Ljava/util/regex/MatchResult;", "groupIndex", "toInt", "", "kotlin-stdlib"})
@SourceDebugExtension(value={"SMAP\nRegex.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Regex.kt\nkotlin/text/RegexKt\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,396:1\n1789#2,3:397\n*S KotlinDebug\n*F\n+ 1 Regex.kt\nkotlin/text/RegexKt\n*L\n19#1:397,3\n*E\n"})
public final class RegexKt {
    private static final int toInt(Iterable<? extends FlagEnum> iterable) {
        Iterable<? extends FlagEnum> iterable2 = iterable;
        int n = 0;
        boolean bl = false;
        int n2 = n;
        Iterator<? extends FlagEnum> iterator2 = iterable2.iterator();
        while (iterator2.hasNext()) {
            FlagEnum flagEnum;
            FlagEnum flagEnum2 = flagEnum = iterator2.next();
            int n3 = n2;
            boolean bl2 = false;
            n2 = n3 | flagEnum2.getValue();
        }
        return n2;
    }

    private static final <T extends Enum<T>> Set<T> fromInt(int n) {
        EnumSet<Enum> enumSet;
        boolean bl = false;
        Intrinsics.reifiedOperationMarker(4, "T");
        EnumSet<Enum> enumSet2 = enumSet = EnumSet.allOf(Enum.class);
        boolean bl2 = false;
        Intrinsics.checkNotNullExpressionValue(enumSet2, "fromInt$lambda$1");
        Iterable iterable = enumSet2;
        Intrinsics.needClassReification();
        CollectionsKt.retainAll(iterable, (Function1)new Function1<T, Boolean>(n){
            final int $value;
            {
                this.$value = n;
                super(1);
            }

            @NotNull
            public final Boolean invoke(T t) {
                return (this.$value & ((FlagEnum)t).getMask()) == ((FlagEnum)t).getValue();
            }

            public Object invoke(Object object) {
                return this.invoke((T)((Enum)object));
            }
        });
        Set set = Collections.unmodifiableSet((Set)enumSet);
        Intrinsics.checkNotNullExpressionValue(set, "unmodifiableSet(EnumSet.\u2026mask == it.value }\n    })");
        return set;
    }

    private static final MatchResult findNext(Matcher matcher, int n, CharSequence charSequence) {
        return !matcher.find(n) ? null : (MatchResult)new MatcherMatchResult(matcher, charSequence);
    }

    private static final MatchResult matchEntire(Matcher matcher, CharSequence charSequence) {
        return !matcher.matches() ? null : (MatchResult)new MatcherMatchResult(matcher, charSequence);
    }

    private static final IntRange range(java.util.regex.MatchResult matchResult) {
        return RangesKt.until(matchResult.start(), matchResult.end());
    }

    private static final IntRange range(java.util.regex.MatchResult matchResult, int n) {
        return RangesKt.until(matchResult.start(n), matchResult.end(n));
    }

    public static final int access$toInt(Iterable iterable) {
        return RegexKt.toInt(iterable);
    }

    public static final MatchResult access$findNext(Matcher matcher, int n, CharSequence charSequence) {
        return RegexKt.findNext(matcher, n, charSequence);
    }

    public static final MatchResult access$matchEntire(Matcher matcher, CharSequence charSequence) {
        return RegexKt.matchEntire(matcher, charSequence);
    }

    public static final IntRange access$range(java.util.regex.MatchResult matchResult) {
        return RegexKt.range(matchResult);
    }

    public static final IntRange access$range(java.util.regex.MatchResult matchResult, int n) {
        return RegexKt.range(matchResult, n);
    }
}

