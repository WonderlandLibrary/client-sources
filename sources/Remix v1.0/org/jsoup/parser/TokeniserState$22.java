package org.jsoup.parser;

enum TokeniserState$22
{
    @Override
    void read(final Tokeniser t, final CharacterReader r) {
        if (r.isEmpty()) {
            t.eofError(this);
            t.transition(TokeniserState$22.Data);
            return;
        }
        switch (r.current()) {
            case '-': {
                t.emit('-');
                t.advanceTransition(TokeniserState$22.ScriptDataEscapedDash);
                break;
            }
            case '<': {
                t.advanceTransition(TokeniserState$22.ScriptDataEscapedLessthanSign);
                break;
            }
            case '\0': {
                t.error(this);
                r.advance();
                t.emit('\ufffd');
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