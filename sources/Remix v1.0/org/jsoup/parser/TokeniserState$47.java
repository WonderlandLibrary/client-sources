package org.jsoup.parser;

enum TokeniserState$47
{
    @Override
    void read(final Tokeniser t, final CharacterReader r) {
        final char c = r.current();
        switch (c) {
            case '-': {
                t.advanceTransition(TokeniserState$47.CommentEndDash);
                break;
            }
            case '\0': {
                t.error(this);
                r.advance();
                t.commentPending.data.append('\ufffd');
                break;
            }
            case '\uffff': {
                t.eofError(this);
                t.emitCommentPending();
                t.transition(TokeniserState$47.Data);
                break;
            }
            default: {
                t.commentPending.data.append(r.consumeToAny('-', '\0'));
                break;
            }
        }
    }
}