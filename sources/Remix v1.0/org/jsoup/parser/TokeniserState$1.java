package org.jsoup.parser;

enum TokeniserState$1
{
    @Override
    void read(final Tokeniser t, final CharacterReader r) {
        switch (r.current()) {
            case '&': {
                t.advanceTransition(TokeniserState$1.CharacterReferenceInData);
                break;
            }
            case '<': {
                t.advanceTransition(TokeniserState$1.TagOpen);
                break;
            }
            case '\0': {
                t.error(this);
                t.emit(r.consume());
                break;
            }
            case '\uffff': {
                t.emit(new Token.EOF());
                break;
            }
            default: {
                final String data = r.consumeData();
                t.emit(data);
                break;
            }
        }
    }
}