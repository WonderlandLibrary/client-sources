package org.jsoup.parser;

enum TokeniserState$54
{
    @Override
    void read(final Tokeniser t, final CharacterReader r) {
        if (r.isEmpty()) {
            t.eofError(this);
            t.doctypePending.forceQuirks = true;
            t.emitDoctypePending();
            t.transition(TokeniserState$54.Data);
            return;
        }
        if (r.matchesAny('\t', '\n', '\r', '\f', ' ')) {
            r.advance();
        }
        else if (r.matches('>')) {
            t.emitDoctypePending();
            t.advanceTransition(TokeniserState$54.Data);
        }
        else if (r.matchConsumeIgnoreCase("PUBLIC")) {
            t.doctypePending.pubSysKey = "PUBLIC";
            t.transition(TokeniserState$54.AfterDoctypePublicKeyword);
        }
        else if (r.matchConsumeIgnoreCase("SYSTEM")) {
            t.doctypePending.pubSysKey = "SYSTEM";
            t.transition(TokeniserState$54.AfterDoctypeSystemKeyword);
        }
        else {
            t.error(this);
            t.doctypePending.forceQuirks = true;
            t.advanceTransition(TokeniserState$54.BogusDoctype);
        }
    }
}