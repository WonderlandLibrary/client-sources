package org.jsoup.parser;

enum TokeniserState$14
{
    @Override
    void read(final Tokeniser t, final CharacterReader r) {
        if (r.matches('/')) {
            t.createTempBuffer();
            t.advanceTransition(TokeniserState$14.RawtextEndTagOpen);
        }
        else {
            t.emit('<');
            t.transition(TokeniserState$14.Rawtext);
        }
    }
}