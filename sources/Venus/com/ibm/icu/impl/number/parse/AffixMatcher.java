/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.number.parse;

import com.ibm.icu.impl.StandardPlural;
import com.ibm.icu.impl.StringSegment;
import com.ibm.icu.impl.number.AffixPatternProvider;
import com.ibm.icu.impl.number.AffixUtils;
import com.ibm.icu.impl.number.PatternStringUtils;
import com.ibm.icu.impl.number.parse.AffixPatternMatcher;
import com.ibm.icu.impl.number.parse.AffixTokenMatcherFactory;
import com.ibm.icu.impl.number.parse.IgnorablesMatcher;
import com.ibm.icu.impl.number.parse.NumberParseMatcher;
import com.ibm.icu.impl.number.parse.NumberParserImpl;
import com.ibm.icu.impl.number.parse.ParsedNumber;
import com.ibm.icu.number.NumberFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

public class AffixMatcher
implements NumberParseMatcher {
    private final AffixPatternMatcher prefix;
    private final AffixPatternMatcher suffix;
    private final int flags;
    public static final Comparator<AffixMatcher> COMPARATOR = new Comparator<AffixMatcher>(){

        @Override
        public int compare(AffixMatcher affixMatcher, AffixMatcher affixMatcher2) {
            if (AffixMatcher.access$100(AffixMatcher.access$000(affixMatcher)) != AffixMatcher.access$100(AffixMatcher.access$000(affixMatcher2))) {
                return AffixMatcher.access$100(AffixMatcher.access$000(affixMatcher)) > AffixMatcher.access$100(AffixMatcher.access$000(affixMatcher2)) ? -1 : 1;
            }
            if (AffixMatcher.access$100(AffixMatcher.access$200(affixMatcher)) != AffixMatcher.access$100(AffixMatcher.access$200(affixMatcher2))) {
                return AffixMatcher.access$100(AffixMatcher.access$200(affixMatcher)) > AffixMatcher.access$100(AffixMatcher.access$200(affixMatcher2)) ? -1 : 1;
            }
            if (!affixMatcher.equals(affixMatcher2)) {
                return affixMatcher.hashCode() > affixMatcher2.hashCode() ? -1 : 1;
            }
            return 1;
        }

        @Override
        public int compare(Object object, Object object2) {
            return this.compare((AffixMatcher)object, (AffixMatcher)object2);
        }
    };

    private static boolean isInteresting(AffixPatternProvider affixPatternProvider, IgnorablesMatcher ignorablesMatcher, int n) {
        String string = affixPatternProvider.getString(256);
        String string2 = affixPatternProvider.getString(0);
        String string3 = null;
        String string4 = null;
        if (affixPatternProvider.hasNegativeSubpattern()) {
            string3 = affixPatternProvider.getString(768);
            string4 = affixPatternProvider.getString(512);
        }
        return 0 == (n & 0x100) && AffixUtils.containsOnlySymbolsAndIgnorables(string, ignorablesMatcher.getSet()) && AffixUtils.containsOnlySymbolsAndIgnorables(string2, ignorablesMatcher.getSet()) && AffixUtils.containsOnlySymbolsAndIgnorables(string3, ignorablesMatcher.getSet()) && AffixUtils.containsOnlySymbolsAndIgnorables(string4, ignorablesMatcher.getSet()) && !AffixUtils.containsType(string2, -2) && !AffixUtils.containsType(string2, -1) && !AffixUtils.containsType(string4, -2) && !AffixUtils.containsType(string4, -1);
    }

    public static void createMatchers(AffixPatternProvider affixPatternProvider, NumberParserImpl numberParserImpl, AffixTokenMatcherFactory affixTokenMatcherFactory, IgnorablesMatcher ignorablesMatcher, int n) {
        if (!AffixMatcher.isInteresting(affixPatternProvider, ignorablesMatcher, n)) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<AffixMatcher> arrayList = new ArrayList<AffixMatcher>(6);
        boolean bl = 0 != (n & 0x80);
        NumberFormatter.SignDisplay signDisplay = 0 != (n & 0x400) ? NumberFormatter.SignDisplay.ALWAYS : NumberFormatter.SignDisplay.AUTO;
        AffixPatternMatcher affixPatternMatcher = null;
        AffixPatternMatcher affixPatternMatcher2 = null;
        for (int i = 1; i >= -1; --i) {
            PatternStringUtils.patternInfoToStringBuilder(affixPatternProvider, true, i, signDisplay, StandardPlural.OTHER, false, stringBuilder);
            AffixPatternMatcher affixPatternMatcher3 = AffixPatternMatcher.fromAffixPattern(stringBuilder.toString(), affixTokenMatcherFactory, n);
            PatternStringUtils.patternInfoToStringBuilder(affixPatternProvider, false, i, signDisplay, StandardPlural.OTHER, false, stringBuilder);
            AffixPatternMatcher affixPatternMatcher4 = AffixPatternMatcher.fromAffixPattern(stringBuilder.toString(), affixTokenMatcherFactory, n);
            if (i == 1) {
                affixPatternMatcher = affixPatternMatcher3;
                affixPatternMatcher2 = affixPatternMatcher4;
            } else if (Objects.equals(affixPatternMatcher3, affixPatternMatcher) && Objects.equals(affixPatternMatcher4, affixPatternMatcher2)) continue;
            int n2 = i == -1 ? 1 : 0;
            arrayList.add(AffixMatcher.getInstance(affixPatternMatcher3, affixPatternMatcher4, n2));
            if (!bl || affixPatternMatcher3 == null || affixPatternMatcher4 == null) continue;
            if (i == 1 || !Objects.equals(affixPatternMatcher3, affixPatternMatcher)) {
                arrayList.add(AffixMatcher.getInstance(affixPatternMatcher3, null, n2));
            }
            if (i != 1 && Objects.equals(affixPatternMatcher4, affixPatternMatcher2)) continue;
            arrayList.add(AffixMatcher.getInstance(null, affixPatternMatcher4, n2));
        }
        Collections.sort(arrayList, COMPARATOR);
        numberParserImpl.addMatchers(arrayList);
    }

    private static final AffixMatcher getInstance(AffixPatternMatcher affixPatternMatcher, AffixPatternMatcher affixPatternMatcher2, int n) {
        return new AffixMatcher(affixPatternMatcher, affixPatternMatcher2, n);
    }

    private AffixMatcher(AffixPatternMatcher affixPatternMatcher, AffixPatternMatcher affixPatternMatcher2, int n) {
        this.prefix = affixPatternMatcher;
        this.suffix = affixPatternMatcher2;
        this.flags = n;
    }

    @Override
    public boolean match(StringSegment stringSegment, ParsedNumber parsedNumber) {
        if (!parsedNumber.seenNumber()) {
            if (parsedNumber.prefix != null || this.prefix == null) {
                return true;
            }
            int n = stringSegment.getOffset();
            boolean bl = this.prefix.match(stringSegment, parsedNumber);
            if (n != stringSegment.getOffset()) {
                parsedNumber.prefix = this.prefix.getPattern();
            }
            return bl;
        }
        if (parsedNumber.suffix != null || this.suffix == null || !AffixMatcher.matched(this.prefix, parsedNumber.prefix)) {
            return true;
        }
        int n = stringSegment.getOffset();
        boolean bl = this.suffix.match(stringSegment, parsedNumber);
        if (n != stringSegment.getOffset()) {
            parsedNumber.suffix = this.suffix.getPattern();
        }
        return bl;
    }

    @Override
    public boolean smokeTest(StringSegment stringSegment) {
        return this.prefix != null && this.prefix.smokeTest(stringSegment) || this.suffix != null && this.suffix.smokeTest(stringSegment);
    }

    @Override
    public void postProcess(ParsedNumber parsedNumber) {
        if (AffixMatcher.matched(this.prefix, parsedNumber.prefix) && AffixMatcher.matched(this.suffix, parsedNumber.suffix)) {
            if (parsedNumber.prefix == null) {
                parsedNumber.prefix = "";
            }
            if (parsedNumber.suffix == null) {
                parsedNumber.suffix = "";
            }
            parsedNumber.flags |= this.flags;
            if (this.prefix != null) {
                this.prefix.postProcess(parsedNumber);
            }
            if (this.suffix != null) {
                this.suffix.postProcess(parsedNumber);
            }
        }
    }

    static boolean matched(AffixPatternMatcher affixPatternMatcher, String string) {
        return affixPatternMatcher == null && string == null || affixPatternMatcher != null && affixPatternMatcher.getPattern().equals(string);
    }

    private static int length(AffixPatternMatcher affixPatternMatcher) {
        return affixPatternMatcher == null ? 0 : affixPatternMatcher.getPattern().length();
    }

    public boolean equals(Object object) {
        if (!(object instanceof AffixMatcher)) {
            return true;
        }
        AffixMatcher affixMatcher = (AffixMatcher)object;
        return Objects.equals(this.prefix, affixMatcher.prefix) && Objects.equals(this.suffix, affixMatcher.suffix) && this.flags == affixMatcher.flags;
    }

    public int hashCode() {
        return Objects.hashCode(this.prefix) ^ Objects.hashCode(this.suffix) ^ this.flags;
    }

    public String toString() {
        boolean bl = 0 != (this.flags & 1);
        return "<AffixMatcher" + (bl ? ":negative " : " ") + this.prefix + "#" + this.suffix + ">";
    }

    static AffixPatternMatcher access$000(AffixMatcher affixMatcher) {
        return affixMatcher.prefix;
    }

    static int access$100(AffixPatternMatcher affixPatternMatcher) {
        return AffixMatcher.length(affixPatternMatcher);
    }

    static AffixPatternMatcher access$200(AffixMatcher affixMatcher) {
        return affixMatcher.suffix;
    }
}

