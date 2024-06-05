package org.jsoup.parser;

enum TokeniserState$61
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
                t.transition(TokeniserState$61.BeforeDoctypeSystemIdentifier);
                break;
            }
            case '>': {
                t.error(this);
                t.doctypePending.forceQuirks = true;
                t.emitDoctypePending();
                t.transition(TokeniserState$61.Data);
                break;
            }
            case '\"': {
                t.error(this);
                t.transition(TokeniserState$61.DoctypeSystemIdentifier_doubleQuoted);
                break;
            }
            case '\'': {
                t.error(this);
                t.transition(TokeniserState$61.DoctypeSystemIdentifier_singleQuoted);
                break;
            }
            case '\uffff': {
                t.eofError(this);
                t.doctypePending.forceQuirks = true;
                t.emitDoctypePending();
                t.transition(TokeniserState$61.Data);
                break;
            }
            default: {
                t.error(this);
                t.doctypePending.forceQuirks = true;
                t.emitDoctypePending();
                break;
            }
        }
    }
}