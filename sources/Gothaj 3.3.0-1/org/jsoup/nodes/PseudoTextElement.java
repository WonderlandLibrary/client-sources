package org.jsoup.nodes;

import org.jsoup.parser.Tag;

public class PseudoTextElement extends Element {
   public PseudoTextElement(Tag tag, String baseUri, Attributes attributes) {
      super(tag, baseUri, attributes);
   }

   @Override
   void outerHtmlHead(Appendable accum, int depth, Document.OutputSettings out) {
   }

   @Override
   void outerHtmlTail(Appendable accum, int depth, Document.OutputSettings out) {
   }
}
