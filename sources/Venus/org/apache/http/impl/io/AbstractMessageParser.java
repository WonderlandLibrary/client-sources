/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.io;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpMessage;
import org.apache.http.MessageConstraintException;
import org.apache.http.ParseException;
import org.apache.http.ProtocolException;
import org.apache.http.config.MessageConstraints;
import org.apache.http.io.HttpMessageParser;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.message.BasicLineParser;
import org.apache.http.message.LineParser;
import org.apache.http.params.HttpParamConfig;
import org.apache.http.params.HttpParams;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;

public abstract class AbstractMessageParser<T extends HttpMessage>
implements HttpMessageParser<T> {
    private static final int HEAD_LINE = 0;
    private static final int HEADERS = 1;
    private final SessionInputBuffer sessionBuffer;
    private final MessageConstraints messageConstraints;
    private final List<CharArrayBuffer> headerLines;
    protected final LineParser lineParser;
    private int state;
    private T message;

    @Deprecated
    public AbstractMessageParser(SessionInputBuffer sessionInputBuffer, LineParser lineParser, HttpParams httpParams) {
        Args.notNull(sessionInputBuffer, "Session input buffer");
        Args.notNull(httpParams, "HTTP parameters");
        this.sessionBuffer = sessionInputBuffer;
        this.messageConstraints = HttpParamConfig.getMessageConstraints(httpParams);
        this.lineParser = lineParser != null ? lineParser : BasicLineParser.INSTANCE;
        this.headerLines = new ArrayList<CharArrayBuffer>();
        this.state = 0;
    }

    public AbstractMessageParser(SessionInputBuffer sessionInputBuffer, LineParser lineParser, MessageConstraints messageConstraints) {
        this.sessionBuffer = Args.notNull(sessionInputBuffer, "Session input buffer");
        this.lineParser = lineParser != null ? lineParser : BasicLineParser.INSTANCE;
        this.messageConstraints = messageConstraints != null ? messageConstraints : MessageConstraints.DEFAULT;
        this.headerLines = new ArrayList<CharArrayBuffer>();
        this.state = 0;
    }

    public static Header[] parseHeaders(SessionInputBuffer sessionInputBuffer, int n, int n2, LineParser lineParser) throws HttpException, IOException {
        ArrayList<CharArrayBuffer> arrayList = new ArrayList<CharArrayBuffer>();
        return AbstractMessageParser.parseHeaders(sessionInputBuffer, n, n2, lineParser != null ? lineParser : BasicLineParser.INSTANCE, arrayList);
    }

    public static Header[] parseHeaders(SessionInputBuffer sessionInputBuffer, int n, int n2, LineParser lineParser, List<CharArrayBuffer> list) throws HttpException, IOException {
        int n3;
        block9: {
            Args.notNull(sessionInputBuffer, "Session input buffer");
            Args.notNull(lineParser, "Line parser");
            Args.notNull(list, "Header line list");
            CharArrayBuffer charArrayBuffer = null;
            CharArrayBuffer charArrayBuffer2 = null;
            do {
                if (charArrayBuffer == null) {
                    charArrayBuffer = new CharArrayBuffer(64);
                } else {
                    charArrayBuffer.clear();
                }
                int n4 = sessionInputBuffer.readLine(charArrayBuffer);
                if (n4 == -1 || charArrayBuffer.length() < 1) break block9;
                if ((charArrayBuffer.charAt(0) == ' ' || charArrayBuffer.charAt(0) == '\t') && charArrayBuffer2 != null) {
                    char c;
                    for (n3 = 0; n3 < charArrayBuffer.length() && ((c = charArrayBuffer.charAt(n3)) == ' ' || c == '\t'); ++n3) {
                    }
                    if (n2 > 0 && charArrayBuffer2.length() + 1 + charArrayBuffer.length() - n3 > n2) {
                        throw new MessageConstraintException("Maximum line length limit exceeded");
                    }
                    charArrayBuffer2.append(' ');
                    charArrayBuffer2.append(charArrayBuffer, n3, charArrayBuffer.length() - n3);
                    continue;
                }
                list.add(charArrayBuffer);
                charArrayBuffer2 = charArrayBuffer;
                charArrayBuffer = null;
            } while (n <= 0 || list.size() < n);
            throw new MessageConstraintException("Maximum header count exceeded");
        }
        Header[] headerArray = new Header[list.size()];
        for (n3 = 0; n3 < list.size(); ++n3) {
            CharArrayBuffer charArrayBuffer = list.get(n3);
            try {
                headerArray[n3] = lineParser.parseHeader(charArrayBuffer);
                continue;
            } catch (ParseException parseException) {
                throw new ProtocolException(parseException.getMessage());
            }
        }
        return headerArray;
    }

    protected abstract T parseHead(SessionInputBuffer var1) throws IOException, HttpException, ParseException;

    @Override
    public T parse() throws IOException, HttpException {
        int n = this.state;
        switch (n) {
            case 0: {
                try {
                    this.message = this.parseHead(this.sessionBuffer);
                } catch (ParseException parseException) {
                    throw new ProtocolException(parseException.getMessage(), parseException);
                }
                this.state = 1;
            }
            case 1: {
                Header[] headerArray = AbstractMessageParser.parseHeaders(this.sessionBuffer, this.messageConstraints.getMaxHeaderCount(), this.messageConstraints.getMaxLineLength(), this.lineParser, this.headerLines);
                this.message.setHeaders(headerArray);
                T t = this.message;
                this.message = null;
                this.headerLines.clear();
                this.state = 0;
                return t;
            }
        }
        throw new IllegalStateException("Inconsistent parser state");
    }
}

