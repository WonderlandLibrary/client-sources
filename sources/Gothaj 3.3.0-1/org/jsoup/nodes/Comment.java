package org.jsoup.nodes;

import java.io.IOException;
import javax.annotation.Nullable;
import org.jsoup.parser.ParseSettings;
import org.jsoup.parser.Parser;

public class Comment extends LeafNode {
   public Comment(String data) {
      this.value = data;
   }

   @Override
   public String nodeName() {
      return "#comment";
   }

   public String getData() {
      return this.coreValue();
   }

   public Comment setData(String data) {
      this.coreValue(data);
      return this;
   }

   @Override
   void outerHtmlHead(Appendable accum, int depth, Document.OutputSettings out) throws IOException {
      if (out.prettyPrint()
         && (this.siblingIndex() == 0 && this.parentNode instanceof Element && ((Element)this.parentNode).tag().formatAsBlock() || out.outline())) {
         this.indent(accum, depth, out);
      }

      accum.append("<!--").append(this.getData()).append("-->");
   }

   @Override
   void outerHtmlTail(Appendable accum, int depth, Document.OutputSettings out) {
   }

   @Override
   public String toString() {
      return this.outerHtml();
   }

   public Comment clone() {
      return (Comment)super.clone();
   }

   public boolean isXmlDeclaration() {
      String data = this.getData();
      return isXmlDeclarationData(data);
   }

   private static boolean isXmlDeclarationData(String data) {
      return data.length() > 1 && (data.startsWith("!") || data.startsWith("?"));
   }

   @Nullable
   public XmlDeclaration asXmlDeclaration() {
      String data = this.getData();
      XmlDeclaration decl = null;
      String declContent = data.substring(1, data.length() - 1);
      if (isXmlDeclarationData(declContent)) {
         return null;
      } else {
         String fragment = "<" + declContent + ">";
         Document doc = Parser.htmlParser().settings(ParseSettings.preserveCase).parseInput(fragment, this.baseUri());
         if (doc.body().children().size() > 0) {
            Element el = doc.body().child(0);
            decl = new XmlDeclaration(NodeUtils.parser(doc).settings().normalizeTag(el.tagName()), data.startsWith("!"));
            decl.attributes().addAll(el.attributes());
         }

         return decl;
      }
   }
}
