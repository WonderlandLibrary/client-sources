package org.jsoup.parser;

import org.jsoup.helper.*;

enum HtmlTreeBuilderState$13
{
    @Override
    boolean process(final Token t, final HtmlTreeBuilder tb) {
        switch (t.type) {
            case StartTag: {
                final Token.StartTag startTag = t.asStartTag();
                final String name = startTag.normalName();
                if (name.equals("tr")) {
                    tb.clearStackToTableBodyContext();
                    tb.insert(startTag);
                    tb.transition(HtmlTreeBuilderState$13.InRow);
                    break;
                }
                if (StringUtil.in(name, "th", "td")) {
                    tb.error(this);
                    tb.processStartTag("tr");
                    return tb.process(startTag);
                }
                if (StringUtil.in(name, "caption", "col", "colgroup", "tbody", "tfoot", "thead")) {
                    return this.exitTableBody(t, tb);
                }
                return this.anythingElse(t, tb);
            }
            case EndTag: {
                final Token.EndTag endTag = t.asEndTag();
                final String name = endTag.normalName();
                if (StringUtil.in(name, "tbody", "tfoot", "thead")) {
                    if (!tb.inTableScope(name)) {
                        tb.error(this);
                        return false;
                    }
                    tb.clearStackToTableBodyContext();
                    tb.pop();
                    tb.transition(HtmlTreeBuilderState$13.InTable);
                    break;
                }
                else {
                    if (name.equals("table")) {
                        return this.exitTableBody(t, tb);
                    }
                    if (StringUtil.in(name, "body", "caption", "col", "colgroup", "html", "td", "th", "tr")) {
                        tb.error(this);
                        return false;
                    }
                    return this.anythingElse(t, tb);
                }
                break;
            }
            default: {
                return this.anythingElse(t, tb);
            }
        }
        return true;
    }
    
    private boolean exitTableBody(final Token t, final HtmlTreeBuilder tb) {
        if (!tb.inTableScope("tbody") && !tb.inTableScope("thead") && !tb.inScope("tfoot")) {
            tb.error(this);
            return false;
        }
        tb.clearStackToTableBodyContext();
        tb.processEndTag(tb.currentElement().nodeName());
        return tb.process(t);
    }
    
    private boolean anythingElse(final Token t, final HtmlTreeBuilder tb) {
        return tb.process(t, HtmlTreeBuilderState$13.InTable);
    }
}