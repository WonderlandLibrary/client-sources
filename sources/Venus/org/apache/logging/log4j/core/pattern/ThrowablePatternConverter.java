/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.pattern;

import java.io.PrintWriter;
import java.io.StringWriter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.impl.ThrowableFormatOptions;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.util.Strings;

@Plugin(name="ThrowablePatternConverter", category="Converter")
@ConverterKeys(value={"ex", "throwable", "exception"})
public class ThrowablePatternConverter
extends LogEventPatternConverter {
    private String rawOption;
    protected final ThrowableFormatOptions options;

    protected ThrowablePatternConverter(String string, String string2, String[] stringArray) {
        super(string, string2);
        this.options = ThrowableFormatOptions.newInstance(stringArray);
        if (stringArray != null && stringArray.length > 0) {
            this.rawOption = stringArray[0];
        }
    }

    public static ThrowablePatternConverter newInstance(String[] stringArray) {
        return new ThrowablePatternConverter("Throwable", "throwable", stringArray);
    }

    @Override
    public void format(LogEvent logEvent, StringBuilder stringBuilder) {
        Throwable throwable = logEvent.getThrown();
        if (this.isSubShortOption()) {
            this.formatSubShortOption(throwable, stringBuilder);
        } else if (throwable != null && this.options.anyLines()) {
            this.formatOption(throwable, stringBuilder);
        }
    }

    private boolean isSubShortOption() {
        return "short.message".equalsIgnoreCase(this.rawOption) || "short.localizedMessage".equalsIgnoreCase(this.rawOption) || "short.fileName".equalsIgnoreCase(this.rawOption) || "short.lineNumber".equalsIgnoreCase(this.rawOption) || "short.methodName".equalsIgnoreCase(this.rawOption) || "short.className".equalsIgnoreCase(this.rawOption);
    }

    private void formatSubShortOption(Throwable throwable, StringBuilder stringBuilder) {
        StackTraceElement[] stackTraceElementArray;
        StackTraceElement stackTraceElement = null;
        if (throwable != null && (stackTraceElementArray = throwable.getStackTrace()) != null && stackTraceElementArray.length > 0) {
            stackTraceElement = stackTraceElementArray[0];
        }
        if (throwable != null && stackTraceElement != null) {
            String string = "";
            if ("short.className".equalsIgnoreCase(this.rawOption)) {
                string = stackTraceElement.getClassName();
            } else if ("short.methodName".equalsIgnoreCase(this.rawOption)) {
                string = stackTraceElement.getMethodName();
            } else if ("short.lineNumber".equalsIgnoreCase(this.rawOption)) {
                string = String.valueOf(stackTraceElement.getLineNumber());
            } else if ("short.message".equalsIgnoreCase(this.rawOption)) {
                string = throwable.getMessage();
            } else if ("short.localizedMessage".equalsIgnoreCase(this.rawOption)) {
                string = throwable.getLocalizedMessage();
            } else if ("short.fileName".equalsIgnoreCase(this.rawOption)) {
                string = stackTraceElement.getFileName();
            }
            int n = stringBuilder.length();
            if (n > 0 && !Character.isWhitespace(stringBuilder.charAt(n - 1))) {
                stringBuilder.append(' ');
            }
            stringBuilder.append(string);
        }
    }

    private void formatOption(Throwable throwable, StringBuilder stringBuilder) {
        StringWriter stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));
        int n = stringBuilder.length();
        if (n > 0 && !Character.isWhitespace(stringBuilder.charAt(n - 1))) {
            stringBuilder.append(' ');
        }
        if (!this.options.allLines() || !Strings.LINE_SEPARATOR.equals(this.options.getSeparator())) {
            StringBuilder stringBuilder2 = new StringBuilder();
            String[] stringArray = stringWriter.toString().split(Strings.LINE_SEPARATOR);
            int n2 = this.options.minLines(stringArray.length) - 1;
            for (int i = 0; i <= n2; ++i) {
                stringBuilder2.append(stringArray[i]);
                if (i >= n2) continue;
                stringBuilder2.append(this.options.getSeparator());
            }
            stringBuilder.append(stringBuilder2.toString());
        } else {
            stringBuilder.append(stringWriter.toString());
        }
    }

    @Override
    public boolean handlesThrowable() {
        return false;
    }
}

