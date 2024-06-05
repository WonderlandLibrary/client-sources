package org.jsoup.nodes;

import org.jsoup.parser.*;
import org.jsoup.helper.*;
import java.util.*;
import org.jsoup.select.*;
import java.nio.charset.*;

public class Document extends Element
{
    private OutputSettings outputSettings;
    private QuirksMode quirksMode;
    private String location;
    private boolean updateMetaCharset;
    
    public Document(final String baseUri) {
        super(Tag.valueOf("#root", ParseSettings.htmlDefault), baseUri);
        this.outputSettings = new OutputSettings();
        this.quirksMode = QuirksMode.noQuirks;
        this.updateMetaCharset = false;
        this.location = baseUri;
    }
    
    public static Document createShell(final String baseUri) {
        Validate.notNull(baseUri);
        final Document doc = new Document(baseUri);
        final Element html = doc.appendElement("html");
        html.appendElement("head");
        html.appendElement("body");
        return doc;
    }
    
    public String location() {
        return this.location;
    }
    
    public Element head() {
        return this.findFirstElementByTagName("head", this);
    }
    
    public Element body() {
        return this.findFirstElementByTagName("body", this);
    }
    
    public String title() {
        final Element titleEl = this.getElementsByTag("title").first();
        return (titleEl != null) ? StringUtil.normaliseWhitespace(titleEl.text()).trim() : "";
    }
    
    public void title(final String title) {
        Validate.notNull(title);
        final Element titleEl = this.getElementsByTag("title").first();
        if (titleEl == null) {
            this.head().appendElement("title").text(title);
        }
        else {
            titleEl.text(title);
        }
    }
    
    public Element createElement(final String tagName) {
        return new Element(Tag.valueOf(tagName, ParseSettings.preserveCase), this.baseUri());
    }
    
    public Document normalise() {
        Element htmlEl = this.findFirstElementByTagName("html", this);
        if (htmlEl == null) {
            htmlEl = this.appendElement("html");
        }
        if (this.head() == null) {
            htmlEl.prependElement("head");
        }
        if (this.body() == null) {
            htmlEl.appendElement("body");
        }
        this.normaliseTextNodes(this.head());
        this.normaliseTextNodes(htmlEl);
        this.normaliseTextNodes(this);
        this.normaliseStructure("head", htmlEl);
        this.normaliseStructure("body", htmlEl);
        this.ensureMetaCharsetElement();
        return this;
    }
    
    private void normaliseTextNodes(final Element element) {
        final List<Node> toMove = new ArrayList<Node>();
        for (final Node node : element.childNodes) {
            if (node instanceof TextNode) {
                final TextNode tn = (TextNode)node;
                if (tn.isBlank()) {
                    continue;
                }
                toMove.add(tn);
            }
        }
        for (int i = toMove.size() - 1; i >= 0; --i) {
            final Node node = toMove.get(i);
            element.removeChild(node);
            this.body().prependChild(new TextNode(" ", ""));
            this.body().prependChild(node);
        }
    }
    
    private void normaliseStructure(final String tag, final Element htmlEl) {
        final Elements elements = this.getElementsByTag(tag);
        final Element master = elements.first();
        if (elements.size() > 1) {
            final List<Node> toMove = new ArrayList<Node>();
            for (int i = 1; i < elements.size(); ++i) {
                final Node dupe = ((ArrayList<Node>)elements).get(i);
                for (final Node node : dupe.childNodes) {
                    toMove.add(node);
                }
                dupe.remove();
            }
            final Iterator<Node> iterator2 = toMove.iterator();
            while (iterator2.hasNext()) {
                final Node dupe = iterator2.next();
                master.appendChild(dupe);
            }
        }
        if (!master.parent().equals(htmlEl)) {
            htmlEl.appendChild(master);
        }
    }
    
    private Element findFirstElementByTagName(final String tag, final Node node) {
        if (node.nodeName().equals(tag)) {
            return (Element)node;
        }
        for (final Node child : node.childNodes) {
            final Element found = this.findFirstElementByTagName(tag, child);
            if (found != null) {
                return found;
            }
        }
        return null;
    }
    
    @Override
    public String outerHtml() {
        return super.html();
    }
    
    @Override
    public Element text(final String text) {
        this.body().text(text);
        return this;
    }
    
    @Override
    public String nodeName() {
        return "#document";
    }
    
    public void charset(final Charset charset) {
        this.updateMetaCharsetElement(true);
        this.outputSettings.charset(charset);
        this.ensureMetaCharsetElement();
    }
    
    public Charset charset() {
        return this.outputSettings.charset();
    }
    
    public void updateMetaCharsetElement(final boolean update) {
        this.updateMetaCharset = update;
    }
    
