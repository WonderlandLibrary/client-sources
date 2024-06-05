package org.jsoup.parser;

enum TokeniserState$66
{
    @Override
    void read(final Tokeniser t, final CharacterReader r) {
        final char c = r.consume();
        switch (c) {
            case '>': {
                t.emitDoctypePending();
                t.transition(TokeniserState$66.Data);
                break;
            }
            case '\uffff': {
                t.emitDoctypePending();
                t.transition(TokeniserState$66.Data);
                break;
            }
        }
    }
}