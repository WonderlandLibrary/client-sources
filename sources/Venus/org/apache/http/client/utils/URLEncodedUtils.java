/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.client.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.message.ParserCursor;
import org.apache.http.message.TokenParser;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;

public class URLEncodedUtils {
    public static final String CONTENT_TYPE = "application/x-www-form-urlencoded";
    private static final char QP_SEP_A = '&';
    private static final char QP_SEP_S = ';';
    private static final String NAME_VALUE_SEPARATOR = "=";
    private static final char PATH_SEPARATOR = '/';
    private static final BitSet PATH_SEPARATORS;
    private static final BitSet UNRESERVED;
    private static final BitSet PUNCT;
    private static final BitSet USERINFO;
    private static final BitSet PATHSAFE;
    private static final BitSet URIC;
    private static final BitSet RESERVED;
    private static final BitSet URLENCODER;
    private static final BitSet PATH_SPECIAL;
    private static final int RADIX = 16;

    @Deprecated
    public static List<NameValuePair> parse(URI uRI, String string) {
        return URLEncodedUtils.parse(uRI, string != null ? Charset.forName(string) : null);
    }

    public static List<NameValuePair> parse(URI uRI, Charset charset) {
        Args.notNull(uRI, "URI");
        String string = uRI.getRawQuery();
        if (string != null && !string.isEmpty()) {
            return URLEncodedUtils.parse(string, charset);
        }
        return URLEncodedUtils.createEmptyList();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static List<NameValuePair> parse(HttpEntity httpEntity) throws IOException {
        CharArrayBuffer charArrayBuffer;
        Args.notNull(httpEntity, "HTTP entity");
        ContentType contentType = ContentType.get(httpEntity);
        if (contentType == null || !contentType.getMimeType().equalsIgnoreCase(CONTENT_TYPE)) {
            return URLEncodedUtils.createEmptyList();
        }
        long l = httpEntity.getContentLength();
        Args.check(l <= Integer.MAX_VALUE, "HTTP entity is too large");
        Charset charset = contentType.getCharset() != null ? contentType.getCharset() : HTTP.DEF_CONTENT_CHARSET;
        InputStream inputStream = httpEntity.getContent();
        if (inputStream == null) {
            return URLEncodedUtils.createEmptyList();
        }
        try {
            int n;
            charArrayBuffer = new CharArrayBuffer(l > 0L ? (int)l : 1024);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, charset);
            char[] cArray = new char[1024];
            while ((n = inputStreamReader.read(cArray)) != -1) {
                charArrayBuffer.append(cArray, 0, n);
            }
        } finally {
            inputStream.close();
        }
        if (charArrayBuffer.isEmpty()) {
            return URLEncodedUtils.createEmptyList();
        }
        return URLEncodedUtils.parse(charArrayBuffer, charset, '&');
    }

    public static boolean isEncoded(HttpEntity httpEntity) {
        HeaderElement[] headerElementArray;
        Args.notNull(httpEntity, "HTTP entity");
        Header header = httpEntity.getContentType();
        if (header != null && (headerElementArray = header.getElements()).length > 0) {
            String string = headerElementArray[0].getName();
            return string.equalsIgnoreCase(CONTENT_TYPE);
        }
        return true;
    }

    @Deprecated
    public static void parse(List<NameValuePair> list, Scanner scanner, String string) {
        URLEncodedUtils.parse(list, scanner, "[&;]", string);
    }

    @Deprecated
    public static void parse(List<NameValuePair> list, Scanner scanner, String string, String string2) {
        scanner.useDelimiter(string);
        while (scanner.hasNext()) {
            String string3;
            String string4;
            String string5 = scanner.next();
            int n = string5.indexOf(NAME_VALUE_SEPARATOR);
            if (n != -1) {
                string4 = URLEncodedUtils.decodeFormFields(string5.substring(0, n).trim(), string2);
                string3 = URLEncodedUtils.decodeFormFields(string5.substring(n + 1).trim(), string2);
            } else {
                string4 = URLEncodedUtils.decodeFormFields(string5.trim(), string2);
                string3 = null;
            }
            list.add(new BasicNameValuePair(string4, string3));
        }
    }

