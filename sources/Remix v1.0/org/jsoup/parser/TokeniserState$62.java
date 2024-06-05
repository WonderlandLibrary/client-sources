package org.jsoup.parser;

enum TokeniserState$62
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
                break;
            }
            case '\"': {
                t.transition(TokeniserState$62.DoctypeSystemIdentifier_doubleQuoted);
                break;
            }
            case '\'': {
                t.transition(TokeniserState$62.DoctypeSystemIdentifier_singleQuoted);
                break;
            }
            case '>': {
                t.error(this);
                t.doctypePending.forceQuirks = true;
                t.emitDoctypePending();
                t.transition(TokeniserState$62.Data);
                break;
            }
            case '\uffff': {
                t.eofError(this);
                t.doctypePending.forceQuirks = true;
                t.emitDoctypePending();
                t.transition(TokeniserState$62.Data);
                break;
            }
            default: {
                t.error(this);
                t.doctypePending.forceQuirks = true;
                t.transition(TokeniserState$62.BogusDoctype);
                break;
            }
        }
    }
}