package ninja.leaping.configurate;

import java.util.Collections;
import ninja.leaping.configurate.ConfigValue;
import ninja.leaping.configurate.SimpleConfigurationNode;
import ninja.leaping.configurate.ValueType;

class NullConfigValue extends ConfigValue {
   NullConfigValue(SimpleConfigurationNode var1) {
      super(var1);
   }

   ValueType getType() {
      return ValueType.NULL;
   }

   public Object getValue() {
      return null;
   }

   public void setValue(Object var1) {
   }

   SimpleConfigurationNode putChild(Object var1, SimpleConfigurationNode var2) {
      return null;
   }

   SimpleConfigurationNode putChildIfAbsent(Object var1, SimpleConfigurationNode var2) {
      return null;
   }

   public SimpleConfigurationNode getChild(Object var1) {
      return null;
   }

   public Iterable iterateChildren() {
      return Collections.emptySet();
   }

   NullConfigValue copy(SimpleConfigurationNode var1) {
      return new NullConfigValue(var1);
   }

   public void clear() {
   }

   public boolean equals(Object var1) {
      return var1 instanceof NullConfigValue;
   }

   public int hashCode() {
      return 1009;
   }

   public String toString() {
      return "NullConfigValue{}";
   }
}
