/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
 */
package org.apache.logging.log4j.core.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.io.Serializable;
import org.apache.logging.log4j.core.impl.ExtendedClassInfo;

@JsonPropertyOrder(value={"class", "method", "file", "line", "exact", "location", "version"})
abstract class ExtendedStackTraceElementMixIn
implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonCreator
    public ExtendedStackTraceElementMixIn(@JsonProperty(value="class") String string, @JsonProperty(value="method") String string2, @JsonProperty(value="file") String string3, @JsonProperty(value="line") int n, @JsonProperty(value="exact") boolean bl, @JsonProperty(value="location") String string4, @JsonProperty(value="version") String string5) {
    }

    @JsonProperty(value="class")
    @JacksonXmlProperty(localName="class", isAttribute=true)
    public abstract String getClassName();

    @JsonProperty
    @JacksonXmlProperty(isAttribute=true)
    public abstract boolean getExact();

    @JsonIgnore
    public abstract ExtendedClassInfo getExtraClassInfo();

    @JsonProperty(value="file")
    @JacksonXmlProperty(localName="file", isAttribute=true)
    public abstract String getFileName();

    @JsonProperty(value="line")
    @JacksonXmlProperty(localName="line", isAttribute=true)
    public abstract int getLineNumber();

    @JsonProperty
    @JacksonXmlProperty(isAttribute=true)
    public abstract String getLocation();

    @JsonProperty(value="method")
    @JacksonXmlProperty(localName="method", isAttribute=true)
    public abstract String getMethodName();

    @JsonIgnore
    abstract StackTraceElement getStackTraceElement();

    @JsonProperty
    @JacksonXmlProperty(isAttribute=true)
    public abstract String getVersion();

    @JsonIgnore
    public abstract boolean isNativeMethod();
}

