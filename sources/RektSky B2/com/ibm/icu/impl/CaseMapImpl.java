package com.ibm.icu.impl;

import java.util.*;
import java.io.*;
import com.ibm.icu.text.*;
import com.ibm.icu.util.*;
import java.text.*;

public final class CaseMapImpl
{
    public static final int TITLECASE_WHOLE_STRING = 32;
    public static final int TITLECASE_SENTENCES = 64;
    private static final int TITLECASE_ITERATOR_MASK = 224;
    public static final int TITLECASE_ADJUST_TO_CASED = 1024;
    private static final int TITLECASE_ADJUSTMENT_MASK = 1536;
    private static final int LNS = 251792942;
    public static final int OMIT_UNCHANGED_TEXT = 16384;
    private static final Trie2_16 CASE_TRIE;
    
    public static int addTitleAdjustmentOption(final int options, final int newOption) {
        final int adjOptions = options & 0x600;
        if (adjOptions != 0 && adjOptions != newOption) {
            throw new IllegalArgumentException("multiple titlecasing index adjustment options");
        }
        return options | newOption;
    }
    
    private static boolean isLNS(final int c) {
        final int gc = UCharacterProperty.INSTANCE.getType(c);
        return (1 << gc & 0xF020E2E) != 0x0 || (gc == 4 && UCaseProps.INSTANCE.getType(c) != 0);
    }
    
    public static int addTitleIteratorOption(final int options, final int newOption) {
        final int iterOptions = options & 0xE0;
        if (iterOptions != 0 && iterOptions != newOption) {
            throw new IllegalArgumentException("multiple titlecasing iterator options");
        }
        return options | newOption;
    }
    
    public static BreakIterator getTitleBreakIterator(final Locale locale, int options, BreakIterator iter) {
        options &= 0xE0;
        if (options != 0 && iter != null) {
            throw new IllegalArgumentException("titlecasing iterator option together with an explicit iterator");
        }
        if (iter == null) {
            switch (options) {
                case 0: {
                    iter = BreakIterator.getWordInstance(locale);
                    break;
                }
                case 32: {
                    iter = new WholeStringBreakIterator();
                    break;
                }
                case 64: {
                    iter = BreakIterator.getSentenceInstance(locale);
                    break;
                }
                default: {
                    throw new IllegalArgumentException("unknown titlecasing iterator option");
                }
            }
        }
        return iter;
    }
    
    public static BreakIterator getTitleBreakIterator(final ULocale locale, int options, BreakIterator iter) {
        options &= 0xE0;
        if (options != 0 && iter != null) {
            throw new IllegalArgumentException("titlecasing iterator option together with an explicit iterator");
        }
        if (iter == null) {
            switch (options) {
                case 0: {
                    iter = BreakIterator.getWordInstance(locale);
                    break;
                }
                case 32: {
                    iter = new WholeStringBreakIterator();
                    break;
                }
                case 64: {
                    iter = BreakIterator.getSentenceInstance(locale);
                    break;
                }
                default: {
                    throw new IllegalArgumentException("unknown titlecasing iterator option");
                }
            }
        }
        return iter;
    }
    
    private static int appendCodePoint(final Appendable a, final int c) throws IOException {
        if (c <= 65535) {
            a.append((char)c);
            return 1;
        }
        a.append((char)(55232 + (c >> 10)));
        a.append((char)(56320 + (c & 0x3FF)));
        return 2;
    }
    
    private static void appendResult(final int result, final Appendable dest, final int cpLength, final int options, final Edits edits) throws IOException {
        if (result < 0) {
            if (edits != null) {
                edits.addUnchanged(cpLength);
            }
            if ((options & 0x4000) != 0x0) {
                return;
            }
            appendCodePoint(dest, ~result);
        }
        else if (result <= 31) {
            if (edits != null) {
                edits.addReplace(cpLength, result);
            }
        }
        else {
            final int length = appendCodePoint(dest, result);
            if (edits != null) {
                edits.addReplace(cpLength, length);
            }
        }
    }
    
