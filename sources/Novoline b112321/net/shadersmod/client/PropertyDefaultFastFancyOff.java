package net.shadersmod.client;

import net.optifine.Config;
import net.shadersmod.client.Property;
import net.shadersmod.client.ShaderOption;

public class PropertyDefaultFastFancyOff extends Property {
   public static final String[] PROPERTY_VALUES = new String[]{"default", "fast", "fancy", "off"};
   public static final String[] USER_VALUES = new String[]{"Default", "Fast", "Fancy", "OFF"};

   public PropertyDefaultFastFancyOff(String var1, String var2, int var3) {
      super(var1, PROPERTY_VALUES, var2, USER_VALUES, var3);
   }

   public boolean b() {
      String[] var1 = ShaderOption.p();
      return this.getValue() == 0;
   }

   public boolean isFast() {
      String[] var1 = ShaderOption.p();
      return this.getValue() == 1;
   }

   public boolean d() {
      String[] var1 = ShaderOption.p();
      return this.getValue() == 2;
   }

   public boolean c() {
      String[] var1 = ShaderOption.p();
      return this.getValue() == 3;
   }

   public boolean setPropertyValue(String var1) {
      String[] var2 = ShaderOption.p();
      if(Config.equals(var1, "none")) {
         var1 = "off";
      }

      return super.setPropertyValue(var1);
   }
}
