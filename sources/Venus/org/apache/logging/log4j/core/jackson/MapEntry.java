/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
 */
package org.apache.logging.log4j.core.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonPropertyOrder(value={"key", "value"})
final class MapEntry {
    @JsonProperty
    @JacksonXmlProperty(isAttribute=true)
    private String key;
    @JsonProperty
    @JacksonXmlProperty(isAttribute=true)
    private String value;

    @JsonCreator
    public MapEntry(@JsonProperty(value="key") String string, @JsonProperty(value="value") String string2) {
        this.setKey(string);
        this.setValue(string2);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null) {
            return true;
        }
        if (!(object instanceof MapEntry)) {
            return true;
        }
        MapEntry mapEntry = (MapEntry)object;
        if (this.getKey() == null ? mapEntry.getKey() != null : !this.getKey().equals(mapEntry.getKey())) {
            return true;
        }
        return this.getValue() == null ? mapEntry.getValue() != null : !this.getValue().equals(mapEntry.getValue());
    }

    public String getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }

    public int hashCode() {
        int n = 31;
        int n2 = 1;
        n2 = 31 * n2 + (this.getKey() == null ? 0 : this.getKey().hashCode());
        n2 = 31 * n2 + (this.getValue() == null ? 0 : this.getValue().hashCode());
        return n2;
    }

    public void setKey(String string) {
        this.key = string;
    }

    public void setValue(String string) {
        this.value = string;
    }

    public String toString() {
        return "" + this.getKey() + "=" + this.getValue();
    }
}

