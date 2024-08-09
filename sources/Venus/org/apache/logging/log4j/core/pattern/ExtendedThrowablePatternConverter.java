/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.pattern;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.impl.ThrowableProxy;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.ThrowablePatternConverter;
import org.apache.logging.log4j.util.Strings;

@Plugin(name="ExtendedThrowablePatternConverter", category="Converter")
@ConverterKeys(value={"xEx", "xThrowable", "xException"})
public final class ExtendedThrowablePatternConverter
extends ThrowablePatternConverter {
    private ExtendedThrowablePatternConverter(String[] stringArray) {
        super("ExtendedThrowable", "throwable", stringArray);
    }

    public static ExtendedThrowablePatternConverter newInstance(String[] stringArray) {
        return new ExtendedThrowablePatternConverter(stringArray);
    }

    @Override
    public void format(LogEvent logEvent, StringBuilder stringBuilder) {
        ThrowableProxy throwableProxy = logEvent.getThrownProxy();
        Throwable throwable = logEvent.getThrown();
        if ((throwable != null || throwableProxy != null) && this.options.anyLines()) {
            if (throwableProxy == null) {
                super.format(logEvent, stringBuilder);
                return;
            }
            String string = throwableProxy.getExtendedStackTraceAsString(this.options.getIgnorePackages(), this.options.getTextRenderer());
            int n = stringBuilder.length();
            if (n > 0 && !Character.isWhitespace(stringBuilder.charAt(n - 1))) {
                stringBuilder.append(' ');
            }
            if (!this.options.allLines() || !Strings.LINE_SEPARATOR.equals(this.options.getSeparator())) {
                StringBuilder stringBuilder2 = new StringBuilder();
                String[] stringArray = string.split(Strings.LINE_SEPARATOR);
                int n2 = this.options.minLines(stringArray.length) - 1;
                for (int i = 0; i <= n2; ++i) {
                    stringBuilder2.append(stringArray[i]);
                    if (i >= n2) continue;
                    stringBuilder2.append(this.options.getSeparator());
                }
                stringBuilder.append(stringBuilder2.toString());
            } else {
                stringBuilder.append(string);
            }
        }
    }
}

