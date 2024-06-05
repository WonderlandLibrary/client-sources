package org.jsoup.parser;

import org.jsoup.helper.*;

enum HtmlTreeBuilderState$17
{
    @Override
    boolean process(final Token t, final HtmlTreeBuilder tb) {
        if (t.isStartTag() && StringUtil.in(t.asStartTag().normalName(), "caption", "table", "tbody", "tfoot", "thead", "tr", "td", "th")) {
            tb.error(this);
            tb.processEndTag("select");
            return tb.process(t);
        }
        if (!t.isEndTag() || !StringUtil.in(t.asEndTag().normalName(), "caption", "table", "tbody", "tfoot", "thead", "tr", "td", "th")) {
            return tb.process(t, HtmlTreeBuilderState$17.InSelect);
        }
        tb.error(this);
        if (tb.inTableScope(t.asEndTag().normalName())) {
            tb.processEndTag("select");
            return tb.process(t);
        }
        return false;
    }
}