package org.jsoup.parser;

enum HtmlTreeBuilderState$8
{
    @Override
    boolean process(final Token t, final HtmlTreeBuilder tb) {
        if (t.isCharacter()) {
            tb.insert(t.asCharacter());
        }
        else {
            if (t.isEOF()) {
                tb.error(this);
                tb.pop();
                tb.transition(tb.originalState());
                return tb.process(t);
            }
            if (t.isEndTag()) {
                tb.pop();
                tb.transition(tb.originalState());
            }
        }
        return true;
    }
}