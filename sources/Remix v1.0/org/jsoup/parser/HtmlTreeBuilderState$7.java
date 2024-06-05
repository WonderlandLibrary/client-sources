package org.jsoup.parser;

import org.jsoup.helper.*;
import org.jsoup.nodes.*;
import java.util.*;

enum HtmlTreeBuilderState$7
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
                if (tb.framesetOk() && HtmlTreeBuilderState.access$100(c)) {
                    tb.reconstructFormattingElements();
                    tb.insert(c);
                    break;
                }
                tb.reconstructFormattingElements();
                tb.insert(c);
                tb.framesetOk(false);
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
                final Token.StartTag startTag = t.asStartTag();
                final String name = startTag.normalName();
                if (name.equals("a")) {
                    if (tb.getActiveFormattingElement("a") != null) {
                        tb.error(this);
                        tb.processEndTag("a");
                        final Element remainingA = tb.getFromStack("a");
                        if (remainingA != null) {
                            tb.removeFromActiveFormattingElements(remainingA);
                            tb.removeFromStack(remainingA);
                        }
                    }
                    tb.reconstructFormattingElements();
                    final Element a = tb.insert(startTag);
                    tb.pushActiveFormattingElements(a);
                    break;
                }
                if (StringUtil.inSorted(name, Constants.InBodyStartEmptyFormatters)) {
                    tb.reconstructFormattingElements();
                    tb.insertEmpty(startTag);
                    tb.framesetOk(false);
                    break;
                }
                if (StringUtil.inSorted(name, Constants.InBodyStartPClosers)) {
                    if (tb.inButtonScope("p")) {
                        tb.processEndTag("p");
                    }
                    tb.insert(startTag);
                    break;
                }
                if (name.equals("span")) {
                    tb.reconstructFormattingElements();
                    tb.insert(startTag);
                    break;
                }
                if (name.equals("li")) {
                    tb.framesetOk(false);
                    final ArrayList<Element> stack = tb.getStack();
                    for (int i = stack.size() - 1; i > 0; --i) {
                        final Element el = stack.get(i);
                        if (el.nodeName().equals("li")) {
                            tb.processEndTag("li");
                            break;
                        }
                        if (tb.isSpecial(el) && !StringUtil.inSorted(el.nodeName(), Constants.InBodyStartLiBreakers)) {
                            break;
                        }
                    }
                    if (tb.inButtonScope("p")) {
                        tb.processEndTag("p");
                    }
                    tb.insert(startTag);
                    break;
                }
                if (name.equals("html")) {
                    tb.error(this);
                    final Element html = tb.getStack().get(0);
                    for (final Attribute attribute : startTag.getAttributes()) {
                        if (!html.hasAttr(attribute.getKey())) {
                            html.attributes().put(attribute);
                        }
                    }
                    break;
                }
                if (StringUtil.inSorted(name, Constants.InBodyStartToHead)) {
                    return tb.process(t, HtmlTreeBuilderState$7.InHead);
                }
                if (name.equals("body")) {
                    tb.error(this);
                    final ArrayList<Element> stack = tb.getStack();
                    if (stack.size() == 1 || (stack.size() > 2 && !stack.get(1).nodeName().equals("body"))) {
                        return false;
                    }
                    tb.framesetOk(false);
                    final Element body = stack.get(1);
                    for (final Attribute attribute2 : startTag.getAttributes()) {
                        if (!body.hasAttr(attribute2.getKey())) {
                            body.attributes().put(attribute2);
                        }
                    }
                    break;
                }
                else if (name.equals("frameset")) {
                    tb.error(this);
                    final ArrayList<Element> stack = tb.getStack();
                    if (stack.size() == 1 || (stack.size() > 2 && !stack.get(1).nodeName().equals("body"))) {
                        return false;
                    }
                    if (!tb.framesetOk()) {
                        return false;
                    }
                    final Element second = stack.get(1);
                    if (second.parent() != null) {
                        second.remove();
                    }
                    while (stack.size() > 1) {
                        stack.remove(stack.size() - 1);
                    }
                    tb.insert(startTag);
                    tb.transition(HtmlTreeBuilderState$7.InFrameset);
                    break;
                }
                else {
                    if (StringUtil.inSorted(name, Constants.Headings)) {
                        if (tb.inButtonScope("p")) {
                            tb.processEndTag("p");
                        }
                        if (StringUtil.inSorted(tb.currentElement().nodeName(), Constants.Headings)) {
                            tb.error(this);
                            tb.pop();
                        }
                        tb.insert(startTag);
                        break;
                    }
                    if (StringUtil.inSorted(name, Constants.InBodyStartPreListing)) {
                        if (tb.inButtonScope("p")) {
                            tb.processEndTag("p");
                        }
                        tb.insert(startTag);
                        tb.framesetOk(false);
                        break;
                    }
                    if (name.equals("form")) {
                        if (tb.getFormElement() != null) {
                            tb.error(this);
                            return false;
                        }
                        if (tb.inButtonScope("p")) {
                            tb.processEndTag("p");
                        }
                        tb.insertForm(startTag, true);
                        break;
                    }
                    else {
                        if (StringUtil.inSorted(name, Constants.DdDt)) {
                            tb.framesetOk(false);
                            final ArrayList<Element> stack = tb.getStack();
                            for (int i = stack.size() - 1; i > 0; --i) {
                                final Element el = stack.get(i);
                                if (StringUtil.inSorted(el.nodeName(), Constants.DdDt)) {
                                    tb.processEndTag(el.nodeName());
                                    break;
                                }
                                if (tb.isSpecial(el) && !StringUtil.inSorted(el.nodeName(), Constants.InBodyStartLiBreakers)) {
                                    break;
                                }
                            }
                            if (tb.inButtonScope("p")) {
                                tb.processEndTag("p");
                            }
                            tb.insert(startTag);
                            break;
                        }
                        if (name.equals("plaintext")) {
                            if (tb.inButtonScope("p")) {
                                tb.processEndTag("p");
                            }
                            tb.insert(startTag);
                            tb.tokeniser.transition(TokeniserState.PLAINTEXT);
                            break;
                        }
                        if (name.equals("button")) {
                            if (tb.inButtonScope("button")) {
                                tb.error(this);
                                tb.processEndTag("button");
                                tb.process(startTag);
                                break;
                            }
                            tb.reconstructFormattingElements();
                            tb.insert(startTag);
                            tb.framesetOk(false);
                            break;
                        }
                        else {
                            if (StringUtil.inSorted(name, Constants.Formatters)) {
                                tb.reconstructFormattingElements();
                                final Element el2 = tb.insert(startTag);
                                tb.pushActiveFormattingElements(el2);
                                break;
                            }
                            if (name.equals("nobr")) {
                                tb.reconstructFormattingElements();
                                if (tb.inScope("nobr")) {
                                    tb.error(this);
                                    tb.processEndTag("nobr");
                                    tb.reconstructFormattingElements();
                                }
                                final Element el2 = tb.insert(startTag);
                                tb.pushActiveFormattingElements(el2);
                                break;
                            }
                            if (StringUtil.inSorted(name, Constants.InBodyStartApplets)) {
                                tb.reconstructFormattingElements();
                                tb.insert(startTag);
                                tb.insertMarkerToFormattingElements();
                                tb.framesetOk(false);
                                break;
                            }
                            if (name.equals("table")) {
                                if (tb.getDocument().quirksMode() != Document.QuirksMode.quirks && tb.inButtonScope("p")) {
                                    tb.processEndTag("p");
                                }
                                tb.insert(startTag);
                                tb.framesetOk(false);
                                tb.transition(HtmlTreeBuilderState$7.InTable);
                                break;
                            }
                            if (name.equals("input")) {
                                tb.reconstructFormattingElements();
                                final Element el2 = tb.insertEmpty(startTag);
                                if (!el2.attr("type").equalsIgnoreCase("hidden")) {
                                    tb.framesetOk(false);
                                }
                                break;
                            }
                            if (StringUtil.inSorted(name, Constants.InBodyStartMedia)) {
                                tb.insertEmpty(startTag);
                                break;
                            }
                            if (name.equals("hr")) {
                                if (tb.inButtonScope("p")) {
                                    tb.processEndTag("p");
                                }
                                tb.insertEmpty(startTag);
                                tb.framesetOk(false);
                                break;
                            }
                            if (name.equals("image")) {
                                if (tb.getFromStack("svg") == null) {
                                    return tb.process(startTag.name("img"));
                                }
                                tb.insert(startTag);
                                break;
                            }
                            else if (name.equals("isindex")) {
                                tb.error(this);
                                if (tb.getFormElement() != null) {
                                    return false;
                                }
                                tb.tokeniser.acknowledgeSelfClosingFlag();
                                tb.processStartTag("form");
                                if (startTag.attributes.hasKey("action")) {
                                    final Element form = tb.getFormElement();
                                    form.attr("action", startTag.attributes.get("action"));
                                }
                                tb.processStartTag("hr");
                                tb.processStartTag("label");
                                final String prompt = startTag.attributes.hasKey("prompt") ? startTag.attributes.get("prompt") : "This is a searchable index. Enter search keywords: ";
                                tb.process(new Token.Character().data(prompt));
                                final Attributes inputAttribs = new Attributes();
                                for (final Attribute attr : startTag.attributes) {
                                    if (!StringUtil.inSorted(attr.getKey(), Constants.InBodyStartInputAttribs)) {
                                        inputAttribs.put(attr);
                                    }
                                }
                                inputAttribs.put("name", "isindex");
                                tb.processStartTag("input", inputAttribs);
                                tb.processEndTag("label");
                                tb.processStartTag("hr");
                                tb.processEndTag("form");
                                break;
                            }
                            else {
                                if (name.equals("textarea")) {
                                    tb.insert(startTag);
                                    tb.tokeniser.transition(TokeniserState.Rcdata);
                                    tb.markInsertionMode();
                                    tb.framesetOk(false);
                                    tb.transition(HtmlTreeBuilderState$7.Text);
                                    break;
                                }
                                if (name.equals("xmp")) {
                                    if (tb.inButtonScope("p")) {
                                        tb.processEndTag("p");
                                    }
                                    tb.reconstructFormattingElements();
                                    tb.framesetOk(false);
                                    HtmlTreeBuilderState.access$300(startTag, tb);
                                    break;
                                }
                                if (name.equals("iframe")) {
                                    tb.framesetOk(false);
                                    HtmlTreeBuilderState.access$300(startTag, tb);
                                    break;
                                }
                                if (name.equals("noembed")) {
                                    HtmlTreeBuilderState.access$300(startTag, tb);
                                    break;
                                }
                                if (name.equals("select")) {
                                    tb.reconstructFormattingElements();
                                    tb.insert(startTag);
                                    tb.framesetOk(false);
                                    final HtmlTreeBuilderState state = tb.state();
                                    if (state.equals(HtmlTreeBuilderState$7.InTable) || state.equals(HtmlTreeBuilderState$7.InCaption) || state.equals(HtmlTreeBuilderState$7.InTableBody) || state.equals(HtmlTreeBuilderState$7.InRow) || state.equals(HtmlTreeBuilderState$7.InCell)) {
                                        tb.transition(HtmlTreeBuilderState$7.InSelectInTable);
                                    }
                                    else {
                                        tb.transition(HtmlTreeBuilderState$7.InSelect);
                                    }
                                    break;
                                }
                                if (StringUtil.inSorted(name, Constants.InBodyStartOptions)) {
                                    if (tb.currentElement().nodeName().equals("option")) {
                                        tb.processEndTag("option");
                                    }
                                    tb.reconstructFormattingElements();
                                    tb.insert(startTag);
                                    break;
                                }
                                if (StringUtil.inSorted(name, Constants.InBodyStartRuby)) {
                                    if (tb.inScope("ruby")) {
                                        tb.generateImpliedEndTags();
                                        if (!tb.currentElement().nodeName().equals("ruby")) {
                                            tb.error(this);
                                            tb.popStackToBefore("ruby");
                                        }
                                        tb.insert(startTag);
                                        break;
                                    }
                                    break;
                                }
                                else {
                                    if (name.equals("math")) {
                                        tb.reconstructFormattingElements();
                                        tb.insert(startTag);
                                        tb.tokeniser.acknowledgeSelfClosingFlag();
                                        break;
                                    }
                                    if (name.equals("svg")) {
                                        tb.reconstructFormattingElements();
                                        tb.insert(startTag);
                                        tb.tokeniser.acknowledgeSelfClosingFlag();
                                        break;
                                    }
                                    if (StringUtil.inSorted(name, Constants.InBodyStartDrop)) {
                                        tb.error(this);
                                        return false;
                                    }
                                    tb.reconstructFormattingElements();
                                    tb.insert(startTag);
                                    break;
                                }
                            }
                        }
                    }
                }
                break;
            }
            case EndTag: {
                final Token.EndTag endTag = t.asEndTag();
                final String name = endTag.normalName();
                if (StringUtil.inSorted(name, Constants.InBodyEndAdoptionFormatters)) {
                    for (int i = 0; i < 8; ++i) {
                        final Element formatEl = tb.getActiveFormattingElement(name);
                        if (formatEl == null) {
                            return this.anyOtherEndTag(t, tb);
                        }
                        if (!tb.onStack(formatEl)) {
                            tb.error(this);
                            tb.removeFromActiveFormattingElements(formatEl);
                            return true;
                        }
                        if (!tb.inScope(formatEl.nodeName())) {
                            tb.error(this);
                            return false;
                        }
                        if (tb.currentElement() != formatEl) {
                            tb.error(this);
                        }
                        Element furthestBlock = null;
                        Element commonAncestor = null;
                        boolean seenFormattingElement = false;
                        final ArrayList<Element> stack2 = tb.getStack();
                        for (int stackSize = stack2.size(), si = 0; si < stackSize && si < 64; ++si) {
                            final Element el3 = stack2.get(si);
                            if (el3 == formatEl) {
                                commonAncestor = stack2.get(si - 1);
                                seenFormattingElement = true;
                            }
                            else if (seenFormattingElement && tb.isSpecial(el3)) {
                                furthestBlock = el3;
                                break;
                            }
                        }
                        if (furthestBlock == null) {
                            tb.popStackToClose(formatEl.nodeName());
                            tb.removeFromActiveFormattingElements(formatEl);
                            return true;
                        }
                        Element node = furthestBlock;
                        Element lastNode = furthestBlock;
                        for (int j = 0; j < 3; ++j) {
                            if (tb.onStack(node)) {
                                node = tb.aboveOnStack(node);
                            }
                            if (!tb.isInActiveFormattingElements(node)) {
                                tb.removeFromStack(node);
                            }
                            else {
                                if (node == formatEl) {
                                    break;
                                }
                                final Element replacement = new Element(Tag.valueOf(node.nodeName(), ParseSettings.preserveCase), tb.getBaseUri());
                                tb.replaceActiveFormattingElement(node, replacement);
                                tb.replaceOnStack(node, replacement);
                                node = replacement;
                                if (lastNode == furthestBlock) {}
                                if (lastNode.parent() != null) {
                                    lastNode.remove();
                                }
                                node.appendChild(lastNode);
                                lastNode = node;
                            }
                        }
                        if (StringUtil.inSorted(commonAncestor.nodeName(), Constants.InBodyEndTableFosters)) {
                            if (lastNode.parent() != null) {
                                lastNode.remove();
                            }
                            tb.insertInFosterParent(lastNode);
                        }
                        else {
                            if (lastNode.parent() != null) {
                                lastNode.remove();
                            }
                            commonAncestor.appendChild(lastNode);
                        }
                        final Element adopter = new Element(formatEl.tag(), tb.getBaseUri());
                        adopter.attributes().addAll(formatEl.attributes());
                        final Node[] array;
                        final Node[] childNodes = array = furthestBlock.childNodes().toArray(new Node[furthestBlock.childNodeSize()]);
                        for (final Node childNode : array) {
                            adopter.appendChild(childNode);
                        }
                        furthestBlock.appendChild(adopter);
                        tb.removeFromActiveFormattingElements(formatEl);
                        tb.removeFromStack(formatEl);
                        tb.insertOnStackAfter(furthestBlock, adopter);
                    }
                    break;
                }
                if (StringUtil.inSorted(name, Constants.InBodyEndClosers)) {
                    if (!tb.inScope(name)) {
                        tb.error(this);
                        return false;
                    }
                    tb.generateImpliedEndTags();
                    if (!tb.currentElement().nodeName().equals(name)) {
                        tb.error(this);
                    }
                    tb.popStackToClose(name);
                    break;
                }
                else {
                    if (name.equals("span")) {
                        return this.anyOtherEndTag(t, tb);
                    }
                    if (name.equals("li")) {
                        if (!tb.inListItemScope(name)) {
                            tb.error(this);
                            return false;
                        }
                        tb.generateImpliedEndTags(name);
                        if (!tb.currentElement().nodeName().equals(name)) {
                            tb.error(this);
                        }
                        tb.popStackToClose(name);
                        break;
                    }
                    else if (name.equals("body")) {
                        if (!tb.inScope("body")) {
                            tb.error(this);
                            return false;
                        }
                        tb.transition(HtmlTreeBuilderState$7.AfterBody);
                        break;
                    }
                    else if (name.equals("html")) {
                        final boolean notIgnored = tb.processEndTag("body");
                        if (notIgnored) {
                            return tb.process(endTag);
                        }
                        break;
                    }
                    else if (name.equals("form")) {
                        final Element currentForm = tb.getFormElement();
                        tb.setFormElement(null);
                        if (currentForm == null || !tb.inScope(name)) {
                            tb.error(this);
                            return false;
                        }
                        tb.generateImpliedEndTags();
                        if (!tb.currentElement().nodeName().equals(name)) {
                            tb.error(this);
                        }
                        tb.removeFromStack(currentForm);
                        break;
                    }
                    else if (name.equals("p")) {
                        if (!tb.inButtonScope(name)) {
                            tb.error(this);
                            tb.processStartTag(name);
                            return tb.process(endTag);
                        }
                        tb.generateImpliedEndTags(name);
                        if (!tb.currentElement().nodeName().equals(name)) {
                            tb.error(this);
                        }
                        tb.popStackToClose(name);
                        break;
                    }
                    else if (StringUtil.inSorted(name, Constants.DdDt)) {
                        if (!tb.inScope(name)) {
                            tb.error(this);
                            return false;
                        }
                        tb.generateImpliedEndTags(name);
                        if (!tb.currentElement().nodeName().equals(name)) {
                            tb.error(this);
                        }
                        tb.popStackToClose(name);
                        break;
                    }
                    else if (StringUtil.inSorted(name, Constants.Headings)) {
                        if (!tb.inScope(Constants.Headings)) {
                            tb.error(this);
                            return false;
                        }
                        tb.generateImpliedEndTags(name);
                        if (!tb.currentElement().nodeName().equals(name)) {
                            tb.error(this);
                        }
                        tb.popStackToClose(Constants.Headings);
                        break;
                    }
                    else {
                        if (name.equals("sarcasm")) {
                            return this.anyOtherEndTag(t, tb);
                        }
                        if (StringUtil.inSorted(name, Constants.InBodyStartApplets)) {
                            if (tb.inScope("name")) {
                                break;
                            }
                            if (!tb.inScope(name)) {
                                tb.error(this);
                                return false;
                            }
                            tb.generateImpliedEndTags();
                            if (!tb.currentElement().nodeName().equals(name)) {
                                tb.error(this);
                            }
                            tb.popStackToClose(name);
                            tb.clearFormattingElementsToLastMarker();
                            break;
                        }
                        else {
                            if (name.equals("br")) {
                                tb.error(this);
                                tb.processStartTag("br");
                                return false;
                            }
                            return this.anyOtherEndTag(t, tb);
                        }
                    }
                }
                break;
            }
        }
        return true;
    }
    
    boolean anyOtherEndTag(final Token t, final HtmlTreeBuilder tb) {
        final String name = t.asEndTag().name();
        final ArrayList<Element> stack = tb.getStack();
        for (int pos = stack.size() - 1; pos >= 0; --pos) {
            final Element node = stack.get(pos);
            if (node.nodeName().equals(name)) {
                tb.generateImpliedEndTags(name);
                if (!name.equals(tb.currentElement().nodeName())) {
                    tb.error(this);
                }
                tb.popStackToClose(name);
                break;
            }
            if (tb.isSpecial(node)) {
                tb.error(this);
                return false;
            }
        }
        return true;
    }
}