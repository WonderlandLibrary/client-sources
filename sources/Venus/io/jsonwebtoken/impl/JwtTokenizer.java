/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl;

import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.impl.DefaultTokenizedJwe;
import io.jsonwebtoken.impl.DefaultTokenizedJwt;
import io.jsonwebtoken.impl.TokenizedJwt;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Strings;
import java.io.IOException;
import java.io.Reader;

public class JwtTokenizer {
    static final char DELIMITER = '.';
    private static final String DELIM_ERR_MSG_PREFIX = "Invalid compact JWT string: Compact JWSs must contain exactly 2 period characters, and compact JWEs must contain exactly 4.  Found: ";

    private static int read(Reader reader, char[] cArray) {
        try {
            return reader.read(cArray);
        } catch (IOException iOException) {
            String string = "Unable to read compact JWT: " + iOException.getMessage();
            throw new MalformedJwtException(string, iOException);
        }
    }

    public <T extends TokenizedJwt> T tokenize(Reader reader) {
        Assert.notNull(reader, "Reader argument cannot be null.");
        String string = "";
        String string2 = "";
        String string3 = "";
        String string4 = "";
        String string5 = "";
        int n = 0;
        char[] cArray = new char[4096];
        int n2 = 0;
        StringBuilder stringBuilder = new StringBuilder(4096);
        while (n2 != -1) {
            n2 = JwtTokenizer.read(reader, cArray);
            for (int i = 0; i < n2; ++i) {
                CharSequence charSequence;
                char c = cArray[i];
                if (Character.isWhitespace(c)) {
                    charSequence = "Compact JWT strings may not contain whitespace.";
                    throw new MalformedJwtException((String)charSequence);
                }
                if (c == '.') {
                    charSequence = Strings.clean(stringBuilder);
                    String string6 = charSequence != null ? charSequence.toString() : "";
                    switch (n) {
                        case 0: {
                            string = string6;
                            break;
                        }
                        case 1: {
                            string2 = string6;
                            string3 = string6;
                            break;
                        }
                        case 2: {
                            string2 = "";
                            string4 = string6;
                            break;
                        }
                        case 3: {
                            string2 = string6;
                        }
                    }
                    ++n;
                    stringBuilder.setLength(0);
                    continue;
                }
                stringBuilder.append(c);
            }
        }
        if (n != 2 && n != 4) {
            String string7 = DELIM_ERR_MSG_PREFIX + n;
            throw new MalformedJwtException(string7);
        }
        if (stringBuilder.length() > 0) {
            string5 = stringBuilder.toString();
        }
        if (n == 2) {
            return (T)new DefaultTokenizedJwt(string, string2, string5);
        }
        return (T)new DefaultTokenizedJwe(string, string2, string5, string3, string4);
    }
}

