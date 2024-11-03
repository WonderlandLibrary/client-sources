package org.jsoup.nodes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Nullable;
import org.jsoup.SerializationException;
import org.jsoup.helper.Consumer;
import org.jsoup.helper.Validate;
import org.jsoup.internal.StringUtil;
import org.jsoup.select.NodeFilter;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;

public abstract class Node implements Cloneable {
   static final List<Node> EmptyNodes = Collections.emptyList();
   static final String EmptyString = "";
   @Nullable
   Node parentNode;
   int siblingIndex;

   protected Node() {
   }

   public abstract String nodeName();

   protected abstract boolean hasAttributes();

   public boolean hasParent() {
      return this.parentNode != null;
   }

   public String attr(String attributeKey) {
      Validate.notNull(attributeKey);
      if (!this.hasAttributes()) {
         return "";
      } else {
         String val = this.attributes().getIgnoreCase(attributeKey);
         if (val.length() > 0) {
            return val;
         } else {
            return attributeKey.startsWith("abs:") ? this.absUrl(attributeKey.substring("abs:".length())) : "";
         }
      }
   }

   public abstract Attributes attributes();

   public int attributesSize() {
      return this.hasAttributes() ? this.attributes().size() : 0;
   }

   public Node attr(String attributeKey, String attributeValue) {
      attributeKey = NodeUtils.parser(this).settings().normalizeAttribute(attributeKey);
      this.attributes().putIgnoreCase(attributeKey, attributeValue);
      return this;
   }

   public boolean hasAttr(String attributeKey) {
      Validate.notNull(attributeKey);
      if (!this.hasAttributes()) {
         return false;
      } else {
         if (attributeKey.startsWith("abs:")) {
            String key = attributeKey.substring("abs:".length());
            if (this.attributes().hasKeyIgnoreCase(key) && !this.absUrl(key).isEmpty()) {
               return true;
            }
         }

         return this.attributes().hasKeyIgnoreCase(attributeKey);
      }
   }

   public Node removeAttr(String attributeKey) {
      Validate.notNull(attributeKey);
      if (this.hasAttributes()) {
         this.attributes().removeIgnoreCase(attributeKey);
      }

      return this;
   }

   public Node clearAttributes() {
      if (this.hasAttributes()) {
         Iterator<Attribute> it = this.attributes().iterator();

         while (it.hasNext()) {
            it.next();
            it.remove();
         }
      }

      return this;
   }

   public abstract String baseUri();

   protected abstract void doSetBaseUri(String var1);

   public void setBaseUri(String baseUri) {
      Validate.notNull(baseUri);
      this.doSetBaseUri(baseUri);
   }

   public String absUrl(String attributeKey) {
      Validate.notEmpty(attributeKey);
      return this.hasAttributes() && this.attributes().hasKeyIgnoreCase(attributeKey)
         ? StringUtil.resolve(this.baseUri(), this.attributes().getIgnoreCase(attributeKey))
         : "";
   }

   protected abstract List<Node> ensureChildNodes();

   public Node childNode(int index) {
      return this.ensureChildNodes().get(index);
   }

   public List<Node> childNodes() {
      if (this.childNodeSize() == 0) {
         return EmptyNodes;
      } else {
         List<Node> children = this.ensureChildNodes();
         List<Node> rewrap = new ArrayList<>(children.size());
         rewrap.addAll(children);
         return Collections.unmodifiableList(rewrap);
      }
   }

   public List<Node> childNodesCopy() {
      List<Node> nodes = this.ensureChildNodes();
      ArrayList<Node> children = new ArrayList<>(nodes.size());

      for (Node node : nodes) {
         children.add(node.clone());
      }

      return children;
   }

   public abstract int childNodeSize();

   protected Node[] childNodesAsArray() {
      return this.ensureChildNodes().toArray(new Node[0]);
   }

   public abstract Node empty();

   @Nullable
   public Node parent() {
      return this.parentNode;
   }

   @Nullable
   public final Node parentNode() {
      return this.parentNode;
   }

   public Node root() {
      Node node = this;

      while (node.parentNode != null) {
         node = node.parentNode;
      }

      return node;
   }

   @Nullable
   public Document ownerDocument() {
      Node root = this.root();
      return root instanceof Document ? (Document)root : null;
   }

   public void remove() {
      Validate.notNull(this.parentNode);
      this.parentNode.removeChild(this);
   }

   public Node before(String html) {
      this.addSiblingHtml(this.siblingIndex, html);
      return this;
   }

   public Node before(Node node) {
      Validate.notNull(node);
      Validate.notNull(this.parentNode);
      this.parentNode.addChildren(this.siblingIndex, node);
      return this;
   }

   public Node after(String html) {
      this.addSiblingHtml(this.siblingIndex + 1, html);
      return this;
   }

   public Node after(Node node) {
      Validate.notNull(node);
      Validate.notNull(this.parentNode);
      this.parentNode.addChildren(this.siblingIndex + 1, node);
      return this;
   }

