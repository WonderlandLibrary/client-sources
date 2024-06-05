package org.jsoup.parser;

enum TokeniserState$44
{
    @Override
    void read(final Tokeniser t, final CharacterReader r) {
        if (r.matchConsume("--")) {
            t.createCommentPending();
            t.transition(TokeniserState$44.CommentStart);
        }
        else if (r.matchConsumeIgnoreCase("DOCTYPE")) {
            t.transition(TokeniserState$44.Doctype);
        }
        else if (r.matchConsume("[CDATA[")) {
            t.transition(TokeniserState$44.CdataSection);
        }
        else {
            t.error(this);
            t.advanceTransition(TokeniserState$44.BogusComment);
        }
    }
}