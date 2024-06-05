package org.jsoup.parser;

enum TokeniserState$56
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
                t.transition(TokeniserState$56.DoctypePublicIdentifier_doubleQuoted);
                break;
            }
            case '\'': {
                t.transition(TokeniserState$56.DoctypePublicIdentifier_singleQuoted);
                break;
            }
            case '>': {
                t.error(this);
                t.doctypePending.forceQuirks = true;
                t.emitDoctypePending();
                t.transition(TokeniserState$56.Data);
                break;
            }
            case '\uffff': {
                t.eofError(this);
                t.doctypePending.forceQuirks = true;
                t.emitDoctypePending();
                t.transition(TokeniserState$56.Data);
                break;
            }
            default: {
                t.error(this);
                t.doctypePending.forceQuirks = true;
                t.transition(TokeniserState$56.BogusDoctype);
                break;
            }
        }
    }
}