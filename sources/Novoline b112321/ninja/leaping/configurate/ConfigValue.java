package ninja.leaping.configurate;

import java.util.Iterator;
import ninja.leaping.configurate.SimpleConfigurationNode;
import ninja.leaping.configurate.ValueType;

abstract class ConfigValue {
   protected final SimpleConfigurationNode holder;

   protected ConfigValue(SimpleConfigurationNode var1) {
      this.holder = var1;
   }

   abstract ValueType getType();

   abstract Object getValue();

   abstract void setValue(Object var1);

   abstract SimpleConfigurationNode putChild(Object var1, SimpleConfigurationNode var2);

   abstract SimpleConfigurationNode putChildIfAbsent(Object var1, SimpleConfigurationNode var2);

   abstract SimpleConfigurationNode getChild(Object var1);

   abstract Iterable iterateChildren();

   abstract ConfigValue copy(SimpleConfigurationNode var1);

   void clear() {
      ValueType.b();
      Iterator var2 = this.iterateChildren().iterator();
      if(var2.hasNext()) {
         SimpleConfigurationNode var3 = (SimpleConfigurationNode)var2.next();
         var3.attached = false;
         var2.remove();
         if(var3.getParentEnsureAttached().equals(this.holder)) {
            var3.clear();
         }
      }

   }
}
