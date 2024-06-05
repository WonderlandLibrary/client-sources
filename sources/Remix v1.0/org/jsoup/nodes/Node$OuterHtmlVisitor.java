package org.jsoup.nodes;

import org.jsoup.select.*;
import org.jsoup.*;
import java.io.*;

private static class OuterHtmlVisitor implements NodeVisitor
{
    private Appendable accum;
    private Document.OutputSettings out;
    
    OuterHtmlVisitor(final Appendable accum, final Document.OutputSettings out) {
        this.accum = accum;
        this.out = out;
    }
    
    public void head(final Node node, final int depth) {
        try {
            node.outerHtmlHead(this.accum, depth, this.out);
        }
        catch (IOException exception) {
            throw new SerializationException(exception);
        }
    }
    
    public void tail(final Node node, final int depth) {
        if (!node.nodeName().equals("#text")) {
            try {
                node.outerHtmlTail(this.accum, depth, this.out);
            }
            catch (IOException exception) {
                throw new SerializationException(exception);
            }
        }
    }
}
