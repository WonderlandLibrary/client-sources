package org.jsoup.select;

import javax.annotation.Nullable;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

public class Collector {
   private Collector() {
   }

   public static Elements collect(Evaluator eval, Element root) {
      Elements elements = new Elements();
      NodeTraversor.traverse(new Collector.Accumulator(root, elements, eval), root);
      return elements;
   }

   @Nullable
   public static Element findFirst(Evaluator eval, Element root) {
      Collector.FirstFinder finder = new Collector.FirstFinder(eval);
      return finder.find(root, root);
   }

   private static class Accumulator implements NodeVisitor {
      private final Element root;
      private final Elements elements;
      private final Evaluator eval;

      Accumulator(Element root, Elements elements, Evaluator eval) {
         this.root = root;
         this.elements = elements;
         this.eval = eval;
      }

      @Override
      public void head(Node node, int depth) {
         if (node instanceof Element) {
            Element el = (Element)node;
            if (this.eval.matches(this.root, el)) {
               this.elements.add(el);
            }
         }
      }

      @Override
      public void tail(Node node, int depth) {
      }
   }

   static class FirstFinder implements NodeFilter {
      @Nullable
      private Element evalRoot = null;
      @Nullable
      private Element match = null;
      private final Evaluator eval;

      FirstFinder(Evaluator eval) {
         this.eval = eval;
      }

      @Nullable
      Element find(Element root, Element start) {
         this.evalRoot = root;
         this.match = null;
         NodeTraversor.filter(this, start);
         return this.match;
      }

      @Override
      public NodeFilter.FilterResult head(Node node, int depth) {
         if (node instanceof Element) {
            Element el = (Element)node;
            if (this.eval.matches(this.evalRoot, el)) {
               this.match = el;
               return NodeFilter.FilterResult.STOP;
            }
         }

         return NodeFilter.FilterResult.CONTINUE;
      }

      @Override
      public NodeFilter.FilterResult tail(Node node, int depth) {
         return NodeFilter.FilterResult.CONTINUE;
      }
   }
}
