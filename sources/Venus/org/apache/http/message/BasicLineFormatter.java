/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.message;

import org.apache.http.FormattedHeader;
import org.apache.http.Header;
import org.apache.http.ProtocolVersion;
import org.apache.http.RequestLine;
import org.apache.http.StatusLine;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.message.LineFormatter;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class BasicLineFormatter
implements LineFormatter {
    @Deprecated
    public static final BasicLineFormatter DEFAULT = new BasicLineFormatter();
    public static final BasicLineFormatter INSTANCE = new BasicLineFormatter();

    protected CharArrayBuffer initBuffer(CharArrayBuffer charArrayBuffer) {
        CharArrayBuffer charArrayBuffer2 = charArrayBuffer;
        if (charArrayBuffer2 != null) {
            charArrayBuffer2.clear();
        } else {
            charArrayBuffer2 = new CharArrayBuffer(64);
        }
        return charArrayBuffer2;
    }

    public static String formatProtocolVersion(ProtocolVersion protocolVersion, LineFormatter lineFormatter) {
        return (lineFormatter != null ? lineFormatter : INSTANCE).appendProtocolVersion(null, protocolVersion).toString();
    }

    @Override
    public CharArrayBuffer appendProtocolVersion(CharArrayBuffer charArrayBuffer, ProtocolVersion protocolVersion) {
        Args.notNull(protocolVersion, "Protocol version");
        CharArrayBuffer charArrayBuffer2 = charArrayBuffer;
        int n = this.estimateProtocolVersionLen(protocolVersion);
        if (charArrayBuffer2 == null) {
            charArrayBuffer2 = new CharArrayBuffer(n);
        } else {
            charArrayBuffer2.ensureCapacity(n);
        }
        charArrayBuffer2.append(protocolVersion.getProtocol());
        charArrayBuffer2.append('/');
        charArrayBuffer2.append(Integer.toString(protocolVersion.getMajor()));
        charArrayBuffer2.append('.');
        charArrayBuffer2.append(Integer.toString(protocolVersion.getMinor()));
        return charArrayBuffer2;
    }

    protected int estimateProtocolVersionLen(ProtocolVersion protocolVersion) {
        return protocolVersion.getProtocol().length() + 4;
    }

    public static String formatRequestLine(RequestLine requestLine, LineFormatter lineFormatter) {
        return (lineFormatter != null ? lineFormatter : INSTANCE).formatRequestLine(null, requestLine).toString();
    }

    @Override
    public CharArrayBuffer formatRequestLine(CharArrayBuffer charArrayBuffer, RequestLine requestLine) {
        Args.notNull(requestLine, "Request line");
        CharArrayBuffer charArrayBuffer2 = this.initBuffer(charArrayBuffer);
        this.doFormatRequestLine(charArrayBuffer2, requestLine);
        return charArrayBuffer2;
    }

    protected void doFormatRequestLine(CharArrayBuffer charArrayBuffer, RequestLine requestLine) {
        String string = requestLine.getMethod();
        String string2 = requestLine.getUri();
        int n = string.length() + 1 + string2.length() + 1 + this.estimateProtocolVersionLen(requestLine.getProtocolVersion());
        charArrayBuffer.ensureCapacity(n);
        charArrayBuffer.append(string);
        charArrayBuffer.append(' ');
        charArrayBuffer.append(string2);
        charArrayBuffer.append(' ');
        this.appendProtocolVersion(charArrayBuffer, requestLine.getProtocolVersion());
    }

    public static String formatStatusLine(StatusLine statusLine, LineFormatter lineFormatter) {
        return (lineFormatter != null ? lineFormatter : INSTANCE).formatStatusLine(null, statusLine).toString();
    }

    @Override
    public CharArrayBuffer formatStatusLine(CharArrayBuffer charArrayBuffer, StatusLine statusLine) {
        Args.notNull(statusLine, "Status line");
        CharArrayBuffer charArrayBuffer2 = this.initBuffer(charArrayBuffer);
        this.doFormatStatusLine(charArrayBuffer2, statusLine);
        return charArrayBuffer2;
    }

    protected void doFormatStatusLine(CharArrayBuffer charArrayBuffer, StatusLine statusLine) {
        int n = this.estimateProtocolVersionLen(statusLine.getProtocolVersion()) + 1 + 3 + 1;
        String string = statusLine.getReasonPhrase();
        if (string != null) {
            n += string.length();
        }
        charArrayBuffer.ensureCapacity(n);
        this.appendProtocolVersion(charArrayBuffer, statusLine.getProtocolVersion());
        charArrayBuffer.append(' ');
        charArrayBuffer.append(Integer.toString(statusLine.getStatusCode()));
        charArrayBuffer.append(' ');
        if (string != null) {
            charArrayBuffer.append(string);
        }
    }

    public static String formatHeader(Header header, LineFormatter lineFormatter) {
        return (lineFormatter != null ? lineFormatter : INSTANCE).formatHeader(null, header).toString();
    }

    @Override
    public CharArrayBuffer formatHeader(CharArrayBuffer charArrayBuffer, Header header) {
        CharArrayBuffer charArrayBuffer2;
        Args.notNull(header, "Header");
        if (header instanceof FormattedHeader) {
            charArrayBuffer2 = ((FormattedHeader)header).getBuffer();
        } else {
            charArrayBuffer2 = this.initBuffer(charArrayBuffer);
            this.doFormatHeader(charArrayBuffer2, header);
        }
        return charArrayBuffer2;
    }

    protected void doFormatHeader(CharArrayBuffer charArrayBuffer, Header header) {
        String string = header.getName();
        String string2 = header.getValue();
        int n = string.length() + 2;
        if (string2 != null) {
            n += string2.length();
        }
        charArrayBuffer.ensureCapacity(n);
        charArrayBuffer.append(string);
        charArrayBuffer.append(": ");
        if (string2 != null) {
            charArrayBuffer.ensureCapacity(charArrayBuffer.length() + string2.length());
            for (int i = 0; i < string2.length(); ++i) {
                char c = string2.charAt(i);
                if (c == '\r' || c == '\n' || c == '\f' || c == '\u000b') {
                    c = ' ';
                }
                charArrayBuffer.append(c);
            }
        }
    }
}

