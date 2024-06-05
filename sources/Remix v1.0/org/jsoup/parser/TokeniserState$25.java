package org.jsoup.parser;

enum TokeniserState$25
{
    @Override
    void read(final Tokeniser t, final CharacterReader r) {
        if (r.matchesLetter()) {
            t.createTempBuffer();
            t.dataBuffer.append(r.current());
            t.emit("<" + r.current());
            t.advanceTransition(TokeniserState$25.ScriptDataDoubleEscapeStart);
        }
        else if (r.matches('/')) {
            t.createTempBuffer();
            t.advanceTransition(TokeniserState$25.ScriptDataEscapedEndTagOpen);
        }
        else {
            t.emit('<');
            t.transition(TokeniserState$25.ScriptDataEscaped);
        }
    }
}