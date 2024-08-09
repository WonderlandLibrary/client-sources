/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.apache.commons.lang3.text.StrMatcher;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class StrTokenizer
implements ListIterator<String>,
Cloneable {
    private static final StrTokenizer CSV_TOKENIZER_PROTOTYPE = new StrTokenizer();
    private static final StrTokenizer TSV_TOKENIZER_PROTOTYPE;
    private char[] chars;
    private String[] tokens;
    private int tokenPos;
    private StrMatcher delimMatcher = StrMatcher.splitMatcher();
    private StrMatcher quoteMatcher = StrMatcher.noneMatcher();
    private StrMatcher ignoredMatcher = StrMatcher.noneMatcher();
    private StrMatcher trimmerMatcher = StrMatcher.noneMatcher();
    private boolean emptyAsNull = false;
    private boolean ignoreEmptyTokens = true;

    private static StrTokenizer getCSVClone() {
        return (StrTokenizer)CSV_TOKENIZER_PROTOTYPE.clone();
    }

    public static StrTokenizer getCSVInstance() {
        return StrTokenizer.getCSVClone();
    }

    public static StrTokenizer getCSVInstance(String string) {
        StrTokenizer strTokenizer = StrTokenizer.getCSVClone();
        strTokenizer.reset(string);
        return strTokenizer;
    }

    public static StrTokenizer getCSVInstance(char[] cArray) {
        StrTokenizer strTokenizer = StrTokenizer.getCSVClone();
        strTokenizer.reset(cArray);
        return strTokenizer;
    }

    private static StrTokenizer getTSVClone() {
        return (StrTokenizer)TSV_TOKENIZER_PROTOTYPE.clone();
    }

    public static StrTokenizer getTSVInstance() {
        return StrTokenizer.getTSVClone();
    }

    public static StrTokenizer getTSVInstance(String string) {
        StrTokenizer strTokenizer = StrTokenizer.getTSVClone();
        strTokenizer.reset(string);
        return strTokenizer;
    }

    public static StrTokenizer getTSVInstance(char[] cArray) {
        StrTokenizer strTokenizer = StrTokenizer.getTSVClone();
        strTokenizer.reset(cArray);
        return strTokenizer;
    }

    public StrTokenizer() {
        this.chars = null;
    }

    public StrTokenizer(String string) {
        this.chars = (char[])(string != null ? string.toCharArray() : null);
    }

    public StrTokenizer(String string, char c) {
        this(string);
        this.setDelimiterChar(c);
    }

    public StrTokenizer(String string, String string2) {
        this(string);
        this.setDelimiterString(string2);
    }

    public StrTokenizer(String string, StrMatcher strMatcher) {
        this(string);
        this.setDelimiterMatcher(strMatcher);
    }

    public StrTokenizer(String string, char c, char c2) {
        this(string, c);
        this.setQuoteChar(c2);
    }

    public StrTokenizer(String string, StrMatcher strMatcher, StrMatcher strMatcher2) {
        this(string, strMatcher);
        this.setQuoteMatcher(strMatcher2);
    }

    public StrTokenizer(char[] cArray) {
        this.chars = ArrayUtils.clone(cArray);
    }

    public StrTokenizer(char[] cArray, char c) {
        this(cArray);
        this.setDelimiterChar(c);
    }

    public StrTokenizer(char[] cArray, String string) {
        this(cArray);
        this.setDelimiterString(string);
    }

    public StrTokenizer(char[] cArray, StrMatcher strMatcher) {
        this(cArray);
        this.setDelimiterMatcher(strMatcher);
    }

    public StrTokenizer(char[] cArray, char c, char c2) {
        this(cArray, c);
        this.setQuoteChar(c2);
    }

    public StrTokenizer(char[] cArray, StrMatcher strMatcher, StrMatcher strMatcher2) {
        this(cArray, strMatcher);
        this.setQuoteMatcher(strMatcher2);
    }

    public int size() {
        this.checkTokenized();
        return this.tokens.length;
    }

    public String nextToken() {
        if (this.hasNext()) {
            return this.tokens[this.tokenPos++];
        }
        return null;
    }

    public String previousToken() {
        if (this.hasPrevious()) {
            return this.tokens[--this.tokenPos];
        }
        return null;
    }

    public String[] getTokenArray() {
        this.checkTokenized();
        return (String[])this.tokens.clone();
    }

    public List<String> getTokenList() {
        this.checkTokenized();
        ArrayList<String> arrayList = new ArrayList<String>(this.tokens.length);
        for (String string : this.tokens) {
            arrayList.add(string);
        }
        return arrayList;
    }

    public StrTokenizer reset() {
        this.tokenPos = 0;
        this.tokens = null;
        return this;
    }

    public StrTokenizer reset(String string) {
        this.reset();
        this.chars = (char[])(string != null ? string.toCharArray() : null);
        return this;
    }

    public StrTokenizer reset(char[] cArray) {
        this.reset();
        this.chars = ArrayUtils.clone(cArray);
        return this;
    }

    @Override
    public boolean hasNext() {
        this.checkTokenized();
        return this.tokenPos < this.tokens.length;
    }

    @Override
    public String next() {
        if (this.hasNext()) {
            return this.tokens[this.tokenPos++];
        }
        throw new NoSuchElementException();
    }

    @Override
    public int nextIndex() {
        return this.tokenPos;
    }

    @Override
    public boolean hasPrevious() {
        this.checkTokenized();
        return this.tokenPos > 0;
    }

    @Override
    public String previous() {
        if (this.hasPrevious()) {
            return this.tokens[--this.tokenPos];
        }
        throw new NoSuchElementException();
    }

    @Override
    public int previousIndex() {
        return this.tokenPos - 1;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove() is unsupported");
    }

    @Override
    public void set(String string) {
        throw new UnsupportedOperationException("set() is unsupported");
    }

    @Override
    public void add(String string) {
        throw new UnsupportedOperationException("add() is unsupported");
    }

    private void checkTokenized() {
        if (this.tokens == null) {
            if (this.chars == null) {
                List<String> list = this.tokenize(null, 0, 0);
                this.tokens = list.toArray(new String[list.size()]);
            } else {
                List<String> list = this.tokenize(this.chars, 0, this.chars.length);
                this.tokens = list.toArray(new String[list.size()]);
            }
        }
    }

    protected List<String> tokenize(char[] cArray, int n, int n2) {
        if (cArray == null || n2 == 0) {
            return Collections.emptyList();
        }
        StrBuilder strBuilder = new StrBuilder();
        ArrayList<String> arrayList = new ArrayList<String>();
        int n3 = n;
        while (n3 >= 0 && n3 < n2) {
            if ((n3 = this.readNextToken(cArray, n3, n2, strBuilder, arrayList)) < n2) continue;
            this.addToken(arrayList, "");
        }
        return arrayList;
    }

    private void addToken(List<String> list, String string) {
        if (StringUtils.isEmpty(string)) {
            if (this.isIgnoreEmptyTokens()) {
                return;
            }
            if (this.isEmptyTokenAsNull()) {
                string = null;
            }
        }
        list.add(string);
    }

    private int readNextToken(char[] cArray, int n, int n2, StrBuilder strBuilder, List<String> list) {
        int n3;
        while (n < n2 && (n3 = Math.max(this.getIgnoredMatcher().isMatch(cArray, n, n, n2), this.getTrimmerMatcher().isMatch(cArray, n, n, n2))) != 0 && this.getDelimiterMatcher().isMatch(cArray, n, n, n2) <= 0 && this.getQuoteMatcher().isMatch(cArray, n, n, n2) <= 0) {
            n += n3;
        }
        if (n >= n2) {
            this.addToken(list, "");
            return 1;
        }
        n3 = this.getDelimiterMatcher().isMatch(cArray, n, n, n2);
        if (n3 > 0) {
            this.addToken(list, "");
            return n + n3;
        }
        int n4 = this.getQuoteMatcher().isMatch(cArray, n, n, n2);
        if (n4 > 0) {
            return this.readWithQuotes(cArray, n + n4, n2, strBuilder, list, n, n4);
        }
        return this.readWithQuotes(cArray, n, n2, strBuilder, list, 0, 0);
    }

    private int readWithQuotes(char[] cArray, int n, int n2, StrBuilder strBuilder, List<String> list, int n3, int n4) {
        strBuilder.clear();
        int n5 = n;
        boolean bl = n4 > 0;
        int n6 = 0;
        while (n5 < n2) {
            if (bl) {
                if (this.isQuote(cArray, n5, n2, n3, n4)) {
                    if (this.isQuote(cArray, n5 + n4, n2, n3, n4)) {
                        strBuilder.append(cArray, n5, n4);
                        n5 += n4 * 2;
                        n6 = strBuilder.size();
                        continue;
                    }
                    bl = false;
                    n5 += n4;
                    continue;
                }
                strBuilder.append(cArray[n5++]);
                n6 = strBuilder.size();
                continue;
            }
            int n7 = this.getDelimiterMatcher().isMatch(cArray, n5, n, n2);
            if (n7 > 0) {
                this.addToken(list, strBuilder.substring(0, n6));
                return n5 + n7;
            }
            if (n4 > 0 && this.isQuote(cArray, n5, n2, n3, n4)) {
                bl = true;
                n5 += n4;
                continue;
            }
            int n8 = this.getIgnoredMatcher().isMatch(cArray, n5, n, n2);
            if (n8 > 0) {
                n5 += n8;
                continue;
            }
            int n9 = this.getTrimmerMatcher().isMatch(cArray, n5, n, n2);
            if (n9 > 0) {
                strBuilder.append(cArray, n5, n9);
                n5 += n9;
                continue;
            }
            strBuilder.append(cArray[n5++]);
            n6 = strBuilder.size();
        }
        this.addToken(list, strBuilder.substring(0, n6));
        return 1;
    }

    private boolean isQuote(char[] cArray, int n, int n2, int n3, int n4) {
        for (int i = 0; i < n4; ++i) {
            if (n + i < n2 && cArray[n + i] == cArray[n3 + i]) continue;
            return true;
        }
        return false;
    }

    public StrMatcher getDelimiterMatcher() {
        return this.delimMatcher;
    }

    public StrTokenizer setDelimiterMatcher(StrMatcher strMatcher) {
        this.delimMatcher = strMatcher == null ? StrMatcher.noneMatcher() : strMatcher;
        return this;
    }

    public StrTokenizer setDelimiterChar(char c) {
        return this.setDelimiterMatcher(StrMatcher.charMatcher(c));
    }

    public StrTokenizer setDelimiterString(String string) {
        return this.setDelimiterMatcher(StrMatcher.stringMatcher(string));
    }

    public StrMatcher getQuoteMatcher() {
        return this.quoteMatcher;
    }

    public StrTokenizer setQuoteMatcher(StrMatcher strMatcher) {
        if (strMatcher != null) {
            this.quoteMatcher = strMatcher;
        }
        return this;
    }

    public StrTokenizer setQuoteChar(char c) {
        return this.setQuoteMatcher(StrMatcher.charMatcher(c));
    }

    public StrMatcher getIgnoredMatcher() {
        return this.ignoredMatcher;
    }

    public StrTokenizer setIgnoredMatcher(StrMatcher strMatcher) {
        if (strMatcher != null) {
            this.ignoredMatcher = strMatcher;
        }
        return this;
    }

    public StrTokenizer setIgnoredChar(char c) {
        return this.setIgnoredMatcher(StrMatcher.charMatcher(c));
    }

    public StrMatcher getTrimmerMatcher() {
        return this.trimmerMatcher;
    }

    public StrTokenizer setTrimmerMatcher(StrMatcher strMatcher) {
        if (strMatcher != null) {
            this.trimmerMatcher = strMatcher;
        }
        return this;
    }

    public boolean isEmptyTokenAsNull() {
        return this.emptyAsNull;
    }

    public StrTokenizer setEmptyTokenAsNull(boolean bl) {
        this.emptyAsNull = bl;
        return this;
    }

    public boolean isIgnoreEmptyTokens() {
        return this.ignoreEmptyTokens;
    }

    public StrTokenizer setIgnoreEmptyTokens(boolean bl) {
        this.ignoreEmptyTokens = bl;
        return this;
    }

    public String getContent() {
        if (this.chars == null) {
            return null;
        }
        return new String(this.chars);
    }

    public Object clone() {
        try {
            return this.cloneReset();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            return null;
        }
    }

    Object cloneReset() throws CloneNotSupportedException {
        StrTokenizer strTokenizer = (StrTokenizer)super.clone();
        if (strTokenizer.chars != null) {
            strTokenizer.chars = (char[])strTokenizer.chars.clone();
        }
        strTokenizer.reset();
        return strTokenizer;
    }

    public String toString() {
        if (this.tokens == null) {
            return "StrTokenizer[not tokenized yet]";
        }
        return "StrTokenizer" + this.getTokenList();
    }

    @Override
    public void add(Object object) {
        this.add((String)object);
    }

    @Override
    public void set(Object object) {
        this.set((String)object);
    }

    @Override
    public Object previous() {
        return this.previous();
    }

    @Override
    public Object next() {
        return this.next();
    }

    static {
        CSV_TOKENIZER_PROTOTYPE.setDelimiterMatcher(StrMatcher.commaMatcher());
        CSV_TOKENIZER_PROTOTYPE.setQuoteMatcher(StrMatcher.doubleQuoteMatcher());
        CSV_TOKENIZER_PROTOTYPE.setIgnoredMatcher(StrMatcher.noneMatcher());
        CSV_TOKENIZER_PROTOTYPE.setTrimmerMatcher(StrMatcher.trimMatcher());
        CSV_TOKENIZER_PROTOTYPE.setEmptyTokenAsNull(true);
        CSV_TOKENIZER_PROTOTYPE.setIgnoreEmptyTokens(true);
        TSV_TOKENIZER_PROTOTYPE = new StrTokenizer();
        TSV_TOKENIZER_PROTOTYPE.setDelimiterMatcher(StrMatcher.tabMatcher());
        TSV_TOKENIZER_PROTOTYPE.setQuoteMatcher(StrMatcher.doubleQuoteMatcher());
        TSV_TOKENIZER_PROTOTYPE.setIgnoredMatcher(StrMatcher.noneMatcher());
        TSV_TOKENIZER_PROTOTYPE.setTrimmerMatcher(StrMatcher.trimMatcher());
        TSV_TOKENIZER_PROTOTYPE.setEmptyTokenAsNull(true);
        TSV_TOKENIZER_PROTOTYPE.setIgnoreEmptyTokens(true);
    }
}

