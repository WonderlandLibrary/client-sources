package org.jsoup.parser;

enum TokeniserState$24
{
    @Override
    void read(final Tokeniser t, final CharacterReader r) {
        if (r.isEmpty()) {
            t.eofError(this);
            t.transition(TokeniserState$24.Data);
            return;
        }
        final char c = r.consume();
        switch (c) {
            case '-': {
                t.emit(c);
                break;
            }
            case '<': {
                t.transition(TokeniserState$24.ScriptDataEscapedLessthanSign);
                break;
            }
            case '>': {
                t.emit(c);
                t.transition(TokeniserState$24.ScriptData);
                break;
            }
            case '\0': {
                t.error(this);
                t.emit('\ufffd');
                t.transition(TokeniserState$24.ScriptDataEscaped);
                break;
            }
            default: {
                t.emit(c);
                t.transition(TokeniserState$24.ScriptDataEscaped);
                break;
            }
        }
    }
}