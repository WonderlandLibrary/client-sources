/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.apache.commons.csv.CSVFormat
 *  org.apache.commons.csv.QuoteMode
 */
package org.apache.logging.log4j.core.layout;

import java.nio.charset.Charset;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.QuoteMode;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.layout.AbstractStringLayout;
import org.apache.logging.log4j.core.layout.PatternLayout;

public abstract class AbstractCsvLayout
extends AbstractStringLayout {
    protected static final String DEFAULT_CHARSET = "UTF-8";
    protected static final String DEFAULT_FORMAT = "Default";
    private static final String CONTENT_TYPE = "text/csv";
    private final CSVFormat format;

    protected static CSVFormat createFormat(String string, Character c, Character c2, Character c3, QuoteMode quoteMode, String string2, String string3) {
        CSVFormat cSVFormat = CSVFormat.valueOf((String)string);
        if (AbstractCsvLayout.isNotNul(c)) {
            cSVFormat = cSVFormat.withDelimiter(c.charValue());
        }
        if (AbstractCsvLayout.isNotNul(c2)) {
            cSVFormat = cSVFormat.withEscape(c2);
        }
        if (AbstractCsvLayout.isNotNul(c3)) {
            cSVFormat = cSVFormat.withQuote(c3);
        }
        if (quoteMode != null) {
            cSVFormat = cSVFormat.withQuoteMode(quoteMode);
        }
        if (string2 != null) {
            cSVFormat = cSVFormat.withNullString(string2);
        }
        if (string3 != null) {
            cSVFormat = cSVFormat.withRecordSeparator(string3);
        }
        return cSVFormat;
    }

    private static boolean isNotNul(Character c) {
        return c != null && c.charValue() != '\u0000';
    }

    protected AbstractCsvLayout(Configuration configuration, Charset charset, CSVFormat cSVFormat, String string, String string2) {
        super(configuration, charset, PatternLayout.newSerializerBuilder().setConfiguration(configuration).setPattern(string).build(), PatternLayout.newSerializerBuilder().setConfiguration(configuration).setPattern(string2).build());
        this.format = cSVFormat;
    }

    @Override
    public String getContentType() {
        return "text/csv; charset=" + this.getCharset();
    }

    public CSVFormat getFormat() {
        return this.format;
    }
}

