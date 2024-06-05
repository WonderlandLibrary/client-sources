package ninja.leaping.configurate.util;

import java.util.Iterator;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.util.ConfigurationNodeWalker;
import ninja.leaping.configurate.util.ConfigurationNodeWalker$BreadthFirstIterator;

final class ConfigurationNodeWalker$1 extends ConfigurationNodeWalker {
   public Iterator walkWithPath(ConfigurationNode var1) {
      return new ConfigurationNodeWalker$BreadthFirstIterator(var1);
   }
}
