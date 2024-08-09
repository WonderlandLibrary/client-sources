/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.LocaleIDs;
import com.ibm.icu.impl.locale.AsciiUtil;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public final class LocaleIDParser {
    private char[] id;
    private int index;
    private StringBuilder buffer;
    private boolean canonicalize;
    private boolean hadCountry;
    Map<String, String> keywords;
    String baseName;
    private static final char KEYWORD_SEPARATOR = '@';
    private static final char HYPHEN = '-';
    private static final char KEYWORD_ASSIGN = '=';
    private static final char COMMA = ',';
    private static final char ITEM_SEPARATOR = ';';
    private static final char DOT = '.';
    private static final char UNDERSCORE = '_';
    private static final char DONE = '\uffff';

    public LocaleIDParser(String string) {
        this(string, false);
    }

    public LocaleIDParser(String string, boolean bl) {
        this.id = string.toCharArray();
        this.index = 0;
        this.buffer = new StringBuilder(this.id.length + 5);
        this.canonicalize = bl;
    }

    private void reset() {
        this.index = 0;
        this.buffer = new StringBuilder(this.id.length + 5);
    }

    private void append(char c) {
        this.buffer.append(c);
    }

    private void addSeparator() {
        this.append('_');
    }

    private String getString(int n) {
        return this.buffer.substring(n);
    }

    private void set(int n, String string) {
        this.buffer.delete(n, this.buffer.length());
        this.buffer.insert(n, string);
    }

    private void append(String string) {
        this.buffer.append(string);
    }

    private char next() {
        if (this.index == this.id.length) {
            ++this.index;
            return '\u0000';
        }
        return this.id[this.index++];
    }

    private void skipUntilTerminatorOrIDSeparator() {
        while (!this.isTerminatorOrIDSeparator(this.next())) {
        }
        --this.index;
    }

    private boolean atTerminator() {
        return this.index >= this.id.length || this.isTerminator(this.id[this.index]);
    }

    private boolean isTerminator(char c) {
        return c == '@' || c == '\uffff' || c == '.';
    }

    private boolean isTerminatorOrIDSeparator(char c) {
        return c == '_' || c == '-' || this.isTerminator(c);
    }

    private boolean haveExperimentalLanguagePrefix() {
        char c;
        if (this.id.length > 2 && ((c = this.id[1]) == '-' || c == '_')) {
            c = this.id[0];
            return c == 'x' || c == 'X' || c == 'i' || c == 'I';
        }
        return true;
    }

    private boolean haveKeywordAssign() {
        for (int i = this.index; i < this.id.length; ++i) {
            if (this.id[i] != '=') continue;
            return false;
        }
        return true;
    }

    private int parseLanguage() {
        String string;
        char c;
        int n = this.buffer.length();
        if (this.haveExperimentalLanguagePrefix()) {
            this.append(AsciiUtil.toLower(this.id[0]));
            this.append('-');
            this.index = 2;
        }
        while (!this.isTerminatorOrIDSeparator(c = this.next())) {
            this.append(AsciiUtil.toLower(c));
        }
        --this.index;
        if (this.buffer.length() - n == 3 && (string = LocaleIDs.threeToTwoLetterLanguage(this.getString(0))) != null) {
            this.set(0, string);
        }
        return 1;
    }

    private void skipLanguage() {
        if (this.haveExperimentalLanguagePrefix()) {
            this.index = 2;
        }
        this.skipUntilTerminatorOrIDSeparator();
    }

    private int parseScript() {
        if (!this.atTerminator()) {
            char c;
            int n = this.index++;
            int n2 = this.buffer.length();
            boolean bl = true;
            while (!this.isTerminatorOrIDSeparator(c = this.next()) && AsciiUtil.isAlpha(c)) {
                if (bl) {
                    this.addSeparator();
                    this.append(AsciiUtil.toUpper(c));
                    bl = false;
                    continue;
                }
                this.append(AsciiUtil.toLower(c));
            }
            --this.index;
            if (this.index - n != 5) {
                this.index = n;
                this.buffer.delete(n2, this.buffer.length());
            } else {
                ++n2;
            }
            return n2;
        }
        return this.buffer.length();
    }

    private void skipScript() {
        if (!this.atTerminator()) {
            char c;
            int n = this.index++;
            while (!this.isTerminatorOrIDSeparator(c = this.next()) && AsciiUtil.isAlpha(c)) {
            }
            --this.index;
            if (this.index - n != 5) {
                this.index = n;
            }
        }
    }

    private int parseCountry() {
        if (!this.atTerminator()) {
            char c;
            int n = this.index++;
            int n2 = this.buffer.length();
            boolean bl = true;
            while (!this.isTerminatorOrIDSeparator(c = this.next())) {
                if (bl) {
                    this.hadCountry = true;
                    this.addSeparator();
                    ++n2;
                    bl = false;
                }
                this.append(AsciiUtil.toUpper(c));
            }
            --this.index;
            int n3 = this.buffer.length() - n2;
            if (n3 != 0) {
                String string;
                if (n3 < 2 || n3 > 3) {
                    this.index = n;
                    this.buffer.delete(--n2, this.buffer.length());
                    this.hadCountry = false;
                } else if (n3 == 3 && (string = LocaleIDs.threeToTwoLetterRegion(this.getString(n2))) != null) {
                    this.set(n2, string);
                }
            }
            return n2;
        }
        return this.buffer.length();
    }

    private void skipCountry() {
        if (!this.atTerminator()) {
            if (this.id[this.index] == '_' || this.id[this.index] == '-') {
                ++this.index;
            }
            int n = this.index;
            this.skipUntilTerminatorOrIDSeparator();
            int n2 = this.index - n;
            if (n2 < 2 || n2 > 3) {
                this.index = n;
            }
        }
    }

    private int parseVariant() {
        char c;
        int n = this.buffer.length();
        boolean bl = true;
        boolean bl2 = true;
        boolean bl3 = false;
        boolean bl4 = true;
        while ((c = this.next()) != '\uffff') {
            if (c == '.') {
                bl = false;
                bl3 = true;
                continue;
            }
            if (c == '@') {
                if (this.haveKeywordAssign()) break;
                bl3 = false;
                bl = false;
                bl2 = true;
                continue;
            }
            if (bl) {
                bl = false;
                if (c == '_' || c == '-') continue;
                --this.index;
                continue;
            }
            if (bl3) continue;
            if (bl2) {
                bl2 = false;
                if (bl4 && !this.hadCountry) {
                    this.addSeparator();
                    ++n;
                }
                this.addSeparator();
                if (bl4) {
                    ++n;
                    bl4 = false;
                }
            }
            if ((c = AsciiUtil.toUpper(c)) == '-' || c == ',') {
                c = '_';
            }
            this.append(c);
        }
        --this.index;
        return n;
    }

    public String getLanguage() {
        this.reset();
        return this.getString(this.parseLanguage());
    }

    public String getScript() {
        this.reset();
        this.skipLanguage();
        return this.getString(this.parseScript());
    }

    public String getCountry() {
        this.reset();
        this.skipLanguage();
        this.skipScript();
        return this.getString(this.parseCountry());
    }

    public String getVariant() {
        this.reset();
        this.skipLanguage();
        this.skipScript();
        this.skipCountry();
        return this.getString(this.parseVariant());
    }

    public String[] getLanguageScriptCountryVariant() {
        this.reset();
        return new String[]{this.getString(this.parseLanguage()), this.getString(this.parseScript()), this.getString(this.parseCountry()), this.getString(this.parseVariant())};
    }

    public void setBaseName(String string) {
        this.baseName = string;
    }

    public void parseBaseName() {
        if (this.baseName != null) {
            this.set(0, this.baseName);
        } else {
            this.reset();
            this.parseLanguage();
            this.parseScript();
            this.parseCountry();
            this.parseVariant();
            int n = this.buffer.length();
            if (n > 0 && this.buffer.charAt(n - 1) == '_') {
                this.buffer.deleteCharAt(n - 1);
            }
        }
    }

    public String getBaseName() {
        if (this.baseName != null) {
            return this.baseName;
        }
        this.parseBaseName();
        return this.getString(0);
    }

    public String getName() {
        this.parseBaseName();
        this.parseKeywords();
        return this.getString(0);
    }

    private boolean setToKeywordStart() {
        for (int i = this.index; i < this.id.length; ++i) {
            if (this.id[i] != '@') continue;
            if (this.canonicalize) {
                for (int j = ++i; j < this.id.length; ++j) {
                    if (this.id[j] != '=') continue;
                    this.index = i;
                    return false;
                }
                break;
            }
            if (++i >= this.id.length) break;
            this.index = i;
            return false;
        }
        return true;
    }

    private static boolean isDoneOrKeywordAssign(char c) {
        return c == '\uffff' || c == '=';
    }

    private static boolean isDoneOrItemSeparator(char c) {
        return c == '\uffff' || c == ';';
    }

    private String getKeyword() {
        int n = this.index;
        while (!LocaleIDParser.isDoneOrKeywordAssign(this.next())) {
        }
        --this.index;
        return AsciiUtil.toLowerString(new String(this.id, n, this.index - n).trim());
    }

    private String getValue() {
        int n = this.index;
        while (!LocaleIDParser.isDoneOrItemSeparator(this.next())) {
        }
        --this.index;
        return new String(this.id, n, this.index - n).trim();
    }

    private Comparator<String> getKeyComparator() {
        Comparator<String> comparator = new Comparator<String>(this){
            final LocaleIDParser this$0;
            {
                this.this$0 = localeIDParser;
            }

            @Override
            public int compare(String string, String string2) {
                return string.compareTo(string2);
            }

            @Override
            public int compare(Object object, Object object2) {
                return this.compare((String)object, (String)object2);
            }
        };
        return comparator;
    }

    public Map<String, String> getKeywordMap() {
        block6: {
            Map<String, String> map;
            block7: {
                String string;
                if (this.keywords != null) break block6;
                map = null;
                if (!this.setToKeywordStart()) break block7;
                while ((string = this.getKeyword()).length() != 0) {
                    block9: {
                        String string2;
                        block11: {
                            block10: {
                                block8: {
                                    char c = this.next();
                                    if (c == '=') break block8;
                                    if (c == '\uffff') {
                                        break;
                                    }
                                    break block9;
                                }
                                string2 = this.getValue();
                                if (string2.length() == 0) break block9;
                                if (map != null) break block10;
                                map = new TreeMap(this.getKeyComparator());
                                break block11;
                            }
                            if (((TreeMap)map).containsKey(string)) break block9;
                        }
                        ((TreeMap)map).put(string, string2);
                    }
                    if (this.next() == ';') continue;
                }
            }
            this.keywords = map != null ? map : Collections.emptyMap();
        }
        return this.keywords;
    }

    private int parseKeywords() {
        int n = this.buffer.length();
        Map<String, String> map = this.getKeywordMap();
        if (!map.isEmpty()) {
            boolean bl = true;
            for (Map.Entry<String, String> entry : map.entrySet()) {
                this.append(bl ? (char)'@' : ';');
                bl = false;
                this.append(entry.getKey());
                this.append('=');
                this.append(entry.getValue());
            }
            if (!bl) {
                ++n;
            }
        }
        return n;
    }

    public Iterator<String> getKeywords() {
        Map<String, String> map = this.getKeywordMap();
        return map.isEmpty() ? null : map.keySet().iterator();
    }

    public String getKeywordValue(String string) {
        Map<String, String> map = this.getKeywordMap();
        return map.isEmpty() ? null : map.get(AsciiUtil.toLowerString(string.trim()));
    }

    public void defaultKeywordValue(String string, String string2) {
        this.setKeywordValue(string, string2, false);
    }

    public void setKeywordValue(String string, String string2) {
        this.setKeywordValue(string, string2, true);
    }

    private void setKeywordValue(String string, String string2, boolean bl) {
        if (string == null) {
            if (bl) {
                this.keywords = Collections.emptyMap();
            }
        } else {
            if ((string = AsciiUtil.toLowerString(string.trim())).length() == 0) {
                throw new IllegalArgumentException("keyword must not be empty");
            }
            if (string2 != null && (string2 = string2.trim()).length() == 0) {
                throw new IllegalArgumentException("value must not be empty");
            }
            Map<String, String> map = this.getKeywordMap();
            if (map.isEmpty()) {
                if (string2 != null) {
                    this.keywords = new TreeMap<String, String>(this.getKeyComparator());
                    this.keywords.put(string, string2.trim());
                }
            } else if (bl || !map.containsKey(string)) {
                if (string2 != null) {
                    map.put(string, string2);
                } else {
                    map.remove(string);
                    if (map.isEmpty()) {
                        this.keywords = Collections.emptyMap();
                    }
                }
            }
        }
    }
}

