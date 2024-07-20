/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model.anim;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;
import java.util.ArrayList;
import net.optifine.entity.model.anim.EnumTokenType;
import net.optifine.entity.model.anim.ParseException;
import net.optifine.entity.model.anim.Token;

public class TokenParser {
    public static Token[] parse(String str) throws IOException, ParseException {
        StringReader reader = new StringReader(str);
        PushbackReader pushbackreader = new PushbackReader(reader);
        ArrayList<Token> list = new ArrayList<Token>();
        while (true) {
            int i;
            if ((i = pushbackreader.read()) < 0) {
                Token[] atoken = list.toArray(new Token[list.size()]);
                return atoken;
            }
            char c0 = (char)i;
            if (Character.isWhitespace(c0)) continue;
            EnumTokenType enumtokentype = EnumTokenType.getTypeByFirstChar(c0);
            if (enumtokentype == null) {
                throw new ParseException("Invalid character: '" + c0 + "', in: " + str);
            }
            Token token = TokenParser.readToken(c0, enumtokentype, pushbackreader);
            list.add(token);
        }
    }

    private static Token readToken(char chFirst, EnumTokenType type2, PushbackReader pr) throws IOException {
        int i;
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append(chFirst);
        while ((type2.getMaxLen() <= 0 || stringbuffer.length() < type2.getMaxLen()) && (i = pr.read()) >= 0) {
            char c0 = (char)i;
            if (!type2.hasChar(c0)) {
                pr.unread(c0);
                break;
            }
            stringbuffer.append(c0);
        }
        return new Token(type2, stringbuffer.toString());
    }
}

