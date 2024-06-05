package org.jsoup.parser;

enum TokeniserState$21
{
    @Override
    void read(final Tokeniser t, final CharacterReader r) {
        if (r.matches('-')) {
            t.emit('-');
            t.advanceTransition(TokeniserState$21.ScriptDataEscapedDashDash);
        }
        else {
            t.transition(TokeniserState$21.ScriptData);
        }
    }
}