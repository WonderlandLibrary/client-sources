package org.jsoup.parser;

enum TokeniserState$55
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
                t.transition(TokeniserState$55.BeforeDoctypePublicIdentifier);
                break;
            }
            case '\"': {
                t.error(this);
                t.transition(TokeniserState$55.DoctypePublicIdentifier_doubleQuoted);
                break;
            }
            case '\'': {
                t.error(this);
                t.transition(TokeniserState$55.DoctypePublicIdentifier_singleQuoted);
                break;
            }
            case '>': {
                t.error(this);
                t.doctypePending.forceQuirks = true;
                t.emitDoctypePending();
                t.transition(TokeniserState$55.Data);
                break;
            }
            case '\uffff': {
                t.eofError(this);
                t.doctypePending.forceQuirks = true;
                t.emitDoctypePending();
                t.transition(TokeniserState$55.Data);
                break;
            }
            default: {
                t.error(this);
                t.doctypePending.forceQuirks = true;
                t.transition(TokeniserState$55.BogusDoctype);
                break;
            }
        }
    }
}