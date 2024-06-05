package org.jsoup.parser;

enum TokeniserState$31
{
    @Override
    void read(final Tokeniser t, final CharacterReader r) {
        final char c = r.consume();
        switch (c) {
            case '-': {
                t.emit(c);
                break;
            }
            case '<': {
                t.emit(c);
                t.transition(TokeniserState$31.ScriptDataDoubleEscapedLessthanSign);
                break;
            }
            case '>': {
                t.emit(c);
                t.transition(TokeniserState$31.ScriptData);
                break;
            }
            case '\0': {
                t.error(this);
                t.emit('\ufffd');
                t.transition(TokeniserState$31.ScriptDataDoubleEscaped);
                break;
            }
            case '\uffff': {
                t.eofError(this);
                t.transition(TokeniserState$31.Data);
                break;
            }
            default: {
                t.emit(c);
                t.transition(TokeniserState$31.ScriptDataDoubleEscaped);
                break;
            }
        }
    }
}