   private void addSiblingHtml(int index, String html) {
      Validate.notNull(html);
      Validate.notNull(this.parentNode);
      Element context = this.parent() instanceof Element ? (Element)this.parent() : null;
      List<Node> nodes = NodeUtils.parser(this).parseFragmentInput(html, context, this.baseUri());
      this.parentNode.addChildren(index, nodes.toArray(new Node[0]));
   }

   public Node wrap(String html) {
      Validate.notEmpty(html);
      Element context = this.parentNode != null && this.parentNode instanceof Element
         ? (Element)this.parentNode
         : (this instanceof Element ? (Element)this : null);
      List<Node> wrapChildren = NodeUtils.parser(this).parseFragmentInput(html, context, this.baseUri());
      Node wrapNode = wrapChildren.get(0);
      if (!(wrapNode instanceof Element)) {
         return this;
      } else {
         Element wrap = (Element)wrapNode;
         Element deepest = this.getDeepChild(wrap);
         if (this.parentNode != null) {
            this.parentNode.replaceChild(this, wrap);
         }

         deepest.addChildren(new Node[]{this});
         if (wrapChildren.size() > 0) {
            for (int i = 0; i < wrapChildren.size(); i++) {
               Node remainder = wrapChildren.get(i);
               if (wrap != remainder) {
                  if (remainder.parentNode != null) {
                     remainder.parentNode.removeChild(remainder);
                  }

                  wrap.after(remainder);
               }
            }
         }

         return this;
      }
   }

   @Nullable
   public Node unwrap() {
      Validate.notNull(this.parentNode);
      Node firstChild = this.firstChild();
      this.parentNode.addChildren(this.siblingIndex, this.childNodesAsArray());
      this.remove();
      return firstChild;
   }

   private Element getDeepChild(Element el) {
      List<Element> children = el.children();
      return children.size() > 0 ? this.getDeepChild(children.get(0)) : el;
   }

   void nodelistChanged() {
   }

   public void replaceWith(Node in) {
      Validate.notNull(in);
      Validate.notNull(this.parentNode);
      this.parentNode.replaceChild(this, in);
   }

   protected void setParentNode(Node parentNode) {
      Validate.notNull(parentNode);
      if (this.parentNode != null) {
         this.parentNode.removeChild(this);
      }

      this.parentNode = parentNode;
   }

   protected void replaceChild(Node out, Node in) {
      Validate.isTrue(out.parentNode == this);
      Validate.notNull(in);
      if (in.parentNode != null) {
         in.parentNode.removeChild(in);
      }

      int index = out.siblingIndex;
      this.ensureChildNodes().set(index, in);
      in.parentNode = this;
      in.setSiblingIndex(index);
      out.parentNode = null;
   }

   protected void removeChild(Node out) {
      Validate.isTrue(out.parentNode == this);
      int index = out.siblingIndex;
      this.ensureChildNodes().remove(index);
      this.reindexChildren(index);
      out.parentNode = null;
   }

   protected void addChildren(Node... children) {
      List<Node> nodes = this.ensureChildNodes();

      for (Node child : children) {
         this.reparentChild(child);
         nodes.add(child);
         child.setSiblingIndex(nodes.size() - 1);
      }
   }

   protected void addChildren(int index, Node... children) {
      Validate.notNull(children);
      if (children.length != 0) {
         List<Node> nodes = this.ensureChildNodes();
         Node firstParent = children[0].parent();
         if (firstParent != null && firstParent.childNodeSize() == children.length) {
            boolean sameList = true;
            List<Node> firstParentNodes = firstParent.ensureChildNodes();
            int i = children.length;

            while (i-- > 0) {
               if (children[i] != firstParentNodes.get(i)) {
                  sameList = false;
                  break;
               }
            }

            if (sameList) {
               boolean wasEmpty = this.childNodeSize() == 0;
               firstParent.empty();
               nodes.addAll(index, Arrays.asList(children));
               i = children.length;

               while (i-- > 0) {
                  children[i].parentNode = this;
               }

               if (!wasEmpty || children[0].siblingIndex != 0) {
                  this.reindexChildren(index);
               }

               return;
            }
         }

         Validate.noNullElements(children);

         for (Node child : children) {
            this.reparentChild(child);
         }

         nodes.addAll(index, Arrays.asList(children));
         this.reindexChildren(index);
      }
   }

   protected void reparentChild(Node child) {
      child.setParentNode(this);
   }

   private void reindexChildren(int start) {
      int size = this.childNodeSize();
      if (size != 0) {
         List<Node> childNodes = this.ensureChildNodes();

         for (int i = start; i < size; i++) {
            childNodes.get(i).setSiblingIndex(i);
         }
      }
   }

   public List<Node> siblingNodes() {
      if (this.parentNode == null) {
         return Collections.emptyList();
      } else {
         List<Node> nodes = this.parentNode.ensureChildNodes();
         List<Node> siblings = new ArrayList<>(nodes.size() - 1);

         for (Node node : nodes) {
            if (node != this) {
               siblings.add(node);
            }
         }

         return siblings;
      }
   }

