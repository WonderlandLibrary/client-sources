package ninja.leaping.configurate.util;

import java.util.Iterator;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.util.ConfigurationNodeWalker;
import ninja.leaping.configurate.util.ConfigurationNodeWalker$DepthFirstPreOrderIterator;

final class ConfigurationNodeWalker$2 extends ConfigurationNodeWalker {
   public Iterator walkWithPath(ConfigurationNode var1) {
      return new ConfigurationNodeWalker$DepthFirstPreOrderIterator(var1);
   }
}
