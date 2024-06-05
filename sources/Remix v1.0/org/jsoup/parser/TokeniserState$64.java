package org.jsoup.parser;

enum TokeniserState$64
{
    @Override
    void read(final Tokeniser t, final CharacterReader r) {
        final char c = r.consume();
        switch (c) {
            case '\'': {
                t.transition(TokeniserState$64.AfterDoctypeSystemIdentifier);
                break;
            }
            case '\0': {
                t.error(this);
                t.doctypePending.systemIdentifier.append('\ufffd');
                break;
            }
            case '>': {
                t.error(this);
                t.doctypePending.forceQuirks = true;
                t.emitDoctypePending();
                t.transition(TokeniserState$64.Data);
                break;
            }
            case '\uffff': {
                t.eofError(this);
                t.doctypePending.forceQuirks = true;
                t.emitDoctypePending();
                t.transition(TokeniserState$64.Data);
                break;
            }
            default: {
                t.doctypePending.systemIdentifier.append(c);
                break;
            }
        }
    }
}