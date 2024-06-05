package ninja.leaping.configurate.util;

import com.google.common.collect.Iterators;
import java.util.Arrays;
import java.util.Iterator;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.transformation.NodePath;
import ninja.leaping.configurate.util.ConfigurationNodeWalker$VisitedNode;

final class ConfigurationNodeWalker$VisitedNodeImpl implements ConfigurationNodeWalker$VisitedNode, NodePath {
   private final Object[] path;
   private final ConfigurationNode node;

   ConfigurationNodeWalker$VisitedNodeImpl(Object[] var1, ConfigurationNode var2) {
      this.path = var1;
      this.node = var2;
   }

   Object[] getRawPath() {
      return this.path;
   }

   public ConfigurationNode getNode() {
      return this.node;
   }

   public NodePath getPath() {
      return this;
   }

   public Object get(int var1) {
      return this.path[var1];
   }

   public int size() {
      return this.path.length;
   }

   public Object[] getArray() {
      return Arrays.copyOf(this.path, this.path.length);
   }

   public Iterator iterator() {
      return Iterators.forArray(this.path);
   }
}
