/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

public class CharsetUtils {
    public static Charset lookup(String string) {
        if (string == null) {
            return null;
        }
        try {
            return Charset.forName(string);
        } catch (UnsupportedCharsetException unsupportedCharsetException) {
            return null;
        }
    }

    public static Charset get(String string) throws UnsupportedEncodingException {
        if (string == null) {
            return null;
        }
        try {
            return Charset.forName(string);
        } catch (UnsupportedCharsetException unsupportedCharsetException) {
            throw new UnsupportedEncodingException(string);
        }
    }
}

