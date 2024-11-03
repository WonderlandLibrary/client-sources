package org.jsoup.nodes;

import java.io.IOException;
import org.jsoup.helper.Validate;
import org.jsoup.internal.StringUtil;

public class TextNode extends LeafNode {
   public TextNode(String text) {
      this.value = text;
   }

   @Override
   public String nodeName() {
      return "#text";
   }

   public String text() {
      return StringUtil.normaliseWhitespace(this.getWholeText());
   }

   public TextNode text(String text) {
      this.coreValue(text);
      return this;
   }

   public String getWholeText() {
      return this.coreValue();
   }

   public boolean isBlank() {
      return StringUtil.isBlank(this.coreValue());
   }

   public TextNode splitText(int offset) {
      String text = this.coreValue();
      Validate.isTrue(offset >= 0, "Split offset must be not be negative");
      Validate.isTrue(offset < text.length(), "Split offset must not be greater than current text length");
      String head = text.substring(0, offset);
      String tail = text.substring(offset);
      this.text(head);
      TextNode tailNode = new TextNode(tail);
      if (this.parentNode != null) {
         this.parentNode.addChildren(this.siblingIndex() + 1, tailNode);
      }

      return tailNode;
   }

   @Override
   void outerHtmlHead(Appendable accum, int depth, Document.OutputSettings out) throws IOException {
      boolean prettyPrint = out.prettyPrint();
      Element parent = this.parentNode instanceof Element ? (Element)this.parentNode : null;
      boolean normaliseWhite = prettyPrint && !Element.preserveWhitespace(this.parentNode);
      boolean trimLeading = false;
      boolean trimTrailing = false;
      if (normaliseWhite) {
         trimLeading = this.siblingIndex == 0 && parent != null && parent.tag().isBlock() || this.parentNode instanceof Document;
         trimTrailing = this.nextSibling() == null && parent != null && parent.tag().isBlock();
         Node next = this.nextSibling();
         boolean couldSkip = next instanceof Element && ((Element)next).shouldIndent(out) || next instanceof TextNode && ((TextNode)next).isBlank();
         if (couldSkip && this.isBlank()) {
            return;
         }

         if (this.siblingIndex == 0 && parent != null && parent.tag().formatAsBlock() && !this.isBlank()
            || out.outline() && this.siblingNodes().size() > 0 && !this.isBlank()) {
            this.indent(accum, depth, out);
         }
      }

      Entities.escape(accum, this.coreValue(), out, false, normaliseWhite, trimLeading, trimTrailing);
   }

   @Override
   void outerHtmlTail(Appendable accum, int depth, Document.OutputSettings out) {
   }

   @Override
   public String toString() {
      return this.outerHtml();
   }

   public TextNode clone() {
      return (TextNode)super.clone();
   }

   public static TextNode createFromEncoded(String encodedText) {
      String text = Entities.unescape(encodedText);
      return new TextNode(text);
   }

   static String normaliseWhitespace(String text) {
      return StringUtil.normaliseWhitespace(text);
   }

   static String stripLeadingWhitespace(String text) {
      return text.replaceFirst("^\\s+", "");
   }

   static boolean lastCharIsWhitespace(StringBuilder sb) {
      return sb.length() != 0 && sb.charAt(sb.length() - 1) == ' ';
   }
}
