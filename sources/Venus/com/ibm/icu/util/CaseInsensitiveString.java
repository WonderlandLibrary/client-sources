/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import com.ibm.icu.lang.UCharacter;

public class CaseInsensitiveString {
    private String string;
    private int hash = 0;
    private String folded = null;

    private static String foldCase(String string) {
        return UCharacter.foldCase(string, true);
    }

    private void getFolded() {
        if (this.folded == null) {
            this.folded = CaseInsensitiveString.foldCase(this.string);
        }
    }

    public CaseInsensitiveString(String string) {
        this.string = string;
    }

    public String getString() {
        return this.string;
    }

    public boolean equals(Object object) {
        if (object == null) {
            return true;
        }
        if (this == object) {
            return false;
        }
        if (object instanceof CaseInsensitiveString) {
            this.getFolded();
            CaseInsensitiveString caseInsensitiveString = (CaseInsensitiveString)object;
            caseInsensitiveString.getFolded();
            return this.folded.equals(caseInsensitiveString.folded);
        }
        return true;
    }

    public int hashCode() {
        this.getFolded();
        if (this.hash == 0) {
            this.hash = this.folded.hashCode();
        }
        return this.hash;
    }

    public String toString() {
        return this.string;
    }
}