    private static final void appendUnchanged(final CharSequence src, final int start, final int length, final Appendable dest, final int options, final Edits edits) throws IOException {
        if (length > 0) {
            if (edits != null) {
                edits.addUnchanged(length);
            }
            if ((options & 0x4000) != 0x0) {
                return;
            }
            dest.append(src, start, start + length);
        }
    }
    
    private static String applyEdits(final CharSequence src, final StringBuilder replacementChars, final Edits edits) {
        if (!edits.hasChanges()) {
            return src.toString();
        }
        final StringBuilder result = new StringBuilder(src.length() + edits.lengthDelta());
        final Edits.Iterator ei = edits.getCoarseIterator();
        while (ei.next()) {
            if (ei.hasChange()) {
                final int i = ei.replacementIndex();
                result.append(replacementChars, i, i + ei.newLength());
            }
            else {
                final int i = ei.sourceIndex();
                result.append(src, i, i + ei.oldLength());
            }
        }
        return result.toString();
    }
    
    private static void internalToLower(final int caseLocale, final int options, final CharSequence src, final int srcStart, final int srcLimit, StringContextIterator iter, final Appendable dest, final Edits edits) throws IOException {
        byte[] latinToLower;
        if (caseLocale == 1 || ((caseLocale < 0) ? ((options & 0x7) == 0x0) : (caseLocale != 2 && caseLocale != 3))) {
            latinToLower = UCaseProps.LatinCase.TO_LOWER_NORMAL;
        }
        else {
            latinToLower = UCaseProps.LatinCase.TO_LOWER_TR_LT;
        }
        int prev = srcStart;
        int srcIndex = srcStart;
        while (srcIndex < srcLimit) {
            char lead = src.charAt(srcIndex);
            Label_0222: {
                int delta;
                if (lead < '\u017f') {
                    final byte d = latinToLower[lead];
                    if (d == -128) {
                        break Label_0222;
                    }
                    ++srcIndex;
                    if (d == 0) {
                        continue;
                    }
                    delta = d;
                }
                else {
                    if (lead >= '\ud800') {
                        break Label_0222;
                    }
                    final int props = CaseMapImpl.CASE_TRIE.getFromU16SingleLead(lead);
                    if (UCaseProps.propsHasException(props)) {
                        break Label_0222;
                    }
                    ++srcIndex;
                    if (!UCaseProps.isUpperOrTitleFromProps(props)) {
                        continue;
                    }
                    if ((delta = UCaseProps.getDelta(props)) == 0) {
                        continue;
                    }
                }
                lead += (char)delta;
                appendUnchanged(src, prev, srcIndex - 1 - prev, dest, options, edits);
                dest.append(lead);
                if (edits != null) {
                    edits.addReplace(1, 1);
                }
                prev = srcIndex;
                continue;
            }
            final int cpStart = srcIndex++;
            final char trail;
            int c;
            if (Character.isHighSurrogate(lead) && srcIndex < srcLimit && Character.isLowSurrogate(trail = src.charAt(srcIndex))) {
                c = Character.toCodePoint(lead, trail);
                ++srcIndex;
            }
            else {
                c = lead;
            }
            if (caseLocale >= 0) {
                if (iter == null) {
                    iter = new StringContextIterator(src, cpStart, srcIndex);
                }
                else {
                    iter.setCPStartAndLimit(cpStart, srcIndex);
                }
                c = UCaseProps.INSTANCE.toFullLower(c, iter, dest, caseLocale);
            }
            else {
                c = UCaseProps.INSTANCE.toFullFolding(c, dest, options);
            }
            if (c >= 0) {
                appendUnchanged(src, prev, cpStart - prev, dest, options, edits);
                appendResult(c, dest, srcIndex - cpStart, options, edits);
                prev = srcIndex;
            }
        }
        appendUnchanged(src, prev, srcIndex - prev, dest, options, edits);
    }
    
