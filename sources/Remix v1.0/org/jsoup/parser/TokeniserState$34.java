package org.jsoup.parser;

enum TokeniserState$34
{
    @Override
    void read(final Tokeniser t, final CharacterReader r) {
        final char c = r.consume();
        switch (c) {
            case '\t':
            case '\n':
            case '\f':
            case '\r':
            case ' ': {
                break;
            }
            case '/': {
                t.transition(TokeniserState$34.SelfClosingStartTag);
                break;
            }
            case '>': {
                t.emitTagPending();
                t.transition(TokeniserState$34.Data);
                break;
            }
            case '\0': {
                t.error(this);
                t.tagPending.newAttribute();
                r.unconsume();
                t.transition(TokeniserState$34.AttributeName);
                break;
            }
            case '\uffff': {
                t.eofError(this);
                t.transition(TokeniserState$34.Data);
                break;
            }
            case '\"':
            case '\'':
            case '<':
            case '=': {
                t.error(this);
                t.tagPending.newAttribute();
                t.tagPending.appendAttributeName(c);
                t.transition(TokeniserState$34.AttributeName);
                break;
            }
            default: {
                t.tagPending.newAttribute();
                r.unconsume();
                t.transition(TokeniserState$34.AttributeName);
                break;
            }
        }
    }
}