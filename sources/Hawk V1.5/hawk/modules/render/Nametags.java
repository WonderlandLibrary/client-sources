package hawk.modules.render;

import hawk.modules.Module;

public class Nametags extends Module {
   public Nametags() {
      super("Nametags", 0, Module.Category.RENDER);
   }

   public void onDisable() {
      this.mc.thePlayer.setAlwaysRenderNameTag(false);
   }

   public void onEnable() {
      this.mc.thePlayer.setAlwaysRenderNameTag(true);
   }
}
