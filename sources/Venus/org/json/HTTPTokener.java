/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.json;

import org.json.JSONException;
import org.json.JSONTokener;

public class HTTPTokener
extends JSONTokener {
    public HTTPTokener(String string) {
        super(string);
    }

    public String nextToken() throws JSONException {
        char c;
        StringBuilder stringBuilder = new StringBuilder();
        while (Character.isWhitespace(c = this.next())) {
        }
        if (c == '\"' || c == '\'') {
            char c2 = c;
            while (true) {
                if ((c = this.next()) < ' ') {
                    throw this.syntaxError("Unterminated string.");
                }
                if (c == c2) {
                    return stringBuilder.toString();
                }
                stringBuilder.append(c);
            }
        }
        while (c != '\u0000' && !Character.isWhitespace(c)) {
            stringBuilder.append(c);
            c = this.next();
        }
        return stringBuilder.toString();
    }
}

