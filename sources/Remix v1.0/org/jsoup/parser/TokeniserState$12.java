package org.jsoup.parser;

enum TokeniserState$12
{
    @Override
    void read(final Tokeniser t, final CharacterReader r) {
        if (r.matchesLetter()) {
            t.createTagPending(false);
            t.tagPending.appendTagName(r.current());
            t.dataBuffer.append(r.current());
            t.advanceTransition(TokeniserState$12.RCDATAEndTagName);
        }
        else {
            t.emit("</");
            t.transition(TokeniserState$12.Rcdata);
        }
    }
}