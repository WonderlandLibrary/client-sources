package org.jsoup.parser;

import org.jsoup.helper.*;
import org.jsoup.nodes.*;

enum HtmlTreeBuilderState$4
{
    @Override
    boolean process(final Token t, final HtmlTreeBuilder tb) {
        if (HtmlTreeBuilderState.access$100(t)) {
            tb.insert(t.asCharacter());
            return true;
        }
        switch (t.type) {
            case Comment: {
                tb.insert(t.asComment());
                break;
            }
            case Doctype: {
                tb.error(this);
                return false;
            }
            case StartTag: {
                final Token.StartTag start = t.asStartTag();
                final String name = start.normalName();
                if (name.equals("html")) {
                    return HtmlTreeBuilderState$4.InBody.process(t, tb);
                }
                if (StringUtil.in(name, "base", "basefont", "bgsound", "command", "link")) {
                    final Element el = tb.insertEmpty(start);
                    if (name.equals("base") && el.hasAttr("href")) {
                        tb.maybeSetBaseUri(el);
                    }
                    break;
                }
                if (name.equals("meta")) {
                    tb.insertEmpty(start);
                    break;
                }
                if (name.equals("title")) {
                    HtmlTreeBuilderState.access$200(start, tb);
                    break;
                }
                if (StringUtil.in(name, "noframes", "style")) {
                    HtmlTreeBuilderState.access$300(start, tb);
                    break;
                }
                if (name.equals("noscript")) {
                    tb.insert(start);
                    tb.transition(HtmlTreeBuilderState$4.InHeadNoscript);
                    break;
                }
                if (name.equals("script")) {
                    tb.tokeniser.transition(TokeniserState.ScriptData);
                    tb.markInsertionMode();
                    tb.transition(HtmlTreeBuilderState$4.Text);
                    tb.insert(start);
                    break;
                }
                if (name.equals("head")) {
                    tb.error(this);
                    return false;
                }
                return this.anythingElse(t, tb);
            }
            case EndTag: {
                final Token.EndTag end = t.asEndTag();
                final String name = end.normalName();
                if (name.equals("head")) {
                    tb.pop();
                    tb.transition(HtmlTreeBuilderState$4.AfterHead);
                    break;
                }
                if (StringUtil.in(name, "body", "html", "br")) {
                    return this.anythingElse(t, tb);
                }
                tb.error(this);
                return false;
            }
            default: {
                return this.anythingElse(t, tb);
            }
        }
        return true;
    }
    
    private boolean anythingElse(final Token t, final TreeBuilder tb) {
        tb.processEndTag("head");
        return tb.process(t);
    }
}