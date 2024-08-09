/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.pattern;

import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.util.PluginManager;
import org.apache.logging.log4j.core.config.plugins.util.PluginType;
import org.apache.logging.log4j.core.pattern.AnsiConverter;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.ExtendedThrowablePatternConverter;
import org.apache.logging.log4j.core.pattern.FormattingInfo;
import org.apache.logging.log4j.core.pattern.LiteralPatternConverter;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.core.pattern.NanoTimePatternConverter;
import org.apache.logging.log4j.core.pattern.PatternConverter;
import org.apache.logging.log4j.core.pattern.PatternFormatter;
import org.apache.logging.log4j.core.util.SystemNanoClock;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.Strings;

public final class PatternParser {
    static final String DISABLE_ANSI = "disableAnsi";
    static final String NO_CONSOLE_NO_ANSI = "noConsoleNoAnsi";
    private static final char ESCAPE_CHAR = '%';
    private static final Logger LOGGER = StatusLogger.getLogger();
    private static final int BUF_SIZE = 32;
    private static final int DECIMAL = 10;
    private final Configuration config;
    private final Map<String, Class<PatternConverter>> converterRules;

    public PatternParser(String string) {
        this(null, string, null, null);
    }

    public PatternParser(Configuration configuration, String string, Class<?> clazz) {
        this(configuration, string, clazz, null);
    }

    public PatternParser(Configuration configuration, String string, Class<?> clazz, Class<?> clazz2) {
        this.config = configuration;
        PluginManager pluginManager = new PluginManager(string);
        pluginManager.collectPlugins(configuration == null ? null : configuration.getPluginPackages());
        Map<String, PluginType<?>> map = pluginManager.getPlugins();
        LinkedHashMap<String, Class<PatternConverter>> linkedHashMap = new LinkedHashMap<String, Class<PatternConverter>>();
        for (PluginType<?> pluginType : map.values()) {
            try {
                ConverterKeys converterKeys;
                Class<?> clazz3 = pluginType.getPluginClass();
                if (clazz2 != null && !clazz2.isAssignableFrom(clazz3) || (converterKeys = clazz3.getAnnotation(ConverterKeys.class)) == null) continue;
                for (String string2 : converterKeys.value()) {
                    if (linkedHashMap.containsKey(string2)) {
                        LOGGER.warn("Converter key '{}' is already mapped to '{}'. Sorry, Dave, I can't let you do that! Ignoring plugin [{}].", (Object)string2, linkedHashMap.get(string2), (Object)clazz3);
                        continue;
                    }
                    linkedHashMap.put(string2, clazz3);
                }
            } catch (Exception exception) {
                LOGGER.error("Error processing plugin " + pluginType.getElementName(), (Throwable)exception);
            }
        }
        this.converterRules = linkedHashMap;
    }

    public List<PatternFormatter> parse(String string) {
        return this.parse(string, false, false, true);
    }

    public List<PatternFormatter> parse(String string, boolean bl, boolean bl2) {
        return this.parse(string, bl, false, bl2);
    }

    public List<PatternFormatter> parse(String string, boolean bl, boolean bl2, boolean bl3) {
        ArrayList<PatternFormatter> arrayList = new ArrayList<PatternFormatter>();
        ArrayList<PatternConverter> arrayList2 = new ArrayList<PatternConverter>();
        ArrayList<FormattingInfo> arrayList3 = new ArrayList<FormattingInfo>();
        this.parse(string, arrayList2, arrayList3, bl2, bl3, false);
        Iterator iterator2 = arrayList3.iterator();
        boolean bl4 = false;
        for (PatternConverter patternConverter : arrayList2) {
            LogEventPatternConverter logEventPatternConverter;
            if (patternConverter instanceof NanoTimePatternConverter && this.config != null) {
                this.config.setNanoClock(new SystemNanoClock());
            }
            if (patternConverter instanceof LogEventPatternConverter) {
                logEventPatternConverter = (LogEventPatternConverter)patternConverter;
                bl4 |= logEventPatternConverter.handlesThrowable();
            } else {
                logEventPatternConverter = new LiteralPatternConverter(this.config, "", true);
            }
            FormattingInfo formattingInfo = iterator2.hasNext() ? (FormattingInfo)iterator2.next() : FormattingInfo.getDefault();
            arrayList.add(new PatternFormatter(logEventPatternConverter, formattingInfo));
        }
        if (bl && !bl4) {
            ExtendedThrowablePatternConverter extendedThrowablePatternConverter = ExtendedThrowablePatternConverter.newInstance(null);
            arrayList.add(new PatternFormatter(extendedThrowablePatternConverter, FormattingInfo.getDefault()));
        }
        return arrayList;
    }

