package org.jsoup.parser;

enum TokeniserState$51
{
    @Override
    void read(final Tokeniser t, final CharacterReader r) {
        final char c = r.consume();
        switch (c) {
            case '\t':
            case '\n':
            case '\f':
            case '\r':
            case ' ': {
                t.transition(TokeniserState$51.BeforeDoctypeName);
                break;
            }
            case '\uffff': {
                t.eofError(this);
            }
            case '>': {
                t.error(this);
                t.createDoctypePending();
                t.doctypePending.forceQuirks = true;
                t.emitDoctypePending();
                t.transition(TokeniserState$51.Data);
                break;
            }
            default: {
                t.error(this);
                t.transition(TokeniserState$51.BeforeDoctypeName);
                break;
            }
        }
    }
}