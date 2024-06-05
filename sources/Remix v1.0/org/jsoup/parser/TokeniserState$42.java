package org.jsoup.parser;

enum TokeniserState$42
{
    @Override
    void read(final Tokeniser t, final CharacterReader r) {
        final char c = r.consume();
        switch (c) {
            case '>': {
                t.tagPending.selfClosing = true;
                t.emitTagPending();
                t.transition(TokeniserState$42.Data);
                break;
            }
            case '\uffff': {
                t.eofError(this);
                t.transition(TokeniserState$42.Data);
                break;
            }
            default: {
                t.error(this);
                r.unconsume();
                t.transition(TokeniserState$42.BeforeAttributeName);
                break;
            }
        }
    }
}