    private static int extractConverter(char c, String string, int n, StringBuilder stringBuilder, StringBuilder stringBuilder2) {
        int n2;
        stringBuilder.setLength(0);
        if (!Character.isUnicodeIdentifierStart(c)) {
            return n2;
        }
        stringBuilder.append(c);
        for (n2 = n; n2 < string.length() && Character.isUnicodeIdentifierPart(string.charAt(n2)); ++n2) {
            stringBuilder.append(string.charAt(n2));
            stringBuilder2.append(string.charAt(n2));
        }
        return n2;
    }

    private static int extractOptions(String string, int n, List<String> list) {
        int n2 = n;
        while (n2 < string.length() && string.charAt(n2) == '{') {
            int n3;
            int n4 = n2++;
            int n5 = 0;
            while ((n3 = string.indexOf(125, n2)) != -1) {
                int n6 = string.indexOf("{", n2);
                if (n6 != -1 && n6 < n3) {
                    n2 = n3 + 1;
                    ++n5;
                } else if (n5 > 0) {
                    --n5;
                }
                if (n5 > 0) continue;
            }
            if (n3 == -1) break;
            String string2 = string.substring(n4 + 1, n3);
            list.add(string2);
            n2 = n3 + 1;
        }
        return n2;
    }

    public void parse(String string, List<PatternConverter> list, List<FormattingInfo> list2, boolean bl, boolean bl2) {
        this.parse(string, list, list2, false, bl, bl2);
    }

