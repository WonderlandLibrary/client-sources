package org.jsoup.parser;

enum TokeniserState$40
{
    @Override
    void read(final Tokeniser t, final CharacterReader r) {
        final String value = r.consumeToAnySorted(TokeniserState.access$1000());
        if (value.length() > 0) {
            t.tagPending.appendAttributeValue(value);
        }
        final char c = r.consume();
        switch (c) {
            case '\t':
            case '\n':
            case '\f':
            case '\r':
            case ' ': {
                t.transition(TokeniserState$40.BeforeAttributeName);
                break;
            }
            case '&': {
                final int[] ref = t.consumeCharacterReference('>', true);
                if (ref != null) {
                    t.tagPending.appendAttributeValue(ref);
                    break;
                }
                t.tagPending.appendAttributeValue('&');
                break;
            }
            case '>': {
                t.emitTagPending();
                t.transition(TokeniserState$40.Data);
                break;
            }
            case '\0': {
                t.error(this);
                t.tagPending.appendAttributeValue('\ufffd');
                break;
            }
            case '\uffff': {
                t.eofError(this);
                t.transition(TokeniserState$40.Data);
                break;
            }
            case '\"':
            case '\'':
            case '<':
            case '=':
            case '`': {
                t.error(this);
                t.tagPending.appendAttributeValue(c);
                break;
            }
        }
    }
}