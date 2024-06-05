package org.jsoup.parser;

import org.jsoup.helper.*;

enum HtmlTreeBuilderState$14
{
    @Override
    boolean process(final Token t, final HtmlTreeBuilder tb) {
        if (t.isStartTag()) {
            final Token.StartTag startTag = t.asStartTag();
            final String name = startTag.normalName();
            if (StringUtil.in(name, "th", "td")) {
                tb.clearStackToTableRowContext();
                tb.insert(startTag);
                tb.transition(HtmlTreeBuilderState$14.InCell);
                tb.insertMarkerToFormattingElements();
            }
            else {
                if (StringUtil.in(name, "caption", "col", "colgroup", "tbody", "tfoot", "thead", "tr")) {
                    return this.handleMissingTr(t, tb);
                }
                return this.anythingElse(t, tb);
            }
        }
        else {
            if (!t.isEndTag()) {
                return this.anythingElse(t, tb);
            }
            final Token.EndTag endTag = t.asEndTag();
            final String name = endTag.normalName();
            if (name.equals("tr")) {
                if (!tb.inTableScope(name)) {
                    tb.error(this);
                    return false;
                }
                tb.clearStackToTableRowContext();
                tb.pop();
                tb.transition(HtmlTreeBuilderState$14.InTableBody);
            }
            else {
                if (name.equals("table")) {
                    return this.handleMissingTr(t, tb);
                }
                if (StringUtil.in(name, "tbody", "tfoot", "thead")) {
                    if (!tb.inTableScope(name)) {
                        tb.error(this);
                        return false;
                    }
                    tb.processEndTag("tr");
                    return tb.process(t);
                }
                else {
                    if (StringUtil.in(name, "body", "caption", "col", "colgroup", "html", "td", "th")) {
                        tb.error(this);
                        return false;
                    }
                    return this.anythingElse(t, tb);
                }
            }
        }
        return true;
    }
    
    private boolean anythingElse(final Token t, final HtmlTreeBuilder tb) {
        return tb.process(t, HtmlTreeBuilderState$14.InTable);
    }
    
    private boolean handleMissingTr(final Token t, final TreeBuilder tb) {
        final boolean processed = tb.processEndTag("tr");
        return processed && tb.process(t);
    }
}