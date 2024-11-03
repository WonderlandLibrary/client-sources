package org.jsoup.select;

import org.jsoup.helper.Validate;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

public class NodeTraversor {
   public static void traverse(NodeVisitor visitor, Node root) {
      Validate.notNull(visitor);
      Validate.notNull(root);
      Node node = root;
      int depth = 0;

      label54:
      while (node != null) {
         Node parent = node.parentNode();
         int origSize = parent != null ? parent.childNodeSize() : 0;
         Node next = node.nextSibling();
         visitor.head(node, depth);
         if (parent != null && !node.hasParent()) {
            if (origSize != parent.childNodeSize()) {
               node = next;
               if (next == null) {
                  node = parent;
                  depth--;
               }
               continue;
            }

            node = parent.childNode(node.siblingIndex());
         }

         if (node.childNodeSize() > 0) {
            node = node.childNode(0);
            depth++;
         } else {
            while ($assertionsDisabled || node != null) {
               if (node.nextSibling() != null || depth <= 0) {
                  visitor.tail(node, depth);
                  if (node == root) {
                     return;
                  }

                  node = node.nextSibling();
                  continue label54;
               }

               visitor.tail(node, depth);
               node = node.parentNode();
               depth--;
            }

            throw new AssertionError();
         }
      }
   }

   public static void traverse(NodeVisitor visitor, Elements elements) {
      Validate.notNull(visitor);
      Validate.notNull(elements);

      for (Element el : elements) {
         traverse(visitor, el);
      }
   }

   public static NodeFilter.FilterResult filter(NodeFilter filter, Node root) {
      Node node = root;
      int depth = 0;

      label71:
      while (node != null) {
         NodeFilter.FilterResult result = filter.head(node, depth);
         if (result == NodeFilter.FilterResult.STOP) {
            return result;
         }

         if (result == NodeFilter.FilterResult.CONTINUE && node.childNodeSize() > 0) {
            node = node.childNode(0);
            depth++;
         } else {
            for (; $assertionsDisabled || node != null; result = NodeFilter.FilterResult.CONTINUE) {
               if (node.nextSibling() != null || depth <= 0) {
                  if (result == NodeFilter.FilterResult.CONTINUE || result == NodeFilter.FilterResult.SKIP_CHILDREN) {
                     result = filter.tail(node, depth);
                     if (result == NodeFilter.FilterResult.STOP) {
                        return result;
                     }
                  }

                  if (node == root) {
                     return result;
                  }

                  Node prev = node;
                  node = node.nextSibling();
                  if (result == NodeFilter.FilterResult.REMOVE) {
                     prev.remove();
                  }
                  continue label71;
               }

               if (result == NodeFilter.FilterResult.CONTINUE || result == NodeFilter.FilterResult.SKIP_CHILDREN) {
                  result = filter.tail(node, depth);
                  if (result == NodeFilter.FilterResult.STOP) {
                     return result;
                  }
               }

               Node prev = node;
               node = node.parentNode();
               depth--;
               if (result == NodeFilter.FilterResult.REMOVE) {
                  prev.remove();
               }
            }

            throw new AssertionError();
         }
      }

      return NodeFilter.FilterResult.CONTINUE;
   }

   public static void filter(NodeFilter filter, Elements elements) {
      Validate.notNull(filter);
      Validate.notNull(elements);

      for (Element el : elements) {
         if (filter(filter, el) == NodeFilter.FilterResult.STOP) {
            break;
         }
      }
   }
}
