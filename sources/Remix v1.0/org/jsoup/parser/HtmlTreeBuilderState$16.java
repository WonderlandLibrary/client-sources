package org.jsoup.parser;

import org.jsoup.helper.*;

enum HtmlTreeBuilderState$16
{
    @Override
    boolean process(final Token t, final HtmlTreeBuilder tb) {
        switch (t.type) {
            case Character: {
                final Token.Character c = t.asCharacter();
                if (c.getData().equals(HtmlTreeBuilderState.access$400())) {
                    tb.error(this);
                    return false;
                }
                tb.insert(c);
                break;
            }
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
                    return tb.process(start, HtmlTreeBuilderState$16.InBody);
                }
                if (name.equals("option")) {
                    if (tb.currentElement().nodeName().equals("option")) {
                        tb.processEndTag("option");
                    }
                    tb.insert(start);
                    break;
                }
                if (name.equals("optgroup")) {
                    if (tb.currentElement().nodeName().equals("option")) {
                        tb.processEndTag("option");
                    }
                    else if (tb.currentElement().nodeName().equals("optgroup")) {
                        tb.processEndTag("optgroup");
                    }
                    tb.insert(start);
                    break;
                }
                if (name.equals("select")) {
                    tb.error(this);
                    return tb.processEndTag("select");
                }
                if (StringUtil.in(name, "input", "keygen", "textarea")) {
                    tb.error(this);
                    if (!tb.inSelectScope("select")) {
                        return false;
                    }
                    tb.processEndTag("select");
                    return tb.process(start);
                }
                else {
                    if (name.equals("script")) {
                        return tb.process(t, HtmlTreeBuilderState$16.InHead);
                    }
                    return this.anythingElse(t, tb);
                }
                break;
            }
            case EndTag: {
                final Token.EndTag end = t.asEndTag();
                final String name = end.normalName();
                if (name.equals("optgroup")) {
                    if (tb.currentElement().nodeName().equals("option") && tb.aboveOnStack(tb.currentElement()) != null && tb.aboveOnStack(tb.currentElement()).nodeName().equals("optgroup")) {
                        tb.processEndTag("option");
                    }
                    if (tb.currentElement().nodeName().equals("optgroup")) {
                        tb.pop();
                        break;
                    }
                    tb.error(this);
                    break;
                }
                else if (name.equals("option")) {
                    if (tb.currentElement().nodeName().equals("option")) {
                        tb.pop();
                        break;
                    }
                    tb.error(this);
                    break;
                }
                else {
                    if (!name.equals("select")) {
                        return this.anythingElse(t, tb);
                    }
                    if (!tb.inSelectScope(name)) {
                        tb.error(this);
                        return false;
                    }
                    tb.popStackToClose(name);
                    tb.resetInsertionMode();
                    break;
                }
                break;
            }
            case EOF: {
                if (!tb.currentElement().nodeName().equals("html")) {
                    tb.error(this);
                    break;
                }
                break;
            }
            default: {
                return this.anythingElse(t, tb);
            }
        }
        return true;
    }
    
    private boolean anythingElse(final Token t, final HtmlTreeBuilder tb) {
        tb.error(this);
        return false;
    }
}