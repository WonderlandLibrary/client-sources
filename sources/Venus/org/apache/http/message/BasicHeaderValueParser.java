/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.message;

import java.util.ArrayList;
import java.util.BitSet;
import org.apache.http.HeaderElement;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.message.BasicHeaderElement;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.message.HeaderValueParser;
import org.apache.http.message.ParserCursor;
import org.apache.http.message.TokenParser;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class BasicHeaderValueParser
implements HeaderValueParser {
    @Deprecated
    public static final BasicHeaderValueParser DEFAULT = new BasicHeaderValueParser();
    public static final BasicHeaderValueParser INSTANCE = new BasicHeaderValueParser();
    private static final char PARAM_DELIMITER = ';';
    private static final char ELEM_DELIMITER = ',';
    private static final BitSet TOKEN_DELIMS = TokenParser.INIT_BITSET(61, 59, 44);
    private static final BitSet VALUE_DELIMS = TokenParser.INIT_BITSET(59, 44);
    private final TokenParser tokenParser = TokenParser.INSTANCE;

    public static HeaderElement[] parseElements(String string, HeaderValueParser headerValueParser) throws ParseException {
        Args.notNull(string, "Value");
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(string.length());
        charArrayBuffer.append(string);
        ParserCursor parserCursor = new ParserCursor(0, string.length());
        return (headerValueParser != null ? headerValueParser : INSTANCE).parseElements(charArrayBuffer, parserCursor);
    }

    @Override
    public HeaderElement[] parseElements(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor) {
        Args.notNull(charArrayBuffer, "Char array buffer");
        Args.notNull(parserCursor, "Parser cursor");
        ArrayList<HeaderElement> arrayList = new ArrayList<HeaderElement>();
        while (!parserCursor.atEnd()) {
            HeaderElement headerElement = this.parseHeaderElement(charArrayBuffer, parserCursor);
            if (headerElement.getName().isEmpty() && headerElement.getValue() == null) continue;
            arrayList.add(headerElement);
        }
        return arrayList.toArray(new HeaderElement[arrayList.size()]);
    }

    public static HeaderElement parseHeaderElement(String string, HeaderValueParser headerValueParser) throws ParseException {
        Args.notNull(string, "Value");
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(string.length());
        charArrayBuffer.append(string);
        ParserCursor parserCursor = new ParserCursor(0, string.length());
        return (headerValueParser != null ? headerValueParser : INSTANCE).parseHeaderElement(charArrayBuffer, parserCursor);
    }

    @Override
    public HeaderElement parseHeaderElement(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor) {
        char c;
        Args.notNull(charArrayBuffer, "Char array buffer");
        Args.notNull(parserCursor, "Parser cursor");
        NameValuePair nameValuePair = this.parseNameValuePair(charArrayBuffer, parserCursor);
        NameValuePair[] nameValuePairArray = null;
        if (!parserCursor.atEnd() && (c = charArrayBuffer.charAt(parserCursor.getPos() - 1)) != ',') {
            nameValuePairArray = this.parseParameters(charArrayBuffer, parserCursor);
        }
        return this.createHeaderElement(nameValuePair.getName(), nameValuePair.getValue(), nameValuePairArray);
    }

    protected HeaderElement createHeaderElement(String string, String string2, NameValuePair[] nameValuePairArray) {
        return new BasicHeaderElement(string, string2, nameValuePairArray);
    }

    public static NameValuePair[] parseParameters(String string, HeaderValueParser headerValueParser) throws ParseException {
        Args.notNull(string, "Value");
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(string.length());
        charArrayBuffer.append(string);
        ParserCursor parserCursor = new ParserCursor(0, string.length());
        return (headerValueParser != null ? headerValueParser : INSTANCE).parseParameters(charArrayBuffer, parserCursor);
    }

    @Override
    public NameValuePair[] parseParameters(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor) {
        Args.notNull(charArrayBuffer, "Char array buffer");
        Args.notNull(parserCursor, "Parser cursor");
        this.tokenParser.skipWhiteSpace(charArrayBuffer, parserCursor);
        ArrayList<NameValuePair> arrayList = new ArrayList<NameValuePair>();
        while (!parserCursor.atEnd()) {
            NameValuePair nameValuePair = this.parseNameValuePair(charArrayBuffer, parserCursor);
            arrayList.add(nameValuePair);
            char c = charArrayBuffer.charAt(parserCursor.getPos() - 1);
            if (c != ',') continue;
            break;
        }
        return arrayList.toArray(new NameValuePair[arrayList.size()]);
    }

    public static NameValuePair parseNameValuePair(String string, HeaderValueParser headerValueParser) throws ParseException {
        Args.notNull(string, "Value");
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(string.length());
        charArrayBuffer.append(string);
        ParserCursor parserCursor = new ParserCursor(0, string.length());
        return (headerValueParser != null ? headerValueParser : INSTANCE).parseNameValuePair(charArrayBuffer, parserCursor);
    }

    @Override
    public NameValuePair parseNameValuePair(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor) {
        Args.notNull(charArrayBuffer, "Char array buffer");
        Args.notNull(parserCursor, "Parser cursor");
        String string = this.tokenParser.parseToken(charArrayBuffer, parserCursor, TOKEN_DELIMS);
        if (parserCursor.atEnd()) {
            return new BasicNameValuePair(string, null);
        }
        char c = charArrayBuffer.charAt(parserCursor.getPos());
        parserCursor.updatePos(parserCursor.getPos() + 1);
        if (c != '=') {
            return this.createNameValuePair(string, null);
        }
        String string2 = this.tokenParser.parseValue(charArrayBuffer, parserCursor, VALUE_DELIMS);
        if (!parserCursor.atEnd()) {
            parserCursor.updatePos(parserCursor.getPos() + 1);
        }
        return this.createNameValuePair(string, string2);
    }

    @Deprecated
    public NameValuePair parseNameValuePair(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor, char[] cArray) {
        Args.notNull(charArrayBuffer, "Char array buffer");
        Args.notNull(parserCursor, "Parser cursor");
        BitSet bitSet = new BitSet();
        if (cArray != null) {
            for (char c : cArray) {
                bitSet.set(c);
            }
        }
        bitSet.set(61);
        String string = this.tokenParser.parseToken(charArrayBuffer, parserCursor, bitSet);
        if (parserCursor.atEnd()) {
            return new BasicNameValuePair(string, null);
        }
        int n = charArrayBuffer.charAt(parserCursor.getPos());
        parserCursor.updatePos(parserCursor.getPos() + 1);
        if (n != 61) {
            return this.createNameValuePair(string, null);
        }
        bitSet.clear(61);
        String string2 = this.tokenParser.parseValue(charArrayBuffer, parserCursor, bitSet);
        if (!parserCursor.atEnd()) {
            parserCursor.updatePos(parserCursor.getPos() + 1);
        }
        return this.createNameValuePair(string, string2);
    }

    protected NameValuePair createNameValuePair(String string, String string2) {
        return new BasicNameValuePair(string, string2);
    }
}

