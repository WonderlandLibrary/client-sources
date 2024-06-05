package org.jsoup.parser;

enum TokeniserState$30
{
    @Override
    void read(final Tokeniser t, final CharacterReader r) {
        final char c = r.consume();
        switch (c) {
            case '-': {
                t.emit(c);
                t.transition(TokeniserState$30.ScriptDataDoubleEscapedDashDash);
                break;
            }
            case '<': {
                t.emit(c);
                t.transition(TokeniserState$30.ScriptDataDoubleEscapedLessthanSign);
                break;
            }
            case '\0': {
                t.error(this);
                t.emit('\ufffd');
                t.transition(TokeniserState$30.ScriptDataDoubleEscaped);
                break;
            }
            case '\uffff': {
                t.eofError(this);
                t.transition(TokeniserState$30.Data);
                break;
            }
            default: {
                t.emit(c);
                t.transition(TokeniserState$30.ScriptDataDoubleEscaped);
                break;
            }
        }
    }
}