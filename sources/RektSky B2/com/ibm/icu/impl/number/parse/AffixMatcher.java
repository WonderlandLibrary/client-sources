package com.ibm.icu.impl.number.parse;

import com.ibm.icu.number.*;
import com.ibm.icu.impl.number.*;
import java.util.*;
import com.ibm.icu.impl.*;

public class AffixMatcher implements NumberParseMatcher
{
    private final AffixPatternMatcher prefix;
    private final AffixPatternMatcher suffix;
    private final int flags;
    public static final Comparator<AffixMatcher> COMPARATOR;
    
    private static boolean isInteresting(final AffixPatternProvider patternInfo, final IgnorablesMatcher ignorables, final int parseFlags) {
        final String posPrefixString = patternInfo.getString(256);
        final String posSuffixString = patternInfo.getString(0);
        String negPrefixString = null;
        String negSuffixString = null;
        if (patternInfo.hasNegativeSubpattern()) {
            negPrefixString = patternInfo.getString(768);
            negSuffixString = patternInfo.getString(512);
        }
        return 0x0 != (parseFlags & 0x100) || !AffixUtils.containsOnlySymbolsAndIgnorables(posPrefixString, ignorables.getSet()) || !AffixUtils.containsOnlySymbolsAndIgnorables(posSuffixString, ignorables.getSet()) || !AffixUtils.containsOnlySymbolsAndIgnorables(negPrefixString, ignorables.getSet()) || !AffixUtils.containsOnlySymbolsAndIgnorables(negSuffixString, ignorables.getSet()) || AffixUtils.containsType(posSuffixString, -2) || AffixUtils.containsType(posSuffixString, -1) || AffixUtils.containsType(negSuffixString, -2) || AffixUtils.containsType(negSuffixString, -1);
    }
    
    public static void createMatchers(final AffixPatternProvider patternInfo, final NumberParserImpl output, final AffixTokenMatcherFactory factory, final IgnorablesMatcher ignorables, final int parseFlags) {
        if (!isInteresting(patternInfo, ignorables, parseFlags)) {
            return;
        }
        final StringBuilder sb = new StringBuilder();
        final ArrayList<AffixMatcher> matchers = new ArrayList<AffixMatcher>(6);
        final boolean includeUnpaired = 0x0 != (parseFlags & 0x80);
        final NumberFormatter.SignDisplay signDisplay = (0x0 != (parseFlags & 0x400)) ? NumberFormatter.SignDisplay.ALWAYS : NumberFormatter.SignDisplay.AUTO;
        AffixPatternMatcher posPrefix = null;
        AffixPatternMatcher posSuffix = null;
        for (int signum = 1; signum >= -1; --signum) {
            PatternStringUtils.patternInfoToStringBuilder(patternInfo, true, signum, signDisplay, StandardPlural.OTHER, false, sb);
            final AffixPatternMatcher prefix = AffixPatternMatcher.fromAffixPattern(sb.toString(), factory, parseFlags);
            PatternStringUtils.patternInfoToStringBuilder(patternInfo, false, signum, signDisplay, StandardPlural.OTHER, false, sb);
            final AffixPatternMatcher suffix = AffixPatternMatcher.fromAffixPattern(sb.toString(), factory, parseFlags);
            if (signum == 1) {
                posPrefix = prefix;
                posSuffix = suffix;
            }
            else if (Utility.equals(prefix, posPrefix) && Utility.equals(suffix, posSuffix)) {
                continue;
            }
            final int flags = (signum == -1) ? 1 : 0;
            matchers.add(getInstance(prefix, suffix, flags));
            if (includeUnpaired && prefix != null && suffix != null) {
                if (signum == 1 || !Utility.equals(prefix, posPrefix)) {
                    matchers.add(getInstance(prefix, null, flags));
                }
                if (signum == 1 || !Utility.equals(suffix, posSuffix)) {
                    matchers.add(getInstance(null, suffix, flags));
                }
            }
        }
        Collections.sort(matchers, AffixMatcher.COMPARATOR);
        output.addMatchers(matchers);
    }
    
