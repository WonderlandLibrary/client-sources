package org.jsoup.parser;

enum TokeniserState$23
{
    @Override
    void read(final Tokeniser t, final CharacterReader r) {
        if (r.isEmpty()) {
            t.eofError(this);
            t.transition(TokeniserState$23.Data);
            return;
        }
        final char c = r.consume();
        switch (c) {
            case '-': {
                t.emit(c);
                t.transition(TokeniserState$23.ScriptDataEscapedDashDash);
                break;
            }
            case '<': {
                t.transition(TokeniserState$23.ScriptDataEscapedLessthanSign);
                break;
            }
            case '\0': {
                t.error(this);
                t.emit('\ufffd');
                t.transition(TokeniserState$23.ScriptDataEscaped);
                break;
            }
            default: {
                t.emit(c);
                t.transition(TokeniserState$23.ScriptDataEscaped);
                break;
            }
        }
    }
}