    public static List<NameValuePair> parse(String string, Charset charset) {
        if (string == null) {
            return URLEncodedUtils.createEmptyList();
        }
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(string.length());
        charArrayBuffer.append(string);
        return URLEncodedUtils.parse(charArrayBuffer, charset, '&', ';');
    }

    public static List<NameValuePair> parse(String string, Charset charset, char ... cArray) {
        if (string == null) {
            return URLEncodedUtils.createEmptyList();
        }
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(string.length());
        charArrayBuffer.append(string);
        return URLEncodedUtils.parse(charArrayBuffer, charset, cArray);
    }

    public static List<NameValuePair> parse(CharArrayBuffer charArrayBuffer, Charset charset, char ... cArray) {
        Args.notNull(charArrayBuffer, "Char array buffer");
        TokenParser tokenParser = TokenParser.INSTANCE;
        BitSet bitSet = new BitSet();
        for (char c : cArray) {
            bitSet.set(c);
        }
        Object object = new ParserCursor(0, charArrayBuffer.length());
        ArrayList<NameValuePair> arrayList = new ArrayList<NameValuePair>();
        while (!((ParserCursor)object).atEnd()) {
            bitSet.set(61);
            String string = tokenParser.parseToken(charArrayBuffer, (ParserCursor)object, bitSet);
            String string2 = null;
            if (!((ParserCursor)object).atEnd()) {
                char c = charArrayBuffer.charAt(((ParserCursor)object).getPos());
                ((ParserCursor)object).updatePos(((ParserCursor)object).getPos() + 1);
                if (c == '=') {
                    bitSet.clear(61);
                    string2 = tokenParser.parseToken(charArrayBuffer, (ParserCursor)object, bitSet);
                    if (!((ParserCursor)object).atEnd()) {
                        ((ParserCursor)object).updatePos(((ParserCursor)object).getPos() + 1);
                    }
                }
            }
            if (string.isEmpty()) continue;
            arrayList.add(new BasicNameValuePair(URLEncodedUtils.decodeFormFields(string, charset), URLEncodedUtils.decodeFormFields(string2, charset)));
        }
        return arrayList;
    }

    static List<String> splitSegments(CharSequence charSequence, BitSet bitSet) {
        ParserCursor parserCursor = new ParserCursor(0, charSequence.length());
        if (parserCursor.atEnd()) {
            return Collections.emptyList();
        }
        if (bitSet.get(charSequence.charAt(parserCursor.getPos()))) {
            parserCursor.updatePos(parserCursor.getPos() + 1);
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            if (parserCursor.atEnd()) break;
            char c = charSequence.charAt(parserCursor.getPos());
            if (bitSet.get(c)) {
                arrayList.add(stringBuilder.toString());
                stringBuilder.setLength(0);
            } else {
                stringBuilder.append(c);
            }
            parserCursor.updatePos(parserCursor.getPos() + 1);
        }
        arrayList.add(stringBuilder.toString());
        return arrayList;
    }

    static List<String> splitPathSegments(CharSequence charSequence) {
        return URLEncodedUtils.splitSegments(charSequence, PATH_SEPARATORS);
    }

    public static List<String> parsePathSegments(CharSequence charSequence, Charset charset) {
        Args.notNull(charSequence, "Char sequence");
        List<String> list = URLEncodedUtils.splitPathSegments(charSequence);
        for (int i = 0; i < list.size(); ++i) {
            list.set(i, URLEncodedUtils.urlDecode(list.get(i), charset != null ? charset : Consts.UTF_8, false));
        }
        return list;
    }

