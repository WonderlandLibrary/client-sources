package org.jsoup.parser;

enum TokeniserState$41
{
    @Override
    void read(final Tokeniser t, final CharacterReader r) {
        final char c = r.consume();
        switch (c) {
            case '\t':
            case '\n':
            case '\f':
            case '\r':
            case ' ': {
                t.transition(TokeniserState$41.BeforeAttributeName);
                break;
            }
            case '/': {
                t.transition(TokeniserState$41.SelfClosingStartTag);
                break;
            }
            case '>': {
                t.emitTagPending();
                t.transition(TokeniserState$41.Data);
                break;
            }
            case '\uffff': {
                t.eofError(this);
                t.transition(TokeniserState$41.Data);
                break;
            }
            default: {
                t.error(this);
                r.unconsume();
                t.transition(TokeniserState$41.BeforeAttributeName);
                break;
            }
        }
    }
}