    public void parse(String string, List<PatternConverter> list, List<FormattingInfo> list2, boolean bl, boolean bl2, boolean bl3) {
        Objects.requireNonNull(string, "pattern");
        StringBuilder stringBuilder = new StringBuilder(32);
        int n = string.length();
        ParserState parserState = ParserState.LITERAL_STATE;
        int n2 = 0;
        FormattingInfo formattingInfo = FormattingInfo.getDefault();
        while (n2 < n) {
            char c = string.charAt(n2++);
            block0 : switch (1.$SwitchMap$org$apache$logging$log4j$core$pattern$PatternParser$ParserState[parserState.ordinal()]) {
                case 1: {
                    if (n2 == n) {
                        stringBuilder.append(c);
                        break;
                    }
                    if (c == '%') {
                        switch (string.charAt(n2)) {
                            case '%': {
                                stringBuilder.append(c);
                                ++n2;
                                break block0;
                            }
                        }
                        if (stringBuilder.length() != 0) {
                            list.add(new LiteralPatternConverter(this.config, stringBuilder.toString(), bl3));
                            list2.add(FormattingInfo.getDefault());
                        }
                        stringBuilder.setLength(0);
                        stringBuilder.append(c);
                        parserState = ParserState.CONVERTER_STATE;
                        formattingInfo = FormattingInfo.getDefault();
                        break;
                    }
                    stringBuilder.append(c);
                    break;
                }
                case 2: {
                    stringBuilder.append(c);
                    switch (c) {
                        case '-': {
                            formattingInfo = new FormattingInfo(true, formattingInfo.getMinLength(), formattingInfo.getMaxLength(), formattingInfo.isLeftTruncate());
                            break block0;
                        }
                        case '.': {
                            parserState = ParserState.DOT_STATE;
                            break block0;
                        }
                    }
                    if (c >= '0' && c <= '9') {
                        formattingInfo = new FormattingInfo(formattingInfo.isLeftAligned(), c - 48, formattingInfo.getMaxLength(), formattingInfo.isLeftTruncate());
                        parserState = ParserState.MIN_STATE;
                        break;
                    }
                    n2 = this.finalizeConverter(c, string, n2, stringBuilder, formattingInfo, this.converterRules, list, list2, bl, bl2, bl3);
                    parserState = ParserState.LITERAL_STATE;
                    formattingInfo = FormattingInfo.getDefault();
                    stringBuilder.setLength(0);
                    break;
                }
                case 3: {
                    stringBuilder.append(c);
                    if (c >= '0' && c <= '9') {
                        formattingInfo = new FormattingInfo(formattingInfo.isLeftAligned(), formattingInfo.getMinLength() * 10 + c - 48, formattingInfo.getMaxLength(), formattingInfo.isLeftTruncate());
                        break;
                    }
                    if (c == '.') {
                        parserState = ParserState.DOT_STATE;
                        break;
                    }
                    n2 = this.finalizeConverter(c, string, n2, stringBuilder, formattingInfo, this.converterRules, list, list2, bl, bl2, bl3);
                    parserState = ParserState.LITERAL_STATE;
                    formattingInfo = FormattingInfo.getDefault();
                    stringBuilder.setLength(0);
                    break;
                }
                case 4: {
                    stringBuilder.append(c);
                    switch (c) {
                        case '-': {
                            formattingInfo = new FormattingInfo(formattingInfo.isLeftAligned(), formattingInfo.getMinLength(), formattingInfo.getMaxLength(), false);
                            break block0;
                        }
                    }
                    if (c >= '0' && c <= '9') {
                        formattingInfo = new FormattingInfo(formattingInfo.isLeftAligned(), formattingInfo.getMinLength(), c - 48, formattingInfo.isLeftTruncate());
                        parserState = ParserState.MAX_STATE;
                        break;
                    }
                    LOGGER.error("Error occurred in position " + n2 + ".\n Was expecting digit, instead got char \"" + c + "\".");
                    parserState = ParserState.LITERAL_STATE;
                    break;
                }
                case 5: {
                    stringBuilder.append(c);
                    if (c >= '0' && c <= '9') {
                        formattingInfo = new FormattingInfo(formattingInfo.isLeftAligned(), formattingInfo.getMinLength(), formattingInfo.getMaxLength() * 10 + c - 48, formattingInfo.isLeftTruncate());
                        break;
                    }
                    n2 = this.finalizeConverter(c, string, n2, stringBuilder, formattingInfo, this.converterRules, list, list2, bl, bl2, bl3);
                    parserState = ParserState.LITERAL_STATE;
                    formattingInfo = FormattingInfo.getDefault();
                    stringBuilder.setLength(0);
                }
            }
        }
        if (stringBuilder.length() != 0) {
            list.add(new LiteralPatternConverter(this.config, stringBuilder.toString(), bl3));
            list2.add(FormattingInfo.getDefault());
        }
    }

    private PatternConverter createConverter(String string, StringBuilder stringBuilder, Map<String, Class<PatternConverter>> map, List<String> list, boolean bl, boolean bl2) {
        Object[] objectArray;
        String string2 = string;
        Class<PatternConverter> clazz = null;
        if (map == null) {
            LOGGER.error("Null rules for [" + string + ']');
            return null;
        }
        for (int i = string.length(); i > 0 && clazz == null; --i) {
            string2 = string2.substring(0, i);
            clazz = map.get(string2);
        }
        if (clazz == null) {
            LOGGER.error("Unrecognized format specifier [" + string + ']');
            return null;
        }
        if (AnsiConverter.class.isAssignableFrom(clazz)) {
            list.add("disableAnsi=" + bl);
            list.add("noConsoleNoAnsi=" + bl2);
        }
        Method[] methodArray = clazz.getDeclaredMethods();
        Method method = null;
        for (Method method2 : methodArray) {
            if (!Modifier.isStatic(method2.getModifiers()) || !method2.getDeclaringClass().equals(clazz) || !method2.getName().equals("newInstance")) continue;
            if (method == null) {
                method = method2;
                continue;
            }
            if (!method2.getReturnType().equals(method.getReturnType())) continue;
            LOGGER.error("Class " + clazz + " cannot contain multiple static newInstance methods");
            return null;
        }
        if (method == null) {
            LOGGER.error("Class " + clazz + " does not contain a static newInstance method");
            return null;
        }
        GenericDeclaration[] genericDeclarationArray = method.getParameterTypes();
        Object[] objectArray2 = objectArray = genericDeclarationArray.length > 0 ? new Object[genericDeclarationArray.length] : null;
        if (objectArray != null) {
            int n = 0;
            boolean bl3 = false;
            for (GenericDeclaration genericDeclaration : genericDeclarationArray) {
                if (((Class)genericDeclaration).isArray() && ((Class)genericDeclaration).getName().equals("[Ljava.lang.String;")) {
                    String[] stringArray;
                    objectArray[n] = stringArray = list.toArray(new String[list.size()]);
                } else if (((Class)genericDeclaration).isAssignableFrom(Configuration.class)) {
                    objectArray[n] = this.config;
                } else {
                    LOGGER.error("Unknown parameter type " + ((Class)genericDeclaration).getName() + " for static newInstance method of " + clazz.getName());
                    bl3 = true;
                }
                ++n;
            }
            if (bl3) {
                return null;
            }
        }
        try {
            Object object = method.invoke(null, objectArray);
            if (object instanceof PatternConverter) {
                stringBuilder.delete(0, stringBuilder.length() - (string.length() - string2.length()));
                return (PatternConverter)object;
            }
            LOGGER.warn("Class {} does not extend PatternConverter.", (Object)clazz.getName());
        } catch (Exception exception) {
            LOGGER.error("Error creating converter for " + string, (Throwable)exception);
        }
        return null;
    }