    public boolean updateMetaCharsetElement() {
        return this.updateMetaCharset;
    }
    
    @Override
    public Document clone() {
        final Document clone = (Document)super.clone();
        clone.outputSettings = this.outputSettings.clone();
        return clone;
    }
    
    private void ensureMetaCharsetElement() {
        if (this.updateMetaCharset) {
            final OutputSettings.Syntax syntax = this.outputSettings().syntax();
            if (syntax == OutputSettings.Syntax.html) {
                final Element metaCharset = this.select("meta[charset]").first();
                if (metaCharset != null) {
                    metaCharset.attr("charset", this.charset().displayName());
                }
                else {
                    final Element head = this.head();
                    if (head != null) {
                        head.appendElement("meta").attr("charset", this.charset().displayName());
                    }
                }
                this.select("meta[name=charset]").remove();
            }
            else if (syntax == OutputSettings.Syntax.xml) {
                final Node node = this.childNodes().get(0);
                if (node instanceof XmlDeclaration) {
                    XmlDeclaration decl = (XmlDeclaration)node;
                    if (decl.name().equals("xml")) {
                        decl.attr("encoding", this.charset().displayName());
                        final String version = decl.attr("version");
                        if (version != null) {
                            decl.attr("version", "1.0");
                        }
                    }
                    else {
                        decl = new XmlDeclaration("xml", this.baseUri, false);
                        decl.attr("version", "1.0");
                        decl.attr("encoding", this.charset().displayName());
                        this.prependChild(decl);
                    }
                }
                else {
                    final XmlDeclaration decl = new XmlDeclaration("xml", this.baseUri, false);
                    decl.attr("version", "1.0");
                    decl.attr("encoding", this.charset().displayName());
                    this.prependChild(decl);
                }
            }
        }
    }
    
    public OutputSettings outputSettings() {
        return this.outputSettings;
    }
    
    public Document outputSettings(final OutputSettings outputSettings) {
        Validate.notNull(outputSettings);
        this.outputSettings = outputSettings;
        return this;
    }
    
    public QuirksMode quirksMode() {
        return this.quirksMode;
    }
    
    public Document quirksMode(final QuirksMode quirksMode) {
        this.quirksMode = quirksMode;
        return this;
    }
    
    public static class OutputSettings implements Cloneable
    {
        private Entities.EscapeMode escapeMode;
        private Charset charset;
        private boolean prettyPrint;
        private boolean outline;
        private int indentAmount;
        private Syntax syntax;
        
        public OutputSettings() {
            this.escapeMode = Entities.EscapeMode.base;
            this.charset = Charset.forName("UTF-8");
            this.prettyPrint = true;
            this.outline = false;
            this.indentAmount = 1;
            this.syntax = Syntax.html;
        }
        
        public Entities.EscapeMode escapeMode() {
            return this.escapeMode;
        }
        
        public OutputSettings escapeMode(final Entities.EscapeMode escapeMode) {
            this.escapeMode = escapeMode;
            return this;
        }
        
        public Charset charset() {
            return this.charset;
        }
        
        public OutputSettings charset(final Charset charset) {
            this.charset = charset;
            return this;
        }
        
        public OutputSettings charset(final String charset) {
            this.charset(Charset.forName(charset));
            return this;
        }
        
        CharsetEncoder encoder() {
            return this.charset.newEncoder();
        }
        
        public Syntax syntax() {
            return this.syntax;
        }
        
        public OutputSettings syntax(final Syntax syntax) {
            this.syntax = syntax;
            return this;
        }
        
        public boolean prettyPrint() {
            return this.prettyPrint;
        }
        
        public OutputSettings prettyPrint(final boolean pretty) {
            this.prettyPrint = pretty;
            return this;
        }
        
        public boolean outline() {
            return this.outline;
        }
        
        public OutputSettings outline(final boolean outlineMode) {
            this.outline = outlineMode;
            return this;
        }
        
        public int indentAmount() {
            return this.indentAmount;
        }
        
        public OutputSettings indentAmount(final int indentAmount) {
            Validate.isTrue(indentAmount >= 0);
            this.indentAmount = indentAmount;
            return this;
        }
        
        public OutputSettings clone() {
            OutputSettings clone;
            try {
                clone = (OutputSettings)super.clone();
            }
            catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
            clone.charset(this.charset.name());
            clone.escapeMode = Entities.EscapeMode.valueOf(this.escapeMode.name());
            return clone;
        }
        
        public enum Syntax
        {
            html, 
            xml;
        }
    }
    
    public enum QuirksMode
    {
        noQuirks, 
        quirks, 
        limitedQuirks;
    }
}
