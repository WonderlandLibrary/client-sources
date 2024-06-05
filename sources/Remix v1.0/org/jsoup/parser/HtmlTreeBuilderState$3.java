package org.jsoup.parser;

import org.jsoup.helper.*;
import org.jsoup.nodes.*;

enum HtmlTreeBuilderState$3
{
    @Override
    boolean process(final Token t, final HtmlTreeBuilder tb) {
        if (HtmlTreeBuilderState.access$100(t)) {
            return true;
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
                return HtmlTreeBuilderState$3.InBody.process(t, tb);
            }
            if (t.isStartTag() && t.asStartTag().normalName().equals("head")) {
                final Element head = tb.insert(t.asStartTag());
                tb.setHeadElement(head);
                tb.transition(HtmlTreeBuilderState$3.InHead);
            }
            else {
                if (t.isEndTag() && StringUtil.in(t.asEndTag().normalName(), "head", "body", "html", "br")) {
                    tb.processStartTag("head");
                    return tb.process(t);
                }
                if (t.isEndTag()) {
                    tb.error(this);
                    return false;
                }
                tb.processStartTag("head");
                return tb.process(t);
            }
        }
        return true;
    }
}