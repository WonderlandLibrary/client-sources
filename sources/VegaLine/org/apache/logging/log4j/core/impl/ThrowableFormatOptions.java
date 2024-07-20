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

    protected ThrowableFormatOptions(int lines, String separator, List<String> ignorePackages, TextRenderer textRenderer) {
        this.lines = lines;
        this.separator = separator == null ? Strings.LINE_SEPARATOR : separator;
        this.ignorePackages = ignorePackages;
        this.textRenderer = textRenderer == null ? PlainTextRenderer.getInstance() : textRenderer;
    }

    protected ThrowableFormatOptions(List<String> packages) {
        this(Integer.MAX_VALUE, null, packages, null);
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

    public int minLines(int maxLines) {
        return this.lines > maxLines ? maxLines : this.lines;
    }

    public boolean hasPackages() {
        return this.ignorePackages != null && !this.ignorePackages.isEmpty();
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append('{').append(this.allLines() ? FULL : (this.lines == 2 ? SHORT : (this.anyLines() ? String.valueOf(this.lines) : NONE))).append('}');
        s.append("{separator(").append(this.separator).append(")}");
        if (this.hasPackages()) {
            s.append("{filters(");
            for (String p : this.ignorePackages) {
                s.append(p).append(',');
            }
            s.deleteCharAt(s.length() - 1);
            s.append(")}");
        }
        return s.toString();
    }

    public static ThrowableFormatOptions newInstance(String[] options) {
        if (options == null || options.length == 0) {
            return DEFAULT;
        }
        if (options.length == 1 && Strings.isNotEmpty(options[0])) {
            String[] opts = options[0].split(Patterns.COMMA_SEPARATOR, 2);
            String first = opts[0].trim();
            try (Scanner scanner = new Scanner(first);){
                if (opts.length > 1 && (first.equalsIgnoreCase(FULL) || first.equalsIgnoreCase(SHORT) || first.equalsIgnoreCase(NONE) || scanner.hasNextInt())) {
                    options = new String[]{first, opts[1].trim()};
                }
            }
        }
        int lines = ThrowableFormatOptions.DEFAULT.lines;
        String separator = ThrowableFormatOptions.DEFAULT.separator;
        List<String> packages = ThrowableFormatOptions.DEFAULT.ignorePackages;
        TextRenderer ansiRenderer = ThrowableFormatOptions.DEFAULT.textRenderer;
        for (String rawOption : options) {
            String option;
            if (rawOption == null || (option = rawOption.trim()).isEmpty()) continue;
            if (option.startsWith("separator(") && option.endsWith(")")) {
                separator = option.substring("separator(".length(), option.length() - 1);
                continue;
            }
            if (option.startsWith("filters(") && option.endsWith(")")) {
                String[] array;
                String filterStr = option.substring("filters(".length(), option.length() - 1);
                if (filterStr.length() <= 0 || (array = filterStr.split(Patterns.COMMA_SEPARATOR)).length <= 0) continue;
                packages = new ArrayList<String>(array.length);
                for (String token : array) {
                    if ((token = token.trim()).length() <= 0) continue;
                    packages.add(token);
                }
                continue;
            }
            if (option.equalsIgnoreCase(NONE)) {
                lines = 0;
                continue;
            }
            if (option.equalsIgnoreCase(SHORT) || option.equalsIgnoreCase(CLASS_NAME) || option.equalsIgnoreCase(METHOD_NAME) || option.equalsIgnoreCase(LINE_NUMBER) || option.equalsIgnoreCase(FILE_NAME) || option.equalsIgnoreCase(MESSAGE) || option.equalsIgnoreCase(LOCALIZED_MESSAGE)) {
                lines = 2;
                continue;
            }
            if (option.startsWith("ansi(") && option.endsWith(")") || option.equals("ansi")) {
                if (Loader.isJansiAvailable()) {
                    String styleMapStr = option.equals("ansi") ? "" : option.substring("ansi(".length(), option.length() - 1);
                    ansiRenderer = new JAnsiTextRenderer(new String[]{null, styleMapStr}, JAnsiTextRenderer.DefaultExceptionStyleMap);
                    continue;
                }
                StatusLogger.getLogger().warn("You requested ANSI exception rendering but JANSI is not on the classpath. Please see https://logging.apache.org/log4j/2.x/runtime-dependencies.html");
                continue;
            }
            if (option.equalsIgnoreCase(FULL)) continue;
            lines = Integer.parseInt(option);
        }
        return new ThrowableFormatOptions(lines, separator, packages, ansiRenderer);
    }
}

