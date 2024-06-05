package org.jsoup.parser;

enum TokeniserState$3
{
    @Override
    void read(final Tokeniser t, final CharacterReader r) {
        switch (r.current()) {
            case '&': {
                t.advanceTransition(TokeniserState$3.CharacterReferenceInRcdata);
                break;
            }
            case '<': {
                t.advanceTransition(TokeniserState$3.RcdataLessthanSign);
                break;
            }
            case '\0': {
                t.error(this);
                r.advance();
                t.emit('\ufffd');
                break;
            }
            case '\uffff': {
                t.emit(new Token.EOF());
                break;
            }
            default: {
                final String data = r.consumeToAny('&', '<', '\0');
                t.emit(data);
                break;
            }
        }
    }
}