package org.jsoup.nodes;

import java.util.List;

abstract class LeafNode extends Node {
   Object value;

   @Override
   protected final boolean hasAttributes() {
      return this.value instanceof Attributes;
   }

   @Override
   public final Attributes attributes() {
      this.ensureAttributes();
      return (Attributes)this.value;
   }

   private void ensureAttributes() {
      if (!this.hasAttributes()) {
         Object coreValue = this.value;
         Attributes attributes = new Attributes();
         this.value = attributes;
         if (coreValue != null) {
            attributes.put(this.nodeName(), (String)coreValue);
         }
      }
   }

   String coreValue() {
      return this.attr(this.nodeName());
   }

   void coreValue(String value) {
      this.attr(this.nodeName(), value);
   }

   @Override
   public String attr(String key) {
      if (!this.hasAttributes()) {
         return this.nodeName().equals(key) ? (String)this.value : "";
      } else {
         return super.attr(key);
      }
   }

   @Override
   public Node attr(String key, String value) {
      if (!this.hasAttributes() && key.equals(this.nodeName())) {
         this.value = value;
      } else {
         this.ensureAttributes();
         super.attr(key, value);
      }

      return this;
   }

   @Override
   public boolean hasAttr(String key) {
      this.ensureAttributes();
      return super.hasAttr(key);
   }

   @Override
   public Node removeAttr(String key) {
      this.ensureAttributes();
      return super.removeAttr(key);
   }

   @Override
   public String absUrl(String key) {
      this.ensureAttributes();
      return super.absUrl(key);
   }

   @Override
   public String baseUri() {
      return this.hasParent() ? this.parent().baseUri() : "";
   }

   @Override
   protected void doSetBaseUri(String baseUri) {
   }

   @Override
   public int childNodeSize() {
      return 0;
   }

   @Override
   public Node empty() {
      return this;
   }

   @Override
   protected List<Node> ensureChildNodes() {
      return EmptyNodes;
   }

   protected LeafNode doClone(Node parent) {
      LeafNode clone = (LeafNode)super.doClone(parent);
      if (this.hasAttributes()) {
         clone.value = ((Attributes)this.value).clone();
      }

      return clone;
   }
}
