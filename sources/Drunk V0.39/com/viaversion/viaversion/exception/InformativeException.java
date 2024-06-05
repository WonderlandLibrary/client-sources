/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.exception;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class InformativeException
extends Exception {
    private final Map<String, Object> info = new HashMap<String, Object>();
    private int sources;

    public InformativeException(Throwable cause) {
        super(cause);
    }

    public InformativeException set(String key, Object value) {
        this.info.put(key, value);
        return this;
    }

    public InformativeException addSource(Class<?> sourceClazz) {
        return this.set("Source " + this.sources++, this.getSource(sourceClazz));
    }

    private String getSource(Class<?> sourceClazz) {
        String string;
        if (sourceClazz.isAnonymousClass()) {
            string = sourceClazz.getName() + " (Anonymous)";
            return string;
        }
        string = sourceClazz.getName();
        return string;
    }

    @Override
    public String getMessage() {
        StringBuilder builder = new StringBuilder("Please post this error to https://github.com/ViaVersion/ViaVersion/issues and follow the issue template\n{");
        boolean first = true;
        Iterator<Map.Entry<String, Object>> iterator = this.info.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            if (!first) {
                builder.append(", ");
            }
            builder.append(entry.getKey()).append(": ").append(entry.getValue());
            first = false;
        }
        return builder.append("}\nActual Error: ").toString();
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}