    private static void internalToUpper(final int caseLocale, final int options, final CharSequence src, final Appendable dest, final Edits edits) throws IOException {
        StringContextIterator iter = null;
        byte[] latinToUpper;
        if (caseLocale == 2) {
            latinToUpper = UCaseProps.LatinCase.TO_UPPER_TR;
        }
        else {
            latinToUpper = UCaseProps.LatinCase.TO_UPPER_NORMAL;
        }
        int prev = 0;
        int srcIndex = 0;
        final int srcLength = src.length();
        while (srcIndex < srcLength) {
            char lead = src.charAt(srcIndex);
            Label_0208: {
                int delta;
                if (lead < '\u017f') {
                    final byte d = latinToUpper[lead];
                    if (d == -128) {
                        break Label_0208;
                    }
                    ++srcIndex;
                    if (d == 0) {
                        continue;
                    }
                    delta = d;
                }
                else {
                    if (lead >= '\ud800') {
                        break Label_0208;
                    }
                    final int props = CaseMapImpl.CASE_TRIE.getFromU16SingleLead(lead);
                    if (UCaseProps.propsHasException(props)) {
                        break Label_0208;
                    }
                    ++srcIndex;
                    if (UCaseProps.getTypeFromProps(props) != 1) {
                        continue;
                    }
                    if ((delta = UCaseProps.getDelta(props)) == 0) {
                        continue;
                    }
                }
                lead += (char)delta;
                appendUnchanged(src, prev, srcIndex - 1 - prev, dest, options, edits);
                dest.append(lead);
                if (edits != null) {
                    edits.addReplace(1, 1);
                }
                prev = srcIndex;
                continue;
            }
            final int cpStart = srcIndex++;
            final char trail;
            int c;
            if (Character.isHighSurrogate(lead) && srcIndex < srcLength && Character.isLowSurrogate(trail = src.charAt(srcIndex))) {
                c = Character.toCodePoint(lead, trail);
                ++srcIndex;
            }
            else {
                c = lead;
            }
            if (iter == null) {
                iter = new StringContextIterator(src, cpStart, srcIndex);
            }
            else {
                iter.setCPStartAndLimit(cpStart, srcIndex);
            }
            c = UCaseProps.INSTANCE.toFullUpper(c, iter, dest, caseLocale);
            if (c >= 0) {
                appendUnchanged(src, prev, cpStart - prev, dest, options, edits);
                appendResult(c, dest, srcIndex - cpStart, options, edits);
                prev = srcIndex;
            }
        }
        appendUnchanged(src, prev, srcIndex - prev, dest, options, edits);
    }
    
    public static String toLower(final int caseLocale, final int options, final CharSequence src) {
        if (src.length() > 100 || (options & 0x4000) != 0x0) {
            return toLower(caseLocale, options, src, new StringBuilder(src.length()), null).toString();
        }
        if (src.length() == 0) {
            return src.toString();
        }
        final Edits edits = new Edits();
        final StringBuilder replacementChars = toLower(caseLocale, options | 0x4000, src, new StringBuilder(), edits);
        return applyEdits(src, replacementChars, edits);
    }
    
    public static <A extends Appendable> A toLower(final int caseLocale, final int options, final CharSequence src, final A dest, final Edits edits) {
        try {
            if (edits != null) {
                edits.reset();
            }
            internalToLower(caseLocale, options, src, 0, src.length(), null, dest, edits);
            return dest;
        }
        catch (IOException e) {
            throw new ICUUncheckedIOException(e);
        }
    }
    
    public static String toUpper(final int caseLocale, final int options, final CharSequence src) {
        if (src.length() > 100 || (options & 0x4000) != 0x0) {
            return toUpper(caseLocale, options, src, new StringBuilder(src.length()), null).toString();
        }
        if (src.length() == 0) {
            return src.toString();
        }
        final Edits edits = new Edits();
        final StringBuilder replacementChars = toUpper(caseLocale, options | 0x4000, src, new StringBuilder(), edits);
        return applyEdits(src, replacementChars, edits);
    }
    
