/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core;

import java.io.Serializable;
import java.util.Map;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.layout.Encoder;

public interface Layout<T extends Serializable>
extends Encoder<LogEvent> {
    public static final String ELEMENT_TYPE = "layout";

    public byte[] getFooter();

    public byte[] getHeader();

    public byte[] toByteArray(LogEvent var1);

    public T toSerializable(LogEvent var1);

    public String getContentType();

    public Map<String, String> getContentFormat();
}

