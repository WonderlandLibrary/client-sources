package org.jsoup.select;

import org.jsoup.nodes.*;

public class NodeTraversor
{
    private NodeVisitor visitor;
    
    public NodeTraversor(final NodeVisitor visitor) {
        this.visitor = visitor;
    }
    
    public void traverse(final Node root) {
        Node node = root;
        int depth = 0;
        while (node != null) {
            this.visitor.head(node, depth);
            if (node.childNodeSize() > 0) {
                node = node.childNode(0);
                ++depth;
            }
            else {
                while (node.nextSibling() == null && depth > 0) {
                    this.visitor.tail(node, depth);
                    node = node.parentNode();
                    --depth;
                }
                this.visitor.tail(node, depth);
                if (node == root) {
                    break;
                }
                node = node.nextSibling();
            }
        }
    }
}
