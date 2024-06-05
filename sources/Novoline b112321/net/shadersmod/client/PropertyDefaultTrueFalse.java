package net.shadersmod.client;

import net.optifine.Lang;
import net.shadersmod.client.Property;
import net.shadersmod.client.ShaderOption;

public class PropertyDefaultTrueFalse extends Property {
   public static final String[] PROPERTY_VALUES = new String[]{"default", "true", "false"};
   public static final String[] USER_VALUES = new String[]{"Default", "ON", "OFF"};

   public PropertyDefaultTrueFalse(String var1, String var2, int var3) {
      super(var1, PROPERTY_VALUES, var2, USER_VALUES, var3);
   }

   public String getUserValue() {
      String[] var1 = ShaderOption.p();
      return this.isDefault()?Lang.getDefault():(this.isTrue()?Lang.getOn():(this.isFalse()?Lang.getOff():super.getUserValue()));
   }

   public boolean isDefault() {
      String[] var1 = ShaderOption.p();
      return this.getValue() == 0;
   }

   public boolean isTrue() {
      String[] var1 = ShaderOption.p();
      return this.getValue() == 1;
   }

   public boolean isFalse() {
      String[] var1 = ShaderOption.p();
      return this.getValue() == 2;
   }
}
