package org.jsoup.parser;

enum TokeniserState$32
{
    @Override
    void read(final Tokeniser t, final CharacterReader r) {
        if (r.matches('/')) {
            t.emit('/');
            t.createTempBuffer();
            t.advanceTransition(TokeniserState$32.ScriptDataDoubleEscapeEnd);
        }
        else {
            t.transition(TokeniserState$32.ScriptDataDoubleEscaped);
        }
    }
}