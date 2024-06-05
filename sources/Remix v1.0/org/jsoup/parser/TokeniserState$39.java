package org.jsoup.parser;

enum TokeniserState$39
{
    @Override
    void read(final Tokeniser t, final CharacterReader r) {
        final String value = r.consumeToAny(TokeniserState.access$900());
        if (value.length() > 0) {
            t.tagPending.appendAttributeValue(value);
        }
        else {
            t.tagPending.setEmptyAttributeValue();
        }
        final char c = r.consume();
        switch (c) {
            case '\'': {
                t.transition(TokeniserState$39.AfterAttributeValue_quoted);
                break;
            }
            case '&': {
                final int[] ref = t.consumeCharacterReference('\'', true);
                if (ref != null) {
                    t.tagPending.appendAttributeValue(ref);
                    break;
                }
                t.tagPending.appendAttributeValue('&');
                break;
            }
            case '\0': {
                t.error(this);
                t.tagPending.appendAttributeValue('\ufffd');
                break;
            }
            case '\uffff': {
                t.eofError(this);
                t.transition(TokeniserState$39.Data);
                break;
            }
        }
    }
}