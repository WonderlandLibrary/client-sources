package org.jsoup.nodes;

import org.jsoup.internal.*;
import org.jsoup.parser.*;
import org.jsoup.select.*;
import java.io.*;
import java.util.*;
import org.jsoup.*;
import org.jsoup.helper.*;

public abstract class Node implements Cloneable
{
    private static final List<Node> EMPTY_NODES;
    Node parentNode;
    List<Node> childNodes;
    Attributes attributes;
    String baseUri;
    int siblingIndex;
    
    protected Node(final String baseUri, final Attributes attributes) {
        Validate.notNull(baseUri);
        Validate.notNull(attributes);
        this.childNodes = Node.EMPTY_NODES;
        this.baseUri = baseUri.trim();
        this.attributes = attributes;
    }
    
    protected Node(final String baseUri) {
        this(baseUri, new Attributes());
    }
    
    protected Node() {
        this.childNodes = Node.EMPTY_NODES;
        this.attributes = null;
    }
    
    public abstract String nodeName();
    
    public String attr(final String attributeKey) {
        Validate.notNull(attributeKey);
        final String val = this.attributes.getIgnoreCase(attributeKey);
        if (val.length() > 0) {
            return val;
        }
        if (Normalizer.lowerCase(attributeKey).startsWith("abs:")) {
            return this.absUrl(attributeKey.substring("abs:".length()));
        }
        return "";
    }
    
    public Attributes attributes() {
        return this.attributes;
    }
    
    public Node attr(final String attributeKey, final String attributeValue) {
        this.attributes.put(attributeKey, attributeValue);
        return this;
    }
    
    public boolean hasAttr(final String attributeKey) {
        Validate.notNull(attributeKey);
        if (attributeKey.startsWith("abs:")) {
            final String key = attributeKey.substring("abs:".length());
            if (this.attributes.hasKeyIgnoreCase(key) && !this.absUrl(key).equals("")) {
                return true;
            }
        }
        return this.attributes.hasKeyIgnoreCase(attributeKey);
    }
    
    public Node removeAttr(final String attributeKey) {
        Validate.notNull(attributeKey);
        this.attributes.removeIgnoreCase(attributeKey);
        return this;
    }
    
    public Node clearAttributes() {
        final Iterator<Attribute> it = this.attributes.iterator();
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
        return this;
    }
    
    public String baseUri() {
        return this.baseUri;
    }
    
    public void setBaseUri(final String baseUri) {
        Validate.notNull(baseUri);
        this.traverse(new NodeVisitor() {
            public void head(final Node node, final int depth) {
                node.baseUri = baseUri;
            }
            
            public void tail(final Node node, final int depth) {
            }
        });
    }
    
    public String absUrl(final String attributeKey) {
        Validate.notEmpty(attributeKey);
        if (!this.hasAttr(attributeKey)) {
            return "";
        }
        return StringUtil.resolve(this.baseUri, this.attr(attributeKey));
    }
    
    public Node childNode(final int index) {
        return this.childNodes.get(index);
    }
    
    public List<Node> childNodes() {
        return Collections.unmodifiableList((List<? extends Node>)this.childNodes);
    }
    
    public List<Node> childNodesCopy() {
        final List<Node> children = new ArrayList<Node>(this.childNodes.size());
        for (final Node node : this.childNodes) {
            children.add(node.clone());
        }
        return children;
    }
    
    public final int childNodeSize() {
        return this.childNodes.size();
    }
    
    protected Node[] childNodesAsArray() {
        return this.childNodes.toArray(new Node[this.childNodeSize()]);
    }
    
    public Node parent() {
        return this.parentNode;
    }
    
    public final Node parentNode() {
        return this.parentNode;
    }
    
    public Node root() {
        Node node;
        for (node = this; node.parentNode != null; node = node.parentNode) {}
        return node;
    }
    
    public Document ownerDocument() {
        final Node root = this.root();
        return (root instanceof Document) ? ((Document)root) : null;
    }
    
    public void remove() {
        Validate.notNull(this.parentNode);
        this.parentNode.removeChild(this);
    }
    
    public Node before(final String html) {
        this.addSiblingHtml(this.siblingIndex, html);
        return this;
    }
    
