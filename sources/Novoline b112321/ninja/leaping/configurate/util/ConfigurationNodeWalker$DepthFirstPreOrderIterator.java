package ninja.leaping.configurate.util;

import com.google.common.collect.Iterators;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import net.aIp;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.util.ConfigurationNodeWalker;
import ninja.leaping.configurate.util.ConfigurationNodeWalker$VisitedNode;
import ninja.leaping.configurate.util.ConfigurationNodeWalker$VisitedNodeImpl;

final class ConfigurationNodeWalker$DepthFirstPreOrderIterator implements Iterator {
   private final Deque stack = new ArrayDeque();

   ConfigurationNodeWalker$DepthFirstPreOrderIterator(ConfigurationNode var1) {
      this.stack.push(Iterators.singletonIterator(new ConfigurationNodeWalker$VisitedNodeImpl(var1.getPath(), var1)));
   }

   public boolean hasNext() {
      String[] var1 = aIp.c();
      return !this.stack.isEmpty();
   }

   public ConfigurationNodeWalker$VisitedNode next() {
      Iterator var2 = (Iterator)this.stack.getLast();
      aIp.c();
      ConfigurationNodeWalker$VisitedNodeImpl var3 = (ConfigurationNodeWalker$VisitedNodeImpl)var2.next();
      if(!var2.hasNext()) {
         this.stack.removeLast();
      }

      Iterator var4 = ConfigurationNodeWalker.access$000(var3);
      if(var4.hasNext()) {
         this.stack.addLast(var4);
      }

      return var3;
   }
}