    private static final AffixMatcher getInstance(final AffixPatternMatcher prefix, final AffixPatternMatcher suffix, final int flags) {
        return new AffixMatcher(prefix, suffix, flags);
    }
    
    private AffixMatcher(final AffixPatternMatcher prefix, final AffixPatternMatcher suffix, final int flags) {
        this.prefix = prefix;
        this.suffix = suffix;
        this.flags = flags;
    }
    
    @Override
    public boolean match(final StringSegment segment, final ParsedNumber result) {
        if (!result.seenNumber()) {
            if (result.prefix != null || this.prefix == null) {
                return false;
            }
            final int initialOffset = segment.getOffset();
            final boolean maybeMore = this.prefix.match(segment, result);
            if (initialOffset != segment.getOffset()) {
                result.prefix = this.prefix.getPattern();
            }
            return maybeMore;
        }
        else {
            if (result.suffix != null || this.suffix == null || !matched(this.prefix, result.prefix)) {
                return false;
            }
            final int initialOffset = segment.getOffset();
            final boolean maybeMore = this.suffix.match(segment, result);
            if (initialOffset != segment.getOffset()) {
                result.suffix = this.suffix.getPattern();
            }
            return maybeMore;
        }
    }
    
    @Override
    public boolean smokeTest(final StringSegment segment) {
        return (this.prefix != null && this.prefix.smokeTest(segment)) || (this.suffix != null && this.suffix.smokeTest(segment));
    }
    
    @Override
    public void postProcess(final ParsedNumber result) {
        if (matched(this.prefix, result.prefix) && matched(this.suffix, result.suffix)) {
            if (result.prefix == null) {
                result.prefix = "";
            }
            if (result.suffix == null) {
                result.suffix = "";
            }
            result.flags |= this.flags;
            if (this.prefix != null) {
                this.prefix.postProcess(result);
            }
            if (this.suffix != null) {
                this.suffix.postProcess(result);
            }
        }
    }
    
    static boolean matched(final AffixPatternMatcher affix, final String patternString) {
        return (affix == null && patternString == null) || (affix != null && affix.getPattern().equals(patternString));
    }
    
    private static int length(final AffixPatternMatcher matcher) {
        return (matcher == null) ? 0 : matcher.getPattern().length();
    }
    
    @Override
    public boolean equals(final Object _other) {
        if (!(_other instanceof AffixMatcher)) {
            return false;
        }
        final AffixMatcher other = (AffixMatcher)_other;
        return Utility.equals(this.prefix, other.prefix) && Utility.equals(this.suffix, other.suffix) && this.flags == other.flags;
    }
    
    @Override
    public int hashCode() {
        return Utility.hashCode(this.prefix) ^ Utility.hashCode(this.suffix) ^ this.flags;
    }
    
    @Override
    public String toString() {
        final boolean isNegative = 0x0 != (this.flags & 0x1);
        return "<AffixMatcher" + (isNegative ? ":negative " : " ") + this.prefix + "#" + this.suffix + ">";
    }
    
    static {
        COMPARATOR = new Comparator<AffixMatcher>() {
            @Override
            public int compare(final AffixMatcher lhs, final AffixMatcher rhs) {
                if (length(lhs.prefix) != length(rhs.prefix)) {
                    return (length(lhs.prefix) > length(rhs.prefix)) ? -1 : 1;
                }
                if (length(lhs.suffix) != length(rhs.suffix)) {
                    return (length(lhs.suffix) > length(rhs.suffix)) ? -1 : 1;
                }
                if (!lhs.equals(rhs)) {
                    return (lhs.hashCode() > rhs.hashCode()) ? -1 : 1;
                }
                return 0;
            }
        };
    }
}
