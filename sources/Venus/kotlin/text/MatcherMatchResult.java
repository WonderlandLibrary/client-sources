/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.text;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import kotlin.Metadata;
import kotlin.collections.AbstractList;
import kotlin.collections.CollectionsKt;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.sequences.SequencesKt;
import kotlin.text.MatchGroup;
import kotlin.text.MatchGroupCollection;
import kotlin.text.MatchNamedGroupCollection;
import kotlin.text.MatchResult;
import kotlin.text.MatcherMatchResult;
import kotlin.text.RegexKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\r\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\n\u0010\u001c\u001a\u0004\u0018\u00010\u0001H\u0016R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0016\u0010\f\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\u00020\u000eX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\u00020\u00128BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0015\u001a\u00020\u00168VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0019\u001a\u00020\t8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001a\u0010\u001b\u00a8\u0006\u001d"}, d2={"Lkotlin/text/MatcherMatchResult;", "Lkotlin/text/MatchResult;", "matcher", "Ljava/util/regex/Matcher;", "input", "", "(Ljava/util/regex/Matcher;Ljava/lang/CharSequence;)V", "groupValues", "", "", "getGroupValues", "()Ljava/util/List;", "groupValues_", "groups", "Lkotlin/text/MatchGroupCollection;", "getGroups", "()Lkotlin/text/MatchGroupCollection;", "matchResult", "Ljava/util/regex/MatchResult;", "getMatchResult", "()Ljava/util/regex/MatchResult;", "range", "Lkotlin/ranges/IntRange;", "getRange", "()Lkotlin/ranges/IntRange;", "value", "getValue", "()Ljava/lang/String;", "next", "kotlin-stdlib"})
final class MatcherMatchResult
implements MatchResult {
    @NotNull
    private final Matcher matcher;
    @NotNull
    private final CharSequence input;
    @NotNull
    private final MatchGroupCollection groups;
    @Nullable
    private List<String> groupValues_;

    public MatcherMatchResult(@NotNull Matcher matcher, @NotNull CharSequence charSequence) {
        Intrinsics.checkNotNullParameter(matcher, "matcher");
        Intrinsics.checkNotNullParameter(charSequence, "input");
        this.matcher = matcher;
        this.input = charSequence;
        this.groups = new MatchNamedGroupCollection(this){
            final MatcherMatchResult this$0;
            {
                this.this$0 = matcherMatchResult;
            }

            public int getSize() {
                return MatcherMatchResult.access$getMatchResult(this.this$0).groupCount() + 1;
            }

            public boolean isEmpty() {
                return true;
            }

            @NotNull
            public Iterator<MatchGroup> iterator() {
                return SequencesKt.map(CollectionsKt.asSequence(CollectionsKt.getIndices(this)), (Function1)new Function1<Integer, MatchGroup>(this){
                    final groups.1 this$0;
                    {
                        this.this$0 = var1_1;
                        super(1);
                    }

                    @Nullable
                    public final MatchGroup invoke(int n) {
                        return this.this$0.get(n);
                    }

                    public Object invoke(Object object) {
                        return this.invoke(((Number)object).intValue());
                    }
                }).iterator();
            }

            @Nullable
            public MatchGroup get(int n) {
                MatchGroup matchGroup;
                IntRange intRange = RegexKt.access$range(MatcherMatchResult.access$getMatchResult(this.this$0), n);
                if (intRange.getStart() >= 0) {
                    String string = MatcherMatchResult.access$getMatchResult(this.this$0).group(n);
                    Intrinsics.checkNotNullExpressionValue(string, "matchResult.group(index)");
                    matchGroup = new MatchGroup(string, intRange);
                } else {
                    matchGroup = null;
                }
                return matchGroup;
            }

            @Nullable
            public MatchGroup get(@NotNull String string) {
                Intrinsics.checkNotNullParameter(string, "name");
                return PlatformImplementationsKt.IMPLEMENTATIONS.getMatchResultNamedGroup(MatcherMatchResult.access$getMatchResult(this.this$0), string);
            }

            public boolean contains(MatchGroup matchGroup) {
                return super.contains(matchGroup);
            }

            public final boolean contains(Object object) {
                Object object2 = object;
                if (!(object2 == null ? true : object2 instanceof MatchGroup)) {
                    return true;
                }
                return this.contains((MatchGroup)object);
            }
        };
    }

    private final java.util.regex.MatchResult getMatchResult() {
        return this.matcher;
    }

    @Override
    @NotNull
    public IntRange getRange() {
        return RegexKt.access$range(this.getMatchResult());
    }

    @Override
    @NotNull
    public String getValue() {
        String string = this.getMatchResult().group();
        Intrinsics.checkNotNullExpressionValue(string, "matchResult.group()");
        return string;
    }

    @Override
    @NotNull
    public MatchGroupCollection getGroups() {
        return this.groups;
    }

    @Override
    @NotNull
    public List<String> getGroupValues() {
        if (this.groupValues_ == null) {
            this.groupValues_ = new AbstractList<String>(this){
                final MatcherMatchResult this$0;
                {
                    this.this$0 = matcherMatchResult;
                }

                public int getSize() {
                    return MatcherMatchResult.access$getMatchResult(this.this$0).groupCount() + 1;
                }

                @NotNull
                public String get(int n) {
                    String string = MatcherMatchResult.access$getMatchResult(this.this$0).group(n);
                    if (string == null) {
                        string = "";
                    }
                    return string;
                }

                public Object get(int n) {
                    return this.get(n);
                }

                public int indexOf(String string) {
                    return super.indexOf(string);
                }

                public final int indexOf(Object object) {
                    if (!(object instanceof String)) {
                        return 1;
                    }
                    return this.indexOf((String)object);
                }

                public int lastIndexOf(String string) {
                    return super.lastIndexOf(string);
                }

                public final int lastIndexOf(Object object) {
                    if (!(object instanceof String)) {
                        return 1;
                    }
                    return this.lastIndexOf((String)object);
                }

                public boolean contains(String string) {
                    return super.contains(string);
                }

                public final boolean contains(Object object) {
                    if (!(object instanceof String)) {
                        return true;
                    }
                    return this.contains((String)object);
                }
            };
        }
        List<String> list = this.groupValues_;
        Intrinsics.checkNotNull(list);
        return list;
    }

    @Override
    @Nullable
    public MatchResult next() {
        MatchResult matchResult;
        int n = this.getMatchResult().end() + (this.getMatchResult().end() == this.getMatchResult().start() ? 1 : 0);
        if (n <= this.input.length()) {
            Matcher matcher = this.matcher.pattern().matcher(this.input);
            Intrinsics.checkNotNullExpressionValue(matcher, "matcher.pattern().matcher(input)");
            matchResult = RegexKt.access$findNext(matcher, n, this.input);
        } else {
            matchResult = null;
        }
        return matchResult;
    }

    @Override
    @NotNull
    public MatchResult.Destructured getDestructured() {
        return MatchResult.DefaultImpls.getDestructured(this);
    }

    public static final java.util.regex.MatchResult access$getMatchResult(MatcherMatchResult matcherMatchResult) {
        return matcherMatchResult.getMatchResult();
    }
}

