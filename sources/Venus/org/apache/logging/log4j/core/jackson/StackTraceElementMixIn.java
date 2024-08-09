/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
 */
package org.apache.logging.log4j.core.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(value={"nativeMethod"})
abstract class StackTraceElementMixIn {
    @JsonCreator
    StackTraceElementMixIn(@JsonProperty(value="class") String string, @JsonProperty(value="method") String string2, @JsonProperty(value="file") String string3, @JsonProperty(value="line") int n) {
    }

    @JsonProperty(value="class")
    @JacksonXmlProperty(localName="class", isAttribute=true)
    abstract String getClassName();

    @JsonProperty(value="file")
    @JacksonXmlProperty(localName="file", isAttribute=true)
    abstract String getFileName();

    @JsonProperty(value="line")
    @JacksonXmlProperty(localName="line", isAttribute=true)
    abstract int getLineNumber();

    @JsonProperty(value="method")
    @JacksonXmlProperty(localName="method", isAttribute=true)
    abstract String getMethodName();
}

