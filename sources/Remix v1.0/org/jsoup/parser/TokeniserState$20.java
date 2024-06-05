package org.jsoup.parser;

enum TokeniserState$20
{
    @Override
    void read(final Tokeniser t, final CharacterReader r) {
        if (r.matches('-')) {
            t.emit('-');
            t.advanceTransition(TokeniserState$20.ScriptDataEscapeStartDash);
        }
        else {
            t.transition(TokeniserState$20.ScriptData);
        }
    }
}