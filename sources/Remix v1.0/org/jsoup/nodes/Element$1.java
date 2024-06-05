package org.jsoup.nodes;

import org.jsoup.select.*;

class Element$1 implements NodeVisitor {
    final /* synthetic */ StringBuilder val$accum;
    
    public void head(final Node node, final int depth) {
        if (node instanceof TextNode) {
            final TextNode textNode = (TextNode)node;
            Element.access$000(this.val$accum, textNode);
        }
        else if (node instanceof Element) {
            final Element element = (Element)node;
            if (this.val$accum.length() > 0 && (element.isBlock() || Element.access$100(element).getName().equals("br")) && !TextNode.lastCharIsWhitespace(this.val$accum)) {
                this.val$accum.append(" ");
            }
        }
    }
    
    public void tail(final Node node, final int depth) {
    }
}