/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http;

import io.netty.util.AsciiString;

public enum HttpStatusClass {
    INFORMATIONAL(100, 200, "Informational"),
    SUCCESS(200, 300, "Success"),
    REDIRECTION(300, 400, "Redirection"),
    CLIENT_ERROR(400, 500, "Client Error"),
    SERVER_ERROR(500, 600, "Server Error"),
    UNKNOWN(0, 0, "Unknown Status"){

        @Override
        public boolean contains(int n) {
            return n < 100 || n >= 600;
        }
    };

    private final int min;
    private final int max;
    private final AsciiString defaultReasonPhrase;

    public static HttpStatusClass valueOf(int n) {
        if (INFORMATIONAL.contains(n)) {
            return INFORMATIONAL;
        }
        if (SUCCESS.contains(n)) {
            return SUCCESS;
        }
        if (REDIRECTION.contains(n)) {
            return REDIRECTION;
        }
        if (CLIENT_ERROR.contains(n)) {
            return CLIENT_ERROR;
        }
        if (SERVER_ERROR.contains(n)) {
            return SERVER_ERROR;
        }
        return UNKNOWN;
    }

    public static HttpStatusClass valueOf(CharSequence charSequence) {
        if (charSequence != null && charSequence.length() == 3) {
            char c = charSequence.charAt(0);
            return HttpStatusClass.isDigit(c) && HttpStatusClass.isDigit(charSequence.charAt(1)) && HttpStatusClass.isDigit(charSequence.charAt(2)) ? HttpStatusClass.valueOf(HttpStatusClass.digit(c) * 100) : UNKNOWN;
        }
        return UNKNOWN;
    }

    private static int digit(char c) {
        return c - 48;
    }

    private static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private HttpStatusClass(int n2, int n3, String string2) {
        this.min = n2;
        this.max = n3;
        this.defaultReasonPhrase = AsciiString.cached(string2);
    }

    public boolean contains(int n) {
        return n >= this.min && n < this.max;
    }

    AsciiString defaultReasonPhrase() {
        return this.defaultReasonPhrase;
    }

    HttpStatusClass(int n2, int n3, String string2, 1 var6_6) {
        this(n2, n3, string2);
    }
}

