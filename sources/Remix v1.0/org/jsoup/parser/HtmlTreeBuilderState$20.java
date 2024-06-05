package org.jsoup.parser;

enum HtmlTreeBuilderState$20
{
    @Override
    boolean process(final Token t, final HtmlTreeBuilder tb) {
        if (HtmlTreeBuilderState.access$100(t)) {
            tb.insert(t.asCharacter());
        }
        else if (t.isComment()) {
            tb.insert(t.asComment());
        }
        else {
            if (t.isDoctype()) {
                tb.error(this);
                return false;
            }
            if (t.isStartTag() && t.asStartTag().normalName().equals("html")) {
                return tb.process(t, HtmlTreeBuilderState$20.InBody);
            }
            if (t.isEndTag() && t.asEndTag().normalName().equals("html")) {
                tb.transition(HtmlTreeBuilderState$20.AfterAfterFrameset);
            }
            else {
                if (t.isStartTag() && t.asStartTag().normalName().equals("noframes")) {
                    return tb.process(t, HtmlTreeBuilderState$20.InHead);
                }
                if (!t.isEOF()) {
                    tb.error(this);
                    return false;
                }
            }
        }
        return true;
    }
}