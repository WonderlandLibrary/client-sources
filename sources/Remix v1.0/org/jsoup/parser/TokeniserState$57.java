package org.jsoup.parser;

enum TokeniserState$57
{
    @Override
    void read(final Tokeniser t, final CharacterReader r) {
        final char c = r.consume();
        switch (c) {
            case '\"': {
                t.transition(TokeniserState$57.AfterDoctypePublicIdentifier);
                break;
            }
            case '\0': {
                t.error(this);
                t.doctypePending.publicIdentifier.append('\ufffd');
                break;
            }
            case '>': {
                t.error(this);
                t.doctypePending.forceQuirks = true;
                t.emitDoctypePending();
                t.transition(TokeniserState$57.Data);
                break;
            }
            case '\uffff': {
                t.eofError(this);
                t.doctypePending.forceQuirks = true;
                t.emitDoctypePending();
                t.transition(TokeniserState$57.Data);
                break;
            }
            default: {
                t.doctypePending.publicIdentifier.append(c);
                break;
            }
        }
    }
}