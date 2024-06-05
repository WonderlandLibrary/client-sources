package org.jsoup.parser;

enum TokeniserState$8
{
    @Override
    void read(final Tokeniser t, final CharacterReader r) {
        switch (r.current()) {
            case '!': {
                t.advanceTransition(TokeniserState$8.MarkupDeclarationOpen);
                break;
            }
            case '/': {
                t.advanceTransition(TokeniserState$8.EndTagOpen);
                break;
            }
            case '?': {
                t.advanceTransition(TokeniserState$8.BogusComment);
                break;
            }
            default: {
                if (r.matchesLetter()) {
                    t.createTagPending(true);
                    t.transition(TokeniserState$8.TagName);
                    break;
                }
                t.error(this);
                t.emit('<');
                t.transition(TokeniserState$8.Data);
                break;
            }
        }
    }
}