    public static <A extends Appendable> A toUpper(final int caseLocale, final int options, final CharSequence src, final A dest, final Edits edits) {
        try {
            if (edits != null) {
                edits.reset();
            }
            if (caseLocale == 4) {
                return (A)toUpper(options, src, dest, edits);
            }
            internalToUpper(caseLocale, options, src, dest, edits);
            return dest;
        }
        catch (IOException e) {
            throw new ICUUncheckedIOException(e);
        }
    }
    
    public static String toTitle(final int caseLocale, final int options, final BreakIterator iter, final CharSequence src) {
        if (src.length() > 100 || (options & 0x4000) != 0x0) {
            return toTitle(caseLocale, options, iter, src, new StringBuilder(src.length()), null).toString();
        }
        if (src.length() == 0) {
            return src.toString();
        }
        final Edits edits = new Edits();
        final StringBuilder replacementChars = toTitle(caseLocale, options | 0x4000, iter, src, new StringBuilder(), edits);
        return applyEdits(src, replacementChars, edits);
    }
    
    public static <A extends Appendable> A toTitle(final int caseLocale, final int options, final BreakIterator titleIter, final CharSequence src, final A dest, final Edits edits) {
        try {
            if (edits != null) {
                edits.reset();
            }
            final StringContextIterator iter = new StringContextIterator(src);
            final int srcLength = src.length();
            int prev = 0;
            boolean isFirstIndex = true;
            while (prev < srcLength) {
                int index;
                if (isFirstIndex) {
                    isFirstIndex = false;
                    index = titleIter.first();
                }
                else {
                    index = titleIter.next();
                }
                if (index == -1 || index > srcLength) {
                    index = srcLength;
                }
                if (prev < index) {
                    int titleStart = prev;
                    iter.setLimit(index);
                    int c = iter.nextCaseMapCP();
                    if ((options & 0x200) == 0x0) {
                        final boolean toCased = (options & 0x400) != 0x0;
                        do {
                            if (toCased) {
                                if (0 != UCaseProps.INSTANCE.getType(c)) {
                                    break;
                                }
                            }
                            else if (isLNS(c)) {
                                break;
                            }
                        } while ((c = iter.nextCaseMapCP()) >= 0);
                        titleStart = iter.getCPStart();
                        if (prev < titleStart) {
                            appendUnchanged(src, prev, titleStart - prev, dest, options, edits);
                        }
                    }
                    if (titleStart < index) {
                        int titleLimit = iter.getCPLimit();
                        c = UCaseProps.INSTANCE.toFullTitle(c, iter, dest, caseLocale);
                        appendResult(c, dest, iter.getCPLength(), options, edits);
                        if (titleStart + 1 < index && caseLocale == 5) {
                            final char c2 = src.charAt(titleStart);
                            if (c2 == 'i' || c2 == 'I') {
                                final char c3 = src.charAt(titleStart + 1);
                                if (c3 == 'j') {
                                    dest.append('J');
                                    if (edits != null) {
                                        edits.addReplace(1, 1);
                                    }
                                    c = iter.nextCaseMapCP();
                                    ++titleLimit;
                                    assert c == c3;
                                    assert titleLimit == iter.getCPLimit();
                                }
                                else if (c3 == 'J') {
                                    appendUnchanged(src, titleStart + 1, 1, dest, options, edits);
                                    c = iter.nextCaseMapCP();
                                    ++titleLimit;
                                    assert c == c3;
                                    assert titleLimit == iter.getCPLimit();
                                }
                            }
                        }
                        if (titleLimit < index) {
                            if ((options & 0x100) == 0x0) {
                                internalToLower(caseLocale, options, src, titleLimit, index, iter, dest, edits);
                            }
                            else {
                                appendUnchanged(src, titleLimit, index - titleLimit, dest, options, edits);
                            }
                            iter.moveToLimit();
                        }
                    }
                }
                prev = index;
            }
            return dest;
        }
        catch (IOException e) {
            throw new ICUUncheckedIOException(e);
        }
    }
    
