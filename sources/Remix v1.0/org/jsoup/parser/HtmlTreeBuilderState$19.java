package org.jsoup.parser;

enum HtmlTreeBuilderState$19
{
    @Override
    boolean process(final Token t, final HtmlTreeBuilder tb) {
        if (HtmlTreeBuilderState.access$100(t)) {
            tb.insert(t.asCharacter());
        }
        else if (t.isComment()) {
            tb.insert(t.asComment());
        }
        else {
            if (t.isDoctype()) {
                tb.error(this);
                return false;
            }
            if (t.isStartTag()) {
                final Token.StartTag start = t.asStartTag();
                final String name = start.normalName();
                if (name.equals("html")) {
                    return tb.process(start, HtmlTreeBuilderState$19.InBody);
                }
                if (name.equals("frameset")) {
                    tb.insert(start);
                }
                else if (name.equals("frame")) {
                    tb.insertEmpty(start);
                }
                else {
                    if (name.equals("noframes")) {
                        return tb.process(start, HtmlTreeBuilderState$19.InHead);
                    }
                    tb.error(this);
                    return false;
                }
            }
            else if (t.isEndTag() && t.asEndTag().normalName().equals("frameset")) {
                if (tb.currentElement().nodeName().equals("html")) {
                    tb.error(this);
                    return false;
                }
                tb.pop();
                if (!tb.isFragmentParsing() && !tb.currentElement().nodeName().equals("frameset")) {
                    tb.transition(HtmlTreeBuilderState$19.AfterFrameset);
                }
            }
            else {
                if (!t.isEOF()) {
                    tb.error(this);
                    return false;
                }
                if (!tb.currentElement().nodeName().equals("html")) {
                    tb.error(this);
                    return true;
                }
            }
        }
        return true;
    }
}