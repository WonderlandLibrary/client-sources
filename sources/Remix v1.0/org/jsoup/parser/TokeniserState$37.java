package org.jsoup.parser;

enum TokeniserState$37
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
            case '\"': {
                t.transition(TokeniserState$37.AttributeValue_doubleQuoted);
                break;
            }
            case '&': {
                r.unconsume();
                t.transition(TokeniserState$37.AttributeValue_unquoted);
                break;
            }
            case '\'': {
                t.transition(TokeniserState$37.AttributeValue_singleQuoted);
                break;
            }
            case '\0': {
                t.error(this);
                t.tagPending.appendAttributeValue('\ufffd');
                t.transition(TokeniserState$37.AttributeValue_unquoted);
                break;
            }
            case '\uffff': {
                t.eofError(this);
                t.emitTagPending();
                t.transition(TokeniserState$37.Data);
                break;
            }
            case '>': {
                t.error(this);
                t.emitTagPending();
                t.transition(TokeniserState$37.Data);
                break;
            }
            case '<':
            case '=':
            case '`': {
                t.error(this);
                t.tagPending.appendAttributeValue(c);
                t.transition(TokeniserState$37.AttributeValue_unquoted);
                break;
            }
            default: {
                r.unconsume();
                t.transition(TokeniserState$37.AttributeValue_unquoted);
                break;
            }
        }
    }
}