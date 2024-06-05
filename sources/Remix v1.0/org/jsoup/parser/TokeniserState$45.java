package org.jsoup.parser;

enum TokeniserState$45
{
    @Override
    void read(final Tokeniser t, final CharacterReader r) {
        final char c = r.consume();
        switch (c) {
            case '-': {
                t.transition(TokeniserState$45.CommentStartDash);
                break;
            }
            case '\0': {
                t.error(this);
                t.commentPending.data.append('\ufffd');
                t.transition(TokeniserState$45.Comment);
                break;
            }
            case '>': {
                t.error(this);
                t.emitCommentPending();
                t.transition(TokeniserState$45.Data);
                break;
            }
            case '\uffff': {
                t.eofError(this);
                t.emitCommentPending();
                t.transition(TokeniserState$45.Data);
                break;
            }
            default: {
                t.commentPending.data.append(c);
                t.transition(TokeniserState$45.Comment);
                break;
            }
        }
    }
}