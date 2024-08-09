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
        Object[] objectArray = new Object[this.data.size()];
        for (int i = 0; i < this.data.size(); ++i) {
            objectArray[i] = this.data.getValueAt(i);
        }
        return objectArray;
    }

    @Override
    public String getFormat() {
        return "";
    }

    public Map<String, String> getData() {
        TreeMap<String, String> treeMap = new TreeMap<String, String>();
        for (int i = 0; i < this.data.size(); ++i) {
            treeMap.put(this.data.getKeyAt(i), (String)this.data.getValueAt(i));
        }
        return Collections.unmodifiableMap(treeMap);
    }

    public IndexedReadOnlyStringMap getIndexedReadOnlyStringMap() {
        return this.data;
    }

    public void clear() {
        this.data.clear();
    }

    public MapMessage with(String string, String string2) {
        this.put(string, string2);
        return this;
    }

    public void put(String string, String string2) {
        if (string2 == null) {
            throw new IllegalArgumentException("No value provided for key " + string);
        }
        this.validate(string, string2);
        this.data.putValue(string, string2);
    }

    protected void validate(String string, String string2) {
    }

    public void putAll(Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            this.data.putValue(entry.getKey(), entry.getValue());
        }
    }

    public String get(String string) {
        return (String)this.data.getValue(string);
    }

    public String remove(String string) {
        String string2 = (String)this.data.getValue(string);
        this.data.remove(string);
        return string2;
    }

    public String asString() {
        return this.format(null, new StringBuilder()).toString();
    }

    public String asString(String string) {
        try {
            return this.format(EnglishEnums.valueOf(MapFormat.class, string), new StringBuilder()).toString();
        } catch (IllegalArgumentException illegalArgumentException) {
            return this.asString();
        }
    }

    private StringBuilder format(MapFormat mapFormat, StringBuilder stringBuilder) {
        if (mapFormat == null) {
            this.appendMap(stringBuilder);
        } else {
            switch (1.$SwitchMap$org$apache$logging$log4j$message$MapMessage$MapFormat[mapFormat.ordinal()]) {
                case 1: {
                    this.asXml(stringBuilder);
                    break;
                }
                case 2: {
                    this.asJson(stringBuilder);
                    break;
                }
                case 3: {
                    this.asJava(stringBuilder);
                    break;
                }
                default: {
                    this.appendMap(stringBuilder);
                }
            }
        }
        return stringBuilder;
    }

    public void asXml(StringBuilder stringBuilder) {
        stringBuilder.append("<Map>\n");
        for (int i = 0; i < this.data.size(); ++i) {
            stringBuilder.append("  <Entry key=\"").append(this.data.getKeyAt(i)).append("\">").append(this.data.getValueAt(i)).append("</Entry>\n");
        }
        stringBuilder.append("</Map>");
    }

    @Override
    public String getFormattedMessage() {
        return this.asString();
    }

    @Override
    public String getFormattedMessage(String[] stringArray) {
        if (stringArray == null || stringArray.length == 0) {
            return this.asString();
        }
        for (int i = 0; i < stringArray.length; ++i) {
            MapFormat mapFormat = MapFormat.lookupIgnoreCase(stringArray[i]);
            if (mapFormat == null) continue;
            return this.format(mapFormat, new StringBuilder()).toString();
        }
        return this.asString();
    }

    protected void appendMap(StringBuilder stringBuilder) {
        for (int i = 0; i < this.data.size(); ++i) {
            if (i > 0) {
                stringBuilder.append(' ');
            }
            StringBuilders.appendKeyDqValue(stringBuilder, this.data.getKeyAt(i), this.data.getValueAt(i));
        }
    }

    protected void asJson(StringBuilder stringBuilder) {
        stringBuilder.append('{');
        for (int i = 0; i < this.data.size(); ++i) {
            if (i > 0) {
                stringBuilder.append(", ");
            }
            StringBuilders.appendDqValue(stringBuilder, this.data.getKeyAt(i)).append(':');
            StringBuilders.appendDqValue(stringBuilder, this.data.getValueAt(i));
        }
        stringBuilder.append('}');
    }

    protected void asJava(StringBuilder stringBuilder) {
        stringBuilder.append('{');
        for (int i = 0; i < this.data.size(); ++i) {
            if (i > 0) {
                stringBuilder.append(", ");
            }
            StringBuilders.appendKeyDqValue(stringBuilder, this.data.getKeyAt(i), this.data.getValueAt(i));
        }
        stringBuilder.append('}');
    }

    public MapMessage newInstance(Map<String, String> map) {
        return new MapMessage(map);
    }

    public String toString() {
        return this.asString();
    }

    @Override
    public void formatTo(StringBuilder stringBuilder) {
        this.format(null, stringBuilder);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        MapMessage mapMessage = (MapMessage)object;
        return this.data.equals(mapMessage.data);
    }

    public int hashCode() {
        return this.data.hashCode();
    }

    @Override
    public Throwable getThrowable() {
        return null;
    }

    static class 1 {
        static final int[] $SwitchMap$org$apache$logging$log4j$message$MapMessage$MapFormat = new int[MapFormat.values().length];

        static {
            try {
                1.$SwitchMap$org$apache$logging$log4j$message$MapMessage$MapFormat[MapFormat.XML.ordinal()] = 1;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                1.$SwitchMap$org$apache$logging$log4j$message$MapMessage$MapFormat[MapFormat.JSON.ordinal()] = 2;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                1.$SwitchMap$org$apache$logging$log4j$message$MapMessage$MapFormat[MapFormat.JAVA.ordinal()] = 3;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
        }
    }

    public static enum MapFormat {
        XML,
        JSON,
        JAVA;


        public static MapFormat lookupIgnoreCase(String string) {
            return XML.name().equalsIgnoreCase(string) ? XML : (JSON.name().equalsIgnoreCase(string) ? JSON : (JAVA.name().equalsIgnoreCase(string) ? JAVA : null));
        }

        public static String[] names() {
            return new String[]{XML.name(), JSON.name(), JAVA.name()};
        }
    }
}

