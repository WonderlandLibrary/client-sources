package ninja.leaping.configurate.util;

import java.util.Iterator;
import net.alJ;
import ninja.leaping.configurate.util.ConfigurationNodeWalker$VisitedNodeImpl;

final class ConfigurationNodeWalker$DepthFirstPostOrderIterator$NodeAndChildren {
   final ConfigurationNodeWalker$VisitedNodeImpl node;
   final Iterator children;
   final alJ a;

   ConfigurationNodeWalker$DepthFirstPostOrderIterator$NodeAndChildren(alJ var1, ConfigurationNodeWalker$VisitedNodeImpl var2, Iterator var3) {
      this.a = var1;
      this.node = var2;
      this.children = var3;
   }
}
