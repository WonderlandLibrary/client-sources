package org.jsoup.parser;

enum HtmlTreeBuilderState$22
{
    @Override
    boolean process(final Token t, final HtmlTreeBuilder tb) {
        if (t.isComment()) {
            tb.insert(t.asComment());
        }
        else {
            if (t.isDoctype() || HtmlTreeBuilderState.access$100(t) || (t.isStartTag() && t.asStartTag().normalName().equals("html"))) {
                return tb.process(t, HtmlTreeBuilderState$22.InBody);
            }
            if (!t.isEOF()) {
                if (t.isStartTag() && t.asStartTag().normalName().equals("noframes")) {
                    return tb.process(t, HtmlTreeBuilderState$22.InHead);
                }
                tb.error(this);
                return false;
            }
        }
        return true;
    }
}