    public static String fold(final int options, final CharSequence src) {
        if (src.length() > 100 || (options & 0x4000) != 0x0) {
            return fold(options, src, new StringBuilder(src.length()), null).toString();
        }
        if (src.length() == 0) {
            return src.toString();
        }
        final Edits edits = new Edits();
        final StringBuilder replacementChars = fold(options | 0x4000, src, new StringBuilder(), edits);
        return applyEdits(src, replacementChars, edits);
    }
    
    public static <A extends Appendable> A fold(final int options, final CharSequence src, final A dest, final Edits edits) {
        try {
            if (edits != null) {
                edits.reset();
            }
            internalToLower(-1, options, src, 0, src.length(), null, dest, edits);
            return dest;
        }
        catch (IOException e) {
            throw new ICUUncheckedIOException(e);
        }
    }
    
    static {
        CASE_TRIE = UCaseProps.getTrie();
    }
    
    public static final class StringContextIterator implements UCaseProps.ContextIterator
    {
        protected CharSequence s;
        protected int index;
        protected int limit;
        protected int cpStart;
        protected int cpLimit;
        protected int dir;
        
        public StringContextIterator(final CharSequence src) {
            this.s = src;
            this.limit = src.length();
            final int cpStart = 0;
            this.index = cpStart;
            this.cpLimit = cpStart;
            this.cpStart = cpStart;
            this.dir = 0;
        }
        
        public StringContextIterator(final CharSequence src, final int cpStart, final int cpLimit) {
            this.s = src;
            this.index = 0;
            this.limit = src.length();
            this.cpStart = cpStart;
            this.cpLimit = cpLimit;
            this.dir = 0;
        }
        
        public void setLimit(final int lim) {
            if (0 <= lim && lim <= this.s.length()) {
                this.limit = lim;
            }
            else {
                this.limit = this.s.length();
            }
        }
        
        public void moveToLimit() {
            final int limit = this.limit;
            this.cpLimit = limit;
            this.cpStart = limit;
        }
        
        public int nextCaseMapCP() {
            this.cpStart = this.cpLimit;
            if (this.cpLimit < this.limit) {
                final int c = Character.codePointAt(this.s, this.cpLimit);
                this.cpLimit += Character.charCount(c);
                return c;
            }
            return -1;
        }
        
        public void setCPStartAndLimit(final int s, final int l) {
            this.cpStart = s;
            this.cpLimit = l;
            this.dir = 0;
        }
        
        public int getCPStart() {
            return this.cpStart;
        }
        
        public int getCPLimit() {
            return this.cpLimit;
        }
        
        public int getCPLength() {
            return this.cpLimit - this.cpStart;
        }
        
        @Override
        public void reset(final int direction) {
            if (direction > 0) {
                this.dir = 1;
                this.index = this.cpLimit;
            }
            else if (direction < 0) {
                this.dir = -1;
                this.index = this.cpStart;
            }
            else {
                this.dir = 0;
                this.index = 0;
            }
        }
        
        @Override
        public int next() {
            if (this.dir > 0 && this.index < this.s.length()) {
                final int c = Character.codePointAt(this.s, this.index);
                this.index += Character.charCount(c);
                return c;
            }
            if (this.dir < 0 && this.index > 0) {
                final int c = Character.codePointBefore(this.s, this.index);
                this.index -= Character.charCount(c);
                return c;
            }
            return -1;
        }
    }
    
    private static final class WholeStringBreakIterator extends BreakIterator
    {
        private int length;
        
        private static void notImplemented() {
            throw new UnsupportedOperationException("should not occur");
        }
        
        @Override
        public int first() {
            return 0;
        }
        
        @Override
        public int last() {
            notImplemented();
            return 0;
        }
        
        @Override
        public int next(final int n) {
            notImplemented();
            return 0;
        }
        
        @Override
        public int next() {
            return this.length;
        }
        
        @Override
        public int previous() {
            notImplemented();
            return 0;
        }
        
        @Override
        public int following(final int offset) {
            notImplemented();
            return 0;
        }
        
        @Override
        public int current() {
            notImplemented();
            return 0;
        }
        
        @Override
        public CharacterIterator getText() {
            notImplemented();
            return null;
        }
        
        @Override
        public void setText(final CharacterIterator newText) {
            this.length = newText.getEndIndex();
        }
        
