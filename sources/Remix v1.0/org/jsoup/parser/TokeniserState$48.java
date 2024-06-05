package org.jsoup.parser;

enum TokeniserState$48
{
    @Override
    void read(final Tokeniser t, final CharacterReader r) {
        final char c = r.consume();
        switch (c) {
            case '-': {
                t.transition(TokeniserState$48.CommentEnd);
                break;
            }
            case '\0': {
                t.error(this);
                t.commentPending.data.append('-').append('\ufffd');
                t.transition(TokeniserState$48.Comment);
                break;
            }
            case '\uffff': {
                t.eofError(this);
                t.emitCommentPending();
                t.transition(TokeniserState$48.Data);
                break;
            }
            default: {
                t.commentPending.data.append('-').append(c);
                t.transition(TokeniserState$48.Comment);
                break;
            }
        }
    }
}