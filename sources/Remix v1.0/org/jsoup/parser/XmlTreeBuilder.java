package org.jsoup.parser;

import org.jsoup.helper.*;
import org.jsoup.*;
import java.util.*;
import org.jsoup.nodes.*;

public class XmlTreeBuilder extends TreeBuilder
{
    @Override
    ParseSettings defaultSettings() {
        return ParseSettings.preserveCase;
    }
    
    Document parse(final String input, final String baseUri) {
        return this.parse(input, baseUri, ParseErrorList.noTracking(), ParseSettings.preserveCase);
    }
    
    @Override
    protected void initialiseParse(final String input, final String baseUri, final ParseErrorList errors, final ParseSettings settings) {
        super.initialiseParse(input, baseUri, errors, settings);
        this.stack.add(this.doc);
        this.doc.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
    }
    
    @Override
    protected boolean process(final Token token) {
        switch (token.type) {
            case StartTag: {
                this.insert(token.asStartTag());
                break;
            }
            case EndTag: {
                this.popStackToClose(token.asEndTag());
                break;
            }
            case Comment: {
                this.insert(token.asComment());
                break;
            }
            case Character: {
                this.insert(token.asCharacter());
                break;
            }
            case Doctype: {
                this.insert(token.asDoctype());
                break;
            }
            case EOF: {
                break;
            }
            default: {
                Validate.fail("Unexpected token type: " + token.type);
                break;
            }
        }
        return true;
    }
    
    private void insertNode(final Node node) {
        this.currentElement().appendChild(node);
    }
    
    Element insert(final Token.StartTag startTag) {
        final Tag tag = Tag.valueOf(startTag.name(), this.settings);
        final Element el = new Element(tag, this.baseUri, this.settings.normalizeAttributes(startTag.attributes));
        this.insertNode(el);
        if (startTag.isSelfClosing()) {
            this.tokeniser.acknowledgeSelfClosingFlag();
            if (!tag.isKnownTag()) {
                tag.setSelfClosing();
            }
        }
        else {
            this.stack.add(el);
        }
        return el;
    }
    
    void insert(final Token.Comment commentToken) {
        Node insert;
        final Comment comment = (Comment)(insert = new Comment(commentToken.getData(), this.baseUri));
        if (commentToken.bogus) {
            final String data = comment.getData();
            if (data.length() > 1 && (data.startsWith("!") || data.startsWith("?"))) {
                final Document doc = Jsoup.parse("<" + data.substring(1, data.length() - 1) + ">", this.baseUri, Parser.xmlParser());
                final Element el = doc.child(0);
                insert = new XmlDeclaration(this.settings.normalizeTag(el.tagName()), comment.baseUri(), data.startsWith("!"));
                insert.attributes().addAll(el.attributes());
            }
        }
        this.insertNode(insert);
    }
    
    void insert(final Token.Character characterToken) {
        final Node node = new TextNode(characterToken.getData(), this.baseUri);
        this.insertNode(node);
    }
    
    void insert(final Token.Doctype d) {
        final DocumentType doctypeNode = new DocumentType(this.settings.normalizeTag(d.getName()), d.getPubSysKey(), d.getPublicIdentifier(), d.getSystemIdentifier(), this.baseUri);
        this.insertNode(doctypeNode);
    }
    
    private void popStackToClose(final Token.EndTag endTag) {
        final String elName = endTag.name();
        Element firstFound = null;
        for (int pos = this.stack.size() - 1; pos >= 0; --pos) {
            final Element next = this.stack.get(pos);
            if (next.nodeName().equals(elName)) {
                firstFound = next;
                break;
            }
        }
        if (firstFound == null) {
            return;
        }
        for (int pos = this.stack.size() - 1; pos >= 0; --pos) {
            final Element next = this.stack.get(pos);
            this.stack.remove(pos);
            if (next == firstFound) {
                break;
            }
        }
    }
    
    List<Node> parseFragment(final String inputFragment, final String baseUri, final ParseErrorList errors, final ParseSettings settings) {
        this.initialiseParse(inputFragment, baseUri, errors, settings);
        this.runParser();
        return this.doc.childNodes();
    }
}
