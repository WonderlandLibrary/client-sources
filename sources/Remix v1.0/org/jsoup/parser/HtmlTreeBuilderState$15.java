package org.jsoup.parser;

import org.jsoup.helper.*;

enum HtmlTreeBuilderState$15
{
    @Override
    boolean process(final Token t, final HtmlTreeBuilder tb) {
        if (t.isEndTag()) {
            final Token.EndTag endTag = t.asEndTag();
            final String name = endTag.normalName();
            if (StringUtil.in(name, "td", "th")) {
                if (!tb.inTableScope(name)) {
                    tb.error(this);
                    tb.transition(HtmlTreeBuilderState$15.InRow);
                    return false;
                }
                tb.generateImpliedEndTags();
                if (!tb.currentElement().nodeName().equals(name)) {
                    tb.error(this);
                }
                tb.popStackToClose(name);
                tb.clearFormattingElementsToLastMarker();
                tb.transition(HtmlTreeBuilderState$15.InRow);
                return true;
            }
            else {
                if (StringUtil.in(name, "body", "caption", "col", "colgroup", "html")) {
                    tb.error(this);
                    return false;
                }
                if (!StringUtil.in(name, "table", "tbody", "tfoot", "thead", "tr")) {
                    return this.anythingElse(t, tb);
                }
                if (!tb.inTableScope(name)) {
                    tb.error(this);
                    return false;
                }
                this.closeCell(tb);
                return tb.process(t);
            }
        }
        else {
            if (!t.isStartTag() || !StringUtil.in(t.asStartTag().normalName(), "caption", "col", "colgroup", "tbody", "td", "tfoot", "th", "thead", "tr")) {
                return this.anythingElse(t, tb);
            }
            if (!tb.inTableScope("td") && !tb.inTableScope("th")) {
                tb.error(this);
                return false;
            }
            this.closeCell(tb);
            return tb.process(t);
        }
    }
    
    private boolean anythingElse(final Token t, final HtmlTreeBuilder tb) {
        return tb.process(t, HtmlTreeBuilderState$15.InBody);
    }
    
    private void closeCell(final HtmlTreeBuilder tb) {
        if (tb.inTableScope("td")) {
            tb.processEndTag("td");
        }
        else {
            tb.processEndTag("th");
        }
    }
}