/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.apache.commons.csv.CSVFormat
 *  org.apache.commons.csv.QuoteMode
 */
package org.apache.logging.log4j.core.layout;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.QuoteMode;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.AbstractCsvLayout;
import org.apache.logging.log4j.status.StatusLogger;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@Plugin(name="CsvLogEventLayout", category="Core", elementType="layout", printObject=true)
public class CsvLogEventLayout
extends AbstractCsvLayout {
    public static CsvLogEventLayout createDefaultLayout() {
        return new CsvLogEventLayout(null, Charset.forName("UTF-8"), CSVFormat.valueOf((String)"Default"), null, null);
    }

    public static CsvLogEventLayout createLayout(CSVFormat cSVFormat) {
        return new CsvLogEventLayout(null, Charset.forName("UTF-8"), cSVFormat, null, null);
    }

    @PluginFactory
    public static CsvLogEventLayout createLayout(@PluginConfiguration Configuration configuration, @PluginAttribute(value="format", defaultString="Default") String string, @PluginAttribute(value="delimiter") Character c, @PluginAttribute(value="escape") Character c2, @PluginAttribute(value="quote") Character c3, @PluginAttribute(value="quoteMode") QuoteMode quoteMode, @PluginAttribute(value="nullString") String string2, @PluginAttribute(value="recordSeparator") String string3, @PluginAttribute(value="charset", defaultString="UTF-8") Charset charset, @PluginAttribute(value="header") String string4, @PluginAttribute(value="footer") String string5) {
        CSVFormat cSVFormat = CsvLogEventLayout.createFormat(string, c, c2, c3, quoteMode, string2, string3);
        return new CsvLogEventLayout(configuration, charset, cSVFormat, string4, string5);
    }

    protected CsvLogEventLayout(Configuration configuration, Charset charset, CSVFormat cSVFormat, String string, String string2) {
        super(configuration, charset, cSVFormat, string, string2);
    }

    @Override
    public String toSerializable(LogEvent logEvent) {
        StringBuilder stringBuilder = CsvLogEventLayout.getStringBuilder();
        CSVFormat cSVFormat = this.getFormat();
        try {
            cSVFormat.print((Object)logEvent.getNanoTime(), (Appendable)stringBuilder, false);
            cSVFormat.print((Object)logEvent.getTimeMillis(), (Appendable)stringBuilder, true);
            cSVFormat.print((Object)logEvent.getLevel(), (Appendable)stringBuilder, true);
            cSVFormat.print((Object)logEvent.getThreadId(), (Appendable)stringBuilder, true);
            cSVFormat.print((Object)logEvent.getThreadName(), (Appendable)stringBuilder, true);
            cSVFormat.print((Object)logEvent.getThreadPriority(), (Appendable)stringBuilder, true);
            cSVFormat.print((Object)logEvent.getMessage().getFormattedMessage(), (Appendable)stringBuilder, true);
            cSVFormat.print((Object)logEvent.getLoggerFqcn(), (Appendable)stringBuilder, true);
            cSVFormat.print((Object)logEvent.getLoggerName(), (Appendable)stringBuilder, true);
            cSVFormat.print((Object)logEvent.getMarker(), (Appendable)stringBuilder, true);
            cSVFormat.print((Object)logEvent.getThrownProxy(), (Appendable)stringBuilder, true);
            cSVFormat.print((Object)logEvent.getSource(), (Appendable)stringBuilder, true);
            cSVFormat.print((Object)logEvent.getContextData(), (Appendable)stringBuilder, true);
            cSVFormat.print((Object)logEvent.getContextStack(), (Appendable)stringBuilder, true);
            cSVFormat.println((Appendable)stringBuilder);
            return stringBuilder.toString();
        } catch (IOException iOException) {
            StatusLogger.getLogger().error(logEvent.toString(), (Throwable)iOException);
            return cSVFormat.getCommentMarker() + " " + iOException;
        }
    }

    @Override
    public Serializable toSerializable(LogEvent logEvent) {
        return this.toSerializable(logEvent);
    }
}

