package ninja.leaping.configurate.util;

import com.google.common.collect.Iterators;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;
import net.aIp;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.util.ConfigurationNodeWalker;
import ninja.leaping.configurate.util.ConfigurationNodeWalker$VisitedNode;
import ninja.leaping.configurate.util.ConfigurationNodeWalker$VisitedNodeImpl;

final class ConfigurationNodeWalker$BreadthFirstIterator implements Iterator {
   private final Queue queue = new ArrayDeque();

   ConfigurationNodeWalker$BreadthFirstIterator(ConfigurationNode var1) {
      this.queue.add(new ConfigurationNodeWalker$VisitedNodeImpl(var1.getPath(), var1));
   }

   public boolean hasNext() {
      String[] var1 = aIp.c();
      return !this.queue.isEmpty();
   }

   public ConfigurationNodeWalker$VisitedNode next() {
      ConfigurationNodeWalker$VisitedNodeImpl var1 = (ConfigurationNodeWalker$VisitedNodeImpl)this.queue.remove();
      Iterators.addAll(this.queue, ConfigurationNodeWalker.access$000(var1));
      return var1;
   }
}
