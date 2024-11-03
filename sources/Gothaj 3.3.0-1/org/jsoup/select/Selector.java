package org.jsoup.select;

import java.util.Collection;
import java.util.IdentityHashMap;
import javax.annotation.Nullable;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Element;

public class Selector {
   private Selector() {
   }

   public static Elements select(String query, Element root) {
      Validate.notEmpty(query);
      return select(QueryParser.parse(query), root);
   }

   public static Elements select(Evaluator evaluator, Element root) {
      Validate.notNull(evaluator);
      Validate.notNull(root);
      return Collector.collect(evaluator, root);
   }

   public static Elements select(String query, Iterable<Element> roots) {
      Validate.notEmpty(query);
      Validate.notNull(roots);
      Evaluator evaluator = QueryParser.parse(query);
      Elements elements = new Elements();
      IdentityHashMap<Element, Boolean> seenElements = new IdentityHashMap<>();

      for (Element root : roots) {
         for (Element el : select(evaluator, root)) {
            if (seenElements.put(el, Boolean.TRUE) == null) {
               elements.add(el);
            }
         }
      }

      return elements;
   }

   static Elements filterOut(Collection<Element> elements, Collection<Element> outs) {
      Elements output = new Elements();

      for (Element el : elements) {
         boolean found = false;

         for (Element out : outs) {
            if (el.equals(out)) {
               found = true;
               break;
            }
         }

         if (!found) {
            output.add(el);
         }
      }

      return output;
   }

   @Nullable
   public static Element selectFirst(String cssQuery, Element root) {
      Validate.notEmpty(cssQuery);
      return Collector.findFirst(QueryParser.parse(cssQuery), root);
   }

   public static class SelectorParseException extends IllegalStateException {
      public SelectorParseException(String msg) {
         super(msg);
      }

      public SelectorParseException(String msg, Object... params) {
         super(String.format(msg, params));
      }
   }
}
