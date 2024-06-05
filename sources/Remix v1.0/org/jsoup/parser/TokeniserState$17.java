package org.jsoup.parser;

enum TokeniserState$17
{
    @Override
    void read(final Tokeniser t, final CharacterReader r) {
        switch (r.consume()) {
            case '/': {
                t.createTempBuffer();
                t.transition(TokeniserState$17.ScriptDataEndTagOpen);
                break;
            }
            case '!': {
                t.emit("<!");
                t.transition(TokeniserState$17.ScriptDataEscapeStart);
                break;
            }
            default: {
                t.emit("<");
                r.unconsume();
                t.transition(TokeniserState$17.ScriptData);
                break;
            }
        }
    }
}