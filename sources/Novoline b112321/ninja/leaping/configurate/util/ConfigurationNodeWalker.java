package ninja.leaping.configurate.util;

import com.google.common.base.Function;
import com.google.common.collect.Iterators;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import net.aIp;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.util.ConfigurationNodeWalker$1;
import ninja.leaping.configurate.util.ConfigurationNodeWalker$2;
import ninja.leaping.configurate.util.ConfigurationNodeWalker$3;
import ninja.leaping.configurate.util.ConfigurationNodeWalker$4;
import ninja.leaping.configurate.util.ConfigurationNodeWalker$VisitedNode;
import ninja.leaping.configurate.util.ConfigurationNodeWalker$VisitedNodeImpl;

public abstract class ConfigurationNodeWalker {
   public static final ConfigurationNodeWalker BREADTH_FIRST = new ConfigurationNodeWalker$1();
   public static final ConfigurationNodeWalker DEPTH_FIRST_PRE_ORDER = new ConfigurationNodeWalker$2();
   public static final ConfigurationNodeWalker DEPTH_FIRST_POST_ORDER = new ConfigurationNodeWalker$3();

   public abstract Iterator walkWithPath(ConfigurationNode var1);

   public Iterator walk(ConfigurationNode var1) {
      return Iterators.transform(this.walkWithPath(var1), ConfigurationNodeWalker$VisitedNode::getNode);
   }

   public void walk(ConfigurationNode var1, BiConsumer var2) {
      aIp.c();
      Iterator var4 = this.walkWithPath(var1);
      if(var4.hasNext()) {
         ConfigurationNodeWalker$VisitedNode var5 = (ConfigurationNodeWalker$VisitedNode)var4.next();
         var2.accept(var5.getPath(), var5.getNode());
      }

   }

   private static Object[] calculatePath(Object[] var0, Object var1) {
      String[] var2 = aIp.c();
      if(var0.length == 1 && var0[0] == null) {
         return new Object[]{var1};
      } else {
         Object[] var3 = Arrays.copyOf(var0, var0.length + 1);
         var3[var3.length - 1] = var1;
         return var3;
      }
   }

   private static Iterator getChildren(ConfigurationNodeWalker$VisitedNodeImpl var0) {
      ConfigurationNode var1 = var0.getNode();
      switch(ConfigurationNodeWalker$4.$SwitchMap$ninja$leaping$configurate$ValueType[var1.getValueType().ordinal()]) {
      case 1:
         Object[] var3 = var0.getRawPath();
         return Iterators.transform(var1.getChildrenList().iterator(), ConfigurationNodeWalker::lambda$getChildren$0);
      case 2:
         Object[] var2 = var0.getRawPath();
         return Iterators.transform(var1.getChildrenMap().entrySet().iterator(), ConfigurationNodeWalker::lambda$getChildren$1);
      default:
         return Collections.emptyIterator();
      }
   }

   private static ConfigurationNodeWalker$VisitedNodeImpl lambda$getChildren$1(Object[] var0, Entry var1) {
      Objects.requireNonNull(var1);
      ConfigurationNode var2 = (ConfigurationNode)var1.getValue();
      Object[] var3 = calculatePath(var0, var1.getKey());
      return new ConfigurationNodeWalker$VisitedNodeImpl(var3, var2);
   }

   private static ConfigurationNodeWalker$VisitedNodeImpl lambda$getChildren$0(Object[] var0, ConfigurationNode var1) {
      Objects.requireNonNull(var1);
      Object[] var3 = calculatePath(var0, var1.getKey());
      return new ConfigurationNodeWalker$VisitedNodeImpl(var3, var1);
   }

   static Iterator access$000(ConfigurationNodeWalker$VisitedNodeImpl var0) {
      return getChildren(var0);
   }
}
