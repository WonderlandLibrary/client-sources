package net.shadersmod.client;

import net.shadersmod.client.ShaderOption;
import net.shadersmod.client.Shaders;

public class ShaderOptionScreen extends ShaderOption {
   public ShaderOptionScreen(String var1) {
      super(var1, (String)null, (String)null, new String[]{null}, (String)null, (String)null);
   }

   public String getNameText() {
      return Shaders.translate("screen." + this.getName(), this.getName());
   }

   public String getDescriptionText() {
      return Shaders.translate("screen." + this.getName() + ".comment", (String)null);
   }
}
