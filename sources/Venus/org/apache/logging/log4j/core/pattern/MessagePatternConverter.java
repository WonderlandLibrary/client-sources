/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.pattern;

import java.util.Locale;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.HtmlTextRenderer;
import org.apache.logging.log4j.core.pattern.JAnsiTextRenderer;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.core.pattern.TextRenderer;
import org.apache.logging.log4j.core.util.ArrayUtils;
import org.apache.logging.log4j.core.util.Loader;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.MultiformatMessage;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.PerformanceSensitive;
import org.apache.logging.log4j.util.StringBuilderFormattable;

@Plugin(name="MessagePatternConverter", category="Converter")
@ConverterKeys(value={"m", "msg", "message"})
@PerformanceSensitive(value={"allocation"})
public final class MessagePatternConverter
extends LogEventPatternConverter {
    private static final String NOLOOKUPS = "nolookups";
    private final String[] formats;
    private final Configuration config;
    private final TextRenderer textRenderer;
    private final boolean noLookups;

    private MessagePatternConverter(Configuration configuration, String[] stringArray) {
        super("Message", "message");
        this.formats = stringArray;
        this.config = configuration;
        int n = this.loadNoLookups(stringArray);
        this.noLookups = n >= 0;
        this.textRenderer = this.loadMessageRenderer(n >= 0 ? ArrayUtils.remove(stringArray, n) : stringArray);
    }

    private int loadNoLookups(String[] stringArray) {
        if (stringArray != null) {
            for (int i = 0; i < stringArray.length; ++i) {
                String string = stringArray[i];
                if (!NOLOOKUPS.equalsIgnoreCase(string)) continue;
                return i;
            }
        }
        return 1;
    }

    private TextRenderer loadMessageRenderer(String[] stringArray) {
        if (stringArray != null) {
            for (String string : stringArray) {
                switch (string.toUpperCase(Locale.ROOT)) {
                    case "ANSI": {
                        if (Loader.isJansiAvailable()) {
                            return new JAnsiTextRenderer(stringArray, JAnsiTextRenderer.DefaultMessageStyleMap);
                        }
                        StatusLogger.getLogger().warn("You requested ANSI message rendering but JANSI is not on the classpath.");
                        return null;
                    }
                    case "HTML": {
                        return new HtmlTextRenderer(stringArray);
                    }
                }
            }
        }
        return null;
    }

    public static MessagePatternConverter newInstance(Configuration configuration, String[] stringArray) {
        return new MessagePatternConverter(configuration, stringArray);
    }

    @Override
    public void format(LogEvent logEvent, StringBuilder stringBuilder) {
        Message message = logEvent.getMessage();
        if (message instanceof StringBuilderFormattable) {
            boolean bl = this.textRenderer != null;
            StringBuilder stringBuilder2 = bl ? new StringBuilder(80) : stringBuilder;
            StringBuilderFormattable stringBuilderFormattable = (StringBuilderFormattable)((Object)message);
            int n = stringBuilder2.length();
            stringBuilderFormattable.formatTo(stringBuilder2);
            if (this.config != null && !this.noLookups) {
                for (int i = n; i < stringBuilder2.length() - 1; ++i) {
                    if (stringBuilder2.charAt(i) != '$' || stringBuilder2.charAt(i + 1) != '{') continue;
                    String string = stringBuilder2.substring(n, stringBuilder2.length());
                    stringBuilder2.setLength(n);
                    stringBuilder2.append(this.config.getStrSubstitutor().replace(logEvent, string));
                }
            }
            if (bl) {
                this.textRenderer.render(stringBuilder2, stringBuilder);
            }
            return;
        }
        if (message != null) {
            String string = message instanceof MultiformatMessage ? ((MultiformatMessage)message).getFormattedMessage(this.formats) : message.getFormattedMessage();
            if (string != null) {
                stringBuilder.append(this.config != null && string.contains("${") ? this.config.getStrSubstitutor().replace(logEvent, string) : string);
            } else {
                stringBuilder.append("null");
            }
        }
    }
}

