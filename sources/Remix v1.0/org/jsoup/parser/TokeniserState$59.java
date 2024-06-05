package org.jsoup.parser;

enum TokeniserState$59
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
                t.transition(TokeniserState$59.BetweenDoctypePublicAndSystemIdentifiers);
                break;
            }
            case '>': {
                t.emitDoctypePending();
                t.transition(TokeniserState$59.Data);
                break;
            }
            case '\"': {
                t.error(this);
                t.transition(TokeniserState$59.DoctypeSystemIdentifier_doubleQuoted);
                break;
            }
            case '\'': {
                t.error(this);
                t.transition(TokeniserState$59.DoctypeSystemIdentifier_singleQuoted);
                break;
            }
            case '\uffff': {
                t.eofError(this);
                t.doctypePending.forceQuirks = true;
                t.emitDoctypePending();
                t.transition(TokeniserState$59.Data);
                break;
            }
            default: {
                t.error(this);
                t.doctypePending.forceQuirks = true;
                t.transition(TokeniserState$59.BogusDoctype);
                break;
            }
        }
    }
}