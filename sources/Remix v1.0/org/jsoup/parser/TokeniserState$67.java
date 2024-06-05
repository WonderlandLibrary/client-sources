package org.jsoup.parser;

enum TokeniserState$67
{
    @Override
    void read(final Tokeniser t, final CharacterReader r) {
        final String data = r.consumeTo("]]>");
        t.emit(data);
        r.matchConsume("]]>");
        t.transition(TokeniserState$67.Data);
    }
}