    public Node before(final Node node) {
        Validate.notNull(node);
        Validate.notNull(this.parentNode);
        this.parentNode.addChildren(this.siblingIndex, node);
        return this;
    }
    
    public Node after(final String html) {
        this.addSiblingHtml(this.siblingIndex + 1, html);
        return this;
    }
    
    public Node after(final Node node) {
        Validate.notNull(node);
        Validate.notNull(this.parentNode);
        this.parentNode.addChildren(this.siblingIndex + 1, node);
        return this;
    }
    
    private void addSiblingHtml(final int index, final String html) {
        Validate.notNull(html);
        Validate.notNull(this.parentNode);
        final Element context = (this.parent() instanceof Element) ? ((Element)this.parent()) : null;
        final List<Node> nodes = Parser.parseFragment(html, context, this.baseUri());
        this.parentNode.addChildren(index, (Node[])nodes.toArray(new Node[nodes.size()]));
    }
    
    public Node wrap(final String html) {
        Validate.notEmpty(html);
        final Element context = (this.parent() instanceof Element) ? ((Element)this.parent()) : null;
        final List<Node> wrapChildren = Parser.parseFragment(html, context, this.baseUri());
        final Node wrapNode = wrapChildren.get(0);
        if (wrapNode == null || !(wrapNode instanceof Element)) {
            return null;
        }
        final Element wrap = (Element)wrapNode;
        final Element deepest = this.getDeepChild(wrap);
        this.parentNode.replaceChild(this, wrap);
        deepest.addChildren(this);
        if (wrapChildren.size() > 0) {
            for (int i = 0; i < wrapChildren.size(); ++i) {
                final Node remainder = wrapChildren.get(i);
                remainder.parentNode.removeChild(remainder);
                wrap.appendChild(remainder);
            }
        }
        return this;
    }
    
    public Node unwrap() {
        Validate.notNull(this.parentNode);
        final Node firstChild = (this.childNodes.size() > 0) ? this.childNodes.get(0) : null;
        this.parentNode.addChildren(this.siblingIndex, this.childNodesAsArray());
        this.remove();
        return firstChild;
    }
    
    private Element getDeepChild(final Element el) {
        final List<Element> children = el.children();
        if (children.size() > 0) {
            return this.getDeepChild(children.get(0));
        }
        return el;
    }
    
    void nodelistChanged() {
    }
    
    public void replaceWith(final Node in) {
        Validate.notNull(in);
        Validate.notNull(this.parentNode);
        this.parentNode.replaceChild(this, in);
    }
    
    protected void setParentNode(final Node parentNode) {
        Validate.notNull(parentNode);
        if (this.parentNode != null) {
            this.parentNode.removeChild(this);
        }
        this.parentNode = parentNode;
    }
    
    protected void replaceChild(final Node out, final Node in) {
        Validate.isTrue(out.parentNode == this);
        Validate.notNull(in);
        if (in.parentNode != null) {
            in.parentNode.removeChild(in);
        }
        final int index = out.siblingIndex;
        this.childNodes.set(index, in);
        in.parentNode = this;
        in.setSiblingIndex(index);
        out.parentNode = null;
    }
    
    protected void removeChild(final Node out) {
        Validate.isTrue(out.parentNode == this);
        final int index = out.siblingIndex;
        this.childNodes.remove(index);
        this.reindexChildren(index);
        out.parentNode = null;
    }
    
    protected void addChildren(final Node... children) {
        for (final Node child : children) {
            this.reparentChild(child);
            this.ensureChildNodes();
            this.childNodes.add(child);
            child.setSiblingIndex(this.childNodes.size() - 1);
        }
    }
    
    protected void addChildren(final int index, final Node... children) {
        Validate.noNullElements(children);
        this.ensureChildNodes();
        for (int i = children.length - 1; i >= 0; --i) {
            final Node in = children[i];
            this.reparentChild(in);
            this.childNodes.add(index, in);
            this.reindexChildren(index);
        }
    }
    
    protected void ensureChildNodes() {
        if (this.childNodes == Node.EMPTY_NODES) {
            this.childNodes = new NodeList(4);
        }
    }
    
    protected void reparentChild(final Node child) {
        if (child.parentNode != null) {
            child.parentNode.removeChild(child);
        }
        child.setParentNode(this);
    }
    
