package org.jsoup.helper;

import org.jsoup.select.*;
import org.w3c.dom.*;
import java.util.*;
import org.jsoup.nodes.*;

protected static class W3CBuilder implements NodeVisitor
{
    private static final String xmlnsKey = "xmlns";
    private static final String xmlnsPrefix = "xmlns:";
    private final Document doc;
    private final HashMap<String, String> namespaces;
    private Element dest;
    
    public W3CBuilder(final Document doc) {
        this.namespaces = new HashMap<String, String>();
        this.doc = doc;
    }
    
    public void head(final Node source, final int depth) {
        if (source instanceof org.jsoup.nodes.Element) {
            final org.jsoup.nodes.Element sourceEl = (org.jsoup.nodes.Element)source;
            final String prefix = this.updateNamespaces(sourceEl);
            final String namespace = this.namespaces.get(prefix);
            final Element el = this.doc.createElementNS(namespace, sourceEl.tagName());
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
        if (source instanceof org.jsoup.nodes.Element && this.dest.getParentNode() instanceof Element) {
            this.dest = (Element)this.dest.getParentNode();
        }
    }
    
    private void copyAttributes(final Node source, final Element el) {
        for (final Attribute attribute : source.attributes()) {
            final String key = attribute.getKey().replaceAll("[^-a-zA-Z0-9_:.]", "");
            if (key.matches("[a-zA-Z_:]{1}[-a-zA-Z0-9_:.]*")) {
                el.setAttribute(key, attribute.getValue());
            }
        }
    }
    
    private String updateNamespaces(final org.jsoup.nodes.Element el) {
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
