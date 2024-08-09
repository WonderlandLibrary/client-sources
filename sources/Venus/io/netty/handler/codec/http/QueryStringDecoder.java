/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http;

import io.netty.handler.codec.http.HttpConstants;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class QueryStringDecoder {
    private static final int DEFAULT_MAX_PARAMS = 1024;
    private final Charset charset;
    private final String uri;
    private final int maxParams;
    private int pathEndIdx;
    private String path;
    private Map<String, List<String>> params;

    public QueryStringDecoder(String string) {
        this(string, HttpConstants.DEFAULT_CHARSET);
    }

    public QueryStringDecoder(String string, boolean bl) {
        this(string, HttpConstants.DEFAULT_CHARSET, bl);
    }

    public QueryStringDecoder(String string, Charset charset) {
        this(string, charset, true);
    }

    public QueryStringDecoder(String string, Charset charset, boolean bl) {
        this(string, charset, bl, 1024);
    }

    public QueryStringDecoder(String string, Charset charset, boolean bl, int n) {
        this.uri = ObjectUtil.checkNotNull(string, "uri");
        this.charset = ObjectUtil.checkNotNull(charset, "charset");
        this.maxParams = ObjectUtil.checkPositive(n, "maxParams");
        this.pathEndIdx = bl ? -1 : 0;
    }

    public QueryStringDecoder(URI uRI) {
        this(uRI, HttpConstants.DEFAULT_CHARSET);
    }

    public QueryStringDecoder(URI uRI, Charset charset) {
        this(uRI, charset, 1024);
    }

    public QueryStringDecoder(URI uRI, Charset charset, int n) {
        String string;
        String string2 = uRI.getRawPath();
        if (string2 == null) {
            string2 = "";
        }
        this.uri = (string = uRI.getRawQuery()) == null ? string2 : string2 + '?' + string;
        this.charset = ObjectUtil.checkNotNull(charset, "charset");
        this.maxParams = ObjectUtil.checkPositive(n, "maxParams");
        this.pathEndIdx = string2.length();
    }

    public String toString() {
        return this.uri();
    }

    public String uri() {
        return this.uri;
    }

    public String path() {
        if (this.path == null) {
            this.path = QueryStringDecoder.decodeComponent(this.uri, 0, this.pathEndIdx(), this.charset, true);
        }
        return this.path;
    }

    public Map<String, List<String>> parameters() {
        if (this.params == null) {
            this.params = QueryStringDecoder.decodeParams(this.uri, this.pathEndIdx(), this.charset, this.maxParams);
        }
        return this.params;
    }

    public String rawPath() {
        return this.uri.substring(0, this.pathEndIdx());
    }

    public String rawQuery() {
        int n = this.pathEndIdx() + 1;
        return n < this.uri.length() ? this.uri.substring(n) : "";
    }

    private int pathEndIdx() {
        if (this.pathEndIdx == -1) {
            this.pathEndIdx = QueryStringDecoder.findPathEndIndex(this.uri);
        }
        return this.pathEndIdx;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static Map<String, List<String>> decodeParams(String string, int n, Charset charset, int n2) {
        int n3;
        int n4 = string.length();
        if (n >= n4) {
            return Collections.emptyMap();
        }
        if (string.charAt(n) == '?') {
            ++n;
        }
        LinkedHashMap<String, List<String>> linkedHashMap = new LinkedHashMap<String, List<String>>();
        int n5 = n;
        int n6 = -1;
        block5: for (n3 = n; n3 < n4; ++n3) {
            switch (string.charAt(n3)) {
                case '=': {
                    if (n5 == n3) {
                        n5 = n3 + 1;
                        break;
                    }
                    if (n6 >= n5) break;
                    n6 = n3 + 1;
                    break;
                }
                case '&': 
                case ';': {
                    if (QueryStringDecoder.addParam(string, n5, n6, n3, linkedHashMap, charset) && --n2 == 0) {
                        return linkedHashMap;
                    }
                    n5 = n3 + 1;
                    break;
                }
                case '#': {
                    break block5;
                }
            }
        }
        QueryStringDecoder.addParam(string, n5, n6, n3, linkedHashMap, charset);
        return linkedHashMap;
    }

    private static boolean addParam(String string, int n, int n2, int n3, Map<String, List<String>> map, Charset charset) {
        if (n >= n3) {
            return true;
        }
        if (n2 <= n) {
            n2 = n3 + 1;
        }
        String string2 = QueryStringDecoder.decodeComponent(string, n, n2 - 1, charset, false);
        String string3 = QueryStringDecoder.decodeComponent(string, n2, n3, charset, false);
        List<String> list = map.get(string2);
        if (list == null) {
            list = new ArrayList<String>(1);
            map.put(string2, list);
        }
        list.add(string3);
        return false;
    }

    public static String decodeComponent(String string) {
        return QueryStringDecoder.decodeComponent(string, HttpConstants.DEFAULT_CHARSET);
    }

    public static String decodeComponent(String string, Charset charset) {
        if (string == null) {
            return "";
        }
        return QueryStringDecoder.decodeComponent(string, 0, string.length(), charset, false);
    }

    private static String decodeComponent(String string, int n, int n2, Charset charset, boolean bl) {
        int n3;
        int n4 = n2 - n;
        if (n4 <= 0) {
            return "";
        }
        int n5 = -1;
        for (int i = n; i < n2; ++i) {
            n3 = string.charAt(i);
            if (n3 != 37 && (n3 != 43 || bl)) continue;
            n5 = i;
            break;
        }
        if (n5 == -1) {
            return string.substring(n, n2);
        }
        CharsetDecoder charsetDecoder = CharsetUtil.decoder(charset);
        n3 = (n2 - n5) / 3;
        ByteBuffer byteBuffer = ByteBuffer.allocate(n3);
        CharBuffer charBuffer = CharBuffer.allocate(n3);
        StringBuilder stringBuilder = new StringBuilder(n4);
        stringBuilder.append(string, n, n5);
        for (int i = n5; i < n2; ++i) {
            char c = string.charAt(i);
            if (c != '%') {
                stringBuilder.append(c != '+' || bl ? c : (char)' ');
                continue;
            }
            byteBuffer.clear();
            do {
                if (i + 3 > n2) {
                    throw new IllegalArgumentException("unterminated escape sequence at index " + i + " of: " + string);
                }
                byteBuffer.put(StringUtil.decodeHexByte(string, i + 1));
            } while ((i += 3) < n2 && string.charAt(i) == '%');
            --i;
            byteBuffer.flip();
            charBuffer.clear();
            CoderResult coderResult = charsetDecoder.reset().decode(byteBuffer, charBuffer, false);
            try {
                if (!coderResult.isUnderflow()) {
                    coderResult.throwException();
                }
                if (!(coderResult = charsetDecoder.flush(charBuffer)).isUnderflow()) {
                    coderResult.throwException();
                }
            } catch (CharacterCodingException characterCodingException) {
                throw new IllegalStateException(characterCodingException);
            }
            stringBuilder.append(charBuffer.flip());
        }
        return stringBuilder.toString();
    }

    private static int findPathEndIndex(String string) {
        int n = string.length();
        for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            if (c != '?' && c != '#') continue;
            return i;
        }
        return n;
    }
}

