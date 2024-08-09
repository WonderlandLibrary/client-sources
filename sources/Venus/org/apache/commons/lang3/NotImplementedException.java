/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3;

public class NotImplementedException
extends UnsupportedOperationException {
    private static final long serialVersionUID = 20131021L;
    private final String code;

    public NotImplementedException(String string) {
        this(string, (String)null);
    }

    public NotImplementedException(Throwable throwable) {
        this(throwable, null);
    }

    public NotImplementedException(String string, Throwable throwable) {
        this(string, throwable, null);
    }

    public NotImplementedException(String string, String string2) {
        super(string);
        this.code = string2;
    }

    public NotImplementedException(Throwable throwable, String string) {
        super(throwable);
        this.code = string;
    }

    public NotImplementedException(String string, Throwable throwable, String string2) {
        super(string, throwable);
        this.code = string2;
    }

    public String getCode() {
        return this.code;
    }
}

