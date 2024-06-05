package org.jsoup.parser;

enum TokeniserState$9
{
    @Override
    void read(final Tokeniser t, final CharacterReader r) {
        if (r.isEmpty()) {
            t.eofError(this);
            t.emit("</");
            t.transition(TokeniserState$9.Data);
        }
        else if (r.matchesLetter()) {
            t.createTagPending(false);
            t.transition(TokeniserState$9.TagName);
        }
        else if (r.matches('>')) {
            t.error(this);
            t.advanceTransition(TokeniserState$9.Data);
        }
        else {
            t.error(this);
            t.advanceTransition(TokeniserState$9.BogusComment);
        }
    }
}