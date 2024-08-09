/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.base;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.AbstractIterator;
import com.google.common.base.CharMatcher;
import com.google.common.base.CommonMatcher;
import com.google.common.base.CommonPattern;
import com.google.common.base.JdkPattern;
import com.google.common.base.Joiner;
import com.google.common.base.Platform;
import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@GwtCompatible(emulated=true)
public final class Splitter {
    private final CharMatcher trimmer;
    private final boolean omitEmptyStrings;
    private final Strategy strategy;
    private final int limit;

    private Splitter(Strategy strategy) {
        this(strategy, false, CharMatcher.none(), Integer.MAX_VALUE);
    }

    private Splitter(Strategy strategy, boolean bl, CharMatcher charMatcher, int n) {
        this.strategy = strategy;
        this.omitEmptyStrings = bl;
        this.trimmer = charMatcher;
        this.limit = n;
    }

    public static Splitter on(char c) {
        return Splitter.on(CharMatcher.is(c));
    }

    public static Splitter on(CharMatcher charMatcher) {
        Preconditions.checkNotNull(charMatcher);
        return new Splitter(new Strategy(charMatcher){
            final CharMatcher val$separatorMatcher;
            {
                this.val$separatorMatcher = charMatcher;
            }

            public SplittingIterator iterator(Splitter splitter, CharSequence charSequence) {
                return new SplittingIterator(this, splitter, charSequence){
                    final 1 this$0;
                    {
                        this.this$0 = var1_1;
                        super(splitter, charSequence);
                    }

                    @Override
                    int separatorStart(int n) {
                        return this.this$0.val$separatorMatcher.indexIn(this.toSplit, n);
                    }

                    @Override
                    int separatorEnd(int n) {
                        return n + 1;
                    }
                };
            }

            public Iterator iterator(Splitter splitter, CharSequence charSequence) {
                return this.iterator(splitter, charSequence);
            }
        });
    }

    public static Splitter on(String string) {
        Preconditions.checkArgument(string.length() != 0, "The separator may not be the empty string.");
        if (string.length() == 1) {
            return Splitter.on(string.charAt(0));
        }
        return new Splitter(new Strategy(string){
            final String val$separator;
            {
                this.val$separator = string;
            }

            public SplittingIterator iterator(Splitter splitter, CharSequence charSequence) {
                return new SplittingIterator(this, splitter, charSequence){
                    final 2 this$0;
                    {
                        this.this$0 = var1_1;
                        super(splitter, charSequence);
                    }

                    @Override
                    public int separatorStart(int n) {
                        int n2 = this.this$0.val$separator.length();
                        int n3 = this.toSplit.length() - n2;
                        block0: for (int i = n; i <= n3; ++i) {
                            for (int j = 0; j < n2; ++j) {
                                if (this.toSplit.charAt(j + i) != this.this$0.val$separator.charAt(j)) continue block0;
                            }
                            return i;
                        }
                        return 1;
                    }

                    @Override
                    public int separatorEnd(int n) {
                        return n + this.this$0.val$separator.length();
                    }
                };
            }

            public Iterator iterator(Splitter splitter, CharSequence charSequence) {
                return this.iterator(splitter, charSequence);
            }
        });
    }

    @GwtIncompatible
    public static Splitter on(Pattern pattern) {
        return Splitter.on(new JdkPattern(pattern));
    }

