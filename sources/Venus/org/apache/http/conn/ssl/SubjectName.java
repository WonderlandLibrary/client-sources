/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.conn.ssl;

import org.apache.http.util.Args;

final class SubjectName {
    static final int DNS = 2;
    static final int IP = 7;
    private final String value;
    private final int type;

    static SubjectName IP(String string) {
        return new SubjectName(string, 7);
    }

    static SubjectName DNS(String string) {
        return new SubjectName(string, 2);
    }

    SubjectName(String string, int n) {
        this.value = Args.notNull(string, "Value");
        this.type = Args.positive(n, "Type");
    }

    public int getType() {
        return this.type;
    }

    public String getValue() {
        return this.value;
    }

    public String toString() {
        return this.value;
    }
}

