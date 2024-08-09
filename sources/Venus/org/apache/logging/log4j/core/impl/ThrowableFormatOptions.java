/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.apache.logging.log4j.core.pattern.JAnsiTextRenderer;
import org.apache.logging.log4j.core.pattern.PlainTextRenderer;
import org.apache.logging.log4j.core.pattern.TextRenderer;
import org.apache.logging.log4j.core.util.Loader;
import org.apache.logging.log4j.core.util.Patterns;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.Strings;

public final class ThrowableFormatOptions {
    private static final int DEFAULT_LINES = Integer.MAX_VALUE;
    protected static final ThrowableFormatOptions DEFAULT = new ThrowableFormatOptions();
    private static final String FULL = "full";
    private static final String NONE = "none";
    private static final String SHORT = "short";
    private final TextRenderer textRenderer;
    private final int lines;
    private final String separator;
    private final List<String> ignorePackages;
    public static final String CLASS_NAME = "short.className";
    public static final String METHOD_NAME = "short.methodName";
    public static final String LINE_NUMBER = "short.lineNumber";
    public static final String FILE_NAME = "short.fileName";
    public static final String MESSAGE = "short.message";
    public static final String LOCALIZED_MESSAGE = "short.localizedMessage";

    protected ThrowableFormatOptions(int n, String string, List<String> list, TextRenderer textRenderer) {
        this.lines = n;
        this.separator = string == null ? Strings.LINE_SEPARATOR : string;
        this.ignorePackages = list;
        this.textRenderer = textRenderer == null ? PlainTextRenderer.getInstance() : textRenderer;
    }

    protected ThrowableFormatOptions(List<String> list) {
        this(Integer.MAX_VALUE, null, list, null);
    }

    protected ThrowableFormatOptions() {
        this(Integer.MAX_VALUE, null, null, null);
    }

    public int getLines() {
        return this.lines;
    }

    public String getSeparator() {
        return this.separator;
    }

    public TextRenderer getTextRenderer() {
        return this.textRenderer;
    }

    public List<String> getIgnorePackages() {
        return this.ignorePackages;
    }

    public boolean allLines() {
        return this.lines == Integer.MAX_VALUE;
    }

    public boolean anyLines() {
        return this.lines > 0;
    }

    public int minLines(int n) {
        return this.lines > n ? n : this.lines;
    }

    public boolean hasPackages() {
        return this.ignorePackages != null && !this.ignorePackages.isEmpty();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('{').append(this.allLines() ? FULL : (this.lines == 2 ? SHORT : (this.anyLines() ? String.valueOf(this.lines) : NONE))).append('}');
        stringBuilder.append("{separator(").append(this.separator).append(")}");
        if (this.hasPackages()) {
            stringBuilder.append("{filters(");
            for (String string : this.ignorePackages) {
                stringBuilder.append(string).append(',');
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            stringBuilder.append(")}");
        }
        return stringBuilder.toString();
    }

    public static ThrowableFormatOptions newInstance(String[] stringArray) {
        Object object;
        ArrayList<String> arrayList;
        String string;
        if (stringArray == null || stringArray.length == 0) {
            return DEFAULT;
        }
        if (stringArray.length == 1 && Strings.isNotEmpty(stringArray[0])) {
            String[] stringArray2 = stringArray[0].split(Patterns.COMMA_SEPARATOR, 2);
            string = stringArray2[0].trim();
            arrayList = new Scanner(string);
            object = null;
            try {
                if (stringArray2.length > 1 && (string.equalsIgnoreCase(FULL) || string.equalsIgnoreCase(SHORT) || string.equalsIgnoreCase(NONE) || ((Scanner)((Object)arrayList)).hasNextInt())) {
                    stringArray = new String[]{string, stringArray2[5].trim()};
                }
            } catch (Throwable throwable) {
                object = throwable;
                throw throwable;
            } finally {
                if (arrayList != null) {
                    if (object != null) {
                        try {
                            ((Scanner)((Object)arrayList)).close();
                        } catch (Throwable throwable) {
                            ((Throwable)object).addSuppressed(throwable);
                        }
                    } else {
                        ((Scanner)((Object)arrayList)).close();
                    }
                }
            }
        }
        int n = ThrowableFormatOptions.DEFAULT.lines;
        string = ThrowableFormatOptions.DEFAULT.separator;
        arrayList = ThrowableFormatOptions.DEFAULT.ignorePackages;
        object = ThrowableFormatOptions.DEFAULT.textRenderer;
        for (String string2 : stringArray) {
            String string3;
            String string4;
            if (string2 == null || (string4 = string2.trim()).isEmpty()) continue;
            if (string4.startsWith("separator(") && string4.endsWith(")")) {
                string = string4.substring(10, string4.length() - 1);
                continue;
            }
            if (string4.startsWith("filters(") && string4.endsWith(")")) {
                String[] stringArray3;
                string3 = string4.substring(8, string4.length() - 1);
                if (string3.length() <= 0 || (stringArray3 = string3.split(Patterns.COMMA_SEPARATOR)).length <= 0) continue;
                arrayList = new ArrayList<String>(stringArray3.length);
                for (String string5 : stringArray3) {
                    if ((string5 = string5.trim()).length() <= 0) continue;
                    arrayList.add(string5);
                }
                continue;
            }
            if (string4.equalsIgnoreCase(NONE)) {
                n = 0;
                continue;
            }
            if (string4.equalsIgnoreCase(SHORT) || string4.equalsIgnoreCase(CLASS_NAME) || string4.equalsIgnoreCase(METHOD_NAME) || string4.equalsIgnoreCase(LINE_NUMBER) || string4.equalsIgnoreCase(FILE_NAME) || string4.equalsIgnoreCase(MESSAGE) || string4.equalsIgnoreCase(LOCALIZED_MESSAGE)) {
                n = 2;
                continue;
            }
            if (string4.startsWith("ansi(") && string4.endsWith(")") || string4.equals("ansi")) {
                if (Loader.isJansiAvailable()) {
                    string3 = string4.equals("ansi") ? "" : string4.substring(5, string4.length() - 1);
                    object = new JAnsiTextRenderer(new String[]{null, string3}, JAnsiTextRenderer.DefaultExceptionStyleMap);
                    continue;
                }
                StatusLogger.getLogger().warn("You requested ANSI exception rendering but JANSI is not on the classpath. Please see https://logging.apache.org/log4j/2.x/runtime-dependencies.html");
                continue;
            }
            if (string4.equalsIgnoreCase(FULL)) continue;
            n = Integer.parseInt(string4);
        }
        return new ThrowableFormatOptions(n, string, (List<String>)arrayList, (TextRenderer)object);
    }
}

