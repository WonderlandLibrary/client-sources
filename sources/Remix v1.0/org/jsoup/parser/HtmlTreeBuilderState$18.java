package org.jsoup.parser;

enum HtmlTreeBuilderState$18
{
    @Override
    boolean process(final Token t, final HtmlTreeBuilder tb) {
        if (HtmlTreeBuilderState.access$100(t)) {
            return tb.process(t, HtmlTreeBuilderState$18.InBody);
        }
        if (t.isComment()) {
            tb.insert(t.asComment());
        }
        else {
            if (t.isDoctype()) {
                tb.error(this);
                return false;
            }
            if (t.isStartTag() && t.asStartTag().normalName().equals("html")) {
                return tb.process(t, HtmlTreeBuilderState$18.InBody);
            }
            if (t.isEndTag() && t.asEndTag().normalName().equals("html")) {
                if (tb.isFragmentParsing()) {
                    tb.error(this);
                    return false;
                }
                tb.transition(HtmlTreeBuilderState$18.AfterAfterBody);
            }
            else if (!t.isEOF()) {
                tb.error(this);
                tb.transition(HtmlTreeBuilderState$18.InBody);
                return tb.process(t);
            }
        }
        return true;
    }
}