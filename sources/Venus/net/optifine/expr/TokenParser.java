/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.expr;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;
import java.util.ArrayList;
import net.optifine.expr.ParseException;
import net.optifine.expr.Token;
import net.optifine.expr.TokenType;

public class TokenParser {
    public static Token[] parse(String string) throws IOException, ParseException {
        StringReader stringReader = new StringReader(string);
        PushbackReader pushbackReader = new PushbackReader(stringReader);
        ArrayList<Token> arrayList = new ArrayList<Token>();
        while (true) {
            int n;
            if ((n = pushbackReader.read()) < 0) {
                Token[] tokenArray = arrayList.toArray(new Token[arrayList.size()]);
                return tokenArray;
            }
            char c = (char)n;
            if (Character.isWhitespace(c)) continue;
            TokenType tokenType = TokenType.getTypeByFirstChar(c);
            if (tokenType == null) {
                throw new ParseException("Invalid character: '" + c + "', in: " + string);
            }
            Token token = TokenParser.readToken(c, tokenType, pushbackReader);
            arrayList.add(token);
        }
    }

    private static Token readToken(char c, TokenType tokenType, PushbackReader pushbackReader) throws IOException {
        int n;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(c);
        while ((n = pushbackReader.read()) >= 0) {
            char c2 = (char)n;
            if (!tokenType.hasCharNext(c2)) {
                pushbackReader.unread(c2);
                break;
            }
            stringBuffer.append(c2);
        }
        return new Token(tokenType, stringBuffer.toString());
    }
}

