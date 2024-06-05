package org.jsoup.safety;

import org.jsoup.select.*;
import org.jsoup.nodes.*;

private final class CleaningVisitor implements NodeVisitor
{
    private int numDiscarded;
    private final Element root;
    private Element destination;
    
    private CleaningVisitor(final Element root, final Element destination) {
        this.numDiscarded = 0;
        this.root = root;
        this.destination = destination;
    }
    
    public void head(final Node source, final int depth) {
        if (source instanceof Element) {
            final Element sourceEl = (Element)source;
            if (Cleaner.access$000(Cleaner.this).isSafeTag(sourceEl.tagName())) {
                final ElementMeta meta = Cleaner.access$100(Cleaner.this, sourceEl);
                final Element destChild = meta.el;
                this.destination.appendChild(destChild);
                this.numDiscarded += meta.numAttribsDiscarded;
                this.destination = destChild;
            }
            else if (source != this.root) {
                ++this.numDiscarded;
            }
        }
        else if (source instanceof TextNode) {
            final TextNode sourceText = (TextNode)source;
            final TextNode destText = new TextNode(sourceText.getWholeText(), source.baseUri());
            this.destination.appendChild(destText);
        }
        else if (source instanceof DataNode && Cleaner.access$000(Cleaner.this).isSafeTag(source.parent().nodeName())) {
            final DataNode sourceData = (DataNode)source;
            final DataNode destData = new DataNode(sourceData.getWholeData(), source.baseUri());
            this.destination.appendChild(destData);
        }
        else {
            ++this.numDiscarded;
        }
    }
    
    public void tail(final Node source, final int depth) {
        if (source instanceof Element && Cleaner.access$000(Cleaner.this).isSafeTag(source.nodeName())) {
            this.destination = this.destination.parent();
        }
    }
}
