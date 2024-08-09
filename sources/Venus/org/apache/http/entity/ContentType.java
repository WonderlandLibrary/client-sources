/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.entity;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.message.BasicHeaderValueFormatter;
import org.apache.http.message.BasicHeaderValueParser;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.message.ParserCursor;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.TextUtils;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public final class ContentType
implements Serializable {
    private static final long serialVersionUID = -7768694718232371896L;
    public static final ContentType APPLICATION_ATOM_XML = ContentType.create("application/atom+xml", Consts.ISO_8859_1);
    public static final ContentType APPLICATION_FORM_URLENCODED = ContentType.create("application/x-www-form-urlencoded", Consts.ISO_8859_1);
    public static final ContentType APPLICATION_JSON = ContentType.create("application/json", Consts.UTF_8);
    public static final ContentType APPLICATION_OCTET_STREAM = ContentType.create("application/octet-stream", (Charset)null);
    public static final ContentType APPLICATION_SOAP_XML = ContentType.create("application/soap+xml", Consts.UTF_8);
    public static final ContentType APPLICATION_SVG_XML = ContentType.create("application/svg+xml", Consts.ISO_8859_1);
    public static final ContentType APPLICATION_XHTML_XML = ContentType.create("application/xhtml+xml", Consts.ISO_8859_1);
    public static final ContentType APPLICATION_XML = ContentType.create("application/xml", Consts.ISO_8859_1);
    public static final ContentType IMAGE_BMP = ContentType.create("image/bmp");
    public static final ContentType IMAGE_GIF = ContentType.create("image/gif");
    public static final ContentType IMAGE_JPEG = ContentType.create("image/jpeg");
    public static final ContentType IMAGE_PNG = ContentType.create("image/png");
    public static final ContentType IMAGE_SVG = ContentType.create("image/svg+xml");
    public static final ContentType IMAGE_TIFF = ContentType.create("image/tiff");
    public static final ContentType IMAGE_WEBP = ContentType.create("image/webp");
    public static final ContentType MULTIPART_FORM_DATA = ContentType.create("multipart/form-data", Consts.ISO_8859_1);
    public static final ContentType TEXT_HTML = ContentType.create("text/html", Consts.ISO_8859_1);
    public static final ContentType TEXT_PLAIN = ContentType.create("text/plain", Consts.ISO_8859_1);
    public static final ContentType TEXT_XML = ContentType.create("text/xml", Consts.ISO_8859_1);
    public static final ContentType WILDCARD = ContentType.create("*/*", (Charset)null);
    private static final Map<String, ContentType> CONTENT_TYPE_MAP;
    public static final ContentType DEFAULT_TEXT;
    public static final ContentType DEFAULT_BINARY;
    private final String mimeType;
    private final Charset charset;
    private final NameValuePair[] params;

    ContentType(String string, Charset charset) {
        this.mimeType = string;
        this.charset = charset;
        this.params = null;
    }

    ContentType(String string, Charset charset, NameValuePair[] nameValuePairArray) {
        this.mimeType = string;
        this.charset = charset;
        this.params = nameValuePairArray;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public Charset getCharset() {
        return this.charset;
    }

    public String getParameter(String string) {
        Args.notEmpty(string, "Parameter name");
        if (this.params == null) {
            return null;
        }
        for (NameValuePair nameValuePair : this.params) {
            if (!nameValuePair.getName().equalsIgnoreCase(string)) continue;
            return nameValuePair.getValue();
        }
        return null;
    }

    public String toString() {
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(64);
        charArrayBuffer.append(this.mimeType);
        if (this.params != null) {
            charArrayBuffer.append("; ");
            BasicHeaderValueFormatter.INSTANCE.formatParameters(charArrayBuffer, this.params, true);
        } else if (this.charset != null) {
            charArrayBuffer.append("; charset=");
            charArrayBuffer.append(this.charset.name());
        }
        return charArrayBuffer.toString();
    }

    private static boolean valid(String string) {
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (c != '\"' && c != ',' && c != ';') continue;
            return true;
        }
        return false;
    }

    public static ContentType create(String string, Charset charset) {
        String string2 = Args.notBlank(string, "MIME type").toLowerCase(Locale.ROOT);
        Args.check(ContentType.valid(string2), "MIME type may not contain reserved characters");
        return new ContentType(string2, charset);
    }

    public static ContentType create(String string) {
        return ContentType.create(string, (Charset)null);
    }

    public static ContentType create(String string, String string2) throws UnsupportedCharsetException {
        return ContentType.create(string, !TextUtils.isBlank(string2) ? Charset.forName(string2) : null);
    }

    private static ContentType create(HeaderElement headerElement, boolean bl) {
        return ContentType.create(headerElement.getName(), headerElement.getParameters(), bl);
    }

    private static ContentType create(String string, NameValuePair[] nameValuePairArray, boolean bl) {
        Charset charset = null;
        for (NameValuePair nameValuePair : nameValuePairArray) {
            if (!nameValuePair.getName().equalsIgnoreCase("charset")) continue;
            String string2 = nameValuePair.getValue();
            if (TextUtils.isBlank(string2)) break;
            try {
                charset = Charset.forName(string2);
                break;
            } catch (UnsupportedCharsetException unsupportedCharsetException) {
                if (!bl) break;
                throw unsupportedCharsetException;
            }
        }
        return new ContentType(string, charset, nameValuePairArray != null && nameValuePairArray.length > 0 ? nameValuePairArray : null);
    }

    public static ContentType create(String string, NameValuePair ... nameValuePairArray) throws UnsupportedCharsetException {
        String string2 = Args.notBlank(string, "MIME type").toLowerCase(Locale.ROOT);
        Args.check(ContentType.valid(string2), "MIME type may not contain reserved characters");
        return ContentType.create(string, nameValuePairArray, true);
    }

    public static ContentType parse(String string) throws ParseException, UnsupportedCharsetException {
        Args.notNull(string, "Content type");
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(string.length());
        charArrayBuffer.append(string);
        ParserCursor parserCursor = new ParserCursor(0, string.length());
        HeaderElement[] headerElementArray = BasicHeaderValueParser.INSTANCE.parseElements(charArrayBuffer, parserCursor);
        if (headerElementArray.length > 0) {
            return ContentType.create(headerElementArray[0], true);
        }
        throw new ParseException("Invalid content type: " + string);
    }

    public static ContentType get(HttpEntity httpEntity) throws ParseException, UnsupportedCharsetException {
        HeaderElement[] headerElementArray;
        if (httpEntity == null) {
            return null;
        }
        Header header = httpEntity.getContentType();
        if (header != null && (headerElementArray = header.getElements()).length > 0) {
            return ContentType.create(headerElementArray[0], true);
        }
        return null;
    }

    public static ContentType getLenient(HttpEntity httpEntity) {
        if (httpEntity == null) {
            return null;
        }
        Header header = httpEntity.getContentType();
        if (header != null) {
            try {
                HeaderElement[] headerElementArray = header.getElements();
                if (headerElementArray.length > 0) {
                    return ContentType.create(headerElementArray[0], false);
                }
            } catch (ParseException parseException) {
                // empty catch block
            }
        }
        return null;
    }

    public static ContentType getOrDefault(HttpEntity httpEntity) throws ParseException, UnsupportedCharsetException {
        ContentType contentType = ContentType.get(httpEntity);
        return contentType != null ? contentType : DEFAULT_TEXT;
    }

    public static ContentType getLenientOrDefault(HttpEntity httpEntity) throws ParseException, UnsupportedCharsetException {
        ContentType contentType = ContentType.get(httpEntity);
        return contentType != null ? contentType : DEFAULT_TEXT;
    }

    public static ContentType getByMimeType(String string) {
        if (string == null) {
            return null;
        }
        return CONTENT_TYPE_MAP.get(string);
    }

    public ContentType withCharset(Charset charset) {
        return ContentType.create(this.getMimeType(), charset);
    }

    public ContentType withCharset(String string) {
        return ContentType.create(this.getMimeType(), string);
    }

    public ContentType withParameters(NameValuePair ... nameValuePairArray) throws UnsupportedCharsetException {
        if (nameValuePairArray.length == 0) {
            return this;
        }
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<String, String>();
        if (this.params != null) {
            for (NameValuePair nameValuePair : this.params) {
                linkedHashMap.put(nameValuePair.getName(), nameValuePair.getValue());
            }
        }
        for (NameValuePair nameValuePair : nameValuePairArray) {
            linkedHashMap.put(nameValuePair.getName(), nameValuePair.getValue());
        }
        ArrayList arrayList = new ArrayList(linkedHashMap.size() + 1);
        if (this.charset != null && !linkedHashMap.containsKey("charset")) {
            arrayList.add(new BasicNameValuePair("charset", this.charset.name()));
        }
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            arrayList.add(new BasicNameValuePair((String)entry.getKey(), (String)entry.getValue()));
        }
        return ContentType.create(this.getMimeType(), arrayList.toArray(new NameValuePair[arrayList.size()]), true);
    }

    static {
        ContentType[] contentTypeArray = new ContentType[]{APPLICATION_ATOM_XML, APPLICATION_FORM_URLENCODED, APPLICATION_JSON, APPLICATION_SVG_XML, APPLICATION_XHTML_XML, APPLICATION_XML, IMAGE_BMP, IMAGE_GIF, IMAGE_JPEG, IMAGE_PNG, IMAGE_SVG, IMAGE_TIFF, IMAGE_WEBP, MULTIPART_FORM_DATA, TEXT_HTML, TEXT_PLAIN, TEXT_XML};
        HashMap<String, ContentType> hashMap = new HashMap<String, ContentType>();
        for (ContentType contentType : contentTypeArray) {
            hashMap.put(contentType.getMimeType(), contentType);
        }
        CONTENT_TYPE_MAP = Collections.unmodifiableMap(hashMap);
        DEFAULT_TEXT = TEXT_PLAIN;
        DEFAULT_BINARY = APPLICATION_OCTET_STREAM;
    }
}