        @Override
        public void setText(final CharSequence newText) {
            this.length = newText.length();
        }
        
        @Override
        public void setText(final String newText) {
            this.length = newText.length();
        }
    }
    
    private static final class GreekUpper
    {
        private static final int UPPER_MASK = 1023;
        private static final int HAS_VOWEL = 4096;
        private static final int HAS_YPOGEGRAMMENI = 8192;
        private static final int HAS_ACCENT = 16384;
        private static final int HAS_DIALYTIKA = 32768;
        private static final int HAS_COMBINING_DIALYTIKA = 65536;
        private static final int HAS_OTHER_GREEK_DIACRITIC = 131072;
        private static final int HAS_VOWEL_AND_ACCENT = 20480;
        private static final int HAS_VOWEL_AND_ACCENT_AND_DIALYTIKA = 53248;
        private static final int HAS_EITHER_DIALYTIKA = 98304;
        private static final int AFTER_CASED = 1;
        private static final int AFTER_VOWEL_WITH_ACCENT = 2;
        private static final char[] data0370;
        private static final char[] data1F00;
        private static final char data2126 = '\u13a9';
        
        private static final int getLetterData(final int c) {
            if (c < 880 || 8486 < c || (1023 < c && c < 7936)) {
                return 0;
            }
            if (c <= 1023) {
                return GreekUpper.data0370[c - 880];
            }
            if (c <= 8191) {
                return GreekUpper.data1F00[c - 7936];
            }
            if (c == 8486) {
                return 5033;
            }
            return 0;
        }
        
        private static final int getDiacriticData(final int c) {
            switch (c) {
                case 768:
                case 769:
                case 770:
                case 771:
                case 785:
                case 834: {
                    return 16384;
                }
                case 776: {
                    return 65536;
                }
                case 836: {
                    return 81920;
                }
                case 837: {
                    return 8192;
                }
                case 772:
                case 774:
                case 787:
                case 788:
                case 835: {
                    return 131072;
                }
                default: {
                    return 0;
                }
            }
        }
        
        private static boolean isFollowedByCasedLetter(final CharSequence s, int i) {
            while (i < s.length()) {
                final int c = Character.codePointAt(s, i);
                final int type = UCaseProps.INSTANCE.getTypeOrIgnorable(c);
                if ((type & 0x4) == 0x0) {
                    return type != 0;
                }
                i += Character.charCount(c);
            }
            return false;
        }
        
