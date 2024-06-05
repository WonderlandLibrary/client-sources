package org.jsoup.parser;

enum TokeniserState$11
{
    @Override
    void read(final Tokeniser t, final CharacterReader r) {
        if (r.matches('/')) {
            t.createTempBuffer();
            t.advanceTransition(TokeniserState$11.RCDATAEndTagOpen);
        }
        else if (r.matchesLetter() && t.appropriateEndTagName() != null && !r.containsIgnoreCase("</" + t.appropriateEndTagName())) {
            t.tagPending = t.createTagPending(false).name(t.appropriateEndTagName());
            t.emitTagPending();
            r.unconsume();
            t.transition(TokeniserState$11.Data);
        }
        else {
            t.emit("<");
            t.transition(TokeniserState$11.Rcdata);
        }
    }
}