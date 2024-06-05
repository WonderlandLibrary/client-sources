package org.jsoup.parser;

enum TokeniserState$10
{
    @Override
    void read(final Tokeniser t, final CharacterReader r) {
        final String tagName = r.consumeTagName();
        t.tagPending.appendTagName(tagName);
        switch (r.consume()) {
            case '\t':
            case '\n':
            case '\f':
            case '\r':
            case ' ': {
                t.transition(TokeniserState$10.BeforeAttributeName);
                break;
            }
            case '/': {
                t.transition(TokeniserState$10.SelfClosingStartTag);
                break;
            }
            case '>': {
                t.emitTagPending();
                t.transition(TokeniserState$10.Data);
                break;
            }
            case '\0': {
                t.tagPending.appendTagName(TokeniserState.access$300());
                break;
            }
            case '\uffff': {
                t.eofError(this);
                t.transition(TokeniserState$10.Data);
                break;
            }
        }
    }
}