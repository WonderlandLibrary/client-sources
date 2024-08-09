/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.appender.SocketAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.layout.AbstractStringLayout;
import org.apache.logging.log4j.core.layout.LoggerFields;
import org.apache.logging.log4j.core.layout.Rfc5424Layout;
import org.apache.logging.log4j.core.layout.SyslogLayout;
import org.apache.logging.log4j.core.net.AbstractSocketManager;
import org.apache.logging.log4j.core.net.Advertiser;
import org.apache.logging.log4j.core.net.Facility;
import org.apache.logging.log4j.core.net.Protocol;
import org.apache.logging.log4j.core.net.ssl.SslConfiguration;
import org.apache.logging.log4j.core.util.Constants;
import org.apache.logging.log4j.util.EnglishEnums;

@Plugin(name="Syslog", category="Core", elementType="appender", printObject=true)
public class SyslogAppender
extends SocketAppender {
    protected static final String RFC5424 = "RFC5424";

    protected SyslogAppender(String string, Layout<? extends Serializable> layout, Filter filter, boolean bl, boolean bl2, AbstractSocketManager abstractSocketManager, Advertiser advertiser) {
        super(string, layout, filter, abstractSocketManager, bl, bl2, advertiser);
    }

    @Deprecated
    public static <B extends Builder<B>> SyslogAppender createAppender(String string, int n, String string2, SslConfiguration sslConfiguration, int n2, int n3, boolean bl, String string3, boolean bl2, boolean bl3, Facility facility, String string4, int n4, boolean bl4, String string5, String string6, String string7, boolean bl5, String string8, String string9, String string10, String string11, String string12, String string13, String string14, Filter filter, Configuration configuration, Charset charset, String string15, LoggerFields[] loggerFieldsArray, boolean bl6) {
        return ((Builder)((Builder)((Builder)((Builder)((Builder)((Builder)((Builder)((Builder)((Builder)((Builder)((Builder)((Builder)((Builder)((Builder)((Builder)((Builder)((Builder)((Builder)((Builder)((Builder)((Builder)((Builder)((Builder)((Builder)((Builder)((Builder)((Builder)((Builder)((Builder)((Builder)((SocketAppender.AbstractBuilder)SyslogAppender.newSyslogAppenderBuilder()).withHost(string)).withPort(n)).withProtocol(EnglishEnums.valueOf(Protocol.class, string2))).withSslConfiguration(sslConfiguration)).withConnectTimeoutMillis(n2)).withReconnectDelayMillis(n3)).withImmediateFail(bl)).withName(string9)).withImmediateFlush(bl2)).withIgnoreExceptions(bl3)).withFilter(filter)).setConfiguration(configuration)).withAdvertise(bl6)).setFacility(facility)).setId(string4)).setEnterpriseNumber(n4)).setIncludeMdc(bl4)).setMdcId(string5)).setMdcPrefix(string6)).setEventPrefix(string7)).setNewLine(bl5)).setAppName(string9)).setMsgId(string10)).setExcludes(string11)).setIncludeMdc(bl4)).setRequired(string13)).setFormat(string14)).setCharsetName(charset)).setExceptionPattern(string15)).setLoggerFields(loggerFieldsArray)).build();
    }

    @PluginBuilderFactory
    public static <B extends Builder<B>> B newSyslogAppenderBuilder() {
        return (B)((Builder)new Builder().asBuilder());
    }

    static Logger access$000() {
        return LOGGER;
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Builder<B extends Builder<B>>
    extends SocketAppender.AbstractBuilder<B>
    implements org.apache.logging.log4j.core.util.Builder<SocketAppender> {
        @PluginBuilderAttribute(value="facility")
        private Facility facility = Facility.LOCAL0;
        @PluginBuilderAttribute(value="id")
        private String id;
        @PluginBuilderAttribute(value="enterpriseNumber")
        private int enterpriseNumber = 18060;
        @PluginBuilderAttribute(value="includeMdc")
        private boolean includeMdc = true;
        @PluginBuilderAttribute(value="mdcId")
        private String mdcId;
        @PluginBuilderAttribute(value="mdcPrefix")
        private String mdcPrefix;
        @PluginBuilderAttribute(value="eventPrefix")
        private String eventPrefix;
        @PluginBuilderAttribute(value="newLine")
        private boolean newLine;
        @PluginBuilderAttribute(value="newLineEscape")
        private String escapeNL;
        @PluginBuilderAttribute(value="appName")
        private String appName;
        @PluginBuilderAttribute(value="messageId")
        private String msgId;
        @PluginBuilderAttribute(value="mdcExcludes")
        private String excludes;
        @PluginBuilderAttribute(value="mdcIncludes")
        private String includes;
        @PluginBuilderAttribute(value="mdcRequired")
        private String required;
        @PluginBuilderAttribute(value="format")
        private String format;
        @PluginBuilderAttribute(value="charset")
        private Charset charsetName = StandardCharsets.UTF_8;
        @PluginBuilderAttribute(value="exceptionPattern")
        private String exceptionPattern;
        @PluginElement(value="LoggerFields")
        private LoggerFields[] loggerFields;

        @Override
        public SyslogAppender build() {
            String string;
            Protocol protocol = this.getProtocol();
            SslConfiguration sslConfiguration = this.getSslConfiguration();
            boolean bl = sslConfiguration != null || protocol == Protocol.SSL;
            Configuration configuration = this.getConfiguration();
            SyslogLayout syslogLayout = this.getLayout();
            if (syslogLayout == null) {
                AbstractStringLayout abstractStringLayout = syslogLayout = SyslogAppender.RFC5424.equalsIgnoreCase(this.format) ? Rfc5424Layout.createLayout(this.facility, this.id, this.enterpriseNumber, this.includeMdc, this.mdcId, this.mdcPrefix, this.eventPrefix, this.newLine, this.escapeNL, this.appName, this.msgId, this.excludes, this.includes, this.required, this.exceptionPattern, bl, this.loggerFields, configuration) : ((SyslogLayout.Builder)((AbstractStringLayout.Builder)((SyslogLayout.Builder)((SyslogLayout.Builder)((SyslogLayout.Builder)SyslogLayout.newBuilder()).setFacility(this.facility)).setIncludeNewLine(this.newLine)).setEscapeNL(this.escapeNL)).setCharset(this.charsetName)).build();
            }
            if ((string = this.getName()) == null) {
                SyslogAppender.access$000().error("No name provided for SyslogAppender");
                return null;
            }
            AbstractSocketManager abstractSocketManager = SocketAppender.createSocketManager(string, protocol, this.getHost(), this.getPort(), this.getConnectTimeoutMillis(), sslConfiguration, this.getReconnectDelayMillis(), this.getImmediateFail(), syslogLayout, Constants.ENCODER_BYTE_BUFFER_SIZE, null);
            return new SyslogAppender(string, (Layout<? extends Serializable>)syslogLayout, this.getFilter(), this.isIgnoreExceptions(), this.isImmediateFlush(), abstractSocketManager, this.getAdvertise() ? configuration.getAdvertiser() : null);
        }

        public Facility getFacility() {
            return this.facility;
        }

        public String getId() {
            return this.id;
        }

        public int getEnterpriseNumber() {
            return this.enterpriseNumber;
        }

        public boolean isIncludeMdc() {
            return this.includeMdc;
        }

        public String getMdcId() {
            return this.mdcId;
        }

        public String getMdcPrefix() {
            return this.mdcPrefix;
        }

        public String getEventPrefix() {
            return this.eventPrefix;
        }

        public boolean isNewLine() {
            return this.newLine;
        }

        public String getEscapeNL() {
            return this.escapeNL;
        }

        public String getAppName() {
            return this.appName;
        }

        public String getMsgId() {
            return this.msgId;
        }

        public String getExcludes() {
            return this.excludes;
        }

        public String getIncludes() {
            return this.includes;
        }

        public String getRequired() {
            return this.required;
        }

        public String getFormat() {
            return this.format;
        }

        public Charset getCharsetName() {
            return this.charsetName;
        }

        public String getExceptionPattern() {
            return this.exceptionPattern;
        }

        public LoggerFields[] getLoggerFields() {
            return this.loggerFields;
        }

        public B setFacility(Facility facility) {
            this.facility = facility;
            return (B)((Builder)this.asBuilder());
        }

        public B setId(String string) {
            this.id = string;
            return (B)((Builder)this.asBuilder());
        }

        public B setEnterpriseNumber(int n) {
            this.enterpriseNumber = n;
            return (B)((Builder)this.asBuilder());
        }

        public B setIncludeMdc(boolean bl) {
            this.includeMdc = bl;
            return (B)((Builder)this.asBuilder());
        }

        public B setMdcId(String string) {
            this.mdcId = string;
            return (B)((Builder)this.asBuilder());
        }

        public B setMdcPrefix(String string) {
            this.mdcPrefix = string;
            return (B)((Builder)this.asBuilder());
        }

        public B setEventPrefix(String string) {
            this.eventPrefix = string;
            return (B)((Builder)this.asBuilder());
        }

        public B setNewLine(boolean bl) {
            this.newLine = bl;
            return (B)((Builder)this.asBuilder());
        }

        public B setEscapeNL(String string) {
            this.escapeNL = string;
            return (B)((Builder)this.asBuilder());
        }

        public B setAppName(String string) {
            this.appName = string;
            return (B)((Builder)this.asBuilder());
        }

        public B setMsgId(String string) {
            this.msgId = string;
            return (B)((Builder)this.asBuilder());
        }

        public B setExcludes(String string) {
            this.excludes = string;
            return (B)((Builder)this.asBuilder());
        }

        public B setIncludes(String string) {
            this.includes = string;
            return (B)((Builder)this.asBuilder());
        }

        public B setRequired(String string) {
            this.required = string;
            return (B)((Builder)this.asBuilder());
        }

        public B setFormat(String string) {
            this.format = string;
            return (B)((Builder)this.asBuilder());
        }

        public B setCharsetName(Charset charset) {
            this.charsetName = charset;
            return (B)((Builder)this.asBuilder());
        }

        public B setExceptionPattern(String string) {
            this.exceptionPattern = string;
            return (B)((Builder)this.asBuilder());
        }

        public B setLoggerFields(LoggerFields[] loggerFieldsArray) {
            this.loggerFields = loggerFieldsArray;
            return (B)((Builder)this.asBuilder());
        }

        @Override
        public Object build() {
            return this.build();
        }
    }
}

