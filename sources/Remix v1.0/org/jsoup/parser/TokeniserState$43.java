package org.jsoup.parser;

enum TokeniserState$43
{
    @Override
    void read(final Tokeniser t, final CharacterReader r) {
        r.unconsume();
        final Token.Comment comment = new Token.Comment();
        comment.bogus = true;
        comment.data.append(r.consumeTo('>'));
        t.emit(comment);
        t.advanceTransition(TokeniserState$43.Data);
    }
}