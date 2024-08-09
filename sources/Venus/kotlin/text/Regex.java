/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.ExperimentalStdlibApi;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.ResultKt;
import kotlin.SinceKotlin;
import kotlin.Unit;
import kotlin.WasExperimental;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequenceScope;
import kotlin.sequences.SequencesKt;
import kotlin.text.FlagEnum;
import kotlin.text.MatchResult;
import kotlin.text.MatcherMatchResult;
import kotlin.text.Regex;
import kotlin.text.RegexKt;
import kotlin.text.RegexOption;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\r\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 02\u00060\u0001j\u0002`\u0002:\u000201B\u000f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005B\u0017\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bB\u001d\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n\u00a2\u0006\u0002\u0010\u000bB\u000f\b\u0001\u0012\u0006\u0010\f\u001a\u00020\r\u00a2\u0006\u0002\u0010\u000eJ\u000e\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017J\u001a\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u001a\u001a\u00020\u001bJ\u001e\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00190\u001d2\u0006\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u001a\u001a\u00020\u001bJ\u001a\u0010\u001e\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u001f\u001a\u00020\u001bH\u0007J\u0010\u0010 \u001a\u0004\u0018\u00010\u00192\u0006\u0010\u0016\u001a\u00020\u0017J\u0011\u0010!\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0086\u0004J\u0018\u0010\"\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u001f\u001a\u00020\u001bH\u0007J\"\u0010#\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u00172\u0012\u0010$\u001a\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u00170%J\u0016\u0010#\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010&\u001a\u00020\u0004J\u0016\u0010'\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010&\u001a\u00020\u0004J\u001e\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00040)2\u0006\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010*\u001a\u00020\u001bJ \u0010+\u001a\b\u0012\u0004\u0012\u00020\u00040\u001d2\u0006\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010*\u001a\u00020\u001bH\u0007J\u0006\u0010,\u001a\u00020\rJ\b\u0010-\u001a\u00020\u0004H\u0016J\b\u0010.\u001a\u00020/H\u0002R\u0016\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n8F\u00a2\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0003\u001a\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013\u00a8\u00062"}, d2={"Lkotlin/text/Regex;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "pattern", "", "(Ljava/lang/String;)V", "option", "Lkotlin/text/RegexOption;", "(Ljava/lang/String;Lkotlin/text/RegexOption;)V", "options", "", "(Ljava/lang/String;Ljava/util/Set;)V", "nativePattern", "Ljava/util/regex/Pattern;", "(Ljava/util/regex/Pattern;)V", "_options", "getOptions", "()Ljava/util/Set;", "getPattern", "()Ljava/lang/String;", "containsMatchIn", "", "input", "", "find", "Lkotlin/text/MatchResult;", "startIndex", "", "findAll", "Lkotlin/sequences/Sequence;", "matchAt", "index", "matchEntire", "matches", "matchesAt", "replace", "transform", "Lkotlin/Function1;", "replacement", "replaceFirst", "split", "", "limit", "splitToSequence", "toPattern", "toString", "writeReplace", "", "Companion", "Serialized", "kotlin-stdlib"})
@SourceDebugExtension(value={"SMAP\nRegex.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Regex.kt\nkotlin/text/Regex\n+ 2 Regex.kt\nkotlin/text/RegexKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,396:1\n22#2,3:397\n1#3:400\n*S KotlinDebug\n*F\n+ 1 Regex.kt\nkotlin/text/Regex\n*L\n102#1:397,3\n*E\n"})
public final class Regex
implements Serializable {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final Pattern nativePattern;
    @Nullable
    private Set<? extends RegexOption> _options;

    @PublishedApi
    public Regex(@NotNull Pattern pattern) {
        Intrinsics.checkNotNullParameter(pattern, "nativePattern");
        this.nativePattern = pattern;
    }

    public Regex(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "pattern");
        Pattern pattern = Pattern.compile(string);
        Intrinsics.checkNotNullExpressionValue(pattern, "compile(pattern)");
        this(pattern);
    }

    public Regex(@NotNull String string, @NotNull RegexOption regexOption) {
        Intrinsics.checkNotNullParameter(string, "pattern");
        Intrinsics.checkNotNullParameter(regexOption, "option");
        Pattern pattern = Pattern.compile(string, kotlin.text.Regex$Companion.access$ensureUnicodeCase(Companion, regexOption.getValue()));
        Intrinsics.checkNotNullExpressionValue(pattern, "compile(pattern, ensureUnicodeCase(option.value))");
        this(pattern);
    }

    public Regex(@NotNull String string, @NotNull Set<? extends RegexOption> set) {
        Intrinsics.checkNotNullParameter(string, "pattern");
        Intrinsics.checkNotNullParameter(set, "options");
        Pattern pattern = Pattern.compile(string, kotlin.text.Regex$Companion.access$ensureUnicodeCase(Companion, RegexKt.access$toInt(set)));
        Intrinsics.checkNotNullExpressionValue(pattern, "compile(pattern, ensureU\u2026odeCase(options.toInt()))");
        this(pattern);
    }

    @NotNull
    public final String getPattern() {
        String string = this.nativePattern.pattern();
        Intrinsics.checkNotNullExpressionValue(string, "nativePattern.pattern()");
        return string;
    }

    @NotNull
    public final Set<RegexOption> getOptions() {
        Set<RegexOption> set = this._options;
        if (set == null) {
            Set set2;
            EnumSet<RegexOption> enumSet;
            int n = this.nativePattern.flags();
            boolean bl = false;
            EnumSet<RegexOption> enumSet2 = enumSet = EnumSet.allOf(RegexOption.class);
            boolean bl2 = false;
            Intrinsics.checkNotNullExpressionValue(enumSet2, "fromInt$lambda$1");
            CollectionsKt.retainAll((Iterable)enumSet2, (Function1)new Function1<RegexOption, Boolean>(n){
                final int $value;
                {
                    this.$value = n;
                    super(1);
                }

                /*
                 * Ignored method signature, as it can't be verified against descriptor
                 */
                @NotNull
                public final Boolean invoke(Enum enum_) {
                    return (this.$value & ((FlagEnum)((Object)enum_)).getMask()) == ((FlagEnum)((Object)enum_)).getValue();
                }

                public Object invoke(Object object) {
                    return this.invoke((Enum)object);
                }
            });
            Set set3 = Collections.unmodifiableSet((Set)enumSet);
            Intrinsics.checkNotNullExpressionValue(set3, "unmodifiableSet(EnumSet.\u2026mask == it.value }\n    })");
            Set set4 = set2 = set3;
            boolean bl3 = false;
            this._options = set4;
            set = set2;
        }
        return set;
    }

    public final boolean matches(@NotNull CharSequence charSequence) {
        Intrinsics.checkNotNullParameter(charSequence, "input");
        return this.nativePattern.matcher(charSequence).matches();
    }

    public final boolean containsMatchIn(@NotNull CharSequence charSequence) {
        Intrinsics.checkNotNullParameter(charSequence, "input");
        return this.nativePattern.matcher(charSequence).find();
    }

    @Nullable
    public final MatchResult find(@NotNull CharSequence charSequence, int n) {
        Intrinsics.checkNotNullParameter(charSequence, "input");
        Matcher matcher = this.nativePattern.matcher(charSequence);
        Intrinsics.checkNotNullExpressionValue(matcher, "nativePattern.matcher(input)");
        return RegexKt.access$findNext(matcher, n, charSequence);
    }

    public static MatchResult find$default(Regex regex, CharSequence charSequence, int n, int n2, Object object) {
        if ((n2 & 2) != 0) {
            n = 0;
        }
        return regex.find(charSequence, n);
    }

    @NotNull
    public final Sequence<MatchResult> findAll(@NotNull CharSequence charSequence, int n) {
        Intrinsics.checkNotNullParameter(charSequence, "input");
        if (n < 0 || n > charSequence.length()) {
            throw new IndexOutOfBoundsException("Start index out of bounds: " + n + ", input length: " + charSequence.length());
        }
        return SequencesKt.generateSequence((Function0)new Function0<MatchResult>(this, charSequence, n){
            final Regex this$0;
            final CharSequence $input;
            final int $startIndex;
            {
                this.this$0 = regex;
                this.$input = charSequence;
                this.$startIndex = n;
                super(0);
            }

            @Nullable
            public final MatchResult invoke() {
                return this.this$0.find(this.$input, this.$startIndex);
            }

            public Object invoke() {
                return this.invoke();
            }
        }, (Function1)findAll.2.INSTANCE);
    }

    public static Sequence findAll$default(Regex regex, CharSequence charSequence, int n, int n2, Object object) {
        if ((n2 & 2) != 0) {
            n = 0;
        }
        return regex.findAll(charSequence, n);
    }

    @Nullable
    public final MatchResult matchEntire(@NotNull CharSequence charSequence) {
        Intrinsics.checkNotNullParameter(charSequence, "input");
        Matcher matcher = this.nativePattern.matcher(charSequence);
        Intrinsics.checkNotNullExpressionValue(matcher, "nativePattern.matcher(input)");
        return RegexKt.access$matchEntire(matcher, charSequence);
    }

    @SinceKotlin(version="1.7")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    @Nullable
    public final MatchResult matchAt(@NotNull CharSequence charSequence, int n) {
        MatcherMatchResult matcherMatchResult;
        Matcher matcher;
        Intrinsics.checkNotNullParameter(charSequence, "input");
        Matcher matcher2 = matcher = this.nativePattern.matcher(charSequence).useAnchoringBounds(true).useTransparentBounds(false).region(n, charSequence.length());
        boolean bl = false;
        if (matcher2.lookingAt()) {
            Intrinsics.checkNotNullExpressionValue(matcher2, "this");
            matcherMatchResult = new MatcherMatchResult(matcher2, charSequence);
        } else {
            matcherMatchResult = null;
        }
        return matcherMatchResult;
    }

    @SinceKotlin(version="1.7")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    public final boolean matchesAt(@NotNull CharSequence charSequence, int n) {
        Intrinsics.checkNotNullParameter(charSequence, "input");
        return this.nativePattern.matcher(charSequence).useAnchoringBounds(true).useTransparentBounds(false).region(n, charSequence.length()).lookingAt();
    }

    @NotNull
    public final String replace(@NotNull CharSequence charSequence, @NotNull String string) {
        Intrinsics.checkNotNullParameter(charSequence, "input");
        Intrinsics.checkNotNullParameter(string, "replacement");
        String string2 = this.nativePattern.matcher(charSequence).replaceAll(string);
        Intrinsics.checkNotNullExpressionValue(string2, "nativePattern.matcher(in\u2026).replaceAll(replacement)");
        return string2;
    }

    @NotNull
    public final String replace(@NotNull CharSequence charSequence, @NotNull Function1<? super MatchResult, ? extends CharSequence> function1) {
        Intrinsics.checkNotNullParameter(charSequence, "input");
        Intrinsics.checkNotNullParameter(function1, "transform");
        MatchResult matchResult = Regex.find$default(this, charSequence, 0, 2, null);
        if (matchResult == null) {
            return ((Object)charSequence).toString();
        }
        MatchResult matchResult2 = matchResult;
        int n = 0;
        int n2 = charSequence.length();
        StringBuilder stringBuilder = new StringBuilder(n2);
        do {
            MatchResult matchResult3 = matchResult2;
            stringBuilder.append(charSequence, n, (int)matchResult3.getRange().getStart());
            stringBuilder.append(function1.invoke(matchResult3));
            n = matchResult3.getRange().getEndInclusive() + 1;
            matchResult2 = matchResult3.next();
        } while (n < n2 && matchResult2 != null);
        if (n < n2) {
            stringBuilder.append(charSequence, n, n2);
        }
        String string = stringBuilder.toString();
        Intrinsics.checkNotNullExpressionValue(string, "sb.toString()");
        return string;
    }

    @NotNull
    public final String replaceFirst(@NotNull CharSequence charSequence, @NotNull String string) {
        Intrinsics.checkNotNullParameter(charSequence, "input");
        Intrinsics.checkNotNullParameter(string, "replacement");
        String string2 = this.nativePattern.matcher(charSequence).replaceFirst(string);
        Intrinsics.checkNotNullExpressionValue(string2, "nativePattern.matcher(in\u2026replaceFirst(replacement)");
        return string2;
    }

    @NotNull
    public final List<String> split(@NotNull CharSequence charSequence, int n) {
        Intrinsics.checkNotNullParameter(charSequence, "input");
        StringsKt.requireNonNegativeLimit(n);
        Matcher matcher = this.nativePattern.matcher(charSequence);
        if (n == 1 || !matcher.find()) {
            return CollectionsKt.listOf(((Object)charSequence).toString());
        }
        ArrayList<String> arrayList = new ArrayList<String>(n > 0 ? RangesKt.coerceAtMost(n, 10) : 10);
        int n2 = 0;
        int n3 = n - 1;
        do {
            arrayList.add(((Object)charSequence.subSequence(n2, matcher.start())).toString());
            n2 = matcher.end();
        } while ((n3 < 0 || arrayList.size() != n3) && matcher.find());
        arrayList.add(((Object)charSequence.subSequence(n2, charSequence.length())).toString());
        return arrayList;
    }

    public static List split$default(Regex regex, CharSequence charSequence, int n, int n2, Object object) {
        if ((n2 & 2) != 0) {
            n = 0;
        }
        return regex.split(charSequence, n);
    }

    @SinceKotlin(version="1.6")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    @NotNull
    public final Sequence<String> splitToSequence(@NotNull CharSequence charSequence, int n) {
        Intrinsics.checkNotNullParameter(charSequence, "input");
        StringsKt.requireNonNegativeLimit(n);
        return SequencesKt.sequence((Function2)new Function2<SequenceScope<? super String>, Continuation<? super Unit>, Object>(this, charSequence, n, null){
            Object L$1;
            int I$0;
            int label;
            private Object L$0;
            final Regex this$0;
            final CharSequence $input;
            final int $limit;
            {
                this.this$0 = regex;
                this.$input = charSequence;
                this.$limit = n;
                super(2, continuation);
            }

            /*
             * Unable to fully structure code
             */
            @Nullable
            public final Object invokeSuspend(@NotNull Object var1_1) {
                var6_2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                switch (this.label) {
                    case 0: {
                        ResultKt.throwOnFailure(var1_1);
                        var2_3 = (SequenceScope)this.L$0;
                        var3_4 = Regex.access$getNativePattern$p(this.this$0).matcher(this.$input);
                        if (this.$limit != 1 && var3_4.find()) break;
                        this.label = 1;
                        v0 = var2_3.yield(this.$input.toString(), this);
                        if (v0 == var6_2) {
                            return var6_2;
                        }
                        ** GOTO lbl16
                    }
                    case 1: {
                        ResultKt.throwOnFailure(var1_1);
                        v0 = var1_1;
lbl16:
                        // 2 sources

                        return Unit.INSTANCE;
                    }
                }
                var4_5 = 0;
                var5_6 = 0;
                while (true) {
                    this.L$0 = var2_3;
                    this.L$1 = var3_4;
                    this.I$0 = var5_6;
                    this.label = 2;
                    v1 = var2_3.yield(this.$input.subSequence(var4_5, var3_4.start()).toString(), this);
                    if (v1 == var6_2) {
                        return var6_2;
                    }
                    ** GOTO lbl34
                    break;
                }
                {
                    case 2: {
                        var5_6 = this.I$0;
                        var3_4 = (Matcher)this.L$1;
                        var2_3 = (SequenceScope)this.L$0;
                        ResultKt.throwOnFailure(var1_1);
                        v1 = var1_1;
lbl34:
                        // 2 sources

                        var4_5 = var3_4.end();
                        if (++var5_6 != this.$limit - 1 && var3_4.find()) ** continue;
                        this.L$0 = null;
                        this.L$1 = null;
                        this.label = 3;
                        v2 = var2_3.yield(this.$input.subSequence(var4_5, this.$input.length()).toString(), this);
                        if (v2 == var6_2) {
                            return var6_2;
                        }
                        ** GOTO lbl46
                    }
                    case 3: {
                        ResultKt.throwOnFailure(var1_1);
                        v2 = var1_1;
lbl46:
                        // 2 sources

                        return Unit.INSTANCE;
                    }
                }
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }

            @NotNull
            public final Continuation<Unit> create(@Nullable Object object, @NotNull Continuation<?> continuation) {
                Function2<SequenceScope<? super String>, Continuation<? super Unit>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                function2.L$0 = object;
                return (Continuation)((Object)function2);
            }

            @Nullable
            public final Object invoke(@NotNull SequenceScope<? super String> sequenceScope, @Nullable Continuation<? super Unit> continuation) {
                return (this.create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            public Object invoke(Object object, Object object2) {
                return this.invoke((SequenceScope)object, (Continuation)object2);
            }
        });
    }

    public static Sequence splitToSequence$default(Regex regex, CharSequence charSequence, int n, int n2, Object object) {
        if ((n2 & 2) != 0) {
            n = 0;
        }
        return regex.splitToSequence(charSequence, n);
    }

    @NotNull
    public String toString() {
        String string = this.nativePattern.toString();
        Intrinsics.checkNotNullExpressionValue(string, "nativePattern.toString()");
        return string;
    }

    @NotNull
    public final Pattern toPattern() {
        return this.nativePattern;
    }

    private final Object writeReplace() {
        String string = this.nativePattern.pattern();
        Intrinsics.checkNotNullExpressionValue(string, "nativePattern.pattern()");
        return new Serialized(string, this.nativePattern.flags());
    }

    public static final Pattern access$getNativePattern$p(Regex regex) {
        return regex.nativePattern;
    }

    @Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0002J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007J\u000e\u0010\t\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\u0007\u00a8\u0006\f"}, d2={"Lkotlin/text/Regex$Companion;", "", "()V", "ensureUnicodeCase", "", "flags", "escape", "", "literal", "escapeReplacement", "fromLiteral", "Lkotlin/text/Regex;", "kotlin-stdlib"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Regex fromLiteral(@NotNull String string) {
            Intrinsics.checkNotNullParameter(string, "literal");
            return new Regex(string, RegexOption.LITERAL);
        }

        @NotNull
        public final String escape(@NotNull String string) {
            Intrinsics.checkNotNullParameter(string, "literal");
            String string2 = Pattern.quote(string);
            Intrinsics.checkNotNullExpressionValue(string2, "quote(literal)");
            return string2;
        }

        @NotNull
        public final String escapeReplacement(@NotNull String string) {
            Intrinsics.checkNotNullParameter(string, "literal");
            String string2 = Matcher.quoteReplacement(string);
            Intrinsics.checkNotNullExpressionValue(string2, "quoteReplacement(literal)");
            return string2;
        }

        private final int ensureUnicodeCase(int n) {
            return (n & 2) != 0 ? n | 0x40 : n;
        }

        public static final int access$ensureUnicodeCase(Companion companion, int n) {
            return companion.ensureUnicodeCase(n);
        }

        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    @Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0002\u0018\u0000 \u000e2\u00060\u0001j\u0002`\u0002:\u0001\u000eB\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J\b\u0010\f\u001a\u00020\rH\u0002R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000f"}, d2={"Lkotlin/text/Regex$Serialized;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "pattern", "", "flags", "", "(Ljava/lang/String;I)V", "getFlags", "()I", "getPattern", "()Ljava/lang/String;", "readResolve", "", "Companion", "kotlin-stdlib"})
    private static final class Serialized
    implements Serializable {
        @NotNull
        public static final Companion Companion = new Companion(null);
        @NotNull
        private final String pattern;
        private final int flags;
        private static final long serialVersionUID = 0L;

        public Serialized(@NotNull String string, int n) {
            Intrinsics.checkNotNullParameter(string, "pattern");
            this.pattern = string;
            this.flags = n;
        }

        @NotNull
        public final String getPattern() {
            return this.pattern;
        }

        public final int getFlags() {
            return this.flags;
        }

        private final Object readResolve() {
            Pattern pattern = Pattern.compile(this.pattern, this.flags);
            Intrinsics.checkNotNullExpressionValue(pattern, "compile(pattern, flags)");
            return new Regex(pattern);
        }

        @Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2={"Lkotlin/text/Regex$Serialized$Companion;", "", "()V", "serialVersionUID", "", "kotlin-stdlib"})
        public static final class Companion {
            private Companion() {
            }

            public Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }
    }
}