        private static <A extends Appendable> A toUpper(final int options, final CharSequence src, final A dest, final Edits edits) throws IOException {
            int state = 0;
            int nextIndex;
            int nextState;
            for (int i = 0; i < src.length(); i = nextIndex, state = nextState) {
                int c = Character.codePointAt(src, i);
                nextIndex = i + Character.charCount(c);
                nextState = 0;
                final int type = UCaseProps.INSTANCE.getTypeOrIgnorable(c);
                if ((type & 0x4) != 0x0) {
                    nextState |= (state & 0x1);
                }
                else if (type != 0) {
                    nextState |= 0x1;
                }
                int data = getLetterData(c);
                if (data > 0) {
                    int upper = data & 0x3FF;
                    if ((data & 0x1000) != 0x0 && (state & 0x2) != 0x0 && (upper == 921 || upper == 933)) {
                        data |= 0x8000;
                    }
                    int numYpogegrammeni = 0;
                    if ((data & 0x2000) != 0x0) {
                        numYpogegrammeni = 1;
                    }
                    while (nextIndex < src.length()) {
                        final int diacriticData = getDiacriticData(src.charAt(nextIndex));
                        if (diacriticData == 0) {
                            break;
                        }
                        data |= diacriticData;
                        if ((diacriticData & 0x2000) != 0x0) {
                            ++numYpogegrammeni;
                        }
                        ++nextIndex;
                    }
                    if ((data & 0xD000) == 0x5000) {
                        nextState |= 0x2;
                    }
                    boolean addTonos = false;
                    if (upper == 919 && (data & 0x4000) != 0x0 && numYpogegrammeni == 0 && (state & 0x1) == 0x0 && !isFollowedByCasedLetter(src, nextIndex)) {
                        if (i == nextIndex) {
                            upper = 905;
                        }
                        else {
                            addTonos = true;
                        }
                    }
                    else if ((data & 0x8000) != 0x0) {
                        if (upper == 921) {
                            upper = 938;
                            data &= 0xFFFE7FFF;
                        }
                        else if (upper == 933) {
                            upper = 939;
                            data &= 0xFFFE7FFF;
                        }
                    }
                    boolean change;
                    if (edits == null && (options & 0x4000) == 0x0) {
                        change = true;
                    }
                    else {
                        change = (src.charAt(i) != upper || numYpogegrammeni > 0);
                        int i2 = i + 1;
                        if ((data & 0x18000) != 0x0) {
                            change |= (i2 >= nextIndex || src.charAt(i2) != '\u0308');
                            ++i2;
                        }
                        if (addTonos) {
                            change |= (i2 >= nextIndex || src.charAt(i2) != '\u0301');
                            ++i2;
                        }
                        final int oldLength = nextIndex - i;
                        final int newLength = i2 - i + numYpogegrammeni;
                        change |= (oldLength != newLength);
                        if (change) {
                            if (edits != null) {
                                edits.addReplace(oldLength, newLength);
                            }
                        }
                        else {
                            if (edits != null) {
                                edits.addUnchanged(oldLength);
                            }
                            change = ((options & 0x4000) == 0x0);
                        }
                    }
                    if (change) {
                        dest.append((char)upper);
                        if ((data & 0x18000) != 0x0) {
                            dest.append('\u0308');
                        }
                        if (addTonos) {
                            dest.append('\u0301');
                        }
                        while (numYpogegrammeni > 0) {
                            dest.append('\u0399');
                            --numYpogegrammeni;
                        }
                    }
                }
                else {
                    c = UCaseProps.INSTANCE.toFullUpper(c, null, dest, 4);
                    appendResult(c, dest, nextIndex - i, options, edits);
                }
            }
            return dest;
        }
        
