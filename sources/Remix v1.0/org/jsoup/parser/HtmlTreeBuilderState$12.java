package org.jsoup.parser;

enum HtmlTreeBuilderState$12
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
                break;
            }
            case StartTag: {
                final Token.StartTag startTag = t.asStartTag();
                final String name = startTag.normalName();
                if (name.equals("html")) {
                    return tb.process(t, HtmlTreeBuilderState$12.InBody);
                }
                if (name.equals("col")) {
                    tb.insertEmpty(startTag);
                    break;
                }
                return this.anythingElse(t, tb);
            }
            case EndTag: {
                final Token.EndTag endTag = t.asEndTag();
                final String name = endTag.normalName();
                if (!name.equals("colgroup")) {
                    return this.anythingElse(t, tb);
                }
                if (tb.currentElement().nodeName().equals("html")) {
                    tb.error(this);
                    return false;
                }
                tb.pop();
                tb.transition(HtmlTreeBuilderState$12.InTable);
                break;
            }
            case EOF: {
                return tb.currentElement().nodeName().equals("html") || this.anythingElse(t, tb);
            }
            default: {
                return this.anythingElse(t, tb);
            }
        }
        return true;
    }
    
    private boolean anythingElse(final Token t, final TreeBuilder tb) {
        final boolean processed = tb.processEndTag("colgroup");
        return !processed || tb.process(t);
    }
}