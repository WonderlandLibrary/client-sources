package org.jsoup.parser;

enum TokeniserState$35
{
    @Override
    void read(final Tokeniser t, final CharacterReader r) {
        final String name = r.consumeToAnySorted(TokeniserState.access$700());
        t.tagPending.appendAttributeName(name);
        final char c = r.consume();
        switch (c) {
            case '\t':
            case '\n':
            case '\f':
            case '\r':
            case ' ': {
                t.transition(TokeniserState$35.AfterAttributeName);
                break;
            }
            case '/': {
                t.transition(TokeniserState$35.SelfClosingStartTag);
                break;
            }
            case '=': {
                t.transition(TokeniserState$35.BeforeAttributeValue);
                break;
            }
            case '>': {
                t.emitTagPending();
                t.transition(TokeniserState$35.Data);
                break;
            }
            case '\0': {
                t.error(this);
                t.tagPending.appendAttributeName('\ufffd');
                break;
            }
            case '\uffff': {
                t.eofError(this);
                t.transition(TokeniserState$35.Data);
                break;
            }
            case '\"':
            case '\'':
            case '<': {
                t.error(this);
                t.tagPending.appendAttributeName(c);
                break;
            }
        }
    }
}