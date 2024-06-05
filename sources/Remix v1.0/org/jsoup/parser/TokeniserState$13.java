package org.jsoup.parser;

enum TokeniserState$13
{
    @Override
    void read(final Tokeniser t, final CharacterReader r) {
        if (r.matchesLetter()) {
            final String name = r.consumeLetterSequence();
            t.tagPending.appendTagName(name);
            t.dataBuffer.append(name);
            return;
        }
        final char c = r.consume();
        switch (c) {
            case '\t':
            case '\n':
            case '\f':
            case '\r':
            case ' ': {
                if (t.isAppropriateEndTagToken()) {
                    t.transition(TokeniserState$13.BeforeAttributeName);
                    break;
                }
                this.anythingElse(t, r);
                break;
            }
            case '/': {
                if (t.isAppropriateEndTagToken()) {
                    t.transition(TokeniserState$13.SelfClosingStartTag);
                    break;
                }
                this.anythingElse(t, r);
                break;
            }
            case '>': {
                if (t.isAppropriateEndTagToken()) {
                    t.emitTagPending();
                    t.transition(TokeniserState$13.Data);
                    break;
                }
                this.anythingElse(t, r);
                break;
            }
            default: {
                this.anythingElse(t, r);
                break;
            }
        }
    }
    
    private void anythingElse(final Tokeniser t, final CharacterReader r) {
        t.emit("</" + t.dataBuffer.toString());
        r.unconsume();
        t.transition(TokeniserState$13.Rcdata);
    }
}