package org.jsoup.parser;

enum TokeniserState$49
{
    @Override
    void read(final Tokeniser t, final CharacterReader r) {
        final char c = r.consume();
        switch (c) {
            case '>': {
                t.emitCommentPending();
                t.transition(TokeniserState$49.Data);
                break;
            }
            case '\0': {
                t.error(this);
                t.commentPending.data.append("--").append('\ufffd');
                t.transition(TokeniserState$49.Comment);
                break;
            }
            case '!': {
                t.error(this);
                t.transition(TokeniserState$49.CommentEndBang);
                break;
            }
            case '-': {
                t.error(this);
                t.commentPending.data.append('-');
                break;
            }
            case '\uffff': {
                t.eofError(this);
                t.emitCommentPending();
                t.transition(TokeniserState$49.Data);
                break;
            }
            default: {
                t.error(this);
                t.commentPending.data.append("--").append(c);
                t.transition(TokeniserState$49.Comment);
                break;
            }
        }
    }
}