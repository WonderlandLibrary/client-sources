package org.jsoup.parser;

import org.jsoup.helper.*;

enum HtmlTreeBuilderState$5
{
    @Override
    boolean process(final Token t, final HtmlTreeBuilder tb) {
        if (t.isDoctype()) {
            tb.error(this);
        }
        else {
            if (t.isStartTag() && t.asStartTag().normalName().equals("html")) {
                return tb.process(t, HtmlTreeBuilderState$5.InBody);
            }
            if (t.isEndTag() && t.asEndTag().normalName().equals("noscript")) {
                tb.pop();
                tb.transition(HtmlTreeBuilderState$5.InHead);
            }
            else {
                if (HtmlTreeBuilderState.access$100(t) || t.isComment() || (t.isStartTag() && StringUtil.in(t.asStartTag().normalName(), "basefont", "bgsound", "link", "meta", "noframes", "style"))) {
                    return tb.process(t, HtmlTreeBuilderState$5.InHead);
                }
                if (t.isEndTag() && t.asEndTag().normalName().equals("br")) {
                    return this.anythingElse(t, tb);
                }
                if ((t.isStartTag() && StringUtil.in(t.asStartTag().normalName(), "head", "noscript")) || t.isEndTag()) {
                    tb.error(this);
                    return false;
                }
                return this.anythingElse(t, tb);
            }
        }
        return true;
    }
    
    private boolean anythingElse(final Token t, final HtmlTreeBuilder tb) {
        tb.error(this);
        tb.insert(new Token.Character().data(t.toString()));
        return true;
    }
}