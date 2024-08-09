/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.exception;

import java.util.HashMap;
import java.util.Map;

public class InformativeException
extends Exception {
    private final Map<String, Object> info = new HashMap<String, Object>();
    private boolean shouldBePrinted = true;
    private int sources;

    public InformativeException(Throwable throwable) {
        super(throwable);
    }

    public InformativeException set(String string, Object object) {
        this.info.put(string, object);
        return this;
    }

    public InformativeException addSource(Class<?> clazz) {
        return this.set("Source " + this.sources++, this.getSource(clazz));
    }

    private String getSource(Class<?> clazz) {
        return clazz.isAnonymousClass() ? clazz.getName() + " (Anonymous)" : clazz.getName();
    }

    public boolean shouldBePrinted() {
        return this.shouldBePrinted;
    }

    public void setShouldBePrinted(boolean bl) {
        this.shouldBePrinted = bl;
    }

    @Override
    public String getMessage() {
        StringBuilder stringBuilder = new StringBuilder("Please report this on the Via support Discord or open an issue on the relevant GitHub repository\n");
        boolean bl = true;
        for (Map.Entry<String, Object> entry : this.info.entrySet()) {
            if (!bl) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(entry.getKey()).append(": ").append(entry.getValue());
            bl = false;
        }
        return stringBuilder.toString();
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}

