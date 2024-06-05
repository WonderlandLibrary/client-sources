package org.jsoup.parser;

enum TokeniserState$52
{
    @Override
    void read(final Tokeniser t, final CharacterReader r) {
        if (r.matchesLetter()) {
            t.createDoctypePending();
            t.transition(TokeniserState$52.DoctypeName);
            return;
        }
        final char c = r.consume();
        switch (c) {
            case '\t':
            case '\n':
            case '\f':
            case '\r':
            case ' ': {
                break;
            }
            case '\0': {
                t.error(this);
                t.createDoctypePending();
                t.doctypePending.name.append('\ufffd');
                t.transition(TokeniserState$52.DoctypeName);
                break;
            }
            case '\uffff': {
                t.eofError(this);
                t.createDoctypePending();
                t.doctypePending.forceQuirks = true;
                t.emitDoctypePending();
                t.transition(TokeniserState$52.Data);
                break;
            }
            default: {
                t.createDoctypePending();
                t.doctypePending.name.append(c);
                t.transition(TokeniserState$52.DoctypeName);
                break;
            }
        }
    }
}