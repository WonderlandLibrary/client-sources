package org.jsoup.parser;

import org.jsoup.helper.*;

enum HtmlTreeBuilderState$2
{
    @Override
    boolean process(final Token t, final HtmlTreeBuilder tb) {
        if (t.isDoctype()) {
            tb.error(this);
            return false;
        }
        if (t.isComment()) {
            tb.insert(t.asComment());
        }
        else {
            if (HtmlTreeBuilderState.access$100(t)) {
                return true;
            }
            if (t.isStartTag() && t.asStartTag().normalName().equals("html")) {
                tb.insert(t.asStartTag());
                tb.transition(HtmlTreeBuilderState$2.BeforeHead);
            }
            else {
                if (t.isEndTag() && StringUtil.in(t.asEndTag().normalName(), "head", "body", "html", "br")) {
                    return this.anythingElse(t, tb);
                }
                if (t.isEndTag()) {
                    tb.error(this);
                    return false;
                }
                return this.anythingElse(t, tb);
            }
        }
        return true;
    }
    
    private boolean anythingElse(final Token t, final HtmlTreeBuilder tb) {
        tb.insertStartTag("html");
        tb.transition(HtmlTreeBuilderState$2.BeforeHead);
        return tb.process(t);
    }
}