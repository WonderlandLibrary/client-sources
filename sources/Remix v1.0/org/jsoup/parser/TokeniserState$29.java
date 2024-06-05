package org.jsoup.parser;

enum TokeniserState$29
{
    @Override
    void read(final Tokeniser t, final CharacterReader r) {
        final char c = r.current();
        switch (c) {
            case '-': {
                t.emit(c);
                t.advanceTransition(TokeniserState$29.ScriptDataDoubleEscapedDash);
                break;
            }
            case '<': {
                t.emit(c);
                t.advanceTransition(TokeniserState$29.ScriptDataDoubleEscapedLessthanSign);
                break;
            }
            case '\0': {
                t.error(this);
                r.advance();
                t.emit('\ufffd');
                break;
            }
            case '\uffff': {
                t.eofError(this);
                t.transition(TokeniserState$29.Data);
                break;
            }
            default: {
                final String data = r.consumeToAny('-', '<', '\0');
                t.emit(data);
                break;
            }
        }
    }
}