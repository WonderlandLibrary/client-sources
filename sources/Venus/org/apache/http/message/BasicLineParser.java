/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.message;

import org.apache.http.Header;
import org.apache.http.HttpVersion;
import org.apache.http.ParseException;
import org.apache.http.ProtocolVersion;
import org.apache.http.RequestLine;
import org.apache.http.StatusLine;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.message.BasicRequestLine;
import org.apache.http.message.BasicStatusLine;
import org.apache.http.message.BufferedHeader;
import org.apache.http.message.LineParser;
import org.apache.http.message.ParserCursor;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class BasicLineParser
implements LineParser {
    @Deprecated
    public static final BasicLineParser DEFAULT = new BasicLineParser();
    public static final BasicLineParser INSTANCE = new BasicLineParser();
    protected final ProtocolVersion protocol;

    public BasicLineParser(ProtocolVersion protocolVersion) {
        this.protocol = protocolVersion != null ? protocolVersion : HttpVersion.HTTP_1_1;
    }

    public BasicLineParser() {
        this(null);
    }

    public static ProtocolVersion parseProtocolVersion(String string, LineParser lineParser) throws ParseException {
        Args.notNull(string, "Value");
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(string.length());
        charArrayBuffer.append(string);
        ParserCursor parserCursor = new ParserCursor(0, string.length());
        return (lineParser != null ? lineParser : INSTANCE).parseProtocolVersion(charArrayBuffer, parserCursor);
    }

    @Override
    public ProtocolVersion parseProtocolVersion(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor) throws ParseException {
        int n;
        int n2;
        int n3;
        Args.notNull(charArrayBuffer, "Char array buffer");
        Args.notNull(parserCursor, "Parser cursor");
        String string = this.protocol.getProtocol();
        int n4 = string.length();
        int n5 = parserCursor.getPos();
        int n6 = parserCursor.getUpperBound();
        this.skipWhitespace(charArrayBuffer, parserCursor);
        int n7 = parserCursor.getPos();
        if (n7 + n4 + 4 > n6) {
            throw new ParseException("Not a valid protocol version: " + charArrayBuffer.substring(n5, n6));
        }
        boolean bl = true;
        for (n3 = 0; bl && n3 < n4; ++n3) {
            bl = charArrayBuffer.charAt(n7 + n3) == string.charAt(n3);
        }
        if (bl) {
            boolean bl2 = bl = charArrayBuffer.charAt(n7 + n4) == '/';
        }
        if (!bl) {
            throw new ParseException("Not a valid protocol version: " + charArrayBuffer.substring(n5, n6));
        }
        n3 = charArrayBuffer.indexOf(46, n7 += n4 + 1, n6);
        if (n3 == -1) {
            throw new ParseException("Invalid protocol version number: " + charArrayBuffer.substring(n5, n6));
        }
        try {
            n2 = Integer.parseInt(charArrayBuffer.substringTrimmed(n7, n3));
        } catch (NumberFormatException numberFormatException) {
            throw new ParseException("Invalid protocol major version number: " + charArrayBuffer.substring(n5, n6));
        }
        n7 = n3 + 1;
        int n8 = charArrayBuffer.indexOf(32, n7, n6);
        if (n8 == -1) {
            n8 = n6;
        }
        try {
            n = Integer.parseInt(charArrayBuffer.substringTrimmed(n7, n8));
        } catch (NumberFormatException numberFormatException) {
            throw new ParseException("Invalid protocol minor version number: " + charArrayBuffer.substring(n5, n6));
        }
        parserCursor.updatePos(n8);
        return this.createProtocolVersion(n2, n);
    }

    protected ProtocolVersion createProtocolVersion(int n, int n2) {
        return this.protocol.forVersion(n, n2);
    }

    @Override
    public boolean hasProtocolVersion(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor) {
        int n;
        Args.notNull(charArrayBuffer, "Char array buffer");
        Args.notNull(parserCursor, "Parser cursor");
        String string = this.protocol.getProtocol();
        int n2 = string.length();
        if (charArrayBuffer.length() < n2 + 4) {
            return true;
        }
        if (n < 0) {
            n = charArrayBuffer.length() - 4 - n2;
        } else if (n == 0) {
            for (n = parserCursor.getPos(); n < charArrayBuffer.length() && HTTP.isWhitespace(charArrayBuffer.charAt(n)); ++n) {
            }
        }
        if (n + n2 + 4 > charArrayBuffer.length()) {
            return true;
        }
        boolean bl = true;
        for (int i = 0; bl && i < n2; ++i) {
            bl = charArrayBuffer.charAt(n + i) == string.charAt(i);
        }
        if (bl) {
            bl = charArrayBuffer.charAt(n + n2) == '/';
        }
        return bl;
    }

    public static RequestLine parseRequestLine(String string, LineParser lineParser) throws ParseException {
        Args.notNull(string, "Value");
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(string.length());
        charArrayBuffer.append(string);
        ParserCursor parserCursor = new ParserCursor(0, string.length());
        return (lineParser != null ? lineParser : INSTANCE).parseRequestLine(charArrayBuffer, parserCursor);
    }

    @Override
    public RequestLine parseRequestLine(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor) throws ParseException {
        Args.notNull(charArrayBuffer, "Char array buffer");
        Args.notNull(parserCursor, "Parser cursor");
        int n = parserCursor.getPos();
        int n2 = parserCursor.getUpperBound();
        try {
            this.skipWhitespace(charArrayBuffer, parserCursor);
            int n3 = parserCursor.getPos();
            int n4 = charArrayBuffer.indexOf(32, n3, n2);
            if (n4 < 0) {
                throw new ParseException("Invalid request line: " + charArrayBuffer.substring(n, n2));
            }
            String string = charArrayBuffer.substringTrimmed(n3, n4);
            parserCursor.updatePos(n4);
            this.skipWhitespace(charArrayBuffer, parserCursor);
            n3 = parserCursor.getPos();
            n4 = charArrayBuffer.indexOf(32, n3, n2);
            if (n4 < 0) {
                throw new ParseException("Invalid request line: " + charArrayBuffer.substring(n, n2));
            }
            String string2 = charArrayBuffer.substringTrimmed(n3, n4);
            parserCursor.updatePos(n4);
            ProtocolVersion protocolVersion = this.parseProtocolVersion(charArrayBuffer, parserCursor);
            this.skipWhitespace(charArrayBuffer, parserCursor);
            if (!parserCursor.atEnd()) {
                throw new ParseException("Invalid request line: " + charArrayBuffer.substring(n, n2));
            }
            return this.createRequestLine(string, string2, protocolVersion);
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            throw new ParseException("Invalid request line: " + charArrayBuffer.substring(n, n2));
        }
    }

    protected RequestLine createRequestLine(String string, String string2, ProtocolVersion protocolVersion) {
        return new BasicRequestLine(string, string2, protocolVersion);
    }

    public static StatusLine parseStatusLine(String string, LineParser lineParser) throws ParseException {
        Args.notNull(string, "Value");
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(string.length());
        charArrayBuffer.append(string);
        ParserCursor parserCursor = new ParserCursor(0, string.length());
        return (lineParser != null ? lineParser : INSTANCE).parseStatusLine(charArrayBuffer, parserCursor);
    }

    @Override
    public StatusLine parseStatusLine(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor) throws ParseException {
        Args.notNull(charArrayBuffer, "Char array buffer");
        Args.notNull(parserCursor, "Parser cursor");
        int n = parserCursor.getPos();
        int n2 = parserCursor.getUpperBound();
        try {
            int n3;
            ProtocolVersion protocolVersion = this.parseProtocolVersion(charArrayBuffer, parserCursor);
            this.skipWhitespace(charArrayBuffer, parserCursor);
            int n4 = parserCursor.getPos();
            int n5 = charArrayBuffer.indexOf(32, n4, n2);
            if (n5 < 0) {
                n5 = n2;
            }
            String string = charArrayBuffer.substringTrimmed(n4, n5);
            for (int i = 0; i < string.length(); ++i) {
                if (Character.isDigit(string.charAt(i))) continue;
                throw new ParseException("Status line contains invalid status code: " + charArrayBuffer.substring(n, n2));
            }
            try {
                n3 = Integer.parseInt(string);
            } catch (NumberFormatException numberFormatException) {
                throw new ParseException("Status line contains invalid status code: " + charArrayBuffer.substring(n, n2));
            }
            n4 = n5;
            String string2 = n4 < n2 ? charArrayBuffer.substringTrimmed(n4, n2) : "";
            return this.createStatusLine(protocolVersion, n3, string2);
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            throw new ParseException("Invalid status line: " + charArrayBuffer.substring(n, n2));
        }
    }

    protected StatusLine createStatusLine(ProtocolVersion protocolVersion, int n, String string) {
        return new BasicStatusLine(protocolVersion, n, string);
    }

    public static Header parseHeader(String string, LineParser lineParser) throws ParseException {
        Args.notNull(string, "Value");
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(string.length());
        charArrayBuffer.append(string);
        return (lineParser != null ? lineParser : INSTANCE).parseHeader(charArrayBuffer);
    }

    @Override
    public Header parseHeader(CharArrayBuffer charArrayBuffer) throws ParseException {
        return new BufferedHeader(charArrayBuffer);
    }

    protected void skipWhitespace(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor) {
        int n;
        int n2 = parserCursor.getUpperBound();
        for (n = parserCursor.getPos(); n < n2 && HTTP.isWhitespace(charArrayBuffer.charAt(n)); ++n) {
        }
        parserCursor.updatePos(n);
    }
}

