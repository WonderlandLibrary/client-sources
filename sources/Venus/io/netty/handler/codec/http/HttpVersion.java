/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpVersion
implements Comparable<HttpVersion> {
    private static final Pattern VERSION_PATTERN = Pattern.compile("(\\S+)/(\\d+)\\.(\\d+)");
    private static final String HTTP_1_0_STRING = "HTTP/1.0";
    private static final String HTTP_1_1_STRING = "HTTP/1.1";
    public static final HttpVersion HTTP_1_0 = new HttpVersion("HTTP", 1, 0, false, true);
    public static final HttpVersion HTTP_1_1 = new HttpVersion("HTTP", 1, 1, true, true);
    private final String protocolName;
    private final int majorVersion;
    private final int minorVersion;
    private final String text;
    private final boolean keepAliveDefault;
    private final byte[] bytes;

    public static HttpVersion valueOf(String string) {
        if (string == null) {
            throw new NullPointerException("text");
        }
        if ((string = string.trim()).isEmpty()) {
            throw new IllegalArgumentException("text is empty (possibly HTTP/0.9)");
        }
        HttpVersion httpVersion = HttpVersion.version0(string);
        if (httpVersion == null) {
            httpVersion = new HttpVersion(string, true);
        }
        return httpVersion;
    }

    private static HttpVersion version0(String string) {
        if (HTTP_1_1_STRING.equals(string)) {
            return HTTP_1_1;
        }
        if (HTTP_1_0_STRING.equals(string)) {
            return HTTP_1_0;
        }
        return null;
    }

    public HttpVersion(String string, boolean bl) {
        if (string == null) {
            throw new NullPointerException("text");
        }
        if ((string = string.trim().toUpperCase()).isEmpty()) {
            throw new IllegalArgumentException("empty text");
        }
        Matcher matcher = VERSION_PATTERN.matcher(string);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("invalid version format: " + string);
        }
        this.protocolName = matcher.group(1);
        this.majorVersion = Integer.parseInt(matcher.group(2));
        this.minorVersion = Integer.parseInt(matcher.group(3));
        this.text = this.protocolName + '/' + this.majorVersion + '.' + this.minorVersion;
        this.keepAliveDefault = bl;
        this.bytes = null;
    }

    public HttpVersion(String string, int n, int n2, boolean bl) {
        this(string, n, n2, bl, false);
    }

    private HttpVersion(String string, int n, int n2, boolean bl, boolean bl2) {
        if (string == null) {
            throw new NullPointerException("protocolName");
        }
        if ((string = string.trim().toUpperCase()).isEmpty()) {
            throw new IllegalArgumentException("empty protocolName");
        }
        for (int i = 0; i < string.length(); ++i) {
            if (!Character.isISOControl(string.charAt(i)) && !Character.isWhitespace(string.charAt(i))) continue;
            throw new IllegalArgumentException("invalid character in protocolName");
        }
        if (n < 0) {
            throw new IllegalArgumentException("negative majorVersion");
        }
        if (n2 < 0) {
            throw new IllegalArgumentException("negative minorVersion");
        }
        this.protocolName = string;
        this.majorVersion = n;
        this.minorVersion = n2;
        this.text = string + '/' + n + '.' + n2;
        this.keepAliveDefault = bl;
        this.bytes = (byte[])(bl2 ? this.text.getBytes(CharsetUtil.US_ASCII) : null);
    }

    public String protocolName() {
        return this.protocolName;
    }

    public int majorVersion() {
        return this.majorVersion;
    }

    public int minorVersion() {
        return this.minorVersion;
    }

    public String text() {
        return this.text;
    }

    public boolean isKeepAliveDefault() {
        return this.keepAliveDefault;
    }

    public String toString() {
        return this.text();
    }

    public int hashCode() {
        return (this.protocolName().hashCode() * 31 + this.majorVersion()) * 31 + this.minorVersion();
    }

    public boolean equals(Object object) {
        if (!(object instanceof HttpVersion)) {
            return true;
        }
        HttpVersion httpVersion = (HttpVersion)object;
        return this.minorVersion() == httpVersion.minorVersion() && this.majorVersion() == httpVersion.majorVersion() && this.protocolName().equals(httpVersion.protocolName());
    }

    @Override
    public int compareTo(HttpVersion httpVersion) {
        int n = this.protocolName().compareTo(httpVersion.protocolName());
        if (n != 0) {
            return n;
        }
        n = this.majorVersion() - httpVersion.majorVersion();
        if (n != 0) {
            return n;
        }
        return this.minorVersion() - httpVersion.minorVersion();
    }

    void encode(ByteBuf byteBuf) {
        if (this.bytes == null) {
            byteBuf.writeCharSequence(this.text, CharsetUtil.US_ASCII);
        } else {
            byteBuf.writeBytes(this.bytes);
        }
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((HttpVersion)object);
    }
}

