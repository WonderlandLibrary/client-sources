package org.jsoup.parser;

enum TokeniserState$50
{
    @Override
    void read(final Tokeniser t, final CharacterReader r) {
        final char c = r.consume();
        switch (c) {
            case '-': {
                t.commentPending.data.append("--!");
                t.transition(TokeniserState$50.CommentEndDash);
                break;
            }
            case '>': {
                t.emitCommentPending();
                t.transition(TokeniserState$50.Data);
                break;
            }
            case '\0': {
                t.error(this);
                t.commentPending.data.append("--!").append('\ufffd');
                t.transition(TokeniserState$50.Comment);
                break;
            }
            case '\uffff': {
                t.eofError(this);
                t.emitCommentPending();
                t.transition(TokeniserState$50.Data);
                break;
            }
            default: {
                t.commentPending.data.append("--!").append(c);
                t.transition(TokeniserState$50.Comment);
                break;
            }
        }
    }
}