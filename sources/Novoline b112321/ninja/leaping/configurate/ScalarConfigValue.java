package ninja.leaping.configurate;

import java.util.Collections;
import java.util.Objects;
import net.acE;
import ninja.leaping.configurate.ConfigValue;
import ninja.leaping.configurate.SimpleConfigurationNode;
import ninja.leaping.configurate.ValueType;

class ScalarConfigValue extends ConfigValue {
   private volatile Object value;

   ScalarConfigValue(SimpleConfigurationNode var1) {
      super(var1);
   }

   ValueType getType() {
      return ValueType.SCALAR;
   }

   public Object getValue() {
      return this.value;
   }

   public void setValue(Object var1) {
      acE[] var2 = ValueType.b();
      if(!this.holder.getOptions().acceptsType(Objects.requireNonNull(var1).getClass())) {
         throw new IllegalArgumentException("Configuration does not accept objects of type " + var1.getClass());
      } else {
         this.value = var1;
      }
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

   ScalarConfigValue copy(SimpleConfigurationNode var1) {
      ScalarConfigValue var2 = new ScalarConfigValue(var1);
      var2.value = this.value;
      return var2;
   }

   public void clear() {
      this.value = null;
   }

   public boolean equals(Object var1) {
      acE[] var2 = ValueType.b();
      if(this == var1) {
         return true;
      } else if(var1 != null && this.getClass() == var1.getClass()) {
         ScalarConfigValue var3 = (ScalarConfigValue)var1;
         return Objects.equals(this.value, var3.value);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return 7 + Objects.hashCode(this.value);
   }

   public String toString() {
      return "ScalarConfigValue{value=" + this.value + '}';
   }

   private static IllegalArgumentException a(IllegalArgumentException var0) {
      return var0;
   }
}
