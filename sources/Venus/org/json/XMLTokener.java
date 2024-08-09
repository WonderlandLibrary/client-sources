/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.json;

import java.io.Reader;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONTokener;
import org.json.XML;

public class XMLTokener
extends JSONTokener {
    public static final HashMap<String, Character> entity = new HashMap(8);

    public XMLTokener(Reader reader) {
        super(reader);
    }

    public XMLTokener(String string) {
        super(string);
    }

    public String nextCDATA() throws JSONException {
        StringBuilder stringBuilder = new StringBuilder();
        while (this.more()) {
            char c = this.next();
            stringBuilder.append(c);
            int n = stringBuilder.length() - 3;
            if (n < 0 || stringBuilder.charAt(n) != ']' || stringBuilder.charAt(n + 1) != ']' || stringBuilder.charAt(n + 2) != '>') continue;
            stringBuilder.setLength(n);
            return stringBuilder.toString();
        }
        throw this.syntaxError("Unclosed CDATA");
    }

    public Object nextContent() throws JSONException {
        char c;
        while (Character.isWhitespace(c = this.next())) {
        }
        if (c == '\u0000') {
            return null;
        }
        if (c == '<') {
            return XML.LT;
        }
        StringBuilder stringBuilder = new StringBuilder();
        while (c != '\u0000') {
            if (c == '<') {
                this.back();
                return stringBuilder.toString().trim();
            }
            if (c == '&') {
                stringBuilder.append(this.nextEntity(c));
            } else {
                stringBuilder.append(c);
            }
            c = this.next();
        }
        return stringBuilder.toString().trim();
    }

    public Object nextEntity(char c) throws JSONException {
        char c2;
        StringBuilder stringBuilder = new StringBuilder();
        while (Character.isLetterOrDigit(c2 = this.next()) || c2 == '#') {
            stringBuilder.append(Character.toLowerCase(c2));
        }
        if (c2 != ';') {
            throw this.syntaxError("Missing ';' in XML entity: &" + stringBuilder);
        }
        String string = stringBuilder.toString();
        return XMLTokener.unescapeEntity(string);
    }

    static String unescapeEntity(String string) {
        if (string == null || string.isEmpty()) {
            return "";
        }
        if (string.charAt(0) == '#') {
            int n = string.charAt(1) == 'x' || string.charAt(1) == 'X' ? Integer.parseInt(string.substring(2), 16) : Integer.parseInt(string.substring(1));
            return new String(new int[]{n}, 0, 1);
        }
        Character c = entity.get(string);
        if (c == null) {
            return '&' + string + ';';
        }
        return c.toString();
    }

    public Object nextMeta() throws JSONException {
        char c;
        while (Character.isWhitespace(c = this.next())) {
        }
        switch (c) {
            case '\u0000': {
                throw this.syntaxError("Misshaped meta tag");
            }
            case '<': {
                return XML.LT;
            }
            case '>': {
                return XML.GT;
            }
            case '/': {
                return XML.SLASH;
            }
            case '=': {
                return XML.EQ;
            }
            case '!': {
                return XML.BANG;
            }
            case '?': {
                return XML.QUEST;
            }
            case '\"': 
            case '\'': {
                char c2 = c;
                do {
                    if ((c = this.next()) != '\u0000') continue;
                    throw this.syntaxError("Unterminated string");
                } while (c != c2);
                return Boolean.TRUE;
            }
        }
        while (!Character.isWhitespace(c = this.next())) {
            switch (c) {
                case '\u0000': {
                    throw this.syntaxError("Unterminated string");
                }
                case '!': 
                case '\"': 
                case '\'': 
                case '/': 
                case '<': 
                case '=': 
                case '>': 
                case '?': {
                    this.back();
                    return Boolean.TRUE;
                }
            }
        }
        return Boolean.TRUE;
    }

    public Object nextToken() throws JSONException {
        char c;
        while (Character.isWhitespace(c = this.next())) {
        }
        switch (c) {
            case '\u0000': {
                throw this.syntaxError("Misshaped element");
            }
            case '<': {
                throw this.syntaxError("Misplaced '<'");
            }
            case '>': {
                return XML.GT;
            }
            case '/': {
                return XML.SLASH;
            }
            case '=': {
                return XML.EQ;
            }
            case '!': {
                return XML.BANG;
            }
            case '?': {
                return XML.QUEST;
            }
            case '\"': 
            case '\'': {
                char c2 = c;
                StringBuilder stringBuilder = new StringBuilder();
                while (true) {
                    if ((c = this.next()) == '\u0000') {
                        throw this.syntaxError("Unterminated string");
                    }
                    if (c == c2) {
                        return stringBuilder.toString();
                    }
                    if (c == '&') {
                        stringBuilder.append(this.nextEntity(c));
                        continue;
                    }
                    stringBuilder.append(c);
                }
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            stringBuilder.append(c);
            c = this.next();
            if (Character.isWhitespace(c)) {
                return stringBuilder.toString();
            }
            switch (c) {
                case '\u0000': {
                    return stringBuilder.toString();
                }
                case '!': 
                case '/': 
                case '=': 
                case '>': 
                case '?': 
                case '[': 
                case ']': {
                    this.back();
                    return stringBuilder.toString();
                }
                case '\"': 
                case '\'': 
                case '<': {
                    throw this.syntaxError("Bad character in a name");
                }
            }
        }
    }

    public void skipPast(String string) {
        char c;
        int n;
        int n2 = 0;
        int n3 = string.length();
        char[] cArray = new char[n3];
        for (n = 0; n < n3; ++n) {
            c = this.next();
            if (c == '\u0000') {
                return;
            }
            cArray[n] = c;
        }
        while (true) {
            int n4 = n2;
            boolean bl = true;
            for (n = 0; n < n3; ++n) {
                if (cArray[n4] != string.charAt(n)) {
                    bl = false;
                    break;
                }
                if (++n4 < n3) continue;
                n4 -= n3;
            }
            if (bl) {
                return;
            }
            c = this.next();
            if (c == '\u0000') {
                return;
            }
            cArray[n2] = c;
            if (++n2 < n3) continue;
            n2 -= n3;
        }
    }

    static {
        entity.put("amp", XML.AMP);
        entity.put("apos", XML.APOS);
        entity.put("gt", XML.GT);
        entity.put("lt", XML.LT);
        entity.put("quot", XML.QUOT);
    }
}