    private static Splitter on(CommonPattern commonPattern) {
        Preconditions.checkArgument(!commonPattern.matcher("").matches(), "The pattern may not match the empty string: %s", (Object)commonPattern);
        return new Splitter(new Strategy(commonPattern){
            final CommonPattern val$separatorPattern;
            {
                this.val$separatorPattern = commonPattern;
            }

            public SplittingIterator iterator(Splitter splitter, CharSequence charSequence) {
                CommonMatcher commonMatcher = this.val$separatorPattern.matcher(charSequence);
                return new SplittingIterator(this, splitter, charSequence, commonMatcher){
                    final CommonMatcher val$matcher;
                    final 3 this$0;
                    {
                        this.this$0 = var1_1;
                        this.val$matcher = commonMatcher;
                        super(splitter, charSequence);
                    }

                    @Override
                    public int separatorStart(int n) {
                        return this.val$matcher.find(n) ? this.val$matcher.start() : -1;
                    }

                    @Override
                    public int separatorEnd(int n) {
                        return this.val$matcher.end();
                    }
                };
            }

            public Iterator iterator(Splitter splitter, CharSequence charSequence) {
                return this.iterator(splitter, charSequence);
            }
        });
    }

    @GwtIncompatible
    public static Splitter onPattern(String string) {
        return Splitter.on(Platform.compilePattern(string));
    }

    public static Splitter fixedLength(int n) {
        Preconditions.checkArgument(n > 0, "The length may not be less than 1");
        return new Splitter(new Strategy(n){
            final int val$length;
            {
                this.val$length = n;
            }

            public SplittingIterator iterator(Splitter splitter, CharSequence charSequence) {
                return new SplittingIterator(this, splitter, charSequence){
                    final 4 this$0;
                    {
                        this.this$0 = var1_1;
                        super(splitter, charSequence);
                    }

                    @Override
                    public int separatorStart(int n) {
                        int n2 = n + this.this$0.val$length;
                        return n2 < this.toSplit.length() ? n2 : -1;
                    }

                    @Override
                    public int separatorEnd(int n) {
                        return n;
                    }
                };
            }

            public Iterator iterator(Splitter splitter, CharSequence charSequence) {
                return this.iterator(splitter, charSequence);
            }
        });
    }

    public Splitter omitEmptyStrings() {
        return new Splitter(this.strategy, true, this.trimmer, this.limit);
    }

    public Splitter limit(int n) {
        Preconditions.checkArgument(n > 0, "must be greater than zero: %s", n);
        return new Splitter(this.strategy, this.omitEmptyStrings, this.trimmer, n);
    }

    public Splitter trimResults() {
        return this.trimResults(CharMatcher.whitespace());
    }

    public Splitter trimResults(CharMatcher charMatcher) {
        Preconditions.checkNotNull(charMatcher);
        return new Splitter(this.strategy, this.omitEmptyStrings, charMatcher, this.limit);
    }

    public Iterable<String> split(CharSequence charSequence) {
        Preconditions.checkNotNull(charSequence);
        return new Iterable<String>(this, charSequence){
            final CharSequence val$sequence;
            final Splitter this$0;
            {
                this.this$0 = splitter;
                this.val$sequence = charSequence;
            }

            @Override
            public Iterator<String> iterator() {
                return Splitter.access$000(this.this$0, this.val$sequence);
            }

            public String toString() {
                return Joiner.on(", ").appendTo(new StringBuilder().append('['), (Iterable<?>)this).append(']').toString();
            }
        };
    }

    private Iterator<String> splittingIterator(CharSequence charSequence) {
        return this.strategy.iterator(this, charSequence);
    }

    @Beta
    public List<String> splitToList(CharSequence charSequence) {
        Preconditions.checkNotNull(charSequence);
        Iterator<String> iterator2 = this.splittingIterator(charSequence);
        ArrayList<String> arrayList = new ArrayList<String>();
        while (iterator2.hasNext()) {
            arrayList.add(iterator2.next());
        }
        return Collections.unmodifiableList(arrayList);
    }

    @Beta
    public MapSplitter withKeyValueSeparator(String string) {
        return this.withKeyValueSeparator(Splitter.on(string));
    }

    @Beta
    public MapSplitter withKeyValueSeparator(char c) {
        return this.withKeyValueSeparator(Splitter.on(c));
    }

    @Beta
    public MapSplitter withKeyValueSeparator(Splitter splitter) {
        return new MapSplitter(this, splitter, null);
    }

