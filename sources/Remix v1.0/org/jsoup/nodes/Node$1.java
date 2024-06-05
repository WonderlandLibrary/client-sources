package org.jsoup.nodes;

import org.jsoup.select.*;

class Node$1 implements NodeVisitor {
    final /* synthetic */ String val$baseUri;
    
    public void head(final Node node, final int depth) {
        node.baseUri = this.val$baseUri;
    }
    
    public void tail(final Node node, final int depth) {
    }
}