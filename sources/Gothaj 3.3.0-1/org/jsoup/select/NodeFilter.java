package org.jsoup.select;

import org.jsoup.nodes.Node;

public interface NodeFilter {
   NodeFilter.FilterResult head(Node var1, int var2);

   default NodeFilter.FilterResult tail(Node node, int depth) {
      return NodeFilter.FilterResult.CONTINUE;
   }

   public static enum FilterResult {
      CONTINUE,
      SKIP_CHILDREN,
      SKIP_ENTIRELY,
      REMOVE,
      STOP;
   }
}