        static {
            data0370 = new char[] { '\u0370', '\u0370', '\u0372', '\u0372', '\0', '\0', '\u0376', '\u0376', '\0', '\0', '\u037a', '\u03fd', '\u03fe', '\u03ff', '\0', '\u037f', '\0', '\0', '\0', '\0', '\0', '\0', '\u5391', '\0', '\u5395', '\u5397', '\u5399', '\0', '\u539f', '\0', '\u53a5', '\u53a9', '\ud399', '\u1391', '\u0392', '\u0393', '\u0394', '\u1395', '\u0396', '\u1397', '\u0398', '\u1399', '\u039a', '\u039b', '\u039c', '\u039d', '\u039e', '\u139f', '\u03a0', '\u03a1', '\0', '\u03a3', '\u03a4', '\u13a5', '\u03a6', '\u03a7', '\u03a8', '\u13a9', '\u9399', '\u93a5', '\u5391', '\u5395', '\u5397', '\u5399', '\ud3a5', '\u1391', '\u0392', '\u0393', '\u0394', '\u1395', '\u0396', '\u1397', '\u0398', '\u1399', '\u039a', '\u039b', '\u039c', '\u039d', '\u039e', '\u139f', '\u03a0', '\u03a1', '\u03a3', '\u03a3', '\u03a4', '\u13a5', '\u03a6', '\u03a7', '\u03a8', '\u13a9', '\u9399', '\u93a5', '\u539f', '\u53a5', '\u53a9', '\u03cf', '\u0392', '\u0398', '\u03d2', '\u43d2', '\u83d2', '\u03a6', '\u03a0', '\u03cf', '\u03d8', '\u03d8', '\u03da', '\u03da', '\u03dc', '\u03dc', '\u03de', '\u03de', '\u03e0', '\u03e0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\u039a', '\u03a1', '\u03f9', '\u037f', '\u03f4', '\u1395', '\0', '\u03f7', '\u03f7', '\u03f9', '\u03fa', '\u03fa', '\u03fc', '\u03fd', '\u03fe', '\u03ff' };
            data1F00 = new char[] { '\u1391', '\u1391', '\u5391', '\u5391', '\u5391', '\u5391', '\u5391', '\u5391', '\u1391', '\u1391', '\u5391', '\u5391', '\u5391', '\u5391', '\u5391', '\u5391', '\u1395', '\u1395', '\u5395', '\u5395', '\u5395', '\u5395', '\0', '\0', '\u1395', '\u1395', '\u5395', '\u5395', '\u5395', '\u5395', '\0', '\0', '\u1397', '\u1397', '\u5397', '\u5397', '\u5397', '\u5397', '\u5397', '\u5397', '\u1397', '\u1397', '\u5397', '\u5397', '\u5397', '\u5397', '\u5397', '\u5397', '\u1399', '\u1399', '\u5399', '\u5399', '\u5399', '\u5399', '\u5399', '\u5399', '\u1399', '\u1399', '\u5399', '\u5399', '\u5399', '\u5399', '\u5399', '\u5399', '\u139f', '\u139f', '\u539f', '\u539f', '\u539f', '\u539f', '\0', '\0', '\u139f', '\u139f', '\u539f', '\u539f', '\u539f', '\u539f', '\0', '\0', '\u13a5', '\u13a5', '\u53a5', '\u53a5', '\u53a5', '\u53a5', '\u53a5', '\u53a5', '\0', '\u13a5', '\0', '\u53a5', '\0', '\u53a5', '\0', '\u53a5', '\u13a9', '\u13a9', '\u53a9', '\u53a9', '\u53a9', '\u53a9', '\u53a9', '\u53a9', '\u13a9', '\u13a9', '\u53a9', '\u53a9', '\u53a9', '\u53a9', '\u53a9', '\u53a9', '\u5391', '\u5391', '\u5395', '\u5395', '\u5397', '\u5397', '\u5399', '\u5399', '\u539f', '\u539f', '\u53a5', '\u53a5', '\u53a9', '\u53a9', '\0', '\0', '\u3391', '\u3391', '\u7391', '\u7391', '\u7391', '\u7391', '\u7391', '\u7391', '\u3391', '\u3391', '\u7391', '\u7391', '\u7391', '\u7391', '\u7391', '\u7391', '\u3397', '\u3397', '\u7397', '\u7397', '\u7397', '\u7397', '\u7397', '\u7397', '\u3397', '\u3397', '\u7397', '\u7397', '\u7397', '\u7397', '\u7397', '\u7397', '\u33a9', '\u33a9', '\u73a9', '\u73a9', '\u73a9', '\u73a9', '\u73a9', '\u73a9', '\u33a9', '\u33a9', '\u73a9', '\u73a9', '\u73a9', '\u73a9', '\u73a9', '\u73a9', '\u1391', '\u1391', '\u7391', '\u3391', '\u7391', '\0', '\u5391', '\u7391', '\u1391', '\u1391', '\u5391', '\u5391', '\u3391', '\0', '\u1399', '\0', '\0', '\0', '\u7397', '\u3397', '\u7397', '\0', '\u5397', '\u7397', '\u5395', '\u5395', '\u5397', '\u5397', '\u3397', '\0', '\0', '\0', '\u1399', '\u1399', '\ud399', '\ud399', '\0', '\0', '\u5399', '\ud399', '\u1399', '\u1399', '\u5399', '\u5399', '\0', '\0', '\0', '\0', '\u13a5', '\u13a5', '\ud3a5', '\ud3a5', '\u03a1', '\u03a1', '\u53a5', '\ud3a5', '\u13a5', '\u13a5', '\u53a5', '\u53a5', '\u03a1', '\0', '\0', '\0', '\0', '\0', '\u73a9', '\u33a9', '\u73a9', '\0', '\u53a9', '\u73a9', '\u539f', '\u539f', '\u53a9', '\u53a9', '\u33a9', '\0', '\0', '\0' };
        }
    }
}