    private int finalizeConverter(char c, String string, int n, StringBuilder stringBuilder, FormattingInfo formattingInfo, Map<String, Class<PatternConverter>> map, List<PatternConverter> list, List<FormattingInfo> list2, boolean bl, boolean bl2, boolean bl3) {
        int n2 = n;
        StringBuilder stringBuilder2 = new StringBuilder();
        n2 = PatternParser.extractConverter(c, string, n2, stringBuilder2, stringBuilder);
        String string2 = stringBuilder2.toString();
        ArrayList<String> arrayList = new ArrayList<String>();
        n2 = PatternParser.extractOptions(string, n2, arrayList);
        PatternConverter patternConverter = this.createConverter(string2, stringBuilder, map, arrayList, bl, bl2);
        if (patternConverter == null) {
            StringBuilder stringBuilder3;
            if (Strings.isEmpty(string2)) {
                stringBuilder3 = new StringBuilder("Empty conversion specifier starting at position ");
            } else {
                stringBuilder3 = new StringBuilder("Unrecognized conversion specifier [");
                stringBuilder3.append(string2);
                stringBuilder3.append("] starting at position ");
            }
            stringBuilder3.append(Integer.toString(n2));
            stringBuilder3.append(" in conversion pattern.");
            LOGGER.error(stringBuilder3.toString());
            list.add(new LiteralPatternConverter(this.config, stringBuilder.toString(), bl3));
            list2.add(FormattingInfo.getDefault());
        } else {
            list.add(patternConverter);
            list2.add(formattingInfo);
            if (stringBuilder.length() > 0) {
                list.add(new LiteralPatternConverter(this.config, stringBuilder.toString(), bl3));
                list2.add(FormattingInfo.getDefault());
            }
        }
        stringBuilder.setLength(0);
        return n2;
    }

    static class 1 {
        static final int[] $SwitchMap$org$apache$logging$log4j$core$pattern$PatternParser$ParserState = new int[ParserState.values().length];

        static {
            try {
                1.$SwitchMap$org$apache$logging$log4j$core$pattern$PatternParser$ParserState[ParserState.LITERAL_STATE.ordinal()] = 1;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                1.$SwitchMap$org$apache$logging$log4j$core$pattern$PatternParser$ParserState[ParserState.CONVERTER_STATE.ordinal()] = 2;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                1.$SwitchMap$org$apache$logging$log4j$core$pattern$PatternParser$ParserState[ParserState.MIN_STATE.ordinal()] = 3;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                1.$SwitchMap$org$apache$logging$log4j$core$pattern$PatternParser$ParserState[ParserState.DOT_STATE.ordinal()] = 4;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                1.$SwitchMap$org$apache$logging$log4j$core$pattern$PatternParser$ParserState[ParserState.MAX_STATE.ordinal()] = 5;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
        }
    }

    private static enum ParserState {
        LITERAL_STATE,
        CONVERTER_STATE,
        DOT_STATE,
        MIN_STATE,
        MAX_STATE;

    }
}