    private void reindexChildren(final int start) {
        for (int i = start; i < this.childNodes.size(); ++i) {
            this.childNodes.get(i).setSiblingIndex(i);
        }
    }
    
    public List<Node> siblingNodes() {
        if (this.parentNode == null) {
            return Collections.emptyList();
        }
        final List<Node> nodes = this.parentNode.childNodes;
        final List<Node> siblings = new ArrayList<Node>(nodes.size() - 1);
        for (final Node node : nodes) {
            if (node != this) {
                siblings.add(node);
            }
        }
        return siblings;
    }
    
    public Node nextSibling() {
        if (this.parentNode == null) {
            return null;
        }
        final List<Node> siblings = this.parentNode.childNodes;
        final int index = this.siblingIndex + 1;
        if (siblings.size() > index) {
            return siblings.get(index);
        }
        return null;
    }
    
    public Node previousSibling() {
        if (this.parentNode == null) {
            return null;
        }
        if (this.siblingIndex > 0) {
            return this.parentNode.childNodes.get(this.siblingIndex - 1);
        }
        return null;
    }
    
    public int siblingIndex() {
        return this.siblingIndex;
    }
    
    protected void setSiblingIndex(final int siblingIndex) {
        this.siblingIndex = siblingIndex;
    }
    
    public Node traverse(final NodeVisitor nodeVisitor) {
        Validate.notNull(nodeVisitor);
        final NodeTraversor traversor = new NodeTraversor(nodeVisitor);
        traversor.traverse(this);
        return this;
    }
    
    public String outerHtml() {
        final StringBuilder accum = new StringBuilder(128);
        this.outerHtml(accum);
        return accum.toString();
    }
    
    protected void outerHtml(final Appendable accum) {
        new NodeTraversor(new OuterHtmlVisitor(accum, this.getOutputSettings())).traverse(this);
    }
    
    Document.OutputSettings getOutputSettings() {
        final Document owner = this.ownerDocument();
        return (owner != null) ? owner.outputSettings() : new Document("").outputSettings();
    }
    
    abstract void outerHtmlHead(final Appendable p0, final int p1, final Document.OutputSettings p2) throws IOException;
    
    abstract void outerHtmlTail(final Appendable p0, final int p1, final Document.OutputSettings p2) throws IOException;
    
    public <T extends Appendable> T html(final T appendable) {
        this.outerHtml(appendable);
        return appendable;
    }
    
    @Override
    public String toString() {
        return this.outerHtml();
    }
    
    protected void indent(final Appendable accum, final int depth, final Document.OutputSettings out) throws IOException {
        accum.append("\n").append(StringUtil.padding(depth * out.indentAmount()));
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o;
    }
    
    public boolean hasSameValue(final Object o) {
        return this == o || (o != null && this.getClass() == o.getClass() && this.outerHtml().equals(((Node)o).outerHtml()));
    }
    
    public Node clone() {
        final Node thisClone = this.doClone(null);
        final LinkedList<Node> nodesToProcess = new LinkedList<Node>();
        nodesToProcess.add(thisClone);
        while (!nodesToProcess.isEmpty()) {
            final Node currParent = nodesToProcess.remove();
            for (int i = 0; i < currParent.childNodes.size(); ++i) {
                final Node childClone = currParent.childNodes.get(i).doClone(currParent);
                currParent.childNodes.set(i, childClone);
                nodesToProcess.add(childClone);
            }
        }
        return thisClone;
    }
    
    protected Node doClone(final Node parent) {
        Node clone;
        try {
            clone = (Node)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        clone.parentNode = parent;
        clone.siblingIndex = ((parent == null) ? 0 : this.siblingIndex);
        clone.attributes = ((this.attributes != null) ? this.attributes.clone() : null);
        clone.baseUri = this.baseUri;
        clone.childNodes = new NodeList(this.childNodes.size());
        for (final Node child : this.childNodes) {
            clone.childNodes.add(child);
        }
        return clone;
    }
    
    static {
        EMPTY_NODES = Collections.emptyList();
    }
    
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
    
    private final class NodeList extends ChangeNotifyingArrayList<Node>
    {
        NodeList(final int initialCapacity) {
            super(initialCapacity);
        }
        
        @Override
        public void onContentsChanged() {
            Node.this.nodelistChanged();
        }
    }
}
