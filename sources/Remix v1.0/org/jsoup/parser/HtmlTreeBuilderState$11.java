package org.jsoup.parser;

import org.jsoup.helper.*;

enum HtmlTreeBuilderState$11
{
    @Override
    boolean process(final Token t, final HtmlTreeBuilder tb) {
        if (t.isEndTag() && t.asEndTag().normalName().equals("caption")) {
            final Token.EndTag endTag = t.asEndTag();
            final String name = endTag.normalName();
            if (!tb.inTableScope(name)) {
                tb.error(this);
                return false;
            }
            tb.generateImpliedEndTags();
            if (!tb.currentElement().nodeName().equals("caption")) {
                tb.error(this);
            }
            tb.popStackToClose("caption");
            tb.clearFormattingElementsToLastMarker();
            tb.transition(HtmlTreeBuilderState$11.InTable);
        }
        else if ((t.isStartTag() && StringUtil.in(t.asStartTag().normalName(), "caption", "col", "colgroup", "tbody", "td", "tfoot", "th", "thead", "tr")) || (t.isEndTag() && t.asEndTag().normalName().equals("table"))) {
            tb.error(this);
            final boolean processed = tb.processEndTag("caption");
            if (processed) {
                return tb.process(t);
            }
        }
        else {
            if (t.isEndTag() && StringUtil.in(t.asEndTag().normalName(), "body", "col", "colgroup", "html", "tbody", "td", "tfoot", "th", "thead", "tr")) {
                tb.error(this);
                return false;
            }
            return tb.process(t, HtmlTreeBuilderState$11.InBody);
        }
        return true;
    }
}