/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import java.text.ParseException;

public class StringPrepParseException
extends ParseException {
    static final long serialVersionUID = 7160264827701651255L;
    public static final int INVALID_CHAR_FOUND = 0;
    public static final int ILLEGAL_CHAR_FOUND = 1;
    public static final int PROHIBITED_ERROR = 2;
    public static final int UNASSIGNED_ERROR = 3;
    public static final int CHECK_BIDI_ERROR = 4;
    public static final int STD3_ASCII_RULES_ERROR = 5;
    public static final int ACE_PREFIX_ERROR = 6;
    public static final int VERIFICATION_ERROR = 7;
    public static final int LABEL_TOO_LONG_ERROR = 8;
    public static final int BUFFER_OVERFLOW_ERROR = 9;
    public static final int ZERO_LENGTH_LABEL = 10;
    public static final int DOMAIN_NAME_TOO_LONG_ERROR = 11;
    private int error;
    private int line;
    private StringBuffer preContext = new StringBuffer();
    private StringBuffer postContext = new StringBuffer();
    private static final int PARSE_CONTEXT_LEN = 16;
    static final boolean $assertionsDisabled = !StringPrepParseException.class.desiredAssertionStatus();

    public StringPrepParseException(String string, int n) {
        super(string, -1);
        this.error = n;
        this.line = 0;
    }

    public StringPrepParseException(String string, int n, String string2, int n2) {
        super(string, -1);
        this.error = n;
        this.setContext(string2, n2);
        this.line = 0;
    }

    public StringPrepParseException(String string, int n, String string2, int n2, int n3) {
        super(string, -1);
        this.error = n;
        this.setContext(string2, n2);
        this.line = n3;
    }

    public boolean equals(Object object) {
        if (!(object instanceof StringPrepParseException)) {
            return true;
        }
        return ((StringPrepParseException)object).error == this.error;
    }

    public int hashCode() {
        if (!$assertionsDisabled) {
            throw new AssertionError((Object)"hashCode not designed");
        }
        return 1;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.getMessage());
        stringBuilder.append(". line:  ");
        stringBuilder.append(this.line);
        stringBuilder.append(". preContext:  ");
        stringBuilder.append(this.preContext);
        stringBuilder.append(". postContext: ");
        stringBuilder.append(this.postContext);
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    private void setPreContext(String string, int n) {
        this.setPreContext(string.toCharArray(), n);
    }

    private void setPreContext(char[] cArray, int n) {
        int n2 = n <= 16 ? 0 : n - 15;
        int n3 = n2 <= 16 ? n2 : 16;
        this.preContext.append(cArray, n2, n3);
    }

    private void setPostContext(String string, int n) {
        this.setPostContext(string.toCharArray(), n);
    }

    private void setPostContext(char[] cArray, int n) {
        int n2 = n;
        int n3 = cArray.length - n2;
        this.postContext.append(cArray, n2, n3);
    }

    private void setContext(String string, int n) {
        this.setPreContext(string, n);
        this.setPostContext(string, n);
    }

    public int getError() {
        return this.error;
    }
}