    static Iterator access$000(Splitter splitter, CharSequence charSequence) {
        return splitter.splittingIterator(charSequence);
    }

    static CharMatcher access$200(Splitter splitter) {
        return splitter.trimmer;
    }

    static boolean access$300(Splitter splitter) {
        return splitter.omitEmptyStrings;
    }

    static int access$400(Splitter splitter) {
        return splitter.limit;
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static abstract class SplittingIterator
    extends AbstractIterator<String> {
        final CharSequence toSplit;
        final CharMatcher trimmer;
        final boolean omitEmptyStrings;
        int offset = 0;
        int limit;

        abstract int separatorStart(int var1);

        abstract int separatorEnd(int var1);

        protected SplittingIterator(Splitter splitter, CharSequence charSequence) {
            this.trimmer = Splitter.access$200(splitter);
            this.omitEmptyStrings = Splitter.access$300(splitter);
            this.limit = Splitter.access$400(splitter);
            this.toSplit = charSequence;
        }

        @Override
        protected String computeNext() {
            int n = this.offset;
            while (this.offset != -1) {
                int n2;
                int n3 = n;
                int n4 = this.separatorStart(this.offset);
                if (n4 == -1) {
                    n2 = this.toSplit.length();
                    this.offset = -1;
                } else {
                    n2 = n4;
                    this.offset = this.separatorEnd(n4);
                }
                if (this.offset == n) {
                    ++this.offset;
                    if (this.offset <= this.toSplit.length()) continue;
                    this.offset = -1;
                    continue;
                }
                while (n3 < n2 && this.trimmer.matches(this.toSplit.charAt(n3))) {
                    ++n3;
                }
                while (n2 > n3 && this.trimmer.matches(this.toSplit.charAt(n2 - 1))) {
                    --n2;
                }
                if (this.omitEmptyStrings && n3 == n2) {
                    n = this.offset;
                    continue;
                }
                if (this.limit == 1) {
                    this.offset = -1;
                    for (n2 = this.toSplit.length(); n2 > n3 && this.trimmer.matches(this.toSplit.charAt(n2 - 1)); --n2) {
                    }
                } else {
                    --this.limit;
                }
                return this.toSplit.subSequence(n3, n2).toString();
            }
            return (String)this.endOfData();
        }

        @Override
        protected Object computeNext() {
            return this.computeNext();
        }
    }

    private static interface Strategy {
        public Iterator<String> iterator(Splitter var1, CharSequence var2);
    }

    @Beta
    public static final class MapSplitter {
        private static final String INVALID_ENTRY_MESSAGE = "Chunk [%s] is not a valid entry";
        private final Splitter outerSplitter;
        private final Splitter entrySplitter;

        private MapSplitter(Splitter splitter, Splitter splitter2) {
            this.outerSplitter = splitter;
            this.entrySplitter = Preconditions.checkNotNull(splitter2);
        }

        public Map<String, String> split(CharSequence charSequence) {
            LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<String, String>();
            for (String string : this.outerSplitter.split(charSequence)) {
                Iterator iterator2 = Splitter.access$000(this.entrySplitter, string);
                Preconditions.checkArgument(iterator2.hasNext(), INVALID_ENTRY_MESSAGE, (Object)string);
                String string2 = (String)iterator2.next();
                Preconditions.checkArgument(!linkedHashMap.containsKey(string2), "Duplicate key [%s] found.", (Object)string2);
                Preconditions.checkArgument(iterator2.hasNext(), INVALID_ENTRY_MESSAGE, (Object)string);
                String string3 = (String)iterator2.next();
                linkedHashMap.put(string2, string3);
                Preconditions.checkArgument(!iterator2.hasNext(), INVALID_ENTRY_MESSAGE, (Object)string);
            }
            return Collections.unmodifiableMap(linkedHashMap);
        }

        MapSplitter(Splitter splitter, Splitter splitter2, 1 var3_3) {
            this(splitter, splitter2);
        }
    }
}

