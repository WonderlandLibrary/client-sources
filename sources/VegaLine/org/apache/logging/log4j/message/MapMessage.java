/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.message;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import org.apache.logging.log4j.message.AsynchronouslyFormattable;
import org.apache.logging.log4j.message.MultiformatMessage;
import org.apache.logging.log4j.util.EnglishEnums;
import org.apache.logging.log4j.util.IndexedReadOnlyStringMap;
import org.apache.logging.log4j.util.IndexedStringMap;
import org.apache.logging.log4j.util.PerformanceSensitive;
import org.apache.logging.log4j.util.SortedArrayStringMap;
import org.apache.logging.log4j.util.StringBuilderFormattable;
import org.apache.logging.log4j.util.StringBuilders;

@AsynchronouslyFormattable
@PerformanceSensitive(value={"allocation"})
public class MapMessage
implements MultiformatMessage,
StringBuilderFormattable {
    private static final long serialVersionUID = -5031471831131487120L;
    private final IndexedStringMap data;

    public MapMessage() {
        this.data = new SortedArrayStringMap();
    }

    public MapMessage(Map<String, String> map) {
        this.data = new SortedArrayStringMap(map);
    }

    @Override
    public String[] getFormats() {
        return MapFormat.names();
    }

    @Override
    public Object[] getParameters() {
        Object[] result = new Object[this.data.size()];
        for (int i = 0; i < this.data.size(); ++i) {
            result[i] = this.data.getValueAt(i);
        }
        return result;
    }

    @Override
    public String getFormat() {
        return "";
    }

    public Map<String, String> getData() {
        TreeMap<String, String> result = new TreeMap<String, String>();
        for (int i = 0; i < this.data.size(); ++i) {
            result.put(this.data.getKeyAt(i), (String)this.data.getValueAt(i));
        }
        return Collections.unmodifiableMap(result);
    }

    public IndexedReadOnlyStringMap getIndexedReadOnlyStringMap() {
        return this.data;
    }

    public void clear() {
        this.data.clear();
    }

    public MapMessage with(String key, String value) {
        this.put(key, value);
        return this;
    }

    public void put(String key, String value) {
        if (value == null) {
            throw new IllegalArgumentException("No value provided for key " + key);
        }
        this.validate(key, value);
        this.data.putValue(key, value);
    }

    protected void validate(String key, String value) {
    }

    public void putAll(Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            this.data.putValue(entry.getKey(), entry.getValue());
        }
    }

    public String get(String key) {
        return (String)this.data.getValue(key);
    }

    public String remove(String key) {
        String result = (String)this.data.getValue(key);
        this.data.remove(key);
        return result;
    }

    public String asString() {
        return this.format(null, new StringBuilder()).toString();
    }

    public String asString(String format) {
        try {
            return this.format(EnglishEnums.valueOf(MapFormat.class, format), new StringBuilder()).toString();
        } catch (IllegalArgumentException ex) {
            return this.asString();
        }
    }

    private StringBuilder format(MapFormat format, StringBuilder sb) {
        if (format == null) {
            this.appendMap(sb);
        } else {
            switch (format) {
                case XML: {
                    this.asXml(sb);
                    break;
                }
                case JSON: {
                    this.asJson(sb);
                    break;
                }
                case JAVA: {
                    this.asJava(sb);
                    break;
                }
                default: {
                    this.appendMap(sb);
                }
            }
        }
        return sb;
    }

    public void asXml(StringBuilder sb) {
        sb.append("<Map>\n");
        for (int i = 0; i < this.data.size(); ++i) {
            sb.append("  <Entry key=\"").append(this.data.getKeyAt(i)).append("\">").append(this.data.getValueAt(i)).append("</Entry>\n");
        }
        sb.append("</Map>");
    }

    @Override
    public String getFormattedMessage() {
        return this.asString();
    }

    @Override
    public String getFormattedMessage(String[] formats) {
        if (formats == null || formats.length == 0) {
            return this.asString();
        }
        for (int i = 0; i < formats.length; ++i) {
            MapFormat mapFormat = MapFormat.lookupIgnoreCase(formats[i]);
            if (mapFormat == null) continue;
            return this.format(mapFormat, new StringBuilder()).toString();
        }
        return this.asString();
    }

    protected void appendMap(StringBuilder sb) {
        for (int i = 0; i < this.data.size(); ++i) {
            if (i > 0) {
                sb.append(' ');
            }
            StringBuilders.appendKeyDqValue(sb, this.data.getKeyAt(i), this.data.getValueAt(i));
        }
    }

    protected void asJson(StringBuilder sb) {
        sb.append('{');
        for (int i = 0; i < this.data.size(); ++i) {
            if (i > 0) {
                sb.append(", ");
            }
            StringBuilders.appendDqValue(sb, this.data.getKeyAt(i)).append(':');
            StringBuilders.appendDqValue(sb, this.data.getValueAt(i));
        }
        sb.append('}');
    }

    protected void asJava(StringBuilder sb) {
        sb.append('{');
        for (int i = 0; i < this.data.size(); ++i) {
            if (i > 0) {
                sb.append(", ");
            }
            StringBuilders.appendKeyDqValue(sb, this.data.getKeyAt(i), this.data.getValueAt(i));
        }
        sb.append('}');
    }

    public MapMessage newInstance(Map<String, String> map) {
        return new MapMessage(map);
    }

    public String toString() {
        return this.asString();
    }

    @Override
    public void formatTo(StringBuilder buffer) {
        this.format(null, buffer);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        MapMessage that = (MapMessage)o;
        return this.data.equals(that.data);
    }

    public int hashCode() {
        return this.data.hashCode();
    }

    @Override
    public Throwable getThrowable() {
        return null;
    }

    public static enum MapFormat {
        XML,
        JSON,
        JAVA;


        public static MapFormat lookupIgnoreCase(String format) {
            return XML.name().equalsIgnoreCase(format) ? XML : (JSON.name().equalsIgnoreCase(format) ? JSON : (JAVA.name().equalsIgnoreCase(format) ? JAVA : null));
        }

        public static String[] names() {
            return new String[]{XML.name(), JSON.name(), JAVA.name()};
        }
    }
}

