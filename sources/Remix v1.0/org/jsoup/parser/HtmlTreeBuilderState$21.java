package org.jsoup.parser;

enum HtmlTreeBuilderState$21
{
    @Override
    boolean process(final Token t, final HtmlTreeBuilder tb) {
        if (t.isComment()) {
            tb.insert(t.asComment());
        }
        else {
            if (t.isDoctype() || HtmlTreeBuilderState.access$100(t) || (t.isStartTag() && t.asStartTag().normalName().equals("html"))) {
                return tb.process(t, HtmlTreeBuilderState$21.InBody);
            }
            if (!t.isEOF()) {
                tb.error(this);
                tb.transition(HtmlTreeBuilderState$21.InBody);
                return tb.process(t);
            }
        }
        return true;
    }
}