package org.jsoup.helper;

import javax.xml.parsers.*;
import org.jsoup.select.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import java.io.*;
import javax.xml.transform.*;
import org.w3c.dom.*;
import java.util.*;
import org.jsoup.nodes.*;

public class W3CDom
{
    protected DocumentBuilderFactory factory;
    
    public W3CDom() {
        this.factory = DocumentBuilderFactory.newInstance();
    }
    
    public org.w3c.dom.Document fromJsoup(final Document in) {
        Validate.notNull(in);
        try {
            this.factory.setNamespaceAware(true);
            final DocumentBuilder builder = this.factory.newDocumentBuilder();
            final org.w3c.dom.Document out = builder.newDocument();
            this.convert(in, out);
            return out;
        }
        catch (ParserConfigurationException e) {
            throw new IllegalStateException(e);
        }
    }
    
    public void convert(final Document in, final org.w3c.dom.Document out) {
        if (!StringUtil.isBlank(in.location())) {
            out.setDocumentURI(in.location());
        }
        final Element rootEl = in.child(0);
        final NodeTraversor traversor = new NodeTraversor(new W3CBuilder(out));
        traversor.traverse(rootEl);
    }
    
    public String asString(final org.w3c.dom.Document doc) {
        try {
            final DOMSource domSource = new DOMSource(doc);
            final StringWriter writer = new StringWriter();
            final StreamResult result = new StreamResult(writer);
            final TransformerFactory tf = TransformerFactory.newInstance();
            final Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);
            return writer.toString();
        }
        catch (TransformerException e) {
            throw new IllegalStateException(e);
        }
    }
    
    protected static class W3CBuilder implements NodeVisitor
    {
        private static final String xmlnsKey = "xmlns";
        private static final String xmlnsPrefix = "xmlns:";
        private final org.w3c.dom.Document doc;
        private final HashMap<String, String> namespaces;
        private org.w3c.dom.Element dest;
        
        public W3CBuilder(final org.w3c.dom.Document doc) {
            this.namespaces = new HashMap<String, String>();
            this.doc = doc;
        }
        
        public void head(final Node source, final int depth) {
            if (source instanceof Element) {
                final Element sourceEl = (Element)source;
                final String prefix = this.updateNamespaces(sourceEl);
                final String namespace = this.namespaces.get(prefix);
                final org.w3c.dom.Element el = this.doc.createElementNS(namespace, sourceEl.tagName());
                this.copyAttributes(sourceEl, el);
                if (this.dest == null) {
                    this.doc.appendChild(el);
                }
                else {
                    this.dest.appendChild(el);
                }
                this.dest = el;
            }
            else if (source instanceof TextNode) {
                final TextNode sourceText = (TextNode)source;
                final Text text = this.doc.createTextNode(sourceText.getWholeText());
                this.dest.appendChild(text);
            }
            else if (source instanceof Comment) {
                final Comment sourceComment = (Comment)source;
                final org.w3c.dom.Comment comment = this.doc.createComment(sourceComment.getData());
                this.dest.appendChild(comment);
            }
            else if (source instanceof DataNode) {
                final DataNode sourceData = (DataNode)source;
                final Text node = this.doc.createTextNode(sourceData.getWholeData());
                this.dest.appendChild(node);
            }
        }
        
        public void tail(final Node source, final int depth) {
            if (source instanceof Element && this.dest.getParentNode() instanceof org.w3c.dom.Element) {
                this.dest = (org.w3c.dom.Element)this.dest.getParentNode();
            }
        }
        
        private void copyAttributes(final Node source, final org.w3c.dom.Element el) {
            for (final Attribute attribute : source.attributes()) {
                final String key = attribute.getKey().replaceAll("[^-a-zA-Z0-9_:.]", "");
                if (key.matches("[a-zA-Z_:]{1}[-a-zA-Z0-9_:.]*")) {
                    el.setAttribute(key, attribute.getValue());
                }
            }
        }
        
        private String updateNamespaces(final Element el) {
            final Attributes attributes = el.attributes();
            for (final Attribute attr : attributes) {
                final String key = attr.getKey();
                String prefix;
                if (key.equals("xmlns")) {
                    prefix = "";
                }
                else {
                    if (!key.startsWith("xmlns:")) {
                        continue;
                    }
                    prefix = key.substring("xmlns:".length());
                }
                this.namespaces.put(prefix, attr.getValue());
            }
            final int pos = el.tagName().indexOf(":");
            return (pos > 0) ? el.tagName().substring(0, pos) : "";
        }
    }
}
