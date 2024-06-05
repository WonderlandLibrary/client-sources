package org.jsoup.parser;

import org.jsoup.helper.*;
import org.jsoup.nodes.*;
import java.util.*;

enum HtmlTreeBuilderState
{
    Initial {
        @Override
        boolean process(final Token t, final HtmlTreeBuilder tb) {
            if (isWhitespace(t)) {
                return true;
            }
            if (t.isComment()) {
                tb.insert(t.asComment());
            }
            else {
                if (!t.isDoctype()) {
                    tb.transition(HtmlTreeBuilderState$1.BeforeHtml);
                    return tb.process(t);
                }
                final Token.Doctype d = t.asDoctype();
                final DocumentType doctype = new DocumentType(tb.settings.normalizeTag(d.getName()), d.getPubSysKey(), d.getPublicIdentifier(), d.getSystemIdentifier(), tb.getBaseUri());
                tb.getDocument().appendChild(doctype);
                if (d.isForceQuirks()) {
                    tb.getDocument().quirksMode(Document.QuirksMode.quirks);
                }
                tb.transition(HtmlTreeBuilderState$1.BeforeHtml);
            }
            return true;
        }
    }, 
    BeforeHtml {
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
                if (isWhitespace(t)) {
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
    }, 
    BeforeHead {
        @Override
        boolean process(final Token t, final HtmlTreeBuilder tb) {
            if (isWhitespace(t)) {
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
    }, 
    InHead {
        @Override
        boolean process(final Token t, final HtmlTreeBuilder tb) {
            if (isWhitespace(t)) {
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
                        handleRcData(start, tb);
                        break;
                    }
                    if (StringUtil.in(name, "noframes", "style")) {
                        handleRawtext(start, tb);
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
    }, 
    InHeadNoscript {
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
                    if (isWhitespace(t) || t.isComment() || (t.isStartTag() && StringUtil.in(t.asStartTag().normalName(), "basefont", "bgsound", "link", "meta", "noframes", "style"))) {
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
    }, 
    AfterHead {
        @Override
        boolean process(final Token t, final HtmlTreeBuilder tb) {
            if (isWhitespace(t)) {
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
    }, 
    InBody {
        @Override
        boolean process(final Token t, final HtmlTreeBuilder tb) {
            switch (t.type) {
                case Character: {
                    final Token.Character c = t.asCharacter();
                    if (c.getData().equals(HtmlTreeBuilderState.nullString)) {
                        tb.error(this);
                        return false;
                    }
                    if (tb.framesetOk() && isWhitespace(c)) {
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
                                        handleRawtext(startTag, tb);
                                        break;
                                    }
                                    if (name.equals("iframe")) {
                                        tb.framesetOk(false);
                                        handleRawtext(startTag, tb);
                                        break;
                                    }
                                    if (name.equals("noembed")) {
                                        handleRawtext(startTag, tb);
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
    }, 
    Text {
        @Override
        boolean process(final Token t, final HtmlTreeBuilder tb) {
            if (t.isCharacter()) {
                tb.insert(t.asCharacter());
            }
            else {
                if (t.isEOF()) {
                    tb.error(this);
                    tb.pop();
                    tb.transition(tb.originalState());
                    return tb.process(t);
                }
                if (t.isEndTag()) {
                    tb.pop();
                    tb.transition(tb.originalState());
                }
            }
            return true;
        }
    }, 
    InTable {
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
    }, 
    InTableText {
        @Override
        boolean process(final Token t, final HtmlTreeBuilder tb) {
            switch (t.type) {
                case Character: {
                    final Token.Character c = t.asCharacter();
                    if (c.getData().equals(HtmlTreeBuilderState.nullString)) {
                        tb.error(this);
                        return false;
                    }
                    tb.getPendingTableCharacters().add(c.getData());
                    return true;
                }
                default: {
                    if (tb.getPendingTableCharacters().size() > 0) {
                        for (final String character : tb.getPendingTableCharacters()) {
                            if (!isWhitespace(character)) {
                                tb.error(this);
                                if (StringUtil.in(tb.currentElement().nodeName(), "table", "tbody", "tfoot", "thead", "tr")) {
                                    tb.setFosterInserts(true);
                                    tb.process(new Token.Character().data(character), HtmlTreeBuilderState$10.InBody);
                                    tb.setFosterInserts(false);
                                }
                                else {
                                    tb.process(new Token.Character().data(character), HtmlTreeBuilderState$10.InBody);
                                }
                            }
                            else {
                                tb.insert(new Token.Character().data(character));
                            }
                        }
                        tb.newPendingTableCharacters();
                    }
                    tb.transition(tb.originalState());
                    return tb.process(t);
                }
            }
        }
    }, 
    InCaption {
        @Override
        boolean process(final Token t, final HtmlTreeBuilder tb) {
            if (t.isEndTag() && t.asEndTag().normalName().equals("caption")) {
                final Token.EndTag endTag = t.asEndTag();
                final String name = endTag.normalName();
                if (!tb.inTableScope(name)) {
                    tb.error(this);
                    return false;
                }
                tb.generateImpliedEndTags();
                if (!tb.currentElement().nodeName().equals("caption")) {
                    tb.error(this);
                }
                tb.popStackToClose("caption");
                tb.clearFormattingElementsToLastMarker();
                tb.transition(HtmlTreeBuilderState$11.InTable);
            }
            else if ((t.isStartTag() && StringUtil.in(t.asStartTag().normalName(), "caption", "col", "colgroup", "tbody", "td", "tfoot", "th", "thead", "tr")) || (t.isEndTag() && t.asEndTag().normalName().equals("table"))) {
                tb.error(this);
                final boolean processed = tb.processEndTag("caption");
                if (processed) {
                    return tb.process(t);
                }
            }
            else {
                if (t.isEndTag() && StringUtil.in(t.asEndTag().normalName(), "body", "col", "colgroup", "html", "tbody", "td", "tfoot", "th", "thead", "tr")) {
                    tb.error(this);
                    return false;
                }
                return tb.process(t, HtmlTreeBuilderState$11.InBody);
            }
            return true;
        }
    }, 
    InColumnGroup {
        @Override
        boolean process(final Token t, final HtmlTreeBuilder tb) {
            if (isWhitespace(t)) {
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
    }, 
    InTableBody {
        @Override
        boolean process(final Token t, final HtmlTreeBuilder tb) {
            switch (t.type) {
                case StartTag: {
                    final Token.StartTag startTag = t.asStartTag();
                    final String name = startTag.normalName();
                    if (name.equals("tr")) {
                        tb.clearStackToTableBodyContext();
                        tb.insert(startTag);
                        tb.transition(HtmlTreeBuilderState$13.InRow);
                        break;
                    }
                    if (StringUtil.in(name, "th", "td")) {
                        tb.error(this);
                        tb.processStartTag("tr");
                        return tb.process(startTag);
                    }
                    if (StringUtil.in(name, "caption", "col", "colgroup", "tbody", "tfoot", "thead")) {
                        return this.exitTableBody(t, tb);
                    }
                    return this.anythingElse(t, tb);
                }
                case EndTag: {
                    final Token.EndTag endTag = t.asEndTag();
                    final String name = endTag.normalName();
                    if (StringUtil.in(name, "tbody", "tfoot", "thead")) {
                        if (!tb.inTableScope(name)) {
                            tb.error(this);
                            return false;
                        }
                        tb.clearStackToTableBodyContext();
                        tb.pop();
                        tb.transition(HtmlTreeBuilderState$13.InTable);
                        break;
                    }
                    else {
                        if (name.equals("table")) {
                            return this.exitTableBody(t, tb);
                        }
                        if (StringUtil.in(name, "body", "caption", "col", "colgroup", "html", "td", "th", "tr")) {
                            tb.error(this);
                            return false;
                        }
                        return this.anythingElse(t, tb);
                    }
                    break;
                }
                default: {
                    return this.anythingElse(t, tb);
                }
            }
            return true;
        }
        
        private boolean exitTableBody(final Token t, final HtmlTreeBuilder tb) {
            if (!tb.inTableScope("tbody") && !tb.inTableScope("thead") && !tb.inScope("tfoot")) {
                tb.error(this);
                return false;
            }
            tb.clearStackToTableBodyContext();
            tb.processEndTag(tb.currentElement().nodeName());
            return tb.process(t);
        }
        
        private boolean anythingElse(final Token t, final HtmlTreeBuilder tb) {
            return tb.process(t, HtmlTreeBuilderState$13.InTable);
        }
    }, 
    InRow {
        @Override
        boolean process(final Token t, final HtmlTreeBuilder tb) {
            if (t.isStartTag()) {
                final Token.StartTag startTag = t.asStartTag();
                final String name = startTag.normalName();
                if (StringUtil.in(name, "th", "td")) {
                    tb.clearStackToTableRowContext();
                    tb.insert(startTag);
                    tb.transition(HtmlTreeBuilderState$14.InCell);
                    tb.insertMarkerToFormattingElements();
                }
                else {
                    if (StringUtil.in(name, "caption", "col", "colgroup", "tbody", "tfoot", "thead", "tr")) {
                        return this.handleMissingTr(t, tb);
                    }
                    return this.anythingElse(t, tb);
                }
            }
            else {
                if (!t.isEndTag()) {
                    return this.anythingElse(t, tb);
                }
                final Token.EndTag endTag = t.asEndTag();
                final String name = endTag.normalName();
                if (name.equals("tr")) {
                    if (!tb.inTableScope(name)) {
                        tb.error(this);
                        return false;
                    }
                    tb.clearStackToTableRowContext();
                    tb.pop();
                    tb.transition(HtmlTreeBuilderState$14.InTableBody);
                }
                else {
                    if (name.equals("table")) {
                        return this.handleMissingTr(t, tb);
                    }
                    if (StringUtil.in(name, "tbody", "tfoot", "thead")) {
                        if (!tb.inTableScope(name)) {
                            tb.error(this);
                            return false;
                        }
                        tb.processEndTag("tr");
                        return tb.process(t);
                    }
                    else {
                        if (StringUtil.in(name, "body", "caption", "col", "colgroup", "html", "td", "th")) {
                            tb.error(this);
                            return false;
                        }
                        return this.anythingElse(t, tb);
                    }
                }
            }
            return true;
        }
        
        private boolean anythingElse(final Token t, final HtmlTreeBuilder tb) {
            return tb.process(t, HtmlTreeBuilderState$14.InTable);
        }
        
        private boolean handleMissingTr(final Token t, final TreeBuilder tb) {
            final boolean processed = tb.processEndTag("tr");
            return processed && tb.process(t);
        }
    }, 
    InCell {
        @Override
        boolean process(final Token t, final HtmlTreeBuilder tb) {
            if (t.isEndTag()) {
                final Token.EndTag endTag = t.asEndTag();
                final String name = endTag.normalName();
                if (StringUtil.in(name, "td", "th")) {
                    if (!tb.inTableScope(name)) {
                        tb.error(this);
                        tb.transition(HtmlTreeBuilderState$15.InRow);
                        return false;
                    }
                    tb.generateImpliedEndTags();
                    if (!tb.currentElement().nodeName().equals(name)) {
                        tb.error(this);
                    }
                    tb.popStackToClose(name);
                    tb.clearFormattingElementsToLastMarker();
                    tb.transition(HtmlTreeBuilderState$15.InRow);
                    return true;
                }
                else {
                    if (StringUtil.in(name, "body", "caption", "col", "colgroup", "html")) {
                        tb.error(this);
                        return false;
                    }
                    if (!StringUtil.in(name, "table", "tbody", "tfoot", "thead", "tr")) {
                        return this.anythingElse(t, tb);
                    }
                    if (!tb.inTableScope(name)) {
                        tb.error(this);
                        return false;
                    }
                    this.closeCell(tb);
                    return tb.process(t);
                }
            }
            else {
                if (!t.isStartTag() || !StringUtil.in(t.asStartTag().normalName(), "caption", "col", "colgroup", "tbody", "td", "tfoot", "th", "thead", "tr")) {
                    return this.anythingElse(t, tb);
                }
                if (!tb.inTableScope("td") && !tb.inTableScope("th")) {
                    tb.error(this);
                    return false;
                }
                this.closeCell(tb);
                return tb.process(t);
            }
        }
        
        private boolean anythingElse(final Token t, final HtmlTreeBuilder tb) {
            return tb.process(t, HtmlTreeBuilderState$15.InBody);
        }
        
        private void closeCell(final HtmlTreeBuilder tb) {
            if (tb.inTableScope("td")) {
                tb.processEndTag("td");
            }
            else {
                tb.processEndTag("th");
            }
        }
    }, 
    InSelect {
        @Override
        boolean process(final Token t, final HtmlTreeBuilder tb) {
            switch (t.type) {
                case Character: {
                    final Token.Character c = t.asCharacter();
                    if (c.getData().equals(HtmlTreeBuilderState.nullString)) {
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
    }, 
    InSelectInTable {
        @Override
        boolean process(final Token t, final HtmlTreeBuilder tb) {
            if (t.isStartTag() && StringUtil.in(t.asStartTag().normalName(), "caption", "table", "tbody", "tfoot", "thead", "tr", "td", "th")) {
                tb.error(this);
                tb.processEndTag("select");
                return tb.process(t);
            }
            if (!t.isEndTag() || !StringUtil.in(t.asEndTag().normalName(), "caption", "table", "tbody", "tfoot", "thead", "tr", "td", "th")) {
                return tb.process(t, HtmlTreeBuilderState$17.InSelect);
            }
            tb.error(this);
            if (tb.inTableScope(t.asEndTag().normalName())) {
                tb.processEndTag("select");
                return tb.process(t);
            }
            return false;
        }
    }, 
    AfterBody {
        @Override
        boolean process(final Token t, final HtmlTreeBuilder tb) {
            if (isWhitespace(t)) {
                return tb.process(t, HtmlTreeBuilderState$18.InBody);
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
                    return tb.process(t, HtmlTreeBuilderState$18.InBody);
                }
                if (t.isEndTag() && t.asEndTag().normalName().equals("html")) {
                    if (tb.isFragmentParsing()) {
                        tb.error(this);
                        return false;
                    }
                    tb.transition(HtmlTreeBuilderState$18.AfterAfterBody);
                }
                else if (!t.isEOF()) {
                    tb.error(this);
                    tb.transition(HtmlTreeBuilderState$18.InBody);
                    return tb.process(t);
                }
            }
            return true;
        }
    }, 
    InFrameset {
        @Override
        boolean process(final Token t, final HtmlTreeBuilder tb) {
            if (isWhitespace(t)) {
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
    }, 
    AfterFrameset {
        @Override
        boolean process(final Token t, final HtmlTreeBuilder tb) {
            if (isWhitespace(t)) {
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
                if (t.isStartTag() && t.asStartTag().normalName().equals("html")) {
                    return tb.process(t, HtmlTreeBuilderState$20.InBody);
                }
                if (t.isEndTag() && t.asEndTag().normalName().equals("html")) {
                    tb.transition(HtmlTreeBuilderState$20.AfterAfterFrameset);
                }
                else {
                    if (t.isStartTag() && t.asStartTag().normalName().equals("noframes")) {
                        return tb.process(t, HtmlTreeBuilderState$20.InHead);
                    }
                    if (!t.isEOF()) {
                        tb.error(this);
                        return false;
                    }
                }
            }
            return true;
        }
    }, 
    AfterAfterBody {
        @Override
        boolean process(final Token t, final HtmlTreeBuilder tb) {
            if (t.isComment()) {
                tb.insert(t.asComment());
            }
            else {
                if (t.isDoctype() || isWhitespace(t) || (t.isStartTag() && t.asStartTag().normalName().equals("html"))) {
                    return tb.process(t, HtmlTreeBuilderState$21.InBody);
                }
                if (!t.isEOF()) {
                    tb.error(this);
                    tb.transition(HtmlTreeBuilderState$21.InBody);
                    return tb.process(t);
                }
            }
            return true;
        }
    }, 
    AfterAfterFrameset {
        @Override
        boolean process(final Token t, final HtmlTreeBuilder tb) {
            if (t.isComment()) {
                tb.insert(t.asComment());
            }
            else {
                if (t.isDoctype() || isWhitespace(t) || (t.isStartTag() && t.asStartTag().normalName().equals("html"))) {
                    return tb.process(t, HtmlTreeBuilderState$22.InBody);
                }
                if (!t.isEOF()) {
                    if (t.isStartTag() && t.asStartTag().normalName().equals("noframes")) {
                        return tb.process(t, HtmlTreeBuilderState$22.InHead);
                    }
                    tb.error(this);
                    return false;
                }
            }
            return true;
        }
    }, 
    ForeignContent {
        @Override
        boolean process(final Token t, final HtmlTreeBuilder tb) {
            return true;
        }
    };
    
    private static String nullString;
    
    abstract boolean process(final Token p0, final HtmlTreeBuilder p1);
    
    private static boolean isWhitespace(final Token t) {
        if (t.isCharacter()) {
            final String data = t.asCharacter().getData();
            return isWhitespace(data);
        }
        return false;
    }
    
    private static boolean isWhitespace(final String data) {
        for (int i = 0; i < data.length(); ++i) {
            final char c = data.charAt(i);
            if (!StringUtil.isWhitespace(c)) {
                return false;
            }
        }
        return true;
    }
    
    private static void handleRcData(final Token.StartTag startTag, final HtmlTreeBuilder tb) {
        tb.insert(startTag);
        tb.tokeniser.transition(TokeniserState.Rcdata);
        tb.markInsertionMode();
        tb.transition(HtmlTreeBuilderState.Text);
    }
    
    private static void handleRawtext(final Token.StartTag startTag, final HtmlTreeBuilder tb) {
        tb.insert(startTag);
        tb.tokeniser.transition(TokeniserState.Rawtext);
        tb.markInsertionMode();
        tb.transition(HtmlTreeBuilderState.Text);
    }
    
    static {
        HtmlTreeBuilderState.nullString = String.valueOf('\0');
    }
    
    private static final class Constants
    {
        private static final String[] InBodyStartToHead;
        private static final String[] InBodyStartPClosers;
        private static final String[] Headings;
        private static final String[] InBodyStartPreListing;
        private static final String[] InBodyStartLiBreakers;
        private static final String[] DdDt;
        private static final String[] Formatters;
        private static final String[] InBodyStartApplets;
        private static final String[] InBodyStartEmptyFormatters;
        private static final String[] InBodyStartMedia;
        private static final String[] InBodyStartInputAttribs;
        private static final String[] InBodyStartOptions;
        private static final String[] InBodyStartRuby;
        private static final String[] InBodyStartDrop;
        private static final String[] InBodyEndClosers;
        private static final String[] InBodyEndAdoptionFormatters;
        private static final String[] InBodyEndTableFosters;
        
        static {
            InBodyStartToHead = new String[] { "base", "basefont", "bgsound", "command", "link", "meta", "noframes", "script", "style", "title" };
            InBodyStartPClosers = new String[] { "address", "article", "aside", "blockquote", "center", "details", "dir", "div", "dl", "fieldset", "figcaption", "figure", "footer", "header", "hgroup", "menu", "nav", "ol", "p", "section", "summary", "ul" };
            Headings = new String[] { "h1", "h2", "h3", "h4", "h5", "h6" };
            InBodyStartPreListing = new String[] { "pre", "listing" };
            InBodyStartLiBreakers = new String[] { "address", "div", "p" };
            DdDt = new String[] { "dd", "dt" };
            Formatters = new String[] { "b", "big", "code", "em", "font", "i", "s", "small", "strike", "strong", "tt", "u" };
            InBodyStartApplets = new String[] { "applet", "marquee", "object" };
            InBodyStartEmptyFormatters = new String[] { "area", "br", "embed", "img", "keygen", "wbr" };
            InBodyStartMedia = new String[] { "param", "source", "track" };
            InBodyStartInputAttribs = new String[] { "name", "action", "prompt" };
            InBodyStartOptions = new String[] { "optgroup", "option" };
            InBodyStartRuby = new String[] { "rp", "rt" };
            InBodyStartDrop = new String[] { "caption", "col", "colgroup", "frame", "head", "tbody", "td", "tfoot", "th", "thead", "tr" };
            InBodyEndClosers = new String[] { "address", "article", "aside", "blockquote", "button", "center", "details", "dir", "div", "dl", "fieldset", "figcaption", "figure", "footer", "header", "hgroup", "listing", "menu", "nav", "ol", "pre", "section", "summary", "ul" };
            InBodyEndAdoptionFormatters = new String[] { "a", "b", "big", "code", "em", "font", "i", "nobr", "s", "small", "strike", "strong", "tt", "u" };
            InBodyEndTableFosters = new String[] { "table", "tbody", "tfoot", "thead", "tr" };
        }
    }
}
