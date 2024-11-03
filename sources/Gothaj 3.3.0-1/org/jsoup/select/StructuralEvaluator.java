package org.jsoup.select;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

abstract class StructuralEvaluator extends Evaluator {
   Evaluator evaluator;

   static class Has extends StructuralEvaluator {
      final Collector.FirstFinder finder;

      public Has(Evaluator evaluator) {
         this.evaluator = evaluator;
         this.finder = new Collector.FirstFinder(evaluator);
      }

      @Override
      public boolean matches(Element root, Element element) {
         for (int i = 0; i < element.childNodeSize(); i++) {
            Node node = element.childNode(i);
            if (node instanceof Element) {
               Element match = this.finder.find(element, (Element)node);
               if (match != null) {
                  return true;
               }
            }
         }

         return false;
      }

      @Override
      public String toString() {
         return String.format(":has(%s)", this.evaluator);
      }
   }

   static class ImmediateParent extends StructuralEvaluator {
      public ImmediateParent(Evaluator evaluator) {
         this.evaluator = evaluator;
      }

      @Override
      public boolean matches(Element root, Element element) {
         if (root == element) {
            return false;
         } else {
            Element parent = element.parent();
            return parent != null && this.evaluator.matches(root, parent);
         }
      }

      @Override
      public String toString() {
         return String.format("%s > ", this.evaluator);
      }
   }

   static class ImmediatePreviousSibling extends StructuralEvaluator {
      public ImmediatePreviousSibling(Evaluator evaluator) {
         this.evaluator = evaluator;
      }

      @Override
      public boolean matches(Element root, Element element) {
         if (root == element) {
            return false;
         } else {
            Element prev = element.previousElementSibling();
            return prev != null && this.evaluator.matches(root, prev);
         }
      }

      @Override
      public String toString() {
         return String.format("%s + ", this.evaluator);
      }
   }

   static class Not extends StructuralEvaluator {
      public Not(Evaluator evaluator) {
         this.evaluator = evaluator;
      }

      @Override
      public boolean matches(Element root, Element node) {
         return !this.evaluator.matches(root, node);
      }

      @Override
      public String toString() {
         return String.format(":not(%s)", this.evaluator);
      }
   }

   static class Parent extends StructuralEvaluator {
      public Parent(Evaluator evaluator) {
         this.evaluator = evaluator;
      }

      @Override
      public boolean matches(Element root, Element element) {
         if (root == element) {
            return false;
         } else {
            for (Element parent = element.parent(); parent != null; parent = parent.parent()) {
               if (this.evaluator.matches(root, parent)) {
                  return true;
               }

               if (parent == root) {
                  break;
               }
            }

            return false;
         }
      }

      @Override
      public String toString() {
         return String.format("%s ", this.evaluator);
      }
   }

   static class PreviousSibling extends StructuralEvaluator {
      public PreviousSibling(Evaluator evaluator) {
         this.evaluator = evaluator;
      }

      @Override
      public boolean matches(Element root, Element element) {
         if (root == element) {
            return false;
         } else {
            for (Element prev = element.previousElementSibling(); prev != null; prev = prev.previousElementSibling()) {
               if (this.evaluator.matches(root, prev)) {
                  return true;
               }
            }

            return false;
         }
      }

      @Override
      public String toString() {
         return String.format("%s ~ ", this.evaluator);
      }
   }

   static class Root extends Evaluator {
      @Override
      public boolean matches(Element root, Element element) {
         return root == element;
      }
   }
}
