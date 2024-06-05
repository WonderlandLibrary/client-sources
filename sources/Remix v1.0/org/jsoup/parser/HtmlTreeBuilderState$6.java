package org.jsoup.parser;

import org.jsoup.helper.*;
import org.jsoup.nodes.*;

enum HtmlTreeBuilderState$6
{
    @Override
    boolean process(final Token t, final HtmlTreeBuilder tb) {
        if (HtmlTreeBuilderState.access$100(t)) {
            tb.insert(t.asCharacter());
        }
        else if (t.isComment()) {
            tb.insert(t.asComment());
        }
        else if (t.isDoctype()) {
            tb.error(this);
        }
        else if (t.isStartTag()) {
            final Token.StartTag startTag = t.asStartTag();
            final String name = startTag.normalName();
            if (name.equals("html")) {
                return tb.process(t, HtmlTreeBuilderState$6.InBody);
            }
            if (name.equals("body")) {
                tb.insert(startTag);
                tb.framesetOk(false);
                tb.transition(HtmlTreeBuilderState$6.InBody);
            }
            else if (name.equals("frameset")) {
                tb.insert(startTag);
                tb.transition(HtmlTreeBuilderState$6.InFrameset);
            }
            else if (StringUtil.in(name, "base", "basefont", "bgsound", "link", "meta", "noframes", "script", "style", "title")) {
                tb.error(this);
                final Element head = tb.getHeadElement();
                tb.push(head);
                tb.process(t, HtmlTreeBuilderState$6.InHead);
                tb.removeFromStack(head);
            }
            else {
                if (name.equals("head")) {
                    tb.error(this);
                    return false;
                }
                this.anythingElse(t, tb);
            }
        }
        else if (t.isEndTag()) {
            if (!StringUtil.in(t.asEndTag().normalName(), "body", "html")) {
                tb.error(this);
                return false;
            }
            this.anythingElse(t, tb);
        }
        else {
            this.anythingElse(t, tb);
        }
        return true;
    }
    
    private boolean anythingElse(final Token t, final HtmlTreeBuilder tb) {
        tb.processStartTag("body");
        tb.framesetOk(true);
        return tb.process(t);
    }
}