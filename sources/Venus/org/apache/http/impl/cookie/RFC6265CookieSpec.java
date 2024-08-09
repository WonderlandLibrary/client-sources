/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.cookie;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.FormattedHeader;
import org.apache.http.Header;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.cookie.CommonCookieAttributeHandler;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieAttributeHandler;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookiePriorityComparator;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BufferedHeader;
import org.apache.http.message.ParserCursor;
import org.apache.http.message.TokenParser;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;

@Contract(threading=ThreadingBehavior.SAFE)
public class RFC6265CookieSpec
implements CookieSpec {
    private static final char PARAM_DELIMITER = ';';
    private static final char COMMA_CHAR = ',';
    private static final char EQUAL_CHAR = '=';
    private static final char DQUOTE_CHAR = '\"';
    private static final char ESCAPE_CHAR = '\\';
    private static final BitSet TOKEN_DELIMS = TokenParser.INIT_BITSET(61, 59);
    private static final BitSet VALUE_DELIMS = TokenParser.INIT_BITSET(59);
    private static final BitSet SPECIAL_CHARS = TokenParser.INIT_BITSET(32, 34, 44, 59, 92);
    private final CookieAttributeHandler[] attribHandlers;
    private final Map<String, CookieAttributeHandler> attribHandlerMap;
    private final TokenParser tokenParser;

    protected RFC6265CookieSpec(CommonCookieAttributeHandler ... commonCookieAttributeHandlerArray) {
        this.attribHandlers = (CookieAttributeHandler[])commonCookieAttributeHandlerArray.clone();
        this.attribHandlerMap = new ConcurrentHashMap<String, CookieAttributeHandler>(commonCookieAttributeHandlerArray.length);
        for (CommonCookieAttributeHandler commonCookieAttributeHandler : commonCookieAttributeHandlerArray) {
            this.attribHandlerMap.put(commonCookieAttributeHandler.getAttributeName().toLowerCase(Locale.ROOT), commonCookieAttributeHandler);
        }
        this.tokenParser = TokenParser.INSTANCE;
    }

    static String getDefaultPath(CookieOrigin cookieOrigin) {
        String string = cookieOrigin.getPath();
        int n = string.lastIndexOf(47);
        if (n >= 0) {
            if (n == 0) {
                n = 1;
            }
            string = string.substring(0, n);
        }
        return string;
    }

    static String getDefaultDomain(CookieOrigin cookieOrigin) {
        return cookieOrigin.getHost();
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public final List<Cookie> parse(Header header, CookieOrigin cookieOrigin) throws MalformedCookieException {
        String string;
        ParserCursor parserCursor;
        CharArrayBuffer charArrayBuffer;
        Args.notNull(header, "Header");
        Args.notNull(cookieOrigin, "Cookie origin");
        if (!header.getName().equalsIgnoreCase("Set-Cookie")) {
            throw new MalformedCookieException("Unrecognized cookie header: '" + header.toString() + "'");
        }
        if (header instanceof FormattedHeader) {
            charArrayBuffer = ((FormattedHeader)header).getBuffer();
            parserCursor = new ParserCursor(((FormattedHeader)header).getValuePos(), charArrayBuffer.length());
        } else {
            string = header.getValue();
            if (string == null) {
                throw new MalformedCookieException("Header value is null");
            }
            charArrayBuffer = new CharArrayBuffer(string.length());
            charArrayBuffer.append(string);
            parserCursor = new ParserCursor(0, charArrayBuffer.length());
        }
        string = this.tokenParser.parseToken(charArrayBuffer, parserCursor, TOKEN_DELIMS);
        if (string.isEmpty()) {
            return Collections.emptyList();
        }
        if (parserCursor.atEnd()) {
            return Collections.emptyList();
        }
        char c = charArrayBuffer.charAt(parserCursor.getPos());
        parserCursor.updatePos(parserCursor.getPos() + 1);
        if (c != '=') {
            throw new MalformedCookieException("Cookie value is invalid: '" + header.toString() + "'");
        }
        String string2 = this.tokenParser.parseValue(charArrayBuffer, parserCursor, VALUE_DELIMS);
        if (!parserCursor.atEnd()) {
            parserCursor.updatePos(parserCursor.getPos() + 1);
        }
        BasicClientCookie basicClientCookie = new BasicClientCookie(string, string2);
        basicClientCookie.setPath(RFC6265CookieSpec.getDefaultPath(cookieOrigin));
        basicClientCookie.setDomain(RFC6265CookieSpec.getDefaultDomain(cookieOrigin));
        basicClientCookie.setCreationDate(new Date());
        LinkedHashMap<Object, void> linkedHashMap = new LinkedHashMap<Object, void>();
        while (!parserCursor.atEnd()) {
            void object;
            String string3 = this.tokenParser.parseToken(charArrayBuffer, parserCursor, TOKEN_DELIMS).toLowerCase(Locale.ROOT);
            Object var11_12 = null;
            if (!parserCursor.atEnd()) {
                char c2 = charArrayBuffer.charAt(parserCursor.getPos());
                parserCursor.updatePos(parserCursor.getPos() + 1);
                if (c2 == '=') {
                    String string4 = this.tokenParser.parseToken(charArrayBuffer, parserCursor, VALUE_DELIMS);
                    if (!parserCursor.atEnd()) {
                        parserCursor.updatePos(parserCursor.getPos() + 1);
                    }
                }
            }
            basicClientCookie.setAttribute(string3, (String)object);
            linkedHashMap.put(string3, object);
        }
        if (linkedHashMap.containsKey("max-age")) {
            linkedHashMap.remove("expires");
        }
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            String string5 = (String)entry.getKey();
            String string6 = (String)entry.getValue();
            CookieAttributeHandler cookieAttributeHandler = this.attribHandlerMap.get(string5);
            if (cookieAttributeHandler == null) continue;
            cookieAttributeHandler.parse(basicClientCookie, string6);
        }
        return Collections.singletonList(basicClientCookie);
    }

    @Override
    public final void validate(Cookie cookie, CookieOrigin cookieOrigin) throws MalformedCookieException {
        Args.notNull(cookie, "Cookie");
        Args.notNull(cookieOrigin, "Cookie origin");
        for (CookieAttributeHandler cookieAttributeHandler : this.attribHandlers) {
            cookieAttributeHandler.validate(cookie, cookieOrigin);
        }
    }

    @Override
    public final boolean match(Cookie cookie, CookieOrigin cookieOrigin) {
        Args.notNull(cookie, "Cookie");
        Args.notNull(cookieOrigin, "Cookie origin");
        for (CookieAttributeHandler cookieAttributeHandler : this.attribHandlers) {
            if (cookieAttributeHandler.match(cookie, cookieOrigin)) continue;
            return true;
        }
        return false;
    }

    @Override
    public List<Header> formatCookies(List<Cookie> list) {
        List<Cookie> list2;
        Args.notEmpty(list, "List of cookies");
        if (list.size() > 1) {
            list2 = new ArrayList<Cookie>(list);
            Collections.sort(list2, CookiePriorityComparator.INSTANCE);
        } else {
            list2 = list;
        }
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(20 * list2.size());
        charArrayBuffer.append("Cookie");
        charArrayBuffer.append(": ");
        for (int i = 0; i < list2.size(); ++i) {
            Cookie cookie = list2.get(i);
            if (i > 0) {
                charArrayBuffer.append(';');
                charArrayBuffer.append(' ');
            }
            charArrayBuffer.append(cookie.getName());
            String string = cookie.getValue();
            if (string == null) continue;
            charArrayBuffer.append('=');
            if (this.containsSpecialChar(string)) {
                charArrayBuffer.append('\"');
                for (int j = 0; j < string.length(); ++j) {
                    char c = string.charAt(j);
                    if (c == '\"' || c == '\\') {
                        charArrayBuffer.append('\\');
                    }
                    charArrayBuffer.append(c);
                }
                charArrayBuffer.append('\"');
                continue;
            }
            charArrayBuffer.append(string);
        }
        ArrayList<Header> arrayList = new ArrayList<Header>(1);
        arrayList.add(new BufferedHeader(charArrayBuffer));
        return arrayList;
    }

    boolean containsSpecialChar(CharSequence charSequence) {
        return this.containsChars(charSequence, SPECIAL_CHARS);
    }

    boolean containsChars(CharSequence charSequence, BitSet bitSet) {
        for (int i = 0; i < charSequence.length(); ++i) {
            char c = charSequence.charAt(i);
            if (!bitSet.get(c)) continue;
            return false;
        }
        return true;
    }

    @Override
    public final int getVersion() {
        return 1;
    }

    @Override
    public final Header getVersionHeader() {
        return null;
    }
}

