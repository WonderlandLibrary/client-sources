package org.jsoup.parser;

enum TokeniserState$53
{
    @Override
    void read(final Tokeniser t, final CharacterReader r) {
        if (r.matchesLetter()) {
            final String name = r.consumeLetterSequence();
            t.doctypePending.name.append(name);
            return;
        }
        final char c = r.consume();
        switch (c) {
            case '>': {
                t.emitDoctypePending();
                t.transition(TokeniserState$53.Data);
                break;
            }
            case '\t':
            case '\n':
            case '\f':
            case '\r':
            case ' ': {
                t.transition(TokeniserState$53.AfterDoctypeName);
                break;
            }
            case '\0': {
                t.error(this);
                t.doctypePending.name.append('\ufffd');
                break;
            }
            case '\uffff': {
                t.eofError(this);
                t.doctypePending.forceQuirks = true;
                t.emitDoctypePending();
                t.transition(TokeniserState$53.Data);
                break;
            }
            default: {
                t.doctypePending.name.append(c);
                break;
            }
        }
    }
}