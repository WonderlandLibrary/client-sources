package ninja.leaping.configurate.util;

import java.util.Iterator;
import net.alJ;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.util.ConfigurationNodeWalker;

final class ConfigurationNodeWalker$3 extends ConfigurationNodeWalker {
   public Iterator walkWithPath(ConfigurationNode var1) {
      return new alJ(var1);
   }
}
