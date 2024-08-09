/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.layout;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LoggingException;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.TlsSyslogFrame;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.AbstractStringLayout;
import org.apache.logging.log4j.core.layout.LoggerFields;
import org.apache.logging.log4j.core.net.Facility;
import org.apache.logging.log4j.core.net.Priority;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.core.pattern.PatternConverter;
import org.apache.logging.log4j.core.pattern.PatternFormatter;
import org.apache.logging.log4j.core.pattern.PatternParser;
import org.apache.logging.log4j.core.pattern.ThrowablePatternConverter;
import org.apache.logging.log4j.core.util.NetUtils;
import org.apache.logging.log4j.core.util.Patterns;
import org.apache.logging.log4j.message.MapMessage;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.StructuredDataId;
import org.apache.logging.log4j.message.StructuredDataMessage;
import org.apache.logging.log4j.util.StringBuilders;
import org.apache.logging.log4j.util.Strings;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@Plugin(name="Rfc5424Layout", category="Core", elementType="layout", printObject=true)
public final class Rfc5424Layout
extends AbstractStringLayout {
    public static final int DEFAULT_ENTERPRISE_NUMBER = 18060;
    public static final String DEFAULT_ID = "Audit";
    public static final Pattern NEWLINE_PATTERN = Pattern.compile("\\r?\\n");
    public static final Pattern PARAM_VALUE_ESCAPE_PATTERN = Pattern.compile("[\\\"\\]\\\\]");
    public static final String DEFAULT_MDCID = "mdc";
    private static final String LF = "\n";
    private static final int TWO_DIGITS = 10;
    private static final int THREE_DIGITS = 100;
    private static final int MILLIS_PER_MINUTE = 60000;
    private static final int MINUTES_PER_HOUR = 60;
    private static final String COMPONENT_KEY = "RFC5424-Converter";
    private final Facility facility;
    private final String defaultId;
    private final int enterpriseNumber;
    private final boolean includeMdc;
    private final String mdcId;
    private final StructuredDataId mdcSdId;
    private final String localHostName;
    private final String appName;
    private final String messageId;
    private final String configName;
    private final String mdcPrefix;
    private final String eventPrefix;
    private final List<String> mdcExcludes;
    private final List<String> mdcIncludes;
    private final List<String> mdcRequired;
    private final ListChecker listChecker;
    private final ListChecker noopChecker = new NoopChecker(this, null);
    private final boolean includeNewLine;
    private final String escapeNewLine;
    private final boolean useTlsMessageFormat;
    private long lastTimestamp = -1L;
    private String timestamppStr;
    private final List<PatternFormatter> exceptionFormatters;
    private final Map<String, FieldFormatter> fieldFormatters;
    private final String procId;

    private Rfc5424Layout(Configuration configuration, Facility facility, String string, int n, boolean bl, boolean bl2, String string2, String string3, String string4, String string5, String string6, String string7, String string8, String string9, String string10, Charset charset, String string11, boolean bl3, LoggerFields[] loggerFieldsArray) {
        super(charset);
        Object object;
        PatternParser patternParser = Rfc5424Layout.createPatternParser(configuration, ThrowablePatternConverter.class);
        this.exceptionFormatters = string11 == null ? null : patternParser.parse(string11);
        this.facility = facility;
        this.defaultId = string == null ? DEFAULT_ID : string;
        this.enterpriseNumber = n;
        this.includeMdc = bl;
        this.includeNewLine = bl2;
        this.escapeNewLine = string2 == null ? null : Matcher.quoteReplacement(string2);
        this.mdcId = string3;
        this.mdcSdId = new StructuredDataId(string3, this.enterpriseNumber, null, null);
        this.mdcPrefix = string4;
        this.eventPrefix = string5;
        this.appName = string6;
        this.messageId = string7;
        this.useTlsMessageFormat = bl3;
        this.localHostName = NetUtils.getLocalHostname();
        ListChecker listChecker = null;
        if (string8 != null) {
            object = string8.split(Patterns.COMMA_SEPARATOR);
            if (((String[])object).length > 0) {
                listChecker = new ExcludeChecker(this, null);
                this.mdcExcludes = new ArrayList<String>(((String[])object).length);
                for (String string12 : object) {
                    this.mdcExcludes.add(string12.trim());
                }
            } else {
                this.mdcExcludes = null;
            }
        } else {
            this.mdcExcludes = null;
        }
        if (string9 != null) {
            object = string9.split(Patterns.COMMA_SEPARATOR);
            if (((String[])object).length > 0) {
                listChecker = new IncludeChecker(this, null);
                this.mdcIncludes = new ArrayList<String>(((String[])object).length);
                for (String string12 : object) {
                    this.mdcIncludes.add(string12.trim());
                }
            } else {
                this.mdcIncludes = null;
            }
        } else {
            this.mdcIncludes = null;
        }
        if (string10 != null) {
            object = string10.split(Patterns.COMMA_SEPARATOR);
            if (((String[])object).length > 0) {
                this.mdcRequired = new ArrayList<String>(((String[])object).length);
                for (String string12 : object) {
                    this.mdcRequired.add(string12.trim());
                }
            } else {
                this.mdcRequired = null;
            }
        } else {
            this.mdcRequired = null;
        }
        this.listChecker = listChecker != null ? listChecker : this.noopChecker;
        object = configuration == null ? null : configuration.getName();
        this.configName = Strings.isNotEmpty((CharSequence)object) ? object : null;
        this.fieldFormatters = this.createFieldFormatters(loggerFieldsArray, configuration);
        this.procId = "-";
    }

    private Map<String, FieldFormatter> createFieldFormatters(LoggerFields[] loggerFieldsArray, Configuration configuration) {
        HashMap<String, FieldFormatter> hashMap = new HashMap<String, FieldFormatter>(loggerFieldsArray == null ? 0 : loggerFieldsArray.length);
        if (loggerFieldsArray != null) {
            for (LoggerFields loggerFields : loggerFieldsArray) {
                StructuredDataId structuredDataId = loggerFields.getSdId() == null ? this.mdcSdId : loggerFields.getSdId();
                HashMap<String, List<PatternFormatter>> hashMap2 = new HashMap<String, List<PatternFormatter>>();
                Map<String, String> map = loggerFields.getMap();
                if (map.isEmpty()) continue;
                PatternParser patternParser = Rfc5424Layout.createPatternParser(configuration, null);
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    List<PatternFormatter> list = patternParser.parse(entry.getValue());
                    hashMap2.put(entry.getKey(), list);
                }
                FieldFormatter fieldFormatter = new FieldFormatter(this, hashMap2, loggerFields.getDiscardIfAllFieldsAreEmpty());
                hashMap.put(structuredDataId.toString(), fieldFormatter);
            }
        }
        return hashMap.size() > 0 ? hashMap : null;
    }

    private static PatternParser createPatternParser(Configuration configuration, Class<? extends PatternConverter> clazz) {
        if (configuration == null) {
            return new PatternParser(configuration, "Converter", LogEventPatternConverter.class, clazz);
        }
        PatternParser patternParser = (PatternParser)configuration.getComponent(COMPONENT_KEY);
        if (patternParser == null) {
            patternParser = new PatternParser(configuration, "Converter", ThrowablePatternConverter.class);
            configuration.addComponent(COMPONENT_KEY, patternParser);
            patternParser = (PatternParser)configuration.getComponent(COMPONENT_KEY);
        }
        return patternParser;
    }

    @Override
    public Map<String, String> getContentFormat() {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("structured", "true");
        hashMap.put("formatType", "RFC5424");
        return hashMap;
    }

    @Override
    public String toSerializable(LogEvent logEvent) {
        StringBuilder stringBuilder = Rfc5424Layout.getStringBuilder();
        this.appendPriority(stringBuilder, logEvent.getLevel());
        this.appendTimestamp(stringBuilder, logEvent.getTimeMillis());
        this.appendSpace(stringBuilder);
        this.appendHostName(stringBuilder);
        this.appendSpace(stringBuilder);
        this.appendAppName(stringBuilder);
        this.appendSpace(stringBuilder);
        this.appendProcessId(stringBuilder);
        this.appendSpace(stringBuilder);
        this.appendMessageId(stringBuilder, logEvent.getMessage());
        this.appendSpace(stringBuilder);
        this.appendStructuredElements(stringBuilder, logEvent);
        this.appendMessage(stringBuilder, logEvent);
        if (this.useTlsMessageFormat) {
            return new TlsSyslogFrame(stringBuilder.toString()).toString();
        }
        return stringBuilder.toString();
    }

    private void appendPriority(StringBuilder stringBuilder, Level level) {
        stringBuilder.append('<');
        stringBuilder.append(Priority.getPriority(this.facility, level));
        stringBuilder.append(">1 ");
    }

    private void appendTimestamp(StringBuilder stringBuilder, long l) {
        stringBuilder.append(this.computeTimeStampString(l));
    }

    private void appendSpace(StringBuilder stringBuilder) {
        stringBuilder.append(' ');
    }

    private void appendHostName(StringBuilder stringBuilder) {
        stringBuilder.append(this.localHostName);
    }

    private void appendAppName(StringBuilder stringBuilder) {
        if (this.appName != null) {
            stringBuilder.append(this.appName);
        } else if (this.configName != null) {
            stringBuilder.append(this.configName);
        } else {
            stringBuilder.append('-');
        }
    }

    private void appendProcessId(StringBuilder stringBuilder) {
        stringBuilder.append(this.getProcId());
    }

    private void appendMessageId(StringBuilder stringBuilder, Message message) {
        String string;
        boolean bl = message instanceof StructuredDataMessage;
        String string2 = string = bl ? ((StructuredDataMessage)message).getType() : null;
        if (string != null) {
            stringBuilder.append(string);
        } else if (this.messageId != null) {
            stringBuilder.append(this.messageId);
        } else {
            stringBuilder.append('-');
        }
    }

    private void appendMessage(StringBuilder stringBuilder, LogEvent logEvent) {
        String string;
        Message message = logEvent.getMessage();
        String string2 = string = message instanceof StructuredDataMessage ? message.getFormat() : message.getFormattedMessage();
        if (string != null && string.length() > 0) {
            stringBuilder.append(' ').append(this.escapeNewlines(string, this.escapeNewLine));
        }
        if (this.exceptionFormatters != null && logEvent.getThrown() != null) {
            StringBuilder stringBuilder2 = new StringBuilder(LF);
            for (PatternFormatter patternFormatter : this.exceptionFormatters) {
                patternFormatter.format(logEvent, stringBuilder2);
            }
            stringBuilder.append(this.escapeNewlines(stringBuilder2.toString(), this.escapeNewLine));
        }
        if (this.includeNewLine) {
            stringBuilder.append(LF);
        }
    }

    private void appendStructuredElements(StringBuilder stringBuilder, LogEvent logEvent) {
        Object object;
        Object object2;
        Object object3;
        Message message = logEvent.getMessage();
        boolean bl = message instanceof StructuredDataMessage;
        if (!bl && this.fieldFormatters != null && this.fieldFormatters.isEmpty() && !this.includeMdc) {
            stringBuilder.append('-');
            return;
        }
        HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
        Map<String, String> map = logEvent.getContextData().toMap();
        if (this.mdcRequired != null) {
            this.checkRequired(map);
        }
        if (this.fieldFormatters != null) {
            for (Map.Entry object42 : this.fieldFormatters.entrySet()) {
                object3 = (String)object42.getKey();
                object2 = ((FieldFormatter)object42.getValue()).format(logEvent);
                hashMap.put(object3, object2);
            }
        }
        if (this.includeMdc && map.size() > 0) {
            object = this.mdcSdId.toString();
            StructuredDataElement structuredDataElement = (StructuredDataElement)hashMap.get(object);
            if (structuredDataElement != null) {
                structuredDataElement.union(map);
                hashMap.put(object, structuredDataElement);
            } else {
                object3 = new StructuredDataElement(this, map, false);
                hashMap.put(object, object3);
            }
        }
        if (bl) {
            StructuredDataElement structuredDataElement;
            object = (StructuredDataMessage)message;
            Map<String, String> map2 = ((MapMessage)object).getData();
            object3 = ((StructuredDataMessage)object).getId();
            object2 = this.getId((StructuredDataId)object3);
            if (hashMap.containsKey(object2)) {
                structuredDataElement = (StructuredDataElement)hashMap.get(((StructuredDataId)object3).toString());
                structuredDataElement.union(map2);
                hashMap.put(object2, structuredDataElement);
            } else {
                structuredDataElement = new StructuredDataElement(this, map2, false);
                hashMap.put(object2, structuredDataElement);
            }
        }
        if (hashMap.isEmpty()) {
            stringBuilder.append('-');
            return;
        }
        for (Map.Entry entry : hashMap.entrySet()) {
            this.formatStructuredElement((String)entry.getKey(), this.mdcPrefix, (StructuredDataElement)entry.getValue(), stringBuilder, this.listChecker);
        }
    }

    private String escapeNewlines(String string, String string2) {
        if (null == string2) {
            return string;
        }
        return NEWLINE_PATTERN.matcher(string).replaceAll(string2);
    }

    protected String getProcId() {
        return this.procId;
    }

    protected List<String> getMdcExcludes() {
        return this.mdcExcludes;
    }

    protected List<String> getMdcIncludes() {
        return this.mdcIncludes;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private String computeTimeStampString(long l) {
        long l2;
        Object object = this;
        synchronized (object) {
            l2 = this.lastTimestamp;
            if (l == this.lastTimestamp) {
                return this.timestamppStr;
            }
        }
        object = new StringBuilder();
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTimeInMillis(l);
        ((StringBuilder)object).append(Integer.toString(gregorianCalendar.get(1)));
        ((StringBuilder)object).append('-');
        this.pad(gregorianCalendar.get(2) + 1, 10, (StringBuilder)object);
        ((StringBuilder)object).append('-');
        this.pad(gregorianCalendar.get(5), 10, (StringBuilder)object);
        ((StringBuilder)object).append('T');
        this.pad(gregorianCalendar.get(11), 10, (StringBuilder)object);
        ((StringBuilder)object).append(':');
        this.pad(gregorianCalendar.get(12), 10, (StringBuilder)object);
        ((StringBuilder)object).append(':');
        this.pad(gregorianCalendar.get(13), 10, (StringBuilder)object);
        ((StringBuilder)object).append('.');
        this.pad(gregorianCalendar.get(14), 100, (StringBuilder)object);
        int n = (gregorianCalendar.get(15) + gregorianCalendar.get(16)) / 60000;
        if (n == 0) {
            ((StringBuilder)object).append('Z');
        } else {
            if (n < 0) {
                n = -n;
                ((StringBuilder)object).append('-');
            } else {
                ((StringBuilder)object).append('+');
            }
            int n2 = n / 60;
            this.pad(n2, 10, (StringBuilder)object);
            ((StringBuilder)object).append(':');
            this.pad(n -= n2 * 60, 10, (StringBuilder)object);
        }
        Rfc5424Layout rfc5424Layout = this;
        synchronized (rfc5424Layout) {
            if (l2 == this.lastTimestamp) {
                this.lastTimestamp = l;
                this.timestamppStr = ((StringBuilder)object).toString();
            }
        }
        return ((StringBuilder)object).toString();
    }

    private void pad(int n, int n2, StringBuilder stringBuilder) {
        while (n2 > 1) {
            if (n < n2) {
                stringBuilder.append('0');
            }
            n2 /= 10;
        }
        stringBuilder.append(Integer.toString(n));
    }

    private void formatStructuredElement(String string, String string2, StructuredDataElement structuredDataElement, StringBuilder stringBuilder, ListChecker listChecker) {
        if (string == null && this.defaultId == null || structuredDataElement.discard()) {
            return;
        }
        stringBuilder.append('[');
        stringBuilder.append(string);
        if (!this.mdcSdId.toString().equals(string)) {
            this.appendMap(string2, structuredDataElement.getFields(), stringBuilder, this.noopChecker);
        } else {
            this.appendMap(string2, structuredDataElement.getFields(), stringBuilder, listChecker);
        }
        stringBuilder.append(']');
    }

    private String getId(StructuredDataId structuredDataId) {
        int n;
        StringBuilder stringBuilder = new StringBuilder();
        if (structuredDataId == null || structuredDataId.getName() == null) {
            stringBuilder.append(this.defaultId);
        } else {
            stringBuilder.append(structuredDataId.getName());
        }
        int n2 = n = structuredDataId != null ? structuredDataId.getEnterpriseNumber() : this.enterpriseNumber;
        if (n < 0) {
            n = this.enterpriseNumber;
        }
        if (n >= 0) {
            stringBuilder.append('@').append(n);
        }
        return stringBuilder.toString();
    }

    private void checkRequired(Map<String, String> map) {
        for (String string : this.mdcRequired) {
            String string2 = map.get(string);
            if (string2 != null) continue;
            throw new LoggingException("Required key " + string + " is missing from the " + this.mdcId);
        }
    }

    private void appendMap(String string, Map<String, String> map, StringBuilder stringBuilder, ListChecker listChecker) {
        TreeMap<String, String> treeMap = new TreeMap<String, String>(map);
        for (Map.Entry entry : treeMap.entrySet()) {
            if (!listChecker.check((String)entry.getKey()) || entry.getValue() == null) continue;
            stringBuilder.append(' ');
            if (string != null) {
                stringBuilder.append(string);
            }
            String string2 = this.escapeNewlines(this.escapeSDParams((String)entry.getKey()), this.escapeNewLine);
            String string3 = this.escapeNewlines(this.escapeSDParams((String)entry.getValue()), this.escapeNewLine);
            StringBuilders.appendKeyDqValue(stringBuilder, string2, string3);
        }
    }

    private String escapeSDParams(String string) {
        return PARAM_VALUE_ESCAPE_PATTERN.matcher(string).replaceAll("\\\\$0");
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("facility=").append(this.facility.name());
        stringBuilder.append(" appName=").append(this.appName);
        stringBuilder.append(" defaultId=").append(this.defaultId);
        stringBuilder.append(" enterpriseNumber=").append(this.enterpriseNumber);
        stringBuilder.append(" newLine=").append(this.includeNewLine);
        stringBuilder.append(" includeMDC=").append(this.includeMdc);
        stringBuilder.append(" messageId=").append(this.messageId);
        return stringBuilder.toString();
    }

    @PluginFactory
    public static Rfc5424Layout createLayout(@PluginAttribute(value="facility", defaultString="LOCAL0") Facility facility, @PluginAttribute(value="id") String string, @PluginAttribute(value="enterpriseNumber", defaultInt=18060) int n, @PluginAttribute(value="includeMDC", defaultBoolean=true) boolean bl, @PluginAttribute(value="mdcId", defaultString="mdc") String string2, @PluginAttribute(value="mdcPrefix") String string3, @PluginAttribute(value="eventPrefix") String string4, @PluginAttribute(value="newLine") boolean bl2, @PluginAttribute(value="newLineEscape") String string5, @PluginAttribute(value="appName") String string6, @PluginAttribute(value="messageId") String string7, @PluginAttribute(value="mdcExcludes") String string8, @PluginAttribute(value="mdcIncludes") String string9, @PluginAttribute(value="mdcRequired") String string10, @PluginAttribute(value="exceptionPattern") String string11, @PluginAttribute(value="useTlsMessageFormat") boolean bl3, @PluginElement(value="LoggerFields") LoggerFields[] loggerFieldsArray, @PluginConfiguration Configuration configuration) {
        if (string9 != null && string8 != null) {
            LOGGER.error("mdcIncludes and mdcExcludes are mutually exclusive. Includes wil be ignored");
            string9 = null;
        }
        return new Rfc5424Layout(configuration, facility, string, n, bl, bl2, string5, string2, string3, string4, string6, string7, string8, string9, string10, StandardCharsets.UTF_8, string11, bl3, loggerFieldsArray);
    }

    public Facility getFacility() {
        return this.facility;
    }

    @Override
    public Serializable toSerializable(LogEvent logEvent) {
        return this.toSerializable(logEvent);
    }

    static List access$300(Rfc5424Layout rfc5424Layout) {
        return rfc5424Layout.mdcIncludes;
    }

    static List access$400(Rfc5424Layout rfc5424Layout) {
        return rfc5424Layout.mdcExcludes;
    }

    static class 1 {
    }

    private class StructuredDataElement {
        private final Map<String, String> fields;
        private final boolean discardIfEmpty;
        final Rfc5424Layout this$0;

        public StructuredDataElement(Rfc5424Layout rfc5424Layout, Map<String, String> map, boolean bl) {
            this.this$0 = rfc5424Layout;
            this.discardIfEmpty = bl;
            this.fields = map;
        }

        boolean discard() {
            if (!this.discardIfEmpty) {
                return true;
            }
            boolean bl = false;
            for (Map.Entry<String, String> entry : this.fields.entrySet()) {
                if (!Strings.isNotEmpty(entry.getValue())) continue;
                bl = true;
                break;
            }
            return !bl;
        }

        void union(Map<String, String> map) {
            this.fields.putAll(map);
        }

        Map<String, String> getFields() {
            return this.fields;
        }
    }

    private class FieldFormatter {
        private final Map<String, List<PatternFormatter>> delegateMap;
        private final boolean discardIfEmpty;
        final Rfc5424Layout this$0;

        public FieldFormatter(Rfc5424Layout rfc5424Layout, Map<String, List<PatternFormatter>> map, boolean bl) {
            this.this$0 = rfc5424Layout;
            this.discardIfEmpty = bl;
            this.delegateMap = map;
        }

        public StructuredDataElement format(LogEvent logEvent) {
            HashMap<String, String> hashMap = new HashMap<String, String>(this.delegateMap.size());
            for (Map.Entry<String, List<PatternFormatter>> entry : this.delegateMap.entrySet()) {
                StringBuilder stringBuilder = new StringBuilder();
                for (PatternFormatter patternFormatter : entry.getValue()) {
                    patternFormatter.format(logEvent, stringBuilder);
                }
                hashMap.put(entry.getKey(), stringBuilder.toString());
            }
            return new StructuredDataElement(this.this$0, hashMap, this.discardIfEmpty);
        }
    }

    private class NoopChecker
    implements ListChecker {
        final Rfc5424Layout this$0;

        private NoopChecker(Rfc5424Layout rfc5424Layout) {
            this.this$0 = rfc5424Layout;
        }

        @Override
        public boolean check(String string) {
            return false;
        }

        NoopChecker(Rfc5424Layout rfc5424Layout, 1 var2_2) {
            this(rfc5424Layout);
        }
    }

    private class ExcludeChecker
    implements ListChecker {
        final Rfc5424Layout this$0;

        private ExcludeChecker(Rfc5424Layout rfc5424Layout) {
            this.this$0 = rfc5424Layout;
        }

        @Override
        public boolean check(String string) {
            return !Rfc5424Layout.access$400(this.this$0).contains(string);
        }

        ExcludeChecker(Rfc5424Layout rfc5424Layout, 1 var2_2) {
            this(rfc5424Layout);
        }
    }

    private class IncludeChecker
    implements ListChecker {
        final Rfc5424Layout this$0;

        private IncludeChecker(Rfc5424Layout rfc5424Layout) {
            this.this$0 = rfc5424Layout;
        }

        @Override
        public boolean check(String string) {
            return Rfc5424Layout.access$300(this.this$0).contains(string);
        }

        IncludeChecker(Rfc5424Layout rfc5424Layout, 1 var2_2) {
            this(rfc5424Layout);
        }
    }

    private static interface ListChecker {
        public boolean check(String var1);
    }
}

