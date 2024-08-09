/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.layout;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.layout.AbstractStringLayout;
import org.apache.logging.log4j.core.net.Facility;
import org.apache.logging.log4j.core.net.Priority;
import org.apache.logging.log4j.core.util.NetUtils;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@Plugin(name="SyslogLayout", category="Core", elementType="layout", printObject=true)
public final class SyslogLayout
extends AbstractStringLayout {
    public static final Pattern NEWLINE_PATTERN = Pattern.compile("\\r?\\n");
    private final Facility facility;
    private final boolean includeNewLine;
    private final String escapeNewLine;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd HH:mm:ss", Locale.ENGLISH);
    private final String localHostname = NetUtils.getLocalHostname();

    @PluginBuilderFactory
    public static <B extends Builder<B>> B newBuilder() {
        return (B)((Builder)new Builder().asBuilder());
    }

    protected SyslogLayout(Facility facility, boolean bl, String string, Charset charset) {
        super(charset);
        this.facility = facility;
        this.includeNewLine = bl;
        this.escapeNewLine = string == null ? null : Matcher.quoteReplacement(string);
    }

    @Override
    public String toSerializable(LogEvent logEvent) {
        StringBuilder stringBuilder = SyslogLayout.getStringBuilder();
        stringBuilder.append('<');
        stringBuilder.append(Priority.getPriority(this.facility, logEvent.getLevel()));
        stringBuilder.append('>');
        this.addDate(logEvent.getTimeMillis(), stringBuilder);
        stringBuilder.append(' ');
        stringBuilder.append(this.localHostname);
        stringBuilder.append(' ');
        String string = logEvent.getMessage().getFormattedMessage();
        if (null != this.escapeNewLine) {
            string = NEWLINE_PATTERN.matcher(string).replaceAll(this.escapeNewLine);
        }
        stringBuilder.append(string);
        if (this.includeNewLine) {
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }

    private synchronized void addDate(long l, StringBuilder stringBuilder) {
        int n = stringBuilder.length() + 4;
        stringBuilder.append(this.dateFormat.format(new Date(l)));
        if (stringBuilder.charAt(n) == '0') {
            stringBuilder.setCharAt(n, ' ');
        }
    }

    @Override
    public Map<String, String> getContentFormat() {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("structured", "false");
        hashMap.put("formatType", "logfilepatternreceiver");
        hashMap.put("dateFormat", this.dateFormat.toPattern());
        hashMap.put("format", "<LEVEL>TIMESTAMP PROP(HOSTNAME) MESSAGE");
        return hashMap;
    }

    @Deprecated
    public static SyslogLayout createLayout(Facility facility, boolean bl, String string, Charset charset) {
        return new SyslogLayout(facility, bl, string, charset);
    }

    public Facility getFacility() {
        return this.facility;
    }

    @Override
    public Serializable toSerializable(LogEvent logEvent) {
        return this.toSerializable(logEvent);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Builder<B extends Builder<B>>
    extends AbstractStringLayout.Builder<B>
    implements org.apache.logging.log4j.core.util.Builder<SyslogLayout> {
        @PluginBuilderAttribute
        private Facility facility = Facility.LOCAL0;
        @PluginBuilderAttribute(value="newLine")
        private boolean includeNewLine;
        @PluginBuilderAttribute(value="newLineEscape")
        private String escapeNL;

        public Builder() {
            this.setCharset(StandardCharsets.UTF_8);
        }

        @Override
        public SyslogLayout build() {
            return new SyslogLayout(this.facility, this.includeNewLine, this.escapeNL, this.getCharset());
        }

        public Facility getFacility() {
            return this.facility;
        }

        public boolean isIncludeNewLine() {
            return this.includeNewLine;
        }

        public String getEscapeNL() {
            return this.escapeNL;
        }

        public B setFacility(Facility facility) {
            this.facility = facility;
            return (B)((Builder)this.asBuilder());
        }

        public B setIncludeNewLine(boolean bl) {
            this.includeNewLine = bl;
            return (B)((Builder)this.asBuilder());
        }

        public B setEscapeNL(String string) {
            this.escapeNL = string;
            return (B)((Builder)this.asBuilder());
        }

        @Override
        public Object build() {
            return this.build();
        }
    }
}