   @Nullable
   public Node nextSibling() {
      if (this.parentNode == null) {
         return null;
      } else {
         List<Node> siblings = this.parentNode.ensureChildNodes();
         int index = this.siblingIndex + 1;
         return siblings.size() > index ? siblings.get(index) : null;
      }
   }

   @Nullable
   public Node previousSibling() {
      if (this.parentNode == null) {
         return null;
      } else {
         return this.siblingIndex > 0 ? this.parentNode.ensureChildNodes().get(this.siblingIndex - 1) : null;
      }
   }

   public int siblingIndex() {
      return this.siblingIndex;
   }

   protected void setSiblingIndex(int siblingIndex) {
      this.siblingIndex = siblingIndex;
   }

   @Nullable
   public Node firstChild() {
      return this.childNodeSize() == 0 ? null : this.ensureChildNodes().get(0);
   }

   @Nullable
   public Node lastChild() {
      int size = this.childNodeSize();
      if (size == 0) {
         return null;
      } else {
         List<Node> children = this.ensureChildNodes();
         return children.get(size - 1);
      }
   }

   public Node traverse(NodeVisitor nodeVisitor) {
      Validate.notNull(nodeVisitor);
      NodeTraversor.traverse(nodeVisitor, this);
      return this;
   }

   public Node forEachNode(Consumer<? super Node> action) {
      Validate.notNull(action);
      NodeTraversor.traverse((node, depth) -> action.accept(node), this);
      return this;
   }

   public Node filter(NodeFilter nodeFilter) {
      Validate.notNull(nodeFilter);
      NodeTraversor.filter(nodeFilter, this);
      return this;
   }

   public String outerHtml() {
      StringBuilder accum = StringUtil.borrowBuilder();
      this.outerHtml(accum);
      return StringUtil.releaseBuilder(accum);
   }

   protected void outerHtml(Appendable accum) {
      NodeTraversor.traverse(new Node.OuterHtmlVisitor(accum, NodeUtils.outputSettings(this)), this);
   }

   abstract void outerHtmlHead(Appendable var1, int var2, Document.OutputSettings var3) throws IOException;

   abstract void outerHtmlTail(Appendable var1, int var2, Document.OutputSettings var3) throws IOException;

   public <T extends Appendable> T html(T appendable) {
      this.outerHtml(appendable);
      return appendable;
   }

   public Range sourceRange() {
      return Range.of(this, true);
   }

   @Override
   public String toString() {
      return this.outerHtml();
   }

   protected void indent(Appendable accum, int depth, Document.OutputSettings out) throws IOException {
      accum.append('\n').append(StringUtil.padding(depth * out.indentAmount(), out.maxPaddingWidth()));
   }

   @Override
   public boolean equals(@Nullable Object o) {
      return this == o;
   }

   @Override
   public int hashCode() {
      return super.hashCode();
   }

   public boolean hasSameValue(@Nullable Object o) {
      if (this == o) {
         return true;
      } else {
         return o != null && this.getClass() == o.getClass() ? this.outerHtml().equals(((Node)o).outerHtml()) : false;
      }
   }

   public Node clone() {
      Node thisClone = this.doClone(null);
      LinkedList<Node> nodesToProcess = new LinkedList<>();
      nodesToProcess.add(thisClone);

      while (!nodesToProcess.isEmpty()) {
         Node currParent = nodesToProcess.remove();
         int size = currParent.childNodeSize();

         for (int i = 0; i < size; i++) {
            List<Node> childNodes = currParent.ensureChildNodes();
            Node childClone = childNodes.get(i).doClone(currParent);
            childNodes.set(i, childClone);
            nodesToProcess.add(childClone);
         }
      }

      return thisClone;
   }

   public Node shallowClone() {
      return this.doClone(null);
   }

   protected Node doClone(@Nullable Node parent) {
      Node clone;
      try {
         clone = (Node)super.clone();
      } catch (CloneNotSupportedException var5) {
         throw new RuntimeException(var5);
      }

      clone.parentNode = parent;
      clone.siblingIndex = parent == null ? 0 : this.siblingIndex;
      if (parent == null && !(this instanceof Document)) {
         Document doc = this.ownerDocument();
         if (doc != null) {
            Document docClone = doc.shallowClone();
            clone.parentNode = docClone;
            docClone.ensureChildNodes().add(clone);
         }
      }

      return clone;
   }

   private static class OuterHtmlVisitor implements NodeVisitor {
      private final Appendable accum;
      private final Document.OutputSettings out;

      OuterHtmlVisitor(Appendable accum, Document.OutputSettings out) {
         this.accum = accum;
         this.out = out;
         out.prepareEncoder();
      }

      @Override
      public void head(Node node, int depth) {
         try {
            node.outerHtmlHead(this.accum, depth, this.out);
         } catch (IOException var4) {
            throw new SerializationException(var4);
         }
      }

      @Override
      public void tail(Node node, int depth) {
         if (!node.nodeName().equals("#text")) {
            try {
               node.outerHtmlTail(this.accum, depth, this.out);
            } catch (IOException var4) {
               throw new SerializationException(var4);
            }
         }
      }
   }
}
