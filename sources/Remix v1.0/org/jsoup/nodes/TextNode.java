package org.jsoup.nodes;

import org.jsoup.helper.*;
import java.io.*;

public class TextNode extends Node
{
    private static final String TEXT_KEY = "text";
    String text;
    
    public TextNode(final String text, final String baseUri) {
        this.baseUri = baseUri;
        this.text = text;
    }
    
    @Override
    public String nodeName() {
        return "#text";
    }
    
    public String text() {
        return normaliseWhitespace(this.getWholeText());
    }
    
    public TextNode text(final String text) {
        this.text = text;
        if (this.attributes != null) {
            this.attributes.put("text", text);
        }
        return this;
    }
    
    public String getWholeText() {
        return (this.attributes == null) ? this.text : this.attributes.get("text");
    }
    
    public boolean isBlank() {
        return StringUtil.isBlank(this.getWholeText());
    }
    
    public TextNode splitText(final int offset) {
        Validate.isTrue(offset >= 0, "Split offset must be not be negative");
        Validate.isTrue(offset < this.text.length(), "Split offset must not be greater than current text length");
        final String head = this.getWholeText().substring(0, offset);
        final String tail = this.getWholeText().substring(offset);
        this.text(head);
        final TextNode tailNode = new TextNode(tail, this.baseUri());
        if (this.parent() != null) {
            this.parent().addChildren(this.siblingIndex() + 1, tailNode);
        }
        return tailNode;
    }
    
    @Override
    void outerHtmlHead(final Appendable accum, final int depth, final Document.OutputSettings out) throws IOException {
        if (out.prettyPrint() && ((this.siblingIndex() == 0 && this.parentNode instanceof Element && ((Element)this.parentNode).tag().formatAsBlock() && !this.isBlank()) || (out.outline() && this.siblingNodes().size() > 0 && !this.isBlank()))) {
            this.indent(accum, depth, out);
        }
        final boolean normaliseWhite = out.prettyPrint() && this.parent() instanceof Element && !Element.preserveWhitespace(this.parent());
        Entities.escape(accum, this.getWholeText(), out, false, normaliseWhite, false);
    }
    
    @Override
    void outerHtmlTail(final Appendable accum, final int depth, final Document.OutputSettings out) {
    }
    
    @Override
    public String toString() {
        return this.outerHtml();
    }
    
    public static TextNode createFromEncoded(final String encodedText, final String baseUri) {
        final String text = Entities.unescape(encodedText);
        return new TextNode(text, baseUri);
    }
    
    static String normaliseWhitespace(String text) {
        text = StringUtil.normaliseWhitespace(text);
        return text;
    }
    
    static String stripLeadingWhitespace(final String text) {
        return text.replaceFirst("^\\s+", "");
    }
    
    static boolean lastCharIsWhitespace(final StringBuilder sb) {
        return sb.length() != 0 && sb.charAt(sb.length() - 1) == ' ';
    }
    
    private void ensureAttributes() {
        if (this.attributes == null) {
            (this.attributes = new Attributes()).put("text", this.text);
        }
    }
    
    @Override
    public String attr(final String attributeKey) {
        this.ensureAttributes();
        return super.attr(attributeKey);
    }
    
    @Override
    public Attributes attributes() {
        this.ensureAttributes();
        return super.attributes();
    }
    
    @Override
    public Node attr(final String attributeKey, final String attributeValue) {
        this.ensureAttributes();
        return super.attr(attributeKey, attributeValue);
    }
    
    @Override
    public boolean hasAttr(final String attributeKey) {
        this.ensureAttributes();
        return super.hasAttr(attributeKey);
    }
    
    @Override
    public Node removeAttr(final String attributeKey) {
        this.ensureAttributes();
        return super.removeAttr(attributeKey);
    }
    
    @Override
    public String absUrl(final String attributeKey) {
        this.ensureAttributes();
        return super.absUrl(attributeKey);
    }
}
