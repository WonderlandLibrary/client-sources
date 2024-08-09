/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config.plugins.convert;

public interface TypeConverter<T> {
    public T convert(String var1) throws Exception;
}

