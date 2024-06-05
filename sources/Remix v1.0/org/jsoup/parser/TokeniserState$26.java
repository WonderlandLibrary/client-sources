package org.jsoup.parser;

enum TokeniserState$26
{
    @Override
    void read(final Tokeniser t, final CharacterReader r) {
        if (r.matchesLetter()) {
            t.createTagPending(false);
            t.tagPending.appendTagName(r.current());
            t.dataBuffer.append(r.current());
            t.advanceTransition(TokeniserState$26.ScriptDataEscapedEndTagName);
        }
        else {
            t.emit("</");
            t.transition(TokeniserState$26.ScriptDataEscaped);
        }
    }
}