    public static List<String> parsePathSegments(CharSequence charSequence) {
        return URLEncodedUtils.parsePathSegments(charSequence, Consts.UTF_8);
    }

    public static String formatSegments(Iterable<String> iterable, Charset charset) {
        Args.notNull(iterable, "Segments");
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : iterable) {
            stringBuilder.append('/').append(URLEncodedUtils.urlEncode(string, charset, PATHSAFE, false));
        }
        return stringBuilder.toString();
    }

    public static String formatSegments(String ... stringArray) {
        return URLEncodedUtils.formatSegments(Arrays.asList(stringArray), Consts.UTF_8);
    }

    public static String format(List<? extends NameValuePair> list, String string) {
        return URLEncodedUtils.format(list, '&', string);
    }

    public static String format(List<? extends NameValuePair> list, char c, String string) {
        StringBuilder stringBuilder = new StringBuilder();
        for (NameValuePair nameValuePair : list) {
            String string2 = URLEncodedUtils.encodeFormFields(nameValuePair.getName(), string);
            String string3 = URLEncodedUtils.encodeFormFields(nameValuePair.getValue(), string);
            if (stringBuilder.length() > 0) {
                stringBuilder.append(c);
            }
            stringBuilder.append(string2);
            if (string3 == null) continue;
            stringBuilder.append(NAME_VALUE_SEPARATOR);
            stringBuilder.append(string3);
        }
        return stringBuilder.toString();
    }

    public static String format(Iterable<? extends NameValuePair> iterable, Charset charset) {
        return URLEncodedUtils.format(iterable, '&', charset);
    }

    public static String format(Iterable<? extends NameValuePair> iterable, char c, Charset charset) {
        Args.notNull(iterable, "Parameters");
        StringBuilder stringBuilder = new StringBuilder();
        for (NameValuePair nameValuePair : iterable) {
            String string = URLEncodedUtils.encodeFormFields(nameValuePair.getName(), charset);
            String string2 = URLEncodedUtils.encodeFormFields(nameValuePair.getValue(), charset);
            if (stringBuilder.length() > 0) {
                stringBuilder.append(c);
            }
            stringBuilder.append(string);
            if (string2 == null) continue;
            stringBuilder.append(NAME_VALUE_SEPARATOR);
            stringBuilder.append(string2);
        }
        return stringBuilder.toString();
    }

    private static List<NameValuePair> createEmptyList() {
        return new ArrayList<NameValuePair>(0);
    }

    private static String urlEncode(String string, Charset charset, BitSet bitSet, boolean bl) {
        if (string == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        ByteBuffer byteBuffer = charset.encode(string);
        while (byteBuffer.hasRemaining()) {
            int n = byteBuffer.get() & 0xFF;
            if (bitSet.get(n)) {
                stringBuilder.append((char)n);
                continue;
            }
            if (bl && n == 32) {
                stringBuilder.append('+');
                continue;
            }
            stringBuilder.append("%");
            char c = Character.toUpperCase(Character.forDigit(n >> 4 & 0xF, 16));
            char c2 = Character.toUpperCase(Character.forDigit(n & 0xF, 16));
            stringBuilder.append(c);
            stringBuilder.append(c2);
        }
        return stringBuilder.toString();
    }

    private static String urlDecode(String string, Charset charset, boolean bl) {
        if (string == null) {
            return null;
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(string.length());
        CharBuffer charBuffer = CharBuffer.wrap(string);
        while (charBuffer.hasRemaining()) {
            char c = charBuffer.get();
            if (c == '%' && charBuffer.remaining() >= 2) {
                char c2 = charBuffer.get();
                char c3 = charBuffer.get();
                int n = Character.digit(c2, 16);
                int n2 = Character.digit(c3, 16);
                if (n != -1 && n2 != -1) {
                    byteBuffer.put((byte)((n << 4) + n2));
                    continue;
                }
                byteBuffer.put((byte)37);
                byteBuffer.put((byte)c2);
                byteBuffer.put((byte)c3);
                continue;
            }
            if (bl && c == '+') {
                byteBuffer.put((byte)32);
                continue;
            }
            byteBuffer.put((byte)c);
        }
        byteBuffer.flip();
        return charset.decode(byteBuffer).toString();
    }

    private static String decodeFormFields(String string, String string2) {
        if (string == null) {
            return null;
        }
        return URLEncodedUtils.urlDecode(string, string2 != null ? Charset.forName(string2) : Consts.UTF_8, true);
    }

    private static String decodeFormFields(String string, Charset charset) {
        if (string == null) {
            return null;
        }
        return URLEncodedUtils.urlDecode(string, charset != null ? charset : Consts.UTF_8, true);
    }

    private static String encodeFormFields(String string, String string2) {
        if (string == null) {
            return null;
        }
        return URLEncodedUtils.urlEncode(string, string2 != null ? Charset.forName(string2) : Consts.UTF_8, URLENCODER, true);
    }

    private static String encodeFormFields(String string, Charset charset) {
        if (string == null) {
            return null;
        }
        return URLEncodedUtils.urlEncode(string, charset != null ? charset : Consts.UTF_8, URLENCODER, true);
    }

    static String encUserInfo(String string, Charset charset) {
        return URLEncodedUtils.urlEncode(string, charset, USERINFO, false);
    }

    static String encUric(String string, Charset charset) {
        return URLEncodedUtils.urlEncode(string, charset, URIC, false);
    }

    static String encPath(String string, Charset charset) {
        return URLEncodedUtils.urlEncode(string, charset, PATH_SPECIAL, false);
    }

    static {
        int n;
        PATH_SEPARATORS = new BitSet(256);
        PATH_SEPARATORS.set(47);
        UNRESERVED = new BitSet(256);
        PUNCT = new BitSet(256);
        USERINFO = new BitSet(256);
        PATHSAFE = new BitSet(256);
        URIC = new BitSet(256);
        RESERVED = new BitSet(256);
        URLENCODER = new BitSet(256);
        PATH_SPECIAL = new BitSet(256);
        for (n = 97; n <= 122; ++n) {
            UNRESERVED.set(n);
        }
        for (n = 65; n <= 90; ++n) {
            UNRESERVED.set(n);
        }
        for (n = 48; n <= 57; ++n) {
            UNRESERVED.set(n);
        }
        UNRESERVED.set(95);
        UNRESERVED.set(45);
        UNRESERVED.set(46);
        UNRESERVED.set(42);
        URLENCODER.or(UNRESERVED);
        UNRESERVED.set(33);
        UNRESERVED.set(126);
        UNRESERVED.set(39);
        UNRESERVED.set(40);
        UNRESERVED.set(41);
        PUNCT.set(44);
        PUNCT.set(59);
        PUNCT.set(58);
        PUNCT.set(36);
        PUNCT.set(38);
        PUNCT.set(43);
        PUNCT.set(61);
        USERINFO.or(UNRESERVED);
        USERINFO.or(PUNCT);
        PATHSAFE.or(UNRESERVED);
        PATHSAFE.set(59);
        PATHSAFE.set(58);
        PATHSAFE.set(64);
        PATHSAFE.set(38);
        PATHSAFE.set(61);
        PATHSAFE.set(43);
        PATHSAFE.set(36);
        PATHSAFE.set(44);
        PATH_SPECIAL.or(PATHSAFE);
        PATH_SPECIAL.set(47);
        RESERVED.set(59);
        RESERVED.set(47);
        RESERVED.set(63);
        RESERVED.set(58);
        RESERVED.set(64);
        RESERVED.set(38);
        RESERVED.set(61);
        RESERVED.set(43);
        RESERVED.set(36);
        RESERVED.set(44);
        RESERVED.set(91);
        RESERVED.set(93);
        URIC.or(RESERVED);
        URIC.or(UNRESERVED);
    }
}

