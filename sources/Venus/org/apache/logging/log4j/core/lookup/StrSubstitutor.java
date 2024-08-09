/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.lookup;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationAware;
import org.apache.logging.log4j.core.lookup.MapLookup;
import org.apache.logging.log4j.core.lookup.StrLookup;
import org.apache.logging.log4j.core.lookup.StrMatcher;
import org.apache.logging.log4j.util.Strings;

public class StrSubstitutor
implements ConfigurationAware {
    public static final char DEFAULT_ESCAPE = '$';
    public static final StrMatcher DEFAULT_PREFIX = StrMatcher.stringMatcher("${");
    public static final StrMatcher DEFAULT_SUFFIX = StrMatcher.stringMatcher("}");
    public static final StrMatcher DEFAULT_VALUE_DELIMITER = StrMatcher.stringMatcher(":-");
    private static final int BUF_SIZE = 256;
    private char escapeChar;
    private StrMatcher prefixMatcher;
    private StrMatcher suffixMatcher;
    private StrMatcher valueDelimiterMatcher;
    private StrLookup variableResolver;
    private boolean enableSubstitutionInVariables = true;
    private Configuration configuration;

    public StrSubstitutor() {
        this(null, DEFAULT_PREFIX, DEFAULT_SUFFIX, '$');
    }

    public StrSubstitutor(Map<String, String> map) {
        this((StrLookup)new MapLookup(map), DEFAULT_PREFIX, DEFAULT_SUFFIX, '$');
    }

    public StrSubstitutor(Map<String, String> map, String string, String string2) {
        this((StrLookup)new MapLookup(map), string, string2, '$');
    }

    public StrSubstitutor(Map<String, String> map, String string, String string2, char c) {
        this((StrLookup)new MapLookup(map), string, string2, c);
    }

    public StrSubstitutor(Map<String, String> map, String string, String string2, char c, String string3) {
        this((StrLookup)new MapLookup(map), string, string2, c, string3);
    }

    public StrSubstitutor(Properties properties) {
        this(StrSubstitutor.toTypeSafeMap(properties));
    }

    public StrSubstitutor(StrLookup strLookup) {
        this(strLookup, DEFAULT_PREFIX, DEFAULT_SUFFIX, '$');
    }

    public StrSubstitutor(StrLookup strLookup, String string, String string2, char c) {
        this.setVariableResolver(strLookup);
        this.setVariablePrefix(string);
        this.setVariableSuffix(string2);
        this.setEscapeChar(c);
    }

    public StrSubstitutor(StrLookup strLookup, String string, String string2, char c, String string3) {
        this.setVariableResolver(strLookup);
        this.setVariablePrefix(string);
        this.setVariableSuffix(string2);
        this.setEscapeChar(c);
        this.setValueDelimiter(string3);
    }

    public StrSubstitutor(StrLookup strLookup, StrMatcher strMatcher, StrMatcher strMatcher2, char c) {
        this(strLookup, strMatcher, strMatcher2, c, DEFAULT_VALUE_DELIMITER);
    }

    public StrSubstitutor(StrLookup strLookup, StrMatcher strMatcher, StrMatcher strMatcher2, char c, StrMatcher strMatcher3) {
        this.setVariableResolver(strLookup);
        this.setVariablePrefixMatcher(strMatcher);
        this.setVariableSuffixMatcher(strMatcher2);
        this.setEscapeChar(c);
        this.setValueDelimiterMatcher(strMatcher3);
    }

    public static String replace(Object object, Map<String, String> map) {
        return new StrSubstitutor(map).replace(object);
    }

    public static String replace(Object object, Map<String, String> map, String string, String string2) {
        return new StrSubstitutor(map, string, string2).replace(object);
    }

    public static String replace(Object object, Properties properties) {
        if (properties == null) {
            return object.toString();
        }
        HashMap<String, String> hashMap = new HashMap<String, String>();
        Enumeration<?> enumeration = properties.propertyNames();
        while (enumeration.hasMoreElements()) {
            String string = (String)enumeration.nextElement();
            String string2 = properties.getProperty(string);
            hashMap.put(string, string2);
        }
        return StrSubstitutor.replace(object, hashMap);
    }

    private static Map<String, String> toTypeSafeMap(Properties properties) {
        HashMap<String, String> hashMap = new HashMap<String, String>(properties.size());
        for (String string : properties.stringPropertyNames()) {
            hashMap.put(string, properties.getProperty(string));
        }
        return hashMap;
    }

    public String replace(String string) {
        return this.replace(null, string);
    }

    public String replace(LogEvent logEvent, String string) {
        if (string == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder(string);
        if (!this.substitute(logEvent, stringBuilder, 0, string.length())) {
            return string;
        }
        return stringBuilder.toString();
    }

    public String replace(String string, int n, int n2) {
        return this.replace(null, string, n, n2);
    }

    public String replace(LogEvent logEvent, String string, int n, int n2) {
        if (string == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder(n2).append(string, n, n2);
        if (!this.substitute(logEvent, stringBuilder, 0, n2)) {
            return string.substring(n, n + n2);
        }
        return stringBuilder.toString();
    }

    public String replace(char[] cArray) {
        return this.replace((LogEvent)null, cArray);
    }

    public String replace(LogEvent logEvent, char[] cArray) {
        if (cArray == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder(cArray.length).append(cArray);
        this.substitute(logEvent, stringBuilder, 0, cArray.length);
        return stringBuilder.toString();
    }

    public String replace(char[] cArray, int n, int n2) {
        return this.replace(null, cArray, n, n2);
    }

    public String replace(LogEvent logEvent, char[] cArray, int n, int n2) {
        if (cArray == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder(n2).append(cArray, n, n2);
        this.substitute(logEvent, stringBuilder, 0, n2);
        return stringBuilder.toString();
    }

    public String replace(StringBuffer stringBuffer) {
        return this.replace(null, stringBuffer);
    }

    public String replace(LogEvent logEvent, StringBuffer stringBuffer) {
        if (stringBuffer == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder(stringBuffer.length()).append(stringBuffer);
        this.substitute(logEvent, stringBuilder, 0, stringBuilder.length());
        return stringBuilder.toString();
    }

    public String replace(StringBuffer stringBuffer, int n, int n2) {
        return this.replace(null, stringBuffer, n, n2);
    }

    public String replace(LogEvent logEvent, StringBuffer stringBuffer, int n, int n2) {
        if (stringBuffer == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder(n2).append(stringBuffer, n, n2);
        this.substitute(logEvent, stringBuilder, 0, n2);
        return stringBuilder.toString();
    }

    public String replace(StringBuilder stringBuilder) {
        return this.replace(null, stringBuilder);
    }

    public String replace(LogEvent logEvent, StringBuilder stringBuilder) {
        if (stringBuilder == null) {
            return null;
        }
        StringBuilder stringBuilder2 = new StringBuilder(stringBuilder.length()).append((CharSequence)stringBuilder);
        this.substitute(logEvent, stringBuilder2, 0, stringBuilder2.length());
        return stringBuilder2.toString();
    }

    public String replace(StringBuilder stringBuilder, int n, int n2) {
        return this.replace(null, stringBuilder, n, n2);
    }

    public String replace(LogEvent logEvent, StringBuilder stringBuilder, int n, int n2) {
        if (stringBuilder == null) {
            return null;
        }
        StringBuilder stringBuilder2 = new StringBuilder(n2).append(stringBuilder, n, n2);
        this.substitute(logEvent, stringBuilder2, 0, n2);
        return stringBuilder2.toString();
    }

    public String replace(Object object) {
        return this.replace(null, object);
    }

    public String replace(LogEvent logEvent, Object object) {
        if (object == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder().append(object);
        this.substitute(logEvent, stringBuilder, 0, stringBuilder.length());
        return stringBuilder.toString();
    }

    public boolean replaceIn(StringBuffer stringBuffer) {
        if (stringBuffer == null) {
            return true;
        }
        return this.replaceIn(stringBuffer, 0, stringBuffer.length());
    }

    public boolean replaceIn(StringBuffer stringBuffer, int n, int n2) {
        return this.replaceIn(null, stringBuffer, n, n2);
    }

    public boolean replaceIn(LogEvent logEvent, StringBuffer stringBuffer, int n, int n2) {
        if (stringBuffer == null) {
            return true;
        }
        StringBuilder stringBuilder = new StringBuilder(n2).append(stringBuffer, n, n2);
        if (!this.substitute(logEvent, stringBuilder, 0, n2)) {
            return true;
        }
        stringBuffer.replace(n, n + n2, stringBuilder.toString());
        return false;
    }

    public boolean replaceIn(StringBuilder stringBuilder) {
        return this.replaceIn(null, stringBuilder);
    }

    public boolean replaceIn(LogEvent logEvent, StringBuilder stringBuilder) {
        if (stringBuilder == null) {
            return true;
        }
        return this.substitute(logEvent, stringBuilder, 0, stringBuilder.length());
    }

    public boolean replaceIn(StringBuilder stringBuilder, int n, int n2) {
        return this.replaceIn(null, stringBuilder, n, n2);
    }

    public boolean replaceIn(LogEvent logEvent, StringBuilder stringBuilder, int n, int n2) {
        if (stringBuilder == null) {
            return true;
        }
        return this.substitute(logEvent, stringBuilder, n, n2);
    }

    protected boolean substitute(LogEvent logEvent, StringBuilder stringBuilder, int n, int n2) {
        return this.substitute(logEvent, stringBuilder, n, n2, null) > 0;
    }

    private int substitute(LogEvent logEvent, StringBuilder stringBuilder, int n, int n2, List<String> list) {
        StrMatcher strMatcher = this.getVariablePrefixMatcher();
        StrMatcher strMatcher2 = this.getVariableSuffixMatcher();
        char c = this.getEscapeChar();
        StrMatcher strMatcher3 = this.getValueDelimiterMatcher();
        boolean bl = this.isEnableSubstitutionInVariables();
        boolean bl2 = list == null;
        boolean bl3 = false;
        int n3 = 0;
        char[] cArray = this.getChars(stringBuilder);
        int n4 = n + n2;
        int n5 = n;
        block0: while (n5 < n4) {
            int n6 = strMatcher.isMatch(cArray, n5, n, n4);
            if (n6 == 0) {
                ++n5;
                continue;
            }
            if (n5 > n && cArray[n5 - 1] == c) {
                stringBuilder.deleteCharAt(n5 - 1);
                cArray = this.getChars(stringBuilder);
                --n3;
                bl3 = true;
                --n4;
                continue;
            }
            int n7 = n5;
            n5 += n6;
            int n8 = 0;
            int n9 = 0;
            while (n5 < n4) {
                if (bl && (n8 = strMatcher.isMatch(cArray, n5, n, n4)) != 0) {
                    ++n9;
                    n5 += n8;
                    continue;
                }
                n8 = strMatcher2.isMatch(cArray, n5, n, n4);
                if (n8 == 0) {
                    ++n5;
                    continue;
                }
                if (n9 == 0) {
                    int n10;
                    int n11;
                    Object object;
                    String string = new String(cArray, n7 + n6, n5 - n7 - n6);
                    if (bl) {
                        StringBuilder stringBuilder2 = new StringBuilder(string);
                        this.substitute(logEvent, stringBuilder2, 0, stringBuilder2.length());
                        string = stringBuilder2.toString();
                    }
                    int n12 = n5 += n8;
                    String string2 = string;
                    String string3 = null;
                    if (strMatcher3 != null) {
                        object = string.toCharArray();
                        n11 = 0;
                        for (n10 = 0; n10 < ((char[])object).length && (bl || strMatcher.isMatch((char[])object, n10, n10, ((char[])object).length) == 0); ++n10) {
                            n11 = strMatcher3.isMatch((char[])object, n10);
                            if (n11 == 0) continue;
                            string2 = string.substring(0, n10);
                            string3 = string.substring(n10 + n11);
                            break;
                        }
                    }
                    if (list == null) {
                        list = new ArrayList<String>();
                        list.add(new String(cArray, n, n2 + n3));
                    }
                    this.checkCyclicSubstitution(string2, list);
                    list.add(string2);
                    object = this.resolveVariable(logEvent, string2, stringBuilder, n7, n12);
                    if (object == null) {
                        object = string3;
                    }
                    if (object != null) {
                        n11 = ((String)object).length();
                        stringBuilder.replace(n7, n12, (String)object);
                        bl3 = true;
                        n10 = this.substitute(logEvent, stringBuilder, n7, n11, list);
                        n5 += (n10 += n11 - (n12 - n7));
                        n4 += n10;
                        n3 += n10;
                        cArray = this.getChars(stringBuilder);
                    }
                    list.remove(list.size() - 1);
                    continue block0;
                }
                --n9;
                n5 += n8;
            }
        }
        if (bl2) {
            return bl3 ? 1 : 0;
        }
        return n3;
    }

    private void checkCyclicSubstitution(String string, List<String> list) {
        if (!list.contains(string)) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder(256);
        stringBuilder.append("Infinite loop in property interpolation of ");
        stringBuilder.append(list.remove(0));
        stringBuilder.append(": ");
        this.appendWithSeparators(stringBuilder, list, "->");
        throw new IllegalStateException(stringBuilder.toString());
    }

    protected String resolveVariable(LogEvent logEvent, String string, StringBuilder stringBuilder, int n, int n2) {
        StrLookup strLookup = this.getVariableResolver();
        if (strLookup == null) {
            return null;
        }
        return strLookup.lookup(logEvent, string);
    }

    public char getEscapeChar() {
        return this.escapeChar;
    }

    public void setEscapeChar(char c) {
        this.escapeChar = c;
    }

    public StrMatcher getVariablePrefixMatcher() {
        return this.prefixMatcher;
    }

    public StrSubstitutor setVariablePrefixMatcher(StrMatcher strMatcher) {
        if (strMatcher == null) {
            throw new IllegalArgumentException("Variable prefix matcher must not be null!");
        }
        this.prefixMatcher = strMatcher;
        return this;
    }

    public StrSubstitutor setVariablePrefix(char c) {
        return this.setVariablePrefixMatcher(StrMatcher.charMatcher(c));
    }

    public StrSubstitutor setVariablePrefix(String string) {
        if (string == null) {
            throw new IllegalArgumentException("Variable prefix must not be null!");
        }
        return this.setVariablePrefixMatcher(StrMatcher.stringMatcher(string));
    }

    public StrMatcher getVariableSuffixMatcher() {
        return this.suffixMatcher;
    }

    public StrSubstitutor setVariableSuffixMatcher(StrMatcher strMatcher) {
        if (strMatcher == null) {
            throw new IllegalArgumentException("Variable suffix matcher must not be null!");
        }
        this.suffixMatcher = strMatcher;
        return this;
    }

    public StrSubstitutor setVariableSuffix(char c) {
        return this.setVariableSuffixMatcher(StrMatcher.charMatcher(c));
    }

    public StrSubstitutor setVariableSuffix(String string) {
        if (string == null) {
            throw new IllegalArgumentException("Variable suffix must not be null!");
        }
        return this.setVariableSuffixMatcher(StrMatcher.stringMatcher(string));
    }

    public StrMatcher getValueDelimiterMatcher() {
        return this.valueDelimiterMatcher;
    }

    public StrSubstitutor setValueDelimiterMatcher(StrMatcher strMatcher) {
        this.valueDelimiterMatcher = strMatcher;
        return this;
    }

    public StrSubstitutor setValueDelimiter(char c) {
        return this.setValueDelimiterMatcher(StrMatcher.charMatcher(c));
    }

    public StrSubstitutor setValueDelimiter(String string) {
        if (Strings.isEmpty(string)) {
            this.setValueDelimiterMatcher(null);
            return this;
        }
        return this.setValueDelimiterMatcher(StrMatcher.stringMatcher(string));
    }

    public StrLookup getVariableResolver() {
        return this.variableResolver;
    }

    public void setVariableResolver(StrLookup strLookup) {
        if (strLookup instanceof ConfigurationAware && this.configuration != null) {
            ((ConfigurationAware)((Object)strLookup)).setConfiguration(this.configuration);
        }
        this.variableResolver = strLookup;
    }

    public boolean isEnableSubstitutionInVariables() {
        return this.enableSubstitutionInVariables;
    }

    public void setEnableSubstitutionInVariables(boolean bl) {
        this.enableSubstitutionInVariables = bl;
    }

    private char[] getChars(StringBuilder stringBuilder) {
        char[] cArray = new char[stringBuilder.length()];
        stringBuilder.getChars(0, stringBuilder.length(), cArray, 0);
        return cArray;
    }

    public void appendWithSeparators(StringBuilder stringBuilder, Iterable<?> iterable, String string) {
        if (iterable != null) {
            string = string == null ? "" : string;
            Iterator<?> iterator2 = iterable.iterator();
            while (iterator2.hasNext()) {
                stringBuilder.append(iterator2.next());
                if (!iterator2.hasNext()) continue;
                stringBuilder.append(string);
            }
        }
    }

    public String toString() {
        return "StrSubstitutor(" + this.variableResolver.toString() + ')';
    }

    @Override
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
        if (this.variableResolver instanceof ConfigurationAware) {
            ((ConfigurationAware)((Object)this.variableResolver)).setConfiguration(this.configuration);
        }
    }
}

