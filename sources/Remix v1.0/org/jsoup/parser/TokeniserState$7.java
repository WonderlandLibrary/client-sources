package org.jsoup.parser;

enum TokeniserState$7
{
    @Override
    void read(final Tokeniser t, final CharacterReader r) {
        switch (r.current()) {
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
                final String data = r.consumeTo('\0');
                t.emit(data);
                break;
            }
        }
    }
}