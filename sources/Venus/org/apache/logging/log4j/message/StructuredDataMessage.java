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

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
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

    public StructuredDataMessage(String string, String string2, String string3) {
        this.id = new StructuredDataId(string, null, null);
        this.message = string2;
        this.type = string3;
    }

    public StructuredDataMessage(String string, String string2, String string3, Map<String, String> map) {
        super(map);
        this.id = new StructuredDataId(string, null, null);
        this.message = string2;
        this.type = string3;
    }

    public StructuredDataMessage(StructuredDataId structuredDataId, String string, String string2) {
        this.id = structuredDataId;
        this.message = string;
        this.type = string2;
    }

    public StructuredDataMessage(StructuredDataId structuredDataId, String string, String string2, Map<String, String> map) {
        super(map);
        this.id = structuredDataId;
        this.message = string;
        this.type = string2;
    }

    private StructuredDataMessage(StructuredDataMessage structuredDataMessage, Map<String, String> map) {
        super(map);
        this.id = structuredDataMessage.id;
        this.message = structuredDataMessage.message;
        this.type = structuredDataMessage.type;
    }

    protected StructuredDataMessage() {
    }

    @Override
    public StructuredDataMessage with(String string, String string2) {
        this.put(string, string2);
        return this;
    }

    @Override
    public String[] getFormats() {
        String[] stringArray = new String[Format.values().length];
        int n = 0;
        for (Format format2 : Format.values()) {
            stringArray[n++] = format2.name();
        }
        return stringArray;
    }

    public StructuredDataId getId() {
        return this.id;
    }

    protected void setId(String string) {
        this.id = new StructuredDataId(string, null, null);
    }

    protected void setId(StructuredDataId structuredDataId) {
        this.id = structuredDataId;
    }

    public String getType() {
        return this.type;
    }

    protected void setType(String string) {
        if (string.length() > 32) {
            throw new IllegalArgumentException("structured data type exceeds maximum length of 32 characters: " + string);
        }
        this.type = string;
    }

    @Override
    public void formatTo(StringBuilder stringBuilder) {
        this.asString(Format.FULL, null, stringBuilder);
    }

    @Override
    public String getFormat() {
        return this.message;
    }

    protected void setMessageFormat(String string) {
        this.message = string;
    }

    @Override
    protected void validate(String string, String string2) {
        this.validateKey(string);
    }

    private void validateKey(String string) {
        if (string.length() > 32) {
            throw new IllegalArgumentException("Structured data keys are limited to 32 characters. key: " + string);
        }
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (c >= '!' && c <= '~' && c != '=' && c != ']' && c != '\"') continue;
            throw new IllegalArgumentException("Structured data keys must contain printable US ASCII charactersand may not contain a space, =, ], or \"");
        }
    }

    @Override
    public String asString() {
        return this.asString(Format.FULL, null);
    }

    @Override
    public String asString(String string) {
        try {
            return this.asString(EnglishEnums.valueOf(Format.class, string), null);
        } catch (IllegalArgumentException illegalArgumentException) {
            return this.asString();
        }
    }

    public final String asString(Format format2, StructuredDataId structuredDataId) {
        StringBuilder stringBuilder = new StringBuilder();
        this.asString(format2, structuredDataId, stringBuilder);
        return stringBuilder.toString();
    }

    public final void asString(Format format2, StructuredDataId structuredDataId, StringBuilder stringBuilder) {
        String string;
        Object object;
        boolean bl = Format.FULL.equals((Object)format2);
        if (bl) {
            object = this.getType();
            if (object == null) {
                return;
            }
            stringBuilder.append(this.getType()).append(' ');
        }
        if ((object = (object = this.getId()) != null ? ((StructuredDataId)object).makeId(structuredDataId) : structuredDataId) == null || ((StructuredDataId)object).getName() == null) {
            return;
        }
        stringBuilder.append('[');
        StringBuilders.appendValue(stringBuilder, object);
        stringBuilder.append(' ');
        this.appendMap(stringBuilder);
        stringBuilder.append(']');
        if (bl && (string = this.getFormat()) != null) {
            stringBuilder.append(' ').append(string);
        }
    }

    @Override
    public String getFormattedMessage() {
        return this.asString(Format.FULL, null);
    }

    @Override
    public String getFormattedMessage(String[] stringArray) {
        if (stringArray != null && stringArray.length > 0) {
            for (int i = 0; i < stringArray.length; ++i) {
                String string = stringArray[i];
                if (Format.XML.name().equalsIgnoreCase(string)) {
                    return this.asXml();
                }
                if (!Format.FULL.name().equalsIgnoreCase(string)) continue;
                return this.asString(Format.FULL, null);
            }
            return this.asString(null, null);
        }
        return this.asString(Format.FULL, null);
    }

    private String asXml() {
        StringBuilder stringBuilder = new StringBuilder();
        StructuredDataId structuredDataId = this.getId();
        if (structuredDataId == null || structuredDataId.getName() == null || this.type == null) {
            return stringBuilder.toString();
        }
        stringBuilder.append("<StructuredData>\n");
        stringBuilder.append("<type>").append(this.type).append("</type>\n");
        stringBuilder.append("<id>").append(structuredDataId).append("</id>\n");
        super.asXml(stringBuilder);
        stringBuilder.append("</StructuredData>\n");
        return stringBuilder.toString();
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
    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        StructuredDataMessage structuredDataMessage = (StructuredDataMessage)object;
        if (!super.equals(object)) {
            return true;
        }
        if (this.type != null ? !this.type.equals(structuredDataMessage.type) : structuredDataMessage.type != null) {
            return true;
        }
        if (this.id != null ? !this.id.equals(structuredDataMessage.id) : structuredDataMessage.id != null) {
            return true;
        }
        return this.message != null ? !this.message.equals(structuredDataMessage.message) : structuredDataMessage.message != null;
    }

    @Override
    public int hashCode() {
        int n = super.hashCode();
        n = 31 * n + (this.type != null ? this.type.hashCode() : 0);
        n = 31 * n + (this.id != null ? this.id.hashCode() : 0);
        n = 31 * n + (this.message != null ? this.message.hashCode() : 0);
        return n;
    }

    @Override
    public MapMessage with(String string, String string2) {
        return this.with(string, string2);
    }

    public static enum Format {
        XML,
        FULL;

    }
}

