package org.jsoup.nodes;

import java.io.IOException;
import org.jsoup.SerializationException;
import org.jsoup.helper.Validate;
import org.jsoup.internal.StringUtil;

public class XmlDeclaration extends LeafNode {
   private final boolean isProcessingInstruction;

   public XmlDeclaration(String name, boolean isProcessingInstruction) {
      Validate.notNull(name);
      this.value = name;
      this.isProcessingInstruction = isProcessingInstruction;
   }

   @Override
   public String nodeName() {
      return "#declaration";
   }

   public String name() {
      return this.coreValue();
   }

   public String getWholeDeclaration() {
      StringBuilder sb = StringUtil.borrowBuilder();

      try {
         this.getWholeDeclaration(sb, new Document.OutputSettings());
      } catch (IOException var3) {
         throw new SerializationException(var3);
      }

      return StringUtil.releaseBuilder(sb).trim();
   }

   private void getWholeDeclaration(Appendable accum, Document.OutputSettings out) throws IOException {
      for (Attribute attribute : this.attributes()) {
         String key = attribute.getKey();
         String val = attribute.getValue();
         if (!key.equals(this.nodeName())) {
            accum.append(' ');
            accum.append(key);
            if (!val.isEmpty()) {
               accum.append("=\"");
               Entities.escape(accum, val, out, true, false, false, false);
               accum.append('"');
            }
         }
      }
   }

   @Override
   void outerHtmlHead(Appendable accum, int depth, Document.OutputSettings out) throws IOException {
      accum.append("<").append(this.isProcessingInstruction ? "!" : "?").append(this.coreValue());
      this.getWholeDeclaration(accum, out);
      accum.append(this.isProcessingInstruction ? "!" : "?").append(">");
   }

   @Override
   void outerHtmlTail(Appendable accum, int depth, Document.OutputSettings out) {
   }

   @Override
   public String toString() {
      return this.outerHtml();
   }

   public XmlDeclaration clone() {
      return (XmlDeclaration)super.clone();
   }
}
