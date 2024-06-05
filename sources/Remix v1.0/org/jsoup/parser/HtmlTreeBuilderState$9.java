package org.jsoup.parser;

import org.jsoup.helper.*;

enum HtmlTreeBuilderState$9
{
    @Override
    boolean process(final Token t, final HtmlTreeBuilder tb) {
        if (t.isCharacter()) {
            tb.newPendingTableCharacters();
            tb.markInsertionMode();
            tb.transition(HtmlTreeBuilderState$9.InTableText);
            return tb.process(t);
        }
        if (t.isComment()) {
            tb.insert(t.asComment());
            return true;
        }
        if (t.isDoctype()) {
            tb.error(this);
            return false;
        }
        if (t.isStartTag()) {
            final Token.StartTag startTag = t.asStartTag();
            final String name = startTag.normalName();
            if (name.equals("caption")) {
                tb.clearStackToTableContext();
                tb.insertMarkerToFormattingElements();
                tb.insert(startTag);
                tb.transition(HtmlTreeBuilderState$9.InCaption);
            }
            else if (name.equals("colgroup")) {
                tb.clearStackToTableContext();
                tb.insert(startTag);
                tb.transition(HtmlTreeBuilderState$9.InColumnGroup);
            }
            else {
                if (name.equals("col")) {
                    tb.processStartTag("colgroup");
                    return tb.process(t);
                }
                if (StringUtil.in(name, "tbody", "tfoot", "thead")) {
                    tb.clearStackToTableContext();
                    tb.insert(startTag);
                    tb.transition(HtmlTreeBuilderState$9.InTableBody);
                }
                else {
                    if (StringUtil.in(name, "td", "th", "tr")) {
                        tb.processStartTag("tbody");
                        return tb.process(t);
                    }
                    if (name.equals("table")) {
                        tb.error(this);
                        final boolean processed = tb.processEndTag("table");
                        if (processed) {
                            return tb.process(t);
                        }
                    }
                    else {
                        if (StringUtil.in(name, "style", "script")) {
                            return tb.process(t, HtmlTreeBuilderState$9.InHead);
                        }
                        if (name.equals("input")) {
                            if (!startTag.attributes.get("type").equalsIgnoreCase("hidden")) {
                                return this.anythingElse(t, tb);
                            }
                            tb.insertEmpty(startTag);
                        }
                        else {
                            if (!name.equals("form")) {
                                return this.anythingElse(t, tb);
                            }
                            tb.error(this);
                            if (tb.getFormElement() != null) {
                                return false;
                            }
                            tb.insertForm(startTag, false);
                        }
                    }
                }
            }
            return true;
        }
        if (t.isEndTag()) {
            final Token.EndTag endTag = t.asEndTag();
            final String name = endTag.normalName();
            if (name.equals("table")) {
                if (!tb.inTableScope(name)) {
                    tb.error(this);
                    return false;
                }
                tb.popStackToClose("table");
                tb.resetInsertionMode();
                return true;
            }
            else {
                if (StringUtil.in(name, "body", "caption", "col", "colgroup", "html", "tbody", "td", "tfoot", "th", "thead", "tr")) {
                    tb.error(this);
                    return false;
                }
                return this.anythingElse(t, tb);
            }
        }
        else {
            if (t.isEOF()) {
                if (tb.currentElement().nodeName().equals("html")) {
                    tb.error(this);
                }
                return true;
            }
            return this.anythingElse(t, tb);
        }
    }
    
    boolean anythingElse(final Token t, final HtmlTreeBuilder tb) {
        tb.error(this);
        boolean processed;
        if (StringUtil.in(tb.currentElement().nodeName(), "table", "tbody", "tfoot", "thead", "tr")) {
            tb.setFosterInserts(true);
            processed = tb.process(t, HtmlTreeBuilderState$9.InBody);
            tb.setFosterInserts(false);
        }
        else {
            processed = tb.process(t, HtmlTreeBuilderState$9.InBody);
        }
        return processed;
    }
}