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

    private MessagePatternConverter(Configuration config, String[] options) {
        super("Message", "message");
        this.formats = options;
        this.config = config;
        int noLookupsIdx = this.loadNoLookups(options);
        this.noLookups = noLookupsIdx >= 0;
        this.textRenderer = this.loadMessageRenderer(noLookupsIdx >= 0 ? ArrayUtils.remove(options, noLookupsIdx) : options);
    }

    private int loadNoLookups(String[] options) {
        if (options != null) {
            for (int i = 0; i < options.length; ++i) {
                String option = options[i];
                if (!NOLOOKUPS.equalsIgnoreCase(option)) continue;
                return i;
            }
        }
        return -1;
    }

    private TextRenderer loadMessageRenderer(String[] options) {
        if (options != null) {
            for (String option : options) {
                switch (option.toUpperCase(Locale.ROOT)) {
                    case "ANSI": {
                        if (Loader.isJansiAvailable()) {
                            return new JAnsiTextRenderer(options, JAnsiTextRenderer.DefaultMessageStyleMap);
                        }
                        StatusLogger.getLogger().warn("You requested ANSI message rendering but JANSI is not on the classpath.");
                        return null;
                    }
                    case "HTML": {
                        return new HtmlTextRenderer(options);
                    }
                }
            }
        }
        return null;
    }

    public static MessagePatternConverter newInstance(Configuration config, String[] options) {
        return new MessagePatternConverter(config, options);
    }

    @Override
    public void format(LogEvent event, StringBuilder toAppendTo) {
        Message msg = event.getMessage();
        if (msg instanceof StringBuilderFormattable) {
            boolean doRender = this.textRenderer != null;
            StringBuilder workingBuilder = doRender ? new StringBuilder(80) : toAppendTo;
            StringBuilderFormattable stringBuilderFormattable = (StringBuilderFormattable)((Object)msg);
            int offset = workingBuilder.length();
            stringBuilderFormattable.formatTo(workingBuilder);
            if (this.config != null && !this.noLookups) {
                for (int i = offset; i < workingBuilder.length() - 1; ++i) {
                    if (workingBuilder.charAt(i) != '$' || workingBuilder.charAt(i + 1) != '{') continue;
                    String value = workingBuilder.substring(offset, workingBuilder.length());
                    workingBuilder.setLength(offset);
                    workingBuilder.append(this.config.getStrSubstitutor().replace(event, value));
                }
            }
            if (doRender) {
                this.textRenderer.render(workingBuilder, toAppendTo);
            }
            return;
        }
        if (msg != null) {
            String result = msg instanceof MultiformatMessage ? ((MultiformatMessage)msg).getFormattedMessage(this.formats) : msg.getFormattedMessage();
            if (result != null) {
                toAppendTo.append(this.config != null && result.contains("${") ? this.config.getStrSubstitutor().replace(event, result) : result);
            } else {
                toAppendTo.append("null");
            }
        }
    }
}

