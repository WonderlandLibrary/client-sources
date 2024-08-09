/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http;

public class HttpException
extends Exception {
    private static final int FIRST_VALID_CHAR = 32;
    private static final long serialVersionUID = -5437299376222011036L;

    static String clean(String string) {
        int n;
        char[] cArray = string.toCharArray();
        for (n = 0; n < cArray.length && cArray[n] >= ' '; ++n) {
        }
        if (n == cArray.length) {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder(cArray.length * 2);
        for (n = 0; n < cArray.length; ++n) {
            char c = cArray[n];
            if (c < ' ') {
                stringBuilder.append("[0x");
                String string2 = Integer.toHexString(n);
                if (string2.length() == 1) {
                    stringBuilder.append("0");
                }
                stringBuilder.append(string2);
                stringBuilder.append("]");
                continue;
            }
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    public HttpException() {
    }

    public HttpException(String string) {
        super(HttpException.clean(string));
    }

    public HttpException(String string, Throwable throwable) {
        super(HttpException.clean(string));
        this.initCause(throwable);
    }
}

