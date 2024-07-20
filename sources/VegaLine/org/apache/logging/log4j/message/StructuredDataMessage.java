/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.message;

import java.util.Map;
import org.apache.logging.log4j.message.AsynchronouslyFormattable;
import org.apache.logging.log4j.message.MapMessage;
import org.apache.logging.log4j.message.StructuredDataId;
import org.apache.logging.log4j.util.EnglishEnums;
import org.apache.logging.log4j.util.StringBuilderFormattable;
import org.apache.logging.log4j.util.StringBuilders;

@AsynchronouslyFormattable
public class StructuredDataMessage
extends MapMessage
implements StringBuilderFormattable {
    private static final long serialVersionUID = 1703221292892071920L;
    private static final int MAX_LENGTH = 32;
    private static final int HASHVAL = 31;
    private StructuredDataId id;
    private String message;
    private String type;

    public StructuredDataMessage(String id, String msg, String type2) {
        this.id = new StructuredDataId(id, null, null);
        this.message = msg;
        this.type = type2;
    }

    public StructuredDataMessage(String id, String msg, String type2, Map<String, String> data) {
        super(data);
        this.id = new StructuredDataId(id, null, null);
        this.message = msg;
        this.type = type2;
    }

    public StructuredDataMessage(StructuredDataId id, String msg, String type2) {
        this.id = id;
        this.message = msg;
        this.type = type2;
    }

    public StructuredDataMessage(StructuredDataId id, String msg, String type2, Map<String, String> data) {
        super(data);
        this.id = id;
        this.message = msg;
        this.type = type2;
    }

    private StructuredDataMessage(StructuredDataMessage msg, Map<String, String> map) {
        super(map);
        this.id = msg.id;
        this.message = msg.message;
        this.type = msg.type;
    }

    protected StructuredDataMessage() {
    }

    @Override
    public StructuredDataMessage with(String key, String value) {
        this.put(key, value);
        return this;
    }

    @Override
    public String[] getFormats() {
        String[] formats = new String[Format.values().length];
        int i = 0;
        for (Format format : Format.values()) {
            formats[i++] = format.name();
        }
        return formats;
    }

    public StructuredDataId getId() {
        return this.id;
    }

    protected void setId(String id) {
        this.id = new StructuredDataId(id, null, null);
    }

    protected void setId(StructuredDataId id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    protected void setType(String type2) {
        if (type2.length() > 32) {
            throw new IllegalArgumentException("structured data type exceeds maximum length of 32 characters: " + type2);
        }
        this.type = type2;
    }

    @Override
    public void formatTo(StringBuilder buffer) {
        this.asString(Format.FULL, null, buffer);
    }

    @Override
    public String getFormat() {
        return this.message;
    }

    protected void setMessageFormat(String msg) {
        this.message = msg;
    }

    @Override
    protected void validate(String key, String value) {
        this.validateKey(key);
    }

    private void validateKey(String key) {
        if (key.length() > 32) {
            throw new IllegalArgumentException("Structured data keys are limited to 32 characters. key: " + key);
        }
        for (int i = 0; i < key.length(); ++i) {
            char c = key.charAt(i);
            if (c >= '!' && c <= '~' && c != '=' && c != ']' && c != '\"') continue;
            throw new IllegalArgumentException("Structured data keys must contain printable US ASCII charactersand may not contain a space, =, ], or \"");
        }
    }

    @Override
    public String asString() {
        return this.asString(Format.FULL, null);
    }

    @Override
    public String asString(String format) {
        try {
            return this.asString(EnglishEnums.valueOf(Format.class, format), null);
        } catch (IllegalArgumentException ex) {
            return this.asString();
        }
    }

    public final String asString(Format format, StructuredDataId structuredDataId) {
        StringBuilder sb = new StringBuilder();
        this.asString(format, structuredDataId, sb);
        return sb.toString();
    }

    public final void asString(Format format, StructuredDataId structuredDataId, StringBuilder sb) {
        String msg;
        StructuredDataId sdId;
        boolean full = Format.FULL.equals((Object)format);
        if (full) {
            String myType = this.getType();
            if (myType == null) {
                return;
            }
            sb.append(this.getType()).append(' ');
        }
        if ((sdId = (sdId = this.getId()) != null ? sdId.makeId(structuredDataId) : structuredDataId) == null || sdId.getName() == null) {
            return;
        }
        sb.append('[');
        StringBuilders.appendValue(sb, sdId);
        sb.append(' ');
        this.appendMap(sb);
        sb.append(']');
        if (full && (msg = this.getFormat()) != null) {
            sb.append(' ').append(msg);
        }
    }

    @Override
    public String getFormattedMessage() {
        return this.asString(Format.FULL, null);
    }

    @Override
    public String getFormattedMessage(String[] formats) {
        if (formats != null && formats.length > 0) {
            for (int i = 0; i < formats.length; ++i) {
                String format = formats[i];
                if (Format.XML.name().equalsIgnoreCase(format)) {
                    return this.asXml();
                }
                if (!Format.FULL.name().equalsIgnoreCase(format)) continue;
                return this.asString(Format.FULL, null);
            }
            return this.asString(null, null);
        }
        return this.asString(Format.FULL, null);
    }

    private String asXml() {
        StringBuilder sb = new StringBuilder();
        StructuredDataId sdId = this.getId();
        if (sdId == null || sdId.getName() == null || this.type == null) {
            return sb.toString();
        }
        sb.append("<StructuredData>\n");
        sb.append("<type>").append(this.type).append("</type>\n");
        sb.append("<id>").append(sdId).append("</id>\n");
        super.asXml(sb);
        sb.append("</StructuredData>\n");
        return sb.toString();
    }

    @Override
    public String toString() {
        return this.asString(null, null);
    }

    @Override
    public MapMessage newInstance(Map<String, String> map) {
        return new StructuredDataMessage(this, map);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        StructuredDataMessage that = (StructuredDataMessage)o;
        if (!super.equals(o)) {
            return false;
        }
        if (this.type != null ? !this.type.equals(that.type) : that.type != null) {
            return false;
        }
        if (this.id != null ? !this.id.equals(that.id) : that.id != null) {
            return false;
        }
        return !(this.message != null ? !this.message.equals(that.message) : that.message != null);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (this.type != null ? this.type.hashCode() : 0);
        result = 31 * result + (this.id != null ? this.id.hashCode() : 0);
        result = 31 * result + (this.message != null ? this.message.hashCode() : 0);
        return result;
    }

    public static enum Format {
        XML,
        FULL;

    }
}

